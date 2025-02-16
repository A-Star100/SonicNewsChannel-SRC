package gnu.text;

import gnu.lists.LList;
import gnu.mapping.ThreadLocation;
import java.io.IOException;
import java.io.Writer;

public class PrettyWriter extends Writer {
    private static final int BLOCK_PER_LINE_PREFIX_END = -3;
    private static final int BLOCK_PREFIX_LENGTH = -4;
    private static final int BLOCK_SECTION_COLUMN = -2;
    private static final int BLOCK_SECTION_START_LINE = -6;
    private static final int BLOCK_START_COLUMN = -1;
    private static final int BLOCK_SUFFIX_LENGTH = -5;
    private static final int LOGICAL_BLOCK_LENGTH = 6;
    public static final int NEWLINE_FILL = 70;
    public static final int NEWLINE_LINEAR = 78;
    public static final int NEWLINE_LITERAL = 76;
    public static final int NEWLINE_MANDATORY = 82;
    public static final int NEWLINE_MISER = 77;
    public static final int NEWLINE_SPACE = 83;
    static final int QITEM_BASE_SIZE = 2;
    static final int QITEM_BLOCK_END_SIZE = 2;
    static final int QITEM_BLOCK_END_TYPE = 5;
    static final int QITEM_BLOCK_START_BLOCK_END = 4;
    static final int QITEM_BLOCK_START_PREFIX = 5;
    static final int QITEM_BLOCK_START_SIZE = 7;
    static final int QITEM_BLOCK_START_SUFFIX = 6;
    static final int QITEM_BLOCK_START_TYPE = 4;
    static final int QITEM_INDENTATION_AMOUNT = 3;
    static final char QITEM_INDENTATION_BLOCK = 'B';
    static final char QITEM_INDENTATION_CURRENT = 'C';
    static final int QITEM_INDENTATION_KIND = 2;
    static final int QITEM_INDENTATION_SIZE = 4;
    static final int QITEM_INDENTATION_TYPE = 3;
    static final int QITEM_NEWLINE_KIND = 4;
    static final int QITEM_NEWLINE_SIZE = 5;
    static final int QITEM_NEWLINE_TYPE = 2;
    static final int QITEM_NOP_TYPE = 0;
    static final int QITEM_POSN = 1;
    static final int QITEM_SECTION_START_DEPTH = 2;
    static final int QITEM_SECTION_START_SECTION_END = 3;
    static final int QITEM_SECTION_START_SIZE = 4;
    static final int QITEM_TAB_COLINC = 4;
    static final int QITEM_TAB_COLNUM = 3;
    static final int QITEM_TAB_FLAGS = 2;
    static final int QITEM_TAB_IS_RELATIVE = 2;
    static final int QITEM_TAB_IS_SECTION = 1;
    static final int QITEM_TAB_SIZE = 5;
    static final int QITEM_TAB_TYPE = 6;
    static final int QITEM_TYPE_AND_SIZE = 0;
    static final int QUEUE_INIT_ALLOC_SIZE = 300;
    public static ThreadLocation indentLoc = new ThreadLocation("indent");
    public static int initialBufferSize = 126;
    public static ThreadLocation lineLengthLoc = new ThreadLocation("line-length");
    public static ThreadLocation miserWidthLoc = new ThreadLocation("miser-width");
    int blockDepth;
    int[] blocks;
    public char[] buffer;
    public int bufferFillPointer;
    int bufferOffset;
    int bufferStartColumn;
    int currentBlock;
    int lineLength = 80;
    int lineNumber;
    int miserWidth = 40;
    protected Writer out;
    public int pendingBlocksCount;
    char[] prefix;
    int prettyPrintingMode;
    int[] queueInts;
    int queueSize;
    String[] queueStrings;
    int queueTail;
    char[] suffix;
    boolean wordEndSeen;

    public PrettyWriter(Writer out2) {
        int i = initialBufferSize;
        this.buffer = new char[i];
        this.blocks = new int[60];
        this.blockDepth = 6;
        this.prefix = new char[i];
        this.suffix = new char[i];
        this.queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
        this.queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
        this.currentBlock = -1;
        this.out = out2;
        this.prettyPrintingMode = 1;
    }

    public PrettyWriter(Writer out2, int lineLength2) {
        int i = initialBufferSize;
        this.buffer = new char[i];
        this.blocks = new int[60];
        this.blockDepth = 6;
        this.prefix = new char[i];
        this.suffix = new char[i];
        this.queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
        this.queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
        this.currentBlock = -1;
        this.out = out2;
        this.lineLength = lineLength2;
        this.prettyPrintingMode = lineLength2 <= 1 ? 0 : 1;
    }

