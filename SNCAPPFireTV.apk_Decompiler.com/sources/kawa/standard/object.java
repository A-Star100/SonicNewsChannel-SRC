package kawa.standard;

import gnu.expr.ClassExp;
import gnu.expr.Declaration;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class object extends Syntax {
    public static final Keyword accessKeyword = Keyword.make("access");
    public static final Keyword allocationKeyword = Keyword.make("allocation");
    public static final Keyword classNameKeyword = Keyword.make("class-name");
    static final Symbol coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    static final Keyword initKeyword = Keyword.make("init");
    static final Keyword init_formKeyword = Keyword.make("init-form");
    static final Keyword init_keywordKeyword = Keyword.make("init-keyword");
    static final Keyword init_valueKeyword = Keyword.make("init-value");
    static final Keyword initformKeyword = Keyword.make("initform");
    public static final Keyword interfaceKeyword = Keyword.make("interface");
    public static final object objectSyntax;
    public static final Keyword throwsKeyword = Keyword.make("throws");
    static final Keyword typeKeyword = Keyword.make("type");
    Lambda lambda;

    static {
        object object = new object(SchemeCompilation.lambda);
        objectSyntax = object;
        object.setName("object");
    }

    public object(Lambda lambda2) {
        this.lambda = lambda2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r4, kawa.lang.Translator r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r4.getCdr()
            boolean r0 = r0 instanceof gnu.lists.Pair
            if (r0 != 0) goto L_0x000f
            java.lang.String r0 = "missing superclass specification in object"
            gnu.expr.Expression r0 = r5.syntaxError(r0)
            return r0
        L_0x000f:
            java.lang.Object r0 = r4.getCdr()
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            gnu.expr.ObjectExp r1 = new gnu.expr.ObjectExp
            r1.<init>()
            java.lang.Object r2 = r0.getCar()
            boolean r2 = r2 instanceof gnu.lists.FString
            if (r2 == 0) goto L_0x0038
            java.lang.Object r2 = r0.getCdr()
            boolean r2 = r2 instanceof gnu.lists.Pair
            if (r2 != 0) goto L_0x0031
            java.lang.String r2 = "missing superclass specification after object class name"
            gnu.expr.Expression r2 = r5.syntaxError(r2)
            return r2
        L_0x0031:
            java.lang.Object r2 = r0.getCdr()
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
        L_0x0038:
            java.lang.Object[] r2 = r3.scanClassDef(r0, r1, r5)
            if (r2 == 0) goto L_0x0041
            r3.rewriteClassDef(r2, r5)
        L_0x0041:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }

    public Object[] scanClassDef(Pair pair, ClassExp oexp, Translator tr) {
        char c;
        ClassExp classExp;
        long classAccessFlag;
        Object superlist;
        Object classNamePair;
        Object pair_car;
        Object obj;
        Object classNamePair2;
        Object obj2;
        Object savedPos1;
        Object components;
        long accessFlag;
        Declaration decl;
        Object args;
        Declaration decl2;
        LambdaExp method_list;
        LambdaExp last_method;
        Pair typePair;
        int allocationFlag;
        Object savedPos12;
        Object pair2;
        Object savedPos2;
        Object pair_car2;
        Declaration decl3;
        Object components2;
        Object savedPos13;
        LambdaExp last_method2;
        Object obj3;
        Declaration decl4;
        Object savedPos14;
        LambdaExp method_list2;
        Object savedPos22;
        Object pair_car3;
        Pair pair3;
        int allocationFlag2;
        Object components3;
        Object savedPos15;
        LambdaExp last_method3;
        Object obj4;
        Declaration decl5;
        Pair pair4;
        Object savedPos16;
        LambdaExp method_list3;
        Object savedPos23;
        Object pair_car4;
        int i;
        Pair pair5;
        int allocationFlag3;
        Pair typePair2;
        Object key;
        Object savedPos17;
        LambdaExp last_method4;
        Object obj5;
        Declaration decl6;
        Pair pair6;
        Object savedPos18;
        Object components4;
        LambdaExp method_list4;
        Object savedPos24;
        Object pair_car5;
        int allocationFlag4;
        ClassExp classExp2 = oexp;
        Translator translator = tr;
        tr.mustCompileHere();
        Object savedPos25 = pair.getCar();
        Object components5 = pair.getCdr();
        Vector inits = new Vector(20);
        Object classNamePair3 = null;
        LambdaExp method_list5 = null;
        LambdaExp last_method5 = null;
        Object obj6 = components5;
        Object classNamePair4 = pair;
        long classAccessFlag2 = 0;
        while (obj6 != LList.Empty) {
            while (obj6 instanceof SyntaxForm) {
                obj6 = ((SyntaxForm) obj6).getDatum();
            }
            if (!(obj6 instanceof Pair)) {
                translator.error('e', "object member not a list");
                return null;
            }
            Pair pair7 = (Pair) obj6;
            Object pair_car6 = pair7.getCar();
            while (pair_car6 instanceof SyntaxForm) {
                pair_car6 = ((SyntaxForm) pair_car6).getDatum();
            }
            Object obj7 = pair7.getCdr();
            Object savedPos19 = translator.pushPositionOf(pair7);
            if (pair_car6 instanceof Keyword) {
                obj = obj7;
                while (obj instanceof SyntaxForm) {
                    obj = ((SyntaxForm) obj).getDatum();
                }
                if (!(obj instanceof Pair)) {
                    pair_car = pair_car6;
                    Pair pair8 = pair7;
                    classAccessFlag = classAccessFlag2;
                    superlist = savedPos25;
                    classNamePair = classNamePair3;
                    classNamePair2 = savedPos19;
                } else if (pair_car6 == interfaceKeyword) {
                    if (((Pair) obj).getCar() == Boolean.FALSE) {
                        classExp2.setFlag(65536);
                    } else {
                        classExp2.setFlag(32768);
                    }
                    Object obj8 = ((Pair) obj).getCdr();
                    translator.popPositionOf(savedPos19);
                    Object pair_car7 = pair7;
                    obj6 = obj8;
                } else if (pair_car6 == classNameKeyword) {
                    if (classNamePair3 != null) {
                        translator.error('e', "duplicate class-name specifiers");
                    }
                    classNamePair3 = obj;
                    obj6 = ((Pair) obj).getCdr();
                    translator.popPositionOf(savedPos19);
                    Object pair_car8 = pair7;
                } else if (pair_car6 == accessKeyword) {
                    Object obj9 = pair_car6;
                    Pair pair9 = pair7;
                    Object superlist2 = savedPos25;
                    Object classNamePair5 = classNamePair3;
                    Object savedPos110 = savedPos19;
                    Object savedPos26 = translator.pushPositionOf(obj);
                    long j = classAccessFlag2;
                    classAccessFlag2 = addAccessFlags(((Pair) obj).getCar(), classAccessFlag2, Declaration.CLASS_ACCESS_FLAGS, "class", tr);
                    if (classExp2.nameDecl == null) {
                        translator.error('e', "access specifier for anonymous class");
                    }
                    translator.popPositionOf(savedPos26);
                    obj6 = ((Pair) obj).getCdr();
                    translator.popPositionOf(savedPos110);
                    Pair pair10 = pair9;
                    classNamePair3 = classNamePair5;
                    savedPos25 = superlist2;
                } else {
                    pair_car = pair_car6;
                    Pair pair11 = pair7;
                    classAccessFlag = classAccessFlag2;
                    superlist = savedPos25;
                    classNamePair = classNamePair3;
                    classNamePair2 = savedPos19;
                }
            } else {
                pair_car = pair_car6;
                Pair pair12 = pair7;
                classAccessFlag = classAccessFlag2;
                superlist = savedPos25;
                classNamePair = classNamePair3;
                classNamePair2 = savedPos19;
                obj = obj7;
            }
            Object pair_car9 = pair_car;
            if (!(pair_car9 instanceof Pair)) {
                translator.error('e', "object member not a list");
                return null;
            }
            Pair typePair3 = (Pair) pair_car9;
            Object pair_car10 = typePair3.getCar();
            while (pair_car10 instanceof SyntaxForm) {
                pair_car10 = ((SyntaxForm) pair_car10).getDatum();
            }
            if ((pair_car10 instanceof String) || (pair_car10 instanceof Symbol) || (pair_car10 instanceof Keyword)) {
                Object sname = pair_car10;
                if (sname instanceof Keyword) {
                    decl = null;
                    args = typePair3;
                    accessFlag = 0;
                } else {
                    decl = classExp2.addDeclaration(sname);
                    accessFlag = 0;
                    decl.setSimple(false);
                    decl.setFlag(1048576);
                    Translator.setLine(decl, (Object) typePair3);
                    args = typePair3.getCdr();
                }
                boolean seenInit = false;
                Pair initPair = null;
                Object obj10 = args;
                Pair typePair4 = null;
                Object args2 = obj10;
                int allocationFlag5 = 0;
                int nKeywords = 0;
                while (true) {
                    Object pair13 = typePair3;
                    if (args2 == LList.Empty) {
                        decl2 = decl;
                        Object obj11 = pair_car10;
                        components = components5;
                        savedPos1 = classNamePair2;
                        method_list = method_list5;
                        last_method = last_method5;
                        obj2 = obj;
                        typePair = typePair4;
                        allocationFlag = allocationFlag5;
                        savedPos12 = sname;
                        pair2 = pair13;
                        break;
                    }
                    while (args2 instanceof SyntaxForm) {
                        args2 = ((SyntaxForm) args2).getDatum();
                    }
                    Pair pair14 = args2;
                    Pair keyPair = pair14;
                    Pair typePair5 = typePair4;
                    Object key2 = pair14.getCar();
                    while (true) {
                        Object args3 = args2;
                        if (!(key2 instanceof SyntaxForm)) {
                            break;
                        }
                        key2 = ((SyntaxForm) key2).getDatum();
                        args2 = args3;
                    }
                    Object savedPos27 = translator.pushPositionOf(pair14);
                    args2 = pair14.getCdr();
                    Pair pair15 = pair14;
                    Symbol symbol = coloncolon;
                    if (key2 != symbol) {
                        decl3 = decl;
                        if (!(key2 instanceof Keyword)) {
                            components2 = components5;
                            savedPos13 = classNamePair2;
                            last_method2 = last_method5;
                            obj3 = obj;
                            typePair = typePair5;
                            decl4 = decl3;
                            allocationFlag = allocationFlag5;
                            savedPos14 = sname;
                            method_list2 = method_list5;
                            Object obj12 = key2;
                            Object obj13 = savedPos27;
                            savedPos22 = pair_car10;
                            pair_car3 = obj13;
                            if (args2 == LList.Empty || seenInit) {
                                if ((args2 instanceof Pair) && nKeywords == 0 && !seenInit && typePair == null) {
                                    Pair pair16 = (Pair) args2;
                                    Pair pair17 = pair16;
                                    if (pair16.getCdr() != LList.Empty) {
                                        pair2 = pair17;
                                        break;
                                    }
                                    args2 = pair17.getCdr();
                                    initPair = pair17;
                                    seenInit = true;
                                    allocationFlag5 = allocationFlag;
                                    Pair pair18 = pair17;
                                    typePair4 = keyPair;
                                    typePair3 = pair18;
                                    translator.popPositionOf(pair_car2);
                                    obj = obj2;
                                    decl = decl2;
                                    sname = savedPos12;
                                    method_list5 = method_list;
                                    pair_car10 = savedPos2;
                                    components5 = components;
                                    classNamePair2 = savedPos1;
                                    last_method5 = last_method;
                                    ClassExp classExp3 = oexp;
                                } else {
                                    pair2 = pair15;
                                }
                            } else {
                                initPair = keyPair;
                                seenInit = true;
                                typePair4 = typePair;
                                allocationFlag5 = allocationFlag;
                                typePair3 = pair15;
                                translator.popPositionOf(pair_car2);
                                obj = obj2;
                                decl = decl2;
                                sname = savedPos12;
                                method_list5 = method_list;
                                pair_car10 = savedPos2;
                                components5 = components;
                                classNamePair2 = savedPos1;
                                last_method5 = last_method;
                                ClassExp classExp32 = oexp;
                            }
                        }
                    } else {
                        decl3 = decl;
                    }
                    if (!(args2 instanceof Pair)) {
                        components2 = components5;
                        savedPos13 = classNamePair2;
                        last_method2 = last_method5;
                        obj3 = obj;
                        typePair = typePair5;
                        decl4 = decl3;
                        allocationFlag = allocationFlag5;
                        savedPos14 = sname;
                        method_list2 = method_list5;
                        Object obj14 = key2;
                        Object obj15 = savedPos27;
                        savedPos22 = pair_car10;
                        pair_car3 = obj15;
                        if (args2 == LList.Empty) {
                        }
                        if (args2 instanceof Pair) {
                            break;
                        }
                        break;
                    }
                    int nKeywords2 = nKeywords + 1;
                    Pair pair19 = (Pair) args2;
                    Object value = pair19.getCar();
                    Object args4 = pair19.getCdr();
                    if (key2 == symbol) {
                        components3 = components5;
                        savedPos15 = classNamePair2;
                        last_method3 = last_method5;
                        obj4 = obj;
                        Pair pair20 = typePair5;
                        decl5 = decl3;
                        allocationFlag2 = allocationFlag5;
                        pair4 = pair19;
                        savedPos16 = sname;
                        method_list3 = method_list5;
                        Object obj16 = key2;
                        Object obj17 = savedPos27;
                        savedPos23 = pair_car10;
                        pair_car4 = obj17;
                    } else if (key2 == typeKeyword) {
                        Object obj18 = value;
                        components3 = components5;
                        savedPos15 = classNamePair2;
                        last_method3 = last_method5;
                        obj4 = obj;
                        Pair pair21 = typePair5;
                        decl5 = decl3;
                        allocationFlag2 = allocationFlag5;
                        pair4 = pair19;
                        savedPos16 = sname;
                        method_list3 = method_list5;
                        Object obj19 = key2;
                        Object obj20 = savedPos27;
                        savedPos23 = pair_car10;
                        pair_car4 = obj20;
                    } else {
                        if (key2 == allocationKeyword) {
                            if (allocationFlag5 != 0) {
                                allocationFlag4 = allocationFlag5;
                                translator.error('e', "duplicate allocation: specification");
                            } else {
                                allocationFlag4 = allocationFlag5;
                            }
                            if (matches(value, "class", translator) || matches(value, "static", translator)) {
                                allocationFlag5 = 2048;
                                components = components5;
                                savedPos1 = classNamePair2;
                                last_method = last_method5;
                                obj2 = obj;
                                decl2 = decl3;
                                pair3 = pair19;
                                savedPos12 = sname;
                                LambdaExp lambdaExp = method_list5;
                                Object obj21 = key2;
                                typePair4 = typePair5;
                                method_list = lambdaExp;
                                Object obj22 = savedPos27;
                                savedPos2 = pair_car10;
                                pair_car2 = obj22;
                                args2 = args4;
                                typePair3 = pair3;
                                nKeywords = nKeywords2;
                                translator.popPositionOf(pair_car2);
                                obj = obj2;
                                decl = decl2;
                                sname = savedPos12;
                                method_list5 = method_list;
                                pair_car10 = savedPos2;
                                components5 = components;
                                classNamePair2 = savedPos1;
                                last_method5 = last_method;
                                ClassExp classExp322 = oexp;
                            } else if (matches(value, "instance", translator)) {
                                allocationFlag5 = 4096;
                                components = components5;
                                savedPos1 = classNamePair2;
                                last_method = last_method5;
                                obj2 = obj;
                                decl2 = decl3;
                                pair3 = pair19;
                                savedPos12 = sname;
                                LambdaExp lambdaExp2 = method_list5;
                                Object obj23 = key2;
                                typePair4 = typePair5;
                                method_list = lambdaExp2;
                                Object obj24 = savedPos27;
                                savedPos2 = pair_car10;
                                pair_car2 = obj24;
                                args2 = args4;
                                typePair3 = pair3;
                                nKeywords = nKeywords2;
                                translator.popPositionOf(pair_car2);
                                obj = obj2;
                                decl = decl2;
                                sname = savedPos12;
                                method_list5 = method_list;
                                pair_car10 = savedPos2;
                                components5 = components;
                                classNamePair2 = savedPos1;
                                last_method5 = last_method;
                                ClassExp classExp3222 = oexp;
                            } else {
                                translator.error('e', "unknown allocation kind '" + value + "'");
                                savedPos1 = classNamePair2;
                                last_method = last_method5;
                                obj2 = obj;
                                pair5 = typePair5;
                                decl2 = decl3;
                                i = allocationFlag4;
                                pair3 = pair19;
                                savedPos12 = sname;
                                components = components5;
                                method_list = method_list5;
                                Object obj25 = key2;
                                Object obj26 = savedPos27;
                                savedPos2 = pair_car10;
                                pair_car2 = obj26;
                            }
                        } else {
                            int allocationFlag6 = allocationFlag5;
                            Keyword keyword = initKeyword;
                            if (key2 == keyword || key2 == initformKeyword || key2 == init_formKeyword) {
                                Object obj27 = value;
                                savedPos17 = classNamePair2;
                                last_method4 = last_method5;
                                obj5 = obj;
                                typePair2 = typePair5;
                                decl6 = decl3;
                                allocationFlag3 = allocationFlag6;
                                pair6 = pair19;
                                savedPos18 = sname;
                                components4 = components5;
                                method_list4 = method_list5;
                                key = key2;
                                Object obj28 = savedPos27;
                                savedPos24 = pair_car10;
                                pair_car5 = obj28;
                            } else if (key2 == init_valueKeyword) {
                                Object obj29 = value;
                                savedPos17 = classNamePair2;
                                last_method4 = last_method5;
                                obj5 = obj;
                                typePair2 = typePair5;
                                decl6 = decl3;
                                allocationFlag3 = allocationFlag6;
                                pair6 = pair19;
                                savedPos18 = sname;
                                components4 = components5;
                                method_list4 = method_list5;
                                key = key2;
                                Object obj30 = savedPos27;
                                savedPos24 = pair_car10;
                                pair_car5 = obj30;
                            } else if (key2 == init_keywordKeyword) {
                                if (!(value instanceof Keyword)) {
                                    translator.error('e', "invalid 'init-keyword' - not a keyword");
                                    savedPos1 = classNamePair2;
                                    last_method = last_method5;
                                    obj2 = obj;
                                    pair5 = typePair5;
                                    decl2 = decl3;
                                    i = allocationFlag6;
                                    pair3 = pair19;
                                    savedPos12 = sname;
                                    components = components5;
                                    method_list = method_list5;
                                    Object obj31 = key2;
                                    Object obj32 = savedPos27;
                                    savedPos2 = pair_car10;
                                    pair_car2 = obj32;
                                } else if (((Keyword) value).getName() != sname.toString()) {
                                    translator.error('w', "init-keyword option ignored");
                                    savedPos1 = classNamePair2;
                                    last_method = last_method5;
                                    obj2 = obj;
                                    pair5 = typePair5;
                                    decl2 = decl3;
                                    i = allocationFlag6;
                                    pair3 = pair19;
                                    savedPos12 = sname;
                                    components = components5;
                                    method_list = method_list5;
                                    Object obj33 = key2;
                                    Object obj34 = savedPos27;
                                    savedPos2 = pair_car10;
                                    pair_car2 = obj34;
                                } else {
                                    savedPos1 = classNamePair2;
                                    last_method = last_method5;
                                    obj2 = obj;
                                    pair5 = typePair5;
                                    decl2 = decl3;
                                    i = allocationFlag6;
                                    pair3 = pair19;
                                    savedPos12 = sname;
                                    components = components5;
                                    method_list = method_list5;
                                    Object obj35 = key2;
                                    Object obj36 = savedPos27;
                                    savedPos2 = pair_car10;
                                    pair_car2 = obj36;
                                }
                            } else if (key2 == accessKeyword) {
                                Object savedPos28 = savedPos27;
                                Object obj37 = value;
                                savedPos2 = pair_car10;
                                pair_car2 = savedPos28;
                                last_method = last_method5;
                                obj2 = obj;
                                Pair typePair6 = typePair5;
                                int allocationFlag7 = allocationFlag6;
                                components = components5;
                                method_list = method_list5;
                                Object obj38 = key2;
                                decl2 = decl3;
                                pair3 = pair19;
                                savedPos1 = classNamePair2;
                                savedPos12 = sname;
                                accessFlag = addAccessFlags(value, accessFlag, Declaration.FIELD_ACCESS_FLAGS, "field", tr);
                                translator.popPositionOf(translator.pushPositionOf(pair19));
                                typePair4 = typePair6;
                                allocationFlag5 = allocationFlag7;
                                args2 = args4;
                                typePair3 = pair3;
                                nKeywords = nKeywords2;
                                translator.popPositionOf(pair_car2);
                                obj = obj2;
                                decl = decl2;
                                sname = savedPos12;
                                method_list5 = method_list;
                                pair_car10 = savedPos2;
                                components5 = components;
                                classNamePair2 = savedPos1;
                                last_method5 = last_method;
                                ClassExp classExp32222 = oexp;
                            } else {
                                savedPos1 = classNamePair2;
                                last_method = last_method5;
                                obj2 = obj;
                                pair5 = typePair5;
                                decl2 = decl3;
                                i = allocationFlag6;
                                pair3 = pair19;
                                savedPos12 = sname;
                                components = components5;
                                method_list = method_list5;
                                Object obj39 = savedPos27;
                                savedPos2 = pair_car10;
                                pair_car2 = obj39;
                                translator.error('w', "unknown slot keyword '" + key2 + "'");
                            }
                            if (seenInit) {
                                translator.error('e', "duplicate initialization");
                            }
                            seenInit = true;
                            if (key != keyword) {
                                initPair = pair3;
                                typePair4 = typePair2;
                                allocationFlag5 = allocationFlag3;
                            } else {
                                typePair4 = typePair2;
                                allocationFlag5 = allocationFlag3;
                            }
                            args2 = args4;
                            typePair3 = pair3;
                            nKeywords = nKeywords2;
                            translator.popPositionOf(pair_car2);
                            obj = obj2;
                            decl = decl2;
                            sname = savedPos12;
                            method_list5 = method_list;
                            pair_car10 = savedPos2;
                            components5 = components;
                            classNamePair2 = savedPos1;
                            last_method5 = last_method;
                            ClassExp classExp322222 = oexp;
                        }
                        typePair4 = pair5;
                        allocationFlag5 = i;
                        args2 = args4;
                        typePair3 = pair3;
                        nKeywords = nKeywords2;
                        translator.popPositionOf(pair_car2);
                        obj = obj2;
                        decl = decl2;
                        sname = savedPos12;
                        method_list5 = method_list;
                        pair_car10 = savedPos2;
                        components5 = components;
                        classNamePair2 = savedPos1;
                        last_method5 = last_method;
                        ClassExp classExp3222222 = oexp;
                    }
                    typePair4 = pair3;
                    allocationFlag5 = allocationFlag2;
                    args2 = args4;
                    typePair3 = pair3;
                    nKeywords = nKeywords2;
                    translator.popPositionOf(pair_car2);
                    obj = obj2;
                    decl = decl2;
                    sname = savedPos12;
                    method_list5 = method_list;
                    pair_car10 = savedPos2;
                    components5 = components;
                    classNamePair2 = savedPos1;
                    last_method5 = last_method;
                    ClassExp classExp32222222 = oexp;
                }
                args2 = null;
                if (args2 != LList.Empty) {
                    translator.error('e', "invalid argument list for slot '" + savedPos12 + '\'' + " args:" + (args2 == null ? "null" : args2.getClass().getName()));
                    return null;
                }
                if (seenInit) {
                    inits.addElement(decl2 != null ? decl2 : allocationFlag == 2048 ? Boolean.TRUE : Boolean.FALSE);
                    inits.addElement(initPair);
                }
                if (decl2 != null) {
                    if (typePair != null) {
                        decl2.setType(translator.exp2Type(typePair));
                    }
                    if (allocationFlag != 0) {
                        decl2.setFlag((long) allocationFlag);
                    }
                    long accessFlag2 = accessFlag;
                    if (accessFlag2 != 0) {
                        decl2.setFlag(accessFlag2);
                    }
                    decl2.setCanRead(true);
                    decl2.setCanWrite(true);
                } else if (!seenInit) {
                    translator.error('e', "missing field name");
                    return null;
                }
                Object args5 = pair2;
                method_list5 = method_list;
                last_method5 = last_method;
            } else if (pair_car10 instanceof Pair) {
                Pair mpair = (Pair) pair_car10;
                Object mname = mpair.getCar();
                if ((mname instanceof String) || (mname instanceof Symbol)) {
                    LambdaExp lexp = new LambdaExp();
                    Translator.setLine(classExp2.addMethod(lexp, mname), (Object) mpair);
                    if (last_method5 == null) {
                        method_list5 = lexp;
                    } else {
                        last_method5.nextSibling = lexp;
                    }
                    last_method5 = lexp;
                    Pair pair22 = typePair3;
                    Object obj40 = pair_car10;
                    components = components5;
                    savedPos1 = classNamePair2;
                    obj2 = obj;
                } else {
                    translator.error('e', "missing method name");
                    return null;
                }
            } else {
                translator.error('e', "invalid field/method definition");
                Pair pair23 = typePair3;
                Object obj41 = pair_car10;
                components = components5;
                savedPos1 = classNamePair2;
                obj2 = obj;
            }
            translator.popPositionOf(savedPos1);
            obj6 = obj2;
            classExp2 = oexp;
            classNamePair3 = classNamePair;
            savedPos25 = superlist;
            classAccessFlag2 = classAccessFlag;
            components5 = components;
        }
        Object superlist3 = savedPos25;
        Object components6 = components5;
        Object classNamePair6 = classNamePair3;
        LambdaExp method_list6 = method_list5;
        LambdaExp lambdaExp3 = last_method5;
        if (classAccessFlag2 != 0) {
            classExp = oexp;
            c = 1;
            classExp.nameDecl.setFlag(classAccessFlag2);
        } else {
            classExp = oexp;
            c = 1;
        }
        Object[] result = new Object[6];
        result[0] = classExp;
        result[c] = components6;
        result[2] = inits;
        result[3] = method_list6;
        result[4] = superlist3;
        result[5] = classNamePair6;
        return result;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v0, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v3, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v4, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v5, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v0, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v14, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v14, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v15, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v19, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v20, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v16, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v21, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v17, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v24, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v22, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v18, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v13, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v19, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v26, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v15, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v16, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v18, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v23, resolved type: gnu.lists.Pair} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v20, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v53, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v55, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v22, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v5, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kawa.lang.SyntaxForm} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v25, resolved type: gnu.expr.LambdaExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v28, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v29, resolved type: int} */
    /* JADX WARNING: type inference failed for: r27v4 */
    /* JADX WARNING: type inference failed for: r27v7 */
    /* JADX WARNING: type inference failed for: r27v8 */
    /* JADX WARNING: type inference failed for: r27v9 */
    /* JADX WARNING: type inference failed for: r27v11 */
    /* JADX WARNING: type inference failed for: r1v37, types: [gnu.expr.ScopeExp] */
    /* JADX WARNING: type inference failed for: r27v14 */
    /* JADX WARNING: type inference failed for: r0v37, types: [gnu.expr.ScopeExp] */
    /* JADX WARNING: type inference failed for: r27v19 */
    /* JADX WARNING: type inference failed for: r27v25 */
    /* JADX WARNING: type inference failed for: r14v23 */
    /* JADX WARNING: type inference failed for: r9v24 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0253 A[Catch:{ all -> 0x0261, all -> 0x0299 }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x02d4 A[Catch:{ all -> 0x041a }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x02d7 A[Catch:{ all -> 0x041a }] */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x02e1 A[Catch:{ all -> 0x041a }, LOOP:8: B:142:0x02e1->B:147:0x02ee, LOOP_START, PHI: r2 r19 
      PHI: (r2v17 'args' java.lang.Object) = (r2v13 'args' java.lang.Object), (r2v28 'args' java.lang.Object) binds: [B:141:0x02df, B:147:0x02ee] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r19v3 'memberSyntax' kawa.lang.SyntaxForm) = (r19v2 'memberSyntax' kawa.lang.SyntaxForm), (r19v4 'memberSyntax' kawa.lang.SyntaxForm) binds: [B:141:0x02df, B:147:0x02ee] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x037c A[Catch:{ all -> 0x039c }] */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x03c5  */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x03ff  */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x03bd A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:279:0x039a A[EDGE_INSN: B:279:0x039a->B:209:0x039a ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteClassDef(java.lang.Object[] r33, kawa.lang.Translator r34) {
        /*
            r32 = this;
            r7 = r34
            r0 = 0
            r1 = r33[r0]
            r8 = r1
            gnu.expr.ClassExp r8 = (gnu.expr.ClassExp) r8
            r9 = 1
            r10 = r33[r9]
            r1 = 2
            r1 = r33[r1]
            r11 = r1
            java.util.Vector r11 = (java.util.Vector) r11
            r1 = 3
            r1 = r33[r1]
            r12 = r1
            gnu.expr.LambdaExp r12 = (gnu.expr.LambdaExp) r12
            r1 = 4
            r1 = r33[r1]
            r2 = 5
            r13 = r33[r2]
            r8.firstChild = r12
            int r2 = kawa.lang.Translator.listLength(r1)
            r3 = 101(0x65, float:1.42E-43)
            if (r2 >= 0) goto L_0x002f
            java.lang.String r4 = "object superclass specification not a list"
            r7.error(r3, r4)
            r2 = 0
            r14 = r2
            goto L_0x0030
        L_0x002f:
            r14 = r2
        L_0x0030:
            gnu.expr.Expression[] r15 = new gnu.expr.Expression[r14]
            r2 = 0
            r16 = r1
        L_0x0035:
            if (r2 >= r14) goto L_0x007d
            r1 = r16
        L_0x0039:
            boolean r4 = r1 instanceof kawa.lang.SyntaxForm
            if (r4 == 0) goto L_0x0045
            r4 = r1
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4
            java.lang.Object r1 = r4.getDatum()
            goto L_0x0039
        L_0x0045:
            r4 = r1
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            gnu.expr.Expression r5 = r7.rewrite_car((gnu.lists.Pair) r4, (boolean) r0)
            r15[r2] = r5
            r5 = r15[r2]
            boolean r5 = r5 instanceof gnu.expr.ReferenceExp
            if (r5 == 0) goto L_0x0075
            r5 = r15[r2]
            gnu.expr.ReferenceExp r5 = (gnu.expr.ReferenceExp) r5
            gnu.expr.Declaration r5 = r5.getBinding()
            gnu.expr.Declaration r5 = gnu.expr.Declaration.followAliases(r5)
            if (r5 == 0) goto L_0x0075
            gnu.expr.Expression r6 = r5.getValue()
            r16 = r6
            boolean r6 = r6 instanceof gnu.expr.ClassExp
            if (r6 == 0) goto L_0x0075
            r6 = r16
            gnu.expr.ClassExp r6 = (gnu.expr.ClassExp) r6
            r9 = 131072(0x20000, float:1.83671E-40)
            r6.setFlag(r9)
        L_0x0075:
            java.lang.Object r16 = r4.getCdr()
            int r2 = r2 + 1
            r9 = 1
            goto L_0x0035
        L_0x007d:
            if (r13 == 0) goto L_0x00a8
            r1 = r13
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            gnu.expr.Expression r1 = r7.rewrite_car((gnu.lists.Pair) r1, (boolean) r0)
            java.lang.Object r2 = r1.valueIfConstant()
            boolean r4 = r2 instanceof java.lang.CharSequence
            if (r4 == 0) goto L_0x009c
            java.lang.String r5 = r2.toString()
            r6 = r5
            int r5 = r5.length()
            if (r5 <= 0) goto L_0x009c
            r8.classNameSpecifier = r6
            goto L_0x00a8
        L_0x009c:
            java.lang.Object r5 = r7.pushPositionOf(r13)
            java.lang.String r6 = "class-name specifier must be a non-empty string literal"
            r7.error(r3, r6)
            r7.popPositionOf(r5)
        L_0x00a8:
            r8.supers = r15
            r8.setTypes(r7)
            int r9 = r11.size()
            r1 = 0
        L_0x00b2:
            r6 = 0
            if (r1 >= r9) goto L_0x00ca
            int r2 = r1 + 1
            java.lang.Object r2 = r11.elementAt(r2)
            if (r2 == 0) goto L_0x00c7
            java.lang.Object r3 = r11.elementAt(r1)
            r4 = r2
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4
            rewriteInit(r3, r8, r4, r7, r6)
        L_0x00c7:
            int r1 = r1 + 2
            goto L_0x00b2
        L_0x00ca:
            r7.push((gnu.expr.ScopeExp) r8)
            r1 = r12
            r2 = 0
            r3 = 0
            r4 = r10
            r5 = r1
            r1 = r4
            r4 = r2
        L_0x00d4:
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            if (r1 == r2) goto L_0x0477
            r17 = r3
        L_0x00da:
            boolean r2 = r1 instanceof kawa.lang.SyntaxForm
            if (r2 == 0) goto L_0x00e7
            r17 = r1
            kawa.lang.SyntaxForm r17 = (kawa.lang.SyntaxForm) r17
            java.lang.Object r1 = r17.getDatum()
            goto L_0x00da
        L_0x00e7:
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r3 = r7.pushPositionOf(r2)
            java.lang.Object r18 = r2.getCar()
            r19 = r17
            r6 = r18
        L_0x00f6:
            boolean r0 = r6 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x0104
            r19 = r6
            kawa.lang.SyntaxForm r19 = (kawa.lang.SyntaxForm) r19
            java.lang.Object r6 = r19.getDatum()
            r0 = 0
            goto L_0x00f6
        L_0x0104:
            java.lang.Object r0 = r2.getCdr()     // Catch:{ all -> 0x0464 }
            r1 = r0
            boolean r0 = r6 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x0452 }
            if (r0 == 0) goto L_0x0131
            boolean r0 = r1 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0121 }
            if (r0 == 0) goto L_0x0131
            r0 = r1
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0121 }
            java.lang.Object r0 = r0.getCdr()     // Catch:{ all -> 0x0121 }
            r1 = r0
            r7.popPositionOf(r3)
            r3 = r17
            r0 = 0
            r6 = 0
            goto L_0x00d4
        L_0x0121:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            goto L_0x0473
        L_0x0131:
            r0 = r6
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0     // Catch:{ all -> 0x0452 }
            r2 = r0
            java.lang.Object r0 = r2.getCar()     // Catch:{ all -> 0x043e }
            r6 = r19
            r31 = r6
            r6 = r0
            r0 = r31
        L_0x0140:
            r21 = r1
            boolean r1 = r6 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x0428 }
            if (r1 == 0) goto L_0x0164
            r1 = r6
            kawa.lang.SyntaxForm r1 = (kawa.lang.SyntaxForm) r1     // Catch:{ all -> 0x0152 }
            r0 = r1
            java.lang.Object r1 = r0.getDatum()     // Catch:{ all -> 0x0152 }
            r6 = r1
            r1 = r21
            goto L_0x0140
        L_0x0152:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r9 = r3
            goto L_0x0473
        L_0x0164:
            boolean r1 = r6 instanceof java.lang.String     // Catch:{ all -> 0x0428 }
            if (r1 != 0) goto L_0x02ba
            boolean r1 = r6 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x02a4 }
            if (r1 != 0) goto L_0x02ba
            boolean r1 = r6 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x02a4 }
            if (r1 == 0) goto L_0x0186
            r23 = r0
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r20 = 0
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
            goto L_0x02ce
        L_0x0186:
            boolean r1 = r6 instanceof gnu.lists.Pair     // Catch:{ all -> 0x02a4 }
            if (r1 == 0) goto L_0x0278
            gnu.expr.ScopeExp r1 = r34.currentScope()     // Catch:{ all -> 0x02a4 }
            if (r19 == 0) goto L_0x019a
            r22 = r1
            kawa.lang.TemplateScope r1 = r19.getScope()     // Catch:{ all -> 0x0152 }
            r7.setCurrentScope(r1)     // Catch:{ all -> 0x0152 }
            goto L_0x019c
        L_0x019a:
            r22 = r1
        L_0x019c:
            java.lang.String r1 = "*init*"
            r23 = r3
            java.lang.String r3 = r5.getName()     // Catch:{ all -> 0x0261 }
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0261 }
            if (r1 == 0) goto L_0x01c3
            gnu.bytecode.PrimType r1 = gnu.bytecode.Type.voidType     // Catch:{ all -> 0x01b0 }
            r5.setReturnType(r1)     // Catch:{ all -> 0x01b0 }
            goto L_0x01c3
        L_0x01b0:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r9 = r23
            goto L_0x0473
        L_0x01c3:
            kawa.lang.Translator.setLine((gnu.expr.Expression) r5, (java.lang.Object) r2)     // Catch:{ all -> 0x0261 }
            gnu.expr.LambdaExp r1 = r7.curMethodLambda     // Catch:{ all -> 0x0261 }
            r3 = r1
            r7.curMethodLambda = r5     // Catch:{ all -> 0x0261 }
            r1 = r32
            r24 = r3
            kawa.lang.Lambda r3 = r1.lambda     // Catch:{ all -> 0x0261 }
            r25 = r6
            gnu.lists.Pair r25 = (gnu.lists.Pair) r25     // Catch:{ all -> 0x0261 }
            java.lang.Object r25 = r25.getCdr()     // Catch:{ all -> 0x0261 }
            java.lang.Object r26 = r2.getCdr()     // Catch:{ all -> 0x0261 }
            if (r0 == 0) goto L_0x0221
            if (r19 == 0) goto L_0x0203
            kawa.lang.TemplateScope r1 = r0.getScope()     // Catch:{ all -> 0x01ee }
            r27 = r2
            kawa.lang.TemplateScope r2 = r19.getScope()     // Catch:{ all -> 0x020c }
            if (r1 == r2) goto L_0x0223
            goto L_0x0205
        L_0x01ee:
            r0 = move-exception
            r27 = r2
            r28 = r5
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r27 = r9
            r9 = r23
            goto L_0x0473
        L_0x0203:
            r27 = r2
        L_0x0205:
            kawa.lang.TemplateScope r1 = r0.getScope()     // Catch:{ all -> 0x020c }
            r28 = r1
            goto L_0x0225
        L_0x020c:
            r0 = move-exception
            r28 = r5
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r1 = r21
            r2 = r27
            r27 = r9
            r9 = r23
            goto L_0x0473
        L_0x0221:
            r27 = r2
        L_0x0223:
            r28 = 0
        L_0x0225:
            r2 = r22
            r1 = r3
            r3 = r2
            r22 = r27
            r2 = r5
            r27 = r9
            r9 = r23
            r23 = r0
            r0 = r3
            r31 = r24
            r24 = r10
            r10 = r31
            r3 = r25
            r25 = r12
            r12 = r4
            r4 = r26
            r26 = r13
            r13 = r5
            r5 = r34
            r29 = r14
            r20 = 0
            r14 = r6
            r6 = r28
            r1.rewrite(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0299 }
            r7.curMethodLambda = r10     // Catch:{ all -> 0x0299 }
            if (r19 == 0) goto L_0x0256
            r7.setCurrentScope(r0)     // Catch:{ all -> 0x0299 }
        L_0x0256:
            gnu.expr.LambdaExp r1 = r13.nextSibling     // Catch:{ all -> 0x0299 }
            r0 = r1
            r5 = r0
            r4 = r12
            r30 = r14
            r2 = r22
            goto L_0x0403
        L_0x0261:
            r0 = move-exception
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r23
            r12 = r4
            r14 = r6
            r28 = r5
            r1 = r21
            goto L_0x0473
        L_0x0278:
            r23 = r0
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r20 = 0
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
            java.lang.String r0 = "invalid field/method definition"
            r7.syntaxError(r0)     // Catch:{ all -> 0x0299 }
            r4 = r12
            r5 = r13
            r30 = r14
            r2 = r22
            goto L_0x0403
        L_0x0299:
            r0 = move-exception
            r4 = r12
            r28 = r13
            r6 = r14
            r1 = r21
            r2 = r22
            goto L_0x0473
        L_0x02a4:
            r0 = move-exception
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            r14 = r6
            r28 = r5
            r1 = r21
            goto L_0x0473
        L_0x02ba:
            r23 = r0
            r22 = r2
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r20 = 0
            r9 = r3
            r12 = r4
            r13 = r5
            r14 = r6
        L_0x02ce:
            r0 = 0
            r1 = 0
            boolean r2 = r14 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x041a }
            if (r2 == 0) goto L_0x02d7
            r2 = r22
            goto L_0x02db
        L_0x02d7:
            java.lang.Object r2 = r22.getCdr()     // Catch:{ all -> 0x041a }
        L_0x02db:
            r3 = 0
            r4 = 0
        L_0x02dd:
            gnu.lists.LList r5 = gnu.lists.LList.Empty     // Catch:{ all -> 0x041a }
            if (r2 == r5) goto L_0x03bd
        L_0x02e1:
            boolean r5 = r2 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x041a }
            if (r5 == 0) goto L_0x02f0
            r5 = r2
            kawa.lang.SyntaxForm r5 = (kawa.lang.SyntaxForm) r5     // Catch:{ all -> 0x0299 }
            r19 = r5
            java.lang.Object r5 = r19.getDatum()     // Catch:{ all -> 0x0299 }
            r2 = r5
            goto L_0x02e1
        L_0x02f0:
            r5 = r2
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5     // Catch:{ all -> 0x041a }
            java.lang.Object r6 = r5.getCar()     // Catch:{ all -> 0x03b0 }
        L_0x02f7:
            boolean r10 = r6 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x03b0 }
            if (r10 == 0) goto L_0x030e
            r10 = r6
            kawa.lang.SyntaxForm r10 = (kawa.lang.SyntaxForm) r10     // Catch:{ all -> 0x0304 }
            java.lang.Object r10 = r10.getDatum()     // Catch:{ all -> 0x0304 }
            r6 = r10
            goto L_0x02f7
        L_0x0304:
            r0 = move-exception
            r2 = r5
            r4 = r12
            r28 = r13
            r6 = r14
            r1 = r21
            goto L_0x0473
        L_0x030e:
            java.lang.Object r10 = r7.pushPositionOf(r5)     // Catch:{ all -> 0x03b0 }
            java.lang.Object r22 = r5.getCdr()     // Catch:{ all -> 0x03b0 }
            r2 = r22
            r28 = r13
            gnu.mapping.Symbol r13 = coloncolon     // Catch:{ all -> 0x03a5 }
            if (r6 == r13) goto L_0x0325
            r30 = r14
            boolean r14 = r6 instanceof gnu.expr.Keyword     // Catch:{ all -> 0x039c }
            if (r14 == 0) goto L_0x035a
            goto L_0x0327
        L_0x0325:
            r30 = r14
        L_0x0327:
            boolean r14 = r2 instanceof gnu.lists.Pair     // Catch:{ all -> 0x039c }
            if (r14 == 0) goto L_0x035a
            int r1 = r1 + 1
            r14 = r2
            gnu.lists.Pair r14 = (gnu.lists.Pair) r14     // Catch:{ all -> 0x039c }
            r5 = r14
            java.lang.Object r14 = r5.getCar()     // Catch:{ all -> 0x039c }
            java.lang.Object r22 = r5.getCdr()     // Catch:{ all -> 0x039c }
            r2 = r22
            if (r6 == r13) goto L_0x0356
            gnu.expr.Keyword r13 = typeKeyword     // Catch:{ all -> 0x039c }
            if (r6 != r13) goto L_0x0342
            goto L_0x0356
        L_0x0342:
            gnu.expr.Keyword r13 = initKeyword     // Catch:{ all -> 0x039c }
            if (r6 == r13) goto L_0x0352
            gnu.expr.Keyword r13 = initformKeyword     // Catch:{ all -> 0x039c }
            if (r6 == r13) goto L_0x0352
            gnu.expr.Keyword r13 = init_formKeyword     // Catch:{ all -> 0x039c }
            if (r6 == r13) goto L_0x0352
            gnu.expr.Keyword r13 = init_valueKeyword     // Catch:{ all -> 0x039c }
            if (r6 != r13) goto L_0x0357
        L_0x0352:
            r3 = r5
            r4 = r19
            goto L_0x0357
        L_0x0356:
            r0 = r14
        L_0x0357:
            r22 = r5
            goto L_0x0387
        L_0x035a:
            gnu.lists.LList r13 = gnu.lists.LList.Empty     // Catch:{ all -> 0x039c }
            if (r2 != r13) goto L_0x0366
            if (r3 != 0) goto L_0x0366
            r3 = r5
            r4 = r19
            r22 = r5
            goto L_0x0387
        L_0x0366:
            boolean r13 = r2 instanceof gnu.lists.Pair     // Catch:{ all -> 0x039c }
            if (r13 == 0) goto L_0x039a
            if (r1 != 0) goto L_0x039a
            if (r3 != 0) goto L_0x039a
            if (r0 != 0) goto L_0x039a
            r13 = r2
            gnu.lists.Pair r13 = (gnu.lists.Pair) r13     // Catch:{ all -> 0x039c }
            r5 = r13
            java.lang.Object r13 = r13.getCdr()     // Catch:{ all -> 0x039c }
            gnu.lists.LList r14 = gnu.lists.LList.Empty     // Catch:{ all -> 0x039c }
            if (r13 != r14) goto L_0x039a
            r0 = r6
            r3 = r5
            r4 = r19
            java.lang.Object r13 = r5.getCdr()     // Catch:{ all -> 0x039c }
            r2 = r13
            r22 = r5
        L_0x0387:
            r7.popPositionOf(r10)     // Catch:{ all -> 0x0390 }
            r13 = r28
            r14 = r30
            goto L_0x02dd
        L_0x0390:
            r0 = move-exception
            r4 = r12
            r1 = r21
            r2 = r22
            r6 = r30
            goto L_0x0473
        L_0x039a:
            r2 = 0
            goto L_0x03c3
        L_0x039c:
            r0 = move-exception
            r2 = r5
            r4 = r12
            r1 = r21
            r6 = r30
            goto L_0x0473
        L_0x03a5:
            r0 = move-exception
            r30 = r14
            r2 = r5
            r4 = r12
            r1 = r21
            r6 = r30
            goto L_0x0473
        L_0x03b0:
            r0 = move-exception
            r28 = r13
            r30 = r14
            r2 = r5
            r4 = r12
            r1 = r21
            r6 = r30
            goto L_0x0473
        L_0x03bd:
            r28 = r13
            r30 = r14
            r5 = r22
        L_0x03c3:
            if (r3 == 0) goto L_0x03ff
            int r6 = r12 + 1
            java.lang.Object r10 = r11.elementAt(r12)     // Catch:{ all -> 0x03f6 }
            boolean r12 = r10 instanceof gnu.expr.Declaration     // Catch:{ all -> 0x03f6 }
            if (r12 == 0) goto L_0x03d9
            r12 = r10
            gnu.expr.Declaration r12 = (gnu.expr.Declaration) r12     // Catch:{ all -> 0x03f6 }
            r13 = 2048(0x800, double:1.0118E-320)
            boolean r12 = r12.getFlag(r13)     // Catch:{ all -> 0x03f6 }
            goto L_0x03e0
        L_0x03d9:
            java.lang.Boolean r12 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x03f6 }
            if (r10 != r12) goto L_0x03df
            r12 = 1
            goto L_0x03e0
        L_0x03df:
            r12 = 0
        L_0x03e0:
            int r13 = r6 + 1
            java.lang.Object r6 = r11.elementAt(r6)     // Catch:{ all -> 0x03ed }
            if (r6 != 0) goto L_0x03eb
            rewriteInit(r10, r8, r3, r7, r4)     // Catch:{ all -> 0x03ed }
        L_0x03eb:
            r4 = r13
            goto L_0x0400
        L_0x03ed:
            r0 = move-exception
            r2 = r5
            r4 = r13
            r1 = r21
            r6 = r30
            goto L_0x0473
        L_0x03f6:
            r0 = move-exception
            r2 = r5
            r4 = r6
            r1 = r21
            r6 = r30
            goto L_0x0473
        L_0x03ff:
            r4 = r12
        L_0x0400:
            r2 = r5
            r5 = r28
        L_0x0403:
            r7.popPositionOf(r9)
            r3 = r17
            r6 = r20
            r1 = r21
            r10 = r24
            r12 = r25
            r13 = r26
            r9 = r27
            r14 = r29
            r0 = 0
            goto L_0x00d4
        L_0x041a:
            r0 = move-exception
            r28 = r13
            r30 = r14
            r4 = r12
            r1 = r21
            r2 = r22
            r6 = r30
            goto L_0x0473
        L_0x0428:
            r0 = move-exception
            r22 = r2
            r28 = r5
            r30 = r6
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            r1 = r21
            goto L_0x0473
        L_0x043e:
            r0 = move-exception
            r21 = r1
            r22 = r2
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            goto L_0x0473
        L_0x0452:
            r0 = move-exception
            r21 = r1
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
            goto L_0x0473
        L_0x0464:
            r0 = move-exception
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r9 = r3
            r12 = r4
        L_0x0473:
            r7.popPositionOf(r9)
            throw r0
        L_0x0477:
            r28 = r5
            r27 = r9
            r24 = r10
            r25 = r12
            r26 = r13
            r29 = r14
            r12 = r4
            gnu.expr.LambdaExp r0 = r8.initMethod
            if (r0 == 0) goto L_0x048c
            gnu.expr.LambdaExp r0 = r8.initMethod
            r0.outer = r8
        L_0x048c:
            gnu.expr.LambdaExp r0 = r8.clinitMethod
            if (r0 == 0) goto L_0x0494
            gnu.expr.LambdaExp r0 = r8.clinitMethod
            r0.outer = r8
        L_0x0494:
            r7.pop(r8)
            r8.declareParts(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteClassDef(java.lang.Object[], kawa.lang.Translator):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: gnu.expr.ApplyExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: gnu.expr.SetExp} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: gnu.expr.ApplyExp} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void rewriteInit(java.lang.Object r7, gnu.expr.ClassExp r8, gnu.lists.Pair r9, kawa.lang.Translator r10, kawa.lang.SyntaxForm r11) {
        /*
            boolean r0 = r7 instanceof gnu.expr.Declaration
            r1 = 1
            if (r0 == 0) goto L_0x000f
            r0 = r7
            gnu.expr.Declaration r0 = (gnu.expr.Declaration) r0
            r2 = 2048(0x800, double:1.0118E-320)
            boolean r0 = r0.getFlag(r2)
            goto L_0x0016
        L_0x000f:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            if (r7 != r0) goto L_0x0015
            r0 = 1
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            if (r0 == 0) goto L_0x001b
            gnu.expr.LambdaExp r2 = r8.clinitMethod
            goto L_0x001d
        L_0x001b:
            gnu.expr.LambdaExp r2 = r8.initMethod
        L_0x001d:
            r3 = 0
            if (r2 != 0) goto L_0x0054
            gnu.expr.LambdaExp r4 = new gnu.expr.LambdaExp
            gnu.expr.BeginExp r5 = new gnu.expr.BeginExp
            r5.<init>()
            r4.<init>((gnu.expr.Expression) r5)
            r2 = r4
            r2.setClassMethod(r1)
            gnu.bytecode.PrimType r1 = gnu.bytecode.Type.voidType
            r2.setReturnType(r1)
            if (r0 == 0) goto L_0x003d
            java.lang.String r1 = "$clinit$"
            r2.setName(r1)
            r8.clinitMethod = r2
            goto L_0x004e
        L_0x003d:
            java.lang.String r1 = "$finit$"
            r2.setName(r1)
            r8.initMethod = r2
            gnu.expr.Declaration r1 = new gnu.expr.Declaration
            java.lang.String r4 = gnu.expr.ThisExp.THIS_NAME
            r1.<init>((java.lang.Object) r4)
            r2.add(r3, r1)
        L_0x004e:
            gnu.expr.LambdaExp r1 = r8.firstChild
            r2.nextSibling = r1
            r8.firstChild = r2
        L_0x0054:
            r10.push((gnu.expr.ScopeExp) r2)
            gnu.expr.LambdaExp r1 = r10.curMethodLambda
            r10.curMethodLambda = r2
            gnu.expr.Expression r4 = r10.rewrite_car((gnu.lists.Pair) r9, (kawa.lang.SyntaxForm) r11)
            boolean r5 = r7 instanceof gnu.expr.Declaration
            if (r5 == 0) goto L_0x0073
            r5 = r7
            gnu.expr.Declaration r5 = (gnu.expr.Declaration) r5
            gnu.expr.SetExp r6 = new gnu.expr.SetExp
            r6.<init>((gnu.expr.Declaration) r5, (gnu.expr.Expression) r4)
            r6.setLocation(r5)
            r5.noteValue(r3)
            r3 = r6
            goto L_0x007e
        L_0x0073:
            gnu.expr.QuoteExp r3 = new gnu.expr.QuoteExp
            gnu.bytecode.PrimType r5 = gnu.bytecode.Type.voidType
            r3.<init>(r5)
            gnu.expr.ApplyExp r3 = gnu.expr.Compilation.makeCoercion((gnu.expr.Expression) r4, (gnu.expr.Expression) r3)
        L_0x007e:
            gnu.expr.Expression r4 = r2.body
            gnu.expr.BeginExp r4 = (gnu.expr.BeginExp) r4
            r4.add(r3)
            r10.curMethodLambda = r1
            r10.pop(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.object.rewriteInit(java.lang.Object, gnu.expr.ClassExp, gnu.lists.Pair, kawa.lang.Translator, kawa.lang.SyntaxForm):void");
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else {
            if (exp instanceof Pair) {
                Object matchQuoted = tr.matchQuoted((Pair) exp);
                Object qvalue = matchQuoted;
                if (matchQuoted instanceof SimpleSymbol) {
                    value = qvalue.toString();
                }
            }
            return false;
        }
        if (tag == null || tag.equals(value)) {
            return true;
        }
        return false;
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if (((-1 ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair pair = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        Object cdr = pair.getCdr();
        if (cdr == LList.Empty || icar == 0) {
            return icar;
        }
        if (cdr instanceof Pair) {
            long icdr = matchAccess2((Pair) cdr, tr);
            if (icdr != 0) {
                return icar | icdr;
            }
        }
        return 0;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216;
        }
        if ("protected".equals(value)) {
            return 33554432;
        }
        if ("public".equals(value)) {
            return 67108864;
        }
        if ("package".equals(value)) {
            return 134217728;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0;
    }
}
