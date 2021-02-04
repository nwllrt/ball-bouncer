package com.nickdo.ballbouncer.itemspawn;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.utils.StatsTracker;

import java.util.Random;

import static com.nickdo.ballbouncer.Game.GAME_WORLD_HEIGHT;
import static com.nickdo.ballbouncer.Game.GAME_WORLD_WIDTH;

public abstract class ItemSpawner {

    protected Body item;
    protected World world;
    protected Texture texture;
    protected SpriteBatch batch;
    protected float radius = 0.08f;
    protected StatsTracker statsTracker;
    protected Camera camera;

    protected Vector2 calculatePosition(Vector2 obstacle, Vector2 ballPos, float ballRadius) {
        Vector2 pos = new Vector2(calculateXValue(), calculateYValue());
        while (pos.dst(obstacle) < radius * 8 || pos.dst(ballPos) <= 2 * ballRadius) {
            pos = new Vector2(calculateXValue(), calculateYValue());
        }
        return pos;
    }

    protected float calculateXValue() {
        Random random = new Random();
        return (GAME_WORLD_WIDTH * 0.1f) + (random.nextInt((int) ((GAME_WORLD_WIDTH * 0.8f) * 100)) / 100f);
    }

    protected float calculateYValue() {
        Random random = new Random();
        return (GAME_WORLD_HEIGHT * 0.2f) + (random.nextInt((int) (GAME_WORLD_HEIGHT * 0.7f) * 100) / 100f);
    }
}
