package compiler.lexan;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            Parser ps = new Parser().loadKeywords(new FileInputStream("input/keywords.txt"))
                    .loadDelimiters(new FileInputStream("input/delimiters.txt"))
                    .parse(new FileInputStream("input/program.txt"))
                    .writeLexems(new FileOutputStream("output/result.txt", false));

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
