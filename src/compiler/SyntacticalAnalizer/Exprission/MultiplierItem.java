package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.*;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 4/22/16.
 */
public class MultiplierItem extends NamedTreeNode implements IConstantValue{

    private UnsignedConstant constant;
    private Expression expression;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        if(iterator.getCurrent().isEqual(Token.Delimiter.OPEN_BRACKET)) {  // delimiter "("
            parseExactTokenNode(iterator, Token.Delimiter.OPEN_BRACKET); // delimiter "("
            expression = parseChild(iterator, Expression.class);
            constant = null;
            parseExactTokenNode(iterator, Token.Delimiter.CLOSE_BRACKET); // delimiter ")"
        }
        else if(iterator.getCurrent().getType() == Token.Type.CONSTANT) {
            constant = parseChild(iterator, UnsignedConstant.class);
            expression = null;
        }
        else if(iterator.getCurrent().getType() == Token.Type.IDENTIFIER) {
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
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        if(isConstant()){
            return new ConstantValue(constant.getToken().getConstant());
        }
        else if (isIdentifier() && !constantTable.isEmpty()){
            IConstantValue current = constantTable.getConstantByName(getIdentifier().getToken().getView());
            if(current != null)
                return current.getConstantValue(constantTable);
            else
                throw new UnknownIdentifierException("Constant excepted", getIdentifier().getPosition());
        }
        else if(isExpression()){
            return expression.getConstantValue(constantTable);
        }
        return new ConstantValue();
    }
}
