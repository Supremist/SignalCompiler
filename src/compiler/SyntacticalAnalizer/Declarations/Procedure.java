package compiler.SyntacticalAnalizer.Declarations;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclaration;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclarations;
import compiler.lexan.ParseException;

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

    public VariableDeclarations getParameters(){
        return parameters;
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException {
        StringBuilder buffer = new StringBuilder();
        buffer.append("extern ").append(super.toAsmCode())
                .append("@").append(parameters.getSize()).append(":far\n");
        return buffer;
    }

}
