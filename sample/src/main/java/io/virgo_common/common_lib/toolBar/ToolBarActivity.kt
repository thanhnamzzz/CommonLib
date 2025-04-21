package io.virgo_common.common_lib.toolBar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import io.virgo_common.common_lib.databinding.ActivityToolBarBinding
import io.virgo_common.common_libs.baseApp.SimpleActivity

class ToolBarActivity : SimpleActivity<ActivityToolBarBinding>(ActivityToolBarBinding::inflate) {

	private lateinit var viewAdapter: ViewAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars =
				insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//			binding.appBar.setPadding(0, systemBars.top, 0, 0)
			insets
		}

		viewAdapter = ViewAdapter(this)
		val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
		binding.rvList.addItemDecoration(itemDecoration)
		binding.rvList.adapter = viewAdapter

//		binding.toolBar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
		binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
		binding.nestedScroll.isNestedScrollingEnabled = false
	}
}