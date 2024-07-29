package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.mapping.OutPort;
import java.util.Hashtable;
import java.util.Vector;

public class ClassExp extends LambdaExp {
    public static final int CLASS_SPECIFIED = 65536;
    public static final int HAS_SUBCLASS = 131072;
    public static final int INTERFACE_SPECIFIED = 32768;
    public static final int IS_ABSTRACT = 16384;
    public String classNameSpecifier;
    public LambdaExp clinitMethod;
    boolean explicitInit;
    public LambdaExp initMethod;
    ClassType instanceType;
    boolean partsDeclared;
    boolean simple;
    public int superClassIndex = -1;
    public Expression[] supers;

    public boolean isSimple() {
        return this.simple;
    }

    public void setSimple(boolean value) {
        this.simple = value;
    }

    public final boolean isAbstract() {
        return getFlag(16384);
    }

    public boolean isMakingClassPair() {
        return this.type != this.instanceType;
    }

    public Type getType() {
        return this.simple ? Compilation.typeClass : Compilation.typeClassType;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public ClassExp() {
    }

    public ClassExp(boolean simple2) {
        this.simple = simple2;
        ClassType classType = new ClassType();
        this.type = classType;
        this.instanceType = classType;
    }

    /* access modifiers changed from: protected */
    public boolean mustCompile() {
        return true;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            compileMembers(comp);
            compilePushClass(comp, target);
        }
    }

