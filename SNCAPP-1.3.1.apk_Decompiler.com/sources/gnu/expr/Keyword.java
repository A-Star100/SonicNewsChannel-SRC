package gnu.expr;

import gnu.lists.Consumer;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Keyword extends Symbol implements Printable, Externalizable {
    public static final Namespace keywordNamespace;

    static {
        Namespace create = Namespace.create();
        keywordNamespace = create;
        create.setName("(keywords)");
    }

    public Keyword() {
    }

    private Keyword(String name) {
        super(keywordNamespace, name);
    }

    public Keyword(Namespace namespace, String name) {
        super(namespace, name);
    }

    public Symbol asSymbol() {
        return Namespace.EmptyNamespace.getSymbol(getName());
    }

    public static Keyword make(String name) {
        int hash = name.hashCode();
        Namespace namespace = keywordNamespace;
        Keyword keyword = (Keyword) namespace.lookup(name, hash, false);
        if (keyword != null) {
            return keyword;
        }
        Keyword keyword2 = new Keyword(name);
        namespace.add(keyword2, hash);
        return keyword2;
    }

    public static boolean isKeyword(Object obj) {
        return obj instanceof Keyword;
    }

    public final String toString() {
        return getName() + ':';
    }

    public void print(Consumer out) {
        Symbols.print(getName(), out);
        out.write(58);
    }

    public static Object searchForKeyword(Object[] vals, int offset, Object keyword) {
        for (int i = offset; i < vals.length; i += 2) {
            if (vals[i] == keyword) {
                return vals[i + 1];
            }
        }
        return Special.dfault;
    }

    public static Object searchForKeyword(Object[] vals, int offset, Object keyword, Object dfault) {
        for (int i = offset; i < vals.length; i += 2) {
            if (vals[i] == keyword) {
                return vals[i + 1];
            }
        }
        return dfault;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        return make(keywordNamespace, getName());
    }
}
