package kawa.standard;

import gnu.expr.CatchClause;
import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.TryExp;
import gnu.lists.FVector;
import kawa.lang.Translator;

public class try_catch {
    public static Expression rewrite(Object try_part, Object clauses) {
        Translator tr = (Translator) Compilation.getCurrent();
        Expression try_part_exp = tr.rewrite(try_part);
        CatchClause prev = null;
        CatchClause chain = null;
        FVector vec = (FVector) clauses;
        int n = vec.size();
        for (int i = 0; i < n; i++) {
            Expression cl = SchemeCompilation.lambda.rewrite(vec.get(i), tr);
            if (cl instanceof ErrorExp) {
                return cl;
            }
            if (!(cl instanceof LambdaExp)) {
                return tr.syntaxError("internal error with try-catch");
            }
            CatchClause ccl = new CatchClause((LambdaExp) cl);
            if (prev == null) {
                chain = ccl;
            } else {
                prev.setNext(ccl);
            }
            prev = ccl;
        }
        if ((try_part_exp instanceof ErrorExp) != 0) {
            return try_part_exp;
        }
        TryExp texp = new TryExp(try_part_exp, (Expression) null);
        texp.setCatchClauses(chain);
        return texp;
    }
}
