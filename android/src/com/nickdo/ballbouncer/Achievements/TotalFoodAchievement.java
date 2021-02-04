package com.nickdo.ballbouncer.Achievements;

import com.nickdo.ballbouncer.utils.StatsTracker;

public class TotalFoodAchievement extends Achievement {

    public TotalFoodAchievement(String name, int goal, int assetIndex) {
        super(name, goal, assetIndex);
    }

    @Override
    public int getProgress() {
        return StatsTracker.getInstance().getTotalFoodCount();
    }

    @Override
    public boolean getIsUnlocked() {
        return StatsTracker.getInstance().getTotalFoodCount() >= goal;
    }

    @Override
    public String getDescription() {
        return "Get " + goal + " score points in total.";
    }
}
