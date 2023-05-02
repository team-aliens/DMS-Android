object Plugins {

    object Gradle {
        const val Android = "com.android.tools.build:gradle:${Versions.Gradle.Android}"
        const val Kotlin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Gradle.Kotlin}"
        const val Hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Di.Hilt}"
    }

    object Module {
        const val AndroidApplication = "com.android.application"
        const val AndroidLibrary = "com.android.library"
        const val KotlinAndroid = "org.jetbrains.kotlin.android"
        const val KotlinKapt = "kotlin-kapt"
        const val Hilt = "dagger.hilt.android.plugin"
    }
}
