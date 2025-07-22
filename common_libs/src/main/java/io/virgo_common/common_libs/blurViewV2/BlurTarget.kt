package io.virgo_common.common_libs.blurViewV2

import android.content.Context
import android.graphics.Canvas
import android.graphics.RenderNode
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * A FrameLayout that records a snapshot of its children on a RenderNode.
 * This snapshot is used by the BlurView to apply blur effect.
 */
class BlurTarget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        // Need both RenderNode (API 29) and RenderEffect (API 31) to be available for a full hardware rendering pipeline
        val canUseHardwareRendering: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    var renderNode: RenderNode? = if (canUseHardwareRendering) {
        RenderNode("BlurViewHost node")
    } else null

    override fun dispatchDraw(canvas: Canvas) {
        if (canUseHardwareRendering && canvas.isHardwareAccelerated && renderNode != null) {
            renderNode!!.setPosition(0, 0, width, height)
            val recordingCanvas = renderNode!!.beginRecording()
            super.dispatchDraw(recordingCanvas)
            renderNode!!.endRecording()
            canvas.drawRenderNode(renderNode!!)
        } else {
            super.dispatchDraw(canvas)
        }
    }
}
