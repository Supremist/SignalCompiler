package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TokenNode;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class UnsignedConstant extends TokenNode {
    @Override
    public TreeNode parse(TokenIterator iterator)throws ParseException {
        if (iterator.getNext().getType() == Token.Type.CONSTANT)
            super.parse(iterator);
        else
            throw new ParseException("UnsignedConstant expected ", iterator.getNext().getPosition());
        return this;
    }
}
