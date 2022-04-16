package ru.kamanin.nstu.graduate.thesis.config

internal object Configuration {

	const val APPLICATION_ID = "ru.kamanin.nstu.graduate.thesis"

	const val MIN_SDK = 24
	const val COMPILE_SDK = 31
	const val TARGET_SDK = 31

	const val VERSION_CODE = 1
	const val VERSION_NAME = "1.0.0"

	val excludes =
		listOf("META-INF/*.kotlin_module")

	const val BACKEND_ENDPOINT_FIELD = "BACKEND_ENDPOINT"
	const val BACKEND_DEVELOP_URL = "http://nstu.mock.ru/"
	const val BACKEND_LIVE_URL = "http://217.71.129.139:4502/"

	object BuiltTypes {

		const val DEBUG = "debug"
		const val RELEASE = "release"
	}

	object ProductFlavors {

		const val DEVELOP = "develop"
		const val LIVE = "live"
	}
}