package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TokenNode;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.List;

/**
 * Created by supremist on 4/30/16.
 */
public class ListedTokenNode extends TokenNode {

    private List<Integer> allowedId;
    private String exceptionText;

    public ListedTokenNode(List<Integer> allowedValues){
        super();
        allowedId = allowedValues;
        exceptionText = "Unexpected token ";
    }

    public ListedTokenNode(List<Integer> allowedValues, String exceptionText){
        this(allowedValues);
        setExceptionText(exceptionText);
    }

    public void setExceptionText(String text){
        this.exceptionText = text;
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        super.parse(iterator);
        if (allowedId.indexOf(getToken().getId()) == -1)
            throw new ParseException(exceptionText, getToken().getPosition());
        return this;
    }
}
