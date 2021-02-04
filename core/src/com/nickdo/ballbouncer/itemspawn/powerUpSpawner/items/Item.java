package com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.Ball;
import com.nickdo.ballbouncer.Platform;
import com.nickdo.ballbouncer.utils.StatsTracker;


public abstract class Item {

    protected Platform platform;
    protected Ball ball;
    protected StatsTracker statsTracker;
    protected boolean featureActive;
    protected boolean isItemHit;
    protected Texture texture;
    protected World world;

    public Item(World world, Platform platform, Ball ball, StatsTracker statsTracker) {
        this.platform = platform;
        this.ball = ball;
        this.world = world;
        this.statsTracker = statsTracker;
        this.featureActive = false;
        this.isItemHit = false;
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract void activate();

    public abstract void undo();

    public abstract void pickedUp();

    public boolean isFeatureActive() {
        return featureActive;
    }

    public boolean isItemHit() {
        return isItemHit;
    }

    public float calculateYOffset(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;

    }
}
