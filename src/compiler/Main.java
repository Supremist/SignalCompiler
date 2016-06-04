package compiler;
import compiler.Exceptions.CompileException;
import compiler.SyntacticalAnalyzer.CompilationInfo;
import compiler.SyntacticalAnalyzer.Program;
import compiler.SyntacticalAnalyzer.TokenIterator;
import compiler.LexicalAnalyzer.Grammar;
import compiler.LexicalAnalyzer.LinesSerializer;
import compiler.Exceptions.ParseException;
import compiler.LexicalAnalyzer.Parser;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static final String SETTINGS_DIRECTORY = "settings/";
    public static final String INPUT_DELIMITERS_FILENAME = "delimiters.txt";
    public static final String INPUT_KEYWORDS_FILENAME = "keywords.txt";
    public static final String DEFAULT_INPUT = "input/program.txt";
    public static final String OUTPUT_CONSTANTS_FILENAME = "constants.txt";
    public static final String OUTPUT_IDENTIFIERS_FILENAME = "identifiers.txt";
    public static final String OUTPUT_TOKENS_FILENAME = "tokens.txt";
    public static final String OUTPUT_SYNTAX_TREE_FILENAME = "syntax_tree.xml";
    public static final String OUTPUT_ASM_CODE_FILENAME = "asm_code.asm";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Enter the source code file path (default value: %s): ", DEFAULT_INPUT));
        String input = scanner.nextLine();
        if (input.isEmpty())
            input = DEFAULT_INPUT;
        File in = new File(input);
        String default_output = in.getParent() + "/"
                + in.getName().split("\\.")[0]
                + "_output/";
        System.out.println(String.format("Enter the output directory path (default value: %s):", default_output));
        String output = scanner.nextLine();
        if (output.isEmpty()) {
            output = default_output;
        }

        try {

            File out = new File(output);
            if (!out.exists() || !out.isDirectory()) {
                if(!out.mkdir()){
                    throw new FileNotFoundException();
                }
            }

            // set keywords and delimiters to grammar
            Grammar grammar = new Grammar()
                    .setKeywords  (LinesSerializer.loadLines(
                            new FileInputStream(SETTINGS_DIRECTORY + INPUT_KEYWORDS_FILENAME)))
                    .setDelimiters(LinesSerializer.loadLines(
                            new FileInputStream(SETTINGS_DIRECTORY + INPUT_DELIMITERS_FILENAME)));

            //initialize parser
            Parser parser = new Parser(grammar);

            try {
                //parse the input file
                parser.parse(new FileInputStream(input));
            } catch (ParseException ex){
                ex.printStackTrace();
            }

            //write lexical analyzer output
            LinesSerializer.writeLines(
                    parser.getTokens().stream().map(String::valueOf).collect(Collectors.toList()),
                    new FileOutputStream(output + OUTPUT_TOKENS_FILENAME, false));

            LinesSerializer.writeLines(
                    grammar.getConstants().stream().map(String::valueOf).collect(Collectors.toList()),
                    new FileOutputStream(output + OUTPUT_CONSTANTS_FILENAME, false));

            LinesSerializer.writeLines(grammar.getIdentifiers(),
                    new FileOutputStream(output + OUTPUT_IDENTIFIERS_FILENAME, false));

            Program program = new Program();
            CompilationInfo info = new CompilationInfo();

            try {
                // build syntax tree
                program.parse(new TokenIterator(parser.getTokens()));
                //set level of syntax tree nodes
                program.setLevel(0);

                LinesSerializer.writeLine(program.toXmlView().toString(),
                        new FileOutputStream(output + OUTPUT_SYNTAX_TREE_FILENAME));

                // compile program
                program.Compile(info);
                LinesSerializer.writeLine(program.toAsmCode(info).toString(),
                        new FileOutputStream(output + OUTPUT_ASM_CODE_FILENAME));
            }
            catch (ParseException ex){
                ex.printStackTrace();
            }
            //System.out.print(program.toStringTree().toString());
            //System.out.print(program.toXmlView().toString());
            //System.out.println(program.toAsmCode(info));
            System.out.println("Compilation done.");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
