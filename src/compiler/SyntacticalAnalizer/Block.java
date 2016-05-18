package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Declarations;
import compiler.lexan.Grammar;
import compiler.lexan.ParseException;

/**
 * Created by supremist on 5/8/16.
 */
public class Block extends TreeNode {
    private Declarations declarations;
    private SyntaxList<Statement> statementList;

    public Block(){
        super();
        declarations = new Declarations();
        statementList = new SyntaxList<Statement>(Statement.class);
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        declarations.parse(iterator);
        addChild(declarations);
        parseExactTokenNode(iterator, 402); // Keyword "BEGIN"
        if (iterator.getNext().getId() != 403) {
            statementList.parse(iterator);
            addChild(statementList);
        }
        parseExactTokenNode(iterator, 403); // Keyword "END"
        return this;
    }

    public Declarations getDeclarations() {
        return declarations;
    }

    public StringBuilder toAsmCode(Grammar grammar){
        return new StringBuilder();
    }
}
