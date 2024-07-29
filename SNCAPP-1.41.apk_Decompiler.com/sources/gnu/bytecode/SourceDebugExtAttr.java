package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class SourceDebugExtAttr extends Attribute {
    int curFileIndex = -1;
    String curFileName;
    int curLineIndex = -1;
    byte[] data;
    private String defaultStratumId;
    int dlength;
    int fileCount;
    int[] fileIDs;
    String[] fileNames;
    int lineCount;
    int[] lines;
    int maxFileID;
    private String outputFileName;

    private int fixLine(int sourceLine, int index) {
        int[] iArr = this.lines;
        int sourceMin = iArr[index];
        int repeat = iArr[index + 2];
        if (sourceLine < sourceMin) {
            if (index > 0) {
                return -1;
            }
            iArr[index] = sourceLine;
            iArr[index + 2] = (((sourceMin + repeat) - 1) - sourceLine) + 1;
            iArr[index + 3] = sourceLine;
            sourceMin = sourceLine;
        }
        int delta = iArr[index + 3] - sourceMin;
        if (sourceLine < sourceMin + repeat) {
            return sourceLine + delta;
        }
        if (index != (this.lineCount - 1) * 5 && (index != 0 || sourceLine >= iArr[8])) {
            return -1;
        }
        iArr[index + 2] = (sourceLine - sourceMin) + 1;
        return sourceLine + delta;
    }

    /* access modifiers changed from: package-private */
    public int fixLine(int sourceLine) {
        int outputStartLine;
        int outLine;
        int outLine2;
        int i = this.curLineIndex;
        if (i >= 0 && (outLine2 = fixLine(sourceLine, i)) >= 0) {
            return outLine2;
        }
        int i5 = 0;
        int findex = this.curFileIndex;
        int i2 = 0;
        while (i2 < this.lineCount) {
            if (i5 == this.curLineIndex || findex != this.lines[i5 + 1] || (outLine = fixLine(sourceLine, i5)) < 0) {
                i5 += 5;
                i2++;
            } else {
                this.curLineIndex = i5;
                return outLine;
            }
        }
        int[] iArr = this.lines;
        if (iArr == null) {
            this.lines = new int[20];
        } else if (i5 >= iArr.length) {
            int[] newLines = new int[(i5 * 2)];
            System.arraycopy(iArr, 0, newLines, 0, i5);
            this.lines = newLines;
        }
        int inputStartLine = sourceLine;
        if (i5 == 0) {
            outputStartLine = sourceLine;
        } else {
            int[] iArr2 = this.lines;
            int outputStartLine2 = iArr2[(i5 - 5) + 3] + iArr2[(i5 - 5) + 2];
            if (i5 != 5 || outputStartLine2 >= 10000) {
                outputStartLine = outputStartLine2;
            } else {
                outputStartLine = 10000;
            }
            sourceLine = outputStartLine;
        }
        int[] iArr3 = this.lines;
        iArr3[i5] = inputStartLine;
        iArr3[i5 + 1] = findex;
        iArr3[i5 + 2] = 1;
        iArr3[i5 + 3] = outputStartLine;
        iArr3[i5 + 4] = 1;
        this.curLineIndex = i5;
        this.lineCount++;
        return sourceLine;
    }

    /* access modifiers changed from: package-private */
    public void addFile(String fname) {
        String fpath;
        String str = this.curFileName;
        if (str == fname) {
            return;
        }
        if (fname == null || !fname.equals(str)) {
            this.curFileName = fname;
            String fname2 = SourceFileAttr.fixSourceFile(fname);
            int slash = fname2.lastIndexOf(47);
            if (slash >= 0) {
                String fpath2 = fname2;
                fname2 = fname2.substring(slash + 1);
                fpath = fname2 + 10 + fpath2;
            } else {
                fpath = fname2;
            }
            int i = this.curFileIndex;
            if (i < 0 || !fpath.equals(this.fileNames[i])) {
                int n = this.fileCount;
                int i2 = 0;
                while (i2 < n) {
                    if (i2 == this.curFileIndex || !fpath.equals(this.fileNames[i2])) {
                        i2++;
                    } else {
                        this.curFileIndex = i2;
                        this.curLineIndex = -1;
                        return;
                    }
                }
                int[] iArr = this.fileIDs;
                if (iArr == null) {
                    this.fileIDs = new int[5];
                    this.fileNames = new String[5];
                } else if (n >= iArr.length) {
                    int[] newIDs = new int[(n * 2)];
                    String[] newNames = new String[(n * 2)];
                    System.arraycopy(iArr, 0, newIDs, 0, n);
                    System.arraycopy(this.fileNames, 0, newNames, 0, n);
                    this.fileIDs = newIDs;
                    this.fileNames = newNames;
                }
                this.fileCount++;
                int id = this.maxFileID + 1;
                this.maxFileID = id;
                int id2 = id << 1;
                if (slash >= 0) {
                    id2++;
                }
                this.fileNames[n] = fpath;
                if (this.outputFileName == null) {
                    this.outputFileName = fname2;
                }
                this.fileIDs[n] = id2;
                this.curFileIndex = n;
                this.curLineIndex = -1;
            }
        }
    }

    public void addStratum(String name) {
        this.defaultStratumId = name;
    }

    public SourceDebugExtAttr(ClassType cl) {
        super("SourceDebugExtension");
        addToFrontOf(cl);
    }

    /* access modifiers changed from: package-private */
    public void nonAsteriskString(String str, StringBuffer sbuf) {
        if (str == null || str.length() == 0 || str.charAt(0) == '*') {
            sbuf.append(' ');
        }
        sbuf.append(str);
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("SMAP\n");
        nonAsteriskString(this.outputFileName, sbuf);
        sbuf.append(10);
        String stratum = this.defaultStratumId;
        if (stratum == null) {
            stratum = "Java";
        }
        nonAsteriskString(stratum, sbuf);
        sbuf.append(10);
        sbuf.append("*S ");
        sbuf.append(stratum);
        sbuf.append(10);
        sbuf.append("*F\n");
        int i = 0;
        while (true) {
            boolean with_path = true;
            if (i >= this.fileCount) {
                break;
            }
            int id = this.fileIDs[i];
            if ((id & 1) == 0) {
                with_path = false;
            }
            int id2 = id >> 1;
            if (with_path) {
                sbuf.append("+ ");
            }
            sbuf.append(id2);
            sbuf.append(' ');
            sbuf.append(this.fileNames[i]);
            sbuf.append(10);
            i++;
        }
        if (this.lineCount > 0) {
            int prevFileID = 0;
            sbuf.append("*L\n");
            int i2 = 0;
            int i5 = 0;
            do {
                int[] iArr = this.lines;
                int inputStartLine = iArr[i5];
                int lineFileID = this.fileIDs[iArr[i5 + 1]] >> 1;
                int repeatCount = iArr[i5 + 2];
                int outputStartLine = iArr[i5 + 3];
                int outputLineIncrement = iArr[i5 + 4];
                sbuf.append(inputStartLine);
                if (lineFileID != prevFileID) {
                    sbuf.append('#');
                    sbuf.append(lineFileID);
                    prevFileID = lineFileID;
                }
                if (repeatCount != 1) {
                    sbuf.append(',');
                    sbuf.append(repeatCount);
                }
                sbuf.append(':');
                sbuf.append(outputStartLine);
                if (outputLineIncrement != 1) {
                    sbuf.append(',');
                    sbuf.append(outputLineIncrement);
                }
                sbuf.append(10);
                i5 += 5;
                i2++;
            } while (i2 < this.lineCount);
        }
        sbuf.append("*E\n");
        try {
            byte[] bytes = sbuf.toString().getBytes("UTF-8");
            this.data = bytes;
            this.dlength = bytes.length;
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    public int getLength() {
        return this.dlength;
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.write(this.data, 0, this.dlength);
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.println(this.dlength);
        try {
            dst.print(new String(this.data, 0, this.dlength, "UTF-8"));
        } catch (Exception ex) {
            dst.print("(Caught ");
            dst.print(ex);
            dst.println(')');
        }
        int i = this.dlength;
        if (i > 0) {
            byte[] bArr = this.data;
            if (bArr[i - 1] != 13 && bArr[i - 1] != 10) {
                dst.println();
            }
        }
    }
}
