package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.lexan.Position;

/**
 * Created by supremist on 6/1/16.
 */
public class UnknownIdentifierException extends CompileException {
    public UnknownIdentifierException () { super(); }
    public UnknownIdentifierException (String message, Position position) {
        super(message, position);
    }
    public UnknownIdentifierException (String message, Position position, Throwable cause) {
        super(message , position, cause);
    }
    public UnknownIdentifierException (Throwable cause) { super(cause); }
}
