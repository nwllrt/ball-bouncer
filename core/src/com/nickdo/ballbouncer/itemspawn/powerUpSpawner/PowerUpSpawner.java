package com.nickdo.ballbouncer.itemspawn.powerUpSpawner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.Ball;
import com.nickdo.ballbouncer.Platform;
import com.nickdo.ballbouncer.SettingsManager;
import com.nickdo.ballbouncer.itemspawn.ItemSpawner;
import com.nickdo.ballbouncer.itemspawn.foodspawner.FoodSpawner;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items.DoublePoints;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items.Item;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items.SlowDownBall;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.items.SpeedUpBall;
import com.nickdo.ballbouncer.utils.Constants;
import com.nickdo.ballbouncer.utils.StatsTracker;

import java.util.Random;

public class PowerUpSpawner extends ItemSpawner {


    private long itemSpawnedTimestamp;
    private long itemBonusEndTimestamp;

    private boolean isShown;
    private Item powerUp;
    private Ball ball;
    private Platform platform;
    private StatsTracker statsTracker;
    private Random random;
    private Vector2 position;
    private FoodSpawner foodSpawner;
    private Sound sound;
    private float textureAlpha = 1;
    private boolean increaseTextureAlpha;

    public PowerUpSpawner(World world, Camera camera, Ball ball, Platform platform, FoodSpawner foodSpawner) {
        this.world = world;
        this.camera = camera;
        this.random = new Random();
        this.ball = ball;
        this.platform = platform;
        this.itemSpawnedTimestamp = System.currentTimeMillis() + Constants.POWER_UP_COOLDOWN + random.nextInt(Constants.POWER_UP_MIN_SPAWN);
        this.itemBonusEndTimestamp = 0;
        this.foodSpawner = foodSpawner;
        this.isShown = false;
        this.increaseTextureAlpha = false;

        createItem();
        updateItem();

        this.batch = new SpriteBatch();
        this.statsTracker = StatsTracker.getInstance();

        sound = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_POWERUP_PICKUP));
    }

    public void update() {
        if (!powerUp.isItemHit() && System.currentTimeMillis() > itemSpawnedTimestamp && item.getPosition().x < 0) {
            item.setTransform(position, 0);
        }
        if (powerUp.isItemHit()) {
            item.setTransform(-5, -5, 0);
        }
        if (System.currentTimeMillis() > itemSpawnedTimestamp + Constants.POWER_UP_DESPAWN_DELAY && !powerUp.isFeatureActive()) {
            updateItem();
            textureAlpha = 1;
            increaseTextureAlpha = false;
        }

        if (System.currentTimeMillis() > itemSpawnedTimestamp + Constants.POWER_UP_FLASH_DELAY && !powerUp.isFeatureActive()) {
            if (increaseTextureAlpha) {
                textureAlpha = textureAlpha + 0.05f;
                if (textureAlpha >= 1) {
                    increaseTextureAlpha = false;
                }
            } else {
                textureAlpha = textureAlpha - 0.05f;
                if (textureAlpha <= 0) {
                    increaseTextureAlpha = true;
                }
            }
        }

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, textureAlpha);
        batch.draw(powerUp.getTexture(), item.getPosition().x - radius, item.getPosition().y - radius, radius * 2, radius * 2);
        batch.end();
    }

    public void updateItem() {
        if (powerUp != null)
            powerUp.undo();
        powerUp = getRandomItem();
        this.itemSpawnedTimestamp = System.currentTimeMillis() + Constants.POWER_UP_COOLDOWN + random.nextInt(Constants.POWER_UP_MIN_SPAWN);
        this.itemBonusEndTimestamp = System.currentTimeMillis() - 1;
        position = calculatePosition(foodSpawner.getPosition(), ball.getBody().getPosition(), ball.getRadius());
        item.setTransform(-5, -5, 0);
    }

    public boolean shouldDisable() {
        return System.currentTimeMillis() > itemBonusEndTimestamp;
    }

    public Item getItem() {
        return powerUp;
    }

    public void pickedUp() {
        if(SettingsManager.getInstance().isSound())
            sound.play(0.9f);
        
        this.itemBonusEndTimestamp = System.currentTimeMillis() + Constants.POWER_UP_DURATION;
        textureAlpha = 1;
        increaseTextureAlpha = false;
    }

    private void createItem() {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.KinematicBody;
        def.position.set(calculateXValue(), calculateYValue());
        def.fixedRotation = true;
        item = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(radius);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1;
        fd.isSensor = true;
        item.createFixture(fd).setUserData(Constants.BIT_ITEM);
        shape.dispose();
    }

    private Item getRandomItem() {
        Random random = new Random();
        int value = random.nextInt(1000);
        if (value < 333) {
            return new SlowDownBall(world, platform, ball, statsTracker);
        } else if (value < 666) {
            return new SpeedUpBall(world, platform, ball, statsTracker);
        } else {
            return new DoublePoints(world, platform, ball, statsTracker);
        }
    }

    public void dispose() {
        sound.dispose();
        batch.dispose();

    }

    public Vector2 getPosition() {
        return position;
    }

    public void restart() {
        textureAlpha = 1;
    }
}
