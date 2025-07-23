package io.virgo_common.common_libs.blurView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import io.virgo_common.common_libs.R
import androidx.core.content.withStyledAttributes
import io.virgo_common.common_libs.extensions.isS31Plus

/**
 * FrameLayout that blurs its underlying content.
 * Can have children and draw them over blurred background.
 */
class BlurView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
    private var blurController: BlurController = NoOpController()

    @ColorInt
    private var overlayColor = 0
    private var blurAutoUpdate = true

    init {
        context.withStyledAttributes(attrs, R.styleable.BlurView, defStyleAttr, 0) {
            overlayColor =
                getColor(R.styleable.BlurView_blurOverlayColor, PreDrawBlurController.TRANSPARENT)
        }
    }

    override fun draw(canvas: Canvas) {
        val shouldDraw = blurController.draw(canvas)
        if (shouldDraw) {
            super.draw(canvas)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        blurController.updateBlurViewSize()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        blurController.setBlurAutoUpdate(false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isHardwareAccelerated) {
            blurController.setBlurAutoUpdate(this.blurAutoUpdate)
        }
    }

    /**
     * @param rootView  root to start blur from.
     * Can be Activity's root content layout (android.R.id.content)
     * or (preferably) some of your layouts. The lower amount of Views are in the root, the better for performance.
     * @param algorithm sets the blur algorithm
     * @return [BlurView] to setup needed params.
     *
     * @param rootView root to start blur from.
     * Can be Activity's root content layout (android.R.id.content)
     * or (preferably) some of your layouts. The lower amount of Views are in the root, the better for performance.
     *
     *
     * BlurAlgorithm is automatically picked based on the API version.
     * It uses RenderEffectBlur on API 31+, and RenderScriptBlur on older versions.
     * @return [BlurView] to setup needed params.
     */
    @JvmOverloads
    fun setupWith(
        rootView: ViewGroup,
        algorithm: BlurAlgorithm = blurAlgorithm,
    ): BlurViewFacade {
        blurController.destroy()
        blurController = PreDrawBlurController(this, rootView, overlayColor, algorithm)
        return blurController

    }

    // Setters duplicated to be able to conveniently change these settings outside of setupWith chain
    /**
     * @see BlurViewFacade.setBlurRadius
     */
    fun setBlurRadius(radius: Float): BlurViewFacade {
        return blurController.setBlurRadius(radius)
    }

    /**
     * @see BlurViewFacade.setOverlayColor
     */
    fun setOverlayColor(@ColorInt overlayColor: Int): BlurViewFacade {
        this.overlayColor = overlayColor
        return blurController.setOverlayColor(overlayColor)
    }

    /**
     * @see BlurViewFacade.setBlurAutoUpdate
     */
    fun setBlurAutoUpdate(enabled: Boolean): BlurViewFacade {
        this.blurAutoUpdate = enabled
        return blurController.setBlurAutoUpdate(enabled)
    }

    /**
     * @see BlurViewFacade.setBlurEnabled
     */
    fun setBlurEnabled(enabled: Boolean): BlurViewFacade {
        return blurController.setBlurEnabled(enabled)
    }

    private val blurAlgorithm: BlurAlgorithm
        get() = if (isS31Plus()) {
            RenderEffectBlur()
        } else {
            RenderScriptBlur(context)
        }
}
