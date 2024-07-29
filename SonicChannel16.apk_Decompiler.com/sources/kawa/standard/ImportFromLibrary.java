package kawa.standard;

import com.google.appinventor.components.runtime.Component;
import gnu.expr.Expression;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class ImportFromLibrary extends Syntax {
    private static final String BUILTIN = "";
    private static final String MISSING = null;
    static final String[][] SRFI97Map = {new String[]{Component.TYPEFACE_SANSSERIF, "lists", "gnu.kawa.slib.srfi1"}, new String[]{Component.TYPEFACE_SERIF, "and-let*", "gnu.kawa.slib.srfi2"}, new String[]{"5", "let", null}, new String[]{"6", "basic-string-ports", ""}, new String[]{"8", "receive", ""}, new String[]{"9", "records", ""}, new String[]{"11", "let-values", ""}, new String[]{"13", "strings", "gnu.kawa.slib.srfi13"}, new String[]{"14", "char-sets", null}, new String[]{"16", "case-lambda", ""}, new String[]{"17", "generalized-set!", ""}, new String[]{"18", "multithreading", null}, new String[]{"19", "time", null}, new String[]{"21", "real-time-multithreading", null}, new String[]{"23", "error", ""}, new String[]{"25", "multi-dimensional-arrays", ""}, new String[]{"26", "cut", ""}, new String[]{"27", "random-bits", null}, new String[]{"28", "basic-format-strings", ""}, new String[]{"29", "localization", null}, new String[]{"31", "rec", null}, new String[]{"38", "with-shared-structure", null}, new String[]{"39", "parameters", ""}, new String[]{"41", "streams", null}, new String[]{"42", "eager-comprehensions", null}, new String[]{"43", "vectors", null}, new String[]{"44", "collections", null}, new String[]{"45", "lazy", null}, new String[]{"46", "syntax-rules", null}, new String[]{"47", "arrays", null}, new String[]{"48", "intermediate-format-strings", null}, new String[]{"51", "rest-values", null}, new String[]{"54", "cat", null}, new String[]{"57", "records", null}, new String[]{"59", "vicinities", null}, new String[]{"60", "integer-bits", null}, new String[]{"61", "cond", null}, new String[]{"63", "arrays", null}, new String[]{"64", "testing", "gnu.kawa.slib.testing"}, new String[]{"66", "octet-vectors", null}, new String[]{"67", "compare-procedures", null}, new String[]{"69", "basic-hash-tables", "gnu.kawa.slib.srfi69"}, new String[]{"71", "let", null}, new String[]{"74", "blobs", null}, new String[]{"78", "lightweight-testing", null}, new String[]{"86", "mu-and-nu", null}, new String[]{"87", "case", null}, new String[]{"95", "sorting-and-merging", "kawa.lib.srfi95"}};
    public static final ImportFromLibrary instance = new ImportFromLibrary();
    public String[] classPrefixPath = {"", "kawa.lib."};

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v46, resolved type: gnu.mapping.Procedure} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0148  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0151  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scanForDefinitions(gnu.lists.Pair r26, java.util.Vector r27, gnu.expr.ScopeExp r28, kawa.lang.Translator r29) {
        /*
            r25 = this;
            r1 = r25
            r8 = r29
            r0 = 0
            java.lang.Object r9 = r26.getCdr()
            boolean r2 = r9 instanceof gnu.lists.Pair
            r3 = 0
            if (r2 != 0) goto L_0x000f
            return r3
        L_0x000f:
            r2 = r9
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r4 = r2.getCar()
            int r5 = gnu.lists.LList.listLength(r4, r3)
            r6 = 101(0x65, float:1.42E-43)
            if (r5 > 0) goto L_0x0024
            java.lang.String r5 = "expected <library reference> which must be a list"
            r8.error(r6, r5)
            return r3
        L_0x0024:
            java.lang.Object r10 = r2.getCdr()
            boolean r5 = r10 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x0043
            r5 = r10
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r5 = r5.getCar()
            boolean r5 = r5 instanceof gnu.mapping.Procedure
            if (r5 == 0) goto L_0x0043
            r5 = r10
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r5 = r5.getCar()
            r0 = r5
            gnu.mapping.Procedure r0 = (gnu.mapping.Procedure) r0
            r11 = r0
            goto L_0x0044
        L_0x0043:
            r11 = r0
        L_0x0044:
            r0 = 0
            r5 = 0
            java.lang.StringBuffer r7 = new java.lang.StringBuffer
            r7.<init>()
            r12 = r7
            r13 = r0
            r14 = r2
            r15 = r4
            r7 = r5
        L_0x0050:
            boolean r0 = r15 instanceof gnu.lists.Pair
            r2 = 46
            if (r0 == 0) goto L_0x00c1
            r14 = r15
            gnu.lists.Pair r14 = (gnu.lists.Pair) r14
            java.lang.Object r0 = r14.getCar()
            java.lang.Object r4 = r14.getCdr()
            boolean r5 = r0 instanceof gnu.lists.Pair
            if (r5 == 0) goto L_0x0098
            if (r13 == 0) goto L_0x007d
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "duplicate version reference - was "
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.StringBuilder r2 = r2.append(r13)
            java.lang.String r2 = r2.toString()
            r8.error(r6, r2)
        L_0x007d:
            r2 = r0
            java.io.PrintStream r5 = java.lang.System.err
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r3 = "import version "
            java.lang.StringBuilder r3 = r13.append(r3)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            r5.println(r3)
            r13 = r2
            goto L_0x00be
        L_0x0098:
            boolean r3 = r0 instanceof java.lang.String
            if (r3 == 0) goto L_0x00aa
            boolean r2 = r4 instanceof gnu.lists.Pair
            if (r2 == 0) goto L_0x00a5
            java.lang.String r2 = "source specifier must be last elemnt in library reference"
            r8.error(r6, r2)
        L_0x00a5:
            r2 = r0
            java.lang.String r2 = (java.lang.String) r2
            r7 = r2
            goto L_0x00be
        L_0x00aa:
            int r3 = r12.length()
            if (r3 <= 0) goto L_0x00b3
            r12.append(r2)
        L_0x00b3:
            java.lang.String r2 = r0.toString()
            java.lang.String r2 = gnu.expr.Compilation.mangleNameIfNeeded(r2)
            r12.append(r2)
        L_0x00be:
            r15 = r4
            r3 = 0
            goto L_0x0050
        L_0x00c1:
            r0 = 0
            if (r7 == 0) goto L_0x00e4
            r5 = r28
            gnu.expr.ModuleInfo r0 = kawa.standard.require.lookupModuleFromSourcePath(r7, r5)
            if (r0 != 0) goto L_0x00e6
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "malformed URL: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            r8.error(r6, r2)
            r2 = 0
            return r2
        L_0x00e4:
            r5 = r28
        L_0x00e6:
            java.lang.String r3 = r12.toString()
            java.lang.String r4 = "srfi."
            boolean r4 = r3.startsWith(r4)
            if (r4 == 0) goto L_0x021b
            r4 = 5
            java.lang.String r4 = r3.substring(r4)
            java.lang.String r4 = gnu.expr.Compilation.demangleName(r4)
            int r2 = r4.indexOf(r2)
            if (r2 >= 0) goto L_0x010a
            r18 = 0
            int r2 = r4.length()
            r6 = r18
            goto L_0x0110
        L_0x010a:
            int r6 = r2 + 1
            java.lang.String r6 = r4.substring(r6)
        L_0x0110:
            r19 = 0
            r20 = r0
            r0 = 2
            if (r2 >= r0) goto L_0x0126
            r21 = r3
            r3 = 0
            char r0 = r4.charAt(r3)
            r3 = 58
            if (r0 != r3) goto L_0x0123
            goto L_0x0128
        L_0x0123:
            r23 = r2
            goto L_0x0144
        L_0x0126:
            r21 = r3
        L_0x0128:
            r0 = 1
        L_0x0129:
            if (r0 != r2) goto L_0x0135
            r3 = 1
            java.lang.String r19 = r4.substring(r3, r2)
            r23 = r2
            r2 = r19
            goto L_0x0146
        L_0x0135:
            char r3 = r4.charAt(r0)
            r23 = r2
            r2 = 10
            int r2 = java.lang.Character.digit(r3, r2)
            if (r2 >= 0) goto L_0x020c
        L_0x0144:
            r2 = r19
        L_0x0146:
            if (r2 != 0) goto L_0x0151
            java.lang.String r0 = "SRFI library reference must have the form: (srfi :NNN [name])"
            r3 = 101(0x65, float:1.42E-43)
            r8.error(r3, r0)
            r3 = 0
            return r3
        L_0x0151:
            java.lang.String[][] r0 = SRFI97Map
            int r0 = r0.length
        L_0x0154:
            int r0 = r0 + -1
            if (r0 >= 0) goto L_0x017a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r24 = r4
            java.lang.String r4 = "unknown SRFI number '"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r4 = "' in SRFI library reference"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r4 = 101(0x65, float:1.42E-43)
            r8.error(r4, r3)
            r3 = 0
            return r3
        L_0x017a:
            r24 = r4
            r3 = 0
            java.lang.String[][] r4 = SRFI97Map
            r16 = r4[r0]
            r5 = r16[r3]
            boolean r3 = r5.equals(r2)
            if (r3 == 0) goto L_0x0201
            r3 = r4[r0]
            r4 = 1
            r5 = r3[r4]
            r4 = 2
            r3 = r3[r4]
            if (r6 == 0) goto L_0x01c5
            boolean r4 = r6.equals(r5)
            if (r4 != 0) goto L_0x01c5
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r22 = r0
            java.lang.String r0 = "the name of SRFI "
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r4 = " should be '"
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.StringBuilder r0 = r0.append(r5)
            r4 = 39
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            r4 = 119(0x77, float:1.67E-43)
            r8.error(r4, r0)
            goto L_0x01c7
        L_0x01c5:
            r22 = r0
        L_0x01c7:
            java.lang.String r0 = ""
            if (r3 != r0) goto L_0x01ce
            r18 = 1
            return r18
        L_0x01ce:
            r18 = 1
            java.lang.String r0 = MISSING
            if (r3 != r0) goto L_0x01fe
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r4 = "sorry - Kawa does not support SRFI "
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r4 = " ("
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.StringBuilder r0 = r0.append(r5)
            r4 = 41
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            r4 = 101(0x65, float:1.42E-43)
            r8.error(r4, r0)
            r4 = 0
            return r4
        L_0x01fe:
            r0 = r3
            r6 = r0
            goto L_0x0223
        L_0x0201:
            r22 = r0
            r4 = 2
            r18 = 1
            r5 = r28
            r4 = r24
            goto L_0x0154
        L_0x020c:
            r24 = r4
            r4 = 2
            r18 = 1
            int r0 = r0 + 1
            r5 = r28
            r2 = r23
            r4 = r24
            goto L_0x0129
        L_0x021b:
            r20 = r0
            r21 = r3
            r18 = 1
            r6 = r21
        L_0x0223:
            java.lang.String[] r0 = r1.classPrefixPath
            int r5 = r0.length
            r0 = 0
            r2 = r0
        L_0x0228:
            if (r2 >= r5) goto L_0x024f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String[] r3 = r1.classPrefixPath
            r3 = r3[r2]
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r3 = r0.toString()
            gnu.expr.ModuleManager r0 = gnu.expr.ModuleManager.getInstance()     // Catch:{ Exception -> 0x024a }
            gnu.expr.ModuleInfo r0 = r0.findWithClassName(r3)     // Catch:{ Exception -> 0x024a }
            r20 = r0
            goto L_0x024c
        L_0x024a:
            r0 = move-exception
        L_0x024c:
            int r2 = r2 + 1
            goto L_0x0228
        L_0x024f:
            if (r20 != 0) goto L_0x026b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "unknown class "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r0 = r0.toString()
            r2 = 101(0x65, float:1.42E-43)
            r8.error(r2, r0)
            r2 = 0
            return r2
        L_0x026b:
            r2 = 0
            r3 = r20
            r4 = r11
            r16 = r5
            r5 = r27
            r21 = r6
            r17 = 1
            r6 = r28
            r18 = r7
            r7 = r29
            kawa.standard.require.importDefinitions(r2, r3, r4, r5, r6, r7)
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.ImportFromLibrary.scanForDefinitions(gnu.lists.Pair, java.util.Vector, gnu.expr.ScopeExp, kawa.lang.Translator):boolean");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
