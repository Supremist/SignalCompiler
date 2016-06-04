package compiler.SyntacticalAnalyzer.Declarations.Variable;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.*;
import compiler.LexicalAnalyzer.Token;

/**
 * Created by supremist on 5/14/16.
 */

public class Variable implements Compilable{
    private NamedTreeNode name;
    private VariableType type;

    public Variable(NamedTreeNode name, VariableType type){
        this.name = name;
        this.type = type;
    }

    public NamedTreeNode getName(){
        return name;
    }

    public VariableType getType(){
        return type;
    }

    public int getSize(){
        return type.getSize();
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        StringBuilder buffer = new StringBuilder();
        if (type.isExtern())
            buffer.append("extern ");
        buffer.append(name.toAsmCode(info)).append(" ")
                .append(type.toAsmCode(info));
        buffer.append("\n");
        return buffer;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Variable){
            return  this.name.getIdentifier().getToken().equals(
                    ((Variable) other).getName().getIdentifier().getToken());
        }
        else if(other instanceof Token){
            return this.name.getIdentifier().getToken().equals(other);
        }
        else {
            return false;
        }
    }

    @Override
    public void Compile(CompilationInfo info) throws CompileException{
        info.addIdentifier(name.getIdentifier().getToken());
    }
}
