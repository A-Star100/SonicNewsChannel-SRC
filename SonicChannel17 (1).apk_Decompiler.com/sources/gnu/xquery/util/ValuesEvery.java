package gnu.xquery.util;

import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;

public class ValuesEvery extends MethodProc {
    public static final ValuesEvery every = new ValuesEvery(true);
    public static final ValuesEvery some = new ValuesEvery(false);
    boolean matchAll;

    public ValuesEvery(boolean matchAll2) {
        this.matchAll = matchAll2;
    }

    public int numArgs() {
        return 8194;
    }

    public void apply(CallContext ctx) throws Throwable {
        Procedure proc = (Procedure) ctx.getNextArg();
        Object val = ctx.getNextArg();
        boolean ok = this.matchAll;
        Procedure.checkArgCount(proc, 1);
        if (val instanceof Values) {
            int ipos = 0;
            Values values = (Values) val;
            do {
                int nextPos = values.nextPos(ipos);
                ipos = nextPos;
                if (nextPos == 0) {
                    break;
                }
                proc.check1(values.getPosPrevious(ipos), ctx);
                ok = BooleanValue.booleanValue(ctx.runUntilValue());
            } while (ok == this.matchAll);
        } else {
            proc.check1(val, ctx);
            ok = BooleanValue.booleanValue(ctx.runUntilValue());
        }
        ctx.consumer.writeBoolean(ok);
    }
}
