package com.nickdo.ballbouncer;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nickdo.ballbouncer.Achievements.Achievement;
import com.nickdo.ballbouncer.Achievements.AchievementRepository;

import java.io.IOException;
import java.util.List;

public class PlayerPicker {

    private static final int COLS = 4;
    private static final int DEFAULT_PLAYERS = 4;

    private Activity context;

    private RadioGroup radioGroup0;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private boolean isChecking = true;
    private int oldPlayerIndex;
    private int checkedId;

    private MenuActivity.ColorChangedListener listener;

    public PlayerPicker(Activity context, int oldPlayerIndex, MenuActivity.ColorChangedListener listener) {
        this.context = context;
        this.listener = listener;
        this.oldPlayerIndex = oldPlayerIndex;
    }

    public void build() {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.player_picker, null);
        radioGroup0 = view.findViewById(R.id.radioGroup0);
        radioGroup1 = view.findViewById(R.id.radioGroup1);
        radioGroup2 = view.findViewById(R.id.radioGroup2);
        radioGroup3 = view.findViewById(R.id.radioGroup3);
        radioGroup4 = view.findViewById(R.id.radioGroup4);

        final List<Achievement> achievements = AchievementRepository.getInstance().getUnlockedAchievements();
        int totalPlayers = DEFAULT_PLAYERS + achievements.size();
        if (oldPlayerIndex < DEFAULT_PLAYERS) {
            checkedId = oldPlayerIndex;
        } else {
            boolean checkedIdSet = false;
            for (int i = 0; i < achievements.size() && !checkedIdSet; i++) {
                if (oldPlayerIndex == achievements.get(i).getAssetIndex()) {
                    checkedId = i + DEFAULT_PLAYERS;
                    checkedIdSet = true;
                }
            }
        }

        radioGroup0.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int newCheckedId) {
                if (newCheckedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup1.clearCheck();
                    radioGroup2.clearCheck();
                    radioGroup3.clearCheck();
                    radioGroup4.clearCheck();
                    view.findViewById(checkedId).setAlpha(0.1f);
                    checkedId = newCheckedId;
                    view.findViewById(checkedId).setAlpha(1);
                }
                isChecking = true;
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int newCheckedId) {
                if (newCheckedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup0.clearCheck();
                    radioGroup2.clearCheck();
                    radioGroup3.clearCheck();
                    radioGroup4.clearCheck();
                    view.findViewById(checkedId).setAlpha(0.1f);
                    checkedId = newCheckedId;
                    view.findViewById(checkedId).setAlpha(1);
                }
                isChecking = true;
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int newCheckedId) {
                if (newCheckedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup0.clearCheck();
                    radioGroup1.clearCheck();
                    radioGroup3.clearCheck();
                    radioGroup4.clearCheck();
                    view.findViewById(checkedId).setAlpha(0.1f);
                    checkedId = newCheckedId;
                    view.findViewById(checkedId).setAlpha(1);
                }
                isChecking = true;
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int newCheckedId) {
                if (newCheckedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup0.clearCheck();
                    radioGroup1.clearCheck();
                    radioGroup2.clearCheck();
                    radioGroup4.clearCheck();
                    view.findViewById(checkedId).setAlpha(0.1f);
                    checkedId = newCheckedId;
                    view.findViewById(checkedId).setAlpha(1);
                }
                isChecking = true;
            }
        });

        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int newCheckedId) {
                if (newCheckedId != -1 && isChecking) {
                    isChecking = false;
                    radioGroup0.clearCheck();
                    radioGroup1.clearCheck();
                    radioGroup2.clearCheck();
                    radioGroup3.clearCheck();
                    view.findViewById(checkedId).setAlpha(0.1f);
                    checkedId = newCheckedId;
                    view.findViewById(checkedId).setAlpha(1);
                }
                isChecking = true;
            }
        });

        for (int i = 0; i < totalPlayers; i++) {
            RadioGroup groupToAdd;
            if (i / COLS == 0) {
                groupToAdd = radioGroup0;
            } else if (i / COLS == 1) {
                groupToAdd = radioGroup1;
            } else if (i / COLS == 2) {
                groupToAdd = radioGroup2;
            } else if (i / COLS == 3) {
                groupToAdd = radioGroup3;
            } else {
                groupToAdd = radioGroup4;
            }

            final RadioButton radioButton = (RadioButton) context.getLayoutInflater().inflate(R.layout.radio_button, groupToAdd, false);
            radioButton.setId(i);
            radioButton.setAlpha(0.1f);

            try {
                Drawable drawable;
                if (i < DEFAULT_PLAYERS) {
                    drawable = Drawable.createFromStream(context.getAssets().open("players/player" + i + ".png"), null);
                } else {
                    drawable = Drawable.createFromStream(context.getAssets().open(achievements.get(i - DEFAULT_PLAYERS).getAssetsName()), null);
                }
                radioButton.setBackground(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

            groupToAdd.addView(radioButton);
        }

        ((RadioButton) view.findViewById(checkedId)).setChecked(true);

        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (checkedId < DEFAULT_PLAYERS) {
                    listener.onColorChanged(checkedId);
                } else {
                    listener.onColorChanged(achievements.get(checkedId - DEFAULT_PLAYERS).getAssetIndex());
                }
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.dark_color));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.dark_color));
    }
}
