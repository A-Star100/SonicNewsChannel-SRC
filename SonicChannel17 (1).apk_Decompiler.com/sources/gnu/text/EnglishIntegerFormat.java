package gnu.text;

import gnu.lists.Consumer;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class EnglishIntegerFormat extends NumberFormat {
    private static EnglishIntegerFormat cardinalEnglish;
    public static final String[] ones = {null, "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    public static final String[] onesth = {null, "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelveth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth"};
    private static EnglishIntegerFormat ordinalEnglish;
    public static final String[] power1000s = {null, " thousand", " million", " billion", " trillion", " quadrillion", " quintillion", " sextillion", " septillion", " octillion", " nonillion", " decillion", " undecillion", " duodecillion", " tredecillion", " quattuordecillion", " quindecillion", " sexdecillion", " septendecillion", " octodecillion", " novemdecillion", " vigintillion"};
    public static final String[] tens = {null, null, "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    public static final String[] tensth = {null, null, "twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth"};
    public boolean ordinal;

    public EnglishIntegerFormat(boolean ordinal2) {
        this.ordinal = ordinal2;
    }

    public static EnglishIntegerFormat getInstance(boolean ordinal2) {
        if (ordinal2) {
            if (ordinalEnglish == null) {
                ordinalEnglish = new EnglishIntegerFormat(true);
            }
            return ordinalEnglish;
        }
        if (cardinalEnglish == null) {
            cardinalEnglish = new EnglishIntegerFormat(false);
        }
        return cardinalEnglish;
    }

    /* access modifiers changed from: package-private */
    public void format999(StringBuffer sbuf, int num, boolean ordinal2) {
        if (num >= 100) {
            int num100s = num / 100;
            num %= 100;
            if (num100s > 1) {
                sbuf.append(ones[num100s]);
                sbuf.append(' ');
            }
            sbuf.append("hundred");
            if (num > 0) {
                sbuf.append(' ');
            } else if (ordinal2) {
                sbuf.append("th");
            }
        }
        if (num >= 20) {
            int num10s = num / 10;
            num %= 10;
            sbuf.append(((!ordinal2 || num != 0) ? tens : tensth)[num10s]);
            if (num > 0) {
                sbuf.append('-');
            }
        }
        if (num > 0) {
            sbuf.append((ordinal2 ? onesth : ones)[num]);
        }
    }

    /* access modifiers changed from: package-private */
    public void format(StringBuffer sbuf, long num, int exp1000, boolean ordinal2) {
        long num2;
        StringBuffer stringBuffer = sbuf;
        int i = exp1000;
        if (num >= 1000) {
            format(sbuf, num / 1000, i + 1, false);
            long num3 = num % 1000;
            if (num3 > 0) {
                sbuf.append(", ");
            } else if (ordinal2) {
                sbuf.append("th");
            }
            num2 = num3;
        } else {
            num2 = num;
        }
        if (num2 > 0) {
            format999(sbuf, (int) num2, ordinal2 && i == 0);
            String[] strArr = power1000s;
            if (i >= strArr.length) {
                sbuf.append(" times ten to the ");
                format(sbuf, (long) (i * 3), 0, true);
                sbuf.append(" power");
            } else if (i > 0) {
                sbuf.append(strArr[i]);
            }
        }
    }

    public void writeInt(int value, Consumer out) {
        writeLong((long) value, out);
    }

    public void writeLong(long value, Consumer out) {
        StringBuffer sbuf = new StringBuffer();
        format(value, sbuf, (FieldPosition) null);
        out.write((CharSequence) sbuf, 0, sbuf.length());
    }

    public void writeObject(Object value, Consumer out) {
        writeLong(((Number) value).longValue(), out);
    }

    public void writeBoolean(boolean value, Consumer out) {
        writeLong(value ? 1 : 0, out);
    }

    public StringBuffer format(long num, StringBuffer sbuf, FieldPosition fpos) {
        if (num == 0) {
            sbuf.append(this.ordinal ? "zeroth" : "zero");
        } else {
            if (num < 0) {
                sbuf.append("minus ");
                num = -num;
            }
            format(sbuf, num, 0, this.ordinal);
        }
        return sbuf;
    }

    public StringBuffer format(double num, StringBuffer sbuf, FieldPosition fpos) {
        long inum = (long) num;
        if (((double) inum) == num) {
            return format(inum, sbuf, fpos);
        }
        sbuf.append(Double.toString(num));
        return sbuf;
    }

    public Number parse(String text, ParsePosition status) {
        throw new Error("EnglishIntegerFormat.parseObject - not implemented");
    }
}
