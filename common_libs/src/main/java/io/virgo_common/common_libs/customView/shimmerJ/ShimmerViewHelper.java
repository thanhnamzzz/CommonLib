package io.virgo_common.common_libs.customView.shimmerJ;

import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import io.virgo_common.common_libs.R;

/**
 * Shimmer
 * User: romainpiel
 * Date: 10/03/2014
 * Time: 17:06
 */
public class ShimmerViewHelper {

    public interface AnimationSetupCallback {
        void onSetupAnimation(View target);
    }

    private static final int DEFAULT_REFLECTION_COLOR = 0xFFFFFFFF;

    private final View view;
    private final Paint paint;

    // center position of the gradient
    private float gradientX;

    // shader applied on the text view
    // only null until the first global layout
    private LinearGradient linearGradient;

    // shader's local matrix
    // never null
    private Matrix linearGradientMatrix;

    private int primaryColor;

    // shimmer reflection color
    private int reflectionColor;

    // true when animating
    private boolean isShimmering;

    // true after first global layout
    private boolean isSetUp;

    // callback called after first global layout
    private AnimationSetupCallback callback;

    public ShimmerViewHelper(View view, Paint paint, AttributeSet attributeSet) {
        this.view = view;
        this.paint = paint;
        init(attributeSet);
    }

    public float getGradientX() {
        return gradientX;
    }

    public void setGradientX(float gradientX) {
        this.gradientX = gradientX;
        view.invalidate();
    }

    public boolean isShimmering() {
        return isShimmering;
    }

    public void setShimmering(boolean isShimmering) {
        this.isShimmering = isShimmering;
    }

    public boolean isSetUp() {
        return isSetUp;
    }

    public void setAnimationSetupCallback(AnimationSetupCallback callback) {
        this.callback = callback;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        if (isSetUp) {
            resetLinearGradient();
        }
    }

    public int getReflectionColor() {
        return reflectionColor;
    }

    public void setReflectionColor(int reflectionColor) {
        this.reflectionColor = reflectionColor;
        if (isSetUp) {
            resetLinearGradient();
        }
    }

    private void init(AttributeSet attributeSet) {

        reflectionColor = DEFAULT_REFLECTION_COLOR;
        if (attributeSet != null) {
            try (TypedArray a = view.getContext().obtainStyledAttributes(
                    attributeSet, R.styleable.ShimmerView, 0, 0)) {
                reflectionColor = a.getColor(
                        R.styleable.ShimmerView_reflectionColor,
                        DEFAULT_REFLECTION_COLOR
                );
            } catch (Exception e) {
                Log.e("ShimmerTextView", "Error while reading reflectionColor attribute", e);
            }
        }

        linearGradientMatrix = new Matrix();
    }

    private void resetLinearGradient() {

        // our gradient is a simple linear gradient from textColor to reflectionColor. its axis is at the center
        // when it's outside of the view, the outer color (textColor) will be repeated (Shader.TileMode.CLAMP)
        // initially, the linear gradient is positioned on the left side of the view
        linearGradient = new LinearGradient(-view.getWidth(), 0, 0, 0,
                new int[]{
                        primaryColor,
                        reflectionColor,
                        primaryColor,
                },
                new float[]{
                        0,
                        0.5f,
                        1
                },
                Shader.TileMode.CLAMP
        );

        paint.setShader(linearGradient);
    }

    protected void onSizeChanged() {

        resetLinearGradient();

        if (!isSetUp) {
            isSetUp = true;

            if (callback != null) {
                callback.onSetupAnimation(view);
            }
        }
    }

    /**
     * content of the wrapping view's onDraw(Canvas)
     * MUST BE CALLED BEFORE SUPER STATEMENT
     */
    public void onDraw() {

        // only draw the shader gradient over the text while animating
        if (isShimmering) {

            // first onDraw() when shimmering
            if (paint.getShader() == null) {
                paint.setShader(linearGradient);
            }

            // translate the shader local matrix
            linearGradientMatrix.setTranslate(2 * gradientX, 0);

            // this is required in order to invalidate the shader's position
            linearGradient.setLocalMatrix(linearGradientMatrix);

        } else {
            // we're not animating, remove the shader from the paint
            paint.setShader(null);
        }

    }
}
