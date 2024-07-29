package gnu.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class LineBufferedReader extends Reader {
    public static final int BUFFER_SIZE = 8192;
    private static final int CONVERT_CR = 1;
    private static final int DONT_KEEP_FULL_LINES = 8;
    private static final int PREV_WAS_CR = 4;
    private static final int USER_BUFFER = 2;
    public char[] buffer;
    private int flags;
    int highestPos;
    protected Reader in;
    public int limit;
    protected int lineNumber;
    private int lineStartPos;
    protected int markPos;
    Path path;
    public int pos;
    protected int readAheadLimit = 0;
    public char readState = 10;

    public void close() throws IOException {
        this.in.close();
    }

    public char getReadState() {
        return this.readState;
    }

    public void setKeepFullLines(boolean keep) {
        if (keep) {
            this.flags &= -9;
        } else {
            this.flags |= 8;
        }
    }

    public final boolean getConvertCR() {
        return (this.flags & 1) != 0;
    }

    public final void setConvertCR(boolean convertCR) {
        if (convertCR) {
            this.flags |= 1;
        } else {
            this.flags &= -2;
        }
    }

    public LineBufferedReader(InputStream in2) {
        this.in = new InputStreamReader(in2);
    }

    public LineBufferedReader(Reader in2) {
        this.in = in2;
    }

    public void lineStart(boolean revisited) throws IOException {
    }

    public int fill(int len) throws IOException {
        return this.in.read(this.buffer, this.pos, len);
    }

    private void clearMark() {
        int i = 0;
        this.readAheadLimit = 0;
        int i2 = this.lineStartPos;
        if (i2 >= 0) {
            i = i2;
        }
        while (true) {
            i++;
            if (i < this.pos) {
                char ch = this.buffer[i - 1];
                if (ch == 10 || (ch == 13 && (!getConvertCR() || this.buffer[i] != 10))) {
                    this.lineNumber++;
                    this.lineStartPos = i;
                }
            } else {
                return;
            }
        }
    }

    public void setBuffer(char[] buffer2) throws IOException {
        if (buffer2 == null) {
            char[] cArr = this.buffer;
            if (cArr != null) {
                char[] buffer3 = new char[cArr.length];
                System.arraycopy(cArr, 0, buffer3, 0, cArr.length);
                this.buffer = buffer3;
            }
            this.flags &= -3;
        } else if (this.limit - this.pos <= buffer2.length) {
            this.flags |= 2;
            reserve(buffer2, 0);
        } else {
            throw new IOException("setBuffer - too short");
        }
    }

    private void reserve(char[] buffer2, int reserve) throws IOException {
        int saveStart;
        int i;
        int i2;
        int reserve2 = reserve + this.limit;
        if (reserve2 <= buffer2.length) {
            saveStart = 0;
        } else {
            saveStart = this.pos;
            int i3 = this.readAheadLimit;
            if (i3 > 0 && (i = this.markPos) < (i2 = this.pos)) {
                if (i2 - i > i3 || ((this.flags & 2) != 0 && reserve2 - i > buffer2.length)) {
                    clearMark();
                } else {
                    saveStart = this.markPos;
                }
            }
            int reserve3 = reserve2 - buffer2.length;
            if (reserve3 > saveStart || (saveStart > this.lineStartPos && (this.flags & 8) == 0)) {
                int i4 = this.lineStartPos;
                if (reserve3 <= i4 && saveStart > i4) {
                    saveStart = this.lineStartPos;
                } else if ((this.flags & 2) != 0) {
                    saveStart -= (saveStart - reserve3) >> 2;
                } else {
                    if (i4 >= 0) {
                        saveStart = this.lineStartPos;
                    }
                    buffer2 = new char[(buffer2.length * 2)];
                }
            }
            this.lineStartPos -= saveStart;
            this.limit -= saveStart;
            this.markPos -= saveStart;
            this.pos -= saveStart;
            this.highestPos -= saveStart;
        }
        int i5 = this.limit;
        if (i5 > 0) {
            System.arraycopy(this.buffer, saveStart, buffer2, 0, i5);
        }
        this.buffer = buffer2;
    }

    public int read() throws IOException {
        char prev;
        int i = this.pos;
        if (i > 0) {
            prev = this.buffer[i - 1];
        } else if ((this.flags & 4) != 0) {
            prev = 13;
        } else if (this.lineStartPos >= 0) {
            prev = 10;
        } else {
            prev = 0;
        }
        if (prev == 13 || prev == 10) {
            if (this.lineStartPos < i && (this.readAheadLimit == 0 || i <= this.markPos)) {
                this.lineStartPos = i;
                this.lineNumber++;
            }
            boolean revisited = i < this.highestPos;
            if (prev != 10 || (i > 1 ? this.buffer[i - 2] != 13 : (this.flags & 4) == 0)) {
                lineStart(revisited);
            }
            if (!revisited) {
                this.highestPos = this.pos + 1;
            }
        }
        int i2 = this.pos;
        int i3 = this.limit;
        if (i2 >= i3) {
            char[] cArr = this.buffer;
            if (cArr == null) {
                this.buffer = new char[8192];
            } else if (i3 == cArr.length) {
                reserve(cArr, 1);
            }
            int i4 = this.pos;
            if (i4 == 0) {
                if (prev == 13) {
                    this.flags |= 4;
                } else {
                    this.flags &= -5;
                }
            }
            int readCount = fill(this.buffer.length - i4);
            if (readCount <= 0) {
                return -1;
            }
            this.limit += readCount;
        }
        char[] cArr2 = this.buffer;
        int i5 = this.pos;
        int i6 = i5 + 1;
        this.pos = i6;
        char ch = cArr2[i5];
        if (ch == 10) {
            if (prev == 13) {
                int i7 = this.lineStartPos;
                if (i7 == i6 - 1) {
                    this.lineNumber--;
                    this.lineStartPos = i7 - 1;
                }
                if (getConvertCR()) {
                    return read();
                }
            }
        } else if (ch != 13 || !getConvertCR()) {
            return ch;
        } else {
            return 10;
        }
        return ch;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(char[] r9, int r10, int r11) throws java.io.IOException {
        /*
            r8 = this;
            int r0 = r8.pos
            int r1 = r8.limit
            if (r0 < r1) goto L_0x0008
            r0 = 0
            goto L_0x0020
        L_0x0008:
            if (r0 <= 0) goto L_0x0011
            char[] r1 = r8.buffer
            int r0 = r0 + -1
            char r0 = r1[r0]
            goto L_0x0020
        L_0x0011:
            int r0 = r8.flags
            r0 = r0 & 4
            if (r0 != 0) goto L_0x001e
            int r0 = r8.lineStartPos
            if (r0 < 0) goto L_0x001c
            goto L_0x001e
        L_0x001c:
            r0 = 0
            goto L_0x0020
        L_0x001e:
            r0 = 10
        L_0x0020:
            r1 = r11
        L_0x0021:
            if (r1 <= 0) goto L_0x0075
            int r2 = r8.pos
            int r3 = r8.limit
            if (r2 >= r3) goto L_0x0058
            r4 = 10
            if (r0 == r4) goto L_0x0058
            r5 = 13
            if (r0 != r5) goto L_0x0032
            goto L_0x0058
        L_0x0032:
            int r2 = r8.pos
            int r3 = r8.limit
            int r6 = r3 - r2
            if (r1 >= r6) goto L_0x003c
            int r3 = r2 + r1
        L_0x003c:
            if (r2 >= r3) goto L_0x0050
            char[] r6 = r8.buffer
            char r0 = r6[r2]
            if (r0 == r4) goto L_0x0050
            if (r0 != r5) goto L_0x0047
            goto L_0x0050
        L_0x0047:
            int r6 = r10 + 1
            char r7 = (char) r0
            r9[r10] = r7
            int r2 = r2 + 1
            r10 = r6
            goto L_0x003c
        L_0x0050:
            int r4 = r8.pos
            int r4 = r2 - r4
            int r1 = r1 - r4
            r8.pos = r2
            goto L_0x0021
        L_0x0058:
            if (r2 < r3) goto L_0x005f
            if (r1 >= r11) goto L_0x005f
            int r2 = r11 - r1
            return r2
        L_0x005f:
            int r0 = r8.read()
            if (r0 >= 0) goto L_0x006c
            int r11 = r11 - r1
            if (r11 > 0) goto L_0x006a
            r2 = -1
            goto L_0x006b
        L_0x006a:
            r2 = r11
        L_0x006b:
            return r2
        L_0x006c:
            int r2 = r10 + 1
            char r3 = (char) r0
            r9[r10] = r3
            int r1 = r1 + -1
            r10 = r2
            goto L_0x0021
        L_0x0075:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.read(char[], int, int):int");
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path2) {
        this.path = path2;
    }

    public String getName() {
        Path path2 = this.path;
        if (path2 == null) {
            return null;
        }
        return path2.toString();
    }

    public void setName(Object name) {
        setPath(Path.valueOf(name));
    }

    public int getLineNumber() {
        int lineno = this.lineNumber;
        if (this.readAheadLimit == 0) {
            int i = this.pos;
            if (i <= 0 || i <= this.lineStartPos) {
                return lineno;
            }
            char prev = this.buffer[i - 1];
            if (prev == 10 || prev == 13) {
                return lineno + 1;
            }
            return lineno;
        }
        char[] cArr = this.buffer;
        int i2 = this.lineStartPos;
        if (i2 < 0) {
            i2 = 0;
        }
        return lineno + countLines(cArr, i2, this.pos);
    }

    public void setLineNumber(int lineNumber2) {
        this.lineNumber += lineNumber2 - getLineNumber();
    }

    public void incrLineNumber(int lineDelta, int lineStartPos2) {
        this.lineNumber += lineDelta;
        this.lineStartPos = lineStartPos2;
    }

    public int getColumnNumber() {
        int i;
        char prev;
        int i2 = this.pos;
        int i3 = 0;
        if (i2 > 0 && ((prev = this.buffer[i2 - 1]) == 10 || prev == 13)) {
            return 0;
        }
        if (this.readAheadLimit <= 0) {
            return i2 - this.lineStartPos;
        }
        int i4 = this.lineStartPos;
        if (i4 >= 0) {
            i3 = i4;
        }
        int start = i3;
        while (true) {
            i = this.pos;
            if (i3 >= i) {
                break;
            }
            int i5 = i3 + 1;
            char ch = this.buffer[i3];
            if (ch == 10 || ch == 13) {
                start = i5;
            }
            i3 = i5;
        }
        int col = i - start;
        int i6 = this.lineStartPos;
        if (i6 < 0) {
            return col - i6;
        }
        return col;
    }

    public boolean markSupported() {
        return true;
    }

    public synchronized void mark(int readAheadLimit2) {
        if (this.readAheadLimit > 0) {
            clearMark();
        }
        this.readAheadLimit = readAheadLimit2;
        this.markPos = this.pos;
    }

    public void reset() throws IOException {
        if (this.readAheadLimit > 0) {
            int i = this.pos;
            if (i > this.highestPos) {
                this.highestPos = i;
            }
            this.pos = this.markPos;
            this.readAheadLimit = 0;
            return;
        }
        throw new IOException("mark invalid");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005c, code lost:
        r7.append(r6.buffer, r1, r2 - r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void readLine(java.lang.StringBuffer r7, char r8) throws java.io.IOException {
        /*
            r6 = this;
        L_0x0000:
            int r0 = r6.read()
            if (r0 >= 0) goto L_0x0007
            return
        L_0x0007:
            int r1 = r6.pos
            int r1 = r1 + -1
            r6.pos = r1
        L_0x000d:
            int r2 = r6.pos
            int r3 = r6.limit
            if (r2 >= r3) goto L_0x005c
            char[] r3 = r6.buffer
            int r4 = r2 + 1
            r6.pos = r4
            char r0 = r3[r2]
            r2 = 13
            r5 = 10
            if (r0 == r2) goto L_0x0023
            if (r0 != r5) goto L_0x000d
        L_0x0023:
            int r4 = r4 + -1
            int r4 = r4 - r1
            r7.append(r3, r1, r4)
            r3 = 80
            if (r8 != r3) goto L_0x0034
            int r2 = r6.pos
            int r2 = r2 + -1
            r6.pos = r2
            return
        L_0x0034:
            boolean r3 = r6.getConvertCR()
            r4 = 73
            if (r3 != 0) goto L_0x0056
            if (r0 != r5) goto L_0x003f
            goto L_0x0056
        L_0x003f:
            if (r8 == r4) goto L_0x0044
            r7.append(r2)
        L_0x0044:
            int r0 = r6.read()
            if (r0 != r5) goto L_0x0050
            if (r8 == r4) goto L_0x005b
            r7.append(r5)
            goto L_0x005b
        L_0x0050:
            if (r0 < 0) goto L_0x005b
            r6.unread_quick()
            goto L_0x005b
        L_0x0056:
            if (r8 == r4) goto L_0x005b
            r7.append(r5)
        L_0x005b:
            return
        L_0x005c:
            char[] r3 = r6.buffer
            int r2 = r2 - r1
            r7.append(r3, r1, r2)
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.readLine(java.lang.StringBuffer, char):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        r6 = r6 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        if (r0 == 10) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        if (getConvertCR() != false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r1 = r7.pos;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        if (r1 < r7.limit) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        r7.pos = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0040, code lost:
        if (r7.buffer[r1] != 10) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0042, code lost:
        r7.pos = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004f, code lost:
        return new java.lang.String(r7.buffer, r3, r6 - r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readLine() throws java.io.IOException {
        /*
            r7 = this;
            int r0 = r7.read()
            if (r0 >= 0) goto L_0x0008
            r1 = 0
            return r1
        L_0x0008:
            r1 = 13
            if (r0 == r1) goto L_0x0069
            r2 = 10
            if (r0 != r2) goto L_0x0011
            goto L_0x0069
        L_0x0011:
            int r3 = r7.pos
            int r3 = r3 + -1
        L_0x0015:
            int r4 = r7.pos
            int r5 = r7.limit
            if (r4 >= r5) goto L_0x0050
            char[] r5 = r7.buffer
            int r6 = r4 + 1
            r7.pos = r6
            char r0 = r5[r4]
            if (r0 == r1) goto L_0x0027
            if (r0 != r2) goto L_0x0015
        L_0x0027:
            int r6 = r6 + -1
            if (r0 == r2) goto L_0x0046
            boolean r1 = r7.getConvertCR()
            if (r1 != 0) goto L_0x0046
            int r1 = r7.pos
            int r4 = r7.limit
            if (r1 < r4) goto L_0x003c
            int r1 = r1 + -1
            r7.pos = r1
            goto L_0x0050
        L_0x003c:
            char[] r4 = r7.buffer
            char r4 = r4[r1]
            if (r4 != r2) goto L_0x0046
            int r1 = r1 + 1
            r7.pos = r1
        L_0x0046:
            java.lang.String r1 = new java.lang.String
            char[] r2 = r7.buffer
            int r4 = r6 - r3
            r1.<init>(r2, r3, r4)
            return r1
        L_0x0050:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r2 = 100
            r1.<init>(r2)
            char[] r2 = r7.buffer
            int r4 = r7.pos
            int r4 = r4 - r3
            r1.append(r2, r3, r4)
            r2 = 73
            r7.readLine(r1, r2)
            java.lang.String r2 = r1.toString()
            return r2
        L_0x0069:
            java.lang.String r1 = ""
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.readLine():java.lang.String");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int skip(int r8) throws java.io.IOException {
        /*
            r7 = this;
            if (r8 >= 0) goto L_0x0012
            int r0 = -r8
        L_0x0003:
            if (r0 <= 0) goto L_0x000f
            int r1 = r7.pos
            if (r1 <= 0) goto L_0x000f
            r7.unread()
            int r0 = r0 + -1
            goto L_0x0003
        L_0x000f:
            int r1 = r8 + r0
            return r1
        L_0x0012:
            r0 = r8
            int r1 = r7.pos
            int r2 = r7.limit
            if (r1 < r2) goto L_0x001b
            r1 = 0
            goto L_0x0033
        L_0x001b:
            if (r1 <= 0) goto L_0x0024
            char[] r2 = r7.buffer
            int r1 = r1 + -1
            char r1 = r2[r1]
            goto L_0x0033
        L_0x0024:
            int r1 = r7.flags
            r1 = r1 & 4
            if (r1 != 0) goto L_0x0031
            int r1 = r7.lineStartPos
            if (r1 < 0) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            r1 = 0
            goto L_0x0033
        L_0x0031:
            r1 = 10
        L_0x0033:
            if (r0 <= 0) goto L_0x0070
            r2 = 10
            if (r1 == r2) goto L_0x0064
            r3 = 13
            if (r1 == r3) goto L_0x0064
            int r4 = r7.pos
            int r5 = r7.limit
            if (r4 < r5) goto L_0x0044
            goto L_0x0064
        L_0x0044:
            int r4 = r7.pos
            int r5 = r7.limit
            int r6 = r5 - r4
            if (r0 >= r6) goto L_0x004e
            int r5 = r4 + r0
        L_0x004e:
            if (r4 >= r5) goto L_0x005c
            char[] r6 = r7.buffer
            char r1 = r6[r4]
            if (r1 == r2) goto L_0x005c
            if (r1 != r3) goto L_0x0059
            goto L_0x005c
        L_0x0059:
            int r4 = r4 + 1
            goto L_0x004e
        L_0x005c:
            int r2 = r7.pos
            int r2 = r4 - r2
            int r0 = r0 - r2
            r7.pos = r4
            goto L_0x0033
        L_0x0064:
            int r1 = r7.read()
            if (r1 >= 0) goto L_0x006d
            int r2 = r8 - r0
            return r2
        L_0x006d:
            int r0 = r0 + -1
            goto L_0x0033
        L_0x0070:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.skip(int):int");
    }

    public boolean ready() throws IOException {
        return this.pos < this.limit || this.in.ready();
    }

    public final void skip_quick() throws IOException {
        this.pos++;
    }

    public void skip() throws IOException {
        read();
    }

    static int countLines(char[] buffer2, int start, int limit2) {
        int count = 0;
        char prev = 0;
        for (int i = start; i < limit2; i++) {
            char ch = buffer2[i];
            if ((ch == 10 && prev != 13) || ch == 13) {
                count++;
            }
            prev = ch;
        }
        return count;
    }

    public void skipRestOfLine() throws IOException {
        int c;
        do {
            c = read();
            if (c >= 0) {
                if (c == 13) {
                    int c2 = read();
                    if (c2 >= 0 && c2 != 10) {
                        unread();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        } while (c != 10);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
        r0 = r0 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void unread() throws java.io.IOException {
        /*
            r6 = this;
            int r0 = r6.pos
            if (r0 == 0) goto L_0x004b
            int r0 = r0 + -1
            r6.pos = r0
            char[] r1 = r6.buffer
            char r1 = r1[r0]
            r2 = 13
            r3 = 10
            if (r1 == r3) goto L_0x0014
            if (r1 != r2) goto L_0x004a
        L_0x0014:
            if (r0 <= 0) goto L_0x002c
            if (r1 != r3) goto L_0x002c
            boolean r0 = r6.getConvertCR()
            if (r0 == 0) goto L_0x002c
            char[] r0 = r6.buffer
            int r4 = r6.pos
            int r5 = r4 + -1
            char r0 = r0[r5]
            if (r0 != r2) goto L_0x002c
            int r4 = r4 + -1
            r6.pos = r4
        L_0x002c:
            int r0 = r6.pos
            int r4 = r6.lineStartPos
            if (r0 >= r4) goto L_0x004a
            int r0 = r6.lineNumber
            int r0 = r0 + -1
            r6.lineNumber = r0
            int r0 = r6.pos
        L_0x003a:
            if (r0 <= 0) goto L_0x0048
            char[] r4 = r6.buffer
            int r0 = r0 + -1
            char r1 = r4[r0]
            if (r1 == r2) goto L_0x0046
            if (r1 != r3) goto L_0x003a
        L_0x0046:
            int r0 = r0 + 1
        L_0x0048:
            r6.lineStartPos = r0
        L_0x004a:
            return
        L_0x004b:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "unread too much"
            r0.<init>(r1)
            goto L_0x0054
        L_0x0053:
            throw r0
        L_0x0054:
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.unread():void");
    }

    public void unread_quick() {
        this.pos--;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0008, code lost:
        r1 = r4.buffer;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int peek() throws java.io.IOException {
        /*
            r4 = this;
            int r0 = r4.pos
            int r1 = r4.limit
            if (r0 >= r1) goto L_0x0023
            if (r0 <= 0) goto L_0x0023
            char[] r1 = r4.buffer
            int r2 = r0 + -1
            char r2 = r1[r2]
            r3 = 10
            if (r2 == r3) goto L_0x0023
            r3 = 13
            if (r2 == r3) goto L_0x0023
            char r0 = r1[r0]
            if (r0 != r3) goto L_0x0022
            boolean r1 = r4.getConvertCR()
            if (r1 == 0) goto L_0x0022
            r0 = 10
        L_0x0022:
            return r0
        L_0x0023:
            int r0 = r4.read()
            if (r0 < 0) goto L_0x002c
            r4.unread_quick()
        L_0x002c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.LineBufferedReader.peek():int");
    }
}
