@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.library.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
	id(libs.plugins.kotlin.kapt.get().pluginId)
}

dependencies {
	implementation(libs.android.paging)

	implementation(libs.bundles.kotlin.coroutines)

	implementation(libs.squareup.retrofit)
	implementation(libs.squareup.moshi.kotlin)
	kapt(libs.squareup.moshi.codegen)

	implementation(libs.hilt.android)

	implementation(projects.shared.exam)
	implementation(projects.shared.chat)

	implementation(projects.utils.coroutines)
	implementation(projects.utils.paging)
}