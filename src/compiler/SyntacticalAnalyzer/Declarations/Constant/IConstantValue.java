package compiler.SyntacticalAnalyzer.Declarations.Constant;

import compiler.Exceptions.CompileException;

/**
 * Created by supremist on 5/27/16.
 */
public interface IConstantValue {
    ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException;
}
