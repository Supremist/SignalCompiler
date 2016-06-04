package compiler.SyntacticalAnalizer;


import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.SyntacticalAnalizer.Declarations.Declarations;
import compiler.lexan.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by supremist on 6/2/16.
 */
public class CompilationInfo {
    private Declarations declarations;
    private List<Token> identifiers;
    private HashMap<Integer, Register> registers;
    private boolean isProcedure;
    private Token programName;

    public CompilationInfo (){
        identifiers = new ArrayList<>();
        registers = new HashMap<>();
    }

    public void  setProcedure(boolean isProcedure){
        this.isProcedure = isProcedure;
    }

    public void setDeclarations(Declarations declarations){
        this.declarations = declarations;
    }

    public void setProgramName(Token programName){
        this.programName = programName;
    }

    public void addIdentifier(Token identifier) throws CompileException{
        if(identifiers.indexOf(identifier) != -1)
            throw new CompileException("Identifier is not unique ", identifier.getPosition());
        else
            identifiers.add(identifier);
    }

    public HashMap<Integer, Register> getRegisters(){
        return registers;
    }

    public boolean isProcedure(){
        return isProcedure;
    }
    public List<Token> getIdentifiers(){
        return identifiers;
    }

    public Token getProgramName(){
        return programName;
    }


    public Declarations getDeclarations(){
        return declarations;
    }
}
