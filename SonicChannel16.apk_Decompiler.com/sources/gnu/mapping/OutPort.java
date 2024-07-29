package gnu.mapping;

import gnu.lists.AbstractFormat;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.PrintConsumer;
import gnu.text.Path;
import gnu.text.PrettyWriter;
import gnu.text.Printable;
import gnu.text.WriterManager;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.NumberFormat;

public class OutPort extends PrintConsumer implements Printable {
    private static OutPort errInitial = new OutPort(new LogWriter(new OutputStreamWriter(System.err)), true, true, Path.valueOf("/dev/stderr"));
    public static final ThreadLocation errLocation;
    static Writer logFile;
    static OutPort outInitial = new OutPort(new LogWriter(new BufferedWriter(new OutputStreamWriter(System.out))), true, true, Path.valueOf("/dev/stdout"));
    public static final ThreadLocation outLocation;
    private Writer base;
    protected PrettyWriter bout;
    NumberFormat numberFormat;
    public AbstractFormat objectFormat;
    Path path;
    public boolean printReadable;
    protected Object unregisterRef;

    protected OutPort(Writer base2, PrettyWriter out, boolean autoflush) {
        super((Writer) out, autoflush);
        this.bout = out;
        this.base = base2;
        if (closeOnExit()) {
            this.unregisterRef = WriterManager.instance.register(out);
        }
    }

    protected OutPort(OutPort out, boolean autoflush) {
        this((Writer) out, out.bout, autoflush);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    protected OutPort(Writer out, boolean autoflush) {
        this(out, out instanceof OutPort ? ((OutPort) out).bout : new PrettyWriter(out, true), autoflush);
    }

    public OutPort(Writer base2, boolean printPretty, boolean autoflush) {
        this(base2, new PrettyWriter(base2, printPretty), autoflush);
    }

    public OutPort(Writer base2, boolean printPretty, boolean autoflush, Path path2) {
        this(base2, new PrettyWriter(base2, printPretty), autoflush);
        this.path = path2;
    }

    public OutPort(OutputStream out) {
        this(out, (Path) null);
    }

    public OutPort(OutputStream out, Path path2) {
        this((Writer) new OutputStreamWriter(out), true, path2);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public OutPort(Writer out) {
        this(out, out instanceof OutPort ? ((OutPort) out).bout : new PrettyWriter(out, false), false);
    }

    public OutPort(Writer base2, Path path2) {
        this(base2, false, false);
        this.path = path2;
    }

    public OutPort(Writer base2, boolean autoflush, Path path2) {
        this(base2, false, autoflush);
        this.path = path2;
    }

    static {
        ThreadLocation threadLocation = new ThreadLocation("out-default");
        outLocation = threadLocation;
        threadLocation.setGlobal(outInitial);
        ThreadLocation threadLocation2 = new ThreadLocation("err-default");
        errLocation = threadLocation2;
        threadLocation2.setGlobal(errInitial);
    }

    public static OutPort outDefault() {
        return (OutPort) outLocation.get();
    }

    public static void setOutDefault(OutPort o) {
        outLocation.set(o);
    }

    public static OutPort errDefault() {
        return (OutPort) errLocation.get();
    }

    public static void setErrDefault(OutPort e) {
        errLocation.set(e);
    }

    public static OutPort openFile(Object fname) throws IOException {
        Writer wr;
        Object conv = Environment.user().get((Object) "port-char-encoding");
        Path path2 = Path.valueOf(fname);
        OutputStream strm = new BufferedOutputStream(path2.openOutputStream());
        if (conv == null || conv == Boolean.TRUE) {
            wr = new OutputStreamWriter(strm);
        } else {
            if (conv == Boolean.FALSE) {
                conv = "8859_1";
            }
            wr = new OutputStreamWriter(strm, conv.toString());
        }
        return new OutPort(wr, path2);
    }

    public void echo(char[] buf, int off, int len) throws IOException {
        Writer writer = this.base;
        if (writer instanceof LogWriter) {
            ((LogWriter) writer).echo(buf, off, len);
        }
    }

    public static void closeLogFile() throws IOException {
        Writer writer = logFile;
        if (writer != null) {
            writer.close();
            logFile = null;
        }
        Writer writer2 = outInitial.base;
        if (writer2 instanceof LogWriter) {
            Writer writer3 = null;
            ((LogWriter) writer2).setLogFile((Writer) null);
        }
        Writer writer4 = errInitial.base;
        if (writer4 instanceof LogWriter) {
            Writer writer5 = null;
            ((LogWriter) writer4).setLogFile((Writer) null);
        }
    }

    public static void setLogFile(String name) throws IOException {
        if (logFile != null) {
            closeLogFile();
        }
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(name)));
        logFile = printWriter;
        Writer writer = outInitial.base;
        if (writer instanceof LogWriter) {
            ((LogWriter) writer).setLogFile((Writer) printWriter);
        }
        Writer writer2 = errInitial.base;
        if (writer2 instanceof LogWriter) {
            ((LogWriter) writer2).setLogFile(logFile);
        }
    }

