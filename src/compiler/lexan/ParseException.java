package compiler.lexan;

/**
 * Created by supremist on 3/24/16.
 */
public class ParseException extends Exception{
    public ParseException () { super(); }
    public ParseException (String message) { super(message); }
    public ParseException (String message, Throwable cause) { super(message, cause); }
    public ParseException (Throwable cause) { super(cause); }
}
