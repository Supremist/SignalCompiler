package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.CompileException;

/**
 * Created by supremist on 5/27/16.
 */
public interface IConstantValue {
    ConstantValue calcConstantValue(ConstantDeclarations declarations) throws CompileException;
}