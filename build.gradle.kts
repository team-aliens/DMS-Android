buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.Gradle.Android)
        classpath(Plugins.Gradle.Kotlin)
        classpath(Plugins.Gradle.Hilt)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}