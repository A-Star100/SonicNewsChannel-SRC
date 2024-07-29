package kawa;

import gnu.bytecode.ZipLoader;
import gnu.expr.Compilation;
import gnu.expr.CompiledModule;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleManager;
import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;

public class Shell {
    private static Class[] boolClasses;
    public static ThreadLocal currentLoadPath = new ThreadLocal();
    public static Object[] defaultFormatInfo;
    public static Method defaultFormatMethod;
    public static String defaultFormatName;
    static Object[][] formats;
    private static Class[] httpPrinterClasses = {OutPort.class};
    private static Class[] noClasses = new Class[0];
    private static Object portArg = "(port)";
    private static Class[] xmlPrinterClasses = {OutPort.class, Object.class};

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object[][]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.ThreadLocal r0 = new java.lang.ThreadLocal
            r0.<init>()
            currentLoadPath = r0
            r0 = 0
            java.lang.Class[] r1 = new java.lang.Class[r0]
            noClasses = r1
            r1 = 1
            java.lang.Class[] r2 = new java.lang.Class[r1]
            java.lang.Class r3 = java.lang.Boolean.TYPE
            r2[r0] = r3
            boolClasses = r2
            r3 = 2
            java.lang.Class[] r4 = new java.lang.Class[r3]
            java.lang.Class<gnu.mapping.OutPort> r5 = gnu.mapping.OutPort.class
            r4[r0] = r5
            java.lang.Class<java.lang.Object> r5 = java.lang.Object.class
            r4[r1] = r5
            xmlPrinterClasses = r4
            java.lang.Class[] r4 = new java.lang.Class[r1]
            java.lang.Class<gnu.mapping.OutPort> r5 = gnu.mapping.OutPort.class
            r4[r0] = r5
            httpPrinterClasses = r4
            java.lang.String r4 = "(port)"
            portArg = r4
            r4 = 14
            java.lang.Object[][] r4 = new java.lang.Object[r4][]
            r5 = 5
            java.lang.Object[] r6 = new java.lang.Object[r5]
            java.lang.String r7 = "scheme"
            r6[r0] = r7
            java.lang.String r7 = "gnu.kawa.functions.DisplayFormat"
            r6[r1] = r7
            java.lang.String r8 = "getSchemeFormat"
            r6[r3] = r8
            r9 = 3
            r6[r9] = r2
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            r10 = 4
            r6[r10] = r2
            r4[r0] = r6
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r6 = "readable-scheme"
            r2[r0] = r6
            r2[r1] = r7
            r2[r3] = r8
            java.lang.Class[] r6 = boolClasses
            r2[r9] = r6
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r2[r10] = r6
            r4[r1] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r6 = "elisp"
            r2[r0] = r6
            r2[r1] = r7
            java.lang.String r6 = "getEmacsLispFormat"
            r2[r3] = r6
            java.lang.Class[] r8 = boolClasses
            r2[r9] = r8
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r2[r10] = r8
            r4[r3] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r8 = "readable-elisp"
            r2[r0] = r8
            r2[r1] = r7
            r2[r3] = r6
            java.lang.Class[] r6 = boolClasses
            r2[r9] = r6
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r2[r10] = r6
            r4[r9] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r6 = "clisp"
            r2[r0] = r6
            r2[r1] = r7
            java.lang.String r6 = "getCommonLispFormat"
            r2[r3] = r6
            java.lang.Class[] r8 = boolClasses
            r2[r9] = r8
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r2[r10] = r8
            r4[r10] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r8 = "readable-clisp"
            r2[r0] = r8
            r2[r1] = r7
            r2[r3] = r6
            java.lang.Class[] r8 = boolClasses
            r2[r9] = r8
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            r2[r10] = r8
            r4[r5] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r8 = "commonlisp"
            r2[r0] = r8
            r2[r1] = r7
            r2[r3] = r6
            java.lang.Class[] r8 = boolClasses
            r2[r9] = r8
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            r2[r10] = r8
            r8 = 6
            r4[r8] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r11 = "readable-commonlisp"
            r2[r0] = r11
            r2[r1] = r7
            r2[r3] = r6
            java.lang.Class[] r6 = boolClasses
            r2[r9] = r6
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r2[r10] = r6
            r6 = 7
            r4[r6] = r2
            java.lang.Object[] r2 = new java.lang.Object[r8]
            java.lang.String r6 = "xml"
            r2[r0] = r6
            java.lang.String r6 = "gnu.xml.XMLPrinter"
            r2[r1] = r6
            java.lang.String r7 = "make"
            r2[r3] = r7
            java.lang.Class[] r11 = xmlPrinterClasses
            r2[r9] = r11
            java.lang.Object r12 = portArg
            r2[r10] = r12
            r13 = 0
            r2[r5] = r13
            r14 = 8
            r4[r14] = r2
            java.lang.Object[] r2 = new java.lang.Object[r8]
            java.lang.String r14 = "html"
            r2[r0] = r14
            r2[r1] = r6
            r2[r3] = r7
            r2[r9] = r11
            r2[r10] = r12
            r2[r5] = r14
            r14 = 9
            r4[r14] = r2
            java.lang.Object[] r2 = new java.lang.Object[r8]
            java.lang.String r8 = "xhtml"
            r2[r0] = r8
            r2[r1] = r6
            r2[r3] = r7
            r2[r9] = r11
            r2[r10] = r12
            r2[r5] = r8
            r6 = 10
            r4[r6] = r2
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r5 = "cgi"
            r2[r0] = r5
            java.lang.String r5 = "gnu.kawa.xml.HttpPrinter"
            r2[r1] = r5
            r2[r3] = r7
            java.lang.Class[] r5 = httpPrinterClasses
            r2[r9] = r5
            r2[r10] = r12
            r5 = 11
            r4[r5] = r2
            java.lang.Object[] r2 = new java.lang.Object[r10]
            java.lang.String r5 = "ignore"
            r2[r0] = r5
            java.lang.String r5 = "gnu.lists.VoidConsumer"
            r2[r1] = r5
            java.lang.String r5 = "getInstance"
            r2[r3] = r5
            java.lang.Class[] r3 = noClasses
            r2[r9] = r3
            r3 = 12
            r4[r3] = r2
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r1[r0] = r13
            r0 = 13
            r4[r0] = r1
            formats = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.Shell.<clinit>():void");
    }

    public static void setDefaultFormat(String name) {
        Object[] info;
        String name2 = name.intern();
        defaultFormatName = name2;
        int i = 0;
        while (true) {
            info = formats[i];
            Object iname = info[0];
            if (iname == null) {
                System.err.println("kawa: unknown output format '" + name2 + "'");
                System.exit(-1);
            } else if (iname == name2) {
                break;
            }
            i++;
        }
        defaultFormatInfo = info;
        try {
            defaultFormatMethod = Class.forName((String) info[1]).getMethod((String) info[2], (Class[]) info[3]);
        } catch (Throwable ex) {
            System.err.println("kawa:  caught " + ex + " while looking for format '" + name2 + "'");
            System.exit(-1);
        }
        if (!defaultFormatInfo[1].equals("gnu.lists.VoidConsumer")) {
            ModuleBody.setMainPrintValues(true);
        }
    }

    public static Consumer getOutputConsumer(OutPort out) {
        Object[] info = defaultFormatInfo;
        if (out == null) {
            return VoidConsumer.getInstance();
        }
        if (info == null) {
            return Language.getDefaultLanguage().getOutputConsumer(out);
        }
        try {
            Object[] args = new Object[(info.length - 4)];
            System.arraycopy(info, 4, args, 0, args.length);
            int i = args.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                } else if (args[i] == portArg) {
                    args[i] = out;
                }
            }
            Object format = defaultFormatMethod.invoke((Object) null, args);
            if (!(format instanceof AbstractFormat)) {
                return (Consumer) format;
            }
            out.objectFormat = (AbstractFormat) format;
            return out;
        } catch (Throwable ex) {
            throw new RuntimeException("cannot get output-format '" + defaultFormatName + "' - caught " + ex);
        }
    }

    public static boolean run(Language language, Environment env) {
        OutPort perr;
        InPort inp = InPort.inDefault();
        SourceMessages messages = new SourceMessages();
        if (inp instanceof TtyInPort) {
            Procedure prompter = language.getPrompter();
            if (prompter != null) {
                ((TtyInPort) inp).setPrompter(prompter);
            }
            perr = OutPort.errDefault();
        } else {
            perr = null;
        }
        Throwable ex = run(language, env, inp, OutPort.outDefault(), perr, messages);
        if (ex == null) {
            return true;
        }
        printError(ex, messages, OutPort.errDefault());
        return false;
    }

    public static Throwable run(Language language, Environment env, InPort inp, OutPort pout, OutPort perr, SourceMessages messages) {
        AbstractFormat saveFormat = null;
        if (pout != null) {
            saveFormat = pout.objectFormat;
        }
        try {
            return run(language, env, inp, getOutputConsumer(pout), perr, (URL) null, messages);
        } finally {
            if (pout != null) {
                pout.objectFormat = saveFormat;
            }
        }
    }

    public static boolean run(Language language, Environment env, InPort inp, Consumer out, OutPort perr, URL url) {
        SourceMessages messages = new SourceMessages();
        Throwable ex = run(language, env, inp, out, perr, url, messages);
        if (ex != null) {
            printError(ex, messages, perr);
        }
        return ex == null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x00e4 A[SYNTHETIC, Splitter:B:68:0x00e4] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00dc A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Throwable run(gnu.expr.Language r18, gnu.mapping.Environment r19, gnu.mapping.InPort r20, gnu.lists.Consumer r21, gnu.mapping.OutPort r22, java.net.URL r23, gnu.text.SourceMessages r24) {
        /*
            r1 = r18
            r2 = r21
            r3 = r22
            r4 = r24
            gnu.expr.Language r5 = gnu.expr.Language.setSaveCurrent(r18)
            r6 = r20
            gnu.text.Lexer r7 = r1.getLexer(r6, r4)
            if (r3 == 0) goto L_0x0016
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            r9 = r0
            r7.setInteractive(r9)
            gnu.mapping.CallContext r10 = gnu.mapping.CallContext.getInstance()
            r0 = 0
            if (r2 == 0) goto L_0x0028
            gnu.lists.Consumer r0 = r10.consumer
            r10.consumer = r2
            r11 = r0
            goto L_0x0029
        L_0x0028:
            r11 = r0
        L_0x0029:
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ SecurityException -> 0x003e }
            java.lang.ClassLoader r12 = r0.getContextClassLoader()     // Catch:{ SecurityException -> 0x003e }
            boolean r13 = r12 instanceof gnu.bytecode.ArrayClassLoader     // Catch:{ SecurityException -> 0x003e }
            if (r13 != 0) goto L_0x003d
            gnu.bytecode.ArrayClassLoader r13 = new gnu.bytecode.ArrayClassLoader     // Catch:{ SecurityException -> 0x003e }
            r13.<init>((java.lang.ClassLoader) r12)     // Catch:{ SecurityException -> 0x003e }
            r0.setContextClassLoader(r13)     // Catch:{ SecurityException -> 0x003e }
        L_0x003d:
            goto L_0x003f
        L_0x003e:
            r0 = move-exception
        L_0x003f:
            r12 = 7
            r0 = 0
            gnu.expr.Compilation r13 = r1.parse((gnu.text.Lexer) r7, (int) r12, (gnu.expr.ModuleInfo) r0)     // Catch:{ all -> 0x00d2 }
            if (r9 == 0) goto L_0x004e
            r14 = 20
            boolean r14 = r4.checkErrors((java.io.PrintWriter) r3, (int) r14)     // Catch:{ all -> 0x00d2 }
            goto L_0x0055
        L_0x004e:
            boolean r14 = r24.seenErrors()     // Catch:{ all -> 0x00d2 }
            if (r14 != 0) goto L_0x00c4
            r14 = 0
        L_0x0055:
            if (r13 != 0) goto L_0x005c
            r8 = r19
            r15 = r23
            goto L_0x00b3
        L_0x005c:
            if (r14 == 0) goto L_0x005f
            goto L_0x003f
        L_0x005f:
            gnu.expr.ModuleExp r15 = r13.getModule()     // Catch:{ all -> 0x00d2 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r0.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.String r8 = "atInteractiveLevel$"
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x00d2 }
            int r8 = gnu.expr.ModuleExp.interactiveCounter     // Catch:{ all -> 0x00d2 }
            r16 = 1
            int r8 = r8 + 1
            gnu.expr.ModuleExp.interactiveCounter = r8     // Catch:{ all -> 0x00be }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x00be }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00be }
            r15.setName(r0)     // Catch:{ all -> 0x00be }
        L_0x0081:
            int r0 = r20.read()     // Catch:{ all -> 0x00be }
            if (r0 < 0) goto L_0x009b
            r8 = 13
            if (r0 == r8) goto L_0x009b
            r8 = 10
            if (r0 != r8) goto L_0x0090
            goto L_0x009b
        L_0x0090:
            r8 = 32
            if (r0 == r8) goto L_0x0081
            r8 = 9
            if (r0 == r8) goto L_0x0081
            r20.unread()     // Catch:{ all -> 0x00be }
        L_0x009b:
            r8 = r19
            r15 = r23
            boolean r17 = gnu.expr.ModuleExp.evalModule(r8, r10, r13, r15, r3)     // Catch:{ all -> 0x00d0 }
            if (r17 != 0) goto L_0x00a6
            goto L_0x003f
        L_0x00a6:
            boolean r1 = r2 instanceof java.io.Writer     // Catch:{ all -> 0x00d0 }
            if (r1 == 0) goto L_0x00b0
            r1 = r2
            java.io.Writer r1 = (java.io.Writer) r1     // Catch:{ all -> 0x00d0 }
            r1.flush()     // Catch:{ all -> 0x00d0 }
        L_0x00b0:
            if (r0 >= 0) goto L_0x00bd
        L_0x00b3:
            if (r2 == 0) goto L_0x00b7
            r10.consumer = r11
        L_0x00b7:
            gnu.expr.Language.restoreCurrent(r5)
            r0 = 0
            return r0
        L_0x00bd:
            goto L_0x00e7
        L_0x00be:
            r0 = move-exception
            r8 = r19
            r15 = r23
            goto L_0x00d9
        L_0x00c4:
            r8 = r19
            r15 = r23
            r16 = 1
            gnu.text.SyntaxException r0 = new gnu.text.SyntaxException     // Catch:{ all -> 0x00d0 }
            r0.<init>(r4)     // Catch:{ all -> 0x00d0 }
            throw r0     // Catch:{ all -> 0x00d0 }
        L_0x00d0:
            r0 = move-exception
            goto L_0x00d9
        L_0x00d2:
            r0 = move-exception
            r8 = r19
            r15 = r23
            r16 = 1
        L_0x00d9:
            if (r9 != 0) goto L_0x00e4
            if (r2 == 0) goto L_0x00e0
            r10.consumer = r11
        L_0x00e0:
            gnu.expr.Language.restoreCurrent(r5)
            return r0
        L_0x00e4:
            printError(r0, r4, r3)     // Catch:{ all -> 0x00eb }
        L_0x00e7:
            r1 = r18
            goto L_0x003f
        L_0x00eb:
            r0 = move-exception
            r1 = r0
            if (r2 == 0) goto L_0x00f1
            r10.consumer = r11
        L_0x00f1:
            gnu.expr.Language.restoreCurrent(r5)
            goto L_0x00f6
        L_0x00f5:
            throw r1
        L_0x00f6:
            goto L_0x00f5
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.Shell.run(gnu.expr.Language, gnu.mapping.Environment, gnu.mapping.InPort, gnu.lists.Consumer, gnu.mapping.OutPort, java.net.URL, gnu.text.SourceMessages):java.lang.Throwable");
    }

    public static void printError(Throwable ex, SourceMessages messages, OutPort perr) {
        if (ex instanceof WrongArguments) {
            WrongArguments e = (WrongArguments) ex;
            messages.printAll((PrintWriter) perr, 20);
            if (e.usage != null) {
                perr.println("usage: " + e.usage);
            }
            e.printStackTrace(perr);
        } else if (ex instanceof ClassCastException) {
            messages.printAll((PrintWriter) perr, 20);
            perr.println("Invalid parameter, was: " + ex.getMessage());
            ex.printStackTrace(perr);
        } else {
            if (ex instanceof SyntaxException) {
                SyntaxException syntaxException = (SyntaxException) ex;
                SyntaxException se = syntaxException;
                if (syntaxException.getMessages() == messages) {
                    se.printAll(perr, 20);
                    se.clear();
                    return;
                }
            }
            messages.printAll((PrintWriter) perr, 20);
            ex.printStackTrace(perr);
        }
    }

    public static final CompiledModule checkCompiledZip(InputStream fs, Path path, Environment env, Language language) throws IOException {
        try {
            fs.mark(5);
            boolean isZip = fs.read() == 80 && fs.read() == 75 && fs.read() == 3 && fs.read() == 4;
            fs.reset();
            if (!isZip) {
                return null;
            }
            fs.close();
            Environment orig_env = Environment.getCurrent();
            String name = path.toString();
            if (env != orig_env) {
                try {
                    Environment.setCurrent(env);
                } catch (IOException ex) {
                    throw new WrappedException("load: " + name + " - " + ex.toString(), ex);
                } catch (Throwable th) {
                    if (env != orig_env) {
                        Environment.setCurrent(orig_env);
                    }
                    throw th;
                }
            }
            if (path instanceof FilePath) {
                File zfile = ((FilePath) path).toFile();
                if (!zfile.exists()) {
                    throw new RuntimeException("load: " + name + " - not found");
                } else if (zfile.canRead()) {
                    CompiledModule make = CompiledModule.make(new ZipLoader(name).loadAllClasses(), language);
                    if (env != orig_env) {
                        Environment.setCurrent(orig_env);
                    }
                    return make;
                } else {
                    throw new RuntimeException("load: " + name + " - not readable");
                }
            } else {
                throw new RuntimeException("load: " + name + " - not a file path");
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean runFileOrClass(String str, boolean z, int i) {
        InputStream inputStream;
        Path path;
        Language defaultLanguage = Language.getDefaultLanguage();
        try {
            if (str.equals("-")) {
                path = Path.valueOf("/dev/stdin");
                inputStream = System.in;
            } else {
                path = Path.valueOf(str);
                inputStream = path.openInputStream();
            }
            return runFile(inputStream, path, Environment.getCurrent(), z, i);
        } catch (Throwable th) {
            try {
                CompiledModule.make(Class.forName(str), defaultLanguage).evalModule(Environment.getCurrent(), OutPort.outDefault());
                return true;
            } catch (Throwable th2) {
                th2.printStackTrace();
                return false;
            }
        }
    }

    public static final boolean runFile(InputStream fs, Path path, Environment env, boolean lineByLine, int skipLines) throws Throwable {
        InputStream fs2;
        InputStream fs3 = fs;
        Path path2 = path;
        Environment environment = env;
        if (!(fs3 instanceof BufferedInputStream)) {
            fs2 = new BufferedInputStream(fs3);
        } else {
            fs2 = fs3;
        }
        Language language = Language.getDefaultLanguage();
        Path savePath = (Path) currentLoadPath.get();
        try {
            currentLoadPath.set(path2);
            CompiledModule cmodule = checkCompiledZip(fs2, path2, environment, language);
            if (cmodule == null) {
                InPort src = InPort.openFile(fs2, path2);
                int skipLines2 = skipLines;
                while (true) {
                    int skipLines3 = skipLines2 - 1;
                    if (skipLines3 < 0) {
                        break;
                    }
                    try {
                        src.skipRestOfLine();
                        skipLines2 = skipLines3;
                    } catch (Throwable th) {
                        th = th;
                        currentLoadPath.set(savePath);
                        throw th;
                    }
                }
                SourceMessages messages = new SourceMessages();
                URL url = path.toURL();
                if (lineByLine) {
                    URL url2 = url;
                    Throwable ex = run(language, env, src, ModuleBody.getMainPrintValues() ? getOutputConsumer(OutPort.outDefault()) : new VoidConsumer(), (OutPort) null, url, messages);
                    if (ex != null) {
                        throw ex;
                    }
                } else {
                    cmodule = compileSource(src, environment, url, language, messages);
                    messages.printAll((PrintWriter) OutPort.errDefault(), 20);
                    if (cmodule == null) {
                        src.close();
                        currentLoadPath.set(savePath);
                        return false;
                    }
                }
                src.close();
            } else {
                int i = skipLines;
            }
            if (cmodule != null) {
                cmodule.evalModule(environment, OutPort.outDefault());
            }
            currentLoadPath.set(savePath);
            return true;
        } catch (Throwable th2) {
            th = th2;
            int i2 = skipLines;
            currentLoadPath.set(savePath);
            throw th;
        }
    }

    static CompiledModule compileSource(InPort port, Environment env, URL url, Language language, SourceMessages messages) throws SyntaxException, IOException {
        Compilation comp = language.parse(port, messages, 1, ModuleManager.getInstance().findWithSourcePath(port.getName()));
        CallContext.getInstance().values = Values.noArgs;
        Object inst = ModuleExp.evalModule1(env, comp, url, (OutPort) null);
        if (inst == null || messages.seenErrors()) {
            return null;
        }
        return new CompiledModule(comp.getModule(), inst, language);
    }
}
