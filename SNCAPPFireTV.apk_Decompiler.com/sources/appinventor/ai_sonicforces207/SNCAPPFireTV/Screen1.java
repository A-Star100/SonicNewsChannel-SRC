package appinventor.ai_sonicforces207.SNCAPPFireTV;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.CircularProgress;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.File;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.TextToSpeech;
import com.google.appinventor.components.runtime.WebViewer;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;

/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen1").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("Button9$Click").readResolve());
    static final FString Lit101 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("HomeUrl").readResolve());
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$PageLoaded").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("PageLoaded").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$BeforePageLoad").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("BeforePageLoad").readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("TextToSpeech1").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("Speak").readResolve());
    static final PairWithPosition Lit111;
    static final PairWithPosition Lit112;
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$ErrorOccurred").readResolve());
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("ErrorOccurred").readResolve());
    static final FString Lit115 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final IntNum Lit116 = IntNum.make(16777215);
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit118;
    static final FString Lit119 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("$choice").readResolve());
    static final PairWithPosition Lit121;
    static final PairWithPosition Lit122;
    static final PairWithPosition Lit123;
    static final PairWithPosition Lit124;
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("ShowTextDialog").readResolve());
    static final PairWithPosition Lit126;
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("Notifier1$AfterChoosing").readResolve());
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("AfterChoosing").readResolve());
    static final FString Lit129 = new FString("com.google.appinventor.components.runtime.TextToSpeech");
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final FString Lit130 = new FString("com.google.appinventor.components.runtime.TextToSpeech");
    static final FString Lit131 = new FString("com.google.appinventor.components.runtime.File");
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("DefaultScope").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("WritePermission").readResolve());
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.File");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit136 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit137 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit138 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit139 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit140 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit141 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit143 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit145 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit146 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit147 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit148 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit149;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("VersionCode").readResolve());
    static final IntNum Lit17 = IntNum.make(15);
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("number").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("VersionName").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final FString Lit20 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit23 = IntNum.make(3);
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit25 = IntNum.make(16777215);
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit27 = IntNum.make(-2);
    static final FString Lit28 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit29 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$FileSaveData").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit32 = IntNum.make(1);
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit34 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("WebViewer1").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("GoHome").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit39 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("AboutScreen").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("Button5").readResolve());
    static final FString Lit41 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("Notifier1").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("ShowMessageDialog").readResolve());
    static final PairWithPosition Lit44;
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("GoToUrl").readResolve());
    static final PairWithPosition Lit46;
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("Button5$Click").readResolve());
    static final FString Lit48 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final SimpleSymbol Lit5;
    static final FString Lit50 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("ClearCaches").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("ClearCookies").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("ClearLocations").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve());
    static final FString Lit55 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("Button4").readResolve());
    static final FString Lit57 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("File1").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("Scope").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit60;
    static final PairWithPosition Lit61;
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("Exists").readResolve());
    static final PairWithPosition Lit63;
    static final PairWithPosition Lit64;
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("SaveFile").readResolve());
    static final PairWithPosition Lit66;
    static final PairWithPosition Lit67;
    static final PairWithPosition Lit68;
    static final PairWithPosition Lit69;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("ShowChooseDialog").readResolve());
    static final PairWithPosition Lit71;
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve());
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("CircularProgress1").readResolve());
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit76 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve());
    static final IntNum Lit78 = IntNum.make(16777215);
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final FString Lit80 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("Button7").readResolve());
    static final FString Lit82 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("RunJavaScript").readResolve());
    static final PairWithPosition Lit84;
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("Button7$Click").readResolve());
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol("Button6").readResolve());
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit89;
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("CloseScreenAnimation").readResolve());
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("Button6$Click").readResolve());
    static final FString Lit91 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("Button8").readResolve());
    static final FString Lit93 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit94;
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("Button8$Click").readResolve());
    static final FString Lit96 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("Button9").readResolve());
    static final FString Lit98 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit99;
    public static Screen1 Screen1;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public Button Button3;
    public final ModuleMethod Button3$Click;
    public Button Button4;
    public final ModuleMethod Button4$Click;
    public Button Button5;
    public final ModuleMethod Button5$Click;
    public Button Button6;
    public final ModuleMethod Button6$Click;
    public Button Button7;
    public final ModuleMethod Button7$Click;
    public Button Button8;
    public final ModuleMethod Button8$Click;
    public Button Button9;
    public final ModuleMethod Button9$Click;
    public CircularProgress CircularProgress1;
    public File File1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public Notifier Notifier1;
    public final ModuleMethod Notifier1$AfterChoosing;
    public TextToSpeech TextToSpeech1;
    public WebViewer WebViewer1;
    public final ModuleMethod WebViewer1$BeforePageLoad;
    public final ModuleMethod WebViewer1$ErrorOccurred;
    public final ModuleMethod WebViewer1$PageLoaded;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public final ModuleMethod dispatchGenericEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public final ModuleMethod get$Mnsimple$Mnname;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod onCreate;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("any").readResolve();
        Lit149 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit5 = simpleSymbol2;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit7 = simpleSymbol3;
        Lit126 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635528), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635523), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635517);
        Lit124 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635377), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635372);
        Lit123 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635269), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635264), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635258);
        Lit122 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635113), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 635107);
        Lit121 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 634979), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 634974);
        int[] iArr = new int[2];
        iArr[0] = -16777216;
        Lit118 = IntNum.make(iArr);
        Lit112 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 590069), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 590064), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 590058);
        Lit111 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 589919);
        Lit99 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 536713);
        Lit94 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 499848);
        Lit89 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 462977);
        Lit84 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 426115);
        Lit71 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 325275), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 325270), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 325265), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 325260), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 325254);
        Lit69 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 325004), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324999);
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("com.google.appinventor.components.common.FileScopeEnum").readResolve();
        Lit60 = simpleSymbol4;
        Lit68 = PairWithPosition.make(simpleSymbol4, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324987), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324931);
        Lit67 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324675), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324670), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324664);
        Lit66 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324456), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324450);
        Lit64 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324322), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324317);
        Lit63 = PairWithPosition.make(simpleSymbol4, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324305), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324249);
        Lit61 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 324002), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 323997), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 323991);
        Lit46 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 233797);
        Lit44 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 233674), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 233669), "/tmp/1719981813109_0.4259653065647817-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPPFireTV/Screen1.yail", 233663);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit135, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit136, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit137, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit138, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit139, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit140, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit141, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit142, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit143, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit144, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit145, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit146, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit147, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit148, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime11706901263022568124.scm:634");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, (Object) null, 0);
        this.Button3$Click = new ModuleMethod(frame2, 26, Lit37, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, (Object) null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 28, (Object) null, 0);
        this.Button5$Click = new ModuleMethod(frame2, 29, Lit47, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 30, (Object) null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 31, (Object) null, 0);
        this.Button1$Click = new ModuleMethod(frame2, 32, Lit54, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 33, (Object) null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 34, (Object) null, 0);
        this.Button4$Click = new ModuleMethod(frame2, 35, Lit72, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 36, (Object) null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 37, (Object) null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 38, (Object) null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 39, (Object) null, 0);
        this.Button7$Click = new ModuleMethod(frame2, 40, Lit85, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 41, (Object) null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 42, (Object) null, 0);
        this.Button6$Click = new ModuleMethod(frame2, 43, Lit90, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 44, (Object) null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 45, (Object) null, 0);
        this.Button8$Click = new ModuleMethod(frame2, 46, Lit95, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 47, (Object) null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 48, (Object) null, 0);
        this.Button9$Click = new ModuleMethod(frame2, 49, Lit100, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 50, (Object) null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 51, (Object) null, 0);
        this.WebViewer1$PageLoaded = new ModuleMethod(frame2, 52, Lit105, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.WebViewer1$BeforePageLoad = new ModuleMethod(frame2, 53, Lit107, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.WebViewer1$ErrorOccurred = new ModuleMethod(frame2, 54, Lit113, 12291);
        lambda$Fn26 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 56, (Object) null, 0);
        this.Notifier1$AfterChoosing = new ModuleMethod(frame2, 57, Lit127, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn28 = new ModuleMethod(frame2, 58, (Object) null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 59, (Object) null, 0);
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            SimpleSymbol simpleSymbol = Lit0;
            this.form$Mnenvironment = Environment.make(misc.symbol$To$String(simpleSymbol));
            FString stringAppend = strings.stringAppend(misc.symbol$To$String(simpleSymbol), "-global-vars");
            this.global$Mnvar$Mnenvironment = Environment.make(stringAppend == null ? null : stringAppend.toString());
            Screen1 = null;
            this.form$Mnname$Mnsymbol = simpleSymbol;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            Object find2 = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find2).run();
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, "<html><body><a href=\"https://ibb.co/Pz6FK6b\"><img src=\"https://i.ibb.co/x87XW7c/Screen-Shot-2024-06-23-at-5-33-20-PM.png\" alt=\"Screen-Shot-2024-06-23-at-5-33-20-PM\" border=\"0\"></a>"), $result);
                } else {
                    addToGlobalVars(Lit3, lambda$Fn2);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    SimpleSymbol simpleSymbol2 = Lit4;
                    SimpleSymbol simpleSymbol3 = Lit5;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, "Screen for browser", simpleSymbol3);
                    SimpleSymbol simpleSymbol4 = Lit6;
                    Boolean bool = Boolean.TRUE;
                    SimpleSymbol simpleSymbol5 = Lit7;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol4, bool, simpleSymbol5);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit8, "Sonic Channel ", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit9, "zoom", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit10, "162-1629457_classic-sonic-sonic-the-hedgehog-classic-sonic.png", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit11, "zoom", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit12, "unspecified", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit13, Boolean.TRUE, simpleSymbol5);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit14, "Responsive", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit15, "Sonic Channel - Android", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit16, Lit17, Lit18);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit19, "1.5 (Sky)", simpleSymbol3), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn3));
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit20, Lit21, lambda$Fn4), $result);
                } else {
                    addToComponents(simpleSymbol, Lit28, Lit21, lambda$Fn5);
                }
                this.Button3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit29, Lit30, lambda$Fn6), $result);
                } else {
                    addToComponents(Lit21, Lit34, Lit30, lambda$Fn7);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit37, this.Button3$Click);
                } else {
                    addToFormEnvironment(Lit37, this.Button3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
                } else {
                    addToEvents(Lit30, Lit38);
                }
                this.Button5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit39, Lit40, lambda$Fn8), $result);
                } else {
                    addToComponents(Lit21, Lit41, Lit40, lambda$Fn9);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit47, this.Button5$Click);
                } else {
                    addToFormEnvironment(Lit47, this.Button5$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button5", "Click");
                } else {
                    addToEvents(Lit40, Lit38);
                }
                this.Button1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit48, Lit49, lambda$Fn10), $result);
                } else {
                    addToComponents(Lit21, Lit50, Lit49, lambda$Fn11);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit54, this.Button1$Click);
                } else {
                    addToFormEnvironment(Lit54, this.Button1$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
                } else {
                    addToEvents(Lit49, Lit38);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit55, Lit56, lambda$Fn12), $result);
                } else {
                    addToComponents(Lit21, Lit57, Lit56, lambda$Fn13);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit72, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit72, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit56, Lit38);
                }
                this.CircularProgress1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit73, Lit74, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit21, Lit75, Lit74, Boolean.FALSE);
                }
                this.HorizontalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit76, Lit77, lambda$Fn14), $result);
                } else {
                    addToComponents(simpleSymbol, Lit79, Lit77, lambda$Fn15);
                }
                this.Button7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit77, Lit80, Lit81, lambda$Fn16), $result);
                } else {
                    addToComponents(Lit77, Lit82, Lit81, lambda$Fn17);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit85, this.Button7$Click);
                } else {
                    addToFormEnvironment(Lit85, this.Button7$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button7", "Click");
                } else {
                    addToEvents(Lit81, Lit38);
                }
                this.Button6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit77, Lit86, Lit87, lambda$Fn18), $result);
                } else {
                    addToComponents(Lit77, Lit88, Lit87, lambda$Fn19);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit90, this.Button6$Click);
                } else {
                    addToFormEnvironment(Lit90, this.Button6$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button6", "Click");
                } else {
                    addToEvents(Lit87, Lit38);
                }
                this.Button8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit77, Lit91, Lit92, lambda$Fn20), $result);
                } else {
                    addToComponents(Lit77, Lit93, Lit92, lambda$Fn21);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit95, this.Button8$Click);
                } else {
                    addToFormEnvironment(Lit95, this.Button8$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button8", "Click");
                } else {
                    addToEvents(Lit92, Lit38);
                }
                this.Button9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit77, Lit96, Lit97, lambda$Fn22), $result);
                } else {
                    addToComponents(Lit77, Lit98, Lit97, lambda$Fn23);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit100, this.Button9$Click);
                } else {
                    addToFormEnvironment(Lit100, this.Button9$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button9", "Click");
                } else {
                    addToEvents(Lit97, Lit38);
                }
                this.WebViewer1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit101, Lit35, lambda$Fn24), $result);
                } else {
                    addToComponents(simpleSymbol, Lit103, Lit35, lambda$Fn25);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit105, this.WebViewer1$PageLoaded);
                } else {
                    addToFormEnvironment(Lit105, this.WebViewer1$PageLoaded);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "PageLoaded");
                } else {
                    addToEvents(Lit35, Lit106);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit107, this.WebViewer1$BeforePageLoad);
                } else {
                    addToFormEnvironment(Lit107, this.WebViewer1$BeforePageLoad);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "BeforePageLoad");
                } else {
                    addToEvents(Lit35, Lit108);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit113, this.WebViewer1$ErrorOccurred);
                } else {
                    addToFormEnvironment(Lit113, this.WebViewer1$ErrorOccurred);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "ErrorOccurred");
                } else {
                    addToEvents(Lit35, Lit114);
                }
                this.Notifier1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit115, Lit42, lambda$Fn26), $result);
                } else {
                    addToComponents(simpleSymbol, Lit119, Lit42, lambda$Fn27);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit127, this.Notifier1$AfterChoosing);
                } else {
                    addToFormEnvironment(Lit127, this.Notifier1$AfterChoosing);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Notifier1", "AfterChoosing");
                } else {
                    addToEvents(Lit42, Lit128);
                }
                this.TextToSpeech1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit129, Lit109, Boolean.FALSE), $result);
                } else {
                    addToComponents(simpleSymbol, Lit130, Lit109, Boolean.FALSE);
                }
                this.File1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit131, Lit58, lambda$Fn28), $result);
                } else {
                    addToComponents(simpleSymbol, Lit134, Lit58, lambda$Fn29);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static String lambda3() {
        return "<html><body><a href=\"https://ibb.co/Pz6FK6b\"><img src=\"https://i.ibb.co/x87XW7c/Screen-Shot-2024-06-23-at-5-33-20-PM.png\" alt=\"Screen-Shot-2024-06-23-at-5-33-20-PM\" border=\"0\"></a>";
    }

    static Object lambda4() {
        SimpleSymbol simpleSymbol = Lit0;
        SimpleSymbol simpleSymbol2 = Lit4;
        SimpleSymbol simpleSymbol3 = Lit5;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, "Screen for browser", simpleSymbol3);
        SimpleSymbol simpleSymbol4 = Lit6;
        Boolean bool = Boolean.TRUE;
        SimpleSymbol simpleSymbol5 = Lit7;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol4, bool, simpleSymbol5);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit8, "Sonic Channel ", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit9, "zoom", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit10, "162-1629457_classic-sonic-sonic-the-hedgehog-classic-sonic.png", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit11, "zoom", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit12, "unspecified", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit13, Boolean.TRUE, simpleSymbol5);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit14, "Responsive", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit15, "Sonic Channel - Android", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit16, Lit17, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit19, "1.5 (Sky)", simpleSymbol3);
    }

    static Object lambda5() {
        SimpleSymbol simpleSymbol = Lit21;
        SimpleSymbol simpleSymbol2 = Lit22;
        IntNum intNum = Lit23;
        SimpleSymbol simpleSymbol3 = Lit18;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit24, Lit25, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit26, Lit27, simpleSymbol3);
    }

    static Object lambda6() {
        SimpleSymbol simpleSymbol = Lit21;
        SimpleSymbol simpleSymbol2 = Lit22;
        IntNum intNum = Lit23;
        SimpleSymbol simpleSymbol3 = Lit18;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit24, Lit25, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit26, Lit27, simpleSymbol3);
    }

    static Object lambda7() {
        SimpleSymbol simpleSymbol = Lit30;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Go Home", Lit5);
    }

    static Object lambda8() {
        SimpleSymbol simpleSymbol = Lit30;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Go Home", Lit5);
    }

    public Object Button3$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit35, Lit36, LList.Empty, LList.Empty);
    }

    static Object lambda10() {
        SimpleSymbol simpleSymbol = Lit40;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Go to regular website", Lit5);
    }

    static Object lambda9() {
        SimpleSymbol simpleSymbol = Lit40;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Go to regular website", Lit5);
    }

    public Object Button5$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit42, Lit43, LList.list3("Depending on your device's capabilities, you may not be able to browse the site without lag", "Warning", "OK"), Lit44);
        return runtime.callComponentMethod(Lit35, Lit45, LList.list1("https://sites.google.com/view/sonicnews-snc/home"), Lit46);
    }

    static Object lambda11() {
        SimpleSymbol simpleSymbol = Lit49;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Clear All Data Stored", Lit5);
    }

    static Object lambda12() {
        SimpleSymbol simpleSymbol = Lit49;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Clear All Data Stored", Lit5);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit35;
        runtime.callComponentMethod(simpleSymbol, Lit51, LList.Empty, LList.Empty);
        runtime.callComponentMethod(simpleSymbol, Lit52, LList.Empty, LList.Empty);
        return runtime.callComponentMethod(simpleSymbol, Lit53, LList.Empty, LList.Empty);
    }

    static Object lambda13() {
        SimpleSymbol simpleSymbol = Lit56;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Write an HTML ", Lit5);
    }

    static Object lambda14() {
        SimpleSymbol simpleSymbol = Lit56;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit31, Lit32, Lit18);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit33, "Write an HTML ", Lit5);
    }

    public Object Button4$Click() {
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit58;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit59, FileScope.Shared, Lit60);
        SimpleSymbol simpleSymbol2 = Lit42;
        SimpleSymbol simpleSymbol3 = Lit43;
        runtime.callComponentMethod(simpleSymbol2, simpleSymbol3, LList.list3("Before you attempt to save the HTML file, please ensure permissions for writing to external storage is enabled.", "Warning", "OK, I'll enable permissions"), Lit61);
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol4 = Lit62;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethodWithBlockingContinuation(simpleSymbol, simpleSymbol4, LList.list2(FileScope.Shared, "/Download/snc.html"), Lit63), Boolean.FALSE), Lit64, "=") != Boolean.FALSE) {
            runtime.callComponentMethod(simpleSymbol, Lit65, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), "/Download/snc.html"), Lit66);
            return runtime.callComponentMethod(simpleSymbol2, simpleSymbol3, LList.list3("The HTML was created. Open this in your browser. This HTML does not refer to the entire page.", "File has been created", "OK"), Lit67);
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.callComponentMethodWithBlockingContinuation(simpleSymbol, simpleSymbol4, LList.list2(FileScope.Shared, "/Download/snc.html"), Lit68), Boolean.TRUE), Lit69, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            SimpleSymbol simpleSymbol5 = Lit70;
            Pair list1 = LList.list1("An HTML of the same name (snc.html) already exists. Would you like to delete the original and replace it or don't do anything.");
            LList.chain4(list1, "Warning", "Yes", "No", Boolean.FALSE);
            return runtime.callComponentMethod(simpleSymbol2, simpleSymbol5, list1, Lit71);
        }
    }

    static Object lambda15() {
        SimpleSymbol simpleSymbol = Lit77;
        SimpleSymbol simpleSymbol2 = Lit22;
        IntNum intNum = Lit23;
        SimpleSymbol simpleSymbol3 = Lit18;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit24, Lit78, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit26, Lit27, simpleSymbol3);
    }

    static Object lambda16() {
        SimpleSymbol simpleSymbol = Lit77;
        SimpleSymbol simpleSymbol2 = Lit22;
        IntNum intNum = Lit23;
        SimpleSymbol simpleSymbol3 = Lit18;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit24, Lit78, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit26, Lit27, simpleSymbol3);
    }

    static Object lambda17() {
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit33, "Up", Lit5);
    }

    static Object lambda18() {
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit33, "Up", Lit5);
    }

    public Object Button7$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit35, Lit83, LList.list1("function scrollToTop() {   window.scrollTo(0, 0); }"), Lit84);
    }

    static Object lambda19() {
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit33, "Down", Lit5);
    }

    static Object lambda20() {
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit33, "Down", Lit5);
    }

    public Object Button6$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit35, Lit83, LList.list1("function scrollWin() {   window.scrollBy(0, 100);"), Lit89);
    }

    static Object lambda21() {
        return runtime.setAndCoerceProperty$Ex(Lit92, Lit33, "Left", Lit5);
    }

    static Object lambda22() {
        return runtime.setAndCoerceProperty$Ex(Lit92, Lit33, "Left", Lit5);
    }

    public Object Button8$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit35, Lit83, LList.list1("document.getElementById(\"container\").scrollLeft += 20;"), Lit94);
    }

    static Object lambda23() {
        return runtime.setAndCoerceProperty$Ex(Lit97, Lit33, "Right", Lit5);
    }

    static Object lambda24() {
        return runtime.setAndCoerceProperty$Ex(Lit97, Lit33, "Right", Lit5);
    }

    public Object Button9$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit35, Lit83, LList.list1("document.getElementById(\"container\").scrollRight += 20;"), Lit99);
    }

    static Object lambda25() {
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit102, "https://sites.google.com/view/snc-mobile/home", Lit5);
    }

    static Object lambda26() {
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit102, "https://sites.google.com/view/snc-mobile/home", Lit5);
    }

    public Object WebViewer1$PageLoaded(Object $url) {
        runtime.sanitizeComponentData($url);
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit104, Boolean.FALSE, Lit7);
    }

    public Object WebViewer1$BeforePageLoad(Object $url) {
        runtime.sanitizeComponentData($url);
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit104, Boolean.TRUE, Lit7);
    }

    public Object WebViewer1$ErrorOccurred(Object $errorCode, Object $description, Object $failingUrl) {
        runtime.sanitizeComponentData($errorCode);
        runtime.sanitizeComponentData($description);
        runtime.sanitizeComponentData($failingUrl);
        runtime.setThisForm();
        runtime.callComponentMethod(Lit109, Lit110, LList.list1("An error has occured"), Lit111);
        return runtime.callComponentMethod(Lit42, Lit43, LList.list3("Page could not load. Please reload app.", "Error", "OK"), Lit112);
    }

    static Object lambda27() {
        SimpleSymbol simpleSymbol = Lit42;
        SimpleSymbol simpleSymbol2 = Lit24;
        IntNum intNum = Lit116;
        SimpleSymbol simpleSymbol3 = Lit18;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit117, Lit118, simpleSymbol3);
    }

    static Object lambda28() {
        SimpleSymbol simpleSymbol = Lit42;
        SimpleSymbol simpleSymbol2 = Lit24;
        IntNum intNum = Lit116;
        SimpleSymbol simpleSymbol3 = Lit18;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit117, Lit118, simpleSymbol3);
    }

    public Object Notifier1$AfterChoosing(Object $choice) {
        Object obj;
        Object obj2;
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list3;
        PairWithPosition pairWithPosition;
        Object $choice2 = runtime.sanitizeComponentData($choice);
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        if ($choice2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit120), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $choice2;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(obj, "Yes"), Lit121, "=") != Boolean.FALSE) {
            runtime.callComponentMethod(Lit58, Lit65, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), "/Download/snc.html"), Lit122);
            simpleSymbol = Lit42;
            simpleSymbol2 = Lit43;
            list3 = LList.list3("Open the HTML in your browser!", "File has been created", "OK");
            pairWithPosition = Lit123;
        } else {
            ModuleMethod moduleMethod2 = runtime.yail$Mnequal$Qu;
            if ($choice2 instanceof Package) {
                obj2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit120), " is not bound in the current context"), "Unbound Variable");
            } else {
                obj2 = $choice2;
            }
            if (runtime.callYailPrimitive(moduleMethod2, LList.list2(obj2, "No"), Lit124, "=") == Boolean.FALSE) {
                return Values.empty;
            }
            simpleSymbol = Lit42;
            simpleSymbol2 = Lit125;
            list3 = LList.list3(":) Your choice = done!", "File Not Made :)", Boolean.TRUE);
            pairWithPosition = Lit126;
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list3, pairWithPosition);
    }

    static Object lambda29() {
        SimpleSymbol simpleSymbol = Lit58;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit132, "Shared", Lit60);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit133, Boolean.TRUE, Lit7);
    }

    static Object lambda30() {
        SimpleSymbol simpleSymbol = Lit58;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit132, "Shared", Lit60);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit133, Boolean.TRUE, Lit7);
    }

    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main;

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 54 ? this.$main.WebViewer1$ErrorOccurred(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 54) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 7:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 14:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 52:
                case 53:
                case 57:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 8:
                    if (!(obj instanceof Symbol)) {
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
                case 11:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 15:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    if (!(obj4 instanceof String)) {
                        return -786428;
                    }
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 16:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return this.$main.getSimpleName(obj);
                case 2:
                    try {
                        this.$main.onCreate((Bundle) obj);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "onCreate", 1, obj);
                    }
                case 3:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 7:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "is-bound-in-form-environment", 1, obj);
                    }
                case 12:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case 13:
                    this.$main.sendError(obj);
                    return Values.empty;
                case 14:
                    this.$main.processException(obj);
                    return Values.empty;
                case 52:
                    return this.$main.WebViewer1$PageLoaded(obj);
                case 53:
                    return this.$main.WebViewer1$BeforePageLoad(obj);
                case 57:
                    return this.$main.Notifier1$AfterChoosing(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            boolean z = true;
            switch (moduleMethod.selector) {
                case 10:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case 15:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "dispatchEvent", 1, obj);
                    }
                case 16:
                    Screen1 screen1 = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    screen1.dispatchGenericEvent(component, str, z, (Object[]) obj4);
                                    return Values.empty;
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "dispatchGenericEvent", 4, obj4);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "dispatchGenericEvent", 3, obj3);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "dispatchGenericEvent", 2, obj2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "dispatchGenericEvent", 1, obj);
                    }
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 4:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 8:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "add-to-global-var-environment", 1, obj);
                    }
                case 9:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case 11:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case 17:
                    return this.$main.lookupHandler(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Screen1.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen1.lambda3();
                case 21:
                    return Screen1.lambda4();
                case 22:
                    return Screen1.lambda5();
                case 23:
                    return Screen1.lambda6();
                case 24:
                    return Screen1.lambda7();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return this.$main.Button3$Click();
                case 27:
                    return Screen1.lambda9();
                case 28:
                    return Screen1.lambda10();
                case 29:
                    return this.$main.Button5$Click();
                case 30:
                    return Screen1.lambda11();
                case 31:
                    return Screen1.lambda12();
                case 32:
                    return this.$main.Button1$Click();
                case 33:
                    return Screen1.lambda13();
                case 34:
                    return Screen1.lambda14();
                case 35:
                    return this.$main.Button4$Click();
                case 36:
                    return Screen1.lambda15();
                case 37:
                    return Screen1.lambda16();
                case 38:
                    return Screen1.lambda17();
                case 39:
                    return Screen1.lambda18();
                case 40:
                    return this.$main.Button7$Click();
                case 41:
                    return Screen1.lambda19();
                case 42:
                    return Screen1.lambda20();
                case 43:
                    return this.$main.Button6$Click();
                case 44:
                    return Screen1.lambda21();
                case 45:
                    return Screen1.lambda22();
                case 46:
                    return this.$main.Button8$Click();
                case 47:
                    return Screen1.lambda23();
                case 48:
                    return Screen1.lambda24();
                case 49:
                    return this.$main.Button9$Click();
                case 50:
                    return Screen1.lambda25();
                case 51:
                    return Screen1.lambda26();
                case 55:
                    return Screen1.lambda27();
                case 56:
                    return Screen1.lambda28();
                case 58:
                    return Screen1.lambda29();
                case 59:
                    return Screen1.lambda30();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 18:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 55:
                case 56:
                case 58:
                case 59:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    public String getSimpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    public void onCreate(Bundle icicle) {
        AppInventorCompatActivity.setClassicModeFromYail(true);
        super.onCreate(icicle);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        Environment environment = this.form$Mnenvironment;
        boolean x = true & ((environment == null ? 1 : 0) + 1);
        if (!x ? !x : !environment.isBound(name)) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = lists.cons(lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public void processException(Object ex) {
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            return false;
        } else if (lookupInFormEnvironment(registeredObject) != componentObject) {
            return false;
        } else {
            boolean x = true;
            try {
                Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                return true;
            } catch (StopBlocksExecution e) {
                StopBlocksExecution exception = e;
                return false;
            } catch (PermissionException e2) {
                PermissionException exception2 = e2;
                exception2.printStackTrace();
                if (this != componentObject) {
                    x = false;
                }
                if (!x ? !x : !IsEqual.apply(eventName, "PermissionNeeded")) {
                    PermissionDenied(componentObject, eventName, exception2.getPermissionNeeded());
                    return false;
                }
                processException(exception2);
                return false;
            } catch (Throwable th) {
                Throwable exception3 = th;
                androidLogForm(exception3.getMessage());
                exception3.printStackTrace();
                processException(exception3);
                return false;
            }
        }
    }

    public void dispatchGenericEvent(Component componentObject, String eventName, boolean notAlreadyHandled, Object[] args) {
        boolean x = false;
        Object handler = lookupInFormEnvironment(misc.string$To$Symbol(strings.stringAppend("any$", getSimpleName(componentObject), "$", eventName)));
        if (handler != Boolean.FALSE) {
            try {
                Scheme.apply.apply2(handler, lists.cons(componentObject, lists.cons(notAlreadyHandled ? Boolean.TRUE : Boolean.FALSE, LList.makeList(args, 0))));
            } catch (StopBlocksExecution e) {
                StopBlocksExecution exception = e;
            } catch (PermissionException e2) {
                PermissionException exception2 = e2;
                exception2.printStackTrace();
                if (this == componentObject) {
                    x = true;
                }
                if (!x ? !x : !IsEqual.apply(eventName, "PermissionNeeded")) {
                    PermissionDenied(componentObject, eventName, exception2.getPermissionNeeded());
                } else {
                    processException(exception2);
                }
            } catch (Throwable th) {
                Throwable exception3 = th;
                androidLogForm(exception3.getMessage());
                exception3.printStackTrace();
                processException(exception3);
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        String str = null;
        String obj = componentName == null ? null : componentName.toString();
        if (eventName != null) {
            str = eventName.toString();
        }
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj, str)));
    }

    public void $define() {
        int i;
        Object arg0;
        Object arg02;
        Object arg03;
        WrongType wrongType;
        Object var;
        Object apply1;
        Object component$Mnname;
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            Exception exception2 = exception;
            androidLogForm(exception2.getMessage());
            processException(exception2);
        }
        Screen1 = this;
        addToFormEnvironment(Lit0, this);
        Object obj = this.events$Mnto$Mnregister;
        Object components = obj;
        Object arg04 = obj;
        while (true) {
            i = -2;
            if (arg04 == LList.Empty) {
                try {
                    break;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "arg0", -2, arg0);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "arg0", -2, arg02);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "lookup-in-form-environment", 0, apply1);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "add-to-form-environment", 0, component$Mnname);
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "arg0", i, arg03);
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "add-to-global-var-environment", 0, var);
                } catch (YailRuntimeError exception3) {
                    Object obj2 = components;
                    processException(exception3);
                    return;
                }
            } else {
                try {
                    Pair arg05 = (Pair) arg04;
                    Object event$Mninfo = arg05.getCar();
                    Object apply12 = lists.car.apply1(event$Mninfo);
                    String obj3 = apply12 == null ? null : apply12.toString();
                    Object apply13 = lists.cdr.apply1(event$Mninfo);
                    EventDispatcher.registerEventForDelegation(this, obj3, apply13 == null ? null : apply13.toString());
                    arg04 = arg05.getCdr();
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "arg0", -2, arg04);
                }
            }
        }
        components = lists.reverse(this.components$Mnto$Mncreate);
        addToGlobalVars(Lit2, lambda$Fn1);
        arg0 = lists.reverse(this.form$Mndo$Mnafter$Mncreation);
        while (arg0 != LList.Empty) {
            Pair arg06 = (Pair) arg0;
            misc.force(arg06.getCar());
            arg0 = arg06.getCdr();
            i = -2;
        }
        Object var$Mnval = null;
        arg02 = components;
        while (arg02 != LList.Empty) {
            Pair arg07 = (Pair) arg02;
            Object component$Mninfo = arg07.getCar();
            Object apply14 = lists.caddr.apply1(component$Mninfo);
            lists.cadddr.apply1(component$Mninfo);
            Object component$Mntype = lists.cadr.apply1(component$Mninfo);
            apply1 = lists.car.apply1(component$Mninfo);
            component$Mnname = apply14;
            Object obj4 = apply1;
            Object component$Mnobject = Invoke.make.apply2(component$Mntype, lookupInFormEnvironment((Symbol) apply1));
            SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
            addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
            arg02 = arg07.getCdr();
            Pair pair = arg07;
            var$Mnval = component$Mninfo;
            i = -2;
        }
        arg03 = lists.reverse(this.global$Mnvars$Mnto$Mncreate);
        while (arg03 != LList.Empty) {
            Pair arg08 = (Pair) arg03;
            var$Mnval = arg08.getCar();
            var = lists.car.apply1(var$Mnval);
            addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(lists.cadr.apply1(var$Mnval)));
            arg03 = arg08.getCdr();
            Pair pair2 = arg08;
        }
        Object component$Mndescriptors = components;
        Object arg09 = component$Mndescriptors;
        while (arg09 != LList.Empty) {
            try {
                Pair arg010 = (Pair) arg09;
                Object component$Mninfo2 = arg010.getCar();
                lists.caddr.apply1(component$Mninfo2);
                Object obj5 = var$Mnval;
                var$Mnval = lists.cadddr.apply1(component$Mninfo2);
                if (var$Mnval != Boolean.FALSE) {
                    Scheme.applyToArgs.apply1(var$Mnval);
                }
                arg09 = arg010.getCdr();
            } catch (ClassCastException e8) {
                wrongType = new WrongType(e8, "arg0", i, arg09);
                throw wrongType;
            }
        }
        Object arg011 = component$Mndescriptors;
        while (arg011 != LList.Empty) {
            try {
                Pair arg012 = (Pair) arg011;
                Object component$Mninfo3 = arg012.getCar();
                Object apply15 = lists.caddr.apply1(component$Mninfo3);
                lists.cadddr.apply1(component$Mninfo3);
                Object obj6 = var$Mnval;
                var$Mnval = apply15;
                callInitialize(SlotGet.field.apply2(this, var$Mnval));
                arg011 = arg012.getCdr();
            } catch (ClassCastException e9) {
                wrongType = new WrongType(e9, "arg0", i, arg011);
                throw wrongType;
            }
        }
        Object obj7 = components;
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        Object symbols = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object result = LList.Empty;
        Object arg0 = symbols;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object cdr = arg02.getCdr();
                Object car = arg02.getCar();
                try {
                    result = Pair.make(misc.symbol$To$String((Symbol) car), result);
                    arg0 = cdr;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        Object apply2 = apply.apply2(moduleMethod, LList.reverseInPlace(result));
        try {
            return misc.string$To$Symbol((CharSequence) apply2);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, apply2);
        }
    }

    static Object lambda2() {
        return null;
    }
}
