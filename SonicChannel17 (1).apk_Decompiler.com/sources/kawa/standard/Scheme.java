package kawa.standard;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.DisplayFormat;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEq;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.Map;
import gnu.kawa.functions.Not;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.functions.NumberPredicate;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReaderDispatch;
import gnu.kawa.lispexpr.ReaderDispatchMisc;
import gnu.kawa.lispexpr.ReaderParens;
import gnu.kawa.lispexpr.ReaderQuote;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.AbstractFormat;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.InheritingEnvironment;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleEnvironment;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kawa.lang.Eval;

public class Scheme extends LispLanguage {
    public static final Apply apply;
    static final Declaration applyFieldDecl;
    public static final ApplyToArgs applyToArgs;
    public static LangPrimType booleanType;
    public static final AbstractFormat displayFormat = new DisplayFormat(false, 'S');
    public static final Map forEach;
    public static final Scheme instance;
    public static final InstanceOf instanceOf;
    public static final IsEq isEq;
    public static final IsEqual isEqual;
    public static final IsEqv isEqv;
    public static final NumberPredicate isEven;
    public static final NumberPredicate isOdd;
    protected static final SimpleEnvironment kawaEnvironment;
    public static final Map map;
    public static final Not not;
    public static final Environment nullEnvironment;
    public static final NumberCompare numEqu;
    public static final NumberCompare numGEq;
    public static final NumberCompare numGrt;
    public static final NumberCompare numLEq;
    public static final NumberCompare numLss;
    public static final Environment r4Environment;
    public static final Environment r5Environment;
    static HashMap<Type, String> typeToStringMap;
    static HashMap<String, Type> types;
    public static final Namespace unitNamespace = Namespace.valueOf("http://kawa.gnu.org/unit", "unit");
    public static final AbstractFormat writeFormat = new DisplayFormat(true, 'S');

