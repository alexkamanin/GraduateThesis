plugins {
	`kotlin-dsl`
}

repositories {
	gradlePluginPortal()
	mavenCentral()
	google()
}

dependencies {
	implementation(libs.gradle.android)
	implementation(libs.gradle.kotlin)
	implementation(libs.squareup.javapoet)
}