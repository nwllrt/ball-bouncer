package com.nickdo.ballbouncer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.utils.Constants;

import static com.nickdo.ballbouncer.Game.GAME_WORLD_HEIGHT;
import static com.nickdo.ballbouncer.Game.GAME_WORLD_WIDTH;

public class Ball {

    private static final int OUT_OF_BOUNDS_BUFFER = 1;

    private Body player;
    private World world;
    private Camera camera;
    private int direction = 1;
    private float horizontalVelocity = 1.1f;
    private float radius = 0.12f;
    private Vector2 origin;

    private Texture texture;
    private SpriteBatch batch;

    public Ball(World world, Camera camera) {
        this.world = world;
        this.camera = camera;

        origin = new Vector2(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT * 0.7f);
        player = createPlayer();
        player.setLinearVelocity(horizontalVelocity, 0);

        String picUrl = "players/player" + SettingsManager.getInstance().getCurrentPlayerIndex() + ".png";
        texture = new Texture(picUrl);
        batch = new SpriteBatch();
    }

    private Body createPlayer() {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(origin);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(radius);

        pBody.createFixture(shape, 1).setUserData(Constants.BIT_BALL);
        shape.dispose();
        return pBody;
    }

    public void update() {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(texture, player.getPosition().x - radius * -direction, player.getPosition().y - radius, -direction * radius * 2, radius * 2);
        batch.end();
    }

    public void changePlayerDirection() {
        if (direction == 1) {
            direction = -1;
        } else {
            direction = 1;
        }
        float velocity = direction * horizontalVelocity;
        player.setLinearVelocity(velocity, player.getLinearVelocity().y);
    }

    public boolean isOutOfBounds() {
        return (player.getPosition().y + OUT_OF_BOUNDS_BUFFER < 0);
    }

    public void restart() {
        player.setTransform(origin, 0);
        player.setLinearVelocity(0, 0);
        player.setAngularVelocity(0);
        direction = 1;
        player.setLinearVelocity(horizontalVelocity, 0);
    }

    public float getRadius() {
        return radius;
    }

    public void dispose() {
        texture.dispose();
        batch.dispose();
    }

    public Body getBody() {
        return player;
    }
}
