package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;

/**
 * Created by supremist on 4/13/16.
 */
public class Declaration extends SyntaxNode {

    private SyntaxList<NamedTreeNode> variables;
    private SyntaxList<Attribute> attributes;

    public Declaration(){
        super();
        variables = new SyntaxList<>(NamedTreeNode.class, 3); // separator = ","
        attributes = new SyntaxList<>(Attribute.class); // separator = None
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
}
