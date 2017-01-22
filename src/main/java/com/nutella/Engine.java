package com.nutella;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Engine {
    private static final String PRELINE = "|| ";

    private static Scanner in = null;
    private static PrintWriter err = null;

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
        in = new Scanner(System.in);

        try {
            File errFile = new File("log.txt");

            if (!errFile.isFile()) {
                new PrintWriter(errFile).close();
            }

            err = new PrintWriter(errFile);
        } catch (FileNotFoundException e) {
            error("could not open log.txt for writing", e);
        }

        Engine.echo("Welcome to Nutella Wars!");
        Engine.echoLine();
    }

    private static void closePrompt() {
        in.close();
        err.close();
        echo("Thanks for playing Nutella Wars!\n");
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
            echo("  (2) Learn more about this location");
            echo("  (3) Go inside");
            echo("  (4) Travel");

            // parse input
            print("> ");
            choice = getInt();
            echoLine();

            if (choice == Menu.EXIT_GAME) {
                loop = false;
            } else if (choice == Menu.CHECK_STATS) {
                user.echoStats();
            } else if (choice == Menu.GET_INFO) {
                Map.echoInfo(curLocation);
            } else if (choice == Menu.GO_INSIDE) {
                Map.goInside(curLocation, user);
            } else if (choice == Menu.TRAVEL) {
                curLocation = Map.travel(curLocation);
            } else {
                error("that wasn't an option");
            }
        }
    }

    private static void endSession(User user) {
        echo("Saving account...");

        try {
            user.makeSaveFile();
            echo("Save successful!");
        } catch (FileNotFoundException e) {
            error("save file not created", e);
        }

        echoLine();
    }

    public static void echo(String msg) {
        System.out.println(PRELINE + msg);
    }

    public static void echo() {
        echo("");
    }

    public static void print(String msg) {
        System.out.print(PRELINE + msg);
    }

    public static void error(String msg) {
        System.err.println("~~ ERROR - " + msg);
        if (err != null) err.println(timeStamp() + "  ERROR: " + msg);
    }
    
    public static void error(String msg, Exception e) {
        error(msg);
        if (err != null) err.println(e.getStackTrace());
    }
    
    private static String timeStamp() {
        return "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "]";
    }

    public static void echoLine() {
        echo();
        echo("==================================================");
        echo();
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
