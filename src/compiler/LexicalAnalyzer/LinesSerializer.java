package compiler.LexicalAnalyzer;

import sun.plugin.javascript.navig.Array;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by supremist on 4/12/16.
 */
public class LinesSerializer {

    public static List<String> loadLines(InputStream input){
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

    public static void writeLines(List<String> lines, OutputStream out){
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
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception ex) {/*ignore*/}
        }
    }

    public static void writeLine(String line, OutputStream out){
        writeLines(Arrays.asList(line), out);
    }

}
