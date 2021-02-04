package com.nickdo.ballbouncer.itemspawn.foodspawner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.nickdo.ballbouncer.Ball;
import com.nickdo.ballbouncer.SettingsManager;
import com.nickdo.ballbouncer.itemspawn.ItemSpawner;
import com.nickdo.ballbouncer.itemspawn.powerUpSpawner.PowerUpSpawner;
import com.nickdo.ballbouncer.utils.Constants;
import com.nickdo.ballbouncer.utils.GlobalData;
import com.nickdo.ballbouncer.utils.StatsTracker;

import java.util.Random;

public class FoodSpawner extends ItemSpawner {

    private boolean updatePosition;
    private BitmapFont font;
    private PowerUpSpawner powerUpSpawner;
    private Sound sound;
    private Ball ball;

    public FoodSpawner(World world, Camera camera, Ball ball, PowerUpSpawner powerUpSpawner) {
        this.world = world;
        this.updatePosition = false;
        this.camera = camera;
        this.ball = ball;
        this.powerUpSpawner = powerUpSpawner;
        createFood();
        initFont();
        this.batch = new SpriteBatch();
        this.texture = new Texture(Constants.FOOD_1_NAME);
        this.statsTracker = StatsTracker.getInstance();
        sound = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_FOOD_PICKUP));
        setRandomFood();
    }

    private void initFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/premier.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 180;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 6;
        parameter.shadowOffsetY = 6;
        font = generator.generateFont(parameter);
    }

    public void update() {
        if (updatePosition) {
            updatePosition();
            updatePosition = false;
        }

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(texture, item.getPosition().x - radius, item.getPosition().y - radius, radius * 2, radius * 2);
        batch.end();
    }

    public void updatePosition() {
        item.setTransform(calculatePosition(powerUpSpawner.getPosition(), ball.getBody().getPosition(), ball.getRadius()), 0);
    }

    private void createFood() {
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
        item.createFixture(fd).setUserData(Constants.BIT_FOOD);
        shape.dispose();
    }

    public void setUpdatePosition(boolean updatePosition) {
        this.updatePosition = updatePosition;
    }

    private void setRandomFood() {
        int count = statsTracker.getCurrentFoodCount();
        String textureS = "";

        if (count <= Constants.FOOD_1_LIMIT) {
            textureS = getTexture(100, 0, 0, 0);
        } else if (count <= Constants.FOOD_2_LIMIT) {
            textureS = getTexture(85, 15, 0, 0);
        } else if (count <= Constants.FOOD_3_LIMIT) {
            textureS = getTexture(70, 20, 10, 0);
        } else if (count <= Constants.FOOD_4_LIMIT) {
            textureS = getTexture(50, 25, 20, 5);
        } else if (count <= Constants.FOOD_5_LIMIT) {
            textureS = getTexture(35, 30, 25, 10);
        } else if (count <= Constants.FOOD_6_LIMIT) {
            textureS = getTexture(25, 20, 30, 25);
        } else if (count <= Constants.FOOD_7_LIMIT) {
            textureS = getTexture(10, 20, 30, 40);
        } else {
            textureS = getTexture(5, 15, 30, 50);
        }

        texture = new Texture(textureS);

        if (textureS.equals(Constants.FOOD_1_NAME)) {
            GlobalData.getInstance().setFoodAddPoints(Constants.FOOD_1_POINTS);
        } else if (textureS.equals(Constants.FOOD_2_NAME)) {
            GlobalData.getInstance().setFoodAddPoints(Constants.FOOD_2_POINTS);
        } else if (textureS.equals(Constants.FOOD_3_NAME)) {
            GlobalData.getInstance().setFoodAddPoints(Constants.FOOD_3_POINTS);
        } else {
            GlobalData.getInstance().setFoodAddPoints(Constants.FOOD_4_POINTS);
        }
    }

    private String getTexture(int firstChance, int secondChance, int thirdChance, int fourthChance) {
        String textureS;
        Random random = new Random();
        int number = random.nextInt(100);
        if (number < firstChance) {
            textureS = Constants.FOOD_1_NAME;
        } else if (number < firstChance + secondChance) {
            textureS = Constants.FOOD_2_NAME;
        } else if (number < firstChance + secondChance + thirdChance) {
            textureS = Constants.FOOD_3_NAME;
        } else {
            textureS = Constants.FOOD_4_NAME;
        }
        return textureS;
    }

    public void pickedUp() {
        setRandomFood();
        if(SettingsManager.getInstance().isSound())
            sound.play(0.7f);
    }

    public Vector2 getPosition() {
        return item.getPosition();
    }

    public void setPowerUpSpawner(PowerUpSpawner powerUpSpawner) {
        this.powerUpSpawner = powerUpSpawner;
    }

}
