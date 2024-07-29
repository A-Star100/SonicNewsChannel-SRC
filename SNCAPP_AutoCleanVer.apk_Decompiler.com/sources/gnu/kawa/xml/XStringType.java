package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.util.regex.Pattern;

public class XStringType extends XDataType {
    public static final XStringType ENTITYType;
    public static final XStringType IDREFType;
    public static final XStringType IDType;
    public static final XStringType NCNameType;
    public static final XStringType NMTOKENType;
    public static final XStringType NameType;
    static ClassType XStringType = ClassType.make("gnu.kawa.xml.XString");
    public static final XStringType languageType;
    public static final XStringType normalizedStringType;
    public static final XStringType tokenType;
    Pattern pattern;

    static {
        XStringType xStringType = new XStringType("normalizedString", stringType, 39, (String) null);
        normalizedStringType = xStringType;
        XStringType xStringType2 = new XStringType("token", xStringType, 40, (String) null);
        tokenType = xStringType2;
        languageType = new XStringType("language", xStringType2, 41, "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*");
        NMTOKENType = new XStringType("NMTOKEN", xStringType2, 42, "\\c+");
        XStringType xStringType3 = new XStringType("Name", xStringType2, 43, (String) null);
        NameType = xStringType3;
        XStringType xStringType4 = new XStringType("NCName", xStringType3, 44, (String) null);
        NCNameType = xStringType4;
        IDType = new XStringType("ID", xStringType4, 45, (String) null);
        IDREFType = new XStringType("IDREF", xStringType4, 46, (String) null);
        ENTITYType = new XStringType("ENTITY", xStringType4, 47, (String) null);
    }

    public XStringType(String name, XDataType base, int typeCode, String pattern2) {
        super(name, XStringType, typeCode);
        this.baseType = base;
        if (pattern2 != null) {
            this.pattern = Pattern.compile(pattern2);
        }
    }

    public boolean isInstance(Object obj) {
        if (!(obj instanceof XString)) {
            return false;
        }
        for (XDataType objType = ((XString) obj).getStringType(); objType != null; objType = objType.baseType) {
            if (objType == this) {
                return true;
            }
        }
        return false;
    }

    public String matches(String value) {
        boolean status;
        boolean status2 = true;
        switch (this.typeCode) {
            case 39:
            case 40:
                if (value != TextUtils.replaceWhitespace(value, this.typeCode != 39)) {
                    status2 = false;
                }
                status = status2;
                break;
            case 42:
                status = XName.isNmToken(value);
                break;
            case 43:
                status = XName.isName(value);
                break;
            case 44:
            case 45:
            case 46:
            case 47:
                status = XName.isNCName(value);
                break;
            default:
                Pattern pattern2 = this.pattern;
                if (pattern2 != null && !pattern2.matcher(value).matches()) {
                    status2 = false;
                }
                status = status2;
                break;
        }
        if (status) {
            return null;
        }
        return "not a valid XML " + getName();
    }

    public Object valueOf(String value) {
        String value2 = TextUtils.replaceWhitespace(value, this != normalizedStringType);
        if (matches(value2) == null) {
            return new XString(value2, this);
        }
        throw new ClassCastException("cannot cast " + value2 + " to " + this.name);
    }

    public Object cast(Object value) {
        if (value instanceof XString) {
            XString xvalue = (XString) value;
            if (xvalue.getStringType() == this) {
                return xvalue;
            }
        }
        return valueOf((String) stringType.cast(value));
    }

    public static XString makeNCName(String value) {
        return (XString) NCNameType.valueOf(value);
    }
}
