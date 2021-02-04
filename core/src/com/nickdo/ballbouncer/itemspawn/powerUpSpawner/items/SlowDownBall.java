package com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.Ball;
import com.nickdo.ballbouncer.Platform;
import com.nickdo.ballbouncer.utils.Constants;
import com.nickdo.ballbouncer.utils.GlobalData;
import com.nickdo.ballbouncer.utils.StatsTracker;

import static com.nickdo.ballbouncer.Game.GAME_WORLD_HEIGHT;

public class SlowDownBall extends Item {

    public SlowDownBall(World world, Platform platform, Ball ball, StatsTracker statsTracker) {
        super(world, platform, ball, statsTracker);
        this.texture = new Texture("items/slow.png");
    }

    @Override
    public void activate() {
        GlobalData.getInstance().setVerticalBallForce(Constants.VERTICAL_BALL_VELO_SLOW);
        featureActive = true;
    }

    @Override
    public void undo() {
        ball.getBody().setGravityScale(Constants.VERTICAL_BALL_GRAVITY_SCALE_NORMAL);
        GlobalData.getInstance().setVerticalBallForce(Constants.VERTICAL_BALL_VELO_NORMAL);
        featureActive = false;
        isItemHit = false;
    }

    @Override
    public void pickedUp() {
        isItemHit = true;
        float yOffset = calculateYOffset(ball.getBody().getPosition().y, 0, GAME_WORLD_HEIGHT, 1.8f, 0.4f);
        if (ball.getBody().getLinearVelocity().y > 0) {
            ball.getBody().setLinearVelocity(ball.getBody().getLinearVelocity().x, ball.getBody().getLinearVelocity().y - yOffset);
        }
        ball.getBody().setGravityScale(Constants.VERTICAL_BALL_GRAVITY_SCALE_SLOW);
    }
}
