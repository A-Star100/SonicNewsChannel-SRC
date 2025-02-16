package gnu.lists;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractSequence {
    public abstract int createPos(int i, boolean z);

    public abstract Object get(int i);

    public abstract int size();

    public boolean isEmpty() {
        return size() == 0;
    }

    public int rank() {
        return 1;
    }

    public int getEffectiveIndex(int[] indexes) {
        return indexes[0];
    }

    public Object get(int[] indexes) {
        return get(indexes[0]);
    }

    public Object set(int[] indexes, Object value) {
        return set(indexes[0], value);
    }

    public int getLowBound(int dim) {
        return 0;
    }

    public int getSize(int dim) {
        if (dim == 0) {
            return size();
        }
        return 1;
    }

    /* access modifiers changed from: protected */
    public RuntimeException unsupported(String text) {
        return unsupportedException(getClass().getName() + " does not implement " + text);
    }

    public static RuntimeException unsupportedException(String text) {
        return new UnsupportedOperationException(text);
    }

    public Object set(int index, Object element) {
        throw unsupported("set");
    }

    public void fill(Object value) {
        int i = startPos();
        while (true) {
            int nextPos = nextPos(i);
            i = nextPos;
            if (nextPos != 0) {
                setPosPrevious(i, value);
            } else {
                return;
            }
        }
    }

    public void fillPosRange(int fromPos, int toPos, Object value) {
        int i = copyPos(fromPos);
        while (compare(i, toPos) < 0) {
            setPosNext(i, value);
            i = nextPos(i);
        }
        releasePos(i);
    }

    public void fill(int fromIndex, int toIndex, Object value) {
        int i = createPos(fromIndex, false);
        int limit = createPos(toIndex, true);
        while (compare(i, limit) < 0) {
            setPosNext(i, value);
            i = nextPos(i);
        }
        releasePos(i);
        releasePos(limit);
    }

    public int indexOf(Object o) {
        int i = 0;
        int iter = startPos();
        while (true) {
            int nextPos = nextPos(iter);
            iter = nextPos;
            if (nextPos == 0) {
                return -1;
            }
            Object v = getPosPrevious(iter);
            if (o == null) {
                if (v == null) {
                    break;
                }
                i++;
            } else if (o.equals(v)) {
                break;
            } else {
                i++;
            }
        }
        releasePos(iter);
        return i;
    }

    public int lastIndexOf(Object o) {
        int n = size();
        while (true) {
            n--;
            if (n < 0) {
                return -1;
            }
            Object e = get(n);
            if (o == null) {
                if (e == null) {
                    break;
                }
            } else if (o.equals(e)) {
                break;
            }
        }
        return n;
    }

    public int nextMatching(int startPos, ItemPredicate type, int endPos, boolean descend) {
        if (!descend) {
            int ipos = startPos;
            while (compare(ipos, endPos) < 0) {
                ipos = nextPos(ipos);
                if (type.isInstancePos(this, ipos)) {
                    return ipos;
                }
            }
            return 0;
        }
        throw unsupported("nextMatching with descend");
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public boolean containsAll(Collection c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    public final Enumeration elements() {
        return getIterator();
    }

    public final SeqPosition getIterator() {
        return getIterator(0);
    }

    public SeqPosition getIterator(int index) {
        return new SeqPosition(this, index, false);
    }

    public SeqPosition getIteratorAtPos(int ipos) {
        return new SeqPosition(this, copyPos(ipos));
    }

    public final Iterator iterator() {
        return getIterator();
    }

    public final ListIterator listIterator() {
        return getIterator(0);
    }

    public final ListIterator listIterator(int index) {
        return getIterator(index);
    }

    /* access modifiers changed from: protected */
    public int addPos(int ipos, Object value) {
        throw unsupported("addPos");
    }

    public boolean add(Object o) {
        addPos(endPos(), o);
        return true;
    }

    public void add(int index, Object o) {
        int pos = createPos(index, false);
        addPos(pos, o);
        releasePos(pos);
    }

    public boolean addAll(Collection c) {
        return addAll(size(), c);
    }

    public boolean addAll(int index, Collection c) {
        boolean changed = false;
        int pos = createPos(index, false);
        for (Object addPos : c) {
            pos = addPos(pos, addPos);
            changed = true;
        }
        releasePos(pos);
        return changed;
    }

    public void removePos(int ipos, int count) {
        int rpos = createRelativePos(ipos, count, false);
        if (count >= 0) {
            removePosRange(ipos, rpos);
        } else {
            removePosRange(rpos, ipos);
        }
        releasePos(rpos);
    }

    /* access modifiers changed from: protected */
    public void removePosRange(int ipos0, int ipos1) {
        throw unsupported("removePosRange");
    }

    public Object remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int ipos = createPos(index, false);
        Object result = getPosNext(ipos);
        removePos(ipos, 1);
        releasePos(ipos);
        return result;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) {
            return false;
        }
        int ipos = createPos(index, false);
        removePos(ipos, 1);
        releasePos(ipos);
        return true;
    }

    public boolean removeAll(Collection c) {
        boolean changed = false;
        int iter = startPos();
        while (true) {
            int nextPos = nextPos(iter);
            iter = nextPos;
            if (nextPos == 0) {
                return changed;
            }
            if (c.contains(getPosPrevious(iter))) {
                removePos(iter, -1);
                changed = true;
            }
        }
    }

    public boolean retainAll(Collection c) {
        boolean changed = false;
        int iter = startPos();
        while (true) {
            int nextPos = nextPos(iter);
            iter = nextPos;
            if (nextPos == 0) {
                return changed;
            }
            if (!c.contains(getPosPrevious(iter))) {
                removePos(iter, -1);
                changed = true;
            }
        }
    }

    public void clear() {
        removePos(startPos(), endPos());
    }

    /* access modifiers changed from: protected */
    public boolean isAfterPos(int ipos) {
        return (ipos & 1) != 0;
    }

    public int createRelativePos(int pos, int delta, boolean isAfter) {
        return createPos(nextIndex(pos) + delta, isAfter);
    }

    public int startPos() {
        return 0;
    }

    public int endPos() {
        return -1;
    }

    /* access modifiers changed from: protected */
    public void releasePos(int ipos) {
    }

    public int copyPos(int ipos) {
        return ipos;
    }

    /* access modifiers changed from: protected */
    public int getIndexDifference(int ipos1, int ipos0) {
        return nextIndex(ipos1) - nextIndex(ipos0);
    }

    /* access modifiers changed from: protected */
    public int nextIndex(int ipos) {
        return getIndexDifference(ipos, startPos());
    }

    /* access modifiers changed from: protected */
    public int fromEndIndex(int ipos) {
        return size() - nextIndex(ipos);
    }

    /* access modifiers changed from: protected */
    public int getContainingSequenceSize(int ipos) {
        return size();
    }

    public boolean hasNext(int ipos) {
        return nextIndex(ipos) != size();
    }

    public int getNextKind(int ipos) {
        return hasNext(ipos) ? 32 : 0;
    }

    public String getNextTypeName(int ipos) {
        return null;
    }

    public Object getNextTypeObject(int ipos) {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean hasPrevious(int ipos) {
        return nextIndex(ipos) != 0;
    }

    public int nextPos(int ipos) {
        if (!hasNext(ipos)) {
            return 0;
        }
        int next = createRelativePos(ipos, 1, true);
        releasePos(ipos);
        return next;
    }

    public int previousPos(int ipos) {
        if (!hasPrevious(ipos)) {
            return 0;
        }
        int next = createRelativePos(ipos, -1, false);
        releasePos(ipos);
        return next;
    }

    public final boolean gotoChildrenStart(TreePosition pos) {
        int ipos = firstChildPos(pos.getPos());
        if (ipos == 0) {
            return false;
        }
        pos.push(this, ipos);
        return true;
    }

    public int firstChildPos(int ipos) {
        return 0;
    }

    public int firstChildPos(int ipos, ItemPredicate predicate) {
        int child = firstChildPos(ipos);
        if (child == 0) {
            return 0;
        }
        if (predicate.isInstancePos(this, child)) {
            return child;
        }
        return nextMatching(child, predicate, endPos(), false);
    }

    public int firstAttributePos(int ipos) {
        return 0;
    }

    public int parentPos(int ipos) {
        return endPos();
    }

    /* access modifiers changed from: protected */
    public boolean gotoParent(TreePosition pos) {
        if (pos.depth < 0) {
            return false;
        }
        pos.pop();
        return true;
    }

    public int getAttributeLength() {
        return 0;
    }

    public Object getAttribute(int index) {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean gotoAttributesStart(TreePosition pos) {
        return false;
    }

    public Object getPosNext(int ipos) {
        if (!hasNext(ipos)) {
            return Sequence.eofValue;
        }
        return get(nextIndex(ipos));
    }

    public Object getPosPrevious(int ipos) {
        int index = nextIndex(ipos);
        if (index <= 0) {
            return Sequence.eofValue;
        }
        return get(index - 1);
    }

    /* access modifiers changed from: protected */
    public void setPosNext(int ipos, Object value) {
        int index = nextIndex(ipos);
        if (index < size()) {
            set(index, value);
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    /* access modifiers changed from: protected */
    public void setPosPrevious(int ipos, Object value) {
        int index = nextIndex(ipos);
        if (index != 0) {
            set(index - 1, value);
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public final int nextIndex(SeqPosition pos) {
        return nextIndex(pos.ipos);
    }

    public boolean equals(int ipos1, int ipos2) {
        return compare(ipos1, ipos2) == 0;
    }

    public int compare(int ipos1, int ipos2) {
        int i1 = nextIndex(ipos1);
        int i2 = nextIndex(ipos2);
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }

    public final int compare(SeqPosition i1, SeqPosition i2) {
        return compare(i1.ipos, i2.ipos);
    }

    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int it = startPos();
        int i = 0;
        while (true) {
            int nextPos = nextPos(it);
            it = nextPos;
            if (nextPos == 0) {
                return arr;
            }
            arr[i] = getPosPrevious(it);
            i++;
        }
    }

    public Object[] toArray(Object[] arr) {
        int alen = arr.length;
        int len = size();
        if (len > alen) {
            arr = (Object[]) Array.newInstance(arr.getClass().getComponentType(), len);
            alen = len;
        }
        int it = startPos();
        int i = 0;
        while (true) {
            int nextPos = nextPos(it);
            it = nextPos;
            if (nextPos == 0) {
                break;
            }
            arr[i] = getPosPrevious(it);
            i++;
        }
        if (len < alen) {
            arr[len] = null;
        }
        return arr;
    }

    public int stableCompare(AbstractSequence other) {
        int id1 = System.identityHashCode(this);
        int id2 = System.identityHashCode(other);
        if (id1 < id2) {
            return -1;
        }
        return id1 > id2 ? 1 : 0;
    }

    public static int compare(AbstractSequence seq1, int pos1, AbstractSequence seq2, int pos2) {
        if (seq1 == seq2) {
            return seq1.compare(pos1, pos2);
        }
        return seq1.stableCompare(seq2);
    }

    public int hashCode() {
        int hash = 1;
        int i = startPos();
        while (true) {
            int nextPos = nextPos(i);
            i = nextPos;
            if (nextPos == 0) {
                return hash;
            }
            Object obj = getPosPrevious(i);
            hash = (hash * 31) + (obj == null ? 0 : obj.hashCode());
        }
    }

    public boolean equals(Object o) {
        if ((this instanceof List) && (o instanceof List)) {
            Iterator it1 = iterator();
            Iterator it2 = ((List) o).iterator();
            while (true) {
                boolean more1 = it1.hasNext();
                if (more1 != it2.hasNext()) {
                    return false;
                }
                if (!more1) {
                    return true;
                }
                Object e1 = it1.next();
                Object e2 = it2.next();
                if (e1 == null) {
                    if (e2 != null) {
                        return false;
                    }
                } else if (!e1.equals(e2)) {
                    return false;
                }
            }
        } else if (this == o) {
            return true;
        } else {
            return false;
        }
    }

    public Sequence subSequence(SeqPosition start, SeqPosition end) {
        return subSequencePos(start.ipos, end.ipos);
    }

    /* access modifiers changed from: protected */
    public Sequence subSequencePos(int ipos0, int ipos1) {
        return new SubSequence(this, ipos0, ipos1);
    }

    public List subList(int fromIx, int toIx) {
        return subSequencePos(createPos(fromIx, false), createPos(toIx, true));
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int next = nextPos(ipos);
        if (next == 0) {
            return false;
        }
        consumePosRange(ipos, next, out);
        return true;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int it = copyPos(iposStart);
            while (!equals(it, iposEnd)) {
                if (hasNext(it)) {
                    out.writeObject(getPosNext(it));
                    it = nextPos(it);
                } else {
                    throw new RuntimeException();
                }
            }
            releasePos(it);
        }
    }

    public void consume(Consumer out) {
        boolean isSequence = this instanceof Sequence;
        if (isSequence) {
            out.startElement("#sequence");
        }
        consumePosRange(startPos(), endPos(), out);
        if (isSequence) {
            out.endElement();
        }
    }

    public void toString(String sep, StringBuffer sbuf) {
        boolean seen = false;
        int i = startPos();
        while (true) {
            int nextPos = nextPos(i);
            i = nextPos;
            if (nextPos != 0) {
                if (seen) {
                    sbuf.append(sep);
                } else {
                    seen = true;
                }
                sbuf.append(getPosPrevious(i));
            } else {
                return;
            }
        }
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer(100);
        if (this instanceof Sequence) {
            sbuf.append('[');
        }
        toString(", ", sbuf);
        if (this instanceof Sequence) {
            sbuf.append(']');
        }
        return sbuf.toString();
    }
}
