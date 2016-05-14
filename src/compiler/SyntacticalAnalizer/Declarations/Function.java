package compiler.SyntacticalAnalizer.Declarations;

import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.SyntacticalAnalizer.Exprission.Expression;
import compiler.SyntacticalAnalizer.NamedTreeNode;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Function extends NamedTreeNode {

    private Expression expression;
    private UnsignedConstant[] characteristic;

    public Function(){
        super();
        characteristic = new UnsignedConstant[2];
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        super.parse(iterator);
        parseExactTokenNode(iterator, 5); // "="
        expression = parseChild(iterator, Expression.class);
        parseExactTokenNode(iterator, 17); // "\"
        characteristic[0] = parseChild(iterator, UnsignedConstant.class);
        parseExactTokenNode(iterator, 3); // ","
        characteristic[1] = parseChild(iterator, UnsignedConstant.class);
        parseExactTokenNode(iterator, 0); // ";"
        return this;
    }

}
