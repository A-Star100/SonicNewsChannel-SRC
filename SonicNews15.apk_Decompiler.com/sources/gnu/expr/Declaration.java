package gnu.expr;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.SourceLocator;

public class Declaration implements SourceLocator {
    static final int CAN_CALL = 4;
    static final int CAN_READ = 2;
    static final int CAN_WRITE = 8;
    public static final long CLASS_ACCESS_FLAGS = 25820135424L;
    public static final int EARLY_INIT = 536870912;
    public static final long ENUM_ACCESS = 8589934592L;
    public static final int EXPORT_SPECIFIED = 1024;
    public static final int EXTERNAL_ACCESS = 524288;
    public static final long FIELD_ACCESS_FLAGS = 32463912960L;
    public static final int FIELD_OR_METHOD = 1048576;
    public static final long FINAL_ACCESS = 17179869184L;
    static final int INDIRECT_BINDING = 1;
    public static final int IS_ALIAS = 256;
    public static final int IS_CONSTANT = 16384;
    public static final int IS_DYNAMIC = 268435456;
    static final int IS_FLUID = 16;
    public static final int IS_IMPORTED = 131072;
    public static final int IS_NAMESPACE_PREFIX = 2097152;
    static final int IS_SIMPLE = 64;
    public static final int IS_SINGLE_VALUE = 262144;
    public static final int IS_SYNTAX = 32768;
    public static final int IS_UNKNOWN = 65536;
    public static final long METHOD_ACCESS_FLAGS = 17431527424L;
    public static final int MODULE_REFERENCE = 1073741824;
    public static final int NONSTATIC_SPECIFIED = 4096;
    public static final int NOT_DEFINING = 512;
    public static final int PACKAGE_ACCESS = 134217728;
    static final int PRIVATE = 32;
    public static final int PRIVATE_ACCESS = 16777216;
    public static final String PRIVATE_PREFIX = "$Prvt$";
    public static final int PRIVATE_SPECIFIED = 16777216;
    static final int PROCEDURE = 128;
    public static final int PROTECTED_ACCESS = 33554432;
    public static final int PUBLIC_ACCESS = 67108864;
    public static final int STATIC_SPECIFIED = 2048;
    public static final long TRANSIENT_ACCESS = 4294967296L;
    public static final int TYPE_SPECIFIED = 8192;
    static final String UNKNOWN_PREFIX = "loc$";
    public static final long VOLATILE_ACCESS = 2147483648L;
    static int counter;
    public Declaration base;
    public ScopeExp context;
    int evalIndex;
    public Field field;
    String filename;
    public ApplyExp firstCall;
    protected long flags;
    protected int id;
    Method makeLocationMethod;
    Declaration next;
    Declaration nextCapturedVar;
    int position;
    Object symbol;
    protected Type type;
    protected Expression typeExp;
    protected Expression value;
    Variable var;

    public void setCode(int code) {
        if (code < 0) {
            this.id = code;
            return;
        }
        throw new Error("code must be negative");
    }

    public int getCode() {
        return this.id;
    }

    public final Expression getTypeExp() {
        if (this.typeExp == null) {
            setType(Type.objectType);
        }
        return this.typeExp;
    }

    public final Type getType() {
        if (this.type == null) {
            setType(Type.objectType);
        }
        return this.type;
    }

    public final void setType(Type type2) {
        this.type = type2;
        Variable variable = this.var;
        if (variable != null) {
            variable.setType(type2);
        }
        this.typeExp = QuoteExp.getInstance(type2);
    }

    public final void setTypeExp(Expression typeExp2) {
        Type t;
        this.typeExp = typeExp2;
        if (typeExp2 instanceof TypeValue) {
            t = ((TypeValue) typeExp2).getImplementationType();
        } else {
            t = Language.getDefaultLanguage().getTypeFor(typeExp2, false);
        }
        if (t == null) {
            t = Type.pointer_type;
        }
        this.type = t;
        Variable variable = this.var;
        if (variable != null) {
            variable.setType(t);
        }
    }

    public final String getName() {
        Object obj = this.symbol;
        if (obj == null) {
            return null;
        }
        return obj instanceof Symbol ? ((Symbol) obj).getName() : obj.toString();
    }

