package com.nutella;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Engine {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        User user = User.loginPrompt();

        if (user != null) {
            mainLoop(user);
        } else {
            error("login failed");
        }

        echoLine();
        echo("Thanks for playing Nutella Wars!\n");
        in.close();
    }

    private static void mainLoop(User user) {
        boolean loop;
        String input;

        do {
            // print where you are
            // print your options
            // parse input
            print("Enter \"exit\" to quit: ");
            input = getString();
            loop = !"exit".equals(input);
        } while (loop);

        // End of session
        echoLine();
        echo("Saving account...");

        try {
            user.makeSaveFile();
        } catch (FileNotFoundException e) {error("save file not created");}
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }

    public static void print(String msg) {
        System.out.print(msg);
    }

    public static void error(String msg) {
        System.err.println("ERROR - " + msg);
    }

    public static void echoLine() {
        echo("\n========================================\n");
    }

    /**
     * TODO: add error handling
     */
    public static int getInt() {
        return Integer.parseInt(in.nextLine());
    }

    public static String getString() {
        return in.nextLine();
    }
}
