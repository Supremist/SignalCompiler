package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/10/16.
 */

public abstract class TreeNode {
    private List<TreeNode> children;
    private TreeNode parent;
    
    public TreeNode(){
        children = new ArrayList<>();
    }

    public TreeNode(TokenIterator iterator) throws ParseException{
        this();
        parse(iterator);
    }

    abstract public TreeNode parse(TokenIterator iterator) throws ParseException;

    public void addChild(TreeNode child){
        children.add(child);
        child.parent = this;
    }

    public void clearChildren(){
        children.clear();
    }
    
    public TreeNode getParent(){return parent;}
    public List<TreeNode> getChildren(){return children;}

}
