package com.nutella;

public class Hospital implements Location {
    private static Hospital instance = null;

    private static final int GO_OUTSIDE = 0;
    private static final int GET_INFO   = 1;
    private static final int GET_HEALED = 2;

    private static final String[] MENU_OPTIONS = {
        "Go outside",
        "Learn more about the Hazelnut Hospital",
        "Heal your jar"
    };

    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }

        return instance;
    }

    public void goInside(User user) {
        openPrompt();
        mainLoop(user);
        closePrompt();
    }

    public void openPrompt() {
        Engine.echo("Welcome to the Hazelnut Hospital.");
        Engine.echoLine();
    }

    public void closePrompt() {
        Engine.echo("Good luck out there!");
        Engine.echoLine();
    }

    public void mainLoop(User user) {
        boolean loop = true;
        int choice;

        while (loop) {
            Engine.echo("What would you like to do?");

            for (int i = 0; i < MENU_OPTIONS.length; ++i) {
                Engine.echo("  (" + i + ") " + MENU_OPTIONS[i]);
            }

            // parse input
            choice = Engine.getInt();
            Engine.echoLine();

            if (choice == GO_OUTSIDE) {
                loop = false;
            } else if (choice == GET_INFO) {
                Engine.echo("Many jars get damaged or broken in the Jarena.");
                Engine.echo("Here you can heal your jar.");
            } else if (choice == GET_HEALED) {
                getHealed(user);
            } else {
                Engine.error("that wasn't an option");
            }

            if (choice != GO_OUTSIDE) Engine.echoLine();
        }
    }

    public static void getHealed(User player) {
        player.curHP = player.maxHP;
        player.curSP = player.maxSP;
        Engine.echo("You have been healed to full health.");
    }
}

