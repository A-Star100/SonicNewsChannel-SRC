package appinventor.ai_sonicforces207.SNCAPP_AutoCleanVer;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.common.Permission;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.CheckBox;
import com.google.appinventor.components.runtime.CircularProgress;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.File;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.Sound;
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
import gnu.kawa.functions.AddOp;
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
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DFloNum;
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
    static final PairWithPosition Lit10;
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("Button7$Click").readResolve());
    static final FString Lit101 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("HomeUrl").readResolve());
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("CircularProgress1").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("DismissProgressDialog").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$PageLoaded").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("PageLoaded").readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$BeforePageLoad").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("WebViewer1").readResolve());
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("BeforePageLoad").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("TextToSpeech1").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("Speak").readResolve());
    static final PairWithPosition Lit113;
    static final PairWithPosition Lit114;
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$ErrorOccurred").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("ErrorOccurred").readResolve());
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("ClearCaches").readResolve());
    static final IntNum Lit120 = IntNum.make(2);
    static final IntNum Lit121 = IntNum.make(16777215);
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit123 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit124 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit125 = new FString("com.google.appinventor.components.runtime.CheckBox");
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("CheckBox1").readResolve());
    static final FString Lit127 = new FString("com.google.appinventor.components.runtime.CheckBox");
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("Checked").readResolve());
    static final PairWithPosition Lit129;
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("ClearCookies").readResolve());
    static final PairWithPosition Lit130;
    static final PairWithPosition Lit131;
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("ShowAlert").readResolve());
    static final PairWithPosition Lit133;
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("CheckBox1$Changed").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("Changed").readResolve());
    static final FString Lit136 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit137 = ((SimpleSymbol) new SimpleSymbol("Button4").readResolve());
    static final DFloNum Lit138 = DFloNum.make((double) 12);
    static final FString Lit139 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("RunJavaScript").readResolve());
    static final SimpleSymbol Lit140 = ((SimpleSymbol) new SimpleSymbol("Scope").readResolve());
    static final SimpleSymbol Lit141;
    static final PairWithPosition Lit142;
    static final PairWithPosition Lit143;
    static final PairWithPosition Lit144;
    static final PairWithPosition Lit145;
    static final PairWithPosition Lit146;
    static final SimpleSymbol Lit147 = ((SimpleSymbol) new SimpleSymbol("Exists").readResolve());
    static final PairWithPosition Lit148;
    static final PairWithPosition Lit149;
    static final PairWithPosition Lit15;
    static final SimpleSymbol Lit150 = ((SimpleSymbol) new SimpleSymbol("SaveFile").readResolve());
    static final PairWithPosition Lit151;
    static final PairWithPosition Lit152;
    static final PairWithPosition Lit153;
    static final PairWithPosition Lit154;
    static final SimpleSymbol Lit155 = ((SimpleSymbol) new SimpleSymbol("ShowChooseDialog").readResolve());
    static final PairWithPosition Lit156;
    static final SimpleSymbol Lit157 = ((SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve());
    static final FString Lit158 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit159 = ((SimpleSymbol) new SimpleSymbol("Button8").readResolve());
    static final IntNum Lit16 = IntNum.make(1);
    static final IntNum Lit160 = IntNum.make(45);
    static final IntNum Lit161 = IntNum.make(60);
    static final FString Lit162 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit163 = ((SimpleSymbol) new SimpleSymbol("ShowProgressDialog").readResolve());
    static final PairWithPosition Lit164;
    static final PairWithPosition Lit165;
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("Button8$Click").readResolve());
    static final FString Lit167 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final IntNum Lit168 = IntNum.make(16777215);
    static final SimpleSymbol Lit169 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final PairWithPosition Lit17;
    static final IntNum Lit170;
    static final FString Lit171 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final SimpleSymbol Lit172 = ((SimpleSymbol) new SimpleSymbol("$response").readResolve());
    static final SimpleSymbol Lit173 = ((SimpleSymbol) new SimpleSymbol("Notifier1$AfterTextInput").readResolve());
    static final SimpleSymbol Lit174 = ((SimpleSymbol) new SimpleSymbol("AfterTextInput").readResolve());
    static final SimpleSymbol Lit175 = ((SimpleSymbol) new SimpleSymbol("$choice").readResolve());
    static final PairWithPosition Lit176;
    static final PairWithPosition Lit177;
    static final PairWithPosition Lit178;
    static final PairWithPosition Lit179;
    static final PairWithPosition Lit18;
    static final SimpleSymbol Lit180 = ((SimpleSymbol) new SimpleSymbol("Notifier1$AfterChoosing").readResolve());
    static final SimpleSymbol Lit181 = ((SimpleSymbol) new SimpleSymbol("AfterChoosing").readResolve());
    static final FString Lit182 = new FString("com.google.appinventor.components.runtime.TextToSpeech");
    static final FString Lit183 = new FString("com.google.appinventor.components.runtime.TextToSpeech");
    static final FString Lit184 = new FString("com.google.appinventor.components.runtime.File");
    static final SimpleSymbol Lit185 = ((SimpleSymbol) new SimpleSymbol("DefaultScope").readResolve());
    static final SimpleSymbol Lit186 = ((SimpleSymbol) new SimpleSymbol("WritePermission").readResolve());
    static final FString Lit187 = new FString("com.google.appinventor.components.runtime.File");
    static final FString Lit188 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit189 = new FString("com.google.appinventor.components.runtime.Sound");
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("File1").readResolve());
    static final FString Lit190 = new FString("com.google.appinventor.components.runtime.Clock");
    static final SimpleSymbol Lit191 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit192 = ((SimpleSymbol) new SimpleSymbol("TimerInterval").readResolve());
    static final IntNum Lit193 = IntNum.make(60000);
    static final FString Lit194 = new FString("com.google.appinventor.components.runtime.Clock");
    static final PairWithPosition Lit195;
    static final PairWithPosition Lit196;
    static final SimpleSymbol Lit197 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final SimpleSymbol Lit198 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final SimpleSymbol Lit199 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("Delete").readResolve());
    static final SimpleSymbol Lit200 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit201 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit202 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit207 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit208 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit209 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final PairWithPosition Lit21;
    static final SimpleSymbol Lit210 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit211 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit212 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit213;
    static final PairWithPosition Lit22;
    static final PairWithPosition Lit23;
    static final PairWithPosition Lit24;
    static final PairWithPosition Lit25;
    static final PairWithPosition Lit26;
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("g$FileSaveData").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("AboutScreen").readResolve());
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$cleanschedule").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final IntNum Lit31;
    static final SimpleSymbol Lit32;
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit34;
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("CloseScreenAnimation").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final IntNum Lit4 = IntNum.make(0);
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("VersionCode").readResolve());
    static final IntNum Lit44 = IntNum.make(17);
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("VersionName").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("Notifier1").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("g$MinutesPassed").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("ShowTextDialog").readResolve());
    static final PairWithPosition Lit51;
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("CanGoBack").readResolve());
    static final PairWithPosition Lit55;
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("GoBack").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("Screen1$BackPressed").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("BackPressed").readResolve());
    static final FString Lit59 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("p$procedure2").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit62 = IntNum.make(3);
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit64 = IntNum.make(16777215);
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit66 = IntNum.make(-2);
    static final FString Lit67 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit68 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("Button9").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$fileclean").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit71 = IntNum.make(50);
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol(Component.LISTVIEW_KEY_IMAGE).readResolve());
    static final SimpleSymbol Lit73 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final FString Lit74 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit75;
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("Button9$Click").readResolve());
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit78 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("p$AUTOClear").readResolve());
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit81 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol("GoHome").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve());
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("Button5").readResolve());
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol("ShowMessageDialog").readResolve());
    static final PairWithPosition Lit88;
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("GoToUrl").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("p$clearauto").readResolve());
    static final PairWithPosition Lit90;
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("Button5$Click").readResolve());
    static final FString Lit92 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("Button7").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit95 = IntNum.make(40);
    static final FString Lit96 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("CanGoForward").readResolve());
    static final PairWithPosition Lit98;
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("GoForward").readResolve());
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
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn36 = null;
    static final ModuleMethod lambda$Fn37 = null;
    static final ModuleMethod lambda$Fn38 = null;
    static final ModuleMethod lambda$Fn39 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn40 = null;
    static final ModuleMethod lambda$Fn41 = null;
    static final ModuleMethod lambda$Fn42 = null;
    static final ModuleMethod lambda$Fn43 = null;
    static final ModuleMethod lambda$Fn44 = null;
    static final ModuleMethod lambda$Fn45 = null;
    static final ModuleMethod lambda$Fn46 = null;
    static final ModuleMethod lambda$Fn47 = null;
    static final ModuleMethod lambda$Fn48 = null;
    static final ModuleMethod lambda$Fn49 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button3;
    public final ModuleMethod Button3$Click;
    public Button Button4;
    public final ModuleMethod Button4$Click;
    public Button Button5;
    public final ModuleMethod Button5$Click;
    public Button Button7;
    public final ModuleMethod Button7$Click;
    public Button Button8;
    public final ModuleMethod Button8$Click;
    public Button Button9;
    public final ModuleMethod Button9$Click;
    public CheckBox CheckBox1;
    public final ModuleMethod CheckBox1$Changed;
    public CircularProgress CircularProgress1;
    public Clock Clock1;
    public final ModuleMethod Clock1$Timer;
    public File File1;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public Notifier Notifier1;
    public final ModuleMethod Notifier1$AfterChoosing;
    public final ModuleMethod Notifier1$AfterTextInput;
    public final ModuleMethod Screen1$BackPressed;
    public final ModuleMethod Screen1$Initialize;
    public Sound Sound1;
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
        Lit213 = simpleSymbol;
        Lit196 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 864499), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 864494);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit32 = simpleSymbol2;
        Lit195 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 864369), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 864361);
        Lit179 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 742021), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 742016);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit29 = simpleSymbol3;
        Lit178 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741913), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741908), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741902);
        Lit177 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741609), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741603);
        Lit176 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741475), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 741470);
        int[] iArr = new int[2];
        iArr[0] = -16777216;
        Lit170 = IntNum.make(iArr);
        Lit165 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 688381);
        Lit164 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 688286), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 688280);
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit34 = simpleSymbol4;
        Lit156 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 629336), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 629331), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 629326), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 629321), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 629315);
        Lit154 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628923), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628918);
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("com.google.appinventor.components.common.FileScopeEnum").readResolve();
        Lit141 = simpleSymbol5;
        Lit153 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628906), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628850);
        Lit152 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628594), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628589), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628583);
        Lit151 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628233), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628227);
        Lit149 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628099), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628094);
        Lit148 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628082), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 628026);
        Lit146 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627768), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627763), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627757);
        Lit145 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627506), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627501);
        Lit144 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627349), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627344);
        Lit143 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627191), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627186);
        Lit142 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627034), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 627029);
        Lit133 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 574235);
        Lit131 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 574063), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 574058);
        Lit130 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 573946), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 573941), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 573935);
        Lit129 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 573547), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 573542);
        Lit114 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 454992), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 454987), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 454981);
        Lit113 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 454751);
        Lit98 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 401555), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 401550);
        Lit90 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 340435);
        Lit88 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 340170), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 340165), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 340159);
        Lit75 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 250064), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 250059), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 250053);
        Lit55 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 143504), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 143499);
        Lit51 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 135529), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 135524), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 135518);
        int[] iArr2 = new int[2];
        iArr2[0] = -14977281;
        Lit31 = IntNum.make(iArr2);
        Lit26 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 54470);
        Lit25 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 54382);
        Lit24 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53922), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53917);
        Lit23 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53818);
        Lit22 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53358), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53353);
        Lit21 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 54470);
        Lit18 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 54382);
        Lit17 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53922), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53917);
        Lit15 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53818);
        Lit10 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53358), "/tmp/1720913115502_8536323037650669568-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 53353);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit199, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit200, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit201, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit202, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit203, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit204, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit205, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit206, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit207, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit208, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit209, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit210, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit211, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit212, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime9567714469163431307.scm:634");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, (Object) null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 26, (Object) null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 27, (Object) null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 28, (Object) null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 29, (Object) null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 30, (Object) null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 31, (Object) null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 32, (Object) null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 33, (Object) null, 0);
        this.Screen1$Initialize = new ModuleMethod(frame2, 34, Lit52, 0);
        this.Screen1$BackPressed = new ModuleMethod(frame2, 35, Lit57, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 36, (Object) null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 37, (Object) null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 38, (Object) null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 39, (Object) null, 0);
        this.Button9$Click = new ModuleMethod(frame2, 40, Lit76, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 41, (Object) null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 42, (Object) null, 0);
        this.Button3$Click = new ModuleMethod(frame2, 43, Lit83, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 44, (Object) null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 45, (Object) null, 0);
        this.Button5$Click = new ModuleMethod(frame2, 46, Lit91, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 47, (Object) null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 48, (Object) null, 0);
        this.Button7$Click = new ModuleMethod(frame2, 49, Lit100, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 50, (Object) null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 51, (Object) null, 0);
        this.WebViewer1$PageLoaded = new ModuleMethod(frame2, 52, Lit107, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.WebViewer1$BeforePageLoad = new ModuleMethod(frame2, 53, Lit109, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.WebViewer1$ErrorOccurred = new ModuleMethod(frame2, 54, Lit115, 12291);
        lambda$Fn28 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 56, (Object) null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 57, (Object) null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 58, (Object) null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 59, (Object) null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 60, (Object) null, 0);
        this.CheckBox1$Changed = new ModuleMethod(frame2, 61, Lit134, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 62, (Object) null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 63, (Object) null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 64, (Object) null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 65, (Object) null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 66, (Object) null, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 67, (Object) null, 0);
        this.Button4$Click = new ModuleMethod(frame2, 68, Lit157, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 69, (Object) null, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 70, (Object) null, 0);
        this.Button8$Click = new ModuleMethod(frame2, 71, Lit166, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 72, (Object) null, 0);
        lambda$Fn43 = new ModuleMethod(frame2, 73, (Object) null, 0);
        this.Notifier1$AfterTextInput = new ModuleMethod(frame2, 74, Lit173, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.Notifier1$AfterChoosing = new ModuleMethod(frame2, 75, Lit180, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn44 = new ModuleMethod(frame2, 76, (Object) null, 0);
        lambda$Fn45 = new ModuleMethod(frame2, 77, (Object) null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 78, (Object) null, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 79, (Object) null, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 80, (Object) null, 0);
        lambda$Fn49 = new ModuleMethod(frame2, 81, (Object) null, 0);
        this.Clock1$Timer = new ModuleMethod(frame2, 82, Lit197, 0);
    }

    static Boolean lambda5() {
        return Boolean.FALSE;
    }

    static Boolean lambda9() {
        return Boolean.FALSE;
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
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, Lit4), $result);
                } else {
                    addToGlobalVars(Lit3, lambda$Fn2);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, Lit4), $result);
                } else {
                    addToGlobalVars(Lit5, lambda$Fn3);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit6, lambda$Fn4), $result);
                } else {
                    addToGlobalVars(Lit6, lambda$Fn5);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, Lit4), $result);
                } else {
                    addToGlobalVars(Lit7, lambda$Fn7);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit8, lambda$Fn8), $result);
                } else {
                    addToGlobalVars(Lit8, lambda$Fn9);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit9, lambda$Fn11), $result);
                } else {
                    addToGlobalVars(Lit9, lambda$Fn12);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit27, "<html><body><a href=\"https://ibb.co/Pz6FK6b\"><img src=\"https://i.ibb.co/x87XW7c/Screen-Shot-2024-06-23-at-5-33-20-PM.png\" alt=\"Screen-Shot-2024-06-23-at-5-33-20-PM\" border=\"0\"></a>"), $result);
                } else {
                    addToGlobalVars(Lit27, lambda$Fn14);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    SimpleSymbol simpleSymbol2 = Lit28;
                    SimpleSymbol simpleSymbol3 = Lit29;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, "Screen for browser", simpleSymbol3);
                    SimpleSymbol simpleSymbol4 = Lit30;
                    IntNum intNum = Lit31;
                    SimpleSymbol simpleSymbol5 = Lit32;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol4, intNum, simpleSymbol5);
                    SimpleSymbol simpleSymbol6 = Lit33;
                    Boolean bool = Boolean.TRUE;
                    SimpleSymbol simpleSymbol7 = Lit34;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol6, bool, simpleSymbol7);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit35, "Sonic Channel ", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit36, "zoom", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit37, "162-1629457_classic-sonic-sonic-the-hedgehog-classic-sonic.png", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit38, "zoom", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit39, "unspecified", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit40, Boolean.TRUE, simpleSymbol7);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit41, "Responsive", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit42, "Sonic Channel - Android", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit43, Lit44, simpleSymbol5);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit45, "1.7 (Speedway)", simpleSymbol3), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn15));
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit52, this.Screen1$Initialize);
                } else {
                    addToFormEnvironment(Lit52, this.Screen1$Initialize);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
                } else {
                    addToEvents(simpleSymbol, Lit53);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit57, this.Screen1$BackPressed);
                } else {
                    addToFormEnvironment(Lit57, this.Screen1$BackPressed);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "BackPressed");
                } else {
                    addToEvents(simpleSymbol, Lit58);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit59, Lit60, lambda$Fn16), $result);
                } else {
                    addToComponents(simpleSymbol, Lit67, Lit60, lambda$Fn17);
                }
                this.Button9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit60, Lit68, Lit69, lambda$Fn18), $result);
                } else {
                    addToComponents(Lit60, Lit74, Lit69, lambda$Fn19);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit76, this.Button9$Click);
                } else {
                    addToFormEnvironment(Lit76, this.Button9$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button9", "Click");
                } else {
                    addToEvents(Lit69, Lit77);
                }
                this.Button3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit60, Lit78, Lit79, lambda$Fn20), $result);
                } else {
                    addToComponents(Lit60, Lit81, Lit79, lambda$Fn21);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit83, this.Button3$Click);
                } else {
                    addToFormEnvironment(Lit83, this.Button3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
                } else {
                    addToEvents(Lit79, Lit77);
                }
                this.Button5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit60, Lit84, Lit85, lambda$Fn22), $result);
                } else {
                    addToComponents(Lit60, Lit86, Lit85, lambda$Fn23);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit91, this.Button5$Click);
                } else {
                    addToFormEnvironment(Lit91, this.Button5$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button5", "Click");
                } else {
                    addToEvents(Lit85, Lit77);
                }
                this.Button7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit60, Lit92, Lit93, lambda$Fn24), $result);
                } else {
                    addToComponents(Lit60, Lit96, Lit93, lambda$Fn25);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit100, this.Button7$Click);
                } else {
                    addToFormEnvironment(Lit100, this.Button7$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button7", "Click");
                } else {
                    addToEvents(Lit93, Lit77);
                }
                this.WebViewer1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit101, Lit11, lambda$Fn26), $result);
                } else {
                    addToComponents(simpleSymbol, Lit103, Lit11, lambda$Fn27);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit107, this.WebViewer1$PageLoaded);
                } else {
                    addToFormEnvironment(Lit107, this.WebViewer1$PageLoaded);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "PageLoaded");
                } else {
                    addToEvents(Lit11, Lit108);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit109, this.WebViewer1$BeforePageLoad);
                } else {
                    addToFormEnvironment(Lit109, this.WebViewer1$BeforePageLoad);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "BeforePageLoad");
                } else {
                    addToEvents(Lit11, Lit110);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit115, this.WebViewer1$ErrorOccurred);
                } else {
                    addToFormEnvironment(Lit115, this.WebViewer1$ErrorOccurred);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "ErrorOccurred");
                } else {
                    addToEvents(Lit11, Lit116);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit117, Lit118, lambda$Fn28), $result);
                } else {
                    addToComponents(simpleSymbol, Lit122, Lit118, lambda$Fn29);
                }
                this.CircularProgress1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit123, Lit104, lambda$Fn30), $result);
                } else {
                    addToComponents(Lit118, Lit124, Lit104, lambda$Fn31);
                }
                this.CheckBox1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit125, Lit126, lambda$Fn32), $result);
                } else {
                    addToComponents(Lit118, Lit127, Lit126, lambda$Fn33);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit134, this.CheckBox1$Changed);
                } else {
                    addToFormEnvironment(Lit134, this.CheckBox1$Changed);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "CheckBox1", "Changed");
                } else {
                    addToEvents(Lit126, Lit135);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit136, Lit137, lambda$Fn34), $result);
                } else {
                    addToComponents(Lit118, Lit139, Lit137, lambda$Fn35);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit157, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit157, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit137, Lit77);
                }
                this.Button8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit158, Lit159, lambda$Fn40), $result);
                } else {
                    addToComponents(Lit118, Lit162, Lit159, lambda$Fn41);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit166, this.Button8$Click);
                } else {
                    addToFormEnvironment(Lit166, this.Button8$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button8", "Click");
                } else {
                    addToEvents(Lit159, Lit77);
                }
                this.Notifier1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit167, Lit49, lambda$Fn42), $result);
                } else {
                    addToComponents(simpleSymbol, Lit171, Lit49, lambda$Fn43);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit173, this.Notifier1$AfterTextInput);
                } else {
                    addToFormEnvironment(Lit173, this.Notifier1$AfterTextInput);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Notifier1", "AfterTextInput");
                } else {
                    addToEvents(Lit49, Lit174);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit180, this.Notifier1$AfterChoosing);
                } else {
                    addToFormEnvironment(Lit180, this.Notifier1$AfterChoosing);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Notifier1", "AfterChoosing");
                } else {
                    addToEvents(Lit49, Lit181);
                }
                this.TextToSpeech1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit182, Lit111, Boolean.FALSE), $result);
                } else {
                    addToComponents(simpleSymbol, Lit183, Lit111, Boolean.FALSE);
                }
                this.File1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit184, Lit19, lambda$Fn44), $result);
                } else {
                    addToComponents(simpleSymbol, Lit187, Lit19, lambda$Fn45);
                }
                this.Sound1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit188, Lit46, lambda$Fn46), $result);
                } else {
                    addToComponents(simpleSymbol, Lit189, Lit46, lambda$Fn47);
                }
                this.Clock1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit190, Lit191, lambda$Fn48), $result);
                } else {
                    addToComponents(simpleSymbol, Lit194, Lit191, lambda$Fn49);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit197, this.Clock1$Timer);
                } else {
                    addToFormEnvironment(Lit197, this.Clock1$Timer);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
                } else {
                    addToEvents(Lit191, Lit198);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static IntNum lambda3() {
        return Lit4;
    }

    static IntNum lambda4() {
        return Lit4;
    }

    static Procedure lambda6() {
        return lambda$Fn6;
    }

    static Boolean lambda7() {
        return Boolean.FALSE;
    }

    static IntNum lambda8() {
        return Lit4;
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
                    return Screen1.lambda7();
                case 24:
                    return Screen1.lambda6();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return Screen1.lambda9();
                case 27:
                    return Screen1.lambda11();
                case 28:
                    return Screen1.lambda10();
                case 29:
                    return Screen1.lambda12();
                case 30:
                    return Screen1.lambda14();
                case 31:
                    return Screen1.lambda13();
                case 32:
                    return Screen1.lambda15();
                case 33:
                    return Screen1.lambda16();
                case 34:
                    return this.$main.Screen1$Initialize();
                case 35:
                    return this.$main.Screen1$BackPressed();
                case 36:
                    return Screen1.lambda17();
                case 37:
                    return Screen1.lambda18();
                case 38:
                    return Screen1.lambda19();
                case 39:
                    return Screen1.lambda20();
                case 40:
                    return this.$main.Button9$Click();
                case 41:
                    return Screen1.lambda21();
                case 42:
                    return Screen1.lambda22();
                case 43:
                    return this.$main.Button3$Click();
                case 44:
                    return Screen1.lambda23();
                case 45:
                    return Screen1.lambda24();
                case 46:
                    return this.$main.Button5$Click();
                case 47:
                    return Screen1.lambda25();
                case 48:
                    return Screen1.lambda26();
                case 49:
                    return this.$main.Button7$Click();
                case 50:
                    return Screen1.lambda27();
                case 51:
                    return Screen1.lambda28();
                case 55:
                    return Screen1.lambda29();
                case 56:
                    return Screen1.lambda30();
                case 57:
                    return Screen1.lambda31();
                case 58:
                    return Screen1.lambda32();
                case 59:
                    return Screen1.lambda33();
                case 60:
                    return Screen1.lambda34();
                case 61:
                    return this.$main.CheckBox1$Changed();
                case 62:
                    return Screen1.lambda35();
                case 63:
                    return Screen1.lambda36();
                case 64:
                    return Screen1.lambda37();
                case 65:
                    return Screen1.lambda38();
                case 66:
                    return Screen1.lambda39();
                case 67:
                    return Screen1.lambda40();
                case 68:
                    return this.$main.Button4$Click();
                case 69:
                    return Screen1.lambda41();
                case 70:
                    return Screen1.lambda42();
                case 71:
                    return this.$main.Button8$Click();
                case 72:
                    return Screen1.lambda43();
                case 73:
                    return Screen1.lambda44();
                case 76:
                    return Screen1.lambda45();
                case 77:
                    return Screen1.lambda46();
                case 78:
                    return Screen1.lambda47();
                case 79:
                    return Screen1.lambda48();
                case 80:
                    return Screen1.lambda49();
                case 81:
                    return Screen1.lambda50();
                case 82:
                    return this.$main.Clock1$Timer();
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
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 24:
                case 25:
                case 26:
                case 27:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 28:
                case 29:
                case 30:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
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
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
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
                case 74:
                case 75:
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
                case 74:
                    return this.$main.Notifier1$AfterTextInput(obj);
                case 75:
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
    }

    static Procedure lambda10() {
        return lambda$Fn10;
    }

    static Boolean lambda11() {
        return Boolean.FALSE;
    }

    static Object lambda12() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list1;
        PairWithPosition pairWithPosition;
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol3 = Lit7;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit10, "=") != Boolean.FALSE) {
            simpleSymbol = Lit11;
            runtime.callComponentMethod(simpleSymbol, Lit12, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol, Lit13, LList.Empty, LList.Empty);
            simpleSymbol2 = Lit14;
            list1 = LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>");
            pairWithPosition = Lit15;
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit16), Lit17, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            SimpleSymbol simpleSymbol4 = Lit11;
            runtime.callComponentMethod(simpleSymbol4, Lit12, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol4, Lit13, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol4, Lit14, LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>"), Lit18);
            simpleSymbol = Lit19;
            simpleSymbol2 = Lit20;
            list1 = LList.list1("/Download/snc.html");
            pairWithPosition = Lit21;
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list1, pairWithPosition);
    }

    static Procedure lambda13() {
        return lambda$Fn13;
    }

    static Object lambda14() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list1;
        PairWithPosition pairWithPosition;
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol3 = Lit7;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit22, "=") != Boolean.FALSE) {
            simpleSymbol = Lit11;
            runtime.callComponentMethod(simpleSymbol, Lit12, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol, Lit13, LList.Empty, LList.Empty);
            simpleSymbol2 = Lit14;
            list1 = LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>");
            pairWithPosition = Lit23;
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit16), Lit24, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            SimpleSymbol simpleSymbol4 = Lit11;
            runtime.callComponentMethod(simpleSymbol4, Lit12, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol4, Lit13, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol4, Lit14, LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>"), Lit25);
            simpleSymbol = Lit19;
            simpleSymbol2 = Lit20;
            list1 = LList.list1("/Download/snc.html");
            pairWithPosition = Lit26;
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list1, pairWithPosition);
    }

    static String lambda15() {
        return "<html><body><a href=\"https://ibb.co/Pz6FK6b\"><img src=\"https://i.ibb.co/x87XW7c/Screen-Shot-2024-06-23-at-5-33-20-PM.png\" alt=\"Screen-Shot-2024-06-23-at-5-33-20-PM\" border=\"0\"></a>";
    }

    static Object lambda16() {
        SimpleSymbol simpleSymbol = Lit0;
        SimpleSymbol simpleSymbol2 = Lit28;
        SimpleSymbol simpleSymbol3 = Lit29;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, "Screen for browser", simpleSymbol3);
        SimpleSymbol simpleSymbol4 = Lit30;
        IntNum intNum = Lit31;
        SimpleSymbol simpleSymbol5 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol4, intNum, simpleSymbol5);
        SimpleSymbol simpleSymbol6 = Lit33;
        Boolean bool = Boolean.TRUE;
        SimpleSymbol simpleSymbol7 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol6, bool, simpleSymbol7);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit35, "Sonic Channel ", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit36, "zoom", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit37, "162-1629457_classic-sonic-sonic-the-hedgehog-classic-sonic.png", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit38, "zoom", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit39, "unspecified", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit40, Boolean.TRUE, simpleSymbol7);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit41, "Responsive", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit42, "Sonic Channel - Android", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit43, Lit44, simpleSymbol5);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit45, "1.7 (Speedway)", simpleSymbol3);
    }

    public Object Screen1$Initialize() {
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit46;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit47, "mixkit-software-interface-start-2574.wav", Lit29);
        runtime.callComponentMethod(simpleSymbol, Lit48, LList.Empty, LList.Empty);
        return runtime.callComponentMethod(Lit49, Lit50, LList.list3("The schedule data you set will be gone once the app is restarted.", "Set how long to wait until cache is cleared.", Boolean.TRUE), Lit51);
    }

    public Object Screen1$BackPressed() {
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol = Lit11;
        return runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethod(simpleSymbol, Lit54, LList.Empty, LList.Empty), Boolean.TRUE), Lit55, "=") != Boolean.FALSE ? runtime.callComponentMethod(simpleSymbol, Lit56, LList.Empty, LList.Empty) : Values.empty;
    }

    static Object lambda17() {
        SimpleSymbol simpleSymbol = Lit60;
        SimpleSymbol simpleSymbol2 = Lit61;
        IntNum intNum = Lit62;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit64, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit66, simpleSymbol3);
    }

    static Object lambda18() {
        SimpleSymbol simpleSymbol = Lit60;
        SimpleSymbol simpleSymbol2 = Lit61;
        IntNum intNum = Lit62;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit64, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit66, simpleSymbol3);
    }

    static Object lambda19() {
        SimpleSymbol simpleSymbol = Lit69;
        SimpleSymbol simpleSymbol2 = Lit70;
        IntNum intNum = Lit71;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit72, "istockphoto-1283073101-612x612.jpg", Lit29);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, intNum, simpleSymbol3);
    }

    static Object lambda20() {
        SimpleSymbol simpleSymbol = Lit69;
        SimpleSymbol simpleSymbol2 = Lit70;
        IntNum intNum = Lit71;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit72, "istockphoto-1283073101-612x612.jpg", Lit29);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, intNum, simpleSymbol3);
    }

    public Object Button9$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit49, Lit50, LList.list3("The schedule data you set will be gone once the app is restarted.", "Set how long to wait until cache is cleared.", Boolean.TRUE), Lit75);
    }

    static Object lambda21() {
        SimpleSymbol simpleSymbol = Lit79;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, Lit32);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Go Home", Lit29);
    }

    static Object lambda22() {
        SimpleSymbol simpleSymbol = Lit79;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, Lit32);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Go Home", Lit29);
    }

    public Object Button3$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit11, Lit82, LList.Empty, LList.Empty);
    }

    static Object lambda23() {
        SimpleSymbol simpleSymbol = Lit85;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, Lit32);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Go to regular website", Lit29);
    }

    static Object lambda24() {
        SimpleSymbol simpleSymbol = Lit85;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, Lit32);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Go to regular website", Lit29);
    }

    public Object Button5$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit49, Lit87, LList.list3("Depending on your device's capabilities, you may not be able to browse the site without lag", "Warning", "OK"), Lit88);
        SimpleSymbol simpleSymbol = Lit46;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit47, "mixkit-elevator-tone-2863.wav", Lit29);
        runtime.callComponentMethod(simpleSymbol, Lit48, LList.Empty, LList.Empty);
        return runtime.callComponentMethod(Lit11, Lit89, LList.list1("https://sites.google.com/view/sonicnews-snc/home"), Lit90);
    }

    static Object lambda25() {
        SimpleSymbol simpleSymbol = Lit93;
        SimpleSymbol simpleSymbol2 = Lit94;
        IntNum intNum = Lit16;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit70, Lit71, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit72, "image.png", Lit29);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit95, simpleSymbol3);
    }

    static Object lambda26() {
        SimpleSymbol simpleSymbol = Lit93;
        SimpleSymbol simpleSymbol2 = Lit94;
        IntNum intNum = Lit16;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit70, Lit71, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit72, "image.png", Lit29);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit95, simpleSymbol3);
    }

    public Object Button7$Click() {
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol = Lit11;
        return runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethod(simpleSymbol, Lit97, LList.Empty, LList.Empty), Boolean.TRUE), Lit98, "=") != Boolean.FALSE ? runtime.callComponentMethod(simpleSymbol, Lit99, LList.Empty, LList.Empty) : Values.empty;
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit11, Lit102, "https://sites.google.com/view/snc-mobile/home", Lit29);
    }

    static Object lambda28() {
        return runtime.setAndCoerceProperty$Ex(Lit11, Lit102, "https://sites.google.com/view/snc-mobile/home", Lit29);
    }

    public Object WebViewer1$PageLoaded(Object $url) {
        runtime.sanitizeComponentData($url);
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit104, Lit105, Boolean.FALSE, Lit34);
        return runtime.callComponentMethod(Lit49, Lit106, LList.Empty, LList.Empty);
    }

    public Object WebViewer1$BeforePageLoad(Object $url) {
        runtime.sanitizeComponentData($url);
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit105, Boolean.TRUE, Lit34);
    }

    public Object WebViewer1$ErrorOccurred(Object $errorCode, Object $description, Object $failingUrl) {
        runtime.sanitizeComponentData($errorCode);
        runtime.sanitizeComponentData($description);
        runtime.sanitizeComponentData($failingUrl);
        runtime.setThisForm();
        runtime.callComponentMethod(Lit111, Lit112, LList.list1("An error has occured"), Lit113);
        return runtime.callComponentMethod(Lit49, Lit87, LList.list3("Page could not load. Either this is an error with your internet connection or this could be an app error. Try getting back online.", "Error", "OK"), Lit114);
    }

    static Object lambda29() {
        SimpleSymbol simpleSymbol = Lit118;
        SimpleSymbol simpleSymbol2 = Lit61;
        IntNum intNum = Lit62;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit119, Lit120, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit121, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit66, simpleSymbol3);
    }

    static Object lambda30() {
        SimpleSymbol simpleSymbol = Lit118;
        SimpleSymbol simpleSymbol2 = Lit61;
        IntNum intNum = Lit62;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit119, Lit120, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit121, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit66, simpleSymbol3);
    }

    static Object lambda31() {
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit105, Boolean.FALSE, Lit34);
    }

    static Object lambda32() {
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit105, Boolean.FALSE, Lit34);
    }

    static Object lambda33() {
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit80, "AutoClean for app files", Lit29);
    }

    static Object lambda34() {
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit80, "AutoClean for app files", Lit29);
    }

    public Object CheckBox1$Changed() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list1;
        PairWithPosition pairWithPosition;
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol3 = Lit126;
        SimpleSymbol simpleSymbol4 = Lit128;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.getProperty$1(simpleSymbol3, simpleSymbol4), Boolean.TRUE), Lit129, "=") != Boolean.FALSE) {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit7, Lit16);
            SimpleSymbol simpleSymbol5 = Lit46;
            runtime.setAndCoerceProperty$Ex(simpleSymbol5, Lit47, "mixkit-elevator-tone-2863.wav", Lit29);
            runtime.callComponentMethod(simpleSymbol5, Lit48, LList.Empty, LList.Empty);
            simpleSymbol = Lit49;
            simpleSymbol2 = Lit87;
            list1 = LList.list3("Written files by the app will be erased every 5 minutes with AutoClean.", "Written file will be erased every 5 minutes.", "OK");
            pairWithPosition = Lit130;
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.getProperty$1(simpleSymbol3, simpleSymbol4), Boolean.FALSE), Lit131, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit7, Lit4);
            simpleSymbol = Lit49;
            simpleSymbol2 = Lit132;
            list1 = LList.list1("Written files by the app will not be erased with AutoClean.");
            pairWithPosition = Lit133;
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list1, pairWithPosition);
    }

    static Object lambda35() {
        SimpleSymbol simpleSymbol = Lit137;
        SimpleSymbol simpleSymbol2 = Lit94;
        DFloNum dFloNum = Lit138;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, dFloNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Write an HTML ", Lit29);
    }

    static Object lambda36() {
        SimpleSymbol simpleSymbol = Lit137;
        SimpleSymbol simpleSymbol2 = Lit94;
        DFloNum dFloNum = Lit138;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, dFloNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Write an HTML ", Lit29);
    }

    public Object Button4$Click() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list1;
        PairWithPosition pairWithPosition;
        runtime.setThisForm();
        SimpleSymbol simpleSymbol3 = Lit19;
        runtime.setAndCoerceProperty$Ex(simpleSymbol3, Lit140, FileScope.Shared, Lit141);
        if (runtime.processAndDelayed$V(new Object[]{lambda$Fn36, lambda$Fn37, lambda$Fn38, lambda$Fn39}) != Boolean.FALSE) {
            simpleSymbol = Lit49;
            simpleSymbol2 = Lit87;
            list1 = LList.list3("Before you attempt to save the HTML file, please ensure permissions for writing to external storage is enabled.", "Warning", "OK, I'll enable permissions");
            pairWithPosition = Lit146;
        } else {
            ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
            SimpleSymbol simpleSymbol4 = Lit147;
            if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethodWithBlockingContinuation(simpleSymbol3, simpleSymbol4, LList.list2(FileScope.Shared, "/Download/snc.html"), Lit148), Boolean.FALSE), Lit149, "=") != Boolean.FALSE) {
                runtime.callComponentMethod(simpleSymbol3, Lit150, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit27, runtime.$Stthe$Mnnull$Mnvalue$St), "/Download/snc.html"), Lit151);
                SimpleSymbol simpleSymbol5 = Lit46;
                runtime.setAndCoerceProperty$Ex(simpleSymbol5, Lit47, "mixkit-elevator-tone-2863.wav", Lit29);
                runtime.callComponentMethod(simpleSymbol5, Lit48, LList.Empty, LList.Empty);
                simpleSymbol = Lit49;
                simpleSymbol2 = Lit87;
                list1 = LList.list3("The HTML was created. Open this in your browser. This HTML does not refer to the entire page.", "File has been created", "OK");
                pairWithPosition = Lit152;
            } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.callComponentMethodWithBlockingContinuation(simpleSymbol3, simpleSymbol4, LList.list2(FileScope.Shared, "/Download/snc.html"), Lit153), Boolean.TRUE), Lit154, "=") == Boolean.FALSE) {
                return Values.empty;
            } else {
                SimpleSymbol simpleSymbol6 = Lit46;
                runtime.setAndCoerceProperty$Ex(simpleSymbol6, Lit47, "mixkit-elevator-tone-2863.wav", Lit29);
                runtime.callComponentMethod(simpleSymbol6, Lit48, LList.Empty, LList.Empty);
                simpleSymbol = Lit49;
                simpleSymbol2 = Lit155;
                list1 = LList.list1("An HTML of the same name (snc.html) already exists. Would you like to delete the original and replace it or don't do anything.");
                LList.chain4(list1, "Warning", "Yes", "No", Boolean.FALSE);
                pairWithPosition = Lit156;
            }
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list1, pairWithPosition);
    }

    static Object lambda37() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.WriteExternalStorage, Boolean.FALSE), Lit142, "=");
    }

    static Object lambda38() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.ReadMediaVideo, Boolean.FALSE), Lit143, "=");
    }

    static Object lambda39() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.ReadMediaImages, Boolean.FALSE), Lit144, "=");
    }

    static Object lambda40() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.ReadMediaAudio, Boolean.FALSE), Lit145, "=");
    }

    static Object lambda41() {
        SimpleSymbol simpleSymbol = Lit159;
        SimpleSymbol simpleSymbol2 = Lit70;
        IntNum intNum = Lit160;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Sonic Official", Lit29);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit161, simpleSymbol3);
    }

    static Object lambda42() {
        SimpleSymbol simpleSymbol = Lit159;
        SimpleSymbol simpleSymbol2 = Lit70;
        IntNum intNum = Lit160;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit73, Lit16, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit80, "Sonic Official", Lit29);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit65, Lit161, simpleSymbol3);
    }

    public Object Button8$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit49, Lit163, LList.list2("The Sonic website usually takes a while to load up...", "URL Loading"), Lit164);
        return runtime.callComponentMethod(Lit11, Lit89, LList.list1("sonicthehedgehog.com"), Lit165);
    }

    static Object lambda43() {
        SimpleSymbol simpleSymbol = Lit49;
        SimpleSymbol simpleSymbol2 = Lit63;
        IntNum intNum = Lit168;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit169, Lit170, simpleSymbol3);
    }

    static Object lambda44() {
        SimpleSymbol simpleSymbol = Lit49;
        SimpleSymbol simpleSymbol2 = Lit63;
        IntNum intNum = Lit168;
        SimpleSymbol simpleSymbol3 = Lit32;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit169, Lit170, simpleSymbol3);
    }

    public Object Notifier1$AfterTextInput(Object $response) {
        Object obj;
        Object $response2 = runtime.sanitizeComponentData($response);
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit3;
        if ($response2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit172), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $response2;
        }
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, obj);
    }

    public Object Notifier1$AfterChoosing(Object $choice) {
        Object obj;
        Object $choice2 = runtime.sanitizeComponentData($choice);
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        if ($choice2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit175), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $choice2;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(obj, "Yes"), Lit176, "=") != Boolean.FALSE) {
            runtime.callComponentMethod(Lit19, Lit150, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit27, runtime.$Stthe$Mnnull$Mnvalue$St), "/Download/snc.html"), Lit177);
            SimpleSymbol simpleSymbol = Lit46;
            runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit47, "mixkit-correct-answer-tone-2870.wav", Lit29);
            runtime.callComponentMethod(simpleSymbol, Lit48, LList.Empty, LList.Empty);
            return runtime.callComponentMethod(Lit49, Lit87, LList.list3("Open the HTML in your browser!", "File has been created", "OK"), Lit178);
        }
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2($choice2 instanceof Package ? runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit175), " is not bound in the current context"), "Unbound Variable") : $choice2, "No"), Lit179, "=") != Boolean.FALSE ? Boolean.FALSE : Values.empty;
    }

    static Object lambda45() {
        SimpleSymbol simpleSymbol = Lit19;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit185, "Shared", Lit141);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit186, Boolean.TRUE, Lit34);
    }

    static Object lambda46() {
        SimpleSymbol simpleSymbol = Lit19;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit185, "Shared", Lit141);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit186, Boolean.TRUE, Lit34);
    }

    static Object lambda47() {
        return runtime.setAndCoerceProperty$Ex(Lit46, Lit47, "mixkit-software-interface-start-2574.wav", Lit29);
    }

    static Object lambda48() {
        return runtime.setAndCoerceProperty$Ex(Lit46, Lit47, "mixkit-software-interface-start-2574.wav", Lit29);
    }

    static Object lambda49() {
        return runtime.setAndCoerceProperty$Ex(Lit191, Lit192, Lit193, Lit32);
    }

    static Object lambda50() {
        return runtime.setAndCoerceProperty$Ex(Lit191, Lit192, Lit193, Lit32);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit5;
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol, runtime.$Stthe$Mnnull$Mnvalue$St), Lit16), Lit195, "+"));
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit196, "=") == Boolean.FALSE) {
            return Boolean.FALSE;
        }
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, Lit4);
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
