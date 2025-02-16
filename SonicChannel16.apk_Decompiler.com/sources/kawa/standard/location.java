package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Location;
import gnu.mapping.LocationProc;
import gnu.mapping.ProcLocation;
import gnu.mapping.Procedure;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class location extends Syntax {
    public static final location location;
    private static ClassType thisType = ClassType.make("kawa.standard.location");

    static {
        location location2 = new location();
        location = location2;
        location2.setName("location");
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing argument to location");
        }
        Pair pair = (Pair) obj;
        if (pair.getCdr() != LList.Empty) {
            return tr.syntaxError("extra arguments to location");
        }
        return Invoke.makeInvokeStatic(thisType, "makeLocationProc", new Expression[]{rewrite(tr.rewrite(pair.getCar()), tr)});
    }

    public static Expression rewrite(Expression arg, Translator tr) {
        if (arg instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) arg;
            rexp.setDontDereference(true);
            Declaration decl = rexp.getBinding();
            if (decl != null) {
                decl.maybeIndirectBinding(tr);
                Declaration decl2 = Declaration.followAliases(decl);
                decl2.setCanRead(true);
                decl2.setCanWrite(true);
            }
            return rexp;
        } else if (!(arg instanceof ApplyExp)) {
            return tr.syntaxError("invalid argument to location");
        } else {
            ApplyExp aexp = (ApplyExp) arg;
            Expression[] args = new Expression[(aexp.getArgs().length + 1)];
            args[0] = aexp.getFunction();
            System.arraycopy(aexp.getArgs(), 0, args, 1, args.length - 1);
            return Invoke.makeInvokeStatic(thisType, "makeProcLocation", args);
        }
    }

    public static Location makeProcLocation$V(Procedure proc, Object[] args) {
        int nargs = args.length;
        if ((proc instanceof ApplyToArgs) && nargs > 0 && (args[0] instanceof Procedure)) {
            Procedure proc2 = args[0];
            if ((proc2 instanceof LocationProc) && nargs == 1) {
                return ((LocationProc) proc2).getLocation();
            }
            Object[] rargs = new Object[(nargs - 1)];
            System.arraycopy(args, 1, rargs, 0, rargs.length);
            return new ProcLocation(proc2, rargs);
        } else if (!(proc instanceof LocationProc) || nargs != 0) {
            return new ProcLocation(proc, args);
        } else {
            return ((LocationProc) proc).getLocation();
        }
    }

    public static Procedure makeLocationProc(Location loc) {
        return new LocationProc(loc);
    }
}
