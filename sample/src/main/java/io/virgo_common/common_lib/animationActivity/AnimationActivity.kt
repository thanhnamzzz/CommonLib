package io.virgo_common.common_lib.animationActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo_common.common_lib.databinding.ActivityAnimationBinding
import io.virgo_common.common_libs.baseApp.SimpleActivity

class AnimationActivity :
	SimpleActivity<ActivityAnimationBinding>(ActivityAnimationBinding::inflate) {
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
		val intent = Intent(this, SecondActivity::class.java)

		binding.btnMakeSceneTransition.setOnClickListener {
			val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
				this,
				binding.btnMakeSceneTransition,
				"shared"
			)
			startActivity(intent, options.toBundle())
		}

		binding.btnMakeBasic.setOnClickListener {
			val options = ActivityOptionsCompat.makeBasic()
			startActivity(intent, options.toBundle())
		}

		binding.btnClipReveal.setOnClickListener {
			val o = ActivityOptionsCompat.makeClipRevealAnimation(
				binding.imageRec, 0, 0, binding.imageRec.width, binding.imageRec.height
			)
			startActivity(intent, o.toBundle())
		}

		binding.btnScaleUp.setOnClickListener {
			val o = ActivityOptionsCompat.makeScaleUpAnimation(
				binding.imageRec, 0,0,binding.imageRec.width,binding.imageRec.height
			)
			startActivity(intent, o.toBundle())
		}
	}
}