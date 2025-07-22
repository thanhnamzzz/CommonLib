package io.virgo_common.common_libs.blurViewV2

import android.graphics.Bitmap
import android.graphics.Canvas

interface BlurAlgorithm {
    /**
     * @param bitmap     bitmap to be blurred
     * @param blurRadius blur radius
     * @return blurred bitmap
     */
    fun blur(bitmap: Bitmap, blurRadius: Float): Bitmap?

    /**
     * Frees allocated resources
     */
    fun destroy()

    /**
     * @return true if this algorithm returns the same instance of bitmap as it accepted
     * false if it creates a new instance.
     *
     *
     * If you return false from this method, you'll be responsible to swap bitmaps in your
     * [BlurAlgorithm.blur] implementation
     * (assign input bitmap to your field and return the instance algorithm just blurred).
     */
    fun canModifyBitmap(): Boolean

    /**
     * Retrieve the [Bitmap.Config] on which the [BlurAlgorithm]
     * can actually work.
     *
     * @return bitmap config supported by the given blur algorithm.
     */
    val supportedBitmapConfig: Bitmap.Config

    fun render(canvas: Canvas, bitmap: Bitmap)
}
