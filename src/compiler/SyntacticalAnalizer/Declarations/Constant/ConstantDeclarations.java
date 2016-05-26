package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.SyntaxList;
import compiler.SyntacticalAnalizer.TokenIterator;
import compiler.SyntacticalAnalizer.TreeNode;
import compiler.lexan.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by supremist on 5/26/16.
 */
public class ConstantDeclarations extends SyntaxList<ConstantDeclaration> {


    public ConstantDeclarations(){
        super(ConstantDeclaration.class);
    }

    @Override
    public TreeNode parse(TokenIterator iterator) throws ParseException {
        super.parse(iterator);
        initConstantList();
        return this;
    }

    private void initConstantList() throws CompileException{
        for(ConstantDeclaration constant: getItems()){
            constant.calcConstantValue(this);
        }
    }

    public ConstantDeclaration getConstantByName(String name){
        Iterator<ConstantDeclaration> iterator = getItems().listIterator();
        ConstantDeclaration current = iterator.next();
        while (iterator.hasNext() && !current.getIdentifier().getToken().getView()
                .equals(name)){
            current = iterator.next();
        }
        if(current.getIdentifier().getToken().getView()
                .equals(name)){
            return current;
        }
        return null;
    }


}
