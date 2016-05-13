package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.NamedTreeNode;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class ConstantDeclaration extends NamedTreeNode {
    private Constant constant;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException{
        super.parse(iterator);
        parseExactTokenNode(iterator, 5); // "="
        constant = parseChild(iterator, Constant.class);
        parseExactTokenNode(iterator, 0); // ";"
        return this;
    }
}
