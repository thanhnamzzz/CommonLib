package io.virgo_common.common_libs.customView.shimmer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Shimmer
 * User: romainpiel
 * Date: 06/03/2014
 * Time: 15:42
 */
public class Shimmer {

    public static final int ANIMATION_DIRECTION_LTR = 0;
    public static final int ANIMATION_DIRECTION_RTL = 1;

    private static final int DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE;
    private static final long DEFAULT_DURATION = 1000;
    private static final long DEFAULT_START_DELAY = 0;
    private static final int DEFAULT_DIRECTION = ANIMATION_DIRECTION_LTR;

    private int repeatCount;
    private long duration;
    private long startDelay;
    private int direction;
    private Animator.AnimatorListener animatorListener;

    private ObjectAnimator animator;

    public Shimmer() {
        repeatCount = DEFAULT_REPEAT_COUNT;
        duration = DEFAULT_DURATION;
        startDelay = DEFAULT_START_DELAY;
        direction = DEFAULT_DIRECTION;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public Shimmer setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public Shimmer setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public Shimmer setStartDelay(long startDelay) {
        this.startDelay = startDelay;
        return this;
    }

    public int getDirection() {
        return direction;
    }

    public Shimmer setDirection(int direction) {

        if (direction != ANIMATION_DIRECTION_LTR && direction != ANIMATION_DIRECTION_RTL) {
            throw new IllegalArgumentException("The animation direction must be either ANIMATION_DIRECTION_LTR or ANIMATION_DIRECTION_RTL");
        }

        this.direction = direction;
        return this;
    }

    public Animator.AnimatorListener getAnimatorListener() {
        return animatorListener;
    }

    public Shimmer setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
        return this;
    }

    public <V extends View & ShimmerViewBase> void start(final V shimmerView) {

        if (isAnimating()) {
            return;
        }

        final Runnable animate = () -> {

            shimmerView.setShimmering(true);

            float fromX = 0;
            float toX = shimmerView.getWidth();
            if (direction == ANIMATION_DIRECTION_RTL) {
                fromX = shimmerView.getWidth();
                toX = 0;
            }

            animator = ObjectAnimator.ofFloat(shimmerView, "gradientX", fromX, toX);
            animator.setRepeatCount(repeatCount);
            animator.setDuration(duration);
            animator.setStartDelay(startDelay);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    shimmerView.setShimmering(false);

                    shimmerView.postInvalidateOnAnimation();

                    animator = null;
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {

                }
            });

            if (animatorListener != null) {
                animator.addListener(animatorListener);
            }

            animator.start();
        };

        if (!shimmerView.isSetUp()) {
            shimmerView.setAnimationSetupCallback(target -> animate.run());
        } else {
            animate.run();
        }
    }

    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public boolean isAnimating() {
        return animator != null && animator.isRunning();
    }
}
