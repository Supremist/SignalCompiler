package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 4/13/16.
 */
public class VariableDeclaration extends TreeNode {

    private SyntaxList<NamedTreeNode> variables;
    private SyntaxList<Attribute> attributes;

    public VariableDeclaration(){
        super();
        variables = new SyntaxList<>(NamedTreeNode.class, CommaSeparator.class); // separator = ","
        attributes = new SyntaxList<>(Attribute.class, SyntaxList.EmptySeparator.class); // separator = None
    }

    public TreeNode parse(TokenIterator iterator) throws ParseException{
        clearChildren();
        variables.parse(iterator);
        addChild(variables);
        parseExactTokenNode(iterator, 4); // ":"
        attributes.parse(iterator);
        addChild(attributes);
        parseExactTokenNode(iterator, 0); // ";"
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
