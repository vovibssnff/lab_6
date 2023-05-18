package serv.managment;

import java.util.Scanner;

public class ServerController extends Thread {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
        } while (!input.equalsIgnoreCase("exit"));

        System.exit(0);
    }
}
