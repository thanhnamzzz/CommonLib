package io.virgo_common.common_lib

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomMarqueeTextView(context: Context, attrs: AttributeSet) :
    AppCompatTextView(context, attrs) {
    init {
        setMarqueeSpeed(1.0f) // 1.0 là tốc độ bình thường
    }

    fun setMarqueeSpeed(speed: Float) {
        try {
            val field = AppCompatTextView::class.java.getDeclaredField("mMarquee")
            field.isAccessible = true
            val marquee = field.get(this)
            val speedField = marquee.javaClass.getDeclaredField("mScrollUnit")
            speedField.isAccessible = true
            speedField.setFloat(marquee, speed) // Speed là giá trị float
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}