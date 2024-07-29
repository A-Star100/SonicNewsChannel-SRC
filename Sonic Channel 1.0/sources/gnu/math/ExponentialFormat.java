package gnu.math;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ExponentialFormat extends Format {
    static final double LOG10 = Math.log(10.0d);
    public int expDigits;
    public char exponentChar = 'E';
    public boolean exponentShowSign;
    public int fracDigits = -1;
    public boolean general;
    public int intDigits;
    public char overflowChar;
    public char padChar;
    public boolean showPlus;
    public int width;

    static boolean addOne(StringBuffer sbuf, int digStart, int digEnd) {
        int j = digEnd;
        while (j != digStart) {
            j--;
            char ch = sbuf.charAt(j);
            if (ch != '9') {
                sbuf.setCharAt(j, (char) (ch + 1));
                return false;
            }
            sbuf.setCharAt(j, '0');
        }
        sbuf.insert(j, '1');
        return true;
    }

    public StringBuffer format(float value, StringBuffer sbuf, FieldPosition fpos) {
        return format((double) value, this.fracDigits < 0 ? Float.toString(value) : null, sbuf, fpos);
    }

    public StringBuffer format(double value, StringBuffer sbuf, FieldPosition fpos) {
        return format(value, this.fracDigits < 0 ? Double.toString(value) : null, sbuf, fpos);
    }

    /* access modifiers changed from: package-private */
    public StringBuffer format(double value, String dstr, StringBuffer sbuf, FieldPosition fpos) {
        int exponent;
        int scale;
        int digits;
        int ee;
        int dot;
        int i;
        int j;
        int d;
        int digits2;
        String dstr2;
        int log;
        double value2 = value;
        StringBuffer stringBuffer = sbuf;
        int k = this.intDigits;
        int n = this.fracDigits;
        boolean negative = value2 < 0.0d;
        if (negative) {
            value2 = -value2;
        }
        int oldLen = sbuf.length();
        int signLen = 1;
        if (negative) {
            if (n >= 0) {
                stringBuffer.append('-');
            }
        } else if (this.showPlus) {
            stringBuffer.append('+');
        } else {
            signLen = 0;
        }
        int digStart = sbuf.length();
        boolean nonFinite = Double.isNaN(value2) || Double.isInfinite(value2);
        if (n < 0 || nonFinite) {
            if (dstr == null) {
                dstr2 = Double.toString(value2);
            } else {
                dstr2 = dstr;
            }
            int indexE = dstr2.indexOf(69);
            if (indexE >= 0) {
                stringBuffer.append(dstr2);
                int indexE2 = indexE + digStart;
                boolean negexp = dstr2.charAt(indexE2 + 1) == '-';
                int exponent2 = 0;
                int i2 = indexE2 + (negexp ? 2 : 1);
                while (true) {
                    double value3 = value2;
                    if (i2 >= sbuf.length()) {
                        break;
                    }
                    exponent2 = (exponent2 * 10) + (stringBuffer.charAt(i2) - '0');
                    i2++;
                    value2 = value3;
                }
                if (negexp) {
                    exponent2 = -exponent2;
                }
                stringBuffer.setLength(indexE2);
                exponent = exponent2;
            } else {
                exponent = RealNum.toStringScientific(dstr2, stringBuffer);
            }
            if (negative) {
                digStart++;
            }
            stringBuffer.deleteCharAt(digStart + 1);
            digits = sbuf.length() - digStart;
            if (digits > 1 && stringBuffer.charAt((digStart + digits) - 1) == '0') {
                digits--;
                stringBuffer.setLength(digStart + digits);
            }
            scale = (digits - exponent) - 1;
        } else {
            int digits3 = n + (k > 0 ? 1 : k);
            int log2 = (int) ((Math.log(value2) / LOG10) + 1000.0d);
            if (log2 == Integer.MIN_VALUE) {
                log = 0;
            } else {
                log = log2 - 1000;
            }
            scale = (digits3 - log) - 1;
            RealNum.toScaledInt(value2, scale).format(10, stringBuffer);
            exponent = (digits3 - 1) - scale;
            String str = dstr;
            double d2 = value2;
            digits = digits3;
        }
        int exponent3 = exponent - (k - 1);
        int exponentAbs = exponent3 < 0 ? -exponent3 : exponent3;
        int exponentLen = exponentAbs >= 1000 ? 4 : exponentAbs >= 100 ? 3 : exponentAbs >= 10 ? 2 : 1;
        int i3 = this.expDigits;
        if (i3 > exponentLen) {
            exponentLen = this.expDigits;
        }
        boolean showExponent = true;
        boolean z = negative;
        boolean negative2 = this.general;
        int ee2 = !negative2 ? 0 : i3 > 0 ? i3 + 2 : 4;
        boolean fracUnspecified = n < 0;
        if (negative2 || fracUnspecified) {
            int d3 = n;
            int n2 = digits - scale;
            if (fracUnspecified) {
                ee = ee2;
                d = 7;
                if (n2 < 7) {
                    d = n2;
                }
                if (digits > d) {
                    d = digits;
                }
            } else {
                ee = ee2;
                d = d3;
            }
            int dd = d - n2;
            if (negative2 && n2 >= 0 && dd >= 0) {
                digits = d;
                k = n2;
                showExponent = false;
                n = d;
            } else if (fracUnspecified) {
                int i4 = this.width;
                if (i4 <= 0) {
                    digits2 = d;
                } else {
                    digits2 = ((i4 - signLen) - exponentLen) - 3;
                    if (k < 0) {
                        digits2 -= k;
                    }
                    if (digits2 > d) {
                        digits2 = d;
                    }
                }
                if (digits <= 0) {
                    digits = 1;
                    n = d;
                } else {
                    n = d;
                }
            } else {
                n = d;
            }
        } else {
            ee = ee2;
        }
        int digEnd = digStart + digits;
        while (sbuf.length() < digEnd) {
            stringBuffer.append('0');
        }
        int i5 = digits;
        boolean addOne = (digEnd == sbuf.length() ? '0' : stringBuffer.charAt(digEnd)) >= '5';
        if (addOne && addOne(stringBuffer, digStart, digEnd)) {
            scale++;
        }
        int scale2 = scale - (sbuf.length() - digEnd);
        stringBuffer.setLength(digEnd);
        int dot2 = digStart;
        if (k < 0) {
            int j2 = k;
            while (true) {
                j2++;
                if (j2 > 0) {
                    break;
                }
                stringBuffer.insert(digStart, '0');
                addOne = addOne;
            }
            dot = dot2;
        } else {
            while (digStart + k > digEnd) {
                stringBuffer.append('0');
                digEnd++;
            }
            dot = dot2 + k;
        }
        if (nonFinite) {
            showExponent = false;
            int i6 = n;
        } else {
            int i7 = n;
            stringBuffer.insert(dot, '.');
        }
        if (showExponent) {
            stringBuffer.append(this.exponentChar);
            if (this.exponentShowSign || exponent3 < 0) {
                stringBuffer.append(exponent3 >= 0 ? '+' : '-');
            }
            int i8 = sbuf.length();
            stringBuffer.append(exponentAbs);
            int newLen = sbuf.length();
            int i9 = exponentAbs;
            int j3 = this.expDigits - (newLen - i8);
            if (j3 > 0) {
                int newLen2 = newLen + j3;
                while (true) {
                    j = j3 - 1;
                    if (j < 0) {
                        break;
                    }
                    stringBuffer.insert(i8, '0');
                    j3 = j;
                }
            }
        } else {
            exponentLen = 0;
        }
        int newLen3 = sbuf.length();
        int used = newLen3 - oldLen;
        int i10 = newLen3;
        int i11 = this.width - used;
        if (fracUnspecified) {
            int i12 = used;
            int i13 = digEnd;
            if ((dot + 1 == sbuf.length() || stringBuffer.charAt(dot + 1) == this.exponentChar) && (this.width <= 0 || i11 > 0)) {
                i11--;
                stringBuffer.insert(dot + 1, '0');
            }
        } else {
            int i14 = digEnd;
        }
        if ((i11 >= 0 || this.width <= 0) && (!showExponent || exponentLen <= (i = this.expDigits) || i <= 0 || this.overflowChar == 0)) {
            if (k <= 0 && (i11 > 0 || this.width <= 0)) {
                stringBuffer.insert(digStart, '0');
                i11--;
            }
            if (!showExponent && this.width > 0) {
                int ee3 = ee;
                while (true) {
                    ee3--;
                    if (ee3 < 0) {
                        break;
                    }
                    stringBuffer.append(' ');
                    i11--;
                }
            }
            while (true) {
                i11--;
                if (i11 < 0) {
                    break;
                }
                stringBuffer.insert(oldLen, this.padChar);
            }
        } else if (this.overflowChar != 0) {
            stringBuffer.setLength(oldLen);
            int i15 = this.width;
            while (true) {
                i15--;
                if (i15 < 0) {
                    break;
                }
                stringBuffer.append(this.overflowChar);
            }
            int i16 = ee;
        } else {
            int i17 = ee;
        }
        return stringBuffer;
    }

    public StringBuffer format(long num, StringBuffer sbuf, FieldPosition fpos) {
        return format((double) num, sbuf, fpos);
    }

    public StringBuffer format(Object num, StringBuffer sbuf, FieldPosition fpos) {
        return format(((RealNum) num).doubleValue(), sbuf, fpos);
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parse - not implemented");
    }

    public Object parseObject(String text, ParsePosition status) {
        throw new Error("ExponentialFormat.parseObject - not implemented");
    }
}
