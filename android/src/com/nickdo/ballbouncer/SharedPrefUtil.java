package com.nickdo.ballbouncer;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {

    public static int getHighScore(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_high_score_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_high_score_key), 0);
    }

    public static void setHighScore(Context context, int newHighScore) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_high_score_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_high_score_key), newHighScore);
        editor.apply();
    }

    public static int getTotalGames(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_total_games_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_total_games_key), 0);
    }

    public static void setTotalGames(Context context, int newTotalGames) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_total_games_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_total_games_key), newTotalGames);
        editor.apply();
    }

    public static int getTotalFood(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_total_food_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_total_food_key), 0);
    }

    public static void setTotalFood(Context context, int newTotalGames) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_total_food_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_total_food_key), newTotalGames);
        editor.apply();
    }

    public static int getHitStreak(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_hit_streak_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_hit_streak_key), 0);
    }

    public static void setHitStreak(Context context, int newHitStreak) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_hit_streak_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_hit_streak_key), newHitStreak);
        editor.apply();
    }

    public static int getCollectedTogether(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_collected_together_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_collected_together_key), 0);
    }

    public static void setCollectedTogether(Context context, int newCollectedTogether) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_collected_together_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_collected_together_key), newCollectedTogether);
        editor.apply();
    }

    public static int getColorMode(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_color_mode_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_color_mode_key), 0);
    }

    public static void setColorMode(Context context, int newColorMode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_color_mode_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_color_mode_key), newColorMode);
        editor.apply();
    }

    public static boolean getSound(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_sound_pref_id), Context.MODE_PRIVATE).getBoolean(context.getString(R.string.saved_sound_key), true);
    }

    public static void setSound(Context context, boolean sound) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_sound_pref_id), Context.MODE_PRIVATE).edit();
        editor.putBoolean(context.getString(R.string.saved_sound_key), sound);
        editor.apply();
    }

    public static int getPlayer(Context context) {
        return context.getSharedPreferences(context.getString(R.string.saved_player_color_pref_id), Context.MODE_PRIVATE).getInt(context.getString(R.string.saved_player_color_key), 0);
    }

    public static void setPlayer(Context context, int newColorMode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.saved_player_color_pref_id), Context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.saved_player_color_key), newColorMode);
        editor.apply();
    }
}
