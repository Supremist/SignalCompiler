package compiler.SyntacticalAnalyzer.Declarations;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.*;
import compiler.SyntacticalAnalyzer.Declarations.Variable.VariableDeclarations;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class Procedure extends NamedTreeNode implements Compilable{

    private VariableDeclarations parameters;

    public Procedure(){
        super();
        parameters = new VariableDeclarations();
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        parseExactTokenNode(iterator, Token.Keyword.PROCEDURE); //Keyword "PROCEDURE"
        super.parse(iterator);
        if (iterator.getCurrent().isEqual(Token.Delimiter.OPEN_BRACKET)) { // delimiter "("
            parseExactTokenNode(iterator, Token.Delimiter.OPEN_BRACKET); // delimiter "("
            parameters = parseChild(iterator, VariableDeclarations.class);
            parseExactTokenNode(iterator, Token.Delimiter.CLOSE_BRACKET); // delimiter ")"
        }
        parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ";"
        return this;
    }

    public VariableDeclarations getParameters(){
        return parameters;
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        StringBuilder buffer = new StringBuilder();
        buffer.append("extern ").append(super.toAsmCode(info))
                .append("@").append(parameters.getSize()).append(":far\n");
        return buffer;
    }

}
