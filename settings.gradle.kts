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

include(":shared:date")

include(":core:database")
include(":core:datastore")
include(":core:jwt")
include(":core:network")
include(":core:project")
include(":core:school")
include(":core:ui")

include(":data")
include(":design-system")
include(":domain")
include(":network")
include(":database")

include(":feature")
