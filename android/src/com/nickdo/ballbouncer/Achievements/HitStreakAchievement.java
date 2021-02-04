package com.nickdo.ballbouncer.Achievements;

import com.nickdo.ballbouncer.utils.StatsTracker;

public class HitStreakAchievement extends Achievement {

    public HitStreakAchievement(String name, int goal, int assetIndex) {
        super(name, goal, assetIndex);
    }

    @Override
    public int getProgress() {
        return StatsTracker.getInstance().getHitStreak();
    }

    @Override
    public boolean getIsUnlocked() {
        return StatsTracker.getInstance().getHitStreak() >= goal;
    }

    @Override
    public String getDescription() {
        return "Get min. 1 food per jump " + goal + " times.";
    }
}
