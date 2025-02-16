package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class StaticSet extends Procedure1 implements Inlineable {
    ClassType ctype;
    Field field;
    String fname;
    java.lang.reflect.Field reflectField;

    StaticSet(Class clas, String fname2) {
        this.ctype = (ClassType) Type.make(clas);
        this.fname = fname2;
    }

    public StaticSet(ClassType ctype2, String name, Type ftype, int flags) {
        this.ctype = ctype2;
        this.fname = name;
        Field field2 = ctype2.getField(name);
        this.field = field2;
        if (field2 == null) {
            this.field = ctype2.addField(name, ftype, flags);
        }
    }

    public Object apply1(Object arg1) {
        if (this.reflectField == null) {
            Class clas = this.ctype.getReflectClass();
            try {
                this.reflectField = clas.getField(this.fname);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("no such field " + this.fname + " in " + clas.getName());
            }
        }
        try {
            this.reflectField.set((Object) null, arg1);
            return Values.empty;
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("illegal access for field " + this.fname);
        }
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        if (this.field == null) {
            Field field2 = this.ctype.getField(this.fname);
            this.field = field2;
            if (field2 == null) {
                this.field = this.ctype.addField(this.fname, Type.make(this.reflectField.getType()), this.reflectField.getModifiers());
            }
        }
        exp.getArgs()[0].compile(comp, this.field.getType());
        comp.getCode().emitPutStatic(this.field);
        comp.compileConstant(Values.empty, target);
    }

    public Type getReturnType(Expression[] args) {
        return Type.voidType;
    }
}