    public final void setName(Object symbol2) {
        this.symbol = symbol2;
    }

    public final Object getSymbol() {
        return this.symbol;
    }

    public final void setSymbol(Object symbol2) {
        this.symbol = symbol2;
    }

    public final Declaration nextDecl() {
        return this.next;
    }

    public final void setNext(Declaration next2) {
        this.next = next2;
    }

    public Variable getVariable() {
        return this.var;
    }

    public final boolean isSimple() {
        return (this.flags & 64) != 0;
    }

    public final void setSimple(boolean b) {
        setFlag(b, 64);
        Variable variable = this.var;
        if (variable != null && !variable.isParameter()) {
            this.var.setSimple(b);
        }
    }

    public final void setSyntax() {
        setSimple(false);
        setFlag(536920064);
    }

    public final ScopeExp getContext() {
        return this.context;
    }

    /* access modifiers changed from: package-private */
    public void loadOwningObject(Declaration owner, Compilation comp) {
        if (owner == null) {
            owner = this.base;
        }
        if (owner != null) {
            owner.load((AccessExp) null, 0, comp, Target.pushObject);
        } else {
            getContext().currentLambda().loadHeapFrame(comp);
        }
    }

    public void load(AccessExp access, int flags2, Compilation comp, Target target) {
        int fragment_cookie;
        Method meth;
        ClassType ltype;
        ReferenceExp rexp;
        Declaration orig;
        AccessExp accessExp = access;
        int i = flags2;
        Compilation compilation = comp;
        Target target2 = target;
        if (!(target2 instanceof IgnoreTarget)) {
            Declaration owner = accessExp == null ? null : access.contextDecl();
            if (isAlias()) {
                Expression expression = this.value;
                if ((expression instanceof ReferenceExp) && (orig = rexp.binding) != null && (((i & 2) == 0 || orig.isIndirectBinding()) && (owner == null || !orig.needsContext()))) {
                    orig.load((rexp = (ReferenceExp) expression), i, compilation, target2);
                    return;
                }
            }
            if (!isFluid() || !(this.context instanceof FluidLetExp)) {
                CodeAttr code = comp.getCode();
                Type rtype = getType();
                if (isIndirectBinding() || (i & 2) == 0) {
                    Field field2 = this.field;
                    if (field2 != null) {
                        compilation.usedClass(field2.getDeclaringClass());
                        compilation.usedClass(this.field.getType());
                        if (!this.field.getStaticFlag()) {
                            loadOwningObject(owner, compilation);
                            code.emitGetField(this.field);
                        } else {
                            code.emitGetStatic(this.field);
                        }
                    } else if (!isIndirectBinding() || !compilation.immediate || getVariable() != null) {
                        if (compilation.immediate) {
                            Object constantValue = getConstantValue();
                            Object val = constantValue;
                            if (constantValue != null) {
                                compilation.compileConstant(val, target2);
                                return;
                            }
                        }
                        if (this.value != QuoteExp.undefined_exp && ignorable()) {
                            Expression expression2 = this.value;
                            if (!(expression2 instanceof LambdaExp) || !(((LambdaExp) expression2).outer instanceof ModuleExp)) {
                                this.value.compile(compilation, target2);
                                return;
                            }
                        }
                        Variable var2 = getVariable();
                        if ((this.context instanceof ClassExp) && var2 == null && !getFlag(128)) {
                            ClassExp classExp = (ClassExp) this.context;
                            ClassExp cl = classExp;
                            if (classExp.isMakingClassPair()) {
                                Method getter = cl.type.getDeclaredMethod(ClassExp.slotToMethodName("get", getName()), 0);
                                cl.loadHeapFrame(compilation);
                                code.emitInvoke(getter);
                            }
                        }
                        if (var2 == null) {
                            var2 = allocateVariable(code);
                        }
                        code.emitLoad(var2);
                    } else {
                        Environment env = Environment.getCurrent();
                        Object obj = this.symbol;
                        Symbol sym = obj instanceof Symbol ? (Symbol) obj : env.getSymbol(obj.toString());
                        Object property = null;
                        if (isProcedureDecl() && comp.getLanguage().hasSeparateFunctionNamespace()) {
                            property = EnvironmentKey.FUNCTION;
                        }
                        compilation.compileConstant(env.getLocation(sym, property), Target.pushValue(Compilation.typeLocation));
                    }
                    if (isIndirectBinding() && (i & 2) == 0) {
                        if (accessExp != null) {
                            String fileName = access.getFileName();
                            String filename2 = fileName;
                            if (fileName != null) {
                                int lineNumber = access.getLineNumber();
                                int line = lineNumber;
                                if (lineNumber > 0) {
                                    ClassType typeUnboundLocationException = ClassType.make("gnu.mapping.UnboundLocationException");
                                    boolean isInTry = code.isInTry();
                                    int column = access.getColumnNumber();
                                    Label startTry = new Label(code);
                                    startTry.define(code);
                                    code.emitInvokeVirtual(Compilation.getLocationMethod);
                                    Label endTry = new Label(code);
                                    endTry.define(code);
                                    Label endLabel = new Label(code);
                                    endLabel.setTypes(code);
                                    if (isInTry) {
                                        code.emitGoto(endLabel);
                                    } else {
                                        code.setUnreachable();
                                    }
                                    if (!isInTry) {
                                        fragment_cookie = code.beginFragment(endLabel);
                                    } else {
                                        fragment_cookie = 0;
                                    }
                                    code.addHandler(startTry, endTry, typeUnboundLocationException);
                                    code.emitDup((Type) typeUnboundLocationException);
                                    code.emitPushString(filename2);
                                    code.emitPushInt(line);
                                    code.emitPushInt(column);
                                    code.emitInvokeVirtual(typeUnboundLocationException.getDeclaredMethod("setLine", 3));
                                    code.emitThrow();
                                    if (isInTry) {
                                        endLabel.define(code);
                                    } else {
                                        code.endFragment(fragment_cookie);
                                    }
                                    rtype = Type.pointer_type;
                                }
                            }
                        }
                        code.emitInvokeVirtual(Compilation.getLocationMethod);
                        rtype = Type.pointer_type;
                    }
                } else if (this.field != null) {
                    boolean immediate = compilation.immediate;
                    int i2 = 2;
                    if (this.field.getStaticFlag()) {
                        ltype = ClassType.make("gnu.kawa.reflect.StaticFieldLocation");
                        if (immediate) {
                            i2 = 1;
                        }
                        meth = ltype.getDeclaredMethod("make", i2);
                    } else {
                        ClassType ltype2 = ClassType.make("gnu.kawa.reflect.FieldLocation");
                        Method meth2 = ltype2.getDeclaredMethod("make", immediate ? 2 : 3);
                        loadOwningObject(owner, compilation);
                        ClassType classType = ltype2;
                        meth = meth2;
                        ltype = classType;
                    }
                    if (immediate) {
                        compilation.compileConstant(this);
                    } else {
                        compilation.compileConstant(this.field.getDeclaringClass().getName());
                        compilation.compileConstant(this.field.getName());
                    }
                    code.emitInvokeStatic(meth);
                    rtype = ltype;
                } else {
                    throw new Error("internal error: cannot take location of " + this);
                }
                target2.compileFromStack(compilation, rtype);
                return;
            }
            this.base.load(accessExp, i, compilation, target2);
        }
    }

