package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.mapping.OutPort;

public class FluidLetExp extends LetExp {
    public FluidLetExp(Expression[] i) {
        super(i);
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return true;
    }

    public void compile(Compilation comp, Target target) {
        Target ttarg;
        Compilation compilation = comp;
        Target target2 = target;
        CodeAttr code = comp.getCode();
        Type result_type = target2 instanceof IgnoreTarget ? null : getType();
        if (result_type == null) {
            ttarg = Target.Ignore;
        } else if (result_type == Type.pointer_type) {
            ttarg = Target.pushObject;
        } else {
            ttarg = new StackTarget(result_type);
        }
        Scope scope = getVarScope();
        code.enterScope(scope);
        Variable ctx = scope.addVariable(code, Compilation.typeCallContext, (String) null);
        comp.loadCallContext();
        code.emitStore(ctx);
        Variable[] save = new Variable[this.inits.length];
        Declaration decl = firstDecl();
        doInits(decl, 0, save, comp, ctx);
        code.emitTryStart(true, result_type);
        this.body.compileWithPosition(compilation, ttarg);
        code.emitFinallyStart();
        int i = 0;
        Declaration decl2 = decl;
        while (i < this.inits.length) {
            decl2.load((AccessExp) null, 2, compilation, Target.pushObject);
            code.emitLoad(save[i]);
            code.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("setRestore", 1));
            i++;
            decl2 = decl2.nextDecl();
        }
        code.emitTryCatchEnd();
        popScope(code);
        if (result_type != null) {
            target2.compileFromStack(compilation, result_type);
        }
    }

    private void doInits(Declaration decl, int i, Variable[] save, Compilation comp, Variable ctx) {
        if (i < this.inits.length) {
            CodeAttr code = comp.getCode();
            save[i] = code.addLocal(Type.pointer_type);
            decl.allocateVariable(code);
            decl.base.load((AccessExp) null, 2, comp, Target.pushObject);
            code.emitDup();
            code.emitStore(decl.getVariable());
            this.inits[i].compile(comp, Target.pushObject);
            doInits(decl.nextDecl(), i + 1, save, comp, ctx);
            code.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("setWithSave", 1));
            code.emitStore(save[i]);
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitFluidLetExp(this, d);
    }

    public void print(OutPort out) {
        print(out, "(FluidLet", ")");
    }
}
