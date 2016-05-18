package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.NamedTreeNode;
import compiler.SyntacticalAnalizer.SyntaxList;

/**
 * Created by supremist on 5/14/16.
 */
public class Variable {
    private NamedTreeNode name;
    private ExtendedType type;

    public enum BaseType {INTEGER, FLOAT, BLOCKFLOAT}

    public class ExtendedType{
        private BaseType baseType = null;
        private boolean isComplex = false;
        private boolean isExt = false;
        private boolean isSignal = false;

        public void init(SyntaxList<Attribute> attributes) throws CompileException{
            for (Attribute attribute: attributes.getItems()){

            }
        }

    }

    public Variable(NamedTreeNode name, ExtendedType type){
        this.name = name;
        this.type = type;
    }

    public NamedTreeNode getName(){
        return name;
    }

    public ExtendedType getType(){
        return type;
    }

    public int getSize(){
        return 4; //TODO add realization
    }
}
