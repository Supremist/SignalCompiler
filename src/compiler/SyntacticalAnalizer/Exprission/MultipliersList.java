package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;
import compiler.SyntacticalAnalizer.Declarations.Constant.IConstantTable;
import compiler.SyntacticalAnalizer.Declarations.Constant.IConstantValue;
import compiler.SyntacticalAnalizer.SyntaxList;
import compiler.SyntacticalAnalizer.TreeNode;

import java.util.Iterator;

/**
 * Created by supremist on 4/22/16.
 */

public class MultipliersList extends SyntaxList<MultiplierItem> implements IConstantValue{

    public MultipliersList() {
        super(MultiplierItem.class, MultiplicationInstruction.class);
    }

    @Override
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        Iterator<TreeNode> iterator = getChildren().listIterator();
        ConstantValue current = ((MultiplierItem) iterator.next()).getConstantValue(constantTable);
        while (iterator.hasNext()){
            MultiplicationInstruction instruction = (MultiplicationInstruction) iterator.next();
            MultiplierItem multiplier = (MultiplierItem) iterator.next();
            try {
                current = instruction.calc(current, multiplier.getConstantValue(constantTable));
            } catch (IllegalArgumentException ex){
                throw new CompileException(ex.getMessage(), instruction.getToken().getPosition());
            }
        }
        return current;
    }
}
