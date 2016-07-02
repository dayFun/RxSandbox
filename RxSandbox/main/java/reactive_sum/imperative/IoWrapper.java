package reactive_sum.imperative;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IoWrapper {
    private final Scanner scanner;
    private final PrintStream output;

    public IoWrapper(InputStream input, PrintStream output) {
        scanner = new Scanner(input);
        this.output = output;
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        output.print(message);
    }
}
