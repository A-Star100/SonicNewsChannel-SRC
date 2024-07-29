package gnu.kawa.xml;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.internal.view.SupportMenu;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.kawa.functions.Arithmetic;
import gnu.math.IntNum;
import gnu.math.RealNum;
import java.math.BigDecimal;

public class XIntegerType extends XDataType {
    public static final XIntegerType byteType;
    public static final XIntegerType intType;
    public static final XIntegerType integerType;
    public static final XIntegerType longType;
    public static final XIntegerType negativeIntegerType;
    public static final XIntegerType nonNegativeIntegerType;
    public static final XIntegerType nonPositiveIntegerType;
    public static final XIntegerType positiveIntegerType;
    public static final XIntegerType shortType;
    static ClassType typeIntNum = ClassType.make("gnu.math.IntNum");
    public static final XIntegerType unsignedByteType;
    public static final XIntegerType unsignedIntType;
    public static final XIntegerType unsignedLongType;
    public static final XIntegerType unsignedShortType;
    boolean isUnsignedType;
    public final IntNum maxValue;
    public final IntNum minValue;

    static {
        XIntegerType xIntegerType = new XIntegerType(PropertyTypeConstants.PROPERTY_TYPE_INTEGER, decimalType, 5, (IntNum) null, (IntNum) null);
        integerType = xIntegerType;
        XIntegerType xIntegerType2 = new XIntegerType("long", (XDataType) xIntegerType, 8, IntNum.make(Long.MIN_VALUE), IntNum.make(Long.MAX_VALUE));
        longType = xIntegerType2;
        XIntegerType xIntegerType3 = new XIntegerType("int", (XDataType) xIntegerType2, 9, IntNum.make(Integer.MIN_VALUE), IntNum.make((int) ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
        intType = xIntegerType3;
        XIntegerType xIntegerType4 = new XIntegerType("short", (XDataType) xIntegerType3, 10, IntNum.make(-32768), IntNum.make(32767));
        shortType = xIntegerType4;
        byteType = new XIntegerType("byte", (XDataType) xIntegerType4, 11, IntNum.make(-128), IntNum.make(127));
        XIntegerType xIntegerType5 = xIntegerType;
        XIntegerType xIntegerType6 = new XIntegerType("nonPositiveInteger", (XDataType) xIntegerType5, 6, (IntNum) null, IntNum.zero());
        nonPositiveIntegerType = xIntegerType6;
        negativeIntegerType = new XIntegerType("negativeInteger", (XDataType) xIntegerType6, 7, (IntNum) null, IntNum.minusOne());
        XIntegerType xIntegerType7 = new XIntegerType("nonNegativeInteger", (XDataType) xIntegerType5, 12, IntNum.zero(), (IntNum) null);
        nonNegativeIntegerType = xIntegerType7;
        XIntegerType xIntegerType8 = new XIntegerType("unsignedLong", (XDataType) xIntegerType7, 13, IntNum.zero(), IntNum.valueOf("18446744073709551615"));
        unsignedLongType = xIntegerType8;
        XIntegerType xIntegerType9 = new XIntegerType("unsignedInt", (XDataType) xIntegerType8, 14, IntNum.zero(), IntNum.make(4294967295L));
        unsignedIntType = xIntegerType9;
        XIntegerType xIntegerType10 = new XIntegerType("unsignedShort", (XDataType) xIntegerType9, 15, IntNum.zero(), IntNum.make((int) SupportMenu.USER_MASK));
        unsignedShortType = xIntegerType10;
        unsignedByteType = new XIntegerType("unsignedByte", (XDataType) xIntegerType10, 16, IntNum.zero(), IntNum.make(255));
        positiveIntegerType = new XIntegerType("positiveInteger", (XDataType) xIntegerType7, 17, IntNum.one(), (IntNum) null);
    }

    public boolean isUnsignedType() {
        return this.isUnsignedType;
    }

    public XIntegerType(String name, XDataType base, int typeCode, IntNum min, IntNum max) {
        this((Object) name, base, typeCode, min, max);
        this.isUnsignedType = name.startsWith("unsigned");
    }

    public XIntegerType(Object name, XDataType base, int typeCode, IntNum min, IntNum max) {
        super(name, typeIntNum, typeCode);
        this.minValue = min;
        this.maxValue = max;
        this.baseType = base;
    }

    public boolean isInstance(Object obj) {
        if (!(obj instanceof IntNum)) {
            return false;
        }
        XDataType objType = integerType;
        if (this == objType) {
            return true;
        }
        if (obj instanceof XInteger) {
            objType = ((XInteger) obj).getIntegerType();
        }
        while (objType != null) {
            if (objType == this) {
                return true;
            }
            objType = objType.baseType;
        }
        return false;
    }

    public Object coerceFromObject(Object obj) {
        return valueOf((IntNum) obj);
    }

    public IntNum valueOf(IntNum value) {
        IntNum intNum;
        if (this == integerType) {
            return value;
        }
        IntNum intNum2 = this.minValue;
        if ((intNum2 == null || IntNum.compare(value, intNum2) >= 0) && ((intNum = this.maxValue) == null || IntNum.compare(value, intNum) <= 0)) {
            return new XInteger(value, this);
        }
        throw new ClassCastException("cannot cast " + value + " to " + this.name);
    }

    public Object cast(Object value) {
        if (value instanceof Boolean) {
            return valueOf(((Boolean) value).booleanValue() ? IntNum.one() : IntNum.zero());
        } else if (value instanceof IntNum) {
            return valueOf((IntNum) value);
        } else {
            if (value instanceof BigDecimal) {
                return valueOf(Arithmetic.asIntNum((BigDecimal) value));
            }
            if (value instanceof RealNum) {
                return valueOf(((RealNum) value).toExactInt(3));
            }
            if (value instanceof Number) {
                return valueOf(RealNum.toExactInt(((Number) value).doubleValue(), 3));
            }
            return super.cast(value);
        }
    }

    public Object valueOf(String value) {
        return valueOf(IntNum.valueOf(value.trim(), 10));
    }

    public IntNum valueOf(String value, int radix) {
        return valueOf(IntNum.valueOf(value.trim(), radix));
    }
}
