@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.library.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
}

dependencies {
	implementation(libs.hilt.android)
	implementation(libs.squareup.retrofit)
}