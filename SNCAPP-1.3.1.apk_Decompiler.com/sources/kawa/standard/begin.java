package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class begin extends Syntax {
    public static final begin begin;

    static {
        begin begin2 = new begin();
        begin = begin2;
        begin2.setName("begin");
    }

    public Expression rewrite(Object obj, Translator tr) {
        return tr.rewrite_body(obj);
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        Object body = tr.scanBody(st.getCdr(), defs, true);
        if (body != LList.Empty) {
            tr.formStack.add(Translator.makePair(st, st.getCar(), body));
        }
    }
}
