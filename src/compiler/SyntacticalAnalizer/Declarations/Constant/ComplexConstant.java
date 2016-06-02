package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class ComplexConstant extends TreeNode {

    private ComplexNumber number;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        parseExactTokenNode(iterator, Token.Delimiter.QUOTE); // symbol '
        number = parseChild(iterator, ComplexNumber.class);
        parseExactTokenNode(iterator, Token.Delimiter.QUOTE); // symbol '
        return this;
    }

    public ComplexNumber getComplexNumber(){return number;}
}
