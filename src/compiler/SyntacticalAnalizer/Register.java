package compiler.SyntacticalAnalizer;

import compiler.lexan.Token;

/**
 * Created by supremist on 6/3/16.
 */
public class Register {
    public enum Type {IN, OUT}
    private Token variable;
    private int number;
    private Type type;

    public Register(Token variableIdentifier, int number, Type type){
        this.variable = variableIdentifier;
        this.number = number;
        this.type = type;
    }

    public Register(Token variableIdentifier, int number){
        this(variableIdentifier, number, null);
    }

    public void setType(Token action) throws CompileException{
        if(type == null){
            type = Type.valueOf(action.getEnum().toString());
        }
        else
            throw new CompileException("Register type already set", action.getPosition());
    }

}
