package com.nickdo.ballbouncer.Achievements;

import com.nickdo.ballbouncer.utils.StatsTracker;

public class TotalGamesAchievement extends Achievement {

    public TotalGamesAchievement(String name, int goal, int assetIndex) {
        super(name, goal, assetIndex);
    }

    @Override
    public int getProgress() {
        return StatsTracker.getInstance().getTotalGames();
    }

    @Override
    public boolean getIsUnlocked() {
        return StatsTracker.getInstance().getTotalGames() >= goal;
    }

    @Override
    public String getDescription() {
        return "Play " + goal + " games.";
    }
}
