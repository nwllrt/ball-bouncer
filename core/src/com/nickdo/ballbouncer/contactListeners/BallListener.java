package com.nickdo.ballbouncer.contactListeners;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nickdo.ballbouncer.Ball;
import com.nickdo.ballbouncer.Platform;
import com.nickdo.ballbouncer.itemspawn.foodspawner.FoodSpawner;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.PowerUpSpawner;
import com.nickdo.ballbouncer.utils.Constants;
import com.nickdo.ballbouncer.utils.GlobalData;
import com.nickdo.ballbouncer.utils.StatsTracker;

public class BallListener implements ContactListener {

    private Ball ball;
    private FoodSpawner foodSpawner;
    private PowerUpSpawner powerUpSpawner;
    private Platform platform;
    private boolean pickedUp;
    private GlobalData data;

    public BallListener(Ball ball, FoodSpawner foodSpawner, PowerUpSpawner powerUpSpawner, Platform platform) {
        this.ball = ball;
        this.foodSpawner = foodSpawner;
        this.powerUpSpawner = powerUpSpawner;
        this.platform = platform;
        this.pickedUp = false;
        this.data = GlobalData.getInstance();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) return;

        short itemA = (short) a.getUserData();
        short itemB = (short) b.getUserData();

        checkForPlatform(itemA, itemB);
        checkForItem(itemA, itemB);
        checkForFood(itemA, itemB);
    }

    public void checkForPlatform(short a, short b) {
        if ((a == Constants.BIT_BALL && b == Constants.BIT_PLATFORM) ||
                (a == Constants.BIT_PLATFORM && b == Constants.BIT_BALL)
        ) {
            StatsTracker.getInstance().addPlatformHits(1);
            if (powerUpSpawner.shouldDisable() && powerUpSpawner.getItem().isFeatureActive()) {
                powerUpSpawner.getItem().undo();
                powerUpSpawner.updateItem();
            }
            platform.hit();
            ball.getBody().setLinearVelocity(new Vector2(ball.getBody().getLinearVelocity().x, data.getBallVerticalForce()));
            if (pickedUp) {
                StatsTracker.getInstance().addCurrentHitStreak(1);
                if (StatsTracker.getInstance().getCurrentHitStreak() > StatsTracker.getInstance().getHitStreak()) {
                    StatsTracker.getInstance().setHitStreak();
                }
            } else {
                StatsTracker.getInstance().setCurrentHitStreak(0);
            }
            pickedUp = false;
            StatsTracker.getInstance().setCurrentCollectedTogetherCount(0);
        }
    }

    public void checkForItem(short a, short b) {
        if ((a == Constants.BIT_BALL && b == Constants.BIT_ITEM) ||
                (a == Constants.BIT_ITEM && b == Constants.BIT_BALL)
        ) {
            powerUpSpawner.getItem().activate();
            powerUpSpawner.pickedUp();
            powerUpSpawner.getItem().pickedUp();
        }
    }

    public void checkForFood(short a, short b) {
        if ((a == Constants.BIT_BALL && b == Constants.BIT_FOOD) ||
                (a == Constants.BIT_FOOD && b == Constants.BIT_BALL)
        ) {
            StatsTracker.getInstance().addCurrentFoodCount(GlobalData.getInstance().getFoodAddPoints());
            StatsTracker.getInstance().addCurrentCollectedTogetherCount(1);
            if (StatsTracker.getInstance().getCurrentCollectedTogetherCount() > StatsTracker.getInstance().getCollectedTogether()) {
                StatsTracker.getInstance().setCollectedTogether();
            }

            foodSpawner.pickedUp();
            foodSpawner.setUpdatePosition(true);
            pickedUp = true;
            platform.shrink();
        }
    }

    public void restart() {
        pickedUp = false;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
