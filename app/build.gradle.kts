plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	compileSdk = 31

	defaultConfig {
		applicationId = "ru.kamanin.nstu.graduate.thesis"
		minSdk = 24
		targetSdk = 31
		versionCode = 1
		versionName = "1.0.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
}

dependencies {
	implementation(libs.android.core)
	implementation(libs.android.appcompat)
	implementation(libs.android.material)
	implementation(libs.android.constraintlayout)
}