package compiler.SyntacticalAnalizer.Exprission;

import compiler.SyntacticalAnalizer.SyntaxList;

/**
 * Created by supremist on 4/30/16.
 */

public class SummandList extends SyntaxList<MultipliersList> {

    public SummandList() {
        super(MultipliersList.class, AddInstruction.class);
    }
}
