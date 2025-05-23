package lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [input-file]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
//        runFile("C:\\Users\\z0053jek\\IdeaProjects\\jlox\\tests\\long_comment.jlox");
    }

    private static void runFile(String Path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(Path));
        run(new String(bytes, Charset.defaultCharset()));

        // Indicate an error in the exit code
        if (hadError) System.exit(65);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine(); // read user input on demand
            if (line == null) break; // ctrl-D
            run(line);
            // reset the hadError flag
            hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens(); // scan source code for tokens

        for (Token token : tokens) {
            System.out.println(token); // print tokens
        }
    }

    // Error Handling
    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[Line " + line + "] Error" + where + ": " + message
        );
        hadError = true;
    }
}
