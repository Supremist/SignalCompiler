package compiler.SyntacticalAnalyzer.Exprission;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.Declarations.Constant.ConstantValue;
import compiler.SyntacticalAnalyzer.Declarations.Constant.IConstantTable;
import compiler.SyntacticalAnalyzer.Declarations.Constant.IConstantValue;
import compiler.SyntacticalAnalyzer.SyntaxList;
import compiler.SyntacticalAnalyzer.TreeNode;

import java.util.Iterator;

/**
 * Created by supremist on 4/30/16.
 */

public class SummandList extends SyntaxList<MultipliersList> implements IConstantValue{

    public SummandList() {
        super(MultipliersList.class, AddInstruction.class);
    }

    @Override
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        Iterator<TreeNode> iterator = getChildren().listIterator();
        ConstantValue current = ((MultipliersList) iterator.next()).getConstantValue(constantTable);
        while (iterator.hasNext()){
            AddInstruction instruction = (AddInstruction) iterator.next();
            MultipliersList summand = (MultipliersList) iterator.next();
            try {
                current = instruction.calc(current, summand.getConstantValue(constantTable));
            } catch (IllegalArgumentException ex){
                throw new CompileException(ex.getMessage(), instruction.getToken().getPosition());
            }
        }
        return current;
    }
}
