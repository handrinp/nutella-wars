package com.nutella;

public class Jarena implements Location {
    private static Jarena instance = null;

    private Location[] reach = null;

    private Jarena() {}

    public String getName() {
        return "Jarena";
    }

    public String getInfo() {
        return "Spreads from all over the shelf come here to duke it out. Will you be vicjarious?";
    }

    private static final int GO_OUTSIDE = 0;
    private static final int GET_INFO   = 1;
    private static final int DO_BATTLE  = 2;

    private static final String[] MENU_OPTIONS = {
        "Go outside",
        "Learn more about the Jarena",
        "Battle"
    };

    public static Jarena getInstance() {
        if (instance == null) {
            instance = new Jarena();
        }

        return instance;
    }

    public void goInside(User user) {
        openPrompt();
        mainLoop(user);
        closePrompt();
    }

    public void openPrompt() {
        Engine.echo("Welcome to the Jarena.");
        Engine.echoLine();
    }

    public void closePrompt() {
        Engine.echo("We hope to see you again, brave jar.");
        Engine.echoLine();
    }

    public void mainLoop(User user) {
        boolean loop = true;
        int choice;
        int numOptions = MENU_OPTIONS.length;

        while (loop) {
            Engine.echo("What would you like to do?");

            for (int i = 0; i < numOptions; ++i) {
                Engine.echo("  (" + i + ") " + MENU_OPTIONS[i]);
            }

            // parse input
            choice = Engine.getInt();
            Engine.echoLine();

            if (choice == GO_OUTSIDE) {
                loop = false;
            } else if (choice == GET_INFO) {
                Engine.echo("Here you can battle all sorts of scary jars.");
                Engine.echo("After being matched up with a foe, you fight jar-to-jar until one emerges victorious.");
            } else if (choice == DO_BATTLE) {
                loop = Combat.doBattle(user);
            } else {
                Engine.error("that wasn't an option");
            }

            if (choice != GO_OUTSIDE) Engine.echoLine();
        }
    }

    public Location[] getReach() {
        if (reach == null) {
            reach = new Location[] {
                JarCentral.getInstance(),
                Hospital.getInstance()
            };
        }

        return reach;
    }
}

