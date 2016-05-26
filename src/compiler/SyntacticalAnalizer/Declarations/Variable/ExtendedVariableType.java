package compiler.SyntacticalAnalizer.Declarations.Variable;

import compiler.SyntacticalAnalizer.Compilable;
import compiler.SyntacticalAnalizer.CompileException;
import compiler.SyntacticalAnalizer.SyntaxList;
import compiler.SyntacticalAnalizer.TokenNode;
import compiler.lexan.Position;

import java.util.List;

/**
 * Created by supremist on 5/19/16.
 */
public class ExtendedVariableType implements Compilable{

    public enum BaseType {INTEGER, FLOAT, BLOCKFLOAT;
        public int getSize(){
            if (this == INTEGER)
                return 2;
            return 4;
        }

        public String getAsmType(){
            if(this == INTEGER)
                return "dw";
            return this.toString();
        }
    }

    private BaseType baseType = null;
    private boolean isComplex = false;
    private boolean isExt = false;
    private boolean isSignal = false;
    private List<Range> dimensions = null;

    public ExtendedVariableType(SyntaxList<Attribute> attributes) throws CompileException {
        for (Attribute attribute: attributes.getItems()){
            Attribute.Type attrType = attribute.getType();
            if(attrType == Attribute.Type.INTEGER ||
                    attrType == Attribute.Type.FLOAT ||
                    attrType == Attribute.Type.BLOCKFLOAT){
                if (baseType == null)
                    baseType = BaseType.valueOf(attrType.name());
                else {
                    Position pos = ((TokenNode) attribute.getLastChild()).getToken().getPosition();
                    throw new CompileException("Base type already set as "+baseType.toString(), pos);
                }
            }
            else if (attrType == Attribute.Type.SIGNAL)
                isSignal = true;
            else if (attrType == Attribute.Type.COMPLEX)
                isComplex = true;
            else if (attrType == Attribute.Type.EXT)
                isExt = true;
            else if (attrType == Attribute.Type.RANGE){
                dimensions = attribute.getRanges();
            }
        }
        if(baseType == null)
            throw new CompileException("Base type must be set ",
                    ((TokenNode) attributes.get(0).getLastChild()).getToken().getPosition());
    }

    @Override
    public StringBuilder toAsmCode() throws CompileException {
        StringBuilder buffer = new StringBuilder();
        String base;
        if (isComplex)
            base = "COMPLEX"+baseType;
        else
            base = baseType.getAsmType();
        buffer.append(base).append(" ");
        if (dimensions != null){
            StringBuilder endBuffer = new StringBuilder();
            for (Range range: dimensions){
                buffer.append(range.getLength()).append(" dup (");
                endBuffer.append(")");
            }
            buffer.append("?").append(endBuffer);
        }
        else
            buffer.append("?");
        return buffer;
    }

    public boolean isExtern (){return isExt;}

}
