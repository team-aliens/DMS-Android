buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.GradlePlugin.GRADLE_ANDROID)
        classpath(Dependencies.GradlePlugin.GRADLE_KOTLIN)
        classpath(Dependencies.GradlePlugin.GRADLE_HILT)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}