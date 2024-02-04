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
include(":domain:task")
include(":data:color:api")
include(":data:color:impl")
include(":domain:color")
include(":data:daily:api")
include(":data:daily:impl")
include(":domain:daily")
include(":data:sleep:api")
include(":data:sleep:impl")
include(":domain:sleep")
include(":feature:main")
include(":core:ui")
include(":feature:color")
include(":feature:measure")
include(":data:alarm:api")
include(":data:alarm:impl")
include(":domain:alarm")
include(":feature:popup")
