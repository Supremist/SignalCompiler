package compiler.SyntacticalAnalizer;

/**
 * Created by supremist on 5/20/16.
 */
public interface Compilable {
    StringBuilder toAsmCode() throws CompileException;
}
