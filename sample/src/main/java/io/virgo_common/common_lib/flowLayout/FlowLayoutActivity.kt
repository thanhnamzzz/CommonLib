package io.virgo_common.common_lib.flowLayout

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.virgo_common.common_lib.R
import io.virgo_common.common_lib.databinding.ActivityFlowLayoutBinding
import io.virgo_common.common_lib.databinding.LayoutItemFlowLayoutBinding
import io.virgo_common.common_libs.baseApp.SimpleActivity
import kotlinx.coroutines.launch

class FlowLayoutActivity :
	SimpleActivity<ActivityFlowLayoutBinding>(ActivityFlowLayoutBinding::inflate) {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars =
				insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		lifecycleScope.launch {
			initFlowLayout()
		}
	}

	private fun initFlowLayout() {
		val list = mutableListOf<FlowObject>()
		list.apply {
			add(FlowObject("\uD83C\uDFB5 Classical", keySelect = "classical"))
			add(FlowObject("\uD83C\uDFB9 Jazz", keySelect = "jazz"))
			add(FlowObject("\uD83C\uDFB6 Blues ", keySelect = "blues"))
			add(FlowObject("\uD83C\uDFBC Pop ", keySelect = "pop"))
			add(FlowObject("\uD83C\uDFB8 Rock", keySelect = "rock"))
			add(FlowObject("\uD83C\uDFB5 Ballad", keySelect = "ballad"))
			add(FlowObject("\uD83C\uDFA4 R&B / Soul", keySelect = "rb_soul"))
			add(FlowObject("\uD83C\uDFAE Game / Anime add(Music)", keySelect = "game_anime"))
			add(FlowObject("\uD83C\uDFAC Soundtrack ", keySelect = "sound_track"))
		}

		list.forEach { ob ->
			val bd = LayoutItemFlowLayoutBinding.inflate(
				LayoutInflater.from(this),
				binding.flowLayout,
				false
			)
			bd.tvName.text = ob.name
			bd.tvName.setOnClickListener {
				ob.isSelect = !ob.isSelect
				if (ob.isSelect) {
					bd.tvName.setTextColor(ContextCompat.getColor(this, R.color.white))
					bd.tvName.setBackgroundResource(R.drawable.bg_border_white_r10_select)
				} else {
					bd.tvName.setTextColor(ContextCompat.getColor(this, R.color.black))
					bd.tvName.setBackgroundResource(R.drawable.bg_border_black_r10)
				}
			}
			binding.flowLayout.addView(bd.root)
		}
	}
}