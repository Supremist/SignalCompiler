package compiler.SyntacticalAnalyzer.Exprission;

import compiler.SyntacticalAnalyzer.Declarations.Constant.ConstantValue;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

import java.util.Arrays;

/**
 * Created by supremist on 5/8/16.
 */
public class MultiplicationInstruction extends ListedTokenNode implements IInstruction{

    private static Token.TokenEnum[] MULTIPLICATION_INSTRUCTIONS  = {
            Token.Delimiter.ASTERISK,
            Token.Delimiter.SLASH,
            Token.Delimiter.AND,
            Token.Delimiter.MOD}; // "*", "/", "&", "MOD"

    public MultiplicationInstruction() throws ParseException {
        super(Arrays.asList(MULTIPLICATION_INSTRUCTIONS),
                "Multiplication instruction expected ");
    }


    @Override
    public ConstantValue calc(ConstantValue value1, ConstantValue value2) throws IllegalArgumentException {
        if (getToken().isEqual(Token.Delimiter.ASTERISK)){
            return value1.multiply(value2);
        }
        else if(getToken().isEqual(Token.Delimiter.SLASH)){
            return value1.divide(value2);
        }
        else if(getToken().isEqual(Token.Delimiter.AND)){
            return new ConstantValue(value1.bitAnd(value2));
        }
        else if(getToken().isEqual(Token.Delimiter.MOD)){
            return new ConstantValue(value1.mod(value2));
        }
        return new ConstantValue();
    }
}