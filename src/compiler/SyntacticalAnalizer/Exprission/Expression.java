package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantDeclarations;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;
import compiler.SyntacticalAnalizer.Declarations.Constant.IConstantValue;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Expression extends TreeNode implements IConstantValue{

    private boolean isMinus = false;
    private SummandList summandList;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        if (iterator.getNext().getId() == 2) { // '-'
            parseChild(iterator, AddInstruction.class);
            isMinus = true;
        }
        summandList = parseChild(iterator, SummandList.class);
        return this;
    }


    @Override
    public ConstantValue calcConstantValue(ConstantDeclarations declarations) throws CompileException {
        if (isMinus)
            return summandList.calcConstantValue(declarations).unaryMinus();
        else
            return summandList.calcConstantValue(declarations);
    }
}
