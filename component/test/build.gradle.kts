@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.library.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
}

dependencies {

	implementation(libs.test.junit)
	implementation(libs.test.coroutines)
}