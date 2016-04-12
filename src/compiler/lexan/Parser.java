package compiler.lexan;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by supremist on 3/22/16.
 */

public class Parser {
    private BufferedReader reader;
    private List<Token> tokens;
    private Grammar grammar;
    private char currentChar;
    private Position currentPosition;
    private StringBuilder buffer;
    private boolean isEnd;
    private boolean isComment;
    private static final String COMMENT_STARTER = "(*";
    private static final String COMMENT_FINISHER = "*)";



    public Parser(){
        grammar = new Grammar();
        tokens = new ArrayList<>();
        buffer = new StringBuilder();
        //constants = new TreeMap<>();
        isEnd = true;
    }

    public Parser(Grammar grammar){
        this();
        setGrammar(grammar);
    }

    private void nextChar(){
        try {
            int ch = reader.read();
            if (ch != -1){
                currentChar = (char) ch;
                currentPosition.nextSymbol();
            }
            else{
                isEnd = true;
            }
        }
        catch (Exception error){
            isEnd = true;
            error.printStackTrace();
        }
    }

    private void parseIdentifier(){
        buffer.setLength(0);
        do {
            buffer.append(currentChar);
            nextChar();
        }while(!isEnd && Character.isLetterOrDigit(currentChar));
        String identifier = buffer.toString();
        int index;
        if ((index = grammar.getKeywords().indexOf(identifier)) != -1)
            addToken(Token.Type.KEYWORD, index);
        else
            addToken(Token.Type.IDENTIFIER, Grammar.addUniqueItem(identifier, grammar.getIdentifiers()));
    }

    private double parseDigits(){
        buffer.setLength(0);
        do{
            buffer.append(currentChar);
            nextChar();
        }while (!isEnd && Character.isDigit(currentChar));
        return Double.valueOf(buffer.toString());
    }


    private void parseDelimiter() throws ParseException{
        int maxSize = grammar.getMaxDelimiterSize();
        char[] charBuffer = new char[maxSize];
        int delimiterSize;
        charBuffer[0] = currentChar;
        try {
            reader.mark(maxSize);
            delimiterSize = reader.read(charBuffer, 1, maxSize-1);
            if (delimiterSize == -1)
                delimiterSize = 1;
            else
                delimiterSize += 1;
            reader.reset();
            int delimiterIndex = grammar.findDelimiter(String.copyValueOf(charBuffer,0,delimiterSize));
            if (addDelimiter(delimiterIndex))
                reader.skip(grammar.getDelimiters().get(delimiterIndex).length()-1);
            else
                throw new ParseException("Unexpected symbol", currentPosition);
        }
        catch(IOException ex){
            //ex.printStackTrace();
            //Ignore, because one symbol was read correctly
        }
        nextChar();
    }

    private boolean addDelimiter(int delimiterIndex){
        if (delimiterIndex >= 0){
            String delimiter = grammar.getDelimiters().get(delimiterIndex);
            if (delimiter.equals(COMMENT_STARTER))
                isComment = true;
            else if (delimiter.length() == 1)
                addToken(Token.Type.SINGLE_CHAR, delimiterIndex);
            else
                addToken(Token.Type.DELIMITER, delimiterIndex);
            return true;
        }
        return false;
    }

    private void addToken(Token.Type type, int index){
        tokens.add(new Token(type, currentPosition, index));
    }

    private void parseComment() throws ParseException{
        int finisherPos = 0;
        while (!isEnd && finisherPos < COMMENT_FINISHER.length()){
            if (currentChar == COMMENT_FINISHER.charAt(finisherPos))
                finisherPos++;
            nextChar();
        }
        if (isEnd && finisherPos < COMMENT_FINISHER.length()) {
            throw new ParseException("Comment not closed correctly", currentPosition);
        }
        isComment = false;
    }

    private void closeReader(){
        try{
            if (reader != null)
                reader.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void parseConstant() throws ParseException{
        double constant = parseDigits();
        if(currentChar == '#'){
            nextChar();
            boolean isMinus = false;
            if(currentChar == '-'){
                isMinus = true;
                nextChar();
            }
            else if(currentChar == '+'){
                isMinus = false;
                nextChar();
            }
            if(Character.isDigit(currentChar)){
                constant /= parseDigits();
                if(isMinus)
                    constant = -constant;
            }
            else
                throw new ParseException("Digit expected", currentPosition);
        }
        addToken(Token.Type.CONSTANT, Grammar.addUniqueItem(constant, grammar.getConstants()));
    }

    public Parser parse(InputStream input) throws ParseException{
        reader = new BufferedReader(new InputStreamReader(input));
        currentPosition = new Position(1, 0);
        isComment = false;
        isEnd = false;
        tokens.clear();
        nextChar();
        while (!isEnd) {
            if (isComment)
                parseComment();
            else if (grammar.isDelimiterStart(String.valueOf(currentChar)))
                parseDelimiter();
            else if (Character.isDigit(currentChar))
                parseConstant();
            else if (Character.isLetter(currentChar))
                parseIdentifier();
            else if (currentChar == '\n') {
                currentPosition.nextLine();
                nextChar();
            }
            else if (Character.isWhitespace(currentChar))
                nextChar();
            else
                throw new ParseException("Unknown symbol", currentPosition);
        }
        closeReader();
        return this;
    }

    public List<Token> getTokens(){
        return tokens;
    }

    public Parser setGrammar(Grammar grammar){
        this.grammar = grammar;
        this.grammar.getDelimiters().add(COMMENT_STARTER);
        return this;
    }

    public Grammar getGrammar(){ return grammar;}

}
