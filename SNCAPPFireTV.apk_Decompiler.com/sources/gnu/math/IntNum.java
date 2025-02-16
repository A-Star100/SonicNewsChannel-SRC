package gnu.math;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class IntNum extends RatNum implements Externalizable {
    static final int maxFixNum = 1024;
    static final int minFixNum = -100;
    static final int numFixNum = 1125;
    static final IntNum[] smallFixNums = new IntNum[numFixNum];
    public int ival;
    public int[] words;

    static {
        int i = numFixNum;
        while (true) {
            i--;
            if (i >= 0) {
                smallFixNums[i] = new IntNum(i + minFixNum);
            } else {
                return;
            }
        }
    }

    public IntNum() {
    }

    public IntNum(int value) {
        this.ival = value;
    }

    public static IntNum make(int value) {
        if (value < minFixNum || value > 1024) {
            return new IntNum(value);
        }
        return smallFixNums[value + 100];
    }

    public static final IntNum zero() {
        return smallFixNums[100];
    }

    public static final IntNum one() {
        return smallFixNums[101];
    }

    public static final IntNum ten() {
        return smallFixNums[110];
    }

    public static IntNum minusOne() {
        return smallFixNums[99];
    }

    public static IntNum make(long value) {
        if (value >= -100 && value <= 1024) {
            return smallFixNums[((int) value) + 100];
        }
        int i = (int) value;
        if (((long) i) == value) {
            return new IntNum(i);
        }
        IntNum result = alloc(2);
        result.ival = 2;
        int[] iArr = result.words;
        iArr[0] = i;
        iArr[1] = (int) (value >> 32);
        return result;
    }

    public static IntNum asIntNumOrNull(Object value) {
        if (value instanceof IntNum) {
            return (IntNum) value;
        }
        if (value instanceof BigInteger) {
            return valueOf(value.toString(), 10);
        }
        if (!(value instanceof Number)) {
            return null;
        }
        if ((value instanceof Integer) || (value instanceof Long) || (value instanceof Short) || (value instanceof Byte)) {
            return make(((Number) value).longValue());
        }
        return null;
    }

    public static IntNum makeU(long value) {
        if (value >= 0) {
            return make(value);
        }
        IntNum result = alloc(3);
        result.ival = 3;
        int[] iArr = result.words;
        iArr[0] = (int) value;
        iArr[1] = (int) (value >> 32);
        iArr[2] = 0;
        return result;
    }

    public static IntNum make(int[] words2, int len) {
        if (words2 == null) {
            return make(len);
        }
        int len2 = wordsNeeded(words2, len);
        if (len2 <= 1) {
            return len2 == 0 ? zero() : make(words2[0]);
        }
        IntNum num = new IntNum();
        num.words = words2;
        num.ival = len2;
        return num;
    }

    public static IntNum make(int[] words2) {
        return make(words2, words2.length);
    }

    public static IntNum alloc(int nwords) {
        if (nwords <= 1) {
            return new IntNum();
        }
        IntNum result = new IntNum();
        result.words = new int[nwords];
        return result;
    }

    public void realloc(int nwords) {
        if (nwords == 0) {
            int[] iArr = this.words;
            if (iArr != null) {
                if (this.ival > 0) {
                    this.ival = iArr[0];
                }
                this.words = null;
                return;
            }
            return;
        }
        int[] iArr2 = this.words;
        if (iArr2 == null || iArr2.length < nwords || iArr2.length > nwords + 2) {
            int[] new_words = new int[nwords];
            if (iArr2 == null) {
                new_words[0] = this.ival;
                this.ival = 1;
            } else {
                if (nwords < this.ival) {
                    this.ival = nwords;
                }
                System.arraycopy(iArr2, 0, new_words, 0, this.ival);
            }
            this.words = new_words;
        }
    }

    public final IntNum numerator() {
        return this;
    }

    public final IntNum denominator() {
        return one();
    }

    public final boolean isNegative() {
        int[] iArr = this.words;
        return (iArr == null ? this.ival : iArr[this.ival - 1]) < 0;
    }

    public int sign() {
        int n = this.ival;
        int[] w = this.words;
        if (w != null) {
            int n2 = n - 1;
            int i = w[n2];
            if (i > 0) {
                return 1;
            }
            if (i < 0) {
                return -1;
            }
            while (n2 != 0) {
                n2--;
                if (w[n2] != 0) {
                    return 1;
                }
            }
            return 0;
        } else if (n > 0) {
            return 1;
        } else {
            if (n < 0) {
                return -1;
            }
            return 0;
        }
    }

    public static int compare(IntNum x, IntNum y) {
        boolean z = false;
        if (x.words == null && y.words == null) {
            int i = x.ival;
            int i2 = y.ival;
            if (i < i2) {
                return -1;
            }
            if (i > i2) {
                return 1;
            }
            return 0;
        }
        boolean x_negative = x.isNegative();
        if (x_negative == y.isNegative()) {
            int[] iArr = x.words;
            int x_len = iArr == null ? 1 : x.ival;
            int[] iArr2 = y.words;
            int y_len = iArr2 == null ? 1 : y.ival;
            if (x_len == y_len) {
                return MPN.cmp(iArr, iArr2, x_len);
            }
            if (x_len > y_len) {
                z = true;
            }
            if (z != x_negative) {
                return 1;
            }
            return -1;
        } else if (x_negative) {
            return -1;
        } else {
            return 1;
        }
    }

    public static int compare(IntNum x, long y) {
        long x_word;
        if (x.words == null) {
            x_word = (long) x.ival;
        } else {
            boolean x_negative = x.isNegative();
            if (x_negative == (y < 0)) {
                int[] iArr = x.words;
                int x_len = iArr == null ? 1 : x.ival;
                if (x_len == 1) {
                    x_word = (long) iArr[0];
                } else if (x_len == 2) {
                    x_word = x.longValue();
                } else if (x_negative) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (x_negative) {
                return -1;
            } else {
                return 1;
            }
        }
        if (x_word < y) {
            return -1;
        }
        return x_word > y ? 1 : 0;
    }

    public int compare(Object obj) {
        if (obj instanceof IntNum) {
            return compare(this, (IntNum) obj);
        }
        return ((RealNum) obj).compareReversed(this);
    }

    public final boolean isOdd() {
        int[] iArr = this.words;
        if (((iArr == null ? this.ival : iArr[0]) & 1) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isZero() {
        return this.words == null && this.ival == 0;
    }

    public final boolean isOne() {
        return this.words == null && this.ival == 1;
    }

    public final boolean isMinusOne() {
        return this.words == null && this.ival == -1;
    }

    public static int wordsNeeded(int[] words2, int len) {
        int i = len;
        if (i > 0) {
            i--;
            int word = words2[i];
            if (word != -1) {
                while (word == 0 && i > 0) {
                    int i2 = words2[i - 1];
                    word = i2;
                    if (i2 < 0) {
                        break;
                    }
                    i--;
                }
            } else {
                while (i > 0) {
                    int i3 = words2[i - 1];
                    int word2 = i3;
                    if (i3 >= 0) {
                        break;
                    }
                    i--;
                    if (word2 != -1) {
                        break;
                    }
                }
            }
        }
        return i + 1;
    }

    public IntNum canonicalize() {
        int i;
        int[] iArr = this.words;
        if (iArr != null) {
            int wordsNeeded = wordsNeeded(iArr, this.ival);
            this.ival = wordsNeeded;
            if (wordsNeeded <= 1) {
                if (wordsNeeded == 1) {
                    this.ival = this.words[0];
                }
                this.words = null;
            }
        }
        if (this.words != null || (i = this.ival) < minFixNum || i > 1024) {
            return this;
        }
        return smallFixNums[i - minFixNum];
    }

    public static final IntNum add(int x, int y) {
        return make(((long) x) + ((long) y));
    }

    public static IntNum add(IntNum x, int y) {
        if (x.words == null) {
            return add(x.ival, y);
        }
        IntNum result = new IntNum(0);
        result.setAdd(x, y);
        return result.canonicalize();
    }

    public void setAdd(IntNum x, int y) {
        if (x.words == null) {
            set(((long) x.ival) + ((long) y));
            return;
        }
        int len = x.ival;
        realloc(len + 1);
        long carry = (long) y;
        for (int i = 0; i < len; i++) {
            long carry2 = carry + (((long) x.words[i]) & 4294967295L);
            this.words[i] = (int) carry2;
            carry = carry2 >> 32;
        }
        if (x.words[len - 1] < 0) {
            carry--;
        }
        int[] iArr = this.words;
        iArr[len] = (int) carry;
        this.ival = wordsNeeded(iArr, len + 1);
    }

    public final void setAdd(int y) {
        setAdd(this, y);
    }

    public final void set(int y) {
        this.words = null;
        this.ival = y;
    }

    public final void set(long y) {
        int i = (int) y;
        if (((long) i) == y) {
            this.ival = i;
            this.words = null;
            return;
        }
        realloc(2);
        int[] iArr = this.words;
        iArr[0] = i;
        iArr[1] = (int) (y >> 32);
        this.ival = 2;
    }

    public final void set(int[] words2, int length) {
        this.ival = length;
        this.words = words2;
    }

    public final void set(IntNum y) {
        if (y.words == null) {
            set(y.ival);
        } else if (this != y) {
            realloc(y.ival);
            System.arraycopy(y.words, 0, this.words, 0, y.ival);
            this.ival = y.ival;
        }
    }

    public static IntNum add(IntNum x, IntNum y) {
        return add(x, y, 1);
    }

    public static IntNum sub(IntNum x, IntNum y) {
        return add(x, y, -1);
    }

    public static IntNum add(IntNum x, IntNum y, int k) {
        if (x.words == null && y.words == null) {
            return make((((long) k) * ((long) y.ival)) + ((long) x.ival));
        }
        if (k != 1) {
            if (k == -1) {
                y = neg(y);
            } else {
                y = times(y, make(k));
            }
        }
        if (x.words == null) {
            return add(y, x.ival);
        }
        if (y.words == null) {
            return add(x, y.ival);
        }
        if (y.ival > x.ival) {
            IntNum tmp = x;
            x = y;
            y = tmp;
        }
        IntNum result = alloc(x.ival + 1);
        int i = y.ival;
        long carry = (long) MPN.add_n(result.words, x.words, y.words, i);
        long y_ext = y.words[i + -1] < 0 ? 4294967295L : 0;
        while (i < x.ival) {
            long carry2 = carry + (((long) x.words[i]) & 4294967295L) + y_ext;
            result.words[i] = (int) carry2;
            carry = carry2 >>> 32;
            i++;
        }
        if (x.words[i - 1] < 0) {
            y_ext--;
        }
        result.words[i] = (int) (carry + y_ext);
        result.ival = i + 1;
        return result.canonicalize();
    }

    public static final IntNum times(int x, int y) {
        return make(((long) x) * ((long) y));
    }

    public static final IntNum times(IntNum x, int y) {
        boolean negative;
        if (y == 0) {
            return zero();
        }
        if (y == 1) {
            return x;
        }
        int[] xwords = x.words;
        int xlen = x.ival;
        if (xwords == null) {
            return make(((long) xlen) * ((long) y));
        }
        IntNum result = alloc(xlen + 1);
        if (xwords[xlen - 1] < 0) {
            negative = true;
            negate(result.words, xwords, xlen);
            xwords = result.words;
        } else {
            negative = false;
        }
        if (y < 0) {
            negative = !negative;
            y = -y;
        }
        int[] iArr = result.words;
        iArr[xlen] = MPN.mul_1(iArr, xwords, xlen, y);
        result.ival = xlen + 1;
        if (negative) {
            result.setNegative();
        }
        return result.canonicalize();
    }

    public static final IntNum times(IntNum x, IntNum y) {
        int[] xwords;
        boolean negative;
        int[] ywords;
        if (y.words == null) {
            return times(x, y.ival);
        }
        if (x.words == null) {
            return times(y, x.ival);
        }
        int xlen = x.ival;
        int ylen = y.ival;
        if (x.isNegative()) {
            negative = true;
            xwords = new int[xlen];
            negate(xwords, x.words, xlen);
        } else {
            negative = false;
            xwords = x.words;
        }
        if (y.isNegative()) {
            negative = !negative;
            ywords = new int[ylen];
            negate(ywords, y.words, ylen);
        } else {
            ywords = y.words;
        }
        if (xlen < ylen) {
            int[] twords = xwords;
            xwords = ywords;
            ywords = twords;
            int tlen = xlen;
            xlen = ylen;
            ylen = tlen;
        }
        IntNum result = alloc(xlen + ylen);
        MPN.mul(result.words, xwords, xlen, ywords, ylen);
        result.ival = xlen + ylen;
        if (negative) {
            result.setNegative();
        }
        return result.canonicalize();
    }

    public static void divide(long x, long y, IntNum quotient, IntNum remainder, int rounding_mode) {
        int rounding_mode2;
        boolean xNegative;
        boolean yNegative;
        long r;
        long x2 = x;
        long y2 = y;
        IntNum intNum = quotient;
        IntNum intNum2 = remainder;
        boolean z = true;
        int i = rounding_mode;
        if (i == 5) {
            rounding_mode2 = y2 < 0 ? 2 : 1;
        } else {
            rounding_mode2 = i;
        }
        if (x2 < 0) {
            xNegative = true;
            if (x2 == Long.MIN_VALUE) {
                divide(make(x), make(y), intNum, intNum2, rounding_mode2);
                return;
            }
            x2 = -x2;
        } else {
            xNegative = false;
        }
        if (y2 < 0) {
            yNegative = true;
            if (y2 != Long.MIN_VALUE) {
                y2 = -y2;
            } else if (rounding_mode2 == 3) {
                if (intNum != null) {
                    intNum.set(0);
                }
                if (intNum2 != null) {
                    intNum2.set(x2);
                    return;
                }
                return;
            } else {
                divide(make(x2), make(y), intNum, intNum2, rounding_mode2);
                return;
            }
        } else {
            yNegative = false;
        }
        long q = x2 / y2;
        long r2 = x2 % y2;
        boolean qNegative = xNegative ^ yNegative;
        boolean add_one = false;
        if (r2 != 0) {
            switch (rounding_mode2) {
                case 1:
                case 2:
                    if (rounding_mode2 != 1) {
                        z = false;
                    }
                    if (qNegative == z) {
                        add_one = true;
                        break;
                    }
                    break;
                case 4:
                    if (r2 <= ((y2 - (q & 1)) >> 1)) {
                        z = false;
                    }
                    add_one = z;
                    break;
            }
        }
        if (intNum != null) {
            if (add_one) {
                q++;
            }
            if (qNegative) {
                q = -q;
            }
            intNum.set(q);
        }
        if (intNum2 != null) {
            if (add_one) {
                xNegative = !xNegative;
                r = y2 - r2;
            } else {
                r = r2;
            }
            if (xNegative) {
                r = -r;
            }
            intNum2.set(r);
            long j = r;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void divide(gnu.math.IntNum r21, gnu.math.IntNum r22, gnu.math.IntNum r23, gnu.math.IntNum r24, int r25) {
        /*
            r0 = r21
            r1 = r22
            r9 = r23
            r10 = r24
            int[] r2 = r0.words
            r3 = 2
            if (r2 == 0) goto L_0x0011
            int r2 = r0.ival
            if (r2 > r3) goto L_0x0037
        L_0x0011:
            int[] r2 = r1.words
            if (r2 == 0) goto L_0x0019
            int r2 = r1.ival
            if (r2 > r3) goto L_0x0037
        L_0x0019:
            long r11 = r21.longValue()
            long r13 = r22.longValue()
            r4 = -9223372036854775808
            int r2 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x0037
            int r2 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x0037
            r2 = r11
            r4 = r13
            r6 = r23
            r7 = r24
            r8 = r25
            divide((long) r2, (long) r4, (gnu.math.IntNum) r6, (gnu.math.IntNum) r7, (int) r8)
            return
        L_0x0037:
            boolean r2 = r21.isNegative()
            boolean r4 = r22.isNegative()
            r5 = r2 ^ r4
            int[] r6 = r1.words
            r7 = 1
            if (r6 != 0) goto L_0x0048
            r6 = 1
            goto L_0x004a
        L_0x0048:
            int r6 = r1.ival
        L_0x004a:
            int[] r8 = new int[r6]
            r1.getAbsolute(r8)
        L_0x004f:
            if (r6 <= r7) goto L_0x005a
            int r11 = r6 + -1
            r11 = r8[r11]
            if (r11 != 0) goto L_0x005a
            int r6 = r6 + -1
            goto L_0x004f
        L_0x005a:
            int[] r11 = r0.words
            if (r11 != 0) goto L_0x0060
            r11 = 1
            goto L_0x0062
        L_0x0060:
            int r11 = r0.ival
        L_0x0062:
            int r12 = r11 + 2
            int[] r12 = new int[r12]
            r0.getAbsolute(r12)
        L_0x0069:
            if (r11 <= r7) goto L_0x0074
            int r13 = r11 + -1
            r13 = r12[r13]
            if (r13 != 0) goto L_0x0074
            int r11 = r11 + -1
            goto L_0x0069
        L_0x0074:
            int r13 = gnu.math.MPN.cmp(r12, r11, r8, r6)
            r14 = 0
            if (r13 >= 0) goto L_0x0089
            r15 = r12
            r12 = r8
            r8 = r15
            r16 = r11
            r17 = 1
            r12[r14] = r14
            r3 = r16
            r14 = r17
            goto L_0x00e9
        L_0x0089:
            if (r13 != 0) goto L_0x0098
            r12[r14] = r7
            r17 = 1
            r8[r14] = r14
            r16 = 1
            r3 = r16
            r14 = r17
            goto L_0x00e9
        L_0x0098:
            if (r6 != r7) goto L_0x00ab
            r17 = r11
            r16 = 1
            r15 = r8[r14]
            int r15 = gnu.math.MPN.divmod_1(r12, r12, r11, r15)
            r8[r14] = r15
            r3 = r16
            r14 = r17
            goto L_0x00e9
        L_0x00ab:
            int r15 = r6 + -1
            r15 = r8[r15]
            int r15 = gnu.math.MPN.count_leading_zeros(r15)
            if (r15 == 0) goto L_0x00c2
            gnu.math.MPN.lshift(r8, r14, r8, r6, r15)
            int r16 = gnu.math.MPN.lshift(r12, r14, r12, r11, r15)
            int r17 = r11 + 1
            r12[r11] = r16
            r11 = r17
        L_0x00c2:
            if (r11 != r6) goto L_0x00ca
            int r16 = r11 + 1
            r12[r11] = r14
            r11 = r16
        L_0x00ca:
            gnu.math.MPN.divide(r12, r11, r8, r6)
            r16 = r6
            r3 = r16
            gnu.math.MPN.rshift0(r8, r12, r14, r3, r15)
            int r16 = r11 + 1
            int r14 = r16 - r6
            if (r9 == 0) goto L_0x00e9
            r16 = 0
            r7 = r16
        L_0x00de:
            if (r7 >= r14) goto L_0x00e9
            int r16 = r7 + r6
            r16 = r12[r16]
            r12[r7] = r16
            int r7 = r7 + 1
            goto L_0x00de
        L_0x00e9:
            r7 = 1
            if (r3 <= r7) goto L_0x00f5
            int r7 = r3 + -1
            r7 = r8[r7]
            if (r7 != 0) goto L_0x00f5
            int r3 = r3 + -1
            goto L_0x00e9
        L_0x00f5:
            int r7 = r3 + -1
            r7 = r8[r7]
            if (r7 >= 0) goto L_0x0100
            r7 = 0
            r8[r3] = r7
            int r3 = r3 + 1
        L_0x0100:
            r7 = 0
            r15 = 1
            if (r3 > r15) goto L_0x0110
            r15 = 0
            r16 = r8[r15]
            if (r16 == 0) goto L_0x010a
            goto L_0x0110
        L_0x010a:
            r0 = r25
            r16 = r6
            goto L_0x016e
        L_0x0110:
            r15 = 5
            r0 = r25
            if (r0 != r15) goto L_0x011e
            if (r4 == 0) goto L_0x011a
            r17 = 2
            goto L_0x011c
        L_0x011a:
            r17 = 1
        L_0x011c:
            r0 = r17
        L_0x011e:
            switch(r0) {
                case 1: goto L_0x015e;
                case 2: goto L_0x015e;
                case 3: goto L_0x0159;
                case 4: goto L_0x0126;
                default: goto L_0x0121;
            }
        L_0x0121:
            r16 = r6
            r17 = r7
            goto L_0x016c
        L_0x0126:
            if (r10 != 0) goto L_0x012e
            gnu.math.IntNum r15 = new gnu.math.IntNum
            r15.<init>()
            goto L_0x012f
        L_0x012e:
            r15 = r10
        L_0x012f:
            r15.set(r8, r3)
            r16 = r6
            r6 = 1
            gnu.math.IntNum r15 = shift((gnu.math.IntNum) r15, (int) r6)
            if (r4 == 0) goto L_0x013e
            r15.setNegative()
        L_0x013e:
            int r6 = compare((gnu.math.IntNum) r15, (gnu.math.IntNum) r1)
            if (r4 == 0) goto L_0x0145
            int r6 = -r6
        L_0x0145:
            r17 = r7
            r7 = 1
            if (r6 == r7) goto L_0x0157
            if (r6 != 0) goto L_0x0155
            r18 = 0
            r19 = r12[r18]
            r20 = r19 & 1
            if (r20 == 0) goto L_0x0155
            goto L_0x0157
        L_0x0155:
            r7 = 0
            goto L_0x0158
        L_0x0157:
            r7 = 1
        L_0x0158:
            goto L_0x016e
        L_0x0159:
            r16 = r6
            r17 = r7
            goto L_0x016c
        L_0x015e:
            r16 = r6
            r17 = r7
            r6 = 1
            if (r0 != r6) goto L_0x0167
            r6 = 1
            goto L_0x0168
        L_0x0167:
            r6 = 0
        L_0x0168:
            if (r5 != r6) goto L_0x016c
            r7 = 1
            goto L_0x016e
        L_0x016c:
            r7 = r17
        L_0x016e:
            if (r9 == 0) goto L_0x0195
            int r6 = r14 + -1
            r6 = r12[r6]
            if (r6 >= 0) goto L_0x017b
            r6 = 0
            r12[r14] = r6
            int r14 = r14 + 1
        L_0x017b:
            r9.set(r12, r14)
            if (r5 == 0) goto L_0x018c
            if (r7 == 0) goto L_0x0187
            r23.setInvert()
            r6 = 1
            goto L_0x0196
        L_0x0187:
            r23.setNegative()
            r6 = 1
            goto L_0x0196
        L_0x018c:
            if (r7 == 0) goto L_0x0193
            r6 = 1
            r9.setAdd(r6)
            goto L_0x0196
        L_0x0193:
            r6 = 1
            goto L_0x0196
        L_0x0195:
            r6 = 1
        L_0x0196:
            if (r10 == 0) goto L_0x01d8
            r10.set(r8, r3)
            if (r7 == 0) goto L_0x01d0
            int[] r15 = r1.words
            if (r15 != 0) goto L_0x01bb
            r6 = r24
            if (r4 == 0) goto L_0x01ae
            r15 = 0
            r15 = r8[r15]
            r25 = r0
            int r0 = r1.ival
            int r15 = r15 + r0
            goto L_0x01b7
        L_0x01ae:
            r25 = r0
            r15 = 0
            r0 = r8[r15]
            int r15 = r1.ival
            int r15 = r0 - r15
        L_0x01b7:
            r6.set((int) r15)
            goto L_0x01c6
        L_0x01bb:
            r25 = r0
            if (r4 == 0) goto L_0x01c1
            r0 = 1
            goto L_0x01c2
        L_0x01c1:
            r0 = -1
        L_0x01c2:
            gnu.math.IntNum r6 = add(r10, r1, r0)
        L_0x01c6:
            if (r2 == 0) goto L_0x01cc
            r10.setNegative(r6)
            goto L_0x01cf
        L_0x01cc:
            r10.set((gnu.math.IntNum) r6)
        L_0x01cf:
            goto L_0x01da
        L_0x01d0:
            r25 = r0
            if (r2 == 0) goto L_0x01da
            r24.setNegative()
            goto L_0x01da
        L_0x01d8:
            r25 = r0
        L_0x01da:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.math.IntNum.divide(gnu.math.IntNum, gnu.math.IntNum, gnu.math.IntNum, gnu.math.IntNum, int):void");
    }

    public static IntNum quotient(IntNum x, IntNum y, int rounding_mode) {
        IntNum quotient = new IntNum();
        divide(x, y, quotient, (IntNum) null, rounding_mode);
        return quotient.canonicalize();
    }

    public static IntNum quotient(IntNum x, IntNum y) {
        return quotient(x, y, 3);
    }

    public IntNum toExactInt(int rounding_mode) {
        return this;
    }

    public RealNum toInt(int rounding_mode) {
        return this;
    }

    public static IntNum remainder(IntNum x, IntNum y, int rounding_mode) {
        if (y.isZero()) {
            return x;
        }
        IntNum rem = new IntNum();
        divide(x, y, (IntNum) null, rem, rounding_mode);
        return rem.canonicalize();
    }

    public static IntNum remainder(IntNum x, IntNum y) {
        return remainder(x, y, 3);
    }

    public static IntNum modulo(IntNum x, IntNum y) {
        return remainder(x, y, 1);
    }

    public Numeric power(IntNum y) {
        int i;
        if (isOne()) {
            return this;
        }
        if (isMinusOne()) {
            return y.isOdd() ? this : one();
        }
        if (y.words == null && (i = y.ival) >= 0) {
            return power(this, i);
        }
        if (isZero()) {
            return y.isNegative() ? RatNum.infinity(-1) : this;
        }
        return super.power(y);
    }

    public static IntNum power(IntNum x, int y) {
        if (y <= 0) {
            if (y == 0) {
                return one();
            }
            throw new Error("negative exponent");
        } else if (x.isZero()) {
            return x;
        } else {
            int plen = x.words == null ? 1 : x.ival;
            int blen = ((x.intLength() * y) >> 5) + (plen * 2);
            boolean negative = x.isNegative() && (y & 1) != 0;
            int[] pow2 = new int[blen];
            int[] rwords = new int[blen];
            int[] work = new int[blen];
            x.getAbsolute(pow2);
            int rlen = 1;
            rwords[0] = 1;
            while (true) {
                if ((y & 1) != 0) {
                    MPN.mul(work, pow2, plen, rwords, rlen);
                    int[] temp = work;
                    work = rwords;
                    int[] rwords2 = temp;
                    rlen += plen;
                    while (rwords2[rlen - 1] == 0) {
                        rlen--;
                    }
                    rwords = rwords2;
                }
                y >>= 1;
                if (y == 0) {
                    break;
                }
                MPN.mul(work, pow2, plen, pow2, plen);
                int[] temp2 = work;
                work = pow2;
                pow2 = temp2;
                int plen2 = plen * 2;
                while (pow2[plen - 1] == 0) {
                    plen2 = plen - 1;
                }
            }
            if (rwords[rlen - 1] < 0) {
                rlen++;
            }
            if (negative) {
                negate(rwords, rwords, rlen);
            }
            return make(rwords, rlen);
        }
    }

    public static final int gcd(int a, int b) {
        if (b > a) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        while (b != 0) {
            if (b == 1) {
                return b;
            }
            int tmp2 = b;
            b = a % b;
            a = tmp2;
        }
        return a;
    }

    public static IntNum gcd(IntNum x, IntNum y) {
        int xval = x.ival;
        int yval = y.ival;
        if (x.words == null) {
            if (xval == 0) {
                return abs(y);
            }
            if (y.words != null || xval == Integer.MIN_VALUE || yval == Integer.MIN_VALUE) {
                xval = 1;
            } else {
                if (xval < 0) {
                    xval = -xval;
                }
                if (yval < 0) {
                    yval = -yval;
                }
                return make(gcd(xval, yval));
            }
        }
        if (y.words == null) {
            if (yval == 0) {
                return abs(x);
            }
            yval = 1;
        }
        int len = xval > yval ? xval : yval;
        int[] xwords = new int[len];
        int[] ywords = new int[len];
        x.getAbsolute(xwords);
        y.getAbsolute(ywords);
        int len2 = MPN.gcd(xwords, ywords, len);
        IntNum result = new IntNum(0);
        if (xwords[len2 - 1] < 0) {
            xwords[len2] = 0;
            len2++;
        }
        result.ival = len2;
        result.words = xwords;
        return result.canonicalize();
    }

    public static IntNum lcm(IntNum x, IntNum y) {
        if (x.isZero() || y.isZero()) {
            return zero();
        }
        IntNum x2 = abs(x);
        IntNum y2 = abs(y);
        IntNum quotient = new IntNum();
        divide(times(x2, y2), gcd(x2, y2), quotient, (IntNum) null, 3);
        return quotient.canonicalize();
    }

    /* access modifiers changed from: package-private */
    public void setInvert() {
        if (this.words == null) {
            this.ival ^= -1;
            return;
        }
        int i = this.ival;
        while (true) {
            i--;
            if (i >= 0) {
                int[] iArr = this.words;
                iArr[i] = iArr[i] ^ -1;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setShiftLeft(IntNum x, int count) {
        int xlen;
        int[] xwords;
        if (x.words != null) {
            xwords = x.words;
            xlen = x.ival;
        } else if (count < 32) {
            set(((long) x.ival) << count);
            return;
        } else {
            xwords = new int[]{x.ival};
            xlen = 1;
        }
        int word_count = count >> 5;
        int count2 = count & 31;
        int new_len = xlen + word_count;
        if (count2 == 0) {
            realloc(new_len);
            int i = xlen;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                this.words[i + word_count] = xwords[i];
            }
        } else {
            new_len++;
            realloc(new_len);
            int shift_out = MPN.lshift(this.words, word_count, xwords, xlen, count2);
            int count3 = 32 - count2;
            this.words[new_len - 1] = (shift_out << count3) >> count3;
        }
        this.ival = new_len;
        int i2 = word_count;
        while (true) {
            i2--;
            if (i2 >= 0) {
                this.words[i2] = 0;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setShiftRight(IntNum x, int count) {
        int i = -1;
        if (x.words == null) {
            if (count < 32) {
                i = x.ival >> count;
            } else if (x.ival >= 0) {
                i = 0;
            }
            set(i);
        } else if (count == 0) {
            set(x);
        } else {
            boolean neg = x.isNegative();
            int word_count = count >> 5;
            int count2 = count & 31;
            int d_len = x.ival - word_count;
            if (d_len <= 0) {
                if (!neg) {
                    i = 0;
                }
                set(i);
                return;
            }
            int[] iArr = this.words;
            if (iArr == null || iArr.length < d_len) {
                realloc(d_len);
            }
            MPN.rshift0(this.words, x.words, word_count, d_len, count2);
            this.ival = d_len;
            if (neg) {
                int[] iArr2 = this.words;
                int i2 = d_len - 1;
                iArr2[i2] = iArr2[i2] | (-2 << (31 - count2));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setShift(IntNum x, int count) {
        if (count > 0) {
            setShiftLeft(x, count);
        } else {
            setShiftRight(x, -count);
        }
    }

    public static IntNum shift(IntNum x, int count) {
        int i = 0;
        if (x.words == null) {
            if (count <= 0) {
                if (count > -32) {
                    i = x.ival >> (-count);
                } else if (x.ival < 0) {
                    i = -1;
                }
                return make(i);
            } else if (count < 32) {
                return make(((long) x.ival) << count);
            }
        }
        if (count == 0) {
            return x;
        }
        IntNum result = new IntNum(0);
        result.setShift(x, count);
        return result.canonicalize();
    }

    public static int shift(int x, int count) {
        if (count >= 32) {
            return 0;
        }
        if (count >= 0) {
            return x << count;
        }
        int count2 = -count;
        if (count2 < 32) {
            return x >> count2;
        }
        if (x < 0) {
            return -1;
        }
        return 0;
    }

    public static long shift(long x, int count) {
        if (count >= 32) {
            return 0;
        }
        if (count >= 0) {
            return x << count;
        }
        int count2 = -count;
        if (count2 < 32) {
            return x >> count2;
        }
        if (x < 0) {
            return -1;
        }
        return 0;
    }

    public void format(int radix, StringBuffer buffer) {
        if (radix == 10) {
            if (this.words == null) {
                buffer.append(this.ival);
                return;
            } else if (this.ival <= 2) {
                buffer.append(longValue());
                return;
            }
        }
        buffer.append(toString(radix));
    }

    public void format(int radix, StringBuilder buffer) {
        int[] work;
        int digit;
        if (this.words == null) {
            if (radix == 10) {
                buffer.append(this.ival);
            } else {
                buffer.append(Integer.toString(this.ival, radix));
            }
        } else if (this.ival <= 2) {
            long lval = longValue();
            if (radix == 10) {
                buffer.append(lval);
            } else {
                buffer.append(Long.toString(lval, radix));
            }
        } else {
            boolean neg = isNegative();
            if (neg || radix != 16) {
                work = new int[this.ival];
                getAbsolute(work);
            } else {
                work = this.words;
            }
            int len = this.ival;
            if (radix == 16) {
                if (neg) {
                    buffer.append('-');
                }
                int buf_start = buffer.length();
                int i = len;
                while (true) {
                    i--;
                    if (i >= 0) {
                        int word = work[i];
                        int j = 8;
                        while (true) {
                            j--;
                            if (j >= 0) {
                                int hex_digit = (word >> (j * 4)) & 15;
                                if (hex_digit > 0 || buffer.length() > buf_start) {
                                    buffer.append(Character.forDigit(hex_digit, 16));
                                }
                            }
                        }
                    } else {
                        return;
                    }
                }
            } else {
                int chars_per_word = MPN.chars_per_word(radix);
                int wradix = radix;
                int j2 = chars_per_word;
                while (true) {
                    j2--;
                    if (j2 <= 0) {
                        break;
                    }
                    wradix *= radix;
                }
                int i2 = buffer.length();
                do {
                    int wdigit = MPN.divmod_1(work, work, len, wradix);
                    while (len > 0 && work[len - 1] == 0) {
                        len--;
                    }
                    int j3 = chars_per_word;
                    while (true) {
                        j3--;
                        if (j3 >= 0 && (len != 0 || wdigit != 0)) {
                            if (wdigit < 0) {
                                digit = (int) ((((long) wdigit) & -1) % ((long) radix));
                            } else {
                                digit = wdigit % radix;
                            }
                            wdigit /= radix;
                            buffer.append(Character.forDigit(digit, radix));
                        }
                    }
                } while (len != 0);
                if (neg) {
                    buffer.append('-');
                }
                for (int j4 = buffer.length() - 1; i2 < j4; j4--) {
                    char tmp = buffer.charAt(i2);
                    buffer.setCharAt(i2, buffer.charAt(j4));
                    buffer.setCharAt(j4, tmp);
                    i2++;
                }
            }
        }
    }

    public String toString(int radix) {
        if (this.words == null) {
            return Integer.toString(this.ival, radix);
        }
        int i = this.ival;
        if (i <= 2) {
            return Long.toString(longValue(), radix);
        }
        StringBuilder buffer = new StringBuilder(i * (MPN.chars_per_word(radix) + 1));
        format(radix, buffer);
        return buffer.toString();
    }

    public int intValue() {
        int[] iArr = this.words;
        if (iArr == null) {
            return this.ival;
        }
        return iArr[0];
    }

    public static int intValue(Object obj) {
        IntNum inum = (IntNum) obj;
        if (inum.words == null) {
            return inum.ival;
        }
        throw new ClassCastException("integer too large");
    }

    public long longValue() {
        int[] iArr = this.words;
        if (iArr == null) {
            return (long) this.ival;
        }
        if (this.ival == 1) {
            return (long) iArr[0];
        }
        return (((long) iArr[1]) << 32) + (((long) iArr[0]) & 4294967295L);
    }

    public int hashCode() {
        int[] iArr = this.words;
        if (iArr == null) {
            return this.ival;
        }
        return iArr[this.ival - 1] + iArr[0];
    }

    public static boolean equals(IntNum x, IntNum y) {
        int[] iArr = x.words;
        if (iArr == null && y.words == null) {
            if (x.ival == y.ival) {
                return true;
            }
            return false;
        } else if (iArr == null || y.words == null || x.ival != y.ival) {
            return false;
        } else {
            int i = x.ival;
            do {
                i--;
                if (i < 0) {
                    return true;
                }
            } while (x.words[i] == y.words[i]);
            return false;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntNum)) {
            return false;
        }
        return equals(this, (IntNum) obj);
    }

    public static IntNum valueOf(char[] buf, int offset, int length, int radix, boolean negative) {
        int byte_len = 0;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            char ch = buf[offset + i];
            if (ch == '-') {
                negative = true;
            } else if (!(ch == '_' || (byte_len == 0 && (ch == ' ' || ch == 9)))) {
                int digit = Character.digit(ch, radix);
                if (digit < 0) {
                    break;
                }
                bytes[byte_len] = (byte) digit;
                byte_len++;
            }
        }
        return valueOf(bytes, byte_len, negative, radix);
    }

    public static IntNum valueOf(String s, int radix) throws NumberFormatException {
        int len = s.length();
        if (len + radix <= 28) {
            if (len > 1 && s.charAt(0) == '+' && Character.digit(s.charAt(1), radix) >= 0) {
                s = s.substring(1);
            }
            return make(Long.parseLong(s, radix));
        }
        int byte_len = 0;
        byte[] bytes = new byte[len];
        boolean negative = false;
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (!((ch == '+' && i == 0) || ch == '_' || (byte_len == 0 && (ch == ' ' || ch == 9)))) {
                int digit = Character.digit(ch, radix);
                if (digit >= 0) {
                    bytes[byte_len] = (byte) digit;
                    byte_len++;
                } else {
                    throw new NumberFormatException("For input string: \"" + s + '\"');
                }
            }
        }
        return valueOf(bytes, byte_len, negative, radix);
    }

    public static IntNum valueOf(byte[] digits, int byte_len, boolean negative, int radix) {
        int[] words2 = new int[((byte_len / MPN.chars_per_word(radix)) + 1)];
        int size = MPN.set_str(words2, digits, byte_len, radix);
        if (size == 0) {
            return zero();
        }
        if (words2[size - 1] < 0) {
            words2[size] = 0;
            size++;
        }
        if (negative) {
            negate(words2, words2, size);
        }
        return make(words2, size);
    }

    public static IntNum valueOf(String s) throws NumberFormatException {
        return valueOf(s, 10);
    }

    public double doubleValue() {
        if (this.words == null) {
            return (double) this.ival;
        }
        if (this.ival <= 2) {
            return (double) longValue();
        }
        if (isNegative()) {
            return neg(this).roundToDouble(0, true, false);
        }
        return roundToDouble(0, false, false);
    }

    /* access modifiers changed from: package-private */
    public boolean checkBits(int n) {
        if (n <= 0) {
            return false;
        }
        if (this.words != null) {
            int i = 0;
            while (i < (n >> 5)) {
                if (this.words[i] != 0) {
                    return true;
                }
                i++;
            }
            if ((n & 31) == 0 || (this.words[i] & ((1 << (n & 31)) - 1)) == 0) {
                return false;
            }
            return true;
        } else if (n > 31 || (this.ival & ((1 << n) - 1)) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public double roundToDouble(int exp, boolean neg, boolean remainder) {
        long m;
        int il = intLength();
        int exp2 = exp + (il - 1);
        if (exp2 < -1075) {
            return neg ? -0.0d : 0.0d;
        }
        if (exp2 <= 1023) {
            int ml = exp2 >= -1022 ? 53 : exp2 + 53 + 1022;
            int excess_bits = il - (ml + 1);
            if (excess_bits > 0) {
                int[] iArr = this.words;
                m = iArr == null ? (long) (this.ival >> excess_bits) : MPN.rshift_long(iArr, this.ival, excess_bits);
            } else {
                m = longValue() << (-excess_bits);
            }
            if (exp2 != 1023 || (m >> 1) != 9007199254740991L) {
                long bits_exp = 0;
                if ((m & 1) == 1 && ((m & 2) == 2 || remainder || checkBits(excess_bits))) {
                    m += 2;
                    if ((18014398509481984L & m) != 0) {
                        exp2++;
                        m >>= 1;
                    } else if (ml == 52 && (9007199254740992L & m) != 0) {
                        exp2++;
                    }
                }
                long m2 = m >> 1;
                long bits_sign = neg ? Long.MIN_VALUE : 0;
                int exp3 = exp2 + 1023;
                if (exp3 > 0) {
                    bits_exp = ((long) exp3) << 52;
                }
                return Double.longBitsToDouble(bits_sign | bits_exp | (-4503599627370497L & m2));
            } else if (!remainder && !checkBits(il - ml)) {
                return neg ? -1.7976931348623157E308d : Double.MAX_VALUE;
            } else {
                if (neg) {
                    return Double.NEGATIVE_INFINITY;
                }
                return Double.POSITIVE_INFINITY;
            }
        } else if (neg) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public Numeric add(Object y, int k) {
        if (y instanceof IntNum) {
            return add(this, (IntNum) y, k);
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).addReversed(this, k);
        }
        throw new IllegalArgumentException();
    }

    public Numeric mul(Object y) {
        if (y instanceof IntNum) {
            return times(this, (IntNum) y);
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).mulReversed(this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric div(Object y) {
        if (y instanceof RatNum) {
            RatNum r = (RatNum) y;
            return RatNum.make(times(this, r.denominator()), r.numerator());
        } else if (y instanceof Numeric) {
            return ((Numeric) y).divReversed(this);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getAbsolute(int[] words2) {
        int len;
        if (this.words != null) {
            len = this.ival;
            int i = len;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                words2[i] = this.words[i];
            }
        } else {
            len = 1;
            words2[0] = this.ival;
        }
        if (words2[len - 1] < 0) {
            negate(words2, words2, len);
        }
        int i2 = words2.length;
        while (true) {
            i2--;
            if (i2 > len) {
                words2[i2] = 0;
            } else {
                return;
            }
        }
    }

    public static boolean negate(int[] dest, int[] src, int len) {
        long carry = 1;
        boolean negative = src[len + -1] < 0;
        for (int i = 0; i < len; i++) {
            long carry2 = carry + (((long) (src[i] ^ -1)) & 4294967295L);
            dest[i] = (int) carry2;
            carry = carry2 >> 32;
        }
        if (!negative || dest[len - 1] >= 0) {
            return false;
        }
        return true;
    }

    public void setNegative(IntNum x) {
        int len = x.ival;
        if (x.words != null) {
            realloc(len + 1);
            if (negate(this.words, x.words, len)) {
                this.words[len] = 0;
                len++;
            }
            this.ival = len;
        } else if (len == Integer.MIN_VALUE) {
            set(-((long) len));
        } else {
            set(-len);
        }
    }

    public final void setNegative() {
        setNegative(this);
    }

    public static IntNum abs(IntNum x) {
        return x.isNegative() ? neg(x) : x;
    }

    public static IntNum neg(IntNum x) {
        int i;
        if (x.words == null && (i = x.ival) != Integer.MIN_VALUE) {
            return make(-i);
        }
        IntNum result = new IntNum(0);
        result.setNegative(x);
        return result.canonicalize();
    }

    public Numeric neg() {
        return neg(this);
    }

    public int intLength() {
        int[] iArr = this.words;
        if (iArr == null) {
            return MPN.intLength(this.ival);
        }
        return MPN.intLength(iArr, this.ival);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int[] iArr = this.words;
        int nwords = iArr == null ? 1 : wordsNeeded(iArr, this.ival);
        if (nwords <= 1) {
            int[] iArr2 = this.words;
            int i = 0;
            if (iArr2 == null) {
                i = this.ival;
            } else if (iArr2.length != 0) {
                i = iArr2[0];
            }
            int i2 = i;
            if (i2 >= -1073741824) {
                out.writeInt(i2);
                return;
            }
            out.writeInt(-2147483647);
            out.writeInt(i2);
            return;
        }
        out.writeInt(Integer.MIN_VALUE | nwords);
        while (true) {
            nwords--;
            if (nwords >= 0) {
                out.writeInt(this.words[nwords]);
            } else {
                return;
            }
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int i = in.readInt();
        if (i <= -1073741824) {
            i &= ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (i == 1) {
                i = in.readInt();
            } else {
                int[] w = new int[i];
                int j = i;
                while (true) {
                    j--;
                    if (j < 0) {
                        break;
                    }
                    w[j] = in.readInt();
                }
                this.words = w;
            }
        }
        this.ival = i;
    }

    public Object readResolve() throws ObjectStreamException {
        return canonicalize();
    }

    public BigInteger asBigInteger() {
        if (this.words == null || this.ival <= 2) {
            return BigInteger.valueOf(longValue());
        }
        return new BigInteger(toString());
    }

    public BigDecimal asBigDecimal() {
        if (this.words == null) {
            return new BigDecimal(this.ival);
        }
        if (this.ival <= 2) {
            return BigDecimal.valueOf(longValue());
        }
        return new BigDecimal(toString());
    }

    public boolean inRange(long lo, long hi) {
        return compare(this, lo) >= 0 && compare(this, hi) <= 0;
    }

    public boolean inIntRange() {
        return inRange(-2147483648L, 2147483647L);
    }

    public boolean inLongRange() {
        return inRange(Long.MIN_VALUE, Long.MAX_VALUE);
    }
}
