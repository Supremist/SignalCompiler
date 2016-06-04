package compiler.SyntacticalAnalyzer;

import compiler.Exceptions.CompileException;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

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

    public String getIdentifierName(){
        return identifier.getToken().getView();
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

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        if (identifier != null)
            return new StringBuilder().append(identifier.getToken().getView());
        else
            return new StringBuilder();
    }
}
