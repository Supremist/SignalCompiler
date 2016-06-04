package compiler.SyntacticalAnalyzer.Declarations.Constant;

import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.SyntacticalAnalyzer.TreeNode;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

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
