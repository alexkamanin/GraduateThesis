import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}