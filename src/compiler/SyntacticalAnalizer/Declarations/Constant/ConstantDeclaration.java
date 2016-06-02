package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class ConstantDeclaration extends NamedTreeNode implements IConstantValue, Compilable{
    private Constant constant;
    private ConstantValue value;
    private boolean isCalculating = false; // flag for detection circular dependency

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException{
        super.parse(iterator);
        parseExactTokenNode(iterator, Token.Delimiter.EQUAL); // "="
        constant = parseChild(iterator, Constant.class);
        value = null;
        parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ";"
        return this;
    }

    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException{
        if(isCalculating){
            throw new CompileException("Circular dependency found while calculating constant", getPosition());
        }
        if (value == null) {
            isCalculating = true;
            value = constant.getConstantValue(constantTable);
            isCalculating = false;
        }
        return value;
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException{
        StringBuilder buffer = new StringBuilder();
        if (value == null)
            throw new CompileException("Constant value not set", getPosition());
        ConstantValueWrapper compiler = new ConstantValueWrapper(value);
        buffer.append(super.toAsmCode()).append(" ").append(compiler.toAsmCode());
        return buffer;
    }

    public boolean equals(Object other){
        return  (other instanceof ConstantDeclaration) && this.getIdentifier().getToken().equals(
                ((ConstantDeclaration) other).getIdentifier().getToken());
    }
}
