package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.Compilable;
import compiler.SyntacticalAnalizer.CompilationInfo;
import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableType;

/**
 * Created by supremist on 6/1/16.
 */
public class ConstantValueWrapper implements Compilable, IConstantValue{

    private ConstantValue mValue;

    public ConstantValueWrapper(ConstantValue value){
        mValue = value;
    }


    public static String floatToAsm(double value){
    //TODO Add mantissa and power generation
        return String.valueOf(value);
    }

    public String getCode(){
        if (mValue.isInteger()){
            return String.valueOf(mValue.getInteger());
        }
        else if(mValue.isFloat()){
            return floatToAsm(mValue.getReal());
        }
        else {
            return  "<"+String.valueOf(mValue.getReal()) + ", " +
                        String.valueOf(mValue.getImagine()) + "> ";
        }
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        StringBuilder buffer = new StringBuilder();
        if (mValue.isInteger()){
            buffer.append(VariableType.BaseType.INTEGER.getAsmType()).append(" ")
                    .append(mValue.getInteger()).append("\n");
        }
        else if(mValue.isFloat()){
            buffer.append(VariableType.BaseType.FLOAT.getAsmType()).append(" ")
                    .append(floatToAsm(mValue.getReal())).append("\n");
        }
        else if(mValue.isComplexInt()){
            buffer.append(String.format(
                    VariableType.COMPLEX_IMPLEMENTATION_TEMPLATE,
                    VariableType.BaseType.INTEGER.toString(),
                    String.valueOf((int) mValue.getReal()),
                    String.valueOf((int) mValue.getImagine())));
        }
        else {
            buffer.append(String.format(
                    VariableType.COMPLEX_IMPLEMENTATION_TEMPLATE,
                    VariableType.BaseType.FLOAT.toString(),
                    String.valueOf(mValue.getReal()),
                    String.valueOf(mValue.getImagine())));
        }
        return buffer;
    }

    @Override
    public void Compile(CompilationInfo info) throws CompileException {

    }


    @Override
    public ConstantValue getConstantValue(IConstantTable constantTable) throws CompileException {
        return mValue;
    }
}
