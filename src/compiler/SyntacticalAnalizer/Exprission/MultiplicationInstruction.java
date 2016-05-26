package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;
import compiler.lexan.ParseException;
import java.util.Arrays;

/**
 * Created by supremist on 5/8/16.
 */
public class MultiplicationInstruction extends ListedTokenNode implements IInstruction{

    private static Integer [] MULTIPLICATION_INSTRUCTIONS  = {11, 12, 13, 314}; // "*", "/", "&", "MOD"

    public MultiplicationInstruction() throws ParseException {
        super(Arrays.asList(MULTIPLICATION_INSTRUCTIONS),
                "Multiplication instruction expected ");
    }


    @Override
    public ConstantValue calc(ConstantValue value1, ConstantValue value2) throws IllegalArgumentException {
        if (getToken().getId() == 11){
            return value1.multiply(value2);
        }
        else if(getToken().getId() == 12){
            return value1.divide(value2);
        }
        else if(getToken().getId() == 13){
            return new ConstantValue(value1.bitAnd(value2));
        }
        else if(getToken().getId() == 314){
            return new ConstantValue(value1.mod(value2));
        }
        return new ConstantValue();
    }
}