package com.nickdo.ballbouncer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nickdo.ballbouncer.Achievements.Achievement;
import com.nickdo.ballbouncer.Achievements.AchievementRepository;

import java.util.List;

public class AchievementsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Achievement>> achievementsData = new MutableLiveData<>();

    public AchievementsViewModel(@NonNull Application application) {
        super(application);

        List<Achievement> achievements = AchievementRepository.getInstance().getAchievements();
        achievementsData.setValue(achievements);
    }

    public LiveData<List<Achievement>> getAchievementsData() {
        return achievementsData;
    }
}
