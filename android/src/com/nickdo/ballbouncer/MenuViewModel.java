package com.nickdo.ballbouncer;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nickdo.ballbouncer.utils.StatsTracker;

import java.io.IOException;

public class MenuViewModel extends AndroidViewModel {

    private int playerIndex;

    private int colorMode;

    private boolean sound;

    public MenuViewModel(@NonNull Application application) {
        super(application);

        initializeStats();

        playerIndex = SharedPrefUtil.getPlayer(application);
        SettingsManager.getInstance().setCurrentPlayerIndex(playerIndex);

        colorMode = SharedPrefUtil.getColorMode(application);
        SettingsManager.getInstance().setColorMode(colorMode);

        sound = SharedPrefUtil.getSound(application);
        SettingsManager.getInstance().setSound(sound);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public Drawable getPlayer() {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(getApplication().getAssets().open("players/player" + playerIndex + ".png"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    public void setPlayer(int playerIndex) {
        this.playerIndex = playerIndex;
        SharedPrefUtil.setPlayer(getApplication(), playerIndex);
        SettingsManager.getInstance().setCurrentPlayerIndex(playerIndex);
    }

    public Drawable getBackground() {
        return (colorMode == SettingsManager.LIGHT_MODE)
                ? getApplication().getDrawable(R.drawable.background_light)
                : getApplication().getDrawable(R.drawable.background_dark);
    }

    public Drawable getSoundImage() {
        return (sound)
                ? getApplication().getDrawable(R.drawable.sound_on_icon)
                : getApplication().getDrawable(R.drawable.sound_off_icon);
    }

    public Drawable changeBackground() {
        if (colorMode == 0) {
            colorMode++;
        } else {
            colorMode--;
        }
        SettingsManager.getInstance().setColorMode(colorMode);
        SharedPrefUtil.setColorMode(getApplication(), colorMode);
        return getBackground();
    }

    public Drawable toggleSound() {
        sound = !sound;
        SettingsManager.getInstance().setSound(sound);
        SharedPrefUtil.setSound(getApplication(), sound);
        return getSoundImage();
    }

    public int getColorMode() {
        return colorMode;
    }

    private void initializeStats() {
        StatsTracker.getInstance().setHighScore(SharedPrefUtil.getHighScore(getApplication()));
        StatsTracker.getInstance().setTotalGames(SharedPrefUtil.getTotalGames(getApplication()));
        StatsTracker.getInstance().setCollectedTogether(SharedPrefUtil.getCollectedTogether(getApplication()));
        StatsTracker.getInstance().setTotalFoodCount(SharedPrefUtil.getTotalFood(getApplication()));
        StatsTracker.getInstance().setHitStreak(SharedPrefUtil.getHitStreak(getApplication()));
    }
}