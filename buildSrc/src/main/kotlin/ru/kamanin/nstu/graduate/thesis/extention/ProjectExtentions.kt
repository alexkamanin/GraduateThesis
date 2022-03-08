package ru.kamanin.nstu.graduate.thesis.extention

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.kamanin.nstu.graduate.thesis.config.Configuration

internal fun Project.setCompileOptions() {
	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = Configuration.JVM_TARGET
	}
}

fun Project.configure() {
	plugins.whenPluginAdded {
		when (this) {
			is AppPlugin     -> ru.kamanin.nstu.graduate.thesis.plugins.ApplicationPlugin.apply(project)
			is LibraryPlugin -> ru.kamanin.nstu.graduate.thesis.plugins.LibraryPlugin.apply(project)
		}
	}
}