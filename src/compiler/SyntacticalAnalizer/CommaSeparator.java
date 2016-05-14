package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/13/16.
 */
public class CommaSeparator extends TokenNode{

    public CommaSeparator() throws ParseException {
        super();
    }

    @Override
    public TreeNode parse(TokenIterator iterator)throws ParseException{
        super.parse(iterator);
        if(getToken().getId() != 3) // ","
            throw new ParseException("Comma expected ", getToken().getPosition());
        return this;
    }
}