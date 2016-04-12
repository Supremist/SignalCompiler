package compiler.SyntacticalAnalizer;

import compiler.lexan.ParseException;
import compiler.lexan.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by supremist on 4/10/16.
 */

public abstract class SyntaxNode  {
    private List<SyntaxNode> children;
    private SyntaxNode parent;
    private Token token;
    
    public SyntaxNode(){
        children = new ArrayList<>();
    }

    abstract public void parse(TokenIterator iterator) throws ParseException;

    public void parseExactToken(TokenIterator iterator, int tokenId) throws ParseException{
        Token current = iterator.next();
        if (current.getId() != tokenId)
            throw new ParseException(String.format("Token %d expected", tokenId), current.getPosition());
        
    }

    public void addChild(SyntaxNode child){
        children.add(child);
        child.parent = this;
    }
    
    public SyntaxNode getParent(){return parent;}
    public List<SyntaxNode> getChildren(){return children;}
    public Token getToken(){return token;}
    public void setToken(Token token){this.token = token;}

}
