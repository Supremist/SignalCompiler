package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class ConstantDeclaration extends NamedTreeNode implements IConstantValue{
    private Constant constant;
    private ConstantValue value;
    private boolean isCalculating = false;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException{
        super.parse(iterator);
        parseExactTokenNode(iterator, 5); // "="
        constant = parseChild(iterator, Constant.class);
        value = null;
        parseExactTokenNode(iterator, 0); // ";"
        return this;
    }

    public ConstantValue getValue(){
        return value;
    }

    public ConstantValue calcConstantValue(ConstantDeclarations declarations) throws CompileException{
        if(isCalculating){
            throw new CompileException("Circular dependency found while calculating constant",
                    ((TokenNode) getLastChild()).getToken().getPosition());
        }
        if (value == null) {
            isCalculating = true;
            value = constant.calcConstantValue(declarations);
            isCalculating = false;
        }
        return value;
    }

    public boolean equals(Object other){
        return  (other instanceof ConstantDeclaration) && this.getIdentifier().getToken().equals(
                ((ConstantDeclaration) other).getIdentifier().getToken());
    }
}
