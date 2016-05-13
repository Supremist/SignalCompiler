package compiler.SyntacticalAnalizer.Declarations;

import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.ConstantDeclaration;
import compiler.SyntacticalAnalizer.Declarations.Variable.VariableDeclaration;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Declarations extends TreeNode {

    private SyntaxList<ConstantDeclaration> constants;
    private SyntaxList<VariableDeclaration> variables;
    private SyntaxList<Function> functions;
    private SyntaxList<Procedure> procedures;

    public Declarations(){
        super();
        constants = new SyntaxList<ConstantDeclaration>(ConstantDeclaration.class);
        variables = new SyntaxList<VariableDeclaration>(VariableDeclaration.class);
        functions = new SyntaxList<Function>(Function.class);
        procedures = new SyntaxList<Procedure>(Procedure.class);
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        if (iterator.getNext().getId() == 404) { //Keyword "CONST"
            parseDeclaration(iterator, constants);
        }
        if (iterator.getNext().getId() == 405) { //Keyword "VAR"
            parseDeclaration(iterator, variables);
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
}
