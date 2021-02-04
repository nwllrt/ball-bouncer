package com.nickdo.ballbouncer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nickdo.ballbouncer.contactListeners.BallListener;
import com.nickdo.ballbouncer.itemspawn.foodspawner.FoodSpawner;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.PowerUpSpawner;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items.DoublePoints;
import com.nickdo.ballbouncer.utils.Constants;
import com.nickdo.ballbouncer.utils.GlobalData;
import com.nickdo.ballbouncer.utils.StatsTracker;

public class Game {

    public static final float GAME_WORLD_WIDTH = 1.8f;
    public static final float GAME_WORLD_HEIGHT = 3.2f;

    private OrthographicCamera camera;
    private Viewport viewport;
//    private Box2DDebugRenderer b2dr;
    private World world;
    private BallListener ballListener;

    private Platform platform;
    private FoodSpawner food;
    private Ball ball;
    private PowerUpSpawner powerUp;

    private boolean isInGame = true;

    public Game() {
        StatsTracker.getInstance().newGame();
    }

    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);

        world = new World(new Vector2(0, -9.8f), false);

//        b2dr = new Box2DDebugRenderer();

        platform = new Platform(world, camera);
        ball = new Ball(world, camera);
        food = new FoodSpawner(world, camera, ball, null);
        powerUp = new PowerUpSpawner(world, camera, ball, platform, food);
        food.setPowerUpSpawner(powerUp);
        ballListener = new BallListener(ball, food, powerUp, platform);
        world.setContactListener(ballListener);

        GlobalData.getInstance().setVerticalBallForce(Constants.VERTICAL_BALL_VELO_NORMAL);
        ball.getBody().setGravityScale(Constants.VERTICAL_BALL_GRAVITY_SCALE_NORMAL);
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
//        b2dr.render(world, camera.combined);
        camera.update();
        if (!ball.isOutOfBounds() && isInGame) {
            platform.update();
            food.update();
            powerUp.update();
            ball.update();
        } else {
            isInGame = false;
        }
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }

    public void restart() {
        platform.restart();
        food.updatePosition();
        ball.restart();
        powerUp.updateItem();
        powerUp.restart();
        ballListener.restart();
    }

    public void touched() {
        ball.changePlayerDirection();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
    }

    public void dispose() {
        world.dispose();
//        b2dr.dispose();
        platform.dispose();
        ball.dispose();
        powerUp.dispose();
    }

    public boolean isDoublePointsActive() {
        return ((powerUp.getItem() instanceof DoublePoints) && powerUp.getItem().isFeatureActive());
    }
}
