package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;
import compiler.SyntacticalAnalizer.Declarations.Constant.IConstantTable;
import compiler.SyntacticalAnalizer.Declarations.Constant.IConstantValue;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 5/8/16.
 */
public class Expression extends TreeNode implements IConstantValue{

    private boolean isMinus = false;
    private SummandList summandList;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        if (iterator.getCurrent().isEqual(Token.Delimiter.MINUS)) { // '-'
            parseChild(iterator, AddInstruction.class);
            isMinus = true;
        }
        summandList = parseChild(iterator, SummandList.class);
        return this;
    }

    public List<MultiplierItem> getItems(){
        List<MultiplierItem> result = new ArrayList<>();
        for(MultipliersList multipliersList: summandList.getItems()){
            result.addAll(multipliersList.getItems());
        }
        return result;
    }


    @Override
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        if (isMinus)
            return summandList.getConstantValue(constantTable).unaryMinus();
        else
            return summandList.getConstantValue(constantTable);
    }
}
