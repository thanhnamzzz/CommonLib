package io.virgo_common.common_libs.extensions

import android.view.View
import android.view.Window
import android.view.WindowInsetsController

fun Window.updateStatusBarColors(backgroundColor: Int) {
    statusBarColor = backgroundColor
    updateStatusBarForegroundColor(backgroundColor)
}

fun Window.updateStatusBarForegroundColor(backgroundColor: Int) {
    if (isR30Plus()) {
        if (backgroundColor.getContrastColor() == DARK_GREY) {
            insetsController!!.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            insetsController!!.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    } else if (isM23Plus()) {
        if (backgroundColor.getContrastColor() == DARK_GREY) {
            decorView.systemUiVisibility =
                decorView.systemUiVisibility.addBit(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            decorView.systemUiVisibility =
                decorView.systemUiVisibility.removeBit(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}

fun Window.updateNavigationBarColors(backgroundColor: Int) {
    navigationBarColor = backgroundColor
    updateNavigationBarForegroundColor(backgroundColor)
}

fun Window.updateNavigationBarForegroundColor(backgroundColor: Int) {
    if (isR30Plus()) {
        if (backgroundColor.getContrastColor() == DARK_GREY) {
            insetsController!!.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else {
            insetsController!!.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        }
    } else if (isO26Plus()) {
        if (backgroundColor.getContrastColor() == DARK_GREY) {
            decorView.systemUiVisibility =
                decorView.systemUiVisibility.addBit(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        } else {
            decorView.systemUiVisibility =
                decorView.systemUiVisibility.removeBit(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }
}