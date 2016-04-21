package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/13/16.
 */
public class SyntaxList<T extends TreeNode> extends SyntaxNode {

    private Constructor<? extends T> ctor;
    private int separatorId;
    private List<T> items;

    public SyntaxList(Class<? extends T> impl){
        this(impl, -1);
    }

    public SyntaxList(Class<? extends T> impl, int separatorId){
        this.separatorId = separatorId;
        items = new ArrayList<T>();
        try {
            this.ctor = impl.getConstructor();
        }
        catch (NoSuchMethodException ex){
            this.ctor = null;
            ex.printStackTrace(); // that should never happen
        }
    }

    public boolean isEmpty(){return items.isEmpty();}

    private boolean parseItem(TokenIterator iterator){
        try {
            T item = ctor.newInstance();
            iterator.savePosition();
            item.parse(iterator);
            addChild(item);
            items.add(item);
        }
        catch (ParseException ex){
            iterator.seekBack();
            return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }

    public T get(int index){
        return items.get(index);
    }

    public List<T> getItems(){return items;}

    public TreeNode parse(TokenIterator iterator) throws ParseException {
        boolean isCorrect = parseItem(iterator);
        if (!isCorrect)
            throw new ParseException("List should have at least one element ", iterator.getNext().getPosition());
        while (isCorrect && (separatorId < 0 || iterator.getNext().getId() == separatorId)){
            if (separatorId >= 0)
                parseTokenNode(iterator);
            isCorrect = parseItem(iterator);
        }
        return this;
    }
}
