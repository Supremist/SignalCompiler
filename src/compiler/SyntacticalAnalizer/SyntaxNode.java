package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/12/16.
 */
public abstract class SyntaxNode extends TreeNode {

    public TokenNode parseTokenNode(TokenIterator iterator) throws ParseException{
        TokenNode node = new TokenNode(iterator);
        addChild(node);
        return node;
    }

    public TokenNode parseExactTokenNode(TokenIterator iterator, int tokenId) throws ParseException{
        TokenNode node = parseTokenNode(iterator);
        if (node.getToken().getId() != tokenId)
            throw new ParseException(String.format("Token with id %d expected", tokenId), node.getToken().getPosition());
        return node;
    }
}
