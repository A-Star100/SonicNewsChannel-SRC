package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class DefineNamespace extends Syntax {
    public static final String XML_NAMESPACE_MAGIC = "&xml&";
    public static final DefineNamespace define_namespace;
    public static final DefineNamespace define_private_namespace;
    public static final DefineNamespace define_xml_namespace;
    private boolean makePrivate;
    private boolean makeXML;

    static {
        DefineNamespace defineNamespace = new DefineNamespace();
        define_namespace = defineNamespace;
        DefineNamespace defineNamespace2 = new DefineNamespace();
        define_private_namespace = defineNamespace2;
        DefineNamespace defineNamespace3 = new DefineNamespace();
        define_xml_namespace = defineNamespace3;
        defineNamespace.setName("define-namespace");
        defineNamespace2.setName("define-private-namespace");
        defineNamespace2.makePrivate = true;
        defineNamespace3.setName("define-xml-namespace");
        defineNamespace3.makeXML = true;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Expression value;
        Namespace namespace;
        if (st.getCdr() instanceof Pair) {
            Pair pair = (Pair) st.getCdr();
            Pair p1 = pair;
            if ((pair.getCar() instanceof Symbol) && (p1.getCdr() instanceof Pair)) {
                Pair pair2 = (Pair) p1.getCdr();
                Pair p2 = pair2;
                if (pair2.getCdr() == LList.Empty) {
                    Symbol name = (Symbol) p1.getCar();
                    Declaration decl = defs.getDefine(name, 'w', tr);
                    tr.push(decl);
                    decl.setFlag(2375680);
                    if (this.makePrivate) {
                        decl.setFlag(16777216);
                        decl.setPrivate(true);
                    } else if (defs instanceof ModuleExp) {
                        decl.setCanRead(true);
                    }
                    Translator.setLine(decl, (Object) p1);
                    if (p2.getCar() instanceof CharSequence) {
                        String literal = p2.getCar().toString();
                        if (literal.startsWith("class:")) {
                            namespace = ClassNamespace.getInstance(literal, ClassType.make(literal.substring(6)));
                            decl.setType(ClassType.make("gnu.kawa.lispexpr.ClassNamespace"));
                        } else if (this.makeXML) {
                            namespace = XmlNamespace.getInstance(name.getName(), literal);
                            decl.setType(ClassType.make("gnu.kawa.xml.XmlNamespace"));
                        } else {
                            namespace = Namespace.valueOf(literal);
                            decl.setType(ClassType.make("gnu.mapping.Namespace"));
                        }
                        value = new QuoteExp(namespace);
                        decl.setFlag(8192);
                    } else {
                        value = tr.rewrite_car(p2, false);
                    }
                    decl.noteValue(value);
                    forms.addElement(SetExp.makeDefinition(decl, value));
                    return true;
                }
            }
        }
        tr.error('e', "invalid syntax for define-namespace");
        return false;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return tr.syntaxError("define-namespace is only allowed in a <body>");
    }
}
