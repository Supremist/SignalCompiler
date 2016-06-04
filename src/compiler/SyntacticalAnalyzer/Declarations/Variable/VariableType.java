package compiler.SyntacticalAnalyzer.Declarations.Variable;

import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.*;
import compiler.LexicalAnalyzer.Position;

import java.util.List;

/**
 * Created by supremist on 5/19/16.
 */
public class VariableType implements Compilable{

    public static final String COMPLEX_DECLARATION_TEMPLATE =
            "COMPLEX%1$s STRUCT\n" +
                    "    left %2$s ?\n" +
                    "    right %2$s ?\n" +
                    "COMPLEX%1$s ENDS\n\n";
    public static final String COMPLEX_IMPLEMENTATION_TEMPLATE =
            "COMPLEX%1$s <%2$s, %3$s>\n";

    private static final int INTEGER_SIZE = 2;

    private static final int FLOAT_SIZE = 5;
    public static final String FLOAT_DECLARATION =
            "FLOAT STRUCT\n" +
            "    mantissa dd ?\n" +
            "    power db ?\n" +
            "FLOAT ENDS\n\n";

    private static final int MANTISSA_COUNT = 10;
    private static final int BLOCKFLOAT_SIZE = MANTISSA_COUNT*4+1;
    public static final String BLOCKFLOAT_DECLARATION =
            "BLOCKFLOAT STRUCT\n" +
            "    power db ?\n" +
            "    mantissa "+ String.valueOf(MANTISSA_COUNT)+" dup ( dd ?)\n" +
            "BLOCKFLOAT ENDS\n\n";


    public enum BaseType {INTEGER, FLOAT, BLOCKFLOAT;

        public int getSize(){
            if (this == INTEGER) {
                return INTEGER_SIZE;
            }
            else if (this == FLOAT) {
                return FLOAT_SIZE;
            }
            else{
                return BLOCKFLOAT_SIZE;
            }
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

    public VariableType(SyntaxList<Attribute> attributes) throws CompileException {
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
    public StringBuilder toAsmCode(CompilationInfo info) throws CompileException {
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

    @Override
    public void Compile(CompilationInfo info) throws CompileException {

    }

    public int getSize(){
        int size = baseType.getSize();
        if(isComplex)
            size *= 2;
        if (dimensions != null) {
            for (Range range : dimensions) {
                size *= range.getLength();
            }
        }
        return size;
    }

    public boolean isSignal(){
        return isSignal;
    }

    public boolean isExtern (){return isExt;}

}
