package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class F64Vector extends SimpleVector implements Externalizable, Comparable {
    protected static double[] empty = new double[0];
    double[] data;

    public F64Vector() {
        this.data = empty;
    }

    public F64Vector(int size, double value) {
        double[] array = new double[size];
        this.data = array;
        this.size = size;
        while (true) {
            size--;
            if (size >= 0) {
                array[size] = value;
            } else {
                return;
            }
        }
    }

    public F64Vector(int size) {
        this.data = new double[size];
        this.size = size;
    }

    public F64Vector(double[] data2) {
        this.data = data2;
        this.size = data2.length;
    }

    public F64Vector(Sequence seq) {
        this.data = new double[seq.size()];
        addAll(seq);
    }

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        double[] dArr = this.data;
        int oldLength = dArr.length;
        if (oldLength != length) {
            double[] tmp = new double[length];
            System.arraycopy(dArr, 0, tmp, 0, oldLength < length ? oldLength : length);
            this.data = tmp;
        }
    }

    /* access modifiers changed from: protected */
    public Object getBuffer() {
        return this.data;
    }

    public final double doubleAt(int index) {
        if (index < this.size) {
            return this.data[index];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final double doubleAtBuffer(int index) {
        return this.data[index];
    }

    public final Object get(int index) {
        if (index <= this.size) {
            return Convert.toObject(this.data[index]);
        }
        throw new IndexOutOfBoundsException();
    }

    public final Object getBuffer(int index) {
        return Convert.toObject(this.data[index]);
    }

    public final int intAtBuffer(int index) {
        return (int) this.data[index];
    }

    public final void setDoubleAt(int index, double value) {
        if (index <= this.size) {
            this.data[index] = value;
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public final void setDoubleAtBuffer(int index, double value) {
        this.data[index] = value;
    }

    public final Object setBuffer(int index, Object value) {
        Object old = Convert.toObject(this.data[index]);
        this.data[index] = Convert.toDouble(value);
        return old;
    }

    /* access modifiers changed from: protected */
    public void clearBuffer(int start, int count) {
        while (true) {
            count--;
            if (count >= 0) {
                this.data[start] = 0.0d;
                start++;
            } else {
                return;
            }
        }
    }

    public int getElementKind() {
        return 26;
    }

    public String getTag() {
        return "f64";
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeDouble(this.data[index]);
        return true;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeDouble(this.data[i]);
            }
        }
    }

    public int compareTo(Object obj) {
        F64Vector vec2 = (F64Vector) obj;
        double[] arr1 = this.data;
        double[] arr2 = vec2.data;
        int n1 = this.size;
        int n2 = vec2.size;
        int n = n1 > n2 ? n2 : n1;
        for (int i = 0; i < n; i++) {
            double v1 = arr1[i];
            double v2 = arr2[i];
            if (v1 != v2) {
                return v1 > v2 ? 1 : -1;
            }
        }
        return n1 - n2;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeDouble(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        double[] data2 = new double[size];
        for (int i = 0; i < size; i++) {
            data2[i] = in.readDouble();
        }
        this.data = data2;
        this.size = size;
    }
}
