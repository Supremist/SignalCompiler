package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Expression extends TreeNode{

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        AddInstruction sign = new AddInstruction();
        return this;
    }
}
