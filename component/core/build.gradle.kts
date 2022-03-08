@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.library.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
	id(libs.plugins.hilt.android.get().pluginId)
	id(libs.plugins.kotlin.kapt.get().pluginId)
}

dependencies {
	implementation(libs.android.core)
	implementation(libs.android.material)
	implementation(libs.android.lifecycle)
	implementation(libs.android.appcompat)
	implementation(libs.android.fragment)
	implementation(libs.viewbinding)

	implementation(libs.squareup.retrofit)

	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)

	implementation(libs.bundles.kotlin.coroutines)

	implementation(projects.component.ui)
}