    public PrettyWriter(Writer out2, boolean prettyPrintingMode2) {
        int i = initialBufferSize;
        this.buffer = new char[i];
        this.blocks = new int[60];
        this.blockDepth = 6;
        this.prefix = new char[i];
        this.suffix = new char[i];
        this.queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
        this.queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
        this.currentBlock = -1;
        this.out = out2;
        this.prettyPrintingMode = prettyPrintingMode2;
    }

    public void setPrettyPrintingMode(int mode) {
        this.prettyPrintingMode = mode;
    }

    public int getPrettyPrintingMode() {
        return this.prettyPrintingMode;
    }

    public boolean isPrettyPrinting() {
        return this.prettyPrintingMode > 0;
    }

    public void setPrettyPrinting(boolean mode) {
        this.prettyPrintingMode = mode ^ true ? 1 : 0;
    }

    private int indexPosn(int index) {
        return this.bufferOffset + index;
    }

    private int posnIndex(int posn) {
        return posn - this.bufferOffset;
    }

    private int posnColumn(int posn) {
        return indexColumn(posnIndex(posn));
    }

    private int getQueueType(int index) {
        return this.queueInts[index] & 255;
    }

    private int getQueueSize(int index) {
        return this.queueInts[index] >> 16;
    }

    private int getSectionColumn() {
        return this.blocks[this.blockDepth - 2];
    }

    private int getStartColumn() {
        return this.blocks[this.blockDepth - 1];
    }

    private int getPerLinePrefixEnd() {
        return this.blocks[this.blockDepth - 3];
    }

    private int getPrefixLength() {
        return this.blocks[this.blockDepth - 4];
    }

    private int getSuffixLength() {
        return this.blocks[this.blockDepth - 5];
    }

    private int getSectionStartLine() {
        return this.blocks[this.blockDepth - 6];
    }

    public void writeWordEnd() {
        this.wordEndSeen = true;
    }

    public void writeWordStart() {
        if (this.wordEndSeen) {
            write(32);
        }
        this.wordEndSeen = false;
    }

    public void clearWordEnd() {
        this.wordEndSeen = false;
    }

    public void write(int ch) {
        this.wordEndSeen = false;
        if (ch != 10 || this.prettyPrintingMode <= 0) {
            ensureSpaceInBuffer(1);
            int fillPointer = this.bufferFillPointer;
            this.buffer[fillPointer] = (char) ch;
            this.bufferFillPointer = fillPointer + 1;
            if (ch == 32 && this.prettyPrintingMode > 1 && this.currentBlock < 0) {
                enqueueNewline(83);
                return;
            }
            return;
        }
        enqueueNewline(76);
    }

    public void write(String str) {
        write(str, 0, str.length());
    }

    public void write(String str, int start, int count) {
        this.wordEndSeen = false;
        while (count > 0) {
            int cnt = count;
            int available = ensureSpaceInBuffer(count);
            if (cnt > available) {
                cnt = available;
            }
            int fillPointer = this.bufferFillPointer;
            count -= cnt;
            while (true) {
                cnt--;
                if (cnt < 0) {
                    break;
                }
                int start2 = start + 1;
                char ch = str.charAt(start);
                if (ch != 10 || this.prettyPrintingMode <= 0) {
                    int fillPointer2 = fillPointer + 1;
                    this.buffer[fillPointer] = ch;
                    if (ch != ' ' || this.prettyPrintingMode <= 1 || this.currentBlock >= 0) {
                        fillPointer = fillPointer2;
                    } else {
                        this.bufferFillPointer = fillPointer2;
                        enqueueNewline(83);
                        fillPointer = this.bufferFillPointer;
                    }
                } else {
                    this.bufferFillPointer = fillPointer;
                    enqueueNewline(76);
                    fillPointer = this.bufferFillPointer;
                }
                start = start2;
            }
            this.bufferFillPointer = fillPointer;
        }
    }

    public void write(char[] str) {
        write(str, 0, str.length);
    }

    public void write(char[] str, int start, int count) {
        char c;
        this.wordEndSeen = false;
        int end = start + count;
        while (count > 0) {
            int i = start;
            while (true) {
                if (i < end) {
                    if (this.prettyPrintingMode > 0) {
                        char c2 = str[i];
                        c = c2;
                        if (c2 == 10 || (c == ' ' && this.currentBlock < 0)) {
                            write(str, start, i - start);
                            write((int) c);
                            start = i + 1;
                            count = end - start;
                        }
                    }
                    i++;
                } else {
                    do {
                        int available = ensureSpaceInBuffer(count);
                        int cnt = available < count ? available : count;
                        int fillPointer = this.bufferFillPointer;
                        int newFillPtr = fillPointer + cnt;
                        int i2 = fillPointer;
                        while (i2 < newFillPtr) {
                            this.buffer[i2] = str[start];
                            i2++;
                            start++;
                        }
                        this.bufferFillPointer = newFillPtr;
                        count -= cnt;
                    } while (count != 0);
                }
            }
            write(str, start, i - start);
            write((int) c);
            start = i + 1;
            count = end - start;
        }
    }

