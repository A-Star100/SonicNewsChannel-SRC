package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Component;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.models.Box;
import gnu.kawa.models.Button;
import gnu.kawa.models.Display;
import gnu.kawa.models.Label;
import gnu.kawa.models.Model;
import gnu.kawa.models.Text;
import gnu.kawa.models.Window;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.xml.KAttr;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.misc;
import kawa.standard.Scheme;

/* compiled from: gui.scm */
public class gui extends ModuleBody {
    public static final gui $instance;
    public static final ModuleMethod Button;
    public static final ModuleMethod Column;
    public static final Macro Image;
    public static final ModuleMethod Label;
    static final Class Lit0 = Color.class;
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve());
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final SyntaxRules Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final SimpleSymbol Lit17;
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("cell-spacing").readResolve());
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final SyntaxRules Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
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
    static final IntNum Lit44;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SyntaxRules Lit8;
    static final SimpleSymbol Lit9;
    public static final ModuleMethod Row;
    public static final ModuleMethod Text;
    public static final ModuleMethod Window;
    public static final ModuleMethod as$Mncolor;
    public static final ModuleMethod button;
    public static final ModuleMethod image$Mnheight;
    public static final ModuleMethod image$Mnread;
    public static final ModuleMethod image$Mnwidth;
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtColumn$Gr;
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtRow$Gr;
    static final Location loc$$St$DtgetHeight;
    static final Location loc$$St$DtgetWidth;
    public static final Macro process$Mnkeywords;
    public static final Macro run$Mnapplication;
    public static final ModuleMethod set$Mncontent;

    static {
        SyntaxRule syntaxRule;
        IntNum make = IntNum.make(1);
        Lit44 = make;
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("value").readResolve();
        Lit43 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("name").readResolve();
        Lit42 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("invoke").readResolve();
        Lit41 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("getName").readResolve();
        Lit40 = simpleSymbol4;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
        Lit39 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("attr").readResolve();
        Lit38 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("<gnu.kawa.xml.KAttr>").readResolve();
        Lit37 = simpleSymbol7;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve();
        Lit36 = simpleSymbol8;
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("+").readResolve();
        Lit35 = simpleSymbol9;
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol("loop").readResolve();
        Lit34 = simpleSymbol10;
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("<object>").readResolve();
        Lit33 = simpleSymbol11;
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("primitive-array-get").readResolve();
        Lit32 = simpleSymbol12;
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve();
        Lit31 = simpleSymbol13;
        SimpleSymbol simpleSymbol14 = (SimpleSymbol) new SimpleSymbol("$lookup$").readResolve();
        Lit30 = simpleSymbol14;
        SimpleSymbol simpleSymbol15 = simpleSymbol;
        SimpleSymbol simpleSymbol16 = (SimpleSymbol) new SimpleSymbol("arg").readResolve();
        Lit29 = simpleSymbol16;
        SimpleSymbol simpleSymbol17 = simpleSymbol2;
        SimpleSymbol simpleSymbol18 = (SimpleSymbol) new SimpleSymbol("num-args").readResolve();
        Lit28 = simpleSymbol18;
        SimpleSymbol simpleSymbol19 = simpleSymbol3;
        SimpleSymbol simpleSymbol20 = (SimpleSymbol) new SimpleSymbol("i").readResolve();
        Lit27 = simpleSymbol20;
        SimpleSymbol simpleSymbol21 = simpleSymbol6;
        SimpleSymbol simpleSymbol22 = (SimpleSymbol) new SimpleSymbol("<int>").readResolve();
        Lit26 = simpleSymbol22;
        SimpleSymbol simpleSymbol23 = simpleSymbol7;
        SimpleSymbol simpleSymbol24 = (SimpleSymbol) new SimpleSymbol("::").readResolve();
        Lit25 = simpleSymbol24;
        IntNum intNum = make;
        SimpleSymbol simpleSymbol25 = simpleSymbol9;
        SimpleSymbol simpleSymbol26 = simpleSymbol4;
        SimpleSymbol simpleSymbol27 = (SimpleSymbol) new SimpleSymbol("run-application").readResolve();
        Lit23 = simpleSymbol27;
        SimpleSymbol simpleSymbol28 = simpleSymbol27;
        SimpleSymbol simpleSymbol29 = simpleSymbol8;
        SimpleSymbol simpleSymbol30 = simpleSymbol12;
        SimpleSymbol simpleSymbol31 = simpleSymbol16;
        SyntaxRules syntaxRules = new SyntaxRules(new Object[]{simpleSymbol27}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0003", new Object[]{PairWithPosition.make(simpleSymbol14, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.kawa.models.Window").readResolve(), Pair.make(Pair.make(simpleSymbol13, Pair.make((SimpleSymbol) new SimpleSymbol("open").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 749575)}, 0)}, 1);
        Lit24 = syntaxRules;
        SimpleSymbol simpleSymbol32 = (SimpleSymbol) new SimpleSymbol("Window").readResolve();
        Lit22 = simpleSymbol32;
        SimpleSymbol simpleSymbol33 = (SimpleSymbol) new SimpleSymbol("set-content").readResolve();
        Lit21 = simpleSymbol33;
        SimpleSymbol simpleSymbol34 = (SimpleSymbol) new SimpleSymbol("Column").readResolve();
        Lit20 = simpleSymbol34;
        SimpleSymbol simpleSymbol35 = (SimpleSymbol) new SimpleSymbol("Row").readResolve();
        Lit19 = simpleSymbol35;
        SimpleSymbol simpleSymbol36 = (SimpleSymbol) new SimpleSymbol("Text").readResolve();
        Lit18 = simpleSymbol36;
        SyntaxRules syntaxRules2 = syntaxRules;
        SimpleSymbol simpleSymbol37 = (SimpleSymbol) new SimpleSymbol("Label").readResolve();
        Lit17 = simpleSymbol37;
        SimpleSymbol simpleSymbol38 = simpleSymbol32;
        SimpleSymbol simpleSymbol39 = (SimpleSymbol) new SimpleSymbol("image-height").readResolve();
        Lit16 = simpleSymbol39;
        SimpleSymbol simpleSymbol40 = simpleSymbol33;
        SimpleSymbol simpleSymbol41 = (SimpleSymbol) new SimpleSymbol("image-width").readResolve();
        Lit15 = simpleSymbol41;
        SimpleSymbol simpleSymbol42 = simpleSymbol34;
        SimpleSymbol simpleSymbol43 = (SimpleSymbol) new SimpleSymbol("image-read").readResolve();
        Lit14 = simpleSymbol43;
        SimpleSymbol simpleSymbol44 = simpleSymbol35;
        SimpleSymbol simpleSymbol45 = simpleSymbol36;
        SimpleSymbol simpleSymbol46 = simpleSymbol37;
        SimpleSymbol simpleSymbol47 = simpleSymbol39;
        SimpleSymbol simpleSymbol48 = simpleSymbol41;
        SimpleSymbol simpleSymbol49 = simpleSymbol43;
        SyntaxRules syntaxRules3 = new SyntaxRules(new Object[]{(SimpleSymbol) new SimpleSymbol("text-field").readResolve()}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\u0003", new Object[0], 1), "\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0002", new Object[]{(SimpleSymbol) new SimpleSymbol("make").readResolve(), (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.DrawImage>").readResolve()}, 0)}, 1);
        Lit13 = syntaxRules3;
        SimpleSymbol simpleSymbol50 = (SimpleSymbol) new SimpleSymbol(Component.LISTVIEW_KEY_IMAGE).readResolve();
        Lit12 = simpleSymbol50;
        SimpleSymbol simpleSymbol51 = (SimpleSymbol) new SimpleSymbol("Button").readResolve();
        Lit11 = simpleSymbol51;
        SimpleSymbol simpleSymbol52 = (SimpleSymbol) new SimpleSymbol("button").readResolve();
        Lit10 = simpleSymbol52;
        SimpleSymbol simpleSymbol53 = (SimpleSymbol) new SimpleSymbol("as-color").readResolve();
        Lit9 = simpleSymbol53;
        SimpleSymbol simpleSymbol54 = simpleSymbol50;
        SimpleSymbol simpleSymbol55 = (SimpleSymbol) new SimpleSymbol("process-keywords").readResolve();
        Lit7 = simpleSymbol55;
        SimpleSymbol simpleSymbol56 = simpleSymbol51;
        SimpleSymbol simpleSymbol57 = simpleSymbol52;
        SimpleSymbol simpleSymbol58 = simpleSymbol53;
        SimpleSymbol simpleSymbol59 = simpleSymbol55;
        Object[] objArr = {simpleSymbol55};
        SyntaxPattern syntaxPattern = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4);
        SimpleSymbol simpleSymbol60 = simpleSymbol11;
        SimpleSymbol simpleSymbol61 = simpleSymbol30;
        PairWithPosition make2 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<gnu.expr.Keyword>").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 32797);
        SimpleSymbol simpleSymbol62 = simpleSymbol31;
        SimpleSymbol simpleSymbol63 = simpleSymbol29;
        SyntaxPattern syntaxPattern2 = syntaxPattern;
        SimpleSymbol simpleSymbol64 = simpleSymbol26;
        IntNum intNum2 = intNum;
        SimpleSymbol simpleSymbol65 = simpleSymbol25;
        SimpleSymbol simpleSymbol66 = simpleSymbol23;
        SimpleSymbol simpleSymbol67 = simpleSymbol21;
        PairWithPosition make3 = PairWithPosition.make(simpleSymbol67, PairWithPosition.make(simpleSymbol24, PairWithPosition.make(simpleSymbol66, PairWithPosition.make(simpleSymbol62, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 57388), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 57367), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 57364), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 57358);
        SimpleSymbol simpleSymbol68 = simpleSymbol19;
        SimpleSymbol simpleSymbol69 = simpleSymbol17;
        SimpleSymbol simpleSymbol70 = simpleSymbol15;
        new SyntaxRule(syntaxPattern2, "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\t\u000b\u0018,\b\u0011\u0018\u0004\u0011\u00184\u0011\u0018<\b\u0011\u0018D\u0011\u0018L\b\u0011\u0018\u0004a\b\u0011\u0018T\b\u0011\u0018\\\t\u000b\u0018d\b\u0011\u0018l©\u0011\u0018ty\t\u0013\t\u0003\u0011\u0018|\b\u0011\u0018\t\u000b\u0018\u0018\u0011\u0018i\u0011\u0018¤\u0011\u0018¬\b\t\u0013\t\u0003\u0018´\u0018¼\b\u0011\u0018Ä1\t\u001b\t\u0003\u0018Ì\u0018Ô", new Object[]{(SimpleSymbol) new SimpleSymbol("let").readResolve(), simpleSymbol18, simpleSymbol24, simpleSymbol22, (SimpleSymbol) new SimpleSymbol("field").readResolve(), PairWithPosition.make(PairWithPosition.make(simpleSymbol5, PairWithPosition.make((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_LENGTH).readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 16426), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 16426), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 16425), simpleSymbol10, PairWithPosition.make(PairWithPosition.make(simpleSymbol20, PairWithPosition.make(simpleSymbol24, PairWithPosition.make(simpleSymbol22, PairWithPosition.make(IntNum.make(0), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 20509), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 20503), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 20500), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 20497), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 20496), (SimpleSymbol) new SimpleSymbol("if").readResolve(), PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<").readResolve(), PairWithPosition.make(simpleSymbol20, PairWithPosition.make(simpleSymbol18, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 24593), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 24591), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 24588), simpleSymbol31, PairWithPosition.make(simpleSymbol61, PairWithPosition.make(simpleSymbol60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 28710), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 28689), PairWithPosition.make(simpleSymbol20, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 28725), (SimpleSymbol) new SimpleSymbol("cond").readResolve(), PairWithPosition.make(simpleSymbol63, PairWithPosition.make(simpleSymbol62, make2, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 32793), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 32782), PairWithPosition.make(PairWithPosition.make(simpleSymbol14, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.expr.Keyword").readResolve(), Pair.make(Pair.make(simpleSymbol13, Pair.make(simpleSymbol64, LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 40970), PairWithPosition.make(simpleSymbol62, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 40995), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 40969), PairWithPosition.make(simpleSymbol61, PairWithPosition.make(simpleSymbol60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 45087), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 45066), PairWithPosition.make(PairWithPosition.make(simpleSymbol65, PairWithPosition.make(simpleSymbol20, PairWithPosition.make(intNum2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 45107), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 45105), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 45102), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 45102), PairWithPosition.make(PairWithPosition.make(simpleSymbol10, PairWithPosition.make(PairWithPosition.make(simpleSymbol65, PairWithPosition.make(simpleSymbol20, PairWithPosition.make(IntNum.make(2), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 49170), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 49168), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 49165), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 49165), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 49159), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 49159), PairWithPosition.make(simpleSymbol63, PairWithPosition.make(simpleSymbol62, PairWithPosition.make(simpleSymbol66, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 53270), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 53266), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 53255), (SimpleSymbol) new SimpleSymbol("let*").readResolve(), PairWithPosition.make(make3, PairWithPosition.make(PairWithPosition.make(simpleSymbol69, PairWithPosition.make(simpleSymbol24, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<java.lang.String>").readResolve(), PairWithPosition.make(PairWithPosition.make(simpleSymbol68, PairWithPosition.make(simpleSymbol67, PairWithPosition.make(PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol64, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61489), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61489), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61488), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61483), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61475), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61475), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61456), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61453), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61447), PairWithPosition.make(PairWithPosition.make(simpleSymbol70, PairWithPosition.make(PairWithPosition.make(simpleSymbol68, PairWithPosition.make(simpleSymbol67, PairWithPosition.make(PairWithPosition.make(simpleSymbol5, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("getObjectValue").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65564), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65564), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65563), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65558), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65550), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65550), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65543), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 65543), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 61447), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 57357), PairWithPosition.make(simpleSymbol69, PairWithPosition.make(simpleSymbol70, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 69666), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 69661), PairWithPosition.make(PairWithPosition.make(simpleSymbol10, PairWithPosition.make(PairWithPosition.make(simpleSymbol65, PairWithPosition.make(simpleSymbol20, PairWithPosition.make(intNum2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 73746), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 73744), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 73741), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 73741), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 73735), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 73735), (SimpleSymbol) new SimpleSymbol("else").readResolve(), PairWithPosition.make(simpleSymbol62, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 81951), PairWithPosition.make(PairWithPosition.make(simpleSymbol10, PairWithPosition.make(PairWithPosition.make(simpleSymbol65, PairWithPosition.make(simpleSymbol20, PairWithPosition.make(intNum2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 86034), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 86032), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 86029), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 86029), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 86023), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 86023)}, 0);
        Object[] objArr2 = objArr;
        SyntaxRules syntaxRules4 = new SyntaxRules(objArr2, new SyntaxRule[]{syntaxRule}, 4);
        Lit8 = syntaxRules4;
        SimpleSymbol simpleSymbol71 = (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.Column>").readResolve();
        Lit6 = simpleSymbol71;
        SimpleSymbol simpleSymbol72 = (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.Row>").readResolve();
        Lit5 = simpleSymbol72;
        SimpleSymbol simpleSymbol73 = (SimpleSymbol) new SimpleSymbol("*.getHeight").readResolve();
        Lit4 = simpleSymbol73;
        SimpleSymbol simpleSymbol74 = (SimpleSymbol) new SimpleSymbol("*.getWidth").readResolve();
        Lit3 = simpleSymbol74;
        gui gui = new gui();
        $instance = gui;
        loc$$St$DtgetWidth = ThreadLocation.getInstance(simpleSymbol74, (Object) null);
        loc$$St$DtgetHeight = ThreadLocation.getInstance(simpleSymbol73, (Object) null);
        loc$$Lsgnu$Dtkawa$Dtmodels$DtRow$Gr = ThreadLocation.getInstance(simpleSymbol72, (Object) null);
        loc$$Lsgnu$Dtkawa$Dtmodels$DtColumn$Gr = ThreadLocation.getInstance(simpleSymbol71, (Object) null);
        process$Mnkeywords = Macro.make(simpleSymbol59, syntaxRules4, gui);
        as$Mncolor = new ModuleMethod(gui, 1, simpleSymbol58, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        button = new ModuleMethod(gui, 2, simpleSymbol57, -4096);
        Button = new ModuleMethod(gui, 3, simpleSymbol56, -4096);
        Image = Macro.make(simpleSymbol54, syntaxRules3, gui);
        image$Mnread = new ModuleMethod(gui, 4, simpleSymbol49, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        image$Mnwidth = new ModuleMethod(gui, 5, simpleSymbol48, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        image$Mnheight = new ModuleMethod(gui, 6, simpleSymbol47, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Label = new ModuleMethod(gui, 7, simpleSymbol46, -4096);
        Text = new ModuleMethod(gui, 8, simpleSymbol45, -4096);
        Row = new ModuleMethod(gui, 9, simpleSymbol44, -4096);
        Column = new ModuleMethod(gui, 10, simpleSymbol42, -4096);
        set$Mncontent = new ModuleMethod(gui, 11, simpleSymbol40, 8194);
        Window = new ModuleMethod(gui, 12, simpleSymbol38, -4096);
        run$Mnapplication = Macro.make(simpleSymbol28, syntaxRules2, gui);
        gui.run();
    }

    public gui() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Color asColor(Object value) {
        if (value instanceof Color) {
            return (Color) value;
        }
        if (value instanceof Integer) {
            try {
                return new Color(((Integer) value).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Integer.intValue()", 1, value);
            }
        } else if (value instanceof IntNum) {
            return new Color(IntNum.intValue(value));
        } else {
            return (Color) SlotGet.staticField.apply2(Lit0, value.toString());
        }
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                if (!(obj instanceof BufferedImage)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                if (!(obj instanceof BufferedImage)) {
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

    static Object buttonKeyword(Button button2, String name, Object value) {
        if (name == "foreground") {
            button2.setForeground(asColor(value));
        } else if (name == "background") {
            button2.setBackground(asColor(value));
        } else if (name == "action") {
            button2.setAction(value);
        } else if (name == PropertyTypeConstants.PROPERTY_TYPE_TEXT) {
            button2.setText(value == null ? null : value.toString());
        } else {
            boolean z = true;
            if (name == "disabled") {
                try {
                    if (value == Boolean.FALSE) {
                        z = false;
                    }
                    button2.setDisabled(z);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.kawa.models.Button.setDisabled(boolean)", 2, value);
                }
            } else {
                return misc.error$V(Format.formatToString(0, "unknown button attribute ~s", name), new Object[0]);
            }
        }
        return Values.empty;
    }

    static Boolean buttonNonKeyword(Button button2, Object arg) {
        return Boolean.TRUE;
    }

    public static Button button(Object... args) {
        Button button2 = new Button();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Keyword keyword = args[i];
            if (keyword instanceof Keyword) {
                try {
                    buttonKeyword(button2, keyword.getName(), args[i + 1]);
                    i += 2;
                    KAttr kAttr = keyword;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                }
            } else if (keyword instanceof KAttr) {
                try {
                    KAttr attr = keyword;
                    KAttr kAttr2 = keyword;
                    buttonKeyword(button2, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, (Object) keyword);
                }
            } else {
                buttonNonKeyword(button2, keyword);
                i++;
            }
        }
        return button2;
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 3:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 7:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 8:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 9:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 10:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 12:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Button Button(Object... args) {
        Button button2 = new Button();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Keyword keyword = args[i];
            if (keyword instanceof Keyword) {
                try {
                    buttonKeyword(button2, keyword.getName(), args[i + 1]);
                    i += 2;
                    KAttr kAttr = keyword;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                }
            } else if (keyword instanceof KAttr) {
                try {
                    KAttr attr = keyword;
                    KAttr kAttr2 = keyword;
                    buttonKeyword(button2, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, (Object) keyword);
                }
            } else {
                buttonNonKeyword(button2, keyword);
                i++;
            }
        }
        return button2;
    }

    public static BufferedImage imageRead(Path uri) {
        return ImageIO.read(uri.openInputStream());
    }

    public static int imageWidth(BufferedImage image) {
        try {
            return ((Number) Scheme.applyToArgs.apply2(loc$$St$DtgetWidth.get(), image)).intValue();
        } catch (UnboundLocationException image2) {
            image2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 74, 3);
            throw image2;
        }
    }

    public static int imageHeight(BufferedImage image) {
        try {
            return ((Number) Scheme.applyToArgs.apply2(loc$$St$DtgetHeight.get(), image)).intValue();
        } catch (UnboundLocationException image2) {
            image2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 77, 3);
            throw image2;
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return asColor(obj);
            case 4:
                try {
                    return imageRead(Path.valueOf(obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "image-read", 1, obj);
                }
            case 5:
                try {
                    return Integer.valueOf(imageWidth((BufferedImage) obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "image-width", 1, obj);
                }
            case 6:
                try {
                    return Integer.valueOf(imageHeight((BufferedImage) obj));
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "image-height", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    static Object labelKeyword(Label instance, String name, Object value) {
        if (name == Lit1) {
            instance.setText(value == null ? null : value.toString());
            return Values.empty;
        }
        return misc.error$V(Format.formatToString(0, "unknown label attribute ~s", name), new Object[0]);
    }

    static void labelNonKeyword(Label instance, Object arg) {
        instance.setText(arg == null ? null : arg.toString());
    }

    public static Label Label(Object... args) {
        Label instance = new Label();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Keyword keyword = args[i];
            if (keyword instanceof Keyword) {
                try {
                    labelKeyword(instance, keyword.getName(), args[i + 1]);
                    i += 2;
                    KAttr kAttr = keyword;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                }
            } else if (keyword instanceof KAttr) {
                try {
                    KAttr attr = keyword;
                    KAttr kAttr2 = keyword;
                    labelKeyword(instance, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, (Object) keyword);
                }
            } else {
                labelNonKeyword(instance, keyword);
                i++;
            }
        }
        return instance;
    }

    static Object textKeyword(Text instance, String name, Object value) {
        if (name == Lit1) {
            instance.setText(value == null ? null : value.toString());
            return Values.empty;
        }
        return misc.error$V(Format.formatToString(0, "unknown text attribute ~s", name), new Object[0]);
    }

    static void textNonKeyword(Text instance, Object arg) {
        instance.setText(arg == null ? null : arg.toString());
    }

    public static Text Text(Object... args) {
        Text instance = new Text();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Keyword keyword = args[i];
            if (keyword instanceof Keyword) {
                try {
                    textKeyword(instance, keyword.getName(), args[i + 1]);
                    i += 2;
                    KAttr kAttr = keyword;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                }
            } else if (keyword instanceof KAttr) {
                try {
                    KAttr attr = keyword;
                    KAttr kAttr2 = keyword;
                    textKeyword(instance, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, (Object) keyword);
                }
            } else {
                textNonKeyword(instance, keyword);
                i++;
            }
        }
        return instance;
    }

    static Object boxKeyword(Box instance, String name, Object value) {
        if (name == Lit2) {
            instance.setCellSpacing(value);
            return Values.empty;
        }
        return misc.error$V(Format.formatToString(0, "unknown box attribute ~s", name), new Object[0]);
    }

    static Model asModel(Object arg) {
        return Display.getInstance().coerceToModel(arg);
    }

    static void boxNonKeyword(Box box, Object arg) {
        box.add(asModel(arg));
    }

    public static Object Row(Object... args) {
        try {
            Object instance = Invoke.make.apply1(loc$$Lsgnu$Dtkawa$Dtmodels$DtRow$Gr.get());
            int num$Mnargs = args.length;
            int i = 0;
            while (i < num$Mnargs) {
                Keyword keyword = args[i];
                if (keyword instanceof Keyword) {
                    try {
                        try {
                            boxKeyword((Box) instance, keyword.getName(), args[i + 1]);
                            i += 2;
                            KAttr kAttr = keyword;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "box-keyword", 0, instance);
                    }
                } else if (keyword instanceof KAttr) {
                    try {
                        KAttr attr = keyword;
                        KAttr kAttr2 = keyword;
                        try {
                            boxKeyword((Box) instance, attr.getName(), attr.getObjectValue());
                            i++;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "box-keyword", 0, instance);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "attr", -2, (Object) keyword);
                    }
                } else {
                    try {
                        boxNonKeyword((Box) instance, keyword);
                        i++;
                        Object obj = instance;
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "box-non-keyword", 0, instance);
                    }
                }
            }
            return instance;
        } catch (UnboundLocationException e6) {
            e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 146, 25);
            throw e6;
        }
    }

    public static Object Column(Object... args) {
        try {
            Object instance = Invoke.make.apply1(loc$$Lsgnu$Dtkawa$Dtmodels$DtColumn$Gr.get());
            int num$Mnargs = args.length;
            int i = 0;
            while (i < num$Mnargs) {
                Keyword keyword = args[i];
                if (keyword instanceof Keyword) {
                    try {
                        try {
                            boxKeyword((Box) instance, keyword.getName(), args[i + 1]);
                            i += 2;
                            KAttr kAttr = keyword;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "box-keyword", 0, instance);
                    }
                } else if (keyword instanceof KAttr) {
                    try {
                        KAttr attr = keyword;
                        KAttr kAttr2 = keyword;
                        try {
                            boxKeyword((Box) instance, attr.getName(), attr.getObjectValue());
                            i++;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "box-keyword", 0, instance);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "attr", -2, (Object) keyword);
                    }
                } else {
                    try {
                        boxNonKeyword((Box) instance, keyword);
                        i++;
                        Object obj = instance;
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "box-non-keyword", 0, instance);
                    }
                }
            }
            return instance;
        } catch (UnboundLocationException e6) {
            e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/gui.scm", 151, 25);
            throw e6;
        }
    }

    public static void setContent(Window window, Object pane) {
        window.setContent(pane);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        if (moduleMethod.selector != 11) {
            return super.apply2(moduleMethod, obj, obj2);
        }
        try {
            setContent((Window) obj, obj2);
            return Values.empty;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-content", 1, obj);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 11) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        if (!(obj instanceof Window)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    static Object windowKeyword(Window instance, String name, Object value) {
        if (name == "title") {
            instance.setTitle(value == null ? null : value.toString());
        } else if (name == "content") {
            instance.setContent(value);
        } else if (name == "menubar") {
            instance.setMenuBar(value);
        } else {
            return misc.error$V(Format.formatToString(0, "unknown window attribute ~s", name), new Object[0]);
        }
        return Values.empty;
    }

    static void windowNonKeyword(Window instance, Object arg) {
        instance.setContent(arg);
    }

    public static Window Window(Object... args) {
        Window instance = Display.getInstance().makeWindow();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Keyword keyword = args[i];
            if (keyword instanceof Keyword) {
                try {
                    windowKeyword(instance, keyword.getName(), args[i + 1]);
                    i += 2;
                    KAttr kAttr = keyword;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, (Object) keyword);
                }
            } else if (keyword instanceof KAttr) {
                try {
                    KAttr attr = keyword;
                    KAttr kAttr2 = keyword;
                    windowKeyword(instance, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, (Object) keyword);
                }
            } else {
                windowNonKeyword(instance, keyword);
                i++;
            }
        }
        return instance;
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 2:
                return button(objArr);
            case 3:
                return Button(objArr);
            case 7:
                return Label(objArr);
            case 8:
                return Text(objArr);
            case 9:
                return Row(objArr);
            case 10:
                return Column(objArr);
            case 12:
                return Window(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
