package com.nutella;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Engine {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        openPrompt();
        User user = User.loginPrompt();

        if (user != null) {
            mainLoop(user);
            endSession(user);
        }

        closePrompt();
    }

    private static void openPrompt() {
        Engine.echo("Welcome to Nutella Wars!");
        Engine.echoLine();
    }

    private static void closePrompt() {
        echoLine();
        echo("Thanks for playing Nutella Wars!\n");
        in.close();
    }

    private static void mainLoop(User user) {
        boolean loop = true;
        int curLocation = 0;
        int choice;

        while (loop) {
            // print where you are
            echo("Location: " + Map.LOCATION[curLocation]);

            // print your options
            echo("What would you like to do?");
            echo("  (0) Exit the game");
            echo("  (1) Check your stats");

            for (int i = 0; i < Map.REACH[curLocation].length; ++i) {
                echo("  (" + (i+2) + ") Go to " + Map.LOCATION[Map.REACH[curLocation][i]]);
            }

            // parse input
            print("> ");
            choice = getInt();

            if (choice == 0) {
                loop = false;
            } else if (choice == 1) {
                echo(user.toString());
            } else {
                if (choice > -1 && choice - 2 < Map.REACH[curLocation].length) {
                    curLocation = choice - 2;
                } else {
                    error("that wasn't an option");
                }
            }
        }
    }

    private static void endSession(User user) {
        echoLine();
        echo("Saving account...");

        try {
            user.makeSaveFile();
            echo("Save successful!");
        } catch (FileNotFoundException e) {
            error("save file not created");
        }
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

