package ru.kamanin.nstu.graduate.thesis.extention

import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.BuildType

internal object ConfigType {

	const val STRING = "String"
}

internal fun BuildType.buildStringField(field: Pair<String, String>) {
	buildConfigField(ConfigType.STRING, field.first, field.second)
}

internal fun BaseFlavor.buildStringField(field: Pair<String, String>) {
	buildConfigField(ConfigType.STRING, field.first, "\"${field.second}\"")
}