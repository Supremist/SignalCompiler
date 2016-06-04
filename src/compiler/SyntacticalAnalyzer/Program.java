package compiler.SyntacticalAnalyzer;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.Block.Block;
import compiler.SyntacticalAnalyzer.Declarations.Procedure;
import compiler.SyntacticalAnalyzer.Declarations.Variable.Variable;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

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

    public boolean isProcedure(){
        return procedure != null;
    }

    @Override
    public void Compile(CompilationInfo info) throws CompileException {
        info.setDeclarations(block.getDeclarations());
        if (isProcedure()){
            info.setProgramName(procedure.getIdentifier().getToken());
        }
        else {
            info.setProgramName(getIdentifier().getToken());
        }
        info.setProcedure(isProcedure());
        super.Compile(info);
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException{
        StringBuilder buffer = new StringBuilder();
        String identifier = info.getProgramName().getView();
        buffer.append(".386\nASSUME CS:").append(identifier)
                .append("CODE,DS:").append(identifier).append("DATA\n");
        buffer.append(getBlock().getDeclarations().toAsmCode(info)).append("\n");
        if (isProcedure()){ // procedure
            int stackStart = 4;
            List<Variable> parameters =  procedure.getParameters().getVariableList();
            buffer.append(info.getProgramName().getView())
                    .append("CODE SEGMENT\norg 100h\n\n")
                    .append(info.getProgramName().getView())
                    .append(" PROC FAR\n")
                    .append("push ebp\n" +
                            "mov ebp, esp\n" +
                            "pushad\n");
            for (int i = parameters.size()-1; i>=0; --i){
                buffer.append("@")
                        .append(parameters.get(i).getName().getIdentifier().getToken().getView())
                        .append(" equ [bp + ")
                        .append(stackStart)
                        .append("]\n");
                stackStart += parameters.get(i).getSize();
            }
            buffer.append("popad\n" +
                    "pop ebp\n" +
                    "ret ")
                    .append(stackStart).append("\n")
                    .append(info.getProgramName().getView())
                    .append(" ENDP\n\n")
                    .append(info.getProgramName().getView()).append("CODE ENDS\n")
                    .append("END");
        }
        else{ // program
            buffer.append(info.getProgramName().getView())
                    .append("CODE SEGMENT\norg 100h\n\n")
                    .append("START:\n")
                    //.append(block.toAsmCode(info))
                    .append(info.getProgramName().getView()).append("CODE ENDS\n")
                    .append("END START");
        }
        return buffer;
    }
}
