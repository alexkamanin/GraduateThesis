import ru.kamanin.nstu.graduate.thesis.extention.configure

buildscript {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}
	dependencies {
		classpath(libs.gradle.android)
		classpath(libs.gradle.kotlin)
		classpath(libs.gradle.hilt)
	}
}

allprojects {
	this.configure()
}

tasks.register("clean", Delete::class.java) {
	delete(rootProject.buildDir)
}