package io.virgo_common.common_libs.extensions

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun isUpsideDownCakePlus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun isTiramisuPlus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
fun isS32Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun isS31Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
fun isR30Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
fun isQ29Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
fun isP28Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun isO26Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
fun isN24Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun isM23Plus(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}