    private void pushLogicalBlock(int column, int perLineEnd, int prefixLength, int suffixLength, int sectionStartLine) {
        int i = this.blockDepth;
        int newLength = i + 6;
        int[] iArr = this.blocks;
        if (newLength >= iArr.length) {
            int[] newBlocks = new int[(iArr.length * 2)];
            System.arraycopy(iArr, 0, newBlocks, 0, i);
            this.blocks = newBlocks;
        }
        this.blockDepth = newLength;
        int[] iArr2 = this.blocks;
        iArr2[newLength - 1] = column;
        iArr2[newLength - 2] = column;
        iArr2[newLength - 3] = perLineEnd;
        iArr2[newLength - 4] = prefixLength;
        iArr2[newLength - 5] = suffixLength;
        iArr2[newLength - 6] = sectionStartLine;
    }

    /* access modifiers changed from: package-private */
    public void reallyStartLogicalBlock(int column, String prefix2, String suffix2) {
        int perLineEnd = getPerLinePrefixEnd();
        int prefixLength = getPrefixLength();
        int suffixLength = getSuffixLength();
        pushLogicalBlock(column, perLineEnd, prefixLength, suffixLength, this.lineNumber);
        setIndentation(column);
        if (prefix2 != null) {
            this.blocks[this.blockDepth - 3] = column;
            int plen = prefix2.length();
            prefix2.getChars(0, plen, this.suffix, column - plen);
        }
        if (suffix2 != null) {
            char[] totalSuffix = this.suffix;
            int totalSuffixLen = totalSuffix.length;
            int additional = suffix2.length();
            int newSuffixLen = suffixLength + additional;
            if (newSuffixLen > totalSuffixLen) {
                int newTotalSuffixLen = enoughSpace(totalSuffixLen, additional);
                char[] cArr = new char[newTotalSuffixLen];
                this.suffix = cArr;
                System.arraycopy(totalSuffix, totalSuffixLen - suffixLength, cArr, newTotalSuffixLen - suffixLength, suffixLength);
                totalSuffixLen = newTotalSuffixLen;
            }
            suffix2.getChars(0, additional, totalSuffix, totalSuffixLen - newSuffixLen);
            this.blocks[this.blockDepth - 5] = newSuffixLen;
        }
    }

    /* access modifiers changed from: package-private */
    public int enqueueTab(int flags, int colnum, int colinc) {
        int addr = enqueue(6, 5);
        int[] iArr = this.queueInts;
        iArr[addr + 2] = flags;
        iArr[addr + 3] = colnum;
        iArr[addr + 4] = colinc;
        return addr;
    }

    private static int enoughSpace(int current, int want) {
        int doubled = current * 2;
        int enough = ((want * 5) >> 2) + current;
        return doubled > enough ? doubled : enough;
    }

    public void setIndentation(int column) {
        char[] prefix2 = this.prefix;
        int prefixLen = prefix2.length;
        int current = getPrefixLength();
        int minimum = getPerLinePrefixEnd();
        if (minimum > column) {
            column = minimum;
        }
        if (column > prefixLen) {
            prefix2 = new char[enoughSpace(prefixLen, column - prefixLen)];
            System.arraycopy(this.prefix, 0, prefix2, 0, current);
            this.prefix = prefix2;
        }
        if (column > current) {
            for (int i = current; i < column; i++) {
                prefix2[i] = ' ';
            }
        }
        this.blocks[this.blockDepth - 4] = column;
    }

    /* access modifiers changed from: package-private */
    public void reallyEndLogicalBlock() {
        int oldIndent = getPrefixLength();
        this.blockDepth -= 6;
        int newIndent = getPrefixLength();
        if (newIndent > oldIndent) {
            for (int i = oldIndent; i < newIndent; i++) {
                this.prefix[i] = ' ';
            }
        }
    }

    public int enqueue(int kind, int size) {
        int oldLength = this.queueInts.length;
        int endAvail = (oldLength - this.queueTail) - this.queueSize;
        if (endAvail > 0 && size > endAvail) {
            enqueue(0, endAvail);
        }
        if (this.queueSize + size > oldLength) {
            int newLength = enoughSpace(oldLength, size);
            int[] newInts = new int[newLength];
            String[] newStrings = new String[newLength];
            int queueHead = (this.queueTail + this.queueSize) - oldLength;
            if (queueHead > 0) {
                System.arraycopy(this.queueInts, 0, newInts, 0, queueHead);
                System.arraycopy(this.queueStrings, 0, newStrings, 0, queueHead);
            }
            int i = this.queueTail;
            int part1Len = oldLength - i;
            int deltaLength = newLength - oldLength;
            System.arraycopy(this.queueInts, i, newInts, i + deltaLength, part1Len);
            String[] strArr = this.queueStrings;
            int i2 = this.queueTail;
            System.arraycopy(strArr, i2, newStrings, i2 + deltaLength, part1Len);
            this.queueInts = newInts;
            this.queueStrings = newStrings;
            int i3 = this.currentBlock;
            int i4 = this.queueTail;
            if (i3 >= i4) {
                this.currentBlock = i3 + deltaLength;
            }
            this.queueTail = i4 + deltaLength;
        }
        int addr = this.queueTail + this.queueSize;
        int[] iArr = this.queueInts;
        if (addr >= iArr.length) {
            addr -= iArr.length;
        }
        iArr[addr + 0] = (size << 16) | kind;
        if (size > 1) {
            iArr[addr + 1] = indexPosn(this.bufferFillPointer);
        }
        this.queueSize += size;
        return addr;
    }

