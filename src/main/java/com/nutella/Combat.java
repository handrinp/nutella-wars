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

            fixStats(player);
            fixStats(computer);

            playerTurn = !playerTurn;
            Engine.echoLine();
        }

        if (computer.curHP == 0) {
            // win
            Engine.echo("You won!");
        } else {
            // lose
            Engine.echo("You browned out!");
        }
    }

    private static void fixStats(User user) {
        user.curDef = Math.max(user.curDef, 1);
        user.curAtk = Math.max(user.curAtk, 1);
        user.curHP = Math.max(user.curHP, 0);
    }

    private static void doPlayerTurn(User player, User computer) {
        int choice;
        int damage = 0;
        int defBonus = 0;
        int d20 = Engine.randInt(1, 19);

        // TODO: error checking
        Engine.echo("What would you like to do?");

        for (int i = 0; i < MENU_OPTIONS.length; ++i) {
            Engine.echo("  (" + i + ") " + MENU_OPTIONS[i]);
        }

        choice = Engine.getInt();
        Engine.echoLine();


        if (choice == ATTACK) {
            Engine.echo("You lunge your jar at the foe...");

            if (d20 == 20) {
                // crit
                Engine.echo("Your attack hit a weak spot!");
                damage = player.curAtk + 3;
            } else if (d20 > 4) {
                // hit
                damage = Engine.randInt(3, player.curAtk);
            } else {
                // miss
                Engine.echo("You miss!");
            }
        } else if (choice == DEFEND) {
            Engine.echo("You take a defensive stance");
            defBonus = 1;

            if (d20 > 18) {
                // counter attack
                Engine.echo("Psych! You counterattack and then take a defensive stance.");
                damage = 1;
            }
        } else if (choice == SPELL) {
            Engine.echo("SPELL STUB");
        } else {
            Engine.error("that wasn't an option");
            Engine.echoLine();
            doPlayerTurn(player, computer);
        }

        if (damage > 0) {
            damage = Math.max(1, damage - computer.curDef);
            Engine.echo("You deal " + damage + " damage!");
            computer.curHP -= damage;
        }

        if (defBonus > 0) {
            Engine.echo("Your defense increases by " + defBonus + ".");
            player.curDef += defBonus;
        }
    }

    private static void doComputerTurn(User player, User computer) {
        Engine.echo("STUBBED OUT COMPUTER TURN");
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

