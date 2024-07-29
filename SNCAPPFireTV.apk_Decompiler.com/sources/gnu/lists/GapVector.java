package gnu.lists;

public class GapVector extends AbstractSequence implements Sequence {
    public SimpleVector base;
    public int gapEnd;
    public int gapStart;

    public GapVector(SimpleVector base2) {
        this.base = base2;
        this.gapStart = 0;
        this.gapEnd = base2.size;
    }

    protected GapVector() {
    }

    public int size() {
        return this.base.size - (this.gapEnd - this.gapStart);
    }

    public boolean hasNext(int ipos) {
        int index = ipos >>> 1;
        int i = this.gapStart;
        if (index >= i) {
            index += this.gapEnd - i;
        }
        return index < this.base.size;
    }

    public int getNextKind(int ipos) {
        if (hasNext(ipos)) {
            return this.base.getElementKind();
        }
        return 0;
    }

    public Object get(int index) {
        int i = this.gapStart;
        if (index >= i) {
            index += this.gapEnd - i;
        }
        return this.base.get(index);
    }

    public Object set(int index, Object value) {
        int i = this.gapStart;
        if (index >= i) {
            index += this.gapEnd - i;
        }
        return this.base.set(index, value);
    }

    public void fill(Object value) {
        SimpleVector simpleVector = this.base;
        simpleVector.fill(this.gapEnd, simpleVector.size, value);
        this.base.fill(0, this.gapStart, value);
    }

    public void fillPosRange(int fromPos, int toPos, Object value) {
        int from = fromPos == -1 ? this.base.size : fromPos >>> 1;
        int to = toPos == -1 ? this.base.size : toPos >>> 1;
        int limit = this.gapStart;
        if (limit >= to) {
            limit = to;
        }
        for (int i = from; i < limit; i++) {
            this.base.setBuffer(i, value);
        }
        for (int i2 = this.gapEnd; i2 < to; i2++) {
            this.base.setBuffer(i2, value);
        }
    }

    /* access modifiers changed from: protected */
    public void shiftGap(int newGapStart) {
        int i = this.gapStart;
        int delta = newGapStart - i;
        if (delta > 0) {
            this.base.shift(this.gapEnd, i, delta);
        } else if (delta < 0) {
            this.base.shift(newGapStart, this.gapEnd + delta, -delta);
        }
        this.gapEnd += delta;
        this.gapStart = newGapStart;
    }

    /* access modifiers changed from: protected */
    public final void gapReserve(int size) {
        gapReserve(this.gapStart, size);
    }

    /* access modifiers changed from: protected */
    public void gapReserve(int where, int needed) {
        int i = this.gapEnd;
        int i2 = this.gapStart;
        if (needed > i - i2) {
            int oldLength = this.base.size;
            int newLength = 16;
            if (oldLength >= 16) {
                newLength = oldLength * 2;
            }
            int i3 = this.gapEnd;
            int i4 = this.gapStart;
            int size = oldLength - (i3 - i4);
            int minLength = size + needed;
            if (newLength < minLength) {
                newLength = minLength;
            }
            int newGapEnd = (newLength - size) + where;
            this.base.resizeShift(i4, i3, where, newGapEnd);
            this.gapStart = where;
            this.gapEnd = newGapEnd;
        } else if (where != i2) {
            shiftGap(where);
        }
    }

    public int getSegment(int where, int len) {
        int length = size();
        if (where < 0 || where > length) {
            return -1;
        }
        if (len < 0) {
            len = 0;
        } else if (where + len > length) {
            len = length - where;
        }
        int i = where + len;
        int i2 = this.gapStart;
        if (i <= i2) {
            return where;
        }
        if (where >= i2) {
            return (this.gapEnd - i2) + where;
        }
        if (i2 - where > (len >> 1)) {
            shiftGap(where + len);
            return where;
        }
        shiftGap(where);
        return (this.gapEnd - this.gapStart) + where;
    }

    /* access modifiers changed from: protected */
    public int addPos(int ipos, Object value) {
        int index = ipos >>> 1;
        int i = this.gapStart;
        if (index >= i) {
            index += this.gapEnd - i;
        }
        add(index, value);
        return ((index + 1) << 1) | 1;
    }

    public void add(int index, Object o) {
        gapReserve(index, 1);
        this.base.set(index, o);
        this.gapStart++;
    }

    /* access modifiers changed from: protected */
    public void removePosRange(int ipos0, int ipos1) {
        int ipos02 = ipos0 >>> 1;
        int ipos12 = ipos1 >>> 1;
        int i = this.gapEnd;
        if (ipos02 > i) {
            shiftGap((ipos02 - i) + this.gapStart);
        } else if (ipos12 < this.gapStart) {
            shiftGap(ipos12);
        }
        int i2 = this.gapStart;
        if (ipos02 < i2) {
            this.base.clearBuffer(ipos02, i2 - ipos02);
            this.gapStart = ipos02;
        }
        int i3 = this.gapEnd;
        if (ipos12 > i3) {
            this.base.clearBuffer(i3, ipos12 - i3);
            this.gapEnd = ipos12;
        }
    }

    public int createPos(int index, boolean isAfter) {
        int i = this.gapStart;
        if (index > i) {
            index += this.gapEnd - i;
        }
        return (index << 1) | isAfter ? 1 : 0;
    }

    /* access modifiers changed from: protected */
    public boolean isAfterPos(int ipos) {
        return (ipos & 1) != 0;
    }

    /* access modifiers changed from: protected */
    public int nextIndex(int ipos) {
        int index = ipos == -1 ? this.base.size : ipos >>> 1;
        int i = this.gapStart;
        if (index > i) {
            return index - (this.gapEnd - i);
        }
        return index;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int i = iposStart >>> 1;
            int end = iposEnd >>> 1;
            int lim = this.gapStart;
            if (i < lim) {
                if (end > lim) {
                    lim = end;
                }
                consumePosRange(iposStart, lim << 1, out);
            }
            int lim2 = this.gapEnd;
            if (end > lim2) {
                if (i >= lim2) {
                    lim2 = i;
                }
                consumePosRange(lim2 << 1, iposEnd, out);
            }
        }
    }
}
