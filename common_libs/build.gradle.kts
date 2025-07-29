import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
	`maven-publish`
}

android {
	namespace = "io.virgo_common.common_libs"
	compileSdk = 36

	defaultConfig {
		minSdk = 21

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
	}
//	kotlin {
//		compilerOptions {
//			jvmTarget.set(JvmTarget.JVM_17)
//		}
//	}

	buildFeatures { viewBinding = true }
}

publishing.publications {
	create<MavenPublication>("release") {
		afterEvaluate {
			from(components["release"])
		}
	}
}

dependencies {
	implementation(libs.androidx.appcompat)
	implementation(libs.sdp.android)
	//Glide
	implementation(libs.glide)
}