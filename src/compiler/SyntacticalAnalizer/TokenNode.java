package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/12/16.
 */
public class TokenNode extends TreeNode {
    private Token token;

    public TokenNode(TokenIterator iterator)throws ParseException{
        super(iterator);
    }

    public Token getToken(){return token;}
    public void setToken(Token token){this.token = token;}

    public TreeNode parse(TokenIterator iterator)throws ParseException{
        token = iterator.next();
        return this;
    }

}
