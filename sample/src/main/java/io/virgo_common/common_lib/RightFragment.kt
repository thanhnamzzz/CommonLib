package io.virgo_common.common_lib

import android.os.Bundle
import android.view.View
import io.virgo_common.common_lib.databinding.FragmentRightBinding
import io.virgo_common.common_libs.baseApp.SimpleFragment

class RightFragment : SimpleFragment<FragmentRightBinding>(FragmentRightBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}