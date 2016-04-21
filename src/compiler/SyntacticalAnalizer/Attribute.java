package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;

/**
 * Created by supremist on 4/13/16.
 */

public class Attribute extends SyntaxNode{
    public enum Type {SIGNAL, COMPLEX, INTEGER, FLOAT, BLOCKFLOAT, EXT, RANGE}

    private Type type;
    private SyntaxList<Range> ranges;

    public Attribute (){
        super();
        ranges = new SyntaxList<>(Range.class, 3); // separator = ","
    }

    public Type getType(){return type;}

    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        int currentTokenId = iterator.getNext().getId();
        if(currentTokenId >= 406 && currentTokenId <= 411) { // All attributes
            type = Type.values()[currentTokenId - 406]; // all numbers are keyword indexes LOL
            parseTokenNode(iterator);
        }
        else {
            type = Type.RANGE;
            ranges.parse(iterator);
        }
        return this;
    }
}