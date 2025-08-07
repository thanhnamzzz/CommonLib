package io.virgo_common.common_libs.animationView

import android.app.Activity
import android.view.View
import androidx.core.app.ActivityOptionsCompat

/** Ở activity thứ 2 thì thêm `android:transitionName="sharedElementName"` vào view mà activity 2 muốn được zoom vào */
fun Activity.activityMakeSceneTransition(
	viewTarget: View,
	sharedElementName: String
): ActivityOptionsCompat {
	return ActivityOptionsCompat.makeSceneTransitionAnimation(
		this,
		viewTarget,
		sharedElementName
	)
}

fun activityMakeClipRevealAnimation(
	viewTarget: View,
	startX: Int = 0,
	startY: Int = 0
): ActivityOptionsCompat {
	return ActivityOptionsCompat.makeClipRevealAnimation(
		viewTarget, startX, startY,
		viewTarget.width, viewTarget.height
	)
}

fun activityMakeScaleUpAnimation(
	viewTarget: View,
	startX: Int = 0,
	startY: Int = 0
): ActivityOptionsCompat {
	return ActivityOptionsCompat.makeScaleUpAnimation(
		viewTarget, startX, startY,
		viewTarget.width, viewTarget.height
	)
}

fun Activity.activityMakeCustomAnimation(enterRes: Int, exitRes: Int): ActivityOptionsCompat {
	return ActivityOptionsCompat.makeCustomAnimation(this, enterRes, exitRes)
}

fun activityMakeBasicAnimation(): ActivityOptionsCompat {
	return ActivityOptionsCompat.makeBasic()
}