    public void compileStore(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (isSimple()) {
            code.emitStore(getVariable());
        } else if (!this.field.getStaticFlag()) {
            loadOwningObject((Declaration) null, comp);
            code.emitSwap();
            code.emitPutField(this.field);
        } else {
            code.emitPutStatic(this.field);
        }
    }

    public final Expression getValue() {
        if (this.value == QuoteExp.undefined_exp) {
            Field field2 = this.field;
            if (field2 != null && (field2.getModifiers() & 24) == 24 && !isIndirectBinding()) {
                try {
                    this.value = new QuoteExp(this.field.getReflectField().get((Object) null));
                } catch (Throwable th) {
                }
            }
        } else if ((this.value instanceof QuoteExp) && getFlag(8192) && this.value.getType() != this.type) {
            try {
                Object val = ((QuoteExp) this.value).getValue();
                Type t = getType();
                this.value = new QuoteExp(t.coerceFromObject(val), t);
            } catch (Throwable th2) {
            }
        }
        return this.value;
    }

    public final void setValue(Expression value2) {
        this.value = value2;
    }

    public final Object getConstantValue() {
        Object v = getValue();
        if (!(v instanceof QuoteExp) || v == QuoteExp.undefined_exp) {
            return null;
        }
        return ((QuoteExp) v).getValue();
    }

