package com.nickdo.ballbouncer.Achievements;

import android.content.Context;

import com.nickdo.ballbouncer.R;

import java.util.ArrayList;
import java.util.List;

public class AchievementRepository {

    private static AchievementRepository repository;

    private List<Achievement> achievements;

    public static AchievementRepository getInstance() {
        if (repository == null) {
            repository = new AchievementRepository();
        }
        return repository;
    }


    public AchievementRepository() {
    }

    public void init(Context context) {
        achievements = new ArrayList<>();
        achievements.add(new HighScoreAchievement(context.getString(R.string.achievements_highscore_1), 100, 4));
        achievements.add(new HighScoreAchievement(context.getString(R.string.achievements_highscore_2), 200, 5));
        achievements.add(new HighScoreAchievement(context.getString(R.string.achievements_highscore_3), 500, 6));
        achievements.add(new HighScoreAchievement(context.getString(R.string.achievements_highscore_4), 1000, 7));
        achievements.add(new TotalFoodAchievement(context.getString(R.string.achievements_food_1), 1000, 8));
        achievements.add(new TotalFoodAchievement(context.getString(R.string.achievements_food_2), 2000, 9));
        achievements.add(new TotalFoodAchievement(context.getString(R.string.achievements_food_3), 5000, 10));
        achievements.add(new TotalFoodAchievement(context.getString(R.string.achievements_food_4), 10000, 11));
        achievements.add(new TotalGamesAchievement(context.getString(R.string.achievements_games_1), 50, 12));
        achievements.add(new TotalGamesAchievement(context.getString(R.string.achievements_games_2), 100, 13));
        achievements.add(new TotalGamesAchievement(context.getString(R.string.achievements_games_3), 200, 14));
        achievements.add(new TotalGamesAchievement(context.getString(R.string.achievements_games_4), 400, 15));
        achievements.add(new HitStreakAchievement(context.getString(R.string.achievements_streak_1), 10, 16));
        achievements.add(new HitStreakAchievement(context.getString(R.string.achievements_streak_2), 20, 17));
        achievements.add(new HitStreakAchievement(context.getString(R.string.achievements_streak_3), 30, 18));
        achievements.add(new HitStreakAchievement(context.getString(R.string.achievements_streak_4), 40, 19));
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public List<Achievement> getUnlockedAchievements() {
        List<Achievement> unlockedAchievements = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.getIsUnlocked()) {
                unlockedAchievements.add(achievement);
            }
        }
        return unlockedAchievements;
    }
}
