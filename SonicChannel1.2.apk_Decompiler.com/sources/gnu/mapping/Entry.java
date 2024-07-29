package gnu.mapping;

import java.lang.ref.WeakReference;

/* compiled from: Table2D */
class Entry {
    Entry chain;
    Object key1;
    Object key2;
    Object value;

    Entry() {
    }

    public Object getKey1() {
        Object obj = this.key1;
        if (obj instanceof WeakReference) {
            return ((WeakReference) obj).get();
        }
        return obj;
    }

    public Object getKey2() {
        Object obj = this.key2;
        if (obj instanceof WeakReference) {
            return ((WeakReference) obj).get();
        }
        return obj;
    }

    public boolean matches(Object key12, Object key22) {
        return key12 == getKey1() && key22 == getKey2();
    }

    public Object getValue() {
        Object obj = this.value;
        if (obj == this) {
            return null;
        }
        return obj;
    }
}
