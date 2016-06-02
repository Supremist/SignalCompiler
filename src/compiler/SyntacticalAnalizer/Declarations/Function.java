package compiler.SyntacticalAnalizer.Declarations;

import com.sun.corba.se.impl.oa.toa.TOA;
import compiler.SyntacticalAnalizer.*;
import compiler.SyntacticalAnalizer.Declarations.Constant.*;
import compiler.SyntacticalAnalizer.Exprission.Expression;
import compiler.SyntacticalAnalizer.Exprission.UnknownIdentifierException;
import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by supremist on 5/8/16.
 */
public class Function extends NamedTreeNode implements Compilable{

    private Expression expression;
    private UnsignedConstant minBound;
    private UnsignedConstant maxBound;
    private List<ConstantValue> values;

    public Function(){
        super();
        values = new ArrayList<>();
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        super.parse(iterator);
        parseExactTokenNode(iterator, Token.Delimiter.EQUAL); // "="
        expression = parseChild(iterator, Expression.class);
        parseExactTokenNode(iterator, Token.Delimiter.BACK_SLASH); // "\"
        minBound = parseChild(iterator, UnsignedConstant.class);
        parseExactTokenNode(iterator, Token.Delimiter.COMMA); // ","
        maxBound = parseChild(iterator, UnsignedConstant.class);
        parseExactTokenNode(iterator, Token.Delimiter.SEMICOLON); // ";"
        return this;
    }

    private void checkBound(UnsignedConstant bound) throws CompileException{
        if(!bound.getToken().isInteger()){
            throw new CompileException("Function bounds should be integer", bound.getPosition());
        }
    }

    public void initValues(IConstantTable constants) throws CompileException{
        values.clear();
        checkBound(minBound);
        checkBound(maxBound);
        int minValue = minBound.getToken().getInteger();
        int maxValue = maxBound.getToken().getInteger();
        if (minValue > maxValue){
            throw new CompileException("Min bound bigger then max bound", minBound.getPosition());
        }

        Set<String> expressionItems = expression.getItems().stream()
                .map(i -> i.getIdentifier().getToken().getView())
                .collect(Collectors.toSet());
        expressionItems.removeAll(constants.getNames());

        if(expressionItems.isEmpty()){
            throw new CompileException("Function has no argument", getPosition());
        }
        else if(expressionItems.size() > 1){
            throw new CompileException("Function has too many arguments", getPosition());
        }
        else{
            String argumentName = expressionItems.toArray(new String[1])[0];
            ConstantTable table = new ConstantTable(constants);
            for (int i = minValue; i<=maxValue; ++i){
                table.put(argumentName, new ConstantValueWrapper(new ConstantValue(i)));
                values.add(expression.getConstantValue(table));
            }
        }
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException {
        StringBuilder buffer = super.toAsmCode();
        Iterator<ConstantValue> iterator = values.listIterator();
        buffer.append(" ").append(new ConstantValueWrapper(iterator.next())
                .toAsmCode().toString().replace("\n", ""));
        while (iterator.hasNext()){
            buffer.append(new ConstantValueWrapper(iterator.next()).getCode());
        }
        buffer.append("\n");
        return buffer;
    }

}
