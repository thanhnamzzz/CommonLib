package io.virgo_common.common_lib.toolBar

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DividerItemDecoration
import io.virgo_common.common_lib.databinding.ActivityToolBarBinding
import io.virgo_common.common_libs.baseApp.SimpleActivity
import io.virgo_common.common_libs.extensions.getBackgroundColor
import io.virgo_common.common_libs.extensions.updateNavigationBarForegroundColor
import io.virgo_common.common_libs.extensions.updateStatusBarForegroundColor

class ToolBarActivity : SimpleActivity<ActivityToolBarBinding>(ActivityToolBarBinding::inflate) {

	private lateinit var viewAdapter: ViewAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars =
				insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
			binding.rvList.setPadding(systemBars.left, systemBars.top + binding.appBar.height, systemBars.right, systemBars.bottom)
//			binding.appBar.setPadding(0, systemBars.top, 0, 0)
			insets
		}
		val color = binding.main.getBackgroundColor()
		window.updateStatusBarForegroundColor(color)
		window.navigationBarColor = Color.TRANSPARENT
		window.updateNavigationBarForegroundColor(color)

		viewAdapter = ViewAdapter(this)
		val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
		binding.rvList.addItemDecoration(itemDecoration)
		binding.rvList.adapter = viewAdapter

//		binding.toolBar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
		binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
		binding.nestedScroll.isNestedScrollingEnabled = false
	}
}