package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.SyntaxList;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 5/14/16.
 */
public class VariableDeclarations extends SyntaxList<VariableDeclaration> {
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

    public List<Variable> getVariableList(){
        return variableList;
    }

    public int getSize(){
        return size;
    }


}
