package compiler.lexan;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/**
 * Created by supremist on 4/11/16.
 */
public class Token {
    public enum Type {SINGLE_CHAR, DELIMITER, KEYWORD, CONSTANT, IDENTIFIER}

    public static final int SINGLE_CHAR_OFFSET = 0;
    public static final int DELIMITERS_OFFSET = 300;
    public static final int KEYWORDS_OFFSET = 400;
    public static final int CONSTANT_OFFSET = 500;
    public static final int IDENTIFIER_OFFSET = 1000;

    public static final int[] OFFSETS = {SINGLE_CHAR_OFFSET, DELIMITERS_OFFSET,
            KEYWORDS_OFFSET, CONSTANT_OFFSET, IDENTIFIER_OFFSET};

    private final Type type;
    private final Position position;
    private final int index;
    private final int id;

    public Token (Type type, Position position, int index){
        this.type = type;
        this.position = new Position(position);
        this.index = index;
        this.id = OFFSETS[type.ordinal()] + index;
    }

    public Token (int id, Position position){
        type = getTypeById(id);
        this.position = new Position(position);
        this.index = id - OFFSETS[type.ordinal()];
        this.id = id;
    }

    public static Token fromId(int id){
        return new Token(id, new Position());
    }

    public static Type getTypeById(int id) throws ValueException{
        if(id < 0)
            throw new ValueException("Value error: id < 0");
        else{
            int index = 1;
            while (index < OFFSETS.length && id >= OFFSETS[index]) index++;
            if (id >= OFFSETS[OFFSETS.length-1])
                return Type.values()[OFFSETS.length-1];
            else
                return Type.values()[index-1];
        }
    }

    public boolean equals(Token other){
        return this.getId() == other.getId();
    }

    public Type getType(){return type;}
    public Position getPosition(){return position;}
    public int getIndex(){return index;}
    public int getId(){return id;}

    public String toString(){
        return String.valueOf(id)+" "+position.toString();
    }

    public static Token fromString(String line){
        String[] values = line.split(" ");
        return new Token(Integer.valueOf(values[0]),
                new Position(Integer.valueOf(values[1]), Integer.valueOf(values[2])));
    }
}
