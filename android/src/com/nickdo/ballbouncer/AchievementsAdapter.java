package com.nickdo.ballbouncer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nickdo.ballbouncer.Achievements.Achievement;

import java.io.IOException;
import java.util.List;

public class AchievementsAdapter extends ArrayAdapter<Achievement> {

    private Context context;
    private int resource;

    public AchievementsAdapter(@NonNull Context context, int resource, List<Achievement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String description = getItem(position).getDescription();
        int currentProgress = getItem(position).getProgress();
        int goal = getItem(position).getGoal();
        if (getItem(position).getIsUnlocked()) {
            currentProgress = goal;
        }
        String assetsName = getItem(position).getAssetsName();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName = convertView.findViewById(R.id.txtName);
        TextView tvDescription = convertView.findViewById(R.id.txtDescription);
        TextView tvProgress = convertView.findViewById(R.id.txtProgress);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);
        ImageView image = convertView.findViewById(R.id.image);

        int color;
        int colorMode = SettingsManager.getInstance().getColorMode();
        if (colorMode == SettingsManager.LIGHT_MODE) {
            color = Color.BLACK;
        } else {
            color = Color.WHITE;
        }
        tvName.setTextColor(color);
        tvDescription.setTextColor(color);
        tvProgress.setTextColor(color);
        progressBar.setProgressTintList(ColorStateList.valueOf(color));
        if (getItem(position).getIsUnlocked()) {
            try {
                Drawable drawable = Drawable.createFromStream(context.getAssets().open(assetsName), null);
                image.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            tvName.setAlpha(0.5f);
            tvDescription.setAlpha(0.5f);
            tvProgress.setAlpha(0.5f);
            progressBar.setAlpha(0.5f);
            image.setImageDrawable(context.getDrawable(R.drawable.locked));
            image.setColorFilter(color);
            image.setAlpha(0.5f);
            tvProgress.setText(context.getString(R.string.progress, currentProgress, goal));
        }

        tvName.setText(name);
        tvDescription.setText(description);
        progressBar.setMax(goal);
        progressBar.setProgress(currentProgress);
//        startAnimation(progressBar, currentProgress);

        return convertView;
    }

    private void startAnimation(ProgressBar progressBar, int currentProgress) {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, currentProgress);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
