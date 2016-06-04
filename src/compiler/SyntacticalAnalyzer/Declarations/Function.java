package compiler.SyntacticalAnalyzer.Declarations;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.*;
import compiler.SyntacticalAnalyzer.Declarations.Constant.*;
import compiler.SyntacticalAnalyzer.Exprission.Expression;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Token;

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

    private void checkBound(UnsignedConstant bound) throws CompileException {
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

        Set<String> expressionItems = expression.getIdentifiers().stream()
                .map(Token::getView)
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

    public void Compile(CompilationInfo info) throws CompileException{
        info.addIdentifier(getIdentifier().getToken());
        initValues(info.getDeclarations().getConstants());
        super.Compile(info);
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        StringBuilder buffer = super.toAsmCode(info);
        Iterator<ConstantValue> iterator = values.listIterator();
        buffer.append(" ").append(new ConstantValueWrapper(iterator.next())
                .toAsmCode(info).toString().replace("\n", ""));
        while (iterator.hasNext()){
            buffer.append(", ")
                    .append(new ConstantValueWrapper(iterator.next()).getCode());
        }
        buffer.append("\n");
        return buffer;
    }

}
