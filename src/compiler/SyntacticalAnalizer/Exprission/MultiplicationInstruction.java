package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by supremist on 5/8/16.
 */
public class MultiplicationInstruction extends ListedTokenNode{

    private static Integer [] MULTIPLICATION_INSTRUCTIONS  = {11, 12, 13, 314}; // "*", "/", "&", "MOD"

    public MultiplicationInstruction() throws ParseException {
        super(Arrays.asList(MULTIPLICATION_INSTRUCTIONS).stream()
                        .map(Token::fromId).collect(Collectors.toList()),
                "Multiplication instruction expected ");
    }
}