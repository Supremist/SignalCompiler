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
    private List<String> singlechars;
    private List<Integer> lexemes;
    //private SortedMap<String, Complex> constants;
    private List<Integer> constants;
    private char currentChar;
    private StringBuilder buffer;
    private boolean isEnd;
    private static final int NOT_FOUND = -1;
    private static final int SINGLE_CHAR_OFFSET = 0;
    private static final int DELIMITERS_OFFSET = 300;
    private static final int KEYWORDS_OFFSET = 400;
    private static final int CONSTANT_OFFSET = 500;
    private static final int IDENTIFIER_OFFSET = 1000;



    public Parser(){
        keywords = new ArrayList<>();
        identifiers = new ArrayList<>();
        delimiters = new ArrayList<>();
        singlechars = new ArrayList<>();
        constants = new ArrayList<>();
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
        addIdentifier(buffer.toString());
    }

    private void addIdentifier(String identifier){
        int index;
        if((index = identifiers.indexOf(identifier)) == NOT_FOUND){
            identifiers.add(identifier);
            index = identifiers.size()-1;
        }
        lexemes.add(IDENTIFIER_OFFSET+index);
    }

    private void parseConstant(){

    }

    public Parser parse(InputStream input){
        nextChar();
        while (!isEnd){
            if (Character.isLetter(currentChar))
                parseIdentifier();
            else if (Character.isDigit(currentChar))
                parseConstant();
        }

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
        List<String> lines = loadLines(input);
        for(String line: lines){
            if (line.length() == 1)
                singlechars.add(line);
            else
                delimiters.add(line);
        }
        return this;
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

    public Parser writeConstants(OutputStream out){
        writeLines(constants.stream()
                .map((i) -> i.toString())
                .collect(Collectors.toList()), out);
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

    private boolean isDelimeterStart(String delimeter){
        for(int i = 0; i<delimiters.size(); ++i){
            if(delimiters.get(i).startsWith(delimeter) &&
                    !delimiters.get(i).equals(delimeter))
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
