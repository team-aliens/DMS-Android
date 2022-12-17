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

rootProject.name = "DMS_Android"

include (":presentation")
include (":data")
include (":domain")
include (":design-system")
include(":local_database")
include(":local_domain")
include(":di")
