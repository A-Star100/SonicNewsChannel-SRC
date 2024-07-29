package gnu.text;

import gnu.lists.CharSeq;
import java.io.Reader;

public class QueueReader extends Reader implements Appendable {
    boolean EOFseen;
    char[] buffer;
    int limit;
    int mark;
    int pos;
    int readAheadLimit;

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readAheadLimit2) {
        this.readAheadLimit = readAheadLimit2;
        this.mark = this.pos;
    }

    public synchronized void reset() {
        if (this.readAheadLimit > 0) {
            this.pos = this.mark;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void resize(int r7) {
        /*
            r6 = this;
            int r0 = r6.limit
            int r1 = r6.pos
            int r2 = r0 - r1
            int r3 = r6.readAheadLimit
            if (r3 <= 0) goto L_0x0013
            int r4 = r6.mark
            int r5 = r1 - r4
            if (r5 > r3) goto L_0x0013
            int r2 = r0 - r4
            goto L_0x0015
        L_0x0013:
            r6.mark = r1
        L_0x0015:
            char[] r0 = r6.buffer
            int r1 = r0.length
            int r3 = r2 + r7
            if (r1 >= r3) goto L_0x0022
            int r1 = r2 * 2
            int r1 = r1 + r7
            char[] r1 = new char[r1]
            goto L_0x0023
        L_0x0022:
            r1 = r0
        L_0x0023:
            int r3 = r6.mark
            r4 = 0
            java.lang.System.arraycopy(r0, r3, r1, r4, r2)
            r6.buffer = r1
            int r0 = r6.pos
            int r3 = r6.mark
            int r0 = r0 - r3
            r6.pos = r0
            r6.mark = r4
            r6.limit = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.QueueReader.resize(int):void");
    }

    public QueueReader append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    public synchronized QueueReader append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        int len = end - start;
        reserveSpace(len);
        int sz = this.limit;
        char[] d = this.buffer;
        if (csq instanceof String) {
            ((String) csq).getChars(start, end, d, sz);
        } else if (csq instanceof CharSeq) {
            ((CharSeq) csq).getChars(start, end, d, sz);
        } else {
            int j = sz;
            int i = start;
            while (i < end) {
                d[j] = csq.charAt(i);
                i++;
                j++;
            }
        }
        this.limit = sz + len;
        notifyAll();
        return this;
    }

    public void append(char[] chars) {
        append(chars, 0, chars.length);
    }

    public synchronized void append(char[] chars, int off, int len) {
        reserveSpace(len);
        System.arraycopy(chars, off, this.buffer, this.limit, len);
        this.limit += len;
        notifyAll();
    }

    public synchronized QueueReader append(char ch) {
        reserveSpace(1);
        char[] cArr = this.buffer;
        int i = this.limit;
        this.limit = i + 1;
        cArr[i] = ch;
        notifyAll();
        return this;
    }

    public synchronized void appendEOF() {
        this.EOFseen = true;
    }

    /* access modifiers changed from: protected */
    public void reserveSpace(int len) {
        char[] cArr = this.buffer;
        if (cArr == null) {
            this.buffer = new char[(len + 100)];
        } else if (cArr.length < this.limit + len) {
            resize(len);
        }
    }

    public synchronized boolean ready() {
        return this.pos < this.limit || this.EOFseen;
    }

    public void checkAvailable() {
    }

    public synchronized int read() {
        while (true) {
            int i = this.pos;
            if (i < this.limit) {
                char[] cArr = this.buffer;
                this.pos = i + 1;
                return cArr[i];
            } else if (this.EOFseen) {
                return -1;
            } else {
                checkAvailable();
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x001c, code lost:
        r1 = r1 - r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x001d, code lost:
        if (r6 <= r1) goto L_0x0020;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x001f, code lost:
        r6 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        java.lang.System.arraycopy(r3.buffer, r0, r4, r5, r6);
        r3.pos += r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x002b, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int read(char[] r4, int r5, int r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r6 != 0) goto L_0x0006
            r0 = 0
            monitor-exit(r3)
            return r0
        L_0x0006:
            int r0 = r3.pos     // Catch:{ all -> 0x002c }
            int r1 = r3.limit     // Catch:{ all -> 0x002c }
            if (r0 < r1) goto L_0x001c
            boolean r0 = r3.EOFseen     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x0013
            r0 = -1
            monitor-exit(r3)
            return r0
        L_0x0013:
            r3.checkAvailable()     // Catch:{ all -> 0x002c }
            r3.wait()     // Catch:{ InterruptedException -> 0x001a }
            goto L_0x0006
        L_0x001a:
            r0 = move-exception
            goto L_0x0006
        L_0x001c:
            int r1 = r1 - r0
            if (r6 <= r1) goto L_0x0020
            r6 = r1
        L_0x0020:
            char[] r2 = r3.buffer     // Catch:{ all -> 0x002c }
            java.lang.System.arraycopy(r2, r0, r4, r5, r6)     // Catch:{ all -> 0x002c }
            int r0 = r3.pos     // Catch:{ all -> 0x002c }
            int r0 = r0 + r6
            r3.pos = r0     // Catch:{ all -> 0x002c }
            monitor-exit(r3)
            return r6
        L_0x002c:
            r4 = move-exception
            monitor-exit(r3)
            goto L_0x0030
        L_0x002f:
            throw r4
        L_0x0030:
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.QueueReader.read(char[], int, int):int");
    }

    public synchronized void close() {
        this.pos = 0;
        this.limit = 0;
        this.mark = 0;
        this.EOFseen = true;
        this.buffer = null;
    }
}
