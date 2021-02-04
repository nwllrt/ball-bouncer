package com.nickdo.ballbouncer.utils;

public class StatsTracker {
    private static StatsTracker instance;
    private int currentFoodCount;
    private int totalFoodCount;
    private int platformHits;
    private int currentClicks;
    private int totalClicks;
    private int currentCollectedTogetherCount;
    private int currentHitStreak;
    private boolean canUpdateCollectedTogether;
    private boolean canUpdateHitStreak;

    private int collectedTogether;
    private int highScore;
    private int totalGames;
    private int hitStreak;

    public StatsTracker() {
        newGame();
    }

    public static StatsTracker getInstance() {
        if (instance == null) {
            instance = new StatsTracker();
        }

        return instance;
    }

    public void newGame() {
        currentFoodCount = 0;
        platformHits = 0;
        currentClicks = 0;
        totalGames++;
        currentCollectedTogetherCount = 0;
        currentHitStreak = 0;
        canUpdateCollectedTogether = false;
        canUpdateHitStreak = false;
    }

    public int getCurrentFoodCount() {
        return currentFoodCount;
    }

    public void addCurrentFoodCount(int currentFoodCount) {
        this.currentFoodCount += currentFoodCount;
    }

    public void setCurrentFoodCount(int currentFoodCount) {
        this.currentFoodCount = currentFoodCount;
    }



    public int getCurrentCollectedTogetherCount() {
        return currentCollectedTogetherCount;
    }

    public void addCurrentCollectedTogetherCount(int addCount) {
        this.currentCollectedTogetherCount += addCount;
    }

    public void setCurrentCollectedTogetherCount(int count) {
        this.currentCollectedTogetherCount = count;
    }



    public int getCurrentHitStreak() {
        return currentHitStreak;
    }

    public void addCurrentHitStreak(int addCount) {
        this.currentHitStreak += addCount;
    }

    public void setCurrentHitStreak(int count) {
        this.currentHitStreak = count;
    }



    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }



    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getTotalGames() {
        return totalGames;
    }



    public int getCollectedTogether() {
        return collectedTogether;
    }

    public void setCollectedTogether(int collectedTogether) {
        this.collectedTogether = collectedTogether;
    }

    public void setCollectedTogether() {
        collectedTogether = currentCollectedTogetherCount;
        canUpdateCollectedTogether = true;
    }

    public boolean canUpdateCollectedTogether() {
        return canUpdateCollectedTogether;
    }



    public int getTotalFoodCount() {
        return totalFoodCount;
    }

    public void addTotalFoodCount(int foodToAdd) {
        this.totalFoodCount += foodToAdd;
    }

    public void setTotalFoodCount(int totalFoodCount) {
        this.totalFoodCount = totalFoodCount;
    }



    public int getHitStreak() {
        return hitStreak;
    }

    public void setHitStreak(int hitStreak) {
        this.hitStreak = hitStreak;
    }

    public void setHitStreak() {
        hitStreak = currentHitStreak;
        canUpdateHitStreak = true;
    }

    public boolean canUpdateHitStreak() {
        return canUpdateHitStreak;
    }



    public int getPlatformHits() {
        return platformHits;
    }

    public void addPlatformHits(int platformHits) {
        this.platformHits += platformHits;
    }

    public int getCurrentClicks() {
        return currentClicks;
    }

    public void addCurrentClicks(int currentClicks) {
        this.currentClicks += currentClicks;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void addTotalClicks(int totalClicks) {
        this.totalClicks += totalClicks;
    }

    public void addHighScore(int highScore) {
        this.highScore += highScore;
    }

    public void setPlatformHits(int platformHits) {
        this.platformHits = platformHits;
    }

    public void setCurrentClicks(int currentClicks) {
        this.currentClicks = currentClicks;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
    }
}
