package io.virgo_common.common_lib.blurView

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import io.virgo_common.common_lib.databinding.ActivityBlurBinding
import io.virgo_common.common_lib.toolBar.ViewAdapter
import io.virgo_common.common_libs.baseApp.SimpleActivity

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

		//BlurView phiên bản cũ
		val float = 2f
//        val windowBackground = window.decorView.background
//        val algorithm = if (isS31Plus()) {
//            RenderEffectBlur()
//        } else RenderScriptBlur(this)
//        binding.blurView.setupWith(binding.root, algorithm)
//            .setFrameClearDrawable(windowBackground)
//            .setBlurRadius(float)
//            .setBlurAutoUpdate(false)
//            .setBlurEnabled(true)
//------------------

		//BlurView phiên baản mới 3.0.0
		binding.blurView.apply {
			setupWith(binding.blurTarget)
				.setFrameClearDrawable(window.decorView.background)
				.setBlurRadius(float)
		}
		binding.blurViewV3.apply {
			clipToOutline = true
			outlineProvider = object : ViewOutlineProvider() {
				override fun getOutline(p0: View?, p1: Outline?) {
					p1?.let {
						binding.blurViewV3.background.getOutline(it)
						it.alpha = 1f
					}
				}
			}
			setupWith(binding.blurTarget)
				.setFrameClearDrawable(window.decorView.background)
				.setBlurRadius(float)
		}
		binding.seekbarBlur.progress = float.toInt()
		binding.seekbarBlur.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
			override fun onProgressChanged(
				p0: SeekBar?,
				p1: Int,
				p2: Boolean,
			) {
				if (p1 > 0) {
					binding.blurView.setBlurRadius(p1.toFloat())
					binding.blurViewV3.setBlurRadius(p1.toFloat())
				}
			}

			override fun onStartTrackingTouch(p0: SeekBar?) {

			}

			override fun onStopTrackingTouch(p0: SeekBar?) {

			}
		})
	}
}