package com.nutella;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    public static final String[] RANDOM_NAMES = {"JarOffensive", "JarDefensive", "JarMagical"};
    public static final int[] EXP = {83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411};

    public String username;
    public int build;
    public int curAtk;
    public int maxAtk;
    public int curDef;
    public int maxDef;
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
        this.curAtk = atk;
        this.maxAtk = atk;
        this.curDef = def;
        this.maxDef = def;
        this.exp = exp;
        this.lv = lv;
        this.curHP = HP;
        this.maxHP = HP;
        this.curSP = SP;
        this.maxSP = SP;
        this.gold = gold;
        this.spells = spells;
    }

    public void checkForLevelUp() {
        boolean levelledUp = false;

        while (exp > EXP[lv - 1]) {
            ++lv;
            levelledUp = true;

            if (levelledUp) {
                Engine.echo("Congrats! You are now level " + lv);
            }

            if (build == Build.OFFENSIVE) {
                ++maxAtk;
                if (lv == 2) {
                    learnSpell(Spells.SLAM);
                    Engine.echo("You learned the spell SLAM");
                } else if (lv == 4) {
                    learnSpell(Spells.BARRAGE);
                    Engine.echo("You learned the spell BARRAGE");
                } else if (lv == 6) {
                    learnSpell(Spells.PUMMEL);
                    Engine.echo("You learned the spell PUMMEL");
                }
            } else if (build == Build.DEFENSIVE) {
                ++maxDef;
            } else if (build == Build.MAGICAL) {
                maxSP += 4;
            }

            maxSP += 4;
        }
    }

    public void learnSpell(int spell) {
        spells ^= spell;
    }

    public void cast(int spell, User target) {
        int cost = Spells.cost(spell);
        int d20 = Engine.randInt(1, 19);

        if (hasSpell(spell) && curSP > cost) {
            int damage = 0;
            int oldAtk = curAtk;
            curSP -= cost;

            if (spell == Spells.SLAM) {
                Engine.echo("SLAM was cast");
                curAtk = (curAtk + (curAtk << 1)) >> 1;

                if (d20 == 20) {
                    // crit
                    Engine.echo("The attack hit a weak spot!");
                    damage = curAtk + 3;
                } else if (d20 > 4) {
                    // hit
                    damage = Engine.randInt(3, curAtk);
                } else {
                    // miss
                    Engine.echo("It misses!");
                }
            } else if (spell == Spells.BARRAGE) {
                Engine.echo("BARRAGE was cast");

                for (int i = 0; i < 3; ++i) {
                    d20 = Engine.randInt(1, 19);

                    if (d20 == 20) {
                        // crit
                        Engine.echo("The attack hit a weak spot!");
                        damage = curAtk + 3;
                    } else if (d20 > 4) {
                        // hit
                        damage = Engine.randInt(3, curAtk);
                    } else {
                        // miss
                        Engine.echo("It misses!");
                    }
                }
            } else if (spell == Spells.PUMMEL) {
            } else if (spell == Spells.HARDEN) {
            }

            if (damage > 0) {
                damage = Math.max(1, damage - target.curDef);
                Engine.echo(damage + " damage dealt!");
                target.curHP -= damage;
            }

            curAtk = oldAtk;
        }
    }

    public static User makeOpponent(int lv) {
        int seed = Engine.randInt(0, 3);
        int hpBonus = lv << 2;
        int atkBonus = 0;
        int defBonus = 0;
        int spBonus = 0;
        int expBonus = lv << 5;

        if (seed == Build.OFFENSIVE) {
            atkBonus = 2 + lv;
        } else if (seed == Build.DEFENSIVE) {
            defBonus = (2 + lv) >> 1;
        } else if (seed == Build.MAGICAL) {
            atkBonus = (1 + lv) >> 1;
            defBonus = (1 + lv) >> 1;
            spBonus = lv << 2;
        }

        return new User(RANDOM_NAMES[seed], seed, 5 + atkBonus, 2 + defBonus, expBonus, lv, 10 + hpBonus, spBonus, 50, 0);
    }

    public void echoStats() {
        Engine.echo("Username: " + username);
        Engine.echo("Build: " + (build == Build.OFFENSIVE ? "OFFENSIVE" : (build == Build.DEFENSIVE ? "DEFENSIVE" : "MAGICAL")));
        Engine.echo("Attack: " + maxAtk);
        Engine.echo("Defense: " + maxDef);
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
        pw.printf("%d %d %d %d %d %d %d %d %d%n", build, maxAtk, maxDef, exp, lv, maxHP, maxSP, gold, spells);
        pw.close();
    }

    public boolean hasSpell(int spell) {
        return (spells & spell) == spell;
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

