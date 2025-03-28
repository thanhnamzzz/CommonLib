package io.virgo_common.common_lib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo_common.common_lib.databinding.ActivityMainBinding
import io.virgo_common.common_libs.baseApp.SimpleActivity
import io.virgo_common.common_libs.functions.GlobalFunction

class MainActivity : SimpleActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
	private val listKey = listOf("en", "hi", "ja", "vi")
	private var index = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		GlobalFunction.hideSystemNavigationBar(window)

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
//		}
		binding.btnOpenMaterialEdittext.setOnClickListener {
			startActivity(Intent(this@MainActivity, MaterialEditTextActivity::class.java))
		}

//		binding.btnChangeLanguage.setOnClickListener {
//			val key = listKey[index]
//			Log.d("Namzzz", "MainActivity: index = $index | key = $key")
//			index = (index + 1) % listKey.size
//			val localeList = LocaleListCompat.forLanguageTags(key)
//			AppCompatDelegate.setApplicationLocales(localeList)
//		}
	}
}