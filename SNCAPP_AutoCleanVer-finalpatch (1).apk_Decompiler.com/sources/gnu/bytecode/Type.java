package gnu.bytecode;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.kawa.util.AbstractWeakHashTable;
import java.io.PrintWriter;
import java.util.HashMap;

public abstract class Type implements java.lang.reflect.Type {
    public static final PrimType booleanType;
    public static final Method booleanValue_method;
    public static final ClassType boolean_ctype;
    public static final PrimType boolean_type;
    public static final PrimType byteType;
    public static final PrimType byte_type;
    public static final PrimType charType;
    public static final PrimType char_type;
    public static final Method clone_method;
    public static final PrimType doubleType;
    public static final Method doubleValue_method;
    public static final PrimType double_type;
    public static final ObjectType errorType = new ClassType("(error type)");
    public static final PrimType floatType;
    public static final Method floatValue_method;
    public static final PrimType float_type;
    public static final PrimType intType;
    public static final Method intValue_method;
    public static final PrimType int_type;
    public static final ClassType java_lang_Class_type;
    public static final ClassType javalangBooleanType;
    public static final ClassType javalangClassType;
    public static final ClassType javalangNumberType;
    public static final ClassType javalangObjectType;
    public static ClassType javalangStringType = ClassType.make("java.lang.String");
    public static final ClassType javalangThrowableType;
    public static final PrimType longType;
    public static final Method longValue_method;
    public static final PrimType long_type;
    static ClassToTypeMap mapClassToType;
    static HashMap<String, Type> mapNameToType;
    public static final PrimType neverReturnsType;
    public static final ObjectType nullType = new ObjectType("(type of null)");
    public static final ClassType number_type;
    public static final ClassType objectType;
    public static final ClassType pointer_type;
    public static final PrimType shortType;
    public static final PrimType short_type;
    public static final ClassType string_type = javalangStringType;
    public static final ClassType throwable_type;
    public static final ClassType toStringType;
    public static final Method toString_method;
    public static final ClassType tostring_type;
    public static final Type[] typeArray0;
    public static final PrimType voidType;
    public static final PrimType void_type;
    ArrayType array_type;
    protected Class reflectClass;
    String signature;
    int size;
    String this_name;

    public abstract Object coerceFromObject(Object obj);

    public abstract int compare(Type type);

    protected Type() {
    }

    public Type getImplementationType() {
        return this;
    }

    public Type getRealType() {
        return this;
    }

    public boolean isExisting() {
        return true;
    }

    public static Type lookupType(String name) {
        Type type;
        HashMap<String, Type> map = mapNameToType;
        synchronized (map) {
            type = map.get(name);
        }
        return type;
    }

    public static Type getType(String name) {
        Type type;
        HashMap<String, Type> map = mapNameToType;
        synchronized (map) {
            type = map.get(name);
            if (type == null) {
                if (name.endsWith("[]")) {
                    type = ArrayType.make(name);
                } else {
                    ClassType cl = new ClassType(name);
                    cl.flags |= 16;
                    type = cl;
                }
                map.put(name, type);
            }
        }
        return type;
    }

    public static synchronized void registerTypeForClass(Class clas, Type type) {
        synchronized (Type.class) {
            ClassToTypeMap map = mapClassToType;
            if (map == null) {
                ClassToTypeMap classToTypeMap = new ClassToTypeMap();
                map = classToTypeMap;
                mapClassToType = classToTypeMap;
            }
            type.reflectClass = clas;
            map.put(clas, type);
        }
    }

