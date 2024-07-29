package kawa;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TelnetInputStream extends FilterInputStream {
    static final int SB_IAC = 400;
    protected byte[] buf = new byte[512];
    Telnet connection;
    int count;
    int pos;
    int state = 0;
    int subCommandLength = 0;

    public TelnetInputStream(InputStream in, Telnet conn) throws IOException {
        super(in);
        this.connection = conn;
    }

    public int read() throws IOException {
        while (true) {
            if (this.pos >= this.count) {
                int avail = this.in.available();
                if (avail <= 0) {
                    avail = 1;
                } else {
                    byte[] bArr = this.buf;
                    int length = bArr.length;
                    int i = this.subCommandLength;
                    if (avail > length - i) {
                        avail = bArr.length - i;
                    }
                }
                int avail2 = this.in.read(this.buf, this.subCommandLength, avail);
                this.pos = this.subCommandLength;
                this.count = avail2;
                if (avail2 <= 0) {
                    return -1;
                }
            }
            byte[] bArr2 = this.buf;
            int i2 = this.pos;
            this.pos = i2 + 1;
            int ch = bArr2[i2] & 255;
            int i3 = this.state;
            if (i3 == 0) {
                if (ch != 255) {
                    return ch;
                }
                this.state = 255;
            } else if (i3 == 255) {
                if (ch == 255) {
                    this.state = 0;
                    return 255;
                } else if (ch == 251 || ch == 252 || ch == 253 || ch == 254 || ch == 250) {
                    this.state = ch;
                } else if (ch == 244) {
                    System.err.println("Interrupt Process");
                    this.state = 0;
                } else if (ch == 236) {
                    return -1;
                } else {
                    this.state = 0;
                }
            } else if (i3 == 251 || i3 == 252 || i3 == 253 || i3 == 254) {
                this.connection.handle(i3, ch);
                this.state = 0;
            } else if (i3 == 250) {
                if (ch == 255) {
                    this.state = SB_IAC;
                } else {
                    int i4 = this.subCommandLength;
                    this.subCommandLength = i4 + 1;
                    bArr2[i4] = (byte) ch;
                }
            } else if (i3 != SB_IAC) {
                System.err.println("Bad state " + this.state);
            } else if (ch == 255) {
                int i5 = this.subCommandLength;
                this.subCommandLength = i5 + 1;
                bArr2[i5] = (byte) ch;
                this.state = 250;
            } else if (ch == 240) {
                this.connection.subCommand(bArr2, 0, this.subCommandLength);
                this.state = 0;
                this.subCommandLength = 0;
            } else {
                this.state = 0;
                this.subCommandLength = 0;
            }
        }
    }

    public int read(byte[] b, int offset, int length) throws IOException {
        byte ch;
        if (length <= 0) {
            return 0;
        }
        int done = 0;
        if (this.state != 0 || this.pos >= this.count) {
            int ch2 = read();
            if (ch2 < 0) {
                return ch2;
            }
            b[offset] = (byte) ch2;
            done = 0 + 1;
            offset++;
        }
        if (this.state == 0) {
            while (true) {
                int i = this.pos;
                if (i >= this.count || done >= length || (ch = this.buf[i]) == -1) {
                    break;
                }
                b[offset] = ch;
                done++;
                this.pos = i + 1;
                offset++;
            }
        }
        return done;
    }
}
