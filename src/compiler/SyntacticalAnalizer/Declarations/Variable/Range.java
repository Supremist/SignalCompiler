package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TokenNode;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/17/16.
 */
public class Range extends TreeNode {

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        TokenNode node = parseChild(iterator, TokenNode.class);
        if (node.getToken().getType() != Token.Type.CONSTANT)
            throw new ParseException("Unexpected symbol", node.getToken().getPosition());
        node = parseChild(iterator, TokenNode.class);
        if (node.getToken().getId() != 306) // ".."
            throw new ParseException("Expected \"..\"", node.getToken().getPosition());
        node = parseChild(iterator, TokenNode.class);
        if (node.getToken().getType() != Token.Type.CONSTANT)
            throw new ParseException("Unexpected symbol", node.getToken().getPosition());
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
