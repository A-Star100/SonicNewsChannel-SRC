package gnu.kawa.functions;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.ArrayGet;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.math.Numeric;
import gnu.text.Char;
import java.io.Externalizable;

public class CompilationHelpers {
    public static final Declaration setterDecl;
    static final Field setterField;
    static final ClassType setterType;
    static final ClassType typeList = ClassType.make("java.util.List");

    private static boolean nonNumeric(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            return false;
        }
        Object value = ((QuoteExp) exp).getValue();
        if ((value instanceof Numeric) || (value instanceof Char) || (value instanceof Symbol)) {
            return false;
        }
        return true;
    }

    static {
        ClassType make = ClassType.make("gnu.kawa.functions.Setter");
        setterType = make;
        Field declaredField = make.getDeclaredField("setter");
        setterField = declaredField;
        Declaration declaration = new Declaration((Object) "setter", declaredField);
        setterDecl = declaration;
        declaration.noteValue(new QuoteExp(Setter.setter));
    }

    public static Expression validateApplyToArgs(ApplyExp exp, InlineCalls visitor, Type required, Procedure applyToArgs) {
        Expression[] args = exp.getArgs();
        int nargs = args.length - 1;
        if (nargs >= 0) {
            Expression proc = args[0];
            if (!proc.getFlag(1)) {
                if (proc instanceof LambdaExp) {
                    Expression[] rargs = new Expression[nargs];
                    System.arraycopy(args, 1, rargs, 0, nargs);
                    return visitor.visit(new ApplyExp(proc, rargs).setLine((Expression) exp), required);
                }
                proc = visitor.visit(proc, (Type) null);
                args[0] = proc;
            }
            Type ptype = proc.getType().getRealType();
            Compilation comp = visitor.getCompilation();
            Language language = comp.getLanguage();
            if (ptype.isSubtype(Compilation.typeProcedure)) {
                Expression[] rargs2 = new Expression[nargs];
                System.arraycopy(args, 1, rargs2, 0, nargs);
                ApplyExp nexp = new ApplyExp(proc, rargs2);
                nexp.setLine((Expression) exp);
                return proc.validateApply(nexp, visitor, required, (Declaration) null);
            }
            ApplyExp result = null;
            if (CompileReflect.checkKnownClass(ptype, comp) >= 0) {
                if (ptype.isSubtype(Compilation.typeType) || language.getTypeFor(proc, false) != null) {
                    result = new ApplyExp((Procedure) Invoke.make, args);
                } else if (ptype instanceof ArrayType) {
                    result = new ApplyExp((Procedure) new ArrayGet(((ArrayType) ptype).getComponentType()), args);
                } else if (ptype instanceof ClassType) {
                    ClassType classType = (ClassType) ptype;
                    ClassType ctype = classType;
                    if (classType.isSubclass(typeList) && nargs == 1) {
                        result = new ApplyExp(ctype.getMethod("get", new Type[]{Type.intType}), args);
                    }
                }
            }
            if (result != null) {
                result.setLine((Expression) exp);
                return visitor.visitApplyOnly(result, required);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateSetter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Declaration decl;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg = args[0];
            Type argType = arg.getType();
            if (argType instanceof ArrayType) {
                return new SetArrayExp(arg, (ArrayType) argType);
            }
            if (argType instanceof ClassType) {
                ClassType classType = (ClassType) argType;
                ClassType classType2 = classType;
                if (classType.isSubclass(typeList)) {
                    if (exp instanceof SetListExp) {
                        return exp;
                    }
                    return new SetListExp(exp.getFunction(), args);
                }
            }
            if ((arg instanceof ReferenceExp) && (decl = ((ReferenceExp) arg).getBinding()) != null) {
                arg = decl.getValue();
            }
            if (arg instanceof QuoteExp) {
                Object value = ((QuoteExp) arg).getValue();
                if (value instanceof Procedure) {
                    Procedure setter = ((Procedure) value).getSetter();
                    if (setter instanceof Procedure) {
                        if (setter instanceof Externalizable) {
                            return new QuoteExp(setter);
                        }
                        Declaration decl2 = Declaration.getDeclaration(setter);
                        if (decl2 != null) {
                            return new ReferenceExp(decl2);
                        }
                    }
                }
            }
        }
        return exp;
    }

    public static Expression validateIsEqv(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (nonNumeric(args[0]) || nonNumeric(args[1])) {
            return new ApplyExp((Procedure) ((IsEqv) proc).isEq, args);
        }
        return exp;
    }
}
