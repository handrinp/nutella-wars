package com.nutella;

public class Combat {
    public static void doBattle(User player) {
        User computer = User.makeOpponent(player.lv);
        computer.echoStats();
    }
}

