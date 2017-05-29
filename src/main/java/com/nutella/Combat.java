package com.nutella;

import com.nutella.place.Hospital;

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

    public static boolean doBattle(User player) {
        boolean won = false;
        User computer = User.makeOpponent(player.lv);
        playerTurn = Engine.randBool();

        while (player.curHP > 0 && computer.curHP > 0) {
            echoScoreboard(player, computer);

            if (playerTurn) {
                doPlayerTurn(player, computer);
            } else {
                doComputerTurn(player, computer);
            }

            fixStats(player);
            fixStats(computer);

            playerTurn = !playerTurn;
            Engine.echoLine();
        }

        if (computer.curHP == 0) {
            won = true;
            doWin(player, computer);
        } else {
            doLose(player);
        }

        return won;
    }

    private static void doWin(User player, User computer) {
        Engine.echo("You won!");

        // gold reward
        player.gold += computer.gold;
        String goldLine = String.format("You got %d gold for winning.", computer.gold);
        Engine.echo(goldLine);

        // exp reward
        int expBonus = Engine.randInt(computer.exp, 15);
        player.exp += expBonus;
        String expLine = String.format("You got %d exp for winning.", expBonus);
        Engine.echo(expLine);

        // check for level up
        player.checkForLevelUp();
    }

    private static void doLose(User player) {
        Engine.echo("You browned out!");
        Engine.echo("You've been carried to the hospital.");
        Engine.teleport(Map.HAZELNUT_HOSPITAL);
        Hospital.getHealed(player);
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
        int magicThreshold = computer.build == Build.MAGICAL ? 13 : 17;
        int damage = 0;
        int defBonus = 0;
        int choice;

        // roll for turn choice
        int d20 = Engine.randInt(1, 19);

        if (d20 < 11) {
            choice = ATTACK;
        } else if (d20 > magicThreshold) {
            choice = SPELL;
        } else {
            choice = DEFEND;
        }

        // reroll for choice calculation
        d20 = Engine.randInt(1, 19);

        if (choice == ATTACK) {
            Engine.echo("Your foe lunges their jar at you...");

            if (d20 == 20) {
                // crit
                Engine.echo("Their attack hit a weak spot!");
                damage = computer.curAtk + 3;
            } else if (d20 > 4) {
                // hit
                damage = Engine.randInt(3, computer.curAtk);
            } else {
                // miss
                Engine.echo("They miss!");
            }
        } else if (choice == DEFEND) {
            Engine.echo("They take a defensive stance");
            defBonus = 1;

            if (d20 > 18) {
                // counter attack
                Engine.echo("Psych! They counterattack and then take a defensive stance.");
                damage = 1;
            }
        } else {
            Engine.echo("SPELL STUB");
        }

        if (damage > 0) {
            damage = Math.max(1, damage - player.curDef);
            Engine.echo("They deal " + damage + " damage!");
            player.curHP -= damage;
        }

        if (defBonus > 0) {
            Engine.echo("Their defense increases by " + defBonus + ".");
            computer.curDef += defBonus;
        }
    }

    private static void echoScoreboard(User player, User computer) {
        String hpLine = String.format("HP: %3d/%3d        Foe HP: %3d/%3d",
                player.curHP, player.maxHP, computer.curHP, computer.maxHP);
        String spLine = String.format("SP: %3d/%3d        Foe SP: %3d/%3d",
                player.curSP, player.maxSP, computer.curSP, computer.maxSP);
        Engine.echo(hpLine);
        Engine.echo(spLine);
    }
}

