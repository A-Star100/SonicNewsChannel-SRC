package gnu.ecmascript;

import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.lists.Sequence;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Vector;
import kawa.standard.Scheme;

public class Parser {
    public static final Expression[] emptyArgs = new Expression[0];
    static Expression emptyStatement = new QuoteExp(Values.empty);
    public static Expression eofExpr = new QuoteExp(Sequence.eofValue);
    public int errors;
    Lexer lexer;
    InPort port;
    Object previous_token;
    Object token;

    public Parser(InPort port2) {
        this.port = port2;
        this.lexer = new Lexer(port2);
    }

    public Expression parseConditionalExpression() throws IOException, SyntaxException {
        Expression exp1 = parseBinaryExpression(1);
        if (peekToken() != Lexer.condToken) {
            return exp1;
        }
        skipToken();
        Expression exp2 = parseAssignmentExpression();
        if (getToken() != Lexer.colonToken) {
            return syntaxError("expected ':' in conditional expression");
        }
        return new IfExp(exp1, exp2, parseAssignmentExpression());
    }

    public Expression parseAssignmentExpression() throws IOException, SyntaxException {
        Expression exp1 = parseConditionalExpression();
        Object token2 = peekToken();
        if (token2 == Lexer.equalToken) {
            skipToken();
            Expression exp2 = parseAssignmentExpression();
            if (!(exp1 instanceof ReferenceExp)) {
                return syntaxError("unmplemented non-symbol ihs in assignment");
            }
            SetExp sex = new SetExp((Object) ((ReferenceExp) exp1).getName(), exp2);
            sex.setDefining(true);
            return sex;
        } else if (!(token2 instanceof Reserved)) {
            return exp1;
        } else {
            Reserved op = (Reserved) token2;
            if (!op.isAssignmentOp()) {
                return exp1;
            }
            skipToken();
            Expression[] args = {exp1, parseAssignmentExpression()};
            return new ApplyExp((Expression) new QuoteExp(op.proc), args);
        }
    }

    public Expression parseExpression() throws IOException, SyntaxException {
        Expression[] exps = null;
        int nExps = 0;
        while (true) {
            Expression exp1 = parseAssignmentExpression();
            boolean last = peekToken() != Lexer.commaToken;
            if (exps != null) {
                int length = exps.length;
                if (!last ? length <= nExps : length != nExps + 1) {
                    Expression[] new_exps = new Expression[(last ? nExps + 1 : exps.length * 2)];
                    System.arraycopy(exps, 0, new_exps, 0, nExps);
                    exps = new_exps;
                }
            } else if (last) {
                return exp1;
            } else {
                exps = new Expression[2];
            }
            int newsize = nExps + 1;
            exps[nExps] = exp1;
            if (last) {
                return new BeginExp(exps);
            }
            skipToken();
            nExps = newsize;
        }
    }

    public Object peekTokenOrLine() throws IOException, SyntaxException {
        if (this.token == null) {
            this.token = this.lexer.getToken();
        }
        return this.token;
    }

    public Object peekToken() throws IOException, SyntaxException {
        if (this.token == null) {
            this.token = this.lexer.getToken();
        }
        while (this.token == Lexer.eolToken) {
            skipToken();
            this.token = this.lexer.getToken();
        }
        return this.token;
    }

    public Object getToken() throws IOException, SyntaxException {
        Object result = peekToken();
        skipToken();
        return result;
    }

    public final void skipToken() {
        if (this.token != Lexer.eofToken) {
            this.previous_token = this.token;
            this.token = null;
        }
    }

    public void getSemicolon() throws IOException, SyntaxException {
        Object peekToken = peekToken();
        this.token = peekToken;
        if (peekToken == Lexer.semicolonToken) {
            skipToken();
        } else if (this.token != Lexer.rbraceToken && this.token != Lexer.eofToken && this.previous_token != Lexer.eolToken) {
            syntaxError("missing ';' after expression");
        }
    }

