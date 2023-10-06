@file:Suppress("UnstableApiUsage")

include(":core:datastore")


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "dms-android"

include(":domain")
include(":data")
include(":remote")
include(":local")
include(":presentation")
include(":di")
include(":design-system")

include(":app")

