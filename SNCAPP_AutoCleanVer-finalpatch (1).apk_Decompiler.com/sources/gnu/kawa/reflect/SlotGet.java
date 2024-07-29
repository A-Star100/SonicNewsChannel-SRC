package gnu.kawa.reflect;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

public class SlotGet extends Procedure2 implements HasSetter, Inlineable {
    public static final SlotGet field = new SlotGet("field", false, SlotSet.set$Mnfield$Ex);
    static Class[] noClasses = new Class[0];
    public static final SlotGet slotRef = new SlotGet("slot-ref", false, SlotSet.set$Mnfield$Ex);
    public static final SlotGet staticField = new SlotGet("static-field", true, SlotSet.set$Mnstatic$Mnfield$Ex);
    boolean isStatic;
    Procedure setter;

    public SlotGet(String name, boolean isStatic2) {
        super(name);
        this.isStatic = isStatic2;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotGet");
    }

    public SlotGet(String name, boolean isStatic2, Procedure setter2) {
        this(name, isStatic2);
        this.setter = setter2;
    }

    public static Object field(Object obj, String fname) {
        return field.apply2(obj, fname);
    }

    public static Object staticField(Object obj, String fname) {
        return staticField.apply2(obj, fname);
    }

    public Object apply2(Object arg1, Object arg2) {
        String name;
        String fname;
        String getName = null;
        String isName = null;
        if (arg2 instanceof Field) {
            fname = ((Field) arg2).getName();
            name = Compilation.demangleName(fname, true);
        } else if (arg2 instanceof Method) {
            String mname = ((Method) arg2).getName();
            name = Compilation.demangleName(mname, false);
            if (mname.startsWith("get")) {
                getName = mname;
            } else if (mname.startsWith("is")) {
                isName = mname;
            }
            fname = null;
        } else if ((arg2 instanceof SimpleSymbol) || (arg2 instanceof CharSequence)) {
            name = arg2.toString();
            fname = Compilation.mangleNameIfNeeded(name);
        } else {
            throw new WrongType((Procedure) this, 2, arg2, PropertyTypeConstants.PROPERTY_TYPE_STRING);
        }
        if ("class".equals(fname)) {
            fname = "class";
        } else if (PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(fname)) {
            fname = PropertyTypeConstants.PROPERTY_TYPE_LENGTH;
        }
        return getSlotValue(this.isStatic, arg1, name, fname, getName, isName, Language.getDefaultLanguage());
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object getSlotValue(boolean r6, java.lang.Object r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, gnu.expr.Language r12) {
        /*
            if (r6 == 0) goto L_0x0007
            java.lang.Class r0 = coerceToClass(r7)
            goto L_0x000b
        L_0x0007:
            java.lang.Class r0 = r7.getClass()
        L_0x000b:
            java.lang.String r1 = "length"
            if (r9 != r1) goto L_0x001e
            boolean r1 = r0.isArray()
            if (r1 == 0) goto L_0x001e
            int r6 = java.lang.reflect.Array.getLength(r7)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            return r6
        L_0x001e:
            java.lang.String r1 = "class"
            if (r9 != r1) goto L_0x0023
            return r0
        L_0x0023:
            r1 = 0
            r2 = 39
            r3 = 1
            if (r9 == 0) goto L_0x006f
            java.lang.reflect.Field r4 = r0.getField(r9)     // Catch:{ Exception -> 0x002e }
            goto L_0x0030
        L_0x002e:
            r4 = move-exception
            r4 = 0
        L_0x0030:
            if (r4 == 0) goto L_0x006f
            if (r6 == 0) goto L_0x005a
            int r5 = r4.getModifiers()
            r5 = r5 & 8
            if (r5 == 0) goto L_0x003d
            goto L_0x005a
        L_0x003d:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "cannot access non-static field '"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r9)
            java.lang.StringBuilder r7 = r7.append(r2)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x005a:
            java.lang.Class r5 = r4.getType()     // Catch:{ IllegalAccessException -> 0x006c, Exception -> 0x0067 }
            java.lang.Object r4 = r4.get(r7)     // Catch:{ IllegalAccessException -> 0x006c, Exception -> 0x0067 }
            java.lang.Object r6 = r12.coerceToObject(r5, r4)     // Catch:{ IllegalAccessException -> 0x006c, Exception -> 0x0067 }
            return r6
        L_0x0067:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x006f
        L_0x006c:
            r1 = move-exception
            r1 = 1
        L_0x006f:
            if (r10 == 0) goto L_0x0074
        L_0x0073:
            goto L_0x007b
        L_0x0074:
            java.lang.String r10 = "get"
            java.lang.String r10 = gnu.expr.ClassExp.slotToMethodName(r10, r8)     // Catch:{ Exception -> 0x0089 }
            goto L_0x0073
        L_0x007b:
            java.lang.Class[] r4 = noClasses     // Catch:{ Exception -> 0x0089 }
            java.lang.reflect.Method r8 = r0.getMethod(r10, r4)     // Catch:{ Exception -> 0x0089 }
            goto L_0x009b
        L_0x0082:
            r6 = move-exception
            goto L_0x00d2
        L_0x0084:
            r6 = move-exception
            goto L_0x00d4
        L_0x0086:
            r6 = move-exception
            goto L_0x0118
        L_0x0089:
            r10 = move-exception
            if (r11 == 0) goto L_0x008d
        L_0x008c:
            goto L_0x0094
        L_0x008d:
            java.lang.String r10 = "is"
            java.lang.String r11 = gnu.expr.ClassExp.slotToMethodName(r10, r8)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            goto L_0x008c
        L_0x0094:
            java.lang.Class[] r8 = noClasses     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.reflect.Method r8 = r0.getMethod(r11, r8)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            r10 = r11
        L_0x009b:
            if (r6 == 0) goto L_0x00c3
            int r6 = r8.getModifiers()     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            r6 = r6 & 8
            if (r6 == 0) goto L_0x00a6
            goto L_0x00c3
        L_0x00a6:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            r7.<init>()     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.String r8 = "cannot call non-static getter method '"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.StringBuilder r7 = r7.append(r2)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.String r7 = r7.toString()     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            r6.<init>(r7)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            throw r6     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
        L_0x00c3:
            java.lang.Object[] r6 = gnu.mapping.Values.noArgs     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.Object r6 = r8.invoke(r7, r6)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.Class r7 = r8.getReturnType()     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            java.lang.Object r6 = r12.coerceToObject(r7, r6)     // Catch:{ InvocationTargetException -> 0x0086, IllegalAccessException -> 0x0084, NoSuchMethodException -> 0x0082 }
            return r6
        L_0x00d2:
            r3 = r1
            goto L_0x00d6
        L_0x00d4:
        L_0x00d6:
            if (r3 == 0) goto L_0x00f1
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "illegal access for field "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r9)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x00f1:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "no such field "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r9)
            java.lang.String r8 = " in "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r0.getName()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x0118:
            java.lang.Throwable r6 = r6.getTargetException()
            java.lang.RuntimeException r6 = gnu.mapping.WrappedException.wrapIfNeeded(r6)
            goto L_0x0122
        L_0x0121:
            throw r6
        L_0x0122:
            goto L_0x0121
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotGet.getSlotValue(boolean, java.lang.Object, java.lang.String, java.lang.String, java.lang.String, java.lang.String, gnu.expr.Language):java.lang.Object");
    }

    static Class coerceToClass(Object obj) {
        if (obj instanceof Class) {
            return (Class) obj;
        }
        if (obj instanceof Type) {
            return ((Type) obj).getReflectClass();
        }
        throw new RuntimeException("argument is neither Class nor Type");
    }

    public void setN(Object[] args) {
        int nargs = args.length;
        if (nargs == 3) {
            set2(args[0], args[1], args[2]);
            return;
        }
        throw new WrongArguments(getSetter(), nargs);
    }

    public void set2(Object obj, Object name, Object value) {
        SlotSet.apply(this.isStatic, obj, (String) name, value);
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field2 = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field2 != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field2, clas)) {
                return field2;
            }
        }
        Method method = clas.getMethod(ClassExp.slotToMethodName("get", name), Type.typeArray0);
        if (method == null) {
            return field2;
        }
        return method;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Compilation compilation = comp;
        Target target2 = target;
        Expression[] args = exp.getArgs();
        Expression arg0 = args[0];
        boolean isStaticField = true;
        Expression arg1 = args[1];
        Language language = comp.getLanguage();
        Type type = this.isStatic ? language.getTypeFor(arg0) : arg0.getType();
        CodeAttr code = comp.getCode();
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            ObjectType ctype = (ObjectType) type;
            Object part = ((QuoteExp) arg1).getValue();
            if (part instanceof Field) {
                Field field2 = (Field) part;
                if ((field2.getModifiers() & 8) == 0) {
                    isStaticField = false;
                }
                args[0].compile(compilation, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (!isStaticField) {
                    code.emitGetField(field2);
                } else if (0 == 0) {
                    code.emitGetStatic(field2);
                }
                target2.compileFromStack(compilation, language.getLangTypeFor(field2.getType()));
                return;
            } else if (part instanceof Method) {
                Method method = (Method) part;
                int modifiers = method.getModifiers();
                boolean isStaticMethod = method.getStaticFlag();
                args[0].compile(compilation, isStaticMethod ? Target.Ignore : Target.pushValue(ctype));
                if (isStaticMethod) {
                    code.emitInvokeStatic(method);
                } else {
                    code.emitInvoke(method);
                }
                target2.compileFromStack(compilation, method.getReturnType());
                return;
            }
        }
        String name = ClassMethods.checkName(arg1);
        if (!(type instanceof ArrayType) || !PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(name) || this.isStatic) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        args[0].compile(compilation, Target.pushValue(type));
        code.emitArrayLength();
        target2.compileFromStack(compilation, LangPrimType.intType);
    }

    public Type getReturnType(Expression[] args) {
        if (args.length == 2) {
            Expression arg0 = args[0];
            QuoteExp quoteExp = args[1];
            if (quoteExp instanceof QuoteExp) {
                Object part = quoteExp.getValue();
                if (part instanceof Field) {
                    return ((Field) part).getType();
                }
                if (part instanceof Method) {
                    return ((Method) part).getReturnType();
                }
                if (!this.isStatic && (arg0.getType() instanceof ArrayType) && PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(ClassMethods.checkName(quoteExp, true))) {
                    return LangPrimType.intType;
                }
            }
        }
        return Type.pointer_type;
    }

    public Procedure getSetter() {
        Procedure procedure = this.setter;
        return procedure == null ? super.getSetter() : procedure;
    }

    public static ApplyExp makeGetField(Expression value, String fieldName) {
        return new ApplyExp((Procedure) field, value, new QuoteExp(fieldName));
    }
}
