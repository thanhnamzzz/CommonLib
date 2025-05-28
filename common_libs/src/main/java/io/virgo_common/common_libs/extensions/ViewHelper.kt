package io.virgo_common.common_libs.extensions

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup

fun View.visibility() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.getPointLocation(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    val point = Point()
    point.x = location[0]
    point.y = location[1]
    return point
}

fun ViewGroup.getBackgroundColor(): Int {
    return (background as? ColorDrawable)?.color ?: Color.TRANSPARENT
}