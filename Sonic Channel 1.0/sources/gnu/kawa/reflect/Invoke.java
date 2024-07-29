package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;

public class Invoke extends ProcedureN {
    public static final Invoke invoke = new Invoke("invoke", '*');
    public static final Invoke invokeSpecial = new Invoke("invoke-special", 'P');
    public static final Invoke invokeStatic = new Invoke("invoke-static", 'S');
    public static final Invoke make = new Invoke("make", 'N');
    char kind;
    Language language;

    public Invoke(String name, char kind2) {
        this(name, kind2, Language.getDefaultLanguage());
    }

    public Invoke(String name, char kind2, Language language2) {
        super(name);
        this.kind = kind2;
        this.language = language2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileInvoke:validateApplyInvoke");
    }

    public static Object invoke$V(Object[] args) throws Throwable {
        return invoke.applyN(args);
    }

    public static Object invokeStatic$V(Object[] args) throws Throwable {
        return invokeStatic.applyN(args);
    }

    public static Object make$V(Object[] args) throws Throwable {
        return make.applyN(args);
    }

    private static ObjectType typeFrom(Object arg, Invoke thisProc) {
        if (arg instanceof Class) {
            arg = Type.make((Class) arg);
        }
        if (arg instanceof ObjectType) {
            return (ObjectType) arg;
        }
        if ((arg instanceof String) || (arg instanceof FString)) {
            return ClassType.make(arg.toString());
        }
        if (arg instanceof Symbol) {
            return ClassType.make(((Symbol) arg).getName());
        }
        if (arg instanceof ClassNamespace) {
            return ((ClassNamespace) arg).getClassType();
        }
        throw new WrongType((Procedure) thisProc, 0, arg, "class-specifier");
    }

