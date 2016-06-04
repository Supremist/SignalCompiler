package compiler.SyntacticalAnalyzer;

import compiler.Exceptions.CompileException;

/**
 * Created by supremist on 5/20/16.
 */
public interface Compilable {
    StringBuilder toAsmCode(CompilationInfo info) throws CompileException;
    void Compile(CompilationInfo info) throws  CompileException;
}