    public void compilePushClass(Compilation comp, Target target) {
        int nargs;
        ClassType typeType;
        ClassType new_class = this.type;
        CodeAttr code = comp.getCode();
        comp.loadClassRef(new_class);
        boolean needsLink = getNeedsClosureEnv();
        if (!isSimple() || needsLink) {
            if (isMakingClassPair() || needsLink) {
                ClassType typeType2 = this.instanceType;
                if (new_class == typeType2) {
                    code.emitDup((Type) typeType2);
                } else {
                    comp.loadClassRef(typeType2);
                }
                typeType = ClassType.make("gnu.expr.PairClassType");
                nargs = needsLink ? 3 : 2;
            } else {
                typeType = ClassType.make("gnu.bytecode.Type");
                nargs = 1;
            }
            Type[] argsClass = new Type[nargs];
            if (needsLink) {
                getOwningLambda().loadHeapFrame(comp);
                nargs--;
                argsClass[nargs] = Type.pointer_type;
            }
            ClassType typeClass = ClassType.make("java.lang.Class");
            while (true) {
                nargs--;
                if (nargs >= 0) {
                    argsClass[nargs] = typeClass;
                } else {
                    code.emitInvokeStatic(typeType.addMethod("make", argsClass, (Type) typeType, 9));
                    target.compileFromStack(comp, typeType);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ClassType getCompiledClassType(Compilation comp) {
        return this.type;
    }

    public void setTypes(Compilation compilation) {
        String str;
        int length;
        int i;
        Expression[] expressionArr = this.supers;
        int i2 = 0;
        int length2 = expressionArr == null ? 0 : expressionArr.length;
        ClassType[] classTypeArr = new ClassType[length2];
        String str2 = null;
        ClassType classType = null;
        int i3 = 0;
        for (int i4 = 0; i4 < length2; i4++) {
            Type typeFor = Language.getDefaultLanguage().getTypeFor(this.supers[i4]);
            if (!(typeFor instanceof ClassType)) {
                compilation.setLine(this.supers[i4]);
                compilation.error('e', "invalid super type");
            } else {
                ClassType classType2 = (ClassType) typeFor;
                try {
                    i = classType2.getModifiers();
                } catch (RuntimeException e) {
                    if (compilation != null) {
                        compilation.error('e', "unknown super-type " + classType2.getName());
                    }
                    i = 0;
                }
                if ((i & 512) == 0) {
                    if (i3 < i4) {
                        compilation.error('e', "duplicate superclass for " + this);
                    }
                    this.superClassIndex = i4;
                    classType = classType2;
                } else {
                    classTypeArr[i3] = classType2;
                    i3++;
                }
            }
        }
        if (!(classType == null || (this.flags & 32768) == 0)) {
            compilation.error('e', "cannot be interface since has superclass");
        }
        if (!this.simple && classType == null && (this.flags & 65536) == 0 && (getFlag(131072) || (this.nameDecl != null && this.nameDecl.isPublic()))) {
            PairClassType pairClassType = new PairClassType();
            this.type = pairClassType;
            pairClassType.setInterface(true);
            pairClassType.instanceType = this.instanceType;
            ClassType[] classTypeArr2 = {this.type};
            this.instanceType.setSuper(Type.pointer_type);
            this.instanceType.setInterfaces(classTypeArr2);
        } else if (getFlag(32768)) {
            this.instanceType.setInterface(true);
        }
        ClassType classType3 = this.type;
        if (classType == null) {
            classType = Type.pointer_type;
        }
        classType3.setSuper(classType);
        if (i3 != length2) {
            ClassType[] classTypeArr3 = new ClassType[i3];
            System.arraycopy(classTypeArr, 0, classTypeArr3, 0, i3);
            classTypeArr = classTypeArr3;
        }
        this.type.setInterfaces(classTypeArr);
        if (this.type.getName() == null) {
            String str3 = this.classNameSpecifier;
            if (str3 == null && (str3 = getName()) != null && (length = str3.length()) > 2 && str3.charAt(0) == '<') {
                int i5 = length - 1;
                if (str3.charAt(i5) == '>') {
                    str3 = str3.substring(1, i5);
                }
            }
            if (str3 == null) {
                StringBuffer stringBuffer = new StringBuffer(100);
                compilation.getModule().classFor(compilation);
                stringBuffer.append(compilation.mainClass.getName());
                stringBuffer.append('$');
                int length3 = stringBuffer.length();
                while (true) {
                    stringBuffer.append(i2);
                    str = stringBuffer.toString();
                    if (compilation.findNamedClass(str) == null) {
                        break;
                    }
                    stringBuffer.setLength(length3);
                    i2++;
                }
            } else if (!isSimple() || (this instanceof ObjectExp)) {
                str = compilation.generateClassName(str3);
            } else {
                StringBuffer stringBuffer2 = new StringBuffer(100);
                int i6 = 0;
                while (true) {
                    int indexOf = str3.indexOf(46, i6);
                    if (indexOf < 0) {
                        break;
                    }
                    stringBuffer2.append(Compilation.mangleNameIfNeeded(str3.substring(i6, indexOf)));
                    i6 = indexOf + 1;
                    if (i6 < str3.length()) {
                        stringBuffer2.append('.');
                    }
                }
                if (i6 == 0) {
                    if (compilation.mainClass != null) {
                        str2 = compilation.mainClass.getName();
                    }
                    int lastIndexOf = str2 == null ? -1 : str2.lastIndexOf(46);
                    if (lastIndexOf > 0) {
                        stringBuffer2.append(str2.substring(0, lastIndexOf + 1));
                    } else if (compilation.classPrefix != null) {
                        stringBuffer2.append(compilation.classPrefix);
                    }
                } else if (i6 == 1 && i6 < str3.length()) {
                    stringBuffer2.setLength(0);
                    stringBuffer2.append(compilation.mainClass.getName());
                    stringBuffer2.append('$');
                }
                if (i6 < str3.length()) {
                    stringBuffer2.append(Compilation.mangleNameIfNeeded(str3.substring(i6)));
                }
                str = stringBuffer2.toString();
            }
            this.type.setName(str);
            compilation.addClass(this.type);
            if (isMakingClassPair()) {
                this.instanceType.setName(this.type.getName() + "$class");
                compilation.addClass(this.instanceType);
            }
        }
    }

    public void declareParts(Compilation comp) {
        if (!this.partsDeclared) {
            this.partsDeclared = true;
            Hashtable<String, Declaration> seenFields = new Hashtable<>();
            for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
                if (decl.getCanRead()) {
                    int flags = decl.getAccessFlags(1);
                    if (decl.getFlag(2048)) {
                        flags |= 8;
                    }
                    if (isMakingClassPair()) {
                        int flags2 = flags | 1024;
                        Type ftype = decl.getType().getImplementationType();
                        this.type.addMethod(slotToMethodName("get", decl.getName()), flags2, Type.typeArray0, ftype);
                        this.type.addMethod(slotToMethodName("set", decl.getName()), flags2, new Type[]{ftype}, (Type) Type.voidType);
                    } else {
                        String fname = Compilation.mangleNameIfNeeded(decl.getName());
                        decl.field = this.instanceType.addField(fname, decl.getType(), flags);
                        decl.setSimple(false);
                        Declaration old = seenFields.get(fname);
                        if (old != null) {
                            duplicateDeclarationError(old, decl, comp);
                        }
                        seenFields.put(fname, decl);
                    }
                }
            }
            for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
                if (child.isAbstract()) {
                    setFlag(16384);
                }
                if ("*init*".equals(child.getName())) {
                    this.explicitInit = true;
                    if (child.isAbstract()) {
                        comp.error('e', "*init* method cannot be abstract", child);
                    }
                    if (this.type instanceof PairClassType) {
                        comp.error('e', "'*init*' methods only supported for simple classes");
                    }
                }
                child.outer = this;
                if (!(child == this.initMethod || child == this.clinitMethod || child.nameDecl == null || child.nameDecl.getFlag(2048)) || !isMakingClassPair()) {
                    child.addMethodFor(this.type, comp, (ObjectType) null);
                }
                if (isMakingClassPair()) {
                    child.addMethodFor(this.instanceType, comp, this.type);
                }
            }
            if (!this.explicitInit && !this.instanceType.isInterface()) {
                Compilation.getConstructor(this.instanceType, this);
            }
            if (isAbstract()) {
                ClassType classType = this.instanceType;
                classType.setModifiers(classType.getModifiers() | 1024);
            }
            if (this.nameDecl != null) {
                ClassType classType2 = this.instanceType;
                classType2.setModifiers(this.nameDecl.getAccessFlags(1) | (classType2.getModifiers() & -2));
            }
        }
    }

    static void getImplMethods(ClassType interfaceType, String mname, Type[] paramTypes, Vector vec) {
        ClassType implType;
        if (interfaceType instanceof PairClassType) {
            implType = ((PairClassType) interfaceType).instanceType;
        } else if (interfaceType.isInterface()) {
            try {
                Class reflectClass = interfaceType.getReflectClass();
                if (reflectClass != null) {
                    implType = (ClassType) Type.make(Class.forName(interfaceType.getName() + "$class", false, reflectClass.getClassLoader()));
                } else {
                    return;
                }
            } catch (Throwable th) {
                return;
            }
        } else {
            return;
        }
        Type[] itypes = new Type[(paramTypes.length + 1)];
        itypes[0] = interfaceType;
        System.arraycopy(paramTypes, 0, itypes, 1, paramTypes.length);
        Method implMethod = implType.getDeclaredMethod(mname, itypes);
        if (implMethod != null) {
            int count = vec.size();
            if (count == 0 || !vec.elementAt(count - 1).equals(implMethod)) {
                vec.addElement(implMethod);
                return;
            }
            return;
        }
        ClassType[] superInterfaces = interfaceType.getInterfaces();
        for (ClassType implMethods : superInterfaces) {
            getImplMethods(implMethods, mname, paramTypes, vec);
        }
    }

    private static void usedSuperClasses(ClassType clas, Compilation comp) {
        comp.usedClass(clas.getSuperclass());
        ClassType[] interfaces = clas.getInterfaces();
        if (interfaces != null) {
            int i = interfaces.length;
            while (true) {
                i--;
                if (i >= 0) {
                    comp.usedClass(interfaces[i]);
                } else {
                    return;
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v0, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v1, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v24, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v2, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v3, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v26, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v4, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v5, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v35, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v6, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v17, resolved type: gnu.bytecode.ClassType} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v38, resolved type: gnu.bytecode.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v39, resolved type: gnu.bytecode.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v40, resolved type: gnu.bytecode.Method} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x016e A[Catch:{ all -> 0x0379 }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0186 A[Catch:{ all -> 0x0379 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0194 A[Catch:{ all -> 0x0379 }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01a5 A[Catch:{ all -> 0x0379 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01a9 A[Catch:{ all -> 0x0379 }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01af A[Catch:{ all -> 0x0379 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.bytecode.ClassType compileMembers(gnu.expr.Compilation r29) {
        /*
            r28 = this;
            r1 = r28
            r2 = r29
            gnu.bytecode.ClassType r3 = r2.curClass
            gnu.bytecode.Method r4 = r2.method
            gnu.bytecode.ClassType r0 = r28.getCompiledClassType(r29)     // Catch:{ all -> 0x0379 }
            r2.curClass = r0     // Catch:{ all -> 0x0379 }
            gnu.expr.LambdaExp r5 = r28.outerLambda()     // Catch:{ all -> 0x0379 }
            r6 = 0
            boolean r7 = r5 instanceof gnu.expr.ClassExp     // Catch:{ all -> 0x0379 }
            if (r7 == 0) goto L_0x001b
            gnu.bytecode.ClassType r7 = r5.type     // Catch:{ all -> 0x0379 }
            r6 = r7
            goto L_0x0038
        L_0x001b:
            if (r5 == 0) goto L_0x0023
            boolean r7 = r5 instanceof gnu.expr.ModuleExp     // Catch:{ all -> 0x0379 }
            if (r7 != 0) goto L_0x0023
            r6 = r4
            goto L_0x0038
        L_0x0023:
            boolean r7 = r5 instanceof gnu.expr.ModuleExp     // Catch:{ all -> 0x0379 }
            if (r7 == 0) goto L_0x0038
            gnu.bytecode.ClassType r7 = r1.type     // Catch:{ all -> 0x0379 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0379 }
            r8 = 36
            int r7 = r7.indexOf(r8)     // Catch:{ all -> 0x0379 }
            if (r7 <= 0) goto L_0x0038
            gnu.bytecode.ClassType r7 = r5.type     // Catch:{ all -> 0x0379 }
            r6 = r7
        L_0x0038:
            if (r6 == 0) goto L_0x0047
            r0.setEnclosingMember(r6)     // Catch:{ all -> 0x0379 }
            boolean r7 = r6 instanceof gnu.bytecode.ClassType     // Catch:{ all -> 0x0379 }
            if (r7 == 0) goto L_0x0047
            r7 = r6
            gnu.bytecode.ClassType r7 = (gnu.bytecode.ClassType) r7     // Catch:{ all -> 0x0379 }
            r7.addMemberClass(r0)     // Catch:{ all -> 0x0379 }
        L_0x0047:
            gnu.bytecode.ClassType r7 = r1.instanceType     // Catch:{ all -> 0x0379 }
            if (r7 == r0) goto L_0x0057
            gnu.bytecode.ClassType r8 = r1.type     // Catch:{ all -> 0x0379 }
            r7.setEnclosingMember(r8)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r7 = r1.type     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r8 = r1.instanceType     // Catch:{ all -> 0x0379 }
            r7.addMemberClass(r8)     // Catch:{ all -> 0x0379 }
        L_0x0057:
            gnu.bytecode.ClassType r7 = r1.type     // Catch:{ all -> 0x0379 }
            usedSuperClasses(r7, r2)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r7 = r1.type     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r8 = r1.instanceType     // Catch:{ all -> 0x0379 }
            if (r7 == r8) goto L_0x0065
            usedSuperClasses(r8, r2)     // Catch:{ all -> 0x0379 }
        L_0x0065:
            java.lang.String r7 = r28.getFileName()     // Catch:{ all -> 0x0379 }
            if (r7 == 0) goto L_0x006e
            r0.setSourceFile(r7)     // Catch:{ all -> 0x0379 }
        L_0x006e:
            gnu.expr.LambdaExp r8 = r2.curLambda     // Catch:{ all -> 0x0379 }
            r2.curLambda = r1     // Catch:{ all -> 0x0379 }
            r28.allocFrame(r29)     // Catch:{ all -> 0x0379 }
            gnu.expr.LambdaExp r9 = r1.firstChild     // Catch:{ all -> 0x0379 }
        L_0x0077:
            if (r9 == 0) goto L_0x01dd
            boolean r13 = r9.isAbstract()     // Catch:{ all -> 0x0379 }
            if (r13 == 0) goto L_0x0087
            r21 = r5
            r22 = r6
            r24 = r7
            goto L_0x01d2
        L_0x0087:
            gnu.bytecode.Method r13 = r2.method     // Catch:{ all -> 0x0379 }
            gnu.expr.LambdaExp r14 = r2.curLambda     // Catch:{ all -> 0x0379 }
            java.lang.String r15 = r29.getFileName()     // Catch:{ all -> 0x0379 }
            int r16 = r29.getLineNumber()     // Catch:{ all -> 0x0379 }
            r17 = r16
            int r16 = r29.getColumnNumber()     // Catch:{ all -> 0x0379 }
            r18 = r16
            r2.setLine((gnu.expr.Expression) r9)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Method r10 = r9.getMainMethod()     // Catch:{ all -> 0x0379 }
            r2.method = r10     // Catch:{ all -> 0x0379 }
            gnu.expr.Declaration r10 = r9.nameDecl     // Catch:{ all -> 0x0379 }
            if (r10 == 0) goto L_0x00b0
            r11 = 2048(0x800, double:1.0118E-320)
            boolean r11 = r10.getFlag(r11)     // Catch:{ all -> 0x0379 }
            if (r11 != 0) goto L_0x00b5
        L_0x00b0:
            gnu.bytecode.ClassType r11 = r2.curClass     // Catch:{ all -> 0x0379 }
            r9.declareThis(r11)     // Catch:{ all -> 0x0379 }
        L_0x00b5:
            gnu.bytecode.ClassType r11 = r1.instanceType     // Catch:{ all -> 0x0379 }
            r2.curClass = r11     // Catch:{ all -> 0x0379 }
            r2.curLambda = r9     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Method r11 = r2.method     // Catch:{ all -> 0x0379 }
            r11.initCode()     // Catch:{ all -> 0x0379 }
            r9.allocChildClasses(r2)     // Catch:{ all -> 0x0379 }
            r9.allocParameters(r2)     // Catch:{ all -> 0x0379 }
            java.lang.String r11 = "*init*"
            java.lang.String r12 = r9.getName()     // Catch:{ all -> 0x0379 }
            boolean r11 = r11.equals(r12)     // Catch:{ all -> 0x0379 }
            if (r11 == 0) goto L_0x01b3
            gnu.bytecode.CodeAttr r11 = r29.getCode()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Field r12 = r1.staticLinkField     // Catch:{ all -> 0x0379 }
            if (r12 == 0) goto L_0x00f1
            r11.emitPushThis()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Scope r12 = r11.getCurrentScope()     // Catch:{ all -> 0x0379 }
            r21 = r5
            r5 = 1
            gnu.bytecode.Variable r5 = r12.getVariable(r5)     // Catch:{ all -> 0x0379 }
            r11.emitLoad(r5)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Field r5 = r1.staticLinkField     // Catch:{ all -> 0x0379 }
            r11.emitPutField(r5)     // Catch:{ all -> 0x0379 }
            goto L_0x00f3
        L_0x00f1:
            r21 = r5
        L_0x00f3:
            gnu.expr.Expression r5 = r9.body     // Catch:{ all -> 0x0379 }
        L_0x00f5:
            boolean r12 = r5 instanceof gnu.expr.BeginExp     // Catch:{ all -> 0x0379 }
            if (r12 == 0) goto L_0x010e
            r12 = r5
            gnu.expr.BeginExp r12 = (gnu.expr.BeginExp) r12     // Catch:{ all -> 0x0379 }
            r22 = r6
            int r6 = r12.length     // Catch:{ all -> 0x0379 }
            if (r6 != 0) goto L_0x0104
            r5 = 0
            goto L_0x010b
        L_0x0104:
            gnu.expr.Expression[] r6 = r12.exps     // Catch:{ all -> 0x0379 }
            r19 = 0
            r6 = r6[r19]     // Catch:{ all -> 0x0379 }
            r5 = r6
        L_0x010b:
            r6 = r22
            goto L_0x00f5
        L_0x010e:
            r22 = r6
            r6 = 0
            boolean r12 = r5 instanceof gnu.expr.ApplyExp     // Catch:{ all -> 0x0379 }
            if (r12 == 0) goto L_0x0160
            r12 = r5
            gnu.expr.ApplyExp r12 = (gnu.expr.ApplyExp) r12     // Catch:{ all -> 0x0379 }
            gnu.expr.Expression r12 = r12.func     // Catch:{ all -> 0x0379 }
            r19 = r12
            boolean r12 = r12 instanceof gnu.expr.QuoteExp     // Catch:{ all -> 0x0379 }
            if (r12 == 0) goto L_0x015b
            r12 = r19
            gnu.expr.QuoteExp r12 = (gnu.expr.QuoteExp) r12     // Catch:{ all -> 0x0379 }
            java.lang.Object r12 = r12.getValue()     // Catch:{ all -> 0x0379 }
            r20 = r12
            boolean r12 = r12 instanceof gnu.expr.PrimProcedure     // Catch:{ all -> 0x0379 }
            if (r12 == 0) goto L_0x0156
            r12 = r20
            gnu.expr.PrimProcedure r12 = (gnu.expr.PrimProcedure) r12     // Catch:{ all -> 0x0379 }
            boolean r23 = r12.isSpecial()     // Catch:{ all -> 0x0379 }
            if (r23 == 0) goto L_0x0151
            r23 = r6
            java.lang.String r6 = "<init>"
            r24 = r7
            gnu.bytecode.Method r7 = r12.method     // Catch:{ all -> 0x0379 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0379 }
            boolean r6 = r6.equals(r7)     // Catch:{ all -> 0x0379 }
            if (r6 == 0) goto L_0x0164
            gnu.bytecode.Method r6 = r12.method     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r6 = r6.getDeclaringClass()     // Catch:{ all -> 0x0379 }
            goto L_0x0166
        L_0x0151:
            r23 = r6
            r24 = r7
            goto L_0x0164
        L_0x0156:
            r23 = r6
            r24 = r7
            goto L_0x0164
        L_0x015b:
            r23 = r6
            r24 = r7
            goto L_0x0164
        L_0x0160:
            r23 = r6
            r24 = r7
        L_0x0164:
            r6 = r23
        L_0x0166:
            gnu.bytecode.ClassType r7 = r1.instanceType     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r7 = r7.getSuperclass()     // Catch:{ all -> 0x0379 }
            if (r6 == 0) goto L_0x0186
            gnu.expr.Target r12 = gnu.expr.Target.Ignore     // Catch:{ all -> 0x0379 }
            r5.compileWithPosition(r2, r12)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r12 = r1.instanceType     // Catch:{ all -> 0x0379 }
            if (r6 == r12) goto L_0x0183
            if (r6 == r7) goto L_0x0183
            java.lang.String r12 = "call to <init> for not this or super class"
            r20 = r5
            r5 = 101(0x65, float:1.42E-43)
            r2.error(r5, r12)     // Catch:{ all -> 0x0379 }
            goto L_0x018d
        L_0x0183:
            r20 = r5
            goto L_0x018d
        L_0x0186:
            r20 = r5
            if (r7 == 0) goto L_0x018d
            invokeDefaultSuperConstructor(r7, r2, r1)     // Catch:{ all -> 0x0379 }
        L_0x018d:
            r9.enterFunction(r2)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r5 = r1.instanceType     // Catch:{ all -> 0x0379 }
            if (r6 == r5) goto L_0x01a5
            gnu.bytecode.ClassType r5 = r28.getCompiledClassType(r29)     // Catch:{ all -> 0x0379 }
            java.util.Vector r12 = new java.util.Vector     // Catch:{ all -> 0x0379 }
            r16 = r7
            r7 = 10
            r12.<init>(r7)     // Catch:{ all -> 0x0379 }
            r2.callInitMethods(r5, r12)     // Catch:{ all -> 0x0379 }
            goto L_0x01a7
        L_0x01a5:
            r16 = r7
        L_0x01a7:
            if (r6 == 0) goto L_0x01af
            gnu.expr.Expression r5 = r9.body     // Catch:{ all -> 0x0379 }
            gnu.expr.Expression.compileButFirst(r5, r2)     // Catch:{ all -> 0x0379 }
            goto L_0x01b2
        L_0x01af:
            r9.compileBody(r2)     // Catch:{ all -> 0x0379 }
        L_0x01b2:
            goto L_0x01bf
        L_0x01b3:
            r21 = r5
            r22 = r6
            r24 = r7
            r9.enterFunction(r2)     // Catch:{ all -> 0x0379 }
            r9.compileBody(r2)     // Catch:{ all -> 0x0379 }
        L_0x01bf:
            r9.compileEnd(r2)     // Catch:{ all -> 0x0379 }
            r9.generateApplyMethods(r2)     // Catch:{ all -> 0x0379 }
            r2.method = r13     // Catch:{ all -> 0x0379 }
            r2.curClass = r0     // Catch:{ all -> 0x0379 }
            r2.curLambda = r14     // Catch:{ all -> 0x0379 }
            r5 = r17
            r6 = r18
            r2.setLine(r15, r5, r6)     // Catch:{ all -> 0x0379 }
        L_0x01d2:
            gnu.expr.LambdaExp r5 = r9.nextSibling     // Catch:{ all -> 0x0379 }
            r9 = r5
            r5 = r21
            r6 = r22
            r7 = r24
            goto L_0x0077
        L_0x01dd:
            r21 = r5
            r22 = r6
            r24 = r7
            boolean r5 = r1.explicitInit     // Catch:{ all -> 0x0379 }
            if (r5 != 0) goto L_0x01f5
            gnu.bytecode.ClassType r5 = r1.instanceType     // Catch:{ all -> 0x0379 }
            boolean r5 = r5.isInterface()     // Catch:{ all -> 0x0379 }
            if (r5 != 0) goto L_0x01f5
            gnu.bytecode.ClassType r5 = r1.instanceType     // Catch:{ all -> 0x0379 }
            r2.generateConstructor(r5, r1)     // Catch:{ all -> 0x0379 }
            goto L_0x0200
        L_0x01f5:
            gnu.expr.Initializer r5 = r1.initChain     // Catch:{ all -> 0x0379 }
            if (r5 == 0) goto L_0x0200
            gnu.expr.Initializer r5 = r1.initChain     // Catch:{ all -> 0x0379 }
            java.lang.String r6 = "unimplemented: explicit constructor cannot initialize "
            r5.reportError(r6, r2)     // Catch:{ all -> 0x0379 }
        L_0x0200:
            boolean r5 = r28.isAbstract()     // Catch:{ all -> 0x0379 }
            if (r5 == 0) goto L_0x0209
            r5 = 0
            r6 = 0
            goto L_0x0210
        L_0x0209:
            gnu.bytecode.ClassType r5 = r1.type     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Method[] r5 = r5.getAbstractMethods()     // Catch:{ all -> 0x0379 }
            int r6 = r5.length     // Catch:{ all -> 0x0379 }
        L_0x0210:
            r7 = 0
        L_0x0211:
            if (r7 >= r6) goto L_0x036a
            r9 = r5[r7]     // Catch:{ all -> 0x0379 }
            java.lang.String r10 = r9.getName()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Type[] r11 = r9.getParameterTypes()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Type r12 = r9.getReturnType()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r13 = r1.instanceType     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Method r13 = r13.getMethod(r10, r11)     // Catch:{ all -> 0x0379 }
            if (r13 == 0) goto L_0x0237
            boolean r14 = r13.isAbstract()     // Catch:{ all -> 0x0379 }
            if (r14 != 0) goto L_0x0237
            r18 = r5
            r25 = r6
            r14 = 101(0x65, float:1.42E-43)
            goto L_0x0362
        L_0x0237:
            int r14 = r10.length()     // Catch:{ all -> 0x0379 }
            r15 = 3
            if (r14 <= r15) goto L_0x02f5
            r14 = 2
            char r14 = r10.charAt(r14)     // Catch:{ all -> 0x0379 }
            r15 = 116(0x74, float:1.63E-43)
            if (r14 != r15) goto L_0x02f5
            r14 = 1
            char r15 = r10.charAt(r14)     // Catch:{ all -> 0x0379 }
            r14 = 101(0x65, float:1.42E-43)
            if (r15 != r14) goto L_0x02f5
            r14 = 0
            char r15 = r10.charAt(r14)     // Catch:{ all -> 0x0379 }
            r14 = r15
            r18 = r5
            r5 = 103(0x67, float:1.44E-43)
            if (r15 == r5) goto L_0x0267
            r15 = 115(0x73, float:1.61E-43)
            if (r14 != r15) goto L_0x0261
            goto L_0x0269
        L_0x0261:
            r25 = r6
            r17 = r13
            goto L_0x02fb
        L_0x0267:
            r15 = 115(0x73, float:1.61E-43)
        L_0x0269:
            if (r14 != r15) goto L_0x027a
            boolean r15 = r12.isVoid()     // Catch:{ all -> 0x0379 }
            if (r15 == 0) goto L_0x027a
            int r15 = r11.length     // Catch:{ all -> 0x0379 }
            r5 = 1
            if (r15 != r5) goto L_0x027a
            r5 = 0
            r15 = r11[r5]     // Catch:{ all -> 0x0379 }
            r5 = r15
            goto L_0x0282
        L_0x027a:
            r5 = 103(0x67, float:1.44E-43)
            if (r14 != r5) goto L_0x02ed
            int r5 = r11.length     // Catch:{ all -> 0x0379 }
            if (r5 != 0) goto L_0x02ed
            r5 = r12
        L_0x0282:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x0379 }
            r15.<init>()     // Catch:{ all -> 0x0379 }
            r25 = r6
            r6 = 3
            char r6 = r10.charAt(r6)     // Catch:{ all -> 0x0379 }
            char r6 = java.lang.Character.toLowerCase(r6)     // Catch:{ all -> 0x0379 }
            java.lang.StringBuilder r6 = r15.append(r6)     // Catch:{ all -> 0x0379 }
            r15 = 4
            java.lang.String r15 = r10.substring(r15)     // Catch:{ all -> 0x0379 }
            java.lang.StringBuilder r6 = r6.append(r15)     // Catch:{ all -> 0x0379 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r15 = r1.instanceType     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Field r15 = r15.getField(r6)     // Catch:{ all -> 0x0379 }
            if (r15 != 0) goto L_0x02b8
            r17 = r13
            gnu.bytecode.ClassType r13 = r1.instanceType     // Catch:{ all -> 0x0379 }
            r26 = r15
            r15 = 1
            gnu.bytecode.Field r13 = r13.addField(r6, r5, r15)     // Catch:{ all -> 0x0379 }
            r15 = r13
            goto L_0x02bc
        L_0x02b8:
            r17 = r13
            r26 = r15
        L_0x02bc:
            gnu.bytecode.ClassType r13 = r1.instanceType     // Catch:{ all -> 0x0379 }
            r26 = r5
            r5 = 1
            gnu.bytecode.Method r13 = r13.addMethod((java.lang.String) r10, (int) r5, (gnu.bytecode.Type[]) r11, (gnu.bytecode.Type) r12)     // Catch:{ all -> 0x0379 }
            r5 = r13
            gnu.bytecode.CodeAttr r13 = r5.startCode()     // Catch:{ all -> 0x0379 }
            r13.emitPushThis()     // Catch:{ all -> 0x0379 }
            r27 = r5
            r5 = 103(0x67, float:1.44E-43)
            if (r14 != r5) goto L_0x02d9
            r13.emitGetField(r15)     // Catch:{ all -> 0x0379 }
            r23 = r6
            goto L_0x02e6
        L_0x02d9:
            r23 = r6
            r5 = 1
            gnu.bytecode.Variable r6 = r13.getArg(r5)     // Catch:{ all -> 0x0379 }
            r13.emitLoad(r6)     // Catch:{ all -> 0x0379 }
            r13.emitPutField(r15)     // Catch:{ all -> 0x0379 }
        L_0x02e6:
            r13.emitReturn()     // Catch:{ all -> 0x0379 }
            r14 = 101(0x65, float:1.42E-43)
            goto L_0x0362
        L_0x02ed:
            r25 = r6
            r17 = r13
            r14 = 101(0x65, float:1.42E-43)
            goto L_0x0362
        L_0x02f5:
            r18 = r5
            r25 = r6
            r17 = r13
        L_0x02fb:
            java.util.Vector r5 = new java.util.Vector     // Catch:{ all -> 0x0379 }
            r5.<init>()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.ClassType r6 = r1.type     // Catch:{ all -> 0x0379 }
            getImplMethods(r6, r10, r11, r5)     // Catch:{ all -> 0x0379 }
            int r6 = r5.size()     // Catch:{ all -> 0x0379 }
            r13 = 1
            if (r6 == r13) goto L_0x032e
            int r6 = r5.size()     // Catch:{ all -> 0x0379 }
            if (r6 != 0) goto L_0x0315
            java.lang.String r6 = "missing implementation for "
            goto L_0x0317
        L_0x0315:
            java.lang.String r6 = "ambiguous implementation for "
        L_0x0317:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0379 }
            r13.<init>()     // Catch:{ all -> 0x0379 }
            java.lang.StringBuilder r13 = r13.append(r6)     // Catch:{ all -> 0x0379 }
            java.lang.StringBuilder r13 = r13.append(r9)     // Catch:{ all -> 0x0379 }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0379 }
            r14 = 101(0x65, float:1.42E-43)
            r2.error(r14, r13)     // Catch:{ all -> 0x0379 }
            goto L_0x0362
        L_0x032e:
            r14 = 101(0x65, float:1.42E-43)
            gnu.bytecode.ClassType r6 = r1.instanceType     // Catch:{ all -> 0x0379 }
            r13 = 1
            gnu.bytecode.Method r6 = r6.addMethod((java.lang.String) r10, (int) r13, (gnu.bytecode.Type[]) r11, (gnu.bytecode.Type) r12)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.CodeAttr r15 = r6.startCode()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Scope r16 = r15.getCurrentScope()     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Variable r16 = r16.firstVar()     // Catch:{ all -> 0x0379 }
            r13 = r16
        L_0x0345:
            if (r13 == 0) goto L_0x0351
            r15.emitLoad(r13)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Variable r16 = r13.nextVar()     // Catch:{ all -> 0x0379 }
            r13 = r16
            goto L_0x0345
        L_0x0351:
            r13 = 0
            java.lang.Object r16 = r5.elementAt(r13)     // Catch:{ all -> 0x0379 }
            gnu.bytecode.Method r16 = (gnu.bytecode.Method) r16     // Catch:{ all -> 0x0379 }
            r19 = r16
            r13 = r19
            r15.emitInvokeStatic(r13)     // Catch:{ all -> 0x0379 }
            r15.emitReturn()     // Catch:{ all -> 0x0379 }
        L_0x0362:
            int r7 = r7 + 1
            r5 = r18
            r6 = r25
            goto L_0x0211
        L_0x036a:
            r18 = r5
            r25 = r6
            r28.generateApplyMethods(r29)     // Catch:{ all -> 0x0379 }
            r2.curLambda = r8     // Catch:{ all -> 0x0379 }
            r2.curClass = r3
            r2.method = r4
            return r0
        L_0x0379:
            r0 = move-exception
            r2.curClass = r3
            r2.method = r4
            goto L_0x0380
        L_0x037f:
            throw r0
        L_0x0380:
            goto L_0x037f
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ClassExp.compileMembers(gnu.expr.Compilation):gnu.bytecode.ClassType");
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            return visitor.visitClassExp(this, d);
        }
        ClassType saveClass = comp.curClass;
        try {
            comp.curClass = this.type;
            R visitClassExp = visitor.visitClassExp(this, d);
            comp.curClass = saveClass;
            return visitClassExp;
        } catch (Throwable th) {
            comp.curClass = saveClass;
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        Declaration firstParam;
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        Expression[] expressionArr = this.supers;
        this.supers = visitor.visitExps(expressionArr, expressionArr.length, d);
        try {
            for (LambdaExp child = this.firstChild; child != null && visitor.exitValue == null; child = child.nextSibling) {
                if (!(this.instanceType == null || (firstParam = child.firstDecl()) == null || !firstParam.isThisParameter())) {
                    firstParam.setType(this.type);
                }
                visitor.visitLambdaExp(child, d);
            }
        } finally {
            visitor.currentLambda = save;
        }
    }

    static void loadSuperStaticLink(Expression superExp, ClassType superClass, Compilation comp) {
        CodeAttr code = comp.getCode();
        superExp.compile(comp, Target.pushValue(Compilation.typeClassType));
        code.emitInvokeStatic(ClassType.make("gnu.expr.PairClassType").getDeclaredMethod("extractStaticLink", 1));
        code.emitCheckcast(superClass.getOuterLinkType());
    }

    static void invokeDefaultSuperConstructor(ClassType superClass, Compilation comp, LambdaExp lexp) {
        CodeAttr code = comp.getCode();
        Method superConstructor = superClass.getDeclaredMethod("<init>", 0);
        if (superConstructor == null) {
            comp.error('e', "super class does not have a default constructor");
            return;
        }
        code.emitPushThis();
        if (superClass.hasOuterLink() && (lexp instanceof ClassExp)) {
            ClassExp clExp = (ClassExp) lexp;
            loadSuperStaticLink(clExp.supers[clExp.superClassIndex], superClass, comp);
        }
        code.emitInvokeSpecial(superConstructor);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(" + getExpClassName() + "/", ")", 2);
        Object name = getSymbol();
        if (name != null) {
            out.print(name);
            out.print('/');
        }
        out.print(this.id);
        out.print("/fl:");
        out.print(Integer.toHexString(this.flags));
        if (this.supers.length > 0) {
            out.writeSpaceFill();
            out.startLogicalBlock("supers:", "", 2);
            int i = 0;
            while (true) {
                Expression[] expressionArr = this.supers;
                if (i >= expressionArr.length) {
                    break;
                }
                expressionArr[i].print(out);
                out.writeSpaceFill();
                i++;
            }
            out.endLogicalBlock("");
        }
        out.print('(');
        int i2 = 0;
        if (this.keywords != null) {
            int length = this.keywords.length;
        }
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (i2 > 0) {
                out.print(' ');
            }
            decl.printInfo(out);
            i2++;
        }
        out.print(") ");
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            out.writeBreakLinear();
            child.print(out);
        }
        if (this.body != null) {
            out.writeBreakLinear();
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Field compileSetField(Compilation comp) {
        return new ClassInitializer(this, comp).field;
    }

    public static String slotToMethodName(String prefix, String sname) {
        if (!Compilation.isValidJavaName(sname)) {
            sname = Compilation.mangleName(sname, false);
        }
        int slen = sname.length();
        StringBuffer sbuf = new StringBuffer(slen + 3);
        sbuf.append(prefix);
        if (slen > 0) {
            sbuf.append(Character.toTitleCase(sname.charAt(0)));
            sbuf.append(sname.substring(1));
        }
        return sbuf.toString();
    }

    public Declaration addMethod(LambdaExp lexp, Object mname) {
        Declaration mdecl = addDeclaration(mname, Compilation.typeProcedure);
        lexp.outer = this;
        lexp.setClassMethod(true);
        mdecl.noteValue(lexp);
        mdecl.setFlag(1048576);
        mdecl.setProcedureDecl(true);
        lexp.setSymbol(mname);
        return mdecl;
    }
}
