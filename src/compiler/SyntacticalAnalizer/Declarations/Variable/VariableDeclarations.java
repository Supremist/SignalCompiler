package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 5/14/16.
 */
public class VariableDeclarations extends SyntaxList<VariableDeclaration> implements Compilable {
    private List<Variable> variableList;
    private int size;

    public VariableDeclarations(){
        super(VariableDeclaration.class);
        variableList = new ArrayList<>();
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        super.parse(iterator);
        initVariableList();
        return this;
    }

    private void initVariableList(){
        variableList.clear();
        size = 0;
        for(VariableDeclaration item: getItems()){
            variableList.addAll(item.getVariables());
            for (Variable var: item.getVariables())
                size += var.getSize();
        }
    }

    public void checkIdentifierUniqueness(List<String> identifiers) throws CompileException {
        for (Variable variable: variableList)
            variable.getName().checkIdentifierUniqueness(identifiers);
    }

    public List<Variable> getVariableList(){
        return variableList;
    }

    public int getSize(){
        return size;
    }


    @Override
    public StringBuilder toAsmCode() throws CompileException {
        StringBuilder buffer = new StringBuilder();
        for (Variable variable: variableList){
            buffer.append(variable.toAsmCode()).append("\n");
        }
        return buffer;
    }
}
