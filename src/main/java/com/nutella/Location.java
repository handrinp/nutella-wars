package com.nutella;

public interface Location {
    public void goInside(User user);
    public void openPrompt();
    public void closePrompt();
    public void mainLoop(User user);
    public String getName();
    public String getInfo();
    public Location[] getReach();
}

