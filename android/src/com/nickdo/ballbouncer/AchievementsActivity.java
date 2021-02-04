package com.nickdo.ballbouncer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nickdo.ballbouncer.Achievements.Achievement;

import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    private ListView lvAchievements;

    private ConstraintLayout constraintLayout;

    private Drawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        setBackground();

        initializeViews();

        constraintLayout.setBackground(background);

        AchievementsViewModel viewModel = new ViewModelProvider(this).get(AchievementsViewModel.class);
        viewModel.getAchievementsData().observe(this, new Observer<List<Achievement>>() {
            @Override
            public void onChanged(List<Achievement> achievements) {
                AchievementsAdapter adapter = new AchievementsAdapter(getApplicationContext(), R.layout.achievement_item, achievements);
                lvAchievements.setAdapter(adapter);
            }
        });
    }

    private void setBackground() {
        int backgroundIndex = SettingsManager.getInstance().getColorMode();
        background = (backgroundIndex == SettingsManager.LIGHT_MODE)
                ? getDrawable(R.drawable.background_light)
                : getDrawable(R.drawable.background_dark);
        getWindow().getDecorView().setBackground(background);
    }

    private void initializeViews() {
        lvAchievements = findViewById(R.id.lvAchievements);
        constraintLayout = findViewById(R.id.clAchievements);
    }
}