package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Procedure;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclaration;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 4/11/16.
 */
public class Program extends NamedTreeNode {

    private Block block;
    private Procedure procedure;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException{
        clearChildren();
        if(iterator.getNext().getId() == 401) { //PROCEDURE
            procedure = parseChild(iterator, Procedure.class);
            block = parseChild(iterator, Block.class);
            parseExactTokenNode(iterator, 0); // ;
        }
        else if(iterator.getNext().getId() == 400) { //PROGRAM
            parseChild(iterator, TokenNode.class);
            parseIdentifier(iterator);
            parseExactTokenNode(iterator, 0); // ";"
            block = parseChild(iterator, Block.class);
            parseExactTokenNode(iterator, 7); // "."
        }
        return this;
    }
}
