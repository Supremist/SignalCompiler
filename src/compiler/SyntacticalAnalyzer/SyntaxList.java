package compiler.SyntacticalAnalyzer;

import compiler.Exceptions.CompileException;
import compiler.Exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/13/16.
 */
public class SyntaxList<T extends TreeNode> extends TreeNode implements Compilable{

    public abstract class EmptySeparator extends TreeNode{}

    private Class<? extends T> itemClass;
    private Class<? extends TreeNode> separatorClass;
    private List<T> items;

    public SyntaxList(Class<? extends T> itemClass){
        items = new ArrayList<>();
        this.separatorClass = EmptySeparator.class;
        this.itemClass = itemClass;
    }

    public SyntaxList(Class<? extends T> itemClass, Class<? extends TreeNode> separatorClass){
        this(itemClass);
        if (separatorClass == null){
            this.separatorClass = EmptySeparator.class;
        }
        else {
            this.separatorClass = separatorClass;
        }
    }

    public boolean isEmpty(){return items.isEmpty();}

    @SuppressWarnings("unchecked")
    private boolean parseItem(TokenIterator iterator) throws ParseException{
        boolean success = tryParseChild(iterator, itemClass);
        if(success){
            items.add((T) getLastChild());
        }
        return success;
    }

    private boolean parseSeparator(TokenIterator iterator){
        if (separatorClass != EmptySeparator.class){
            try{
                return tryParseChild(iterator, separatorClass);
            }catch (ParseException ex){
                return false;
            }
        }
        else {
            return true;
        }
    }

    public T get(int index){
        return items.get(index);
    }

    public List<T> getItems(){return items;}

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        boolean isCorrect;
        try {
            isCorrect = parseItem(iterator);
        } catch (ParseException cause){
            throw new ParseException("List should have at least one element ",
                    iterator.getCurrent().getPosition(), cause);
        }
        while ( isCorrect && parseSeparator(iterator)){
            try{
                isCorrect = parseItem(iterator);
            } catch (ParseException cause){
                isCorrect = false;
                if (separatorClass != EmptySeparator.class)
                    throw new ParseException("Next list item expected ",
                            iterator.getCurrent().getPosition(), cause);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public StringBuilder getXmlAttrs(){
        StringBuilder buffer = super.getXmlAttrs();
        buffer.append(" childType=\"").append(itemClass.getSimpleName()).append("\"");
        return buffer;
    }

    @Override
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
        StringBuilder buffer = new StringBuilder();
        for (T item: items){
            buffer.append(item.toAsmCode(info));
        }
        return buffer;
    }
}
