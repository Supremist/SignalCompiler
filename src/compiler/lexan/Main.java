package compiler.lexan;
import java.io.*;
import java.util.Scanner;

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
            Parser ps = new Parser().loadKeywords(new FileInputStream(input + "keywords.txt"))
                    .loadDelimiters(new FileInputStream(input + "delimiters.txt"))
                    .parse(new FileInputStream(input + "program.txt"))
                    .writeLexems(new FileOutputStream(output + "result.txt", false))
                    .writeConstants(new FileOutputStream(output + "constants.txt", false))
                    .writeIdentifiers(new FileOutputStream(output + "identifiers.txt", false));

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
