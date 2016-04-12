package compiler.lexan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supremist on 4/12/16.
 */
public class Grammar {
    private List<String> keywords;
    private List<String> identifiers;
    private List<String> delimiters;
    private List<Double> constants;
    private int maxDelimiterSize;


    public Grammar(){
        keywords = new ArrayList<>();
        identifiers = new ArrayList<>();
        delimiters = new ArrayList<>();
        constants = new ArrayList<>();
        maxDelimiterSize = 0;
    }

    public static <T> int addUniqueItem(T item, List<T> lines){
        int index;
        if((index = lines.indexOf(item)) == -1){
            lines.add(item);
            index = lines.size()-1;
        }
        return index;
    }

    public Grammar setKeywords(List<String> keywords){
        this.keywords = keywords;
        return this;
    }

    public List<String> getKeywords(){
        return keywords;
    }

    public Grammar setIdentifiers(List<String> identifiers){
        this.identifiers = identifiers;
        return this;
    }

    public List<String> getIdentifiers(){
        return identifiers;
    }

    public Grammar setDelimiters(List<String> delimiters){
        maxDelimiterSize = 0;
        delimiters.stream().forEach((line) -> {
            if (line.length() > maxDelimiterSize)
                maxDelimiterSize = line.length();} );
        this.delimiters = delimiters;
        return this;
    }

    public List<String> getDelimiters(){
        return delimiters;
    }

    public Grammar setConstants(List<Double> constants){
        this.constants = constants;
        return this;
    }

    public List<Double> getConstants(){
        return constants;
    }

    public int getMaxDelimiterSize(){
        return maxDelimiterSize;
    }

    public boolean isDelimiterStart(String delimiter){
        for(String item: delimiters){
            if(item.startsWith(delimiter))
                return true;
        }
        return false;
    }

    public int findDelimiter(String string){
        int index = -1;
        for(int i = 0; i<delimiters.size(); ++i){
            if (string.startsWith(delimiters.get(i)) &&
                    (index == -1 || delimiters.get(i).length() > delimiters.get(index).length()))
                index = i;
        }
        return index;
    }
}
