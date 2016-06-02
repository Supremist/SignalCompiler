package compiler.SyntacticalAnalizer.Declarations.Variable;

import com.sun.corba.se.impl.oa.toa.TOA;
import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

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
        parseExactTokenNode(iterator, Token.Delimiter.COLON); // ":"
        attributes.parse(iterator);
        addChild(attributes);
        parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ";"
        initVariables();
        return this;
    }

    public void initVariables() throws CompileException{
        variables.clear();
        VariableType varType = new VariableType(attributes);
        for(NamedTreeNode name: variable_names.getItems())
            variables.add(new Variable(name, varType));
    }

    public List<Variable> getVariables(){
        return variables;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