    public void enqueueNewline(int kind) {
        this.wordEndSeen = false;
        int depth = this.pendingBlocksCount;
        int newline = enqueue(2, 5);
        int[] iArr = this.queueInts;
        iArr[newline + 4] = kind;
        iArr[newline + 2] = this.pendingBlocksCount;
        iArr[newline + 3] = 0;
        int entry = this.queueTail;
        int todo = this.queueSize;
        while (todo > 0) {
            if (entry == this.queueInts.length) {
                entry = 0;
            }
            if (entry == newline) {
                break;
            }
            int type = getQueueType(entry);
            if (type == 2 || type == 4) {
                int[] iArr2 = this.queueInts;
                if (iArr2[entry + 3] == 0 && depth <= iArr2[entry + 2]) {
                    int delta = newline - entry;
                    if (delta < 0) {
                        delta += iArr2.length;
                    }
                    iArr2[entry + 3] = delta;
                }
            }
            int size = getQueueSize(entry);
            todo -= size;
            entry += size;
        }
        maybeOutput(kind == 76 || kind == 82, false);
    }

    public final void writeBreak(int kind) {
        if (this.prettyPrintingMode > 0) {
            enqueueNewline(kind);
        }
    }

    public int enqueueIndent(char kind, int amount) {
        int result = enqueue(3, 4);
        int[] iArr = this.queueInts;
        iArr[result + 2] = kind;
        iArr[result + 3] = amount;
        return result;
    }

    public void addIndentation(int amount, boolean current) {
        if (this.prettyPrintingMode > 0) {
            enqueueIndent(current ? 'C' : QITEM_INDENTATION_BLOCK, amount);
        }
    }

    public void startLogicalBlock(String prefix2, boolean perLine, String suffix2) {
        int outerBlock;
        String str = null;
        if (this.queueSize == 0 && this.bufferFillPointer == 0) {
            Object llen = lineLengthLoc.get((Object) null);
            if (llen == null) {
                this.lineLength = 80;
            } else {
                this.lineLength = Integer.parseInt(llen.toString());
            }
            Object mwidth = miserWidthLoc.get((Object) null);
            if (mwidth == null || mwidth == Boolean.FALSE || mwidth == LList.Empty) {
                this.miserWidth = -1;
            } else {
                this.miserWidth = Integer.parseInt(mwidth.toString());
            }
            indentLoc.get((Object) null);
        }
        if (prefix2 != null) {
            write(prefix2);
        }
        if (this.prettyPrintingMode != 0) {
            int start = enqueue(4, 7);
            int[] iArr = this.queueInts;
            int i = this.pendingBlocksCount;
            iArr[start + 2] = i;
            String[] strArr = this.queueStrings;
            int i2 = start + 5;
            if (perLine) {
                str = prefix2;
            }
            strArr[i2] = str;
            strArr[start + 6] = suffix2;
            this.pendingBlocksCount = i + 1;
            int outerBlock2 = this.currentBlock;
            if (outerBlock2 < 0) {
                outerBlock = 0;
            } else {
                outerBlock = outerBlock2 - start;
                if (outerBlock > 0) {
                    outerBlock -= iArr.length;
                }
            }
            iArr[start + 4] = outerBlock;
            iArr[start + 3] = 0;
            this.currentBlock = start;
        }
    }

    public void endLogicalBlock() {
        int end = enqueue(5, 2);
        this.pendingBlocksCount--;
        if (this.currentBlock < 0) {
            int[] iArr = this.blocks;
            int i = this.blockDepth;
            int suffixLength = iArr[i - 5];
            int suffixPreviousLength = iArr[(i - 6) - 5];
            if (suffixLength > suffixPreviousLength) {
                char[] cArr = this.suffix;
                write(cArr, cArr.length - suffixLength, suffixLength - suffixPreviousLength);
            }
            this.currentBlock = -1;
            return;
        }
        int start = this.currentBlock;
        int[] iArr2 = this.queueInts;
        int outerBlock = iArr2[start + 4];
        if (outerBlock == 0) {
            this.currentBlock = -1;
        } else {
            int qtailFromStart = this.queueTail - start;
            if (qtailFromStart > 0) {
                qtailFromStart -= iArr2.length;
            }
            if (outerBlock < qtailFromStart) {
                this.currentBlock = -1;
            } else {
                int outerBlock2 = outerBlock + start;
                if (outerBlock2 < 0) {
                    outerBlock2 += iArr2.length;
                }
                this.currentBlock = outerBlock2;
            }
        }
        String suffix2 = this.queueStrings[start + 6];
        if (suffix2 != null) {
            write(suffix2);
        }
        int endFromStart = end - start;
        if (endFromStart < 0) {
            endFromStart += this.queueInts.length;
        }
        this.queueInts[start + 4] = endFromStart;
    }

