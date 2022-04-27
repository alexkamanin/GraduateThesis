@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.library.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
}

dependencies {
	implementation(libs.android.core)
	implementation(libs.android.material)
	implementation(libs.android.fragment)
	implementation(libs.android.appcompat)
	implementation(libs.android.viewbinding)
	implementation(libs.viewbinding)

	implementation(projects.shared.artefact)

	implementation(projects.component.ui.resources)

	implementation(projects.utils.error)
}