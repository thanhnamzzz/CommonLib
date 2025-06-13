package io.virgo_common.common_libs.customView.shimmerK

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import io.virgo_common.common_libs.customView.shimmerK.ShimmerViewHelper.AnimationSetupCallback

/**
 * Shimmer
 * User: romainpiel
 * Date: 06/03/2014
 * Time: 10:19
 *
 * Shimmering TextView
 * Dumb class wrapping a ShimmerViewHelper
 *
 * Source: `https://github.com/romainpiel/Shimmer-android`
 */
class ShimmerTextView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle), ShimmerViewBase {
	private var shimmerViewHelper: ShimmerViewHelper? = null

	override fun onFinishInflate() {
		super.onFinishInflate()
		if (shimmerViewHelper == null) {
			shimmerViewHelper = ShimmerViewHelper(this, paint, null).apply {
				setPrimaryColor(currentTextColor)
			}
		}
	}

	override var gradientX: Float
		get() = shimmerViewHelper?.getGradientX() ?: 0f
		set(gradientX) {
			shimmerViewHelper?.setGradientX(gradientX)
		}

	override var isShimmering: Boolean
		get() = shimmerViewHelper?.isShimmering == true
		set(isShimmering) {
			shimmerViewHelper?.isShimmering = isShimmering
		}

	override val isSetUp: Boolean
		get() = shimmerViewHelper?.isSetUp == true

	override fun setAnimationSetupCallback(callback: AnimationSetupCallback?) {
		shimmerViewHelper?.setAnimationSetupCallback(callback)
	}

	override var primaryColor: Int
		get() = shimmerViewHelper?.getPrimaryColor() ?: currentTextColor
		set(primaryColor) {
			shimmerViewHelper?.setPrimaryColor(primaryColor)
		}

	override var reflectionColor: Int
		get() = shimmerViewHelper?.getReflectionColor() ?: currentTextColor
		set(reflectionColor) {
			shimmerViewHelper?.setReflectionColor(reflectionColor)
		}

	override fun setTextColor(color: Int) {
		super.setTextColor(color)
		shimmerViewHelper?.setPrimaryColor(currentTextColor)
	}

	override fun setTextColor(colors: ColorStateList?) {
		super.setTextColor(colors)
		shimmerViewHelper?.setPrimaryColor(currentTextColor)
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldw, oldh)
		shimmerViewHelper?.onSizeChanged()
	}

	override fun onDraw(canvas: Canvas) {
		shimmerViewHelper?.onDraw()
		super.onDraw(canvas)
	}
}
