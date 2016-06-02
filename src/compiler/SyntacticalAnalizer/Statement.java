package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */

public class Statement extends NamedTreeNode{
    public enum Type {LINK, IN, OUT}

    private Type type;
    private UnsignedConstant constant;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        int currentId = iterator.getNext().getId();
        if (currentId >= 413 && currentId <= 415)//Keyword "LINK" "IN" "OUT"
            type = Type.values()[currentId-413];
        else
            throw new ParseException("Invalid statement ", iterator.getNext().getPosition());
        if (type == Type.LINK) {
            parseExactTokenNode(iterator, 413); //Keyword "LINK"
            parseIdentifier(iterator);
            parseExactTokenNode(iterator, 3); // ","
        }
        else
            parseChild(iterator, TokenNode.class);
        constant = parseChild(iterator, UnsignedConstant.class);
        addChild(constant);
        parseExactTokenNode(iterator, 0); // ;
        return this;
    }
}
