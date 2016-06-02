package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 5/8/16.
 */

public class Statement extends NamedTreeNode{
    public enum Type {LINK, IN, OUT}

    private Type type;
    private UnsignedConstant constant;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        int currentId = iterator.getCurrent().getId();
        Token current = iterator.getCurrent();
        if (current.isEqual(Token.Keyword.LINK)
                || current.isEqual(Token.Keyword.IN)
                || current.isEqual(Token.Keyword.OUT))
            type = Type.valueOf(current.getEnum().toString());
        else
            throw new ParseException("Invalid statement ", iterator.getCurrent().getPosition());
        if (type == Type.LINK) {
            parseExactTokenNode(iterator, Token.Keyword.LINK); //Keyword "LINK"
            parseIdentifier(iterator);
            parseExactTokenNode(iterator, Token.Delimiter.COMMA); // ","
        }
        else
            parseChild(iterator, TokenNode.class);
        constant = parseChild(iterator, UnsignedConstant.class);
        addChild(constant);
        parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ;
        return this;
    }
}
