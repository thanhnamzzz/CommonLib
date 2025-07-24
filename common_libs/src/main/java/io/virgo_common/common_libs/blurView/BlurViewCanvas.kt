/* Clone from https://github.com/Dimezis/BlurView */

package io.virgo_common.common_libs.blurView

import android.graphics.Bitmap
import android.graphics.Canvas

// Serves purely as a marker of a Canvas used in BlurView
// to skip drawing itself and other BlurViews on the View hierarchy snapshot
class BlurViewCanvas(bitmap: Bitmap) : Canvas(bitmap)
