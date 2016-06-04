package compiler.SyntacticalAnalizer;

import compiler.SyntacticalAnalizer.Declarations.Declarations;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

/**
 * Created by supremist on 5/8/16.
 */
public class Block extends TreeNode implements Compilable{
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
        parseExactTokenNode(iterator, Token.Keyword.BEGIN); // Keyword "BEGIN"
        if (!iterator.getCurrent().equals(Token.Keyword.END.makeToken())) {
            statementList.parse(iterator);
            addChild(statementList);
        }
        parseExactTokenNode(iterator, Token.Keyword.END); // Keyword "END"
        return this;
    }

    public Declarations getDeclarations() {
        return declarations;
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        return statementList.toAsmCode(info);
    }
}
