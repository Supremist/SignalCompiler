package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantValue;

/**
 * Created by supremist on 5/27/16.
 */
public interface IInstruction {
    ConstantValue calc(ConstantValue value1, ConstantValue value2) throws IllegalArgumentException;
}
