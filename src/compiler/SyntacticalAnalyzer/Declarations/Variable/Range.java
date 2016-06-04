package compiler.SyntacticalAnalyzer.Declarations.Variable;

import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.SyntacticalAnalyzer.TokenNode;
import compiler.SyntacticalAnalyzer.TreeNode;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

/**
 * Created by supremist on 4/17/16.
 */
public class Range extends TreeNode {

    private  Token leftToken, rightToken;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        leftToken = parsePart(iterator).getToken();
        parseExactTokenNode(iterator, Token.Delimiter.DOUBLE_DOT);
        rightToken = parsePart(iterator).getToken();
        return this;
    }

    private TokenNode parsePart(TokenIterator iterator) throws ParseException{
        TokenNode node = parseChild(iterator, TokenNode.class);
        if (node.getToken().getType() != Token.Type.CONSTANT)
            throw new ParseException("Constant expected", node.getToken().getPosition());
        if (!node.getToken().isInteger())
            throw new ParseException("Integer expected", node.getToken().getPosition());
        return node;
    }


    public Token getLeft(){return leftToken;}
    public Token getRight(){return rightToken;}
    public int getLength(){return  rightToken.getInteger() - leftToken.getInteger() + 1;}

    @Override
    public String toString() {
        return super.toString();
    }

}
