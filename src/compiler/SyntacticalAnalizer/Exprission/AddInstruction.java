package compiler.SyntacticalAnalizer.Exprission;

/**
 * Created by supremist on 5/8/16.
 */

import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.lexan.ParseException;
import compiler.lexan.Token;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AddInstruction extends ListedTokenNode implements IInstruction{

    private static final Integer[] ADD_INSTRUCTIONS  = {1, 2, 15}; // "+", "-", "!"

    public AddInstruction() throws ParseException {
        super(Arrays.asList(ADD_INSTRUCTIONS),
                "Add instruction expected ");
    }

    public ConstantValue calc(ConstantValue value1, ConstantValue value2) throws IllegalArgumentException{
        if (getToken().getId() == 1){
            return value1.add(value2);
        }
        else if(getToken().getId() == 2){
            return value1.subrtact(value2);
        }
        else if(getToken().getId() == 15){
            return new ConstantValue(value1.bitOr(value2));
        }
        return new ConstantValue();
    }

}
