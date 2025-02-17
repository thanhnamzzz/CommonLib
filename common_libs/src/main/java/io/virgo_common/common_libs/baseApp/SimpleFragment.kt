package io.virgo_common.common_libs.baseApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class SimpleFragment<VB : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {
    private var _binding: VB? = null
    protected val fBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return fBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}