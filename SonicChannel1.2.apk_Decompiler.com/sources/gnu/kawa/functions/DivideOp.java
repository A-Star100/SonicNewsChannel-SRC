package gnu.kawa.functions;

import gnu.mapping.Procedure;

public class DivideOp extends ArithOp {
    public static final DivideOp $Sl = new DivideOp("/", 4);
    public static final DivideOp div;
    public static final DivideOp div0;
    public static final DivideOp idiv;
    public static final DivideOp mod;
    public static final DivideOp mod0;
    public static final DivideOp modulo;
    public static final DivideOp quotient;
    public static final DivideOp remainder;
    int rounding_mode;

    public int getRoundingMode() {
        return this.rounding_mode;
    }

    static {
        DivideOp divideOp = new DivideOp("idiv", 7);
        idiv = divideOp;
        DivideOp divideOp2 = new DivideOp("quotient", 6);
        quotient = divideOp2;
        DivideOp divideOp3 = new DivideOp("remainder", 8);
        remainder = divideOp3;
        DivideOp divideOp4 = new DivideOp("modulo", 8);
        modulo = divideOp4;
        DivideOp divideOp5 = new DivideOp("div", 6);
        div = divideOp5;
        DivideOp divideOp6 = new DivideOp("mod", 8);
        mod = divideOp6;
        DivideOp divideOp7 = new DivideOp("div0", 6);
        div0 = divideOp7;
        DivideOp divideOp8 = new DivideOp("mod0", 8);
        mod0 = divideOp8;
        divideOp.rounding_mode = 3;
        divideOp2.rounding_mode = 3;
        divideOp3.rounding_mode = 3;
        divideOp4.rounding_mode = 1;
        divideOp5.rounding_mode = 5;
        divideOp6.rounding_mode = 5;
        divideOp7.rounding_mode = 4;
        divideOp8.rounding_mode = 4;
    }