    public void endLogicalBlock(String suffix2) {
        if (this.prettyPrintingMode > 0) {
            endLogicalBlock();
        } else if (suffix2 != null) {
            write(suffix2);
        }
    }

    /* access modifiers changed from: package-private */
    public int computeTabSize(int tab, int sectionStart, int column) {
        int rem;
        int[] iArr = this.queueInts;
        int flags = iArr[tab + 2];
        int origin = 0;
        boolean isSection = (flags & 1) != 0;
        boolean isRelative = (flags & 2) != 0;
        if (isSection) {
            origin = sectionStart;
        }
        int colnum = iArr[tab + 3];
        int colinc = iArr[tab + 4];
        if (isRelative) {
            if (colinc <= 1 || (rem = (column + colnum) % colinc) == 0) {
                return colnum;
            }
            int colinc2 = rem;
            return colnum + rem;
        } else if (column <= colnum + origin) {
            return (column + origin) - column;
        } else {
            return colinc - ((column - origin) % colinc);
        }
    }

    /* access modifiers changed from: package-private */
    public int indexColumn(int index) {
        int column = this.bufferStartColumn;
        int sectionStart = getSectionColumn();
        int endPosn = indexPosn(index);
        int op = this.queueTail;
        int todo = this.queueSize;
        while (todo > 0) {
            if (op >= this.queueInts.length) {
                op = 0;
            }
            int type = getQueueType(op);
            if (type != 0) {
                int[] iArr = this.queueInts;
                int posn = iArr[op + 1];
                if (posn >= endPosn) {
                    break;
                } else if (type == 6) {
                    column += computeTabSize(op, sectionStart, posnIndex(posn) + column);
                } else if (type == 2 || type == 4) {
                    sectionStart = posnIndex(iArr[op + 1]) + column;
                }
            }
            int size = getQueueSize(op);
            todo -= size;
            op += size;
        }
        return column + index;
    }

