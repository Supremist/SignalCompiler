package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/11/16.
 */
public class NamedTreeNode extends TreeNode{
    private TokenNode identifier;

    public TokenNode getIdentifier(){return identifier;}

    public void parseIdentifier(TokenIterator iterator) throws ParseException{
        TokenNode identifier = parseChild(iterator, TokenNode.class);
        if(identifier.getToken().getType() == Token.Type.IDENTIFIER) {
            this.identifier = identifier;
        }
        else
            throw new ParseException("Identifier expected", identifier.getToken().getPosition());
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        parseIdentifier(iterator);
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " " + identifier.toString();
    }
}
