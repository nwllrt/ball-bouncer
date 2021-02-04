package com.nickdo.ballbouncer.Achievements;

import com.nickdo.ballbouncer.utils.StatsTracker;

public class CollectedTogetherAchievement extends Achievement {

    public CollectedTogetherAchievement(String name, int goal, int assetIndex) {
        super(name, goal, assetIndex);
    }

    @Override
    public int getProgress() {
        return StatsTracker.getInstance().getCollectedTogether();
    }

    @Override
    public boolean getIsUnlocked() {
        return StatsTracker.getInstance().getCollectedTogether() >= goal;
    }

    @Override
    public String getDescription() {
        return "Collect " + goal + " food in one jump.";
    }
}
