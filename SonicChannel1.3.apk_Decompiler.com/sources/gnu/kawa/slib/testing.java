package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.lang.Eval;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.parameters;
import kawa.lib.ports;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.readchar;
import kawa.standard.syntax_case;

/* compiled from: testing.scm */
public class testing extends ModuleBody {
    public static final ModuleMethod $Pctest$Mnbegin;
    static final ModuleMethod $Pctest$Mnnull$Mncallback;
    public static final ModuleMethod $Prvt$$Pctest$Mnapproximimate$Eq;
    public static final ModuleMethod $Prvt$$Pctest$Mnas$Mnspecifier;
    public static final Macro $Prvt$$Pctest$Mncomp1body;
    public static final Macro $Prvt$$Pctest$Mncomp2body;
    public static final ModuleMethod $Prvt$$Pctest$Mnend;
    public static final Macro $Prvt$$Pctest$Mnerror;
    public static final Macro $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnall;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnany;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnnth;
    public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnbegin;
    public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnend;
    public static final ModuleMethod $Prvt$$Pctest$Mnreport$Mnresult;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex;
    public static final ModuleMethod $Prvt$$Pctest$Mnshould$Mnexecute;
    public static final Macro $Prvt$test$Mngroup;
    public static final testing $instance;
    static final IntNum Lit0 = IntNum.make(0);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("result-kind").readResolve());
    static final PairWithPosition Lit10;
    static final SyntaxPattern Lit100 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
    static final SyntaxTemplate Lit101;
    static final SyntaxPattern Lit102 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit103;
    static final SimpleSymbol Lit104;
    static final SyntaxTemplate Lit105 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("eqv?").readResolve()}, 0);
    static final SimpleSymbol Lit106;
    static final SyntaxTemplate Lit107 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("eq?").readResolve()}, 0);
    static final SimpleSymbol Lit108;
    static final SyntaxTemplate Lit109 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("equal?").readResolve()}, 0);
    static final PairWithPosition Lit11;
    static final SimpleSymbol Lit110;
    static final SyntaxPattern Lit111 = new SyntaxPattern("\\\f\u0007\f\u000f\f\u0017\f\u001f\f'\b\f/\b", new Object[0], 6);
    static final SyntaxTemplate Lit112;
    static final SyntaxPattern Lit113 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\b", new Object[0], 5);
    static final SyntaxTemplate Lit114;
    static final SimpleSymbol Lit115;
    static final SyntaxRules Lit116;
    static final SimpleSymbol Lit117;
    static final SyntaxPattern Lit118 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\b", new Object[0], 5);
    static final SyntaxTemplate Lit119;
    static final SimpleSymbol Lit12;
    static final SyntaxPattern Lit120 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
    static final SyntaxTemplate Lit121;
    static final SyntaxPattern Lit122 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit123;
    static final SimpleSymbol Lit124;
    static final SimpleSymbol Lit125;
    static final SyntaxRules Lit126;
    static final SimpleSymbol Lit127;
    static final SimpleSymbol Lit128;
    static final SyntaxRules Lit129;
    static final IntNum Lit13;
    static final SimpleSymbol Lit130;
    static final SimpleSymbol Lit131;
    static final SyntaxRules Lit132;
    static final SimpleSymbol Lit133;
    static final SimpleSymbol Lit134;
    static final SyntaxRules Lit135;
    static final SimpleSymbol Lit136;
    static final SimpleSymbol Lit137;
    static final SyntaxRules Lit138;
    static final SimpleSymbol Lit139;
    static final SimpleSymbol Lit14;
    static final SyntaxRules Lit140;
    static final SimpleSymbol Lit141;
    static final SimpleSymbol Lit142;
    static final SimpleSymbol Lit143;
    static final SimpleSymbol Lit144;
    static final SimpleSymbol Lit145;
    static final SimpleSymbol Lit146;
    static final SimpleSymbol Lit147;
    static final SimpleSymbol Lit148;
    static final SimpleSymbol Lit149;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit150;
    static final SimpleSymbol Lit151;
    static final SimpleSymbol Lit152;
    static final SimpleSymbol Lit153;
    static final SimpleSymbol Lit154;
    static final SimpleSymbol Lit155;
    static final SimpleSymbol Lit156;
    static final SimpleSymbol Lit157;
    static final SimpleSymbol Lit158;
    static final SimpleSymbol Lit159;
    static final SyntaxPattern Lit16 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\f/\b", new Object[0], 6);
    static final SimpleSymbol Lit160;
    static final SimpleSymbol Lit161;
    static final SimpleSymbol Lit162;
    static final SimpleSymbol Lit163;
    static final SimpleSymbol Lit164;
    static final SimpleSymbol Lit165;
    static final SyntaxTemplate Lit17;
    static final SyntaxPattern Lit18 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\f'\b", new Object[0], 5);
    static final SyntaxTemplate Lit19;
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("skip").readResolve());
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
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("xfail").readResolve());
    static final SimpleSymbol Lit30;
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit32;
    static final SimpleSymbol Lit33;
    static final SimpleSymbol Lit34;
    static final SimpleSymbol Lit35;
    static final SimpleSymbol Lit36;
    static final SimpleSymbol Lit37;
    static final SimpleSymbol Lit38;
    static final SimpleSymbol Lit39;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit40;
    static final SimpleSymbol Lit41;
    static final SimpleSymbol Lit42;
    static final SimpleSymbol Lit43;
    static final SimpleSymbol Lit44;
    static final SimpleSymbol Lit45;
    static final SimpleSymbol Lit46;
    static final SimpleSymbol Lit47;
    static final SimpleSymbol Lit48;
    static final SimpleSymbol Lit49;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit50;
    static final SimpleSymbol Lit51;
    static final SimpleSymbol Lit52;
    static final SimpleSymbol Lit53;
    static final SimpleSymbol Lit54;
    static final SimpleSymbol Lit55;
    static final SimpleSymbol Lit56;
    static final SimpleSymbol Lit57;
    static final SimpleSymbol Lit58;
    static final SimpleSymbol Lit59;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit60;
    static final SimpleSymbol Lit61;
    static final SimpleSymbol Lit62;
    static final SimpleSymbol Lit63;
    static final SimpleSymbol Lit64;
    static final SimpleSymbol Lit65;
    static final SimpleSymbol Lit66;
    static final SimpleSymbol Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70;
    static final SyntaxRules Lit71;
    static final SimpleSymbol Lit72;
    static final SyntaxRules Lit73;
    static final SimpleSymbol Lit74;
    static final SimpleSymbol Lit75;
    static final SyntaxRules Lit76;
    static final SimpleSymbol Lit77;
    static final SimpleSymbol Lit78;
    static final SimpleSymbol Lit79;
    static final PairWithPosition Lit8;
    static final SimpleSymbol Lit80;
    static final SimpleSymbol Lit81;
    static final SimpleSymbol Lit82;
    static final SimpleSymbol Lit83;
    static final SimpleSymbol Lit84;
    static final SyntaxRules Lit85;
    static final SimpleSymbol Lit86;
    static final SimpleSymbol Lit87;
    static final SimpleSymbol Lit88;
    static final SimpleSymbol Lit89;
    static final SimpleSymbol Lit9;
    static final SyntaxRules Lit90;
    static final SimpleSymbol Lit91;
    static final SimpleSymbol Lit92;
    static final SyntaxRules Lit93;
    static final SimpleSymbol Lit94;
    static final SyntaxPattern Lit95 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit96;
    static final SyntaxPattern Lit97 = new SyntaxPattern("\u001c\f\u0007\b\f\u000f\b", new Object[0], 2);
    static final SyntaxTemplate Lit98;
    static final SimpleSymbol Lit99;
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn2;
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod test$Mnapply;
    public static final Macro test$Mnapproximate;
    public static final Macro test$Mnassert;
    public static final Macro test$Mnend;
    public static final Macro test$Mneq;
    public static final Macro test$Mnequal;
    public static final Macro test$Mneqv;
    public static final Macro test$Mnerror;
    public static final Macro test$Mnexpect$Mnfail;
    public static final Macro test$Mngroup$Mnwith$Mncleanup;
    public static Boolean test$Mnlog$Mnto$Mnfile;
    public static final Macro test$Mnmatch$Mnall;
    public static final Macro test$Mnmatch$Mnany;
    public static final ModuleMethod test$Mnmatch$Mnname;
    public static final Macro test$Mnmatch$Mnnth;
    public static final ModuleMethod test$Mnon$Mnbad$Mncount$Mnsimple;
    public static final ModuleMethod test$Mnon$Mnbad$Mnend$Mnname$Mnsimple;
    public static final ModuleMethod test$Mnon$Mnfinal$Mnsimple;
    public static final ModuleMethod test$Mnon$Mngroup$Mnbegin$Mnsimple;
    public static final ModuleMethod test$Mnon$Mngroup$Mnend$Mnsimple;
    static final ModuleMethod test$Mnon$Mntest$Mnbegin$Mnsimple;
    public static final ModuleMethod test$Mnon$Mntest$Mnend$Mnsimple;
    public static final ModuleMethod test$Mnpassed$Qu;
    public static final ModuleMethod test$Mnread$Mneval$Mnstring;
    public static final ModuleMethod test$Mnresult$Mnalist;
    public static final ModuleMethod test$Mnresult$Mnalist$Ex;
    public static final ModuleMethod test$Mnresult$Mnclear;
    public static final ModuleMethod test$Mnresult$Mnkind;
    public static final Macro test$Mnresult$Mnref;
    public static final ModuleMethod test$Mnresult$Mnremove;
    public static final ModuleMethod test$Mnresult$Mnset$Ex;
    static final Class test$Mnrunner = test$Mnrunner.class;
    public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue;
    public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue$Ex;
    public static final ModuleMethod test$Mnrunner$Mncreate;
    public static Object test$Mnrunner$Mncurrent;
    public static Object test$Mnrunner$Mnfactory;
    public static final ModuleMethod test$Mnrunner$Mnfail$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnfail$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnget;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnpath;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack$Ex;
    public static final ModuleMethod test$Mnrunner$Mnnull;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend$Ex;
    public static final ModuleMethod test$Mnrunner$Mnpass$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnpass$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnreset;
    public static final ModuleMethod test$Mnrunner$Mnsimple;
    public static final ModuleMethod test$Mnrunner$Mnskip$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnskip$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mntest$Mnname;
    public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Qu;
    public static final Macro test$Mnskip;
    public static final Macro test$Mnwith$Mnrunner;

    public testing() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        test$Mnlog$Mnto$Mnfile = Boolean.TRUE;
        test$Mnrunner$Mncurrent = parameters.makeParameter(Boolean.FALSE);
        test$Mnrunner$Mnfactory = parameters.makeParameter(test$Mnrunner$Mnsimple);
    }

    static {
        Procedure procedure;
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("dynamic-wind").readResolve();
        Lit165 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("p").readResolve();
        Lit164 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("exp").readResolve();
        Lit163 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("res").readResolve();
        Lit162 = simpleSymbol4;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("if").readResolve();
        Lit161 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("name").readResolve();
        Lit160 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve();
        Lit159 = simpleSymbol7;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("cond").readResolve();
        Lit158 = simpleSymbol8;
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("actual-error").readResolve();
        Lit157 = simpleSymbol9;
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol("<java.lang.Throwable>").readResolve();
        Lit156 = simpleSymbol10;
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("actual-value").readResolve();
        Lit155 = simpleSymbol11;
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("try-catch").readResolve();
        Lit154 = simpleSymbol12;
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol("et").readResolve();
        Lit153 = simpleSymbol13;
        SimpleSymbol simpleSymbol14 = (SimpleSymbol) new SimpleSymbol("expected-error").readResolve();
        Lit152 = simpleSymbol14;
        SimpleSymbol simpleSymbol15 = (SimpleSymbol) new SimpleSymbol("ex").readResolve();
        Lit151 = simpleSymbol15;
        SimpleSymbol simpleSymbol16 = simpleSymbol2;
        SimpleSymbol simpleSymbol17 = (SimpleSymbol) new SimpleSymbol("let*").readResolve();
        Lit150 = simpleSymbol17;
        SimpleSymbol simpleSymbol18 = simpleSymbol3;
        SimpleSymbol simpleSymbol19 = (SimpleSymbol) new SimpleSymbol("r").readResolve();
        Lit149 = simpleSymbol19;
        SimpleSymbol simpleSymbol20 = simpleSymbol4;
        SimpleSymbol simpleSymbol21 = (SimpleSymbol) new SimpleSymbol("saved-runner").readResolve();
        Lit148 = simpleSymbol21;
        SimpleSymbol simpleSymbol22 = simpleSymbol7;
        SimpleSymbol simpleSymbol23 = (SimpleSymbol) new SimpleSymbol("lambda").readResolve();
        Lit147 = simpleSymbol23;
        SimpleSymbol simpleSymbol24 = simpleSymbol13;
        SimpleSymbol simpleSymbol25 = (SimpleSymbol) new SimpleSymbol("test-runner-current").readResolve();
        Lit146 = simpleSymbol25;
        SimpleSymbol simpleSymbol26 = simpleSymbol5;
        SimpleSymbol simpleSymbol27 = (SimpleSymbol) new SimpleSymbol("cons").readResolve();
        Lit145 = simpleSymbol27;
        SimpleSymbol simpleSymbol28 = simpleSymbol9;
        SimpleSymbol simpleSymbol29 = (SimpleSymbol) new SimpleSymbol("let").readResolve();
        Lit144 = simpleSymbol29;
        SimpleSymbol simpleSymbol30 = simpleSymbol10;
        SimpleSymbol simpleSymbol31 = (SimpleSymbol) new SimpleSymbol("runner").readResolve();
        Lit143 = simpleSymbol31;
        SimpleSymbol simpleSymbol32 = simpleSymbol15;
        SimpleSymbol simpleSymbol33 = (SimpleSymbol) new SimpleSymbol("test-read-eval-string").readResolve();
        Lit142 = simpleSymbol33;
        SimpleSymbol simpleSymbol34 = simpleSymbol33;
        SimpleSymbol simpleSymbol35 = (SimpleSymbol) new SimpleSymbol("test-match-name").readResolve();
        Lit141 = simpleSymbol35;
        SimpleSymbol simpleSymbol36 = simpleSymbol35;
        SimpleSymbol simpleSymbol37 = simpleSymbol11;
        SimpleSymbol simpleSymbol38 = simpleSymbol12;
        SimpleSymbol simpleSymbol39 = (SimpleSymbol) new SimpleSymbol("test-expect-fail").readResolve();
        Lit139 = simpleSymbol39;
        SimpleSymbol simpleSymbol40 = simpleSymbol39;
        SimpleSymbol simpleSymbol41 = simpleSymbol8;
        SimpleSymbol simpleSymbol42 = simpleSymbol14;
        SimpleSymbol simpleSymbol43 = simpleSymbol6;
        SyntaxPattern syntaxPattern = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol44 = simpleSymbol19;
        SimpleSymbol simpleSymbol45 = (SimpleSymbol) new SimpleSymbol("test-runner-get").readResolve();
        Lit60 = simpleSymbol45;
        SimpleSymbol simpleSymbol46 = simpleSymbol17;
        SimpleSymbol simpleSymbol47 = simpleSymbol23;
        SimpleSymbol simpleSymbol48 = simpleSymbol;
        SimpleSymbol simpleSymbol49 = (SimpleSymbol) new SimpleSymbol("%test-runner-fail-list!").readResolve();
        Lit34 = simpleSymbol49;
        SimpleSymbol simpleSymbol50 = (SimpleSymbol) new SimpleSymbol("test-match-all").readResolve();
        Lit131 = simpleSymbol50;
        SimpleSymbol simpleSymbol51 = simpleSymbol49;
        SimpleSymbol simpleSymbol52 = (SimpleSymbol) new SimpleSymbol("%test-as-specifier").readResolve();
        Lit136 = simpleSymbol52;
        SimpleSymbol simpleSymbol53 = simpleSymbol21;
        SimpleSymbol simpleSymbol54 = (SimpleSymbol) new SimpleSymbol("%test-runner-fail-list").readResolve();
        Lit33 = simpleSymbol54;
        SimpleSymbol simpleSymbol55 = simpleSymbol25;
        SimpleSymbol simpleSymbol56 = simpleSymbol54;
        SyntaxRules syntaxRules = new SyntaxRules(new Object[]{simpleSymbol39}, new SyntaxRule[]{new SyntaxRule(syntaxPattern, "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", new Object[]{simpleSymbol29, PairWithPosition.make(PairWithPosition.make(simpleSymbol31, PairWithPosition.make(PairWithPosition.make(simpleSymbol45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952660), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952660), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952652), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952651), simpleSymbol49, simpleSymbol31, simpleSymbol27, simpleSymbol50, simpleSymbol52, PairWithPosition.make(PairWithPosition.make(simpleSymbol54, PairWithPosition.make(simpleSymbol31, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3964958), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3964934), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3964934)}, 1)}, 1);
        Lit140 = syntaxRules;
        SimpleSymbol simpleSymbol57 = (SimpleSymbol) new SimpleSymbol("test-skip").readResolve();
        Lit137 = simpleSymbol57;
        SyntaxRules syntaxRules2 = syntaxRules;
        SyntaxPattern syntaxPattern2 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol58 = simpleSymbol57;
        SimpleSymbol simpleSymbol59 = (SimpleSymbol) new SimpleSymbol("%test-runner-skip-list!").readResolve();
        Lit32 = simpleSymbol59;
        SimpleSymbol simpleSymbol60 = (SimpleSymbol) new SimpleSymbol("%test-runner-skip-list").readResolve();
        Lit31 = simpleSymbol60;
        SimpleSymbol simpleSymbol61 = simpleSymbol59;
        SyntaxRules syntaxRules3 = new SyntaxRules(new Object[]{simpleSymbol57}, new SyntaxRule[]{new SyntaxRule(syntaxPattern2, "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", new Object[]{simpleSymbol29, PairWithPosition.make(PairWithPosition.make(simpleSymbol31, PairWithPosition.make(PairWithPosition.make(simpleSymbol45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919892), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919892), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919884), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919883), simpleSymbol59, simpleSymbol31, simpleSymbol27, simpleSymbol50, simpleSymbol52, PairWithPosition.make(PairWithPosition.make(simpleSymbol60, PairWithPosition.make(simpleSymbol31, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3932190), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3932166), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3932166)}, 1)}, 1);
        Lit138 = syntaxRules3;
        SimpleSymbol simpleSymbol62 = (SimpleSymbol) new SimpleSymbol("test-match-any").readResolve();
        Lit134 = simpleSymbol62;
        SyntaxRules syntaxRules4 = syntaxRules3;
        SimpleSymbol simpleSymbol63 = simpleSymbol62;
        SyntaxPattern syntaxPattern3 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol64 = (SimpleSymbol) new SimpleSymbol("%test-match-any").readResolve();
        Lit133 = simpleSymbol64;
        SyntaxRules syntaxRules5 = new SyntaxRules(new Object[]{simpleSymbol62}, new SyntaxRule[]{new SyntaxRule(syntaxPattern3, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", new Object[]{simpleSymbol64, simpleSymbol52}, 1)}, 1);
        Lit135 = syntaxRules5;
        SyntaxRules syntaxRules6 = syntaxRules5;
        SimpleSymbol simpleSymbol65 = simpleSymbol64;
        SyntaxPattern syntaxPattern4 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol66 = (SimpleSymbol) new SimpleSymbol("%test-match-all").readResolve();
        Lit130 = simpleSymbol66;
        SyntaxRules syntaxRules7 = new SyntaxRules(new Object[]{simpleSymbol50}, new SyntaxRule[]{new SyntaxRule(syntaxPattern4, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", new Object[]{simpleSymbol66, simpleSymbol52}, 1)}, 1);
        Lit132 = syntaxRules7;
        SimpleSymbol simpleSymbol67 = (SimpleSymbol) new SimpleSymbol("test-match-nth").readResolve();
        Lit128 = simpleSymbol67;
        SimpleSymbol simpleSymbol68 = simpleSymbol52;
        SimpleSymbol simpleSymbol69 = simpleSymbol50;
        SyntaxPattern syntaxPattern5 = new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1);
        IntNum make = IntNum.make(1);
        Lit13 = make;
        SyntaxRules syntaxRules8 = syntaxRules7;
        Object[] objArr = {simpleSymbol67, PairWithPosition.make(make, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3727384)};
        SyntaxPattern syntaxPattern6 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
        SimpleSymbol simpleSymbol70 = (SimpleSymbol) new SimpleSymbol("%test-match-nth").readResolve();
        Lit127 = simpleSymbol70;
        SyntaxRules syntaxRules9 = new SyntaxRules(new Object[]{simpleSymbol67}, new SyntaxRule[]{new SyntaxRule(syntaxPattern5, "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", objArr, 0), new SyntaxRule(syntaxPattern6, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{simpleSymbol70}, 0)}, 2);
        Lit129 = syntaxRules9;
        SimpleSymbol simpleSymbol71 = (SimpleSymbol) new SimpleSymbol("test-with-runner").readResolve();
        Lit125 = simpleSymbol71;
        SimpleSymbol simpleSymbol72 = simpleSymbol66;
        SimpleSymbol simpleSymbol73 = simpleSymbol67;
        SyntaxRules syntaxRules10 = syntaxRules9;
        SimpleSymbol simpleSymbol74 = simpleSymbol70;
        SimpleSymbol simpleSymbol75 = simpleSymbol55;
        SimpleSymbol simpleSymbol76 = simpleSymbol71;
        SimpleSymbol simpleSymbol77 = simpleSymbol53;
        SimpleSymbol simpleSymbol78 = simpleSymbol60;
        SyntaxRules syntaxRules11 = new SyntaxRules(new Object[]{simpleSymbol71}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014Y\u0011\u0018\u001c\t\u0010\b\u0011\u0018$\b\u0003A\u0011\u0018\u001c\t\u0010\b\r\u000b\u0018,", new Object[]{simpleSymbol29, PairWithPosition.make(PairWithPosition.make(simpleSymbol77, PairWithPosition.make(PairWithPosition.make(simpleSymbol75, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657754), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657754), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657740), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657739), simpleSymbol48, simpleSymbol47, simpleSymbol75, PairWithPosition.make(PairWithPosition.make(simpleSymbol47, PairWithPosition.make(LList.Empty, PairWithPosition.make(PairWithPosition.make(simpleSymbol75, PairWithPosition.make(simpleSymbol77, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674156), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674135), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674135), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674132), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674124), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674124)}, 1)}, 2);
        Lit126 = syntaxRules11;
        SimpleSymbol simpleSymbol79 = (SimpleSymbol) new SimpleSymbol("test-apply").readResolve();
        Lit124 = simpleSymbol79;
        SimpleSymbol simpleSymbol80 = simpleSymbol44;
        SimpleSymbol simpleSymbol81 = (SimpleSymbol) new SimpleSymbol("test-result-alist!").readResolve();
        Lit52 = simpleSymbol81;
        SimpleSymbol simpleSymbol82 = (SimpleSymbol) new SimpleSymbol("%test-error").readResolve();
        Lit115 = simpleSymbol82;
        SyntaxRules syntaxRules12 = syntaxRules11;
        Lit123 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\u0011\u0018,\b\u000b", new Object[]{simpleSymbol46, PairWithPosition.make(PairWithPosition.make(simpleSymbol80, PairWithPosition.make(PairWithPosition.make(simpleSymbol45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514382), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514382), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514379), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514378), simpleSymbol81, simpleSymbol80, simpleSymbol82, Boolean.TRUE}, 0);
        Lit121 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t\u000b\b\u0013", new Object[]{simpleSymbol46, PairWithPosition.make(PairWithPosition.make(simpleSymbol80, PairWithPosition.make(PairWithPosition.make(simpleSymbol45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493902), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493902), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493899), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493898), simpleSymbol81, simpleSymbol80, simpleSymbol82}, 0);
        SimpleSymbol simpleSymbol83 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
        Lit15 = simpleSymbol83;
        SimpleSymbol simpleSymbol84 = (SimpleSymbol) new SimpleSymbol("test-name").readResolve();
        Lit7 = simpleSymbol84;
        SimpleSymbol simpleSymbol85 = simpleSymbol79;
        Lit119 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t\u0013\b\u001b", new Object[]{simpleSymbol46, PairWithPosition.make(simpleSymbol80, PairWithPosition.make(PairWithPosition.make(simpleSymbol45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3469326), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3469326), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3469323), simpleSymbol43, simpleSymbol81, simpleSymbol80, simpleSymbol27, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol84, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3477545), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3477545), simpleSymbol82}, 0);
        SimpleSymbol simpleSymbol86 = (SimpleSymbol) new SimpleSymbol("test-error").readResolve();
        Lit117 = simpleSymbol86;
        Object[] objArr2 = {simpleSymbol82};
        SimpleSymbol simpleSymbol87 = simpleSymbol86;
        SimpleSymbol simpleSymbol88 = simpleSymbol82;
        SimpleSymbol simpleSymbol89 = simpleSymbol75;
        SyntaxPattern syntaxPattern7 = new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\b", new Object[]{Boolean.TRUE}, 2);
        SimpleSymbol simpleSymbol90 = (SimpleSymbol) new SimpleSymbol("%test-on-test-begin").readResolve();
        Lit86 = simpleSymbol90;
        SimpleSymbol simpleSymbol91 = (SimpleSymbol) new SimpleSymbol("test-result-set!").readResolve();
        Lit78 = simpleSymbol91;
        SimpleSymbol simpleSymbol92 = simpleSymbol84;
        SimpleSymbol simpleSymbol93 = simpleSymbol27;
        SimpleSymbol simpleSymbol94 = simpleSymbol42;
        SimpleSymbol simpleSymbol95 = simpleSymbol81;
        SimpleSymbol simpleSymbol96 = simpleSymbol80;
        SimpleSymbol simpleSymbol97 = (SimpleSymbol) new SimpleSymbol("%test-on-test-end").readResolve();
        Lit87 = simpleSymbol97;
        SimpleSymbol simpleSymbol98 = simpleSymbol37;
        SimpleSymbol simpleSymbol99 = simpleSymbol45;
        SimpleSymbol simpleSymbol100 = simpleSymbol28;
        Object[] objArr3 = objArr2;
        SimpleSymbol simpleSymbol101 = simpleSymbol32;
        SimpleSymbol simpleSymbol102 = (SimpleSymbol) new SimpleSymbol("%test-report-result").readResolve();
        Lit83 = simpleSymbol102;
        SimpleSymbol simpleSymbol103 = simpleSymbol91;
        SimpleSymbol simpleSymbol104 = simpleSymbol24;
        SimpleSymbol simpleSymbol105 = simpleSymbol100;
        SimpleSymbol simpleSymbol106 = simpleSymbol22;
        SimpleSymbol simpleSymbol107 = simpleSymbol97;
        SimpleSymbol simpleSymbol108 = simpleSymbol98;
        SimpleSymbol simpleSymbol109 = simpleSymbol90;
        SimpleSymbol simpleSymbol110 = simpleSymbol29;
        SimpleSymbol simpleSymbol111 = simpleSymbol30;
        SimpleSymbol simpleSymbol112 = simpleSymbol102;
        Lit116 = new SyntaxRules(objArr3, new SyntaxRule[]{new SyntaxRule(syntaxPattern7, "\u0001\u0001", "\u0011\u0018\u0004\b)\u0011\u0018\f\b\u00039\u0011\u0018\u0014\t\u0003\u0018\u001cũ\u0011\u0018$\t\u0003\b\u0011\u0018,\u0011\u00184\t\u0010Q\u0011\u0018\u0014\t\u0003\u0011\u0018<\b\u000b\u0018D\b\u0011\u0018L\u0011\u0018T9\u0011\u0018\u0014\t\u0003\u0018\\\u0018d\u0018l", new Object[]{simpleSymbol41, simpleSymbol90, simpleSymbol91, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol94, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223581), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223581), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223596), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223580), simpleSymbol97, simpleSymbol38, simpleSymbol29, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol98, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3239966), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3239966), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3244041), simpleSymbol32, simpleSymbol30, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol100, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252256), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252256), PairWithPosition.make(simpleSymbol101, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252269), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252255), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3256331), PairWithPosition.make(PairWithPosition.make(simpleSymbol102, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3260424), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3260424)}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u00141\b\u0011\u0018\u001c\b\u000b9\u0011\u0018$\t\u0003\u0018,ũ\u0011\u00184\t\u0003\b\u0011\u0018<\u0011\u0018\u0014\t\u0010Q\u0011\u0018$\t\u0003\u0011\u0018D\b\u0013\u0018L\b\u0011\u0018T\u0011\u0018\\9\u0011\u0018$\t\u0003\u0018d\u0018l\u0018t", new Object[]{simpleSymbol26, simpleSymbol90, simpleSymbol29, simpleSymbol24, simpleSymbol91, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol94, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276828), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276828), PairWithPosition.make(simpleSymbol104, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276843), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276827), simpleSymbol97, simpleSymbol38, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol98, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3293213), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3293213), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3297288), simpleSymbol101, simpleSymbol30, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol100, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305503), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305503), PairWithPosition.make(simpleSymbol101, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305516), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305502), PairWithPosition.make(PairWithPosition.make(simpleSymbol41, PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("and").readResolve(), PairWithPosition.make(PairWithPosition.make(simpleSymbol106, PairWithPosition.make(simpleSymbol104, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<gnu.bytecode.ClassType>").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309604), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309601), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309590), PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("gnu.bytecode.ClassType").readResolve(), Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("isSubclass").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313673), PairWithPosition.make(simpleSymbol104, PairWithPosition.make(simpleSymbol111, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313710), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313707), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313672), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313672), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309590), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309585), PairWithPosition.make(PairWithPosition.make(simpleSymbol106, PairWithPosition.make(simpleSymbol101, PairWithPosition.make(simpleSymbol104, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317784), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317781), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317770), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317770), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309584), PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("else").readResolve(), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3321871), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3321865), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3321865), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309584), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309578), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309578), PairWithPosition.make(PairWithPosition.make(simpleSymbol112, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3325959), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3325959)}, 0)}, 3);
        SimpleSymbol simpleSymbol113 = simpleSymbol99;
        SimpleSymbol simpleSymbol114 = simpleSymbol96;
        SimpleSymbol simpleSymbol115 = (SimpleSymbol) new SimpleSymbol("%test-comp2body").readResolve();
        Lit89 = simpleSymbol115;
        SimpleSymbol simpleSymbol116 = (SimpleSymbol) new SimpleSymbol("%test-approximimate=").readResolve();
        Lit91 = simpleSymbol116;
        Lit114 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b#\b\u0011\u0018$\u0011\u0018\u001c)\u0011\u0018,\b\u001b\t\u000b\b\u0013", new Object[]{simpleSymbol46, PairWithPosition.make(PairWithPosition.make(simpleSymbol114, PairWithPosition.make(PairWithPosition.make(simpleSymbol113, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916364), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916364), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916361), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916360), simpleSymbol95, simpleSymbol114, simpleSymbol115, simpleSymbol116}, 0);
        SimpleSymbol simpleSymbol117 = simpleSymbol92;
        Lit112 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b+\b\u0011\u0018<\u0011\u0018$)\u0011\u0018D\b#\t\u0013\b\u001b", new Object[]{simpleSymbol46, PairWithPosition.make(simpleSymbol114, PairWithPosition.make(PairWithPosition.make(simpleSymbol113, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2891788), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2891788), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2891785), simpleSymbol43, simpleSymbol95, simpleSymbol114, simpleSymbol93, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol117, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2900007), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2900007), simpleSymbol115, simpleSymbol116}, 0);
        SimpleSymbol simpleSymbol118 = (SimpleSymbol) new SimpleSymbol("test-approximate").readResolve();
        Lit110 = simpleSymbol118;
        SimpleSymbol simpleSymbol119 = (SimpleSymbol) new SimpleSymbol("test-equal").readResolve();
        Lit108 = simpleSymbol119;
        SimpleSymbol simpleSymbol120 = simpleSymbol118;
        SimpleSymbol simpleSymbol121 = (SimpleSymbol) new SimpleSymbol("test-eq").readResolve();
        Lit106 = simpleSymbol121;
        SimpleSymbol simpleSymbol122 = simpleSymbol119;
        SimpleSymbol simpleSymbol123 = (SimpleSymbol) new SimpleSymbol("test-eqv").readResolve();
        Lit104 = simpleSymbol123;
        SimpleSymbol simpleSymbol124 = simpleSymbol121;
        SimpleSymbol simpleSymbol125 = (SimpleSymbol) new SimpleSymbol("%test-comp1body").readResolve();
        Lit92 = simpleSymbol125;
        SimpleSymbol simpleSymbol126 = simpleSymbol123;
        Lit103 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\b\u000b", new Object[]{simpleSymbol46, PairWithPosition.make(PairWithPosition.make(simpleSymbol114, PairWithPosition.make(PairWithPosition.make(simpleSymbol113, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781198), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781198), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781195), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781194), simpleSymbol95, simpleSymbol114, simpleSymbol125}, 0);
        Lit101 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b\u001b\b\u0011\u0018<\u0011\u0018$\b\u0013", new Object[]{simpleSymbol46, PairWithPosition.make(simpleSymbol114, PairWithPosition.make(PairWithPosition.make(simpleSymbol113, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2756622), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2756622), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2756619), simpleSymbol43, simpleSymbol95, simpleSymbol114, simpleSymbol93, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol117, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2764841), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2764841), simpleSymbol125}, 0);
        SimpleSymbol simpleSymbol127 = (SimpleSymbol) new SimpleSymbol("test-assert").readResolve();
        Lit99 = simpleSymbol127;
        SimpleSymbol simpleSymbol128 = (SimpleSymbol) new SimpleSymbol("%test-end").readResolve();
        Lit69 = simpleSymbol128;
        SimpleSymbol simpleSymbol129 = simpleSymbol127;
        SimpleSymbol simpleSymbol130 = simpleSymbol116;
        Lit98 = new SyntaxTemplate("\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u000b", new Object[]{simpleSymbol128, Boolean.FALSE}, 0);
        Lit96 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u000b\b\u0013", new Object[]{simpleSymbol128}, 0);
        SimpleSymbol simpleSymbol131 = (SimpleSymbol) new SimpleSymbol("test-end").readResolve();
        Lit94 = simpleSymbol131;
        Object[] objArr4 = {simpleSymbol125};
        SimpleSymbol simpleSymbol132 = simpleSymbol125;
        SimpleSymbol simpleSymbol133 = simpleSymbol128;
        String str = "\f\u0018\f\u0007\f\u000f\b";
        SimpleSymbol simpleSymbol134 = simpleSymbol113;
        SyntaxPattern syntaxPattern8 = new SyntaxPattern(str, new Object[0], 2);
        SimpleSymbol simpleSymbol135 = simpleSymbol131;
        SimpleSymbol simpleSymbol136 = (SimpleSymbol) new SimpleSymbol("%test-evaluate-with-catch").readResolve();
        Lit84 = simpleSymbol136;
        SimpleSymbol simpleSymbol137 = simpleSymbol117;
        SimpleSymbol simpleSymbol138 = simpleSymbol114;
        SimpleSymbol simpleSymbol139 = simpleSymbol108;
        String str2 = str;
        SimpleSymbol simpleSymbol140 = simpleSymbol111;
        SimpleSymbol simpleSymbol141 = simpleSymbol20;
        SyntaxRules syntaxRules13 = new SyntaxRules(objArr4, new SyntaxRule[]{new SyntaxRule(syntaxPattern8, "\u0001\u0001", "\u0011\u0018\u0004\t\u0010ű\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u0004\t\u0010\b\u0011\u0018\u0004Q\b\u0011\u0018\u001c\b\u0011\u0018$\b\u000b9\u0011\u0018,\t\u0003\u00184\b\u0011\u0018<\t\u0003\u0018D\u0018L", new Object[]{simpleSymbol110, simpleSymbol26, simpleSymbol109, simpleSymbol20, simpleSymbol136, simpleSymbol103, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol139, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666526), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666526), PairWithPosition.make(simpleSymbol141, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666539), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666525), simpleSymbol107, PairWithPosition.make(simpleSymbol141, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2670622), PairWithPosition.make(PairWithPosition.make(simpleSymbol112, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2674696), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2674696)}, 0)}, 2);
        Lit93 = syntaxRules13;
        SyntaxRules syntaxRules14 = syntaxRules13;
        SimpleSymbol simpleSymbol142 = simpleSymbol115;
        SimpleSymbol simpleSymbol143 = simpleSymbol101;
        SimpleSymbol simpleSymbol144 = simpleSymbol18;
        SyntaxRules syntaxRules15 = new SyntaxRules(new Object[]{simpleSymbol115}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0010Ǳ\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u00041\b\u0011\u0018\u001c\b\u00139\u0011\u0018$\t\u0003\u0018,\b\u0011\u0018\u0004Q\b\u0011\u00184\b\u0011\u0018<\b\u001b9\u0011\u0018$\t\u0003\u0018D\b\u0011\u0018L\t\u0003\b\t\u000b\u0018T\u0018\\", new Object[]{simpleSymbol110, simpleSymbol26, simpleSymbol109, simpleSymbol18, simpleSymbol103, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("expected-value").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592794), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592794), PairWithPosition.make(simpleSymbol144, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592809), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592793), simpleSymbol141, simpleSymbol136, PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol139, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2600988), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2600988), PairWithPosition.make(simpleSymbol141, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2601001), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2600987), simpleSymbol107, PairWithPosition.make(simpleSymbol144, PairWithPosition.make(simpleSymbol141, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2605094), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2605090), PairWithPosition.make(PairWithPosition.make(simpleSymbol112, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2609158), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2609158)}, 0)}, 4);
        Lit90 = syntaxRules15;
        SimpleSymbol simpleSymbol145 = (SimpleSymbol) new SimpleSymbol("test-runner-test-name").readResolve();
        Lit88 = simpleSymbol145;
        SimpleSymbol simpleSymbol146 = simpleSymbol89;
        SyntaxRules syntaxRules16 = syntaxRules15;
        SimpleSymbol simpleSymbol147 = simpleSymbol105;
        SimpleSymbol simpleSymbol148 = simpleSymbol145;
        SimpleSymbol simpleSymbol149 = simpleSymbol143;
        SimpleSymbol simpleSymbol150 = simpleSymbol103;
        SimpleSymbol simpleSymbol151 = simpleSymbol136;
        SyntaxRules syntaxRules17 = new SyntaxRules(new Object[]{simpleSymbol136}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", new Object[]{simpleSymbol38, PairWithPosition.make(PairWithPosition.make(simpleSymbol149, PairWithPosition.make(simpleSymbol140, PairWithPosition.make(PairWithPosition.make(simpleSymbol150, PairWithPosition.make(PairWithPosition.make(simpleSymbol146, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347035), PairWithPosition.make(PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol147, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347058), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347058), PairWithPosition.make(simpleSymbol149, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347071), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347057), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347035), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347017), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2351113), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347017), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2342921), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2342917), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2342917)}, 0)}, 1);
        Lit85 = syntaxRules17;
        SimpleSymbol simpleSymbol152 = (SimpleSymbol) new SimpleSymbol("test-passed?").readResolve();
        Lit82 = simpleSymbol152;
        SimpleSymbol simpleSymbol153 = (SimpleSymbol) new SimpleSymbol("test-result-kind").readResolve();
        Lit81 = simpleSymbol153;
        SimpleSymbol simpleSymbol154 = (SimpleSymbol) new SimpleSymbol("test-result-remove").readResolve();
        Lit80 = simpleSymbol154;
        SimpleSymbol simpleSymbol155 = (SimpleSymbol) new SimpleSymbol("test-result-clear").readResolve();
        Lit79 = simpleSymbol155;
        SimpleSymbol simpleSymbol156 = (SimpleSymbol) new SimpleSymbol("test-on-test-end-simple").readResolve();
        Lit77 = simpleSymbol156;
        SimpleSymbol simpleSymbol157 = (SimpleSymbol) new SimpleSymbol("test-result-ref").readResolve();
        Lit75 = simpleSymbol157;
        SyntaxRules syntaxRules18 = syntaxRules17;
        SimpleSymbol simpleSymbol158 = simpleSymbol152;
        SimpleSymbol simpleSymbol159 = simpleSymbol112;
        String str3 = str2;
        SyntaxPattern syntaxPattern9 = new SyntaxPattern(str3, new Object[0], 2);
        SimpleSymbol simpleSymbol160 = simpleSymbol153;
        SimpleSymbol simpleSymbol161 = simpleSymbol154;
        Object[] objArr5 = {simpleSymbol157, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1933348)};
        SyntaxPattern syntaxPattern10 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
        SimpleSymbol simpleSymbol162 = (SimpleSymbol) new SimpleSymbol("test-result-alist").readResolve();
        Lit51 = simpleSymbol162;
        SimpleSymbol simpleSymbol163 = simpleSymbol155;
        SimpleSymbol simpleSymbol164 = simpleSymbol150;
        SimpleSymbol simpleSymbol165 = simpleSymbol156;
        SyntaxRules syntaxRules19 = new SyntaxRules(new Object[]{simpleSymbol157}, new SyntaxRule[]{new SyntaxRule(syntaxPattern9, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\t\u000b\u0018\f", objArr5, 0), new SyntaxRule(syntaxPattern10, "\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0011\u0018\u0014\t\u000b\b\u0011\u0018\u001c\b\u0003\b\u0011\u0018$\u0011\u0018\f\u0011\u0018,\b\u0013", new Object[]{simpleSymbol110, simpleSymbol16, (SimpleSymbol) new SimpleSymbol("assq").readResolve(), simpleSymbol162, simpleSymbol26, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("cdr").readResolve(), PairWithPosition.make(simpleSymbol16, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1945619), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1945614)}, 0)}, 3);
        Lit76 = syntaxRules19;
        SimpleSymbol simpleSymbol166 = (SimpleSymbol) new SimpleSymbol("test-on-test-begin-simple").readResolve();
        Lit74 = simpleSymbol166;
        SimpleSymbol simpleSymbol167 = (SimpleSymbol) new SimpleSymbol("test-group-with-cleanup").readResolve();
        Lit72 = simpleSymbol167;
        SyntaxRules syntaxRules20 = syntaxRules19;
        SyntaxPattern syntaxPattern11 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
        SimpleSymbol simpleSymbol168 = (SimpleSymbol) new SimpleSymbol("test-group").readResolve();
        Lit70 = simpleSymbol168;
        SimpleSymbol simpleSymbol169 = simpleSymbol157;
        SimpleSymbol simpleSymbol170 = simpleSymbol166;
        SimpleSymbol simpleSymbol171 = simpleSymbol162;
        SimpleSymbol simpleSymbol172 = simpleSymbol47;
        SyntaxRules syntaxRules21 = new SyntaxRules(new Object[]{simpleSymbol167}, new SyntaxRule[]{new SyntaxRule(syntaxPattern11, "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u00149\u0011\u0018\u001c\t\u0010\b\u000b\b\u0011\u0018\u001c\t\u0010\b\u0013", new Object[]{simpleSymbol168, simpleSymbol48, PairWithPosition.make(simpleSymbol172, PairWithPosition.make(LList.Empty, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1826831), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1826828), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1826820), simpleSymbol172}, 0), new SyntaxRule(new SyntaxPattern(str3, new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\b\u000b", new Object[]{simpleSymbol167, Boolean.FALSE}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f#", new Object[0], 5), "\u0001\u0001\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u00039\u0011\u0018\f\t\u000b\b\u0013\t\u001b\"", new Object[]{simpleSymbol167, (SimpleSymbol) new SimpleSymbol("begin").readResolve()}, 0)}, 5);
        Lit73 = syntaxRules21;
        SyntaxPattern syntaxPattern12 = new SyntaxPattern("\f\u0018\f\u0007\u000b", new Object[0], 2);
        SimpleSymbol simpleSymbol173 = simpleSymbol138;
        SimpleSymbol simpleSymbol174 = simpleSymbol137;
        SimpleSymbol simpleSymbol175 = (SimpleSymbol) new SimpleSymbol("%test-should-execute").readResolve();
        Lit62 = simpleSymbol175;
        SimpleSymbol simpleSymbol176 = simpleSymbol167;
        SyntaxRules syntaxRules22 = new SyntaxRules(new Object[]{simpleSymbol168}, new SyntaxRule[]{new SyntaxRule(syntaxPattern12, "\u0001\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\b\u0011\u0018,\u0011\u00184\b\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018LY\u0011\u0018T\t\u0010\b\u0011\u0018\\\b\u00031\u0011\u0018T\t\u0010\n\b\u0011\u0018T\t\u0010\b\u0011\u0018d\b\u0003", new Object[]{simpleSymbol110, PairWithPosition.make(PairWithPosition.make(simpleSymbol173, PairWithPosition.make(PairWithPosition.make(simpleSymbol146, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769487), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769487), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769484), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769483), simpleSymbol95, simpleSymbol173, (SimpleSymbol) new SimpleSymbol("list").readResolve(), simpleSymbol93, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol174, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1777707), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1777707), simpleSymbol26, PairWithPosition.make(simpleSymbol175, PairWithPosition.make(simpleSymbol173, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1781794), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1781772), simpleSymbol48, simpleSymbol172, (SimpleSymbol) new SimpleSymbol("test-begin").readResolve(), simpleSymbol135}, 0)}, 2);
        Lit71 = syntaxRules22;
        SimpleSymbol simpleSymbol177 = (SimpleSymbol) new SimpleSymbol("test-on-final-simple").readResolve();
        Lit68 = simpleSymbol177;
        SimpleSymbol simpleSymbol178 = (SimpleSymbol) new SimpleSymbol("test-on-bad-end-name-simple").readResolve();
        Lit67 = simpleSymbol178;
        SimpleSymbol simpleSymbol179 = (SimpleSymbol) new SimpleSymbol("test-on-bad-count-simple").readResolve();
        Lit66 = simpleSymbol179;
        SimpleSymbol simpleSymbol180 = (SimpleSymbol) new SimpleSymbol("test-on-group-end-simple").readResolve();
        Lit65 = simpleSymbol180;
        SimpleSymbol simpleSymbol181 = (SimpleSymbol) new SimpleSymbol("test-on-group-begin-simple").readResolve();
        Lit64 = simpleSymbol181;
        SimpleSymbol simpleSymbol182 = (SimpleSymbol) new SimpleSymbol("%test-begin").readResolve();
        Lit63 = simpleSymbol182;
        SimpleSymbol simpleSymbol183 = (SimpleSymbol) new SimpleSymbol("test-runner-create").readResolve();
        Lit61 = simpleSymbol183;
        SyntaxRules syntaxRules23 = syntaxRules21;
        SimpleSymbol simpleSymbol184 = (SimpleSymbol) new SimpleSymbol("test-runner-simple").readResolve();
        Lit59 = simpleSymbol184;
        SyntaxRules syntaxRules24 = syntaxRules22;
        SimpleSymbol simpleSymbol185 = (SimpleSymbol) new SimpleSymbol("test-runner-null").readResolve();
        Lit58 = simpleSymbol185;
        SimpleSymbol simpleSymbol186 = simpleSymbol168;
        SimpleSymbol simpleSymbol187 = (SimpleSymbol) new SimpleSymbol("%test-null-callback").readResolve();
        Lit57 = simpleSymbol187;
        SimpleSymbol simpleSymbol188 = simpleSymbol177;
        SimpleSymbol simpleSymbol189 = (SimpleSymbol) new SimpleSymbol("test-runner-group-path").readResolve();
        Lit56 = simpleSymbol189;
        SimpleSymbol simpleSymbol190 = simpleSymbol178;
        SimpleSymbol simpleSymbol191 = (SimpleSymbol) new SimpleSymbol("test-runner-reset").readResolve();
        Lit55 = simpleSymbol191;
        SimpleSymbol simpleSymbol192 = simpleSymbol179;
        SimpleSymbol simpleSymbol193 = (SimpleSymbol) new SimpleSymbol("test-runner-aux-value!").readResolve();
        Lit54 = simpleSymbol193;
        SimpleSymbol simpleSymbol194 = simpleSymbol180;
        SimpleSymbol simpleSymbol195 = (SimpleSymbol) new SimpleSymbol("test-runner-aux-value").readResolve();
        Lit53 = simpleSymbol195;
        SimpleSymbol simpleSymbol196 = simpleSymbol181;
        SimpleSymbol simpleSymbol197 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-end-name!").readResolve();
        Lit50 = simpleSymbol197;
        SimpleSymbol simpleSymbol198 = simpleSymbol182;
        SimpleSymbol simpleSymbol199 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-end-name").readResolve();
        Lit49 = simpleSymbol199;
        SimpleSymbol simpleSymbol200 = simpleSymbol175;
        SimpleSymbol simpleSymbol201 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-count!").readResolve();
        Lit48 = simpleSymbol201;
        SimpleSymbol simpleSymbol202 = simpleSymbol183;
        SimpleSymbol simpleSymbol203 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-count").readResolve();
        Lit47 = simpleSymbol203;
        SimpleSymbol simpleSymbol204 = simpleSymbol184;
        SimpleSymbol simpleSymbol205 = (SimpleSymbol) new SimpleSymbol("test-runner-on-final!").readResolve();
        Lit46 = simpleSymbol205;
        SimpleSymbol simpleSymbol206 = simpleSymbol185;
        SimpleSymbol simpleSymbol207 = (SimpleSymbol) new SimpleSymbol("test-runner-on-final").readResolve();
        Lit45 = simpleSymbol207;
        SimpleSymbol simpleSymbol208 = simpleSymbol187;
        SimpleSymbol simpleSymbol209 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-end!").readResolve();
        Lit44 = simpleSymbol209;
        SimpleSymbol simpleSymbol210 = simpleSymbol189;
        SimpleSymbol simpleSymbol211 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-end").readResolve();
        Lit43 = simpleSymbol211;
        SimpleSymbol simpleSymbol212 = simpleSymbol191;
        SimpleSymbol simpleSymbol213 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-begin!").readResolve();
        Lit42 = simpleSymbol213;
        SimpleSymbol simpleSymbol214 = simpleSymbol193;
        SimpleSymbol simpleSymbol215 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-begin").readResolve();
        Lit41 = simpleSymbol215;
        SimpleSymbol simpleSymbol216 = simpleSymbol195;
        SimpleSymbol simpleSymbol217 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-end!").readResolve();
        Lit40 = simpleSymbol217;
        SimpleSymbol simpleSymbol218 = simpleSymbol197;
        SimpleSymbol simpleSymbol219 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-end").readResolve();
        Lit39 = simpleSymbol219;
        SimpleSymbol simpleSymbol220 = simpleSymbol199;
        SimpleSymbol simpleSymbol221 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-begin!").readResolve();
        Lit38 = simpleSymbol221;
        SimpleSymbol simpleSymbol222 = simpleSymbol201;
        SimpleSymbol simpleSymbol223 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-begin").readResolve();
        Lit37 = simpleSymbol223;
        SimpleSymbol simpleSymbol224 = simpleSymbol203;
        SimpleSymbol simpleSymbol225 = (SimpleSymbol) new SimpleSymbol("test-runner-group-stack!").readResolve();
        Lit36 = simpleSymbol225;
        SimpleSymbol simpleSymbol226 = simpleSymbol205;
        SimpleSymbol simpleSymbol227 = (SimpleSymbol) new SimpleSymbol("test-runner-group-stack").readResolve();
        Lit35 = simpleSymbol227;
        SimpleSymbol simpleSymbol228 = simpleSymbol207;
        SimpleSymbol simpleSymbol229 = (SimpleSymbol) new SimpleSymbol("test-runner-skip-count!").readResolve();
        Lit30 = simpleSymbol229;
        SimpleSymbol simpleSymbol230 = simpleSymbol209;
        SimpleSymbol simpleSymbol231 = (SimpleSymbol) new SimpleSymbol("test-runner-skip-count").readResolve();
        Lit29 = simpleSymbol231;
        SimpleSymbol simpleSymbol232 = simpleSymbol211;
        SimpleSymbol simpleSymbol233 = (SimpleSymbol) new SimpleSymbol("test-runner-xfail-count!").readResolve();
        Lit28 = simpleSymbol233;
        SimpleSymbol simpleSymbol234 = simpleSymbol213;
        SimpleSymbol simpleSymbol235 = (SimpleSymbol) new SimpleSymbol("test-runner-xfail-count").readResolve();
        Lit27 = simpleSymbol235;
        SimpleSymbol simpleSymbol236 = simpleSymbol215;
        SimpleSymbol simpleSymbol237 = (SimpleSymbol) new SimpleSymbol("test-runner-xpass-count!").readResolve();
        Lit26 = simpleSymbol237;
        SimpleSymbol simpleSymbol238 = simpleSymbol217;
        SimpleSymbol simpleSymbol239 = (SimpleSymbol) new SimpleSymbol("test-runner-xpass-count").readResolve();
        Lit25 = simpleSymbol239;
        SimpleSymbol simpleSymbol240 = simpleSymbol219;
        SimpleSymbol simpleSymbol241 = (SimpleSymbol) new SimpleSymbol("test-runner-fail-count!").readResolve();
        Lit24 = simpleSymbol241;
        SimpleSymbol simpleSymbol242 = simpleSymbol221;
        SimpleSymbol simpleSymbol243 = (SimpleSymbol) new SimpleSymbol("test-runner-fail-count").readResolve();
        Lit23 = simpleSymbol243;
        SimpleSymbol simpleSymbol244 = simpleSymbol223;
        SimpleSymbol simpleSymbol245 = (SimpleSymbol) new SimpleSymbol("test-runner-pass-count!").readResolve();
        Lit22 = simpleSymbol245;
        SimpleSymbol simpleSymbol246 = simpleSymbol225;
        SimpleSymbol simpleSymbol247 = (SimpleSymbol) new SimpleSymbol("test-runner-pass-count").readResolve();
        Lit21 = simpleSymbol247;
        SimpleSymbol simpleSymbol248 = simpleSymbol227;
        SimpleSymbol simpleSymbol249 = (SimpleSymbol) new SimpleSymbol("test-runner?").readResolve();
        Lit20 = simpleSymbol249;
        SimpleSymbol simpleSymbol250 = simpleSymbol229;
        SimpleSymbol simpleSymbol251 = simpleSymbol231;
        SimpleSymbol simpleSymbol252 = simpleSymbol233;
        SimpleSymbol simpleSymbol253 = simpleSymbol235;
        SimpleSymbol simpleSymbol254 = simpleSymbol134;
        SimpleSymbol simpleSymbol255 = simpleSymbol237;
        Lit19 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t#\t\u000b\b\u0013", new Object[]{simpleSymbol46, PairWithPosition.make(PairWithPosition.make(simpleSymbol173, PairWithPosition.make(PairWithPosition.make(simpleSymbol254, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834444), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834444), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834441), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834440), simpleSymbol95, simpleSymbol173, simpleSymbol142}, 0);
        Lit17 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t+\t\u0013\b\u001b", new Object[]{simpleSymbol46, PairWithPosition.make(simpleSymbol173, PairWithPosition.make(PairWithPosition.make(simpleSymbol254, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2809868), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2809868), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2809865), simpleSymbol43, simpleSymbol95, simpleSymbol173, simpleSymbol93, PairWithPosition.make(simpleSymbol83, PairWithPosition.make(simpleSymbol174, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2818087), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2818087), simpleSymbol142}, 0);
        SimpleSymbol simpleSymbol256 = (SimpleSymbol) new SimpleSymbol("fail").readResolve();
        Lit14 = simpleSymbol256;
        SimpleSymbol simpleSymbol257 = (SimpleSymbol) new SimpleSymbol("pass").readResolve();
        Lit12 = simpleSymbol257;
        SimpleSymbol simpleSymbol258 = (SimpleSymbol) new SimpleSymbol("xpass").readResolve();
        Lit9 = simpleSymbol258;
        Lit11 = PairWithPosition.make(simpleSymbol257, PairWithPosition.make(simpleSymbol258, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2220088), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2220082);
        SimpleSymbol simpleSymbol259 = (SimpleSymbol) new SimpleSymbol("source-file").readResolve();
        Lit4 = simpleSymbol259;
        SimpleSymbol simpleSymbol260 = (SimpleSymbol) new SimpleSymbol("source-line").readResolve();
        Lit5 = simpleSymbol260;
        SimpleSymbol simpleSymbol261 = (SimpleSymbol) new SimpleSymbol("source-form").readResolve();
        Lit6 = simpleSymbol261;
        Lit10 = PairWithPosition.make(simpleSymbol174, PairWithPosition.make(simpleSymbol259, PairWithPosition.make(simpleSymbol260, PairWithPosition.make(simpleSymbol261, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072618), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072606), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072594), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072583);
        Lit8 = PairWithPosition.make(simpleSymbol256, PairWithPosition.make(simpleSymbol258, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1966107), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1966101);
        testing testing = new testing();
        $instance = testing;
        test$Mnrunner$Qu = new ModuleMethod(testing, 12, simpleSymbol249, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnpass$Mncount = new ModuleMethod(testing, 13, simpleSymbol247, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnpass$Mncount$Ex = new ModuleMethod(testing, 14, simpleSymbol245, 8194);
        test$Mnrunner$Mnfail$Mncount = new ModuleMethod(testing, 15, simpleSymbol243, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnfail$Mncount$Ex = new ModuleMethod(testing, 16, simpleSymbol241, 8194);
        test$Mnrunner$Mnxpass$Mncount = new ModuleMethod(testing, 17, simpleSymbol239, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnxpass$Mncount$Ex = new ModuleMethod(testing, 18, simpleSymbol255, 8194);
        test$Mnrunner$Mnxfail$Mncount = new ModuleMethod(testing, 19, simpleSymbol253, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnxfail$Mncount$Ex = new ModuleMethod(testing, 20, simpleSymbol252, 8194);
        test$Mnrunner$Mnskip$Mncount = new ModuleMethod(testing, 21, simpleSymbol251, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnskip$Mncount$Ex = new ModuleMethod(testing, 22, simpleSymbol250, 8194);
        $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist = new ModuleMethod(testing, 23, simpleSymbol78, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex = new ModuleMethod(testing, 24, simpleSymbol61, 8194);
        $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist = new ModuleMethod(testing, 25, simpleSymbol56, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex = new ModuleMethod(testing, 26, simpleSymbol51, 8194);
        test$Mnrunner$Mngroup$Mnstack = new ModuleMethod(testing, 27, simpleSymbol248, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mngroup$Mnstack$Ex = new ModuleMethod(testing, 28, simpleSymbol246, 8194);
        test$Mnrunner$Mnon$Mntest$Mnbegin = new ModuleMethod(testing, 29, simpleSymbol244, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mntest$Mnbegin$Ex = new ModuleMethod(testing, 30, simpleSymbol242, 8194);
        test$Mnrunner$Mnon$Mntest$Mnend = new ModuleMethod(testing, 31, simpleSymbol240, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mntest$Mnend$Ex = new ModuleMethod(testing, 32, simpleSymbol238, 8194);
        test$Mnrunner$Mnon$Mngroup$Mnbegin = new ModuleMethod(testing, 33, simpleSymbol236, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex = new ModuleMethod(testing, 34, simpleSymbol234, 8194);
        test$Mnrunner$Mnon$Mngroup$Mnend = new ModuleMethod(testing, 35, simpleSymbol232, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mngroup$Mnend$Ex = new ModuleMethod(testing, 36, simpleSymbol230, 8194);
        test$Mnrunner$Mnon$Mnfinal = new ModuleMethod(testing, 37, simpleSymbol228, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnfinal$Ex = new ModuleMethod(testing, 38, simpleSymbol226, 8194);
        test$Mnrunner$Mnon$Mnbad$Mncount = new ModuleMethod(testing, 39, simpleSymbol224, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnbad$Mncount$Ex = new ModuleMethod(testing, 40, simpleSymbol222, 8194);
        test$Mnrunner$Mnon$Mnbad$Mnend$Mnname = new ModuleMethod(testing, 41, simpleSymbol220, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex = new ModuleMethod(testing, 42, simpleSymbol218, 8194);
        test$Mnresult$Mnalist = new ModuleMethod(testing, 43, simpleSymbol171, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnalist$Ex = new ModuleMethod(testing, 44, simpleSymbol95, 8194);
        test$Mnrunner$Mnaux$Mnvalue = new ModuleMethod(testing, 45, simpleSymbol216, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnaux$Mnvalue$Ex = new ModuleMethod(testing, 46, simpleSymbol214, 8194);
        test$Mnrunner$Mnreset = new ModuleMethod(testing, 47, simpleSymbol212, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mngroup$Mnpath = new ModuleMethod(testing, 48, simpleSymbol210, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pctest$Mnnull$Mncallback = new ModuleMethod(testing, 49, simpleSymbol208, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ModuleMethod moduleMethod = new ModuleMethod(testing, 50, (Object) null, 12291);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:182");
        lambda$Fn1 = moduleMethod;
        ModuleMethod moduleMethod2 = new ModuleMethod(testing, 51, (Object) null, 12291);
        moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:187");
        lambda$Fn2 = moduleMethod2;
        ModuleMethod moduleMethod3 = new ModuleMethod(testing, 52, (Object) null, 12291);
        moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:188");
        lambda$Fn3 = moduleMethod3;
        test$Mnrunner$Mnnull = new ModuleMethod(testing, 53, simpleSymbol206, 0);
        test$Mnrunner$Mnsimple = new ModuleMethod(testing, 54, simpleSymbol204, 0);
        test$Mnrunner$Mnget = new ModuleMethod(testing, 55, simpleSymbol254, 0);
        test$Mnrunner$Mncreate = new ModuleMethod(testing, 56, simpleSymbol202, 0);
        $Prvt$$Pctest$Mnshould$Mnexecute = new ModuleMethod(testing, 57, simpleSymbol200, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pctest$Mnbegin = new ModuleMethod(testing, 58, simpleSymbol198, 8194);
        test$Mnon$Mngroup$Mnbegin$Mnsimple = new ModuleMethod(testing, 59, simpleSymbol196, 12291);
        test$Mnon$Mngroup$Mnend$Mnsimple = new ModuleMethod(testing, 60, simpleSymbol194, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnon$Mnbad$Mncount$Mnsimple = new ModuleMethod(testing, 61, simpleSymbol192, 12291);
        test$Mnon$Mnbad$Mnend$Mnname$Mnsimple = new ModuleMethod(testing, 62, simpleSymbol190, 12291);
        test$Mnon$Mnfinal$Mnsimple = new ModuleMethod(testing, 63, simpleSymbol188, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnend = new ModuleMethod(testing, 64, simpleSymbol133, 8194);
        $Prvt$test$Mngroup = Macro.make(simpleSymbol186, syntaxRules24, testing);
        test$Mngroup$Mnwith$Mncleanup = Macro.make(simpleSymbol176, syntaxRules23, testing);
        test$Mnon$Mntest$Mnbegin$Mnsimple = new ModuleMethod(testing, 65, simpleSymbol170, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnref = Macro.make(simpleSymbol169, syntaxRules20, testing);
        test$Mnon$Mntest$Mnend$Mnsimple = new ModuleMethod(testing, 66, simpleSymbol165, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnset$Ex = new ModuleMethod(testing, 67, simpleSymbol164, 12291);
        test$Mnresult$Mnclear = new ModuleMethod(testing, 68, simpleSymbol163, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnremove = new ModuleMethod(testing, 69, simpleSymbol161, 8194);
        test$Mnresult$Mnkind = new ModuleMethod(testing, 70, simpleSymbol160, -4096);
        test$Mnpassed$Qu = new ModuleMethod(testing, 71, simpleSymbol158, -4096);
        $Prvt$$Pctest$Mnreport$Mnresult = new ModuleMethod(testing, 72, simpleSymbol159, 0);
        $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch = Macro.make(simpleSymbol151, syntaxRules18, testing);
        $Prvt$$Pctest$Mnon$Mntest$Mnbegin = new ModuleMethod(testing, 73, simpleSymbol109, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnon$Mntest$Mnend = new ModuleMethod(testing, 74, simpleSymbol107, 8194);
        test$Mnrunner$Mntest$Mnname = new ModuleMethod(testing, 75, simpleSymbol148, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mncomp2body = Macro.make(simpleSymbol142, syntaxRules16, testing);
        $Prvt$$Pctest$Mnapproximimate$Eq = new ModuleMethod(testing, 76, simpleSymbol130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mncomp1body = Macro.make(simpleSymbol132, syntaxRules14, testing);
        ModuleMethod moduleMethod4 = new ModuleMethod(testing, 77, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:660");
        test$Mnend = Macro.make(simpleSymbol135, moduleMethod4, testing);
        ModuleMethod moduleMethod5 = new ModuleMethod(testing, 78, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:669");
        test$Mnassert = Macro.make(simpleSymbol129, moduleMethod5, testing);
        ModuleMethod moduleMethod6 = new ModuleMethod(testing, 79, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:696");
        test$Mneqv = Macro.make(simpleSymbol126, moduleMethod6, testing);
        ModuleMethod moduleMethod7 = new ModuleMethod(testing, 80, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod7.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:698");
        test$Mneq = Macro.make(simpleSymbol124, moduleMethod7, testing);
        ModuleMethod moduleMethod8 = new ModuleMethod(testing, 81, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod8.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:700");
        test$Mnequal = Macro.make(simpleSymbol122, moduleMethod8, testing);
        ModuleMethod moduleMethod9 = new ModuleMethod(testing, 82, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod9.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:702");
        test$Mnapproximate = Macro.make(simpleSymbol120, moduleMethod9, testing);
        $Prvt$$Pctest$Mnerror = Macro.make(simpleSymbol88, procedure, testing);
        ModuleMethod moduleMethod10 = new ModuleMethod(testing, 83, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod10.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:843");
        test$Mnerror = Macro.make(simpleSymbol87, moduleMethod10, testing);
        test$Mnapply = new ModuleMethod(testing, 84, simpleSymbol85, -4095);
        test$Mnwith$Mnrunner = Macro.make(simpleSymbol76, syntaxRules12, testing);
        $Prvt$$Pctest$Mnmatch$Mnnth = new ModuleMethod(testing, 85, simpleSymbol74, 8194);
        test$Mnmatch$Mnnth = Macro.make(simpleSymbol73, syntaxRules10, testing);
        $Prvt$$Pctest$Mnmatch$Mnall = new ModuleMethod(testing, 86, simpleSymbol72, -4096);
        test$Mnmatch$Mnall = Macro.make(simpleSymbol69, syntaxRules8, testing);
        $Prvt$$Pctest$Mnmatch$Mnany = new ModuleMethod(testing, 87, simpleSymbol65, -4096);
        test$Mnmatch$Mnany = Macro.make(simpleSymbol63, syntaxRules6, testing);
        $Prvt$$Pctest$Mnas$Mnspecifier = new ModuleMethod(testing, 88, simpleSymbol68, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnskip = Macro.make(simpleSymbol58, syntaxRules4, testing);
        test$Mnexpect$Mnfail = Macro.make(simpleSymbol40, syntaxRules2, testing);
        test$Mnmatch$Mnname = new ModuleMethod(testing, 89, simpleSymbol36, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnread$Mneval$Mnstring = new ModuleMethod(testing, 90, simpleSymbol34, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        testing.run();
    }

    static test$Mnrunner $PcTestRunnerAlloc() {
        return new test$Mnrunner();
    }

    public static boolean isTestRunner(Object obj) {
        return obj instanceof test$Mnrunner;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 12:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 13:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 15:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 17:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 21:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 25:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 33:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 37:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 39:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 41:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 43:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 45:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 57:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 60:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 63:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 65:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 66:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 68:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 73:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 75:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 76:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 77:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 78:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 79:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 80:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 81:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 82:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 83:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 88:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 89:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 90:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object testRunnerPassCount(test$Mnrunner obj) {
        return obj.pass$Mncount;
    }

    public static void testRunnerPassCount$Ex(test$Mnrunner obj, Object value) {
        obj.pass$Mncount = value;
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 14:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 16:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 18:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 20:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 22:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 24:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 26:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 28:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 30:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 32:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 34:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 36:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 38:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 40:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 42:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 44:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 46:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 58:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 64:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 69:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 74:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 85:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object testRunnerFailCount(test$Mnrunner obj) {
        return obj.fail$Mncount;
    }

    public static void testRunnerFailCount$Ex(test$Mnrunner obj, Object value) {
        obj.fail$Mncount = value;
    }

    public static Object testRunnerXpassCount(test$Mnrunner obj) {
        return obj.xpass$Mncount;
    }

    public static void testRunnerXpassCount$Ex(test$Mnrunner obj, Object value) {
        obj.xpass$Mncount = value;
    }

    public static Object testRunnerXfailCount(test$Mnrunner obj) {
        return obj.xfail$Mncount;
    }

    public static void testRunnerXfailCount$Ex(test$Mnrunner obj, Object value) {
        obj.xfail$Mncount = value;
    }

    public static Object testRunnerSkipCount(test$Mnrunner obj) {
        return obj.skip$Mncount;
    }

    public static void testRunnerSkipCount$Ex(test$Mnrunner obj, Object value) {
        obj.skip$Mncount = value;
    }

    public static Object $PcTestRunnerSkipList(test$Mnrunner obj) {
        return obj.skip$Mnlist;
    }

    public static void $PcTestRunnerSkipList$Ex(test$Mnrunner obj, Object value) {
        obj.skip$Mnlist = value;
    }

    public static Object $PcTestRunnerFailList(test$Mnrunner obj) {
        return obj.fail$Mnlist;
    }

    public static void $PcTestRunnerFailList$Ex(test$Mnrunner obj, Object value) {
        obj.fail$Mnlist = value;
    }

    static Object $PcTestRunnerRunList(test$Mnrunner obj) {
        return obj.run$Mnlist;
    }

    static void $PcTestRunnerRunList$Ex(test$Mnrunner obj, Object value) {
        obj.run$Mnlist = value;
    }

    static Object $PcTestRunnerSkipSave(test$Mnrunner obj) {
        return obj.skip$Mnsave;
    }

    static void $PcTestRunnerSkipSave$Ex(test$Mnrunner obj, Object value) {
        obj.skip$Mnsave = value;
    }

    static Object $PcTestRunnerFailSave(test$Mnrunner obj) {
        return obj.fail$Mnsave;
    }

    static void $PcTestRunnerFailSave$Ex(test$Mnrunner obj, Object value) {
        obj.fail$Mnsave = value;
    }

    public static Object testRunnerGroupStack(test$Mnrunner obj) {
        return obj.group$Mnstack;
    }

    public static void testRunnerGroupStack$Ex(test$Mnrunner obj, Object value) {
        obj.group$Mnstack = value;
    }

    public static Object testRunnerOnTestBegin(test$Mnrunner obj) {
        return obj.on$Mntest$Mnbegin;
    }

    public static void testRunnerOnTestBegin$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mntest$Mnbegin = value;
    }

    public static Object testRunnerOnTestEnd(test$Mnrunner obj) {
        return obj.on$Mntest$Mnend;
    }

    public static void testRunnerOnTestEnd$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mntest$Mnend = value;
    }

    public static Object testRunnerOnGroupBegin(test$Mnrunner obj) {
        return obj.on$Mngroup$Mnbegin;
    }

    public static void testRunnerOnGroupBegin$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mngroup$Mnbegin = value;
    }

    public static Object testRunnerOnGroupEnd(test$Mnrunner obj) {
        return obj.on$Mngroup$Mnend;
    }

    public static void testRunnerOnGroupEnd$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mngroup$Mnend = value;
    }

    public static Object testRunnerOnFinal(test$Mnrunner obj) {
        return obj.on$Mnfinal;
    }

    public static void testRunnerOnFinal$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mnfinal = value;
    }

    public static Object testRunnerOnBadCount(test$Mnrunner obj) {
        return obj.on$Mnbad$Mncount;
    }

    public static void testRunnerOnBadCount$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mnbad$Mncount = value;
    }

    public static Object testRunnerOnBadEndName(test$Mnrunner obj) {
        return obj.on$Mnbad$Mnend$Mnname;
    }

    public static void testRunnerOnBadEndName$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mnbad$Mnend$Mnname = value;
    }

    static Object $PcTestRunnerTotalCount(test$Mnrunner obj) {
        return obj.total$Mncount;
    }

    static void $PcTestRunnerTotalCount$Ex(test$Mnrunner obj, Object value) {
        obj.total$Mncount = value;
    }

    static Object $PcTestRunnerCountList(test$Mnrunner obj) {
        return obj.count$Mnlist;
    }

    static void $PcTestRunnerCountList$Ex(test$Mnrunner obj, Object value) {
        obj.count$Mnlist = value;
    }

    public static Object testResultAlist(test$Mnrunner obj) {
        return obj.result$Mnalist;
    }

    public static void testResultAlist$Ex(test$Mnrunner obj, Object value) {
        obj.result$Mnalist = value;
    }

    public static Object testRunnerAuxValue(test$Mnrunner obj) {
        return obj.aux$Mnvalue;
    }

    public static void testRunnerAuxValue$Ex(test$Mnrunner obj, Object value) {
        obj.aux$Mnvalue = value;
    }

    public static void testRunnerReset(Object runner) {
        try {
            testResultAlist$Ex((test$Mnrunner) runner, LList.Empty);
            try {
                test$Mnrunner test_mnrunner = (test$Mnrunner) runner;
                IntNum intNum = Lit0;
                testRunnerPassCount$Ex(test_mnrunner, intNum);
                try {
                    testRunnerFailCount$Ex((test$Mnrunner) runner, intNum);
                    try {
                        testRunnerXpassCount$Ex((test$Mnrunner) runner, intNum);
                        try {
                            testRunnerXfailCount$Ex((test$Mnrunner) runner, intNum);
                            try {
                                testRunnerSkipCount$Ex((test$Mnrunner) runner, intNum);
                                try {
                                    $PcTestRunnerTotalCount$Ex((test$Mnrunner) runner, intNum);
                                    try {
                                        $PcTestRunnerCountList$Ex((test$Mnrunner) runner, LList.Empty);
                                        try {
                                            $PcTestRunnerRunList$Ex((test$Mnrunner) runner, Boolean.TRUE);
                                            try {
                                                $PcTestRunnerSkipList$Ex((test$Mnrunner) runner, LList.Empty);
                                                try {
                                                    $PcTestRunnerFailList$Ex((test$Mnrunner) runner, LList.Empty);
                                                    try {
                                                        $PcTestRunnerSkipSave$Ex((test$Mnrunner) runner, LList.Empty);
                                                        try {
                                                            $PcTestRunnerFailSave$Ex((test$Mnrunner) runner, LList.Empty);
                                                            try {
                                                                testRunnerGroupStack$Ex((test$Mnrunner) runner, LList.Empty);
                                                            } catch (ClassCastException e) {
                                                                throw new WrongType(e, "test-runner-group-stack!", 0, runner);
                                                            }
                                                        } catch (ClassCastException e2) {
                                                            throw new WrongType(e2, "%test-runner-fail-save!", 0, runner);
                                                        }
                                                    } catch (ClassCastException e3) {
                                                        throw new WrongType(e3, "%test-runner-skip-save!", 0, runner);
                                                    }
                                                } catch (ClassCastException e4) {
                                                    throw new WrongType(e4, "%test-runner-fail-list!", 0, runner);
                                                }
                                            } catch (ClassCastException e5) {
                                                throw new WrongType(e5, "%test-runner-skip-list!", 0, runner);
                                            }
                                        } catch (ClassCastException e6) {
                                            throw new WrongType(e6, "%test-runner-run-list!", 0, runner);
                                        }
                                    } catch (ClassCastException e7) {
                                        throw new WrongType(e7, "%test-runner-count-list!", 0, runner);
                                    }
                                } catch (ClassCastException e8) {
                                    throw new WrongType(e8, "%test-runner-total-count!", 0, runner);
                                }
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "test-runner-skip-count!", 0, runner);
                            }
                        } catch (ClassCastException e10) {
                            throw new WrongType(e10, "test-runner-xfail-count!", 0, runner);
                        }
                    } catch (ClassCastException e11) {
                        throw new WrongType(e11, "test-runner-xpass-count!", 0, runner);
                    }
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "test-runner-fail-count!", 0, runner);
                }
            } catch (ClassCastException e13) {
                throw new WrongType(e13, "test-runner-pass-count!", 0, runner);
            }
        } catch (ClassCastException e14) {
            throw new WrongType(e14, "test-result-alist!", 0, runner);
        }
    }

    public static LList testRunnerGroupPath(Object runner) {
        try {
            Object testRunnerGroupStack = testRunnerGroupStack((test$Mnrunner) runner);
            try {
                return lists.reverse((LList) testRunnerGroupStack);
            } catch (ClassCastException e) {
                throw new WrongType(e, "reverse", 1, testRunnerGroupStack);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-group-stack", 0, runner);
        }
    }

    static Boolean $PcTestNullCallback(Object runner) {
        return Boolean.FALSE;
    }

    public static test$Mnrunner testRunnerNull() {
        test$Mnrunner runner = $PcTestRunnerAlloc();
        testRunnerReset(runner);
        testRunnerOnGroupBegin$Ex(runner, lambda$Fn1);
        ModuleMethod moduleMethod = $Pctest$Mnnull$Mncallback;
        testRunnerOnGroupEnd$Ex(runner, moduleMethod);
        testRunnerOnFinal$Ex(runner, moduleMethod);
        testRunnerOnTestBegin$Ex(runner, moduleMethod);
        testRunnerOnTestEnd$Ex(runner, moduleMethod);
        testRunnerOnBadCount$Ex(runner, lambda$Fn2);
        testRunnerOnBadEndName$Ex(runner, lambda$Fn3);
        return runner;
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 53:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 54:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 55:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 56:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 72:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    static Boolean lambda1(Object runner, Object name, Object count) {
        return Boolean.FALSE;
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 50:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 51:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 52:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 59:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 61:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 62:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 67:
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

    static Boolean lambda2(Object runner, Object count, Object expected) {
        return Boolean.FALSE;
    }

    static Boolean lambda3(Object runner, Object begin, Object end) {
        return Boolean.FALSE;
    }

    public static test$Mnrunner testRunnerSimple() {
        test$Mnrunner runner = $PcTestRunnerAlloc();
        testRunnerReset(runner);
        testRunnerOnGroupBegin$Ex(runner, test$Mnon$Mngroup$Mnbegin$Mnsimple);
        testRunnerOnGroupEnd$Ex(runner, test$Mnon$Mngroup$Mnend$Mnsimple);
        testRunnerOnFinal$Ex(runner, test$Mnon$Mnfinal$Mnsimple);
        testRunnerOnTestBegin$Ex(runner, test$Mnon$Mntest$Mnbegin$Mnsimple);
        testRunnerOnTestEnd$Ex(runner, test$Mnon$Mntest$Mnend$Mnsimple);
        testRunnerOnBadCount$Ex(runner, test$Mnon$Mnbad$Mncount$Mnsimple);
        testRunnerOnBadEndName$Ex(runner, test$Mnon$Mnbad$Mnend$Mnname$Mnsimple);
        return runner;
    }

    public static Object testRunnerGet() {
        Object r = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        if (r == Boolean.FALSE) {
            misc.error$V("test-runner not initialized - test-begin missing?", new Object[0]);
        }
        return r;
    }

    static Object $PcTestSpecificierMatches(Object spec, Object runner) {
        return Scheme.applyToArgs.apply2(spec, runner);
    }

    public static Object testRunnerCreate() {
        return Scheme.applyToArgs.apply1(((Procedure) test$Mnrunner$Mnfactory).apply0());
    }

    static Object $PcTestAnySpecifierMatches(Object list, Object runner) {
        Object result = Boolean.FALSE;
        for (Object l = list; !lists.isNull(l); l = lists.cdr.apply1(l)) {
            if ($PcTestSpecificierMatches(lists.car.apply1(l), runner) != Boolean.FALSE) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0025, code lost:
        if (r1 != 0) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0035, code lost:
        if ($PcTestAnySpecifierMatches($PcTestRunnerSkipList((gnu.kawa.slib.test$Mnrunner) r4), r4) != java.lang.Boolean.FALSE) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004f, code lost:
        if ($PcTestAnySpecifierMatches($PcTestRunnerFailList((gnu.kawa.slib.test$Mnrunner) r4), r4) == java.lang.Boolean.FALSE) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0051, code lost:
        r0 = Lit1;
        r1 = Lit3;
        testResultSet$Ex(r4, r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0065, code lost:
        throw new gnu.mapping.WrongType(r1, "%test-runner-fail-list", 0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return java.lang.Boolean.TRUE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object $PcTestShouldExecute(java.lang.Object r4) {
        /*
            r0 = 0
            r1 = r4
            gnu.kawa.slib.test$Mnrunner r1 = (gnu.kawa.slib.test$Mnrunner) r1     // Catch:{ ClassCastException -> 0x0079 }
            java.lang.Object r1 = $PcTestRunnerRunList(r1)
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r3 = 1
            if (r1 != r2) goto L_0x0011
            r2 = 1
            goto L_0x0012
        L_0x0011:
            r2 = 0
        L_0x0012:
            if (r2 == 0) goto L_0x0015
            goto L_0x0020
        L_0x0015:
            java.lang.Object r1 = $PcTestAnySpecifierMatches(r1, r4)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE     // Catch:{ ClassCastException -> 0x006f }
            if (r1 == r2) goto L_0x001f
            r2 = 1
            goto L_0x0020
        L_0x001f:
            r2 = 0
        L_0x0020:
            int r2 = r2 + r3
            r1 = r2 & 1
            if (r1 == 0) goto L_0x0028
            if (r1 == 0) goto L_0x0042
        L_0x0027:
            goto L_0x0038
        L_0x0028:
            r1 = r4
            gnu.kawa.slib.test$Mnrunner r1 = (gnu.kawa.slib.test$Mnrunner) r1     // Catch:{ ClassCastException -> 0x0066 }
            java.lang.Object r1 = $PcTestRunnerSkipList(r1)
            java.lang.Object r1 = $PcTestAnySpecifierMatches(r1, r4)
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            if (r1 == r2) goto L_0x0042
            goto L_0x0027
        L_0x0038:
            gnu.mapping.SimpleSymbol r0 = Lit1
            gnu.mapping.SimpleSymbol r1 = Lit2
            testResultSet$Ex(r4, r0, r1)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            goto L_0x005c
        L_0x0042:
            r1 = r4
            gnu.kawa.slib.test$Mnrunner r1 = (gnu.kawa.slib.test$Mnrunner) r1     // Catch:{ ClassCastException -> 0x005d }
            java.lang.Object r0 = $PcTestRunnerFailList(r1)
            java.lang.Object r0 = $PcTestAnySpecifierMatches(r0, r4)
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            if (r0 == r1) goto L_0x005a
            gnu.mapping.SimpleSymbol r0 = Lit1
            gnu.mapping.SimpleSymbol r1 = Lit3
            testResultSet$Ex(r4, r0, r1)
            r4 = r1
            goto L_0x005c
        L_0x005a:
            java.lang.Boolean r4 = java.lang.Boolean.TRUE
        L_0x005c:
            return r4
        L_0x005d:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "%test-runner-fail-list"
            r2.<init>((java.lang.ClassCastException) r1, (java.lang.String) r3, (int) r0, (java.lang.Object) r4)
            throw r2
        L_0x0066:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "%test-runner-skip-list"
            r2.<init>((java.lang.ClassCastException) r1, (java.lang.String) r3, (int) r0, (java.lang.Object) r4)
            throw r2
        L_0x006f:
            r4 = move-exception
            gnu.mapping.WrongType r0 = new gnu.mapping.WrongType
            r2 = -2
            java.lang.String r3 = "x"
            r0.<init>((java.lang.ClassCastException) r4, (java.lang.String) r3, (int) r2, (java.lang.Object) r1)
            throw r0
        L_0x0079:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "%test-runner-run-list"
            r2.<init>((java.lang.ClassCastException) r1, (java.lang.String) r3, (int) r0, (java.lang.Object) r4)
            goto L_0x0083
        L_0x0082:
            throw r2
        L_0x0083:
            goto L_0x0082
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.testing.$PcTestShouldExecute(java.lang.Object):java.lang.Object");
    }

    public static void $PcTestBegin(Object suite$Mnname, Object count) {
        if (((Procedure) test$Mnrunner$Mncurrent).apply0() == Boolean.FALSE) {
            ((Procedure) test$Mnrunner$Mncurrent).apply1(testRunnerCreate());
        }
        Object runner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        try {
            Scheme.applyToArgs.apply4(testRunnerOnGroupBegin((test$Mnrunner) runner), runner, suite$Mnname, count);
            try {
                try {
                    try {
                        $PcTestRunnerSkipSave$Ex((test$Mnrunner) runner, lists.cons($PcTestRunnerSkipList((test$Mnrunner) runner), $PcTestRunnerSkipSave((test$Mnrunner) runner)));
                        try {
                            try {
                                try {
                                    $PcTestRunnerFailSave$Ex((test$Mnrunner) runner, lists.cons($PcTestRunnerFailList((test$Mnrunner) runner), $PcTestRunnerFailSave((test$Mnrunner) runner)));
                                    try {
                                        try {
                                            try {
                                                $PcTestRunnerCountList$Ex((test$Mnrunner) runner, lists.cons(lists.cons($PcTestRunnerTotalCount((test$Mnrunner) runner), count), $PcTestRunnerCountList((test$Mnrunner) runner)));
                                                try {
                                                    try {
                                                        testRunnerGroupStack$Ex((test$Mnrunner) runner, lists.cons(suite$Mnname, testRunnerGroupStack((test$Mnrunner) runner)));
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "test-runner-group-stack", 0, runner);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "test-runner-group-stack!", 0, runner);
                                                }
                                            } catch (ClassCastException e3) {
                                                throw new WrongType(e3, "%test-runner-count-list", 0, runner);
                                            }
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "%test-runner-total-count", 0, runner);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "%test-runner-count-list!", 0, runner);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "%test-runner-fail-save", 0, runner);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "%test-runner-fail-list", 0, runner);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "%test-runner-fail-save!", 0, runner);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "%test-runner-skip-save", 0, runner);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "%test-runner-skip-list", 0, runner);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "%test-runner-skip-save!", 0, runner);
            }
        } catch (ClassCastException e12) {
            throw new WrongType(e12, "test-runner-on-group-begin", 0, runner);
        }
    }

    public static Boolean testOnGroupBeginSimple(Object runner, Object suite$Mnname, Object count) {
        Object log$Mnfile$Mnname;
        try {
            if (lists.isNull(testRunnerGroupStack((test$Mnrunner) runner))) {
                ports.display("%%%% Starting test ");
                ports.display(suite$Mnname);
                if (strings.isString(Boolean.TRUE)) {
                    log$Mnfile$Mnname = Boolean.TRUE;
                    Object obj = runner;
                } else {
                    log$Mnfile$Mnname = strings.stringAppend(suite$Mnname, ".log");
                    Object obj2 = runner;
                }
                try {
                    OutPort log$Mnfile = ports.openOutputFile(Path.valueOf(log$Mnfile$Mnname));
                    ports.display("%%%% Starting test ", log$Mnfile);
                    ports.display(suite$Mnname, log$Mnfile);
                    ports.newline(log$Mnfile);
                    try {
                        testRunnerAuxValue$Ex((test$Mnrunner) runner, log$Mnfile);
                        ports.display("  (Writing full log to \"");
                        ports.display(log$Mnfile$Mnname);
                        ports.display("\")");
                        ports.newline();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-runner-aux-value!", 0, runner);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "open-output-file", 1, log$Mnfile$Mnname);
                }
            } else {
                log$Mnfile$Mnname = runner;
            }
            try {
                Object log = testRunnerAuxValue((test$Mnrunner) runner);
                Object obj3 = log$Mnfile$Mnname;
                if (ports.isOutputPort(log)) {
                    ports.display("Group begin: ", log);
                    ports.display(suite$Mnname, log);
                    ports.newline(log);
                }
                return Boolean.FALSE;
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "test-runner-aux-value", 0, runner);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "test-runner-group-stack", 0, runner);
        }
    }

    public static Boolean testOnGroupEndSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                ports.display("Group end: ", log);
                try {
                    ports.display(lists.car.apply1(testRunnerGroupStack((test$Mnrunner) runner)), log);
                    ports.newline(log);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-group-stack", 0, runner);
                }
            }
            return Boolean.FALSE;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-aux-value", 0, runner);
        }
    }

    static void $PcTestOnBadCountWrite(Object runner, Object count, Object expected$Mncount, Object port) {
        ports.display("*** Total number of tests was ", port);
        ports.display(count, port);
        ports.display(" but should be ", port);
        ports.display(expected$Mncount, port);
        ports.display(". ***", port);
        ports.newline(port);
        ports.display("*** Discrepancy indicates testsuite error or exceptions. ***", port);
        ports.newline(port);
    }

    public static void testOnBadCountSimple(Object runner, Object count, Object expected$Mncount) {
        $PcTestOnBadCountWrite(runner, count, expected$Mncount, ports.current$Mnoutput$Mnport.apply0());
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                $PcTestOnBadCountWrite(runner, count, expected$Mncount, log);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-runner-aux-value", 0, runner);
        }
    }

    public static Object testOnBadEndNameSimple(Object runner, Object begin$Mnname, Object end$Mnname) {
        return misc.error$V(strings.stringAppend($PcTestFormatLine(runner), "test-end ", begin$Mnname, " does not match test-begin ", end$Mnname), new Object[0]);
    }

    static void $PcTestFinalReport1(Object value, Object label, Object port) {
        if (Scheme.numGrt.apply2(value, Lit0) != Boolean.FALSE) {
            ports.display(label, port);
            ports.display(value, port);
            ports.newline(port);
        }
    }

    static void $PcTestFinalReportSimple(Object runner, Object port) {
        try {
            $PcTestFinalReport1(testRunnerPassCount((test$Mnrunner) runner), "# of expected passes      ", port);
            try {
                $PcTestFinalReport1(testRunnerXfailCount((test$Mnrunner) runner), "# of expected failures    ", port);
                try {
                    $PcTestFinalReport1(testRunnerXpassCount((test$Mnrunner) runner), "# of unexpected successes ", port);
                    try {
                        $PcTestFinalReport1(testRunnerFailCount((test$Mnrunner) runner), "# of unexpected failures  ", port);
                        try {
                            $PcTestFinalReport1(testRunnerSkipCount((test$Mnrunner) runner), "# of skipped tests        ", port);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "test-runner-skip-count", 0, runner);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "test-runner-fail-count", 0, runner);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-xpass-count", 0, runner);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "test-runner-xfail-count", 0, runner);
            }
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "test-runner-pass-count", 0, runner);
        }
    }

    public static void testOnFinalSimple(Object runner) {
        $PcTestFinalReportSimple(runner, ports.current$Mnoutput$Mnport.apply0());
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                $PcTestFinalReportSimple(runner, log);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-runner-aux-value", 0, runner);
        }
    }

    static Object $PcTestFormatLine(Object runner) {
        try {
            Object line$Mninfo = testResultAlist((test$Mnrunner) runner);
            Object source$Mnfile = lists.assq(Lit4, line$Mninfo);
            Object obj = runner;
            Object source$Mnline = lists.assq(Lit5, line$Mninfo);
            Object file = source$Mnfile != Boolean.FALSE ? lists.cdr.apply1(source$Mnfile) : "";
            if (source$Mnline == Boolean.FALSE) {
                return "";
            }
            Object[] objArr = new Object[4];
            objArr[0] = file;
            objArr[1] = ":";
            Object apply1 = lists.cdr.apply1(source$Mnline);
            try {
                objArr[2] = numbers.number$To$String((Number) apply1);
                objArr[3] = ": ";
                return strings.stringAppend(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "number->string", 1, apply1);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-result-alist", 0, runner);
        }
    }

    public static Object $PcTestEnd(Object suite$Mnname, Object line$Mninfo) {
        Object r = testRunnerGet();
        try {
            Object groups = testRunnerGroupStack((test$Mnrunner) r);
            Object line = $PcTestFormatLine(r);
            Object obj = r;
            try {
                testResultAlist$Ex((test$Mnrunner) r, line$Mninfo);
                if (lists.isNull(groups)) {
                    Object obj2 = r;
                    misc.error$V(strings.stringAppend(line, "test-end not in a group"), new Object[0]);
                } else {
                    Object obj3 = r;
                }
                if (suite$Mnname == Boolean.FALSE ? suite$Mnname != Boolean.FALSE : !IsEqual.apply(suite$Mnname, lists.car.apply1(groups))) {
                    try {
                        Scheme.applyToArgs.apply4(testRunnerOnBadEndName((test$Mnrunner) r), r, suite$Mnname, lists.car.apply1(groups));
                        Object obj4 = r;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-runner-on-bad-end-name", 0, r);
                    }
                }
                try {
                    Object count$Mnlist = $PcTestRunnerCountList((test$Mnrunner) r);
                    Object expected$Mncount = lists.cdar.apply1(count$Mnlist);
                    Object obj5 = r;
                    Object saved$Mncount = lists.caar.apply1(count$Mnlist);
                    try {
                        Object group$Mncount = AddOp.$Mn.apply2($PcTestRunnerTotalCount((test$Mnrunner) r), saved$Mncount);
                        if (expected$Mncount == Boolean.FALSE ? expected$Mncount != Boolean.FALSE : Scheme.numEqu.apply2(expected$Mncount, group$Mncount) == Boolean.FALSE) {
                            try {
                                Scheme.applyToArgs.apply4(testRunnerOnBadCount((test$Mnrunner) r), r, group$Mncount, expected$Mncount);
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "test-runner-on-bad-count", 0, r);
                            }
                        }
                        try {
                            Scheme.applyToArgs.apply2(testRunnerOnGroupEnd((test$Mnrunner) r), r);
                            try {
                                try {
                                    testRunnerGroupStack$Ex((test$Mnrunner) r, lists.cdr.apply1(testRunnerGroupStack((test$Mnrunner) r)));
                                    try {
                                        try {
                                            $PcTestRunnerSkipList$Ex((test$Mnrunner) r, lists.car.apply1($PcTestRunnerSkipSave((test$Mnrunner) r)));
                                            try {
                                                try {
                                                    $PcTestRunnerSkipSave$Ex((test$Mnrunner) r, lists.cdr.apply1($PcTestRunnerSkipSave((test$Mnrunner) r)));
                                                    try {
                                                        try {
                                                            $PcTestRunnerFailList$Ex((test$Mnrunner) r, lists.car.apply1($PcTestRunnerFailSave((test$Mnrunner) r)));
                                                            try {
                                                                try {
                                                                    $PcTestRunnerFailSave$Ex((test$Mnrunner) r, lists.cdr.apply1($PcTestRunnerFailSave((test$Mnrunner) r)));
                                                                    try {
                                                                        $PcTestRunnerCountList$Ex((test$Mnrunner) r, lists.cdr.apply1(count$Mnlist));
                                                                        try {
                                                                            if (!lists.isNull(testRunnerGroupStack((test$Mnrunner) r))) {
                                                                                return Values.empty;
                                                                            }
                                                                            try {
                                                                                return Scheme.applyToArgs.apply2(testRunnerOnFinal((test$Mnrunner) r), r);
                                                                            } catch (ClassCastException e3) {
                                                                                throw new WrongType(e3, "test-runner-on-final", 0, r);
                                                                            }
                                                                        } catch (ClassCastException e4) {
                                                                            throw new WrongType(e4, "test-runner-group-stack", 0, r);
                                                                        }
                                                                    } catch (ClassCastException e5) {
                                                                        throw new WrongType(e5, "%test-runner-count-list!", 0, r);
                                                                    }
                                                                } catch (ClassCastException e6) {
                                                                    throw new WrongType(e6, "%test-runner-fail-save", 0, r);
                                                                }
                                                            } catch (ClassCastException e7) {
                                                                throw new WrongType(e7, "%test-runner-fail-save!", 0, r);
                                                            }
                                                        } catch (ClassCastException e8) {
                                                            throw new WrongType(e8, "%test-runner-fail-save", 0, r);
                                                        }
                                                    } catch (ClassCastException e9) {
                                                        throw new WrongType(e9, "%test-runner-fail-list!", 0, r);
                                                    }
                                                } catch (ClassCastException e10) {
                                                    throw new WrongType(e10, "%test-runner-skip-save", 0, r);
                                                }
                                            } catch (ClassCastException e11) {
                                                throw new WrongType(e11, "%test-runner-skip-save!", 0, r);
                                            }
                                        } catch (ClassCastException e12) {
                                            throw new WrongType(e12, "%test-runner-skip-save", 0, r);
                                        }
                                    } catch (ClassCastException e13) {
                                        throw new WrongType(e13, "%test-runner-skip-list!", 0, r);
                                    }
                                } catch (ClassCastException e14) {
                                    throw new WrongType(e14, "test-runner-group-stack", 0, r);
                                }
                            } catch (ClassCastException e15) {
                                throw new WrongType(e15, "test-runner-group-stack!", 0, r);
                            }
                        } catch (ClassCastException e16) {
                            throw new WrongType(e16, "test-runner-on-group-end", 0, r);
                        }
                    } catch (ClassCastException e17) {
                        throw new WrongType(e17, "%test-runner-total-count", 0, r);
                    }
                } catch (ClassCastException e18) {
                    throw new WrongType(e18, "%test-runner-count-list", 0, r);
                }
            } catch (ClassCastException e19) {
                throw new WrongType(e19, "test-result-alist!", 0, r);
            }
        } catch (ClassCastException e20) {
            throw new WrongType(e20, "test-runner-group-stack", 0, r);
        }
    }

    static Object testOnTestBeginSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (!ports.isOutputPort(log)) {
                return Values.empty;
            }
            try {
                Object results = testResultAlist((test$Mnrunner) runner);
                Object obj = runner;
                Object source$Mnfile = lists.assq(Lit4, results);
                Object obj2 = runner;
                Object source$Mnline = lists.assq(Lit5, results);
                Object source$Mnform = lists.assq(Lit6, results);
                Object test$Mnname = lists.assq(Lit7, results);
                ports.display("Test begin:", log);
                ports.newline(log);
                if (test$Mnname != Boolean.FALSE) {
                    $PcTestWriteResult1(test$Mnname, log);
                }
                if (source$Mnfile != Boolean.FALSE) {
                    $PcTestWriteResult1(source$Mnfile, log);
                }
                if (source$Mnline != Boolean.FALSE) {
                    $PcTestWriteResult1(source$Mnline, log);
                }
                if (source$Mnfile != Boolean.FALSE) {
                    return $PcTestWriteResult1(source$Mnform, log);
                }
                return Values.empty;
            } catch (ClassCastException e) {
                throw new WrongType(e, "test-result-alist", 0, runner);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-aux-value", 0, runner);
        }
    }

    public static Object testOnTestEndSimple(Object runner) {
        Object source$Mnfile;
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            try {
                Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
                Object kind = p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE;
                Object obj = runner;
                if (lists.memq(kind, Lit8) != Boolean.FALSE) {
                    try {
                        Object results = testResultAlist((test$Mnrunner) runner);
                        source$Mnfile = lists.assq(Lit4, results);
                        Object obj2 = runner;
                        Object source$Mnline = lists.assq(Lit5, results);
                        Object test$Mnname = lists.assq(Lit7, results);
                        if (!(source$Mnfile == Boolean.FALSE && source$Mnline == Boolean.FALSE)) {
                            if (source$Mnfile != Boolean.FALSE) {
                                ports.display(lists.cdr.apply1(source$Mnfile));
                            }
                            ports.display(":");
                            if (source$Mnline != Boolean.FALSE) {
                                ports.display(lists.cdr.apply1(source$Mnline));
                            }
                            ports.display(": ");
                        }
                        ports.display(kind == Lit9 ? "XPASS" : "FAIL");
                        if (test$Mnname != Boolean.FALSE) {
                            ports.display(" ");
                            ports.display(lists.cdr.apply1(test$Mnname));
                        }
                        ports.newline();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-result-alist", 0, runner);
                    }
                } else {
                    source$Mnfile = runner;
                }
                if (!ports.isOutputPort(log)) {
                    return Values.empty;
                }
                ports.display("Test end:", log);
                ports.newline(log);
                try {
                    for (Object list = testResultAlist((test$Mnrunner) runner); lists.isPair(list); list = lists.cdr.apply1(list)) {
                        Object obj3 = source$Mnfile;
                        source$Mnfile = lists.car.apply1(list);
                        if (lists.memq(lists.car.apply1(source$Mnfile), Lit10) == Boolean.FALSE) {
                            $PcTestWriteResult1(source$Mnfile, log);
                        }
                    }
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-result-alist", 0, runner);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "test-result-alist", 0, runner);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "test-runner-aux-value", 0, runner);
        }
    }

    static Object $PcTestWriteResult1(Object pair, Object port) {
        ports.display("  ", port);
        ports.display(lists.car.apply1(pair), port);
        ports.display(": ", port);
        ports.write(lists.cdr.apply1(pair), port);
        ports.newline(port);
        return Values.empty;
    }

    public static Object testResultSet$Ex(Object runner, Object pname, Object value) {
        try {
            Object alist = testResultAlist((test$Mnrunner) runner);
            Object p = lists.assq(pname, alist);
            Object obj = runner;
            if (p != Boolean.FALSE) {
                try {
                    lists.setCdr$Ex((Pair) p, value);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, p);
                }
            } else {
                try {
                    testResultAlist$Ex((test$Mnrunner) runner, lists.cons(lists.cons(pname, value), alist));
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-result-alist!", 0, runner);
                }
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "test-result-alist", 0, runner);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 50:
                return lambda1(obj, obj2, obj3);
            case 51:
                return lambda2(obj, obj2, obj3);
            case 52:
                return lambda3(obj, obj2, obj3);
            case 59:
                return testOnGroupBeginSimple(obj, obj2, obj3);
            case 61:
                testOnBadCountSimple(obj, obj2, obj3);
                return Values.empty;
            case 62:
                return testOnBadEndNameSimple(obj, obj2, obj3);
            case 67:
                return testResultSet$Ex(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static void testResultClear(Object runner) {
        try {
            testResultAlist$Ex((test$Mnrunner) runner, LList.Empty);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist!", 0, runner);
        }
    }

    public static void testResultRemove(Object runner, Object pname) {
        frame frame6 = new frame();
        try {
            Object alist = testResultAlist((test$Mnrunner) runner);
            frame6.p = lists.assq(pname, alist);
            if (frame6.p != Boolean.FALSE) {
                try {
                    testResultAlist$Ex((test$Mnrunner) runner, frame6.lambda4loop(alist));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-result-alist!", 0, runner);
                }
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-result-alist", 0, runner);
        }
    }

    /* compiled from: testing.scm */
    public class frame extends ModuleBody {
        Object p;

        public Object lambda4loop(Object r) {
            if (r == this.p) {
                return lists.cdr.apply1(r);
            }
            return lists.cons(lists.car.apply1(r), lambda4loop(lists.cdr.apply1(r)));
        }
    }

    public static Object testResultKind$V(Object[] argsArray) {
        LList rest = LList.makeList(argsArray, 0);
        Object runner = lists.isPair(rest) ? lists.car.apply1(rest) : ((Procedure) test$Mnrunner$Mncurrent).apply0();
        try {
            Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
            return p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 70:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 71:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 84:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 86:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 87:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object isTestPassed$V(Object[] argsArray) {
        LList rest = LList.makeList(argsArray, 0);
        Object runner = lists.isPair(rest) ? lists.car.apply1(rest) : testRunnerGet();
        try {
            Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
            return lists.memq(p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE, Lit11);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    public static Object $PcTestReportResult() {
        Object r = testRunnerGet();
        Object result$Mnkind = testResultKind$V(new Object[]{r});
        if (Scheme.isEqv.apply2(result$Mnkind, Lit12) != Boolean.FALSE) {
            try {
                try {
                    testRunnerPassCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerPassCount((test$Mnrunner) r)));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count", 0, r);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "test-runner-pass-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit14) != Boolean.FALSE) {
            try {
                try {
                    testRunnerFailCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerFailCount((test$Mnrunner) r)));
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-fail-count", 0, r);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "test-runner-fail-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit9) != Boolean.FALSE) {
            try {
                try {
                    testRunnerXpassCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerXpassCount((test$Mnrunner) r)));
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "test-runner-xpass-count", 0, r);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "test-runner-xpass-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit3) != Boolean.FALSE) {
            try {
                try {
                    testRunnerXfailCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerXfailCount((test$Mnrunner) r)));
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "test-runner-xfail-count", 0, r);
                }
            } catch (ClassCastException e8) {
                throw new WrongType(e8, "test-runner-xfail-count!", 0, r);
            }
        } else {
            try {
                try {
                    testRunnerSkipCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerSkipCount((test$Mnrunner) r)));
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "test-runner-skip-count", 0, r);
                }
            } catch (ClassCastException e10) {
                throw new WrongType(e10, "test-runner-skip-count!", 0, r);
            }
        }
        try {
            try {
                $PcTestRunnerTotalCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, $PcTestRunnerTotalCount((test$Mnrunner) r)));
                try {
                    return Scheme.applyToArgs.apply2(testRunnerOnTestEnd((test$Mnrunner) r), r);
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "test-runner-on-test-end", 0, r);
                }
            } catch (ClassCastException e12) {
                throw new WrongType(e12, "%test-runner-total-count", 0, r);
            }
        } catch (ClassCastException e13) {
            throw new WrongType(e13, "%test-runner-total-count!", 0, r);
        }
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 53:
                return testRunnerNull();
            case 54:
                return testRunnerSimple();
            case 55:
                return testRunnerGet();
            case 56:
                return testRunnerCreate();
            case 72:
                return $PcTestReportResult();
            default:
                return super.apply0(moduleMethod);
        }
    }

    static Object $PcTestSyntaxFile(Object form) {
        return std_syntax.syntaxSource(form);
    }

    static Pair $PcTestSourceLine2(Object form) {
        Object line = std_syntax.syntaxLine(form);
        Object file = $PcTestSyntaxFile(form);
        Object line$Mnpair = line != Boolean.FALSE ? LList.list1(lists.cons(Lit5, line)) : LList.Empty;
        return lists.cons(lists.cons(Lit6, std_syntax.syntaxObject$To$Datum(form)), file != Boolean.FALSE ? lists.cons(lists.cons(Lit4, file), line$Mnpair) : line$Mnpair);
    }

    public static boolean $PcTestOnTestBegin(Object r) {
        $PcTestShouldExecute(r);
        int i = 0;
        try {
            Scheme.applyToArgs.apply2(testRunnerOnTestBegin((test$Mnrunner) r), r);
            SimpleSymbol simpleSymbol = Lit2;
            try {
                Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) r));
                Object obj = r;
                if (simpleSymbol == (p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE)) {
                    i = 1;
                }
                return (i + 1) & true;
            } catch (ClassCastException e) {
                throw new WrongType(e, "test-result-alist", 0, r);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-on-test-begin", 0, r);
        }
    }

    public static Object $PcTestOnTestEnd(Object r, Object result) {
        SimpleSymbol simpleSymbol = Lit1;
        try {
            Object p = lists.assq(simpleSymbol, testResultAlist((test$Mnrunner) r));
            Object apply1 = p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE;
            Object p2 = Lit3;
            if (apply1 != p2) {
                p2 = result != Boolean.FALSE ? Lit12 : Lit14;
            } else if (result != Boolean.FALSE) {
                p2 = Lit9;
            }
            return testResultSet$Ex(r, simpleSymbol, p2);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, r);
        }
    }

    public static Object testRunnerTestName(Object runner) {
        try {
            Object p = lists.assq(Lit7, testResultAlist((test$Mnrunner) runner));
            return p != Boolean.FALSE ? lists.cdr.apply1(p) : "";
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    /* compiled from: testing.scm */
    public class frame0 extends ModuleBody {
        Object error;
        final ModuleMethod lambda$Fn4;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, (Object) null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:640");
            this.lambda$Fn4 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            if (moduleMethod.selector == 1) {
                return lambda5(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply2(moduleMethod, obj, obj2);
        }

        /* access modifiers changed from: package-private */
        public boolean lambda5(Object value, Object expected) {
            Object apply2 = Scheme.numGEq.apply2(value, AddOp.$Mn.apply2(expected, this.error));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (x) {
                    return ((Boolean) Scheme.numLEq.apply2(value, AddOp.$Pl.apply2(expected, this.error))).booleanValue();
                }
                return x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    public static Procedure $PcTestApproximimate$Eq(Object error) {
        frame0 frame02 = new frame0();
        frame02.error = error;
        return frame02.lambda$Fn4;
    }

    static Object lambda16(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(3, (Object[]) null);
        if (Lit95.match(list2, allocVars, 0)) {
            return Lit96.execute(allocVars, TemplateScope.make());
        } else if (!Lit97.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit98.execute(allocVars, TemplateScope.make());
        }
    }

    static Object lambda17(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(4, (Object[]) null);
        if (Lit100.match(list2, allocVars, 0)) {
            return Lit101.execute(allocVars, TemplateScope.make());
        } else if (!Lit102.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit103.execute(allocVars, TemplateScope.make());
        }
    }

    static Object $PcTestComp2(Object comp, Object x) {
        Pair list3 = LList.list3(x, LList.list2(Lit15, $PcTestSourceLine2(x)), comp);
        Object[] allocVars = SyntaxPattern.allocVars(6, (Object[]) null);
        if (Lit16.match(list3, allocVars, 0)) {
            return Lit17.execute(allocVars, TemplateScope.make());
        } else if (!Lit18.match(list3, allocVars, 0)) {
            return syntax_case.error("syntax-case", list3);
        } else {
            return Lit19.execute(allocVars, TemplateScope.make());
        }
    }

    static Object lambda18(Object x) {
        return $PcTestComp2(Lit105.execute((Object[]) null, TemplateScope.make()), x);
    }

    static Object lambda19(Object x) {
        return $PcTestComp2(Lit107.execute((Object[]) null, TemplateScope.make()), x);
    }

    static Object lambda20(Object x) {
        return $PcTestComp2(Lit109.execute((Object[]) null, TemplateScope.make()), x);
    }

    static Object lambda21(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(6, (Object[]) null);
        if (Lit111.match(list2, allocVars, 0)) {
            return Lit112.execute(allocVars, TemplateScope.make());
        } else if (!Lit113.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit114.execute(allocVars, TemplateScope.make());
        }
    }

    static Object lambda22(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(5, (Object[]) null);
        if (Lit118.match(list2, allocVars, 0)) {
            return Lit119.execute(allocVars, TemplateScope.make());
        } else if (Lit120.match(list2, allocVars, 0)) {
            return Lit121.execute(allocVars, TemplateScope.make());
        } else if (!Lit122.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit123.execute(allocVars, TemplateScope.make());
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 12:
                return isTestRunner(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 13:
                try {
                    return testRunnerPassCount((test$Mnrunner) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count", 1, obj);
                }
            case 15:
                try {
                    return testRunnerFailCount((test$Mnrunner) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-runner-fail-count", 1, obj);
                }
            case 17:
                try {
                    return testRunnerXpassCount((test$Mnrunner) obj);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-xpass-count", 1, obj);
                }
            case 19:
                try {
                    return testRunnerXfailCount((test$Mnrunner) obj);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "test-runner-xfail-count", 1, obj);
                }
            case 21:
                try {
                    return testRunnerSkipCount((test$Mnrunner) obj);
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "test-runner-skip-count", 1, obj);
                }
            case 23:
                try {
                    return $PcTestRunnerSkipList((test$Mnrunner) obj);
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "%test-runner-skip-list", 1, obj);
                }
            case 25:
                try {
                    return $PcTestRunnerFailList((test$Mnrunner) obj);
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "%test-runner-fail-list", 1, obj);
                }
            case 27:
                try {
                    return testRunnerGroupStack((test$Mnrunner) obj);
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "test-runner-group-stack", 1, obj);
                }
            case 29:
                try {
                    return testRunnerOnTestBegin((test$Mnrunner) obj);
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "test-runner-on-test-begin", 1, obj);
                }
            case 31:
                try {
                    return testRunnerOnTestEnd((test$Mnrunner) obj);
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "test-runner-on-test-end", 1, obj);
                }
            case 33:
                try {
                    return testRunnerOnGroupBegin((test$Mnrunner) obj);
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "test-runner-on-group-begin", 1, obj);
                }
            case 35:
                try {
                    return testRunnerOnGroupEnd((test$Mnrunner) obj);
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "test-runner-on-group-end", 1, obj);
                }
            case 37:
                try {
                    return testRunnerOnFinal((test$Mnrunner) obj);
                } catch (ClassCastException e13) {
                    throw new WrongType(e13, "test-runner-on-final", 1, obj);
                }
            case 39:
                try {
                    return testRunnerOnBadCount((test$Mnrunner) obj);
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "test-runner-on-bad-count", 1, obj);
                }
            case 41:
                try {
                    return testRunnerOnBadEndName((test$Mnrunner) obj);
                } catch (ClassCastException e15) {
                    throw new WrongType(e15, "test-runner-on-bad-end-name", 1, obj);
                }
            case 43:
                try {
                    return testResultAlist((test$Mnrunner) obj);
                } catch (ClassCastException e16) {
                    throw new WrongType(e16, "test-result-alist", 1, obj);
                }
            case 45:
                try {
                    return testRunnerAuxValue((test$Mnrunner) obj);
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "test-runner-aux-value", 1, obj);
                }
            case 47:
                testRunnerReset(obj);
                return Values.empty;
            case 48:
                return testRunnerGroupPath(obj);
            case 49:
                return $PcTestNullCallback(obj);
            case 57:
                return $PcTestShouldExecute(obj);
            case 60:
                return testOnGroupEndSimple(obj);
            case 63:
                testOnFinalSimple(obj);
                return Values.empty;
            case 65:
                return testOnTestBeginSimple(obj);
            case 66:
                return testOnTestEndSimple(obj);
            case 68:
                testResultClear(obj);
                return Values.empty;
            case 73:
                return $PcTestOnTestBegin(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 75:
                return testRunnerTestName(obj);
            case 76:
                return $PcTestApproximimate$Eq(obj);
            case 77:
                return lambda16(obj);
            case 78:
                return lambda17(obj);
            case 79:
                return lambda18(obj);
            case 80:
                return lambda19(obj);
            case 81:
                return lambda20(obj);
            case 82:
                return lambda21(obj);
            case 83:
                return lambda22(obj);
            case 88:
                return $PcTestAsSpecifier(obj);
            case 89:
                return testMatchName(obj);
            case 90:
                return testReadEvalString(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object testApply$V(Object first, Object[] argsArray) {
        Pair pair;
        frame1 frame12 = new frame1();
        frame12.first = first;
        frame12.rest = LList.makeList(argsArray, 0);
        if (isTestRunner(frame12.first)) {
            frame12.saved$Mnrunner$1 = ((Procedure) test$Mnrunner$Mncurrent).apply0();
            return misc.dynamicWind(frame12.lambda$Fn5, frame12.lambda$Fn6, frame12.lambda$Fn7);
        }
        Object r = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        if (r != Boolean.FALSE) {
            try {
                Object run$Mnlist = $PcTestRunnerRunList((test$Mnrunner) r);
                if (lists.isNull(frame12.rest)) {
                    try {
                        try {
                            $PcTestRunnerRunList$Ex((test$Mnrunner) r, lists.reverse$Ex((LList) run$Mnlist));
                            return Scheme.applyToArgs.apply1(frame12.first);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "reverse!", 1, run$Mnlist);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%test-runner-run-list!", 0, r);
                    }
                } else {
                    try {
                        test$Mnrunner test_mnrunner = (test$Mnrunner) r;
                        if (run$Mnlist == Boolean.TRUE) {
                            pair = LList.list1(frame12.first);
                        } else {
                            pair = lists.cons(frame12.first, run$Mnlist);
                        }
                        $PcTestRunnerRunList$Ex(test_mnrunner, pair);
                        Scheme.apply.apply2(test$Mnapply, frame12.rest);
                        try {
                            $PcTestRunnerRunList$Ex((test$Mnrunner) r, run$Mnlist);
                            return Values.empty;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "%test-runner-run-list!", 0, r);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "%test-runner-run-list!", 0, r);
                    }
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "%test-runner-run-list", 0, r);
            }
        } else {
            frame12.r = testRunnerCreate();
            frame12.saved$Mnrunner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
            misc.dynamicWind(frame12.lambda$Fn8, frame12.lambda$Fn9, frame12.lambda$Fn10);
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            Object obj = frame12.r;
            try {
                return applyToArgs.apply2(testRunnerOnFinal((test$Mnrunner) obj), frame12.r);
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "test-runner-on-final", 0, obj);
            }
        }
    }

    /* compiled from: testing.scm */
    public class frame1 extends ModuleBody {
        Object first;
        final ModuleMethod lambda$Fn10;
        final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 2, (Object) null, 0);
        final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 3, (Object) null, 0);
        final ModuleMethod lambda$Fn7;
        final ModuleMethod lambda$Fn8;
        final ModuleMethod lambda$Fn9;
        Object r;
        LList rest;
        Object saved$Mnrunner;
        Object saved$Mnrunner$1;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 4, (Object) null, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:897");
            this.lambda$Fn7 = moduleMethod;
            this.lambda$Fn8 = new ModuleMethod(this, 5, (Object) null, 0);
            this.lambda$Fn9 = new ModuleMethod(this, 6, (Object) null, 0);
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 7, (Object) null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:897");
            this.lambda$Fn10 = moduleMethod2;
        }

        /* access modifiers changed from: package-private */
        public Object lambda6() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.first);
        }

        /* access modifiers changed from: package-private */
        public Object lambda7() {
            return Scheme.apply.apply2(testing.test$Mnapply, this.rest);
        }

        /* access modifiers changed from: package-private */
        public Object lambda10() {
            return Scheme.apply.apply3(testing.test$Mnapply, this.first, this.rest);
        }

        /* access modifiers changed from: package-private */
        public Object lambda9() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.r);
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda6();
                case 3:
                    return lambda7();
                case 4:
                    return lambda8();
                case 5:
                    return lambda9();
                case 6:
                    return lambda10();
                case 7:
                    return lambda11();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        /* access modifiers changed from: package-private */
        public Object lambda11() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner);
        }

        /* access modifiers changed from: package-private */
        public Object lambda8() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner$1);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    public static Procedure $PcTestMatchNth(Object n, Object count) {
        frame2 frame22 = new frame2();
        frame22.n = n;
        frame22.count = count;
        frame22.i = Lit0;
        return frame22.lambda$Fn11;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 14:
                try {
                    testRunnerPassCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count!", 1, obj);
                }
            case 16:
                try {
                    testRunnerFailCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-runner-fail-count!", 1, obj);
                }
            case 18:
                try {
                    testRunnerXpassCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-xpass-count!", 1, obj);
                }
            case 20:
                try {
                    testRunnerXfailCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "test-runner-xfail-count!", 1, obj);
                }
            case 22:
                try {
                    testRunnerSkipCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "test-runner-skip-count!", 1, obj);
                }
            case 24:
                try {
                    $PcTestRunnerSkipList$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "%test-runner-skip-list!", 1, obj);
                }
            case 26:
                try {
                    $PcTestRunnerFailList$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "%test-runner-fail-list!", 1, obj);
                }
            case 28:
                try {
                    testRunnerGroupStack$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "test-runner-group-stack!", 1, obj);
                }
            case 30:
                try {
                    testRunnerOnTestBegin$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "test-runner-on-test-begin!", 1, obj);
                }
            case 32:
                try {
                    testRunnerOnTestEnd$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "test-runner-on-test-end!", 1, obj);
                }
            case 34:
                try {
                    testRunnerOnGroupBegin$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "test-runner-on-group-begin!", 1, obj);
                }
            case 36:
                try {
                    testRunnerOnGroupEnd$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "test-runner-on-group-end!", 1, obj);
                }
            case 38:
                try {
                    testRunnerOnFinal$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e13) {
                    throw new WrongType(e13, "test-runner-on-final!", 1, obj);
                }
            case 40:
                try {
                    testRunnerOnBadCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "test-runner-on-bad-count!", 1, obj);
                }
            case 42:
                try {
                    testRunnerOnBadEndName$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e15) {
                    throw new WrongType(e15, "test-runner-on-bad-end-name!", 1, obj);
                }
            case 44:
                try {
                    testResultAlist$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e16) {
                    throw new WrongType(e16, "test-result-alist!", 1, obj);
                }
            case 46:
                try {
                    testRunnerAuxValue$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "test-runner-aux-value!", 1, obj);
                }
            case 58:
                $PcTestBegin(obj, obj2);
                return Values.empty;
            case 64:
                return $PcTestEnd(obj, obj2);
            case 69:
                testResultRemove(obj, obj2);
                return Values.empty;
            case 74:
                return $PcTestOnTestEnd(obj, obj2);
            case 85:
                return $PcTestMatchNth(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* compiled from: testing.scm */
    public class frame2 extends ModuleBody {
        Object count;
        Object i;
        final ModuleMethod lambda$Fn11;
        Object n;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 8, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:903");
            this.lambda$Fn11 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 8) {
                return lambda12(obj) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public boolean lambda12(Object runner) {
            this.i = AddOp.$Pl.apply2(this.i, testing.Lit13);
            Object apply2 = Scheme.numGEq.apply2(this.i, this.n);
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                return x ? ((Boolean) Scheme.numLss.apply2(this.i, AddOp.$Pl.apply2(this.n, this.count))).booleanValue() : x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 8) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: testing.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn12;
        LList pred$Mnlist;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:915");
            this.lambda$Fn12 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 9 ? lambda13(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public Object lambda13(Object runner) {
            Object result = Boolean.TRUE;
            for (Object l = this.pred$Mnlist; !lists.isNull(l); l = lists.cdr.apply1(l)) {
                if (Scheme.applyToArgs.apply2(lists.car.apply1(l), runner) == Boolean.FALSE) {
                    result = Boolean.FALSE;
                }
            }
            return result;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    public static Procedure $PcTestMatchAll$V(Object[] argsArray) {
        frame3 frame32 = new frame3();
        frame32.pred$Mnlist = LList.makeList(argsArray, 0);
        return frame32.lambda$Fn12;
    }

    /* compiled from: testing.scm */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn13;
        LList pred$Mnlist;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 10, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:931");
            this.lambda$Fn13 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 10 ? lambda14(obj) : super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public Object lambda14(Object runner) {
            Object result = Boolean.FALSE;
            for (Object l = this.pred$Mnlist; !lists.isNull(l); l = lists.cdr.apply1(l)) {
                if (Scheme.applyToArgs.apply2(lists.car.apply1(l), runner) != Boolean.FALSE) {
                    result = Boolean.TRUE;
                }
            }
            return result;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    public static Procedure $PcTestMatchAny$V(Object[] argsArray) {
        frame4 frame42 = new frame4();
        frame42.pred$Mnlist = LList.makeList(argsArray, 0);
        return frame42.lambda$Fn13;
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 70:
                return testResultKind$V(objArr);
            case 71:
                return isTestPassed$V(objArr);
            case 84:
                Object obj = objArr[0];
                int length = objArr.length - 1;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return testApply$V(obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 86:
                return $PcTestMatchAll$V(objArr);
            case 87:
                return $PcTestMatchAny$V(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object $PcTestAsSpecifier(Object specifier) {
        if (misc.isProcedure(specifier)) {
            return specifier;
        }
        if (numbers.isInteger(specifier)) {
            return $PcTestMatchNth(Lit13, specifier);
        }
        if (strings.isString(specifier)) {
            return testMatchName(specifier);
        }
        return misc.error$V("not a valid test specifier", new Object[0]);
    }

    /* compiled from: testing.scm */
    public class frame5 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object name;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 11, (Object) null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:971");
            this.lambda$Fn14 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 11) {
                return lambda15(obj) ? Boolean.TRUE : Boolean.FALSE;
            }
            return super.apply1(moduleMethod, obj);
        }

        /* access modifiers changed from: package-private */
        public boolean lambda15(Object runner) {
            return IsEqual.apply(this.name, testing.testRunnerTestName(runner));
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    public static Procedure testMatchName(Object name) {
        frame5 frame52 = new frame5();
        frame52.name = name;
        return frame52.lambda$Fn14;
    }

    public static Object testReadEvalString(Object string) {
        try {
            InPort port = ports.openInputString((CharSequence) string);
            Object form = ports.read(port);
            Object obj = string;
            if (ports.isEofObject(readchar.readChar.apply1(port))) {
                return Eval.eval.apply1(form);
            }
            return misc.error$V("(not at eof)", new Object[0]);
        } catch (ClassCastException e) {
            throw new WrongType(e, "open-input-string", 1, string);
        }
    }
}