    public static synchronized Type make(Class cls) {
        Type type;
        Type type2;
        synchronized (Type.class) {
            ClassToTypeMap classToTypeMap = mapClassToType;
            if (classToTypeMap != null && (type2 = (Type) classToTypeMap.get(cls)) != null) {
                return type2;
            }
            if (cls.isArray()) {
                type = ArrayType.make(make(cls.getComponentType()));
            } else if (!cls.isPrimitive()) {
                String name = cls.getName();
                HashMap<String, Type> hashMap = mapNameToType;
                synchronized (hashMap) {
                    Type type3 = hashMap.get(name);
                    if (type3 != null) {
                        Class cls2 = type3.reflectClass;
                        if (cls2 == cls || cls2 == null) {
                            type = type3;
                        }
                    }
                    ClassType classType = new ClassType(name);
                    classType.flags |= 16;
                    mapNameToType.put(name, classType);
                    type = classType;
                }
            } else {
                throw new Error("internal error - primitive type not found");
            }
            registerTypeForClass(cls, type);
            return type;
        }
    }

    public String getSignature() {
        return this.signature;
    }

    /* access modifiers changed from: protected */
    public void setSignature(String sig) {
        this.signature = sig;
    }

    Type(String nam, String sig) {
        this.this_name = nam;
        this.signature = sig;
    }

    public Type(Type type) {
        this.this_name = type.this_name;
        this.signature = type.signature;
        this.size = type.size;
        this.reflectClass = type.reflectClass;
    }

    public Type promote() {
        return this.size < 4 ? intType : this;
    }

    public final int getSize() {
        return this.size;
    }

    public int getSizeInWords() {
        return this.size > 4 ? 2 : 1;
    }

    public final boolean isVoid() {
        return this.size == 0;
    }

    public static PrimType signatureToPrimitive(char sig) {
        switch (sig) {
            case 'B':
                return byteType;
            case 'C':
                return charType;
            case 'D':
                return doubleType;
            case 'F':
                return floatType;
            case 'I':
                return intType;
            case 'J':
                return longType;
            case 'S':
                return shortType;
            case 'V':
                return voidType;
            case 'Z':
                return booleanType;
            default:
                return null;
        }
    }

    public static Type signatureToType(String sig, int off, int len) {
        Type type;
        if (len == 0) {
            return null;
        }
        char c = sig.charAt(off);
        if (len == 1 && (type = signatureToPrimitive(c)) != null) {
            return type;
        }
        if (c == '[') {
            Type type2 = signatureToType(sig, off + 1, len - 1);
            if (type2 == null) {
                return null;
            }
            return ArrayType.make(type2);
        } else if (c == 'L' && len > 2 && sig.indexOf(59, off) == (len - 1) + off) {
            return ClassType.make(sig.substring(off + 1, (len - 1) + off).replace('/', '.'));
        } else {
            return null;
        }
    }

    public static Type signatureToType(String sig) {
        return signatureToType(sig, 0, sig.length());
    }

    public static void printSignature(String sig, int off, int len, PrintWriter out) {
        if (len != 0) {
            char c = sig.charAt(off);
            if (len == 1) {
                Type type = signatureToPrimitive(c);
                if (type != null) {
                    out.print(type.getName());
                }
            } else if (c == '[') {
                printSignature(sig, off + 1, len - 1, out);
                out.print("[]");
            } else if (c == 'L' && len > 2 && sig.indexOf(59, off) == (len - 1) + off) {
                out.print(sig.substring(off + 1, (len - 1) + off).replace('/', '.'));
            } else {
                out.append(sig, off, len - off);
            }
        }
    }

    public static int signatureLength(String sig, int pos) {
        int end;
        if (sig.length() <= pos) {
            return -1;
        }
        char c = sig.charAt(pos);
        int arrays = 0;
        while (c == '[') {
            arrays++;
            pos++;
            c = sig.charAt(pos);
        }
        if (signatureToPrimitive(c) != null) {
            return arrays + 1;
        }
        if (c != 'L' || (end = sig.indexOf(59, pos)) <= 0) {
            return -1;
        }
        return ((arrays + end) + 1) - pos;
    }

    public static int signatureLength(String sig) {
        return signatureLength(sig, 0);
    }

