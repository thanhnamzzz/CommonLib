package io.virgo_common.common_libs.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var colors: IntArray = intArrayOf(Color.RED, Color.YELLOW, Color.BLUE)
    private var orientation: ColorOrientation = ColorOrientation.HORIZONTAL
    private var textShader: LinearGradient? = null

    /**For color list under IntArray*/
    fun setGradientColors(colors: IntArray) {
        this.colors = colors
        invalidateGradient()
        invalidate()
    }

    /**For color list under code #RRGGBB*/
    fun setGradientColors(colorStrings: Array<String>) {
        this.colors = colorStrings.map { Color.parseColor(it) }.toIntArray()
        invalidateGradient()
        invalidate()
    }

    fun setGradientOrientation(orientation: ColorOrientation) {
        this.orientation = orientation
        invalidateGradient()
        invalidate()
    }

    private var previousWidth: Float = 0f
    private fun invalidateGradient() {
        val width = paint.measureText(text.toString())
        if (width > 0 && (textShader == null || width != previousWidth)) {
            textShader = when (orientation) {
                ColorOrientation.HORIZONTAL -> LinearGradient(
                    0f,
                    0f,
                    width,
                    0f,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )

                ColorOrientation.VERTICAL -> LinearGradient(
                    0f,
                    0f,
                    0f,
                    textSize,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )

                ColorOrientation.DIAGONAL -> LinearGradient(
                    0f,
                    0f,
                    width,
                    textSize,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
            }
            previousWidth = width
        }
    }

    override fun onDraw(canvas: Canvas) {
        invalidateGradient()
        paint.shader = textShader
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidateGradient()
    }

    enum class ColorOrientation {
        HORIZONTAL, VERTICAL, DIAGONAL
    }
}