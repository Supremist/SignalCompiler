package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.Exprission.Expression;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TokenNode;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class ComplexNumber extends TreeNode {

    private Expression left;
    private Expression right;
    private boolean isExp;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        isExp = false;
        if (!tryParseRightPart(iterator)){
            left = parseChild(iterator, Expression.class);
            if (!tryParseRightPart(iterator))
                throw new ParseException("Invalid complex number ", iterator.getNext().getPosition());
        }
        else
            left = null;
        return this;
    }

    private boolean tryParseRightPart(TokenIterator iterator) throws ParseException{
        int currentId = iterator.getNext().getId();
        if(currentId == 3) { // ","
            parseChild(iterator, TokenNode.class);
            right = parseChild(iterator, Expression.class);
        }
        else if (currentId == 316) { // "$EXP"
            parseChild(iterator, TokenNode.class);
            parseExactTokenNode(iterator, 9); // "("
            right = parseChild(iterator, Expression.class);
            parseExactTokenNode(iterator, 10); // ")"
            isExp = true;
        }
        else
            return false;
        return true;
    }

}
