package kawa.lib;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.IsEqual;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.InputStream;
import java.util.StringTokenizer;
import kawa.lang.CompileFile;
import kawa.lang.NamedException;
import kawa.standard.Scheme;

/* compiled from: system.scm */
public class system extends ModuleBody {
    public static final system $instance;
    static final IntNum Lit0 = IntNum.make(0);
    static final IntNum Lit1 = IntNum.make(1);
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;

    /* renamed from: catch  reason: not valid java name */
    public static final ModuleMethod f6catch;
    public static Procedure command$Mnparse;
    public static final ModuleMethod compile$Mnfile;
    public static final ModuleMethod convert$Mnlist$Mnto$Mnstring$Mnarray;
    public static final ModuleMethod convert$Mnvector$Mnto$Mnstring$Mnarray;
    public static final ModuleMethod make$Mnprocess;
    public static final ModuleMethod open$Mninput$Mnpipe;
    public static final ModuleMethod process$Mncommand$Mnline$Mnassignments;
    public static final ModuleMethod system;
    public static final ModuleMethod tokenize$Mnstring$Mnto$Mnstring$Mnarray;
    public static final ModuleMethod tokenize$Mnstring$Mnusing$Mnshell;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("process-command-line-assignments").readResolve();
        Lit11 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("catch").readResolve();
        Lit10 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("compile-file").readResolve();
        Lit9 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("tokenize-string-using-shell").readResolve();
        Lit8 = simpleSymbol4;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("tokenize-string-to-string-array").readResolve();
        Lit7 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("convert-list-to-string-array").readResolve();
        Lit6 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("convert-vector-to-string-array").readResolve();
        Lit5 = simpleSymbol7;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("system").readResolve();
        Lit4 = simpleSymbol8;
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("open-input-pipe").readResolve();
        Lit3 = simpleSymbol9;
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol("make-process").readResolve();
        Lit2 = simpleSymbol10;
        system system2 = new system();
        $instance = system2;
        make$Mnprocess = new ModuleMethod(system2, 1, simpleSymbol10, 8194);
        open$Mninput$Mnpipe = new ModuleMethod(system2, 2, simpleSymbol9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        system = new ModuleMethod(system2, 3, simpleSymbol8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        convert$Mnvector$Mnto$Mnstring$Mnarray = new ModuleMethod(system2, 4, simpleSymbol7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        convert$Mnlist$Mnto$Mnstring$Mnarray = new ModuleMethod(system2, 5, simpleSymbol6, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tokenize$Mnstring$Mnto$Mnstring$Mnarray = new ModuleMethod(system2, 6, simpleSymbol5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tokenize$Mnstring$Mnusing$Mnshell = new ModuleMethod(system2, 7, simpleSymbol4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        compile$Mnfile = new ModuleMethod(system2, 8, simpleSymbol3, 8194);
        f6catch = new ModuleMethod(system2, 9, simpleSymbol2, 12291);
        process$Mncommand$Mnline$Mnassignments = new ModuleMethod(system2, 10, simpleSymbol, 0);
        system2.run();
    }

    public system() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        command$Mnparse = IsEqual.apply(System.getProperty("file.separator"), "/") ? tokenize$Mnstring$Mnusing$Mnshell : tokenize$Mnstring$Mnto$Mnstring$Mnarray;
    }

    public static Process makeProcess(Object args, Object env) {
        Object arargs;
        if (vectors.isVector(args)) {
            arargs = convertVectorToStringArray(args);
        } else if (lists.isList(args)) {
            arargs = convertListToStringArray(args);
        } else if (strings.isString(args)) {
            arargs = command$Mnparse.apply1(args);
        } else if (args instanceof String[]) {
            arargs = args;
        } else {
            arargs = misc.error$V("invalid arguments to make-process", new Object[0]);
        }
        try {
            try {
                return Runtime.getRuntime().exec((String[]) arargs, (String[]) env);
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runtime.exec(java.lang.String[],java.lang.String[])", 3, env);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runtime.exec(java.lang.String[],java.lang.String[])", 2, arargs);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 8:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static InputStream openInputPipe(Object command) {
        return makeProcess(command, (Object) null).getInputStream();
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static int system(Object command) {
        return makeProcess(command, (Object) null).waitFor();
    }

    public static Object convertVectorToStringArray(Object vec) {
        try {
            int count = vectors.vectorLength((FVector) vec);
            String[] arr = new String[count];
            Object obj = vec;
            Object i = Lit0;
            while (Scheme.numEqu.apply2(i, Integer.valueOf(count)) == Boolean.FALSE) {
                int intValue = ((Number) i).intValue();
                try {
                    try {
                        Object vectorRef = vectors.vectorRef((FVector) vec, ((Number) i).intValue());
                        arr[intValue] = vectorRef == null ? null : vectorRef.toString();
                        i = AddOp.$Pl.apply2(i, Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "vector-ref", 2, i);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "vector-ref", 1, vec);
                }
            }
            return arr;
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "vector-length", 1, vec);
        }
    }

    public static Object convertListToStringArray(Object lst) {
        try {
            String[] arr = new String[lists.length((LList) lst)];
            Object obj = lst;
            int i = 0;
            Object p = lst;
            while (!lists.isNull(p)) {
                try {
                    Pair pp = (Pair) p;
                    Object car = pp.getCar();
                    arr[i] = car == null ? null : car.toString();
                    p = pp.getCdr();
                    i++;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "pp", -2, p);
                }
            }
            return arr;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, lst);
        }
    }

    public static Object tokenizeStringToStringArray(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        Object obj = LList.Empty;
        while (stringTokenizer.hasMoreTokens()) {
            obj = lists.cons(stringTokenizer.nextToken(), obj);
        }
        try {
            int length = lists.length((LList) obj);
            String[] strArr = new String[length];
            int i = length - 1;
            while (!lists.isNull(obj)) {
                try {
                    Pair pair = (Pair) obj;
                    Object car = pair.getCar();
                    strArr[i] = car == null ? null : car.toString();
                    obj = pair.getCdr();
                    i--;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "pp", -2, obj);
                }
            }
            return strArr;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, obj);
        }
    }

    public static String[] tokenizeStringUsingShell(Object string) {
        String[] arr = new String[3];
        String str = null;
        arr[0] = "/bin/sh";
        arr[1] = "-c";
        if (string != null) {
            str = string.toString();
        }
        arr[2] = str;
        return arr;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return openInputPipe(obj);
            case 3:
                return Integer.valueOf(system(obj));
            case 4:
                return convertVectorToStringArray(obj);
            case 5:
                return convertListToStringArray(obj);
            case 6:
                return tokenizeStringToStringArray(obj == null ? null : obj.toString());
            case 7:
                return tokenizeStringUsingShell(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static void compileFile(CharSequence source, String output) {
        SourceMessages messages = new SourceMessages();
        Compilation comp = CompileFile.read(source.toString(), messages);
        comp.explicit = true;
        if (!messages.seenErrors()) {
            comp.compileToArchive(comp.getModule(), output);
            if (messages.seenErrors()) {
                throw new SyntaxException(messages);
            }
            return;
        }
        throw new SyntaxException(messages);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 1:
                return makeProcess(obj, obj2);
            case 8:
                try {
                    compileFile((CharSequence) obj, obj2 == null ? null : obj2.toString());
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "compile-file", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* renamed from: catch  reason: not valid java name */
    public static Object m585catch(Object key, Procedure thunk, Procedure handler) {
        try {
            return thunk.apply0();
        } catch (NamedException e) {
            return e.applyHandler(key, handler);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector != 9) {
            return super.apply3(moduleMethod, obj, obj2, obj3);
        }
        try {
            try {
                return m585catch(obj, (Procedure) obj2, (Procedure) obj3);
            } catch (ClassCastException e) {
                throw new WrongType(e, "catch", 3, obj3);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "catch", 2, obj2);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 9) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        callContext.value1 = obj;
        if (!(obj2 instanceof Procedure)) {
            return -786430;
        }
        callContext.value2 = obj2;
        if (!(obj3 instanceof Procedure)) {
            return -786429;
        }
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    public static void processCommandLineAssignments() {
        ApplicationMainSupport.processSetProperties();
    }

    public Object apply0(ModuleMethod moduleMethod) {
        if (moduleMethod.selector != 10) {
            return super.apply0(moduleMethod);
        }
        processCommandLineAssignments();
        return Values.empty;
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        if (moduleMethod.selector != 10) {
            return super.match0(moduleMethod, callContext);
        }
        callContext.proc = moduleMethod;
        callContext.pc = 0;
        return 0;
    }
}
