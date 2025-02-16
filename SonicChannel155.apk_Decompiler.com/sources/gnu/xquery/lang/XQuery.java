package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.functions.ConstantFunction0;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XIntegerType;
import gnu.kawa.xml.XStringType;
import gnu.kawa.xml.XTimeType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xml.XMLPrinter;
import gnu.xquery.util.BooleanValue;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Vector;
import kawa.standard.Scheme;

public class XQuery extends Language {
    public static final String DEFAULT_ELEMENT_PREFIX = null;
    public static final String DEFAULT_FUNCTION_PREFIX = "(functions)";
    public static final String KAWA_FUNCTION_NAMESPACE = "http://kawa.gnu.org/";
    public static final String LOCAL_NAMESPACE = "http://www.w3.org/2005/xquery-local-functions";
    public static final int PARSE_WITH_FOCUS = 65536;
    public static final String QEXO_FUNCTION_NAMESPACE = "http://qexo.gnu.org/";
    public static final String SCHEMA_INSTANCE_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    public static final int VARIADIC_FUNCTION_NAMESPACE = -2;
    public static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
    public static final String XQUERY_FUNCTION_NAMESPACE = "http://www.w3.org/2005/xpath-functions";
    static boolean charIsInt = false;
    public static final Namespace[] defaultFunctionNamespacePath;
    static int envCounter = 0;
    public static Environment extensionsEnvEnv = Environment.getInstance(KAWA_FUNCTION_NAMESPACE);
    public static QuoteExp falseExp = new QuoteExp(Boolean.FALSE, XDataType.booleanType);
    public static final ConstantFunction0 falseFunction = new ConstantFunction0("false", falseExp);
    public static final XQuery instance;
    public static final Namespace kawaFunctionNamespace;
    public static final Namespace qexoFunctionNamespace;
    public static QuoteExp trueExp = new QuoteExp(Boolean.TRUE, XDataType.booleanType);
    public static final ConstantFunction0 trueFunction = new ConstantFunction0("true", trueExp);
    static Object[] typeMap = {PropertyTypeConstants.PROPERTY_TYPE_STRING, XDataType.stringType, "untypedAtomic", XDataType.untypedAtomicType, PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, XDataType.booleanType, PropertyTypeConstants.PROPERTY_TYPE_INTEGER, XIntegerType.integerType, "long", XIntegerType.longType, "int", XIntegerType.intType, "short", XIntegerType.shortType, "byte", XIntegerType.byteType, "unsignedLong", XIntegerType.unsignedLongType, "unsignedInt", XIntegerType.unsignedIntType, "unsignedShort", XIntegerType.unsignedShortType, "unsignedByte", XIntegerType.unsignedByteType, "positiveInteger", XIntegerType.positiveIntegerType, "nonPositiveInteger", XIntegerType.nonPositiveIntegerType, "negativeInteger", XIntegerType.negativeIntegerType, "nonNegativeInteger", XIntegerType.nonNegativeIntegerType, "date", XTimeType.dateType, "dateTime", XTimeType.dateTimeType, "time", XTimeType.timeType, "duration", XTimeType.durationType, "yearMonthDuration", XTimeType.yearMonthDurationType, "dayTimeDuration", XTimeType.dayTimeDurationType, "gYearMonth", XTimeType.gYearMonthType, "gYear", XTimeType.gYearType, "gMonthDay", XTimeType.gMonthDayType, "gDay", XTimeType.gDayType, "gMonth", XTimeType.gMonthType, "decimal", XDataType.decimalType, PropertyTypeConstants.PROPERTY_TYPE_FLOAT, XDataType.floatType, "double", XDataType.doubleType, "anyURI", XDataType.anyURIType, "hexBinary", XDataType.hexBinaryType, "base64Binary", XDataType.base64BinaryType, "NOTATION", XDataType.NotationType, "QName", "gnu.mapping.Symbol", "normalizedString", XStringType.normalizedStringType, "token", XStringType.tokenType, "language", XStringType.languageType, "NMTOKEN", XStringType.NMTOKENType, "Name", XStringType.NameType, "NCName", XStringType.NCNameType, "ID", XStringType.IDType, "IDREF", XStringType.IDREFType, "ENTITY", XStringType.ENTITYType, "anyAtomicType", XDataType.anyAtomicType, "anySimpleType", XDataType.anySimpleType, "untyped", XDataType.untypedType, "anyType", Type.objectType};
    public static final Environment xqEnvironment = Environment.make(XQUERY_FUNCTION_NAMESPACE);
    public static final Namespace xqueryFunctionNamespace;
    Namespace defaultNamespace = xqueryFunctionNamespace;

