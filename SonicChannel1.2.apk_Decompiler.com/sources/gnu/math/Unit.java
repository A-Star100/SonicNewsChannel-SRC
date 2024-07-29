package gnu.math;

public abstract class Unit extends Quantity {
    public static BaseUnit Empty = new BaseUnit();
    public static double NON_COMBINABLE = 0.0d;
    public static final Unit cm;
    public static final NamedUnit date;
    public static final BaseUnit duration;
    public static final BaseUnit gram = new BaseUnit("g", "Mass");
    public static final Unit hour;
    public static final Unit in;
    public static final BaseUnit meter;
    public static final Unit minute;
    public static final Unit mm;
    public static final NamedUnit month;
    public static final Unit pica;
    public static final Unit pt;
    public static final Unit radian = define("rad", 1.0d, Empty);
    public static final NamedUnit second;
    static NamedUnit[] table = new NamedUnit[100];
    Unit base;
    Dimensions dims;
    double factor = 1.0d;
    MulUnit products;

    static {
        Dimensions.Empty.bases[0] = Empty;
        BaseUnit baseUnit = new BaseUnit("m", "Length");
        meter = baseUnit;
        BaseUnit baseUnit2 = new BaseUnit("duration", "Time");
        duration = baseUnit2;
        Unit define = define("cm", 0.01d, baseUnit);
        cm = define;
        mm = define("mm", 0.1d, define);
        in = define("in", 0.0254d, baseUnit);
        pt = define("pt", 3.527778E-4d, baseUnit);
        pica = define("pica", 0.004233333d, baseUnit);
        date = new NamedUnit("date", NON_COMBINABLE, baseUnit2);
        NamedUnit namedUnit = new NamedUnit("s", NON_COMBINABLE, baseUnit2);
        second = namedUnit;
        month = new NamedUnit("month", NON_COMBINABLE, baseUnit2);
        Unit define2 = define("min", 60.0d, namedUnit);
        minute = define2;
        hour = define("hour", 60.0d, define2);
    }

    public final Dimensions dimensions() {
        return this.dims;
    }

    public final double doubleValue() {
        return this.factor;
    }

    public int hashCode() {
        return this.dims.hashCode();
    }

    public String getName() {
        return null;
    }

    static Unit times(Unit unit1, int power1, BaseUnit unit2, int power2) {
        if (unit1 == unit2) {
            power1 += power2;
            unit2 = Empty;
            power2 = 0;
        }
        if (power1 == 0 || unit1 == Empty) {
            unit1 = unit2;
            power1 = power2;
            unit2 = Empty;
            power2 = 0;
        }
        if (power2 == 0 || unit2 == Empty) {
            if (power1 == 1) {
                return unit1;
            }
            if (power1 == 0) {
                return Empty;
            }
        }
        if (unit1 instanceof MulUnit) {
            MulUnit munit1 = (MulUnit) unit1;
            if (munit1.unit1 == unit2) {
                return times(unit2, (munit1.power1 * power1) + power2, munit1.unit2, munit1.power2 * power1);
            }
            if (munit1.unit2 == unit2) {
                return times(munit1.unit1, munit1.power1 * power1, unit2, (munit1.power2 * power1) + power2);
            }
            if (unit2 instanceof MulUnit) {
                MulUnit munit2 = (MulUnit) unit2;
                if (munit1.unit1 == munit2.unit1 && munit1.unit2 == munit2.unit2) {
                    return times(munit1.unit1, (munit1.power1 * power1) + (munit2.power1 * power2), munit1.unit2, (munit1.power2 * power1) + (munit2.power2 * power2));
                }
                if (munit1.unit1 == munit2.unit2 && munit1.unit2 == munit2.unit1) {
                    return times(munit1.unit1, (munit1.power1 * power1) + (munit2.power2 * power2), munit1.unit2, (munit1.power2 * power1) + (munit2.power1 * power2));
                }
            }
        }
        if (unit2 instanceof MulUnit) {
            MulUnit munit22 = (MulUnit) unit2;
            if (munit22.unit1 == unit1) {
                return times(unit1, (munit22.power1 * power2) + power1, munit22.unit2, munit22.power2 * power2);
            }
            if (munit22.unit2 == unit1) {
                return times(munit22.unit1, munit22.power1 * power2, unit1, (munit22.power2 * power2) + power1);
            }
        }
        return MulUnit.make(unit1, power1, unit2, power2);
    }

    public static Unit times(Unit unit1, Unit unit2) {
        return times(unit1, 1, unit2, 1);
    }

    public static Unit divide(Unit unit1, Unit unit2) {
        return times(unit1, 1, unit2, -1);
    }

    public static Unit pow(Unit unit, int power) {
        return times(unit, power, Empty, 0);
    }

    Unit() {
    }

    public static NamedUnit make(String name, Quantity value) {
        return NamedUnit.make(name, value);
    }

    public static Unit define(String name, DQuantity value) {
        return new NamedUnit(name, value);
    }

    public static Unit define(String name, double factor2, Unit base2) {
        return new NamedUnit(name, factor2, base2);
    }

    public Complex number() {
        return DFloNum.one();
    }

    public boolean isExact() {
        return false;
    }

    public final boolean isZero() {
        return false;
    }

    public Numeric power(IntNum y) {
        if (y.words == null) {
            return pow(this, y.ival);
        }
        throw new ArithmeticException("Unit raised to bignum power");
    }

    public Unit sqrt() {
        if (this == Empty) {
            return this;
        }
        throw new RuntimeException("unimplemented Unit.sqrt");
    }

    public static NamedUnit lookup(String name) {
        return NamedUnit.lookup(name);
    }

    public String toString(double val) {
        String str = Double.toString(val);
        if (this == Empty) {
            return str;
        }
        return str + toString();
    }

    public String toString(RealNum val) {
        return toString(val.doubleValue());
    }

    public String toString() {
        String name = getName();
        if (name != null) {
            return name;
        }
        if (this == Empty) {
            return "unit";
        }
        return Double.toString(this.factor) + "<unnamed unit>";
    }

    public Unit unit() {
        return this;
    }
}
