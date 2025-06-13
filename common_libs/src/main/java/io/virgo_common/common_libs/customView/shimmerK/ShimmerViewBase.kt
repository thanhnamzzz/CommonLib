package io.virgo_common.common_libs.customView.shimmerK

import io.virgo_common.common_libs.customView.shimmerK.ShimmerViewHelper.AnimationSetupCallback

/**
 * Shimmer
 * User: romainpiel
 * Date: 10/03/2014
 * Time: 17:33
 *
 * Source: `https://github.com/romainpiel/Shimmer-android`
 */
interface ShimmerViewBase {
	var gradientX: Float
	var isShimmering: Boolean
	val isSetUp: Boolean
	fun setAnimationSetupCallback(callback: AnimationSetupCallback?)
	var primaryColor: Int
	var reflectionColor: Int
}
