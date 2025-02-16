package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.GenericProc;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.lists.FString;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import java.util.Vector;

public class ClassMethods extends Procedure2 {
    public static final ClassMethods classMethods;

    static {
        ClassMethods classMethods2 = new ClassMethods();
        classMethods = classMethods2;
        classMethods2.setName("class-methods");
    }

    public Object apply2(Object arg0, Object arg1) {
        return apply(this, arg0, arg1);
    }

    public static MethodProc apply(Procedure thisProc, Object arg0, Object arg1) {
        ClassType dtype;
        if (arg0 instanceof Class) {
            arg0 = Type.make((Class) arg0);
        }
        if (arg0 instanceof ClassType) {
            dtype = (ClassType) arg0;
        } else if ((arg0 instanceof String) || (arg0 instanceof FString) || (arg0 instanceof Symbol)) {
            dtype = ClassType.make(arg0.toString());
        } else {
            throw new WrongType(thisProc, 0, (ClassCastException) null);
        }
        if ((arg1 instanceof String) || (arg1 instanceof FString) || (arg1 instanceof Symbol)) {
            String mname = arg1.toString();
            if (!"<init>".equals(mname)) {
                mname = Compilation.mangleName(mname);
            }
            MethodProc result = apply(dtype, mname, 0, Language.getDefaultLanguage());
            if (result != null) {
                return result;
            }
            throw new RuntimeException("no applicable method named `" + mname + "' in " + dtype.getName());
        }
        throw new WrongType(thisProc, 1, (ClassCastException) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0035 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int removeRedundantMethods(java.util.Vector r12) {
        /*
            int r0 = r12.size()
            r1 = 1
        L_0x0005:
            if (r1 >= r0) goto L_0x0055
            java.lang.Object r2 = r12.elementAt(r1)
            gnu.bytecode.Method r2 = (gnu.bytecode.Method) r2
            gnu.bytecode.ClassType r3 = r2.getDeclaringClass()
            gnu.bytecode.Type[] r4 = r2.getParameterTypes()
            int r5 = r4.length
            r6 = 0
        L_0x0017:
            if (r6 >= r1) goto L_0x0051
            java.lang.Object r7 = r12.elementAt(r6)
            gnu.bytecode.Method r7 = (gnu.bytecode.Method) r7
            gnu.bytecode.Type[] r8 = r7.getParameterTypes()
            int r9 = r8.length
            if (r5 == r9) goto L_0x0027
            goto L_0x0035
        L_0x0027:
            r9 = r5
        L_0x0028:
            int r9 = r9 + -1
            if (r9 < 0) goto L_0x0032
            r10 = r4[r9]
            r11 = r8[r9]
            if (r10 == r11) goto L_0x0028
        L_0x0032:
            if (r9 < 0) goto L_0x0038
        L_0x0035:
            int r6 = r6 + 1
            goto L_0x0017
        L_0x0038:
            gnu.bytecode.ClassType r10 = r7.getDeclaringClass()
            boolean r10 = r3.isSubtype(r10)
            if (r10 == 0) goto L_0x0045
            r12.setElementAt(r2, r6)
        L_0x0045:
            int r10 = r0 + -1
            java.lang.Object r10 = r12.elementAt(r10)
            r12.setElementAt(r10, r1)
            int r0 = r0 + -1
            goto L_0x0005
        L_0x0051:
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0055:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.ClassMethods.removeRedundantMethods(java.util.Vector):int");
    }

    public static PrimProcedure[] getMethods(ObjectType dtype, String mname, char mode, ClassType caller, Language language) {
        if (dtype == Type.tostring_type) {
            dtype = Type.string_type;
        }
        MethodFilter filter = new MethodFilter(mname, 0, 0, caller, mode == 'P' ? null : dtype);
        boolean named_class_only = mode == 'P' || "<init>".equals(mname);
        Vector methods = new Vector();
        dtype.getMethods(filter, named_class_only ? 0 : 2, methods);
        if (!named_class_only && (!(dtype instanceof ClassType) || ((ClassType) dtype).isInterface())) {
            Type.pointer_type.getMethods(filter, 0, methods);
        }
        int mlength = named_class_only ? methods.size() : removeRedundantMethods(methods);
        PrimProcedure[] result = new PrimProcedure[mlength];
        int count = 0;
        int i = mlength;
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            Method method = (Method) methods.elementAt(i);
            if (!named_class_only && method.getDeclaringClass() != dtype) {
                Type itype = dtype.getImplementationType();
                if (itype instanceof ClassType) {
                    method = new Method(method, (ClassType) itype);
                }
            }
            result[count] = new PrimProcedure(method, mode, language);
            count++;
        }
    }

    public static long selectApplicable(PrimProcedure[] methods, Type[] atypes) {
        int limit = methods.length;
        int numDefApplicable = 0;
        int numPosApplicable = 0;
        int i = 0;
        while (i < limit) {
            int code = methods[i].isApplicable(atypes);
            if (code < 0) {
                PrimProcedure tmp = methods[limit - 1];
                methods[limit - 1] = methods[i];
                methods[i] = tmp;
                limit--;
            } else if (code > 0) {
                PrimProcedure tmp2 = methods[numDefApplicable];
                methods[numDefApplicable] = methods[i];
                methods[i] = tmp2;
                numDefApplicable++;
                i++;
            } else {
                numPosApplicable++;
                i++;
            }
        }
        return (((long) numDefApplicable) << 32) + ((long) numPosApplicable);
    }

    public static int selectApplicable(PrimProcedure[] methods, int numArgs) {
        int limit = methods.length;
        int numTooManyArgs = 0;
        int numTooFewArgs = 0;
        int numOk = 0;
        int i = 0;
        while (i < limit) {
            int num = methods[i].numArgs();
            int min = Procedure.minArgs(num);
            int max = Procedure.maxArgs(num);
            boolean ok = false;
            if (numArgs < min) {
                numTooFewArgs++;
            } else if (numArgs <= max || max < 0) {
                ok = true;
            } else {
                numTooManyArgs++;
            }
            if (ok) {
                numOk++;
                i++;
            } else {
                PrimProcedure tmp = methods[limit - 1];
                methods[limit - 1] = methods[i];
                methods[i] = tmp;
                limit--;
            }
        }
        if (numOk > 0) {
            return numOk;
        }
        if (numTooFewArgs > 0) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS;
        }
        if (numTooManyArgs > 0) {
            return MethodProc.NO_MATCH_TOO_MANY_ARGS;
        }
        return 0;
    }

    public static MethodProc apply(ObjectType dtype, String mname, char mode, Language language) {
        PrimProcedure[] methods = getMethods(dtype, mname, mode, (ClassType) null, language);
        GenericProc gproc = null;
        PrimProcedure pproc = null;
        for (PrimProcedure cur : methods) {
            if (pproc != null && gproc == null) {
                gproc = new GenericProc();
                gproc.add((MethodProc) pproc);
            }
            pproc = cur;
            if (gproc != null) {
                gproc.add((MethodProc) pproc);
            }
        }
        if (gproc == null) {
            return pproc;
        }
        gproc.setName(dtype.getName() + "." + mname);
        return gproc;
    }

    static String checkName(Expression exp, boolean reversible) {
        String nam;
        if (!(exp instanceof QuoteExp)) {
            return null;
        }
        Object name = ((QuoteExp) exp).getValue();
        if ((name instanceof FString) || (name instanceof String)) {
            nam = name.toString();
        } else if (!(name instanceof Symbol)) {
            return null;
        } else {
            nam = ((Symbol) name).getName();
        }
        if (Compilation.isValidJavaName(nam)) {
            return nam;
        }
        return Compilation.mangleName(nam, reversible);
    }

    static String checkName(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            return null;
        }
        Object name = ((QuoteExp) exp).getValue();
        if ((name instanceof FString) || (name instanceof String)) {
            return name.toString();
        }
        if (name instanceof Symbol) {
            return ((Symbol) name).getName();
        }
        return null;
    }
}
