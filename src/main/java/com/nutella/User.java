package com.nutella;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    public static final String[] RANDOM_NAMES = {"JarOffensive", "JarDefensive", "JarMagical"};

    public String username;
    public int build;
    public int atk;
    public int def;
    public int exp;
    public int lv;
    public int curHP;
    public int maxHP;
    public int curSP;
    public int maxSP;
    public int gold;
    public int spells;

    // internal constructor
    private User(String username, int build, int atk, int def, int exp, int lv, int HP, int SP, int gold, int spells) {
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

    public static User makeOpponent(int lv) {
        int seed = Engine.randInt(0, 3);
        int atkBonus = 0;
        int defBonus = 0;

        if (seed == Build.OFFENSIVE) {
            atkBonus = 2;
        } else if (seed == Build.DEFENSIVE) {
            defBonus = 2;
        } else if (seed == Build.MAGICAL) {
            atkBonus = 1;
            defBonus = 1;
        }

        return new User(RANDOM_NAMES[seed], seed, 5 + atkBonus, 2 + defBonus, 30, 1, 15, 0, 50, 0);
    }

    public void echoStats() {
        Engine.echo("Username: " + username);
        Engine.echo("Build: " + (build == Build.OFFENSIVE ? "OFFENSIVE" : (build == Build.DEFENSIVE ? "DEFENSIVE" : "MAGICAL")));
        Engine.echo("Attack: " + atk);
        Engine.echo("Defense: " + def);
        Engine.echo("Experience: " + exp);
        Engine.echo("Level: " + lv);
        Engine.echo("HP: " + curHP + "/" + maxHP);
        Engine.echo("SP: " + curSP + "/" + maxSP);
        Engine.echo("Gold: " + gold);
        Engine.echo("Spells: " + spells);
        Engine.echoLine();
    }

    public void makeSaveFile() throws FileNotFoundException {
        File accountFile = fileFromUser(username);
        PrintWriter pw = new PrintWriter(accountFile);
        pw.printf("%d %d %d %d %d %d %d %d %d%n", build, atk, def, exp, lv, maxHP, maxSP, gold, spells);
        pw.close();
    }

    private static File fileFromUser(String username) {
        File accountsDir = new File("accounts");

        if (!(accountsDir.isDirectory() || accountsDir.mkdir())) {
            Engine.error("could not make missing accounts directory");
        }

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
                loggedInAs = new User(user, stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(),
                        stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt(), stat.nextInt());
                Engine.echo("Logged in successfully!");
            } catch (FileNotFoundException e) {
                Engine.error("login failed", e);
            }
        } else {
            // make new account?
            Engine.echo("Account does not exist, would you like to make a new account? (0=YES, 1=NO)");

            // Create new jar
            if (Engine.getInt() == 0) {
                // TODO: error checking
                Engine.echo("What kind of jar do you want to be? (0=OFFENSIVE, 1=DEFENSIVE, 2=MAGICAL)");
                loggedInAs = new User(user, Engine.getInt(), 5, 2, 0, 1, 15, 0, 100, 0);
                Engine.echo("Account created successfully!");
            }
        }

        return loggedInAs;
    }

    public static User loginPrompt() {
        Engine.echo("Login");
        Engine.print("Username: ");
        User user = login(Engine.getString());
        Engine.echoLine();
        return user;
    }
}

