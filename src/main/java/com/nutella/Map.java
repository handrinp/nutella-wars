package com.nutella;

public class Map {
    public static Map instance = null;

    private Location location = null;

    private Map() {
        location = JarCentral.getInstance();
    }

    public static final int JAR_CENTRAL = 0;
    public static final int JARENA = 1;
    public static final int HAZELNUT_HOSPITAL = 2;
    public static final int SHOP = 3;

    public static final int[][] REACH = {
        {1,2,3},
        {0,2},
        {0,1},
        {0}
    };

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }

        return instance;
    }

    public Location getLocation() {
        return location;
    }

    public void echoInfo() {
        Engine.echo(location.getInfo());
        Engine.echoLine();
    }

    public void goInside(User user) {
        location.goInside(user);
    }

    public void travel() {
        Location[] reach = location.getReach();
        Engine.echo("Travel where?");
        Engine.echo("  (" + 0 + ") Nowhere");

        for (int i = 1; i <= reach.length; ++i) {
            Engine.echo("  (" + i + ") " + reach[i - 1].getName());
        }

        // parse input
        int choice = Engine.getInt();

        if (choice == 0) {
            // do nothing - going nowhere
        } else if (choice > 0 && choice <= reach.length) {
            location = reach[choice - 1];
        } else {
            Engine.error("You can't go there");
        }

        Engine.echoLine();
    }
}

