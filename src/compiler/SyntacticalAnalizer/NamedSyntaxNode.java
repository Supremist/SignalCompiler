package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/11/16.
 */
public abstract class NamedSyntaxNode extends SyntaxNode {
    private Token identifier;

    public Token getIdentifier(){return identifier;}
    public void setIdentifier(Token identifier){
        this.identifier = identifier;

    }
    public void parseIdenifier(TokenIterator iterator) throws ParseException{
        Token identifier = iterator.next();
        if(identifier.getType() == Token.Type.IDENTIFIER)
            setIdentifier(identifier);
        else
            throw new ParseException("Identifier expected", identifier.getPosition());
    }

}