    static {
        SimpleEnvironment make = Environment.make("null-environment");
        nullEnvironment = make;
        InheritingEnvironment make2 = Environment.make("r4rs-environment", make);
        r4Environment = make2;
        InheritingEnvironment make3 = Environment.make("r5rs-environment", make2);
        r5Environment = make3;
        InheritingEnvironment make4 = Environment.make("kawa-environment", make3);
        kawaEnvironment = make4;
        Scheme scheme = new Scheme(make4);
        instance = scheme;
        instanceOf = new InstanceOf(scheme, GetNamedPart.INSTANCEOF_METHOD_NAME);
        not = new Not(scheme, "not");
        ApplyToArgs applyToArgs2 = new ApplyToArgs("apply-to-args", scheme);
        applyToArgs = applyToArgs2;
        Declaration declarationFromStatic = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "applyToArgs");
        applyFieldDecl = declarationFromStatic;
        apply = new Apply("apply", applyToArgs2);
        IsEq isEq2 = new IsEq(scheme, "eq?");
        isEq = isEq2;
        isEqv = new IsEqv(scheme, "eqv?", isEq2);
        isEqual = new IsEqual(scheme, "equal?");
        map = new Map(true, applyToArgs2, declarationFromStatic, isEq2);
        forEach = new Map(false, applyToArgs2, declarationFromStatic, isEq2);
        numEqu = NumberCompare.make(scheme, "=", 8);
        numGrt = NumberCompare.make(scheme, ">", 16);
        numGEq = NumberCompare.make(scheme, ">=", 24);
        numLss = NumberCompare.make(scheme, "<", 4);
        numLEq = NumberCompare.make(scheme, "<=", 12);
        isOdd = new NumberPredicate(scheme, "odd?", 1);
        isEven = new NumberPredicate(scheme, "even?", 2);
        scheme.initScheme();
        int withServlets = HttpRequestContext.importServletDefinitions;
        if (withServlets > 0) {
            try {
                scheme.loadClass(withServlets > 1 ? "gnu.kawa.servlet.servlets" : "gnu.kawa.servlet.HTTP");
            } catch (Throwable th) {
            }
        }
    }

    public static Scheme getInstance() {
        return instance;
    }

    public static Environment builtin() {
        return kawaEnvironment;
    }

    /* JADX WARNING: type inference failed for: r12v0, types: [kawa.standard.Scheme] */
    private void initScheme() {
        Environment environment = nullEnvironment;
        this.environ = environment;
        this.environ.addLocation(LispLanguage.lookup_sym, (Object) null, getNamedPartLocation);
        defSntxStFld("lambda", "kawa.standard.SchemeCompilation", "lambda");
        defSntxStFld(LispLanguage.quote_sym, "kawa.lang.Quote", "plainQuote");
        defSntxStFld("%define", "kawa.standard.define", "defineRaw");
        defSntxStFld("define", "kawa.lib.prim_syntax");
        defSntxStFld("if", "kawa.lib.prim_syntax");
        defSntxStFld("set!", "kawa.standard.set_b", "set");
        defSntxStFld("cond", "kawa.lib.std_syntax");
        defSntxStFld("case", "kawa.lib.std_syntax");
        defSntxStFld("and", "kawa.lib.std_syntax");
        defSntxStFld("or", "kawa.lib.std_syntax");
        defSntxStFld("%let", "kawa.standard.let", "let");
        defSntxStFld("let", "kawa.lib.std_syntax");
        defSntxStFld("let*", "kawa.lib.std_syntax");
        defSntxStFld("letrec", "kawa.lib.prim_syntax");
        defSntxStFld("begin", "kawa.standard.begin", "begin");
        defSntxStFld("do", "kawa.lib.std_syntax");
        defSntxStFld("delay", "kawa.lib.std_syntax");
        defProcStFld("%make-promise", "kawa.lib.std_syntax");
        defSntxStFld(LispLanguage.quasiquote_sym, "kawa.lang.Quote", "quasiQuote");
        defSntxStFld("define-syntax", "kawa.lib.prim_syntax");
        defSntxStFld("let-syntax", "kawa.standard.let_syntax", "let_syntax");
        defSntxStFld("letrec-syntax", "kawa.standard.let_syntax", "letrec_syntax");
        defSntxStFld("syntax-rules", "kawa.standard.syntax_rules", "syntax_rules");
        environment.setLocked();
        Environment environment2 = r4Environment;
        this.environ = environment2;
        defProcStFld("not", "kawa.standard.Scheme");
        defProcStFld("boolean?", "kawa.lib.misc");
        defProcStFld("eq?", "kawa.standard.Scheme", "isEq");
        defProcStFld("eqv?", "kawa.standard.Scheme", "isEqv");
        defProcStFld("equal?", "kawa.standard.Scheme", "isEqual");
        defProcStFld("pair?", "kawa.lib.lists");
        defProcStFld("cons", "kawa.lib.lists");
        defProcStFld("car", "kawa.lib.lists");
        defProcStFld("cdr", "kawa.lib.lists");
        defProcStFld("set-car!", "kawa.lib.lists");
        defProcStFld("set-cdr!", "kawa.lib.lists");
        defProcStFld("caar", "kawa.lib.lists");
        defProcStFld("cadr", "kawa.lib.lists");
        defProcStFld("cdar", "kawa.lib.lists");
        defProcStFld("cddr", "kawa.lib.lists");
        defProcStFld("caaar", "kawa.lib.lists");
        defProcStFld("caadr", "kawa.lib.lists");
        defProcStFld("cadar", "kawa.lib.lists");
        defProcStFld("caddr", "kawa.lib.lists");
        defProcStFld("cdaar", "kawa.lib.lists");
        defProcStFld("cdadr", "kawa.lib.lists");
        defProcStFld("cddar", "kawa.lib.lists");
        defProcStFld("cdddr", "kawa.lib.lists");
        defProcStFld("caaaar", "kawa.lib.lists");
        defProcStFld("caaadr", "kawa.lib.lists");
        defProcStFld("caadar", "kawa.lib.lists");
        defProcStFld("caaddr", "kawa.lib.lists");
        defProcStFld("cadaar", "kawa.lib.lists");
        defProcStFld("cadadr", "kawa.lib.lists");
        defProcStFld("caddar", "kawa.lib.lists");
        defProcStFld("cadddr", "kawa.lib.lists");
        defProcStFld("cdaaar", "kawa.lib.lists");
        defProcStFld("cdaadr", "kawa.lib.lists");
        defProcStFld("cdadar", "kawa.lib.lists");
        defProcStFld("cdaddr", "kawa.lib.lists");
        defProcStFld("cddaar", "kawa.lib.lists");
        defProcStFld("cddadr", "kawa.lib.lists");
        defProcStFld("cdddar", "kawa.lib.lists");
        defProcStFld("cddddr", "kawa.lib.lists");
        defProcStFld("null?", "kawa.lib.lists");
        defProcStFld("list?", "kawa.lib.lists");
        defProcStFld(PropertyTypeConstants.PROPERTY_TYPE_LENGTH, "kawa.lib.lists");
        defProcStFld("append", "kawa.standard.append", "append");
        defProcStFld("reverse", "kawa.lib.lists");
        defProcStFld("reverse!", "kawa.lib.lists");
        defProcStFld("list-tail", "kawa.lib.lists");
        defProcStFld("list-ref", "kawa.lib.lists");
        defProcStFld("memq", "kawa.lib.lists");
        defProcStFld("memv", "kawa.lib.lists");
        defProcStFld("member", "kawa.lib.lists");
        defProcStFld("assq", "kawa.lib.lists");
        defProcStFld("assv", "kawa.lib.lists");
        defProcStFld("assoc", "kawa.lib.lists");
        defProcStFld("symbol?", "kawa.lib.misc");
        defProcStFld("symbol->string", "kawa.lib.misc");
        defProcStFld("string->symbol", "kawa.lib.misc");
        defProcStFld("symbol=?", "kawa.lib.misc");
        defProcStFld("symbol-local-name", "kawa.lib.misc");
        defProcStFld("symbol-namespace", "kawa.lib.misc");
        defProcStFld("symbol-namespace-uri", "kawa.lib.misc");
        defProcStFld("symbol-prefix", "kawa.lib.misc");
        defProcStFld("namespace-uri", "kawa.lib.misc");
        defProcStFld("namespace-prefix", "kawa.lib.misc");
        defProcStFld("number?", "kawa.lib.numbers");
        defProcStFld("quantity?", "kawa.lib.numbers");
        defProcStFld("complex?", "kawa.lib.numbers");
        defProcStFld("real?", "kawa.lib.numbers");
        defProcStFld("rational?", "kawa.lib.numbers");
        defProcStFld("integer?", "kawa.lib.numbers");
        defProcStFld("exact?", "kawa.lib.numbers");
        defProcStFld("inexact?", "kawa.lib.numbers");
        defProcStFld("=", "kawa.standard.Scheme", "numEqu");
        defProcStFld("<", "kawa.standard.Scheme", "numLss");
        defProcStFld(">", "kawa.standard.Scheme", "numGrt");
        defProcStFld("<=", "kawa.standard.Scheme", "numLEq");
        defProcStFld(">=", "kawa.standard.Scheme", "numGEq");
        defProcStFld("zero?", "kawa.lib.numbers");
        defProcStFld("positive?", "kawa.lib.numbers");
        defProcStFld("negative?", "kawa.lib.numbers");
        defProcStFld("odd?", "kawa.standard.Scheme", "isOdd");
        defProcStFld("even?", "kawa.standard.Scheme", "isEven");
        defProcStFld("max", "kawa.lib.numbers");
        defProcStFld("min", "kawa.lib.numbers");
        defProcStFld("+", "gnu.kawa.functions.AddOp", "$Pl");
        defProcStFld("-", "gnu.kawa.functions.AddOp", "$Mn");
        defProcStFld("*", "gnu.kawa.functions.MultiplyOp", "$St");
        defProcStFld("/", "gnu.kawa.functions.DivideOp", "$Sl");
        defProcStFld("abs", "kawa.lib.numbers");
        defProcStFld("quotient", "gnu.kawa.functions.DivideOp", "quotient");
        defProcStFld("remainder", "gnu.kawa.functions.DivideOp", "remainder");
        defProcStFld("modulo", "gnu.kawa.functions.DivideOp", "modulo");
        defProcStFld("div", "gnu.kawa.functions.DivideOp", "div");
        defProcStFld("mod", "gnu.kawa.functions.DivideOp", "mod");
        defProcStFld("div0", "gnu.kawa.functions.DivideOp", "div0");
        defProcStFld("mod0", "gnu.kawa.functions.DivideOp", "mod0");
        defProcStFld("div-and-mod", "kawa.lib.numbers");
        defProcStFld("div0-and-mod0", "kawa.lib.numbers");
        defProcStFld("gcd", "kawa.lib.numbers");
        defProcStFld("lcm", "kawa.lib.numbers");
        defProcStFld("numerator", "kawa.lib.numbers");
        defProcStFld("denominator", "kawa.lib.numbers");
        defProcStFld("floor", "kawa.lib.numbers");
        defProcStFld("ceiling", "kawa.lib.numbers");
        defProcStFld("truncate", "kawa.lib.numbers");
        defProcStFld("round", "kawa.lib.numbers");
        defProcStFld("rationalize", "kawa.lib.numbers");
        defProcStFld("exp", "kawa.lib.numbers");
        defProcStFld("log", "kawa.lib.numbers");
        defProcStFld("sin", "kawa.lib.numbers");
        defProcStFld("cos", "kawa.lib.numbers");
        defProcStFld("tan", "kawa.lib.numbers");
        defProcStFld("asin", "kawa.lib.numbers");
        defProcStFld("acos", "kawa.lib.numbers");
        defProcStFld("atan", "kawa.lib.numbers");
        defProcStFld("sqrt", "kawa.lib.numbers");
        defProcStFld("expt", "kawa.standard.expt");
        defProcStFld("make-rectangular", "kawa.lib.numbers");
        defProcStFld("make-polar", "kawa.lib.numbers");
        defProcStFld("real-part", "kawa.lib.numbers");
        defProcStFld("imag-part", "kawa.lib.numbers");
        defProcStFld("magnitude", "kawa.lib.numbers");
        defProcStFld("angle", "kawa.lib.numbers");
        defProcStFld("inexact", "kawa.lib.numbers");
        defProcStFld("exact", "kawa.lib.numbers");
        defProcStFld("exact->inexact", "kawa.lib.numbers");
        defProcStFld("inexact->exact", "kawa.lib.numbers");
        defProcStFld("number->string", "kawa.lib.numbers");
        defProcStFld("string->number", "kawa.lib.numbers");
        defProcStFld("char?", "kawa.lib.characters");
        defProcStFld("char=?", "kawa.lib.characters");
        defProcStFld("char<?", "kawa.lib.characters");
        defProcStFld("char>?", "kawa.lib.characters");
        defProcStFld("char<=?", "kawa.lib.characters");
        defProcStFld("char>=?", "kawa.lib.characters");
        defProcStFld("char-ci=?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci<?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci>?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci<=?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci>=?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-alphabetic?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-numeric?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-whitespace?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-upper-case?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-lower-case?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-title-case?", "kawa.lib.rnrs.unicode");
        defProcStFld("char->integer", "kawa.lib.characters");
        defProcStFld("integer->char", "kawa.lib.characters");
        defProcStFld("char-upcase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-downcase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-titlecase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-foldcase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-general-category", "kawa.lib.rnrs.unicode");
        defProcStFld("string?", "kawa.lib.strings");
        defProcStFld("make-string", "kawa.lib.strings");
        defProcStFld("string-length", "kawa.lib.strings");
        defProcStFld("string-ref", "kawa.lib.strings");
        defProcStFld("string-set!", "kawa.lib.strings");
        defProcStFld("string=?", "kawa.lib.strings");
        defProcStFld("string<?", "kawa.lib.strings");
        defProcStFld("string>?", "kawa.lib.strings");
        defProcStFld("string<=?", "kawa.lib.strings");
        defProcStFld("string>=?", "kawa.lib.strings");
        defProcStFld("string-ci=?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci<?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci>?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci<=?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci>=?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfd", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfkd", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfc", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfkc", "kawa.lib.rnrs.unicode");
        defProcStFld("substring", "kawa.lib.strings");
        defProcStFld("string-append", "kawa.lib.strings");
        defProcStFld("string-append/shared", "kawa.lib.strings");
        defProcStFld("string->list", "kawa.lib.strings");
        defProcStFld("list->string", "kawa.lib.strings");
        defProcStFld("string-copy", "kawa.lib.strings");
        defProcStFld("string-fill!", "kawa.lib.strings");
        defProcStFld("vector?", "kawa.lib.vectors");
        defProcStFld("make-vector", "kawa.lib.vectors");
        defProcStFld("vector-length", "kawa.lib.vectors");
        defProcStFld("vector-ref", "kawa.lib.vectors");
        defProcStFld("vector-set!", "kawa.lib.vectors");
        defProcStFld("list->vector", "kawa.lib.vectors");
        defProcStFld("vector->list", "kawa.lib.vectors");
        defProcStFld("vector-fill!", "kawa.lib.vectors");
        defProcStFld("vector-append", "kawa.standard.vector_append", "vectorAppend");
        defProcStFld("values-append", "gnu.kawa.functions.AppendValues", "appendValues");
        defProcStFld("procedure?", "kawa.lib.misc");
        defProcStFld("apply", "kawa.standard.Scheme", "apply");
        defProcStFld("map", "kawa.standard.Scheme", "map");
        defProcStFld("for-each", "kawa.standard.Scheme", "forEach");
        defProcStFld("call-with-current-continuation", "gnu.kawa.functions.CallCC", "callcc");
        defProcStFld("call/cc", "kawa.standard.callcc", "callcc");
        defProcStFld("force", "kawa.lib.misc");
        defProcStFld("call-with-input-file", "kawa.lib.ports");
        defProcStFld("call-with-output-file", "kawa.lib.ports");
        defProcStFld("input-port?", "kawa.lib.ports");
        defProcStFld("output-port?", "kawa.lib.ports");
        defProcStFld("current-input-port", "kawa.lib.ports");
        defProcStFld("current-output-port", "kawa.lib.ports");
        defProcStFld("with-input-from-file", "kawa.lib.ports");
        defProcStFld("with-output-to-file", "kawa.lib.ports");
        defProcStFld("open-input-file", "kawa.lib.ports");
        defProcStFld("open-output-file", "kawa.lib.ports");
        defProcStFld("close-input-port", "kawa.lib.ports");
        defProcStFld("close-output-port", "kawa.lib.ports");
        defProcStFld("read", "kawa.lib.ports");
        defProcStFld("read-line", "kawa.lib.ports");
        defProcStFld("read-char", "kawa.standard.readchar", "readChar");
        defProcStFld("peek-char", "kawa.standard.readchar", "peekChar");
        defProcStFld("eof-object?", "kawa.lib.ports");
        defProcStFld("char-ready?", "kawa.lib.ports");
        defProcStFld("write", "kawa.lib.ports");
        defProcStFld("display", "kawa.lib.ports");
        defProcStFld("print-as-xml", "gnu.xquery.lang.XQuery", "writeFormat");
        defProcStFld("write-char", "kawa.lib.ports");
        defProcStFld("newline", "kawa.lib.ports");
        defProcStFld("load", "kawa.standard.load", "load");
        defProcStFld("load-relative", "kawa.standard.load", "loadRelative");
        defProcStFld("transcript-off", "kawa.lib.ports");
        defProcStFld("transcript-on", "kawa.lib.ports");
        defProcStFld("call-with-input-string", "kawa.lib.ports");
        defProcStFld("open-input-string", "kawa.lib.ports");
        defProcStFld("open-output-string", "kawa.lib.ports");
        defProcStFld("get-output-string", "kawa.lib.ports");
        defProcStFld("call-with-output-string", "kawa.lib.ports");
        defProcStFld("force-output", "kawa.lib.ports");
        defProcStFld("port-line", "kawa.lib.ports");
        defProcStFld("set-port-line!", "kawa.lib.ports");
        defProcStFld("port-column", "kawa.lib.ports");
        defProcStFld("current-error-port", "kawa.lib.ports");
        defProcStFld("input-port-line-number", "kawa.lib.ports");
        defProcStFld("set-input-port-line-number!", "kawa.lib.ports");
        defProcStFld("input-port-column-number", "kawa.lib.ports");
        defProcStFld("input-port-read-state", "kawa.lib.ports");
        defProcStFld("default-prompter", "kawa.lib.ports");
        defProcStFld("input-port-prompter", "kawa.lib.ports");
        defProcStFld("set-input-port-prompter!", "kawa.lib.ports");
        defProcStFld("base-uri", "kawa.lib.misc");
        defProcStFld("%syntax-error", "kawa.standard.syntax_error", "syntax_error");
        defProcStFld("syntax-error", "kawa.lib.prim_syntax");
        environment2.setLocked();
        Environment environment3 = r5Environment;
        this.environ = environment3;
        defProcStFld("values", "kawa.lib.misc");
        defProcStFld("call-with-values", "kawa.standard.call_with_values", "callWithValues");
        defSntxStFld("let-values", "kawa.lib.syntax");
        defSntxStFld("let*-values", "kawa.lib.syntax");
        defSntxStFld("case-lambda", "kawa.lib.syntax");
        defSntxStFld("receive", "kawa.lib.syntax");
        defProcStFld("eval", "kawa.lang.Eval");
        defProcStFld("repl", "kawa.standard.SchemeCompilation", "repl");
        defProcStFld("scheme-report-environment", "kawa.lib.misc");
        defProcStFld("null-environment", "kawa.lib.misc");
        defProcStFld("interaction-environment", "kawa.lib.misc");
        defProcStFld("dynamic-wind", "kawa.lib.misc");
        environment3.setLocked();
        SimpleEnvironment simpleEnvironment = kawaEnvironment;
        this.environ = simpleEnvironment;
        defSntxStFld("define-private", "kawa.lib.prim_syntax");
        defSntxStFld("define-constant", "kawa.lib.prim_syntax");
        defSntxStFld("define-autoload", "kawa.standard.define_autoload", "define_autoload");
        defSntxStFld("define-autoloads-from-file", "kawa.standard.define_autoload", "define_autoloads_from_file");
        defProcStFld("exit", "kawa.lib.rnrs.programs");
        defProcStFld("command-line", "kawa.lib.rnrs.programs");
        defProcStFld("bitwise-arithmetic-shift", "gnu.kawa.functions.BitwiseOp", "ashift");
        defProcStFld("arithmetic-shift", "gnu.kawa.functions.BitwiseOp", "ashift");
        defProcStFld("ash", "gnu.kawa.functions.BitwiseOp", "ashift");
        defProcStFld("bitwise-arithmetic-shift-left", "gnu.kawa.functions.BitwiseOp", "ashiftl");
        defProcStFld("bitwise-arithmetic-shift-right", "gnu.kawa.functions.BitwiseOp", "ashiftr");
        defProcStFld("bitwise-and", "gnu.kawa.functions.BitwiseOp", "and");
        defProcStFld("logand", "gnu.kawa.functions.BitwiseOp", "and");
        defProcStFld("bitwise-ior", "gnu.kawa.functions.BitwiseOp", "ior");
        defProcStFld("logior", "gnu.kawa.functions.BitwiseOp", "ior");
        defProcStFld("bitwise-xor", "gnu.kawa.functions.BitwiseOp", "xor");
        defProcStFld("logxor", "gnu.kawa.functions.BitwiseOp", "xor");
        defProcStFld("bitwise-if", "kawa.lib.numbers");
        defProcStFld("bitwise-not", "gnu.kawa.functions.BitwiseOp", "not");
        defProcStFld("lognot", "gnu.kawa.functions.BitwiseOp", "not");
        defProcStFld("logop", "kawa.lib.numbers");
        defProcStFld("bitwise-bit-set?", "kawa.lib.numbers");
        defProcStFld("logbit?", "kawa.lib.numbers", Compilation.mangleNameIfNeeded("bitwise-bit-set?"));
        defProcStFld("logtest", "kawa.lib.numbers");
        defProcStFld("bitwise-bit-count", "kawa.lib.numbers");
        defProcStFld("logcount", "kawa.lib.numbers");
        defProcStFld("bitwise-copy-bit", "kawa.lib.numbers");
        defProcStFld("bitwise-copy-bit-field", "kawa.lib.numbers");
        defProcStFld("bitwise-bit-field", "kawa.lib.numbers");
        defProcStFld("bit-extract", "kawa.lib.numbers", Compilation.mangleNameIfNeeded("bitwise-bit-field"));
        defProcStFld("bitwise-length", "kawa.lib.numbers");
        defProcStFld("integer-length", "kawa.lib.numbers", "bitwise$Mnlength");
        defProcStFld("bitwise-first-bit-set", "kawa.lib.numbers");
        defProcStFld("bitwise-rotate-bit-field", "kawa.lib.numbers");
        defProcStFld("bitwise-reverse-bit-field", "kawa.lib.numbers");
        defProcStFld("string-upcase!", "kawa.lib.strings");
        defProcStFld("string-downcase!", "kawa.lib.strings");
        defProcStFld("string-capitalize!", "kawa.lib.strings");
        defProcStFld("string-upcase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-downcase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-titlecase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-foldcase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-capitalize", "kawa.lib.strings");
        defSntxStFld("primitive-virtual-method", "kawa.standard.prim_method", "virtual_method");
        defSntxStFld("primitive-static-method", "kawa.standard.prim_method", "static_method");
        defSntxStFld("primitive-interface-method", "kawa.standard.prim_method", "interface_method");
        defSntxStFld("primitive-constructor", "kawa.lib.reflection");
        defSntxStFld("primitive-op1", "kawa.standard.prim_method", "op1");
        defSntxStFld("primitive-get-field", "kawa.lib.reflection");
        defSntxStFld("primitive-set-field", "kawa.lib.reflection");
        defSntxStFld("primitive-get-static", "kawa.lib.reflection");
        defSntxStFld("primitive-set-static", "kawa.lib.reflection");
        defSntxStFld("primitive-array-new", "kawa.lib.reflection");
        defSntxStFld("primitive-array-get", "kawa.lib.reflection");
        defSntxStFld("primitive-array-set", "kawa.lib.reflection");
        defSntxStFld("primitive-array-length", "kawa.lib.reflection");
        defProcStFld("subtype?", "kawa.lib.reflection");
        defProcStFld("primitive-throw", "kawa.standard.prim_throw", "primitiveThrow");
        defSntxStFld("try-finally", "kawa.lib.syntax");
        defSntxStFld("try-catch", "kawa.lib.prim_syntax");
        defProcStFld("throw", "kawa.standard.throw_name", "throwName");
        defProcStFld("catch", "kawa.lib.system");
        defProcStFld("error", "kawa.lib.misc");
        defProcStFld("as", "gnu.kawa.functions.Convert", "as");
        defProcStFld(GetNamedPart.INSTANCEOF_METHOD_NAME, "kawa.standard.Scheme", "instanceOf");
        defSntxStFld("synchronized", "kawa.lib.syntax");
        defSntxStFld("object", "kawa.standard.object", "objectSyntax");
        defSntxStFld("define-class", "kawa.standard.define_class", "define_class");
        defSntxStFld("define-simple-class", "kawa.standard.define_class", "define_simple_class");
        defSntxStFld("this", "kawa.standard.thisRef", "thisSyntax");
        defProcStFld("make", "gnu.kawa.reflect.Invoke", "make");
        defProcStFld("slot-ref", "gnu.kawa.reflect.SlotGet", "slotRef");
        defProcStFld("slot-set!", "gnu.kawa.reflect.SlotSet", "set$Mnfield$Ex");
        defProcStFld("field", "gnu.kawa.reflect.SlotGet");
        defProcStFld("class-methods", "gnu.kawa.reflect.ClassMethods", "classMethods");
        defProcStFld("static-field", "gnu.kawa.reflect.SlotGet", "staticField");
        defProcStFld("invoke", "gnu.kawa.reflect.Invoke", "invoke");
        defProcStFld("invoke-static", "gnu.kawa.reflect.Invoke", "invokeStatic");
        defProcStFld("invoke-special", "gnu.kawa.reflect.Invoke", "invokeSpecial");
        defSntxStFld("define-macro", "kawa.lib.syntax");
        defSntxStFld("%define-macro", "kawa.standard.define_syntax", "define_macro");
        defSntxStFld("define-syntax-case", "kawa.lib.syntax");
        defSntxStFld("syntax-case", "kawa.standard.syntax_case", "syntax_case");
        defSntxStFld("%define-syntax", "kawa.standard.define_syntax", "define_syntax");
        defSntxStFld("syntax", "kawa.standard.syntax", "syntax");
        defSntxStFld("quasisyntax", "kawa.standard.syntax", "quasiSyntax");
        defProcStFld("syntax-object->datum", "kawa.lib.std_syntax");
        defProcStFld("datum->syntax-object", "kawa.lib.std_syntax");
        defProcStFld("syntax->expression", "kawa.lib.prim_syntax");
        defProcStFld("syntax-body->expression", "kawa.lib.prim_syntax");
        defProcStFld("generate-temporaries", "kawa.lib.std_syntax");
        defSntxStFld("with-syntax", "kawa.lib.std_syntax");
        defProcStFld("identifier?", "kawa.lib.std_syntax");
        defProcStFld("free-identifier=?", "kawa.lib.std_syntax");
        defProcStFld("syntax-source", "kawa.lib.std_syntax");
        defProcStFld("syntax-line", "kawa.lib.std_syntax");
        defProcStFld("syntax-column", "kawa.lib.std_syntax");
        defSntxStFld("begin-for-syntax", "kawa.lib.std_syntax");
        defSntxStFld("define-for-syntax", "kawa.lib.std_syntax");
        defSntxStFld("include", "kawa.lib.misc_syntax");
        defSntxStFld("include-relative", "kawa.lib.misc_syntax");
        defProcStFld("file-exists?", "kawa.lib.files");
        defProcStFld("file-directory?", "kawa.lib.files");
        defProcStFld("file-readable?", "kawa.lib.files");
        defProcStFld("file-writable?", "kawa.lib.files");
        defProcStFld("delete-file", "kawa.lib.files");
        defProcStFld("system-tmpdir", "kawa.lib.files");
        defProcStFld("make-temporary-file", "kawa.lib.files");
        defProcStFld("rename-file", "kawa.lib.files");
        defProcStFld("copy-file", "kawa.lib.files");
        defProcStFld("create-directory", "kawa.lib.files");
        defProcStFld("->pathname", "kawa.lib.files");
        define("port-char-encoding", Boolean.TRUE);
        define("symbol-read-case", "P");
        defProcStFld("system", "kawa.lib.system");
        defProcStFld("make-process", "kawa.lib.system");
        defProcStFld("tokenize-string-to-string-array", "kawa.lib.system");
        defProcStFld("tokenize-string-using-shell", "kawa.lib.system");
        defProcStFld("command-parse", "kawa.lib.system");
        defProcStFld("process-command-line-assignments", "kawa.lib.system");
        defProcStFld("record-accessor", "kawa.lib.reflection");
        defProcStFld("record-modifier", "kawa.lib.reflection");
        defProcStFld("record-predicate", "kawa.lib.reflection");
        defProcStFld("record-constructor", "kawa.lib.reflection");
        defProcStFld("make-record-type", "kawa.lib.reflection");
        defProcStFld("record-type-descriptor", "kawa.lib.reflection");
        defProcStFld("record-type-name", "kawa.lib.reflection");
        defProcStFld("record-type-field-names", "kawa.lib.reflection");
        defProcStFld("record?", "kawa.lib.reflection");
        defSntxStFld("define-record-type", "gnu.kawa.slib.DefineRecordType");
        defSntxStFld("when", "kawa.lib.syntax");
        defSntxStFld("unless", "kawa.lib.syntax");
        defSntxStFld("fluid-let", "kawa.standard.fluid_let", "fluid_let");
        defSntxStFld("constant-fold", "kawa.standard.constant_fold", "constant_fold");
        defProcStFld("make-parameter", "kawa.lib.parameters");
        defSntxStFld("parameterize", "kawa.lib.parameters");
        defProcStFld("compile-file", "kawa.lib.system");
        defProcStFld("environment-bound?", "kawa.lib.misc");
        defProcStFld("scheme-implementation-version", "kawa.lib.misc");
        defProcStFld("scheme-window", "kawa.lib.windows");
        defSntxStFld("define-procedure", "kawa.lib.std_syntax");
        defProcStFld("add-procedure-properties", "kawa.lib.misc");
        defProcStFld("make-procedure", "gnu.kawa.functions.MakeProcedure", "makeProcedure");
        defProcStFld("procedure-property", "kawa.lib.misc");
        defProcStFld("set-procedure-property!", "kawa.lib.misc");
        defSntxStFld("provide", "kawa.lib.misc_syntax");
        defSntxStFld("test-begin", "kawa.lib.misc_syntax");
        defProcStFld("quantity->number", "kawa.lib.numbers");
        defProcStFld("quantity->unit", "kawa.lib.numbers");
        defProcStFld("make-quantity", "kawa.lib.numbers");
        defSntxStFld("define-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_namespace");
        defSntxStFld("define-xml-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_xml_namespace");
        defSntxStFld("define-private-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_private_namespace");
        defSntxStFld("define-unit", "kawa.standard.define_unit", "define_unit");
        defSntxStFld("define-base-unit", "kawa.standard.define_unit", "define_base_unit");
        defProcStFld("duration", "kawa.lib.numbers");
        defProcStFld("gentemp", "kawa.lib.misc");
        defSntxStFld("defmacro", "kawa.lib.syntax");
        defProcStFld("setter", "gnu.kawa.functions.Setter", "setter");
        defSntxStFld("resource-url", "kawa.lib.misc_syntax");
        defSntxStFld("module-uri", "kawa.lib.misc_syntax");
        defSntxStFld("future", "kawa.lib.thread");
        defProcStFld("sleep", "kawa.lib.thread");
        defProcStFld("runnable", "kawa.lib.thread");
        defSntxStFld("trace", "kawa.lib.trace");
        defSntxStFld("untrace", "kawa.lib.trace");
        defSntxStFld("disassemble", "kawa.lib.trace");
        defProcStFld("format", "gnu.kawa.functions.Format");
        defProcStFld("parse-format", "gnu.kawa.functions.ParseFormat", "parseFormat");
        defProcStFld("make-element", "gnu.kawa.xml.MakeElement", "makeElement");
        defProcStFld("make-attribute", "gnu.kawa.xml.MakeAttribute", "makeAttribute");
        defProcStFld("map-values", "gnu.kawa.functions.ValuesMap", "valuesMap");
        defProcStFld("children", "gnu.kawa.xml.Children", "children");
        defProcStFld("attributes", "gnu.kawa.xml.Attributes");
        defProcStFld("unescaped-data", "gnu.kawa.xml.MakeUnescapedData", "unescapedData");
        defProcStFld("keyword?", "kawa.lib.keywords");
        defProcStFld("keyword->string", "kawa.lib.keywords");
        defProcStFld("string->keyword", "kawa.lib.keywords");
        defSntxStFld("location", "kawa.standard.location", "location");
        defSntxStFld("define-alias", "kawa.standard.define_alias", "define_alias");
        defSntxStFld("define-variable", "kawa.standard.define_variable", "define_variable");
        defSntxStFld("define-member-alias", "kawa.standard.define_member_alias", "define_member_alias");
        defSntxStFld("define-enum", "gnu.kawa.slib.enums");
        defSntxStFld("import", "kawa.lib.syntax");
        defSntxStFld("require", "kawa.standard.require", "require");
        defSntxStFld("module-name", "kawa.standard.module_name", "module_name");
        defSntxStFld("module-extends", "kawa.standard.module_extends", "module_extends");
        defSntxStFld("module-implements", "kawa.standard.module_implements", "module_implements");
        defSntxStFld("module-static", "kawa.standard.module_static", "module_static");
        defSntxStFld("module-export", "kawa.standard.export", "module_export");
        defSntxStFld("export", "kawa.standard.export", "export");
        defSntxStFld("module-compile-options", "kawa.standard.module_compile_options", "module_compile_options");
        defSntxStFld("with-compile-options", "kawa.standard.with_compile_options", "with_compile_options");
        defProcStFld("array?", "kawa.lib.arrays");
        defProcStFld("array-rank", "kawa.lib.arrays");
        defProcStFld("make-array", "kawa.lib.arrays");
        defProcStFld("array", "kawa.lib.arrays");
        defProcStFld("array-start", "kawa.lib.arrays");
        defProcStFld("array-end", "kawa.lib.arrays");
        defProcStFld("shape", "kawa.lib.arrays");
        defProcStFld("array-ref", "gnu.kawa.functions.ArrayRef", "arrayRef");
        defProcStFld("array-set!", "gnu.kawa.functions.ArraySet", "arraySet");
        defProcStFld("share-array", "kawa.lib.arrays");
        defProcStFld("s8vector?", "kawa.lib.uniform");
        defProcStFld("make-s8vector", "kawa.lib.uniform");
        defProcStFld("s8vector", "kawa.lib.uniform");
        defProcStFld("s8vector-length", "kawa.lib.uniform");
        defProcStFld("s8vector-ref", "kawa.lib.uniform");
        defProcStFld("s8vector-set!", "kawa.lib.uniform");
        defProcStFld("s8vector->list", "kawa.lib.uniform");
        defProcStFld("list->s8vector", "kawa.lib.uniform");
        defProcStFld("u8vector?", "kawa.lib.uniform");
        defProcStFld("make-u8vector", "kawa.lib.uniform");
        defProcStFld("u8vector", "kawa.lib.uniform");
        defProcStFld("u8vector-length", "kawa.lib.uniform");
        defProcStFld("u8vector-ref", "kawa.lib.uniform");
        defProcStFld("u8vector-set!", "kawa.lib.uniform");
        defProcStFld("u8vector->list", "kawa.lib.uniform");
        defProcStFld("list->u8vector", "kawa.lib.uniform");
        defProcStFld("s16vector?", "kawa.lib.uniform");
        defProcStFld("make-s16vector", "kawa.lib.uniform");
        defProcStFld("s16vector", "kawa.lib.uniform");
        defProcStFld("s16vector-length", "kawa.lib.uniform");
        defProcStFld("s16vector-ref", "kawa.lib.uniform");
        defProcStFld("s16vector-set!", "kawa.lib.uniform");
        defProcStFld("s16vector->list", "kawa.lib.uniform");
        defProcStFld("list->s16vector", "kawa.lib.uniform");
        defProcStFld("u16vector?", "kawa.lib.uniform");
        defProcStFld("make-u16vector", "kawa.lib.uniform");
        defProcStFld("u16vector", "kawa.lib.uniform");
        defProcStFld("u16vector-length", "kawa.lib.uniform");
        defProcStFld("u16vector-ref", "kawa.lib.uniform");
        defProcStFld("u16vector-set!", "kawa.lib.uniform");
        defProcStFld("u16vector->list", "kawa.lib.uniform");
        defProcStFld("list->u16vector", "kawa.lib.uniform");
        defProcStFld("s32vector?", "kawa.lib.uniform");
        defProcStFld("make-s32vector", "kawa.lib.uniform");
        defProcStFld("s32vector", "kawa.lib.uniform");
        defProcStFld("s32vector-length", "kawa.lib.uniform");
        defProcStFld("s32vector-ref", "kawa.lib.uniform");
        defProcStFld("s32vector-set!", "kawa.lib.uniform");
        defProcStFld("s32vector->list", "kawa.lib.uniform");
        defProcStFld("list->s32vector", "kawa.lib.uniform");
        defProcStFld("u32vector?", "kawa.lib.uniform");
        defProcStFld("make-u32vector", "kawa.lib.uniform");
        defProcStFld("u32vector", "kawa.lib.uniform");
        defProcStFld("u32vector-length", "kawa.lib.uniform");
        defProcStFld("u32vector-ref", "kawa.lib.uniform");
        defProcStFld("u32vector-set!", "kawa.lib.uniform");
        defProcStFld("u32vector->list", "kawa.lib.uniform");
        defProcStFld("list->u32vector", "kawa.lib.uniform");
        defProcStFld("s64vector?", "kawa.lib.uniform");
        defProcStFld("make-s64vector", "kawa.lib.uniform");
        defProcStFld("s64vector", "kawa.lib.uniform");
        defProcStFld("s64vector-length", "kawa.lib.uniform");
        defProcStFld("s64vector-ref", "kawa.lib.uniform");
        defProcStFld("s64vector-set!", "kawa.lib.uniform");
        defProcStFld("s64vector->list", "kawa.lib.uniform");
        defProcStFld("list->s64vector", "kawa.lib.uniform");
        defProcStFld("u64vector?", "kawa.lib.uniform");
        defProcStFld("make-u64vector", "kawa.lib.uniform");
        defProcStFld("u64vector", "kawa.lib.uniform");
        defProcStFld("u64vector-length", "kawa.lib.uniform");
        defProcStFld("u64vector-ref", "kawa.lib.uniform");
        defProcStFld("u64vector-set!", "kawa.lib.uniform");
        defProcStFld("u64vector->list", "kawa.lib.uniform");
        defProcStFld("list->u64vector", "kawa.lib.uniform");
        defProcStFld("f32vector?", "kawa.lib.uniform");
        defProcStFld("make-f32vector", "kawa.lib.uniform");
        defProcStFld("f32vector", "kawa.lib.uniform");
        defProcStFld("f32vector-length", "kawa.lib.uniform");
        defProcStFld("f32vector-ref", "kawa.lib.uniform");
        defProcStFld("f32vector-set!", "kawa.lib.uniform");
        defProcStFld("f32vector->list", "kawa.lib.uniform");
        defProcStFld("list->f32vector", "kawa.lib.uniform");
        defProcStFld("f64vector?", "kawa.lib.uniform");
        defProcStFld("make-f64vector", "kawa.lib.uniform");
        defProcStFld("f64vector", "kawa.lib.uniform");
        defProcStFld("f64vector-length", "kawa.lib.uniform");
        defProcStFld("f64vector-ref", "kawa.lib.uniform");
        defProcStFld("f64vector-set!", "kawa.lib.uniform");
        defProcStFld("f64vector->list", "kawa.lib.uniform");
        defProcStFld("list->f64vector", "kawa.lib.uniform");
        defSntxStFld("cut", "gnu.kawa.slib.cut");
        defSntxStFld("cute", "gnu.kawa.slib.cut");
        defSntxStFld("cond-expand", "kawa.lib.syntax");
        defSntxStFld("%cond-expand", "kawa.lib.syntax");
        defAliasStFld("*print-base*", "gnu.kawa.functions.DisplayFormat", "outBase");
        defAliasStFld("*print-radix*", "gnu.kawa.functions.DisplayFormat", "outRadix");
        defAliasStFld("*print-right-margin*", "gnu.text.PrettyWriter", "lineLengthLoc");
        defAliasStFld("*print-miser-width*", "gnu.text.PrettyWriter", "miserWidthLoc");
        defAliasStFld("html", "gnu.kawa.xml.XmlNamespace", "HTML");
        defAliasStFld("unit", "kawa.standard.Scheme", "unitNamespace");
        defAliasStFld("path", "gnu.kawa.lispexpr.LangObjType", "pathType");
        defAliasStFld("filepath", "gnu.kawa.lispexpr.LangObjType", "filepathType");
        defAliasStFld("URI", "gnu.kawa.lispexpr.LangObjType", "URIType");
        defProcStFld("resolve-uri", "kawa.lib.files");
        defAliasStFld("vector", "gnu.kawa.lispexpr.LangObjType", "vectorType");
        defAliasStFld(PropertyTypeConstants.PROPERTY_TYPE_STRING, "gnu.kawa.lispexpr.LangObjType", "stringType");
        defAliasStFld("list", "gnu.kawa.lispexpr.LangObjType", "listType");
        defAliasStFld("regex", "gnu.kawa.lispexpr.LangObjType", "regexType");
        defProcStFld("path?", "kawa.lib.files");
        defProcStFld("filepath?", "kawa.lib.files");
        defProcStFld("URI?", "kawa.lib.files");
        defProcStFld("absolute-path?", "kawa.lib.files");
        defProcStFld("path-scheme", "kawa.lib.files");
        defProcStFld("path-authority", "kawa.lib.files");
        defProcStFld("path-user-info", "kawa.lib.files");
        defProcStFld("path-host", "kawa.lib.files");
        defProcStFld("path-port", "kawa.lib.files");
        defProcStFld("path-file", "kawa.lib.files");
        defProcStFld("path-parent", "kawa.lib.files");
        defProcStFld("path-directory", "kawa.lib.files");
        defProcStFld("path-last", "kawa.lib.files");
        defProcStFld("path-extension", "kawa.lib.files");
        defProcStFld("path-fragment", "kawa.lib.files");
        defProcStFld("path-query", "kawa.lib.files");
        simpleEnvironment.setLocked();
    }

    public Scheme() {
        this.environ = kawaEnvironment;
        this.userEnv = getNewEnvironment();
    }

    protected Scheme(Environment env) {
        this.environ = env;
    }

    public String getName() {
        return "Scheme";
    }

    public static Object eval(String string, Environment env) {
        return eval((InPort) new CharArrayInPort(string), env);
    }

    public static Object eval(InPort port, Environment env) {
        SourceMessages messages = new SourceMessages();
        try {
            Object body = ReaderParens.readList((LispReader) Language.getDefaultLanguage().getLexer(port, messages), 0, 1, -1);
            if (!messages.seenErrors()) {
                return Eval.evalBody(body, env, messages);
            }
            throw new SyntaxException(messages);
        } catch (SyntaxException e) {
            throw new RuntimeException("eval: errors while compiling:\n" + e.getMessages().toString(20));
        } catch (IOException e2) {
            throw new RuntimeException("eval: I/O exception: " + e2.toString());
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Error ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            throw new WrappedException(ex3);
        }
    }

    public static Object eval(Object sexpr, Environment env) {
        try {
            return Eval.eval(sexpr, env);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Error ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            throw new WrappedException(ex3);
        }
    }

    public AbstractFormat getFormat(boolean readable) {
        return readable ? writeFormat : displayFormat;
    }

    public int getNamespaceOf(Declaration decl) {
        return 3;
    }

    public static Type getTypeValue(Expression exp) {
        return getInstance().getTypeFor(exp);
    }

    static synchronized HashMap<String, Type> getTypeMap() {
        HashMap<String, Type> hashMap;
        synchronized (Scheme.class) {
            if (types == null) {
                booleanType = new LangPrimType(Type.booleanType, getInstance());
                HashMap<String, Type> hashMap2 = new HashMap<>();
                types = hashMap2;
                hashMap2.put("void", LangPrimType.voidType);
                types.put("int", LangPrimType.intType);
                types.put("char", LangPrimType.charType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, booleanType);
                types.put("byte", LangPrimType.byteType);
                types.put("short", LangPrimType.shortType);
                types.put("long", LangPrimType.longType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_FLOAT, LangPrimType.floatType);
                types.put("double", LangPrimType.doubleType);
                types.put("never-returns", Type.neverReturnsType);
                types.put("Object", Type.objectType);
                types.put("String", Type.toStringType);
                types.put("object", Type.objectType);
                types.put("number", LangObjType.numericType);
                types.put("quantity", ClassType.make("gnu.math.Quantity"));
                types.put("complex", ClassType.make("gnu.math.Complex"));
                types.put("real", LangObjType.realType);
                types.put("rational", LangObjType.rationalType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_INTEGER, LangObjType.integerType);
                types.put("symbol", ClassType.make("gnu.mapping.Symbol"));
                types.put("namespace", ClassType.make("gnu.mapping.Namespace"));
                types.put("keyword", ClassType.make("gnu.expr.Keyword"));
                types.put("pair", ClassType.make("gnu.lists.Pair"));
                types.put("pair-with-position", ClassType.make("gnu.lists.PairWithPosition"));
                types.put("constant-string", ClassType.make("java.lang.String"));
                types.put("abstract-string", ClassType.make("gnu.lists.CharSeq"));
                types.put("character", ClassType.make("gnu.text.Char"));
                types.put("vector", LangObjType.vectorType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_STRING, LangObjType.stringType);
                types.put("list", LangObjType.listType);
                types.put("function", ClassType.make("gnu.mapping.Procedure"));
                types.put("procedure", ClassType.make("gnu.mapping.Procedure"));
                types.put("input-port", ClassType.make("gnu.mapping.InPort"));
                types.put("output-port", ClassType.make("gnu.mapping.OutPort"));
                types.put("string-output-port", ClassType.make("gnu.mapping.CharArrayOutPort"));
                types.put("record", ClassType.make("kawa.lang.Record"));
                types.put("type", LangObjType.typeType);
                types.put("class-type", LangObjType.typeClassType);
                types.put("class", LangObjType.typeClass);
                types.put("s8vector", ClassType.make("gnu.lists.S8Vector"));
                types.put("u8vector", ClassType.make("gnu.lists.U8Vector"));
                types.put("s16vector", ClassType.make("gnu.lists.S16Vector"));
                types.put("u16vector", ClassType.make("gnu.lists.U16Vector"));
                types.put("s32vector", ClassType.make("gnu.lists.S32Vector"));
                types.put("u32vector", ClassType.make("gnu.lists.U32Vector"));
                types.put("s64vector", ClassType.make("gnu.lists.S64Vector"));
                types.put("u64vector", ClassType.make("gnu.lists.U64Vector"));
                types.put("f32vector", ClassType.make("gnu.lists.F32Vector"));
                types.put("f64vector", ClassType.make("gnu.lists.F64Vector"));
                types.put("document", ClassType.make("gnu.kawa.xml.KDocument"));
                types.put("readtable", ClassType.make("gnu.kawa.lispexpr.ReadTable"));
            }
            hashMap = types;
        }
        return hashMap;
    }

    public static Type getNamedType(String name) {
        getTypeMap();
        Type type = types.get(name);
        if (type == null && (name.startsWith("elisp:") || name.startsWith("clisp:"))) {
            int colon = name.indexOf(58);
            Class clas = getNamedType(name.substring(colon + 1)).getReflectClass();
            String lang = name.substring(0, colon);
            Language interp = Language.getInstance(lang);
            if (interp != null) {
                type = interp.getTypeFor(clas);
                if (type != null) {
                    types.put(name, type);
                }
            } else {
                throw new RuntimeException("unknown type '" + name + "' - unknown language '" + lang + '\'');
            }
        }
        return type;
    }

    public Type getTypeFor(Class clas) {
        String name = clas.getName();
        if (clas.isPrimitive()) {
            return getNamedType(name);
        }
        if ("java.lang.String".equals(name)) {
            return Type.toStringType;
        }
        if ("gnu.math.IntNum".equals(name)) {
            return LangObjType.integerType;
        }
        if ("gnu.math.DFloNum".equals(name)) {
            return LangObjType.dflonumType;
        }
        if ("gnu.math.RatNum".equals(name)) {
            return LangObjType.rationalType;
        }
        if ("gnu.math.RealNum".equals(name)) {
            return LangObjType.realType;
        }
        if ("gnu.math.Numeric".equals(name)) {
            return LangObjType.numericType;
        }
        if ("gnu.lists.FVector".equals(name)) {
            return LangObjType.vectorType;
        }
        if ("gnu.lists.LList".equals(name)) {
            return LangObjType.listType;
        }
        if ("gnu.text.Path".equals(name)) {
            return LangObjType.pathType;
        }
        if ("gnu.text.URIPath".equals(name)) {
            return LangObjType.URIType;
        }
        if ("gnu.text.FilePath".equals(name)) {
            return LangObjType.filepathType;
        }
        if ("java.lang.Class".equals(name)) {
            return LangObjType.typeClass;
        }
        if ("gnu.bytecode.Type".equals(name)) {
            return LangObjType.typeType;
        }
        if ("gnu.bytecode.ClassType".equals(name)) {
            return LangObjType.typeClassType;
        }
        return Type.make(clas);
    }

    public String formatType(Type type) {
        if (typeToStringMap == null) {
            typeToStringMap = new HashMap<>();
            for (Map.Entry<String, Type> e : getTypeMap().entrySet()) {
                String s = e.getKey();
                Type t = e.getValue();
                typeToStringMap.put(t, s);
                Type it = t.getImplementationType();
                if (it != t) {
                    typeToStringMap.put(it, s);
                }
            }
        }
        String str = typeToStringMap.get(type);
        if (str != null) {
            return str;
        }
        return super.formatType(type);
    }

    public static Type string2Type(String name) {
        Type t;
        if (name.endsWith("[]")) {
            t = string2Type(name.substring(0, name.length() - 2));
            if (t != null) {
                t = ArrayType.make(t);
            }
        } else {
            t = getNamedType(name);
        }
        if (t != null) {
            return t;
        }
        Type t2 = Language.string2Type(name);
        if (t2 != null) {
            types.put(name, t2);
        }
        return t2;
    }

    public Type getTypeFor(String name) {
        return string2Type(name);
    }

    public static Type exp2Type(Expression exp) {
        return getInstance().getTypeFor(exp);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0104, code lost:
        r5 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression checkDefaultBinding(gnu.mapping.Symbol r30, kawa.lang.Translator r31) {
        /*
            r29 = this;
            r1 = r31
            gnu.mapping.Namespace r2 = r30.getNamespace()
            java.lang.String r3 = r30.getLocalPart()
            boolean r0 = r2 instanceof gnu.kawa.xml.XmlNamespace
            if (r0 == 0) goto L_0x001a
            r0 = r2
            gnu.kawa.xml.XmlNamespace r0 = (gnu.kawa.xml.XmlNamespace) r0
            java.lang.Object r0 = r0.get(r3)
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.getInstance(r0)
            return r0
        L_0x001a:
            java.lang.String r0 = r2.getName()
            gnu.mapping.Namespace r4 = unitNamespace
            java.lang.String r4 = r4.getName()
            if (r0 != r4) goto L_0x0031
            gnu.math.NamedUnit r0 = gnu.math.Unit.lookup(r3)
            if (r0 == 0) goto L_0x0031
            gnu.expr.QuoteExp r4 = gnu.expr.QuoteExp.getInstance(r0)
            return r4
        L_0x0031:
            java.lang.String r0 = r30.toString()
            int r4 = r0.length()
            r5 = 0
            if (r4 != 0) goto L_0x003d
            return r5
        L_0x003d:
            r6 = 2
            r7 = 0
            r8 = 1
            if (r4 <= r8) goto L_0x00b6
            int r9 = r4 + -1
            char r9 = r0.charAt(r9)
            r10 = 63
            if (r9 != r10) goto L_0x00b6
            int r9 = r3.length()
            if (r9 <= r8) goto L_0x00b1
            int r10 = r9 + -1
            java.lang.String r10 = r3.substring(r7, r10)
            java.lang.String r10 = r10.intern()
            gnu.mapping.Symbol r11 = r2.getSymbol(r10)
            gnu.expr.Expression r12 = r1.rewrite(r11, r7)
            boolean r13 = r12 instanceof gnu.expr.ReferenceExp
            if (r13 == 0) goto L_0x007c
            r13 = r12
            gnu.expr.ReferenceExp r13 = (gnu.expr.ReferenceExp) r13
            gnu.expr.Declaration r13 = r13.getBinding()
            if (r13 == 0) goto L_0x007a
            r14 = 65536(0x10000, double:3.2379E-319)
            boolean r14 = r13.getFlag(r14)
            if (r14 == 0) goto L_0x007b
        L_0x007a:
            r12 = 0
        L_0x007b:
            goto L_0x0081
        L_0x007c:
            boolean r13 = r12 instanceof gnu.expr.QuoteExp
            if (r13 != 0) goto L_0x0081
            r12 = 0
        L_0x0081:
            if (r12 == 0) goto L_0x00ac
            gnu.expr.LambdaExp r13 = new gnu.expr.LambdaExp
            r13.<init>((int) r8)
            r14 = r30
            r13.setSymbol(r14)
            r15 = r5
            java.lang.Object r15 = (java.lang.Object) r15
            gnu.expr.Declaration r5 = r13.addDeclaration((java.lang.Object) r5)
            gnu.expr.ApplyExp r15 = new gnu.expr.ApplyExp
            gnu.kawa.reflect.InstanceOf r8 = instanceOf
            gnu.expr.Expression[] r6 = new gnu.expr.Expression[r6]
            r17 = r3
            gnu.expr.ReferenceExp r3 = new gnu.expr.ReferenceExp
            r3.<init>((gnu.expr.Declaration) r5)
            r6[r7] = r3
            r3 = 1
            r6[r3] = r12
            r15.<init>((gnu.mapping.Procedure) r8, (gnu.expr.Expression[]) r6)
            r13.body = r15
            return r13
        L_0x00ac:
            r14 = r30
            r17 = r3
            goto L_0x00ba
        L_0x00b1:
            r14 = r30
            r17 = r3
            goto L_0x00ba
        L_0x00b6:
            r14 = r30
            r17 = r3
        L_0x00ba:
            char r3 = r0.charAt(r7)
            r8 = 46
            r9 = 43
            r10 = 45
            r11 = 10
            if (r3 == r10) goto L_0x00d0
            if (r3 == r9) goto L_0x00d0
            int r12 = java.lang.Character.digit(r3, r11)
            if (r12 < 0) goto L_0x02cc
        L_0x00d0:
            r12 = 0
            r13 = 0
        L_0x00d2:
            if (r13 >= r4) goto L_0x0137
            char r15 = r0.charAt(r13)
            int r18 = java.lang.Character.digit(r15, r11)
            r5 = 4
            r7 = 3
            if (r18 < 0) goto L_0x00eb
            r11 = 5
            if (r12 >= r7) goto L_0x00e5
            r5 = 2
            goto L_0x00e9
        L_0x00e5:
            if (r12 >= r11) goto L_0x00e8
            goto L_0x00e9
        L_0x00e8:
            r5 = 5
        L_0x00e9:
            r12 = r5
            goto L_0x012e
        L_0x00eb:
            if (r15 == r9) goto L_0x00ef
            if (r15 != r10) goto L_0x00f4
        L_0x00ef:
            if (r12 != 0) goto L_0x00f4
            r5 = 1
            r12 = r5
            goto L_0x012e
        L_0x00f4:
            if (r15 != r8) goto L_0x00fb
            if (r12 >= r7) goto L_0x00fb
            r5 = 3
            r12 = r5
            goto L_0x012e
        L_0x00fb:
            r7 = 101(0x65, float:1.42E-43)
            if (r15 == r7) goto L_0x0106
            r7 = 69
            if (r15 != r7) goto L_0x0104
            goto L_0x0106
        L_0x0104:
            r5 = 1
            goto L_0x0138
        L_0x0106:
            if (r12 == r6) goto L_0x010a
            if (r12 != r5) goto L_0x0104
        L_0x010a:
            int r5 = r13 + 1
            if (r5 >= r4) goto L_0x0135
            int r5 = r13 + 1
            char r7 = r0.charAt(r5)
            if (r7 == r10) goto L_0x0118
            if (r7 != r9) goto L_0x0120
        L_0x0118:
            int r5 = r5 + 1
            if (r5 >= r4) goto L_0x0120
            char r7 = r0.charAt(r5)
        L_0x0120:
            r11 = 10
            int r20 = java.lang.Character.digit(r7, r11)
            if (r20 >= 0) goto L_0x012a
            r5 = 1
            goto L_0x0138
        L_0x012a:
            r11 = 5
            int r13 = r5 + 1
            r12 = r11
        L_0x012e:
            r5 = 1
            int r13 = r13 + r5
            r5 = 0
            r7 = 0
            r11 = 10
            goto L_0x00d2
        L_0x0135:
            r5 = 1
            goto L_0x0138
        L_0x0137:
            r5 = 1
        L_0x0138:
            if (r13 >= r4) goto L_0x02ca
            if (r12 <= r5) goto L_0x02ca
            gnu.math.DFloNum r5 = new gnu.math.DFloNum
            r7 = 0
            java.lang.String r11 = r0.substring(r7, r13)
            r5.<init>((java.lang.String) r11)
            r7 = 0
            java.util.Vector r11 = new java.util.Vector
            r11.<init>()
        L_0x014c:
            if (r13 >= r4) goto L_0x022c
            int r15 = r13 + 1
            char r13 = r0.charAt(r13)
            r8 = 42
            if (r13 != r8) goto L_0x0165
            if (r15 != r4) goto L_0x015c
            goto L_0x02cc
        L_0x015c:
            int r8 = r15 + 1
            char r13 = r0.charAt(r15)
            r15 = r8
            r8 = r7
            goto L_0x017a
        L_0x0165:
            r8 = 47
            if (r13 != r8) goto L_0x0179
            if (r15 == r4) goto L_0x02cc
            if (r7 == 0) goto L_0x016f
            goto L_0x02cc
        L_0x016f:
            r7 = 1
            int r8 = r15 + 1
            char r13 = r0.charAt(r15)
            r15 = r8
            r8 = r7
            goto L_0x017a
        L_0x0179:
            r8 = r7
        L_0x017a:
            int r7 = r15 + -1
        L_0x017c:
            boolean r21 = java.lang.Character.isLetter(r13)
            if (r21 != 0) goto L_0x0188
            int r6 = r15 + -1
            if (r6 != r7) goto L_0x018e
            goto L_0x02cc
        L_0x0188:
            if (r15 != r4) goto L_0x021c
            r6 = r15
            r13 = 49
        L_0x018e:
            java.lang.String r10 = r0.substring(r7, r6)
            r11.addElement(r10)
            r10 = 0
            r9 = 94
            if (r13 != r9) goto L_0x01a6
            r10 = 1
            if (r15 != r4) goto L_0x019f
            goto L_0x02cc
        L_0x019f:
            int r9 = r15 + 1
            char r13 = r0.charAt(r15)
            r15 = r9
        L_0x01a6:
            r9 = r8
            r24 = r6
            r6 = 43
            if (r13 != r6) goto L_0x01bd
            r10 = 1
            if (r15 != r4) goto L_0x01b2
            goto L_0x02cc
        L_0x01b2:
            int r23 = r15 + 1
            char r13 = r0.charAt(r15)
            r15 = r23
            r6 = 45
            goto L_0x01d4
        L_0x01bd:
            r6 = 45
            if (r13 != r6) goto L_0x01d4
            r10 = 1
            if (r15 != r4) goto L_0x01c6
            goto L_0x02cc
        L_0x01c6:
            int r22 = r15 + 1
            char r13 = r0.charAt(r15)
            if (r9 != 0) goto L_0x01d0
            r15 = 1
            goto L_0x01d1
        L_0x01d0:
            r15 = 0
        L_0x01d1:
            r9 = r15
            r15 = r22
        L_0x01d4:
            r22 = 0
            r25 = 0
        L_0x01d8:
            r6 = 10
            int r18 = java.lang.Character.digit(r13, r6)
            if (r18 > 0) goto L_0x01e3
            int r15 = r15 + -1
            goto L_0x01ec
        L_0x01e3:
            int r26 = r25 * 10
            int r25 = r26 + r18
            int r22 = r22 + 1
            if (r15 != r4) goto L_0x0210
        L_0x01ec:
            if (r22 != 0) goto L_0x01f7
            r25 = 1
            if (r10 == 0) goto L_0x01f4
            goto L_0x02cc
        L_0x01f4:
            r6 = r25
            goto L_0x01f9
        L_0x01f7:
            r6 = r25
        L_0x01f9:
            if (r9 == 0) goto L_0x01fc
            int r6 = -r6
        L_0x01fc:
            r27 = r7
            gnu.math.IntNum r7 = gnu.math.IntNum.make((int) r6)
            r11.addElement(r7)
            r7 = r8
            r13 = r15
            r6 = 2
            r8 = 46
            r9 = 43
            r10 = 45
            goto L_0x014c
        L_0x0210:
            r27 = r7
            int r6 = r15 + 1
            char r13 = r0.charAt(r15)
            r15 = r6
            r6 = 45
            goto L_0x01d8
        L_0x021c:
            r27 = r7
            int r6 = r15 + 1
            char r13 = r0.charAt(r15)
            r15 = r6
            r6 = 2
            r9 = 43
            r10 = 45
            goto L_0x017c
        L_0x022c:
            if (r13 != r4) goto L_0x02c3
            int r6 = r11.size()
            r8 = 1
            int r6 = r6 >> r8
            gnu.expr.Expression[] r8 = new gnu.expr.Expression[r6]
            r9 = 0
        L_0x0237:
            if (r9 >= r6) goto L_0x0299
            int r10 = r9 * 2
            java.lang.Object r10 = r11.elementAt(r10)
            java.lang.String r10 = (java.lang.String) r10
            gnu.mapping.Namespace r13 = unitNamespace
            java.lang.String r15 = r10.intern()
            gnu.mapping.Symbol r13 = r13.getSymbol(r15)
            gnu.expr.Expression r15 = r1.rewrite(r13)
            int r18 = r9 * 2
            r22 = r7
            r16 = 1
            int r7 = r18 + 1
            java.lang.Object r7 = r11.elementAt(r7)
            gnu.math.IntNum r7 = (gnu.math.IntNum) r7
            long r18 = r7.longValue()
            r23 = 1
            int r20 = (r18 > r23 ? 1 : (r18 == r23 ? 0 : -1))
            if (r20 == 0) goto L_0x0286
            r18 = r10
            gnu.expr.ApplyExp r10 = new gnu.expr.ApplyExp
            r23 = r11
            kawa.standard.expt r11 = kawa.standard.expt.expt
            r24 = r12
            r19 = r13
            r12 = 2
            gnu.expr.Expression[] r13 = new gnu.expr.Expression[r12]
            r12 = 0
            r13[r12] = r15
            gnu.expr.QuoteExp r12 = gnu.expr.QuoteExp.getInstance(r7)
            r16 = 1
            r13[r16] = r12
            r10.<init>((gnu.mapping.Procedure) r11, (gnu.expr.Expression[]) r13)
            r15 = r10
            goto L_0x028e
        L_0x0286:
            r18 = r10
            r23 = r11
            r24 = r12
            r19 = r13
        L_0x028e:
            r8[r9] = r15
            int r9 = r9 + 1
            r7 = r22
            r11 = r23
            r12 = r24
            goto L_0x0237
        L_0x0299:
            r22 = r7
            r23 = r11
            r24 = r12
            r7 = 1
            if (r6 != r7) goto L_0x02a6
            r7 = 0
            r10 = r8[r7]
            goto L_0x02ae
        L_0x02a6:
            gnu.expr.ApplyExp r7 = new gnu.expr.ApplyExp
            gnu.kawa.functions.MultiplyOp r10 = gnu.kawa.functions.MultiplyOp.$St
            r7.<init>((gnu.mapping.Procedure) r10, (gnu.expr.Expression[]) r8)
            r10 = r7
        L_0x02ae:
            gnu.expr.ApplyExp r7 = new gnu.expr.ApplyExp
            gnu.kawa.functions.MultiplyOp r11 = gnu.kawa.functions.MultiplyOp.$St
            r12 = 2
            gnu.expr.Expression[] r12 = new gnu.expr.Expression[r12]
            gnu.expr.QuoteExp r13 = gnu.expr.QuoteExp.getInstance(r5)
            r15 = 0
            r12[r15] = r13
            r13 = 1
            r12[r13] = r10
            r7.<init>((gnu.mapping.Procedure) r11, (gnu.expr.Expression[]) r12)
            return r7
        L_0x02c3:
            r22 = r7
            r23 = r11
            r24 = r12
            goto L_0x02cc
        L_0x02ca:
            r24 = r12
        L_0x02cc:
            r5 = 2
            if (r4 <= r5) goto L_0x02ed
            r5 = 60
            if (r3 != r5) goto L_0x02ed
            int r5 = r4 + -1
            char r5 = r0.charAt(r5)
            r6 = 62
            if (r5 != r6) goto L_0x02ed
            int r5 = r4 + -1
            r6 = 1
            java.lang.String r0 = r0.substring(r6, r5)
            int r4 = r4 + -2
            r5 = 1
            r28 = r4
            r4 = r0
            r0 = r28
            goto L_0x02f3
        L_0x02ed:
            r5 = 0
            r28 = r4
            r4 = r0
            r0 = r28
        L_0x02f3:
            r6 = 0
            r7 = r6
            r6 = r0
        L_0x02f6:
            r0 = 2
            if (r6 <= r0) goto L_0x0312
            int r8 = r6 + -2
            char r8 = r4.charAt(r8)
            r9 = 91
            if (r8 != r9) goto L_0x0312
            int r8 = r6 + -1
            char r8 = r4.charAt(r8)
            r9 = 93
            if (r8 != r9) goto L_0x0312
            int r6 = r6 + -2
            int r7 = r7 + 1
            goto L_0x02f6
        L_0x0312:
            r0 = r4
            if (r7 == 0) goto L_0x031c
            r8 = 0
            java.lang.String r0 = r4.substring(r8, r6)
            r8 = r0
            goto L_0x031d
        L_0x031c:
            r8 = r0
        L_0x031d:
            gnu.bytecode.Type r0 = getNamedType(r8)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            if (r7 <= 0) goto L_0x0347
            if (r5 == 0) goto L_0x0327
            if (r0 != 0) goto L_0x0347
        L_0x0327:
            java.lang.String r9 = r8.intern()     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            gnu.mapping.Symbol r9 = r2.getSymbol(r9)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r10 = 0
            gnu.expr.Expression r10 = r1.rewrite(r9, r10)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            gnu.expr.Expression r11 = gnu.expr.InlineCalls.inlineCalls(r10, r1)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r10 = r11
            boolean r11 = r10 instanceof gnu.expr.ErrorExp     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            if (r11 != 0) goto L_0x0347
            gnu.expr.Language r11 = r31.getLanguage()     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            gnu.bytecode.Type r11 = r11.getTypeFor((gnu.expr.Expression) r10)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r0 = r11
        L_0x0347:
            if (r0 == 0) goto L_0x0358
        L_0x0349:
            int r7 = r7 + -1
            if (r7 < 0) goto L_0x0353
            gnu.bytecode.ArrayType r9 = gnu.bytecode.ArrayType.make((gnu.bytecode.Type) r0)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r0 = r9
            goto L_0x0349
        L_0x0353:
            gnu.expr.QuoteExp r9 = gnu.expr.QuoteExp.getInstance(r0)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            return r9
        L_0x0358:
            gnu.bytecode.Type r9 = gnu.bytecode.Type.lookupType(r8)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r0 = r9
            boolean r9 = r0 instanceof gnu.bytecode.PrimType     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            if (r9 == 0) goto L_0x0367
            java.lang.Class r9 = r0.getReflectClass()     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            goto L_0x038b
        L_0x0367:
            r9 = 46
            int r9 = r8.indexOf(r9)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            if (r9 >= 0) goto L_0x0387
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r9.<init>()     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            java.lang.String r10 = r1.classPrefix     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            java.lang.String r10 = gnu.expr.Compilation.mangleNameIfNeeded(r8)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            java.lang.String r9 = r9.toString()     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r8 = r9
        L_0x0387:
            java.lang.Class r9 = gnu.bytecode.ClassType.getContextClass(r8)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
        L_0x038b:
            if (r9 == 0) goto L_0x03a8
            if (r7 <= 0) goto L_0x03a3
            gnu.bytecode.Type r10 = gnu.bytecode.Type.make(r9)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r0 = r10
        L_0x0394:
            int r7 = r7 + -1
            if (r7 < 0) goto L_0x039e
            gnu.bytecode.ArrayType r10 = gnu.bytecode.ArrayType.make((gnu.bytecode.Type) r0)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r0 = r10
            goto L_0x0394
        L_0x039e:
            java.lang.Class r10 = r0.getReflectClass()     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            r9 = r10
        L_0x03a3:
            gnu.expr.QuoteExp r10 = gnu.expr.QuoteExp.getInstance(r9)     // Catch:{ ClassNotFoundException -> 0x03ab, all -> 0x03a9 }
            return r10
        L_0x03a8:
            goto L_0x03b7
        L_0x03a9:
            r0 = move-exception
            goto L_0x03b8
        L_0x03ab:
            r0 = move-exception
            java.lang.Package r9 = gnu.bytecode.ArrayClassLoader.getContextPackage(r4)
            if (r9 == 0) goto L_0x03b7
            gnu.expr.QuoteExp r10 = gnu.expr.QuoteExp.getInstance(r9)
            return r10
        L_0x03b7:
        L_0x03b8:
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.Scheme.checkDefaultBinding(gnu.mapping.Symbol, kawa.lang.Translator):gnu.expr.Expression");
    }

    public Expression makeApply(Expression func, Expression[] args) {
        Expression[] exps = new Expression[(args.length + 1)];
        exps[0] = func;
        System.arraycopy(args, 0, exps, 1, args.length);
        return new ApplyExp((Expression) new ReferenceExp(applyFieldDecl), exps);
    }

    public Symbol asSymbol(String ident) {
        return Namespace.EmptyNamespace.getSymbol(ident);
    }

    public ReadTable createReadTable() {
        ReadTable tab = ReadTable.createInitial();
        tab.postfixLookupOperator = ':';
        ReaderDispatch dispatchTable = (ReaderDispatch) tab.lookup(35);
        dispatchTable.set(39, new ReaderQuote(asSymbol("syntax")));
        dispatchTable.set(96, new ReaderQuote(asSymbol("quasisyntax")));
        dispatchTable.set(44, ReaderDispatchMisc.getInstance());
        tab.putReaderCtorFld("path", "gnu.kawa.lispexpr.LangObjType", "pathType");
        tab.putReaderCtorFld("filepath", "gnu.kawa.lispexpr.LangObjType", "filepathType");
        tab.putReaderCtorFld("URI", "gnu.kawa.lispexpr.LangObjType", "URIType");
        tab.putReaderCtor("symbol", (Type) ClassType.make("gnu.mapping.Symbol"));
        tab.putReaderCtor("namespace", (Type) ClassType.make("gnu.mapping.Namespace"));
        tab.putReaderCtorFld("duration", "kawa.lib.numbers", "duration");
        tab.setFinalColonIsKeyword(true);
        return tab;
    }

    public static void registerEnvironment() {
        Language.setDefaults(getInstance());
    }
}
