package gnu.bytecode;

import com.google.appinventor.components.common.PropertyTypeConstants;
import java.io.DataOutputStream;
import java.io.IOException;
import kawa.Telnet;

public class StackMapTableAttr extends MiscAttr {
    public static boolean compressStackMapTable = true;
    int countLocals;
    int countStack;
    int[] encodedLocals;
    int[] encodedStack;
    int numEntries;
    int prevPosition = -1;

    public StackMapTableAttr() {
        super("StackMapTable", (byte[]) null, 0, 0);
        put2(0);
    }

    public StackMapTableAttr(byte[] data, CodeAttr code) {
        super("StackMapTable", data, 0, data.length);
        addToFrontOf(code);
        this.numEntries = u2(0);
    }

    public Method getMethod() {
        return ((CodeAttr) this.container).getMethod();
    }

    public void write(DataOutputStream dstr) throws IOException {
        put2(0, this.numEntries);
        super.write(dstr);
    }

    /* access modifiers changed from: package-private */
    public void emitVerificationType(int encoding) {
        int tag = encoding & 255;
        put1(tag);
        if (tag >= 7) {
            put2(encoding >> 8);
        }
    }

    /* access modifiers changed from: package-private */
    public int encodeVerificationType(Type type, CodeAttr code) {
        if (type == null) {
            return 0;
        }
        if (type instanceof UninitializedType) {
            Label label = ((UninitializedType) type).label;
            if (label == null) {
                return 6;
            }
            return (label.position << 8) | 8;
        }
        Type type2 = type.getImplementationType();
        if (type2 instanceof PrimType) {
            switch (type2.signature.charAt(0)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z':
                    return 1;
                case 'D':
                    return 3;
                case 'F':
                    return 2;
                case 'J':
                    return 4;
                default:
                    return 0;
            }
        } else if (type2 == Type.nullType) {
            return 5;
        } else {
            return (code.getConstants().addClass((ObjectType) type2).index << 8) | 7;
        }
    }

    public void emitStackMapEntry(Label label, CodeAttr code) {
        Label label2 = label;
        CodeAttr codeAttr = code;
        int offset_delta = (label2.position - this.prevPosition) - 1;
        int rawLocalsCount = label2.localTypes.length;
        int[] iArr = this.encodedLocals;
        if (rawLocalsCount > iArr.length) {
            int[] tmp = new int[(iArr.length + rawLocalsCount)];
            System.arraycopy(iArr, 0, tmp, 0, this.countLocals);
            this.encodedLocals = tmp;
        }
        int rawStackCount = label2.stackTypes.length;
        int[] iArr2 = this.encodedStack;
        if (rawStackCount > iArr2.length) {
            int[] tmp2 = new int[(iArr2.length + rawStackCount)];
            System.arraycopy(iArr2, 0, tmp2, 0, this.countStack);
            this.encodedStack = tmp2;
        }
        int unchangedLocals = 0;
        int curLocalsCount = 0;
        int i = 0;
        while (i < rawLocalsCount) {
            int prevType = this.encodedLocals[curLocalsCount];
            int nextType = encodeVerificationType(label2.localTypes[i], codeAttr);
            if (prevType == nextType && unchangedLocals == curLocalsCount) {
                unchangedLocals = curLocalsCount + 1;
            }
            int curLocalsCount2 = curLocalsCount + 1;
            this.encodedLocals[curLocalsCount] = nextType;
            if (nextType == 3 || nextType == 4) {
                i++;
            }
            i++;
            curLocalsCount = curLocalsCount2;
        }
        while (curLocalsCount > 0 && this.encodedLocals[curLocalsCount - 1] == 0) {
            curLocalsCount--;
        }
        int curStackCount = 0;
        int i2 = 0;
        while (i2 < rawStackCount) {
            int i3 = this.encodedStack[curStackCount];
            Type t = label2.stackTypes[i2];
            if (t == Type.voidType) {
                i2++;
                t = label2.stackTypes[i2];
            }
            this.encodedStack[curStackCount] = encodeVerificationType(t, codeAttr);
            i2++;
            curStackCount++;
        }
        int i4 = this.countLocals;
        int localsDelta = curLocalsCount - i4;
        boolean z = compressStackMapTable;
        if (!z || localsDelta != 0 || curLocalsCount != unchangedLocals || curStackCount > 1) {
            if (z && curStackCount == 0 && curLocalsCount < i4 && unchangedLocals == curLocalsCount && localsDelta >= -3) {
                put1(localsDelta + Telnet.WILL);
                put2(offset_delta);
            } else if (!z || curStackCount != 0 || i4 != unchangedLocals || localsDelta > 3) {
                put1(255);
                put2(offset_delta);
                put2(curLocalsCount);
                for (int i5 = 0; i5 < curLocalsCount; i5++) {
                    emitVerificationType(this.encodedLocals[i5]);
                }
                put2(curStackCount);
                for (int i6 = 0; i6 < curStackCount; i6++) {
                    emitVerificationType(this.encodedStack[i6]);
                }
            } else {
                put1(localsDelta + Telnet.WILL);
                put2(offset_delta);
                for (int i7 = 0; i7 < localsDelta; i7++) {
                    emitVerificationType(this.encodedLocals[unchangedLocals + i7]);
                }
            }
        } else if (curStackCount != 0) {
            if (offset_delta <= 63) {
                put1(offset_delta + 64);
            } else {
                put1(247);
                put2(offset_delta);
            }
            emitVerificationType(this.encodedStack[0]);
        } else if (offset_delta <= 63) {
            put1(offset_delta);
        } else {
            put1(Telnet.WILL);
            put2(offset_delta);
        }
        this.countLocals = curLocalsCount;
        this.countStack = curStackCount;
        this.prevPosition = label2.position;
        this.numEntries++;
    }

