package com.nutella;

public class Combat {
    private static boolean playerTurn;

    public static void doBattle(User player) {
        User computer = User.makeOpponent(player.lv);
        playerTurn = Engine.randBool();
        computer.echoStats();

        while (player.curHP > 0 && computer.curHP > 0) {
        }

        if (computer.curHP == 0) {
            // win
        } else {
            // lose
        }
    }

    private static boolean didWin(User player, User computer) {
        return computer.curHP == 0 && player.curHP > 0;
    }

    private static boolean didLose(User player, User computer) {
        return player.curHP == 0 && computer.curHP > 0;
    }
}