    public DivideOp(String name, int op) {
        super(name, op);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forDiv");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        if (r8 != 2) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007e, code lost:
        if (r11 == 7) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0179, code lost:
        r15 = java.math.RoundingMode.HALF_EVEN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x017b, code lost:
        r16 = r1;
        r1 = new java.math.MathContext(0, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0184, code lost:
        switch(r0.op) {
            case 4: goto L_0x019e;
            case 5: goto L_0x0187;
            case 6: goto L_0x0199;
            case 7: goto L_0x018d;
            case 8: goto L_0x0188;
            default: goto L_0x0187;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0188, code lost:
        r7 = r12.remainder(r13, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x018d, code lost:
        r7 = r12.divideToIntegralValue(r13, r1).toBigInteger();
        r11 = 3;
        r8 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0199, code lost:
        r7 = r12.divideToIntegralValue(r13, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x019e, code lost:
        r7 = r12.divide(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01a3, code lost:
        r19 = r1;
        r18 = r15;
        r31 = r16;
        r16 = r12;
        r17 = r13;
        r12 = r5;
        r6 = null;
        r14 = r31;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object applyN(java.lang.Object[] r34) throws java.lang.Throwable {
        /*
            r33 = this;
            r0 = r33
            r1 = r34
            int r2 = r1.length
            if (r2 != 0) goto L_0x000c
            gnu.math.IntNum r3 = gnu.math.IntNum.one()
            return r3
        L_0x000c:
            r3 = 0
            r4 = r1[r3]
            java.lang.Number r4 = (java.lang.Number) r4
            r5 = 1
            if (r2 != r5) goto L_0x001d
            gnu.math.IntNum r3 = gnu.math.IntNum.one()
            java.lang.Object r3 = r0.apply2(r3, r4)
            return r3
        L_0x001d:
            int r6 = gnu.kawa.functions.Arithmetic.classifyValue(r4)
            r7 = 1
            r8 = 0
            r12 = 0
            r14 = r8
            r16 = r12
            r17 = r16
            r18 = r17
            r19 = r18
            r20 = 0
            r22 = 0
            r8 = r6
            r9 = r7
            r12 = r14
            r6 = 0
            r7 = r4
            r4 = 0
        L_0x0038:
            if (r9 >= r2) goto L_0x02eb
            r24 = r1[r9]
            int r10 = gnu.kawa.functions.Arithmetic.classifyValue(r24)
            if (r8 >= r10) goto L_0x0044
            r11 = r10
            goto L_0x0045
        L_0x0044:
            r11 = r8
        L_0x0045:
            r8 = r11
            r5 = 4
            if (r8 >= r5) goto L_0x0063
            int r5 = r0.op
            switch(r5) {
                case 4: goto L_0x005b;
                case 5: goto L_0x005b;
                default: goto L_0x004f;
            }
        L_0x004f:
            int r5 = r0.rounding_mode
            r1 = 3
            if (r5 != r1) goto L_0x0060
            r1 = 1
            if (r8 == r1) goto L_0x0064
            r5 = 2
            if (r8 != r5) goto L_0x0061
            goto L_0x0064
        L_0x005b:
            r5 = 4
            r8 = r5
            r11 = r5
            r1 = 1
            goto L_0x0064
        L_0x0060:
            r1 = 1
        L_0x0061:
            r11 = 4
            goto L_0x0064
        L_0x0063:
            r1 = 1
        L_0x0064:
            int r5 = r0.op
            r1 = 5
            r28 = r2
            r2 = 8
            if (r5 != r1) goto L_0x007b
            r1 = 10
            if (r8 > r1) goto L_0x007b
            r11 = 10
            if (r8 == r2) goto L_0x0089
            r1 = 7
            if (r8 == r1) goto L_0x0089
            r8 = 9
            goto L_0x0089
        L_0x007b:
            if (r11 == r2) goto L_0x0081
            r1 = 7
            if (r11 != r1) goto L_0x0089
            goto L_0x0082
        L_0x0081:
            r1 = 7
        L_0x0082:
            r11 = 9
            int r5 = r0.op
            if (r5 != r1) goto L_0x0089
            r8 = r11
        L_0x0089:
            switch(r11) {
                case 1: goto L_0x0215;
                case 2: goto L_0x01f6;
                case 3: goto L_0x008c;
                case 4: goto L_0x01b3;
                case 5: goto L_0x0145;
                case 6: goto L_0x008c;
                case 7: goto L_0x008c;
                case 8: goto L_0x008c;
                case 9: goto L_0x00b9;
                default: goto L_0x008c;
            }
        L_0x008c:
            r25 = 0
            r1 = r16
            r5 = r17
            r16 = r19
            r17 = r18
            gnu.math.Numeric r2 = gnu.kawa.functions.Arithmetic.asNumeric(r7)
            r19 = r1
            gnu.math.Numeric r1 = gnu.kawa.functions.Arithmetic.asNumeric(r24)
            r27 = r3
            int r3 = r0.op
            r29 = r4
            r4 = 8
            if (r3 != r4) goto L_0x0235
            boolean r3 = r1.isZero()
            if (r3 == 0) goto L_0x0235
            boolean r3 = r1.isExact()
            if (r3 == 0) goto L_0x0230
            r3 = r2
            goto L_0x0234
        L_0x00b9:
            r1 = r16
            r2 = r3
            r3 = r4
            r4 = r17
            r5 = r6
            r6 = r19
            r16 = r18
            double r17 = gnu.kawa.functions.Arithmetic.asDouble(r7)
            double r22 = gnu.kawa.functions.Arithmetic.asDouble(r24)
            r20 = r1
            int r1 = r0.op
            switch(r1) {
                case 4: goto L_0x0125;
                case 5: goto L_0x0125;
                case 6: goto L_0x0110;
                case 7: goto L_0x00fc;
                case 8: goto L_0x00da;
                default: goto L_0x00d3;
            }
        L_0x00d3:
            r21 = r2
            r27 = r3
            r25 = 0
            goto L_0x0132
        L_0x00da:
            r25 = 0
            int r1 = (r22 > r25 ? 1 : (r22 == r25 ? 0 : -1))
            if (r1 == 0) goto L_0x00f3
            r21 = r2
            double r1 = r17 / r22
            r27 = r3
            int r3 = r33.getRoundingMode()
            double r1 = gnu.math.RealNum.toInt(r1, r3)
            double r1 = r1 * r22
            double r17 = r17 - r1
            goto L_0x00f7
        L_0x00f3:
            r21 = r2
            r27 = r3
        L_0x00f7:
            gnu.math.DFloNum r7 = gnu.math.DFloNum.make(r17)
            goto L_0x0132
        L_0x00fc:
            r21 = r2
            r27 = r3
            r25 = 0
            double r1 = r17 / r22
            int r3 = r33.getRoundingMode()
            gnu.math.IntNum r7 = gnu.math.RealNum.toExactInt(r1, r3)
            r1 = 4
            r11 = r1
            r8 = r1
            goto L_0x0132
        L_0x0110:
            r21 = r2
            r27 = r3
            r25 = 0
            double r1 = r17 / r22
            int r3 = r33.getRoundingMode()
            double r1 = gnu.math.RealNum.toInt(r1, r3)
            java.lang.Double r7 = java.lang.Double.valueOf(r1)
            goto L_0x0132
        L_0x0125:
            r21 = r2
            r27 = r3
            r25 = 0
            double r1 = r17 / r22
            gnu.math.DFloNum r7 = gnu.math.DFloNum.make(r1)
        L_0x0132:
            r19 = r6
            r3 = r21
            r6 = r5
            r31 = r17
            r17 = r4
            r18 = r16
            r16 = r20
            r4 = r27
            r20 = r31
            goto L_0x02af
        L_0x0145:
            r25 = 0
            r1 = r14
            r5 = r12
            java.math.BigDecimal r12 = gnu.kawa.functions.Arithmetic.asBigDecimal(r7)
            java.math.BigDecimal r13 = gnu.kawa.functions.Arithmetic.asBigDecimal(r24)
            r14 = 0
            int r15 = r33.getRoundingMode()
            switch(r15) {
                case 1: goto L_0x0176;
                case 2: goto L_0x0171;
                case 3: goto L_0x016c;
                case 4: goto L_0x0159;
                case 5: goto L_0x015c;
                default: goto L_0x0159;
            }
        L_0x0159:
            r15 = r18
            goto L_0x0179
        L_0x015c:
            r15 = r18
            int r16 = r13.signum()
            if (r16 >= 0) goto L_0x0167
            java.math.RoundingMode r16 = java.math.RoundingMode.CEILING
            goto L_0x0169
        L_0x0167:
            java.math.RoundingMode r16 = java.math.RoundingMode.FLOOR
        L_0x0169:
            r15 = r16
            goto L_0x0179
        L_0x016c:
            r15 = r18
            java.math.RoundingMode r15 = java.math.RoundingMode.DOWN
            goto L_0x017b
        L_0x0171:
            r15 = r18
            java.math.RoundingMode r15 = java.math.RoundingMode.CEILING
            goto L_0x017b
        L_0x0176:
            java.math.RoundingMode r15 = java.math.RoundingMode.FLOOR
            goto L_0x017b
        L_0x0179:
            java.math.RoundingMode r15 = java.math.RoundingMode.HALF_EVEN
        L_0x017b:
            r16 = r1
            java.math.MathContext r1 = new java.math.MathContext
            r1.<init>(r14, r15)
            int r2 = r0.op
            switch(r2) {
                case 4: goto L_0x019e;
                case 5: goto L_0x0187;
                case 6: goto L_0x0199;
                case 7: goto L_0x018d;
                case 8: goto L_0x0188;
                default: goto L_0x0187;
            }
        L_0x0187:
            goto L_0x01a3
        L_0x0188:
            java.math.BigDecimal r7 = r12.remainder(r13, r1)
            goto L_0x01a3
        L_0x018d:
            java.math.BigDecimal r2 = r12.divideToIntegralValue(r13, r1)
            java.math.BigInteger r7 = r2.toBigInteger()
            r2 = 3
            r11 = r2
            r8 = r2
            goto L_0x01a3
        L_0x0199:
            java.math.BigDecimal r7 = r12.divideToIntegralValue(r13, r1)
            goto L_0x01a3
        L_0x019e:
            java.math.BigDecimal r7 = r12.divide(r13)
        L_0x01a3:
            r19 = r1
            r18 = r15
            r31 = r16
            r16 = r12
            r17 = r13
            r12 = r5
            r6 = r14
            r14 = r31
            goto L_0x02af
        L_0x01b3:
            r25 = 0
            r1 = r14
            int r5 = r0.op
            switch(r5) {
                case 4: goto L_0x01de;
                case 5: goto L_0x01bb;
                case 6: goto L_0x01cd;
                case 7: goto L_0x01cd;
                case 8: goto L_0x01bc;
                default: goto L_0x01bb;
            }
        L_0x01bb:
            goto L_0x01f3
        L_0x01bc:
            gnu.math.IntNum r5 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r7)
            gnu.math.IntNum r14 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r24)
            int r15 = r33.getRoundingMode()
            gnu.math.IntNum r7 = gnu.math.IntNum.remainder(r5, r14, r15)
            goto L_0x01f3
        L_0x01cd:
            gnu.math.IntNum r5 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r7)
            gnu.math.IntNum r14 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r24)
            int r15 = r33.getRoundingMode()
            gnu.math.IntNum r7 = gnu.math.IntNum.quotient(r5, r14, r15)
            goto L_0x01f3
        L_0x01de:
            gnu.math.IntNum r5 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r7)
            gnu.math.IntNum r14 = gnu.kawa.functions.Arithmetic.asIntNum((java.lang.Object) r24)
            gnu.math.RatNum r7 = gnu.math.RatNum.make(r5, r14)
            boolean r5 = r7 instanceof gnu.math.IntNum
            if (r5 == 0) goto L_0x01f0
            r5 = 4
            goto L_0x01f1
        L_0x01f0:
            r5 = 6
        L_0x01f1:
            r8 = r5
            r11 = r8
        L_0x01f3:
            r14 = r1
            goto L_0x02af
        L_0x01f6:
            r25 = 0
            r1 = r3
            r2 = r4
            long r3 = gnu.kawa.functions.Arithmetic.asLong(r7)
            long r12 = gnu.kawa.functions.Arithmetic.asLong(r24)
            int r5 = r0.op
            switch(r5) {
                case 8: goto L_0x0209;
                default: goto L_0x0207;
            }
        L_0x0207:
            long r3 = r3 / r12
            goto L_0x020b
        L_0x0209:
            long r3 = r3 % r12
        L_0x020b:
            java.lang.Long r7 = java.lang.Long.valueOf(r3)
            r14 = r12
            r12 = r3
            r3 = r1
            r4 = r2
            goto L_0x02af
        L_0x0215:
            r25 = 0
            int r1 = gnu.kawa.functions.Arithmetic.asInt(r7)
            int r2 = gnu.kawa.functions.Arithmetic.asInt(r24)
            int r3 = r0.op
            switch(r3) {
                case 8: goto L_0x0226;
                default: goto L_0x0224;
            }
        L_0x0224:
            int r1 = r1 / r2
            goto L_0x0228
        L_0x0226:
            int r1 = r1 % r2
        L_0x0228:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)
            r3 = r1
            r4 = r2
            goto L_0x02af
        L_0x0230:
            gnu.math.Numeric r3 = r2.toInexact()
        L_0x0234:
            return r3
        L_0x0235:
            gnu.math.Numeric r3 = r2.div(r1)
            int r4 = r0.op
            r30 = r5
            r5 = 8
            if (r4 != r5) goto L_0x0254
            r4 = r3
            gnu.math.RealNum r4 = (gnu.math.RealNum) r4
            int r5 = r33.getRoundingMode()
            gnu.math.RealNum r4 = r4.toInt(r5)
            gnu.math.Numeric r4 = r4.mul(r1)
            gnu.math.Numeric r3 = r2.sub(r4)
        L_0x0254:
            int r4 = r0.op
            switch(r4) {
                case 5: goto L_0x029d;
                case 6: goto L_0x0285;
                case 7: goto L_0x0269;
                default: goto L_0x0259;
            }
        L_0x0259:
            r7 = r3
            r18 = r17
            r3 = r27
            r4 = r29
            r17 = r30
            r31 = r19
            r19 = r16
            r16 = r31
            goto L_0x02af
        L_0x0269:
            r4 = r3
            gnu.math.RealNum r4 = (gnu.math.RealNum) r4
            int r5 = r0.rounding_mode
            gnu.math.IntNum r7 = r4.toExactInt((int) r5)
            r4 = 4
            r5 = r4
            r11 = r4
            r8 = r5
            r18 = r17
            r3 = r27
            r4 = r29
            r17 = r30
            r31 = r19
            r19 = r16
            r16 = r31
            goto L_0x02af
        L_0x0285:
            r4 = r3
            gnu.math.RealNum r4 = (gnu.math.RealNum) r4
            int r5 = r0.rounding_mode
            gnu.math.RealNum r7 = r4.toInt(r5)
            r18 = r17
            r3 = r27
            r4 = r29
            r17 = r30
            r31 = r19
            r19 = r16
            r16 = r31
            goto L_0x02af
        L_0x029d:
            gnu.math.Numeric r7 = r3.toInexact()
            r18 = r17
            r3 = r27
            r4 = r29
            r17 = r30
            r31 = r19
            r19 = r16
            r16 = r31
        L_0x02af:
            if (r8 == r11) goto L_0x02e2
            switch(r8) {
                case 1: goto L_0x02d9;
                case 2: goto L_0x02cf;
                case 3: goto L_0x02c9;
                case 4: goto L_0x02b4;
                case 5: goto L_0x02b4;
                case 6: goto L_0x02b4;
                case 7: goto L_0x02bf;
                case 8: goto L_0x02b5;
                default: goto L_0x02b4;
            }
        L_0x02b4:
            goto L_0x02e2
        L_0x02b5:
            double r1 = r7.doubleValue()
            java.lang.Double r1 = java.lang.Double.valueOf(r1)
            r7 = r1
            goto L_0x02e2
        L_0x02bf:
            float r1 = r7.floatValue()
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r7 = r1
            goto L_0x02e2
        L_0x02c9:
            java.math.BigInteger r1 = gnu.kawa.functions.Arithmetic.asBigInteger(r7)
            r7 = r1
            goto L_0x02e2
        L_0x02cf:
            long r1 = r7.longValue()
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            r7 = r1
            goto L_0x02e2
        L_0x02d9:
            int r1 = r7.intValue()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r7 = r1
        L_0x02e2:
            int r9 = r9 + 1
            r1 = r34
            r2 = r28
            r5 = 1
            goto L_0x0038
        L_0x02eb:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.DivideOp.applyN(java.lang.Object[]):java.lang.Object");
    }

    public int numArgs() {
        return this.op == 4 ? -4095 : 8194;
    }
}
