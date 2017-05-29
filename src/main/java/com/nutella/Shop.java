package com.nutella;

public class Shop implements Location {
    private static Shop instance = null;

    private static final int GO_OUTSIDE = 0;
    private static final int GET_INFO   = 1;
    private static final int TRAIN_HP   = 2;
    private static final int TRAIN_ATK  = 3;
    private static final int TRAIN_DEF  = 4;
    private static final int TRAIN_SP   = 5;

    private static final int HP_COST  = 60;
    private static final int ATK_COST = 40;
    private static final int DEF_COST = 40;
    private static final int SP_COST  = 100;

    private static final int HP_VAL = 2;
    private static final int ATK_VAL = 1;
    private static final int DEF_VAL = 1;
    private static final int SP_VAL = 4;

    private static final int[] COST_OF = {0, 0, HP_COST, ATK_COST, DEF_COST, SP_COST};

    private static final String[] MENU_OPTIONS = {
        "Go outside",
        "Learn more about the shop",
        "[" + HP_COST  + " gold] - Train HP",
        "[" + ATK_COST + " gold] - Train ATK",
        "[" + DEF_COST + " gold] - Train DEF",
        "[" + SP_COST  + " gold] - Train SP"
    };

    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }

        return instance;
    }

    public void goInside(User user) {
        openPrompt();
        mainLoop(user);
        closePrompt();
    }

    public void openPrompt() {
        Engine.echo("Welcome to the shop.");
        Engine.echoLine();
    }

    public void closePrompt() {
        Engine.echo("Come again soon!");
        Engine.echoLine();
    }

    public void mainLoop(User user) {
        boolean loop = true;
        int choice;
        int numOptions = MENU_OPTIONS.length;

        if (user.lv == 1) {
            --numOptions;
        }

        while (loop) {
            Engine.echo("You have " + user.gold + " gold left.");
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
                Engine.echo("Here you can buy upgrades for your jar with gold.");
                Engine.echo("You can train HP, ATK, DEF, or SP.");
                Engine.echo("You can't train SP until level 2.");
            } else if (choice == TRAIN_HP) {
                if (user.gold < HP_COST) {
                    Engine.echo("You can't afford that.");
                } else {
                    user.gold -= HP_COST;
                    user.maxHP += HP_VAL;
                    user.curHP += HP_VAL;
                    Engine.echo("HP increased to " + user.maxHP);
                }
            } else if (choice == TRAIN_ATK) {
                if (user.gold < ATK_COST) {
                    Engine.echo("You can't afford that.");
                } else {
                    user.gold -= ATK_COST;
                    user.maxAtk += ATK_VAL;
                    user.curAtk += ATK_VAL;
                    Engine.echo("ATK increased to " + user.maxAtk);
                }
            } else if (choice == TRAIN_DEF) {
                if (user.gold < DEF_COST) {
                    Engine.echo("You can't afford that.");
                } else {
                    user.gold -= DEF_COST;
                    user.maxDef += DEF_VAL;
                    user.curDef += DEF_VAL;
                    Engine.echo("DEF increased to " + user.maxDef);
                }
            } else if (user.lv > 1 && choice == TRAIN_SP) {
                if (user.gold < SP_COST) {
                    Engine.echo("You can't afford that.");
                } else {
                    user.gold -= SP_COST;
                    user.maxSP += SP_VAL;
                    user.curSP += SP_VAL;
                    Engine.echo("SP increased to " + user.maxSP);
                }
            } else {
                Engine.error("that wasn't an option");
            }

            if (choice != GO_OUTSIDE) Engine.echoLine();
        }
    }
}

