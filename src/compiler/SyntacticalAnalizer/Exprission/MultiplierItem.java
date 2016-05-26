package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.*;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.Iterator;

/**
 * Created by supremist on 4/22/16.
 */
public class MultiplierItem extends NamedTreeNode implements IConstantValue{

    private UnsignedConstant constant;
    private Expression expression;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        if(iterator.getNext().getId() == 9) {  // delimiter "("
            parseExactTokenNode(iterator, 9); // delimiter "("
            expression = parseChild(iterator, Expression.class);
            constant = null;
            parseExactTokenNode(iterator, 10); // delimiter ")"
        }
        else if(iterator.getNext().getType() == Token.Type.CONSTANT) {
            constant = parseChild(iterator, UnsignedConstant.class);
            expression = null;
        }
        else if(iterator.getNext().getType() == Token.Type.IDENTIFIER) {
            constant = null;
            expression = null;
            parseIdentifier(iterator);
        }
        return this;
    }

    public boolean isConstant(){
        return constant != null;
    }

    public boolean isIdentifier(){
        return getIdentifier() != null;
    }

    public boolean isExpression(){
        return expression != null;
    }

    @Override
    public ConstantValue calcConstantValue(ConstantDeclarations declarations) throws CompileException {
        if(isConstant()){
            return new ConstantValue(constant.getToken().getConstant());
        }
        else if (isIdentifier() && !declarations.isEmpty()){
            ConstantDeclaration current = declarations.getConstantByName(getIdentifier().getToken().getView());
            if(current != null)
                return current.calcConstantValue(declarations);
            else
                throw new CompileException("Identifier is not constant", getIdentifier().getPosition());
        }
        else if(isExpression()){
            return expression.calcConstantValue(declarations);
        }
        return new ConstantValue();
    }
}
