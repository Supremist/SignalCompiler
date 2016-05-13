package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TokenNode;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Constant extends TreeNode {
    private boolean isMinus;
    private ComplexConstant complexConstant;
    private UnsignedConstant unsignedConstant;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        if (iterator.getNext().getId() == 2) { // "-"
            isMinus = true;
            parseChild(iterator, TokenNode.class);
        }
        else
            isMinus = false;

        if (iterator.getNext().getId() == 17) { // symbol '
            complexConstant = parseChild(iterator, ComplexConstant.class);
            unsignedConstant= null;
        }
        else {
            complexConstant = null;
            unsignedConstant = parseChild(iterator, UnsignedConstant.class);
        }
        return this;
    }
}