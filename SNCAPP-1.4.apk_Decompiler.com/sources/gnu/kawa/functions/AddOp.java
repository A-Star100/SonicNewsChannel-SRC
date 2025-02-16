package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.RatNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public class AddOp extends ArithOp {
    public static final AddOp $Mn = new AddOp("-", -1);
    public static final AddOp $Pl = new AddOp("+", 1);
    int plusOrMinus;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AddOp(String name, int plusOrMinus2) {
        super(name, plusOrMinus2 > 0 ? 1 : 2);
        this.plusOrMinus = 1;
        this.plusOrMinus = plusOrMinus2;
        Procedure.compilerKey.set(this, plusOrMinus2 > 0 ? "gnu.kawa.functions.CompileArith:$Pl" : "gnu.kawa.functions.CompileArith:$Mn");
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
    }

    public static Object apply2(int plusOrMinus2, Object arg1, Object arg2) {
        int i = plusOrMinus2;
        int code1 = Arithmetic.classifyValue(arg1);
        int code2 = Arithmetic.classifyValue(arg2);
        int code = code1 < code2 ? code2 : code1;
        switch (code) {
            case 1:
                int i2 = code2;
                int i3 = code;
                int i1 = Arithmetic.asInt(arg1);
                int i22 = Arithmetic.asInt(arg2);
                return new Integer(i > 0 ? i1 + i22 : i1 - i22);
            case 2:
                int i4 = code2;
                int i5 = code;
                long l1 = Arithmetic.asLong(arg1);
                long l2 = Arithmetic.asLong(arg2);
                return new Long(i > 0 ? l1 + l2 : l1 - l2);
            case 3:
                int i6 = code2;
                int i7 = code;
                BigInteger bi1 = Arithmetic.asBigInteger(arg1);
                BigInteger bi2 = Arithmetic.asBigInteger(arg2);
                return i > 0 ? bi1.add(bi2) : bi1.subtract(bi2);
            case 4:
                int i8 = code2;
                int i9 = code;
                return IntNum.add(Arithmetic.asIntNum(arg1), Arithmetic.asIntNum(arg2), i);
            case 5:
                int i10 = code2;
                int i11 = code;
                BigDecimal bd1 = Arithmetic.asBigDecimal(arg1);
                BigDecimal bd2 = Arithmetic.asBigDecimal(arg2);
                return i > 0 ? bd1.add(bd2) : bd1.subtract(bd2);
            case 6:
                int i12 = code2;
                int i13 = code;
                return RatNum.add(Arithmetic.asRatNum(arg1), Arithmetic.asRatNum(arg2), i);
            case 7:
                int i14 = code2;
                int i15 = code;
                float f1 = Arithmetic.asFloat(arg1);
                float f2 = Arithmetic.asFloat(arg2);
                return new Float(i > 0 ? f1 + f2 : f1 - f2);
            case 8:
                int i16 = code2;
                int i17 = code;
                double d1 = Arithmetic.asDouble(arg1);
                double d2 = Arithmetic.asDouble(arg2);
                return new Double(i > 0 ? d1 + d2 : d1 - d2);
            case 9:
                double d12 = Arithmetic.asDouble(arg1);
                double d22 = Arithmetic.asDouble(arg2);
                int i18 = code1;
                int i19 = code2;
                int i20 = code;
                return new DFloNum(i > 0 ? d12 + d22 : d12 - d22);
            default:
                int i21 = code1;
                int i23 = code2;
                int i24 = code;
                return Arithmetic.asNumeric(arg1).add(Arithmetic.asNumeric(arg2), i);
        }
    }

    public static Object $Pl(Object arg1, Object arg2) {
        return apply2(1, arg1, arg2);
    }

    public static Object $Mn(Object arg1, Object arg2) {
        return apply2(-1, arg1, arg2);
    }

    public static Object $Mn(Object arg1) {
        switch (Arithmetic.classifyValue(arg1)) {
            case 1:
                return new Integer(-Arithmetic.asInt(arg1));
            case 2:
                return new Long(-Arithmetic.asLong(arg1));
            case 3:
                return Arithmetic.asBigInteger(arg1).negate();
            case 4:
                return IntNum.neg(Arithmetic.asIntNum(arg1));
            case 5:
                return Arithmetic.asBigDecimal(arg1).negate();
            case 6:
                return RatNum.neg(Arithmetic.asRatNum(arg1));
            case 7:
                return new Float(-Arithmetic.asFloat(arg1));
            case 8:
                return new Double(-Arithmetic.asDouble(arg1));
            case 9:
                return new DFloNum(-Arithmetic.asDouble(arg1));
            default:
                return Arithmetic.asNumeric(arg1).neg();
        }
    }

    public static Object $Pl$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        return applyN(1, apply2(1, apply2(1, arg1, arg2), arg3), rest);
    }

    public static Object $Mn$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        return applyN(-1, apply2(-1, apply2(-1, arg1, arg2), arg3), rest);
    }

    public static Object applyN(int plusOrMinus2, Object[] args) {
        int len = args.length;
        if (len == 0) {
            return IntNum.zero();
        }
        Object result = args[0];
        if (len == 1 && plusOrMinus2 < 0) {
            return $Mn(result);
        }
        for (int i = 1; i < len; i++) {
            result = apply2(plusOrMinus2, result, args[i]);
        }
        return result;
    }

    public static Object applyN(int plusOrMinus2, Object init, Object[] args) {
        Object result = init;
        for (Object apply2 : args) {
            result = apply2(plusOrMinus2, result, apply2);
        }
        return result;
    }

    public Object applyN(Object[] args) {
        return applyN(this.plusOrMinus, args);
    }
}