    /* access modifiers changed from: package-private */
    public void printVerificationType(int encoding, ClassTypeWriter dst) {
        int tag = encoding & 255;
        switch (tag) {
            case 0:
                dst.print("top/unavailable");
                return;
            case 1:
                dst.print(PropertyTypeConstants.PROPERTY_TYPE_INTEGER);
                return;
            case 2:
                dst.print(PropertyTypeConstants.PROPERTY_TYPE_FLOAT);
                return;
            case 3:
                dst.print("double");
                return;
            case 4:
                dst.print("long");
                return;
            case 5:
                dst.print("null");
                return;
            case 6:
                dst.print("uninitialized this");
                return;
            case 7:
                int index = encoding >> 8;
                dst.printOptionalIndex(index);
                dst.printConstantTersely(index, 7);
                return;
            case 8:
                dst.print("uninitialized object created at ");
                dst.print(encoding >> 8);
                return;
            default:
                dst.print("<bad verification type tag " + tag + '>');
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public int extractVerificationType(int startOffset, int tag) {
        if (tag == 7 || tag == 8) {
            return tag | (u2(startOffset + 1) << 8);
        }
        return tag;
    }

    static int[] reallocBuffer(int[] buffer, int needed) {
        if (buffer == null) {
            return new int[(needed + 10)];
        }
        if (needed <= buffer.length) {
            return buffer;
        }
        int[] tmp = new int[(needed + 10)];
        System.arraycopy(buffer, 0, tmp, 0, buffer.length);
        return tmp;
    }

    /* access modifiers changed from: package-private */
    public int extractVerificationTypes(int startOffset, int count, int startIndex, int[] buffer) {
        int tag;
        int offset = startOffset;
        while (true) {
            count--;
            if (count < 0) {
                return offset;
            }
            if (offset >= this.dataLength) {
                tag = -1;
            } else {
                byte tag2 = this.data[offset];
                int encoding = extractVerificationType(offset, tag2);
                offset += (tag2 == 7 || tag2 == 8) ? 3 : 1;
                tag = encoding;
            }
            buffer[startIndex] = tag;
            startIndex++;
        }
    }

    /* access modifiers changed from: package-private */
    public void printVerificationTypes(int[] encodings, int startIndex, int count, ClassTypeWriter dst) {
        int regno = 0;
        for (int i = 0; i < startIndex + count; i++) {
            int encoding = encodings[i];
            int tag = encoding & 255;
            if (i >= startIndex) {
                dst.print("  ");
                if (regno < 100) {
                    if (regno < 10) {
                        dst.print(' ');
                    }
                    dst.print(' ');
                }
                dst.print(regno);
                dst.print(": ");
                printVerificationType(encoding, dst);
                dst.println();
            }
            regno++;
            if (tag == 3 || tag == 4) {
                regno++;
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        ClassTypeWriter classTypeWriter = dst;
        classTypeWriter.print("Attribute \"");
        classTypeWriter.print(getName());
        classTypeWriter.print("\", length:");
        classTypeWriter.print(getLength());
        classTypeWriter.print(", number of entries: ");
        classTypeWriter.println(this.numEntries);
        int ipos = 2;
        int pc_offset = -1;
        Method method = getMethod();
        int[] encodedTypes = null;
        int curLocals = (method.getStaticFlag() ^ true ? 1 : 0) + method.arg_types.length;
        int i = 0;
        while (i < this.numEntries) {
            if (ipos < this.dataLength) {
                int ipos2 = ipos + 1;
                int tag = u1(ipos);
                int pc_offset2 = pc_offset + 1;
                if (tag <= 127) {
                    pc_offset = pc_offset2 + (tag & 63);
                } else if (ipos2 + 1 < this.dataLength) {
                    pc_offset = pc_offset2 + u2(ipos2);
                    ipos2 += 2;
                } else {
                    return;
                }
                classTypeWriter.print("  offset: ");
                classTypeWriter.print(pc_offset);
                if (tag <= 63) {
                    classTypeWriter.println(" - same_frame");
                } else if (tag <= 127 || tag == 247) {
                    classTypeWriter.println(tag <= 127 ? " - same_locals_1_stack_item_frame" : " - same_locals_1_stack_item_frame_extended");
                    encodedTypes = reallocBuffer(encodedTypes, 1);
                    ipos2 = extractVerificationTypes(ipos2, 1, 0, encodedTypes);
                    printVerificationTypes(encodedTypes, 0, 1, classTypeWriter);
                } else if (tag <= 246) {
                    classTypeWriter.print(" - tag reserved for future use - ");
                    classTypeWriter.println(tag);
                    int tag2 = ipos2;
                    return;
                } else if (tag <= 250) {
                    int count = 251 - tag;
                    classTypeWriter.print(" - chop_frame - undefine ");
                    classTypeWriter.print(count);
                    classTypeWriter.println(" locals");
                    curLocals -= count;
                } else if (tag == 251) {
                    classTypeWriter.println(" - same_frame_extended");
                } else if (tag <= 254) {
                    int count2 = tag - 251;
                    classTypeWriter.print(" - append_frame - define ");
                    classTypeWriter.print(count2);
                    classTypeWriter.println(" more locals");
                    encodedTypes = reallocBuffer(encodedTypes, curLocals + count2);
                    ipos2 = extractVerificationTypes(ipos2, count2, curLocals, encodedTypes);
                    printVerificationTypes(encodedTypes, curLocals, count2, classTypeWriter);
                    curLocals += count2;
                } else if (ipos2 + 1 < this.dataLength) {
                    int num_locals = u2(ipos2);
                    classTypeWriter.print(" - full_frame.  Locals count: ");
                    classTypeWriter.println(num_locals);
                    int[] encodedTypes2 = reallocBuffer(encodedTypes, num_locals);
                    int ipos3 = extractVerificationTypes(ipos2 + 2, num_locals, 0, encodedTypes2);
                    printVerificationTypes(encodedTypes2, 0, num_locals, classTypeWriter);
                    curLocals = num_locals;
                    if (ipos3 + 1 < this.dataLength) {
                        int num_stack = u2(ipos3);
                        int ipos4 = ipos3 + 2;
                        classTypeWriter.print("    (end of locals)");
                        int nspaces = Integer.toString(pc_offset).length();
                        while (true) {
                            nspaces--;
                            if (nspaces < 0) {
                                break;
                            }
                            classTypeWriter.print(' ');
                        }
                        classTypeWriter.print("       Stack count: ");
                        classTypeWriter.println(num_stack);
                        encodedTypes = reallocBuffer(encodedTypes2, num_stack);
                        int ipos5 = extractVerificationTypes(ipos4, num_stack, 0, encodedTypes);
                        printVerificationTypes(encodedTypes, 0, num_stack, classTypeWriter);
                        int curStack = num_stack;
                        ipos2 = ipos5;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
                if (ipos2 < 0) {
                    classTypeWriter.println("<ERROR - missing data>");
                    return;
                } else {
                    i++;
                    ipos = ipos2;
                }
            } else {
                return;
            }
        }
    }
}
