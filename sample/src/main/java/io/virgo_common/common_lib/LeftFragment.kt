package io.virgo_common.common_lib

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.virgo_common.common_lib.databinding.FragmentLeftBinding
import io.virgo_common.common_libs.baseApp.SimpleFragment

class LeftFragment : SimpleFragment<FragmentLeftBinding>(FragmentLeftBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}