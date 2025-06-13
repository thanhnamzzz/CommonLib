package io.virgo_common.common_libs.customView.shimmerK

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * Shimmer
 * User: romainpiel
 * Date: 06/03/2014
 * Time: 10:19
 *
 * Shimmering Button
 * Dumb class wrapping a ShimmerViewHelper
 */
class ShimmerButton : AppCompatButton, ShimmerViewBase {
	private val shimmerViewHelper: ShimmerViewHelper?

	constructor(context: Context) : super(context) {
		shimmerViewHelper = ShimmerViewHelper(this, paint, null)
		shimmerViewHelper.setPrimaryColor(currentTextColor)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		shimmerViewHelper = ShimmerViewHelper(this, paint, attrs)
		shimmerViewHelper.setPrimaryColor(currentTextColor)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
		context,
		attrs,
		defStyle
	) {
		shimmerViewHelper = ShimmerViewHelper(this, paint, attrs)
		shimmerViewHelper.setPrimaryColor(currentTextColor)
	}

	override var gradientX: Float
		get() = shimmerViewHelper!!.getGradientX()
		set(gradientX) {
			shimmerViewHelper!!.setGradientX(gradientX)
		}

	override var isShimmering: Boolean
		get() = shimmerViewHelper!!.isShimmering
		set(isShimmering) {
			shimmerViewHelper!!.isShimmering = isShimmering
		}

	override val isSetUp: Boolean
		get() = shimmerViewHelper!!.isSetUp

	override fun setAnimationSetupCallback(callback: ShimmerViewHelper.AnimationSetupCallback?) {
		shimmerViewHelper!!.setAnimationSetupCallback(callback)
	}

	override var primaryColor: Int
		get() = shimmerViewHelper!!.getPrimaryColor()
		set(primaryColor) {
			shimmerViewHelper!!.setPrimaryColor(primaryColor)
		}

	override var reflectionColor: Int
		get() = shimmerViewHelper!!.getReflectionColor()
		set(reflectionColor) {
			shimmerViewHelper!!.setReflectionColor(reflectionColor)
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
