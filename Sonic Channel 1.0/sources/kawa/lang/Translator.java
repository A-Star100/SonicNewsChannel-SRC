package kawa.lang;

import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.NamespaceBinding;
import java.util.Stack;
import java.util.Vector;
import kawa.standard.begin;
import kawa.standard.require;

public class Translator extends Compilation {
    private static Expression errorExp = new ErrorExp("unknown syntax error");
    public static final Declaration getNamedPartDecl;
    public LambdaExp curMethodLambda;
    public Macro currentMacroDefinition;
    Syntax currentSyntax;
    private Environment env = Environment.getCurrent();
    public int firstForm;
    public Stack formStack = new Stack();
    Declaration macroContext;
    public Declaration matchArray;
    Vector notedAccess;
    public PatternScope patternScope;
    public Object pendingForm;
    PairWithPosition positionPair;
    Stack renamedAliasStack;
    public Declaration templateScopeDecl;
    public NamespaceBinding xmlElementNamespaces = NamespaceBinding.predefinedXML;

    static {
        Declaration declarationFromStatic = Declaration.getDeclarationFromStatic("gnu.kawa.functions.GetNamedPart", "getNamedPart");
        getNamedPartDecl = declarationFromStatic;
        LispLanguage.getNamedPartLocation.setDeclaration(declarationFromStatic);
    }

    public Translator(Language language, SourceMessages messages, NameLookup lexical) {
        super(language, messages, lexical);
    }

    public final Environment getGlobalEnvironment() {
        return this.env;
    }

    public Expression parse(Object input) {
        return rewrite(input);
    }

    /* JADX INFO: finally extract failed */
    public final Expression rewrite_car(Pair pair, SyntaxForm syntax) {
        if (syntax == null || syntax.getScope() == this.current_scope || (pair.getCar() instanceof SyntaxForm)) {
            return rewrite_car(pair, false);
        }
        ScopeExp save_scope = this.current_scope;
        try {
            setCurrentScope(syntax.getScope());
            Expression rewrite_car = rewrite_car(pair, false);
            setCurrentScope(save_scope);
            return rewrite_car;
        } catch (Throwable th) {
            setCurrentScope(save_scope);
            throw th;
        }
    }

    public final Expression rewrite_car(Pair pair, boolean function) {
        Object car = pair.getCar();
        if (pair instanceof PairWithPosition) {
            return rewrite_with_position(car, function, (PairWithPosition) pair);
        }
        return rewrite(car, function);
    }

    public Syntax getCurrentSyntax() {
        return this.currentSyntax;
    }

    /* access modifiers changed from: package-private */
    public Expression apply_rewrite(Syntax syntax, Pair form) {
        Expression expression = errorExp;
        Syntax saveSyntax = this.currentSyntax;
        this.currentSyntax = syntax;
        try {
            return syntax.rewriteForm(form, this);
        } finally {
            this.currentSyntax = saveSyntax;
        }
    }

    static ReferenceExp getOriginalRef(Declaration decl) {
        if (decl == null || !decl.isAlias() || decl.isIndirectBinding()) {
            return null;
        }
        Expression value = decl.getValue();
        if (value instanceof ReferenceExp) {
            return (ReferenceExp) value;
        }
        return null;
    }

    public final boolean selfEvaluatingSymbol(Object obj) {
        return ((LispLanguage) getLanguage()).selfEvaluatingSymbol(obj);
    }

    public final boolean matches(Object form, String literal) {
        return matches(form, (SyntaxForm) null, literal);
    }

