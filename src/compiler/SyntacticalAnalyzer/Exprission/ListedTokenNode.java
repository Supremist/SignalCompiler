package compiler.SyntacticalAnalyzer.Exprission;

import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.SyntacticalAnalyzer.TokenNode;
import compiler.SyntacticalAnalyzer.TreeNode;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by supremist on 4/30/16.
 */
public class ListedTokenNode extends TokenNode {

    private List<Token> allowedId;
    private String exceptionText;

    public ListedTokenNode(List<Token.TokenEnum> allowedValues){
        super();
        allowedId = allowedValues.stream()
                .map(Token.TokenEnum::makeToken)
                .collect(Collectors.toList());
        exceptionText = "Unexpected token ";
    }

    public ListedTokenNode(List<Token.TokenEnum> allowedValues, String exceptionText){
        this(allowedValues);
        setExceptionText(exceptionText);
    }

    public void setExceptionText(String text){
        this.exceptionText = text;
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        super.parse(iterator);
        if (allowedId.indexOf(getToken()) == -1)
            throw new ParseException(exceptionText, getToken().getPosition());
        return this;
    }
}
