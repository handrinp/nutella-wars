package com.nutella;

public class Combat {
    private static final int ATTACK = 0;
    private static final int DEFEND = 1;
    private static final int SPELL  = 2;

    private static final String[] MENU_OPTIONS = {
        "Regular attack",
        "Parry/defend",
        "Use a special attack"
    };

    private static boolean playerTurn;

    public static void doBattle(User player) {
        User computer = User.makeOpponent(player.lv);
        playerTurn = Engine.randBool();

        while (player.curHP > 0 && computer.curHP > 0) {
            echoScoreboard(player, computer);

            if (playerTurn) {
                Engine.echo("Player turn");
                doPlayerTurn(player, computer);
            } else {
                Engine.echo("Computer turn");
                doComputerTurn(player, computer);
            }

            playerTurn = !playerTurn;
            Engine.echoLine();
        }

        if (computer.curHP == 0) {
            // win
            Engine.echo("You won!");
            Engine.echoLine();
        } else {
            // lose
            Engine.echo("You lost!");
            Engine.echoLine();
        }
    }

    private static void doPlayerTurn(User player, User computer) {
        int choice;

        // TODO: error checking
        Engine.echo("What would you like to do?");

        for (int i = 0; i < MENU_OPTIONS.length; ++i) {
            Engine.echo("  (" + i + ") " + MENU_OPTIONS[i]);
        }

        choice = Engine.getInt();
        Engine.echoLine();
    }

    private static void doComputerTurn(User player, User computer) {
        // AI stuff
        computer.curHP = 0;
    }

    private static void echoScoreboard(User player, User computer) {
        String hpLine = String.format("HP: %3d/%3d        Foe HP: %3d/%3d",
                player.curHP, player.maxHP, computer.curHP, computer.maxHP);
        String spLine = String.format("SP: %3d/%3d        Foe SP: %3d/%3d",
                player.curSP, player.maxSP, computer.curSP, computer.maxSP);
        Engine.echo(hpLine);
        Engine.echo(spLine);
    }

    private static boolean didWin(User player, User computer) {
        return computer.curHP == 0 && player.curHP > 0;
    }

    private static boolean didLose(User player, User computer) {
        return player.curHP == 0 && computer.curHP > 0;
    }
}

