package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MultiplyOp extends ArithOp {
    public static final MultiplyOp $St = new MultiplyOp("*");

    public MultiplyOp(String name) {
        super(name, 3);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forMul");
    }

    public Object defaultResult() {
        return IntNum.one();
    }

    public static Object apply(Object arg1, Object arg2) {
        return ((Numeric) arg1).mul(arg2);
    }

    public Object applyN(Object[] args) {
        int len;
        Object[] objArr = args;
        int len2 = objArr.length;
        if (len2 == 0) {
            return IntNum.one();
        }
        int i1 = 0;
        Number result = (Number) objArr[0];
        long l1 = 0;
        double d1 = 0.0d;
        BigInteger bi2 = null;
        BigInteger bi1 = null;
        BigDecimal bd1 = null;
        BigDecimal bd2 = null;
        int i = 1;
        int code = Arithmetic.classifyValue(result);
        Number result2 = result;
        int i2 = 0;
        while (i < len2) {
            Object arg2 = objArr[i];
            int code2 = Arithmetic.classifyValue(arg2);
            code = code < code2 ? code2 : code;
            switch (code) {
                case 1:
                    len = len2;
                    int i12 = Arithmetic.asInt(result2);
                    int i22 = Arithmetic.asInt(arg2);
                    i2 = i22;
                    result2 = new Integer(i12 * i22);
                    i1 = i12;
                    break;
                case 2:
                    len = len2;
                    int i13 = i1;
                    long l12 = Arithmetic.asLong(result2);
                    l1 = Arithmetic.asLong(arg2);
                    result2 = new Long(l12 * l1);
                    double d = l12;
                    i1 = i13;
                    d1 = d;
                    i2 = i2;
                    break;
                case 3:
                    len = len2;
                    long l2 = l1;
                    double l13 = d1;
                    BigInteger bi12 = Arithmetic.asBigInteger(result2);
                    long l22 = l2;
                    BigInteger bi22 = Arithmetic.asBigInteger(arg2);
                    result2 = bi12.multiply(bi22);
                    long j = l22;
                    bi2 = bi22;
                    bi1 = bi12;
                    d1 = l13;
                    l1 = j;
                    break;
                case 4:
                    len = len2;
                    BigInteger bigInteger = bi2;
                    BigInteger bigInteger2 = bi1;
                    result2 = IntNum.times(Arithmetic.asIntNum((Object) result2), Arithmetic.asIntNum(arg2));
                    break;
                case 5:
                    len = len2;
                    BigInteger bigInteger3 = bi2;
                    BigInteger bigInteger4 = bi1;
                    BigDecimal bd12 = Arithmetic.asBigDecimal(result2);
                    BigDecimal bd22 = Arithmetic.asBigDecimal(arg2);
                    result2 = bd12.multiply(bd22);
                    bd1 = bd12;
                    bd2 = bd22;
                    break;
                case 6:
                    len = len2;
                    BigInteger bi23 = bi2;
                    BigInteger bi13 = bi1;
                    BigDecimal bigDecimal = bd1;
                    BigDecimal bigDecimal2 = bd2;
                    result2 = RatNum.times(Arithmetic.asRatNum(result2), Arithmetic.asRatNum(arg2));
                    bi2 = bi23;
                    bi1 = bi13;
                    break;
                case 7:
                    len = len2;
                    BigInteger bi24 = bi2;
                    BigInteger bi14 = bi1;
                    long l23 = l1;
                    double l14 = d1;
                    BigDecimal bigDecimal3 = bd1;
                    BigDecimal bigDecimal4 = bd2;
                    float f1 = Arithmetic.asFloat(result2);
                    float f2 = Arithmetic.asFloat(arg2);
                    result2 = new Float(f1 * f2);
                    d1 = l14;
                    l1 = l23;
                    float f = f1;
                    float f3 = f2;
                    bi2 = bi24;
                    bi1 = bi14;
                    break;
                case 8:
                    len = len2;
                    BigInteger bi25 = bi2;
                    long l24 = l1;
                    double l15 = d1;
                    BigDecimal bigDecimal5 = bd1;
                    BigDecimal bigDecimal6 = bd2;
                    double d12 = Arithmetic.asDouble(result2);
                    double d2 = Arithmetic.asDouble(arg2);
                    result2 = new Double(d12 * d2);
                    i1 = i1;
                    l1 = l24;
                    double d3 = d2;
                    bi2 = bi25;
                    bi1 = bi1;
                    double d4 = l15;
                    double d5 = d12;
                    d1 = d4;
                    break;
                case 9:
                    int i3 = code2;
                    len = len2;
                    result2 = new DFloNum(Arithmetic.asDouble(result2) * Arithmetic.asDouble(arg2));
                    i1 = i1;
                    break;
                default:
                    int i4 = code2;
                    len = len2;
                    result2 = Arithmetic.asNumeric(result2).mul(Arithmetic.asNumeric(arg2));
                    bd1 = bd1;
                    bi2 = bi2;
                    bd2 = bd2;
                    bi1 = bi1;
                    break;
            }
            i++;
            objArr = args;
            len2 = len;
        }
        return result2;
    }
}
