package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.NamedTreeNode;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
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
            parseExactTokenNode(iterator, 9);
            //TODO Parse Expression
            parseExactTokenNode(iterator, 10); // delimiter ")"
        }
        else if(iterator.getNext().getType() == Token.Type.CONSTANT)
            parseTokenNode(iterator);
        return this;
    }
}
