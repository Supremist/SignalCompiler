package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.SyntacticalAnalizer.Declarations.Variable.Variable;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableType;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.HashMap;
import java.util.List;

/**
 * Created by supremist on 5/8/16.
 */

public class Statement extends NamedTreeNode{
    public enum Type {LINK, IN, OUT}

    private Type type;
    private UnsignedConstant constant;
    private TokenNode action;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        int currentId = iterator.getCurrent().getId();
        Token current = iterator.getCurrent();
        if (current.isEqual(Token.Keyword.LINK)
                || current.isEqual(Token.Keyword.IN)
                || current.isEqual(Token.Keyword.OUT))
            type = Type.valueOf(current.getEnum().toString());
        else
            throw new ParseException("Invalid statement ", iterator.getCurrent().getPosition());
        action = parseChild(iterator, TokenNode.class);
        if (type == Type.LINK) {
            parseIdentifier(iterator);
            parseExactTokenNode(iterator, Token.Delimiter.COMMA); // ","
        }
        constant = parseChild(iterator, UnsignedConstant.class);
        addChild(constant);
        parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ;
        return this;
    }

    public void Compile(CompilationInfo info) throws CompileException{
        if(!constant.getToken().isInteger())
            throw new CompileException("Register number should be integer", constant.getPosition());
        HashMap<Integer, Register> registers = info.getRegisters();
        Integer numberValue = constant.getToken().getInteger();
        if(type == Type.LINK){
            List<Variable> variables = info.getDeclarations().getVariableDeclarations().getVariableList();
            int variableIndex = 0;
            while(variableIndex < variables.size() &&
                    !variables.get(variableIndex++).getName().getIdentifier().getToken()
                            .equals(getIdentifier().getToken())){

            }
            if(variableIndex == variables.size()){
                throw new CompileException("Variable not found", getIdentifier().getPosition());
            }
            if(!variables.get(variableIndex).getType().isSignal()){
                throw new CompileException("Variable declared as non-signal", getIdentifier().getPosition());
            }
            if(!registers.containsKey(numberValue)){
                registers.put(numberValue, new Register(getIdentifier().getToken(), numberValue));
            }
            else{
                throw new CompileException("Register already linked", constant.getPosition());
            }
        }
        else{
            if(registers.containsKey(numberValue)){
                registers.get(numberValue).setType(action.getToken());
            }
            else{
                throw new CompileException("Register not linked yet", constant.getPosition());
            }
        }
    }
}
