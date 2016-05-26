package compiler.SyntacticalAnalizer.Declarations;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantDeclaration;
import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.SyntacticalAnalizer.Declarations.Variable.Variable;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclaration;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclarations;
import compiler.lexan.ParseException;

import java.util.List;

/**
 * Created by supremist on 5/8/16.
 */
public class Declarations extends TreeNode implements Compilable{

    private SyntaxList<ConstantDeclaration> constants;
    private VariableDeclarations variable_declarations;
    private SyntaxList<Function> functions;
    private SyntaxList<Procedure> procedures;
    private SyntaxList<UnsignedConstant> labels;

    private static final String COMPLEX_TEMPLATE =
            "COMPLEX%1$s STRUCT\n" +
            "    left %2$s ?\n" +
            "    right %2$s ?\n" +
            "COMPLEX%1$s ENDS\n\n";

    public Declarations(){
        super();
        constants = new SyntaxList<ConstantDeclaration>(ConstantDeclaration.class);
        variable_declarations = new VariableDeclarations();
        functions = new SyntaxList<Function>(Function.class);
        procedures = new SyntaxList<Procedure>(Procedure.class);
        labels = new SyntaxList<UnsignedConstant>(UnsignedConstant.class, CommaSeparator.class);
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        if (iterator.getNext().getId() == 404) { //Keyword "CONST"
            parseDeclaration(iterator, constants);
        }
        if (iterator.getNext().getId() == 405) { //Keyword "VAR"
            parseDeclaration(iterator, variable_declarations);
        }
        if (iterator.getNext().getId() == 416){ //"LABEL"
            parseDeclaration(iterator, labels);
            parseExactTokenNode(iterator, 0 ); //;
        }
        if (iterator.getNext().getId() == 412) { //Keyword "DEFFUNC"
            parseDeclaration(iterator, functions);
        }
        if (iterator.getNext().getId() == 401) { //Keyword "PROCEDURE"
            procedures.parse(iterator);
            addChild(procedures);
        }
        return this;
    }

    private void parseDeclaration(TokenIterator iterator, SyntaxList list) throws ParseException{
        parseChild(iterator, TokenNode.class);
        list.parse(iterator);
        addChild(list);
    }

    public void checkIdentifierUniqueness(List<String> identifiers) throws CompileException{
        for(ConstantDeclaration constant: constants.getItems())
            constant.checkIdentifierUniqueness(identifiers);
        variable_declarations.checkIdentifierUniqueness(identifiers);
        for (Function function: functions.getItems())
            function.checkIdentifierUniqueness(identifiers);
        for (Procedure procedure: procedures.getItems())
            procedure.checkIdentifierUniqueness(identifiers);
    }


    public List<ConstantDeclaration> getConstants(){
        return constants.getItems();
    }

    public List<VariableDeclaration> getVariableDeclarations(){
        return variable_declarations.getItems();
    }

    public List<Function> getFunctions(){
        return functions.getItems();
    }

    public List<Procedure> getProcedures(){
        return procedures.getItems();
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException {
        StringBuilder buffer = new StringBuilder();
        buffer.append(
                "FLOAT STRUCT\n" +
                "    mantissa dd ?\n" +
                "    power db ?\n" +
                "FLOAT ENDS\n\n" +
                "BLOCKFLOAT STRUCT\n" +
                "    power db ?\n" +
                "    mantissa 10 dup ( dd ?)\n" +
                "BLOCKFLOAT ENDS\n\n")
                .append(String.format(COMPLEX_TEMPLATE, "INTEGER", "dw"))
                .append(String.format(COMPLEX_TEMPLATE, "FLOAT", "FLOAT"))
                .append(String.format(COMPLEX_TEMPLATE, "BLOCKFLOAT", "BLOCKFLOAT"));
        buffer.append("DATA SEGMENT\n");

        buffer.append("DATA ENDS\n");

        return buffer;
    }
}
