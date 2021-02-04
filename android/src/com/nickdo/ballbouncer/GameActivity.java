package com.nickdo.ballbouncer;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import es.dmoral.toasty.Toasty;

public class GameActivity extends AndroidApplication implements DataService {

    private Toast highScoreToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBackground();

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "fonts/premier.ttf");

        Toasty.Config.getInstance()
                .tintIcon(false)
                .setToastTypeface(typeface)
                .apply();

        highScoreToast = Toasty.custom(GameActivity.this, "New Highscore!", getDrawable(R.drawable.trophy), Toast.LENGTH_SHORT, true);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Main(GameActivity.this), config);
    }

    private void setBackground() {
        int backgroundIndex = SettingsManager.getInstance().getColorMode();
        Drawable background = (backgroundIndex == SettingsManager.LIGHT_MODE)
                ? getDrawable(R.drawable.background_light)
                : getDrawable(R.drawable.background_dark);
        getWindow().getDecorView().setBackground(background);
    }

    @Override
    public void setHighScore(int i) {
        highScoreToast.show();

        SharedPrefUtil.setHighScore(getApplicationContext(), i);
    }

    @Override
    public void setTotalGames(int i) {
        SharedPrefUtil.setTotalGames(getApplicationContext(), i);
    }

    @Override
    public void setCollectedTogether(int i) {
        SharedPrefUtil.setCollectedTogether(getApplicationContext(), i);
    }

    @Override
    public void setTotalFood(int i) {
        SharedPrefUtil.setTotalFood(getApplicationContext(), i);
    }

    @Override
    public void setHitStreak(int i) {
        SharedPrefUtil.setHitStreak(getApplicationContext(), i);
    }
}
