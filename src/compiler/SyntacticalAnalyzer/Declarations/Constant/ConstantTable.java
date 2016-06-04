package compiler.SyntacticalAnalyzer.Declarations.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by supremist on 5/27/16.
 */
public class ConstantTable extends HashMap<String, IConstantValue> implements IConstantTable {

    public ConstantTable(){
        super();
    }

    public ConstantTable(IConstantTable table){
        this();
        for (String name: table.getNames()){
            put(name, table.getConstantByName(name));
        }
    }

    @Override
    public IConstantValue getConstantByName(String name) {
        return get(name);
    }

    @Override
    public List<String> getNames() {
        return keySet().stream().collect(Collectors.toList());
    }
}
