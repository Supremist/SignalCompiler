package compiler.SyntacticalAnalyzer.Declarations.Constant;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.SyntacticalAnalyzer.TokenNode;
import compiler.SyntacticalAnalyzer.TreeNode;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class Constant extends TreeNode implements IConstantValue{
    private boolean isMinus;
    private ComplexConstant complexConstant;
    private UnsignedConstant unsignedConstant;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        clearChildren();
        if (iterator.getCurrent().isEqual(Token.Delimiter.MINUS)) { // "-"
            isMinus = true;
            parseChild(iterator, TokenNode.class);
        }
        else
            isMinus = false;

        if (iterator.getCurrent().isEqual(Token.Delimiter.QUOTE)) { // symbol '
            complexConstant = parseChild(iterator, ComplexConstant.class);
            unsignedConstant= null;
        }
        else {
            complexConstant = null;
            unsignedConstant = parseChild(iterator, UnsignedConstant.class);
        }
        return this;
    }

    public boolean isComplex(){
        return complexConstant != null;
    }

    public boolean isUnsigned(){
        return unsignedConstant != null;
    }

    @Override
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        ConstantValue result;
        if(isComplex()){
            result = complexConstant.getComplexNumber().getConstantValue(constantTable);
        }
        else {
            result = new ConstantValue(unsignedConstant.getToken().getConstant());
        }
        if (isMinus)
            return result.unaryMinus();
        else
            return result;
    }
}
