package io.virgo_common.common_libs.blurViewV2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Shader
import io.virgo_common.common_libs.R
import androidx.core.graphics.createBitmap

object Noise {
    private var noisePaint: Paint? = null

    fun apply(canvas: Canvas, context: Context, width: Int, height: Int) {
        initPaint(context)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), noisePaint!!)
    }

    private fun initPaint(context: Context) {
        if (noisePaint == null) {
            val alphaBitmap = getNoiseBitmap(context)
            noisePaint = Paint().apply {
                isAntiAlias = true
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
                shader = BitmapShader(alphaBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            }
        }
    }

    private fun getNoiseBitmap(context: Context): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.blue_noise)
        val alphaBitmap = createBitmap(bitmap.width, bitmap.height)
        val canvas = Canvas(alphaBitmap)
        val paint = Paint().apply { alpha = 38 } // 15% opacity
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return alphaBitmap
    }
}
