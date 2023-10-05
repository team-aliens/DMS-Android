buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.Gradle.Android)
        classpath(Plugins.Gradle.Kotlin)
        classpath(Plugins.Gradle.Hilt)
        classpath(Plugins.Gradle.Firebase)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}