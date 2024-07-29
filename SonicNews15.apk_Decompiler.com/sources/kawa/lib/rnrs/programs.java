package kawa.lib.rnrs;

import androidx.core.app.NotificationCompat;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.numbers;

/* compiled from: programs.scm */
public class programs extends ModuleBody {
    public static final programs $instance;
    static final IntNum Lit0 = IntNum.make(0);
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit2;
    public static final ModuleMethod command$Mnline;
    public static final ModuleMethod exit;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("exit").readResolve();
        Lit2 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("command-line").readResolve();
        Lit1 = simpleSymbol2;
        programs programs = new programs();
        $instance = programs;
        command$Mnline = new ModuleMethod(programs, 1, simpleSymbol2, 0);
        exit = new ModuleMethod(programs, 2, simpleSymbol, 4096);
        programs.run();
    }

    public programs() {
        ModuleInfo.register(this);
    }

    public static void exit() {
        exit(Lit0);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static LList commandLine() {
        return lists.cons("kawa", LList.makeList(ApplicationMainSupport.commandLineArgArray, 0));
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 2:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public static void exit(Object code) {
        OutPort.runCleanups();
        int status = 0;
        if (numbers.isInteger(code)) {
            try {
                status = ((Number) code).intValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, NotificationCompat.CATEGORY_STATUS, -2, code);
            }
        } else if (code == Boolean.FALSE) {
            status = -1;
        }
        System.exit(status);
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 1:
                return commandLine();
            case 2:
                exit();
                return Values.empty;
            default:
                return super.apply0(moduleMethod);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        if (moduleMethod.selector != 2) {
            return super.apply1(moduleMethod, obj);
        }
        exit(obj);
        return Values.empty;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 2) {
            return super.match1(moduleMethod, obj, callContext);
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.pc = 1;
        return 0;
    }
}
