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

    public void Compile(CompilationInfo info) throws CompileException{
        super.Compile(info);
        initVariableList();
    }

}
