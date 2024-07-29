package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import java.util.Set;
import java.util.Vector;

public class LambdaExp extends ScopeExp {
    public static final int ATTEMPT_INLINE = 4096;
    static final int CANNOT_INLINE = 32;
    static final int CAN_CALL = 4;
    static final int CAN_READ = 2;
    static final int CLASS_METHOD = 64;
    static final int DEFAULT_CAPTURES_ARG = 512;
    static final int IMPORTS_LEX_VARS = 8;
    static final int INLINE_ONLY = 8192;
    static final int METHODS_COMPILED = 128;
    static final int NEEDS_STATIC_LINK = 16;
    protected static final int NEXT_AVAIL_FLAG = 16384;
    public static final int NO_FIELD = 256;
    public static final int OVERLOADABLE_FIELD = 2048;
    public static final int SEQUENCE_RESULT = 1024;
    static Method searchForKeywordMethod3;
    static Method searchForKeywordMethod4;
    static final ApplyExp unknownContinuation = new ApplyExp((Expression) null, (Expression[]) null);
    Vector applyMethods;
    Variable argsArray;
    public Expression body;
    Declaration capturedVars;
    Variable closureEnv;
    public Field closureEnvField;
    public Expression[] defaultArgs;
    private Declaration firstArgsArrayArg;
    public LambdaExp firstChild;
    Variable heapFrame;
    Initializer initChain;
    public LambdaExp inlineHome;
    public Keyword[] keywords;
    public int max_args;
    public int min_args;
    public Declaration nameDecl;
    public LambdaExp nextSibling;
    Method[] primBodyMethods;
    Method[] primMethods;
    Object[] properties;
    public Expression returnContinuation;
    public Type returnType;
    int selectorValue;
    public Field staticLinkField;
    Set<LambdaExp> tailCallers;
    Procedure thisValue;
    Variable thisVariable;
    Expression[] throwsSpecification;
    ClassType type = Compilation.typeProcedure;

