package com.nutella;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    private String username;
    private int build;
    private int atk;
    private int def;
    private int exp;
    private int lv;
    private int curHP;
    private int maxHP;
    private int curSP;
    private int maxSP;
    private int gold;
    private int spells;

    // Verbose constructor
    public User(String username, int build, int atk, int def, int exp, int lv, int HP, int SP, int gold, int spells) {
        this.username = username;
        this.build = build;
        this.atk = atk;
        this.def = def;
        this.exp = exp;
        this.lv = lv;
        this.curHP = HP;
        this.maxHP = HP;
        this.curSP = SP;
        this.maxSP = SP;
        this.gold = gold;
        this.spells = spells;
    }


    public String toString() {
        return "Username: " + username + "\n" +
                "Build: " + (build == Build.OFFENSIVE ? "OFFENSIVE" : (build == Build.DEFENSIVE ? "DEFENSIVE" : "MAGICAL")) + "\n" +
                "Attack: " + atk + "\n" +
                "Defense: " + def + "\n" +
                "Experience: " + exp + "\n" +
                "Level: " + lv + "\n" +
                "HP: " + curHP + "/" + maxHP + "\n" +
                "SP: " + curSP + "/" + maxSP + "\n" +
                "Gold: " + gold + "\n" +
                "Spells: " + spells + "\n";
    }

    public void makeSaveFile() throws FileNotFoundException {
        File accountFile = fileFromUser(username);
        PrintWriter pw = new PrintWriter(accountFile);
        pw.printf("%d %d %d %d %d %d %d %d %d%n", build, atk, def, exp, lv, maxHP, maxSP, gold, spells);
        pw.close();
    }

    private static File fileFromUser(String username) {
        return new File("accounts/" + username + ".acc");
    }

    private static User login(String user) {
        User loggedInAs = null;

        // check if account exists
        File accountFile = fileFromUser(user);
        boolean accountExists = accountFile.isFile();

        if (accountExists) {
            // load stats in
            try {
                Scanner stat = new Scanner(accountFile);
                // rehydrate User
                loggedInAs = new User(user, stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt());
            } catch (FileNotFoundException e) {} // squelched because loggedInAs will remain null, as expected
        } else {
            // make new account?
            Engine.print("Account does not exist, would you like to make a new account? (0=YES, 1=NO): ");

            if (Engine.getInt() == 0) {
                // Create new jar
                // TODO: error checking
                Engine.print("What kind of jar do you want to be? (0=OFFENSIVE, 1=DEFENSIVE, 2=MAGICAL): ");
                loggedInAs = new User(user, Engine.getInt(), 5, 2, 0, 1, 15, 0, 100, 0);
            }
        }

        return loggedInAs;
    }

    public static User loginPrompt() {
        Engine.echo("Welcome to Nutella Wars!");
        Engine.echoLine();
        Engine.echo("Login");
        Engine.print("Username: ");
        String username = Engine.getString();
        Engine.echoLine();
        return login(username);
    }
}
