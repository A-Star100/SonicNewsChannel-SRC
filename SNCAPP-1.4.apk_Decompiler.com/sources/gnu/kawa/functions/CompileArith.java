package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;

public class CompileArith implements Inlineable {
    public static CompileArith $Mn = new CompileArith(AddOp.$Mn, 2);
    public static CompileArith $Pl = new CompileArith(AddOp.$Pl, 1);
    int op;
    Procedure proc;

    CompileArith(Object proc2, int op2) {
        this.proc = (Procedure) proc2;
        this.op = op2;
    }

    public static CompileArith forMul(Object proc2) {
        return new CompileArith(proc2, 3);
    }

    public static CompileArith forDiv(Object proc2) {
        return new CompileArith(proc2, ((DivideOp) proc2).op);
    }

    public static CompileArith forBitwise(Object proc2) {
        return new CompileArith(proc2, ((BitwiseOp) proc2).op);
    }

    public static boolean appropriateIntConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixIntValue(args[iarg]);
        if (exp == null) {
            return false;
        }
        args[iarg] = exp;
        return true;
    }

    public static boolean appropriateLongConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixLongValue(args[iarg]);
        if (exp == null) {
            return false;
        }
        args[iarg] = exp;
        return true;
    }

    public static Expression validateApplyArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        int rkind;
        int op2 = ((ArithOp) proc2).op;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length > 2) {
            return pairwise(proc2, exp.getFunction(), args, visitor);
        }
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        if (folded != exp) {
            return folded;
        }
        int rkind2 = 0;
        if (args.length == 2 || args.length == 1) {
            int kind1 = Arithmetic.classifyType(args[0].getType());
            if (args.length != 2 || (op2 >= 9 && op2 <= 12)) {
                rkind = kind1;
            } else {
                int kind2 = Arithmetic.classifyType(args[1].getType());
                rkind = getReturnKind(kind1, kind2, op2);
                if (rkind == 4) {
                    if (kind1 == 1 && appropriateIntConstant(args, 1, visitor)) {
                        rkind = 1;
                    } else if (kind2 == 1 && appropriateIntConstant(args, 0, visitor)) {
                        rkind = 1;
                    } else if (kind1 == 2 && appropriateLongConstant(args, 1, visitor)) {
                        rkind = 2;
                    } else if (kind2 == 2 && appropriateLongConstant(args, 0, visitor)) {
                        rkind = 2;
                    }
                }
            }
            rkind2 = adjustReturnKind(rkind, op2);
            exp.setType(Arithmetic.kindType(rkind2));
        }
        if (!visitor.getCompilation().mustCompile) {
            return exp;
        }
        switch (op2) {
            case 1:
            case 2:
                return validateApplyAdd((AddOp) proc2, exp, visitor);
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return validateApplyDiv((DivideOp) proc2, exp, visitor);
            case 16:
                if (rkind2 > 0) {
                    return validateApplyNot(exp, rkind2, visitor);
                }
                break;
        }
        return exp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0195, code lost:
        if (r11 == 8) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x019c, code lost:
        if (r0 < 13) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x019f, code lost:
        gnu.expr.ApplyExp.compile(r22, r23, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x01a2, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00e1, code lost:
        if (r2 == 8) goto L_0x00f6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0188  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0198  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x01ae  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x014e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compile(gnu.expr.ApplyExp r22, gnu.expr.Compilation r23, gnu.expr.Target r24) {
        /*
            r21 = this;
            r6 = r21
            r7 = r23
            r8 = r24
            gnu.expr.Expression[] r9 = r22.getArgs()
            int r10 = r9.length
            if (r10 != 0) goto L_0x0019
            gnu.mapping.Procedure r0 = r6.proc
            gnu.kawa.functions.ArithOp r0 = (gnu.kawa.functions.ArithOp) r0
            java.lang.Object r0 = r0.defaultResult()
            r7.compileConstant(r0, r8)
            return
        L_0x0019:
            r0 = 1
            if (r10 == r0) goto L_0x0219
            boolean r1 = r8 instanceof gnu.expr.IgnoreTarget
            if (r1 == 0) goto L_0x0022
            goto L_0x0219
        L_0x0022:
            r1 = 0
            r2 = r9[r1]
            gnu.bytecode.Type r2 = r2.getType()
            int r11 = gnu.kawa.functions.Arithmetic.classifyType(r2)
            r2 = r9[r0]
            gnu.bytecode.Type r2 = r2.getType()
            int r12 = gnu.kawa.functions.Arithmetic.classifyType(r2)
            int r2 = r6.op
            int r2 = getReturnKind(r11, r12, r2)
            gnu.bytecode.Type r13 = gnu.kawa.functions.Arithmetic.kindType(r2)
            if (r2 == 0) goto L_0x0213
            r3 = 2
            if (r10 == r3) goto L_0x004a
            r18 = r11
            goto L_0x0215
        L_0x004a:
            gnu.bytecode.Type r14 = r24.getType()
            int r15 = gnu.kawa.functions.Arithmetic.classifyType(r14)
            r1 = 7
            r5 = 8
            r4 = 4
            if (r15 == r0) goto L_0x005a
            if (r15 != r3) goto L_0x0069
        L_0x005a:
            if (r2 < r0) goto L_0x0069
            if (r2 > r4) goto L_0x0069
            r2 = r15
            if (r15 != r0) goto L_0x0064
            gnu.bytecode.PrimType r19 = gnu.kawa.lispexpr.LangPrimType.intType
            goto L_0x0066
        L_0x0064:
            gnu.bytecode.PrimType r19 = gnu.kawa.lispexpr.LangPrimType.longType
        L_0x0066:
            r20 = r19
            goto L_0x0096
        L_0x0069:
            if (r15 == r5) goto L_0x006d
            if (r15 != r1) goto L_0x007e
        L_0x006d:
            if (r2 <= r3) goto L_0x007e
            r0 = 10
            if (r2 > r0) goto L_0x007e
            r2 = r15
            if (r15 != r1) goto L_0x0079
            gnu.bytecode.PrimType r0 = gnu.kawa.lispexpr.LangPrimType.floatType
            goto L_0x007b
        L_0x0079:
            gnu.bytecode.PrimType r0 = gnu.kawa.lispexpr.LangPrimType.doubleType
        L_0x007b:
            r20 = r0
            goto L_0x0096
        L_0x007e:
            if (r2 != r1) goto L_0x0085
            gnu.bytecode.PrimType r0 = gnu.kawa.lispexpr.LangPrimType.floatType
            r20 = r0
            goto L_0x0096
        L_0x0085:
            if (r2 == r5) goto L_0x0090
            r0 = 9
            if (r2 != r0) goto L_0x008c
            goto L_0x0090
        L_0x008c:
            r0 = r13
            r20 = r0
            goto L_0x0096
        L_0x0090:
            r2 = 8
            gnu.bytecode.PrimType r0 = gnu.kawa.lispexpr.LangPrimType.doubleType
            r20 = r0
        L_0x0096:
            int r0 = r6.op
            r3 = 6
            if (r0 < r4) goto L_0x00f6
            if (r0 > r5) goto L_0x00f6
            gnu.mapping.Procedure r0 = r6.proc
            gnu.kawa.functions.DivideOp r0 = (gnu.kawa.functions.DivideOp) r0
            int r5 = r0.op
            if (r5 != r4) goto L_0x00ae
            if (r2 <= r4) goto L_0x00f6
            if (r2 >= r3) goto L_0x00f6
            r5 = 9
            if (r2 > r5) goto L_0x00ae
            goto L_0x00f6
        L_0x00ae:
            int r5 = r0.op
            r3 = 5
            if (r5 != r3) goto L_0x00b9
            r3 = 10
            if (r2 > r3) goto L_0x00b9
            if (r2 != r1) goto L_0x00c1
        L_0x00b9:
            int r3 = r0.op
            if (r3 != r4) goto L_0x00c5
            r3 = 10
            if (r2 != r3) goto L_0x00c5
        L_0x00c1:
            r2 = 8
            r5 = r2
            goto L_0x00f7
        L_0x00c5:
            int r3 = r0.op
            if (r3 == r1) goto L_0x00d4
            int r3 = r0.op
            r5 = 6
            if (r3 != r5) goto L_0x00d1
            if (r2 > r4) goto L_0x00d1
            goto L_0x00d4
        L_0x00d1:
            r3 = 8
            goto L_0x00e4
        L_0x00d4:
            int r3 = r0.getRoundingMode()
            r5 = 3
            if (r3 == r5) goto L_0x00f6
            if (r2 == r4) goto L_0x00f6
            if (r2 == r1) goto L_0x00f6
            r3 = 8
            if (r2 != r3) goto L_0x00e4
            goto L_0x00f6
        L_0x00e4:
            int r5 = r0.op
            if (r5 != r3) goto L_0x00f2
            int r3 = r0.getRoundingMode()
            r5 = 3
            if (r3 == r5) goto L_0x00f6
            if (r2 != r4) goto L_0x00f2
            goto L_0x00f6
        L_0x00f2:
            gnu.expr.ApplyExp.compile(r22, r23, r24)
            return
        L_0x00f6:
            r5 = r2
        L_0x00f7:
            int r0 = r6.op
            if (r0 != r4) goto L_0x014a
            r2 = 10
            if (r5 > r2) goto L_0x014a
            r2 = 8
            if (r5 == r2) goto L_0x014a
            if (r5 == r1) goto L_0x014a
            r0 = 6
            if (r5 == r0) goto L_0x0119
            if (r5 <= r4) goto L_0x010b
            goto L_0x0119
        L_0x010b:
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeIntNum
            gnu.kawa.lispexpr.LangObjType r1 = gnu.kawa.functions.Arithmetic.typeRatNum
            java.lang.String r2 = "make"
            r3 = 2
            gnu.bytecode.Method r1 = r1.getDeclaredMethod(r2, r3)
            r20 = r0
            goto L_0x012c
        L_0x0119:
            r0 = 6
            if (r5 != r0) goto L_0x011f
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeRatNum
            goto L_0x0121
        L_0x011f:
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeRealNum
        L_0x0121:
            r1 = r0
            java.lang.String r2 = "divide"
            r3 = 2
            gnu.bytecode.Method r0 = r0.getDeclaredMethod(r2, r3)
            r20 = r1
            r1 = r0
        L_0x012c:
            gnu.expr.Target r0 = gnu.expr.StackTarget.getInstance(r20)
            r2 = 0
            r2 = r9[r2]
            r2.compile((gnu.expr.Compilation) r7, (gnu.expr.Target) r0)
            r2 = 1
            r2 = r9[r2]
            r2.compile((gnu.expr.Compilation) r7, (gnu.expr.Target) r0)
            gnu.bytecode.CodeAttr r2 = r23.getCode()
            r2.emitInvokeStatic(r1)
            r18 = r11
            r0 = r20
            r11 = r5
            goto L_0x020f
        L_0x014a:
            r2 = 13
            if (r5 != r4) goto L_0x0188
            r3 = 1
            if (r0 == r3) goto L_0x0174
            r3 = 3
            if (r0 == r3) goto L_0x0174
            r3 = 2
            if (r0 == r3) goto L_0x0174
            if (r0 == r2) goto L_0x0174
            r3 = 14
            if (r0 == r3) goto L_0x0174
            r3 = 15
            if (r0 == r3) goto L_0x0174
            if (r0 == r1) goto L_0x0174
            r3 = 8
            if (r0 == r3) goto L_0x0174
            r3 = 9
            if (r0 < r3) goto L_0x0170
            r3 = 11
            if (r0 > r3) goto L_0x0170
            goto L_0x0174
        L_0x0170:
            r18 = r11
            r11 = r5
            goto L_0x018b
        L_0x0174:
            r0 = 0
            r1 = r9[r0]
            r0 = 1
            r2 = r9[r0]
            r0 = r21
            r3 = r11
            r4 = r12
            r18 = r11
            r11 = r5
            r5 = r23
            r0.compileIntNum(r1, r2, r3, r4, r5)
            goto L_0x020d
        L_0x0188:
            r18 = r11
            r11 = r5
        L_0x018b:
            r3 = 1
            if (r11 == r3) goto L_0x01a3
            r3 = 2
            if (r11 == r3) goto L_0x01a3
            if (r11 == r1) goto L_0x0198
            r1 = 8
            if (r11 != r1) goto L_0x019f
            goto L_0x019a
        L_0x0198:
            r1 = 8
        L_0x019a:
            if (r0 <= r1) goto L_0x01a3
            if (r0 < r2) goto L_0x019f
            goto L_0x01a3
        L_0x019f:
            gnu.expr.ApplyExp.compile(r22, r23, r24)
            return
        L_0x01a3:
            gnu.expr.Target r0 = gnu.expr.StackTarget.getInstance(r20)
            gnu.bytecode.CodeAttr r1 = r23.getCode()
            r2 = 0
        L_0x01ac:
            if (r2 >= r10) goto L_0x020c
            r3 = 1
            if (r2 != r3) goto L_0x01c1
            int r3 = r6.op
            r4 = 9
            if (r3 < r4) goto L_0x01c1
            r4 = 12
            if (r3 > r4) goto L_0x01c1
            gnu.bytecode.PrimType r3 = gnu.bytecode.Type.intType
            gnu.expr.Target r0 = gnu.expr.StackTarget.getInstance(r3)
        L_0x01c1:
            r3 = r9[r2]
            r3.compile((gnu.expr.Compilation) r7, (gnu.expr.Target) r0)
            if (r2 != 0) goto L_0x01cd
            r16 = 0
            r19 = 1
            goto L_0x0209
        L_0x01cd:
            switch(r11) {
                case 1: goto L_0x01d5;
                case 2: goto L_0x01d5;
                case 7: goto L_0x01d5;
                case 8: goto L_0x01d5;
                default: goto L_0x01d0;
            }
        L_0x01d0:
            r16 = 0
            r19 = 1
            goto L_0x0209
        L_0x01d5:
            int r3 = r6.op
            r4 = 9
            if (r3 != r4) goto L_0x01f8
            r3 = 2
            gnu.bytecode.Type[] r5 = new gnu.bytecode.Type[r3]
            r16 = 0
            r5[r16] = r20
            gnu.bytecode.PrimType r17 = gnu.bytecode.Type.intType
            r19 = 1
            r5[r19] = r17
            java.lang.String r17 = "gnu.math.IntNum"
            gnu.bytecode.ClassType r3 = gnu.bytecode.ClassType.make(r17)
            java.lang.String r4 = "shift"
            gnu.bytecode.Method r3 = r3.getDeclaredMethod((java.lang.String) r4, (gnu.bytecode.Type[]) r5)
            r1.emitInvokeStatic(r3)
            goto L_0x0209
        L_0x01f8:
            r16 = 0
            r19 = 1
            int r3 = r21.primitiveOpcode()
            gnu.bytecode.Type r4 = r20.getImplementationType()
            gnu.bytecode.PrimType r4 = (gnu.bytecode.PrimType) r4
            r1.emitBinop((int) r3, (gnu.bytecode.Type) r4)
        L_0x0209:
            int r2 = r2 + 1
            goto L_0x01ac
        L_0x020c:
        L_0x020d:
            r0 = r20
        L_0x020f:
            r8.compileFromStack(r7, r0)
            return
        L_0x0213:
            r18 = r11
        L_0x0215:
            gnu.expr.ApplyExp.compile(r22, r23, r24)
            return
        L_0x0219:
            gnu.expr.ApplyExp.compile(r22, r23, r24)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileArith.compile(gnu.expr.ApplyExp, gnu.expr.Compilation, gnu.expr.Target):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00ff, code lost:
        if (r15 != null) goto L_0x0103;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0101, code lost:
        r15 = "ior";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0103, code lost:
        if (r15 != null) goto L_0x0107;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0105, code lost:
        r15 = "xor";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0107, code lost:
        r1 = gnu.bytecode.ClassType.make("gnu.math.BitOps");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0174, code lost:
        if (r16 != null) goto L_0x0181;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0176, code lost:
        r2 = new gnu.bytecode.Type[]{r0, r5};
        r16 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0181, code lost:
        r2 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0183, code lost:
        r14.emitInvokeStatic(r1.getMethod(r15, r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x018a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean compileIntNum(gnu.expr.Expression r21, gnu.expr.Expression r22, int r23, int r24, gnu.expr.Compilation r25) {
        /*
            r20 = this;
            r6 = r20
            r7 = r22
            r8 = r25
            int r0 = r6.op
            r1 = 2
            r3 = 1
            if (r0 != r1) goto L_0x0067
            boolean r0 = r7 instanceof gnu.expr.QuoteExp
            if (r0 == 0) goto L_0x0067
            java.lang.Object r9 = r22.valueIfConstant()
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            r10 = r24
            if (r10 > r1) goto L_0x0033
            r0 = r9
            java.lang.Number r0 = (java.lang.Number) r0
            long r11 = r0.longValue()
            r13 = -2147483648(0xffffffff80000000, double:NaN)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 <= 0) goto L_0x002f
            int r0 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r0 > 0) goto L_0x002f
            r0 = 1
            goto L_0x0030
        L_0x002f:
            r0 = 0
        L_0x0030:
            r12 = r11
            r11 = r0
            goto L_0x004d
        L_0x0033:
            boolean r0 = r9 instanceof gnu.math.IntNum
            if (r0 == 0) goto L_0x0048
            r0 = r9
            gnu.math.IntNum r0 = (gnu.math.IntNum) r0
            long r11 = r0.longValue()
            r13 = -2147483647(0xffffffff80000001, double:NaN)
            boolean r0 = r0.inRange(r13, r4)
            r12 = r11
            r11 = r0
            goto L_0x004d
        L_0x0048:
            r0 = 0
            r11 = 0
            r12 = r11
            r11 = r0
        L_0x004d:
            if (r11 == 0) goto L_0x0069
            gnu.kawa.functions.CompileArith r0 = $Pl
            long r1 = -r12
            int r2 = (int) r1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
            gnu.expr.QuoteExp r2 = gnu.expr.QuoteExp.getInstance(r1)
            r4 = 1
            r1 = r21
            r3 = r23
            r5 = r25
            boolean r0 = r0.compileIntNum(r1, r2, r3, r4, r5)
            return r0
        L_0x0067:
            r10 = r24
        L_0x0069:
            int r0 = r6.op
            r4 = 3
            if (r0 == r3) goto L_0x0073
            if (r0 != r4) goto L_0x0071
            goto L_0x0073
        L_0x0071:
            r5 = 0
            goto L_0x0074
        L_0x0073:
            r5 = 1
        L_0x0074:
            r9 = r5
            if (r9 == 0) goto L_0x00be
            java.lang.Integer r0 = gnu.expr.InlineCalls.checkIntValue(r21)
            if (r0 == 0) goto L_0x0080
            r0 = 1
            r11 = r0
            goto L_0x0082
        L_0x0080:
            r11 = r23
        L_0x0082:
            java.lang.Integer r0 = gnu.expr.InlineCalls.checkIntValue(r22)
            if (r0 == 0) goto L_0x008a
            r0 = 1
            r10 = r0
        L_0x008a:
            if (r11 != r3) goto L_0x0090
            if (r10 == r3) goto L_0x0090
            r0 = 1
            goto L_0x0091
        L_0x0090:
            r0 = 0
        L_0x0091:
            r12 = r0
            if (r12 == 0) goto L_0x00af
            boolean r0 = r21.side_effects()
            if (r0 == 0) goto L_0x00a0
            boolean r0 = r22.side_effects()
            if (r0 != 0) goto L_0x00af
        L_0x00a0:
            r0 = r20
            r1 = r22
            r2 = r21
            r3 = r10
            r4 = r11
            r5 = r25
            boolean r0 = r0.compileIntNum(r1, r2, r3, r4, r5)
            return r0
        L_0x00af:
            if (r11 != r3) goto L_0x00b4
            gnu.bytecode.PrimType r0 = gnu.bytecode.Type.intType
            goto L_0x00b6
        L_0x00b4:
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeIntNum
        L_0x00b6:
            if (r10 != r3) goto L_0x00bb
            gnu.bytecode.PrimType r5 = gnu.bytecode.Type.intType
            goto L_0x00bd
        L_0x00bb:
            gnu.kawa.lispexpr.LangObjType r5 = gnu.kawa.functions.Arithmetic.typeIntNum
        L_0x00bd:
            goto L_0x00d4
        L_0x00be:
            r5 = 9
            if (r0 < r5) goto L_0x00ce
            r5 = 12
            if (r0 > r5) goto L_0x00ce
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeIntNum
            gnu.bytecode.PrimType r5 = gnu.bytecode.Type.intType
            r12 = 0
            r11 = r23
            goto L_0x00d4
        L_0x00ce:
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeIntNum
            r5 = r0
            r12 = 0
            r11 = r23
        L_0x00d4:
            r13 = r21
            r13.compile((gnu.expr.Compilation) r8, (gnu.bytecode.Type) r0)
            r7.compile((gnu.expr.Compilation) r8, (gnu.bytecode.Type) r5)
            gnu.bytecode.CodeAttr r14 = r25.getCode()
            if (r12 == 0) goto L_0x00e9
            r14.emitSwap()
            gnu.kawa.lispexpr.LangObjType r0 = gnu.kawa.functions.Arithmetic.typeIntNum
            gnu.bytecode.PrimType r5 = gnu.kawa.lispexpr.LangPrimType.intType
        L_0x00e9:
            r15 = 0
            r16 = 0
            gnu.kawa.lispexpr.LangObjType r17 = gnu.kawa.functions.Arithmetic.typeIntNum
            int r1 = r6.op
            r19 = 0
            switch(r1) {
                case 1: goto L_0x0170;
                case 2: goto L_0x016b;
                case 3: goto L_0x0166;
                case 4: goto L_0x012d;
                case 5: goto L_0x012d;
                case 6: goto L_0x012d;
                case 7: goto L_0x012d;
                case 8: goto L_0x012d;
                case 9: goto L_0x0126;
                case 10: goto L_0x0111;
                case 11: goto L_0x0111;
                case 12: goto L_0x00f5;
                case 13: goto L_0x00fd;
                case 14: goto L_0x00ff;
                case 15: goto L_0x0103;
                default: goto L_0x00f5;
            }
        L_0x00f5:
            r1 = r19
            java.lang.Error r2 = new java.lang.Error
            r2.<init>()
            throw r2
        L_0x00fd:
            java.lang.String r15 = "and"
        L_0x00ff:
            if (r15 != 0) goto L_0x0103
            java.lang.String r15 = "ior"
        L_0x0103:
            if (r15 != 0) goto L_0x0107
            java.lang.String r15 = "xor"
        L_0x0107:
            java.lang.String r1 = "gnu.math.BitOps"
            gnu.bytecode.ClassType r17 = gnu.bytecode.ClassType.make(r1)
            r1 = r17
            goto L_0x0174
        L_0x0111:
            r4 = r19
            r2 = 10
            if (r1 != r2) goto L_0x011a
            java.lang.String r1 = "shiftLeft"
            goto L_0x011c
        L_0x011a:
            java.lang.String r1 = "shiftRight"
        L_0x011c:
            r15 = r1
            java.lang.String r1 = "gnu.kawa.functions.BitwiseOp"
            gnu.bytecode.ClassType r17 = gnu.bytecode.ClassType.make(r1)
            r1 = r17
            goto L_0x0174
        L_0x0126:
            r1 = r19
            java.lang.String r15 = "shift"
            r1 = r17
            goto L_0x0174
        L_0x012d:
            r2 = 8
            if (r1 != r2) goto L_0x0134
            java.lang.String r19 = "remainder"
            goto L_0x0136
        L_0x0134:
            java.lang.String r19 = "quotient"
        L_0x0136:
            r15 = r19
            gnu.mapping.Procedure r4 = r6.proc
            gnu.kawa.functions.DivideOp r4 = (gnu.kawa.functions.DivideOp) r4
            if (r1 != r2) goto L_0x0147
            int r1 = r4.rounding_mode
            if (r1 != r3) goto L_0x0147
            java.lang.String r15 = "modulo"
            r1 = r17
            goto L_0x0174
        L_0x0147:
            int r1 = r4.rounding_mode
            r2 = 3
            if (r1 == r2) goto L_0x0163
            int r1 = r4.rounding_mode
            r14.emitPushInt(r1)
            gnu.bytecode.Type[] r1 = new gnu.bytecode.Type[r2]
            r2 = 0
            r1[r2] = r0
            r1[r3] = r5
            gnu.bytecode.PrimType r2 = gnu.bytecode.Type.intType
            r18 = 2
            r1[r18] = r2
            r16 = r1
            r1 = r17
            goto L_0x0174
        L_0x0163:
            r1 = r17
            goto L_0x0174
        L_0x0166:
            java.lang.String r15 = "times"
            r1 = r17
            goto L_0x0174
        L_0x016b:
            java.lang.String r15 = "sub"
            r1 = r17
            goto L_0x0174
        L_0x0170:
            java.lang.String r15 = "add"
            r1 = r17
        L_0x0174:
            if (r16 != 0) goto L_0x0181
            r2 = 2
            gnu.bytecode.Type[] r2 = new gnu.bytecode.Type[r2]
            r4 = 0
            r2[r4] = r0
            r2[r3] = r5
            r16 = r2
            goto L_0x0183
        L_0x0181:
            r2 = r16
        L_0x0183:
            gnu.bytecode.Method r4 = r1.getMethod(r15, r2)
            r14.emitInvokeStatic(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileArith.compileIntNum(gnu.expr.Expression, gnu.expr.Expression, int, int, gnu.expr.Compilation):boolean");
    }

    public static int getReturnKind(int kind1, int kind2, int op2) {
        if (op2 < 9 || op2 > 12) {
            return (kind1 <= 0 || (kind1 > kind2 && kind2 > 0)) ? kind1 : kind2;
        }
        return kind1;
    }

    public int getReturnKind(Expression[] args) {
        int len = args.length;
        if (len == 0) {
            return 4;
        }
        ClassType classType = Type.pointer_type;
        int kindr = 0;
        for (int i = 0; i < len; i++) {
            int kind = Arithmetic.classifyType(args[i].getType());
            if (i == 0 || kind == 0 || kind > kindr) {
                kindr = kind;
            }
        }
        return kindr;
    }

    public Type getReturnType(Expression[] args) {
        return Arithmetic.kindType(adjustReturnKind(getReturnKind(args), this.op));
    }

    static int adjustReturnKind(int rkind, int op2) {
        if (op2 < 4 || op2 > 7 || rkind <= 0) {
            return rkind;
        }
        switch (op2) {
            case 4:
                if (rkind <= 4) {
                    return 6;
                }
                return rkind;
            case 5:
                if (rkind > 10 || rkind == 7) {
                    return rkind;
                }
                return 8;
            case 7:
                if (rkind <= 10) {
                    return 4;
                }
                return rkind;
            default:
                return rkind;
        }
    }

    public static Expression validateApplyAdd(AddOp proc2, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length == 1 && proc2.plusOrMinus < 0) {
            Type type0 = args[0].getType();
            if (type0 instanceof PrimType) {
                char sig0 = type0.getSignature().charAt(0);
                Type type = null;
                int opcode = 0;
                if (!(sig0 == 'V' || sig0 == 'Z' || sig0 == 'C')) {
                    if (sig0 == 'D') {
                        opcode = 119;
                        type = LangPrimType.doubleType;
                    } else if (sig0 == 'F') {
                        opcode = 118;
                        type = LangPrimType.floatType;
                    } else if (sig0 == 'J') {
                        opcode = 117;
                        type = LangPrimType.longType;
                    } else {
                        opcode = 116;
                        type = LangPrimType.intType;
                    }
                }
                if (type != null) {
                    return new ApplyExp((Procedure) PrimProcedure.makeBuiltinUnary(opcode, type), args);
                }
            }
        }
        return exp;
    }

    public static Expression validateApplyDiv(DivideOp proc2, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length != 1) {
            return exp;
        }
        Expression[] args2 = {QuoteExp.getInstance(IntNum.one()), args[0]};
        return new ApplyExp(exp.getFunction(), args2);
    }

    public static Expression validateApplyNot(ApplyExp exp, int kind, InlineCalls visitor) {
        String cname;
        if (exp.getArgCount() == 1) {
            Expression arg = exp.getArg(0);
            if (kind == 1 || kind == 2) {
                return visitor.visitApplyOnly(new ApplyExp((Procedure) BitwiseOp.xor, arg, QuoteExp.getInstance(IntNum.minusOne())), (Type) null);
            }
            if (kind == 4) {
                cname = "gnu.math.BitOps";
            } else if (kind == 3) {
                cname = "java.meth.BigInteger";
            } else {
                cname = null;
            }
            if (cname != null) {
                return new ApplyExp(ClassType.make(cname).getDeclaredMethod("not", 1), exp.getArgs());
            }
        }
        return exp;
    }

    public static Expression validateApplyNumberCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc2, visitor);
        if (folded != exp) {
            return folded;
        }
        return exp;
    }

    public int primitiveOpcode() {
        switch (this.op) {
            case 1:
                return 96;
            case 2:
                return 100;
            case 3:
                return 104;
            case 4:
            case 5:
            case 6:
            case 7:
                return 108;
            case 8:
                return 112;
            case 10:
                return 120;
            case 11:
                return 122;
            case 12:
                return 124;
            case 13:
                return 126;
            case 14:
                return 128;
            case 15:
                return 130;
            default:
                return -1;
        }
    }

    public static Expression pairwise(Procedure proc2, Expression rproc, Expression[] args, InlineCalls visitor) {
        int len = args.length;
        Expression prev = args[0];
        for (int i = 1; i < len; i++) {
            ApplyExp next = new ApplyExp(rproc, prev, args[i]);
            Expression inlined = visitor.maybeInline(next, (Type) null, proc2);
            prev = inlined != null ? inlined : next;
        }
        return prev;
    }

    public static Expression validateApplyNumberPredicate(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc2) {
        int i = ((NumberPredicate) proc2).op;
        Expression[] args = exp.getArgs();
        args[0] = visitor.visit(args[0], (Type) LangObjType.integerType);
        exp.setType(Type.booleanType);
        return exp;
    }
}