    public void capture(Declaration decl) {
        if (decl.isSimple()) {
            if (this.capturedVars == null && !decl.isStatic() && !(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
                this.heapFrame = new Variable("heapFrame");
            }
            decl.setSimple(false);
            if (!decl.isPublic()) {
                decl.nextCapturedVar = this.capturedVars;
                this.capturedVars = decl;
            }
        }
    }

    static {
        Expression expression = null;
        Expression[] expressionArr = null;
    }

    public void setExceptions(Expression[] exceptions) {
        this.throwsSpecification = exceptions;
    }

    public final boolean getInlineOnly() {
        return (this.flags & 8192) != 0;
    }

    public final void setInlineOnly(boolean inlineOnly) {
        setFlag(inlineOnly, 8192);
    }

    public final boolean getNeedsClosureEnv() {
        return (this.flags & 24) != 0;
    }

    public final boolean getNeedsStaticLink() {
        return (this.flags & 16) != 0;
    }

    public final void setNeedsStaticLink(boolean needsStaticLink) {
        if (needsStaticLink) {
            this.flags |= 16;
        } else {
            this.flags &= -17;
        }
    }

    public final boolean getImportsLexVars() {
        return (this.flags & 8) != 0;
    }

    public final void setImportsLexVars(boolean importsLexVars) {
        if (importsLexVars) {
            this.flags |= 8;
        } else {
            this.flags &= -9;
        }
    }

    public final void setImportsLexVars() {
        int old = this.flags;
        this.flags |= 8;
        if ((old & 8) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    public final void setNeedsStaticLink() {
        int old = this.flags;
        this.flags |= 16;
        if ((old & 16) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    /* access modifiers changed from: package-private */
    public void setCallersNeedStaticLink() {
        LambdaExp outer = outerLambda();
        for (ApplyExp app = this.nameDecl.firstCall; app != null; app = app.nextCall) {
            LambdaExp caller = app.context;
            while (caller != outer && !(caller instanceof ModuleExp)) {
                caller.setNeedsStaticLink();
                caller = caller.outerLambda();
            }
        }
    }

    public final boolean getCanRead() {
        return (this.flags & 2) != 0;
    }

    public final void setCanRead(boolean read) {
        if (read) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    public final boolean getCanCall() {
        return (this.flags & 4) != 0;
    }

    public final void setCanCall(boolean called) {
        if (called) {
            this.flags |= 4;
        } else {
            this.flags &= -5;
        }
    }

    public final boolean isClassMethod() {
        return (this.flags & 64) != 0;
    }

    public final void setClassMethod(boolean isMethod) {
        if (isMethod) {
            this.flags |= 64;
        } else {
            this.flags &= -65;
        }
    }

    public final boolean isModuleBody() {
        return this instanceof ModuleExp;
    }

    public final boolean isClassGenerated() {
        return isModuleBody() || (this instanceof ClassExp);
    }

    public boolean isAbstract() {
        return this.body == QuoteExp.abstractExp;
    }

    public int getCallConvention() {
        if (isModuleBody()) {
            if (Compilation.defaultCallConvention >= 2) {
                return Compilation.defaultCallConvention;
            }
            return 2;
        } else if (!isClassMethod() && Compilation.defaultCallConvention != 0) {
            return Compilation.defaultCallConvention;
        } else {
            return 1;
        }
    }

    public final boolean isHandlingTailCalls() {
        return isModuleBody() || (Compilation.defaultCallConvention >= 3 && !isClassMethod());
    }

    public final boolean variable_args() {
        return this.max_args < 0;
    }

    /* access modifiers changed from: protected */
    public ClassType getCompiledClassType(Compilation comp) {
        if (this.type != Compilation.typeProcedure) {
            return this.type;
        }
        throw new Error("internal error: getCompiledClassType");
    }

    public Type getType() {
        return this.type;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public void setType(ClassType type2) {
        this.type = type2;
    }

    public int incomingArgs() {
        int i = this.min_args;
        int i2 = this.max_args;
        if (i != i2 || i2 > 4 || i2 <= 0) {
            return 1;
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public int getSelectorValue(Compilation comp) {
        int s = this.selectorValue;
        if (s != 0) {
            return s;
        }
        int s2 = comp.maxSelectorValue;
        comp.maxSelectorValue = this.primMethods.length + s2;
        int s3 = s2 + 1;
        this.selectorValue = s3;
        return s3;
    }

    public final Method getMethod(int argCount) {
        int i;
        int index;
        Method[] methodArr = this.primMethods;
        if (methodArr == null || (((i = this.max_args) >= 0 && argCount > i) || (index = argCount - this.min_args) < 0)) {
            return null;
        }
        int length = methodArr.length;
        return methodArr[index < length ? index : length - 1];
    }

    public final Method getMainMethod() {
        Method[] methods = this.primBodyMethods;
        if (methods == null) {
            return null;
        }
        return methods[methods.length - 1];
    }

    public final Type restArgType() {
        int i = this.min_args;
        int i2 = this.max_args;
        if (i == i2) {
            return null;
        }
        if (this.primMethods != null) {
            Method[] methods = this.primMethods;
            if (i2 >= 0 && methods.length > i2 - i) {
                return null;
            }
            Method method = methods[methods.length - 1];
            Type[] types = method.getParameterTypes();
            int ilast = types.length - 1;
            if (method.getName().endsWith("$X")) {
                ilast--;
            }
            return types[ilast];
        }
        throw new Error("internal error - restArgType");
    }

    public LambdaExp outerLambda() {
        if (this.outer == null) {
            return null;
        }
        return this.outer.currentLambda();
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.LambdaExp outerLambdaNotInline() {
        /*
            r3 = this;
            r0 = r3
        L_0x0001:
            gnu.expr.ScopeExp r1 = r0.outer
            r0 = r1
            if (r1 == 0) goto L_0x0015
            boolean r1 = r0 instanceof gnu.expr.LambdaExp
            if (r1 == 0) goto L_0x0001
            r1 = r0
            gnu.expr.LambdaExp r1 = (gnu.expr.LambdaExp) r1
            boolean r2 = r1.getInlineOnly()
            if (r2 != 0) goto L_0x0014
            return r1
        L_0x0014:
            goto L_0x0001
        L_0x0015:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.outerLambdaNotInline():gnu.expr.LambdaExp");
    }

    /* access modifiers changed from: package-private */
    public boolean inlinedIn(LambdaExp outer) {
        for (LambdaExp exp = this; exp.getInlineOnly(); exp = exp.getCaller()) {
            if (exp == outer) {
                return true;
            }
        }
        return false;
    }

    public LambdaExp getCaller() {
        return this.inlineHome;
    }

    public Variable declareThis(ClassType clas) {
        if (this.thisVariable == null) {
            this.thisVariable = new Variable("this");
            getVarScope().addVariableAfter((Variable) null, this.thisVariable);
            this.thisVariable.setParameter(true);
        }
        if (this.thisVariable.getType() == null) {
            this.thisVariable.setType(clas);
        }
        if (this.decls != null && this.decls.isThisParameter()) {
            this.decls.var = this.thisVariable;
        }
        return this.thisVariable;
    }

    public Variable declareClosureEnv() {
        Variable prev;
        if (this.closureEnv == null && getNeedsClosureEnv()) {
            LambdaExp parent = outerLambda();
            if (parent instanceof ClassExp) {
                parent = parent.outerLambda();
            }
            Variable parentFrame = parent.heapFrame;
            if (parentFrame == null) {
                parentFrame = parent.closureEnv;
            }
            if (isClassMethod() && !"*init*".equals(getName())) {
                this.closureEnv = declareThis(this.type);
            } else if (parent.heapFrame == null && !parent.getNeedsStaticLink() && !(parent instanceof ModuleExp)) {
                this.closureEnv = null;
            } else if (!isClassGenerated() && !getInlineOnly()) {
                Method primMethod = getMainMethod();
                boolean isInit = "*init*".equals(getName());
                if (primMethod.getStaticFlag() || isInit) {
                    this.closureEnv = new Variable("closureEnv", primMethod.getParameterTypes()[0]);
                    if (isInit) {
                        prev = declareThis(primMethod.getDeclaringClass());
                    } else {
                        prev = null;
                    }
                    getVarScope().addVariableAfter(prev, this.closureEnv);
                    this.closureEnv.setParameter(true);
                } else {
                    this.closureEnv = declareThis(primMethod.getDeclaringClass());
                }
            } else if (inlinedIn(parent)) {
                this.closureEnv = parentFrame;
            } else {
                this.closureEnv = new Variable("closureEnv", parentFrame.getType());
                getVarScope().addVariable(this.closureEnv);
            }
        }
        return this.closureEnv;
    }

    public LambdaExp() {
    }

    public LambdaExp(int args) {
        this.min_args = args;
        this.max_args = args;
    }

    public LambdaExp(Expression body2) {
        this.body = body2;
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [gnu.bytecode.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadHeapFrame(gnu.expr.Compilation r6) {
        /*
            r5 = this;
            gnu.expr.LambdaExp r0 = r6.curLambda
        L_0x0002:
            if (r0 == r5) goto L_0x000f
            boolean r1 = r0.getInlineOnly()
            if (r1 == 0) goto L_0x000f
            gnu.expr.LambdaExp r0 = r0.getCaller()
            goto L_0x0002
        L_0x000f:
            gnu.bytecode.CodeAttr r1 = r6.getCode()
            gnu.bytecode.Variable r2 = r0.heapFrame
            if (r2 == 0) goto L_0x001d
            if (r5 != r0) goto L_0x001d
            r1.emitLoad(r2)
            return
        L_0x001d:
            gnu.bytecode.Variable r2 = r0.closureEnv
            if (r2 == 0) goto L_0x002d
            r1.emitLoad(r2)
            gnu.bytecode.Variable r2 = r0.closureEnv
            gnu.bytecode.Type r2 = r2.getType()
            gnu.bytecode.ClassType r2 = (gnu.bytecode.ClassType) r2
            goto L_0x0032
        L_0x002d:
            r1.emitPushThis()
            gnu.bytecode.ClassType r2 = r6.curClass
        L_0x0032:
            if (r0 == r5) goto L_0x004d
            gnu.bytecode.Field r3 = r0.staticLinkField
            if (r3 == 0) goto L_0x0048
            gnu.bytecode.ClassType r4 = r3.getDeclaringClass()
            if (r4 != r2) goto L_0x0048
            r1.emitGetField(r3)
            gnu.bytecode.Type r4 = r3.getType()
            r2 = r4
            gnu.bytecode.ClassType r2 = (gnu.bytecode.ClassType) r2
        L_0x0048:
            gnu.expr.LambdaExp r0 = r0.outerLambda()
            goto L_0x0032
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.loadHeapFrame(gnu.expr.Compilation):void");
    }

    /* access modifiers changed from: package-private */
    public Declaration getArg(int i) {
        for (Declaration var = firstDecl(); var != null; var = var.nextDecl()) {
            if (i == 0) {
                return var;
            }
            i--;
        }
        throw new Error("internal error - getArg");
    }

    public void compileEnd(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!getInlineOnly()) {
            if (comp.method.reachableHere() && (Compilation.defaultCallConvention < 3 || isModuleBody() || isClassMethod() || isHandlingTailCalls())) {
                code.emitReturn();
            }
            popScope(code);
            code.popScope();
        }
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            if (!child.getCanRead() && !child.getInlineOnly()) {
                child.compileAsMethod(comp);
            }
        }
        if (this.heapFrame != null) {
            comp.generateConstructor(this);
        }
    }

    public void generateApplyMethods(Compilation comp) {
        comp.generateMatchMethods(this);
        if (Compilation.defaultCallConvention >= 2) {
            comp.generateApplyMethodsWithContext(this);
        } else {
            comp.generateApplyMethodsWithoutContext(this);
        }
    }

    /* access modifiers changed from: package-private */
    public Field allocFieldFor(Compilation comp) {
        Declaration declaration = this.nameDecl;
        if (declaration != null && declaration.field != null) {
            return this.nameDecl.field;
        }
        boolean needsClosure = getNeedsClosureEnv();
        ClassType frameType = needsClosure ? getOwningLambda().getHeapFrameType() : comp.mainClass;
        String name = getName();
        String fname = name == null ? "lambda" : Compilation.mangleNameIfNeeded(name);
        int fflags = 16;
        Declaration declaration2 = this.nameDecl;
        int suffix = 1;
        if (declaration2 == null || !(declaration2.context instanceof ModuleExp)) {
            StringBuilder append = new StringBuilder().append(fname).append("$Fn");
            int i = comp.localFieldIndex + 1;
            comp.localFieldIndex = i;
            fname = append.append(i).toString();
            if (!needsClosure) {
                fflags = 16 | 8;
            }
        } else {
            boolean external_access = this.nameDecl.needsExternalAccess();
            if (external_access) {
                fname = Declaration.PRIVATE_PREFIX + fname;
            }
            if (this.nameDecl.getFlag(2048)) {
                fflags = 16 | 8;
                if (!((ModuleExp) this.nameDecl.context).isStatic()) {
                    fflags &= -17;
                }
            }
            if (!this.nameDecl.isPrivate() || external_access || comp.immediate) {
                fflags |= 1;
            }
            if ((this.flags & 2048) != 0) {
                String fname0 = fname;
                int i2 = this.min_args;
                if (i2 == this.max_args) {
                    suffix = i2;
                }
                while (true) {
                    int suffix2 = suffix + 1;
                    fname = fname0 + '$' + suffix;
                    if (frameType.getDeclaredField(fname) == null) {
                        break;
                    }
                    suffix = suffix2;
                }
            }
        }
        Field field = frameType.addField(fname, Compilation.typeModuleMethod, fflags);
        Declaration declaration3 = this.nameDecl;
        if (declaration3 != null) {
            declaration3.field = field;
        }
        return field;
    }

    /* access modifiers changed from: package-private */
    public final void addApplyMethod(Compilation comp, Field field) {
        LambdaExp owner = this;
        if (field == null || !field.getStaticFlag()) {
            do {
                owner = owner.outerLambda();
                if ((owner instanceof ModuleExp) || owner.heapFrame != null) {
                }
                owner = owner.outerLambda();
                break;
            } while (owner.heapFrame != null);
            if (!owner.getHeapFrameType().getSuperclass().isSubtype(Compilation.typeModuleBody)) {
                owner = comp.getModule();
            }
        } else {
            owner = comp.getModule();
        }
        if (owner.applyMethods == null) {
            owner.applyMethods = new Vector();
        }
        owner.applyMethods.addElement(this);
    }

    public Field compileSetField(Compilation comp) {
        if (this.primMethods == null) {
            allocMethod(outerLambda(), comp);
        }
        Field field = allocFieldFor(comp);
        if (comp.usingCPStyle()) {
            compile(comp, (Type) Type.objectType);
        } else {
            compileAsMethod(comp);
            addApplyMethod(comp, field);
        }
        return new ProcInitializer(this, comp, field).field;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            CodeAttr code = comp.getCode();
            LambdaExp outer = outerLambda();
            Type rtype = Compilation.typeModuleMethod;
            if ((this.flags & 256) != 0 || (comp.immediate && (outer instanceof ModuleExp))) {
                if (this.primMethods == null) {
                    allocMethod(outerLambda(), comp);
                }
                compileAsMethod(comp);
                addApplyMethod(comp, (Field) null);
                ProcInitializer.emitLoadModuleMethod(this, comp);
            } else {
                Field field = compileSetField(comp);
                if (field.getStaticFlag()) {
                    code.emitGetStatic(field);
                } else {
                    LambdaExp parent = comp.curLambda;
                    Variable frame = parent.heapFrame;
                    if (frame == null) {
                        frame = parent.closureEnv;
                    }
                    code.emitLoad(frame);
                    code.emitGetField(field);
                }
            }
            target.compileFromStack(comp, rtype);
        }
    }

    public ClassType getHeapFrameType() {
        if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
            return (ClassType) getType();
        }
        return (ClassType) this.heapFrame.getType();
    }

    public LambdaExp getOwningLambda() {
        for (ScopeExp exp = this.outer; exp != null; exp = exp.outer) {
            if ((exp instanceof ModuleExp) || (((exp instanceof ClassExp) && getNeedsClosureEnv()) || ((exp instanceof LambdaExp) && ((LambdaExp) exp).heapFrame != null))) {
                return (LambdaExp) exp;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void addMethodFor(Compilation comp, ObjectType closureEnvType) {
        ClassType ctype;
        ScopeExp sc = this;
        while (sc != null && !(sc instanceof ClassExp)) {
            sc = sc.outer;
        }
        if (sc != null) {
            ctype = ((ClassExp) sc).instanceType;
        } else {
            ctype = getOwningLambda().getHeapFrameType();
        }
        addMethodFor(ctype, comp, closureEnvType);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v12, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Class} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v17, resolved type: gnu.bytecode.ArrayType} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0172  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x021e  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x0227  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x022b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x0245  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0301  */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x0304  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x013e A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addMethodFor(gnu.bytecode.ClassType r39, gnu.expr.Compilation r40, gnu.bytecode.ObjectType r41) {
        /*
            r38 = this;
            r0 = r38
            r1 = r39
            r2 = r40
            r3 = r41
            java.lang.String r4 = r38.getName()
            gnu.expr.LambdaExp r5 = r38.outerLambda()
            gnu.expr.Keyword[] r6 = r0.keywords
            if (r6 != 0) goto L_0x0016
            r6 = 0
            goto L_0x0017
        L_0x0016:
            int r6 = r6.length
        L_0x0017:
            gnu.expr.Expression[] r8 = r0.defaultArgs
            if (r8 != 0) goto L_0x001d
            r8 = 0
            goto L_0x001f
        L_0x001d:
            int r8 = r8.length
            int r8 = r8 - r6
        L_0x001f:
            int r9 = r0.flags
            r9 = r9 & 512(0x200, float:7.175E-43)
            if (r9 == 0) goto L_0x0027
            r9 = 0
            goto L_0x0028
        L_0x0027:
            r9 = r8
        L_0x0028:
            int r10 = r0.max_args
            if (r10 < 0) goto L_0x0034
            int r12 = r0.min_args
            int r12 = r12 + r9
            if (r12 >= r10) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            r10 = 0
            goto L_0x0035
        L_0x0034:
            r10 = 1
        L_0x0035:
            int r12 = r9 + 1
            gnu.bytecode.Method[] r12 = new gnu.bytecode.Method[r12]
            r0.primBodyMethods = r12
            gnu.bytecode.Method[] r13 = r0.primMethods
            if (r13 != 0) goto L_0x0041
            r0.primMethods = r12
        L_0x0041:
            r13 = 0
            gnu.expr.Declaration r14 = r0.nameDecl
            if (r14 == 0) goto L_0x0054
            r16 = r8
            r7 = 4096(0x1000, double:2.0237E-320)
            boolean r7 = r14.getFlag(r7)
            if (r7 == 0) goto L_0x0056
            r7 = 0
            r14 = r12
            goto L_0x00bf
        L_0x0054:
            r16 = r8
        L_0x0056:
            gnu.expr.Declaration r7 = r0.nameDecl
            if (r7 == 0) goto L_0x0066
            r14 = r12
            r11 = 2048(0x800, double:1.0118E-320)
            boolean r7 = r7.getFlag(r11)
            if (r7 == 0) goto L_0x0067
            r7 = 1
            goto L_0x00bf
        L_0x0066:
            r14 = r12
        L_0x0067:
            boolean r7 = r38.isClassMethod()
            if (r7 == 0) goto L_0x0094
            boolean r7 = r5 instanceof gnu.expr.ClassExp
            if (r7 == 0) goto L_0x0092
            r7 = r5
            gnu.expr.ClassExp r7 = (gnu.expr.ClassExp) r7
            boolean r11 = r7.isMakingClassPair()
            if (r11 == 0) goto L_0x007e
            if (r3 == 0) goto L_0x007e
            r11 = 1
            goto L_0x007f
        L_0x007e:
            r11 = 0
        L_0x007f:
            gnu.expr.LambdaExp r12 = r7.initMethod
            if (r0 != r12) goto L_0x0087
            r13 = 73
            r7 = r11
            goto L_0x0091
        L_0x0087:
            gnu.expr.LambdaExp r12 = r7.clinitMethod
            if (r0 != r12) goto L_0x0090
            r13 = 67
            r11 = 1
            r7 = r11
            goto L_0x0091
        L_0x0090:
            r7 = r11
        L_0x0091:
            goto L_0x00bf
        L_0x0092:
            r7 = 0
            goto L_0x00bf
        L_0x0094:
            gnu.bytecode.Variable r7 = r0.thisVariable
            if (r7 != 0) goto L_0x00be
            if (r3 != r1) goto L_0x009b
            goto L_0x00be
        L_0x009b:
            gnu.expr.Declaration r7 = r0.nameDecl
            if (r7 == 0) goto L_0x00bc
            gnu.expr.ScopeExp r7 = r7.context
            boolean r7 = r7 instanceof gnu.expr.ModuleExp
            if (r7 == 0) goto L_0x00bc
            gnu.expr.Declaration r7 = r0.nameDecl
            gnu.expr.ScopeExp r7 = r7.context
            gnu.expr.ModuleExp r7 = (gnu.expr.ModuleExp) r7
            gnu.bytecode.ClassType r11 = r7.getSuperType()
            if (r11 != 0) goto L_0x00b9
            gnu.bytecode.ClassType[] r11 = r7.getInterfaces()
            if (r11 != 0) goto L_0x00b9
            r11 = 1
            goto L_0x00ba
        L_0x00b9:
            r11 = 0
        L_0x00ba:
            r7 = r11
            goto L_0x00bf
        L_0x00bc:
            r7 = 1
            goto L_0x00bf
        L_0x00be:
            r7 = 0
        L_0x00bf:
            java.lang.StringBuffer r11 = new java.lang.StringBuffer
            r12 = 60
            r11.<init>(r12)
            if (r7 == 0) goto L_0x00cb
            r12 = 8
            goto L_0x00cc
        L_0x00cb:
            r12 = 0
        L_0x00cc:
            gnu.expr.Declaration r8 = r0.nameDecl
            if (r8 == 0) goto L_0x00f8
            boolean r8 = r8.needsExternalAccess()
            if (r8 == 0) goto L_0x00d9
            r12 = r12 | 1
            goto L_0x00f8
        L_0x00d9:
            gnu.expr.Declaration r8 = r0.nameDecl
            boolean r8 = r8.isPrivate()
            r17 = 1
            r18 = r8 ^ 1
            r17 = r18
            boolean r18 = r38.isClassMethod()
            if (r18 == 0) goto L_0x00f4
            gnu.expr.Declaration r8 = r0.nameDecl
            r15 = r17
            short r17 = r8.getAccessFlags(r15)
            goto L_0x00f6
        L_0x00f4:
            r15 = r17
        L_0x00f6:
            r12 = r12 | r17
        L_0x00f8:
            boolean r8 = r5.isModuleBody()
            if (r8 != 0) goto L_0x0102
            boolean r8 = r5 instanceof gnu.expr.ClassExp
            if (r8 == 0) goto L_0x0104
        L_0x0102:
            if (r4 != 0) goto L_0x0113
        L_0x0104:
            java.lang.String r8 = "lambda"
            r11.append(r8)
            int r8 = r2.method_counter
            r15 = 1
            int r8 = r8 + r15
            r15 = r8
            r2.method_counter = r15
            r11.append(r15)
        L_0x0113:
            r15 = 67
            if (r13 != r15) goto L_0x011d
            java.lang.String r15 = "<clinit>"
            r11.append(r15)
            goto L_0x012a
        L_0x011d:
            java.lang.Object r15 = r38.getSymbol()
            if (r15 == 0) goto L_0x012a
            java.lang.String r15 = gnu.expr.Compilation.mangleName(r4)
            r11.append(r15)
        L_0x012a:
            r15 = 1024(0x400, float:1.435E-42)
            boolean r17 = r0.getFlag(r15)
            if (r17 == 0) goto L_0x0137
            java.lang.String r8 = "$C"
            r11.append(r8)
        L_0x0137:
            int r8 = r38.getCallConvention()
            r15 = 2
            if (r8 < r15) goto L_0x0142
            if (r13 != 0) goto L_0x0142
            r8 = 1
            goto L_0x0143
        L_0x0142:
            r8 = 0
        L_0x0143:
            r20 = r8
            if (r13 == 0) goto L_0x0154
            if (r7 == 0) goto L_0x0150
            r8 = r12 & -3
            r17 = 1
            int r12 = r8 + 1
            goto L_0x0154
        L_0x0150:
            r17 = r12 & 2
            int r12 = r17 + 2
        L_0x0154:
            boolean r17 = r39.isInterface()
            if (r17 != 0) goto L_0x0160
            boolean r17 = r38.isAbstract()
            if (r17 == 0) goto L_0x0162
        L_0x0160:
            r12 = r12 | 1024(0x400, float:1.435E-42)
        L_0x0162:
            boolean r17 = r38.isClassMethod()
            if (r17 == 0) goto L_0x0207
            boolean r8 = r5 instanceof gnu.expr.ClassExp
            if (r8 == 0) goto L_0x0207
            int r8 = r0.min_args
            int r15 = r0.max_args
            if (r8 != r15) goto L_0x0207
            r8 = 0
            r15 = 0
            gnu.expr.Declaration r21 = r38.firstDecl()
            r37 = r21
            r21 = r4
            r4 = r37
        L_0x017e:
            if (r4 != 0) goto L_0x018f
            r22 = r7
            gnu.bytecode.Type r7 = r0.returnType
            if (r7 == 0) goto L_0x018b
            r7 = r5
            r23 = r6
            goto L_0x020e
        L_0x018b:
            r7 = r5
            r23 = r6
            goto L_0x01ac
        L_0x018f:
            r22 = r7
            boolean r7 = r4.isThisParameter()
            if (r7 == 0) goto L_0x019f
            int r15 = r15 + -1
            r7 = r5
            r23 = r6
            r5 = r8
            goto L_0x01f9
        L_0x019f:
            r7 = r5
            r23 = r6
            r5 = 8192(0x2000, double:4.0474E-320)
            boolean r5 = r4.getFlag(r5)
            if (r5 == 0) goto L_0x01ac
            r5 = r8
            goto L_0x01f9
        L_0x01ac:
            if (r8 != 0) goto L_0x01be
            java.lang.String r5 = r11.toString()
            gnu.expr.LambdaExp$1 r6 = new gnu.expr.LambdaExp$1
            r6.<init>(r5)
            r24 = r5
            r5 = 2
            gnu.bytecode.Method[] r8 = r1.getMethods((gnu.bytecode.Filter) r6, (int) r5)
        L_0x01be:
            r5 = 0
            int r6 = r8.length
        L_0x01c0:
            int r6 = r6 + -1
            if (r6 < 0) goto L_0x01e8
            r24 = r8[r6]
            if (r4 != 0) goto L_0x01cd
            gnu.bytecode.Type r25 = r24.getReturnType()
            goto L_0x01d3
        L_0x01cd:
            gnu.bytecode.Type[] r25 = r24.getParameterTypes()
            r25 = r25[r15]
        L_0x01d3:
            r26 = r25
            if (r5 != 0) goto L_0x01dc
            r5 = r26
            r25 = r6
            goto L_0x01e5
        L_0x01dc:
            r25 = r6
            r6 = r26
            if (r6 == r5) goto L_0x01e5
            if (r4 != 0) goto L_0x01f8
            goto L_0x020e
        L_0x01e5:
            r6 = r25
            goto L_0x01c0
        L_0x01e8:
            r25 = r6
            if (r5 == 0) goto L_0x01f5
            if (r4 == 0) goto L_0x01f2
            r4.setType(r5)
            goto L_0x01f5
        L_0x01f2:
            r0.setCoercedReturnType(r5)
        L_0x01f5:
            if (r4 != 0) goto L_0x01f8
            goto L_0x020e
        L_0x01f8:
            r5 = r8
        L_0x01f9:
            gnu.expr.Declaration r4 = r4.nextDecl()
            r6 = 1
            int r15 = r15 + r6
            r8 = r5
            r5 = r7
            r7 = r22
            r6 = r23
            goto L_0x017e
        L_0x0207:
            r21 = r4
            r23 = r6
            r22 = r7
            r7 = r5
        L_0x020e:
            r4 = 1024(0x400, float:1.435E-42)
            boolean r4 = r0.getFlag(r4)
            if (r4 != 0) goto L_0x0227
            int r4 = r38.getCallConvention()
            r5 = 2
            if (r4 < r5) goto L_0x021e
            goto L_0x0227
        L_0x021e:
            gnu.bytecode.Type r4 = r38.getReturnType()
            gnu.bytecode.Type r4 = r4.getImplementationType()
            goto L_0x0229
        L_0x0227:
            gnu.bytecode.PrimType r4 = gnu.bytecode.Type.voidType
        L_0x0229:
            if (r3 == 0) goto L_0x022f
            if (r3 == r1) goto L_0x022f
            r5 = 1
            goto L_0x0230
        L_0x022f:
            r5 = 0
        L_0x0230:
            r6 = 0
            int r15 = r38.getCallConvention()
            r8 = 2
            if (r15 < r8) goto L_0x023b
            if (r13 != 0) goto L_0x023b
            r6 = 1
        L_0x023b:
            int r15 = r11.length()
            r18 = 0
            r8 = r18
        L_0x0243:
            if (r8 > r9) goto L_0x049c
            r11.setLength(r15)
            r18 = r13
            int r13 = r0.min_args
            int r13 = r13 + r8
            r24 = r13
            if (r8 != r9) goto L_0x025a
            if (r10 == 0) goto L_0x025a
            int r24 = r24 + 1
            r25 = r10
            r10 = r24
            goto L_0x025e
        L_0x025a:
            r25 = r10
            r10 = r24
        L_0x025e:
            int r24 = r5 + r10
            r26 = r15
            int r15 = r24 + r6
            gnu.bytecode.Type[] r15 = new gnu.bytecode.Type[r15]
            if (r5 <= 0) goto L_0x026d
            r19 = 0
            r15[r19] = r3
            goto L_0x026f
        L_0x026d:
            r19 = 0
        L_0x026f:
            gnu.expr.Declaration r24 = r38.firstDecl()
            if (r24 == 0) goto L_0x027f
            boolean r27 = r24.isThisParameter()
            if (r27 == 0) goto L_0x027f
            gnu.expr.Declaration r24 = r24.nextDecl()
        L_0x027f:
            r27 = 0
            r28 = r24
            r3 = r27
        L_0x0285:
            if (r3 >= r13) goto L_0x029b
            int r27 = r3 + 1
            int r3 = r3 + r5
            gnu.bytecode.Type r24 = r28.getType()
            gnu.bytecode.Type r24 = r24.getImplementationType()
            r15[r3] = r24
            gnu.expr.Declaration r28 = r28.nextDecl()
            r3 = r27
            goto L_0x0285
        L_0x029b:
            if (r6 == 0) goto L_0x02a6
            int r3 = r15.length
            r17 = 1
            int r3 = r3 + -1
            gnu.bytecode.ClassType r24 = gnu.expr.Compilation.typeCallContext
            r15[r3] = r24
        L_0x02a6:
            if (r13 >= r10) goto L_0x030b
            gnu.bytecode.Type r3 = r28.getType()
            r24 = r5
            java.lang.String r5 = r3.getName()
            r27 = r6
            int r6 = r39.getClassfileVersion()
            r29 = r10
            r10 = 3211264(0x310000, float:4.49994E-39)
            if (r6 < r10) goto L_0x02c5
            boolean r6 = r3 instanceof gnu.bytecode.ArrayType
            if (r6 == 0) goto L_0x02c5
            r12 = r12 | 128(0x80, float:1.794E-43)
            goto L_0x02ca
        L_0x02c5:
            java.lang.String r6 = "$V"
            r11.append(r6)
        L_0x02ca:
            if (r23 > 0) goto L_0x02e3
            r6 = r16
            if (r9 < r6) goto L_0x02e5
            java.lang.String r10 = "gnu.lists.LList"
            boolean r10 = r10.equals(r5)
            if (r10 != 0) goto L_0x02dd
            boolean r10 = r3 instanceof gnu.bytecode.ArrayType
            if (r10 != 0) goto L_0x02dd
            goto L_0x02e5
        L_0x02dd:
            r16 = r3
            r30 = r5
            r3 = 1
            goto L_0x02fa
        L_0x02e3:
            r6 = r16
        L_0x02e5:
            gnu.bytecode.ArrayType r3 = gnu.expr.Compilation.objArrayType
            gnu.bytecode.Variable r10 = new gnu.bytecode.Variable
            r16 = r3
            gnu.bytecode.ArrayType r3 = gnu.expr.Compilation.objArrayType
            r30 = r5
            java.lang.String r5 = "argsArray"
            r10.<init>(r5, r3)
            r0.argsArray = r10
            r3 = 1
            r10.setParameter(r3)
        L_0x02fa:
            r5 = r28
            r0.firstArgsArrayArg = r5
            int r10 = r15.length
            if (r20 == 0) goto L_0x0304
            r17 = 2
            goto L_0x0306
        L_0x0304:
            r17 = 1
        L_0x0306:
            int r10 = r10 - r17
            r15[r10] = r16
            goto L_0x0316
        L_0x030b:
            r24 = r5
            r27 = r6
            r29 = r10
            r6 = r16
            r5 = r28
            r3 = 1
        L_0x0316:
            if (r20 == 0) goto L_0x031d
            java.lang.String r10 = "$X"
            r11.append(r10)
        L_0x031d:
            boolean r10 = r7 instanceof gnu.expr.ClassExp
            if (r10 != 0) goto L_0x0333
            boolean r10 = r7 instanceof gnu.expr.ModuleExp
            if (r10 == 0) goto L_0x0331
            r10 = r7
            gnu.expr.ModuleExp r10 = (gnu.expr.ModuleExp) r10
            r3 = 131072(0x20000, float:1.83671E-40)
            boolean r3 = r10.getFlag(r3)
            if (r3 == 0) goto L_0x0331
            goto L_0x0333
        L_0x0331:
            r3 = 0
            goto L_0x0334
        L_0x0333:
            r3 = 1
        L_0x0334:
            java.lang.String r10 = r11.toString()
            r16 = 0
            r28 = r5
            int r5 = r11.length()
        L_0x0340:
            r21 = r39
            r30 = r6
            r6 = r21
        L_0x0346:
            if (r6 == 0) goto L_0x0374
            gnu.bytecode.Method r21 = r6.getDeclaredMethod((java.lang.String) r10, (gnu.bytecode.Type[]) r15)
            if (r21 == 0) goto L_0x0368
            r11.setLength(r5)
            r21 = r5
            r5 = 36
            r11.append(r5)
            int r5 = r16 + 1
            r11.append(r5)
            java.lang.String r10 = r11.toString()
            r16 = r5
            r5 = r21
            r6 = r30
            goto L_0x0340
        L_0x0368:
            r21 = r5
            if (r3 == 0) goto L_0x036d
            goto L_0x0376
        L_0x036d:
            gnu.bytecode.ClassType r6 = r6.getSuperclass()
            r5 = r21
            goto L_0x0346
        L_0x0374:
            r21 = r5
        L_0x0376:
            gnu.bytecode.Method r5 = r1.addMethod((java.lang.String) r10, (gnu.bytecode.Type[]) r15, (gnu.bytecode.Type) r4, (int) r12)
            r14[r8] = r5
            gnu.expr.Expression[] r6 = r0.throwsSpecification
            if (r6 == 0) goto L_0x047a
            int r1 = r6.length
            if (r1 <= 0) goto L_0x047a
            int r1 = r6.length
            gnu.bytecode.ClassType[] r6 = new gnu.bytecode.ClassType[r1]
            r16 = 0
            r31 = r3
            r3 = r16
        L_0x038d:
            if (r3 >= r1) goto L_0x046b
            r16 = 0
            r21 = r1
            gnu.expr.Expression[] r1 = r0.throwsSpecification
            r1 = r1[r3]
            r32 = 0
            boolean r0 = r1 instanceof gnu.expr.ReferenceExp
            if (r0 == 0) goto L_0x03f8
            r0 = r1
            gnu.expr.ReferenceExp r0 = (gnu.expr.ReferenceExp) r0
            gnu.expr.Declaration r33 = r0.getBinding()
            if (r33 == 0) goto L_0x03da
            r34 = r4
            gnu.expr.Expression r4 = r33.getValue()
            r35 = r7
            boolean r7 = r4 instanceof gnu.expr.ClassExp
            if (r7 == 0) goto L_0x03ba
            r7 = r4
            gnu.expr.ClassExp r7 = (gnu.expr.ClassExp) r7
            gnu.bytecode.ClassType r16 = r7.getCompiledClassType(r2)
            goto L_0x03d9
        L_0x03ba:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r36 = r4
            java.lang.String r4 = "throws specification "
            java.lang.StringBuilder r4 = r7.append(r4)
            java.lang.String r7 = r33.getName()
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r7 = " has non-class lexical binding"
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r32 = r4.toString()
        L_0x03d9:
            goto L_0x03f7
        L_0x03da:
            r34 = r4
            r35 = r7
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "unknown class "
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r7 = r0.getName()
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r4 = r4.toString()
            r32 = r4
        L_0x03f7:
            goto L_0x0449
        L_0x03f8:
            r34 = r4
            r35 = r7
            boolean r0 = r1 instanceof gnu.expr.QuoteExp
            if (r0 == 0) goto L_0x0449
            r0 = r1
            gnu.expr.QuoteExp r0 = (gnu.expr.QuoteExp) r0
            java.lang.Object r0 = r0.getValue()
            boolean r4 = r0 instanceof java.lang.Class
            if (r4 == 0) goto L_0x0412
            r4 = r0
            java.lang.Class r4 = (java.lang.Class) r4
            gnu.bytecode.Type r0 = gnu.bytecode.Type.make(r4)
        L_0x0412:
            boolean r4 = r0 instanceof gnu.bytecode.ClassType
            if (r4 == 0) goto L_0x041d
            r16 = r0
            gnu.bytecode.ClassType r16 = (gnu.bytecode.ClassType) r16
            r4 = r16
            goto L_0x041f
        L_0x041d:
            r4 = r16
        L_0x041f:
            if (r4 == 0) goto L_0x0445
            gnu.bytecode.ClassType r7 = gnu.bytecode.Type.javalangThrowableType
            boolean r7 = r4.isSubtype(r7)
            if (r7 != 0) goto L_0x0445
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r33 = r0
            java.lang.String r0 = r4.getName()
            java.lang.StringBuilder r0 = r7.append(r0)
            java.lang.String r7 = " does not extend Throwable"
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.String r32 = r0.toString()
            r16 = r4
            goto L_0x0449
        L_0x0445:
            r33 = r0
            r16 = r4
        L_0x0449:
            if (r16 != 0) goto L_0x0452
            if (r32 != 0) goto L_0x0452
            java.lang.String r32 = "invalid throws specification"
            r0 = r32
            goto L_0x0454
        L_0x0452:
            r0 = r32
        L_0x0454:
            if (r0 == 0) goto L_0x045d
            r4 = 101(0x65, float:1.42E-43)
            r2.error(r4, r0, r1)
            gnu.bytecode.ClassType r16 = gnu.bytecode.Type.javalangThrowableType
        L_0x045d:
            r6[r3] = r16
            int r3 = r3 + 1
            r0 = r38
            r1 = r21
            r4 = r34
            r7 = r35
            goto L_0x038d
        L_0x046b:
            r21 = r1
            r34 = r4
            r35 = r7
            gnu.bytecode.ExceptionsAttr r0 = new gnu.bytecode.ExceptionsAttr
            r0.<init>(r5)
            r0.setExceptions(r6)
            goto L_0x0480
        L_0x047a:
            r31 = r3
            r34 = r4
            r35 = r7
        L_0x0480:
            int r8 = r8 + 1
            r0 = r38
            r1 = r39
            r3 = r41
            r21 = r10
            r13 = r18
            r5 = r24
            r10 = r25
            r15 = r26
            r6 = r27
            r16 = r30
            r4 = r34
            r7 = r35
            goto L_0x0243
        L_0x049c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.addMethodFor(gnu.bytecode.ClassType, gnu.expr.Compilation, gnu.bytecode.ObjectType):void");
    }

    public void allocChildClasses(Compilation comp) {
        Declaration declaration;
        Method main = getMainMethod();
        if (main != null && !main.getStaticFlag()) {
            declareThis(main.getDeclaringClass());
        }
        Declaration decl = firstDecl();
        while (true) {
            if (decl == this.firstArgsArrayArg && this.argsArray != null) {
                getVarScope().addVariable(this.argsArray);
            }
            if (!getInlineOnly() && getCallConvention() >= 2 && ((declaration = this.firstArgsArrayArg) != null ? !(this.argsArray == null ? decl != declaration.nextDecl() : decl != declaration) : decl == null)) {
                getVarScope().addVariable((CodeAttr) null, Compilation.typeCallContext, "$ctx").setParameter(true);
            }
            if (decl == null) {
                declareClosureEnv();
                allocFrame(comp);
                allocChildMethods(comp);
                return;
            }
            if (decl.var == null && (!getInlineOnly() || !decl.ignorable())) {
                if (!decl.isSimple() || decl.isIndirectBinding()) {
                    String vname = Compilation.mangleName(decl.getName()).intern();
                    Variable var = getVarScope().addVariable((CodeAttr) null, decl.getType().getImplementationType(), vname);
                    decl.var = var;
                    var.setParameter(true);
                } else {
                    Variable var2 = decl.allocateVariable((CodeAttr) null);
                }
            }
            decl = decl.nextDecl();
        }
    }

    /* access modifiers changed from: package-private */
    public void allocMethod(LambdaExp outer, Compilation comp) {
        ObjectType closureEnvType;
        Variable variable;
        if (!getNeedsClosureEnv()) {
            closureEnvType = null;
        } else if ((outer instanceof ClassExp) || (outer instanceof ModuleExp)) {
            closureEnvType = outer.getCompiledClassType(comp);
        } else {
            LambdaExp owner = outer;
            while (true) {
                variable = owner.heapFrame;
                if (variable != null) {
                    break;
                }
                owner = owner.outerLambda();
            }
            closureEnvType = (ClassType) variable.getType();
        }
        addMethodFor(comp, closureEnvType);
    }

    /* JADX WARNING: type inference failed for: r2v4, types: [gnu.bytecode.Type] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void allocChildMethods(gnu.expr.Compilation r5) {
        /*
            r4 = this;
            gnu.expr.LambdaExp r0 = r4.firstChild
        L_0x0002:
            if (r0 == 0) goto L_0x004f
            boolean r1 = r0.isClassGenerated()
            if (r1 != 0) goto L_0x0017
            boolean r1 = r0.getInlineOnly()
            if (r1 != 0) goto L_0x0017
            gnu.expr.Declaration r1 = r0.nameDecl
            if (r1 == 0) goto L_0x0017
            r0.allocMethod(r4, r5)
        L_0x0017:
            boolean r1 = r0 instanceof gnu.expr.ClassExp
            if (r1 == 0) goto L_0x004c
            r1 = r0
            gnu.expr.ClassExp r1 = (gnu.expr.ClassExp) r1
            boolean r2 = r1.getNeedsClosureEnv()
            if (r2 == 0) goto L_0x004c
            boolean r2 = r4 instanceof gnu.expr.ModuleExp
            if (r2 != 0) goto L_0x003b
            boolean r2 = r4 instanceof gnu.expr.ClassExp
            if (r2 == 0) goto L_0x002d
            goto L_0x003b
        L_0x002d:
            gnu.bytecode.Variable r2 = r4.heapFrame
            if (r2 == 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            gnu.bytecode.Variable r2 = r4.closureEnv
        L_0x0034:
            gnu.bytecode.Type r3 = r2.getType()
            gnu.bytecode.ClassType r3 = (gnu.bytecode.ClassType) r3
            goto L_0x0042
        L_0x003b:
            gnu.bytecode.Type r2 = r4.getType()
            r3 = r2
            gnu.bytecode.ClassType r3 = (gnu.bytecode.ClassType) r3
        L_0x0042:
            gnu.bytecode.ClassType r2 = r1.instanceType
            gnu.bytecode.Field r2 = r2.setOuterLink(r3)
            r1.staticLinkField = r2
            r1.closureEnvField = r2
        L_0x004c:
            gnu.expr.LambdaExp r0 = r0.nextSibling
            goto L_0x0002
        L_0x004f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.allocChildMethods(gnu.expr.Compilation):void");
    }

    public void allocFrame(Compilation comp) {
        ClassType frameType;
        if (this.heapFrame != null) {
            if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                frameType = getCompiledClassType(comp);
            } else {
                frameType = new ClassType(comp.generateClassName("frame"));
                frameType.setSuper(comp.getModuleType());
                comp.addClass(frameType);
            }
            this.heapFrame.setType(frameType);
        }
    }

    /* access modifiers changed from: package-private */
    public void allocParameters(Compilation comp) {
        CodeAttr code = comp.getCode();
        code.locals.enterScope(getVarScope());
        int line = getLineNumber();
        if (line > 0) {
            code.putLineNumber(getFileName(), line);
        }
        Variable variable = this.heapFrame;
        if (variable != null) {
            variable.allocateLocal(code);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x029d  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x02a6  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x02b9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void enterFunction(gnu.expr.Compilation r26) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            gnu.bytecode.CodeAttr r2 = r26.getCode()
            gnu.bytecode.Scope r3 = r25.getVarScope()
            r3.noteStartFunction(r2)
            gnu.bytecode.Variable r3 = r0.closureEnv
            if (r3 == 0) goto L_0x0051
            boolean r3 = r3.isParameter()
            if (r3 != 0) goto L_0x0051
            boolean r3 = r26.usingCPStyle()
            if (r3 != 0) goto L_0x0051
            boolean r3 = r25.getInlineOnly()
            if (r3 != 0) goto L_0x003b
            r2.emitPushThis()
            gnu.bytecode.Field r3 = r0.closureEnvField
            if (r3 != 0) goto L_0x0032
            gnu.expr.LambdaExp r4 = r25.outerLambda()
            gnu.bytecode.Field r3 = r4.closureEnvField
        L_0x0032:
            r2.emitGetField(r3)
            gnu.bytecode.Variable r4 = r0.closureEnv
            r2.emitStore(r4)
            goto L_0x0051
        L_0x003b:
            gnu.expr.LambdaExp r3 = r25.outerLambda()
            boolean r3 = r0.inlinedIn(r3)
            if (r3 != 0) goto L_0x0051
            gnu.expr.LambdaExp r3 = r25.outerLambda()
            r3.loadHeapFrame(r1)
            gnu.bytecode.Variable r3 = r0.closureEnv
            r2.emitStore(r3)
        L_0x0051:
            boolean r3 = r26.usingCPStyle()
            r4 = 0
            if (r3 != 0) goto L_0x007a
            gnu.bytecode.Variable r3 = r0.heapFrame
            if (r3 != 0) goto L_0x0065
            gnu.expr.ModuleExp r3 = r25.currentModule()
            gnu.bytecode.ClassType r3 = r3.getCompiledClassType(r1)
            goto L_0x006b
        L_0x0065:
            gnu.bytecode.Type r3 = r3.getType()
            gnu.bytecode.ClassType r3 = (gnu.bytecode.ClassType) r3
        L_0x006b:
            gnu.expr.Declaration r5 = r0.capturedVars
        L_0x006d:
            if (r5 == 0) goto L_0x007a
            gnu.bytecode.Field r6 = r5.field
            if (r6 == 0) goto L_0x0074
            goto L_0x0077
        L_0x0074:
            r5.makeField(r3, r1, r4)
        L_0x0077:
            gnu.expr.Declaration r5 = r5.nextCapturedVar
            goto L_0x006d
        L_0x007a:
            gnu.bytecode.Variable r3 = r0.heapFrame
            if (r3 == 0) goto L_0x00d0
            boolean r3 = r26.usingCPStyle()
            if (r3 != 0) goto L_0x00d0
            gnu.bytecode.Variable r3 = r0.heapFrame
            gnu.bytecode.Type r3 = r3.getType()
            gnu.bytecode.ClassType r3 = (gnu.bytecode.ClassType) r3
            gnu.bytecode.Variable r5 = r0.closureEnv
            if (r5 == 0) goto L_0x00a0
            boolean r6 = r0 instanceof gnu.expr.ModuleExp
            if (r6 != 0) goto L_0x00a0
            gnu.bytecode.Type r5 = r5.getType()
            java.lang.String r6 = "staticLink"
            gnu.bytecode.Field r5 = r3.addField(r6, r5)
            r0.staticLinkField = r5
        L_0x00a0:
            boolean r5 = r0 instanceof gnu.expr.ModuleExp
            if (r5 != 0) goto L_0x00d0
            boolean r5 = r0 instanceof gnu.expr.ClassExp
            if (r5 != 0) goto L_0x00d0
            gnu.bytecode.Method r5 = r1.method
            r3.setEnclosingMember(r5)
            r2.emitNew(r3)
            r2.emitDup((gnu.bytecode.Type) r3)
            gnu.bytecode.Method r5 = gnu.expr.Compilation.getConstructor(r3, r0)
            r2.emitInvokeSpecial(r5)
            gnu.bytecode.Field r6 = r0.staticLinkField
            if (r6 == 0) goto L_0x00cb
            r2.emitDup((gnu.bytecode.Type) r3)
            gnu.bytecode.Variable r6 = r0.closureEnv
            r2.emitLoad(r6)
            gnu.bytecode.Field r6 = r0.staticLinkField
            r2.emitPutField(r6)
        L_0x00cb:
            gnu.bytecode.Variable r6 = r0.heapFrame
            r2.emitStore(r6)
        L_0x00d0:
            gnu.bytecode.Variable r3 = r0.argsArray
            int r5 = r0.min_args
            int r6 = r0.max_args
            r7 = 2
            if (r5 != r6) goto L_0x00e4
            gnu.bytecode.Method[] r5 = r0.primMethods
            if (r5 != 0) goto L_0x00e4
            int r5 = r25.getCallConvention()
            if (r5 >= r7) goto L_0x00e4
            r3 = 0
        L_0x00e4:
            r5 = 0
            r6 = 0
            r8 = 0
            gnu.expr.Keyword[] r9 = r0.keywords
            if (r9 != 0) goto L_0x00ed
            r9 = 0
            goto L_0x00ee
        L_0x00ed:
            int r9 = r9.length
        L_0x00ee:
            gnu.expr.Expression[] r11 = r0.defaultArgs
            if (r11 != 0) goto L_0x00f4
            r11 = 0
            goto L_0x00f6
        L_0x00f4:
            int r11 = r11.length
            int r11 = r11 - r9
        L_0x00f6:
            boolean r12 = r0 instanceof gnu.expr.ModuleExp
            if (r12 == 0) goto L_0x00fb
            return
        L_0x00fb:
            r12 = -1
            r13 = 0
            gnu.bytecode.Method r14 = r25.getMainMethod()
            gnu.bytecode.Variable r15 = r1.callContextVar
            gnu.expr.Declaration r16 = r25.firstDecl()
            r10 = r16
        L_0x0109:
            if (r10 == 0) goto L_0x02ce
            int r4 = r25.getCallConvention()
            if (r4 >= r7) goto L_0x0113
            r4 = 0
            goto L_0x011d
        L_0x0113:
            gnu.bytecode.Scope r4 = r25.getVarScope()
            java.lang.String r7 = "$ctx"
            gnu.bytecode.Variable r4 = r4.lookup(r7)
        L_0x011d:
            r1.callContextVar = r4
            gnu.expr.Declaration r4 = r0.firstArgsArrayArg
            if (r10 != r4) goto L_0x0135
            if (r3 == 0) goto L_0x0135
            gnu.bytecode.Method[] r4 = r0.primMethods
            if (r4 == 0) goto L_0x0131
            r4 = r5
            int r7 = r0.min_args
            int r7 = r4 - r7
            r12 = r4
            r13 = r7
            goto L_0x0135
        L_0x0131:
            r4 = 0
            r7 = 0
            r12 = r4
            r13 = r7
        L_0x0135:
            if (r12 >= 0) goto L_0x014e
            boolean r4 = r10.isSimple()
            if (r4 == 0) goto L_0x014e
            boolean r4 = r10.isIndirectBinding()
            if (r4 == 0) goto L_0x0144
            goto L_0x014e
        L_0x0144:
            r20 = r3
            r19 = r9
            r24 = r11
            r16 = 2
            goto L_0x02be
        L_0x014e:
            gnu.bytecode.Type r4 = r10.getType()
            if (r12 < 0) goto L_0x0157
            gnu.bytecode.ClassType r7 = gnu.bytecode.Type.objectType
            goto L_0x0158
        L_0x0157:
            r7 = r4
        L_0x0158:
            boolean r18 = r10.isSimple()
            if (r18 != 0) goto L_0x0165
            r18 = r7
            r7 = 0
            r10.loadOwningObject(r7, r1)
            goto L_0x0168
        L_0x0165:
            r18 = r7
            r7 = 0
        L_0x0168:
            if (r12 >= 0) goto L_0x0172
            gnu.bytecode.Variable r7 = r10.getVariable()
            r2.emitLoad(r7)
            goto L_0x0181
        L_0x0172:
            int r7 = r0.min_args
            if (r5 >= r7) goto L_0x018d
            r2.emitLoad(r3)
            r2.emitPushInt(r5)
            gnu.bytecode.ClassType r7 = gnu.bytecode.Type.objectType
            r2.emitArrayLoad(r7)
        L_0x0181:
            r20 = r3
            r19 = r9
            r24 = r11
            r7 = r18
            r16 = 2
            goto L_0x0290
        L_0x018d:
            r19 = r9
            int r9 = r7 + r11
            if (r5 >= r9) goto L_0x01c7
            int r7 = r5 - r12
            r2.emitPushInt(r7)
            r2.emitLoad(r3)
            r2.emitArrayLength()
            r2.emitIfIntLt()
            r2.emitLoad(r3)
            int r7 = r5 - r12
            r2.emitPushInt(r7)
            r2.emitArrayLoad()
            r2.emitElse()
            gnu.expr.Expression[] r7 = r0.defaultArgs
            int r9 = r6 + 1
            int r6 = r6 + r13
            r6 = r7[r6]
            r6.compile((gnu.expr.Compilation) r1, (gnu.bytecode.Type) r4)
            r2.emitFi()
            r20 = r3
            r6 = r9
            r24 = r11
            r7 = r18
            r16 = 2
            goto L_0x0290
        L_0x01c7:
            int r9 = r0.max_args
            if (r9 >= 0) goto L_0x01e5
            int r7 = r7 + r11
            if (r5 != r7) goto L_0x01e5
            r2.emitLoad(r3)
            int r7 = r5 - r12
            r2.emitPushInt(r7)
            gnu.bytecode.Method r7 = gnu.expr.Compilation.makeListMethod
            r2.emitInvokeStatic(r7)
            gnu.bytecode.ClassType r7 = gnu.expr.Compilation.scmListType
            r20 = r3
            r24 = r11
            r16 = 2
            goto L_0x0290
        L_0x01e5:
            r2.emitLoad(r3)
            int r7 = r0.min_args
            int r7 = r7 + r11
            int r7 = r7 - r12
            r2.emitPushInt(r7)
            gnu.expr.Keyword[] r7 = r0.keywords
            int r9 = r8 + 1
            r7 = r7[r8]
            r1.compileConstant(r7)
            gnu.expr.Expression[] r7 = r0.defaultArgs
            int r8 = r6 + 1
            int r6 = r6 + r13
            r6 = r7[r6]
            boolean r7 = r6 instanceof gnu.expr.QuoteExp
            r20 = r3
            java.lang.String r3 = "searchForKeyword"
            r21 = r8
            r8 = 1
            if (r7 == 0) goto L_0x0247
            gnu.bytecode.Method r7 = searchForKeywordMethod4
            if (r7 != 0) goto L_0x0238
            r7 = 4
            gnu.bytecode.Type[] r7 = new gnu.bytecode.Type[r7]
            gnu.bytecode.ArrayType r23 = gnu.expr.Compilation.objArrayType
            r16 = 0
            r7[r16] = r23
            gnu.bytecode.PrimType r23 = gnu.bytecode.Type.intType
            r7[r8] = r23
            gnu.bytecode.ClassType r8 = gnu.bytecode.Type.objectType
            r17 = 2
            r7[r17] = r8
            gnu.bytecode.ClassType r8 = gnu.bytecode.Type.objectType
            r22 = 3
            r7[r22] = r8
            gnu.bytecode.ClassType r8 = gnu.expr.Compilation.scmKeywordType
            r23 = r9
            gnu.bytecode.ClassType r9 = gnu.bytecode.Type.objectType
            r24 = r11
            r11 = 9
            gnu.bytecode.Method r3 = r8.addMethod((java.lang.String) r3, (gnu.bytecode.Type[]) r7, (gnu.bytecode.Type) r9, (int) r11)
            searchForKeywordMethod4 = r3
            goto L_0x023c
        L_0x0238:
            r23 = r9
            r24 = r11
        L_0x023c:
            r6.compile((gnu.expr.Compilation) r1, (gnu.bytecode.Type) r4)
            gnu.bytecode.Method r3 = searchForKeywordMethod4
            r2.emitInvokeStatic(r3)
            r16 = 2
            goto L_0x028a
        L_0x0247:
            r23 = r9
            r24 = r11
            gnu.bytecode.Method r7 = searchForKeywordMethod3
            if (r7 != 0) goto L_0x026e
            r7 = 3
            gnu.bytecode.Type[] r7 = new gnu.bytecode.Type[r7]
            gnu.bytecode.ArrayType r9 = gnu.expr.Compilation.objArrayType
            r11 = 0
            r7[r11] = r9
            gnu.bytecode.PrimType r9 = gnu.bytecode.Type.intType
            r7[r8] = r9
            gnu.bytecode.ClassType r9 = gnu.bytecode.Type.objectType
            r16 = 2
            r7[r16] = r9
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.scmKeywordType
            gnu.bytecode.ClassType r11 = gnu.bytecode.Type.objectType
            r8 = 9
            gnu.bytecode.Method r3 = r9.addMethod((java.lang.String) r3, (gnu.bytecode.Type[]) r7, (gnu.bytecode.Type) r11, (int) r8)
            searchForKeywordMethod3 = r3
            goto L_0x0270
        L_0x026e:
            r16 = 2
        L_0x0270:
            gnu.bytecode.Method r3 = searchForKeywordMethod3
            r2.emitInvokeStatic(r3)
            r3 = 1
            r2.emitDup((int) r3)
            gnu.expr.Special r7 = gnu.expr.Special.dfault
            r1.compileConstant(r7)
            r2.emitIfEq()
            r2.emitPop(r3)
            r6.compile((gnu.expr.Compilation) r1, (gnu.bytecode.Type) r4)
            r2.emitFi()
        L_0x028a:
            r7 = r18
            r6 = r21
            r8 = r23
        L_0x0290:
            if (r4 == r7) goto L_0x0297
            int r3 = r5 + 1
            gnu.expr.CheckedTarget.emitCheckedCoerce((gnu.expr.Compilation) r1, (gnu.expr.LambdaExp) r0, (int) r3, (gnu.bytecode.Type) r4)
        L_0x0297:
            boolean r3 = r10.isIndirectBinding()
            if (r3 == 0) goto L_0x02a0
            r10.pushIndirectBinding(r1)
        L_0x02a0:
            boolean r3 = r10.isSimple()
            if (r3 == 0) goto L_0x02b9
            gnu.bytecode.Variable r3 = r10.getVariable()
            boolean r9 = r10.isIndirectBinding()
            if (r9 == 0) goto L_0x02b5
            gnu.bytecode.ClassType r9 = gnu.expr.Compilation.typeLocation
            r3.setType(r9)
        L_0x02b5:
            r2.emitStore(r3)
            goto L_0x02be
        L_0x02b9:
            gnu.bytecode.Field r3 = r10.field
            r2.emitPutField(r3)
        L_0x02be:
            int r5 = r5 + 1
            gnu.expr.Declaration r10 = r10.nextDecl()
            r9 = r19
            r3 = r20
            r11 = r24
            r4 = 0
            r7 = 2
            goto L_0x0109
        L_0x02ce:
            r1.callContextVar = r15
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.enterFunction(gnu.expr.Compilation):void");
    }

    /* access modifiers changed from: package-private */
    public void compileAsMethod(Compilation comp) {
        Method save_method;
        LambdaExp save_lambda;
        Method method;
        int numStubs;
        Expression arg;
        Compilation compilation = comp;
        if ((this.flags & 128) == 0 && !isAbstract()) {
            this.flags |= 128;
            if (this.primMethods != null) {
                Method save_method2 = compilation.method;
                LambdaExp save_lambda2 = compilation.curLambda;
                compilation.curLambda = this;
                int i = 0;
                Method method2 = this.primMethods[0];
                boolean isStatic = method2.getStaticFlag();
                int numStubs2 = this.primMethods.length - 1;
                Type restArgType = restArgType();
                long[] saveDeclFlags = null;
                if (numStubs2 > 0) {
                    saveDeclFlags = new long[(this.min_args + numStubs2)];
                    Declaration decl = firstDecl();
                    for (int k = 0; k < this.min_args + numStubs2; k++) {
                        saveDeclFlags[k] = decl.flags;
                        decl = decl.nextDecl();
                    }
                }
                boolean ctxArg = getCallConvention() >= 2;
                int i2 = 0;
                while (i2 <= numStubs2) {
                    compilation.method = this.primMethods[i2];
                    if (i2 < numStubs2) {
                        CodeAttr code = compilation.method.startCode();
                        int toCall = i2 + 1;
                        while (toCall < numStubs2 && (this.defaultArgs[toCall] instanceof QuoteExp)) {
                            toCall++;
                        }
                        boolean varArgs = toCall == numStubs2 && restArgType != null;
                        Variable callContextSave = compilation.callContextVar;
                        method = method2;
                        Variable var = code.getArg(i);
                        if (!isStatic) {
                            code.emitPushThis();
                            if (getNeedsClosureEnv()) {
                                this.closureEnv = var;
                            }
                            var = code.getArg(1);
                        }
                        Declaration decl2 = firstDecl();
                        save_lambda = save_lambda2;
                        int j = 0;
                        while (true) {
                            save_method = save_method2;
                            if (j >= this.min_args + i2) {
                                break;
                            }
                            decl2.flags |= 64;
                            decl2.var = var;
                            code.emitLoad(var);
                            var = var.nextVar();
                            j++;
                            decl2 = decl2.nextDecl();
                            numStubs2 = numStubs2;
                            save_method2 = save_method;
                            callContextSave = callContextSave;
                        }
                        numStubs = numStubs2;
                        Variable callContextSave2 = callContextSave;
                        compilation.callContextVar = ctxArg ? var : null;
                        int j2 = i2;
                        while (j2 < toCall) {
                            this.defaultArgs[j2].compile(compilation, StackTarget.getInstance(decl2.getType()));
                            j2++;
                            decl2 = decl2.nextDecl();
                        }
                        if (varArgs) {
                            String lastTypeName = restArgType.getName();
                            if ("gnu.lists.LList".equals(lastTypeName)) {
                                arg = new QuoteExp(LList.Empty);
                            } else if ("java.lang.Object[]".equals(lastTypeName)) {
                                arg = new QuoteExp(Values.noArgs);
                            } else {
                                Declaration declaration = decl2;
                                throw new Error("unimplemented #!rest type " + lastTypeName);
                            }
                            arg.compile(compilation, restArgType);
                            Declaration declaration2 = decl2;
                        }
                        if (ctxArg) {
                            code.emitLoad(var);
                        }
                        if (isStatic) {
                            code.emitInvokeStatic(this.primMethods[toCall]);
                        } else {
                            code.emitInvokeVirtual(this.primMethods[toCall]);
                        }
                        code.emitReturn();
                        this.closureEnv = null;
                        compilation.callContextVar = callContextSave2;
                    } else {
                        save_method = save_method2;
                        save_lambda = save_lambda2;
                        method = method2;
                        numStubs = numStubs2;
                        if (saveDeclFlags != null) {
                            Declaration decl3 = firstDecl();
                            for (int k2 = 0; k2 < this.min_args + numStubs; k2++) {
                                decl3.flags = saveDeclFlags[k2];
                                decl3.var = null;
                                decl3 = decl3.nextDecl();
                            }
                        }
                        compilation.method.initCode();
                        allocChildClasses(comp);
                        allocParameters(comp);
                        enterFunction(comp);
                        compileBody(comp);
                        compileEnd(comp);
                        generateApplyMethods(comp);
                    }
                    i2++;
                    numStubs2 = numStubs;
                    method2 = method;
                    save_lambda2 = save_lambda;
                    save_method2 = save_method;
                    i = 0;
                }
                Method method3 = method2;
                compilation.method = save_method2;
                compilation.curLambda = save_lambda2;
            }
        }
    }

    public void compileBody(Compilation comp) {
        Target target;
        Variable callContextSave = comp.callContextVar;
        comp.callContextVar = null;
        if (getCallConvention() >= 2) {
            Variable var = getVarScope().lookup("$ctx");
            if (var != null && var.getType() == Compilation.typeCallContext) {
                comp.callContextVar = var;
            }
            target = ConsumerTarget.makeContextTarget(comp);
        } else {
            target = Target.pushValue(getReturnType());
        }
        Expression expression = this.body;
        expression.compileWithPosition(comp, target, expression.getLineNumber() > 0 ? this.body : this);
        comp.callContextVar = callContextSave;
    }

    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        LambdaExp saveLambda;
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            saveLambda = null;
        } else {
            saveLambda = comp.curLambda;
            comp.curLambda = this;
        }
        try {
            return visitor.visitLambdaExp(this, d);
        } finally {
            if (comp != null) {
                comp.curLambda = saveLambda;
            }
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        visitChildrenOnly(visitor, d);
        visitProperties(visitor, d);
    }

    /* access modifiers changed from: protected */
    public final <R, D> void visitChildrenOnly(ExpVisitor<R, D> visitor, D d) {
        Expression expression;
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        try {
            this.throwsSpecification = visitor.visitExps(this.throwsSpecification, d);
            visitor.visitDefaultArgs(this, d);
            if (visitor.exitValue == null && (expression = this.body) != null) {
                this.body = visitor.update(expression, visitor.visit(expression, d));
            }
        } finally {
            visitor.currentLambda = save;
        }
    }

    /* access modifiers changed from: protected */
    public final <R, D> void visitProperties(ExpVisitor<R, D> visitor, D d) {
        Object[] objArr = this.properties;
        if (objArr != null) {
            int len = objArr.length;
            for (int i = 1; i < len; i += 2) {
                Object[] objArr2 = this.properties;
                Object val = objArr2[i];
                if (val instanceof Expression) {
                    objArr2[i] = visitor.visitAndUpdate((Expression) val, d);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        Keyword[] keywordArr = this.keywords;
        if (keywordArr != null && keywordArr.length > 0) {
            return true;
        }
        Expression[] expressionArr = this.defaultArgs;
        if (expressionArr == null) {
            return false;
        }
        int i = expressionArr.length;
        while (true) {
            i--;
            if (i < 0) {
                return false;
            }
            Expression def = this.defaultArgs[i];
            if (def != null && !(def instanceof QuoteExp)) {
                return true;
            }
        }
    }

    public void apply(CallContext ctx) throws Throwable {
        setIndexes();
        ctx.writeValue(new Closure(this, ctx));
    }

    /* access modifiers changed from: package-private */
    public Object evalDefaultArg(int index, CallContext ctx) {
        try {
            return this.defaultArgs[index].eval(ctx);
        } catch (Throwable ex) {
            throw new WrappedException("error evaluating default argument", ex);
        }
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Expression[] margs;
        LambdaExp lambdaExp = this;
        ApplyExp applyExp = exp;
        InlineCalls inlineCalls = visitor;
        Expression[] args = exp.getArgs();
        if ((lambdaExp.flags & 4096) != 0) {
            Expression inlined = InlineCalls.inlineCall(lambdaExp, args, true);
            if (inlined != null) {
                return inlineCalls.visit(inlined, required);
            }
            Type type2 = required;
        } else {
            Type type3 = required;
        }
        exp.visitArgs(visitor);
        int args_length = applyExp.args.length;
        String msg = WrongArguments.checkArgCount(getName(), lambdaExp.min_args, lambdaExp.max_args, args_length);
        if (msg != null) {
            return inlineCalls.noteError(msg);
        }
        int conv = getCallConvention();
        if (!visitor.getCompilation().inlineOk((Expression) lambdaExp) || !isClassMethod()) {
            int i = args_length;
        } else if (conv <= 2 || conv == 3) {
            Method method = lambdaExp.getMethod(args_length);
            Method method2 = method;
            if (method != null) {
                boolean isStatic = lambdaExp.nameDecl.isStatic();
                if (!isStatic && (lambdaExp.outer instanceof ClassExp)) {
                    ((ClassExp) lambdaExp.outer).isMakingClassPair();
                }
                PrimProcedure mproc = new PrimProcedure(method2, lambdaExp);
                if (isStatic) {
                    margs = applyExp.args;
                    Expression[] expressionArr = args;
                    int i2 = args_length;
                } else {
                    LambdaExp curLambda = visitor.getCurrentLambda();
                    while (curLambda != null) {
                        if (curLambda.outer == lambdaExp.outer) {
                            Declaration d = curLambda.firstDecl();
                            if (d == null) {
                                int i3 = args_length;
                            } else if (!d.isThisParameter()) {
                                Expression[] expressionArr2 = args;
                                int i4 = args_length;
                            } else {
                                int nargs = exp.getArgCount();
                                margs = new Expression[(nargs + 1)];
                                Expression[] expressionArr3 = args;
                                int i5 = args_length;
                                System.arraycopy(exp.getArgs(), 0, margs, 1, nargs);
                                margs[0] = new ThisExp(d);
                            }
                            return inlineCalls.noteError("calling non-static method " + getName() + " from static method " + curLambda.getName());
                        }
                        curLambda = curLambda.outerLambda();
                        lambdaExp = this;
                        args_length = args_length;
                    }
                    return inlineCalls.noteError("internal error: missing " + lambdaExp);
                }
                return new ApplyExp((Procedure) mproc, margs).setLine((Expression) applyExp);
            }
            int i6 = args_length;
        } else {
            Expression[] expressionArr4 = args;
            int i7 = args_length;
        }
        return applyExp;
    }

    public void print(OutPort out) {
        Special mode;
        out.startLogicalBlock("(Lambda/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.print("fl:");
        out.print(Integer.toHexString(this.flags));
        out.writeSpaceFill();
        printLineColumn(out);
        int opt_args = 0;
        out.startLogicalBlock("(", false, ")");
        Special prevMode = null;
        int i = 0;
        int opt_i = 0;
        Keyword[] keywordArr = this.keywords;
        int key_args = keywordArr == null ? 0 : keywordArr.length;
        Expression[] expressionArr = this.defaultArgs;
        if (expressionArr != null) {
            opt_args = expressionArr.length - key_args;
        }
        Declaration decl = firstDecl();
        if (decl != null && decl.isThisParameter()) {
            i = -1;
        }
        while (decl != null) {
            int i2 = this.min_args;
            if (i < i2) {
                mode = null;
            } else if (i < i2 + opt_args) {
                mode = Special.optional;
            } else if (this.max_args >= 0 || i != i2 + opt_args) {
                mode = Special.key;
            } else {
                mode = Special.rest;
            }
            if (decl != firstDecl()) {
                out.writeSpaceFill();
            }
            if (mode != prevMode) {
                out.print((Object) mode);
                out.writeSpaceFill();
            }
            Expression defaultArg = null;
            if (mode == Special.optional || mode == Special.key) {
                defaultArg = this.defaultArgs[opt_i];
                opt_i++;
            }
            if (defaultArg != null) {
                out.print('(');
            }
            decl.printInfo(out);
            if (!(defaultArg == null || defaultArg == QuoteExp.falseExp)) {
                out.print(' ');
                defaultArg.print(out);
                out.print(')');
            }
            i++;
            prevMode = mode;
            decl = decl.nextDecl();
        }
        out.endLogicalBlock(")");
        out.writeSpaceLinear();
        Expression expression = this.body;
        if (expression == null) {
            out.print("<null body>");
        } else {
            expression.print(out);
        }
        out.endLogicalBlock(")");
    }

    /* access modifiers changed from: protected */
    public final String getExpClassName() {
        String cname = getClass().getName();
        int index = cname.lastIndexOf(46);
        if (index >= 0) {
            return cname.substring(index + 1);
        }
        return cname;
    }

    public boolean side_effects() {
        return false;
    }

    public String toString() {
        Expression expression;
        String str = getExpClassName() + ':' + getSymbol() + '/' + this.id + '/';
        int l = getLineNumber();
        if (l <= 0 && (expression = this.body) != null) {
            l = expression.getLineNumber();
        }
        if (l > 0) {
            return str + "l:" + l;
        }
        return str;
    }

    public Object getProperty(Object key, Object defaultValue) {
        Object[] objArr;
        Object[] objArr2 = this.properties;
        if (objArr2 != null) {
            int i = objArr2.length;
            do {
                i -= 2;
                if (i >= 0) {
                    objArr = this.properties;
                }
            } while (objArr[i] != key);
            return objArr[i + 1];
        }
        return defaultValue;
    }

    public synchronized void setProperty(Object key, Object value) {
        this.properties = PropertySet.setProperty(this.properties, key, value);
    }

    public final Type getReturnType() {
        if (this.returnType == null) {
            this.returnType = Type.objectType;
            if (this.body != null && !isAbstract()) {
                this.returnType = this.body.getType();
            }
        }
        return this.returnType;
    }

    public final void setReturnType(Type returnType2) {
        this.returnType = returnType2;
    }

    public final void setCoercedReturnType(Type returnType2) {
        this.returnType = returnType2;
        if (returnType2 != null && returnType2 != Type.objectType && returnType2 != Type.voidType && this.body != QuoteExp.abstractExp) {
            Expression value = this.body;
            Expression makeCoercion = Compilation.makeCoercion(value, returnType2);
            this.body = makeCoercion;
            makeCoercion.setLine(value);
        }
    }

    public final void setCoercedReturnValue(Expression type2, Language language) {
        if (!isAbstract()) {
            Expression value = this.body;
            ApplyExp makeCoercion = Compilation.makeCoercion(value, type2);
            this.body = makeCoercion;
            makeCoercion.setLine(value);
        }
        Type rtype = language.getTypeFor(type2);
        if (rtype != null) {
            setReturnType(rtype);
        }
    }
}
