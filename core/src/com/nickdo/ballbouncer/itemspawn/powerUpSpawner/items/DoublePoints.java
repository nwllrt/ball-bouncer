package com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.Ball;
import com.nickdo.ballbouncer.Platform;
import com.nickdo.ballbouncer.utils.GlobalData;
import com.nickdo.ballbouncer.utils.StatsTracker;

public class DoublePoints extends Item {

    public DoublePoints(World world, Platform platform, Ball ball, StatsTracker statsTracker) {
        super(world, platform, ball, statsTracker);
        this.texture = new Texture("items/2x.png");
    }
    @Override
    public void activate() {
        GlobalData.getInstance().setDoublePointsPowerUpActive(true);
        featureActive = true;
    }

    @Override
    public void undo() {
        GlobalData.getInstance().setDoublePointsPowerUpActive(false);
        featureActive = false;
        isItemHit = false;
    }

    @Override
    public void pickedUp() {
        isItemHit = true;
    }
}
