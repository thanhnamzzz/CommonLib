package io.virgo_common.common_libs.baseApp

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import io.virgo_common.common_libs.extensions.viewBinding

abstract class SimpleActivity<VB : ViewBinding>(bindingInflater: (LayoutInflater) -> VB) :
    AppCompatActivity() {
    protected val binding by viewBinding(bindingInflater)
}