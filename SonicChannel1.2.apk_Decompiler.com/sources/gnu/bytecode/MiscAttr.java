package gnu.bytecode;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import java.io.DataOutputStream;
import java.io.IOException;

public class MiscAttr extends Attribute {
    byte[] data;
    int dataLength;
    int offset;

    public MiscAttr(String name, byte[] data2, int offset2, int length) {
        super(name);
        this.data = data2;
        this.offset = offset2;
        this.dataLength = length;
    }

    public MiscAttr(String name, byte[] data2) {
        this(name, data2, 0, data2.length);
    }

    public int getLength() {
        return this.dataLength;
    }

    /* access modifiers changed from: protected */
    public int u1(int offset2) {
        return this.data[offset2] & Ev3Constants.Opcode.TST;
    }

    /* access modifiers changed from: protected */
    public int u2(int offset2) {
        byte[] bArr = this.data;
        return ((bArr[offset2] & Ev3Constants.Opcode.TST) << 8) + (bArr[offset2 + 1] & Ev3Constants.Opcode.TST);
    }

    /* access modifiers changed from: protected */
    public int u1() {
        int i = this.offset;
        this.offset = i + 1;
        return u1(i);
    }

    /* access modifiers changed from: protected */
    public int u2() {
        int v = u2(this.offset);
        this.offset += 2;
        return v;
    }

    /* access modifiers changed from: protected */
    public void put1(int val) {
        byte[] bArr = this.data;
        if (bArr == null) {
            this.data = new byte[20];
        } else {
            int i = this.dataLength;
            if (i >= bArr.length) {
                byte[] tmp = new byte[(bArr.length * 2)];
                System.arraycopy(bArr, 0, tmp, 0, i);
                this.data = tmp;
            }
        }
        byte[] bArr2 = this.data;
        int i2 = this.dataLength;
        this.dataLength = i2 + 1;
        bArr2[i2] = (byte) val;
    }

    /* access modifiers changed from: protected */
    public void put2(int val) {
        put1((byte) (val >> 8));
        put1((byte) val);
    }

    /* access modifiers changed from: protected */
    public void put2(int offset2, int val) {
        byte[] bArr = this.data;
        bArr[offset2] = (byte) (val >> 8);
        bArr[offset2 + 1] = (byte) val;
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.write(this.data, this.offset, this.dataLength);
    }

    public void print(ClassTypeWriter dst) {
        super.print(dst);
        int len = getLength();
        int i = 0;
        while (i < len) {
            byte b = this.data[i];
            if (i % 20 == 0) {
                dst.print(' ');
            }
            dst.print(' ');
            dst.print(Character.forDigit((b >> 4) & 15, 16));
            dst.print(Character.forDigit(b & 15, 16));
            i++;
            if (i % 20 == 0 || i == len) {
                dst.println();
            }
        }
    }
}