    public Expression parsePrimaryExpression() throws IOException, SyntaxException {
        Object result = getToken();
        if (result instanceof QuoteExp) {
            return (QuoteExp) result;
        }
        if (result instanceof String) {
            return new ReferenceExp((Object) (String) result);
        }
        if (result != Lexer.lparenToken) {
            return syntaxError("unexpected token: " + result);
        }
        Expression expr = parseExpression();
        Object token2 = getToken();
        if (token2 != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token2);
        }
        return expr;
    }

    public Expression makePropertyAccessor(Expression exp, Expression prop) {
        return null;
    }

    public Expression[] parseArguments() throws IOException, SyntaxException {
        skipToken();
        if (peekToken() == Lexer.rparenToken) {
            skipToken();
            return emptyArgs;
        }
        Vector args = new Vector(10);
        while (true) {
            args.addElement(parseAssignmentExpression());
            Object token2 = getToken();
            if (token2 == Lexer.rparenToken) {
                Expression[] exps = new Expression[args.size()];
                args.copyInto(exps);
                return exps;
            } else if (token2 != Lexer.commaToken) {
                syntaxError("invalid token '" + token2 + "' in argument list");
            }
        }
    }

    public Expression makeNewExpression(Expression exp, Expression[] args) {
        if (args == null) {
            args = emptyArgs;
        }
        return new ApplyExp((Expression) null, args);
    }

    public Expression makeCallExpression(Expression exp, Expression[] args) {
        return new ApplyExp(exp, args);
    }

    public String getIdentifier() throws IOException, SyntaxException {
        Object token2 = getToken();
        if (token2 instanceof String) {
            return (String) token2;
        }
        syntaxError("missing identifier");
        return "??";
    }

    public Expression parseLeftHandSideExpression() throws IOException, SyntaxException {
        int newCount = 0;
        while (peekToken() == Lexer.newToken) {
            newCount++;
            skipToken();
        }
        Expression exp = parsePrimaryExpression();
        while (true) {
            Object token2 = peekToken();
            if (token2 == Lexer.dotToken) {
                skipToken();
                exp = makePropertyAccessor(exp, new QuoteExp(getIdentifier()));
            } else if (token2 == Lexer.lbracketToken) {
                skipToken();
                Expression prop = parseExpression();
                Object token3 = getToken();
                if (token3 != Lexer.rbracketToken) {
                    return syntaxError("expected ']' - got:" + token3);
                }
                exp = makePropertyAccessor(exp, prop);
            } else if (token2 == Lexer.lparenToken) {
                Expression[] args = parseArguments();
                System.err.println("after parseArgs:" + peekToken());
                if (newCount > 0) {
                    exp = makeNewExpression(exp, args);
                    newCount--;
                } else {
                    exp = makeCallExpression(exp, args);
                }
            } else {
                while (newCount > 0) {
                    exp = makeNewExpression(exp, (Expression[]) null);
                    newCount--;
                }
                return exp;
            }
        }
    }

    public Expression parsePostfixExpression() throws IOException, SyntaxException {
        Expression exp = parseLeftHandSideExpression();
        Object op = peekTokenOrLine();
        if (op != Reserved.opPlusPlus && op != Reserved.opMinusMinus) {
            return exp;
        }
        skipToken();
        return new ApplyExp((Expression) new QuoteExp(((Reserved) op).proc), exp);
    }

    public Expression parseUnaryExpression() throws IOException, SyntaxException {
        return parsePostfixExpression();
    }

    public Expression syntaxError(String message) {
        this.errors++;
        OutPort err = OutPort.errDefault();
        String current_filename = this.port.getName();
        int current_line = this.port.getLineNumber() + 1;
        int current_column = this.port.getColumnNumber() + 1;
        if (current_line > 0) {
            if (current_filename != null) {
                err.print(current_filename);
            }
            err.print(':');
            err.print(current_line);
            if (current_column > 1) {
                err.print(':');
                err.print(current_column);
            }
            err.print(": ");
        }
        err.println(message);
        return new ErrorExp(message);
    }

    public Expression parseBinaryExpression(int prio) throws IOException, SyntaxException {
        Expression exp1 = parseUnaryExpression();
        while (true) {
            Object peekToken = peekToken();
            this.token = peekToken;
            if (!(peekToken instanceof Reserved)) {
                return exp1;
            }
            Reserved op = (Reserved) peekToken;
            if (op.prio < prio) {
                return exp1;
            }
            getToken();
            QuoteExp quoteExp = new QuoteExp(op.proc);
            exp1 = new ApplyExp((Expression) quoteExp, exp1, parseBinaryExpression(op.prio + 1));
        }
    }

    public Expression parseIfStatement() throws IOException, SyntaxException {
        Expression else_part;
        skipToken();
        Object token2 = getToken();
        if (token2 != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token2);
        }
        Expression test_part = parseExpression();
        Object token3 = getToken();
        if (token3 != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token3);
        }
        Expression then_part = parseStatement();
        if (peekToken() == Lexer.elseToken) {
            skipToken();
            else_part = parseStatement();
        } else {
            else_part = null;
        }
        return new IfExp(test_part, then_part, else_part);
    }

    public Expression buildLoop(Expression init, Expression test, Expression incr, Expression body) {
        if (init != null) {
            return new BeginExp(new Expression[]{init, buildLoop((Expression) null, test, incr, body)});
        }
        throw new Error("not implemented - buildLoop");
    }

    public Expression parseWhileStatement() throws IOException, SyntaxException {
        skipToken();
        Object token2 = getToken();
        if (token2 != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token2);
        }
        Expression test_part = parseExpression();
        Object token3 = getToken();
        if (token3 != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token3);
        }
        return buildLoop((Expression) null, test_part, (Expression) null, parseStatement());
    }

    public Expression parseFunctionDefinition() throws IOException, SyntaxException {
        skipToken();
        String name = getIdentifier();
        Object token2 = getToken();
        if (token2 != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token2);
        }
        Vector args = new Vector(10);
        if (peekToken() == Lexer.rparenToken) {
            skipToken();
        } else {
            while (true) {
                args.addElement(getIdentifier());
                Object token3 = getToken();
                if (token3 == Lexer.rparenToken) {
                    break;
                } else if (token3 != Lexer.commaToken) {
                    syntaxError("invalid token '" + token3 + "' in argument list");
                }
            }
        }
        LambdaExp lexp = new LambdaExp(parseBlock());
        lexp.setName(name);
        SetExp sexp = new SetExp((Object) name, (Expression) lexp);
        sexp.setDefining(true);
        return sexp;
    }

    public Expression parseBlock() throws IOException, SyntaxException {
        boolean last;
        Expression[] exps = null;
        if (getToken() != Lexer.lbraceToken) {
            return syntaxError("extened '{'");
        }
        int nExps = 0;
        while (true) {
            Object peekToken = peekToken();
            this.token = peekToken;
            if (peekToken == Lexer.rbraceToken) {
                skipToken();
                if (exps == null) {
                    return emptyStatement;
                }
                last = true;
            } else {
                last = false;
            }
            if (exps == null) {
                exps = new Expression[2];
            } else {
                int length = exps.length;
                if (!last ? length <= nExps : length != nExps) {
                    Expression[] new_exps = new Expression[(last ? nExps : exps.length * 2)];
                    System.arraycopy(exps, 0, new_exps, 0, nExps);
                    exps = new_exps;
                }
            }
            if (last) {
                return new BeginExp(exps);
            }
            exps[nExps] = parseStatement();
            nExps++;
        }
    }

    public Expression parseStatement() throws IOException, SyntaxException {
        Object token2 = peekToken();
        if (token2 instanceof Reserved) {
            switch (((Reserved) token2).prio) {
                case 31:
                    return parseIfStatement();
                case 32:
                    return parseWhileStatement();
                case 41:
                    return parseFunctionDefinition();
            }
        }
        if (token2 == Lexer.eofToken) {
            return eofExpr;
        }
        if (token2 == Lexer.semicolonToken) {
            skipToken();
            return emptyStatement;
        } else if (token2 == Lexer.lbraceToken) {
            return parseBlock();
        } else {
            Expression exp = parseExpression();
            getSemicolon();
            return exp;
        }
    }

    public static void main(String[] args) {
        new Scheme();
        InPort inp = InPort.inDefault();
        if (inp instanceof TtyInPort) {
            ((TtyInPort) inp).setPrompter(new Prompter());
        }
        Parser parser = new Parser(inp);
        OutPort out = OutPort.outDefault();
        while (true) {
            try {
                Expression expr = parser.parseStatement();
                if (expr != eofExpr) {
                    out.print("[Expression: ");
                    expr.print(out);
                    out.println("]");
                    Object result = expr.eval(Environment.user());
                    out.print("result: ");
                    out.print(result);
                    out.println();
                } else {
                    return;
                }
            } catch (Throwable ex) {
                System.err.println("caught exception:" + ex);
                ex.printStackTrace(System.err);
                return;
            }
        }
    }
}
