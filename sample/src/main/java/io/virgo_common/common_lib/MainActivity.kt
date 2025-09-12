package io.virgo_common.common_lib

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo_common.common_lib.animationActivity.AnimationActivity
import io.virgo_common.common_lib.blurView.BlurActivity
import io.virgo_common.common_lib.databinding.ActivityMainBinding
import io.virgo_common.common_lib.flowLayout.FlowLayoutActivity
import io.virgo_common.common_lib.toolBar.ToolBarActivity
import io.virgo_common.common_libs.animationView.AnimationView
import io.virgo_common.common_libs.animationView.Attention
import io.virgo_common.common_libs.baseApp.SimpleActivity
import io.virgo_common.common_libs.customView.shimmer.Shimmer

class MainActivity : SimpleActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
	private val listKey = listOf("en", "hi", "ja", "vi")
	private var index = 0
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

		window.setFlags(
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
			WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
		)
		AnimationView().apply {
			setAnimation(Attention().Ruberband(binding.btnGradientButton))
			isLoop(true)
			setDelayLoop(2000)
			start()
		}

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
			startActivity(Intent(this, ToolBarActivity::class.java))
		}
		binding.btnOpenFlowLayout.setOnClickListener {
			startActivity(Intent(this, FlowLayoutActivity::class.java))
		}
		binding.btnOpenBlurLayout.setOnClickListener {
			startActivity(Intent(this, BlurActivity::class.java))
		}

		binding.btnGradientButton.setOnClickListener {
			startActivity(Intent(this, AnimationActivity::class.java))
		}

//		binding.btnChangeLanguage.setOnClickListener {
//			val key = listKey[index]
//			Log.d("Namzzz", "MainActivity: index = $index | key = $key")
//			index = (index + 1) % listKey.size
//			val localeList = LocaleListCompat.forLanguageTags(key)
//			AppCompatDelegate.setApplicationLocales(localeList)
//		}


		//BlurView phiên bản cũ
		val float = 10f
//		val windowBackground = window.decorView.background
//		val algorithm = if (isS31Plus()) {
//			RenderEffectBlur()
//		} else RenderScriptBlur(this)
//		binding.viewBlur.setupWith(binding.root, algorithm)
//			.setFrameClearDrawable(windowBackground)
//			.setBlurRadius(float)
//			.setBlurAutoUpdate(true)
//			.setBlurEnabled(true)
//		-------------

		//BlurView phiên bản mới 3.0.0
		binding.viewBlur.apply {
			setupWith(binding.blurTarget)
				.setFrameClearDrawable(window.decorView.background)
				.setBlurRadius(float)
		}


		val shimmer = Shimmer().apply {
			setRepeatCount(-1) //Có thể bỏ qua vì mặc định là -1 là ValueAnimator.INFINITE lặp vô hạn
			setDuration(1500) //Default = 1000L
			setStartDelay(1000) //Default = 0 - không delay khi start()
			setDirection(Shimmer.ANIMATION_DIRECTION_RTL) //Hướng shimmer chạy ANIMATION_DIRECTION_LTR or ANIMATION_DIRECTION_RTL
			setAnimatorListener(object : Animator.AnimatorListener {
				override fun onAnimationStart(p0: Animator) {}

				override fun onAnimationEnd(p0: Animator) {}

				override fun onAnimationCancel(p0: Animator) {}

				override fun onAnimationRepeat(p0: Animator) {}
			}) //bắt sự kiện shimmer chạy
		}
		binding.shimmerTv.apply {
			reflectionColor = ContextCompat.getColor(this@MainActivity, R.color.black)
		}
		shimmer.start(binding.shimmerTv)
	}

	private val launcher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			Log.d("Namzzz", "MainActivity: launcher")
			when (it.resultCode) {
				RESULT_OK -> {
					Log.i("Namzzz", "MainActivity: RESULT_OK")
				}
				RESULT_CANCELED -> {
					Log.e("Namzzz", "MainActivity: RESULT_CANCEL")
				}
				else -> {
					Log.d("Namzzz", "MainActivity: other: ${it.resultCode}")
				}
			}
		}
}