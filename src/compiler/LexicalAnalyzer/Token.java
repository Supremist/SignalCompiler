package compiler.LexicalAnalyzer;

import com.sun.istack.internal.Nullable;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Arrays;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by supremist on 4/11/16.
 */
public class Token {
    public enum Type {SINGLE_CHAR, DELIMITER, KEYWORD, CONSTANT, IDENTIFIER}

    public interface TokenEnum{
        Token makeToken();
        Type getType();
        String toString();
    }

    public enum Keyword implements TokenEnum {PROGRAM, PROCEDURE, BEGIN, END, CONST, VAR,
        SIGNAL, COMPLEX, INTEGER, FLOAT, BLOCKFLOAT, EXT, DEFFUNC, LINK, IN, OUT, LABEL;

        public Token makeToken(){
            return abstractToken(getType(), this.ordinal());
        }

        public Token.Type getType(){
            return Type.KEYWORD;
        }
    }

    public enum Delimiter implements TokenEnum {
        SEMICOLON, PLUS, MINUS, COMMA, COLON, EQUAL, DOUBLE_DOT, DOT,
        QUOTE, OPEN_BRACKET, CLOSE_BRACKET, ASTERISK, SLASH, AND, MOD, BANG, EXP, BACK_SLASH, XOR;

        private static final List<Delimiter> SINGLE_CHAR_LIST = Arrays.asList(
                SEMICOLON, PLUS, MINUS, COMMA, COLON, EQUAL, DOT,
                QUOTE, OPEN_BRACKET, CLOSE_BRACKET, ASTERISK, SLASH, AND, BANG, BACK_SLASH);

        public Token makeToken(){
            return abstractToken(getType(), this.ordinal());
        }

        public Token.Type getType(){
            if (SINGLE_CHAR_LIST.contains(this))
                return Type.SINGLE_CHAR;
            else
                return Type.DELIMITER;
        }
    }

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
    private  String view;

    public Token(String view, Type type, int index, Position position){
        this.view = view;
        this.type = type;
        this.position = new Position(position);
        this.index = index;
        this.id = OFFSETS[type.ordinal()] + index;
    }

    public Token (String view, int id, Position position){
        this.view = view;
        type = getTypeById(id);
        this.position = new Position(position);
        this.index = id - OFFSETS[type.ordinal()];
        this.id = id;
    }

    public static Token abstractToken(Type type, int index){
        return new Token("", type, index, new Position());
    }

    public static Token fromId(int id){
        return new Token("", id, new Position());
    }

    @Nullable
    public TokenEnum getEnum(){
        if (type == Type.KEYWORD){
            return Keyword.values()[index];
        }
        else if (type == Type.SINGLE_CHAR
                || type == Type.DELIMITER){
            return Delimiter.values()[index];
        }
        else {
            return null;
        }
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

    public Type getType(){return type;}
    public Position getPosition(){return position;}
    public int getIndex(){return index;}
    public int getId(){return id;}

    public String toString(){
        return view + " " + String.valueOf(id)+" "+position.toString();
        //return String.valueOf(id)+" "+position.toString();
    }

    public void setView(Grammar grammar){
        if (type == Type.SINGLE_CHAR || type == Type.DELIMITER)
            view = grammar.getDelimiters().get(index);
        else if(type == Type.KEYWORD)
            view = grammar.getKeywords().get(index);
        else if (type == Type.CONSTANT)
            view = String.valueOf(grammar.getConstants().get(index));
        else if (type == Type.IDENTIFIER)
            view = grammar.getIdentifiers().get(index);
    }

    public String getView(){
        return view;
    }

    public double getConstant(){
        if(type == Type.CONSTANT)
            return Double.valueOf(view);
        else
            return NaN;
    }

    public boolean isInteger(){
        double constant = getConstant();
        return constant != NaN && !Double.isInfinite(constant)
                && constant == Math.floor(constant) && constant < Integer.MAX_VALUE;
    }

    public int getInteger(){
        return (int) getConstant();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Token){
            return this.getId() == ((Token) other).getId();
        }
        else
            return false;
    }

    public boolean isEqual(TokenEnum tokenEnum){
        return equals(tokenEnum.makeToken());
    }

    @Override
    public int hashCode(){
        return Integer.valueOf(id).hashCode();
    }

    public static Token fromString(String line){
        String[] values = line.split(" ");
        return new Token(values[0], Integer.valueOf(values[1]),
                new Position(Integer.valueOf(values[2]), Integer.valueOf(values[3])));
    }
}
