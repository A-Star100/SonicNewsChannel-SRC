package gnu.commonlisp.lisp;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.commonlisp.lang.CommonLisp;
import gnu.commonlisp.lang.Lisp2;
import gnu.commonlisp.lang.Symbols;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.NumberCompare;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.lists.SimpleVector;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.PropertyLocation;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;

/* compiled from: PrimOps.scm */
public class PrimOps extends ModuleBody {
    public static final PrimOps $instance;
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("t").readResolve());
    static final IntNum Lit1 = IntNum.make(0);
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final SimpleSymbol Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final SimpleSymbol Lit17;
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final SimpleSymbol Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit30;
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;
    public static final ModuleMethod apply;
    public static final ModuleMethod aref;
    public static final ModuleMethod arrayp;
    public static final ModuleMethod aset;
    public static final ModuleMethod boundp;
    public static final ModuleMethod car;
    public static final ModuleMethod cdr;
    public static final ModuleMethod char$Mnto$Mnstring;
    public static final ModuleMethod fillarray;
    public static final ModuleMethod fset;
    public static final ModuleMethod functionp;
    public static final ModuleMethod get;
    public static final ModuleMethod length;
    public static final ModuleMethod make$Mnstring;
    public static final ModuleMethod plist$Mnget;
    public static final ModuleMethod plist$Mnmember;
    public static final ModuleMethod plist$Mnput;
    public static final ModuleMethod plist$Mnremprop;
    public static final ModuleMethod put;
    public static final ModuleMethod set;
    public static final ModuleMethod setcar;
    public static final ModuleMethod setcdr;
    public static final ModuleMethod setplist;
    public static final ModuleMethod stringp;
    public static final ModuleMethod substring;
    public static final ModuleMethod symbol$Mnfunction;
    public static final ModuleMethod symbol$Mnname;
    public static final ModuleMethod symbol$Mnplist;
    public static final ModuleMethod symbol$Mnvalue;
    public static final ModuleMethod symbolp;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("functionp").readResolve();
        Lit31 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("char-to-string").readResolve();
        Lit30 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("substring").readResolve();
        Lit29 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("make-string").readResolve();
        Lit28 = simpleSymbol4;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("stringp").readResolve();
        Lit27 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("fillarray").readResolve();
        Lit26 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("aset").readResolve();
        Lit25 = simpleSymbol7;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("aref").readResolve();
        Lit24 = simpleSymbol8;
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("arrayp").readResolve();
        Lit23 = simpleSymbol9;
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_LENGTH).readResolve();
        Lit22 = simpleSymbol10;
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("apply").readResolve();
        Lit21 = simpleSymbol11;
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("fset").readResolve();
        Lit20 = simpleSymbol12;
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol("symbol-function").readResolve();
        Lit19 = simpleSymbol13;
        SimpleSymbol simpleSymbol14 = (SimpleSymbol) new SimpleSymbol("set").readResolve();
        Lit18 = simpleSymbol14;
        SimpleSymbol simpleSymbol15 = (SimpleSymbol) new SimpleSymbol("symbol-value").readResolve();
        Lit17 = simpleSymbol15;
        SimpleSymbol simpleSymbol16 = simpleSymbol;
        SimpleSymbol simpleSymbol17 = (SimpleSymbol) new SimpleSymbol("put").readResolve();
        Lit16 = simpleSymbol17;
        SimpleSymbol simpleSymbol18 = simpleSymbol2;
        SimpleSymbol simpleSymbol19 = (SimpleSymbol) new SimpleSymbol("get").readResolve();
        Lit15 = simpleSymbol19;
        SimpleSymbol simpleSymbol20 = simpleSymbol3;
        SimpleSymbol simpleSymbol21 = (SimpleSymbol) new SimpleSymbol("plist-member").readResolve();
        Lit14 = simpleSymbol21;
        SimpleSymbol simpleSymbol22 = simpleSymbol4;
        SimpleSymbol simpleSymbol23 = (SimpleSymbol) new SimpleSymbol("plist-remprop").readResolve();
        Lit13 = simpleSymbol23;
        SimpleSymbol simpleSymbol24 = simpleSymbol5;
        SimpleSymbol simpleSymbol25 = (SimpleSymbol) new SimpleSymbol("plist-put").readResolve();
        Lit12 = simpleSymbol25;
        SimpleSymbol simpleSymbol26 = simpleSymbol6;
        SimpleSymbol simpleSymbol27 = (SimpleSymbol) new SimpleSymbol("plist-get").readResolve();
        Lit11 = simpleSymbol27;
        SimpleSymbol simpleSymbol28 = simpleSymbol7;
        SimpleSymbol simpleSymbol29 = (SimpleSymbol) new SimpleSymbol("setplist").readResolve();
        Lit10 = simpleSymbol29;
        SimpleSymbol simpleSymbol30 = simpleSymbol8;
        SimpleSymbol simpleSymbol31 = (SimpleSymbol) new SimpleSymbol("symbol-plist").readResolve();
        Lit9 = simpleSymbol31;
        SimpleSymbol simpleSymbol32 = simpleSymbol9;
        SimpleSymbol simpleSymbol33 = (SimpleSymbol) new SimpleSymbol("symbol-name").readResolve();
        Lit8 = simpleSymbol33;
        SimpleSymbol simpleSymbol34 = simpleSymbol10;
        SimpleSymbol simpleSymbol35 = (SimpleSymbol) new SimpleSymbol("symbolp").readResolve();
        Lit7 = simpleSymbol35;
        SimpleSymbol simpleSymbol36 = simpleSymbol11;
        SimpleSymbol simpleSymbol37 = (SimpleSymbol) new SimpleSymbol("boundp").readResolve();
        Lit6 = simpleSymbol37;
        SimpleSymbol simpleSymbol38 = simpleSymbol12;
        SimpleSymbol simpleSymbol39 = (SimpleSymbol) new SimpleSymbol("setcdr").readResolve();
        Lit5 = simpleSymbol39;
        SimpleSymbol simpleSymbol40 = simpleSymbol13;
        SimpleSymbol simpleSymbol41 = (SimpleSymbol) new SimpleSymbol("setcar").readResolve();
        Lit4 = simpleSymbol41;
        SimpleSymbol simpleSymbol42 = simpleSymbol14;
        SimpleSymbol simpleSymbol43 = (SimpleSymbol) new SimpleSymbol("cdr").readResolve();
        Lit3 = simpleSymbol43;
        SimpleSymbol simpleSymbol44 = simpleSymbol15;
        SimpleSymbol simpleSymbol45 = (SimpleSymbol) new SimpleSymbol("car").readResolve();
        Lit2 = simpleSymbol45;
        SimpleSymbol simpleSymbol46 = simpleSymbol17;
        PrimOps primOps = new PrimOps();
        $instance = primOps;
        car = new ModuleMethod(primOps, 1, simpleSymbol45, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        cdr = new ModuleMethod(primOps, 2, simpleSymbol43, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setcar = new ModuleMethod(primOps, 3, simpleSymbol41, 8194);
        setcdr = new ModuleMethod(primOps, 4, simpleSymbol39, 8194);
        boundp = new ModuleMethod(primOps, 5, simpleSymbol37, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        symbolp = new ModuleMethod(primOps, 6, simpleSymbol35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        symbol$Mnname = new ModuleMethod(primOps, 7, simpleSymbol33, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        symbol$Mnplist = new ModuleMethod(primOps, 8, simpleSymbol31, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        setplist = new ModuleMethod(primOps, 9, simpleSymbol29, 8194);
        plist$Mnget = new ModuleMethod(primOps, 10, simpleSymbol27, 12290);
        plist$Mnput = new ModuleMethod(primOps, 12, simpleSymbol25, 12291);
        plist$Mnremprop = new ModuleMethod(primOps, 13, simpleSymbol23, 8194);
        plist$Mnmember = new ModuleMethod(primOps, 14, simpleSymbol21, 8194);
        get = new ModuleMethod(primOps, 15, simpleSymbol19, 12290);
        put = new ModuleMethod(primOps, 17, simpleSymbol46, 12291);
        symbol$Mnvalue = new ModuleMethod(primOps, 18, simpleSymbol44, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        set = new ModuleMethod(primOps, 19, simpleSymbol42, 8194);
        symbol$Mnfunction = new ModuleMethod(primOps, 20, simpleSymbol40, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fset = new ModuleMethod(primOps, 21, simpleSymbol38, 8194);
        apply = new ModuleMethod(primOps, 22, simpleSymbol36, -4095);
        length = new ModuleMethod(primOps, 23, simpleSymbol34, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        arrayp = new ModuleMethod(primOps, 24, simpleSymbol32, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        aref = new ModuleMethod(primOps, 25, simpleSymbol30, 8194);
        aset = new ModuleMethod(primOps, 26, simpleSymbol28, 12291);
        fillarray = new ModuleMethod(primOps, 27, simpleSymbol26, 8194);
        stringp = new ModuleMethod(primOps, 28, simpleSymbol24, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnstring = new ModuleMethod(primOps, 29, simpleSymbol22, 8194);
        substring = new ModuleMethod(primOps, 30, simpleSymbol20, 12290);
        char$Mnto$Mnstring = new ModuleMethod(primOps, 32, simpleSymbol18, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        functionp = new ModuleMethod(primOps, 33, simpleSymbol16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        primOps.run();
    }

    public PrimOps() {
        ModuleInfo.register(this);
    }

    public static Object get(Symbol symbol, Object obj) {
        return get(symbol, obj, LList.Empty);
    }

    public static Object plistGet(Object obj, Object obj2) {
        return plistGet(obj, obj2, Boolean.FALSE);
    }

    public static FString substring(CharSequence charSequence, Object obj) {
        return substring(charSequence, obj, LList.Empty);
    }

    public static Object car(Object x) {
        return x == LList.Empty ? x : ((Pair) x).getCar();
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
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
            case 8:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 18:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 32:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 33:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object cdr(Object x) {
        return x == LList.Empty ? x : ((Pair) x).getCdr();
    }

    public static void setcar(Pair p, Object x) {
        lists.setCar$Ex(p, x);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 3:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 4:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 13:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 14:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 15:
                if (!(obj instanceof Symbol)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 21:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 25:
                if (!(obj instanceof SimpleVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 27:
                if (!(obj instanceof SimpleVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 30:
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

    public static void setcdr(Pair p, Object x) {
        lists.setCdr$Ex(p, x);
    }

    public static boolean boundp(Object symbol) {
        return Symbols.isBound(symbol);
    }

    public static boolean symbolp(Object x) {
        return Symbols.isSymbol(x);
    }

    public static Object symbolName(Object symbol) {
        return Symbols.getPrintName(symbol);
    }

    public static Object symbolPlist(Object symbol) {
        return PropertyLocation.getPropertyList(symbol);
    }

    public static Object setplist(Object symbol, Object plist) {
        PropertyLocation.setPropertyList(symbol, plist);
        return plist;
    }

    public static Object plistGet(Object plist, Object prop, Object obj) {
        return PropertyLocation.plistGet(plist, prop, obj);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 10:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 12:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 15:
                if (!(obj instanceof Symbol)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 17:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 26:
                if (!(obj instanceof SimpleVector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 30:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object plistPut(Object plist, Object prop, Object value) {
        return PropertyLocation.plistPut(plist, prop, value);
    }

    public static Object plistRemprop(Object plist, Object prop) {
        return PropertyLocation.plistRemove(plist, prop);
    }

    public static Object plistMember(Object plist, Object prop) {
        return PropertyLocation.plistGet(plist, prop, Values.empty) == Values.empty ? LList.Empty : Lit0;
    }

    public static Object get(Symbol symbol, Object property, Object obj) {
        return PropertyLocation.getProperty(symbol, property, obj);
    }

    public static void put(Object symbol, Object property, Object value) {
        PropertyLocation.putProperty(symbol, property, value);
    }

    public static Object symbolValue(Object sym) {
        return Environment.getCurrent().get(Symbols.getSymbol(sym));
    }

    public static void set(Object symbol, Object value) {
        Environment.getCurrent().put(Symbols.getSymbol(symbol), value);
    }

    public static Object symbolFunction(Object symbol) {
        return Symbols.getFunctionBinding(symbol);
    }

    public static void fset(Object symbol, Object object) {
        Symbols.setFunctionBinding(Environment.getCurrent(), symbol, object);
    }

    public static Object apply(Object func, Object... args) {
        return (misc.isSymbol(func) ? (Procedure) symbolFunction(func) : (Procedure) func).applyN(Apply.getArguments(args, 0, apply));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 22) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        int length2 = objArr.length - 1;
        Object[] objArr2 = new Object[length2];
        while (true) {
            length2--;
            if (length2 < 0) {
                return apply(obj, objArr2);
            }
            objArr2[length2] = objArr[length2 + 1];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 22) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }

    public static int length(Sequence x) {
        return x.size();
    }

    public static boolean arrayp(Object x) {
        return x instanceof SimpleVector;
    }

    public static Object aref(SimpleVector array, int k) {
        return array.get(k);
    }

    public static Object aset(SimpleVector array, int k, Object obj) {
        array.set(k, obj);
        return obj;
    }

    public static Object fillarray(SimpleVector array, Object obj) {
        array.fill(obj);
        return obj;
    }

    public static boolean stringp(Object x) {
        return x instanceof CharSequence;
    }

    public static FString makeString(int count, Object ch) {
        return new FString(count, CommonLisp.asChar(ch));
    }

    public static FString substring(CharSequence str, Object from, Object to) {
        if (to == LList.Empty) {
            to = Integer.valueOf(strings.stringLength(str));
        }
        NumberCompare numberCompare = Scheme.numLss;
        IntNum intNum = Lit1;
        if (numberCompare.apply2(to, intNum) != Boolean.FALSE) {
            to = AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength(str)), to);
        }
        if (Scheme.numLss.apply2(from, intNum) != Boolean.FALSE) {
            from = AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength(str)), from);
        }
        return new FString(str, ((Number) from).intValue(), ((Number) AddOp.$Mn.apply2(to, from)).intValue());
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 3:
                try {
                    setcar((Pair) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "setcar", 1, obj);
                }
            case 4:
                try {
                    setcdr((Pair) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "setcdr", 1, obj);
                }
            case 9:
                return setplist(obj, obj2);
            case 10:
                return plistGet(obj, obj2);
            case 13:
                return plistRemprop(obj, obj2);
            case 14:
                return plistMember(obj, obj2);
            case 15:
                try {
                    return get((Symbol) obj, obj2);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "get", 1, obj);
                }
            case 19:
                set(obj, obj2);
                return Values.empty;
            case 21:
                fset(obj, obj2);
                return Values.empty;
            case 25:
                try {
                    try {
                        return aref((SimpleVector) obj, ((Number) obj2).intValue());
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "aref", 2, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "aref", 1, obj);
                }
            case 27:
                try {
                    return fillarray((SimpleVector) obj, obj2);
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "fillarray", 1, obj);
                }
            case 29:
                try {
                    return makeString(((Number) obj).intValue(), obj2);
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "make-string", 1, obj);
                }
            case 30:
                try {
                    return substring((CharSequence) obj, obj2);
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "substring", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 10:
                return plistGet(obj, obj2, obj3);
            case 12:
                return plistPut(obj, obj2, obj3);
            case 15:
                try {
                    return get((Symbol) obj, obj2, obj3);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "get", 1, obj);
                }
            case 17:
                put(obj, obj2, obj3);
                return Values.empty;
            case 26:
                try {
                    try {
                        return aset((SimpleVector) obj, ((Number) obj2).intValue(), obj3);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "aset", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "aset", 1, obj);
                }
            case 30:
                try {
                    return substring((CharSequence) obj, obj2, obj3);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "substring", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static FString charToString(Object ch) {
        return new FString(1, CommonLisp.asChar(ch));
    }

    public static boolean functionp(Object x) {
        return x instanceof Procedure;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return car(obj);
            case 2:
                return cdr(obj);
            case 5:
                return boundp(obj) ? Lisp2.TRUE : LList.Empty;
            case 6:
                return symbolp(obj) ? Lisp2.TRUE : LList.Empty;
            case 7:
                return symbolName(obj);
            case 8:
                return symbolPlist(obj);
            case 18:
                return symbolValue(obj);
            case 20:
                return symbolFunction(obj);
            case 23:
                try {
                    return Integer.valueOf(length((Sequence) obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, obj);
                }
            case 24:
                return arrayp(obj) ? Lisp2.TRUE : LList.Empty;
            case 28:
                return stringp(obj) ? Lisp2.TRUE : LList.Empty;
            case 32:
                return charToString(obj);
            case 33:
                return functionp(obj) ? Lisp2.TRUE : LList.Empty;
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
