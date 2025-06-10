package io.virgo_common.common_lib.blurView

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import io.virgo_common.common_lib.R
import io.virgo_common.common_lib.databinding.ActivityBlurBinding
import io.virgo_common.common_lib.toolBar.ViewAdapter
import io.virgo_common.common_libs.baseApp.SimpleActivity
import io.virgo_common.common_libs.blurView.RenderEffectBlur
import io.virgo_common.common_libs.blurView.RenderScriptBlur
import io.virgo_common.common_libs.extensions.isS31Plus

class BlurActivity : SimpleActivity<ActivityBlurBinding>(ActivityBlurBinding::inflate) {
	private lateinit var viewAdapter: ViewAdapter
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

		viewAdapter = ViewAdapter(this)
		val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
		binding.rvList.addItemDecoration(itemDecoration)
		binding.rvList.adapter = viewAdapter

		var float = 2f
		val windowBackground = window.decorView.background
		val algorithm = if (isS31Plus()) {
			RenderEffectBlur()
		} else RenderScriptBlur(this)
		binding.blurView.setupWith(binding.root, algorithm)
			.setFrameClearDrawable(windowBackground)
			.setBlurRadius(float)
			.setBlurAutoUpdate(false)
			.setBlurEnabled(true)

		binding.seekbarBlur.progress = float.toInt()
		binding.seekbarBlur.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
			override fun onProgressChanged(
				p0: SeekBar?,
				p1: Int,
				p2: Boolean
			) {
				if (p1 > 0)
					binding.blurView.setBlurRadius(p1.toFloat())
			}

			override fun onStartTrackingTouch(p0: SeekBar?) {

			}

			override fun onStopTrackingTouch(p0: SeekBar?) {

			}
		})
	}
}