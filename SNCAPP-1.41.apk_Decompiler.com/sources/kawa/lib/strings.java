package kawa.lib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Strings;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Char;

/* compiled from: strings.scm */
public class strings extends ModuleBody {
    public static final strings $instance;
    public static final ModuleMethod $make$string$;
    static final Char Lit0 = Char.make(32);
    static final SimpleSymbol Lit1;
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
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;
    public static final ModuleMethod list$Mn$Grstring;
    public static final ModuleMethod make$Mnstring;
    public static final ModuleMethod string$Eq$Qu;
    public static final ModuleMethod string$Gr$Eq$Qu;
    public static final ModuleMethod string$Gr$Qu;
    public static final ModuleMethod string$Ls$Eq$Qu;
    public static final ModuleMethod string$Ls$Qu;
    public static final ModuleMethod string$Mn$Grlist;
    public static final ModuleMethod string$Mnappend;
    public static final ModuleMethod string$Mnappend$Slshared;
    public static final ModuleMethod string$Mncapitalize;
    public static final ModuleMethod string$Mncapitalize$Ex;
    public static final ModuleMethod string$Mncopy;
    public static final ModuleMethod string$Mndowncase$Ex;
    public static final ModuleMethod string$Mnfill$Ex;
    public static final ModuleMethod string$Mnlength;
    public static final ModuleMethod string$Mnref;
    public static final ModuleMethod string$Mnset$Ex;
    public static final ModuleMethod string$Mnupcase$Ex;
    public static final ModuleMethod string$Qu;
    public static final ModuleMethod substring;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("string-append/shared").readResolve();
        Lit22 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("string-append").readResolve();
        Lit21 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("string-capitalize").readResolve();
        Lit20 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("string-capitalize!").readResolve();
        Lit19 = simpleSymbol4;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("string-downcase!").readResolve();
        Lit18 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("string-upcase!").readResolve();
        Lit17 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("string-fill!").readResolve();
        Lit16 = simpleSymbol7;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("string-copy").readResolve();
        Lit15 = simpleSymbol8;
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("list->string").readResolve();
        Lit14 = simpleSymbol9;
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol("string->list").readResolve();
        Lit13 = simpleSymbol10;
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("substring").readResolve();
        Lit12 = simpleSymbol11;
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("string>=?").readResolve();
        Lit11 = simpleSymbol12;
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol("string<=?").readResolve();
        Lit10 = simpleSymbol13;
        SimpleSymbol simpleSymbol14 = (SimpleSymbol) new SimpleSymbol("string>?").readResolve();
        Lit9 = simpleSymbol14;
        SimpleSymbol simpleSymbol15 = (SimpleSymbol) new SimpleSymbol("string<?").readResolve();
        Lit8 = simpleSymbol15;
        SimpleSymbol simpleSymbol16 = simpleSymbol;
        SimpleSymbol simpleSymbol17 = (SimpleSymbol) new SimpleSymbol("string=?").readResolve();
        Lit7 = simpleSymbol17;
        SimpleSymbol simpleSymbol18 = simpleSymbol2;
        SimpleSymbol simpleSymbol19 = (SimpleSymbol) new SimpleSymbol("string-set!").readResolve();
        Lit6 = simpleSymbol19;
        SimpleSymbol simpleSymbol20 = simpleSymbol3;
        SimpleSymbol simpleSymbol21 = (SimpleSymbol) new SimpleSymbol("string-ref").readResolve();
        Lit5 = simpleSymbol21;
        SimpleSymbol simpleSymbol22 = simpleSymbol4;
        SimpleSymbol simpleSymbol23 = (SimpleSymbol) new SimpleSymbol("string-length").readResolve();
        Lit4 = simpleSymbol23;
        SimpleSymbol simpleSymbol24 = simpleSymbol5;
        SimpleSymbol simpleSymbol25 = (SimpleSymbol) new SimpleSymbol("$make$string$").readResolve();
        Lit3 = simpleSymbol25;
        SimpleSymbol simpleSymbol26 = simpleSymbol6;
        SimpleSymbol simpleSymbol27 = (SimpleSymbol) new SimpleSymbol("make-string").readResolve();
        Lit2 = simpleSymbol27;
        SimpleSymbol simpleSymbol28 = simpleSymbol7;
        SimpleSymbol simpleSymbol29 = (SimpleSymbol) new SimpleSymbol("string?").readResolve();
        Lit1 = simpleSymbol29;
        strings strings = new strings();
        $instance = strings;
        string$Qu = new ModuleMethod(strings, 1, simpleSymbol29, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnstring = new ModuleMethod(strings, 2, simpleSymbol27, 8193);
        $make$string$ = new ModuleMethod(strings, 4, simpleSymbol25, -4096);
        string$Mnlength = new ModuleMethod(strings, 5, simpleSymbol23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnref = new ModuleMethod(strings, 6, simpleSymbol21, 8194);
        string$Mnset$Ex = new ModuleMethod(strings, 7, simpleSymbol19, 12291);
        string$Eq$Qu = new ModuleMethod(strings, 8, simpleSymbol17, 8194);
        string$Ls$Qu = new ModuleMethod(strings, 9, simpleSymbol15, 8194);
        string$Gr$Qu = new ModuleMethod(strings, 10, simpleSymbol14, 8194);
        string$Ls$Eq$Qu = new ModuleMethod(strings, 11, simpleSymbol13, 8194);
        string$Gr$Eq$Qu = new ModuleMethod(strings, 12, simpleSymbol12, 8194);
        substring = new ModuleMethod(strings, 13, simpleSymbol11, 12291);
        string$Mn$Grlist = new ModuleMethod(strings, 14, simpleSymbol10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grstring = new ModuleMethod(strings, 15, simpleSymbol9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mncopy = new ModuleMethod(strings, 16, simpleSymbol8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnfill$Ex = new ModuleMethod(strings, 17, simpleSymbol28, 8194);
        string$Mnupcase$Ex = new ModuleMethod(strings, 18, simpleSymbol26, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mndowncase$Ex = new ModuleMethod(strings, 19, simpleSymbol24, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mncapitalize$Ex = new ModuleMethod(strings, 20, simpleSymbol22, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mncapitalize = new ModuleMethod(strings, 21, simpleSymbol20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnappend = new ModuleMethod(strings, 22, simpleSymbol18, -4096);
        string$Mnappend$Slshared = new ModuleMethod(strings, 23, simpleSymbol16, -4096);
        strings.run();
    }

    public strings() {
        ModuleInfo.register(this);
    }

    public static CharSequence makeString(int i) {
        return makeString(i, Lit0);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static boolean isString(Object x) {
        return x instanceof CharSequence;
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
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 14:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 15:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 16:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 18:
                if (!(obj instanceof CharSeq)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                if (!(obj instanceof CharSeq)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 20:
                if (!(obj instanceof CharSeq)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 21:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static CharSequence makeString(int n, Object ch) {
        try {
            return new FString(n, ((Char) ch).charValue());
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.lists.FString.<init>(int,char)", 2, ch);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 6:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 8:
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
            case 11:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 12:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 17:
                if (!(obj instanceof CharSeq)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
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

    public static CharSequence $make$string$(Object... args) {
        int n = args.length;
        FString str = new FString(n);
        for (int i = 0; i < n; i++) {
            str.setCharAt(i, args[i].charValue());
        }
        return str;
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 22:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 23:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static int stringLength(CharSequence str) {
        return str.length();
    }

    public static char stringRef(CharSequence string, int k) {
        return string.charAt(k);
    }

    public static void stringSet$Ex(CharSeq string, int k, char c) {
        string.setCharAt(k, c);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 7:
                if (!(obj instanceof CharSeq)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (!(obj3 instanceof Char)) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 13:
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

    public static boolean isString$Eq(Object x, Object y) {
        return x.toString().equals(y.toString());
    }

    public static boolean isString$Ls(Object x, Object y) {
        return x.toString().compareTo(y.toString()) < 0;
    }

    public static boolean isString$Gr(Object x, Object y) {
        return x.toString().compareTo(y.toString()) > 0;
    }

    public static boolean isString$Ls$Eq(Object x, Object y) {
        return x.toString().compareTo(y.toString()) <= 0;
    }

    public static boolean isString$Gr$Eq(Object x, Object y) {
        return x.toString().compareTo(y.toString()) >= 0;
    }

    public static CharSequence substring(CharSequence str, int start, int end) {
        return new FString(str, start, end - start);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 7:
                try {
                    try {
                        try {
                            stringSet$Ex((CharSeq) obj, ((Number) obj2).intValue(), ((Char) obj3).charValue());
                            return Values.empty;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, obj);
                }
            case 13:
                try {
                    try {
                        try {
                            return substring((CharSequence) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "substring", 3, obj3);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "substring", 2, obj2);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "substring", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static LList string$To$List(CharSequence str) {
        LList result = LList.Empty;
        int i = stringLength(str);
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            result = new Pair(Char.make(stringRef(str, i)), result);
        }
    }

    public static CharSequence list$To$String(LList list) {
        int len = lists.length(list);
        FString fString = new FString(len);
        int i = 0;
        while (i < len) {
            try {
                Pair pair = (Pair) list;
                try {
                    CharSeq charSeq = fString;
                    Object car = pair.getCar();
                    try {
                        stringSet$Ex(charSeq, i, ((Char) car).charValue());
                        Object cdr = pair.getCdr();
                        try {
                            list = (LList) cdr;
                            i++;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "list", -2, cdr);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, car);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 0, (Object) fString);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "pair", -2, (Object) list);
            }
        }
        return fString;
    }

    public static FString stringCopy(CharSequence str) {
        return new FString(str);
    }

    public static void stringFill$Ex(CharSeq str, char ch) {
        str.fill(ch);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 2:
                try {
                    return makeString(((Number) obj).intValue(), obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-string", 1, obj);
                }
            case 6:
                try {
                    try {
                        return Char.make(stringRef((CharSequence) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-ref", 1, obj);
                }
            case 8:
                return isString$Eq(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 9:
                return isString$Ls(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 10:
                return isString$Gr(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 11:
                return isString$Ls$Eq(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 12:
                return isString$Gr$Eq(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 17:
                try {
                    try {
                        stringFill$Ex((CharSeq) obj, ((Char) obj2).charValue());
                        return Values.empty;
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-fill!", 2, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-fill!", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static CharSequence stringUpcase$Ex(CharSeq str) {
        Strings.makeUpperCase(str);
        return str;
    }

    public static CharSequence stringDowncase$Ex(CharSeq str) {
        Strings.makeLowerCase(str);
        return str;
    }

    public static CharSequence stringCapitalize$Ex(CharSeq str) {
        Strings.makeCapitalize(str);
        return str;
    }

    public static CharSequence stringCapitalize(CharSequence str) {
        FString copy = stringCopy(str);
        Strings.makeCapitalize(copy);
        return copy;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return isString(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 2:
                try {
                    return makeString(((Number) obj).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-string", 1, obj);
                }
            case 5:
                try {
                    return Integer.valueOf(stringLength((CharSequence) obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-length", 1, obj);
                }
            case 14:
                try {
                    return string$To$List((CharSequence) obj);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string->list", 1, obj);
                }
            case 15:
                try {
                    return list$To$String((LList) obj);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "list->string", 1, obj);
                }
            case 16:
                try {
                    return stringCopy((CharSequence) obj);
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-copy", 1, obj);
                }
            case 18:
                try {
                    return stringUpcase$Ex((CharSeq) obj);
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-upcase!", 1, obj);
                }
            case 19:
                try {
                    return stringDowncase$Ex((CharSeq) obj);
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "string-downcase!", 1, obj);
                }
            case 20:
                try {
                    return stringCapitalize$Ex((CharSeq) obj);
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "string-capitalize!", 1, obj);
                }
            case 21:
                try {
                    return stringCapitalize((CharSequence) obj);
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "string-capitalize", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static FString stringAppend(Object... args) {
        FString str = new FString();
        str.addAllStrings(args, 0);
        return str;
    }

    public static CharSequence stringAppend$SlShared(Object... args) {
        FString fstr;
        if (args.length == 0) {
            return new FString();
        }
        FString fString = args[0];
        if (fString instanceof FString) {
            try {
                fstr = fString;
            } catch (ClassCastException e) {
                throw new WrongType(e, "fstr", -2, (Object) fString);
            }
        } else {
            try {
                fstr = stringCopy(fString);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-copy", 0, (Object) fString);
            }
        }
        fstr.addAllStrings(args, 1);
        return fstr;
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 4:
                return $make$string$(objArr);
            case 22:
                return stringAppend(objArr);
            case 23:
                return stringAppend$SlShared(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
