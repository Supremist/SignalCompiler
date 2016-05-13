package compiler.SyntacticalAnalizer;

import com.sun.deploy.util.StringUtils;
import compiler.lexan.Grammar;
import compiler.lexan.ParseException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by supremist on 4/10/16.
 */

public abstract class TreeNode implements Serializable{
    private List<TreeNode> children;
    private TreeNode parent;
    private int level;
    
    public TreeNode(){
        children = new ArrayList<>();
        level = 0;
    }

    public TreeNode(TokenIterator iterator) throws ParseException{
        this();
        parse(iterator);
    }

    abstract public TreeNode parse(TokenIterator iterator) throws ParseException;
    public String toString(){
        return this.getClass().getSimpleName();
    }

    public boolean tryParse(TokenIterator iterator){
        try {
            iterator.savePosition();
            parse(iterator);
        }
        catch (ParseException ex){
            iterator.seekBack();
            return false;
        }
        return true;
    }

    public boolean tryParseChild(TokenIterator iterator, Class<? extends TreeNode> childClass){
        try {
            TreeNode item = childClass.newInstance();
            if (item.tryParse(iterator))
                addChild(item);
            else
                return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }

    public <T extends TreeNode> T parseChild(TokenIterator iterator,
                                             Class<? extends T> childClass) throws ParseException{
        try{
            T item = childClass.newInstance();
            addChild(item.parse(iterator));
            return item;
        }
        catch (ParseException ex){
            throw ex;
        }
        catch (Exception ex){
            ex.printStackTrace(); // never happen
        }
        return null;
    }

    public TokenNode parseExactTokenNode(TokenIterator iterator, int tokenId) throws ParseException{
        TokenNode node = parseChild(iterator, TokenNode.class);
        if (node.getToken().getId() != tokenId)
            throw new ParseException(String.format("Token with id %d expected", tokenId), node.getToken().getPosition());
        return node;
    }

    protected StringBuilder getLevelWhitespace(){
        StringBuilder buffer = new StringBuilder();
        if (level > 0){
            char[] chars = new char[level];
            Arrays.fill(chars, ' ');
            buffer.append(chars);
        }
        return buffer;
    }

    public StringBuilder toStringTree(Grammar grammar){
        StringBuilder buffer = getLevelWhitespace();
        buffer.append(toString());
        buffer.append('\n');
        for (TreeNode child:children) {
            buffer.append(child.toStringTree(grammar));
        }
        return buffer;
    }

    public StringBuilder toXmlView(Grammar grammar){
        StringBuilder buffer = getLevelWhitespace();
        buffer.append("<").append(getClass().getSimpleName())
                .append(getXmlAttrs(grammar))
                .append(">\n");
        for(TreeNode child: children){
            buffer.append(child.toXmlView(grammar));
        }
        buffer.append(getLevelWhitespace())
                .append("</").append(getClass().getSimpleName()).append(">\n");
        return buffer;
    }

    public StringBuilder getXmlAttrs(Grammar grammar){
        StringBuilder buffer = new StringBuilder();
        //can add here some attributes to show in xml view
        return buffer;
    }


    public void addChild(TreeNode child){
        children.add(child);
        child.parent = this;
        // may cause slowing
        //child.setLevel(this.level + 1);
    }

    public void setLevel(int level){
        this.level = level;
        for (TreeNode child: children)
            child.setLevel(level+1);
    }

    public void clearChildren(){
        children.clear();
    }
    
    public TreeNode getParent(){return parent;}
    public int getLevel(){return level;}
    public List<TreeNode> getChildren(){return children;}
    public TreeNode getLastChild(){return children.get(children.size()-1);}

}
