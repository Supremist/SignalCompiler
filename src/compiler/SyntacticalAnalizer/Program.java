package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.Iterator;

/**
 * Created by supremist on 4/11/16.
 */
public class Program extends NamedSyntaxNode {

    public void parse(TokenIterator iterator) throws ParseException{
        Token token = iterator.next();
        parseIdenifier(iterator);
        parseExactToken(iterator, 0); // ;
        if(token.getId() == 400) { //PROGRAM

        }
    }
}
