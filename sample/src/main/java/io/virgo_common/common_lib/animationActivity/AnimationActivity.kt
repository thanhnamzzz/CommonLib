package io.virgo_common.common_lib.animationActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo_common.common_lib.databinding.ActivityAnimationBinding
import io.virgo_common.common_libs.animationView.activityMakeBasicAnimation
import io.virgo_common.common_libs.animationView.activityMakeClipRevealAnimation
import io.virgo_common.common_libs.animationView.activityMakeScaleUpAnimation
import io.virgo_common.common_libs.animationView.activityMakeSceneTransition
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
			startActivity(
				intent,
				activityMakeSceneTransition(binding.btnMakeSceneTransition, "shared")
			)
		}

		binding.btnMakeBasic.setOnClickListener {
			startActivity(intent, activityMakeBasicAnimation())
		}

		binding.btnClipReveal.setOnClickListener {
			startActivity(intent, activityMakeClipRevealAnimation(binding.imageRec))
		}

		binding.btnScaleUp.setOnClickListener {
			startActivity(intent, activityMakeScaleUpAnimation(binding.imageRec))
		}
	}
}