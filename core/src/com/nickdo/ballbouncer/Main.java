package com.nickdo.ballbouncer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nickdo.ballbouncer.utils.GlobalData;
import com.nickdo.ballbouncer.utils.StatsTracker;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private BitmapFont font, highScoreFont;
    private FreeTypeFontGenerator generator;
    private Stage stage;
    private InputAdapter inputAdapter;

    private DataService service;
    private StatsTracker statsTracker;

    private float screenWidth;
    private float screenHeight;

    private Texture background;

    private Game game;

    private ParticleEffect confetti;

    private boolean runOnlyOnce;

    private float responsiveMultiplicator;

    public Main(DataService service) {
        this.service = service;
    }

    @Override
    public void create() {

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        responsiveMultiplicator = screenWidth / 1080;

        batch = new SpriteBatch();

        if (SettingsManager.getInstance().getColorMode() == SettingsManager.LIGHT_MODE) {
            background = new Texture("background_light.png");
        } else {
            background = new Texture("background_dark.png");
        }

        game = new Game();
        game.create();

        confetti = new ParticleEffect();
        confetti.load(Gdx.files.internal("particles/confetti.part"), Gdx.files.internal("particles/"));
        confetti.setPosition(screenWidth / 2, screenHeight);
        confetti.start();

        initializeFont();

        initializeButton();

        GlobalData.getInstance().setDoublePointsPowerUpActive(false);
        
        inputAdapter = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                super.touchDown(screenX, screenY, pointer, button);
                game.touched();
                statsTracker.addCurrentClicks(1);
                return true;
            }
        };

        statsTracker = StatsTracker.getInstance();

        service.setTotalGames(statsTracker.getTotalGames());

        Gdx.input.setInputProcessor(inputAdapter);

        runOnlyOnce = true;
    }

    private void initializeFont() {
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/premier.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (200 * responsiveMultiplicator);
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 4;
        parameter.shadowOffsetY = 4;
        font = generator.generateFont(parameter);
        parameter.size = (int) (80 * responsiveMultiplicator);
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        highScoreFont = generator.generateFont(parameter);
    }

    private void initializeButton() {
        stage = new Stage();
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;
        TextButton btnRestart = new TextButton("Restart", btnStyle);
        btnRestart.setPosition(screenWidth / 2f, screenHeight * 0.5f, 0);
        btnRestart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runOnlyOnce = true;
                game.setIsInGame(true);
                game.restart();
                Gdx.input.setInputProcessor(inputAdapter);
                statsTracker.newGame();
                service.setTotalGames(statsTracker.getTotalGames());
            }
        });

        TextButton btnLeave = new TextButton("Leave", btnStyle);
        btnLeave.setPosition(screenWidth / 2f, screenHeight * 0.3f, 0);
        btnLeave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(btnRestart);
        stage.addActor(btnLeave);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.end();

        if (game.isDoublePointsActive() && game.isInGame()) {
            confetti.update(Gdx.graphics.getDeltaTime());
            batch.begin();
            confetti.draw(batch);
            batch.end();
        } else {
            confetti.reset();
        }

        batch.begin();
        font.draw(batch, String.valueOf(statsTracker.getCurrentFoodCount()), screenWidth / 2f, screenHeight * 0.95f, 0, 1, true);
        batch.end();

        if (game.isInGame()) {
            batch.begin();
            highScoreFont.draw(batch, "Streak: " + statsTracker.getCurrentHitStreak(), screenWidth / 2f, screenHeight * 0.82f, 0, 1, true);
            batch.end();
            game.update();
        } else {
            batch.begin();
            highScoreFont.draw(batch, "Highscore:  " + statsTracker.getHighScore(), screenWidth / 2f, screenHeight * 0.82f, 0, 1, true);
            batch.end();
            if (runOnlyOnce) {
                int foodCollectedThisRound = statsTracker.getCurrentFoodCount();
                if (foodCollectedThisRound > statsTracker.getHighScore()) {
                    service.setHighScore(foodCollectedThisRound);
                    statsTracker.setHighScore(foodCollectedThisRound);
                }
                if (statsTracker.canUpdateCollectedTogether()) {
                    service.setCollectedTogether(statsTracker.getCollectedTogether());
                }
                if (foodCollectedThisRound > 0) {
                    statsTracker.addTotalFoodCount(foodCollectedThisRound);
                    service.setTotalFood(statsTracker.getTotalFoodCount());
                }
                if (statsTracker.canUpdateHitStreak()) {
                    service.setHitStreak(statsTracker.getHitStreak());
                }
                confetti.reset();
                Gdx.input.setInputProcessor(stage);
                runOnlyOnce = false;
            }
            stage.act();
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        generator.dispose();
        stage.dispose();
        game.dispose();
        font.dispose();
        highScoreFont.dispose();
        confetti.dispose();
    }
}