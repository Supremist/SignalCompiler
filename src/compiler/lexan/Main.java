package compiler.lexan;
import compiler.SyntacticalAnalizer.Program;
import compiler.SyntacticalAnalizer.TokenIterator;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть шлях до папки з текстом програми (за замовчуванням input):");
        String input = scanner.nextLine();
        if (input.isEmpty())
            input = "input/";
        System.out.println("Введіть шлях до результуючої папки (за замовчуванням output):");
        String output = scanner.nextLine();
        if (output.isEmpty())
            output = "output/";
        try {
            Grammar grammar = new Grammar().setKeywords(
                    LinesSerializer.loadLines(new FileInputStream(input + "keywords.txt")))
                    .setDelimiters(LinesSerializer.loadLines(new FileInputStream(input + "delimiters.txt")));
            Parser parser = new Parser(grammar);
            parser.parse(new FileInputStream(input + "program.txt"));
            LinesSerializer.writeLines(
                    parser.getTokens().stream().map(String::valueOf).collect(Collectors.toList()),
                    new FileOutputStream(output + "result.txt", false));
            LinesSerializer.writeLines(grammar.getConstants().stream()
                            .map(String::valueOf).collect(Collectors.toList()),
                    new FileOutputStream(output + "constants.txt", false));
            LinesSerializer.writeLines(grammar.getIdentifiers(),
                    new FileOutputStream(output + "identifiers.txt", false));

            Program pr = new Program();
            pr.parse(new TokenIterator(parser.getTokens()));
            pr.setLevel(0);
            System.out.print(pr.toStringTree().toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
