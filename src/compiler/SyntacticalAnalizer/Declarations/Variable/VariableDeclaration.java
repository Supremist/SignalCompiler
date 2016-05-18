package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/13/16.
 */
public class VariableDeclaration extends TreeNode {

    private SyntaxList<NamedTreeNode> variable_names;
    private SyntaxList<Attribute> attributes;
    private List<Variable> variables;

    public VariableDeclaration(){
        super();
        variable_names = new SyntaxList<>(NamedTreeNode.class, CommaSeparator.class); // separator = ","
        attributes = new SyntaxList<>(Attribute.class, SyntaxList.EmptySeparator.class); // separator = None
        variables = new ArrayList<>();
    }

    public TreeNode parse(TokenIterator iterator) throws ParseException{
        clearChildren();
        variable_names.parse(iterator);
        addChild(variable_names);
        parseExactTokenNode(iterator, 4); // ":"
        attributes.parse(iterator);
        addChild(attributes);
        parseExactTokenNode(iterator, 0); // ";"
        if (variable_names.getItems().size() != attributes.getItems().size())
            throw new ParseException("Variables size should match attributes size",
                    iterator.getNext().getPosition());
        variables.clear();
        for (int i = 0; i<attributes.getItems().size(); ++i)
            variables.add(new Variable(variable_names.get(i), attributes.get(i)));
        return this;
    }

    public List<Variable> getVariables(){
        return variables;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
