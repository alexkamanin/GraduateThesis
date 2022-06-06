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
	implementation(libs.android.constraintlayout)
	implementation(libs.android.navigation.fragment)

	implementation(libs.viewbinding)

	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)

	implementation(projects.component.ui.resources)
	implementation(projects.component.ui.core)
	implementation(projects.component.navigation)

	implementation(projects.shared.session)
	implementation(projects.shared.account)
	implementation(projects.shared.validation)
	implementation(projects.shared.notification)

	implementation(projects.utils.coroutines)
	implementation(projects.utils.error)

	testImplementation(libs.test.core)
	testImplementation(libs.test.coroutines)
	testImplementation(libs.test.coroutines.flow)
	testImplementation(libs.test.mockito)
	testImplementation(libs.test.junit)
	testImplementation(projects.component.test)
}