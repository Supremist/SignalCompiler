package compiler.SyntacticalAnalyzer.Declarations.Constant;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.Exprission.Expression;
import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.SyntacticalAnalyzer.TokenNode;
import compiler.SyntacticalAnalyzer.TreeNode;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

/**
 * Created by supremist on 5/8/16.
 */

public class ComplexNumber extends TreeNode implements IConstantValue {

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
                throw new ParseException("Invalid complex number ", iterator.getCurrent().getPosition());
        }
        else
            left = null;
        return this;
    }

    private boolean tryParseRightPart(TokenIterator iterator) throws ParseException{
        Token current = iterator.getCurrent();
        if(current.isEqual(Token.Delimiter.COMMA)) { // ","
            parseChild(iterator, TokenNode.class);
            right = parseChild(iterator, Expression.class);
        }
        else if (current.isEqual(Token.Delimiter.EXP)) { // "$EXP"
            parseChild(iterator, TokenNode.class);
            parseExactTokenNode(iterator, Token.Delimiter.OPEN_BRACKET); // "("
            right = parseChild(iterator, Expression.class);
            parseExactTokenNode(iterator, Token.Delimiter.CLOSE_BRACKET); // ")"
            isExp = true;
        }
        else
            return false;
        return true;
    }

    @Override
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        ConstantValue leftValue  = left.getConstantValue(constantTable);
        ConstantValue rightValue = right.getConstantValue(constantTable);
        if (leftValue.isFloat() && rightValue.isFloat()){
            if(isExp){
                return ConstantValue.fromExp(leftValue.getReal(), rightValue.getReal());
            }
            else {
                return new ConstantValue(leftValue.getReal(), rightValue.getReal());
            }
        }
        else {
            throw new CompileException("Complex parts should be real", left.getPosition());
        }
    }
}
