package compiler.SyntacticalAnalizer;

/**
 * Created by supremist on 5/20/16.
 */
public interface Compilable {
    StringBuilder toAsmCode(CompilationInfo info) throws CompileException;
    void Compile(CompilationInfo info) throws  CompileException;
}
