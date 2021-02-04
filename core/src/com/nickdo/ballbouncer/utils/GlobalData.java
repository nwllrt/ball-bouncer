package com.nickdo.ballbouncer.utils;

public class GlobalData {

    private static GlobalData instance;
    private float verticalBallForce;
    private StatsTracker statsTracker;
    private boolean isDoublePointsPowerUpActive;
    private int foodAddPoints;

    public GlobalData(){
        this.verticalBallForce = 6.4f;
        this.statsTracker = StatsTracker.getInstance();
        this.isDoublePointsPowerUpActive = false;
        this.foodAddPoints = 0;
    }
    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public int getFoodAddPoints(){
        return isDoublePointsPowerUpActive == true ? foodAddPoints * 2 : foodAddPoints;
    }

    public void setFoodAddPoints(int points){
        this.foodAddPoints = points;
    }
    public float getBallVerticalForce() {
        return verticalBallForce;
    }

    public void setVerticalBallForce(float verticalBallForce) {
        this.verticalBallForce = verticalBallForce;
    }

    public void setDoublePointsPowerUpActive(boolean val){
        this.isDoublePointsPowerUpActive = val;
    }
}
