package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.ThreadLocation;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xml.NodeTree;
import gnu.xml.XMLParser;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public class Document {
    private static HashMap cache = new HashMap();
    private static ThreadLocation docMapLocation = new ThreadLocation("document-map");
    public static final Document document = new Document();

    public static void parse(Object name, Consumer out) throws Throwable {
        SourceMessages messages = new SourceMessages();
        if (out instanceof XConsumer) {
            ((XConsumer) out).beginEntity(name);
        }
        XMLParser.parse(name, messages, out);
        if (messages.seenErrors()) {
            throw new SyntaxException("document function read invalid XML", messages);
        } else if (out instanceof XConsumer) {
            ((XConsumer) out).endEntity();
        }
    }

    public static KDocument parse(Object uri) throws Throwable {
        NodeTree tree = new NodeTree();
        parse(uri, tree);
        return new KDocument(tree, 10);
    }

    private static class DocReference extends SoftReference {
        static ReferenceQueue queue = new ReferenceQueue();
        Path key;

        public DocReference(Path key2, KDocument doc) {
            super(doc, queue);
            this.key = key2;
        }
    }

    public static void clearLocalCache() {
        docMapLocation.getLocation().set((Object) null);
    }

    public static void clearSoftCache() {
        cache = new HashMap();
    }

    public static KDocument parseCached(Object uri) throws Throwable {
        return parseCached(Path.valueOf(uri));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r4 = (gnu.kawa.xml.Document.DocReference) cache.get(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0038, code lost:
        if (r4 == null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003a, code lost:
        r3 = (gnu.kawa.xml.KDocument) r4.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        if (r3 != null) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
        cache.remove(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0049, code lost:
        r2.put(r7, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004d, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r3 = parse(r7);
        r2.put(r7, r3);
        cache.put(r7, new gnu.kawa.xml.Document.DocReference(r7, r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0061, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000d, code lost:
        r1 = docMapLocation.getLocation();
        r2 = (java.util.Hashtable) r1.get((java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001b, code lost:
        if (r2 != null) goto L_0x0026;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001d, code lost:
        r2 = new java.util.Hashtable();
        r1.set(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0026, code lost:
        r3 = (gnu.kawa.xml.KDocument) r2.get(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002c, code lost:
        if (r3 == null) goto L_0x0030;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized gnu.kawa.xml.KDocument parseCached(gnu.text.Path r7) throws java.lang.Throwable {
        /*
            java.lang.Class<gnu.kawa.xml.Document> r0 = gnu.kawa.xml.Document.class
            monitor-enter(r0)
        L_0x0003:
            java.lang.ref.ReferenceQueue r1 = gnu.kawa.xml.Document.DocReference.queue     // Catch:{ all -> 0x006b }
            java.lang.ref.Reference r1 = r1.poll()     // Catch:{ all -> 0x006b }
            gnu.kawa.xml.Document$DocReference r1 = (gnu.kawa.xml.Document.DocReference) r1     // Catch:{ all -> 0x006b }
            if (r1 != 0) goto L_0x0062
            gnu.mapping.ThreadLocation r1 = docMapLocation     // Catch:{ all -> 0x006b }
            gnu.mapping.NamedLocation r1 = r1.getLocation()     // Catch:{ all -> 0x006b }
            r2 = 0
            java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x006b }
            java.util.Hashtable r2 = (java.util.Hashtable) r2     // Catch:{ all -> 0x006b }
            if (r2 != 0) goto L_0x0026
            java.util.Hashtable r3 = new java.util.Hashtable     // Catch:{ all -> 0x006b }
            r3.<init>()     // Catch:{ all -> 0x006b }
            r2 = r3
            r1.set(r2)     // Catch:{ all -> 0x006b }
        L_0x0026:
            java.lang.Object r3 = r2.get(r7)     // Catch:{ all -> 0x006b }
            gnu.kawa.xml.KDocument r3 = (gnu.kawa.xml.KDocument) r3     // Catch:{ all -> 0x006b }
            if (r3 == 0) goto L_0x0030
            monitor-exit(r0)
            return r3
        L_0x0030:
            java.util.HashMap r4 = cache     // Catch:{ all -> 0x006b }
            java.lang.Object r4 = r4.get(r7)     // Catch:{ all -> 0x006b }
            gnu.kawa.xml.Document$DocReference r4 = (gnu.kawa.xml.Document.DocReference) r4     // Catch:{ all -> 0x006b }
            if (r4 == 0) goto L_0x004e
            java.lang.Object r5 = r4.get()     // Catch:{ all -> 0x006b }
            gnu.kawa.xml.KDocument r5 = (gnu.kawa.xml.KDocument) r5     // Catch:{ all -> 0x006b }
            r3 = r5
            if (r3 != 0) goto L_0x0049
            java.util.HashMap r5 = cache     // Catch:{ all -> 0x006b }
            r5.remove(r7)     // Catch:{ all -> 0x006b }
            goto L_0x004e
        L_0x0049:
            r2.put(r7, r3)     // Catch:{ all -> 0x006b }
            monitor-exit(r0)
            return r3
        L_0x004e:
            gnu.kawa.xml.KDocument r5 = parse(r7)     // Catch:{ all -> 0x006b }
            r3 = r5
            r2.put(r7, r3)     // Catch:{ all -> 0x006b }
            java.util.HashMap r5 = cache     // Catch:{ all -> 0x006b }
            gnu.kawa.xml.Document$DocReference r6 = new gnu.kawa.xml.Document$DocReference     // Catch:{ all -> 0x006b }
            r6.<init>(r7, r3)     // Catch:{ all -> 0x006b }
            r5.put(r7, r6)     // Catch:{ all -> 0x006b }
            monitor-exit(r0)
            return r3
        L_0x0062:
            java.util.HashMap r2 = cache     // Catch:{ all -> 0x006b }
            gnu.text.Path r3 = r1.key     // Catch:{ all -> 0x006b }
            r2.remove(r3)     // Catch:{ all -> 0x006b }
            goto L_0x0003
        L_0x006b:
            r7 = move-exception
            monitor-exit(r0)
            goto L_0x006f
        L_0x006e:
            throw r7
        L_0x006f:
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.xml.Document.parseCached(gnu.text.Path):gnu.kawa.xml.KDocument");
    }
}
