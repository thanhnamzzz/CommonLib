package io.virgo_common.common_lib

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo_common.common_lib.blurView.BlurActivity
import io.virgo_common.common_lib.databinding.ActivityMainBinding
import io.virgo_common.common_lib.flowLayout.FlowLayoutActivity
import io.virgo_common.common_lib.toolBar.ToolBarActivity
import io.virgo_common.common_libs.baseApp.SimpleActivity
import io.virgo_common.common_libs.blurView.RenderEffectBlur
import io.virgo_common.common_libs.blurView.RenderScriptBlur
import io.virgo_common.common_libs.customView.shimmer.Shimmer
import io.virgo_common.common_libs.extensions.isS31Plus

class MainActivity : SimpleActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
	private val listKey = listOf("en", "hi", "ja", "vi")
	private var index = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
			insets
		}

		window.setFlags(
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
		)

//		GlobalFunction.hideSystemNavigationBar(window)

		Log.d("Namzzz", "MainActivity: onCreate")
//		val currentLocales = AppCompatDelegate.getApplicationLocales()
//		if (currentLocales.isEmpty) {
//			// Nếu chưa đặt Locale riêng cho app, dùng Locale mặc định của hệ thống
//			val systemLocale = Resources.getSystem().configuration.locales.get(0)
//			val language = systemLocale.language
//			val country = systemLocale.country
//			Log.d("SystemLocale", "Language: $language, Country: $country")
//		} else {
//			val currentLocale = currentLocales[0]
//			val language = currentLocale.language // Mã ngôn ngữ (vd: "en", "vi")
//			val country = currentLocale.country // Mã quốc gia (vd: "US", "VN")
//			Log.d("CurrentLocale", "Language: $language, Country: $country")
//		}}
		binding.btnOpenToolBar.setOnClickListener {
			startActivity(Intent(this@MainActivity, ToolBarActivity::class.java))
		}
		binding.btnOpenFlowLayout.setOnClickListener {
			startActivity(Intent(this@MainActivity, FlowLayoutActivity::class.java))
		}
		binding.btnOpenBlurLayout.setOnClickListener {
			startActivity(Intent(this@MainActivity, BlurActivity::class.java))
		}

//		binding.btnChangeLanguage.setOnClickListener {
//			val key = listKey[index]
//			Log.d("Namzzz", "MainActivity: index = $index | key = $key")
//			index = (index + 1) % listKey.size
//			val localeList = LocaleListCompat.forLanguageTags(key)
//			AppCompatDelegate.setApplicationLocales(localeList)
//		}

		val float = 10f
		val windowBackground = window.decorView.background
		val algorithm = if (isS31Plus()) {
			RenderEffectBlur()
		} else RenderScriptBlur(this)
		binding.viewBlur.setupWith(binding.root, algorithm)
			.setFrameClearDrawable(windowBackground)
			.setBlurRadius(float)
			.setBlurAutoUpdate(true)
			.setBlurEnabled(true)

		val shimmer = Shimmer().apply {
			setRepeatCount(-1)
			setDuration(1500)
			setStartDelay(1000)
			setDirection(Shimmer.ANIMATION_DIRECTION_RTL)
			setAnimatorListener(object : Animator.AnimatorListener {
				override fun onAnimationStart(p0: Animator) {

				}

				override fun onAnimationEnd(p0: Animator) {

				}

				override fun onAnimationCancel(p0: Animator) {

				}

				override fun onAnimationRepeat(p0: Animator) {

				}
			})
		}
//		val shimmer = Shimmer()
		shimmer.start(binding.shimmerTv)
	}
}