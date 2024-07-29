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
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final FString Lit100 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("CircularProgress1").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("DismissProgressDialog").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$PageLoaded").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("PageLoaded").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$BeforePageLoad").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("BeforePageLoad").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("TextToSpeech1").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("Speak").readResolve());
    static final PairWithPosition Lit109;
    static final SimpleSymbol Lit11;
    static final PairWithPosition Lit110;
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("WebViewer1$ErrorOccurred").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("ErrorOccurred").readResolve());
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve());
    static final IntNum Lit116 = IntNum.make(2);
    static final IntNum Lit117 = IntNum.make(16777215);
    static final FString Lit118 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit119 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("WebViewer1").readResolve());
    static final FString Lit120 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.CheckBox");
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("CheckBox1").readResolve());
    static final FString Lit123 = new FString("com.google.appinventor.components.runtime.CheckBox");
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("Checked").readResolve());
    static final PairWithPosition Lit125;
    static final PairWithPosition Lit126;
    static final PairWithPosition Lit127;
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("ShowAlert").readResolve());
    static final PairWithPosition Lit129;
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("ClearCaches").readResolve());
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("CheckBox1$Changed").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("Changed").readResolve());
    static final FString Lit132 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("Button4").readResolve());
    static final DFloNum Lit134 = DFloNum.make((double) 12);
    static final FString Lit135 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit136 = ((SimpleSymbol) new SimpleSymbol("Scope").readResolve());
    static final SimpleSymbol Lit137;
    static final PairWithPosition Lit138;
    static final PairWithPosition Lit139;
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("ClearCookies").readResolve());
    static final PairWithPosition Lit140;
    static final PairWithPosition Lit141;
    static final PairWithPosition Lit142;
    static final SimpleSymbol Lit143 = ((SimpleSymbol) new SimpleSymbol("Exists").readResolve());
    static final PairWithPosition Lit144;
    static final PairWithPosition Lit145;
    static final SimpleSymbol Lit146 = ((SimpleSymbol) new SimpleSymbol("SaveFile").readResolve());
    static final PairWithPosition Lit147;
    static final PairWithPosition Lit148;
    static final PairWithPosition Lit149;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("RunJavaScript").readResolve());
    static final PairWithPosition Lit150;
    static final SimpleSymbol Lit151 = ((SimpleSymbol) new SimpleSymbol("ShowChooseDialog").readResolve());
    static final PairWithPosition Lit152;
    static final SimpleSymbol Lit153 = ((SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve());
    static final FString Lit154 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit155 = ((SimpleSymbol) new SimpleSymbol("Button8").readResolve());
    static final IntNum Lit156 = IntNum.make(45);
    static final IntNum Lit157 = IntNum.make(60);
    static final FString Lit158 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit159 = ((SimpleSymbol) new SimpleSymbol("ShowProgressDialog").readResolve());
    static final PairWithPosition Lit16;
    static final PairWithPosition Lit160;
    static final PairWithPosition Lit161;
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("Button8$Click").readResolve());
    static final FString Lit163 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final IntNum Lit164 = IntNum.make(16777215);
    static final SimpleSymbol Lit165 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit166;
    static final FString Lit167 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final SimpleSymbol Lit168 = ((SimpleSymbol) new SimpleSymbol("$choice").readResolve());
    static final PairWithPosition Lit169;
    static final IntNum Lit17 = IntNum.make(1);
    static final PairWithPosition Lit170;
    static final PairWithPosition Lit171;
    static final PairWithPosition Lit172;
    static final SimpleSymbol Lit173 = ((SimpleSymbol) new SimpleSymbol("Notifier1$AfterChoosing").readResolve());
    static final SimpleSymbol Lit174 = ((SimpleSymbol) new SimpleSymbol("AfterChoosing").readResolve());
    static final FString Lit175 = new FString("com.google.appinventor.components.runtime.TextToSpeech");
    static final FString Lit176 = new FString("com.google.appinventor.components.runtime.TextToSpeech");
    static final FString Lit177 = new FString("com.google.appinventor.components.runtime.File");
    static final SimpleSymbol Lit178 = ((SimpleSymbol) new SimpleSymbol("DefaultScope").readResolve());
    static final SimpleSymbol Lit179 = ((SimpleSymbol) new SimpleSymbol("WritePermission").readResolve());
    static final PairWithPosition Lit18;
    static final FString Lit180 = new FString("com.google.appinventor.components.runtime.File");
    static final FString Lit181 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit182 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit183 = new FString("com.google.appinventor.components.runtime.Clock");
    static final SimpleSymbol Lit184 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit185 = ((SimpleSymbol) new SimpleSymbol("TimerInterval").readResolve());
    static final IntNum Lit186 = IntNum.make(60000);
    static final FString Lit187 = new FString("com.google.appinventor.components.runtime.Clock");
    static final PairWithPosition Lit188;
    static final IntNum Lit189 = IntNum.make(5);
    static final PairWithPosition Lit19;
    static final PairWithPosition Lit190;
    static final SimpleSymbol Lit191 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final SimpleSymbol Lit192 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final SimpleSymbol Lit193 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit194 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit195 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit196 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit197 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit198 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit199 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("File1").readResolve());
    static final SimpleSymbol Lit200 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit201 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit202 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit207;
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("Delete").readResolve());
    static final PairWithPosition Lit22;
    static final PairWithPosition Lit23;
    static final PairWithPosition Lit24;
    static final PairWithPosition Lit25;
    static final PairWithPosition Lit26;
    static final PairWithPosition Lit27;
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("p$AUTOClear").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("g$FileSaveData").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$MinutesPassed").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("AboutScreen").readResolve());
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final IntNum Lit33;
    static final SimpleSymbol Lit34;
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("CloseScreenAnimation").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final IntNum Lit4 = IntNum.make(0);
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("VersionCode").readResolve());
    static final IntNum Lit45 = IntNum.make(17);
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("VersionName").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("p$procedure2").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("CanGoBack").readResolve());
    static final PairWithPosition Lit53;
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("GoBack").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("Screen1$BackPressed").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("BackPressed").readResolve());
    static final FString Lit57 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("g$fileclean").readResolve());
    static final IntNum Lit60 = IntNum.make(3);
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit62 = IntNum.make(16777215);
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit64 = IntNum.make(-2);
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit66 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("Enabled").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit69 = IntNum.make(50);
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("p$clearauto").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol(Component.LISTVIEW_KEY_IMAGE).readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final FString Lit72 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit76 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("GoHome").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve());
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final PairWithPosition Lit8;
    static final FString Lit80 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("Button5").readResolve());
    static final FString Lit82 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("Notifier1").readResolve());
    static final SimpleSymbol Lit84 = ((SimpleSymbol) new SimpleSymbol("ShowMessageDialog").readResolve());
    static final PairWithPosition Lit85;
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("GoToUrl").readResolve());
    static final PairWithPosition Lit87;
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("Button5$Click").readResolve());
    static final FString Lit89 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("Button10").readResolve());
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("Button7").readResolve());
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit92 = IntNum.make(40);
    static final FString Lit93 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("CanGoForward").readResolve());
    static final PairWithPosition Lit95;
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("GoForward").readResolve());
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("Button7$Click").readResolve());
    static final FString Lit98 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("HomeUrl").readResolve());
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
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button10;
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
        Lit207 = simpleSymbol;
        Lit190 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 852187), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 852182);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit34 = simpleSymbol2;
        Lit188 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 852081), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 852073);
        Lit172 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729733), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729728);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit31 = simpleSymbol3;
        Lit171 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729625), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729620), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729614);
        Lit170 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729321), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729315);
        Lit169 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729187), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 729182);
        int[] iArr = new int[2];
        iArr[0] = -16777216;
        Lit166 = IntNum.make(iArr);
        Lit161 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 684285);
        Lit160 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 684190), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 684184);
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit11 = simpleSymbol4;
        Lit152 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 625240), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 625235), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 625230), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 625225), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 625219);
        Lit150 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624827), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624822);
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("com.google.appinventor.components.common.FileScopeEnum").readResolve();
        Lit137 = simpleSymbol5;
        Lit149 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624810), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624754);
        Lit148 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624498), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624493), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624487);
        Lit147 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624137), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624131);
        Lit145 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 624003), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623998);
        Lit144 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623986), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623930);
        Lit142 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623672), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623667), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623661);
        Lit141 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623410), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623405);
        Lit140 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623253), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623248);
        Lit139 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623095), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 623090);
        Lit138 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 622938), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 622933);
        Lit129 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 570139);
        Lit127 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569967), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569962);
        Lit126 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569850), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569845), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569839);
        Lit125 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569451), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 569446);
        Lit110 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 450896), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 450891), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 450885);
        Lit109 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 450655);
        Lit95 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 397459), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 397454);
        Lit87 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 336339);
        Lit85 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 336074), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 336069), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 336063);
        Lit53 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 139408), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 139403);
        int[] iArr2 = new int[2];
        iArr2[0] = -14977281;
        Lit33 = IntNum.make(iArr2);
        Lit27 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 46449);
        Lit26 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 46361);
        Lit25 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45844), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45839);
        Lit24 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45683);
        Lit23 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45166), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45161);
        Lit22 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 46449);
        Lit19 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 46361);
        Lit18 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45844), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45839);
        Lit16 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45683);
        Lit8 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45166), "/tmp/1720824433597_3831992260990263296-0/youngandroidproject/../src/appinventor/ai_sonicforces207/SNCAPP_AutoCleanVer/Screen1.yail", 45161);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit193, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit194, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit195, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit196, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit197, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit198, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit199, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit200, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit201, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit202, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit203, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit204, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit205, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit206, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime10851451184806059723.scm:634");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, (Object) null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 26, (Object) null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, (Object) null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 28, (Object) null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 29, (Object) null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 30, (Object) null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 31, (Object) null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 32, (Object) null, 0);
        this.Screen1$Initialize = new ModuleMethod(frame2, 33, Lit50, 0);
        this.Screen1$BackPressed = new ModuleMethod(frame2, 34, Lit55, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 35, (Object) null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 36, (Object) null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 37, (Object) null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 38, (Object) null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 39, (Object) null, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 40, (Object) null, 0);
        this.Button3$Click = new ModuleMethod(frame2, 41, Lit78, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 42, (Object) null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 43, (Object) null, 0);
        this.Button5$Click = new ModuleMethod(frame2, 44, Lit88, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 45, (Object) null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 46, (Object) null, 0);
        this.Button7$Click = new ModuleMethod(frame2, 47, Lit97, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 48, (Object) null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 49, (Object) null, 0);
        this.WebViewer1$PageLoaded = new ModuleMethod(frame2, 50, Lit103, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.WebViewer1$BeforePageLoad = new ModuleMethod(frame2, 51, Lit105, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.WebViewer1$ErrorOccurred = new ModuleMethod(frame2, 52, Lit111, 12291);
        lambda$Fn27 = new ModuleMethod(frame2, 53, (Object) null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 54, (Object) null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 56, (Object) null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 57, (Object) null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 58, (Object) null, 0);
        this.CheckBox1$Changed = new ModuleMethod(frame2, 59, Lit130, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 60, (Object) null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 61, (Object) null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 62, (Object) null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 63, (Object) null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 64, (Object) null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 65, (Object) null, 0);
        this.Button4$Click = new ModuleMethod(frame2, 66, Lit153, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 67, (Object) null, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 68, (Object) null, 0);
        this.Button8$Click = new ModuleMethod(frame2, 69, Lit162, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 70, (Object) null, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 71, (Object) null, 0);
        this.Notifier1$AfterChoosing = new ModuleMethod(frame2, 72, Lit173, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn43 = new ModuleMethod(frame2, 73, (Object) null, 0);
        lambda$Fn44 = new ModuleMethod(frame2, 74, (Object) null, 0);
        lambda$Fn45 = new ModuleMethod(frame2, 75, (Object) null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 76, (Object) null, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 77, (Object) null, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 78, (Object) null, 0);
        this.Clock1$Timer = new ModuleMethod(frame2, 79, Lit191, 0);
    }

    static Boolean lambda11() {
        return Boolean.FALSE;
    }

    static Boolean lambda4() {
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
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, lambda$Fn3), $result);
                } else {
                    addToGlobalVars(Lit5, lambda$Fn4);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit6, Lit4), $result);
                } else {
                    addToGlobalVars(Lit6, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, lambda$Fn7), $result);
                } else {
                    addToGlobalVars(Lit7, lambda$Fn8);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit28, lambda$Fn10), $result);
                } else {
                    addToGlobalVars(Lit28, lambda$Fn11);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit29, "<html><body><a href=\"https://ibb.co/Pz6FK6b\"><img src=\"https://i.ibb.co/x87XW7c/Screen-Shot-2024-06-23-at-5-33-20-PM.png\" alt=\"Screen-Shot-2024-06-23-at-5-33-20-PM\" border=\"0\"></a>"), $result);
                } else {
                    addToGlobalVars(Lit29, lambda$Fn13);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    SimpleSymbol simpleSymbol2 = Lit30;
                    SimpleSymbol simpleSymbol3 = Lit31;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, "Screen for browser", simpleSymbol3);
                    SimpleSymbol simpleSymbol4 = Lit32;
                    IntNum intNum = Lit33;
                    SimpleSymbol simpleSymbol5 = Lit34;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol4, intNum, simpleSymbol5);
                    SimpleSymbol simpleSymbol6 = Lit35;
                    Boolean bool = Boolean.TRUE;
                    SimpleSymbol simpleSymbol7 = Lit11;
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol6, bool, simpleSymbol7);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit36, "Sonic Channel ", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit37, "zoom", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit38, "162-1629457_classic-sonic-sonic-the-hedgehog-classic-sonic.png", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit39, "zoom", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit40, "unspecified", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit41, Boolean.TRUE, simpleSymbol7);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit42, "Responsive", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit43, "Sonic Channel - Android", simpleSymbol3);
                    runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit44, Lit45, simpleSymbol5);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit46, "1.7 (Speedway)", simpleSymbol3), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn14));
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit50, this.Screen1$Initialize);
                } else {
                    addToFormEnvironment(Lit50, this.Screen1$Initialize);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
                } else {
                    addToEvents(simpleSymbol, Lit51);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit55, this.Screen1$BackPressed);
                } else {
                    addToFormEnvironment(Lit55, this.Screen1$BackPressed);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "BackPressed");
                } else {
                    addToEvents(simpleSymbol, Lit56);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit57, Lit58, lambda$Fn15), $result);
                } else {
                    addToComponents(simpleSymbol, Lit65, Lit58, lambda$Fn16);
                }
                this.Button10 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit58, Lit66, Lit9, lambda$Fn17), $result);
                } else {
                    addToComponents(Lit58, Lit72, Lit9, lambda$Fn18);
                }
                this.Button3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit58, Lit73, Lit74, lambda$Fn19), $result);
                } else {
                    addToComponents(Lit58, Lit76, Lit74, lambda$Fn20);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit78, this.Button3$Click);
                } else {
                    addToFormEnvironment(Lit78, this.Button3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
                } else {
                    addToEvents(Lit74, Lit79);
                }
                this.Button5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit58, Lit80, Lit81, lambda$Fn21), $result);
                } else {
                    addToComponents(Lit58, Lit82, Lit81, lambda$Fn22);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit88, this.Button5$Click);
                } else {
                    addToFormEnvironment(Lit88, this.Button5$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button5", "Click");
                } else {
                    addToEvents(Lit81, Lit79);
                }
                this.Button7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit58, Lit89, Lit90, lambda$Fn23), $result);
                } else {
                    addToComponents(Lit58, Lit93, Lit90, lambda$Fn24);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit97, this.Button7$Click);
                } else {
                    addToFormEnvironment(Lit97, this.Button7$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button7", "Click");
                } else {
                    addToEvents(Lit90, Lit79);
                }
                this.WebViewer1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit98, Lit12, lambda$Fn25), $result);
                } else {
                    addToComponents(simpleSymbol, Lit100, Lit12, lambda$Fn26);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit103, this.WebViewer1$PageLoaded);
                } else {
                    addToFormEnvironment(Lit103, this.WebViewer1$PageLoaded);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "PageLoaded");
                } else {
                    addToEvents(Lit12, Lit104);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit105, this.WebViewer1$BeforePageLoad);
                } else {
                    addToFormEnvironment(Lit105, this.WebViewer1$BeforePageLoad);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "BeforePageLoad");
                } else {
                    addToEvents(Lit12, Lit106);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit111, this.WebViewer1$ErrorOccurred);
                } else {
                    addToFormEnvironment(Lit111, this.WebViewer1$ErrorOccurred);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "WebViewer1", "ErrorOccurred");
                } else {
                    addToEvents(Lit12, Lit112);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit113, Lit114, lambda$Fn27), $result);
                } else {
                    addToComponents(simpleSymbol, Lit118, Lit114, lambda$Fn28);
                }
                this.CircularProgress1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit114, Lit119, Lit101, lambda$Fn29), $result);
                } else {
                    addToComponents(Lit114, Lit120, Lit101, lambda$Fn30);
                }
                this.CheckBox1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit114, Lit121, Lit122, lambda$Fn31), $result);
                } else {
                    addToComponents(Lit114, Lit123, Lit122, lambda$Fn32);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit130, this.CheckBox1$Changed);
                } else {
                    addToFormEnvironment(Lit130, this.CheckBox1$Changed);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "CheckBox1", "Changed");
                } else {
                    addToEvents(Lit122, Lit131);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit114, Lit132, Lit133, lambda$Fn33), $result);
                } else {
                    addToComponents(Lit114, Lit135, Lit133, lambda$Fn34);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit153, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit153, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit133, Lit79);
                }
                this.Button8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit114, Lit154, Lit155, lambda$Fn39), $result);
                } else {
                    addToComponents(Lit114, Lit158, Lit155, lambda$Fn40);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit162, this.Button8$Click);
                } else {
                    addToFormEnvironment(Lit162, this.Button8$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button8", "Click");
                } else {
                    addToEvents(Lit155, Lit79);
                }
                this.Notifier1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit163, Lit83, lambda$Fn41), $result);
                } else {
                    addToComponents(simpleSymbol, Lit167, Lit83, lambda$Fn42);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit173, this.Notifier1$AfterChoosing);
                } else {
                    addToFormEnvironment(Lit173, this.Notifier1$AfterChoosing);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Notifier1", "AfterChoosing");
                } else {
                    addToEvents(Lit83, Lit174);
                }
                this.TextToSpeech1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit175, Lit107, Boolean.FALSE), $result);
                } else {
                    addToComponents(simpleSymbol, Lit176, Lit107, Boolean.FALSE);
                }
                this.File1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit177, Lit20, lambda$Fn43), $result);
                } else {
                    addToComponents(simpleSymbol, Lit180, Lit20, lambda$Fn44);
                }
                this.Sound1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit181, Lit47, lambda$Fn45), $result);
                } else {
                    addToComponents(simpleSymbol, Lit182, Lit47, lambda$Fn46);
                }
                this.Clock1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(simpleSymbol, Lit183, Lit184, lambda$Fn47), $result);
                } else {
                    addToComponents(simpleSymbol, Lit187, Lit184, lambda$Fn48);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit191, this.Clock1$Timer);
                } else {
                    addToFormEnvironment(Lit191, this.Clock1$Timer);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
                } else {
                    addToEvents(Lit184, Lit192);
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

    static Procedure lambda5() {
        return lambda$Fn5;
    }

    static Boolean lambda6() {
        return Boolean.FALSE;
    }

    static IntNum lambda7() {
        return Lit4;
    }

    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main;

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 52 ? this.$main.WebViewer1$ErrorOccurred(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 52) {
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
                    return Screen1.lambda6();
                case 23:
                    return Screen1.lambda5();
                case 24:
                    return Screen1.lambda7();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return Screen1.lambda10();
                case 27:
                    return Screen1.lambda9();
                case 28:
                    return Screen1.lambda11();
                case 29:
                    return Screen1.lambda13();
                case 30:
                    return Screen1.lambda12();
                case 31:
                    return Screen1.lambda14();
                case 32:
                    return Screen1.lambda15();
                case 33:
                    return this.$main.Screen1$Initialize();
                case 34:
                    return this.$main.Screen1$BackPressed();
                case 35:
                    return Screen1.lambda16();
                case 36:
                    return Screen1.lambda17();
                case 37:
                    return Screen1.lambda18();
                case 38:
                    return Screen1.lambda19();
                case 39:
                    return Screen1.lambda20();
                case 40:
                    return Screen1.lambda21();
                case 41:
                    return this.$main.Button3$Click();
                case 42:
                    return Screen1.lambda22();
                case 43:
                    return Screen1.lambda23();
                case 44:
                    return this.$main.Button5$Click();
                case 45:
                    return Screen1.lambda24();
                case 46:
                    return Screen1.lambda25();
                case 47:
                    return this.$main.Button7$Click();
                case 48:
                    return Screen1.lambda26();
                case 49:
                    return Screen1.lambda27();
                case 53:
                    return Screen1.lambda28();
                case 54:
                    return Screen1.lambda29();
                case 55:
                    return Screen1.lambda30();
                case 56:
                    return Screen1.lambda31();
                case 57:
                    return Screen1.lambda32();
                case 58:
                    return Screen1.lambda33();
                case 59:
                    return this.$main.CheckBox1$Changed();
                case 60:
                    return Screen1.lambda34();
                case 61:
                    return Screen1.lambda35();
                case 62:
                    return Screen1.lambda36();
                case 63:
                    return Screen1.lambda37();
                case 64:
                    return Screen1.lambda38();
                case 65:
                    return Screen1.lambda39();
                case 66:
                    return this.$main.Button4$Click();
                case 67:
                    return Screen1.lambda40();
                case 68:
                    return Screen1.lambda41();
                case 69:
                    return this.$main.Button8$Click();
                case 70:
                    return Screen1.lambda42();
                case 71:
                    return Screen1.lambda43();
                case 73:
                    return Screen1.lambda44();
                case 74:
                    return Screen1.lambda45();
                case 75:
                    return Screen1.lambda46();
                case 76:
                    return Screen1.lambda47();
                case 77:
                    return Screen1.lambda48();
                case 78:
                    return Screen1.lambda49();
                case 79:
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
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 23:
                case 24:
                case 25:
                case 26:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 27:
                case 28:
                case 29:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
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
                case 53:
                case 54:
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
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
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
                case 50:
                case 51:
                case 72:
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
                case 50:
                    return this.$main.WebViewer1$PageLoaded(obj);
                case 51:
                    return this.$main.WebViewer1$BeforePageLoad(obj);
                case 72:
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

    static Object lambda10() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        SimpleSymbol simpleSymbol3;
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol4 = Lit6;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit23, "=") != Boolean.FALSE) {
            simpleSymbol = Lit9;
            simpleSymbol2 = Lit10;
            Boolean bool = Boolean.TRUE;
            simpleSymbol3 = Lit11;
            runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, bool, simpleSymbol3);
            SimpleSymbol simpleSymbol5 = Lit12;
            runtime.callComponentMethod(simpleSymbol5, Lit13, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol5, Lit14, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol5, Lit15, LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>"), Lit24);
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit17), Lit25, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            simpleSymbol = Lit9;
            simpleSymbol2 = Lit10;
            Boolean bool2 = Boolean.TRUE;
            simpleSymbol3 = Lit11;
            runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, bool2, simpleSymbol3);
            SimpleSymbol simpleSymbol6 = Lit12;
            runtime.callComponentMethod(simpleSymbol6, Lit13, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol6, Lit14, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol6, Lit15, LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>"), Lit26);
            runtime.callComponentMethod(Lit20, Lit21, LList.list1("/Download/snc.html"), Lit27);
        }
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, Boolean.FALSE, simpleSymbol3);
    }

    static Object lambda8() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        SimpleSymbol simpleSymbol3;
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol4 = Lit6;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit4), Lit8, "=") != Boolean.FALSE) {
            simpleSymbol = Lit9;
            simpleSymbol2 = Lit10;
            Boolean bool = Boolean.TRUE;
            simpleSymbol3 = Lit11;
            runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, bool, simpleSymbol3);
            SimpleSymbol simpleSymbol5 = Lit12;
            runtime.callComponentMethod(simpleSymbol5, Lit13, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol5, Lit14, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol5, Lit15, LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>"), Lit16);
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit17), Lit18, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            simpleSymbol = Lit9;
            simpleSymbol2 = Lit10;
            Boolean bool2 = Boolean.TRUE;
            simpleSymbol3 = Lit11;
            runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, bool2, simpleSymbol3);
            SimpleSymbol simpleSymbol6 = Lit12;
            runtime.callComponentMethod(simpleSymbol6, Lit13, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol6, Lit14, LList.Empty, LList.Empty);
            runtime.callComponentMethod(simpleSymbol6, Lit15, LList.list1(" <script>       let root = document.getElementById('root')       function clearCache() {          root.innerHTML += 'Cache cleared using location.reload(true)'          windows.location.reload(true)       }    </script>"), Lit19);
            runtime.callComponentMethod(Lit20, Lit21, LList.list1("/Download/snc.html"), Lit22);
        }
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, Boolean.FALSE, simpleSymbol3);
    }

    static Procedure lambda9() {
        return lambda$Fn9;
    }

    static Procedure lambda12() {
        return lambda$Fn12;
    }

    static Boolean lambda13() {
        return Boolean.FALSE;
    }

    static String lambda14() {
        return "<html><body><a href=\"https://ibb.co/Pz6FK6b\"><img src=\"https://i.ibb.co/x87XW7c/Screen-Shot-2024-06-23-at-5-33-20-PM.png\" alt=\"Screen-Shot-2024-06-23-at-5-33-20-PM\" border=\"0\"></a>";
    }

    static Object lambda15() {
        SimpleSymbol simpleSymbol = Lit0;
        SimpleSymbol simpleSymbol2 = Lit30;
        SimpleSymbol simpleSymbol3 = Lit31;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, "Screen for browser", simpleSymbol3);
        SimpleSymbol simpleSymbol4 = Lit32;
        IntNum intNum = Lit33;
        SimpleSymbol simpleSymbol5 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol4, intNum, simpleSymbol5);
        SimpleSymbol simpleSymbol6 = Lit35;
        Boolean bool = Boolean.TRUE;
        SimpleSymbol simpleSymbol7 = Lit11;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol6, bool, simpleSymbol7);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit36, "Sonic Channel ", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit37, "zoom", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit38, "162-1629457_classic-sonic-sonic-the-hedgehog-classic-sonic.png", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit39, "zoom", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit40, "unspecified", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit41, Boolean.TRUE, simpleSymbol7);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit42, "Responsive", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit43, "Sonic Channel - Android", simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit44, Lit45, simpleSymbol5);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit46, "1.7 (Speedway)", simpleSymbol3);
    }

    public Object Screen1$Initialize() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit9, Lit10, Boolean.FALSE, Lit11);
        SimpleSymbol simpleSymbol = Lit47;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit48, "mixkit-software-interface-start-2574.wav", Lit31);
        return runtime.callComponentMethod(simpleSymbol, Lit49, LList.Empty, LList.Empty);
    }

    public Object Screen1$BackPressed() {
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol = Lit12;
        return runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethod(simpleSymbol, Lit52, LList.Empty, LList.Empty), Boolean.TRUE), Lit53, "=") != Boolean.FALSE ? runtime.callComponentMethod(simpleSymbol, Lit54, LList.Empty, LList.Empty) : Values.empty;
    }

    static Object lambda16() {
        SimpleSymbol simpleSymbol = Lit58;
        SimpleSymbol simpleSymbol2 = Lit59;
        IntNum intNum = Lit60;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit61, Lit62, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit64, simpleSymbol3);
    }

    static Object lambda17() {
        SimpleSymbol simpleSymbol = Lit58;
        SimpleSymbol simpleSymbol2 = Lit59;
        IntNum intNum = Lit60;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit61, Lit62, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit64, simpleSymbol3);
    }

    static Object lambda18() {
        SimpleSymbol simpleSymbol = Lit9;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit67, Boolean.FALSE, Lit11);
        SimpleSymbol simpleSymbol2 = Lit68;
        IntNum intNum = Lit69;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit70, "6bac76c8102792b993f0007413b786c6_w200.gif", Lit31);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, intNum, simpleSymbol3);
    }

    static Object lambda19() {
        SimpleSymbol simpleSymbol = Lit9;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit67, Boolean.FALSE, Lit11);
        SimpleSymbol simpleSymbol2 = Lit68;
        IntNum intNum = Lit69;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit70, "6bac76c8102792b993f0007413b786c6_w200.gif", Lit31);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, intNum, simpleSymbol3);
    }

    static Object lambda20() {
        SimpleSymbol simpleSymbol = Lit74;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, Lit34);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Go Home", Lit31);
    }

    static Object lambda21() {
        SimpleSymbol simpleSymbol = Lit74;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, Lit34);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Go Home", Lit31);
    }

    public Object Button3$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit12, Lit77, LList.Empty, LList.Empty);
    }

    static Object lambda22() {
        SimpleSymbol simpleSymbol = Lit81;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, Lit34);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Go to regular website", Lit31);
    }

    static Object lambda23() {
        SimpleSymbol simpleSymbol = Lit81;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, Lit34);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Go to regular website", Lit31);
    }

    public Object Button5$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit83, Lit84, LList.list3("Depending on your device's capabilities, you may not be able to browse the site without lag", "Warning", "OK"), Lit85);
        SimpleSymbol simpleSymbol = Lit47;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit48, "mixkit-elevator-tone-2863.wav", Lit31);
        runtime.callComponentMethod(simpleSymbol, Lit49, LList.Empty, LList.Empty);
        return runtime.callComponentMethod(Lit12, Lit86, LList.list1("https://sites.google.com/view/sonicnews-snc/home"), Lit87);
    }

    static Object lambda24() {
        SimpleSymbol simpleSymbol = Lit90;
        SimpleSymbol simpleSymbol2 = Lit91;
        IntNum intNum = Lit17;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit68, Lit69, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit70, "image.png", Lit31);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit92, simpleSymbol3);
    }

    static Object lambda25() {
        SimpleSymbol simpleSymbol = Lit90;
        SimpleSymbol simpleSymbol2 = Lit91;
        IntNum intNum = Lit17;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit68, Lit69, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit70, "image.png", Lit31);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit92, simpleSymbol3);
    }

    public Object Button7$Click() {
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol = Lit12;
        return runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethod(simpleSymbol, Lit94, LList.Empty, LList.Empty), Boolean.TRUE), Lit95, "=") != Boolean.FALSE ? runtime.callComponentMethod(simpleSymbol, Lit96, LList.Empty, LList.Empty) : Values.empty;
    }

    static Object lambda26() {
        return runtime.setAndCoerceProperty$Ex(Lit12, Lit99, "https://sites.google.com/view/snc-mobile/home", Lit31);
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit12, Lit99, "https://sites.google.com/view/snc-mobile/home", Lit31);
    }

    public Object WebViewer1$PageLoaded(Object $url) {
        runtime.sanitizeComponentData($url);
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit101, Lit10, Boolean.FALSE, Lit11);
        return runtime.callComponentMethod(Lit83, Lit102, LList.Empty, LList.Empty);
    }

    public Object WebViewer1$BeforePageLoad(Object $url) {
        runtime.sanitizeComponentData($url);
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit10, Boolean.TRUE, Lit11);
    }

    public Object WebViewer1$ErrorOccurred(Object $errorCode, Object $description, Object $failingUrl) {
        runtime.sanitizeComponentData($errorCode);
        runtime.sanitizeComponentData($description);
        runtime.sanitizeComponentData($failingUrl);
        runtime.setThisForm();
        runtime.callComponentMethod(Lit107, Lit108, LList.list1("An error has occured"), Lit109);
        return runtime.callComponentMethod(Lit83, Lit84, LList.list3("Page could not load. Either this is an error with your internet connection or this could be an app error. Try getting back online.", "Error", "OK"), Lit110);
    }

    static Object lambda28() {
        SimpleSymbol simpleSymbol = Lit114;
        SimpleSymbol simpleSymbol2 = Lit59;
        IntNum intNum = Lit60;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit115, Lit116, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit61, Lit117, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit64, simpleSymbol3);
    }

    static Object lambda29() {
        SimpleSymbol simpleSymbol = Lit114;
        SimpleSymbol simpleSymbol2 = Lit59;
        IntNum intNum = Lit60;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit115, Lit116, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit61, Lit117, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit64, simpleSymbol3);
    }

    static Object lambda30() {
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit10, Boolean.FALSE, Lit11);
    }

    static Object lambda31() {
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit10, Boolean.FALSE, Lit11);
    }

    static Object lambda32() {
        return runtime.setAndCoerceProperty$Ex(Lit122, Lit75, "AutoClean for app files", Lit31);
    }

    static Object lambda33() {
        return runtime.setAndCoerceProperty$Ex(Lit122, Lit75, "AutoClean for app files", Lit31);
    }

    public Object CheckBox1$Changed() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list1;
        PairWithPosition pairWithPosition;
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        SimpleSymbol simpleSymbol3 = Lit122;
        SimpleSymbol simpleSymbol4 = Lit124;
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.getProperty$1(simpleSymbol3, simpleSymbol4), Boolean.TRUE), Lit125, "=") != Boolean.FALSE) {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit6, Lit17);
            SimpleSymbol simpleSymbol5 = Lit47;
            runtime.setAndCoerceProperty$Ex(simpleSymbol5, Lit48, "mixkit-elevator-tone-2863.wav", Lit31);
            runtime.callComponentMethod(simpleSymbol5, Lit49, LList.Empty, LList.Empty);
            simpleSymbol = Lit83;
            simpleSymbol2 = Lit84;
            list1 = LList.list3("Written files by the app will be erased every 3 minutes with AutoClean.", "Written file will be erased every 3 minutes.", "OK");
            pairWithPosition = Lit126;
        } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.getProperty$1(simpleSymbol3, simpleSymbol4), Boolean.FALSE), Lit127, "=") == Boolean.FALSE) {
            return Values.empty;
        } else {
            runtime.addGlobalVarToCurrentFormEnvironment(Lit6, Lit4);
            simpleSymbol = Lit83;
            simpleSymbol2 = Lit128;
            list1 = LList.list1("Written files by the app will not be erased with AutoClean.");
            pairWithPosition = Lit129;
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list1, pairWithPosition);
    }

    static Object lambda34() {
        SimpleSymbol simpleSymbol = Lit133;
        SimpleSymbol simpleSymbol2 = Lit91;
        DFloNum dFloNum = Lit134;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, dFloNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Write an HTML ", Lit31);
    }

    static Object lambda35() {
        SimpleSymbol simpleSymbol = Lit133;
        SimpleSymbol simpleSymbol2 = Lit91;
        DFloNum dFloNum = Lit134;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, dFloNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Write an HTML ", Lit31);
    }

    public Object Button4$Click() {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2;
        Pair list1;
        PairWithPosition pairWithPosition;
        runtime.setThisForm();
        SimpleSymbol simpleSymbol3 = Lit20;
        runtime.setAndCoerceProperty$Ex(simpleSymbol3, Lit136, FileScope.Shared, Lit137);
        if (runtime.processAndDelayed$V(new Object[]{lambda$Fn35, lambda$Fn36, lambda$Fn37, lambda$Fn38}) != Boolean.FALSE) {
            simpleSymbol = Lit83;
            simpleSymbol2 = Lit84;
            list1 = LList.list3("Before you attempt to save the HTML file, please ensure permissions for writing to external storage is enabled.", "Warning", "OK, I'll enable permissions");
            pairWithPosition = Lit142;
        } else {
            ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
            SimpleSymbol simpleSymbol4 = Lit143;
            if (runtime.callYailPrimitive(moduleMethod, LList.list2(runtime.callComponentMethodWithBlockingContinuation(simpleSymbol3, simpleSymbol4, LList.list2(FileScope.Shared, "/Download/snc.html"), Lit144), Boolean.FALSE), Lit145, "=") != Boolean.FALSE) {
                runtime.callComponentMethod(simpleSymbol3, Lit146, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit29, runtime.$Stthe$Mnnull$Mnvalue$St), "/Download/snc.html"), Lit147);
                SimpleSymbol simpleSymbol5 = Lit47;
                runtime.setAndCoerceProperty$Ex(simpleSymbol5, Lit48, "mixkit-elevator-tone-2863.wav", Lit31);
                runtime.callComponentMethod(simpleSymbol5, Lit49, LList.Empty, LList.Empty);
                simpleSymbol = Lit83;
                simpleSymbol2 = Lit84;
                list1 = LList.list3("The HTML was created. Open this in your browser. This HTML does not refer to the entire page.", "File has been created", "OK");
                pairWithPosition = Lit148;
            } else if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.callComponentMethodWithBlockingContinuation(simpleSymbol3, simpleSymbol4, LList.list2(FileScope.Shared, "/Download/snc.html"), Lit149), Boolean.TRUE), Lit150, "=") == Boolean.FALSE) {
                return Values.empty;
            } else {
                SimpleSymbol simpleSymbol6 = Lit47;
                runtime.setAndCoerceProperty$Ex(simpleSymbol6, Lit48, "mixkit-elevator-tone-2863.wav", Lit31);
                runtime.callComponentMethod(simpleSymbol6, Lit49, LList.Empty, LList.Empty);
                simpleSymbol = Lit83;
                simpleSymbol2 = Lit151;
                list1 = LList.list1("An HTML of the same name (snc.html) already exists. Would you like to delete the original and replace it or don't do anything.");
                LList.chain4(list1, "Warning", "Yes", "No", Boolean.FALSE);
                pairWithPosition = Lit152;
            }
        }
        return runtime.callComponentMethod(simpleSymbol, simpleSymbol2, list1, pairWithPosition);
    }

    static Object lambda36() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.WriteExternalStorage, Boolean.FALSE), Lit138, "=");
    }

    static Object lambda37() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.ReadMediaVideo, Boolean.FALSE), Lit139, "=");
    }

    static Object lambda38() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.ReadMediaImages, Boolean.FALSE), Lit140, "=");
    }

    static Object lambda39() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(Permission.ReadMediaAudio, Boolean.FALSE), Lit141, "=");
    }

    static Object lambda40() {
        SimpleSymbol simpleSymbol = Lit155;
        SimpleSymbol simpleSymbol2 = Lit68;
        IntNum intNum = Lit156;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Sonic Official", Lit31);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit157, simpleSymbol3);
    }

    static Object lambda41() {
        SimpleSymbol simpleSymbol = Lit155;
        SimpleSymbol simpleSymbol2 = Lit68;
        IntNum intNum = Lit156;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit71, Lit17, simpleSymbol3);
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit75, "Sonic Official", Lit31);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit63, Lit157, simpleSymbol3);
    }

    public Object Button8$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit83, Lit159, LList.list2("The Sonic website usually takes a while to load up...", "URL Loading"), Lit160);
        return runtime.callComponentMethod(Lit12, Lit86, LList.list1("sonicthehedgehog.com"), Lit161);
    }

    static Object lambda42() {
        SimpleSymbol simpleSymbol = Lit83;
        SimpleSymbol simpleSymbol2 = Lit61;
        IntNum intNum = Lit164;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit165, Lit166, simpleSymbol3);
    }

    static Object lambda43() {
        SimpleSymbol simpleSymbol = Lit83;
        SimpleSymbol simpleSymbol2 = Lit61;
        IntNum intNum = Lit164;
        SimpleSymbol simpleSymbol3 = Lit34;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, intNum, simpleSymbol3);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit165, Lit166, simpleSymbol3);
    }

    public Object Notifier1$AfterChoosing(Object $choice) {
        Object obj;
        Object $choice2 = runtime.sanitizeComponentData($choice);
        runtime.setThisForm();
        ModuleMethod moduleMethod = runtime.yail$Mnequal$Qu;
        if ($choice2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit168), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $choice2;
        }
        if (runtime.callYailPrimitive(moduleMethod, LList.list2(obj, "Yes"), Lit169, "=") != Boolean.FALSE) {
            runtime.callComponentMethod(Lit20, Lit146, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit29, runtime.$Stthe$Mnnull$Mnvalue$St), "/Download/snc.html"), Lit170);
            SimpleSymbol simpleSymbol = Lit47;
            runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit48, "mixkit-correct-answer-tone-2870.wav", Lit31);
            runtime.callComponentMethod(simpleSymbol, Lit49, LList.Empty, LList.Empty);
            return runtime.callComponentMethod(Lit83, Lit84, LList.list3("Open the HTML in your browser!", "File has been created", "OK"), Lit171);
        }
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2($choice2 instanceof Package ? runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit168), " is not bound in the current context"), "Unbound Variable") : $choice2, "No"), Lit172, "=") != Boolean.FALSE ? Boolean.FALSE : Values.empty;
    }

    static Object lambda44() {
        SimpleSymbol simpleSymbol = Lit20;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit178, "Shared", Lit137);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit179, Boolean.TRUE, Lit11);
    }

    static Object lambda45() {
        SimpleSymbol simpleSymbol = Lit20;
        runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit178, "Shared", Lit137);
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, Lit179, Boolean.TRUE, Lit11);
    }

    static Object lambda46() {
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit48, "mixkit-software-interface-start-2574.wav", Lit31);
    }

    static Object lambda47() {
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit48, "mixkit-software-interface-start-2574.wav", Lit31);
    }

    static Object lambda48() {
        return runtime.setAndCoerceProperty$Ex(Lit184, Lit185, Lit186, Lit34);
    }

    static Object lambda49() {
        return runtime.setAndCoerceProperty$Ex(Lit184, Lit185, Lit186, Lit34);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit3;
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, runtime.callYailPrimitive(AddOp.$Pl, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol, runtime.$Stthe$Mnnull$Mnvalue$St), Lit17), Lit188, "+"));
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(simpleSymbol, runtime.$Stthe$Mnnull$Mnvalue$St), Lit189), Lit190, "=") == Boolean.FALSE) {
            return Boolean.FALSE;
        }
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St));
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
