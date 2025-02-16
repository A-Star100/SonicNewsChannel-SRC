package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.PatternScope;
import kawa.lang.Quote;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxTemplate;
import kawa.lang.Translator;

public class syntax extends Quote {
    static final Method makeTemplateScopeMethod;
    public static final syntax quasiSyntax = new syntax("quasisyntax", true);
    public static final syntax syntax = new syntax("syntax", false);
    static final ClassType typeTemplateScope;

    static {
        ClassType make = ClassType.make("kawa.lang.TemplateScope");
        typeTemplateScope = make;
        makeTemplateScopeMethod = make.getDeclaredMethod("make", 0);
    }

    public syntax(String name, boolean isQuasi) {
        super(name, isQuasi);
    }

    /* access modifiers changed from: protected */
    public boolean expandColonForms() {
        return false;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (form.getCdr() instanceof Pair) {
            Pair pair = (Pair) form.getCdr();
            Pair pair2 = pair;
            Pair form2 = pair;
            if (pair.getCdr() == LList.Empty) {
                Declaration saveTemplateScopeDecl = tr.templateScopeDecl;
                if (saveTemplateScopeDecl == null) {
                    tr.letStart();
                    Declaration templateScopeDecl = tr.letVariable((Object) null, typeTemplateScope, new ApplyExp(makeTemplateScopeMethod, Expression.noExpressions));
                    templateScopeDecl.setCanRead();
                    tr.templateScopeDecl = templateScopeDecl;
                    tr.letEnter();
                }
                try {
                    Expression body = coerceExpression(expand(form2.getCar(), this.isQuasi ? 1 : -1, tr), tr);
                    return saveTemplateScopeDecl == null ? tr.letDone(body) : body;
                } finally {
                    tr.templateScopeDecl = saveTemplateScopeDecl;
                }
            }
        }
        return tr.syntaxError("syntax forms requires a single form");
    }

    /* access modifiers changed from: protected */
    public Expression leaf(Object val, Translator tr) {
        return makeSyntax(val, tr);
    }

    static Expression makeSyntax(Object form, Translator tr) {
        SyntaxTemplate template = new SyntaxTemplate(form, (SyntaxForm) null, tr);
        Expression matchArray = QuoteExp.nullExp;
        PatternScope patternScope = tr.patternScope;
        if (!(patternScope == null || patternScope.matchArray == null)) {
            matchArray = new ReferenceExp(patternScope.matchArray);
        }
        return new ApplyExp(ClassType.make("kawa.lang.SyntaxTemplate").getDeclaredMethod("execute", 2), new QuoteExp(template), matchArray, new ReferenceExp(tr.templateScopeDecl));
    }
}
