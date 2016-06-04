package compiler.SyntacticalAnalyzer.Declarations.Constant;

import java.util.List;

/**
 * Created by supremist on 5/27/16.
 */
public interface IConstantTable {
    IConstantValue getConstantByName(String name);
    List<String> getNames();
    boolean isEmpty();
}
