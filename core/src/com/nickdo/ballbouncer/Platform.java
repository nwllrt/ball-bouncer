package com.nickdo.ballbouncer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.utils.Constants;

import static com.nickdo.ballbouncer.Game.GAME_WORLD_WIDTH;

public class Platform {

    private Body platform;
    private World world;
    private int direction = 1;
    private float platformHorizontalForce = 0.25f;
    private Vector2 origin;
    private Camera camera;
    private Texture texture;
    private SpriteBatch batch;

    private float width = 0.9f;
    private float height = 0.02f;
    private float platformWidth = width / 2f;
    private Sound sound;
    private boolean shouldShrink;

    public Platform(World world, Camera camera) {
        this.world = world;
        this.camera = camera;

        origin = new Vector2(GAME_WORLD_WIDTH / 2, 0.28f);
        platform = createPlatform();

        platform.setLinearVelocity(platformHorizontalForce, 0);

        String picUrl = "platform.png";
        texture = new Texture(picUrl);
        batch = new SpriteBatch();
        sound = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_PLATFORM_HIT));
        shouldShrink = false;
    }

    public void shrink() {
        shouldShrink = true;
    }

    public void update() {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(texture, platform.getPosition().x - platformWidth, platform.getPosition().y - height, platformWidth * 2, width / 12);
        batch.end();

        updatePlatform();
        updatePlatformDirection();
    }

    private void updatePlatform() {
        if (shouldShrink) {
            if (platformWidth > width / 4) {
                platformWidth -= Constants.PLATFORM_DECREASEMENT;
                platform.destroyFixture(platform.getFixtureList().get(0));
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(platformWidth, height / 2);
                platform.createFixture(shape, 1).setUserData(Constants.BIT_PLATFORM);
                shape.dispose();
            }
            shouldShrink = false;
        }
    }

    private void updatePlatformDirection() {
        if (platform.getPosition().x + GAME_WORLD_WIDTH / 4 > GAME_WORLD_WIDTH) {
            direction = -1;
            platform.setLinearVelocity(direction * platformHorizontalForce, 0);
        } else if (platform.getPosition().x - GAME_WORLD_WIDTH / 4 < 0) {
            direction = 1;
            platform.setLinearVelocity(direction * platformHorizontalForce, 0);
        }
    }

    private Body createPlatform() {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.KinematicBody;
        def.position.set(origin);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(platformWidth, height / 2);

        pBody.createFixture(shape, 1).setUserData(Constants.BIT_PLATFORM);
        shape.dispose();
        return pBody;
    }

    public void restart() {
        platform.setTransform(origin, 0);
        direction = 1;
        platform.setLinearVelocity(direction * platformHorizontalForce, 0);
        platformWidth = width / 2f + Constants.PLATFORM_DECREASEMENT;
        updatePlatform();
    }

    public void hit() {
        if(SettingsManager.getInstance().isSound())
            sound.play(0.1f);
    }

    public void dispose() {
        sound.dispose();
        texture.dispose();
        batch.dispose();
    }

    public Body getBody() {
        return platform;
    }
}
