package com.nutella;

public class Map {
    public static final int JAR_CENTRAL = 0;
    public static final int JARENA = 1;
    public static final int HAZELNUT_HOSPITAL = 2;
    public static final int SHOP = 3;

    public static final String[] LOCATION = {
        "Jar Central",
        "Jarena",
        "Hazelnut Hospital",
        "Shop"
    };

    public static final String[] INFO = {
        "You can reach most places from here - wherever your jar desires.",
        "Spreads from all over the shelf come here to duke it out. Will you be vicjarious?",
        "Is your jar all cracked up? Are you running low on SP? Nurse Jar can help!",
        "This is no ordinary shop. With gold, you can upgrade any of your stats!"
    };

    public static final int[][] REACH = {
        {1,2,3},
        {0,2},
        {0,1},
        {0}
    };

    public static void echoInfo(int curLocation) {
        Engine.echo(Map.INFO[curLocation]);
        Engine.echoLine();
    }

    public static void goInside(int curLocation, User user) {
        if (curLocation == JAR_CENTRAL) {
        } else if (curLocation == JARENA) {
        } else if (curLocation == HAZELNUT_HOSPITAL) {
            Hospital.goInside(user);
        } else if (curLocation == SHOP) {
            Shop.goInside(user);
        }
    }

    public static int travel(int curLocation) {
        Engine.echo("Travel where?");

        for (int i = 0; i < Map.REACH[curLocation].length; ++i) {
            Engine.echo("  (" + i + ") " + Map.LOCATION[Map.REACH[curLocation][i]]);
        }

        // parse input
        int choice = Engine.getInt();

        if (choice > -1 && choice < LOCATION.length) {
            curLocation = Map.REACH[curLocation][choice];
        } else {
            Engine.error("you can't go there");
        }

        Engine.echoLine();
        return curLocation;
    }
}
