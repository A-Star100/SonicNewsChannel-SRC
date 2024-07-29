package gnu.bytecode;

public class SwitchState {
    Label after_label;
    Label cases_label;
    Label defaultLabel;
    Label[] labels;
    int maxValue;
    int minValue;
    int numCases = 0;
    TryState outerTry;
    Label switch_label;
    int[] values;

    public int getMaxValue() {
        return this.maxValue;
    }

    public int getNumCases() {
        return this.numCases;
    }

    public SwitchState(CodeAttr code) {
        this.switch_label = new Label(code);
        this.cases_label = new Label(code);
        this.after_label = new Label(code);
        this.outerTry = code.try_stack;
    }

    public void switchValuePushed(CodeAttr code) {
        code.popType();
        this.cases_label.setTypes(code);
        code.fixupChain(this.cases_label, this.switch_label);
    }

    public boolean addCase(int value, CodeAttr code) {
        Label label = new Label(code);
        label.setTypes(this.cases_label);
        label.define(code);
        return insertCase(value, label, code);
    }

    public boolean addCaseGoto(int value, CodeAttr code, Label label) {
        boolean ok = insertCase(value, label, code);
        label.setTypes(this.cases_label);
        code.setUnreachable();
        return ok;
    }

    public void addDefault(CodeAttr code) {
        Label label = new Label(code);
        label.setTypes(this.cases_label);
        label.define(code);
        if (this.defaultLabel == null) {
            this.defaultLabel = label;
            return;
        }
        throw new Error();
    }

    public boolean insertCase(int value, Label label, CodeAttr code) {
        int low;
        int[] iArr = this.values;
        if (iArr == null) {
            int[] iArr2 = new int[10];
            this.values = iArr2;
            Label[] labelArr = new Label[10];
            this.labels = labelArr;
            this.numCases = 1;
            this.maxValue = value;
            this.minValue = value;
            iArr2[0] = value;
            labelArr[0] = label;
            return true;
        }
        int[] old_values = this.values;
        Label[] old_labels = this.labels;
        int i = this.numCases;
        if (i >= iArr.length) {
            this.values = new int[(i * 2)];
            this.labels = new Label[(i * 2)];
        }
        if (value < this.minValue) {
            low = 0;
            this.minValue = value;
        } else if (value > this.maxValue) {
            low = this.numCases;
            this.maxValue = value;
        } else {
            int low2 = 0;
            int hi = i - 1;
            int copyBefore = 0;
            while (low2 <= hi) {
                copyBefore = (low2 + hi) >>> 1;
                if (old_values[copyBefore] >= value) {
                    hi = copyBefore - 1;
                } else {
                    copyBefore++;
                    low2 = copyBefore;
                }
            }
            if (value == old_values[copyBefore]) {
                return false;
            }
            low = copyBefore;
        }
        int copyAfter = this.numCases - low;
        System.arraycopy(old_values, low, this.values, low + 1, copyAfter);
        System.arraycopy(old_values, 0, this.values, 0, low);
        this.values[low] = value;
        System.arraycopy(old_labels, low, this.labels, low + 1, copyAfter);
        System.arraycopy(old_labels, 0, this.labels, 0, low);
        this.labels[low] = label;
        this.numCases++;
        return true;
    }

    public void exitSwitch(CodeAttr code) {
        if (this.outerTry == code.try_stack) {
            code.emitGoto(this.after_label);
            return;
        }
        throw new Error("exitSwitch cannot exit through a try");
    }

    public void finish(CodeAttr code) {
        int index;
        Label lab;
        if (this.defaultLabel == null) {
            Label label = new Label(code);
            this.defaultLabel = label;
            label.define(code);
            ClassType ex = ClassType.make("java.lang.RuntimeException");
            code.emitNew(ex);
            code.emitDup((Type) ex);
            code.emitPushString("bad case value!");
            code.emitInvokeSpecial(ex.addMethod("<init>", 1, new Type[]{Type.string_type}, (Type) Type.voidType));
            code.emitThrow();
        }
        code.fixupChain(this.switch_label, this.after_label);
        int i = this.numCases;
        if (i <= 1) {
            code.pushType(Type.intType);
            if (this.numCases == 1) {
                int i2 = this.minValue;
                if (i2 == 0) {
                    code.emitIfIntEqZero();
                } else {
                    code.emitPushInt(i2);
                    code.emitIfEq();
                }
                code.emitGoto(this.labels[0]);
                code.emitElse();
                code.emitGoto(this.defaultLabel);
                code.emitFi();
            } else {
                code.emitPop(1);
                code.emitGoto(this.defaultLabel);
            }
        } else {
            int i3 = i * 2;
            int i4 = this.maxValue;
            int i5 = this.minValue;
            if (i3 >= i4 - i5) {
                code.reserve((((i4 - i5) + 1) * 4) + 13);
                code.fixupAdd(2, (Label) null);
                code.put1(170);
                code.fixupAdd(3, this.defaultLabel);
                code.PC += 4;
                code.put4(this.minValue);
                code.put4(this.maxValue);
                int index2 = 0;
                int i6 = this.minValue;
                while (i6 <= this.maxValue) {
                    if (this.values[index2] == i6) {
                        index = index2 + 1;
                        lab = this.labels[index2];
                    } else {
                        index = index2;
                        lab = this.defaultLabel;
                    }
                    code.fixupAdd(3, lab);
                    code.PC += 4;
                    i6++;
                    index2 = index;
                }
            } else {
                code.reserve((i * 8) + 9);
                code.fixupAdd(2, (Label) null);
                code.put1(171);
                code.fixupAdd(3, this.defaultLabel);
                code.PC += 4;
                code.put4(this.numCases);
                for (int index3 = 0; index3 < this.numCases; index3++) {
                    code.put4(this.values[index3]);
                    code.fixupAdd(3, this.labels[index3]);
                    code.PC += 4;
                }
            }
        }
        code.fixupChain(this.after_label, this.cases_label);
    }
}
