package io.virgo_common.common_libs.views

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

fun Context.loadImage(
    view: ImageView,
    image: Any,
    complete: (complete: Boolean, error: String?) -> Unit
) {
    val exception = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let {
            (this as Activity).runOnUiThread {
                complete(false, it)
            }
        }
    }
    CoroutineScope(Dispatchers.Main).launch(exception) {
        Glide.with(this@loadImage).load(image).into(view)
        complete(true, null)
        cancel()
    }
}