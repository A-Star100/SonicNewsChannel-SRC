package gnu.mapping;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Hashtable;

public class Namespace implements Externalizable, HasNamedParts {
    public static final Namespace EmptyNamespace = valueOf("");
    protected static final Hashtable nsTable = new Hashtable(50);
    int log2Size;
    private int mask;
    String name;
    int num_bindings;
    protected String prefix;
    protected SymbolRef[] table;

    public final String getName() {
        return this.name;
    }

    public final void setName(String name2) {
        this.name = name2;
    }

    public final String getPrefix() {
        return this.prefix;
    }

    protected Namespace() {
        this(64);
    }

    protected Namespace(int capacity) {
        this.prefix = "";
        this.log2Size = 4;
        while (true) {
            int i = this.log2Size;
            if (capacity > (1 << i)) {
                this.log2Size = i + 1;
            } else {
                int capacity2 = 1 << i;
                this.table = new SymbolRef[capacity2];
                this.mask = capacity2 - 1;
                return;
            }
        }
    }

    public static Namespace create(int capacity) {
        return new Namespace(capacity);
    }

    public static Namespace create() {
        return new Namespace(64);
    }

    public static Namespace getDefault() {
        return EmptyNamespace;
    }

    public static Symbol getDefaultSymbol(String name2) {
        return EmptyNamespace.getSymbol(name2);
    }

    public static Namespace valueOf() {
        return EmptyNamespace;
    }

    public static Namespace valueOf(String name2) {
        if (name2 == null) {
            name2 = "";
        }
        Hashtable hashtable = nsTable;
        synchronized (hashtable) {
            Namespace ns = (Namespace) hashtable.get(name2);
            if (ns != null) {
                return ns;
            }
            Namespace ns2 = new Namespace();
            ns2.setName(name2.intern());
            hashtable.put(name2, ns2);
            return ns2;
        }
    }

    public static Namespace valueOf(String uri, String prefix2) {
        if (prefix2 == null || prefix2.length() == 0) {
            return valueOf(uri);
        }
        String xname = prefix2 + " -> " + uri;
        Hashtable hashtable = nsTable;
        synchronized (hashtable) {
            Object old = hashtable.get(xname);
            if (old instanceof Namespace) {
                Namespace namespace = (Namespace) old;
                return namespace;
            }
            Namespace ns = new Namespace();
            ns.setName(uri.intern());
            ns.prefix = prefix2.intern();
            hashtable.put(xname, ns);
            return ns;
        }
    }

    public static Namespace valueOf(String uri, SimpleSymbol prefix2) {
        return valueOf(uri, prefix2 == null ? null : prefix2.getName());
    }

    public static Namespace makeUnknownNamespace(String prefix2) {
        String uri;
        if (prefix2 == null || prefix2 == "") {
            uri = "";
        } else {
            uri = "http://kawa.gnu.org/unknown-namespace/" + prefix2;
        }
        return valueOf(uri, prefix2);
    }

    public Object get(String key) {
        return Environment.getCurrent().get(getSymbol(key));
    }

    public boolean isConstant(String key) {
        return false;
    }

    public Symbol getSymbol(String key) {
        return lookup(key, key.hashCode(), true);
    }

    public Symbol lookup(String key) {
        return lookup(key, key.hashCode(), false);
    }

    /* access modifiers changed from: protected */
    public final Symbol lookupInternal(String key, int hash) {
        int index = this.mask & hash;
        SymbolRef prev = null;
        SymbolRef ref = this.table[index];
        while (ref != null) {
            SymbolRef next = ref.next;
            Symbol sym = ref.getSymbol();
            if (sym == null) {
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    prev.next = next;
                }
                this.num_bindings--;
            } else if (sym.getLocalPart().equals(key)) {
                return sym;
            } else {
                prev = ref;
            }
            ref = next;
        }
        return null;
    }

    public Symbol add(Symbol sym, int hash) {
        int index = this.mask & hash;
        SymbolRef ref = new SymbolRef(sym, this);
        sym.namespace = this;
        ref.next = this.table[index];
        SymbolRef[] symbolRefArr = this.table;
        symbolRefArr[index] = ref;
        int i = this.num_bindings + 1;
        this.num_bindings = i;
        if (i >= symbolRefArr.length) {
            rehash();
        }
        return sym;
    }

    public Symbol lookup(String key, int hash, boolean create) {
        Symbol sym;
        synchronized (this) {
            Symbol sym2 = lookupInternal(key, hash);
            if (sym2 != null) {
                return sym2;
            }
            if (!create) {
                return null;
            }
            if (this == EmptyNamespace) {
                sym = new SimpleSymbol(key);
            } else {
                sym = new Symbol(this, key);
            }
            Symbol add = add(sym, hash);
            return add;
        }
    }

    public boolean remove(Symbol symbol) {
        synchronized (this) {
            int index = this.mask & symbol.getLocalPart().hashCode();
            SymbolRef prev = null;
            SymbolRef ref = this.table[index];
            while (ref != null) {
                SymbolRef next = ref.next;
                Symbol refsym = ref.getSymbol();
                if (refsym != null) {
                    if (refsym != symbol) {
                        prev = ref;
                        ref = next;
                    }
                }
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    prev.next = next;
                }
                this.num_bindings--;
                if (refsym != null) {
                    return true;
                }
                ref = next;
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void rehash() {
        int oldCapacity = this.table.length;
        int newCapacity = oldCapacity * 2;
        int newMask = newCapacity - 1;
        int countInserted = 0;
        SymbolRef[] oldTable = this.table;
        SymbolRef[] newTable = new SymbolRef[newCapacity];
        int i = oldCapacity;
        while (true) {
            i--;
            if (i >= 0) {
                SymbolRef ref = oldTable[i];
                while (ref != null) {
                    SymbolRef next = ref.next;
                    Symbol sym = ref.getSymbol();
                    if (sym != null) {
                        int index = sym.getName().hashCode() & newMask;
                        countInserted++;
                        ref.next = newTable[index];
                        newTable[index] = ref;
                    }
                    ref = next;
                }
            } else {
                this.table = newTable;
                this.log2Size++;
                this.mask = newMask;
                this.num_bindings = countInserted;
                return;
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(this.prefix);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = ((String) in.readObject()).intern();
        this.prefix = (String) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        String name2 = getName();
        if (name2 != null) {
            String str = this.prefix;
            String xname = (str == null || str.length() == 0) ? name2 : this.prefix + " -> " + name2;
            Hashtable hashtable = nsTable;
            Namespace ns = (Namespace) hashtable.get(xname);
            if (ns != null) {
                return ns;
            }
            hashtable.put(xname, this);
        }
        return this;
    }

    public String toString() {
        StringBuilder sbuf = new StringBuilder("#,(namespace \"");
        sbuf.append(this.name);
        sbuf.append('\"');
        String str = this.prefix;
        if (!(str == null || str == "")) {
            sbuf.append(' ');
            sbuf.append(this.prefix);
        }
        sbuf.append(')');
        return sbuf.toString();
    }
}
