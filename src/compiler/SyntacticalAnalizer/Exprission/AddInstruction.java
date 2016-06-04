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

    private static final Token.TokenEnum [] ADD_INSTRUCTIONS  = {Token.Delimiter.PLUS,
            Token.Delimiter.MINUS, Token.Delimiter.BANG, Token.Delimiter.XOR}; // "+", "-", "!", "XOR"

    public AddInstruction() throws ParseException {
        super(Arrays.asList(ADD_INSTRUCTIONS),
                "Add instruction expected ");
    }

    public ConstantValue calc(ConstantValue value1, ConstantValue value2) throws IllegalArgumentException{
        if (getToken().isEqual(Token.Delimiter.PLUS)){
            return value1.add(value2);
        }
        else if(getToken().isEqual(Token.Delimiter.MINUS)){
            return value1.subrtact(value2);
        }
        else if(getToken().isEqual(Token.Delimiter.BANG)){
            return new ConstantValue(value1.bitOr(value2));
        }
        else if(getToken().isEqual(Token.Delimiter.XOR)){
            return new ConstantValue(value1.bitXor(value2));
        }
        return new ConstantValue();
    }

}