    protected static final boolean isWordChar(char ch) {
        return Character.isJavaIdentifierPart(ch) || ch == '-' || ch == '+';
    }

    public void print(int v) {
        NumberFormat numberFormat2 = this.numberFormat;
        if (numberFormat2 == null) {
            super.print(v);
        } else {
            print(numberFormat2.format((long) v));
        }
    }

    public void print(long v) {
        NumberFormat numberFormat2 = this.numberFormat;
        if (numberFormat2 == null) {
            super.print(v);
        } else {
            print(numberFormat2.format(v));
        }
    }

    public void print(double v) {
        NumberFormat numberFormat2 = this.numberFormat;
        if (numberFormat2 == null) {
            super.print(v);
        } else {
            print(numberFormat2.format(v));
        }
    }

    public void print(float v) {
        NumberFormat numberFormat2 = this.numberFormat;
        if (numberFormat2 == null) {
            super.print(v);
        } else {
            print(numberFormat2.format((double) v));
        }
    }

    public void print(boolean v) {
        AbstractFormat abstractFormat = this.objectFormat;
        if (abstractFormat == null) {
            super.print(v);
        } else {
            abstractFormat.writeBoolean(v, this);
        }
    }

    public void print(String v) {
        write(v == null ? "(null)" : v);
    }

    public void print(Object v) {
        AbstractFormat abstractFormat = this.objectFormat;
        if (abstractFormat != null) {
            abstractFormat.writeObject(v, (PrintConsumer) this);
        } else if (v instanceof Consumable) {
            ((Consumable) v).consume(this);
        } else {
            super.print(v == null ? "null" : v);
        }
    }

    public void print(Consumer out) {
        out.write("#<output-port");
        if (this.path != null) {
            out.write(32);
            out.write(this.path.toString());
        }
        out.write(62);
    }

    public void startElement(Object type) {
        AbstractFormat abstractFormat = this.objectFormat;
        if (abstractFormat != null) {
            abstractFormat.startElement(type, this);
            return;
        }
        print('(');
        print(type);
    }

    public void endElement() {
        AbstractFormat abstractFormat = this.objectFormat;
        if (abstractFormat != null) {
            abstractFormat.endElement(this);
        } else {
            print(')');
        }
    }

    public void startAttribute(Object attrType) {
        AbstractFormat abstractFormat = this.objectFormat;
        if (abstractFormat != null) {
            abstractFormat.startAttribute(attrType, this);
            return;
        }
        print(' ');
        print(attrType);
        print(": ");
    }

    public void endAttribute() {
        AbstractFormat abstractFormat = this.objectFormat;
        if (abstractFormat != null) {
            abstractFormat.endAttribute(this);
        } else {
            print(' ');
        }
    }

    public void writeWordEnd() {
        this.bout.writeWordEnd();
    }

    public void writeWordStart() {
        this.bout.writeWordStart();
    }

    public void freshLine() {
        if (this.bout.getColumnNumber() != 0) {
            println();
        }
    }

    public int getColumnNumber() {
        return this.bout.getColumnNumber();
    }

    public void setColumnNumber(int column) {
        this.bout.setColumnNumber(column);
    }

    public void clearBuffer() {
        this.bout.clearBuffer();
    }

    public void closeThis() {
        try {
            Writer writer = this.base;
            if (!(writer instanceof OutPort) || ((OutPort) writer).bout != this.bout) {
                this.bout.closeThis();
            }
        } catch (IOException e) {
            setError();
        }
        WriterManager.instance.unregister(this.unregisterRef);
    }

    public void close() {
        try {
            Writer writer = this.base;
            if (!(writer instanceof OutPort) || ((OutPort) writer).bout != this.bout) {
                this.out.close();
            } else {
                writer.close();
            }
        } catch (IOException e) {
            setError();
        }
        WriterManager.instance.unregister(this.unregisterRef);
    }

    /* access modifiers changed from: protected */
    public boolean closeOnExit() {
        return true;
    }

    public static void runCleanups() {
        WriterManager.instance.run();
    }

    public void startLogicalBlock(String prefix, boolean perLine, String suffix) {
        this.bout.startLogicalBlock(prefix, perLine, suffix);
    }

    public void startLogicalBlock(String prefix, String suffix, int indent) {
        this.bout.startLogicalBlock(prefix, false, suffix);
        this.bout.addIndentation(prefix == null ? indent : indent - prefix.length(), false);
    }

    public void endLogicalBlock(String suffix) {
        this.bout.endLogicalBlock(suffix);
    }

    public void writeBreak(int kind) {
        this.bout.writeBreak(kind);
    }

    public void writeSpaceLinear() {
        write(32);
        writeBreak(78);
    }

    public void writeBreakLinear() {
        writeBreak(78);
    }

    public void writeSpaceFill() {
        write(32);
        writeBreak(70);
    }

    public void writeBreakFill() {
        writeBreak(70);
    }

    public void setIndentation(int amount, boolean current) {
        this.bout.addIndentation(amount, current);
    }
}
