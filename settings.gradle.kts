@file:Suppress("UnstableApiUsage")

include(":core:notification")



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
include(":shared:exception")
include(":shared:model")
include(":shared:validator")

include(":core:database")
include(":core:datastore")
include(":core:design-system")
include(":core:jwt")
include(":core:network")
include(":core:project")
include(":core:school")
include(":core:file")
include(":core:ui")

include(":data")
include(":network")
include(":database")

include(":feature")
