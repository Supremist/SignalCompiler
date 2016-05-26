package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.*;

/**
 * Created by supremist on 5/14/16.
 */

public class Variable implements Compilable{
    private NamedTreeNode name;
    private ExtendedVariableType type;

    public Variable(NamedTreeNode name, ExtendedVariableType type){
        this.name = name;
        this.type = type;
    }

    public NamedTreeNode getName(){
        return name;
    }

    public ExtendedVariableType getType(){
        return type;
    }

    public int getSize(){
        return 4; //TODO add realization
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException {
        StringBuilder buffer = new StringBuilder();
        if(type.isExtern())
            buffer.append("extern ");
        buffer.append(name.toAsmCode()).append(" ")
                .append(type.toAsmCode());
        return buffer;
    }
}
