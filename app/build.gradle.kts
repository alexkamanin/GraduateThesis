@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	id(libs.plugins.android.application.get().pluginId)
	id(libs.plugins.kotlin.android.get().pluginId)
	id(libs.plugins.hilt.android.get().pluginId)
	id(libs.plugins.kotlin.kapt.get().pluginId)
	id(libs.plugins.google.gms.get().pluginId)
}

dependencies {

	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.messaging)

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
	implementation(projects.component.ui.resources)
	implementation(projects.component.ui.core)

	// ----- Shared ------
	implementation(projects.shared.api.headers)
	implementation(projects.shared.validation)
	implementation(projects.shared.clipdata)
	implementation(projects.shared.artefact)
	implementation(projects.shared.session)
	implementation(projects.shared.account)
	implementation(projects.shared.ticket)
	implementation(projects.shared.exam)
	implementation(projects.shared.chat)
	implementation(projects.shared.notification)

	// ------ Utils ------
	implementation(projects.utils.coroutines)
	implementation(projects.utils.encoding)
	implementation(projects.utils.paging)
	implementation(projects.utils.time)
	implementation(projects.utils.error)
}