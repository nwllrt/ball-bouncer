package com.nickdo.ballbouncer.Achievements;

import com.nickdo.ballbouncer.utils.StatsTracker;

public class HighScoreAchievement extends Achievement {

    public HighScoreAchievement(String name, int goal, int assetIndex) {
        super(name, goal, assetIndex);
    }

    @Override
    public int getProgress() {
        return StatsTracker.getInstance().getHighScore();
    }

    @Override
    public boolean getIsUnlocked() {
        return StatsTracker.getInstance().getHighScore() >= goal;
    }

    @Override
    public String getDescription() {
        return "Reach a high score of " + goal + ".";
    }
}