    /* access modifiers changed from: package-private */
    public void expandTabs(int through) {
        PrettyWriter prettyWriter = this;
        int numInsertions = 0;
        int additional = 0;
        int column = prettyWriter.bufferStartColumn;
        int sectionStart = getSectionColumn();
        int op = prettyWriter.queueTail;
        int todo = prettyWriter.queueSize;
        int size = 6;
        int blocksUsed = prettyWriter.pendingBlocksCount * 6;
        while (true) {
            if (todo <= 0) {
                int i = through;
                break;
            }
            if (op == prettyWriter.queueInts.length) {
                op = 0;
            }
            if (op == through) {
                break;
            }
            if (prettyWriter.getQueueType(op) == size) {
                int index = prettyWriter.posnIndex(prettyWriter.queueInts[op + 1]);
                int tabsize = prettyWriter.computeTabSize(op, sectionStart, column + index);
                if (tabsize != 0) {
                    int i2 = (numInsertions * 2) + blocksUsed + 1;
                    int[] iArr = prettyWriter.blocks;
                    if (i2 >= iArr.length) {
                        int[] newBlocks = new int[(iArr.length * 2)];
                        System.arraycopy(iArr, 0, newBlocks, 0, iArr.length);
                        prettyWriter.blocks = newBlocks;
                    }
                    int[] iArr2 = prettyWriter.blocks;
                    iArr2[(numInsertions * 2) + blocksUsed] = index;
                    iArr2[(numInsertions * 2) + blocksUsed + 1] = tabsize;
                    numInsertions++;
                    additional += tabsize;
                    column += tabsize;
                }
            } else if (op == 2 || op == 4) {
                sectionStart = prettyWriter.posnIndex(prettyWriter.queueInts[op + 1]) + column;
            }
            int size2 = prettyWriter.getQueueSize(op);
            todo -= size2;
            op += size2;
            size = 6;
        }
        if (numInsertions > 0) {
            int fillPtr = prettyWriter.bufferFillPointer;
            int newFillPtr = fillPtr + additional;
            char[] buffer2 = prettyWriter.buffer;
            char[] newBuffer = buffer2;
            int end = fillPtr;
            if (newFillPtr > buffer2.length) {
                newBuffer = new char[enoughSpace(fillPtr, additional)];
                prettyWriter.buffer = newBuffer;
            }
            prettyWriter.bufferFillPointer = newFillPtr;
            prettyWriter.bufferOffset -= additional;
            int i3 = numInsertions;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                int numInsertions2 = numInsertions;
                int[] iArr3 = prettyWriter.blocks;
                int srcpos = iArr3[blocksUsed + (i3 * 2)];
                int amount = iArr3[blocksUsed + (i3 * 2) + 1];
                int column2 = column;
                int dstpos = srcpos + additional;
                int sectionStart2 = sectionStart;
                System.arraycopy(buffer2, srcpos, newBuffer, dstpos, end - srcpos);
                for (int j = dstpos - amount; j < dstpos; j++) {
                    newBuffer[j] = ' ';
                }
                additional -= amount;
                end = srcpos;
                prettyWriter = this;
                numInsertions = numInsertions2;
                column = column2;
                sectionStart = sectionStart2;
            }
            int i4 = column;
            int i5 = sectionStart;
            if (newBuffer != buffer2) {
                System.arraycopy(buffer2, 0, newBuffer, 0, end);
                return;
            }
            return;
        }
        int i6 = column;
        int i7 = sectionStart;
    }

    /* access modifiers changed from: package-private */
    public int ensureSpaceInBuffer(int want) {
        char[] buffer2 = this.buffer;
        int length = buffer2.length;
        int fillPtr = this.bufferFillPointer;
        int available = length - fillPtr;
        if (available > 0) {
            return available;
        }
        if (this.prettyPrintingMode <= 0 || fillPtr <= this.lineLength) {
            int newLength = enoughSpace(length, want);
            char[] newBuffer = new char[newLength];
            this.buffer = newBuffer;
            int i = fillPtr;
            while (true) {
                i--;
                if (i < 0) {
                    return newLength - fillPtr;
                }
                newBuffer[i] = buffer2[i];
            }
        } else {
            if (!maybeOutput(false, false)) {
                outputPartialLine();
            }
            return ensureSpaceInBuffer(want);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00dc, code lost:
        if (r3 == false) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00df, code lost:
        if (r10 == false) goto L_0x00e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e1, code lost:
        if (r4 != 0) goto L_0x00e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        outputPartialLine();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00e7, code lost:
        outputLine(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ea, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00ec, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f2, code lost:
        throw new java.lang.RuntimeException(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00f3, code lost:
        r3 = getQueueSize(r8.queueTail);
        r8.queueSize -= r3;
        r8.queueTail = r2 + r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00f3, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean maybeOutput(boolean r9, boolean r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            int r2 = r8.queueSize
            if (r2 <= 0) goto L_0x0103
            int r2 = r8.queueTail
            int[] r3 = r8.queueInts
            int r3 = r3.length
            if (r2 < r3) goto L_0x000f
            r8.queueTail = r0
        L_0x000f:
            int r2 = r8.queueTail
            int r3 = r8.getQueueType(r2)
            r4 = -1
            r5 = 1
            switch(r3) {
                case 2: goto L_0x009b;
                case 3: goto L_0x0074;
                case 4: goto L_0x0026;
                case 5: goto L_0x0021;
                case 6: goto L_0x001c;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x00f3
        L_0x001c:
            r8.expandTabs(r2)
            goto L_0x00f3
        L_0x0021:
            r8.reallyEndLogicalBlock()
            goto L_0x00f3
        L_0x0026:
            int[] r3 = r8.queueInts
            int r5 = r2 + 3
            r5 = r3[r5]
            if (r5 <= 0) goto L_0x0033
            int r5 = r5 + r2
            int r3 = r3.length
            int r5 = r5 % r3
            goto L_0x0034
        L_0x0033:
            r5 = -1
        L_0x0034:
            int r3 = r8.fitsOnLine(r5, r9)
            if (r3 <= 0) goto L_0x004f
            int[] r3 = r8.queueInts
            int r5 = r2 + 4
            r5 = r3[r5]
            int r6 = r5 + r2
            int r3 = r3.length
            int r6 = r6 % r3
            r8.expandTabs(r6)
            r8.queueTail = r6
            int r3 = r8.queueSize
            int r3 = r3 - r5
            r8.queueSize = r3
            goto L_0x006b
        L_0x004f:
            if (r3 < 0) goto L_0x0053
            if (r10 == 0) goto L_0x0103
        L_0x0053:
            java.lang.String[] r3 = r8.queueStrings
            int r5 = r2 + 5
            r5 = r3[r5]
            int r6 = r2 + 6
            r3 = r3[r6]
            int[] r6 = r8.queueInts
            int r7 = r2 + 1
            r6 = r6[r7]
            int r6 = r8.posnColumn(r6)
            r8.reallyStartLogicalBlock(r6, r5, r3)
            r6 = r2
        L_0x006b:
            int r3 = r8.currentBlock
            if (r3 != r2) goto L_0x0071
            r8.currentBlock = r4
        L_0x0071:
            r2 = r6
            goto L_0x00f3
        L_0x0074:
            boolean r3 = r8.isMisering()
            if (r3 != 0) goto L_0x00f3
            int[] r3 = r8.queueInts
            int r4 = r2 + 2
            r4 = r3[r4]
            int r5 = r2 + 3
            r5 = r3[r5]
            r6 = 66
            if (r4 != r6) goto L_0x008e
            int r3 = r8.getStartColumn()
            int r5 = r5 + r3
            goto L_0x0097
        L_0x008e:
            int r4 = r2 + 1
            r3 = r3[r4]
            int r3 = r8.posnColumn(r3)
            int r5 = r5 + r3
        L_0x0097:
            r8.setIndentation(r5)
            goto L_0x00f3
        L_0x009b:
            int[] r3 = r8.queueInts
            int r6 = r2 + 4
            r3 = r3[r6]
            switch(r3) {
                case 70: goto L_0x00ad;
                case 77: goto L_0x00a8;
                case 83: goto L_0x00bc;
                default: goto L_0x00a5;
            }
        L_0x00a5:
            r3 = 1
            goto L_0x00dc
        L_0x00a8:
            boolean r3 = r8.isMisering()
            goto L_0x00dc
        L_0x00ad:
            boolean r3 = r8.isMisering()
            if (r3 != 0) goto L_0x00da
            int r3 = r8.lineNumber
            int r6 = r8.getSectionStartLine()
            if (r3 <= r6) goto L_0x00bc
            goto L_0x00da
        L_0x00bc:
            int[] r3 = r8.queueInts
            int r6 = r2 + 3
            r6 = r3[r6]
            if (r6 != 0) goto L_0x00c5
            goto L_0x00cc
        L_0x00c5:
            int r4 = r2 + r6
            int r6 = r3.length
            if (r4 < r6) goto L_0x00cc
            int r3 = r3.length
            int r4 = r4 - r3
        L_0x00cc:
            int r4 = r8.fitsOnLine(r4, r9)
            if (r4 <= 0) goto L_0x00d4
            r3 = 0
            goto L_0x00dc
        L_0x00d4:
            if (r4 < 0) goto L_0x00d8
            if (r10 == 0) goto L_0x0103
        L_0x00d8:
            r3 = 1
            goto L_0x00dc
        L_0x00da:
            r3 = 1
        L_0x00dc:
            if (r3 == 0) goto L_0x00f3
            if (r10 == 0) goto L_0x00e7
            if (r4 != 0) goto L_0x00e7
            r8.outputPartialLine()     // Catch:{ IOException -> 0x00ec }
            goto L_0x00ea
        L_0x00e7:
            r8.outputLine(r2)     // Catch:{ IOException -> 0x00ec }
        L_0x00ea:
            r1 = 1
            goto L_0x00f3
        L_0x00ec:
            r9 = move-exception
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            r10.<init>(r9)
            throw r10
        L_0x00f3:
            int r3 = r8.queueTail
            int r3 = r8.getQueueSize(r3)
            int r4 = r8.queueSize
            int r4 = r4 - r3
            r8.queueSize = r4
            int r2 = r2 + r3
            r8.queueTail = r2
            goto L_0x0002
        L_0x0103:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.PrettyWriter.maybeOutput(boolean, boolean):boolean");
    }

    /* access modifiers changed from: protected */
    public int getMiserWidth() {
        return this.miserWidth;
    }

    /* access modifiers changed from: package-private */
    public boolean isMisering() {
        int mwidth = getMiserWidth();
        return mwidth > 0 && this.lineLength - getStartColumn() <= mwidth;
    }

    /* access modifiers changed from: package-private */
    public int getMaxLines() {
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean printReadably() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public int fitsOnLine(int sectionEnd, boolean forceNewlines) {
        int available = this.lineLength;
        if (!printReadably() && getMaxLines() == this.lineNumber) {
            available = (available - 3) - getSuffixLength();
        }
        if (sectionEnd >= 0) {
            if (posnColumn(this.queueInts[sectionEnd + 1]) <= available) {
                return 1;
            }
            return -1;
        } else if (!forceNewlines && indexColumn(this.bufferFillPointer) <= available) {
            return 0;
        } else {
            return -1;
        }
    }

    public void lineAbbreviationHappened() {
    }

    /* access modifiers changed from: package-private */
    public void outputLine(int newline) throws IOException {
        int i;
        int maxLines;
        char[] buffer2 = this.buffer;
        int[] iArr = this.queueInts;
        boolean isLiteral = iArr[newline + 4] == 76;
        int amountToConsume = posnIndex(iArr[newline + 1]);
        if (!isLiteral) {
            int i2 = amountToConsume;
            while (true) {
                i2--;
                if (i2 >= 0) {
                    if (buffer2[i2] != ' ') {
                        i = i2 + 1;
                        break;
                    }
                } else {
                    i = 0;
                    break;
                }
            }
        } else {
            i = amountToConsume;
        }
        this.out.write(buffer2, 0, i);
        int lineNumber2 = this.lineNumber + 1;
        if (!printReadably() && (maxLines = getMaxLines()) > 0 && lineNumber2 >= maxLines) {
            this.out.write(" ..");
            int suffixLength = getSuffixLength();
            if (suffixLength != 0) {
                char[] suffix2 = this.suffix;
                this.out.write(suffix2, suffix2.length - suffixLength, suffixLength);
            }
            lineAbbreviationHappened();
        }
        this.lineNumber = lineNumber2;
        this.out.write(10);
        this.bufferStartColumn = 0;
        int fillPtr = this.bufferFillPointer;
        int prefixLen = isLiteral ? getPerLinePrefixEnd() : getPrefixLength();
        int shift = amountToConsume - prefixLen;
        int newFillPtr = fillPtr - shift;
        char[] newBuffer = buffer2;
        int bufferLength = buffer2.length;
        if (newFillPtr > bufferLength) {
            newBuffer = new char[enoughSpace(bufferLength, newFillPtr - bufferLength)];
            this.buffer = newBuffer;
        }
        System.arraycopy(buffer2, amountToConsume, newBuffer, prefixLen, fillPtr - amountToConsume);
        System.arraycopy(this.prefix, 0, newBuffer, 0, prefixLen);
        this.bufferFillPointer = newFillPtr;
        this.bufferOffset += shift;
        if (!isLiteral) {
            int[] iArr2 = this.blocks;
            int i3 = this.blockDepth;
            iArr2[i3 - 2] = prefixLen;
            iArr2[i3 - 6] = lineNumber2;
        }
    }

    /* access modifiers changed from: package-private */
    public void outputPartialLine() {
        int tail = this.queueTail;
        while (this.queueSize > 0 && getQueueType(tail) == 0) {
            int size = getQueueSize(tail);
            this.queueSize -= size;
            tail += size;
            if (tail == this.queueInts.length) {
                tail = 0;
            }
            this.queueTail = tail;
        }
        int fillPtr = this.bufferFillPointer;
        int count = this.queueSize > 0 ? posnIndex(this.queueInts[tail + 1]) : fillPtr;
        int newFillPtr = fillPtr - count;
        if (count > 0) {
            try {
                this.out.write(this.buffer, 0, count);
                this.bufferFillPointer = count;
                this.bufferStartColumn = getColumnNumber();
                char[] cArr = this.buffer;
                System.arraycopy(cArr, count, cArr, 0, newFillPtr);
                this.bufferFillPointer = newFillPtr;
                this.bufferOffset += count;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new Error("outputPartialLine called when nothing can be output.");
        }
    }

    public void forcePrettyOutput() throws IOException {
        maybeOutput(false, true);
        if (this.bufferFillPointer > 0) {
            outputPartialLine();
        }
        expandTabs(-1);
        this.bufferStartColumn = getColumnNumber();
        this.out.write(this.buffer, 0, this.bufferFillPointer);
        this.bufferFillPointer = 0;
        this.queueSize = 0;
    }

    public void flush() {
        if (this.out != null) {
            try {
                forcePrettyOutput();
                this.out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex.toString());
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            forcePrettyOutput();
            this.out.close();
            this.out = null;
        }
        this.buffer = null;
    }

    public void closeThis() throws IOException {
        if (this.out != null) {
            forcePrettyOutput();
            this.out = null;
        }
        this.buffer = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0006 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x000c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getColumnNumber() {
        /*
            r4 = this;
            int r0 = r4.bufferFillPointer
        L_0x0002:
            int r0 = r0 + -1
            if (r0 >= 0) goto L_0x000c
            int r1 = r4.bufferStartColumn
            int r2 = r4.bufferFillPointer
            int r1 = r1 + r2
            return r1
        L_0x000c:
            char[] r1 = r4.buffer
            char r1 = r1[r0]
            r2 = 10
            if (r1 == r2) goto L_0x001a
            r2 = 13
            if (r1 != r2) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            goto L_0x0002
        L_0x001a:
            int r2 = r4.bufferFillPointer
            int r3 = r0 + 1
            int r2 = r2 - r3
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.PrettyWriter.getColumnNumber():int");
    }

    public void setColumnNumber(int column) {
        this.bufferStartColumn += column - getColumnNumber();
    }

    public void clearBuffer() {
        this.bufferStartColumn = 0;
        this.bufferFillPointer = 0;
        this.lineNumber = 0;
        this.bufferOffset = 0;
        this.blockDepth = 6;
        this.queueTail = 0;
        this.queueSize = 0;
        this.pendingBlocksCount = 0;
    }
}
