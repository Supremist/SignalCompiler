package compiler.SyntacticalAnalizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/10/16.
 */
public class TreeNode<T> {
    private T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode(){
        children = new ArrayList<>();
    }

    public TreeNode(T data){
        this();
        setData(data);
    }

    public void add(TreeNode<T> item){
        children.add(item);
        item.parent = this;
    }

    public void setData(T data){this.data = data;}
    public T getData(){return data;}
    public TreeNode<T> getParent(){return parent;}
    public List<TreeNode<T>> getChildren(){return children;}
}
