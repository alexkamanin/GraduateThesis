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
include(":component:ui:resources")
include(":component:ui:core")
// ----- Shared ------
include(":shared:api:headers")
include(":shared:session")
include(":shared:exam")
include(":shared:account")
include(":shared:artefact")
include(":shared:validation")
include(":shared:clipdata")
include(":shared:ticket")
include(":shared:chat")
// ----- Utils -------
include(":utils:coroutines")
include(":utils:paging")
include(":utils:time")
include(":utils:encoding")
include(":utils:error")