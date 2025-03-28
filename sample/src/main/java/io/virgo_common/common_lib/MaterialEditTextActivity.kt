package io.virgo_common.common_lib

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo_common.common_lib.databinding.ActivityMaterialEditTextBinding
import io.virgo_common.common_libs.baseApp.SimpleActivity
import io.virgo_common.common_libs.customView.materialEdittext.validation.RegexpValidator

class MaterialEditTextActivity :
	SimpleActivity<ActivityMaterialEditTextBinding>(ActivityMaterialEditTextBinding::inflate) {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		Log.d("Namzzz", "MaterialEditTextActivity: onCreate")
		initEnableBt()
		initSingleLineEllipsisEt()
		initSetErrorEt()
		initValidationEt()
	}

	private fun initEnableBt() {
		binding.enableBt.setOnClickListener {
			binding.basicEt.isEnabled = !binding.basicEt.isEnabled
			binding.enableBt.text = if (binding.basicEt.isEnabled) "DISABLE" else "ENABLE"
		}
	}

	private fun initSingleLineEllipsisEt() {
		binding.singleLineEllipsisEt.text?.let { binding.singleLineEllipsisEt.setSelection(it.length) }
	}

	private fun initSetErrorEt() {
		binding.setErrorBt.setOnClickListener {
			binding.bottomTextEt.setError("1-line Error!")
		}
		binding.setError2Bt.setOnClickListener {
			binding.bottomTextEt.setError("2-line\nError!")
		}
		binding.setError3Bt.setOnClickListener {
			binding.bottomTextEt.setError("So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors!")
		}
	}

	private fun initValidationEt() {
		binding.validationEt.addValidator(RegexpValidator("Only Integer Valid!", "\\d+"))
		binding.validateBt.setOnClickListener {
			binding.validationEt.validate()
		}
	}
}