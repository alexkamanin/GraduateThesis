@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.library.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
	id(libs.plugins.hilt.android.get().pluginId)
	id(libs.plugins.kotlin.kapt.get().pluginId)
}

dependencies {
	implementation(libs.android.core)
	implementation(libs.android.appcompat)
	implementation(libs.android.material)
	implementation(libs.android.paging)
	implementation(libs.android.navigation.fragment)
	implementation(libs.viewbinding)

	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)

	implementation(projects.component.ui.resources)
	implementation(projects.component.ui.core)
	implementation(projects.component.navigation)

	implementation(projects.shared.clipdata)
	implementation(projects.shared.ticket)
	implementation(projects.shared.account)
	implementation(projects.shared.artefact)
	implementation(projects.shared.chat)
	implementation(projects.shared.exam)
	implementation(projects.shared.notification)

	implementation(projects.utils.coroutines)
	implementation(projects.utils.paging)
	implementation(projects.utils.error)
	implementation(projects.utils.time)
}