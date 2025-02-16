package gnu.kawa.lispexpr;

import gnu.mapping.InPort;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.regex.Pattern;

public class ReaderDispatchMisc extends ReadTableEntry {
    private static ReaderDispatchMisc instance = new ReaderDispatchMisc();
    protected int code;

    public static ReaderDispatchMisc getInstance() {
        return instance;
    }

    public ReaderDispatchMisc() {
        this.code = -1;
    }

    public ReaderDispatchMisc(int code2) {
        this.code = code2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: char} */
    /* JADX WARNING: type inference failed for: r3v0, types: [int, boolean] */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object read(gnu.text.Lexer r9, int r10, int r11) throws java.io.IOException, gnu.text.SyntaxException {
        /*
            r8 = this;
            r0 = r9
            gnu.kawa.lispexpr.LispReader r0 = (gnu.kawa.lispexpr.LispReader) r0
            int r1 = r8.code
            if (r1 < 0) goto L_0x0009
            r10 = r1
        L_0x0009:
            r1 = 35
            r2 = 2
            r3 = 0
            switch(r10) {
                case 33: goto L_0x01e5;
                case 35: goto L_0x01c9;
                case 44: goto L_0x0116;
                case 47: goto L_0x0111;
                case 58: goto L_0x00ed;
                case 59: goto L_0x00c8;
                case 61: goto L_0x00ac;
                case 66: goto L_0x00a7;
                case 68: goto L_0x00a0;
                case 69: goto L_0x0095;
                case 70: goto L_0x0080;
                case 73: goto L_0x0095;
                case 79: goto L_0x0079;
                case 82: goto L_0x0052;
                case 83: goto L_0x004c;
                case 84: goto L_0x0049;
                case 85: goto L_0x004c;
                case 88: goto L_0x0042;
                case 92: goto L_0x003d;
                case 124: goto L_0x0018;
                default: goto L_0x0010;
            }
        L_0x0010:
            java.lang.String r10 = "An invalid #-construct was read."
            r9.error(r10)
            gnu.mapping.Values r9 = gnu.mapping.Values.empty
            return r9
        L_0x0018:
            gnu.text.LineBufferedReader r9 = r0.getPort()
            boolean r10 = r9 instanceof gnu.mapping.InPort
            r11 = 124(0x7c, float:1.74E-43)
            if (r10 == 0) goto L_0x0029
            r2 = r9
            gnu.mapping.InPort r2 = (gnu.mapping.InPort) r2
            char r3 = r2.readState
            r2.readState = r11
        L_0x0029:
            r0.readNestedComment(r1, r11)     // Catch:{ all -> 0x0035 }
            if (r10 == 0) goto L_0x0032
            gnu.mapping.InPort r9 = (gnu.mapping.InPort) r9
            r9.readState = r3
        L_0x0032:
            gnu.mapping.Values r9 = gnu.mapping.Values.empty
            return r9
        L_0x0035:
            r11 = move-exception
            if (r10 == 0) goto L_0x003c
            gnu.mapping.InPort r9 = (gnu.mapping.InPort) r9
            r9.readState = r3
        L_0x003c:
            throw r11
        L_0x003d:
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readCharacter(r0)
            return r9
        L_0x0042:
            r9 = 16
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readNumberWithRadix(r3, r0, r9)
            return r9
        L_0x0049:
            java.lang.Boolean r9 = java.lang.Boolean.TRUE
            return r9
        L_0x004c:
            char r9 = (char) r10
            gnu.lists.SimpleVector r9 = gnu.kawa.lispexpr.LispReader.readSimpleVector(r0, r9)
            return r9
        L_0x0052:
            r10 = 36
            if (r11 <= r10) goto L_0x0074
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "the radix "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r11 = r1.append(r11)
            java.lang.String r1 = " is too big (max is 36)"
            java.lang.StringBuilder r11 = r11.append(r1)
            java.lang.String r11 = r11.toString()
            r9.error(r11)
            r11 = 36
        L_0x0074:
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readNumberWithRadix(r3, r0, r11)
            return r9
        L_0x0079:
            r9 = 8
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readNumberWithRadix(r3, r0, r9)
            return r9
        L_0x0080:
            int r9 = r9.peek()
            char r9 = (char) r9
            boolean r9 = java.lang.Character.isDigit(r9)
            if (r9 == 0) goto L_0x0092
            r9 = 70
            gnu.lists.SimpleVector r9 = gnu.kawa.lispexpr.LispReader.readSimpleVector(r0, r9)
            return r9
        L_0x0092:
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            return r9
        L_0x0095:
            r0.tokenBufferAppend(r1)
            r0.tokenBufferAppend(r10)
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readNumberWithRadix(r2, r0, r3)
            return r9
        L_0x00a0:
            r9 = 10
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readNumberWithRadix(r3, r0, r9)
            return r9
        L_0x00a7:
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readNumberWithRadix(r3, r0, r2)
            return r9
        L_0x00ac:
            java.lang.Object r10 = r0.readObject()
            boolean r9 = r9 instanceof gnu.kawa.lispexpr.LispReader
            if (r9 == 0) goto L_0x00c7
            gnu.kawa.util.GeneralHashTable<java.lang.Integer, java.lang.Object> r9 = r0.sharedStructureTable
            if (r9 != 0) goto L_0x00c0
            gnu.kawa.util.GeneralHashTable r9 = new gnu.kawa.util.GeneralHashTable
            r9.<init>()
            r0.sharedStructureTable = r9
        L_0x00c0:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            r9.put(r11, r10)
        L_0x00c7:
            return r10
        L_0x00c8:
            gnu.text.LineBufferedReader r9 = r0.getPort()
            boolean r10 = r9 instanceof gnu.mapping.InPort
            if (r10 == 0) goto L_0x00d9
            r11 = r9
            gnu.mapping.InPort r11 = (gnu.mapping.InPort) r11
            char r3 = r11.readState
            r1 = 59
            r11.readState = r1
        L_0x00d9:
            r0.readObject()     // Catch:{ all -> 0x00e5 }
            if (r10 == 0) goto L_0x00e2
            gnu.mapping.InPort r9 = (gnu.mapping.InPort) r9
            r9.readState = r3
        L_0x00e2:
            gnu.mapping.Values r9 = gnu.mapping.Values.empty
            return r9
        L_0x00e5:
            r11 = move-exception
            if (r10 == 0) goto L_0x00ec
            gnu.mapping.InPort r9 = (gnu.mapping.InPort) r9
            r9.readState = r3
        L_0x00ec:
            throw r11
        L_0x00ed:
            int r9 = r0.tokenBufferLength
            int r10 = r0.read()
            r11 = 80
            gnu.kawa.lispexpr.ReadTable r1 = gnu.kawa.lispexpr.ReadTable.getCurrent()
            r0.readToken(r10, r11, r1)
            int r10 = r0.tokenBufferLength
            int r10 = r10 - r9
            java.lang.String r11 = new java.lang.String
            char[] r1 = r0.tokenBuffer
            r11.<init>(r1, r9, r10)
            r0.tokenBufferLength = r9
            java.lang.String r9 = r11.intern()
            gnu.expr.Keyword r9 = gnu.expr.Keyword.make(r9)
            return r9
        L_0x0111:
            java.util.regex.Pattern r9 = readRegex(r9, r10, r11)
            return r9
        L_0x0116:
            gnu.text.LineBufferedReader r10 = r0.getPort()
            int r10 = r10.peek()
            r11 = 40
            if (r10 != r11) goto L_0x01c1
            java.lang.Object r10 = r0.readObject()
            int r11 = gnu.lists.LList.listLength(r10, r3)
            if (r11 <= 0) goto L_0x01c1
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10
            java.lang.Object r0 = r10.getCar()
            boolean r0 = r0 instanceof gnu.mapping.Symbol
            if (r0 == 0) goto L_0x01c1
            java.lang.Object r0 = r10.getCar()
            java.lang.String r0 = r0.toString()
            gnu.kawa.lispexpr.ReadTable r1 = gnu.kawa.lispexpr.ReadTable.getCurrent()
            java.lang.Object r1 = r1.getReaderCtor(r0)
            if (r1 != 0) goto L_0x015f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "unknown reader constructor "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r10 = r10.toString()
            r9.error(r10)
            goto L_0x01c0
        L_0x015f:
            boolean r2 = r1 instanceof gnu.mapping.Procedure
            if (r2 != 0) goto L_0x016d
            boolean r2 = r1 instanceof gnu.bytecode.Type
            if (r2 != 0) goto L_0x016d
            java.lang.String r10 = "reader constructor must be procedure or type name"
            r9.error(r10)
            goto L_0x01c0
        L_0x016d:
            int r11 = r11 + -1
            boolean r2 = r1 instanceof gnu.bytecode.Type
            int r4 = r2 + r11
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Object r10 = r10.getCdr()
            r5 = 0
        L_0x017a:
            if (r5 >= r11) goto L_0x018d
            gnu.lists.Pair r10 = (gnu.lists.Pair) r10
            int r6 = r2 + r5
            java.lang.Object r7 = r10.getCar()
            r4[r6] = r7
            java.lang.Object r10 = r10.getCdr()
            int r5 = r5 + 1
            goto L_0x017a
        L_0x018d:
            if (r2 <= 0) goto L_0x0198
            r4[r3] = r1     // Catch:{ all -> 0x019f }
            gnu.kawa.reflect.Invoke r10 = gnu.kawa.reflect.Invoke.make     // Catch:{ all -> 0x019f }
            java.lang.Object r9 = r10.applyN(r4)     // Catch:{ all -> 0x019f }
            return r9
        L_0x0198:
            gnu.mapping.Procedure r1 = (gnu.mapping.Procedure) r1     // Catch:{ all -> 0x019f }
            java.lang.Object r9 = r1.applyN(r4)     // Catch:{ all -> 0x019f }
            return r9
        L_0x019f:
            r10 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r1 = "caught "
            java.lang.StringBuilder r11 = r11.append(r1)
            java.lang.StringBuilder r10 = r11.append(r10)
            java.lang.String r11 = " applying reader constructor "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r0)
            java.lang.String r10 = r10.toString()
            r9.error(r10)
        L_0x01c0:
            goto L_0x01c6
        L_0x01c1:
            java.lang.String r10 = "a non-empty list starting with a symbol must follow #,"
            r9.error(r10)
        L_0x01c6:
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            return r9
        L_0x01c9:
            boolean r10 = r9 instanceof gnu.kawa.lispexpr.LispReader
            if (r10 == 0) goto L_0x01dd
            gnu.kawa.util.GeneralHashTable<java.lang.Integer, java.lang.Object> r10 = r0.sharedStructureTable
            if (r10 == 0) goto L_0x01dd
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            java.lang.Object r10 = r10.get(r11, r9)
            if (r10 == r9) goto L_0x01dd
            return r10
        L_0x01dd:
            java.lang.String r10 = "an unrecognized #n# back-reference was read"
            r9.error(r10)
            gnu.mapping.Values r9 = gnu.mapping.Values.empty
            return r9
        L_0x01e5:
            java.lang.Object r9 = gnu.kawa.lispexpr.LispReader.readSpecial(r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderDispatchMisc.read(gnu.text.Lexer, int, int):java.lang.Object");
    }

    public static Pattern readRegex(Lexer in, int ch, int count) throws IOException, SyntaxException {
        int startPos = in.tokenBufferLength;
        LineBufferedReader port = in.getPort();
        char saveReadState = 0;
        int flags = 0;
        if (port instanceof InPort) {
            saveReadState = ((InPort) port).readState;
            ((InPort) port).readState = '/';
        }
        while (true) {
            try {
                int c = port.read();
                if (c < 0) {
                    in.eofError("unexpected EOF in regex literal");
                }
                if (c == ch) {
                    break;
                }
                if (c == 92) {
                    c = port.read();
                    if ((c != 32 && c != 9 && c != 13 && c != 10) || !(in instanceof LispReader) || (c = ((LispReader) in).readEscape(c)) != -2) {
                        if (c < 0) {
                            in.eofError("unexpected EOF in regex literal");
                        }
                        if (c != ch) {
                            in.tokenBufferAppend(92);
                        }
                    }
                }
                in.tokenBufferAppend(c);
            } finally {
                in.tokenBufferLength = startPos;
                if (port instanceof InPort) {
                    ((InPort) port).readState = saveReadState;
                }
            }
        }
        String pattern = new String(in.tokenBuffer, startPos, in.tokenBufferLength - startPos);
        while (true) {
            int c2 = in.peek();
            if (c2 != 105) {
                if (c2 != 73) {
                    if (c2 != 115) {
                        if (c2 != 83) {
                            if (c2 != 109) {
                                if (c2 != 77) {
                                    if (!Character.isLetter(c2)) {
                                        break;
                                    }
                                    in.error("unrecognized regex option '" + ((char) c2) + '\'');
                                    in.skip();
                                }
                            }
                            flags |= 8;
                            in.skip();
                        }
                    }
                    flags |= 32;
                    in.skip();
                }
            }
            flags |= 66;
            in.skip();
        }
        return Pattern.compile(pattern, flags);
    }
}
