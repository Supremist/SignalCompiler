package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.SyntaxList;

/**
 * Created by supremist on 4/22/16.
 */

public class MultipliersList extends SyntaxList<MultiplierItem> {

    public MultipliersList() {
        super(MultiplierItem.class, MultiplicationInstruction.class);
    }

}
