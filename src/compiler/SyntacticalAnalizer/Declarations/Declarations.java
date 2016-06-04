package compiler.SyntacticalAnalizer.Declarations;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantDeclaration;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantDeclarations;
import compiler.SyntacticalAnalizer.Declarations.Constant.UnsignedConstant;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclarations;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableType;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.List;

/**
 * Created by supremist on 5/8/16.
 */
public class Declarations extends TreeNode implements Compilable{

    private ConstantDeclarations constants;
    private VariableDeclarations variable_declarations;
    private SyntaxList<Function> functions;
    private SyntaxList<Procedure> procedures;
    private SyntaxList<UnsignedConstant> labels;

    public Declarations(){
        super();
        constants = new ConstantDeclarations();
        variable_declarations = new VariableDeclarations();
        functions = new SyntaxList<Function>(Function.class);
        procedures = new SyntaxList<Procedure>(Procedure.class);
        labels = new SyntaxList<UnsignedConstant>(UnsignedConstant.class, CommaSeparator.class);
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        if (iterator.getCurrent().isEqual(Token.Keyword.CONST)) { //Keyword "CONST"
            parseDeclaration(iterator, constants);
        }
        if (iterator.getCurrent().isEqual(Token.Keyword.VAR)) { //Keyword "VAR"
            parseDeclaration(iterator, variable_declarations);
        }
        if (iterator.getCurrent().isEqual(Token.Keyword.LABEL)){ //"LABEL"
            parseDeclaration(iterator, labels);
            parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); //;
        }
        if (iterator.getCurrent().isEqual(Token.Keyword.DEFFUNC)) { //Keyword "DEFFUNC"
            parseDeclaration(iterator, functions);
        }
        if (iterator.getCurrent().isEqual(Token.Keyword.PROCEDURE)) { //Keyword "PROCEDURE"
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


    public ConstantDeclarations getConstants(){
        return constants;
    }

    public VariableDeclarations getVariableDeclarations(){
        return variable_declarations;
    }

    public List<Function> getFunctions(){
        return functions.getItems();
    }

    public List<Procedure> getProcedures(){
        return procedures.getItems();
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        StringBuilder buffer = new StringBuilder();
        for (Function function: functions.getItems()){
            function.initValues(constants);
        }
        buffer.append(VariableType.FLOAT_DECLARATION)
                .append(VariableType.BLOCKFLOAT_DECLARATION)
                .append(String.format(VariableType.COMPLEX_DECLARATION_TEMPLATE, "INTEGER", "dw"))
                .append(String.format(VariableType.COMPLEX_DECLARATION_TEMPLATE, "FLOAT", "FLOAT"))
                .append(String.format(VariableType.COMPLEX_DECLARATION_TEMPLATE, "BLOCKFLOAT", "BLOCKFLOAT"));
        buffer.append(info.getProgramName().getView())
                .append("DATA SEGMENT\n")
                .append(constants.toAsmCode(info))
                .append(variable_declarations.toAsmCode(info))
                .append(functions.toAsmCode(info))
                .append(procedures.toAsmCode(info))
                .append(info.getProgramName().getView()).append("DATA ENDS\n");

        return buffer;
    }
}
