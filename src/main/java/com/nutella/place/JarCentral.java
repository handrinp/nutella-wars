package com.nutella.place;

import com.nutella.Engine;
import com.nutella.Location;
import com.nutella.User;

public class JarCentral implements Location {
    public static JarCentral instance = null;

    private Location[] reach = null;

    private JarCentral() {}

    public static JarCentral getInstance() {
        if (instance == null) {
            instance = new JarCentral();
        }

        return instance;
    }

    public String getName() {
        return "Jar Central";
    }

    public String getInfo() {
        return "You can reach most places from here - wherever your jar desires.";
    }

    public void goInside(User user) {
    }

    public void openPrompt() {
    }

    public void closePrompt() {
    }

    public void mainLoop(User user) {
    }

    public Location[] getReach() {
        if (reach == null) {
            reach = new Location[] {
                Jarena.getInstance(),
                Hospital.getInstance(),
                Shop.getInstance()
            };
        }

        return reach;
    }
}

