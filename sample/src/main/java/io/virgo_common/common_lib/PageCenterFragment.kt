package io.virgo_common.common_lib

import android.os.Bundle
import android.view.View
import io.virgo_common.common_lib.databinding.FragmentPageCenterBinding
import io.virgo_common.common_libs.baseApp.SimpleFragment
import java.util.Locale

class PageCenterFragment :
    SimpleFragment<FragmentPageCenterBinding>(FragmentPageCenterBinding::inflate) {
    private var index = 0
    fun setIndex(i: Int) {
        index = i
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fBinding.tvPage.text = String.format(Locale.getDefault(), "%d", index)
    }
}