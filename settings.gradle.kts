pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TiTi"
include(":app")
include(":core:designsystem")
include(":feature:time")
include(":core:util")
include(":data:time")
include(":data:time:impl")
include(":data:time:api")
include(":domain:time")
include(":data:task:impl")
include(":data:task:api")
