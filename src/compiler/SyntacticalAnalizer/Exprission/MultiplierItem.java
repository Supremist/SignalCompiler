package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/22/16.
 */
public class MultiplierItem extends NamedTreeNode {

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        if(iterator.getNext().getId() == 9) {  // delimiter "("
            parseExactTokenNode(iterator, 9); // delimiter "("
            parseChild(iterator, Expression.class); //TODO add if branches, save properties
            parseExactTokenNode(iterator, 10); // delimiter ")"
        }
        else if(iterator.getNext().getType() == Token.Type.CONSTANT)
            parseChild(iterator, UnsignedConstant.class);
        return this;
    }
}
