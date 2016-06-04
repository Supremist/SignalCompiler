package compiler.SyntacticalAnalyzer.Declarations.Variable;

import compiler.Exceptions.CompileException;
import compiler.LexicalAnalyzer.Token;
import compiler.SyntacticalAnalyzer.*;

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

    public List<Variable> getVariableList(){
        return variableList;
    }

    public Variable getVariable(Token name){
        for(Variable variable: variableList){
            if(variable.getName().getIdentifier().getToken().equals(name)){
                return variable;
            }
        }
        return null;
    }

    public int getSize(){
        return size;
    }

    public void Compile(CompilationInfo info) throws CompileException {
        super.Compile(info);
        initVariableList();
    }

}