    static {
        Namespace valueOf = Namespace.valueOf(XQUERY_FUNCTION_NAMESPACE);
        xqueryFunctionNamespace = valueOf;
        Namespace valueOf2 = Namespace.valueOf(KAWA_FUNCTION_NAMESPACE);
        kawaFunctionNamespace = valueOf2;
        Namespace valueOf3 = Namespace.valueOf(QEXO_FUNCTION_NAMESPACE);
        qexoFunctionNamespace = valueOf3;
        defaultFunctionNamespacePath = new Namespace[]{valueOf3, valueOf, Namespace.EmptyNamespace, valueOf2};
        XQuery xQuery = new XQuery();
        instance = xQuery;
        xQuery.initXQuery();
    }

    public boolean hasSeparateFunctionNamespace() {
        return true;
    }

    public static Numeric asNumber(Object arg) {
        if (arg instanceof Char) {
            return IntNum.make(((Char) arg).intValue());
        }
        return (Numeric) arg;
    }

    public static char asChar(Object x) {
        int i;
        if (x instanceof Char) {
            return ((Char) x).charValue();
        }
        if (x instanceof Numeric) {
            i = ((Numeric) x).intValue();
        } else {
            i = -1;
        }
        if (i >= 0 && i <= 65535) {
            return (char) i;
        }
        throw new ClassCastException("not a character value");
    }

    public boolean isTrue(Object value) {
        return BooleanValue.booleanValue(value);
    }

    public Lexer getLexer(InPort inp, SourceMessages messages) {
        return new XQParser(inp, messages, this);
    }

    public Compilation getCompilation(Lexer lexer, SourceMessages messages, NameLookup lexical) {
        return new Compilation(this, messages, lexical);
    }

    public boolean parse(Compilation tr, int options) throws IOException, SyntaxException {
        ModuleExp mexp = tr.mainLambda;
        Compilation.defaultCallConvention = 2;
        tr.mustCompileHere();
        XQParser parser = (XQParser) tr.lexer;
        if (parser.isInteractive()) {
            Expression sexp = parser.parse(tr);
            if (sexp == null) {
                return false;
            }
            mexp.body = sexp;
        } else if ((65536 & options) != 0) {
            LambdaExp lexp = new LambdaExp(3);
            Declaration dotDecl = lexp.addDeclaration((Object) XQParser.DOT_VARNAME);
            dotDecl.setFlag(262144);
            dotDecl.noteValue((Expression) null);
            lexp.addDeclaration(XQParser.POSITION_VARNAME, Type.intType);
            lexp.addDeclaration(XQParser.LAST_VARNAME, Type.intType);
            tr.push((ScopeExp) lexp);
            lexp.body = parser.parse(tr);
            tr.pop(lexp);
            mexp.body = lexp;
        } else {
            Vector exps = new Vector(10);
            Expression sexp2 = mexp.body;
            if (sexp2 instanceof BeginExp) {
                BeginExp bexp = (BeginExp) sexp2;
                int blen = bexp.getExpressionCount();
                Expression[] bexps = bexp.getExpressions();
                for (int i = 0; i < blen; i++) {
                    exps.addElement(bexps[i]);
                }
            } else if (!(sexp2 == null || sexp2 == QuoteExp.voidExp)) {
                exps.addElement(sexp2);
            }
            while (true) {
                Expression sexp3 = parser.parse(tr);
                if (sexp3 == null) {
                    break;
                }
                exps.addElement(sexp3);
            }
            if (parser.parseCount != 0 || parser.isInteractive()) {
                int nexps = exps.size();
                if (nexps == 0) {
                    mexp.body = QuoteExp.voidExp;
                } else if (nexps == 1) {
                    mexp.body = (Expression) exps.elementAt(0);
                } else {
                    Expression[] arr = new Expression[nexps];
                    exps.copyInto(arr);
                    mexp.body = new BeginExp(arr);
                }
            } else {
                parser.error('e', "empty module", "XPST0003");
                return false;
            }
        }
        tr.pop(mexp);
        XQResolveNames resolver = new XQResolveNames(tr);
        resolver.functionNamespacePath = parser.functionNamespacePath;
        resolver.parser = parser;
        resolver.resolveModule(mexp);
        tr.setState(4);
        return true;
    }

