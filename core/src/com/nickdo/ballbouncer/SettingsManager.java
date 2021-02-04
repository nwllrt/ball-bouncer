package com.nickdo.ballbouncer;

public class SettingsManager {

    public static final int LIGHT_MODE = 0;

    public static final int DARK_MODE = 1;

    private static SettingsManager repository;

    private int currentPlayerIndex = 0;

    private int currentColorMode = 0;

    private boolean sound = true;

    public static SettingsManager getInstance() {
        if (repository == null) {
            repository = new SettingsManager();
        }
        return repository;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int i) {
        currentPlayerIndex = i;
    }

    public int getColorMode() {
        return currentColorMode;
    }

    public void setColorMode(int i) {
        currentColorMode = i;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }
}