    public static String signatureToName(String sig) {
        Type type;
        int len = sig.length();
        if (len == 0) {
            return null;
        }
        char c = sig.charAt(0);
        if (len == 1 && (type = signatureToPrimitive(c)) != null) {
            return type.getName();
        }
        if (c == '[') {
            int arrays = 1;
            if (1 < len && sig.charAt(1) == '[') {
                arrays = 1 + 1;
            }
            String sig2 = signatureToName(sig.substring(arrays));
            if (sig2 == null) {
                return null;
            }
            StringBuffer buf = new StringBuffer(50);
            buf.append(sig2);
            while (true) {
                arrays--;
                if (arrays < 0) {
                    return buf.toString();
                }
                buf.append("[]");
            }
        } else if (c == 'L' && len > 2 && sig.indexOf(59) == len - 1) {
            return sig.substring(1, len - 1).replace('/', '.');
        } else {
            return null;
        }
    }

    public final String getName() {
        return this.this_name;
    }

    /* access modifiers changed from: protected */
    public void setName(String name) {
        this.this_name = name;
    }

    public static boolean isValidJavaTypeName(String name) {
        boolean in_name = false;
        int len = name.length();
        while (len > 2 && name.charAt(len - 1) == ']' && name.charAt(len - 2) == '[') {
            len -= 2;
        }
        int i = 0;
        while (i < len) {
            char ch = name.charAt(i);
            if (ch != '.') {
                if (in_name) {
                    if (!Character.isJavaIdentifierPart(ch)) {
                        return false;
                    }
                } else if (!Character.isJavaIdentifierStart(ch)) {
                    return false;
                }
                in_name = true;
            } else if (!in_name) {
                return false;
            } else {
                in_name = false;
            }
            i++;
        }
        if (i == len) {
            return true;
        }
        return false;
    }

    public boolean isInstance(Object obj) {
        return getReflectClass().isInstance(obj);
    }

    public final boolean isSubtype(Type other) {
        int comp = compare(other);
        return comp == -1 || comp == 0;
    }

    public static Type lowestCommonSuperType(Type t1, Type t2) {
        PrimType primType = neverReturnsType;
        if (t1 == primType) {
            return t2;
        }
        if (t2 == primType) {
            return t1;
        }
        if (t1 == null || t2 == null) {
            return null;
        }
        if (!(t1 instanceof PrimType) || !(t2 instanceof PrimType)) {
            if (t1.isSubtype(t2)) {
                return t2;
            }
            if (t2.isSubtype(t1)) {
                return t1;
            }
            if (!(t1 instanceof ClassType) || !(t2 instanceof ClassType)) {
                return objectType;
            }
            ClassType c1 = (ClassType) t1;
            ClassType c2 = (ClassType) t2;
            if (c1.isInterface() || c2.isInterface()) {
                return objectType;
            }
            return lowestCommonSuperType(c1.getSuperclass(), c2.getSuperclass());
        } else if (t1 == t2) {
            return t1;
        } else {
            Type t12 = ((PrimType) t1).promotedType();
            if (t12 == ((PrimType) t2).promotedType()) {
                return t12;
            }
            return null;
        }
    }

    protected static int swappedCompareResult(int code) {
        if (code == 1) {
            return -1;
        }
        if (code == -1) {
            return 1;
        }
        return code;
    }

    public static boolean isMoreSpecific(Type[] t1, Type[] t2) {
        if (t1.length != t2.length) {
            return false;
        }
        int i = t1.length;
        do {
            i--;
            if (i < 0) {
                return true;
            }
        } while (t1[i].isSubtype(t2[i]));
        return false;
    }

    public void emitIsInstance(CodeAttr code) {
        code.emitInstanceof(this);
    }

    public Object coerceToObject(Object obj) {
        return obj;
    }

    public void emitConvertFromPrimitive(Type stackType, CodeAttr code) {
        stackType.emitCoerceToObject(code);
    }

    public void emitCoerceToObject(CodeAttr code) {
    }

    public void emitCoerceFromObject(CodeAttr code) {
        throw new Error("unimplemented emitCoerceFromObject for " + this);
    }

