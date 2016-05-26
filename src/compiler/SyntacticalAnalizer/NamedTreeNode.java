package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.List;

/**
 * Created by supremist on 4/11/16.
 */
public class NamedTreeNode extends TreeNode implements  Compilable {
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

    public void checkIdentifierUniqueness(List<String> identifiers) throws CompileException{
        String identifierView =identifier.getToken().getView();
        if(identifiers.indexOf(identifierView) != -1)
            throw new CompileException("Identifier is not unique ", identifier.getToken().getPosition());
        else
            identifiers.add(identifierView);
    }

    @Override
    public String toString() {
        return super.toString() + " " + identifier.toString();
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException {
        return new StringBuilder().append(identifier.getToken().getView());
    }
}
