package io.virgo_common.common_libs.functions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.io.File

object GlobalFunction {

    fun hideSystemNavigationBar(window: Window) {
        val decorView = window.decorView
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.show(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun hideSystemStatusBar(window: Window) {
        val decorView = window.decorView
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, decorView).let { controller ->
            controller.show(WindowInsetsCompat.Type.navigationBars())
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun hideSystemUiBar(window: Window) {
        val decorView = window.decorView
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun showSystemUiBar(window: Window) {
        val decorView = window.decorView
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, decorView).let { controller ->
            controller.show(WindowInsetsCompat.Type.navigationBars())
            controller.show(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun hideKeyBoard(activity: Context, editText: EditText) {
        editText.clearFocus()
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun showKeyBoard(activity: Context, editText: EditText) {
        editText.requestFocus()
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun shareApp(activity: Activity) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool app!")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Download the app from: https://play.google.com/store/apps/details?id=${activity.packageName}"
        )
        activity.startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun openPrivacyPolicy(activity: Activity) {
        //Need add queries action view in manifest
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://gambisoft.com/policy.html")
        )
        if (intent.resolveActivity(activity.packageManager) != null) activity.startActivity(intent)
        else Log.d("Namzzz", "GlobalFunction: openPrivacyPolicy null")
    }

    fun shareFile(context: Context, file: File, fileDoesNotExists: () -> Unit) {
        if (file.exists()) {
            val uri: Uri =
                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                data = uri
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share file using"))
        } else {
            fileDoesNotExists()
        }
    }

    fun Context.shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, "Share text via")
        this.startActivity(chooser)
    }

    fun versionApp(context: Context): String {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }

    fun openMarket(context: Context) {
        try {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}"))
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
            )
            context.startActivity(intent)
        }
    }

    fun openMoreApp(context: Context, id: String = "GambiSoftVn") {
        val devUrl = "https://play.google.com/store/apps/developer?id=$id"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(devUrl)
            setPackage("com.android.vending")
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(devUrl))
            context.startActivity(webIntent)
        }
    }

    @Suppress("DEPRECATION")
    fun isConnectedInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (isM23Plus()) {
            var networkCapabilities: NetworkCapabilities? = null
            val network = connectivityManager.activeNetwork
            if (network != null) networkCapabilities =
                connectivityManager.getNetworkCapabilities(network)

            networkCapabilities?.let {
                return if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) true
                else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) true
                else it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: run { return false }
        } else {
            connectivityManager.activeNetworkInfo?.let {
                return it.isConnected
            } ?: run { return false }
        }
    }
}