    public final boolean hasConstantValue() {
        Object v = getValue();
        return (v instanceof QuoteExp) && v != QuoteExp.undefined_exp;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldEarlyInit() {
        return getFlag(536870912) || isCompiletimeConstant();
    }

    public boolean isCompiletimeConstant() {
        return getFlag(16384) && hasConstantValue();
    }

    public final boolean needsExternalAccess() {
        long j = this.flags;
        return (j & 524320) == 524320 || (j & 2097184) == 2097184;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.field;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean needsContext() {
        /*
            r1 = this;
            gnu.expr.Declaration r0 = r1.base
            if (r0 != 0) goto L_0x0010
            gnu.bytecode.Field r0 = r1.field
            if (r0 == 0) goto L_0x0010
            boolean r0 = r0.getStaticFlag()
            if (r0 != 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Declaration.needsContext():boolean");
    }

    public final boolean getFlag(long flag) {
        return (this.flags & flag) != 0;
    }

    public final void setFlag(boolean setting, long flag) {
        if (setting) {
            this.flags |= flag;
        } else {
            this.flags &= -1 ^ flag;
        }
    }

    public final void setFlag(long flag) {
        this.flags |= flag;
    }

    public final boolean isPublic() {
        return (this.context instanceof ModuleExp) && (this.flags & 32) == 0;
    }

    public final boolean isPrivate() {
        return (this.flags & 32) != 0;
    }

    public final void setPrivate(boolean isPrivate) {
        setFlag(isPrivate, 32);
    }

    public short getAccessFlags(short defaultFlags) {
        short flags2;
        if (getFlag(251658240)) {
            flags2 = 0;
            if (getFlag(16777216)) {
                flags2 = (short) (0 | 2);
            }
            if (getFlag(33554432)) {
                flags2 = (short) (flags2 | 4);
            }
            if (getFlag(67108864)) {
                flags2 = (short) (flags2 | 1);
            }
        } else {
            flags2 = defaultFlags;
        }
        if (getFlag(VOLATILE_ACCESS)) {
            flags2 = (short) (flags2 | 64);
        }
        if (getFlag(TRANSIENT_ACCESS)) {
            flags2 = (short) (flags2 | 128);
        }
        if (getFlag(ENUM_ACCESS)) {
            flags2 = (short) (flags2 | Access.ENUM);
        }
        if (getFlag(FINAL_ACCESS)) {
            return (short) (flags2 | 16);
        }
        return flags2;
    }

    public final boolean isAlias() {
        return (this.flags & 256) != 0;
    }

    public final void setAlias(boolean flag) {
        setFlag(flag, 256);
    }

    public final boolean isFluid() {
        return (this.flags & 16) != 0;
    }

    public final void setFluid(boolean fluid) {
        setFlag(fluid, 16);
    }

    public final boolean isProcedureDecl() {
        return (this.flags & 128) != 0;
    }

    public final void setProcedureDecl(boolean val) {
        setFlag(val, 128);
    }

    public final boolean isNamespaceDecl() {
        return (this.flags & 2097152) != 0;
    }

    public final boolean isIndirectBinding() {
        return (this.flags & 1) != 0;
    }

    public final void setIndirectBinding(boolean indirectBinding) {
        setFlag(indirectBinding, 1);
    }

    public void maybeIndirectBinding(Compilation comp) {
        if ((isLexical() && !(this.context instanceof ModuleExp)) || this.context == comp.mainLambda) {
            setIndirectBinding(true);
        }
    }

    public final boolean getCanRead() {
        return (this.flags & 2) != 0;
    }

    public final void setCanRead(boolean read) {
        setFlag(read, 2);
    }

    public final void setCanRead() {
        setFlag(true, 2);
        Declaration declaration = this.base;
        if (declaration != null) {
            declaration.setCanRead();
        }
    }

    public final boolean getCanCall() {
        return (this.flags & 4) != 0;
    }

    public final void setCanCall(boolean called) {
        setFlag(called, 4);
    }

    public final void setCanCall() {
        setFlag(true, 4);
        Declaration declaration = this.base;
        if (declaration != null) {
            declaration.setCanRead();
        }
    }

    public final boolean getCanWrite() {
        return (this.flags & 8) != 0;
    }

    public final void setCanWrite(boolean written) {
        if (written) {
            this.flags |= 8;
        } else {
            this.flags &= -9;
        }
    }

    public final void setCanWrite() {
        this.flags |= 8;
        Declaration declaration = this.base;
        if (declaration != null) {
            declaration.setCanRead();
        }
    }

    public final boolean isThisParameter() {
        return this.symbol == ThisExp.THIS_NAME;
    }

    public boolean ignorable() {
        if (getCanRead() || isPublic()) {
            return false;
        }
        if (getCanWrite() && getFlag(65536)) {
            return false;
        }
        if (!getCanCall()) {
            return true;
        }
        Expression value2 = getValue();
        if (value2 == null || !(value2 instanceof LambdaExp)) {
            return false;
        }
        LambdaExp lexp = (LambdaExp) value2;
        if (!lexp.isHandlingTailCalls() || lexp.getInlineOnly()) {
            return true;
        }
        return false;
    }

    public boolean needsInit() {
        return !ignorable() && (this.value != QuoteExp.nullExp || this.base == null);
    }

    public boolean isStatic() {
        Field field2 = this.field;
        if (field2 != null) {
            return field2.getStaticFlag();
        }
        if (getFlag(2048) || isCompiletimeConstant()) {
            return true;
        }
        if (getFlag(4096)) {
            return false;
        }
        LambdaExp lambda = this.context.currentLambda();
        if (!(lambda instanceof ModuleExp) || !((ModuleExp) lambda).isStatic()) {
            return false;
        }
        return true;
    }

    public final boolean isLexical() {
        return (this.flags & 268501008) == 0;
    }

    public static final boolean isUnknown(Declaration decl) {
        return decl == null || decl.getFlag(65536);
    }

    public void noteValue(Expression value2) {
        if (this.value == QuoteExp.undefined_exp) {
            if (value2 instanceof LambdaExp) {
                ((LambdaExp) value2).nameDecl = this;
            }
            this.value = value2;
            return;
        }
        Expression expression = this.value;
        if (expression != value2) {
            if (expression instanceof LambdaExp) {
                ((LambdaExp) expression).nameDecl = null;
            }
            this.value = null;
        }
    }

    protected Declaration() {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.value = QuoteExp.undefined_exp;
        this.flags = 64;
        this.makeLocationMethod = null;
    }

    public Declaration(Variable var2) {
        this((Object) var2.getName(), var2.getType());
        this.var = var2;
    }

    public Declaration(Object name) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.value = QuoteExp.undefined_exp;
        this.flags = 64;
        this.makeLocationMethod = null;
        setName(name);
    }

    public Declaration(Object name, Type type2) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.value = QuoteExp.undefined_exp;
        this.flags = 64;
        this.makeLocationMethod = null;
        setName(name);
        setType(type2);
    }

    public Declaration(Object name, Field field2) {
        this(name, field2.getType());
        this.field = field2;
        setSimple(false);
    }

    public void pushIndirectBinding(Compilation comp) {
        CodeAttr code = comp.getCode();
        code.emitPushString(getName());
        if (this.makeLocationMethod == null) {
            this.makeLocationMethod = Compilation.typeLocation.addMethod("make", new Type[]{Type.pointer_type, Type.string_type}, (Type) Compilation.typeLocation, 9);
        }
        code.emitInvokeStatic(this.makeLocationMethod);
    }

    public final Variable allocateVariable(CodeAttr code) {
        if (!isSimple() || this.var == null) {
            String vname = null;
            if (this.symbol != null) {
                vname = Compilation.mangleNameIfNeeded(getName());
            }
            if (!isAlias() || !(getValue() instanceof ReferenceExp)) {
                this.var = this.context.getVarScope().addVariable(code, isIndirectBinding() ? Compilation.typeLocation : getType().getImplementationType(), vname);
            } else {
                Declaration base2 = followAliases(this);
                this.var = base2 == null ? null : base2.var;
            }
        }
        return this.var;
    }

    public final void setLocation(SourceLocator location) {
        this.filename = location.getFileName();
        setLine(location.getLineNumber(), location.getColumnNumber());
    }

    public final void setFile(String filename2) {
        this.filename = filename2;
    }

    public final void setLine(int lineno, int colno) {
        if (lineno < 0) {
            lineno = 0;
        }
        if (colno < 0) {
            colno = 0;
        }
        this.position = (lineno << 12) + colno;
    }

    public final void setLine(int lineno) {
        setLine(lineno, 0);
    }

    public final String getFileName() {
        return this.filename;
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        return this.filename;
    }

    public final int getLineNumber() {
        int line = this.position >> 12;
        if (line == 0) {
            return -1;
        }
        return line;
    }

    public final int getColumnNumber() {
        int column = this.position & 4095;
        if (column == 0) {
            return -1;
        }
        return column;
    }

    public boolean isStableSourceLocation() {
        return true;
    }

    public void printInfo(OutPort out) {
        StringBuffer sbuf = new StringBuffer();
        printInfo(sbuf);
        out.print(sbuf.toString());
    }

    public void printInfo(StringBuffer sbuf) {
        sbuf.append(this.symbol);
        sbuf.append('/');
        sbuf.append(this.id);
        sbuf.append("/fl:");
        sbuf.append(Long.toHexString(this.flags));
        if (ignorable()) {
            sbuf.append("(ignorable)");
        }
        Expression tx = this.typeExp;
        Type t = getType();
        if (tx != null && !(tx instanceof QuoteExp)) {
            sbuf.append("::");
            sbuf.append(tx);
        } else if (!(this.type == null || t == Type.pointer_type)) {
            sbuf.append("::");
            sbuf.append(t.getName());
        }
        if (this.base != null) {
            sbuf.append("(base:#");
            sbuf.append(this.base.id);
            sbuf.append(')');
        }
    }

    public String toString() {
        return "Declaration[" + this.symbol + '/' + this.id + ']';
    }

    public static Declaration followAliases(Declaration decl) {
        Declaration orig;
        while (decl != null && decl.isAlias()) {
            Expression declValue = decl.getValue();
            if (!(declValue instanceof ReferenceExp) || (orig = ((ReferenceExp) declValue).binding) == null) {
                break;
            }
            decl = orig;
        }
        return decl;
    }

    public void makeField(Compilation comp, Expression value2) {
        setSimple(false);
        makeField(comp.mainClass, comp, value2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0075, code lost:
        if (((gnu.expr.ModuleExp) r4).staticInitRun() != false) goto L_0x0077;
     */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void makeField(gnu.bytecode.ClassType r13, gnu.expr.Compilation r14, gnu.expr.Expression r15) {
        /*
            r12 = this;
            boolean r0 = r12.needsExternalAccess()
            r1 = 0
            r2 = 16384(0x4000, double:8.0948E-320)
            boolean r2 = r12.getFlag(r2)
            r3 = 8192(0x2000, double:4.0474E-320)
            boolean r3 = r12.getFlag(r3)
            boolean r4 = r14.immediate
            if (r4 == 0) goto L_0x0023
            gnu.expr.ScopeExp r4 = r12.context
            boolean r4 = r4 instanceof gnu.expr.ModuleExp
            if (r4 == 0) goto L_0x0023
            if (r2 != 0) goto L_0x0023
            if (r3 != 0) goto L_0x0023
            r4 = 1
            r12.setIndirectBinding(r4)
        L_0x0023:
            boolean r4 = r12.isPublic()
            if (r4 != 0) goto L_0x002f
            if (r0 != 0) goto L_0x002f
            boolean r4 = r14.immediate
            if (r4 == 0) goto L_0x0031
        L_0x002f:
            r1 = r1 | 1
        L_0x0031:
            boolean r4 = r12.isStatic()
            if (r4 != 0) goto L_0x0059
            r4 = 268501008(0x10010010, double:1.32657124E-315)
            boolean r4 = r12.getFlag(r4)
            if (r4 == 0) goto L_0x004c
            boolean r4 = r12.isIndirectBinding()
            if (r4 == 0) goto L_0x004c
            boolean r4 = r12.isAlias()
            if (r4 == 0) goto L_0x0059
        L_0x004c:
            boolean r4 = r15 instanceof gnu.expr.ClassExp
            if (r4 == 0) goto L_0x005b
            r4 = r15
            gnu.expr.LambdaExp r4 = (gnu.expr.LambdaExp) r4
            boolean r4 = r4.getNeedsClosureEnv()
            if (r4 != 0) goto L_0x005b
        L_0x0059:
            r1 = r1 | 8
        L_0x005b:
            boolean r4 = r12.isIndirectBinding()
            if (r4 != 0) goto L_0x0077
            if (r2 == 0) goto L_0x0083
            boolean r4 = r12.shouldEarlyInit()
            if (r4 != 0) goto L_0x0077
            gnu.expr.ScopeExp r4 = r12.context
            boolean r5 = r4 instanceof gnu.expr.ModuleExp
            if (r5 == 0) goto L_0x0083
            gnu.expr.ModuleExp r4 = (gnu.expr.ModuleExp) r4
            boolean r4 = r4.staticInitRun()
            if (r4 == 0) goto L_0x0083
        L_0x0077:
            gnu.expr.ScopeExp r4 = r12.context
            boolean r5 = r4 instanceof gnu.expr.ClassExp
            if (r5 != 0) goto L_0x0081
            boolean r4 = r4 instanceof gnu.expr.ModuleExp
            if (r4 == 0) goto L_0x0083
        L_0x0081:
            r1 = r1 | 16
        L_0x0083:
            gnu.bytecode.Type r4 = r12.getType()
            gnu.bytecode.Type r4 = r4.getImplementationType()
            boolean r5 = r12.isIndirectBinding()
            if (r5 == 0) goto L_0x009b
            gnu.bytecode.ClassType r5 = gnu.expr.Compilation.typeLocation
            boolean r5 = r4.isSubtype(r5)
            if (r5 != 0) goto L_0x009b
            gnu.bytecode.ClassType r4 = gnu.expr.Compilation.typeLocation
        L_0x009b:
            boolean r5 = r12.ignorable()
            if (r5 != 0) goto L_0x017a
            java.lang.String r5 = r12.getName()
            if (r5 != 0) goto L_0x00b0
            java.lang.String r5 = "$unnamed$0"
            int r6 = r5.length()
            int r6 = r6 + -2
            goto L_0x00f2
        L_0x00b0:
            java.lang.String r5 = gnu.expr.Compilation.mangleNameIfNeeded(r5)
            r6 = 65536(0x10000, double:3.2379E-319)
            boolean r6 = r12.getFlag(r6)
            if (r6 == 0) goto L_0x00d0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "loc$"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r5 = r6.toString()
        L_0x00d0:
            if (r0 == 0) goto L_0x00ee
            r6 = 1073741824(0x40000000, double:5.304989477E-315)
            boolean r6 = r12.getFlag(r6)
            if (r6 != 0) goto L_0x00ee
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "$Prvt$"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.String r5 = r6.toString()
        L_0x00ee:
            int r6 = r5.length()
        L_0x00f2:
            r7 = 0
        L_0x00f3:
            gnu.bytecode.Field r8 = r13.getDeclaredField(r5)
            if (r8 == 0) goto L_0x0118
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r9 = 0
            java.lang.String r9 = r5.substring(r9, r6)
            java.lang.StringBuilder r8 = r8.append(r9)
            r9 = 36
            java.lang.StringBuilder r8 = r8.append(r9)
            int r7 = r7 + 1
            java.lang.StringBuilder r8 = r8.append(r7)
            java.lang.String r5 = r8.toString()
            goto L_0x00f3
        L_0x0118:
            gnu.bytecode.Field r8 = r13.addField(r5, r4, r1)
            r12.field = r8
            boolean r8 = r15 instanceof gnu.expr.QuoteExp
            if (r8 == 0) goto L_0x017a
            r8 = r15
            gnu.expr.QuoteExp r8 = (gnu.expr.QuoteExp) r8
            java.lang.Object r8 = r8.getValue()
            gnu.bytecode.Field r9 = r12.field
            boolean r9 = r9.getStaticFlag()
            if (r9 == 0) goto L_0x0155
            java.lang.Class r9 = r8.getClass()
            java.lang.String r9 = r9.getName()
            java.lang.String r10 = r4.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x0155
            gnu.expr.LitTable r9 = r14.litTable
            gnu.expr.Literal r9 = r9.findLiteral(r8)
            gnu.bytecode.Field r10 = r9.field
            if (r10 != 0) goto L_0x0154
            gnu.bytecode.Field r10 = r12.field
            gnu.expr.LitTable r11 = r14.litTable
            r9.assign((gnu.bytecode.Field) r10, (gnu.expr.LitTable) r11)
        L_0x0154:
            goto L_0x017a
        L_0x0155:
            boolean r9 = r4 instanceof gnu.bytecode.PrimType
            if (r9 != 0) goto L_0x0165
            java.lang.String r9 = r4.getName()
            java.lang.String r10 = "java.lang.String"
            boolean r9 = r10.equals(r9)
            if (r9 == 0) goto L_0x017a
        L_0x0165:
            boolean r9 = r8 instanceof gnu.text.Char
            if (r9 == 0) goto L_0x0174
            r9 = r8
            gnu.text.Char r9 = (gnu.text.Char) r9
            int r9 = r9.intValue()
            gnu.math.IntNum r8 = gnu.math.IntNum.make((int) r9)
        L_0x0174:
            gnu.bytecode.Field r9 = r12.field
            r9.setConstantValue(r8, r13)
            return
        L_0x017a:
            boolean r5 = r12.shouldEarlyInit()
            if (r5 != 0) goto L_0x018f
            boolean r5 = r12.isIndirectBinding()
            if (r5 != 0) goto L_0x018c
            if (r15 == 0) goto L_0x018f
            boolean r5 = r15 instanceof gnu.expr.ClassExp
            if (r5 != 0) goto L_0x018f
        L_0x018c:
            gnu.expr.BindingInitializer.create(r12, r15, r14)
        L_0x018f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Declaration.makeField(gnu.bytecode.ClassType, gnu.expr.Compilation, gnu.expr.Expression):void");
    }

    /* access modifiers changed from: package-private */
    public Location makeIndirectLocationFor() {
        Object obj = this.symbol;
        return Location.make(obj instanceof Symbol ? (Symbol) obj : Namespace.EmptyNamespace.getSymbol(this.symbol.toString().intern()));
    }

    public static Declaration getDeclarationFromStatic(String cname, String fname) {
        Declaration decl = new Declaration((Object) fname, ClassType.make(cname).getDeclaredField(fname));
        decl.setFlag(18432);
        return decl;
    }

    public static Declaration getDeclarationValueFromStatic(String className, String fieldName, String name) {
        try {
            Object value2 = Class.forName(className).getDeclaredField(fieldName).get((Object) null);
            Declaration decl = new Declaration((Object) name, ClassType.make(className).getDeclaredField(fieldName));
            decl.noteValue(new QuoteExp(value2));
            decl.setFlag(18432);
            return decl;
        } catch (Exception ex) {
            throw new WrappedException((Throwable) ex);
        }
    }

    public static Declaration getDeclaration(Named proc) {
        return getDeclaration(proc, proc.getName());
    }

    public static Declaration getDeclaration(Object proc, String name) {
        Class procClass;
        Field procField = null;
        if (!(name == null || (procClass = PrimProcedure.getProcedureClass(proc)) == null)) {
            procField = ((ClassType) Type.make(procClass)).getDeclaredField(Compilation.mangleNameIfNeeded(name));
        }
        if (procField == null) {
            return null;
        }
        int fflags = procField.getModifiers();
        if ((fflags & 8) == 0) {
            return null;
        }
        Declaration decl = new Declaration((Object) name, procField);
        decl.noteValue(new QuoteExp(proc));
        if ((fflags & 16) != 0) {
            decl.setFlag(16384);
        }
        return decl;
    }
}
