package compiler.SyntacticalAnalizer;

import compiler.lexan.Grammar;
import compiler.lexan.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/13/16.
 */
public class SyntaxList<T extends TreeNode> extends TreeNode{

    public class CommaSeparator extends TokenNode{

        public CommaSeparator(TokenIterator iterator) throws ParseException {
            super(iterator);
        }

        @Override
        public TreeNode parse(TokenIterator iterator)throws ParseException{
            super.parse(iterator);
            if(getToken().getId() != 3) // ","
                throw new ParseException("Comma expected ", getToken().getPosition());
            return this;
        }
    }

    public abstract class EmptySeparator extends TreeNode{}

    private Class<? extends T> itemClass;
    private Class<? extends TreeNode> separatorClass;
    private List<T> items;

    public SyntaxList(Class<? extends T> itemClass){
        items = new ArrayList<>();
        this.separatorClass = null;
        this.itemClass = itemClass;
    }

    public SyntaxList(Class<? extends T> itemClass, Class<? extends TreeNode> separatorClass){
        this(itemClass);
        if (separatorClass == EmptySeparator.class){
            this.separatorClass = null;
        }
        else {
            this.separatorClass = separatorClass;
        }
    }

    public boolean isEmpty(){return items.isEmpty();}

    @SuppressWarnings("unchecked")
    private boolean parseItem(TokenIterator iterator){
        boolean success = tryParseChild(iterator, itemClass);
        if(success){
            items.add((T) getLastChild());
        }
        return success;
    }

    private boolean parseSeparator(TokenIterator iterator){
        return separatorClass == null || tryParseChild(iterator, separatorClass);
    }

    public T get(int index){
        return items.get(index);
    }

    public List<T> getItems(){return items;}

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        boolean isCorrect = parseItem(iterator);
        if (!isCorrect)
            throw new ParseException("List should have at least one element ", iterator.getNext().getPosition());
        while ( isCorrect && parseSeparator(iterator)){
            if ((isCorrect = parseItem(iterator)) && separatorClass != null)
                throw new ParseException("Next list item expected ", iterator.getNext().getPosition());
        }
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public StringBuilder getXmlAttrs(Grammar grammar){
        StringBuilder buffer = super.getXmlAttrs(grammar);
        buffer.append(" childType=\"").append(itemClass.getSimpleName()).append("\"");
        return buffer;
    }
}
