@file:Suppress("UnstableApiUsage")

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

include(":app")

include(":core:database")
include(":core:datastore")
include(":core:network")

include(":data")
include(":design-system")
include(":domain")
include(":di")
include(":remote")
include(":local")

// TODO: remove module
include(":presentation")
