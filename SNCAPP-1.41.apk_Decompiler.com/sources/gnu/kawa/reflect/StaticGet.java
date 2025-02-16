package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure0;

public class StaticGet extends Procedure0 implements Inlineable {
    ClassType ctype;
    Field field;
    String fname;
    java.lang.reflect.Field reflectField;

    StaticGet(Class clas, String fname2) {
        this.ctype = (ClassType) Type.make(clas);
        this.fname = fname2;
    }

    public StaticGet(ClassType ctype2, String name, Type ftype, int flags) {
        this.ctype = ctype2;
        this.fname = name;
        Field field2 = ctype2.getField(name);
        this.field = field2;
        if (field2 == null) {
            this.field = ctype2.addField(name, ftype, flags);
        }
    }

    public Object apply0() {
        if (this.reflectField == null) {
            Class clas = this.ctype.getReflectClass();
            try {
                this.reflectField = clas.getField(this.fname);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("no such field " + this.fname + " in " + clas.getName());
            }
        }
        try {
            return this.reflectField.get((Object) null);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("illegal access for field " + this.fname);
        }
    }

    private Field getField() {
        if (this.field == null) {
            Field field2 = this.ctype.getField(this.fname);
            this.field = field2;
            if (field2 == null) {
                this.field = this.ctype.addField(this.fname, Type.make(this.reflectField.getType()), this.reflectField.getModifiers());
            }
        }
        return this.field;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        getField();
        comp.getCode().emitGetStatic(this.field);
        target.compileFromStack(comp, this.field.getType());
    }

    public Type getReturnType(Expression[] args) {
        return getField().getType();
    }
}
