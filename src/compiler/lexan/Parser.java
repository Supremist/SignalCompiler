package compiler.lexan;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by supremist on 3/22/16.
 */

public class Parser {
    private BufferedReader reader;
    private List<String> keywords;
    private List<String> identifiers;
    private List<String> delimiters;
    private List<Integer> lexemes;
    //private SortedMap<String, Complex> constants;
    private int maxDelimiterSize;
    private List<String> constants;
    private char currentChar;
    private StringBuilder buffer;
    private boolean isEnd;
    private boolean isComment;
    private static final int NOT_FOUND = -1;
    private static final int SINGLE_CHAR_OFFSET = 0;
    private static final int DELIMITERS_OFFSET = 300;
    private static final int KEYWORDS_OFFSET = 400;
    private static final int CONSTANT_OFFSET = 500;
    private static final int IDENTIFIER_OFFSET = 1000;
    private static final String COMMENT_STARTER = "(*";
    private static final String COMMENT_FINISHER = "*)";



    public Parser(){
        keywords = new ArrayList<>();
        identifiers = new ArrayList<>();
        delimiters = new ArrayList<>();
        constants = new ArrayList<>();
        lexemes = new ArrayList<>();
        buffer = new StringBuilder();
        //constants = new TreeMap<>();
        isEnd = true;
    }

    private void nextChar(){
        try {
            int ch = reader.read();
            if (ch != -1){
                currentChar = (char) ch;
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
        if ((index = keywords.indexOf(identifier)) != NOT_FOUND)
            lexemes.add(KEYWORDS_OFFSET + index);
        else
            lexemes.add(IDENTIFIER_OFFSET + addUniqueItem(identifier, identifiers));
    }

    private int addUniqueItem(String item, List<String> lines){
        int index;
        if((index = lines.indexOf(item)) == NOT_FOUND){
            lines.add(item);
            index = lines.size()-1;
        }
        return index;
    }

    private void parseConstant(){
        buffer.setLength(0);
        do{
            buffer.append(currentChar);
            nextChar();
        }while (!isEnd && Character.isDigit(currentChar));
        lexemes.add(CONSTANT_OFFSET + addUniqueItem(buffer.toString(), constants));
    }

    private void parseDelimiter() throws ParseException{
        char[] charBuffer = new char[maxDelimiterSize];
        int delimiterSize = maxDelimiterSize;
        charBuffer[0] = currentChar;
        try {
            reader.mark(maxDelimiterSize);
            delimiterSize = reader.read(charBuffer, 1, maxDelimiterSize-1);
            if (delimiterSize == -1)
                delimiterSize = 1;
            else
                delimiterSize += 1;
            reader.reset();
            int delimiterIndex = findDelimiter(String.copyValueOf(charBuffer,0,delimiterSize));
            if (delimiterIndex != -1){
                reader.skip(delimiters.get(delimiterIndex).length()-1);
                if (delimiters.get(delimiterIndex).equals(COMMENT_STARTER))
                    isComment = true;
                else if (delimiters.get(delimiterIndex).length() == 1)
                    lexemes.add(SINGLE_CHAR_OFFSET + delimiterIndex);
                else
                    lexemes.add(DELIMITERS_OFFSET + delimiterIndex);
            }
            else
                throw new ParseException("Unexpected symbol");
        }
        catch(IOException ex){
            //ex.printStackTrace();
            //Ignore, because one symbol was read correctly
        }
        nextChar();
    }

    private void parseComment() throws ParseException{
        int finisherPos = 0;
        while (!isEnd && finisherPos < COMMENT_FINISHER.length()){
            if (currentChar == COMMENT_FINISHER.charAt(finisherPos))
                finisherPos++;
            nextChar();
        }
        if (isEnd && finisherPos < COMMENT_FINISHER.length()) {
            throw new ParseException("Comment not closed correctly");
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

    public Parser parse(InputStream input) throws ParseException{
        reader = new BufferedReader(new InputStreamReader(input));
        isComment = false;
        isEnd = false;
        lexemes.clear();
        nextChar();
        while (!isEnd) {
            if (isComment)
                parseComment();
            else if (isDelimeterStart(String.valueOf(currentChar)))
                parseDelimiter();
            else if (Character.isDigit(currentChar))
                parseConstant();
            else if (Character.isLetter(currentChar))
                parseIdentifier();
            else if (Character.isWhitespace(currentChar))
                nextChar();
            else
                throw new ParseException("Unknown symbol");
        }
        closeReader();
        return this;
    }

    public Parser loadKeywords(InputStream input){
        keywords = loadLines(input);
        return this;
    }

    public Parser loadIdentifiers(InputStream input){
        identifiers = loadLines(input);
        return this;
    }

    public Parser loadDelimiters(InputStream input){
        delimiters = loadLines(input);
        delimiters.add(COMMENT_STARTER);
        maxDelimiterSize = 0;
        for (String delimiter: delimiters){
            if (delimiter.length() > maxDelimiterSize)
                maxDelimiterSize = delimiter.length();
        }
        return this;
    }


    public Parser writeConstants(OutputStream out){
        writeLines(constants, out);
        return this;
    }

    public Parser writeIdentifiers(OutputStream out){
        writeLines(identifiers, out);
        return this;
    }

    public Parser writeLexems(OutputStream out){
        StringBuilder builder = new StringBuilder();
        for (Integer lexeme: lexemes) {
            builder.append(lexeme);
            builder.append(" ");
        }
        List<String> set = new ArrayList<>();
        set.add(builder.toString());
        writeLines(set, out);
        return this;
    }

    private int findDelimiter(String string){
        int index = -1;
        for(int i = 0; i<delimiters.size(); ++i){
            if (string.startsWith(delimiters.get(i)) &&
                    (index == -1 || delimiters.get(i).length() > delimiters.get(index).length()))
                index = i;
        }
        return index;
    }

    private boolean isDelimeterStart(String delimeter){
        for(int i = 0; i<delimiters.size(); ++i){
            if(delimiters.get(i).startsWith(delimeter))
                return true;
        }
        return false;
    }

    private void writeLines(List<String> lines, OutputStream out){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(out));
            for (String line: lines){
                writer.write(line);
                writer.write('\n');
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    private List<String> loadLines(InputStream input){
        String line;
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
            while((line = reader.readLine()) != null){
                if (!line.isEmpty() && !lines.contains(line))
                    lines.add(line);
            }
        }
        catch(Exception error){
            error.printStackTrace();
        }
        return lines;
    }
}






/*public Parser loadConstants(InputStream input){
        List<String> lines = loadLines(input);
        for (String line: lines) {
            String[] values = line.split(":");
            if (values.length == 2)
                constants.put(values[0], new Complex().readFromStr(values[1]));
        }
        return this;
    }

    public Parser writeConstants(OutputStream out){
        List<String> lines = new ArrayList<>();
        for(String line: constants.keySet()){
            lines.add(line + ":" + constants.get(line).toString());
        }
        writeLines(lines, out);
        return this;
    }*/