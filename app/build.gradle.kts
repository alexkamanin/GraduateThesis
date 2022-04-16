@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.application.get().pluginId)
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
	implementation(libs.android.navigation.ui)

	implementation(libs.squareup.interceptor)
	implementation(libs.squareup.moshi.converter)
	implementation(libs.squareup.retrofit)
	implementation(libs.squareup.moshi.kotlin)
	kapt(libs.squareup.moshi.codegen)

	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)

	implementation(libs.mockcept)

	// ----- Feature -----
	implementation(projects.feature.navigation)
	implementation(projects.feature.exam.list)
	implementation(projects.feature.exam.ticket)
	implementation(projects.feature.exam.chat)
	implementation(projects.feature.exam.task)
	implementation(projects.feature.sign)

	// ---- Component ----
	implementation(projects.component.navigation)
	implementation(projects.component.core)
	implementation(projects.component.ui)

	// ----- Shared ------
	implementation(projects.shared.artefact)
	implementation(projects.shared.session)
	implementation(projects.shared.account)
	implementation(projects.shared.exam)
}