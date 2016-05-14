package compiler.SyntacticalAnalizer.Exprission;

/**
 * Created by supremist on 5/8/16.
 */

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AddInstruction extends ListedTokenNode {

    private static final Integer[] ADD_INSTRUCTIONS  = {1, 2, 15}; // "+", "-", "!"

    public AddInstruction() throws ParseException {
        super(Arrays.asList(ADD_INSTRUCTIONS),
                "Add instruction expected ");
    }

}
