package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantDeclarations;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;
import compiler.SyntacticalAnalizer.Declarations.Constant.IConstantValue;
import compiler.SyntacticalAnalizer.SyntaxList;
import compiler.SyntacticalAnalizer.TreeNode;

import java.util.Iterator;

/**
 * Created by supremist on 4/30/16.
 */

public class SummandList extends SyntaxList<MultipliersList> implements IConstantValue{

    public SummandList() {
        super(MultipliersList.class, AddInstruction.class);
    }

    @Override
    public ConstantValue calcConstantValue(ConstantDeclarations declarations) throws CompileException {
        Iterator<TreeNode> iterator = getChildren().listIterator();
        ConstantValue current = ((MultipliersList) iterator.next()).calcConstantValue(declarations);
        while (iterator.hasNext()){
            AddInstruction instruction = (AddInstruction) iterator.next();
            MultipliersList summand = (MultipliersList) iterator.next();
            try {
                current = instruction.calc(current, summand.calcConstantValue(declarations));
            } catch (IllegalArgumentException ex){
                throw new CompileException(ex.getMessage(), instruction.getToken().getPosition());
            }
        }
        return current;
    }
}
