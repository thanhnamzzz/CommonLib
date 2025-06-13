package io.virgo_common.common_libs.customView.shimmerK

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import io.virgo_common.common_libs.customView.shimmerK.ShimmerViewHelper.AnimationSetupCallback

/**
 * Shimmer
 * User: romainpiel
 * Date: 06/03/2014
 * Time: 15:42
 *
 * Source: `https://github.com/romainpiel/Shimmer-android`
 */
class Shimmer {
	var repeatCount: Int
		private set
	var duration: Long
		private set
	var startDelay: Long
		private set
	var direction: Int
		private set
	var animatorListener: Animator.AnimatorListener? = null
		private set

	private var animator: ObjectAnimator? = null

	init {
		repeatCount = DEFAULT_REPEAT_COUNT
		duration = DEFAULT_DURATION
		startDelay = DEFAULT_START_DELAY
		direction = DEFAULT_DIRECTION
	}

	fun setRepeatCount(repeatCount: Int): Shimmer {
		this.repeatCount = repeatCount
		return this
	}

	fun setDuration(duration: Long): Shimmer {
		this.duration = duration
		return this
	}

	fun setStartDelay(startDelay: Long): Shimmer {
		this.startDelay = startDelay
		return this
	}

	fun setDirection(direction: Int): Shimmer {
		require(!(direction != ANIMATION_DIRECTION_LTR && direction != ANIMATION_DIRECTION_RTL)) { "The animation direction must be either ANIMATION_DIRECTION_LTR or ANIMATION_DIRECTION_RTL" }

		this.direction = direction
		return this
	}

	fun setAnimatorListener(animatorListener: Animator.AnimatorListener?): Shimmer {
		this.animatorListener = animatorListener
		return this
	}

	fun <V> start(shimmerView: V) where V : View, V : ShimmerViewBase? {
		if (this.isAnimating) {
			return
		}

		val animate: Runnable = object : Runnable {
			override fun run() {
				shimmerView.isShimmering = true

				var fromX = 0f
				var toX = shimmerView.width.toFloat()
				if (direction == ANIMATION_DIRECTION_RTL) {
					fromX = shimmerView.width.toFloat()
					toX = 0f
				}

				animator = ObjectAnimator.ofFloat(shimmerView, "gradientX", fromX, toX)
				animator!!.repeatCount = repeatCount
				animator!!.duration = duration
				animator!!.startDelay = startDelay
				animator!!.addListener(object : Animator.AnimatorListener {
					override fun onAnimationStart(
						animation: Animator,
						isReverse: Boolean
					) {
						super.onAnimationStart(animation, isReverse)
					}

					override fun onAnimationStart(p0: Animator) {

					}

					override fun onAnimationEnd(
						animation: Animator,
						isReverse: Boolean
					) {
						super.onAnimationEnd(animation, isReverse)
					}

					override fun onAnimationEnd(p0: Animator) {
						shimmerView.isShimmering = false

						shimmerView.postInvalidateOnAnimation()

						animator = null
					}

					override fun onAnimationCancel(p0: Animator) {

					}

					override fun onAnimationRepeat(p0: Animator) {

					}
				})

				if (animatorListener != null) {
					animator!!.addListener(animatorListener)
				}

				animator!!.start()
			}
		}

		if (!shimmerView.isSetUp) {
			shimmerView.setAnimationSetupCallback(object : AnimationSetupCallback {
				override fun onSetupAnimation(target: View?) {
					animate.run()
				}
			})
		} else {
			animate.run()
		}
	}

	fun cancel() {
		if (animator != null) {
			animator!!.cancel()
		}
	}

	val isAnimating: Boolean
		get() = animator != null && animator!!.isRunning

	companion object {
		const val ANIMATION_DIRECTION_LTR: Int = 0
		const val ANIMATION_DIRECTION_RTL: Int = 1

		const val DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE
		const val DEFAULT_DURATION: Long = 1000
		const val DEFAULT_START_DELAY: Long = 0
		const val DEFAULT_DIRECTION: Int = ANIMATION_DIRECTION_LTR
	}
}
