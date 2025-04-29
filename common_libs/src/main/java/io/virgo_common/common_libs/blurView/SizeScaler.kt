package io.virgo_common.common_libs.blurView

import io.virgo_common.common_libs.blurView.SizeScaler.Companion.ROUNDING_VALUE
import java.lang.Float
import kotlin.Boolean
import kotlin.Int
import kotlin.math.ceil

/**
 * Scales width and height by [scaleFactor],
 * and then rounds the size proportionally so the width is divisible by [ROUNDING_VALUE]
 */
class SizeScaler(private val scaleFactor: Float) {

	companion object {
		private const val ROUNDING_VALUE = 64
	}

	fun scale(width: Int, height: Int): Size {
		val nonRoundedScaledWidth = downscaleSize(width.toDouble())
		val scaledWidth = roundSize(nonRoundedScaledWidth)
		val roundingScaleFactor = (width / scaledWidth).toFloat()
		val scaledHeight = ceil(height / roundingScaleFactor).toInt()
		return Size(scaledWidth, scaledHeight, roundingScaleFactor)
	}

	fun isZeroSized(measuredWidth: Int, measuredHeight: Int): Boolean {
		return downscaleSize(measuredHeight.toDouble()) == 0 || downscaleSize(measuredWidth.toDouble()) == 0
	}

	private fun roundSize(value: Int): Int {
		return if (value % ROUNDING_VALUE == 0) {
			value
		} else {
			value - (value % ROUNDING_VALUE) + ROUNDING_VALUE
		}
	}

	private fun downscaleSize(value: Double): Int {
		return ceil((value.toDouble()) / (scaleFactor).toDouble()).toInt()
	}

	data class Size(
		val width: Int,
		val height: Int,
		val scaleFactor: kotlin.Float
	)
}
