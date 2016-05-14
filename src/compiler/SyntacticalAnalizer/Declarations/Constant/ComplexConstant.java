package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class ComplexConstant extends TreeNode {

    private ComplexNumber number;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        parseExactTokenNode(iterator, 8); // symbol '
        number = parseChild(iterator, ComplexNumber.class);
        parseExactTokenNode(iterator, 8); // symbol '
        return this;
    }
}
