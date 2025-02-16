package gnu.bytecode;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class VarEnumerator implements Enumeration {
    Scope currentScope;
    Variable next;
    Scope topScope;

    public VarEnumerator(Scope scope) {
        this.topScope = scope;
        reset();
    }

    public final void reset() {
        Scope scope = this.topScope;
        this.currentScope = scope;
        if (scope != null) {
            Variable firstVar = scope.firstVar();
            this.next = firstVar;
            if (firstVar == null) {
                fixup();
            }
        }
    }

    private void fixup() {
        while (this.next == null) {
            if (this.currentScope.firstChild != null) {
                this.currentScope = this.currentScope.firstChild;
            } else {
                while (this.currentScope.nextSibling == null) {
                    Scope scope = this.currentScope;
                    if (scope != this.topScope) {
                        this.currentScope = scope.parent;
                    } else {
                        return;
                    }
                }
                this.currentScope = this.currentScope.nextSibling;
            }
            this.next = this.currentScope.firstVar();
        }
    }

    public final Variable nextVar() {
        Variable result = this.next;
        if (result != null) {
            Variable nextVar = result.nextVar();
            this.next = nextVar;
            if (nextVar == null) {
                fixup();
            }
        }
        return result;
    }

    public final boolean hasMoreElements() {
        return this.next != null;
    }

    public Object nextElement() {
        Variable result = nextVar();
        if (result != null) {
            return result;
        }
        throw new NoSuchElementException("VarEnumerator");
    }
}