    static {
        PrimType primType = new PrimType("byte", "B", 1, Byte.TYPE);
        byteType = primType;
        PrimType primType2 = new PrimType("short", "S", 2, Short.TYPE);
        shortType = primType2;
        PrimType primType3 = new PrimType("int", "I", 4, Integer.TYPE);
        intType = primType3;
        PrimType primType4 = new PrimType("long", "J", 8, Long.TYPE);
        longType = primType4;
        PrimType primType5 = new PrimType(PropertyTypeConstants.PROPERTY_TYPE_FLOAT, "F", 4, Float.TYPE);
        floatType = primType5;
        PrimType primType6 = new PrimType("double", "D", 8, Double.TYPE);
        doubleType = primType6;
        PrimType primType7 = new PrimType(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, "Z", 1, Boolean.TYPE);
        booleanType = primType7;
        Class cls = Character.TYPE;
        Object obj = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN;
        Object obj2 = PropertyTypeConstants.PROPERTY_TYPE_FLOAT;
        PrimType primType8 = new PrimType("char", "C", 2, cls);
        charType = primType8;
        PrimType primType9 = new PrimType("void", "V", 0, Void.TYPE);
        voidType = primType9;
        byte_type = primType;
        short_type = primType2;
        int_type = primType3;
        long_type = primType4;
        float_type = primType5;
        double_type = primType6;
        boolean_type = primType7;
        char_type = primType8;
        void_type = primType9;
        HashMap<String, Type> hashMap = new HashMap<>();
        mapNameToType = hashMap;
        hashMap.put("byte", primType);
        mapNameToType.put("short", primType2);
        mapNameToType.put("int", primType3);
        mapNameToType.put("long", primType4);
        mapNameToType.put(obj2, primType5);
        mapNameToType.put("double", primType6);
        mapNameToType.put(obj, primType7);
        mapNameToType.put("char", primType8);
        mapNameToType.put("void", primType9);
        PrimType primType10 = new PrimType(primType9);
        neverReturnsType = primType10;
        primType10.this_name = "(never-returns)";
        ClassType classType = new ClassType("java.lang.String");
        toStringType = classType;
        ClassType make = ClassType.make("java.lang.Object");
        javalangObjectType = make;
        objectType = make;
        ClassType make2 = ClassType.make("java.lang.Boolean");
        javalangBooleanType = make2;
        ClassType make3 = ClassType.make("java.lang.Throwable");
        javalangThrowableType = make3;
        Type[] typeArr = new Type[0];
        typeArray0 = typeArr;
        toString_method = make.getDeclaredMethod("toString", 0);
        ClassType make4 = ClassType.make("java.lang.Number");
        javalangNumberType = make4;
        clone_method = Method.makeCloneMethod(make);
        intValue_method = make4.addMethod("intValue", typeArr, (Type) primType3, 1);
        longValue_method = make4.addMethod("longValue", typeArr, (Type) primType4, 1);
        floatValue_method = make4.addMethod("floatValue", typeArr, (Type) primType5, 1);
        doubleValue_method = make4.addMethod("doubleValue", typeArr, (Type) primType6, 1);
        booleanValue_method = make2.addMethod("booleanValue", typeArr, (Type) primType7, 1);
        ClassType make5 = ClassType.make("java.lang.Class");
        javalangClassType = make5;
        pointer_type = make;
        tostring_type = classType;
        java_lang_Class_type = make5;
        boolean_ctype = make2;
        throwable_type = make3;
        number_type = make4;
    }

    public Class getReflectClass() {
        return this.reflectClass;
    }

    public void setReflectClass(Class rclass) {
        this.reflectClass = rclass;
    }

    public String toString() {
        return "Type " + getName();
    }

    public int hashCode() {
        String name = toString();
        if (name == null) {
            return 0;
        }
        return name.hashCode();
    }

    static class ClassToTypeMap extends AbstractWeakHashTable<Class, Type> {
        ClassToTypeMap() {
        }

        /* access modifiers changed from: protected */
        public Class getKeyFromValue(Type type) {
            return type.reflectClass;
        }

        /* access modifiers changed from: protected */
        public boolean matches(Class oldValue, Class newValue) {
            return oldValue == newValue;
        }
    }
}
