package com.nickdo.ballbouncer.Achievements;

public abstract class Achievement {
    private String name;
    protected int goal;
    private int assetIndex;

    public Achievement(String name, int goal, int assetIndex) {
        this.name = name;
        this.goal = goal;
        this.assetIndex = assetIndex;
    }

    public String getName() {
        return name;
    }

    public int getGoal() {
        return goal;
    }

    public String getAssetsName() {
        return "players/player" + assetIndex + ".png";
    }

    public int getAssetIndex() {
        return assetIndex;
    }

    public abstract int getProgress();

    public abstract boolean getIsUnlocked();

    public abstract String getDescription();
}