    public void apply(CallContext ctx) throws Throwable {
        Object[] args = ctx.getArgs();
        char c = this.kind;
        if (c == 'S' || c == 'V' || c == 's' || c == '*') {
            int nargs = args.length;
            Procedure.checkArgCount(this, nargs);
            Object arg0 = args[0];
            char c2 = this.kind;
            Object typeFrom = (c2 == 'S' || c2 == 's') ? typeFrom(arg0, this) : Type.make(arg0.getClass());
            int i = 1;
            Procedure proc = lookupMethods((ObjectType) typeFrom, args[1]);
            char c3 = this.kind;
            if (c3 == 'S') {
                i = 2;
            }
            Object[] margs = new Object[(nargs - i)];
            int i2 = 0;
            if (c3 == 'V' || c3 == '*') {
                margs[0] = args[0];
                i2 = 0 + 1;
            }
            System.arraycopy(args, 2, margs, i2, nargs - 2);
            proc.checkN(margs, ctx);
            return;
        }
        ctx.writeValue(applyN(args));
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object applyN(java.lang.Object[] r20) throws java.lang.Throwable {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            char r0 = r1.kind
            r3 = 80
            if (r0 == r3) goto L_0x01d1
            int r3 = r2.length
            gnu.mapping.Procedure.checkArgCount(r1, r3)
            r0 = 0
            r4 = r2[r0]
            char r5 = r1.kind
            r6 = 42
            r7 = 86
            if (r5 == r7) goto L_0x0020
            if (r5 == r6) goto L_0x0020
            gnu.bytecode.ObjectType r5 = typeFrom(r4, r1)
            goto L_0x002a
        L_0x0020:
            java.lang.Class r5 = r4.getClass()
            gnu.bytecode.Type r5 = gnu.bytecode.Type.make(r5)
            gnu.bytecode.ObjectType r5 = (gnu.bytecode.ObjectType) r5
        L_0x002a:
            char r8 = r1.kind
            r9 = 78
            r10 = 2
            r11 = 1
            if (r8 != r9) goto L_0x0105
            r8 = 0
            boolean r12 = r5 instanceof gnu.expr.TypeValue
            if (r12 == 0) goto L_0x004c
            r12 = r5
            gnu.expr.TypeValue r12 = (gnu.expr.TypeValue) r12
            gnu.mapping.Procedure r12 = r12.getConstructor()
            if (r12 == 0) goto L_0x004c
            int r3 = r3 + -1
            java.lang.Object[] r6 = new java.lang.Object[r3]
            java.lang.System.arraycopy(r2, r11, r6, r0, r3)
            java.lang.Object r0 = r12.applyN(r6)
            return r0
        L_0x004c:
            boolean r12 = r5 instanceof gnu.expr.PairClassType
            if (r12 == 0) goto L_0x0055
            r12 = r5
            gnu.expr.PairClassType r12 = (gnu.expr.PairClassType) r12
            gnu.bytecode.ClassType r5 = r12.instanceType
        L_0x0055:
            boolean r12 = r5 instanceof gnu.bytecode.ArrayType
            if (r12 == 0) goto L_0x00ff
            r0 = r5
            gnu.bytecode.ArrayType r0 = (gnu.bytecode.ArrayType) r0
            gnu.bytecode.Type r6 = r0.getComponentType()
            int r0 = r2.length
            int r7 = r0 + -1
            if (r7 < r10) goto L_0x0090
            r0 = r2[r11]
            boolean r0 = r0 instanceof gnu.expr.Keyword
            if (r0 == 0) goto L_0x0090
            r0 = r2[r11]
            gnu.expr.Keyword r0 = (gnu.expr.Keyword) r0
            java.lang.String r0 = r0.getName()
            r9 = r0
            java.lang.String r12 = "length"
            boolean r0 = r12.equals(r0)
            if (r0 != 0) goto L_0x0084
            java.lang.String r0 = "size"
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x0090
        L_0x0084:
            r0 = r2[r10]
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            r10 = 3
            r12 = 1
            r9 = r0
            goto L_0x0094
        L_0x0090:
            r0 = r7
            r10 = 1
            r12 = 0
            r9 = r0
        L_0x0094:
            java.lang.Class r0 = r6.getReflectClass()
            java.lang.Object r13 = java.lang.reflect.Array.newInstance(r0, r9)
            r0 = 0
            r14 = r10
            r10 = r0
        L_0x009f:
            if (r14 > r7) goto L_0x00fe
            r15 = r2[r14]
            if (r12 == 0) goto L_0x00ea
            boolean r0 = r15 instanceof gnu.expr.Keyword
            if (r0 == 0) goto L_0x00ea
            if (r14 >= r7) goto L_0x00ea
            r0 = r15
            gnu.expr.Keyword r0 = (gnu.expr.Keyword) r0
            java.lang.String r11 = r0.getName()
            int r0 = java.lang.Integer.parseInt(r11)     // Catch:{ all -> 0x00c1 }
            r10 = r0
            int r14 = r14 + 1
            r15 = r2[r14]
            r17 = r4
            r18 = r5
            goto L_0x00ee
        L_0x00c1:
            r0 = move-exception
            r16 = r0
            r0 = r16
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r17 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r18 = r5
            java.lang.String r5 = "non-integer keyword '"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r11)
            java.lang.String r5 = "' in array constructor"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            throw r0
        L_0x00ea:
            r17 = r4
            r18 = r5
        L_0x00ee:
            java.lang.Object r0 = r6.coerceFromObject(r15)
            java.lang.reflect.Array.set(r13, r10, r0)
            r4 = 1
            int r10 = r10 + r4
            int r14 = r14 + r4
            r4 = r17
            r5 = r18
            r11 = 1
            goto L_0x009f
        L_0x00fe:
            return r13
        L_0x00ff:
            r17 = r4
            r18 = r5
            r4 = 1
            goto L_0x010a
        L_0x0105:
            r17 = r4
            r4 = 1
            r8 = r2[r4]
        L_0x010a:
            gnu.mapping.MethodProc r4 = r1.lookupMethods(r5, r8)
            char r11 = r1.kind
            if (r11 == r9) goto L_0x013a
            r9 = 83
            if (r11 == r9) goto L_0x011e
            r9 = 115(0x73, float:1.61E-43)
            if (r11 != r9) goto L_0x011b
            goto L_0x011e
        L_0x011b:
            r16 = 1
            goto L_0x0120
        L_0x011e:
            r16 = 2
        L_0x0120:
            int r9 = r3 - r16
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r12 = 0
            if (r11 == r7) goto L_0x0129
            if (r11 != r6) goto L_0x0130
        L_0x0129:
            int r6 = r12 + 1
            r0 = r2[r0]
            r9[r12] = r0
            r12 = r6
        L_0x0130:
            int r0 = r3 + -2
            java.lang.System.arraycopy(r2, r10, r9, r12, r0)
            java.lang.Object r0 = r4.applyN(r9)
            return r0
        L_0x013a:
            gnu.mapping.CallContext r6 = gnu.mapping.CallContext.getInstance()
            r7 = 0
        L_0x013f:
            int r9 = r2.length
            if (r7 >= r9) goto L_0x014b
            r9 = r2[r7]
            boolean r9 = r9 instanceof gnu.expr.Keyword
            if (r9 != 0) goto L_0x014b
            int r7 = r7 + 1
            goto L_0x013f
        L_0x014b:
            r9 = -1
            int r10 = r2.length
            if (r7 != r10) goto L_0x0183
            int r9 = r4.matchN(r2, r6)
            if (r9 != 0) goto L_0x015a
            java.lang.Object r0 = r6.runUntilValue()
            return r0
        L_0x015a:
            r10 = r5
            gnu.bytecode.ClassType r10 = (gnu.bytecode.ClassType) r10
            gnu.expr.Language r11 = r1.language
            java.lang.String r12 = "valueOf"
            gnu.mapping.MethodProc r10 = gnu.kawa.reflect.ClassMethods.apply(r10, r12, r0, r11)
            if (r10 == 0) goto L_0x017c
            int r11 = r3 + -1
            java.lang.Object[] r11 = new java.lang.Object[r11]
            int r12 = r3 + -1
            r13 = 1
            java.lang.System.arraycopy(r2, r13, r11, r0, r12)
            int r9 = r10.matchN(r11, r6)
            if (r9 != 0) goto L_0x017c
            java.lang.Object r0 = r6.runUntilValue()
            return r0
        L_0x017c:
            r11 = r2[r0]
            java.lang.Object r10 = r4.apply1(r11)
            goto L_0x018d
        L_0x0183:
            java.lang.Object[] r10 = new java.lang.Object[r7]
            java.lang.System.arraycopy(r2, r0, r10, r0, r7)
            java.lang.Object r11 = r4.applyN(r10)
            r10 = r11
        L_0x018d:
            r11 = r7
        L_0x018e:
            int r12 = r11 + 1
            int r13 = r2.length
            if (r12 >= r13) goto L_0x01ab
            r12 = r2[r11]
            boolean r13 = r12 instanceof gnu.expr.Keyword
            if (r13 != 0) goto L_0x019a
            goto L_0x01ab
        L_0x019a:
            r13 = r12
            gnu.expr.Keyword r13 = (gnu.expr.Keyword) r13
            int r14 = r11 + 1
            r12 = r2[r14]
            java.lang.String r14 = r13.getName()
            gnu.kawa.reflect.SlotSet.apply(r0, r10, r14, r12)
            int r11 = r11 + 2
            goto L_0x018e
        L_0x01ab:
            int r12 = r2.length
            if (r7 != r12) goto L_0x01af
            r11 = 1
        L_0x01af:
            int r12 = r2.length
            if (r11 == r12) goto L_0x01d0
            r12 = r5
            gnu.bytecode.ClassType r12 = (gnu.bytecode.ClassType) r12
            gnu.expr.Language r13 = r1.language
            java.lang.String r14 = "add"
            gnu.mapping.MethodProc r0 = gnu.kawa.reflect.ClassMethods.apply(r12, r14, r0, r13)
            if (r0 == 0) goto L_0x01cb
        L_0x01bf:
            int r12 = r2.length
            if (r11 >= r12) goto L_0x01d0
            int r12 = r11 + 1
            r11 = r2[r11]
            r0.apply2(r10, r11)
            r11 = r12
            goto L_0x01bf
        L_0x01cb:
            java.lang.RuntimeException r12 = gnu.mapping.MethodProc.matchFailAsException(r9, r4, r2)
            throw r12
        L_0x01d0:
            return r10
        L_0x01d1:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r19.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = ": invoke-special not allowed at run time"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            goto L_0x01ef
        L_0x01ee:
            throw r0
        L_0x01ef:
            goto L_0x01ee
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.Invoke.applyN(java.lang.Object[]):java.lang.Object");
    }

