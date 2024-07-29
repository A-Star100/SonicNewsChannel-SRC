package gnu.kawa.functions;

import gnu.lists.FString;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.InPort;
import gnu.mapping.Procedure1;
import gnu.text.CompoundFormat;
import gnu.text.LineBufferedReader;
import gnu.text.LiteralFormat;
import gnu.text.PadFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.util.Vector;

public class ParseFormat extends Procedure1 {
    public static final int PARAM_FROM_LIST = -1610612736;
    public static final int PARAM_UNSPECIFIED = -1073741824;
    public static final int SEEN_HASH = 16;
    public static final int SEEN_MINUS = 1;
    public static final int SEEN_PLUS = 2;
    public static final int SEEN_SPACE = 4;
    public static final int SEEN_ZERO = 8;
    public static final ParseFormat parseFormat = new ParseFormat(false);
    boolean emacsStyle = true;

    public ParseFormat(boolean emacsStyle2) {
        this.emacsStyle = emacsStyle2;
    }

    public ReportFormat parseFormat(LineBufferedReader fmt) throws ParseException, IOException {
        return parseFormat(fmt, this.emacsStyle ? '?' : '~');
    }

    public static ReportFormat parseFormat(LineBufferedReader lineBufferedReader, char c) throws ParseException, IOException {
        int i;
        PadFormat padFormat;
        int i2;
        int i3;
        boolean z;
        char c2 = c;
        int i4 = 100;
        StringBuffer stringBuffer = new StringBuffer(100);
        Vector vector = new Vector();
        while (true) {
            int read = lineBufferedReader.read();
            if (read >= 0) {
                if (read != c2) {
                    stringBuffer.append((char) read);
                } else {
                    read = lineBufferedReader.read();
                    if (read == c2) {
                        stringBuffer.append((char) read);
                    }
                }
            }
            int length = stringBuffer.length();
            int i5 = 0;
            if (length > 0) {
                char[] cArr = new char[length];
                stringBuffer.getChars(0, length, cArr, 0);
                stringBuffer.setLength(0);
                vector.addElement(new LiteralFormat(cArr));
            }
            boolean z2 = true;
            if (read < 0) {
                int size = vector.size();
                if (size == 1) {
                    Object elementAt = vector.elementAt(0);
                    if (elementAt instanceof ReportFormat) {
                        return (ReportFormat) elementAt;
                    }
                }
                Format[] formatArr = new Format[size];
                vector.copyInto(formatArr);
                return new CompoundFormat(formatArr);
            }
            if (read != 36) {
                boolean z3 = false;
            } else if (Character.digit((char) lineBufferedReader.read(), 10) >= 0) {
                do {
                    read = lineBufferedReader.read();
                } while (Character.digit((char) read, 10) >= 0);
            } else {
                throw new ParseException("missing number (position) after '%$'", -1);
            }
            boolean z32 = false;
            while (true) {
                char c3 = (char) read;
                switch (c3) {
                    case ' ':
                        z = z32 | true;
                        break;
                    case '#':
                        z = z32 | true;
                        break;
                    case '+':
                        z = z32 | true;
                        break;
                    case '-':
                        z = z32 | true;
                        break;
                    case '0':
                        z = z32 | true;
                        break;
                    default:
                        int digit = Character.digit(c3, 10);
                        if (digit >= 0) {
                            while (true) {
                                read = lineBufferedReader.read();
                                int digit2 = Character.digit((char) read, 10);
                                if (digit2 >= 0) {
                                    digit = (digit * 10) + digit2;
                                }
                            }
                        } else if (read == 42) {
                            digit = -1610612736;
                        } else {
                            digit = -1073741824;
                        }
                        if (read != 46) {
                            i = -1073741824;
                        } else if (read == 42) {
                            i = -1610612736;
                        } else {
                            int i6 = 0;
                            while (true) {
                                read = lineBufferedReader.read();
                                int digit3 = Character.digit((char) read, 10);
                                if (digit3 < 0) {
                                    i = i6;
                                } else {
                                    i6 = (i6 * 10) + digit3;
                                }
                            }
                        }
                        char c4 = ' ';
                        switch (read) {
                            case 83:
                            case 115:
                                if (read != 83) {
                                    z2 = false;
                                }
                                padFormat = new ObjectFormat(z2, i);
                                break;
                            case 88:
                            case 100:
                            case 105:
                            case 111:
                            case 120:
                                int i7 = 16;
                                if (read == i4 || read == 105) {
                                    i2 = 0;
                                    i7 = 10;
                                } else if (read == 111) {
                                    i2 = 0;
                                    i7 = 8;
                                } else {
                                    i2 = read == 88 ? 32 : 0;
                                }
                                int i8 = z32 & true ? 48 : 32;
                                if (z32 && true) {
                                    i2 |= 8;
                                }
                                if (z32 && true) {
                                    i2 |= 2;
                                }
                                if (z32 && true) {
                                    i2 |= 16;
                                }
                                if (z32 && true) {
                                    i3 = i2 | 4;
                                } else {
                                    i3 = i2;
                                }
                                if (i == -1073741824) {
                                    padFormat = IntegerFormat.getInstance(i7, digit, i8, -1073741824, -1073741824, i3);
                                    break;
                                } else {
                                    z32 &= true;
                                    padFormat = IntegerFormat.getInstance(i7, i, 48, -1073741824, -1073741824, i3 | 64);
                                    break;
                                }
                                break;
                            case 101:
                            case 102:
                            case 103:
                                padFormat = new ObjectFormat(false);
                                break;
                            default:
                                throw new ParseException("unknown format character '" + read + "'", -1);
                        }
                        if (digit > 0) {
                            if (z32 && true) {
                                c4 = '0';
                            }
                            if (z32 && true) {
                                i5 = 100;
                            } else if (c4 == '0') {
                                i5 = -1;
                            }
                            padFormat = new PadFormat(padFormat, digit, c4, i5);
                        }
                        vector.addElement(padFormat);
                        i4 = 100;
                        continue;
                }
                z32 = z;
                read = lineBufferedReader.read();
            }
        }
    }

    public Object apply1(Object arg) {
        return asFormat(arg, this.emacsStyle ? '?' : '~');
    }

    public static ReportFormat asFormat(Object arg, char style) {
        InPort iport;
        try {
            if (arg instanceof ReportFormat) {
                return (ReportFormat) arg;
            }
            if (style == '~') {
                return new LispFormat(arg.toString());
            }
            if (arg instanceof FString) {
                FString str = (FString) arg;
                iport = new CharArrayInPort(str.data, str.size);
            } else {
                iport = new CharArrayInPort(arg.toString());
            }
            ReportFormat parseFormat2 = parseFormat(iport, style);
            iport.close();
            return parseFormat2;
        } catch (IOException ex) {
            throw new RuntimeException("Error parsing format (" + ex + ")");
        } catch (ParseException ex2) {
            throw new RuntimeException("Invalid format (" + ex2 + ")");
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("End while parsing format");
        } catch (Throwable th) {
            iport.close();
            throw th;
        }
    }
}
