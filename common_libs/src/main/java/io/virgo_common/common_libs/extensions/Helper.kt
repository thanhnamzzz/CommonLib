package io.virgo_common.common_libs.extensions

import android.app.Activity
import android.content.Context
import android.media.MediaScannerConnection
import android.view.Window
import android.view.WindowManager
import io.virgo_common.common_libs.views.MyToast

fun reloadGallerySystem(context: Context, filePath: String) {
    MediaScannerConnection.scanFile(
        context, arrayOf(filePath), null
    ) { _, _ -> }
}

fun toastMess(activity: Activity, mess: String, duration: Int, typeToast: MyToast.TypeToast) {
    when (typeToast) {
        MyToast.TypeToast.TOAST_SUCCESS -> MyToast.showToastSuccess(activity, mess, duration)
        MyToast.TypeToast.TOAST_ERROR -> MyToast.showToastError(activity, mess, duration)
        MyToast.TypeToast.TOAST_WARNING -> MyToast.showToastWarning(activity, mess, duration)
        MyToast.TypeToast.TOAST_NONE -> MyToast.showToastNone(activity, mess, duration)
    }
}

fun Window.keepScreenOn() {
    this.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun Window.clearKeepScreenOn() {
    this.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}