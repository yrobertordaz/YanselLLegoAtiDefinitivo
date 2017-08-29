package com.llegoati.llegoati.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.llegoati.llegoati.R;


/**
 * Created by Yansel on 3/13/2017.
 */

public class ProgressBarIndeterminate extends android.support.v7.widget.AppCompatImageView {

    private AnimationSet animation_set;

    public ProgressBarIndeterminate(Context context) {
        super(context);
        init();
    }

    public ProgressBarIndeterminate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBarIndeterminate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        animation_set = new AnimationSet(getContext(), null);
        animation_set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        animation_set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_indeterminate));
        setBackgroundColor(Color.BLACK);

        setImageResource(R.mipmap.ic_loading);
        setAdjustViewBounds(true);
        startProgressBarIndeterminate();
    }

    private void startProgressBarIndeterminate() {
        startAnimation(animation_set);
    }

    private void stopProgressBarIndeterminate() {
        clearAnimation();
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE)
            startProgressBarIndeterminate();
        else
            stopProgressBarIndeterminate();

        super.setVisibility(visibility);
    }
}
