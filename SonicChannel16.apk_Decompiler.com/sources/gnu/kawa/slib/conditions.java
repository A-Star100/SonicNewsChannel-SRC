package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.IsEq;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.slib.condition;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: conditions.scm */
public class conditions extends ModuleBody {
    public static Object $Amcondition;
    public static Object $Amerror;
    public static Object $Ammessage;
    public static Object $Amserious;
    static final Class $Lscondition$Mntype$Gr = condition.Mntype.class;
    public static final Class $Prvt$$Lscondition$Gr = condition.class;
    public static final ModuleMethod $Prvt$type$Mnfield$Mnalist$Mn$Grcondition;
    public static final conditions $instance;
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("&condition").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("&message").readResolve());
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final SimpleSymbol Lit13;
    static final SyntaxRules Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final SimpleSymbol Lit17;
    static final SimpleSymbol Lit18;
    static final SyntaxRules Lit19;
    static final PairWithPosition Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("&serious").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("&error").readResolve());
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SyntaxRules Lit9;
    public static final Macro condition;
    public static final ModuleMethod condition$Mnhas$Mntype$Qu;
    public static final ModuleMethod condition$Mnref;
    static final Macro condition$Mntype$Mnfield$Mnalist;
    public static final ModuleMethod condition$Mntype$Qu;
    public static final ModuleMethod condition$Qu;
    public static final Macro define$Mncondition$Mntype;
    public static final ModuleMethod extract$Mncondition;
    public static final ModuleMethod make$Mncompound$Mncondition;
    public static final ModuleMethod make$Mncondition;
    public static final ModuleMethod make$Mncondition$Mntype;

    public conditions() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        condition.Mntype mntype = new condition.Mntype(Lit0, Boolean.FALSE, LList.Empty, LList.Empty);
        $Amcondition = mntype;
        try {
            condition.Mntype mntype2 = mntype;
            $Ammessage = makeConditionType(Lit1, mntype, Lit2);
            SimpleSymbol simpleSymbol = Lit3;
            Object obj = $Amcondition;
            try {
                condition.Mntype makeConditionType = makeConditionType(simpleSymbol, (condition.Mntype) obj, LList.Empty);
                $Amserious = makeConditionType;
                try {
                    condition.Mntype mntype3 = makeConditionType;
                    $Amerror = makeConditionType(Lit4, makeConditionType, LList.Empty);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-condition-type", 1, (Object) makeConditionType);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "make-condition-type", 1, obj);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "make-condition-type", 1, (Object) mntype);
        }
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("thing").readResolve();
        Lit22 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
        Lit21 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("type-field-alist->condition").readResolve();
        Lit20 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("condition").readResolve();
        Lit18 = simpleSymbol4;
        SyntaxRules syntaxRules = new SyntaxRules(new Object[]{simpleSymbol4}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018]\f\u0007-\f\u000f\f\u0017\b\b\u0010\b\u0000\u0018\b", new Object[0], 3), "\u0003\u0005\u0005", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0005\u0011\u0018\u0014\t\u0003\b\u0011\u0018\f\b\r\u0011\u0018\u0014)\u0011\u0018\u001c\b\u000b\b\u0013", new Object[]{simpleSymbol3, (SimpleSymbol) new SimpleSymbol("list").readResolve(), (SimpleSymbol) new SimpleSymbol("cons").readResolve(), simpleSymbol2}, 2)}, 3);
        Lit19 = syntaxRules;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("extract-condition").readResolve();
        Lit17 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("make-compound-condition").readResolve();
        Lit16 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("condition-ref").readResolve();
        Lit15 = simpleSymbol7;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("condition-type-field-alist").readResolve();
        Lit13 = simpleSymbol8;
        SimpleSymbol simpleSymbol9 = simpleSymbol3;
        SyntaxRules syntaxRules2 = syntaxRules;
        SimpleSymbol simpleSymbol10 = simpleSymbol8;
        SyntaxRules syntaxRules3 = new SyntaxRules(new Object[]{simpleSymbol8}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("*").readResolve(), Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol(".type-field-alist").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm", 581639), (SimpleSymbol) new SimpleSymbol("as").readResolve(), (SimpleSymbol) new SimpleSymbol("<condition>").readResolve()}, 0)}, 1);
        Lit14 = syntaxRules3;
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("condition-has-type?").readResolve();
        Lit12 = simpleSymbol11;
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("make-condition").readResolve();
        Lit11 = simpleSymbol12;
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol("condition?").readResolve();
        Lit10 = simpleSymbol13;
        SimpleSymbol simpleSymbol14 = (SimpleSymbol) new SimpleSymbol("define-condition-type").readResolve();
        Lit8 = simpleSymbol14;
        Object[] objArr = {simpleSymbol14};
        SyntaxRules syntaxRules4 = syntaxRules3;
        SimpleSymbol simpleSymbol15 = simpleSymbol12;
        SimpleSymbol simpleSymbol16 = simpleSymbol14;
        SyntaxPattern syntaxPattern = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017-\f\u001f\f'\b\u0018\u0010\b", new Object[0], 5);
        SimpleSymbol simpleSymbol17 = (SimpleSymbol) new SimpleSymbol("make-condition-type").readResolve();
        Lit7 = simpleSymbol17;
        SyntaxRules syntaxRules5 = new SyntaxRules(objArr, new SyntaxRule[]{new SyntaxRule(syntaxPattern, "\u0001\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004É\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014)\u0011\u0018\u001c\b\u0003\t\u000b\b\u0011\u0018\u001c\b\b\u001d\u001bÁ\u0011\u0018\f!\t\u0013\u0018$\b\u0011\u0018,\u0011\u00184\b\u0011\u0018<\u0011\u0018D\b\u0003\b%\u0011\u0018\f!\t#\u0018L\b\u0011\u0018TA\u0011\u0018\\\u0011\u0018d\b\u0003\b\u0011\u0018\u001c\b\u001b", new Object[]{(SimpleSymbol) new SimpleSymbol("begin").readResolve(), (SimpleSymbol) new SimpleSymbol("define").readResolve(), simpleSymbol17, simpleSymbol2, PairWithPosition.make(simpleSymbol, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm", 327708), (SimpleSymbol) new SimpleSymbol("and").readResolve(), PairWithPosition.make(simpleSymbol13, PairWithPosition.make(simpleSymbol, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm", 331803), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm", 331791), simpleSymbol11, simpleSymbol, PairWithPosition.make(simpleSymbol4, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm", 339996), simpleSymbol7, simpleSymbol5, simpleSymbol4}, 1)}, 5);
        Lit9 = syntaxRules5;
        SimpleSymbol simpleSymbol18 = (SimpleSymbol) new SimpleSymbol("condition-type?").readResolve();
        Lit6 = simpleSymbol18;
        SimpleSymbol simpleSymbol19 = (SimpleSymbol) new SimpleSymbol("message").readResolve();
        Lit5 = simpleSymbol19;
        Lit2 = PairWithPosition.make(simpleSymbol19, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm", 925699);
        conditions conditions = new conditions();
        $instance = conditions;
        condition$Mntype$Qu = new ModuleMethod(conditions, 2, simpleSymbol18, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mncondition$Mntype = new ModuleMethod(conditions, 3, simpleSymbol17, 12291);
        define$Mncondition$Mntype = Macro.make(simpleSymbol16, syntaxRules5, conditions);
        condition$Qu = new ModuleMethod(conditions, 4, simpleSymbol13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mncondition = new ModuleMethod(conditions, 5, simpleSymbol15, -4095);
        condition$Mnhas$Mntype$Qu = new ModuleMethod(conditions, 6, simpleSymbol11, 8194);
        condition$Mntype$Mnfield$Mnalist = Macro.make(simpleSymbol10, syntaxRules4, conditions);
        condition$Mnref = new ModuleMethod(conditions, 7, simpleSymbol7, 8194);
        make$Mncompound$Mncondition = new ModuleMethod(conditions, 8, simpleSymbol6, -4095);
        extract$Mncondition = new ModuleMethod(conditions, 9, simpleSymbol5, 8194);
        condition = Macro.make(simpleSymbol4, syntaxRules2, conditions);
        $Prvt$type$Mnfield$Mnalist$Mn$Grcondition = new ModuleMethod(conditions, 10, simpleSymbol9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        conditions.run();
    }

    public static boolean isConditionType(Object obj) {
        return obj instanceof condition.Mntype;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static condition.Mntype makeConditionType(Symbol name, condition.Mntype supertype, Object fields) {
        if (!lists.isNull(srfi1.lsetIntersection$V(Scheme.isEq, supertype.all$Mnfields, new Object[]{fields}))) {
            misc.error$V("duplicate field name", new Object[0]);
        }
        return new condition.Mntype(name, supertype, fields, append.append$V(new Object[]{supertype.all$Mnfields, fields}));
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector != 3) {
            return super.apply3(moduleMethod, obj, obj2, obj3);
        }
        try {
            try {
                return makeConditionType((Symbol) obj, (condition.Mntype) obj2, obj3);
            } catch (ClassCastException e) {
                throw new WrongType(e, "make-condition-type", 2, obj2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "make-condition-type", 1, obj);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 3) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        if (!(obj instanceof Symbol)) {
            return -786431;
        }
        callContext.value1 = obj;
        if (!(obj2 instanceof condition.Mntype)) {
            return -786430;
        }
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: gnu.kawa.slib.condition$Mntype} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean isConditionSubtype(gnu.kawa.slib.condition.Mntype r2, gnu.kawa.slib.condition.Mntype r3) {
        /*
            r0 = r2
        L_0x0002:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 != r1) goto L_0x0008
            r1 = 0
            goto L_0x000b
        L_0x0008:
            if (r0 != r3) goto L_0x000c
            r1 = 1
        L_0x000b:
            return r1
        L_0x000c:
            java.lang.Object r1 = r0.supertype
            r0 = r1
            gnu.kawa.slib.condition$Mntype r0 = (gnu.kawa.slib.condition.Mntype) r0
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.conditions.isConditionSubtype(gnu.kawa.slib.condition$Mntype, gnu.kawa.slib.condition$Mntype):boolean");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: gnu.kawa.slib.condition$Mntype} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object conditionTypeFieldSupertype(gnu.kawa.slib.condition.Mntype r3, java.lang.Object r4) {
        /*
            r0 = r3
        L_0x0002:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 != r1) goto L_0x000a
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r0 = r1
            goto L_0x0015
        L_0x000a:
            java.lang.Object r1 = r0.fields
            java.lang.Object r1 = kawa.lib.lists.memq(r4, r1)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x0016
        L_0x0015:
            return r0
        L_0x0016:
            java.lang.Object r1 = r0.supertype
            r0 = r1
            gnu.kawa.slib.condition$Mntype r0 = (gnu.kawa.slib.condition.Mntype) r0
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.conditions.conditionTypeFieldSupertype(gnu.kawa.slib.condition$Mntype, java.lang.Object):java.lang.Object");
    }

    public static boolean isCondition(Object obj) {
        return obj instanceof condition;
    }

    public static condition makeCondition$V(condition.Mntype type, Object[] argsArray) {
        Object alist = lambda1label(LList.makeList(argsArray, 0));
        IsEq isEq = Scheme.isEq;
        Object[] objArr = new Object[2];
        objArr[0] = type.all$Mnfields;
        Object result = LList.Empty;
        Object arg0 = alist;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                arg0 = arg02.getCdr();
                result = Pair.make(lists.car.apply1(arg02.getCar()), result);
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        objArr[1] = LList.reverseInPlace(result);
        if (srfi1.lset$Eq$V(isEq, objArr) == Boolean.FALSE) {
            misc.error$V("condition fields don't match condition type", new Object[0]);
        }
        return new condition(LList.list1(lists.cons(type, alist)));
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 5:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 8:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object lambda1label(Object plist) {
        if (lists.isNull(plist)) {
            return LList.Empty;
        }
        return lists.cons(lists.cons(lists.car.apply1(plist), lists.cadr.apply1(plist)), lambda1label(lists.cddr.apply1(plist)));
    }

    public static boolean isConditionHasType(Object condition2, condition.Mntype type) {
        Object types = conditionTypes(condition2);
        while (true) {
            Object apply1 = lists.car.apply1(types);
            try {
                boolean x = isConditionSubtype((condition.Mntype) apply1, type);
                if (x) {
                    return x;
                }
                types = lists.cdr.apply1(types);
            } catch (ClassCastException e) {
                throw new WrongType(e, "condition-subtype?", 0, apply1);
            }
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 6:
                callContext.value1 = obj;
                if (!(obj2 instanceof condition.Mntype)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 7:
                if (!(obj instanceof condition)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                if (!(obj instanceof condition)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof condition.Mntype)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object conditionRef(condition condition2, Object field) {
        return typeFieldAlistRef(condition2.type$Mnfield$Mnalist, field);
    }

    static Object typeFieldAlistRef(Object type$Mnfield$Mnalist, Object field) {
        Object type$Mnfield$Mnalist2 = type$Mnfield$Mnalist;
        while (!lists.isNull(type$Mnfield$Mnalist2)) {
            Object temp = lists.assq(field, lists.cdr.apply1(lists.car.apply1(type$Mnfield$Mnalist2)));
            if (temp != Boolean.FALSE) {
                return lists.cdr.apply1(temp);
            }
            type$Mnfield$Mnalist2 = lists.cdr.apply1(type$Mnfield$Mnalist2);
        }
        return misc.error$V("type-field-alist-ref: field not found", new Object[]{type$Mnfield$Mnalist2, field});
    }

    public static condition makeCompoundCondition$V(Object condition$Mn1, Object[] argsArray) {
        LList conditions = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        append append = append.append;
        Object arg0 = lists.cons(condition$Mn1, conditions);
        Object result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object cdr = arg02.getCdr();
                result = Pair.make(Scheme.applyToArgs.apply2(condition$Mntype$Mnfield$Mnalist, arg02.getCar()), result);
                arg0 = cdr;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return new condition(apply.apply2(append, LList.reverseInPlace(result)));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 5:
                condition.Mntype mntype = objArr[0];
                try {
                    condition.Mntype mntype2 = mntype;
                    int length = objArr.length - 1;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return makeCondition$V(mntype2, objArr2);
                        }
                        objArr2[length] = objArr[length + 1];
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-condition", 1, (Object) mntype);
                }
            case 8:
                Object obj = objArr[0];
                int length2 = objArr.length - 1;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return makeCompoundCondition$V(obj, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 1];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static condition extractCondition(condition condition2, condition.Mntype type) {
        frame frame2 = new frame();
        frame2.type = type;
        Object entry = srfi1.find(frame2.lambda$Fn1, condition2.type$Mnfield$Mnalist);
        if (entry == Boolean.FALSE) {
            misc.error$V("extract-condition: invalid condition type", new Object[]{condition2, frame2.type});
        }
        condition.Mntype mntype = frame2.type;
        Object arg0 = frame2.type.all$Mnfields;
        Object result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object cdr = arg02.getCdr();
                result = Pair.make(lists.assq(arg02.getCar(), lists.cdr.apply1(entry)), result);
                arg0 = cdr;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return new condition(LList.list1(lists.cons(mntype, LList.reverseInPlace(result))));
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 6:
                try {
                    return isConditionHasType(obj, (condition.Mntype) obj2) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "condition-has-type?", 2, obj2);
                }
            case 7:
                try {
                    return conditionRef((condition) obj, obj2);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "condition-ref", 1, obj);
                }
            case 9:
                try {
                    try {
                        return extractCondition((condition) obj, (condition.Mntype) obj2);
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "extract-condition", 2, obj2);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "extract-condition", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* compiled from: conditions.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        condition.Mntype type;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/conditions.scm:166");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 1) {
                return lambda2(obj) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public boolean lambda2(Object entry) {
            Object apply1 = lists.car.apply1(entry);
            try {
                return conditions.isConditionSubtype((condition.Mntype) apply1, this.type);
            } catch (ClassCastException e) {
                throw new WrongType(e, "condition-subtype?", 0, apply1);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    public static condition typeFieldAlist$To$Condition(Object type$Mnfield$Mnalist) {
        Object result = LList.Empty;
        Object arg0 = type$Mnfield$Mnalist;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object cdr = arg02.getCdr();
                Object entry = arg02.getCar();
                Object apply1 = lists.car.apply1(entry);
                Object arg03 = ((condition.Mntype) lists.car.apply1(entry)).all$Mnfields;
                Object result2 = LList.Empty;
                while (arg03 != LList.Empty) {
                    try {
                        Pair arg04 = (Pair) arg03;
                        Object cdr2 = arg04.getCdr();
                        Object field = arg04.getCar();
                        Object x = lists.assq(field, lists.cdr.apply1(entry));
                        result2 = Pair.make(x != Boolean.FALSE ? x : lists.cons(field, typeFieldAlistRef(type$Mnfield$Mnalist, field)), result2);
                        arg03 = cdr2;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "arg0", -2, arg03);
                    }
                }
                result = Pair.make(lists.cons(apply1, LList.reverseInPlace(result2)), result);
                arg0 = cdr;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        return new condition(LList.reverseInPlace(result));
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return isConditionType(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 4:
                return isCondition(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 10:
                return typeFieldAlist$To$Condition(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    static Object conditionTypes(Object condition2) {
        Object arg0 = ((condition) condition2).type$Mnfield$Mnalist;
        Object result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                arg0 = arg02.getCdr();
                result = Pair.make(lists.car.apply1(arg02.getCar()), result);
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(result);
    }

    static Object checkConditionTypeFieldAlist(Object the$Mntype$Mnfield$Mnalist) {
        boolean x;
        Object type$Mnfield$Mnalist = the$Mntype$Mnfield$Mnalist;
        char c = 0;
        while (!lists.isNull(type$Mnfield$Mnalist)) {
            Object entry = lists.car.apply1(type$Mnfield$Mnalist);
            Object apply1 = lists.car.apply1(entry);
            try {
                condition.Mntype type = (condition.Mntype) apply1;
                Object field$Mnalist = lists.cdr.apply1(entry);
                Object result = LList.Empty;
                Object arg0 = field$Mnalist;
                while (arg0 != LList.Empty) {
                    try {
                        Pair arg02 = (Pair) arg0;
                        arg0 = arg02.getCdr();
                        result = Pair.make(lists.car.apply1(arg02.getCar()), result);
                        c = 0;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "arg0", -2, arg0);
                    }
                }
                LList fields = LList.reverseInPlace(result);
                Object all$Mnfields = type.all$Mnfields;
                IsEq isEq = Scheme.isEq;
                Object[] objArr = new Object[1];
                objArr[c] = fields;
                Object arg03 = srfi1.lsetDifference$V(isEq, all$Mnfields, objArr);
                while (arg03 != LList.Empty) {
                    try {
                        Pair arg04 = (Pair) arg03;
                        Object missing$Mnfield = arg04.getCar();
                        Object supertype = conditionTypeFieldSupertype(type, missing$Mnfield);
                        Object alist = the$Mntype$Mnfield$Mnalist;
                        while (true) {
                            Object apply12 = lists.car.apply1(lists.car.apply1(alist));
                            try {
                                try {
                                    x = isConditionSubtype((condition.Mntype) apply12, (condition.Mntype) supertype);
                                    if (x) {
                                        break;
                                    }
                                    alist = lists.cdr.apply1(alist);
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "condition-subtype?", 1, supertype);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "condition-subtype?", 0, apply12);
                            }
                        }
                        if (!x) {
                            misc.error$V("missing field in condition construction", new Object[]{type, missing$Mnfield});
                        }
                        arg03 = arg04.getCdr();
                        c = 0;
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "arg0", -2, arg03);
                    }
                }
                type$Mnfield$Mnalist = lists.cdr.apply1(type$Mnfield$Mnalist);
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "type", -2, apply1);
            }
        }
        return Values.empty;
    }

    static boolean isMessageCondition(Object thing) {
        boolean x = isCondition(thing);
        if (!x) {
            return x;
        }
        Object obj = $Ammessage;
        try {
            return isConditionHasType(thing, (condition.Mntype) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "condition-has-type?", 1, obj);
        }
    }

    static Object conditionMessage(Object condition2) {
        try {
            condition condition3 = (condition) condition2;
            Object obj = $Ammessage;
            try {
                return conditionRef(extractCondition(condition3, (condition.Mntype) obj), Lit5);
            } catch (ClassCastException e) {
                throw new WrongType(e, "extract-condition", 1, obj);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "extract-condition", 0, condition2);
        }
    }

    static boolean isSeriousCondition(Object thing) {
        boolean x = isCondition(thing);
        if (!x) {
            return x;
        }
        Object obj = $Amserious;
        try {
            return isConditionHasType(thing, (condition.Mntype) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "condition-has-type?", 1, obj);
        }
    }

    static boolean isError(Object thing) {
        boolean x = isCondition(thing);
        if (!x) {
            return x;
        }
        Object obj = $Amerror;
        try {
            return isConditionHasType(thing, (condition.Mntype) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "condition-has-type?", 1, obj);
        }
    }
}
