package com.nickdo.ballbouncer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import com.nickdo.ballbouncer.Achievements.AchievementRepository;

public class MenuActivity extends AppCompatActivity {

    private TextView txtStart;

    private TextView txtAchievements;

    private ImageView imgColorMode;

    private ImageView imgPlayer;

    private ImageView imgSound;

    private ConstraintLayout clMenu;

    private MenuViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initializeViews();

        AchievementRepository.getInstance().init(getApplicationContext());

        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        clMenu.setBackground(viewModel.getBackground());

        setColorMode();

        imgPlayer.setImageDrawable(viewModel.getPlayer());

        imgSound.setImageDrawable(viewModel.getSoundImage());

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        txtAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AchievementsActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MenuActivity.this,
                        txtAchievements,
                        ViewCompat.getTransitionName(txtAchievements));
                startActivity(intent, options.toBundle());
            }
        });

        imgColorMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColorMode();
                setColorMode();
            }
        });

        imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable soundImage = viewModel.toggleSound();
                imgSound.setImageDrawable(soundImage);
            }
        });

        imgPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlayer();
            }
        });
    }

    private void initializeViews() {
        txtStart = findViewById(R.id.txtStart);
        txtAchievements = findViewById(R.id.txtAchievements);
        imgColorMode = findViewById(R.id.imgColorMode);
        imgPlayer = findViewById(R.id.imgPlayerColor);
        imgSound = findViewById(R.id.imgSound);
        clMenu = findViewById(R.id.clMenu);
    }

    private void setColorMode() {
        if (viewModel.getColorMode() == SettingsManager.LIGHT_MODE) {
            imgColorMode.setImageDrawable(getDrawable(R.drawable.sun));
        } else {
            imgColorMode.setImageDrawable(getDrawable(R.drawable.moon));
        }
    }

    private void changeColorMode() {
        Drawable background = viewModel.changeBackground();
        clMenu.setBackground(background);
    }

    private void setPlayer() {
        PlayerPicker playerPicker = new PlayerPicker(this, viewModel.getPlayerIndex(), new ColorChangedListener() {
            @Override
            public void onColorChanged(int newPlayerIndex) {
                viewModel.setPlayer(newPlayerIndex);
                imgPlayer.setImageDrawable(viewModel.getPlayer());
            }
        });
        playerPicker.build();
    }

    public interface ColorChangedListener {
        void onColorChanged(int index);
    }
}
