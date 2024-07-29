package gnu.mapping;

import gnu.text.Path;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class TtyInPort extends InPort {
    protected boolean promptEmitted;
    protected Procedure prompter;
    protected OutPort tie;

    public Procedure getPrompter() {
        return this.prompter;
    }

    public void setPrompter(Procedure prompter2) {
        this.prompter = prompter2;
    }

    public TtyInPort(InputStream in, Path name, OutPort tie2) {
        super(in, name);
        setConvertCR(true);
        this.tie = tie2;
    }

    public TtyInPort(Reader in, Path name, OutPort tie2) {
        super(in, name);
        setConvertCR(true);
        this.tie = tie2;
    }

    public int fill(int len) throws IOException {
        int count = this.in.read(this.buffer, this.pos, len);
        OutPort outPort = this.tie;
        if (outPort != null && count > 0) {
            outPort.echo(this.buffer, this.pos, count);
        }
        return count;
    }

    public void emitPrompt(String prompt) throws IOException {
        this.tie.print(prompt);
        this.tie.flush();
        this.tie.clearBuffer();
    }

    public void lineStart(boolean revisited) throws IOException {
        String string;
        if (!revisited) {
            OutPort outPort = this.tie;
            if (outPort != null) {
                outPort.freshLine();
            }
            Procedure procedure = this.prompter;
            if (procedure != null) {
                try {
                    Object prompt = procedure.apply1(this);
                    if (prompt != null && (string = prompt.toString()) != null && string.length() > 0) {
                        emitPrompt(string);
                        this.promptEmitted = true;
                    }
                } catch (Throwable ex) {
                    throw new IOException("Error when evaluating prompt:" + ex);
                }
            }
        }
    }

    public int read() throws IOException {
        OutPort outPort = this.tie;
        if (outPort != null) {
            outPort.flush();
        }
        int ch = super.read();
        if (ch < 0) {
            boolean z = this.promptEmitted;
            OutPort outPort2 = this.tie;
            if (z && (outPort2 != null)) {
                outPort2.println();
            }
        }
        this.promptEmitted = false;
        return ch;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        OutPort outPort = this.tie;
        if (outPort != null) {
            outPort.flush();
        }
        int count = super.read(cbuf, off, len);
        if (count < 0) {
            boolean z = this.promptEmitted;
            OutPort outPort2 = this.tie;
            if (z && (outPort2 != null)) {
                outPort2.println();
            }
        }
        this.promptEmitted = false;
        return count;
    }
}
