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
include (":feature_data")
include (":feature_domain")
include (":design-system")
include(":auth_data")
include(":auth_domain")
include(":local_database")
include(":local_domain")
include(":di")
