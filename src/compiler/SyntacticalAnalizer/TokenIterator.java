package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by supremist on 4/12/16.
 */
public class TokenIterator {

    private Token current;
    private Iterator<Token> iterator;

    public TokenIterator (Iterator<Token> iterator){
        this.iterator = iterator;
    }

    public Token next() throws ParseException{
        if (iterator.hasNext()) {
            current = iterator.next();
            return current;
        }
        else
            throw new ParseException("Unexpected end", current.getPosition());
    }

    public boolean hasNext(){
        return iterator.hasNext();
    }
}
