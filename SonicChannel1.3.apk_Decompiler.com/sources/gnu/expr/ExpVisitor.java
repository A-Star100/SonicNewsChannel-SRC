package gnu.expr;

import gnu.text.SourceLocator;
import gnu.text.SourceMessages;

public class ExpVisitor<R, D> implements SourceLocator {
    Compilation comp;
    protected LambdaExp currentLambda = null;
    protected Object exitValue = null;
    protected SourceMessages messages;

    public Compilation getCompilation() {
        return this.comp;
    }

    public SourceMessages getMessages() {
        return this.messages;
    }

    /* access modifiers changed from: protected */
    public R defaultValue(Expression r, D d) {
        return null;
    }

    /* access modifiers changed from: protected */
    public R visitExpression(Expression exp, D d) {
        exp.visitChildren(this, d);
        return defaultValue(exp, d);
    }

    public void setContext(Compilation comp2) {
        this.comp = comp2;
        this.messages = comp2.getMessages();
    }

    public R visit(Expression exp, D d) {
        int line = exp.getLineNumber();
        SourceMessages sourceMessages = this.messages;
        if (sourceMessages == null || line <= 0) {
            return exp.visit(this, d);
        }
        String saveFile = sourceMessages.getFileName();
        int saveLine = this.messages.getLineNumber();
        int saveColumn = this.messages.getColumnNumber();
        this.messages.setLine(exp.getFileName(), line, exp.getColumnNumber());
        R ret = exp.visit(this, d);
        this.messages.setLine(saveFile, saveLine, saveColumn);
        return ret;
    }

    /* access modifiers changed from: protected */
    public Expression update(Expression exp, R r) {
        return exp;
    }

    /* access modifiers changed from: protected */
    public R visitApplyExp(ApplyExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitIfExp(IfExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public final void visitDeclarationType(Declaration decl) {
        Expression texp2;
        Expression texp1 = decl.typeExp;
        if (texp1 != null && (texp2 = visitAndUpdate(texp1, (Object) null)) != texp1) {
            decl.setTypeExp(texp2);
        }
    }

    /* access modifiers changed from: protected */
    public final void visitDeclarationTypes(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            visitDeclarationType(decl);
        }
    }

    /* access modifiers changed from: protected */
    public R visitScopeExp(ScopeExp exp, D d) {
        visitDeclarationTypes(exp);
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitLetExp(LetExp exp, D d) {
        return visitScopeExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitLambdaExp(LambdaExp exp, D d) {
        return visitScopeExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitClassExp(ClassExp exp, D d) {
        return visitLambdaExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitObjectExp(ObjectExp exp, D d) {
        return visitClassExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitModuleExp(ModuleExp exp, D d) {
        return visitLambdaExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public Expression visitSetExpValue(Expression new_value, D d, Declaration decl) {
        return visitAndUpdate(new_value, d);
    }

    /* access modifiers changed from: protected */
    public R visitSetExp(SetExp exp, D d) {
        Declaration decl = exp.binding;
        boolean updateNeeded = decl != null && decl.value == exp.new_value;
        exp.new_value = visitSetExpValue(exp.new_value, d, exp.getBinding());
        if (updateNeeded && exp.isDefining()) {
            decl.value = exp.new_value;
            if (exp.new_value instanceof LambdaExp) {
                ((LambdaExp) exp.new_value).nameDecl = decl;
            }
        }
        return defaultValue(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitTryExp(TryExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitBeginExp(BeginExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitQuoteExp(QuoteExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitReferenceExp(ReferenceExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitThisExp(ThisExp exp, D d) {
        return visitReferenceExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitSynchronizedExp(SynchronizedExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitBlockExp(BlockExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitExitExp(ExitExp exp, D d) {
        return visitExpression(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitFluidLetExp(FluidLetExp exp, D d) {
        return visitLetExp(exp, d);
    }

    /* access modifiers changed from: protected */
    public R visitLangExp(LangExp exp, D d) {
        return visitExpression(exp, d);
    }

    public Object getExitValue() {
        return this.exitValue;
    }

    public final LambdaExp getCurrentLambda() {
        return this.currentLambda;
    }

    public Expression visitAndUpdate(Expression exp, D d) {
        return update(exp, visit(exp, d));
    }

    public Expression[] visitExps(Expression[] exps, D d) {
        if (exps == null) {
            return null;
        }
        return visitExps(exps, exps.length, d);
    }

    public Expression[] visitExps(Expression[] exps, int n, D d) {
        for (int i = 0; i < n && this.exitValue == null; i++) {
            exps[i] = visitAndUpdate(exps[i], d);
        }
        return exps;
    }

    public void visitDefaultArgs(LambdaExp exp, D d) {
        exp.defaultArgs = visitExps(exp.defaultArgs, d);
    }

    public void error(char kind, String message) {
        if (kind == 'w' && this.comp.warnAsError()) {
            kind = 'e';
        }
        SourceMessages sourceMessages = this.messages;
        if (sourceMessages != null) {
            sourceMessages.error(kind, message);
        } else {
            new Error("internal error: " + message);
        }
    }

    public Expression noteError(String message) {
        SourceMessages sourceMessages = this.messages;
        if (sourceMessages != null) {
            sourceMessages.error('e', message);
        }
        return new ErrorExp(message);
    }

    public final String getFileName() {
        return this.messages.getFileName();
    }

    public final int getLineNumber() {
        return this.messages.getLineNumber();
    }

    public final int getColumnNumber() {
        return this.messages.getColumnNumber();
    }

    public String getPublicId() {
        return this.messages.getPublicId();
    }

    public String getSystemId() {
        return this.messages.getSystemId();
    }

    public boolean isStableSourceLocation() {
        return false;
    }

    public void setFile(String filename) {
        this.messages.setFile(filename);
    }

    public void setLine(int line) {
        this.messages.setLine(line);
    }

    public void setColumn(int column) {
        this.messages.setColumn(column);
    }

    public void setLine(String filename, int line, int column) {
        this.messages.setLine(filename, line, column);
    }
}
