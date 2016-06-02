package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Position;
import compiler.lexan.Token;

import java.util.List;

/**
 * Created by supremist on 4/12/16.
 */
public class TokenIterator {

    private List<Token> list;
    private int current;
    private int savedPos;

    public TokenIterator (List<Token> list){
        this.list = list;
        savedPos = current = -1;
    }

    public Token next() throws ParseException{
        Token result = getCurrent();
        current++;
        return result;
    }

    public Token getCurrent() throws ParseException{
        if (hasNext())
            return list.get(current+1);
        else if(current>0)
            throw new ParseException("Unexpected end", list.get(current).getPosition());
        else
            throw new ParseException("Unexpected end", new Position());
    }

    public boolean hasNext(){
        return current<list.size()-1;
    }

    public void savePosition(){
        savedPos = current;
    }

    public void seekBack(){
        current = savedPos;
    }
}
