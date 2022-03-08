package ru.kamanin.nstu.graduate.thesis.plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.kamanin.nstu.graduate.thesis.extention.*

object LibraryPlugin : Plugin<Project> {

	override fun apply(project: Project) {

		project.setCompileOptions()
		project.configure<BaseExtension> {
			setDefaultLibraryConfig()
			setJavaVersion()
			setExcludes()
			setBuildFeatures()
		}
	}
}