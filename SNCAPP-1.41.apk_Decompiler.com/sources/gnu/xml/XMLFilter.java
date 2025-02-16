package gnu.xml;

import gnu.expr.Keyword;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.AbstractSequence;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.Symbol;
import gnu.text.Char;
import gnu.text.LineBufferedReader;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import java.util.List;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XMLFilter implements DocumentHandler, ContentHandler, SourceLocator, XConsumer, PositionConsumer {
    public static final int COPY_NAMESPACES_INHERIT = 2;
    public static final int COPY_NAMESPACES_PRESERVE = 1;
    private static final int SAW_KEYWORD = 1;
    private static final int SAW_WORD = 2;
    int attrCount = -1;
    String attrLocalName;
    String attrPrefix;
    Consumer base;
    public transient int copyNamespacesMode = 1;
    String currentNamespacePrefix;
    protected int ignoringLevel;
    LineBufferedReader in;
    boolean inStartTag;
    SourceLocator locator;
    MappingInfo[] mappingTable;
    int mappingTableMask;
    private SourceMessages messages;
    boolean mismatchReported;
    NamespaceBinding namespaceBindings;
    public boolean namespacePrefixes = false;
    protected int nesting;
    public Consumer out;
    int previous = 0;
    int[] startIndexes = null;
    protected int stringizingElementNesting = -1;
    protected int stringizingLevel;
    TreeList tlist;
    Object[] workStack;

    public void setSourceLocator(LineBufferedReader in2) {
        this.in = in2;
        this.locator = this;
    }

    public void setSourceLocator(SourceLocator locator2) {
        this.locator = locator2;
    }

    public void setMessages(SourceMessages messages2) {
        this.messages = messages2;
    }

    public NamespaceBinding findNamespaceBinding(String prefix, String uri, NamespaceBinding oldBindings) {
        int hash = uri == null ? 0 : uri.hashCode();
        if (prefix != null) {
            hash ^= prefix.hashCode();
        }
        int bucket = this.mappingTableMask & hash;
        for (MappingInfo info = this.mappingTable[bucket]; info != null; info = info.nextInBucket) {
            if (info.tagHash == hash && info.prefix == prefix) {
                NamespaceBinding namespaceBinding = info.namespaces;
                NamespaceBinding namespaces = namespaceBinding;
                if (namespaceBinding != null && namespaces.getNext() == this.namespaceBindings && namespaces.getPrefix() == prefix && info.uri == uri) {
                    return info.namespaces;
                }
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.nextInBucket = this.mappingTable[bucket];
        this.mappingTable[bucket] = info2;
        info2.tagHash = hash;
        info2.prefix = prefix;
        info2.local = uri;
        info2.uri = uri;
        if (uri == "") {
            uri = null;
        }
        info2.namespaces = new NamespaceBinding(prefix, uri, oldBindings);
        return info2.namespaces;
    }

    public MappingInfo lookupNamespaceBinding(String prefix, char[] uriChars, int uriStart, int uriLength, int uriHash, NamespaceBinding oldBindings) {
        int hash = prefix == null ? uriHash : prefix.hashCode() ^ uriHash;
        int bucket = this.mappingTableMask & hash;
        for (MappingInfo info = this.mappingTable[bucket]; info != null; info = info.nextInBucket) {
            if (info.tagHash == hash && info.prefix == prefix) {
                NamespaceBinding namespaceBinding = info.namespaces;
                NamespaceBinding namespaces = namespaceBinding;
                if (namespaceBinding != null && namespaces.getNext() == this.namespaceBindings && namespaces.getPrefix() == prefix && MappingInfo.equals(info.uri, uriChars, uriStart, uriLength)) {
                    return info;
                }
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.nextInBucket = this.mappingTable[bucket];
        this.mappingTable[bucket] = info2;
        String uri = new String(uriChars, uriStart, uriLength).intern();
        info2.tagHash = hash;
        info2.prefix = prefix;
        info2.local = uri;
        info2.uri = uri;
        if (uri == "") {
            uri = null;
        }
        info2.namespaces = new NamespaceBinding(prefix, uri, oldBindings);
        return info2;
    }

    public void endAttribute() {
        int uriLength;
        char[] data;
        int uriHash;
        int uriHash2;
        String str = this.attrLocalName;
        if (str != null) {
            if (this.previous == 1) {
                this.previous = 0;
                return;
            }
            if (this.stringizingElementNesting >= 0) {
                this.ignoringLevel--;
            }
            int i = this.stringizingLevel - 1;
            this.stringizingLevel = i;
            if (i == 0) {
                if (str == "id" && this.attrPrefix == "xml") {
                    int valStart = this.startIndexes[this.attrCount - 1] + 5;
                    int valEnd = this.tlist.gapStart;
                    char[] data2 = this.tlist.data;
                    int i2 = valStart;
                    while (true) {
                        if (i2 >= valEnd) {
                            break;
                        }
                        int i3 = i2 + 1;
                        char datum = data2[i2];
                        if ((datum & LispReader.TOKEN_ESCAPE_CHAR) > 40959 || datum == 9 || datum == 13 || datum == 10 || (datum == ' ' && (i3 == valEnd || data2[i3] == ' '))) {
                            StringBuffer sbuf = new StringBuffer();
                            this.tlist.stringValue(valStart, valEnd, sbuf);
                            this.tlist.gapStart = valStart;
                            this.tlist.write(TextUtils.replaceWhitespace(sbuf.toString(), true));
                        } else {
                            i2 = i3;
                        }
                    }
                    StringBuffer sbuf2 = new StringBuffer();
                    this.tlist.stringValue(valStart, valEnd, sbuf2);
                    this.tlist.gapStart = valStart;
                    this.tlist.write(TextUtils.replaceWhitespace(sbuf2.toString(), true));
                }
                this.attrLocalName = null;
                this.attrPrefix = null;
                if (this.currentNamespacePrefix == null || this.namespacePrefixes) {
                    this.tlist.endAttribute();
                }
                if (this.currentNamespacePrefix != null) {
                    int attrStart = this.startIndexes[this.attrCount - 1];
                    int uriStart = attrStart;
                    int uriEnd = this.tlist.gapStart;
                    int uriLength2 = uriEnd - uriStart;
                    char[] data3 = this.tlist.data;
                    int uriHash3 = 0;
                    int i4 = uriStart;
                    while (true) {
                        if (i4 >= uriEnd) {
                            int i5 = uriEnd;
                            uriLength = uriLength2;
                            data = data3;
                            uriHash = uriHash3;
                            uriHash2 = uriStart;
                            break;
                        }
                        char datum2 = data3[i4];
                        if ((datum2 & LispReader.TOKEN_ESCAPE_CHAR) > 40959) {
                            StringBuffer sbuf3 = new StringBuffer();
                            this.tlist.stringValue(uriStart, uriEnd, sbuf3);
                            int uriHash4 = sbuf3.hashCode();
                            int uriLength3 = sbuf3.length();
                            int uriEnd2 = uriLength3;
                            char[] data4 = new char[sbuf3.length()];
                            sbuf3.getChars(0, uriEnd2, data4, 0);
                            int i6 = uriEnd2;
                            uriLength = uriLength3;
                            data = data4;
                            uriHash = uriHash4;
                            uriHash2 = 0;
                            break;
                        }
                        uriHash3 = (uriHash3 * 31) + datum2;
                        i4++;
                    }
                    this.tlist.gapStart = attrStart;
                    String str2 = this.currentNamespacePrefix;
                    this.namespaceBindings = lookupNamespaceBinding(str2 == "" ? null : str2, data, uriHash2, uriLength, uriHash, this.namespaceBindings).namespaces;
                    this.currentNamespacePrefix = null;
                }
            }
        }
    }

    private String resolve(String prefix, boolean isAttribute) {
        if (isAttribute && prefix == null) {
            return "";
        }
        String uri = this.namespaceBindings.resolve(prefix);
        if (uri != null) {
            return uri;
        }
        if (prefix != null) {
            error('e', "unknown namespace prefix '" + prefix + '\'');
        }
        return "";
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x019a, code lost:
        if (r1 != null) goto L_0x01ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x019c, code lost:
        r6 = new gnu.xml.XName(r8.qname, r3);
        r1 = r6;
        r8.type = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeStartTag() {
        /*
            r16 = this;
            r0 = r16
            int r1 = r0.attrCount
            if (r1 < 0) goto L_0x02b0
            int r1 = r0.stringizingLevel
            if (r1 <= 0) goto L_0x000c
            goto L_0x02b0
        L_0x000c:
            r1 = 0
            r0.inStartTag = r1
            r0.previous = r1
            java.lang.String r2 = r0.attrLocalName
            if (r2 == 0) goto L_0x0018
            r16.endAttribute()
        L_0x0018:
            int r2 = r0.nesting
            if (r2 != 0) goto L_0x001f
            gnu.xml.NamespaceBinding r2 = gnu.xml.NamespaceBinding.predefinedXML
            goto L_0x0027
        L_0x001f:
            java.lang.Object[] r3 = r0.workStack
            int r2 = r2 + -2
            r2 = r3[r2]
            gnu.xml.NamespaceBinding r2 = (gnu.xml.NamespaceBinding) r2
        L_0x0027:
            gnu.xml.NamespaceBinding r3 = r0.namespaceBindings
            r4 = 0
        L_0x002a:
            int r5 = r0.attrCount
            r6 = 1
            if (r4 > r5) goto L_0x00cb
            java.lang.Object[] r5 = r0.workStack
            int r7 = r0.nesting
            int r7 = r7 + r4
            int r7 = r7 - r6
            r5 = r5[r7]
            boolean r7 = r5 instanceof gnu.mapping.Symbol
            if (r7 == 0) goto L_0x00c7
            r7 = r5
            gnu.mapping.Symbol r7 = (gnu.mapping.Symbol) r7
            java.lang.String r8 = r7.getPrefix()
            java.lang.String r9 = ""
            if (r8 != r9) goto L_0x0047
            r8 = 0
        L_0x0047:
            java.lang.String r10 = r7.getNamespaceURI()
            if (r10 != r9) goto L_0x004e
            r10 = 0
        L_0x004e:
            if (r4 <= 0) goto L_0x0056
            if (r8 != 0) goto L_0x0056
            if (r10 != 0) goto L_0x0056
            goto L_0x00c7
        L_0x0056:
            r9 = 0
            r11 = r3
        L_0x0058:
            if (r11 != r2) goto L_0x005b
            r9 = 1
        L_0x005b:
            if (r11 != 0) goto L_0x0066
            if (r8 != 0) goto L_0x0061
            if (r10 == 0) goto L_0x00c7
        L_0x0061:
            gnu.xml.NamespaceBinding r3 = r0.findNamespaceBinding(r8, r10, r3)
            goto L_0x00c7
        L_0x0066:
            java.lang.String r12 = r11.prefix
            if (r12 != r8) goto L_0x00c4
            java.lang.String r12 = r11.uri
            if (r12 == r10) goto L_0x00c7
            if (r9 == 0) goto L_0x0075
            gnu.xml.NamespaceBinding r3 = r0.findNamespaceBinding(r8, r10, r3)
            goto L_0x00c7
        L_0x0075:
            r12 = r3
        L_0x0076:
            if (r12 != 0) goto L_0x009b
            r13 = 1
        L_0x0079:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "_ns_"
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.StringBuilder r14 = r14.append(r13)
            java.lang.String r14 = r14.toString()
            java.lang.String r14 = r14.intern()
            java.lang.String r15 = r3.resolve(r14)
            if (r15 != 0) goto L_0x0098
            goto L_0x00a8
        L_0x0098:
            int r13 = r13 + 1
            goto L_0x0079
        L_0x009b:
            java.lang.String r13 = r12.uri
            if (r13 != r10) goto L_0x00c1
            java.lang.String r14 = r12.prefix
            java.lang.String r13 = r3.resolve(r14)
            if (r13 != r10) goto L_0x00c1
        L_0x00a8:
            gnu.xml.NamespaceBinding r3 = r0.findNamespaceBinding(r14, r10, r3)
            java.lang.String r12 = r7.getLocalName()
            if (r10 != 0) goto L_0x00b4
            java.lang.String r10 = ""
        L_0x00b4:
            java.lang.Object[] r13 = r0.workStack
            int r15 = r0.nesting
            int r15 = r15 + r4
            int r15 = r15 - r6
            gnu.mapping.Symbol r6 = gnu.mapping.Symbol.make(r10, r12, r14)
            r13[r15] = r6
            goto L_0x00c7
        L_0x00c1:
            gnu.xml.NamespaceBinding r12 = r12.next
            goto L_0x0076
        L_0x00c4:
            gnu.xml.NamespaceBinding r11 = r11.next
            goto L_0x0058
        L_0x00c7:
            int r4 = r4 + 1
            goto L_0x002a
        L_0x00cb:
            r4 = 0
        L_0x00cc:
            int r5 = r0.attrCount
            if (r4 > r5) goto L_0x0284
            java.lang.Object[] r5 = r0.workStack
            int r7 = r0.nesting
            int r7 = r7 + r4
            int r7 = r7 - r6
            r5 = r5[r7]
            r7 = 0
            boolean r8 = r5 instanceof gnu.xml.MappingInfo
            if (r8 != 0) goto L_0x00f2
            gnu.lists.Consumer r8 = r0.out
            gnu.lists.TreeList r9 = r0.tlist
            if (r8 != r9) goto L_0x00e4
            goto L_0x00f2
        L_0x00e4:
            r8 = r5
            gnu.mapping.Symbol r8 = (gnu.mapping.Symbol) r8
            java.lang.String r9 = r8.getNamespaceURI()
            java.lang.String r10 = r8.getLocalName()
            r11 = 0
            goto L_0x01b6
        L_0x00f2:
            boolean r8 = r5 instanceof gnu.xml.MappingInfo
            if (r8 == 0) goto L_0x0115
            r8 = r5
            gnu.xml.MappingInfo r8 = (gnu.xml.MappingInfo) r8
            java.lang.String r9 = r8.prefix
            java.lang.String r10 = r8.local
            if (r4 <= 0) goto L_0x010b
            java.lang.String r11 = "xmlns"
            if (r9 != 0) goto L_0x0105
            if (r10 == r11) goto L_0x0107
        L_0x0105:
            if (r9 != r11) goto L_0x010b
        L_0x0107:
            r7 = 1
            java.lang.String r11 = "(namespace-node)"
            goto L_0x0128
        L_0x010b:
            if (r4 <= 0) goto L_0x010f
            r11 = 1
            goto L_0x0110
        L_0x010f:
            r11 = 0
        L_0x0110:
            java.lang.String r11 = r0.resolve(r9, r11)
            goto L_0x0128
        L_0x0115:
            r8 = r5
            gnu.mapping.Symbol r8 = (gnu.mapping.Symbol) r8
            gnu.xml.MappingInfo r9 = r0.lookupTag(r8)
            java.lang.String r10 = r9.prefix
            java.lang.String r11 = r9.local
            java.lang.String r12 = r8.getNamespaceURI()
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
        L_0x0128:
            int r12 = r8.tagHash
            int r13 = r0.mappingTableMask
            r13 = r13 & r12
            gnu.xml.MappingInfo[] r14 = r0.mappingTable
            r8 = r14[r13]
            r14 = 0
        L_0x0132:
            if (r8 != 0) goto L_0x0163
            r8 = r14
            gnu.xml.MappingInfo r15 = new gnu.xml.MappingInfo
            r15.<init>()
            r8 = r15
            r8.tagHash = r12
            r8.prefix = r9
            r8.local = r10
            gnu.xml.MappingInfo[] r15 = r0.mappingTable
            r15 = r15[r13]
            r8.nextInBucket = r15
            gnu.xml.MappingInfo[] r15 = r0.mappingTable
            r15[r13] = r8
            r8.uri = r11
            gnu.mapping.Symbol r15 = gnu.mapping.Symbol.make(r11, r10, r9)
            r8.qname = r15
            if (r4 != 0) goto L_0x01ab
            gnu.xml.XName r15 = new gnu.xml.XName
            gnu.mapping.Symbol r1 = r8.qname
            r15.<init>(r1, r3)
            r1 = r15
            r8.type = r1
            r8.namespaces = r3
            goto L_0x01ab
        L_0x0163:
            int r1 = r8.tagHash
            if (r1 != r12) goto L_0x027e
            java.lang.String r1 = r8.local
            if (r1 != r10) goto L_0x027e
            java.lang.String r1 = r8.prefix
            if (r1 != r9) goto L_0x027e
            java.lang.String r1 = r8.uri
            if (r1 != 0) goto L_0x017c
            r8.uri = r11
            gnu.mapping.Symbol r1 = gnu.mapping.Symbol.make(r11, r10, r9)
            r8.qname = r1
            goto L_0x018c
        L_0x017c:
            java.lang.String r1 = r8.uri
            if (r1 == r11) goto L_0x0182
            goto L_0x027e
        L_0x0182:
            gnu.mapping.Symbol r1 = r8.qname
            if (r1 != 0) goto L_0x018c
            gnu.mapping.Symbol r1 = gnu.mapping.Symbol.make(r11, r10, r9)
            r8.qname = r1
        L_0x018c:
            if (r4 != 0) goto L_0x01a8
            gnu.xml.NamespaceBinding r1 = r8.namespaces
            if (r1 == r3) goto L_0x0196
            gnu.xml.NamespaceBinding r1 = r8.namespaces
            if (r1 != 0) goto L_0x027e
        L_0x0196:
            gnu.xml.XName r1 = r8.type
            r8.namespaces = r3
            if (r1 != 0) goto L_0x01ab
            gnu.xml.XName r15 = new gnu.xml.XName
            gnu.mapping.Symbol r6 = r8.qname
            r15.<init>(r6, r3)
            r6 = r15
            r1 = r6
            r8.type = r6
            goto L_0x01ab
        L_0x01a8:
            gnu.mapping.Symbol r1 = r8.qname
        L_0x01ab:
            java.lang.Object[] r1 = r0.workStack
            int r6 = r0.nesting
            int r6 = r6 + r4
            r15 = 1
            int r6 = r6 - r15
            r1[r6] = r8
            r9 = r11
            r11 = r8
        L_0x01b6:
            r1 = 1
        L_0x01b7:
            if (r1 >= r4) goto L_0x01fc
            java.lang.Object[] r6 = r0.workStack
            int r8 = r0.nesting
            int r8 = r8 + r1
            r12 = 1
            int r8 = r8 - r12
            r6 = r6[r8]
            boolean r8 = r6 instanceof gnu.mapping.Symbol
            if (r8 == 0) goto L_0x01ca
            r8 = r6
            gnu.mapping.Symbol r8 = (gnu.mapping.Symbol) r8
            goto L_0x01d3
        L_0x01ca:
            boolean r8 = r6 instanceof gnu.xml.MappingInfo
            if (r8 == 0) goto L_0x01f9
            r8 = r6
            gnu.xml.MappingInfo r8 = (gnu.xml.MappingInfo) r8
            gnu.mapping.Symbol r8 = r8.qname
        L_0x01d3:
            java.lang.String r12 = r8.getLocalPart()
            if (r10 != r12) goto L_0x01f9
            java.lang.String r12 = r8.getNamespaceURI()
            if (r9 != r12) goto L_0x01f9
            java.lang.Object[] r12 = r0.workStack
            int r13 = r0.nesting
            r14 = 1
            int r13 = r13 - r14
            r12 = r12[r13]
            boolean r13 = r12 instanceof gnu.xml.MappingInfo
            if (r13 == 0) goto L_0x01f0
            r13 = r12
            gnu.xml.MappingInfo r13 = (gnu.xml.MappingInfo) r13
            gnu.mapping.Symbol r12 = r13.qname
        L_0x01f0:
            r13 = 101(0x65, float:1.42E-43)
            java.lang.String r14 = duplicateAttributeMessage(r8, r12)
            r0.error(r13, r14)
        L_0x01f9:
            int r1 = r1 + 1
            goto L_0x01b7
        L_0x01fc:
            gnu.lists.Consumer r1 = r0.out
            gnu.lists.TreeList r6 = r0.tlist
            if (r1 != r6) goto L_0x0239
            if (r4 != 0) goto L_0x0207
            gnu.xml.XName r1 = r11.type
            goto L_0x0209
        L_0x0207:
            gnu.mapping.Symbol r1 = r11.qname
        L_0x0209:
            int r6 = r11.index
            if (r6 <= 0) goto L_0x0215
            gnu.lists.TreeList r8 = r0.tlist
            java.lang.Object[] r8 = r8.objects
            r8 = r8[r6]
            if (r8 == r1) goto L_0x021d
        L_0x0215:
            gnu.lists.TreeList r8 = r0.tlist
            int r6 = r8.find(r1)
            r11.index = r6
        L_0x021d:
            if (r4 != 0) goto L_0x0227
            gnu.lists.TreeList r8 = r0.tlist
            int r12 = r8.gapEnd
            r8.setElementName(r12, r6)
            goto L_0x0238
        L_0x0227:
            if (r7 == 0) goto L_0x022d
            boolean r8 = r0.namespacePrefixes
            if (r8 == 0) goto L_0x0238
        L_0x022d:
            gnu.lists.TreeList r8 = r0.tlist
            int[] r12 = r0.startIndexes
            int r13 = r4 + -1
            r12 = r12[r13]
            r8.setAttributeName(r12, r6)
        L_0x0238:
            goto L_0x0278
        L_0x0239:
            if (r11 != 0) goto L_0x023d
            r1 = r5
            goto L_0x0244
        L_0x023d:
            if (r4 != 0) goto L_0x0242
            gnu.xml.XName r1 = r11.type
            goto L_0x0244
        L_0x0242:
            gnu.mapping.Symbol r1 = r11.qname
        L_0x0244:
            if (r4 != 0) goto L_0x024c
            gnu.lists.Consumer r6 = r0.out
            r6.startElement(r1)
            goto L_0x0278
        L_0x024c:
            if (r7 == 0) goto L_0x0252
            boolean r6 = r0.namespacePrefixes
            if (r6 == 0) goto L_0x0278
        L_0x0252:
            gnu.lists.Consumer r6 = r0.out
            r6.startAttribute(r1)
            int[] r6 = r0.startIndexes
            int r8 = r4 + -1
            r8 = r6[r8]
            int r12 = r0.attrCount
            if (r4 >= r12) goto L_0x0264
            r6 = r6[r4]
            goto L_0x0268
        L_0x0264:
            gnu.lists.TreeList r6 = r0.tlist
            int r6 = r6.gapStart
        L_0x0268:
            gnu.lists.TreeList r12 = r0.tlist
            int r13 = r8 + 5
            int r14 = r6 + -1
            gnu.lists.Consumer r15 = r0.out
            r12.consumeIRange(r13, r14, r15)
            gnu.lists.Consumer r12 = r0.out
            r12.endAttribute()
        L_0x0278:
            int r4 = r4 + 1
            r1 = 0
            r6 = 1
            goto L_0x00cc
        L_0x027e:
            gnu.xml.MappingInfo r8 = r8.nextInBucket
            r1 = 0
            r6 = 1
            goto L_0x0132
        L_0x0284:
            gnu.lists.Consumer r1 = r0.out
            boolean r4 = r1 instanceof gnu.kawa.sax.ContentConsumer
            if (r4 == 0) goto L_0x028f
            gnu.kawa.sax.ContentConsumer r1 = (gnu.kawa.sax.ContentConsumer) r1
            r1.endStartTag()
        L_0x028f:
            r1 = 1
        L_0x0290:
            int r4 = r0.attrCount
            if (r1 > r4) goto L_0x02a1
            java.lang.Object[] r4 = r0.workStack
            int r5 = r0.nesting
            int r5 = r5 + r1
            r6 = 1
            int r5 = r5 - r6
            r7 = 0
            r4[r5] = r7
            int r1 = r1 + 1
            goto L_0x0290
        L_0x02a1:
            gnu.lists.Consumer r1 = r0.out
            gnu.lists.TreeList r4 = r0.tlist
            if (r1 == r4) goto L_0x02ac
            r0.base = r1
            r4.clear()
        L_0x02ac:
            r1 = -1
            r0.attrCount = r1
            return
        L_0x02b0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLFilter.closeStartTag():void");
    }

    /* access modifiers changed from: protected */
    public boolean checkWriteAtomic() {
        this.previous = 0;
        if (this.ignoringLevel > 0) {
            return false;
        }
        closeStartTag();
        return true;
    }

    public void write(int v) {
        if (checkWriteAtomic()) {
            this.base.write(v);
        }
    }

    public void writeBoolean(boolean v) {
        if (checkWriteAtomic()) {
            this.base.writeBoolean(v);
        }
    }

    public void writeFloat(float v) {
        if (checkWriteAtomic()) {
            this.base.writeFloat(v);
        }
    }

    public void writeDouble(double v) {
        if (checkWriteAtomic()) {
            this.base.writeDouble(v);
        }
    }

    public void writeInt(int v) {
        if (checkWriteAtomic()) {
            this.base.writeInt(v);
        }
    }

    public void writeLong(long v) {
        if (checkWriteAtomic()) {
            this.base.writeLong(v);
        }
    }

    public void writeDocumentUri(Object uri) {
        if (this.nesting == 2) {
            Consumer consumer = this.base;
            if (consumer instanceof TreeList) {
                ((TreeList) consumer).writeDocumentUri(uri);
            }
        }
    }

    public void consume(SeqPosition position) {
        writePosition(position.sequence, position.ipos);
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        if (this.ignoringLevel <= 0) {
            if (this.stringizingLevel > 0 && this.previous == 2) {
                if (this.stringizingElementNesting < 0) {
                    write(32);
                }
                this.previous = 0;
            }
            seq.consumeNext(ipos, this);
            if (this.stringizingLevel > 0 && this.stringizingElementNesting < 0) {
                this.previous = 2;
            }
        }
    }

    public void writeObject(Object v) {
        if (this.ignoringLevel <= 0) {
            if (v instanceof SeqPosition) {
                SeqPosition pos = (SeqPosition) v;
                writePosition(pos.sequence, pos.getPos());
            } else if (v instanceof TreeList) {
                ((TreeList) v).consume((Consumer) this);
            } else if ((v instanceof List) && !(v instanceof CharSeq)) {
                int i = 0;
                for (Object writeObject : (List) v) {
                    writeObject(writeObject);
                    i++;
                }
            } else if (v instanceof Keyword) {
                startAttribute(((Keyword) v).asSymbol());
                this.previous = 1;
            } else {
                closeStartTag();
                if (v instanceof UnescapedData) {
                    this.base.writeObject(v);
                    this.previous = 0;
                    return;
                }
                if (this.previous == 2) {
                    write(32);
                }
                TextUtils.textValue(v, this);
                this.previous = 2;
            }
        }
    }

    public XMLFilter(Consumer out2) {
        MappingInfo[] mappingInfoArr = new MappingInfo[128];
        this.mappingTable = mappingInfoArr;
        this.mappingTableMask = mappingInfoArr.length - 1;
        this.base = out2;
        this.out = out2;
        if (out2 instanceof NodeTree) {
            this.tlist = (NodeTree) out2;
        } else {
            this.tlist = new TreeList();
        }
        this.namespaceBindings = NamespaceBinding.predefinedXML;
    }

    public void write(char[] data, int start, int length) {
        if (length == 0) {
            writeJoiner();
        } else if (checkWriteAtomic()) {
            this.base.write(data, start, length);
        }
    }

    public void write(String str) {
        write((CharSequence) str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        } else if (checkWriteAtomic()) {
            this.base.write(str, start, length);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean inElement() {
        int i = this.nesting;
        while (i > 0 && this.workStack[i - 1] == null) {
            i -= 2;
        }
        return i != 0;
    }

    public void linefeedFromParser() {
        if (inElement() && checkWriteAtomic()) {
            this.base.write(10);
        }
    }

    public void textFromParser(char[] data, int start, int length) {
        if (!inElement()) {
            for (int i = 0; i != length; i++) {
                if (!Character.isWhitespace(data[start + i])) {
                    error('e', "text at document level");
                    return;
                }
            }
        } else if (length > 0 && checkWriteAtomic()) {
            this.base.write(data, start, length);
        }
    }

    /* access modifiers changed from: protected */
    public void writeJoiner() {
        this.previous = 0;
        if (this.ignoringLevel == 0) {
            ((TreeList) this.base).writeJoiner();
        }
    }

    public void writeCDATA(char[] data, int start, int length) {
        if (checkWriteAtomic()) {
            Consumer consumer = this.base;
            if (consumer instanceof XConsumer) {
                ((XConsumer) consumer).writeCDATA(data, start, length);
            } else {
                write(data, start, length);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void startElementCommon() {
        closeStartTag();
        if (this.stringizingLevel == 0) {
            ensureSpaceInWorkStack(this.nesting);
            this.workStack[this.nesting] = this.namespaceBindings;
            this.tlist.startElement(0);
            this.base = this.tlist;
            this.attrCount = 0;
        } else {
            if (this.previous == 2 && this.stringizingElementNesting < 0) {
                write(32);
            }
            this.previous = 0;
            if (this.stringizingElementNesting < 0) {
                this.stringizingElementNesting = this.nesting;
            }
        }
        this.nesting += 2;
    }

    public void emitStartElement(char[] data, int start, int count) {
        closeStartTag();
        MappingInfo info = lookupTag(data, start, count);
        startElementCommon();
        ensureSpaceInWorkStack(this.nesting - 1);
        this.workStack[this.nesting - 1] = info;
    }

    public void startElement(Object type) {
        NamespaceBinding inherited;
        startElementCommon();
        if (this.stringizingLevel == 0) {
            ensureSpaceInWorkStack(this.nesting - 1);
            Object[] objArr = this.workStack;
            int i = this.nesting;
            objArr[i - 1] = type;
            int i2 = this.copyNamespacesMode;
            if (i2 == 0) {
                this.namespaceBindings = NamespaceBinding.predefinedXML;
            } else if (i2 == 1 || i == 2) {
                this.namespaceBindings = type instanceof XName ? ((XName) type).getNamespaceNodes() : NamespaceBinding.predefinedXML;
            } else {
                int i3 = 2;
                while (true) {
                    if (i3 == this.nesting) {
                        inherited = null;
                        break;
                    }
                    Object[] objArr2 = this.workStack;
                    if (objArr2[i3 + 1] != null) {
                        inherited = (NamespaceBinding) objArr2[i3];
                        break;
                    }
                    i3 += 2;
                }
                if (inherited == null) {
                    this.namespaceBindings = type instanceof XName ? ((XName) type).getNamespaceNodes() : NamespaceBinding.predefinedXML;
                } else if (this.copyNamespacesMode == 2) {
                    this.namespaceBindings = inherited;
                } else if (type instanceof XName) {
                    NamespaceBinding preserved = ((XName) type).getNamespaceNodes();
                    if (NamespaceBinding.commonAncestor(inherited, preserved) == inherited) {
                        this.namespaceBindings = preserved;
                    } else {
                        this.namespaceBindings = mergeHelper(inherited, preserved);
                    }
                } else {
                    this.namespaceBindings = inherited;
                }
            }
        }
    }

    private NamespaceBinding mergeHelper(NamespaceBinding list, NamespaceBinding node) {
        if (node == NamespaceBinding.predefinedXML) {
            return list;
        }
        NamespaceBinding list2 = mergeHelper(list, node.next);
        String uri = node.uri;
        if (list2 == null) {
            if (uri == null) {
                return list2;
            }
            list2 = NamespaceBinding.predefinedXML;
        }
        String prefix = node.prefix;
        String found = list2.resolve(prefix);
        if (found != null ? !found.equals(uri) : uri != null) {
            return findNamespaceBinding(prefix, uri, list2);
        }
        return list2;
    }

    private boolean startAttributeCommon() {
        if (this.stringizingElementNesting >= 0) {
            this.ignoringLevel++;
        }
        int i = this.stringizingLevel;
        this.stringizingLevel = i + 1;
        if (i > 0) {
            return false;
        }
        if (this.attrCount < 0) {
            this.attrCount = 0;
        }
        ensureSpaceInWorkStack(this.nesting + this.attrCount);
        ensureSpaceInStartIndexes(this.attrCount);
        this.startIndexes[this.attrCount] = this.tlist.gapStart;
        this.attrCount++;
        return true;
    }

    public void startAttribute(Object attrType) {
        this.previous = 0;
        if (attrType instanceof Symbol) {
            Symbol sym = (Symbol) attrType;
            String local = sym.getLocalPart();
            this.attrLocalName = local;
            this.attrPrefix = sym.getPrefix();
            String uri = sym.getNamespaceURI();
            if (uri == "http://www.w3.org/2000/xmlns/" || (uri == "" && local == "xmlns")) {
                error('e', "arttribute name cannot be 'xmlns' or in xmlns namespace");
            }
        }
        if (this.nesting == 2 && this.workStack[1] == null) {
            error('e', "attribute not allowed at document level");
        }
        if (this.attrCount < 0 && this.nesting > 0) {
            error('e', "attribute '" + attrType + "' follows non-attribute content");
        }
        if (startAttributeCommon()) {
            Object[] objArr = this.workStack;
            int i = this.nesting;
            objArr[(this.attrCount + i) - 1] = attrType;
            if (i == 0) {
                this.base.startAttribute(attrType);
            } else {
                this.tlist.startAttribute(0);
            }
        }
    }

    public void emitStartAttribute(char[] data, int start, int count) {
        if (this.attrLocalName != null) {
            endAttribute();
        }
        if (startAttributeCommon()) {
            MappingInfo info = lookupTag(data, start, count);
            this.workStack[(this.nesting + this.attrCount) - 1] = info;
            String prefix = info.prefix;
            String local = info.local;
            this.attrLocalName = local;
            this.attrPrefix = prefix;
            if (prefix != null) {
                if (prefix == "xmlns") {
                    this.currentNamespacePrefix = local;
                }
            } else if (local == "xmlns" && prefix == null) {
                this.currentNamespacePrefix = "";
            }
            if (this.currentNamespacePrefix == null || this.namespacePrefixes) {
                this.tlist.startAttribute(0);
            }
        }
    }

    public void emitEndAttributes() {
        if (this.attrLocalName != null) {
            endAttribute();
        }
        closeStartTag();
    }

    public void emitEndElement(char[] data, int start, int length) {
        if (this.attrLocalName != null) {
            error('e', "unclosed attribute");
            endAttribute();
        }
        if (!inElement()) {
            error('e', "unmatched end element");
            return;
        }
        if (data != null) {
            MappingInfo info = lookupTag(data, start, length);
            Object old = this.workStack[this.nesting - 1];
            if ((old instanceof MappingInfo) && !this.mismatchReported) {
                MappingInfo mold = (MappingInfo) old;
                if (!(info.local == mold.local && info.prefix == mold.prefix)) {
                    StringBuffer sbuf = new StringBuffer("</");
                    sbuf.append(data, start, length);
                    sbuf.append("> matching <");
                    String oldPrefix = mold.prefix;
                    if (oldPrefix != null) {
                        sbuf.append(oldPrefix);
                        sbuf.append(':');
                    }
                    sbuf.append(mold.local);
                    sbuf.append('>');
                    error('e', sbuf.toString());
                    this.mismatchReported = true;
                }
            }
        }
        closeStartTag();
        if (this.nesting > 0) {
            endElement();
        }
    }

    public void endElement() {
        closeStartTag();
        int i = this.nesting - 2;
        this.nesting = i;
        this.previous = 0;
        if (this.stringizingLevel == 0) {
            Object[] objArr = this.workStack;
            this.namespaceBindings = (NamespaceBinding) objArr[i];
            objArr[i] = null;
            objArr[i + 1] = null;
            this.base.endElement();
        } else if (this.stringizingElementNesting == i) {
            this.stringizingElementNesting = -1;
            this.previous = 2;
        }
    }

    public void emitEntityReference(char[] name, int start, int length) {
        char c0 = name[start];
        char ch = '?';
        if (length == 2 && name[start + 1] == 't') {
            if (c0 == 'l') {
                ch = '<';
            } else if (c0 == 'g') {
                ch = '>';
            }
        } else if (length == 3) {
            if (c0 == 'a' && name[start + 1] == 'm' && name[start + 2] == 'p') {
                ch = '&';
            }
        } else if (length == 4) {
            char c1 = name[start + 1];
            char c2 = name[start + 2];
            char c3 = name[start + 3];
            if (c0 == 'q' && c1 == 'u' && c2 == 'o' && c3 == 't') {
                ch = '\"';
            } else if (c0 == 'a' && c1 == 'p' && c2 == 'o' && c3 == 's') {
                ch = '\'';
            }
        }
        write((int) ch);
    }

    public void emitCharacterReference(int value, char[] name, int start, int length) {
        if (value >= 65536) {
            Char.print(value, this);
        } else {
            write(value);
        }
    }

    /* access modifiers changed from: protected */
    public void checkValidComment(char[] chars, int offset, int length) {
        int i = length;
        boolean sawHyphen = true;
        while (true) {
            i--;
            if (i >= 0) {
                boolean curHyphen = chars[offset + i] == '-';
                if (!sawHyphen || !curHyphen) {
                    sawHyphen = curHyphen;
                } else {
                    error('e', "consecutive or final hyphen in XML comment");
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void writeComment(char[] chars, int start, int length) {
        checkValidComment(chars, start, length);
        commentFromParser(chars, start, length);
    }

    public void commentFromParser(char[] chars, int start, int length) {
        if (this.stringizingLevel == 0) {
            closeStartTag();
            Consumer consumer = this.base;
            if (consumer instanceof XConsumer) {
                ((XConsumer) consumer).writeComment(chars, start, length);
            }
        } else if (this.stringizingElementNesting < 0) {
            this.base.write(chars, start, length);
        }
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        String target2 = TextUtils.replaceWhitespace(target, true);
        int i = offset + length;
        while (true) {
            i--;
            if (i < offset) {
                break;
            }
            char ch = content[i];
            while (true) {
                if (ch != '>' || i - 1 < offset) {
                    break;
                }
                ch = content[i];
                if (ch == '?') {
                    error('e', "'?>' is not allowed in a processing-instruction");
                    break;
                }
            }
        }
        if ("xml".equalsIgnoreCase(target2)) {
            error('e', "processing-instruction target may not be 'xml' (ignoring case)");
        }
        if (!XName.isNCName(target2)) {
            error('e', "processing-instruction target '" + target2 + "' is not a valid Name");
        }
        processingInstructionCommon(target2, content, offset, length);
    }

    /* access modifiers changed from: package-private */
    public void processingInstructionCommon(String target, char[] content, int offset, int length) {
        if (this.stringizingLevel == 0) {
            closeStartTag();
            Consumer consumer = this.base;
            if (consumer instanceof XConsumer) {
                ((XConsumer) consumer).writeProcessingInstruction(target, content, offset, length);
            }
        } else if (this.stringizingElementNesting < 0) {
            this.base.write(content, offset, length);
        }
    }

    public void processingInstructionFromParser(char[] buffer, int tstart, int tlength, int dstart, int dlength) {
        if (tlength != 3 || inElement() || buffer[tstart] != 'x' || buffer[tstart + 1] != 'm' || buffer[tstart + 2] != 'l') {
            processingInstructionCommon(new String(buffer, tstart, tlength), buffer, dstart, dlength);
        }
    }

    public void startDocument() {
        closeStartTag();
        if (this.stringizingLevel > 0) {
            writeJoiner();
            return;
        }
        if (this.nesting == 0) {
            this.base.startDocument();
        } else {
            writeJoiner();
        }
        ensureSpaceInWorkStack(this.nesting);
        Object[] objArr = this.workStack;
        int i = this.nesting;
        objArr[i] = this.namespaceBindings;
        objArr[i + 1] = null;
        this.nesting = i + 2;
    }

    public void endDocument() {
        if (this.stringizingLevel > 0) {
            writeJoiner();
            return;
        }
        int i = this.nesting - 2;
        this.nesting = i;
        Object[] objArr = this.workStack;
        this.namespaceBindings = (NamespaceBinding) objArr[i];
        objArr[i] = null;
        objArr[i + 1] = null;
        if (i == 0) {
            this.base.endDocument();
        } else {
            writeJoiner();
        }
    }

    public void emitDoctypeDecl(char[] buffer, int target, int tlength, int data, int dlength) {
    }

    public void beginEntity(Object baseUri) {
        Consumer consumer = this.base;
        if (consumer instanceof XConsumer) {
            ((XConsumer) consumer).beginEntity(baseUri);
        }
    }

    public void endEntity() {
        Consumer consumer = this.base;
        if (consumer instanceof XConsumer) {
            ((XConsumer) consumer).endEntity();
        }
    }

    public XMLFilter append(char c) {
        write((int) c);
        return this;
    }

    public XMLFilter append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        append(csq, 0, csq.length());
        return this;
    }

    public XMLFilter append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, start, end - start);
        return this;
    }

    /* access modifiers changed from: package-private */
    public MappingInfo lookupTag(Symbol qname) {
        String local = qname.getLocalPart();
        String prefix = qname.getPrefix();
        if (prefix == "") {
            prefix = null;
        }
        String uri = qname.getNamespaceURI();
        int hash = MappingInfo.hash(prefix, local);
        int index = this.mappingTableMask & hash;
        MappingInfo first = this.mappingTable[index];
        for (MappingInfo info = first; info != null; info = info.nextInBucket) {
            if (qname == info.qname) {
                return info;
            }
            if (local == info.local && info.qname == null && ((uri == info.uri || info.uri == null) && prefix == info.prefix)) {
                info.uri = uri;
                info.qname = qname;
                return info;
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.qname = qname;
        info2.prefix = prefix;
        info2.uri = uri;
        info2.local = local;
        info2.tagHash = hash;
        info2.nextInBucket = first;
        this.mappingTable[index] = first;
        return info2;
    }

    /* access modifiers changed from: package-private */
    public MappingInfo lookupTag(char[] data, int start, int length) {
        int hash = 0;
        int prefixHash = 0;
        int colon = -1;
        for (int i = 0; i < length; i++) {
            char ch = data[start + i];
            if (ch != ':' || colon >= 0) {
                hash = (hash * 31) + ch;
            } else {
                colon = i;
                prefixHash = hash;
                hash = 0;
            }
        }
        int hash2 = hash ^ prefixHash;
        int index = this.mappingTableMask & hash2;
        MappingInfo first = this.mappingTable[index];
        for (MappingInfo info = first; info != null; info = info.nextInBucket) {
            if (hash2 == info.tagHash && info.match(data, start, length)) {
                return info;
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.tagHash = hash2;
        if (colon >= 0) {
            info2.prefix = new String(data, start, colon).intern();
            int colon2 = colon + 1;
            info2.local = new String(data, start + colon2, length - colon2).intern();
        } else {
            info2.prefix = null;
            info2.local = new String(data, start, length).intern();
        }
        info2.nextInBucket = first;
        this.mappingTable[index] = first;
        return info2;
    }

    private void ensureSpaceInWorkStack(int oldSize) {
        Object[] objArr = this.workStack;
        if (objArr == null) {
            this.workStack = new Object[20];
        } else if (oldSize >= objArr.length) {
            Object[] tmpn = new Object[(objArr.length * 2)];
            System.arraycopy(objArr, 0, tmpn, 0, oldSize);
            this.workStack = tmpn;
        }
    }

    private void ensureSpaceInStartIndexes(int oldSize) {
        int[] iArr = this.startIndexes;
        if (iArr == null) {
            this.startIndexes = new int[20];
        } else if (oldSize >= iArr.length) {
            int[] tmpn = new int[(iArr.length * 2)];
            System.arraycopy(iArr, 0, tmpn, 0, oldSize);
            this.startIndexes = tmpn;
        }
    }

    public static String duplicateAttributeMessage(Symbol attrSymbol, Object elementName) {
        StringBuffer sbuf = new StringBuffer("duplicate attribute: ");
        String uri = attrSymbol.getNamespaceURI();
        if (uri != null && uri.length() > 0) {
            sbuf.append('{');
            sbuf.append('}');
            sbuf.append(uri);
        }
        sbuf.append(attrSymbol.getLocalPart());
        if (elementName != null) {
            sbuf.append(" in <");
            sbuf.append(elementName);
            sbuf.append('>');
        }
        return sbuf.toString();
    }

    public void error(char severity, String message) {
        SourceMessages sourceMessages = this.messages;
        if (sourceMessages != null) {
            SourceLocator sourceLocator = this.locator;
            if (sourceLocator != null) {
                sourceMessages.error(severity, sourceLocator, message);
            } else {
                sourceMessages.error(severity, message);
            }
        } else {
            throw new RuntimeException(message);
        }
    }

    public boolean ignoring() {
        return this.ignoringLevel > 0;
    }

    public void setDocumentLocator(Locator locator2) {
        if (locator2 instanceof SourceLocator) {
            this.locator = (SourceLocator) locator2;
        }
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        startElement(Symbol.make(namespaceURI, localName));
        int numAttributes = atts.getLength();
        for (int i = 0; i < numAttributes; i++) {
            startAttribute(Symbol.make(atts.getURI(i), atts.getLocalName(i)));
            write(atts.getValue(i));
            endAttribute();
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) {
        endElement();
    }

    public void startElement(String name, AttributeList atts) {
        startElement(name.intern());
        int attrLength = atts.getLength();
        for (int i = 0; i < attrLength; i++) {
            String name2 = atts.getName(i).intern();
            String type = atts.getType(i);
            String value = atts.getValue(i);
            startAttribute(name2);
            write(value);
            endAttribute();
        }
    }

    public void endElement(String name) throws SAXException {
        endElement();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        write(ch, start, length);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        write(ch, start, length);
    }

    public void processingInstruction(String target, String data) {
        char[] chars = data.toCharArray();
        processingInstructionCommon(target, chars, 0, chars.length);
    }

    public void startPrefixMapping(String prefix, String uri) {
        this.namespaceBindings = findNamespaceBinding(prefix.intern(), uri.intern(), this.namespaceBindings);
    }

    public void endPrefixMapping(String prefix) {
        this.namespaceBindings = this.namespaceBindings.getNext();
    }

    public void skippedEntity(String name) {
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        LineBufferedReader lineBufferedReader = this.in;
        if (lineBufferedReader == null) {
            return null;
        }
        return lineBufferedReader.getName();
    }

    public String getFileName() {
        LineBufferedReader lineBufferedReader = this.in;
        if (lineBufferedReader == null) {
            return null;
        }
        return lineBufferedReader.getName();
    }

    public int getLineNumber() {
        int line;
        LineBufferedReader lineBufferedReader = this.in;
        if (lineBufferedReader != null && (line = lineBufferedReader.getLineNumber()) >= 0) {
            return line + 1;
        }
        return -1;
    }

    public int getColumnNumber() {
        LineBufferedReader lineBufferedReader = this.in;
        if (lineBufferedReader != null) {
            int columnNumber = lineBufferedReader.getColumnNumber();
            int i = columnNumber;
            if (columnNumber > 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean isStableSourceLocation() {
        return false;
    }
}
