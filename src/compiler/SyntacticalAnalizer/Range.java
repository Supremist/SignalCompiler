package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/17/16.
 */
public class Range extends SyntaxNode {

    public TreeNode parse(TokenIterator iterator) throws ParseException {
        TokenNode node = parseTokenNode(iterator);
        if (node.getToken().getType() != Token.Type.CONSTANT)
            throw new ParseException("Unexpected symbol", node.getToken().getPosition());
        node = parseTokenNode(iterator);
        if (node.getToken().getId() != 306) // ".."
            throw new ParseException("Expected \"..\"", node.getToken().getPosition());
        node = parseTokenNode(iterator);
        if (node.getToken().getType() != Token.Type.CONSTANT)
            throw new ParseException("Unexpected symbol", node.getToken().getPosition());
        return this;
    }

}