    public void resolve(Compilation comp) {
    }

    public static int namespaceForFunctions(int argCount) {
        return (argCount << 2) | 2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0036, code lost:
        r3 = (gnu.mapping.Procedure) r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getNamespaceOf(gnu.expr.Declaration r7) {
        /*
            r6 = this;
            boolean r0 = r7.isProcedureDecl()
            if (r0 == 0) goto L_0x005a
            int r0 = r7.getCode()
            r1 = -2
            if (r0 >= 0) goto L_0x000e
            return r1
        L_0x000e:
            gnu.expr.Expression r0 = r7.getValue()
            boolean r2 = r0 instanceof gnu.expr.LambdaExp
            if (r2 == 0) goto L_0x0027
            r2 = r0
            gnu.expr.LambdaExp r2 = (gnu.expr.LambdaExp) r2
            int r3 = r2.min_args
            int r4 = r2.max_args
            if (r3 != r4) goto L_0x0026
            int r1 = r2.min_args
            int r1 = namespaceForFunctions(r1)
            return r1
        L_0x0026:
            goto L_0x0059
        L_0x0027:
            boolean r2 = r0 instanceof gnu.expr.QuoteExp
            if (r2 == 0) goto L_0x0049
            r2 = r0
            gnu.expr.QuoteExp r2 = (gnu.expr.QuoteExp) r2
            java.lang.Object r2 = r2.getValue()
            boolean r3 = r2 instanceof gnu.mapping.Procedure
            if (r3 == 0) goto L_0x0048
            r3 = r2
            gnu.mapping.Procedure r3 = (gnu.mapping.Procedure) r3
            int r4 = r3.minArgs()
            int r5 = r3.maxArgs()
            if (r4 != r5) goto L_0x0048
            int r1 = namespaceForFunctions(r4)
            return r1
        L_0x0048:
            goto L_0x0059
        L_0x0049:
            boolean r2 = r0 instanceof gnu.expr.ReferenceExp
            if (r2 == 0) goto L_0x0059
            r1 = r0
            gnu.expr.ReferenceExp r1 = (gnu.expr.ReferenceExp) r1
            gnu.expr.Declaration r1 = r1.getBinding()
            int r1 = r6.getNamespaceOf(r1)
            return r1
        L_0x0059:
            return r1
        L_0x005a:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQuery.getNamespaceOf(gnu.expr.Declaration):int");
    }

    public boolean hasNamespace(Declaration decl, int namespace) {
        int dnspace = getNamespaceOf(decl);
        return dnspace == namespace || (dnspace == -2 && (namespace & 2) != 0) || (namespace == -2 && (dnspace & 2) != 0);
    }

    public Symbol getSymbol(String name) {
        return Symbol.make(this.defaultNamespace, name);
    }

    public void define(String name, Object value) {
        this.environ.define(Symbol.make(this.defaultNamespace, name), value instanceof Procedure ? EnvironmentKey.FUNCTION : null, value);
    }

    /* access modifiers changed from: protected */
    public void define_method(String name, String cname, String mname) {
        Symbol sym = Symbol.make(this.defaultNamespace, name);
        Procedure proc = ClassMethods.apply(ClassType.make(cname), mname, 0, this);
        proc.setSymbol(sym);
        this.environ.define(sym, EnvironmentKey.FUNCTION, proc);
    }

    public String getName() {
        return "XQuery";
    }

    /* JADX INFO: finally extract failed */
    public void applyWithFocus(Procedure proc, Object item, int position, int size, Consumer out) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        proc.check3(item, IntNum.make(position), IntNum.make(size), ctx);
        Consumer save = ctx.consumer;
        try {
            ctx.consumer = out;
            ctx.runUntilDone();
            ctx.consumer = save;
        } catch (Throwable th) {
            ctx.consumer = save;
            throw th;
        }
    }

    public Object applyWithFocus(Procedure proc, Object item, int position, int size) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            proc.check3(item, IntNum.make(position), IntNum.make(size), ctx);
            return ctx.getFromContext(oldIndex);
        } catch (Throwable ex) {
            ctx.cleanupFromContext(oldIndex);
            throw ex;
        }
    }

    /* JADX INFO: finally extract failed */
    public void applyWithFocus(Procedure proc, Object values, Consumer out) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        Consumer save = ctx.consumer;
        try {
            ctx.consumer = out;
            applyWithFocus$X(proc, values, ctx);
            ctx.consumer = save;
        } catch (Throwable th) {
            ctx.consumer = save;
            throw th;
        }
    }

    public Object applyWithFocus(Procedure proc, Object values) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            applyWithFocus$X(proc, values, ctx);
            return ctx.getFromContext(oldIndex);
        } catch (Throwable ex) {
            ctx.cleanupFromContext(oldIndex);
            throw ex;
        }
    }

    public void applyWithFocus$X(Procedure proc, Object values, CallContext ctx) throws Throwable {
        if (values instanceof Values) {
            Values v = (Values) values;
            int count = v.size();
            if (count != 0) {
                int ipos = 0;
                IntNum size = IntNum.make(count);
                int i = 1;
                while (true) {
                    proc.check3(v.getPosNext(ipos), IntNum.make(i), size, ctx);
                    ctx.runUntilDone();
                    if (i != count) {
                        ipos = v.nextPos(ipos);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        } else {
            IntNum one = IntNum.one();
            proc.check3(values, one, one, ctx);
            ctx.runUntilDone();
        }
    }

    public Procedure evalToFocusProc(String expr) throws Throwable {
        SourceMessages messages = new SourceMessages();
        Procedure proc = evalToFocusProc(new CharArrayInPort(expr), messages);
        if (!messages.seenErrors()) {
            return proc;
        }
        throw new RuntimeException("invalid syntax in eval form:\n" + messages.toString(20));
    }

    public Procedure evalToFocusProc(Reader in, SourceMessages messages) throws Throwable {
        Compilation comp = parse(in instanceof InPort ? (InPort) in : new InPort(in), messages, 65537);
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            ModuleExp.evalModule(Environment.getCurrent(), ctx, comp, (URL) null, (OutPort) null);
            return (Procedure) ctx.getFromContext(oldIndex);
        } catch (Throwable ex) {
            ctx.cleanupFromContext(oldIndex);
            throw ex;
        }
    }

    public void evalWithFocus(Reader in, SourceMessages messages, Object values, Consumer out) throws Throwable {
        applyWithFocus(evalToFocusProc(in, messages), values, out);
    }

    public Object evalWithFocus(String expr, Object values) throws Throwable {
        return applyWithFocus(evalToFocusProc(expr), values);
    }

    public Object evalWithFocus(String expr, Object item, int position, int size) throws Throwable {
        return applyWithFocus(evalToFocusProc(expr), item, position, size);
    }

    public void evalWithFocus(Reader in, SourceMessages messages, Object item, int position, int size, Consumer out) throws Throwable {
        applyWithFocus(evalToFocusProc(in, messages), item, position, size, out);
    }

    public void eval_with_focus$X(String expr, Object values, CallContext ctx) throws Throwable {
        applyWithFocus$X(evalToFocusProc(expr), values, ctx);
    }

    public void eval_with_focus$X(String expr, Object item, int position, int size, CallContext ctx) throws Throwable {
        evalToFocusProc(expr).check3(item, IntNum.make(position), IntNum.make(size), ctx);
    }

    public XQuery() {
        this.environ = xqEnvironment;
    }

    private void initXQuery() {
        ModuleBody.setMainPrintValues(true);
        defProcStFld("unescaped-data", "gnu.kawa.xml.MakeUnescapedData", "unescapedData");
        defProcStFld("item-at", "gnu.xquery.util.ItemAt", "itemAt");
        defProcStFld("count", "gnu.kawa.functions.CountValues", "countValues");
        define_method("sum", "gnu.xquery.util.Reduce", "sum");
        defProcStFld("avg", "gnu.xquery.util.Average", "avg");
        defProcStFld("sublist", "gnu.xquery.util.SubList", "subList");
        defProcStFld("subsequence", "gnu.xquery.util.SubList", "subList");
        define_method("empty", "gnu.xquery.util.SequenceUtils", "isEmptySequence");
        define_method("exists", "gnu.xquery.util.SequenceUtils", "exists");
        define_method("insert-before", "gnu.xquery.util.SequenceUtils", "insertBefore$X");
        define_method("remove", "gnu.xquery.util.SequenceUtils", "remove$X");
        define_method("reverse", "gnu.xquery.util.SequenceUtils", "reverse$X");
        defProcStFld("false", "gnu.xquery.lang.XQuery", "falseFunction");
        defProcStFld("true", "gnu.xquery.lang.XQuery", "trueFunction");
        defProcStFld(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, "gnu.xquery.util.BooleanValue", "booleanValue");
        define_method("trace", "gnu.xquery.util.Debug", "trace");
        define_method("error", "gnu.xquery.util.XQException", "error");
        defProcStFld("write-to", "gnu.kawa.xml.WriteTo", "writeTo");
        defProcStFld("write-to-if-changed", "gnu.kawa.xml.WriteTo", "writeToIfChanged");
        defProcStFld("iterator-items", "gnu.kawa.xml.IteratorItems", "iteratorItems");
        defProcStFld("list-items", "gnu.kawa.xml.ListItems", "listItems");
        define_method("node-name", "gnu.xquery.util.NodeUtils", "nodeName");
        define_method("nilled", "gnu.xquery.util.NodeUtils", "nilled");
        define_method("data", "gnu.xquery.util.NodeUtils", "data$X");
        define_method("lower-case", "gnu.xquery.util.StringUtils", "lowerCase");
        define_method("upper-case", "gnu.xquery.util.StringUtils", "upperCase");
        define_method("substring", "gnu.xquery.util.StringUtils", "substring");
        define_method("string-length", "gnu.xquery.util.StringUtils", "stringLength");
        define_method("substring-before", "gnu.xquery.util.StringUtils", "substringBefore");
        define_method("substring-after", "gnu.xquery.util.StringUtils", "substringAfter");
        define_method("translate", "gnu.xquery.util.StringUtils", "translate");
        define_method("encode-for-uri", "gnu.xquery.util.StringUtils", "encodeForUri");
        define_method("iri-to-uri", "gnu.xquery.util.StringUtils", "iriToUri");
        define_method("escape-html-uri", "gnu.xquery.util.StringUtils", "escapeHtmlUri");
        define_method("contains", "gnu.xquery.util.StringUtils", "contains");
        define_method("starts-with", "gnu.xquery.util.StringUtils", "startsWith");
        define_method("ends-with", "gnu.xquery.util.StringUtils", "endsWith");
        define_method("codepoint-equal", "gnu.xquery.util.StringUtils", "codepointEqual");
        define_method("normalize-unicode", "gnu.xquery.util.StringUtils", "normalizeUnicode");
        define_method("string-join", "gnu.xquery.util.StringUtils", "stringJoin");
        define_method("concat", "gnu.xquery.util.StringUtils", "concat$V");
        define_method("matches", "gnu.xquery.util.StringUtils", "matches");
        define_method("replace", "gnu.xquery.util.StringUtils", "replace");
        define_method("tokenize", "gnu.xquery.util.StringUtils", "tokenize$X");
        define_method("string-to-codepoints", "gnu.xquery.util.StringUtils", "stringToCodepoints$X");
        define_method("codepoints-to-string", "gnu.xquery.util.StringUtils", "codepointsToString");
        define_method("abs", "gnu.xquery.util.NumberValue", "abs");
        define_method("floor", "gnu.xquery.util.NumberValue", "floor");
        define_method("ceiling", "gnu.xquery.util.NumberValue", "ceiling");
        define_method("round", "gnu.xquery.util.NumberValue", "round");
        define_method("round-half-to-even", "gnu.xquery.util.NumberValue", "roundHalfToEven");
        define_method("QName", "gnu.xquery.util.QNameUtils", "makeQName");
        define_method("resolve-QName", "gnu.xquery.util.QNameUtils", "resolveQNameUsingElement");
        define_method("prefix-from-QName", "gnu.xquery.util.QNameUtils", "prefixFromQName");
        define_method("local-name-from-QName", "gnu.xquery.util.QNameUtils", "localNameFromQName");
        define_method("namespace-uri-from-QName", "gnu.xquery.util.QNameUtils", "namespaceURIFromQName");
        define_method("namespace-uri-for-prefix", "gnu.xquery.util.QNameUtils", "namespaceURIForPrefix");
        define_method("in-scope-prefixes", "gnu.xquery.util.NodeUtils", "inScopePrefixes$X");
        define_method("document-uri", "gnu.xquery.util.NodeUtils", "documentUri");
        define_method("years-from-duration", "gnu.xquery.util.TimeUtils", "yearsFromDuration");
        define_method("months-from-duration", "gnu.xquery.util.TimeUtils", "monthsFromDuration");
        define_method("days-from-duration", "gnu.xquery.util.TimeUtils", "daysFromDuration");
        define_method("hours-from-duration", "gnu.xquery.util.TimeUtils", "hoursFromDuration");
        define_method("minutes-from-duration", "gnu.xquery.util.TimeUtils", "minutesFromDuration");
        define_method("seconds-from-duration", "gnu.xquery.util.TimeUtils", "secondsFromDuration");
        define_method("year-from-dateTime", "gnu.xquery.util.TimeUtils", "yearFromDateTime");
        define_method("month-from-dateTime", "gnu.xquery.util.TimeUtils", "monthFromDateTime");
        define_method("day-from-dateTime", "gnu.xquery.util.TimeUtils", "dayFromDateTime");
        define_method("hours-from-dateTime", "gnu.xquery.util.TimeUtils", "hoursFromDateTime");
        define_method("minutes-from-dateTime", "gnu.xquery.util.TimeUtils", "minutesFromDateTime");
        define_method("seconds-from-dateTime", "gnu.xquery.util.TimeUtils", "secondsFromDateTime");
        define_method("timezone-from-dateTime", "gnu.xquery.util.TimeUtils", "timezoneFromDateTime");
        define_method("year-from-date", "gnu.xquery.util.TimeUtils", "yearFromDate");
        define_method("month-from-date", "gnu.xquery.util.TimeUtils", "monthFromDate");
        define_method("day-from-date", "gnu.xquery.util.TimeUtils", "dayFromDate");
        define_method("timezone-from-date", "gnu.xquery.util.TimeUtils", "timezoneFromDate");
        define_method("hours-from-time", "gnu.xquery.util.TimeUtils", "hoursFromTime");
        define_method("minutes-from-time", "gnu.xquery.util.TimeUtils", "minutesFromTime");
        define_method("seconds-from-time", "gnu.xquery.util.TimeUtils", "secondsFromTime");
        define_method("timezone-from-time", "gnu.xquery.util.TimeUtils", "timezoneFromTime");
        define_method("adjust-dateTime-to-timezone", "gnu.xquery.util.TimeUtils", "adjustDateTimeToTimezone");
        define_method("adjust-date-to-timezone", "gnu.xquery.util.TimeUtils", "adjustDateToTimezone");
        define_method("adjust-time-to-timezone", "gnu.xquery.util.TimeUtils", "adjustTimeToTimezone");
        define_method("dateTime", "gnu.xquery.util.TimeUtils", "dateTime");
        define_method("current-dateTime", "gnu.xquery.util.TimeUtils", "currentDateTime");
        define_method("current-date", "gnu.xquery.util.TimeUtils", "currentDate");
        define_method("current-time", "gnu.xquery.util.TimeUtils", "currentTime");
        define_method("implicit-timezone", "gnu.xquery.util.TimeUtils", "implicitTimezone");
        define_method("zero-or-one", "gnu.xquery.util.SequenceUtils", "zeroOrOne");
        define_method("one-or-more", "gnu.xquery.util.SequenceUtils", "oneOrMore");
        define_method("exactly-one", "gnu.xquery.util.SequenceUtils", "exactlyOne");
        defProcStFld("distinct-nodes", "gnu.kawa.xml.SortNodes", "sortNodes");
        defProcStFld("children", "gnu.kawa.xml.Children", "children");
        define_method("not", "gnu.xquery.util.BooleanValue", "not");
        this.defaultNamespace = qexoFunctionNamespace;
        defProcStFld("response-header", "gnu.kawa.servlet.HTTP");
        defProcStFld("response-content-type", "gnu.kawa.servlet.HTTP");
        defProcStFld("response-status", "gnu.kawa.servlet.HTTP");
        defProcStFld("error-response", "gnu.kawa.servlet.HTTP");
        defProcStFld("current-servlet", "gnu.kawa.servlet.HTTP");
        defProcStFld("current-servlet-context", "gnu.kawa.servlet.HTTP");
        defProcStFld("current-servlet-config", "gnu.kawa.servlet.HTTP");
        defProcStFld("servlet-context-realpath", "gnu.kawa.servlet.HTTP");
        defProcStFld("get-response", "gnu.kawa.servlet.HTTP");
        defProcStFld("get-request", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-method", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-uri", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-url", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-path-info", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-path-translated", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-servlet-path", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-query-string", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-parameter", "gnu.kawa.servlet.HTTP");
        defProcStFld("request-parameters", "gnu.kawa.servlet.HTTP");
        this.defaultNamespace = xqueryFunctionNamespace;
    }

    public static XQuery getInstance() {
        return instance;
    }

    public static void registerEnvironment() {
        ApplicationMainSupport.processCommandLinePropertyAssignments = true;
        Language.setDefaults(instance);
    }

    public Consumer getOutputConsumer(Writer out) {
        return new XMLPrinter(out, false);
    }

    public static Type getStandardType(String name) {
        int i = typeMap.length;
        do {
            i -= 2;
            if (i < 0) {
                return null;
            }
        } while (!typeMap[i].equals(name));
        Object t = typeMap[i + 1];
        if (t instanceof String) {
            return Scheme.string2Type((String) t);
        }
        return (Type) t;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0021  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.bytecode.Type getTypeFor(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.String r0 = "xs:"
            boolean r0 = r4.startsWith(r0)
            if (r0 == 0) goto L_0x000e
            r0 = 3
        L_0x0009:
            java.lang.String r0 = r4.substring(r0)
            goto L_0x0019
        L_0x000e:
            java.lang.String r0 = "xdt:"
            boolean r0 = r4.startsWith(r0)
            if (r0 == 0) goto L_0x0018
            r0 = 4
            goto L_0x0009
        L_0x0018:
            r0 = r4
        L_0x0019:
            gnu.bytecode.Type r1 = getStandardType(r0)
            if (r1 == 0) goto L_0x0021
            r2 = r1
            goto L_0x0025
        L_0x0021:
            gnu.bytecode.Type r2 = kawa.standard.Scheme.string2Type(r4)
        L_0x0025:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQuery.getTypeFor(java.lang.String):gnu.bytecode.Type");
    }

    public String formatType(Type type) {
        String tname = type.getName();
        if ("gnu.math.IntNum".equals(tname)) {
            return "xs:integer";
        }
        if ("java.lang.String".equals(tname) || "java.lang.CharSequence".equals(tname)) {
            return "xs:string";
        }
        return type.toString();
    }

    public Type getTypeFor(Class clas) {
        if (clas.isPrimitive()) {
            String name = clas.getName();
            if (name.equals(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)) {
                return XDataType.booleanType;
            }
            return Scheme.getNamedType(name);
        }
        if (!clas.isArray()) {
            String name2 = clas.getName();
            if (name2.equals("java.lang.String")) {
                return XDataType.stringStringType;
            }
            if (name2.equals("gnu.kawa.xml.UntypedAtomic")) {
                return XDataType.untypedAtomicType;
            }
            if (name2.equals("java.lang.Boolean")) {
                return XDataType.booleanType;
            }
            if (name2.equals("java.lang.Float")) {
                return XDataType.floatType;
            }
            if (name2.equals("java.lang.Double")) {
                return XDataType.doubleType;
            }
            if (name2.equals("java.math.BigDecimal")) {
                return XDataType.decimalType;
            }
            if (name2.equals("gnu.math.Duration")) {
                return XDataType.durationType;
            }
            if (name2.equals("gnu.text.Path")) {
                return XDataType.anyURIType;
            }
        }
        return Type.make(clas);
    }

    public Procedure getPrompter() {
        return new Prompter();
    }

    static void mangle(String name, int start, int length, StringBuffer sbuf, char mode) {
        boolean wordStart;
        char prev = 'P';
        int outStart = sbuf.length();
        int i = 0;
        while (i < length) {
            char ch = name.charAt(start + i);
            i++;
            boolean z = false;
            if (Character.isUpperCase(ch)) {
                if (prev != 'U' || (i < length && Character.isLowerCase(name.charAt(start + i)))) {
                    z = true;
                }
                wordStart = z;
                prev = 'U';
            } else if (Character.isLowerCase(ch)) {
                if (!(prev == 'L' && prev == 'U')) {
                    z = true;
                }
                wordStart = z;
                prev = 'L';
            } else if (Character.isLetter(ch)) {
                if (prev != 'O') {
                    z = true;
                }
                wordStart = z;
                prev = 'O';
            } else if (Character.isDigit(ch)) {
                if (prev != 'D') {
                    z = true;
                }
                wordStart = z;
                prev = 'D';
            } else if (Character.isJavaIdentifierPart(ch)) {
                if (!(prev == 'D' || prev == 'M')) {
                    z = true;
                }
                wordStart = z;
                prev = Access.METHOD_CONTEXT;
            } else {
                prev = 'P';
            }
            if (wordStart || mode == '_') {
                if (wordStart && mode == '_' && sbuf.length() > outStart) {
                    sbuf.append('_');
                }
                ch = Character.toUpperCase(ch);
            }
            sbuf.append(ch);
        }
    }

    public static String mangle(String name) {
        StringBuffer sbuf = new StringBuffer();
        mangle(name, 0, name.length(), sbuf, 'U');
        return sbuf.toString();
    }

    public static String makeClassName(String source) {
        String source2 = source.replace(File.separatorChar, '/');
        int sl = source2.lastIndexOf(47);
        if (sl >= 0) {
            source2 = source2.substring(sl + 1);
        }
        int dot = source2.lastIndexOf(46);
        if (dot >= 0) {
            source2 = source2.substring(0, dot);
        }
        return Compilation.mangleNameIfNeeded(source2);
    }

    public static Object getExternal(Symbol name, Object type) {
        Environment env = Environment.getCurrent();
        Object value = env.get(name, (Object) null, (Object) null);
        if (value == null) {
            value = env.get(Symbol.makeWithUnknownNamespace(name.getLocalName(), name.getPrefix()), (Object) null, (Object) null);
        }
        if (value == null) {
            throw new RuntimeException("unbound external " + name);
        } else if (type == null) {
            return value;
        } else {
            if (type instanceof XDataType) {
                return ((XDataType) type).cast(value);
            }
            if (type instanceof ClassType) {
                String cname = ((ClassType) type).getName();
                if ("gnu.math.IntNum".equals(cname)) {
                    return IntNum.valueOf(value.toString());
                }
                if ("gnu.math.RealNum".equals(cname)) {
                    return DFloNum.make(Double.parseDouble(value.toString()));
                }
            }
            try {
                return ((Type) type).coerceFromObject(value);
            } catch (ClassCastException e) {
                throw new WrongType(name.toString(), -2, value, type.toString());
            }
        }
    }
}
