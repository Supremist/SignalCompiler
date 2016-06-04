package compiler.SyntacticalAnalyzer.Declarations.Constant;

import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.SyntacticalAnalyzer.TokenNode;
import compiler.SyntacticalAnalyzer.TreeNode;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class UnsignedConstant extends TokenNode {
    @Override
    public TreeNode parse(TokenIterator iterator)throws ParseException {
        if (iterator.getCurrent().getType() == Token.Type.CONSTANT)
            super.parse(iterator);
        else
            throw new ParseException("UnsignedConstant expected ", iterator.getCurrent().getPosition());
        return this;
    }
}
