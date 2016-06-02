package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Procedure;
import compiler.SyntacticalAnalizer.Declarations.Variable.Variable;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.List;

/**
 * Created by supremist on 4/11/16.
 */
public class Program extends NamedTreeNode implements Compilable{

    private Block block;
    private Procedure procedure;

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException{
        clearChildren();
        if(iterator.getCurrent().isEqual(Token.Keyword.PROCEDURE)) {
            procedure = parseChild(iterator, Procedure.class);
            block = parseChild(iterator, Block.class);
            parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON);
        }
        else if(iterator.getCurrent().isEqual(Token.Keyword.PROGRAM)) {
            procedure = null;
            parseChild(iterator, TokenNode.class);
            parseIdentifier(iterator);
            parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ";"
            block = parseChild(iterator, Block.class);
            parseExactTokenNode(iterator, Token.Delimiter.DOT); // "."
        }
        return this;
    }

    public Block getBlock(){
        return block;
    }

    public StringBuilder toAsmCode() throws CompileException{
        StringBuilder buffer = new StringBuilder();
        String identifier = getIdentifier().getToken().getView();
        buffer.append(".386\nASSUME CS:").append(identifier)
                .append("CODE,DS:").append(identifier).append("DATA\n");
        buffer.append(getBlock().getDeclarations().toAsmCode());
        if (procedure == null){ // program

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
