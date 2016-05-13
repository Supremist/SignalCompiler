package compiler.SyntacticalAnalizer;

import compiler.lexan.Grammar;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.Arrays;

/**
 * Created by supremist on 4/12/16.
 */
public class TokenNode extends TreeNode {
    private Token token;

    public TokenNode(TokenIterator iterator)throws ParseException{
        super(iterator);
    }

    public TokenNode(){
        super();
    }

    public Token getToken(){return token;}
    public void setToken(Token token){this.token = token;}

    public TreeNode parse(TokenIterator iterator)throws ParseException{
        clearChildren();
        token = iterator.next();
        return this;
    }

    @Override
    public StringBuilder toStringTree(Grammar grammar){
        StringBuilder buffer = getLevelWhitespace();
        if (grammar != null) {
            buffer.append(token.findView(grammar))
                    .append(" ")
                    .append(toString())
                    .append("\n");
        }
        return buffer;
    }

    @Override
    public StringBuilder toXmlView(Grammar grammar){
        StringBuilder buffer = getLevelWhitespace();
        buffer.append("<").append(getClass().getSimpleName())
                .append(getXmlAttrs(grammar))
                .append("/>\n");
        return buffer;
    }

    @Override
    public StringBuilder getXmlAttrs(Grammar grammar){
        StringBuilder buffer = super.getXmlAttrs(grammar);
        buffer.append(" str=\"").append(token.findView(grammar)).append("\"");
        buffer.append(" type=\"").append(token.getType().toString()).append("\"");
        return buffer;
    }

    @Override
    public String toString() {
        return token.toString();
    }

}
