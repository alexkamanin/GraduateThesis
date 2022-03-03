buildscript {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}
	dependencies {
		classpath(libs.gradle.android)
		classpath(libs.gradle.kotlin)
	}
}

tasks.register("clean", Delete::class.java) {
	delete(rootProject.buildDir)
}