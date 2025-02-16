package kawa.lang;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Environment;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;

public class AutoloadSyntax extends Syntax implements Externalizable {
    String className;
    Environment env;
    Syntax loaded;

    public AutoloadSyntax() {
    }

    public AutoloadSyntax(String name, String className2) {
        super(name);
        this.className = className2;
    }

    public AutoloadSyntax(String name, String className2, Environment env2) {
        super(name);
        this.className = className2;
        this.env = env2;
    }

    public void print(PrintWriter ps) {
        ps.print(toString());
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append("#<syntax ");
        if (getName() != null) {
            sbuf.append(getName());
            sbuf.append(' ');
        }
        if (this.loaded != null) {
            sbuf.append("autoloaded>");
        } else {
            sbuf.append("autoload ");
            sbuf.append(this.className);
            sbuf.append(">");
        }
        return sbuf.toString();
    }

    private void throw_error(String prefix) {
        throw new GenericError(prefix + this.className + " while autoloading " + (getName() == null ? "" : getName().toString()));
    }

    /* access modifiers changed from: package-private */
    public void load() {
        String name = getName();
        try {
            Object newInstance = Class.forName(this.className).newInstance();
            if (newInstance instanceof Syntax) {
                Syntax syntax = (Syntax) newInstance;
                this.loaded = syntax;
                if (name != null && syntax.getName() == null) {
                    this.loaded.setName(name);
                    return;
                }
                return;
            }
            throw_error("failed to autoload valid syntax object ");
        } catch (ClassNotFoundException e) {
            throw_error("failed to find class ");
        } catch (InstantiationException e2) {
            throw_error("failed to instantiate class ");
        } catch (IllegalAccessException e3) {
            throw_error("illegal access in class ");
        } catch (UnboundLocationException e4) {
            throw_error("missing symbol '" + e4.getMessage() + "' ");
        } catch (WrongArguments e5) {
            throw_error("type error");
        }
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        if (this.loaded == null) {
            try {
                load();
            } catch (RuntimeException e) {
                tr.syntaxError(e.getMessage());
                return;
            }
        }
        this.loaded.scanForm(st, defs, tr);
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (this.loaded == null) {
            try {
                load();
            } catch (GenericError e) {
                return tr.syntaxError(e.getMessage());
            } catch (WrongType e2) {
                return tr.syntaxError(e2.getMessage());
            }
        }
        Syntax saveSyntax = tr.currentSyntax;
        tr.currentSyntax = this.loaded;
        try {
            return this.loaded.rewriteForm(form, tr);
        } finally {
            tr.currentSyntax = saveSyntax;
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(this.className);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String) in.readObject());
        this.className = (String) in.readObject();
    }
}
