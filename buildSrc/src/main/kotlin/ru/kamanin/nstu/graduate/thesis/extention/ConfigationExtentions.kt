package ru.kamanin.nstu.graduate.thesis.extention

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import ru.kamanin.nstu.graduate.thesis.config.Configuration

internal fun BaseExtension.setDefaultApplicationConfig() {
	compileSdkVersion(Configuration.COMPILE_SDK)
	defaultConfig {
		applicationId = Configuration.APPLICATION_ID
		minSdk = Configuration.MIN_SDK
		targetSdk = Configuration.TARGET_SDK
		versionCode = Configuration.VERSION_CODE
		versionName = Configuration.VERSION_NAME
		multiDexEnabled = true
	}
}

internal fun BaseExtension.setDefaultLibraryConfig() {
	compileSdkVersion(Configuration.COMPILE_SDK)
	defaultConfig {
		minSdk = Configuration.MIN_SDK
		targetSdk = Configuration.TARGET_SDK
		multiDexEnabled = true
	}
}

internal fun BaseExtension.setJavaVersion() {
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
}

internal fun BaseExtension.setExcludes() {
	packagingOptions {
		resources {
			excludes.addAll(Configuration.excludes)
		}
	}
}

internal fun BaseExtension.setBuildFeatures() {
	buildFeatures.viewBinding = true
}

internal fun BaseExtension.setBuildTypes() {
	buildTypes {
		getByName(Configuration.BuiltTypes.RELEASE)
		getByName(Configuration.BuiltTypes.DEBUG)
	}
}

internal fun BaseExtension.setProductFlavor() {
	flavorDimensions("server")

	productFlavors {
		create(Configuration.ProductFlavors.MOCK) {
			dimension = "server"
			buildStringField(Configuration.BACKEND_ENDPOINT_FIELD to Configuration.BACKEND_MOCK_URL)
		}
		create(Configuration.ProductFlavors.LIVE) {
			dimension = "server"
			buildStringField(Configuration.BACKEND_ENDPOINT_FIELD to Configuration.BACKEND_LIVE_URL)
		}
	}
}

internal fun BaseExtension.setLintOptions() {
	lintOptions {
		disable += "StringFormatInvalid"
	}
}