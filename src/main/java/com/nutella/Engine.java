package com.nutella;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Engine {
    private static final String PRELINE = "|| ";
    private static final String LOG_FILE = "nutella.log";

    private static final int EXIT_GAME   = 0;
    private static final int CHECK_STATS = 1;
    private static final int GET_INFO    = 2;
    private static final int GO_INSIDE   = 3;
    private static final int TRAVEL      = 4;

    private static final String[] LOGO = {
        "███╗   ██╗██╗   ██╗████████╗███████╗██╗     ██╗      █████╗ ",
        "████╗  ██║██║   ██║╚══██╔══╝██╔════╝██║     ██║     ██╔══██╗",
        "██╔██╗ ██║██║   ██║   ██║   █████╗  ██║     ██║     ███████║",
        "██║╚██╗██║██║   ██║   ██║   ██╔══╝  ██║     ██║     ██╔══██║",
        "██║ ╚████║╚██████╔╝   ██║   ███████╗███████╗███████╗██║  ██║",
        "╚═╝  ╚═══╝ ╚═════╝    ╚═╝   ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝",
        "             ██╗    ██╗ █████╗ ██████╗ ███████╗",
        "             ██║    ██║██╔══██╗██╔══██╗██╔════╝",
        "             ██║ █╗ ██║███████║██████╔╝███████╗",
        "             ██║███╗██║██╔══██║██╔══██╗╚════██║",
        "             ╚███╔███╔╝██║  ██║██║  ██║███████║",
        "              ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝"
    };

    private static final String[] MENU_OPTIONS = {
        "Exit the game",
        "Check your stats",
        "Learn more about this location",
        "Go inside",
        "Travel"
    };

    private static Scanner in = null;
    private static PrintWriter err = null;
    private static Random RNG = null;
    private static int curLocation;

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
        RNG = new Random();
        in = new Scanner(System.in);

        try {
            File errFile = new File(LOG_FILE);

            if (!errFile.isFile()) {
                new PrintWriter(errFile).close();
            }

            err = new PrintWriter(new BufferedWriter(new FileWriter(errFile, true)));
        } catch (IOException e) {
            error("could not open " + LOG_FILE + " for writing");
        }

        Engine.echoLine();

        for (int i = 0; i < LOGO.length; ++i) {
            Engine.echo(LOGO[i]);
        }

        Engine.echoLine();
    }

    private static void closePrompt() {
        echo("Thanks for playing Nutella Wars!\n");
        in.close();
        err.close();
    }

    private static void mainLoop(User user) {
        boolean loop = true;
        int choice;
        Map map = Map.getInstance();

        while (loop) {
            // print where you are
            echo("Location: " + map.getLocation().getName());

            // print your options
            echo("What would you like to do?");

            for (int i = 0; i < MENU_OPTIONS.length; ++i) {
                echo("  (" + i + ") " + MENU_OPTIONS[i]);
            }

            // parse input
            choice = getInt();
            echoLine();

            if (choice == EXIT_GAME) {
                loop = false;
            } else if (choice == CHECK_STATS) {
                user.echoStats();
            } else if (choice == GET_INFO) {
                map.echoInfo();
            } else if (choice == GO_INSIDE) {
                map.goInside(user);
            } else if (choice == TRAVEL) {
                map.travel();
            } else {
                error("that wasn't an option");
            }
        }
    }

    public static void teleport(int location) {
        curLocation = location;
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

    public static void print(String msg) {
        System.out.print(PRELINE + msg);
        if (err != null) err.print(timeStamp() + "   GAME: " + msg);
    }

    public static void echo(String msg) {
        print(msg + System.getProperty("line.separator"));
    }

    public static void echo() {
        echo("");
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
        echo("============================================================");
        echo();
    }

    // TODO: add error handling
    public static int getInt() {
        int val = 0;
        boolean invalid = true;

        while (invalid) {
            try {
                print("> ");
                val = Integer.parseInt(in.nextLine());
                invalid = false;
            } catch (NumberFormatException e) {
                error("that's not a number", e);
            }
        }

        if (err != null) err.println(val);
        return val;
    }

    public static String getString() {
        String val = in.nextLine();
        if (err != null) err.println(val);
        return val;
    }

    public static int randInt(int low, int spread) {
        return low + RNG.nextInt(spread);
    }

    public static boolean randBool() {
        return RNG.nextBoolean();
    }
}

