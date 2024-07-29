package com.google.appinventor.components.runtime.multidex;

import androidx.core.internal.view.SupportMenu;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

class ZipEntryReader {
    private static final long CENSIG = 33639248;
    private static final int GPBF_ENCRYPTED_FLAG = 1;
    private static final int GPBF_UNSUPPORTED_MASK = 1;
    static final Charset UTF_8 = Charset.forName("UTF-8");

    ZipEntryReader() {
    }

    static ZipEntry readEntry(ByteBuffer in) throws IOException {
        int i;
        ByteBuffer byteBuffer = in;
        int sig = in.getInt();
        if (((long) sig) == CENSIG) {
            ByteBuffer byteBuffer2 = (ByteBuffer) byteBuffer.position(8);
            int gpbf = in.getShort() & SupportMenu.USER_MASK;
            if ((gpbf & 1) == 0) {
                int i2 = in.getShort() & SupportMenu.USER_MASK;
                int time = in.getShort() & SupportMenu.USER_MASK;
                int modDate = in.getShort() & SupportMenu.USER_MASK;
                int nameLength = in.getShort() & 65535;
                int i3 = in.getShort() & SupportMenu.USER_MASK;
                short s = 65535 & in.getShort();
                ByteBuffer byteBuffer3 = (ByteBuffer) byteBuffer.position(42);
                int i4 = sig;
                int i5 = gpbf;
                byte[] nameBytes = new byte[nameLength];
                long j = ((long) in.getInt()) & 4294967295L;
                byteBuffer.get(nameBytes, 0, nameBytes.length);
                int i6 = nameLength;
                int nameLength2 = nameBytes.length;
                int i7 = i3;
                Charset charset = UTF_8;
                String name = new String(nameBytes, 0, nameLength2, charset);
                ZipEntry entry = new ZipEntry(name);
                entry.setMethod(i2);
                short s2 = s;
                entry.setTime(getTime(time, modDate));
                entry.setCrc(((long) in.getInt()) & 4294967295L);
                entry.setCompressedSize(((long) in.getInt()) & 4294967295L);
                entry.setSize(((long) in.getInt()) & 4294967295L);
                if (s2 > 0) {
                    int commentByteCount = s2;
                    byte[] commentBytes = new byte[commentByteCount];
                    String str = name;
                    i = 0;
                    byteBuffer.get(commentBytes, 0, commentByteCount);
                    int i8 = commentByteCount;
                    short s3 = i2;
                    entry.setComment(new String(commentBytes, 0, commentBytes.length, charset));
                } else {
                    int compressionMethod = i2;
                    short s4 = s2;
                    String str2 = name;
                    i = 0;
                }
                if (i7 > 0) {
                    int extraLength = i7;
                    byte[] extra = new byte[extraLength];
                    byteBuffer.get(extra, i, extraLength);
                    entry.setExtra(extra);
                } else {
                    int extraLength2 = i7;
                }
                return entry;
            }
            throw new ZipException("Invalid General Purpose Bit Flag: " + gpbf);
        }
        throw new ZipException("Central Directory Entry not found");
    }

    private static long getTime(int time, int modDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(14, 0);
        GregorianCalendar gregorianCalendar = cal;
        gregorianCalendar.set(((modDate >> 9) & 127) + 1980, ((modDate >> 5) & 15) - 1, modDate & 31, (time >> 11) & 31, (time >> 5) & 63, (time & 31) << 1);
        return cal.getTime().getTime();
    }
}
