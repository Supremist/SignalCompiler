package compiler.SyntacticalAnalyzer.Exprission;

import compiler.SyntacticalAnalyzer.Declarations.Constant.ConstantValue;

/**
 * Created by supremist on 5/27/16.
 */
public interface IInstruction {
    ConstantValue calc(ConstantValue value1, ConstantValue value2) throws IllegalArgumentException;
}