    public boolean matches(Object form, SyntaxForm syntax, String literal) {
        ReferenceExp rexp;
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form) && (rexp = getOriginalRef(this.lexical.lookup(form, -1))) != null) {
            form = rexp.getSymbol();
        }
        return (form instanceof SimpleSymbol) && ((Symbol) form).getLocalPart() == literal;
    }

    public boolean matches(Object form, SyntaxForm syntax, Symbol literal) {
        ReferenceExp rexp;
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form) && (rexp = getOriginalRef(this.lexical.lookup(form, -1))) != null) {
            form = rexp.getSymbol();
        }
        return form == literal;
    }

    public Object matchQuoted(Pair pair) {
        if (!matches(pair.getCar(), LispLanguage.quote_sym) || !(pair.getCdr() instanceof Pair)) {
            return null;
        }
        Pair pair2 = (Pair) pair.getCdr();
        Pair pair3 = pair2;
        if (pair2.getCdr() == LList.Empty) {
            return pair3.getCar();
        }
        return null;
    }

    public Declaration lookup(Object name, int namespace) {
        Declaration decl = this.lexical.lookup(name, namespace);
        if (decl == null || !getLanguage().hasNamespace(decl, namespace)) {
            return currentModule().lookup(name, getLanguage(), namespace);
        }
        return decl;
    }

    public Declaration lookupGlobal(Object name) {
        return lookupGlobal(name, -1);
    }

    public Declaration lookupGlobal(Object name, int namespace) {
        ModuleExp module = currentModule();
        Declaration decl = module.lookup(name, getLanguage(), namespace);
        if (decl != null) {
            return decl;
        }
        Declaration decl2 = module.getNoDefine(name);
        decl2.setIndirectBinding(true);
        return decl2;
    }

    /* access modifiers changed from: package-private */
    public Syntax check_if_Syntax(Declaration decl) {
        Declaration d = Declaration.followAliases(decl);
        Object obj = null;
        Expression dval = d.getValue();
        if (dval != null && d.getFlag(32768)) {
            try {
                if (decl.getValue() instanceof ReferenceExp) {
                    Declaration context = ((ReferenceExp) decl.getValue()).contextDecl();
                    if (context != null) {
                        this.macroContext = context;
                    } else if (this.current_scope instanceof TemplateScope) {
                        this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                    }
                } else if (this.current_scope instanceof TemplateScope) {
                    this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                }
                obj = dval.eval(this.env);
            } catch (Throwable ex) {
                ex.printStackTrace();
                error('e', "unable to evaluate macro for " + decl.getSymbol());
            }
        } else if (decl.getFlag(32768) && !decl.needsContext()) {
            obj = StaticFieldLocation.make(decl).get((Object) null);
        }
        if (obj instanceof Syntax) {
            return (Syntax) obj;
        }
        return null;
    }

    public Expression rewrite_pair(Pair p, boolean function) {
        Symbol symbol;
        Pair pair = p;
        char c = 1;
        Expression func = rewrite_car(pair, true);
        if (func instanceof QuoteExp) {
            Object proc = func.valueIfConstant();
            if (proc instanceof Syntax) {
                return apply_rewrite((Syntax) proc, pair);
            }
        }
        if (func instanceof ReferenceExp) {
            ReferenceExp ref = (ReferenceExp) func;
            Declaration decl = ref.getBinding();
            if (decl == null) {
                Object sym = ref.getSymbol();
                if (!(sym instanceof Symbol) || selfEvaluatingSymbol(sym)) {
                    symbol = this.env.getSymbol(sym.toString());
                } else {
                    symbol = (Symbol) sym;
                    String name = symbol.getName();
                }
                Object proc2 = this.env.get(symbol, getLanguage().hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, (Object) null);
                if (proc2 instanceof Syntax) {
                    return apply_rewrite((Syntax) proc2, pair);
                }
                if (proc2 instanceof AutoloadProcedure) {
                    try {
                        proc2 = ((AutoloadProcedure) proc2).getLoaded();
                    } catch (RuntimeException e) {
                        proc2 = null;
                    }
                }
                Object obj = proc2;
            } else {
                Declaration saveContext = this.macroContext;
                Syntax syntax = check_if_Syntax(decl);
                if (syntax != null) {
                    Expression e2 = apply_rewrite(syntax, pair);
                    this.macroContext = saveContext;
                    return e2;
                }
            }
            ref.setProcedureName(true);
            if (getLanguage().hasSeparateFunctionNamespace()) {
                func.setFlag(8);
            }
        }
        Object cdr = p.getCdr();
        int cdr_length = listLength(cdr);
        if (cdr_length == -1) {
            return syntaxError("circular list is not allowed after " + p.getCar());
        }
        if (cdr_length < 0) {
            return syntaxError("dotted list [" + cdr + "] is not allowed after " + p.getCar());
        }
        boolean mapKeywordsToAttributes = false;
        Stack vec = new Stack();
        ScopeExp save_scope = this.current_scope;
        int i = 0;
        while (i < cdr_length) {
            if (cdr instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) cdr;
                cdr = sf.getDatum();
                setCurrentScope(sf.getScope());
            }
            Pair cdr_pair = (Pair) cdr;
            Expression arg = rewrite_car(cdr_pair, false);
            i++;
            if (mapKeywordsToAttributes) {
                if ((i & 1) == 0) {
                    Expression[] aargs = new Expression[2];
                    aargs[0] = (Expression) vec.pop();
                    aargs[c] = arg;
                    arg = new ApplyExp((Procedure) MakeAttribute.makeAttribute, aargs);
                } else {
                    if (arg instanceof QuoteExp) {
                        Object value = ((QuoteExp) arg).getValue();
                        Object value2 = value;
                        if ((value instanceof Keyword) && i < cdr_length) {
                            arg = new QuoteExp(((Keyword) value2).asSymbol());
                        }
                    }
                    mapKeywordsToAttributes = false;
                }
            }
            vec.addElement(arg);
            cdr = cdr_pair.getCdr();
            c = 1;
        }
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        if (save_scope != this.current_scope) {
            setCurrentScope(save_scope);
        }
        if (!(func instanceof ReferenceExp) || ((ReferenceExp) func).getBinding() != getNamedPartDecl) {
            boolean z = function;
            return ((LispLanguage) getLanguage()).makeApply(func, args);
        }
        Expression part1 = args[0];
        Expression part2 = args[1];
        Symbol sym2 = namespaceResolve(part1, part2);
        if (sym2 != null) {
            return rewrite(sym2, function);
        }
        boolean z2 = function;
        return CompileNamedPart.makeExp(part1, part2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: gnu.mapping.Namespace} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: gnu.mapping.Namespace} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: gnu.mapping.Namespace} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: gnu.mapping.Namespace} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.mapping.Namespace namespaceResolvePrefix(gnu.expr.Expression r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof gnu.expr.ReferenceExp
            r1 = 0
            if (r0 == 0) goto L_0x0059
            r0 = r8
            gnu.expr.ReferenceExp r0 = (gnu.expr.ReferenceExp) r0
            gnu.expr.Declaration r2 = r0.getBinding()
            if (r2 == 0) goto L_0x0025
            r3 = 65536(0x10000, double:3.2379E-319)
            boolean r3 = r2.getFlag(r3)
            if (r3 == 0) goto L_0x0018
            goto L_0x0025
        L_0x0018:
            boolean r3 = r2.isNamespaceDecl()
            if (r3 == 0) goto L_0x0023
            java.lang.Object r3 = r2.getConstantValue()
            goto L_0x0042
        L_0x0023:
            r3 = 0
            goto L_0x0042
        L_0x0025:
            java.lang.Object r3 = r0.getSymbol()
            boolean r4 = r3 instanceof gnu.mapping.Symbol
            if (r4 == 0) goto L_0x0031
            r4 = r3
            gnu.mapping.Symbol r4 = (gnu.mapping.Symbol) r4
            goto L_0x003b
        L_0x0031:
            gnu.mapping.Environment r4 = r7.env
            java.lang.String r5 = r3.toString()
            gnu.mapping.Symbol r4 = r4.getSymbol(r5)
        L_0x003b:
            gnu.mapping.Environment r5 = r7.env
            java.lang.Object r3 = r5.get((gnu.mapping.EnvironmentKey) r4, (java.lang.Object) r1)
        L_0x0042:
            boolean r4 = r3 instanceof gnu.mapping.Namespace
            if (r4 == 0) goto L_0x0059
            r4 = r3
            gnu.mapping.Namespace r4 = (gnu.mapping.Namespace) r4
            java.lang.String r5 = r4.getName()
            if (r5 == 0) goto L_0x0058
            java.lang.String r6 = "class:"
            boolean r6 = r5.startsWith(r6)
            if (r6 == 0) goto L_0x0058
            return r1
        L_0x0058:
            return r4
        L_0x0059:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.namespaceResolvePrefix(gnu.expr.Expression):gnu.mapping.Namespace");
    }

    public Symbol namespaceResolve(Namespace ns, Expression member) {
        if (ns == null || !(member instanceof QuoteExp)) {
            return null;
        }
        return ns.getSymbol(((QuoteExp) member).getValue().toString().intern());
    }

    public Symbol namespaceResolve(Expression context, Expression member) {
        return namespaceResolve(namespaceResolvePrefix(context), member);
    }

    public static Object stripSyntax(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        return obj;
    }

    public static Object safeCar(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (!(obj instanceof Pair)) {
            return null;
        }
        return stripSyntax(((Pair) obj).getCar());
    }

    public static Object safeCdr(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (!(obj instanceof Pair)) {
            return null;
        }
        return stripSyntax(((Pair) obj).getCdr());
    }

    public static int listLength(Object obj) {
        int n = 0;
        Object slow = obj;
        Object fast = obj;
        while (true) {
            if (fast instanceof SyntaxForm) {
                fast = ((SyntaxForm) fast).getDatum();
            } else {
                while (slow instanceof SyntaxForm) {
                    slow = ((SyntaxForm) slow).getDatum();
                }
                if (fast == LList.Empty) {
                    return n;
                }
                if (!(fast instanceof Pair)) {
                    return -1 - n;
                }
                int n2 = n + 1;
                Object next = ((Pair) fast).getCdr();
                while (next instanceof SyntaxForm) {
                    next = ((SyntaxForm) next).getDatum();
                }
                if (next == LList.Empty) {
                    return n2;
                }
                if (!(next instanceof Pair)) {
                    return -1 - n2;
                }
                slow = ((Pair) slow).getCdr();
                fast = ((Pair) next).getCdr();
                n = n2 + 1;
                if (fast == slow) {
                    return Integer.MIN_VALUE;
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void rewriteInBody(Object exp) {
        if (exp instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) exp;
            ScopeExp save_scope = this.current_scope;
            try {
                setCurrentScope(sf.getScope());
                rewriteInBody(sf.getDatum());
                setCurrentScope(save_scope);
            } catch (Throwable th) {
                setCurrentScope(save_scope);
                throw th;
            }
        } else if (exp instanceof Values) {
            Object[] vals = ((Values) exp).getValues();
            for (Object rewriteInBody : vals) {
                rewriteInBody(rewriteInBody);
            }
        } else {
            this.formStack.add(rewrite(exp, false));
        }
    }

    public Expression rewrite(Object exp) {
        return rewrite(exp, false);
    }

    public Object namespaceResolve(Object name) {
        if (!(name instanceof SimpleSymbol) && (name instanceof Pair)) {
            Pair pair = (Pair) name;
            Pair p = pair;
            if (safeCar(pair) == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                Pair pair2 = (Pair) p.getCdr();
                Pair p2 = pair2;
                if (pair2.getCdr() instanceof Pair) {
                    Expression part1 = rewrite(p2.getCar());
                    Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                    Symbol sym = namespaceResolve(part1, part2);
                    if (sym != null) {
                        return sym;
                    }
                    String combinedName = CompileNamedPart.combineName(part1, part2);
                    if (combinedName != null) {
                        return Namespace.EmptyNamespace.getSymbol(combinedName);
                    }
                }
            }
        }
        return name;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01ea, code lost:
        if ((r15 instanceof gnu.bytecode.ArrayClassLoader) == false) goto L_0x022e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r17, boolean r18) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            boolean r0 = r2 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x0028
            r4 = r2
            kawa.lang.SyntaxForm r4 = (kawa.lang.SyntaxForm) r4
            gnu.expr.ScopeExp r5 = r1.current_scope
            kawa.lang.TemplateScope r0 = r4.getScope()     // Catch:{ all -> 0x0023 }
            r1.setCurrentScope(r0)     // Catch:{ all -> 0x0023 }
            java.lang.Object r0 = r4.getDatum()     // Catch:{ all -> 0x0023 }
            gnu.expr.Expression r0 = r1.rewrite(r0, r3)     // Catch:{ all -> 0x0023 }
            r1.setCurrentScope(r5)
            return r0
        L_0x0023:
            r0 = move-exception
            r1.setCurrentScope(r5)
            throw r0
        L_0x0028:
            boolean r0 = r2 instanceof gnu.lists.PairWithPosition
            if (r0 == 0) goto L_0x0034
            r0 = r2
            gnu.lists.PairWithPosition r0 = (gnu.lists.PairWithPosition) r0
            gnu.expr.Expression r0 = r1.rewrite_with_position(r2, r3, r0)
            return r0
        L_0x0034:
            boolean r0 = r2 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x0040
            r0 = r2
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            gnu.expr.Expression r0 = r1.rewrite_pair(r0, r3)
            return r0
        L_0x0040:
            boolean r0 = r2 instanceof gnu.mapping.Symbol
            if (r0 == 0) goto L_0x0280
            boolean r0 = r16.selfEvaluatingSymbol(r17)
            if (r0 != 0) goto L_0x0280
            gnu.expr.NameLookup r0 = r1.lexical
            gnu.expr.Declaration r0 = r0.lookup((java.lang.Object) r2, (boolean) r3)
            r4 = 0
            gnu.expr.ScopeExp r5 = r1.current_scope
            if (r0 != 0) goto L_0x0057
            r6 = -1
            goto L_0x005d
        L_0x0057:
            gnu.expr.ScopeExp r6 = r0.context
            int r6 = gnu.expr.ScopeExp.nesting(r6)
        L_0x005d:
            boolean r7 = r2 instanceof gnu.mapping.Symbol
            if (r7 == 0) goto L_0x006f
            r7 = r2
            gnu.mapping.Symbol r7 = (gnu.mapping.Symbol) r7
            boolean r7 = r7.hasEmptyNamespace()
            if (r7 == 0) goto L_0x006f
            java.lang.String r7 = r17.toString()
            goto L_0x0071
        L_0x006f:
            r7 = 0
            r5 = 0
        L_0x0071:
            if (r5 == 0) goto L_0x00e8
            boolean r8 = r5 instanceof gnu.expr.LambdaExp
            if (r8 == 0) goto L_0x00e5
            gnu.expr.ScopeExp r8 = r5.outer
            boolean r8 = r8 instanceof gnu.expr.ClassExp
            if (r8 == 0) goto L_0x00e5
            r8 = r5
            gnu.expr.LambdaExp r8 = (gnu.expr.LambdaExp) r8
            boolean r8 = r8.isClassMethod()
            if (r8 == 0) goto L_0x00e5
            gnu.expr.ScopeExp r8 = r5.outer
            int r8 = gnu.expr.ScopeExp.nesting(r8)
            if (r6 < r8) goto L_0x008f
            goto L_0x00e8
        L_0x008f:
            r8 = r5
            gnu.expr.LambdaExp r8 = (gnu.expr.LambdaExp) r8
            gnu.expr.ScopeExp r9 = r5.outer
            gnu.expr.ClassExp r9 = (gnu.expr.ClassExp) r9
            gnu.bytecode.ClassType r10 = r9.getClassType()
            gnu.bytecode.Member r11 = gnu.kawa.reflect.SlotGet.lookupMember(r10, r7, r10)
            gnu.expr.LambdaExp r12 = r9.clinitMethod
            if (r8 == r12) goto L_0x00b1
            gnu.expr.LambdaExp r12 = r9.initMethod
            if (r8 == r12) goto L_0x00af
            gnu.expr.Declaration r12 = r8.nameDecl
            boolean r12 = r12.isStatic()
            if (r12 == 0) goto L_0x00af
            goto L_0x00b1
        L_0x00af:
            r12 = 0
            goto L_0x00b2
        L_0x00b1:
            r12 = 1
        L_0x00b2:
            if (r11 != 0) goto L_0x00c5
            if (r12 == 0) goto L_0x00b9
            r13 = 83
            goto L_0x00bb
        L_0x00b9:
            r13 = 86
        L_0x00bb:
            gnu.expr.Language r14 = r1.language
            gnu.expr.PrimProcedure[] r14 = gnu.kawa.reflect.ClassMethods.getMethods(r10, r7, r13, r10, r14)
            int r15 = r14.length
            if (r15 != 0) goto L_0x00c5
            goto L_0x00e5
        L_0x00c5:
            if (r12 == 0) goto L_0x00d3
            gnu.expr.ReferenceExp r13 = new gnu.expr.ReferenceExp
            gnu.expr.ScopeExp r14 = r8.outer
            gnu.expr.ClassExp r14 = (gnu.expr.ClassExp) r14
            gnu.expr.Declaration r14 = r14.nameDecl
            r13.<init>((gnu.expr.Declaration) r14)
            goto L_0x00dc
        L_0x00d3:
            gnu.expr.ThisExp r13 = new gnu.expr.ThisExp
            gnu.expr.Declaration r14 = r8.firstDecl()
            r13.<init>((gnu.expr.Declaration) r14)
        L_0x00dc:
            gnu.expr.QuoteExp r14 = gnu.expr.QuoteExp.getInstance(r7)
            gnu.expr.Expression r14 = gnu.kawa.functions.CompileNamedPart.makeExp((gnu.expr.Expression) r13, (gnu.expr.Expression) r14)
            return r14
        L_0x00e5:
            gnu.expr.ScopeExp r5 = r5.outer
            goto L_0x0071
        L_0x00e8:
            if (r0 == 0) goto L_0x0103
            java.lang.Object r8 = r0.getSymbol()
            r2 = 0
            gnu.expr.ReferenceExp r9 = getOriginalRef(r0)
            if (r9 == 0) goto L_0x0100
            gnu.expr.Declaration r0 = r9.getBinding()
            if (r0 != 0) goto L_0x0100
            java.lang.Object r2 = r9.getSymbol()
            r8 = r2
        L_0x0100:
            r9 = r8
            r8 = r0
            goto L_0x0107
        L_0x0103:
            r8 = r17
            r9 = r8
            r8 = r0
        L_0x0107:
            r10 = r2
            gnu.mapping.Symbol r10 = (gnu.mapping.Symbol) r10
            gnu.expr.Language r0 = r16.getLanguage()
            boolean r11 = r0.hasSeparateFunctionNamespace()
            if (r8 == 0) goto L_0x0167
            gnu.expr.ScopeExp r0 = r1.current_scope
            boolean r0 = r0 instanceof kawa.lang.TemplateScope
            if (r0 == 0) goto L_0x0128
            boolean r0 = r8.needsContext()
            if (r0 == 0) goto L_0x0128
            gnu.expr.ScopeExp r0 = r1.current_scope
            kawa.lang.TemplateScope r0 = (kawa.lang.TemplateScope) r0
            gnu.expr.Declaration r4 = r0.macroContext
            goto L_0x022e
        L_0x0128:
            r12 = 1048576(0x100000, double:5.180654E-318)
            boolean r0 = r8.getFlag(r12)
            if (r0 == 0) goto L_0x022e
            boolean r0 = r8.isStatic()
            if (r0 != 0) goto L_0x022e
            gnu.expr.ScopeExp r0 = r16.currentScope()
            r5 = r0
        L_0x013c:
            if (r5 == 0) goto L_0x014e
            gnu.expr.ScopeExp r0 = r5.outer
            gnu.expr.ScopeExp r12 = r8.context
            if (r0 != r12) goto L_0x014b
            gnu.expr.Declaration r4 = r5.firstDecl()
            goto L_0x022e
        L_0x014b:
            gnu.expr.ScopeExp r5 = r5.outer
            goto L_0x013c
        L_0x014e:
            java.lang.Error r0 = new java.lang.Error
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "internal error: missing "
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.StringBuilder r12 = r12.append(r8)
            java.lang.String r12 = r12.toString()
            r0.<init>(r12)
            throw r0
        L_0x0167:
            gnu.mapping.Environment r0 = r1.env
            r12 = 0
            if (r3 == 0) goto L_0x0171
            if (r11 == 0) goto L_0x0171
            java.lang.Object r13 = gnu.mapping.EnvironmentKey.FUNCTION
            goto L_0x0172
        L_0x0171:
            r13 = r12
        L_0x0172:
            gnu.mapping.Location r0 = r0.lookup(r10, r13)
            if (r0 == 0) goto L_0x017e
            gnu.mapping.Location r0 = r0.getBase()
            r13 = r0
            goto L_0x017f
        L_0x017e:
            r13 = r0
        L_0x017f:
            boolean r0 = r13 instanceof gnu.kawa.reflect.FieldLocation
            if (r0 == 0) goto L_0x0219
            r14 = r13
            gnu.kawa.reflect.FieldLocation r14 = (gnu.kawa.reflect.FieldLocation) r14
            gnu.expr.Declaration r0 = r14.getDeclaration()     // Catch:{ all -> 0x01f0 }
            r8 = r0
            boolean r0 = r1.inlineOk((gnu.expr.Expression) r12)     // Catch:{ all -> 0x01f0 }
            if (r0 != 0) goto L_0x01b4
            gnu.expr.Declaration r0 = getNamedPartDecl     // Catch:{ all -> 0x01f0 }
            if (r8 == r0) goto L_0x01b4
            java.lang.String r0 = "objectSyntax"
            java.lang.String r12 = r14.getMemberName()     // Catch:{ all -> 0x01f0 }
            boolean r0 = r0.equals(r12)     // Catch:{ all -> 0x01f0 }
            if (r0 == 0) goto L_0x01b2
            java.lang.String r0 = "kawa.standard.object"
            gnu.bytecode.ClassType r12 = r14.getDeclaringClass()     // Catch:{ all -> 0x01f0 }
            java.lang.String r12 = r12.getName()     // Catch:{ all -> 0x01f0 }
            boolean r0 = r0.equals(r12)     // Catch:{ all -> 0x01f0 }
            if (r0 != 0) goto L_0x01b4
        L_0x01b2:
            r8 = 0
            goto L_0x01ef
        L_0x01b4:
            boolean r0 = r1.immediate     // Catch:{ all -> 0x01f0 }
            if (r0 == 0) goto L_0x01d3
            boolean r0 = r8.isStatic()     // Catch:{ all -> 0x01f0 }
            if (r0 != 0) goto L_0x01ef
            gnu.expr.Declaration r0 = new gnu.expr.Declaration     // Catch:{ all -> 0x01f0 }
            java.lang.String r12 = "(module-instance)"
            r0.<init>((java.lang.Object) r12)     // Catch:{ all -> 0x01f0 }
            r4 = r0
            gnu.expr.QuoteExp r0 = new gnu.expr.QuoteExp     // Catch:{ all -> 0x01f0 }
            java.lang.Object r12 = r14.getInstance()     // Catch:{ all -> 0x01f0 }
            r0.<init>(r12)     // Catch:{ all -> 0x01f0 }
            r4.setValue(r0)     // Catch:{ all -> 0x01f0 }
            goto L_0x01ef
        L_0x01d3:
            boolean r0 = r8.isStatic()     // Catch:{ all -> 0x01f0 }
            if (r0 == 0) goto L_0x01ee
            java.lang.Class r0 = r14.getRClass()     // Catch:{ all -> 0x01f0 }
            if (r0 == 0) goto L_0x01ec
            java.lang.ClassLoader r12 = r0.getClassLoader()     // Catch:{ all -> 0x01f0 }
            r15 = r12
            boolean r12 = r12 instanceof gnu.bytecode.ZipLoader     // Catch:{ all -> 0x01f0 }
            if (r12 != 0) goto L_0x01ec
            boolean r12 = r15 instanceof gnu.bytecode.ArrayClassLoader     // Catch:{ all -> 0x01f0 }
            if (r12 == 0) goto L_0x01ed
        L_0x01ec:
            r8 = 0
        L_0x01ed:
            goto L_0x01ef
        L_0x01ee:
            r8 = 0
        L_0x01ef:
            goto L_0x0218
        L_0x01f0:
            r0 = move-exception
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r12 = "exception loading '"
            java.lang.StringBuilder r12 = r15.append(r12)
            java.lang.StringBuilder r12 = r12.append(r2)
            java.lang.String r15 = "' - "
            java.lang.StringBuilder r12 = r12.append(r15)
            java.lang.String r15 = r0.getMessage()
            java.lang.StringBuilder r12 = r12.append(r15)
            java.lang.String r12 = r12.toString()
            r15 = 101(0x65, float:1.42E-43)
            r1.error(r15, r12)
            r8 = 0
        L_0x0218:
            goto L_0x022e
        L_0x0219:
            if (r13 == 0) goto L_0x0221
            boolean r0 = r13.isBound()
            if (r0 != 0) goto L_0x022e
        L_0x0221:
            gnu.expr.Language r0 = r16.getLanguage()
            gnu.kawa.lispexpr.LispLanguage r0 = (gnu.kawa.lispexpr.LispLanguage) r0
            gnu.expr.Expression r0 = r0.checkDefaultBinding(r10, r1)
            if (r0 == 0) goto L_0x022e
            return r0
        L_0x022e:
            if (r8 == 0) goto L_0x026b
            if (r3 != 0) goto L_0x0241
            java.lang.Object r0 = r8.getConstantValue()
            boolean r0 = r0 instanceof kawa.standard.object
            if (r0 == 0) goto L_0x0241
            java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.getInstance(r0)
            return r0
        L_0x0241:
            gnu.expr.ScopeExp r0 = r8.getContext()
            boolean r0 = r0 instanceof kawa.lang.PatternScope
            if (r0 == 0) goto L_0x026b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r12 = "reference to pattern variable "
            java.lang.StringBuilder r0 = r0.append(r12)
            java.lang.String r12 = r8.getName()
            java.lang.StringBuilder r0 = r0.append(r12)
            java.lang.String r12 = " outside syntax template"
            java.lang.StringBuilder r0 = r0.append(r12)
            java.lang.String r0 = r0.toString()
            gnu.expr.Expression r0 = r1.syntaxError(r0)
            return r0
        L_0x026b:
            gnu.expr.ReferenceExp r0 = new gnu.expr.ReferenceExp
            r0.<init>(r9, r8)
            r0.setContextDecl(r4)
            r0.setLine((gnu.expr.Compilation) r1)
            if (r3 == 0) goto L_0x027f
            if (r11 == 0) goto L_0x027f
            r12 = 8
            r0.setFlag(r12)
        L_0x027f:
            return r0
        L_0x0280:
            boolean r0 = r2 instanceof gnu.expr.LangExp
            if (r0 == 0) goto L_0x0290
            r0 = r2
            gnu.expr.LangExp r0 = (gnu.expr.LangExp) r0
            java.lang.Object r0 = r0.getLangValue()
            gnu.expr.Expression r0 = r1.rewrite(r0, r3)
            return r0
        L_0x0290:
            boolean r0 = r2 instanceof gnu.expr.Expression
            if (r0 == 0) goto L_0x0298
            r0 = r2
            gnu.expr.Expression r0 = (gnu.expr.Expression) r0
            return r0
        L_0x0298:
            gnu.expr.Special r0 = gnu.expr.Special.abstractSpecial
            if (r2 != r0) goto L_0x029f
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.abstractExp
            return r0
        L_0x029f:
            java.lang.Object r0 = kawa.lang.Quote.quote(r2, r1)
            gnu.expr.QuoteExp r0 = gnu.expr.QuoteExp.getInstance(r0, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.rewrite(java.lang.Object, boolean):gnu.expr.Expression");
    }

    public static void setLine(Expression exp, Object location) {
        if (location instanceof SourceLocator) {
            exp.setLocation((SourceLocator) location);
        }
    }

    public static void setLine(Declaration decl, Object location) {
        if (location instanceof SourceLocator) {
            decl.setLocation((SourceLocator) location);
        }
    }

    public Object pushPositionOf(Object pair) {
        Object saved;
        if (pair instanceof SyntaxForm) {
            pair = ((SyntaxForm) pair).getDatum();
        }
        if (!(pair instanceof PairWithPosition)) {
            return null;
        }
        PairWithPosition ppair = (PairWithPosition) pair;
        PairWithPosition pairWithPosition = this.positionPair;
        if (pairWithPosition != null && pairWithPosition.getFileName() == getFileName() && this.positionPair.getLineNumber() == getLineNumber() && this.positionPair.getColumnNumber() == getColumnNumber()) {
            saved = this.positionPair;
        } else {
            saved = new PairWithPosition(this, Special.eof, this.positionPair);
        }
        setLine(pair);
        this.positionPair = ppair;
        return saved;
    }

    public void popPositionOf(Object saved) {
        if (saved != null) {
            setLine(saved);
            PairWithPosition pairWithPosition = (PairWithPosition) saved;
            this.positionPair = pairWithPosition;
            if (pairWithPosition.getCar() == Special.eof) {
                this.positionPair = (PairWithPosition) this.positionPair.getCdr();
            }
        }
    }

    public void setLineOf(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            exp.setLocation(this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: gnu.bytecode.Type} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Class} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.bytecode.Type exp2Type(gnu.lists.Pair r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.pushPositionOf(r7)
            r1 = 0
            gnu.expr.Expression r1 = r6.rewrite_car((gnu.lists.Pair) r7, (boolean) r1)     // Catch:{ all -> 0x007b }
            gnu.expr.Expression r2 = gnu.expr.InlineCalls.inlineCalls(r1, r6)     // Catch:{ all -> 0x007b }
            r1 = r2
            boolean r2 = r1 instanceof gnu.expr.ErrorExp     // Catch:{ all -> 0x007b }
            if (r2 == 0) goto L_0x0018
            r2 = 0
            r6.popPositionOf(r0)
            return r2
        L_0x0018:
            gnu.expr.Language r2 = r6.getLanguage()     // Catch:{ all -> 0x007b }
            gnu.bytecode.Type r2 = r2.getTypeFor((gnu.expr.Expression) r1)     // Catch:{ all -> 0x007b }
            if (r2 != 0) goto L_0x003f
            gnu.mapping.Environment r3 = r6.env     // Catch:{ all -> 0x003e }
            java.lang.Object r3 = r1.eval((gnu.mapping.Environment) r3)     // Catch:{ all -> 0x003e }
            boolean r4 = r3 instanceof java.lang.Class     // Catch:{ all -> 0x003e }
            if (r4 == 0) goto L_0x0035
            r4 = r3
            java.lang.Class r4 = (java.lang.Class) r4     // Catch:{ all -> 0x003e }
            gnu.bytecode.Type r4 = gnu.bytecode.Type.make(r4)     // Catch:{ all -> 0x003e }
            r2 = r4
            goto L_0x003d
        L_0x0035:
            boolean r4 = r3 instanceof gnu.bytecode.Type     // Catch:{ all -> 0x003e }
            if (r4 == 0) goto L_0x003d
            r4 = r3
            gnu.bytecode.Type r4 = (gnu.bytecode.Type) r4     // Catch:{ all -> 0x003e }
            r2 = r4
        L_0x003d:
            goto L_0x003f
        L_0x003e:
            r3 = move-exception
        L_0x003f:
            if (r2 != 0) goto L_0x0076
            boolean r3 = r1 instanceof gnu.expr.ReferenceExp     // Catch:{ all -> 0x007b }
            r4 = 101(0x65, float:1.42E-43)
            if (r3 == 0) goto L_0x006b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x007b }
            r3.<init>()     // Catch:{ all -> 0x007b }
            java.lang.String r5 = "unknown type name '"
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x007b }
            r5 = r1
            gnu.expr.ReferenceExp r5 = (gnu.expr.ReferenceExp) r5     // Catch:{ all -> 0x007b }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x007b }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x007b }
            r5 = 39
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ all -> 0x007b }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x007b }
            r6.error(r4, r3)     // Catch:{ all -> 0x007b }
            goto L_0x0070
        L_0x006b:
            java.lang.String r3 = "invalid type spec (must be \"type\" or 'type or <type>)"
            r6.error(r4, r3)     // Catch:{ all -> 0x007b }
        L_0x0070:
            gnu.bytecode.ClassType r3 = gnu.bytecode.Type.pointer_type     // Catch:{ all -> 0x007b }
            r6.popPositionOf(r0)
            return r3
        L_0x0076:
            r6.popPositionOf(r0)
            return r2
        L_0x007b:
            r1 = move-exception
            r6.popPositionOf(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.exp2Type(gnu.lists.Pair):gnu.bytecode.Type");
    }

    public Expression rewrite_with_position(Object exp, boolean function, PairWithPosition pair) {
        Expression result;
        Object saved = pushPositionOf(pair);
        if (exp == pair) {
            try {
                result = rewrite_pair(pair, function);
            } catch (Throwable th) {
                th = th;
                popPositionOf(saved);
                throw th;
            }
        } else {
            result = rewrite(exp, function);
        }
        try {
            setLineOf(result);
            popPositionOf(saved);
            return result;
        } catch (Throwable th2) {
            th = th2;
            popPositionOf(saved);
            throw th;
        }
    }

    public static Object wrapSyntax(Object form, SyntaxForm syntax) {
        if (syntax == null || (form instanceof Expression)) {
            return form;
        }
        return SyntaxForms.fromDatumIfNeeded(form, syntax);
    }

    public Object popForms(int first) {
        Values vals;
        int last = this.formStack.size();
        if (last == first) {
            return Values.empty;
        }
        if (last == first + 1) {
            vals = this.formStack.elementAt(first);
        } else {
            Values vals2 = new Values();
            for (int i = first; i < last; i++) {
                vals2.writeObject(this.formStack.elementAt(i));
            }
            Values values = vals2;
            vals = vals2;
        }
        this.formStack.setSize(first);
        return vals;
    }

    /* JADX INFO: finally extract failed */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: kawa.lang.Syntax} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: gnu.mapping.Symbol} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void scanForm(java.lang.Object r13, gnu.expr.ScopeExp r14) {
        /*
            r12 = this;
            boolean r0 = r13 instanceof kawa.lang.SyntaxForm
            if (r0 == 0) goto L_0x0035
            r0 = r13
            kawa.lang.SyntaxForm r0 = (kawa.lang.SyntaxForm) r0
            gnu.expr.ScopeExp r1 = r12.currentScope()
            kawa.lang.TemplateScope r2 = r0.getScope()     // Catch:{ all -> 0x0030 }
            r12.setCurrentScope(r2)     // Catch:{ all -> 0x0030 }
            java.util.Stack r2 = r12.formStack     // Catch:{ all -> 0x0030 }
            int r2 = r2.size()     // Catch:{ all -> 0x0030 }
            java.lang.Object r3 = r0.getDatum()     // Catch:{ all -> 0x0030 }
            r12.scanForm(r3, r14)     // Catch:{ all -> 0x0030 }
            java.util.Stack r3 = r12.formStack     // Catch:{ all -> 0x0030 }
            java.lang.Object r4 = r12.popForms(r2)     // Catch:{ all -> 0x0030 }
            java.lang.Object r4 = wrapSyntax(r4, r0)     // Catch:{ all -> 0x0030 }
            r3.add(r4)     // Catch:{ all -> 0x0030 }
            r12.setCurrentScope(r1)
            return
        L_0x0030:
            r2 = move-exception
            r12.setCurrentScope(r1)
            throw r2
        L_0x0035:
            boolean r0 = r13 instanceof gnu.mapping.Values
            if (r0 == 0) goto L_0x0054
            gnu.mapping.Values r0 = gnu.mapping.Values.empty
            if (r13 != r0) goto L_0x0040
            gnu.expr.QuoteExp r13 = gnu.expr.QuoteExp.voidExp
            goto L_0x0054
        L_0x0040:
            r0 = r13
            gnu.mapping.Values r0 = (gnu.mapping.Values) r0
            java.lang.Object[] r0 = r0.getValues()
            r1 = 0
        L_0x0048:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x0053
            r2 = r0[r1]
            r12.scanForm(r2, r14)
            int r1 = r1 + 1
            goto L_0x0048
        L_0x0053:
            return
        L_0x0054:
            boolean r0 = r13 instanceof gnu.lists.Pair
            if (r0 == 0) goto L_0x0166
            r0 = r13
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            gnu.expr.Declaration r1 = r12.macroContext
            r2 = 0
            gnu.expr.ScopeExp r3 = r12.current_scope
            java.lang.Object r4 = r12.pushPositionOf(r13)
            boolean r5 = r13 instanceof gnu.text.SourceLocator
            if (r5 == 0) goto L_0x0074
            int r5 = r14.getLineNumber()
            if (r5 >= 0) goto L_0x0074
            r5 = r13
            gnu.text.SourceLocator r5 = (gnu.text.SourceLocator) r5
            r14.setLocation(r5)
        L_0x0074:
            java.lang.Object r5 = r0.getCar()     // Catch:{ all -> 0x015a }
            boolean r6 = r5 instanceof kawa.lang.SyntaxForm     // Catch:{ all -> 0x015a }
            if (r6 == 0) goto L_0x008e
            java.lang.Object r6 = r0.getCar()     // Catch:{ all -> 0x015a }
            kawa.lang.SyntaxForm r6 = (kawa.lang.SyntaxForm) r6     // Catch:{ all -> 0x015a }
            kawa.lang.TemplateScope r7 = r6.getScope()     // Catch:{ all -> 0x015a }
            r12.setCurrentScope(r7)     // Catch:{ all -> 0x015a }
            java.lang.Object r7 = r6.getDatum()     // Catch:{ all -> 0x015a }
            r5 = r7
        L_0x008e:
            boolean r6 = r5 instanceof gnu.lists.Pair     // Catch:{ all -> 0x015a }
            if (r6 == 0) goto L_0x00f5
            r6 = r5
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6     // Catch:{ all -> 0x015a }
            r7 = r6
            java.lang.Object r6 = r6.getCar()     // Catch:{ all -> 0x015a }
            gnu.mapping.Symbol r8 = gnu.kawa.lispexpr.LispLanguage.lookup_sym     // Catch:{ all -> 0x015a }
            if (r6 != r8) goto L_0x00f5
            java.lang.Object r6 = r7.getCdr()     // Catch:{ all -> 0x015a }
            boolean r6 = r6 instanceof gnu.lists.Pair     // Catch:{ all -> 0x015a }
            if (r6 == 0) goto L_0x00f5
            java.lang.Object r6 = r7.getCdr()     // Catch:{ all -> 0x015a }
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6     // Catch:{ all -> 0x015a }
            r7 = r6
            java.lang.Object r6 = r6.getCdr()     // Catch:{ all -> 0x015a }
            boolean r6 = r6 instanceof gnu.lists.Pair     // Catch:{ all -> 0x015a }
            if (r6 == 0) goto L_0x00f5
            java.lang.Object r6 = r7.getCar()     // Catch:{ all -> 0x015a }
            gnu.expr.Expression r6 = r12.rewrite(r6)     // Catch:{ all -> 0x015a }
            java.lang.Object r8 = r7.getCdr()     // Catch:{ all -> 0x015a }
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8     // Catch:{ all -> 0x015a }
            java.lang.Object r8 = r8.getCar()     // Catch:{ all -> 0x015a }
            gnu.expr.Expression r8 = r12.rewrite(r8)     // Catch:{ all -> 0x015a }
            java.lang.Object r9 = r6.valueIfConstant()     // Catch:{ all -> 0x015a }
            java.lang.Object r10 = r8.valueIfConstant()     // Catch:{ all -> 0x015a }
            boolean r11 = r9 instanceof java.lang.Class     // Catch:{ all -> 0x015a }
            if (r11 == 0) goto L_0x00f0
            boolean r11 = r10 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x015a }
            if (r11 == 0) goto L_0x00f0
            r11 = r10
            gnu.mapping.Symbol r11 = (gnu.mapping.Symbol) r11     // Catch:{ all -> 0x00ed }
            java.lang.Object r11 = gnu.kawa.functions.GetNamedPart.getNamedPart(r9, r11)     // Catch:{ all -> 0x00ed }
            r5 = r11
            boolean r11 = r5 instanceof kawa.lang.Syntax     // Catch:{ all -> 0x00ed }
            if (r11 == 0) goto L_0x00ef
            r11 = r5
            kawa.lang.Syntax r11 = (kawa.lang.Syntax) r11     // Catch:{ all -> 0x00ed }
            r2 = r11
            goto L_0x00ef
        L_0x00ed:
            r11 = move-exception
            r5 = 0
        L_0x00ef:
            goto L_0x00f5
        L_0x00f0:
            gnu.mapping.Symbol r11 = r12.namespaceResolve((gnu.expr.Expression) r6, (gnu.expr.Expression) r8)     // Catch:{ all -> 0x015a }
            r5 = r11
        L_0x00f5:
            boolean r6 = r5 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x015a }
            if (r6 == 0) goto L_0x0126
            boolean r6 = r12.selfEvaluatingSymbol(r5)     // Catch:{ all -> 0x015a }
            if (r6 != 0) goto L_0x0126
            r6 = 1
            gnu.expr.Expression r7 = r12.rewrite(r5, r6)     // Catch:{ all -> 0x015a }
            boolean r8 = r7 instanceof gnu.expr.ReferenceExp     // Catch:{ all -> 0x015a }
            if (r8 == 0) goto L_0x0125
            r8 = r7
            gnu.expr.ReferenceExp r8 = (gnu.expr.ReferenceExp) r8     // Catch:{ all -> 0x015a }
            gnu.expr.Declaration r8 = r8.getBinding()     // Catch:{ all -> 0x015a }
            if (r8 == 0) goto L_0x0117
            kawa.lang.Syntax r6 = r12.check_if_Syntax(r8)     // Catch:{ all -> 0x015a }
            r2 = r6
            goto L_0x0125
        L_0x0117:
            java.lang.Object r6 = r12.resolve(r5, r6)     // Catch:{ all -> 0x015a }
            r5 = r6
            boolean r6 = r5 instanceof kawa.lang.Syntax     // Catch:{ all -> 0x015a }
            if (r6 == 0) goto L_0x0125
            r6 = r5
            kawa.lang.Syntax r6 = (kawa.lang.Syntax) r6     // Catch:{ all -> 0x015a }
            r2 = r6
        L_0x0125:
            goto L_0x012e
        L_0x0126:
            kawa.standard.begin r6 = kawa.standard.begin.begin     // Catch:{ all -> 0x015a }
            if (r5 != r6) goto L_0x012e
            r6 = r5
            kawa.lang.Syntax r6 = (kawa.lang.Syntax) r6     // Catch:{ all -> 0x015a }
            r2 = r6
        L_0x012e:
            gnu.expr.ScopeExp r5 = r12.current_scope
            if (r3 == r5) goto L_0x0135
            r12.setCurrentScope(r3)
        L_0x0135:
            r12.popPositionOf(r4)
            if (r2 == 0) goto L_0x0166
            java.lang.String r5 = r12.getFileName()
            int r6 = r12.getLineNumber()
            int r7 = r12.getColumnNumber()
            r12.setLine((java.lang.Object) r0)     // Catch:{ all -> 0x0153 }
            r2.scanForm(r0, r14, r12)     // Catch:{ all -> 0x0153 }
            r12.macroContext = r1
            r12.setLine(r5, r6, r7)
            return
        L_0x0153:
            r8 = move-exception
            r12.macroContext = r1
            r12.setLine(r5, r6, r7)
            throw r8
        L_0x015a:
            r5 = move-exception
            gnu.expr.ScopeExp r6 = r12.current_scope
            if (r3 == r6) goto L_0x0162
            r12.setCurrentScope(r3)
        L_0x0162:
            r12.popPositionOf(r4)
            throw r5
        L_0x0166:
            java.util.Stack r0 = r12.formStack
            r0.add(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.scanForm(java.lang.Object, gnu.expr.ScopeExp):void");
    }

    /* JADX INFO: finally extract failed */
    public LList scanBody(Object body, ScopeExp defs, boolean makeList) {
        LList list = makeList ? LList.Empty : null;
        Pair lastPair = null;
        while (true) {
            if (body != LList.Empty) {
                if (!(body instanceof SyntaxForm)) {
                    if (!(body instanceof Pair)) {
                        this.formStack.add(syntaxError("body is not a proper list"));
                        break;
                    }
                    Pair pair = (Pair) body;
                    int first = this.formStack.size();
                    scanForm(pair.getCar(), defs);
                    if (getState() == 2) {
                        Object car = pair.getCar();
                        Object obj = this.pendingForm;
                        if (car != obj) {
                            pair = makePair(pair, obj, pair.getCdr());
                        }
                        this.pendingForm = new Pair(begin.begin, pair);
                        return LList.Empty;
                    }
                    int fsize = this.formStack.size();
                    if (makeList) {
                        for (int i = first; i < fsize; i++) {
                            LList npair = makePair(pair, this.formStack.elementAt(i), LList.Empty);
                            if (lastPair == null) {
                                list = npair;
                            } else {
                                lastPair.setCdrBackdoor(npair);
                            }
                            lastPair = npair;
                        }
                        this.formStack.setSize(first);
                    }
                    body = pair.getCdr();
                } else {
                    SyntaxForm sf = (SyntaxForm) body;
                    ScopeExp save_scope = this.current_scope;
                    try {
                        setCurrentScope(sf.getScope());
                        int first2 = this.formStack.size();
                        LList f = scanBody(sf.getDatum(), defs, makeList);
                        if (makeList) {
                            LList f2 = (LList) SyntaxForms.fromDatumIfNeeded(f, sf);
                            if (lastPair == null) {
                                setCurrentScope(save_scope);
                                return f2;
                            }
                            lastPair.setCdrBackdoor(f2);
                            setCurrentScope(save_scope);
                            return list;
                        }
                        this.formStack.add(wrapSyntax(popForms(first2), sf));
                        setCurrentScope(save_scope);
                        return null;
                    } catch (Throwable th) {
                        setCurrentScope(save_scope);
                        throw th;
                    }
                }
            } else {
                break;
            }
        }
        return list;
    }

    public static Pair makePair(Pair pair, Object car, Object cdr) {
        if (pair instanceof PairWithPosition) {
            return new PairWithPosition((PairWithPosition) pair, car, cdr);
        }
        return new Pair(car, cdr);
    }

    public Expression rewrite_body(Object exp) {
        Object saved = pushPositionOf(exp);
        LetExp defs = new LetExp((Expression[]) null);
        int first = this.formStack.size();
        defs.outer = this.current_scope;
        this.current_scope = defs;
        try {
            LList list = scanBody(exp, defs, true);
            if (list.isEmpty()) {
                this.formStack.add(syntaxError("body with no expressions"));
            }
            int ndecls = defs.countNonDynamicDecls();
            if (ndecls != 0) {
                Expression[] inits = new Expression[ndecls];
                int i = ndecls;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    inits[i] = QuoteExp.undefined_exp;
                }
                defs.inits = inits;
            }
            rewriteBody(list);
            Expression body = makeBody(first, (ScopeExp) null);
            setLineOf(body);
            if (ndecls == 0) {
                return body;
            }
            defs.body = body;
            setLineOf(defs);
            pop(defs);
            popPositionOf(saved);
            return defs;
        } finally {
            pop(defs);
            popPositionOf(saved);
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: gnu.lists.LList} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void rewriteBody(gnu.lists.LList r4) {
        /*
            r3 = this;
        L_0x0000:
            gnu.lists.LList r0 = gnu.lists.LList.Empty
            if (r4 == r0) goto L_0x0023
            r0 = r4
            gnu.lists.Pair r0 = (gnu.lists.Pair) r0
            java.lang.Object r1 = r3.pushPositionOf(r0)
            java.lang.Object r2 = r0.getCar()     // Catch:{ all -> 0x001e }
            r3.rewriteInBody(r2)     // Catch:{ all -> 0x001e }
            r3.popPositionOf(r1)
            java.lang.Object r2 = r0.getCdr()
            r4 = r2
            gnu.lists.LList r4 = (gnu.lists.LList) r4
            goto L_0x0000
        L_0x001e:
            r2 = move-exception
            r3.popPositionOf(r1)
            throw r2
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.rewriteBody(gnu.lists.LList):void");
    }

    private Expression makeBody(int first, ScopeExp scope) {
        int nforms = this.formStack.size() - first;
        if (nforms == 0) {
            return QuoteExp.voidExp;
        }
        if (nforms == 1) {
            return (Expression) this.formStack.pop();
        }
        Expression[] exps = new Expression[nforms];
        for (int i = 0; i < nforms; i++) {
            exps[i] = (Expression) this.formStack.elementAt(first + i);
        }
        this.formStack.setSize(first);
        if (scope instanceof ModuleExp) {
            return new ApplyExp((Procedure) AppendValues.appendValues, exps);
        }
        return ((LispLanguage) getLanguage()).makeBody(exps);
    }

    public void noteAccess(Object name, ScopeExp scope) {
        if (this.notedAccess == null) {
            this.notedAccess = new Vector();
        }
        this.notedAccess.addElement(name);
        this.notedAccess.addElement(scope);
    }

    public void processAccesses() {
        Vector vector = this.notedAccess;
        if (vector != null) {
            int sz = vector.size();
            ScopeExp saveScope = this.current_scope;
            for (int i = 0; i < sz; i += 2) {
                Object name = this.notedAccess.elementAt(i);
                ScopeExp scope = (ScopeExp) this.notedAccess.elementAt(i + 1);
                if (this.current_scope != scope) {
                    setCurrentScope(scope);
                }
                Declaration decl = this.lexical.lookup(name, -1);
                if (decl != null && !decl.getFlag(65536)) {
                    decl.getContext().currentLambda().capture(decl);
                    decl.setCanRead(true);
                    decl.setSimple(false);
                    decl.setFlag(524288);
                }
            }
            if (this.current_scope != saveScope) {
                setCurrentScope(saveScope);
            }
        }
    }

    public void finishModule(ModuleExp mexp) {
        boolean moduleStatic = mexp.isStatic();
        for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.getFlag(512)) {
                error('e', decl, "'", decl.getFlag(1024) ? "' exported but never defined" : decl.getFlag(2048) ? "' declared static but never defined" : "' declared but never defined");
            }
            if (mexp.getFlag(16384) || (this.generateMain && !this.immediate)) {
                if (!decl.getFlag(1024)) {
                    decl.setPrivate(true);
                } else if (decl.isPrivate()) {
                    if (decl.getFlag(16777216)) {
                        error('e', decl, "'", "' is declared both private and exported");
                    }
                    decl.setPrivate(false);
                }
            }
            if (moduleStatic) {
                decl.setFlag(2048);
            } else if ((mexp.getFlag(65536) && !decl.getFlag(2048)) || Compilation.moduleStatic < 0 || mexp.getFlag(131072)) {
                decl.setFlag(4096);
            }
        }
    }

    static void vectorReverse(Vector vec, int start, int count) {
        int j = count / 2;
        int last = (start + count) - 1;
        for (int i = 0; i < j; i++) {
            Object tmp = vec.elementAt(start + i);
            vec.setElementAt(vec.elementAt(last - i), start + i);
            vec.setElementAt(tmp, last - i);
        }
    }

    public void resolveModule(ModuleExp mexp) {
        int numPending = this.pendingImports == null ? 0 : this.pendingImports.size();
        int i = 0;
        while (i < numPending) {
            int i2 = i + 1;
            ModuleInfo info = (ModuleInfo) this.pendingImports.elementAt(i);
            int i3 = i2 + 1;
            ScopeExp defs = (ScopeExp) this.pendingImports.elementAt(i2);
            int i4 = i3 + 1;
            Expression posExp = (Expression) this.pendingImports.elementAt(i3);
            int i5 = i4 + 1;
            Integer savedSize = (Integer) this.pendingImports.elementAt(i4);
            if (mexp == defs) {
                Object obj = null;
                Expression savePos = new ReferenceExp((Object) null);
                savePos.setLine((Compilation) this);
                setLine(posExp);
                int beforeSize = this.formStack.size();
                require.importDefinitions((String) null, info, (Procedure) null, this.formStack, defs, this);
                int desiredPosition = savedSize.intValue();
                if (savedSize.intValue() != beforeSize) {
                    int curSize = this.formStack.size();
                    vectorReverse(this.formStack, desiredPosition, beforeSize - desiredPosition);
                    vectorReverse(this.formStack, beforeSize, curSize - beforeSize);
                    vectorReverse(this.formStack, desiredPosition, curSize - desiredPosition);
                }
                setLine(savePos);
            }
            i = i5;
        }
        this.pendingImports = null;
        processAccesses();
        setModule(mexp);
        Compilation save_comp = Compilation.setSaveCurrent(this);
        try {
            rewriteInBody(popForms(this.firstForm));
            mexp.body = makeBody(this.firstForm, mexp);
            if (!this.immediate) {
                this.lexical.pop((ScopeExp) mexp);
            }
        } finally {
            Compilation.restoreCurrent(save_comp);
        }
    }

    public Declaration makeRenamedAlias(Declaration decl, ScopeExp templateScope) {
        if (templateScope == null) {
            return decl;
        }
        return makeRenamedAlias(decl.getSymbol(), decl, templateScope);
    }

    public Declaration makeRenamedAlias(Object name, Declaration decl, ScopeExp templateScope) {
        Declaration alias = new Declaration(name);
        alias.setAlias(true);
        alias.setPrivate(true);
        alias.context = templateScope;
        ReferenceExp ref = new ReferenceExp(decl);
        ref.setDontDereference(true);
        alias.noteValue(ref);
        return alias;
    }

    public void pushRenamedAlias(Declaration alias) {
        Declaration decl = getOriginalRef(alias).getBinding();
        ScopeExp templateScope = alias.context;
        decl.setSymbol((Object) null);
        Declaration old = templateScope.lookup(decl.getSymbol());
        if (old != null) {
            templateScope.remove(old);
        }
        templateScope.addDeclaration(alias);
        if (this.renamedAliasStack == null) {
            this.renamedAliasStack = new Stack();
        }
        this.renamedAliasStack.push(old);
        this.renamedAliasStack.push(alias);
        this.renamedAliasStack.push(templateScope);
    }

    public void popRenamedAlias(int count) {
        while (true) {
            count--;
            if (count >= 0) {
                ScopeExp templateScope = (ScopeExp) this.renamedAliasStack.pop();
                Declaration alias = (Declaration) this.renamedAliasStack.pop();
                getOriginalRef(alias).getBinding().setSymbol(alias.getSymbol());
                templateScope.remove(alias);
                Object old = this.renamedAliasStack.pop();
                if (old != null) {
                    templateScope.addDeclaration((Declaration) old);
                }
            } else {
                return;
            }
        }
    }

    public Declaration define(Object name, SyntaxForm nameSyntax, ScopeExp defs) {
        boolean aliasNeeded = (nameSyntax == null || nameSyntax.getScope() == currentScope()) ? false : true;
        Declaration decl = defs.getDefine(aliasNeeded ? new String(name.toString()) : name, 'w', this);
        if (aliasNeeded) {
            nameSyntax.getScope().addDeclaration(makeRenamedAlias(name, decl, nameSyntax.getScope()));
        }
        push(decl);
        return decl;
    }
}
