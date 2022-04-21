enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "GraduateThesis"

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}

// ------- App -------
include(":app")
// ----- Feature -----
include(":feature:exam:list")
include(":feature:exam:ticket")
include(":feature:navigation")
include(":feature:exam:chat")
include(":feature:exam:task")
include(":feature:sign")
// ---- Component ----
include(":component:navigation")
include(":component:core")
include(":component:ui")
// ----- Shared ------
include(":shared:session")
include(":shared:exam")
include(":shared:account")
include(":shared:artefact")
include(":shared:chat")