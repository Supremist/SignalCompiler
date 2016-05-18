package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Procedure;
import compiler.SyntacticalAnalizer.Declarations.Variable.Variable;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclaration;
import compiler.lexan.Grammar;
import compiler.lexan.ParseException;

import java.util.List;

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
            procedure = null;
            parseChild(iterator, TokenNode.class);
            parseIdentifier(iterator);
            parseExactTokenNode(iterator, 0); // ";"
            block = parseChild(iterator, Block.class);
            parseExactTokenNode(iterator, 7); // "."
        }
        return this;
    }

    public Block getBlock(){
        return block;
    }

    public StringBuilder toAsmCode(Grammar grammar){
        StringBuilder buffer = new StringBuilder();
        String identifier = getIdentifier().getToken().findView(grammar);
        if (procedure == null){ // program
            buffer.append(".386\n").append(identifier).append(" SEGMENT\n")
                    .append("assume cs:").append(identifier).append(",ds:").append(identifier)
                    .append("org 100h\n").append(block.toAsmCode(grammar))
                    .append(identifier).append(" ENDS\nend START");
        }
        else{ // procedure
            int steckStart = 4;
            List<Variable> parameters =  procedure.getParameters().getVariableList();
            buffer.append(identifier).append(":\n")
                    .append("push bp\nmov bp,sp\n");
        }
        return buffer;
    }
}
