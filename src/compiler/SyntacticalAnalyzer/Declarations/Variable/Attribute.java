package compiler.SyntacticalAnalyzer.Declarations.Variable;

import compiler.SyntacticalAnalyzer.*;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

import java.util.List;

/**
 * Created by supremist on 4/13/16.
 */

public class Attribute extends TreeNode {
    public enum Type {SIGNAL, COMPLEX, INTEGER, FLOAT, BLOCKFLOAT, EXT, RANGE}

    private Type type;
    private SyntaxList<Range> ranges;

    public Attribute (){
        super();
        ranges = new SyntaxList<>(Range.class, CommaSeparator.class); // separator = ","
    }

    public Type getType(){return type;}
    public List<Range> getRanges(){return ranges.getItems();}

    @Override
    public String toString(){
        return super.toString()+" "+type.toString();
    }

    private Type toAttributeType(Token token) throws IllegalArgumentException{
        if(token.getType() == Token.Type.KEYWORD) {
            return Type.valueOf(token.getEnum().toString());
        }
        else
            throw new IllegalArgumentException();
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        try {
            type = toAttributeType(iterator.getCurrent());
            parseChild(iterator, TokenNode.class);
        }catch (IllegalArgumentException ex){
            type = Type.RANGE;
            ranges.parse(iterator);
            addChild(ranges);
        }
        return this;
    }
}