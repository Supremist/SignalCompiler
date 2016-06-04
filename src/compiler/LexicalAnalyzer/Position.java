package compiler.LexicalAnalyzer;

/**
 * Created by supremist on 4/12/16.
 */
public class Position{
    private int line;
    private int column;

    public Position(int line, int column){
        this.line = line;
        this.column = column;
    }

    public Position(Position copy){
        this.line = copy.line;
        this.column = copy.column;
    }

    public Position(){this(0,0);}
    public void nextLine(){
        column = 0;
        ++line;
    }
    public void nextSymbol(){++column;}
    public int getLine(){return line;}
    public int getColumn(){return column;}
    public String toString(){
        return String.format("%d %d", line, column);
    }

    public static Position fromString(String line){
        String[] values = line.split(" ");
        return new Position(Integer.valueOf(values[0]), Integer.valueOf(values[1]));
    }
}