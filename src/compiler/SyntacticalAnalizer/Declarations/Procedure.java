package compiler.SyntacticalAnalizer.Declarations;

import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclaration;
import compiler.SyntacticalAnalizer.NamedTreeNode;
import compiler.SyntacticalAnalizer.SyntaxList;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Procedure extends NamedTreeNode {

    private SyntaxList<VariableDeclaration> parameters;

    public Procedure(){
        super();
        parameters = new SyntaxList<VariableDeclaration>(VariableDeclaration.class);
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        parseExactTokenNode(iterator, 401); //Keyword "PROCEDURE"
        super.parse(iterator);
        if (iterator.getNext().getId() == 9) { // delimiter "("
            parseExactTokenNode(iterator, 9); // delimiter "("
            parameters.parse(iterator);
            parseExactTokenNode(iterator, 10); // delimiter ")"
        }
        parseExactTokenNode(iterator, 0); // ";"
        return this;
    }

}