    public int numArgs() {
        return (this.kind == 'N' ? 1 : 2) | -4096;
    }

    /* access modifiers changed from: protected */
    public MethodProc lookupMethods(ObjectType dtype, Object name) {
        String mname;
        String mname2;
        if (this.kind == 'N') {
            mname = "<init>";
        } else {
            if ((name instanceof String) || (name instanceof FString)) {
                mname2 = name.toString();
            } else if (name instanceof Symbol) {
                mname2 = ((Symbol) name).getName();
            } else {
                throw new WrongType((Procedure) this, 1, (ClassCastException) null);
            }
            mname = Compilation.mangleName(mname2);
        }
        char c = this.kind;
        char c2 = 'V';
        if (c == 'P') {
            c2 = 'P';
        } else if (!(c == '*' || c == 'V')) {
            c2 = 0;
        }
        MethodProc proc = ClassMethods.apply(dtype, mname, c2, this.language);
        if (proc != null) {
            return proc;
        }
        throw new RuntimeException(getName() + ": no method named `" + mname + "' in class " + dtype.getName());
    }

    public static synchronized ApplyExp makeInvokeStatic(ClassType type, String name, Expression[] args) {
        ApplyExp applyExp;
        synchronized (Invoke.class) {
            PrimProcedure method = getStaticMethod(type, name, args);
            if (method != null) {
                applyExp = new ApplyExp((Procedure) method, args);
            } else {
                throw new RuntimeException("missing or ambiguous method `" + name + "' in " + type.getName());
            }
        }
        return applyExp;
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        PrimProcedure staticMethod;
        synchronized (Invoke.class) {
            staticMethod = CompileInvoke.getStaticMethod(type, name, args);
        }
        return staticMethod;
    }
}
