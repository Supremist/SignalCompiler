package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Position;

/**
 * Created by supremist on 5/18/16.
 */
public class CompileException extends ParseException {
    public CompileException () { super(); }
    public CompileException (String message, Position position) {
        super(message, position);
    }
    public CompileException (String message, Position position, Throwable cause) {
        super(message , position, cause);
    }
    public CompileException (Throwable cause) { super(cause); }
}
