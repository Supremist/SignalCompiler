package compiler.Exceptions;

import compiler.LexicalAnalyzer.Position;

/**
 * Created by supremist on 3/24/16.
 */
public class ParseException extends Exception{
    public ParseException () { super(); }
    public ParseException (String message, Position position) {
        super(message + String.format(" in %d line at %d symbol.", position.getLine(), position.getColumn()));
    }
    public ParseException (String message, Position position, Throwable cause) {
        super(message + String.format(" in %d line at %d symbol.", position.getLine(), position.getColumn()), cause);
    }
    public ParseException (Throwable cause) { super(cause); }
}
