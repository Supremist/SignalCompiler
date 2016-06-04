package compiler.SyntacticalAnalyzer;

import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Position;
import compiler.LexicalAnalyzer.Token;

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
    public StringBuilder toStringTree(){
        StringBuilder buffer = getLevelWhitespace();
            buffer.append(token.getView())
                    .append(" ")
                    .append(toString())
                    .append("\n");
        return buffer;
    }

    @Override
    public StringBuilder toXmlView(){
        StringBuilder buffer = getLevelWhitespace();
        buffer.append("<").append(getClass().getSimpleName())
                .append(getXmlAttrs())
                .append("/>\n");
        return buffer;
    }

    @Override
    public StringBuilder getXmlAttrs(){
        StringBuilder buffer = super.getXmlAttrs();
        buffer.append(" str=\"").append(token.getView()).append("\"");
        buffer.append(" type=\"").append(token.getType().toString()).append("\"");
        return buffer;
    }

    @Override
    public String toString() {
        return token.toString();
    }

    @Override
    public Position getPosition(){
        return getToken().getPosition();
    }

}
