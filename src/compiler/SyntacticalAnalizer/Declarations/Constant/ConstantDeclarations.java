package compiler.SyntacticalAnalizer.Declarations.Constant;

import compiler.SyntacticalAnalizer.*;
import compiler.lexan.ParseException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by supremist on 5/26/16.
 */


public class ConstantDeclarations extends SyntaxList<ConstantDeclaration> implements IConstantTable{


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
            constant.getConstantValue(this);
        }
    }

    public ConstantDeclaration getConstantByName(String name){
        List<ConstantDeclaration> result = getItems().stream()
                .filter(i -> i.getIdentifier().getToken().getView().equals(name))
                .collect(Collectors.toList());
        if (!result.isEmpty())
            return result.get(0);
        else
            return null;
    }

    @Override
    public List<String> getNames() {
        return getItems().stream()
                .map(i -> i.getIdentifier().getToken().getView())
                .collect(Collectors.toList());
    }
}
