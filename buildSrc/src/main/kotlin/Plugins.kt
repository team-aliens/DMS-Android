object Plugins {

    object Gradle {
        const val Android = "com.android.tools.build:gradle:${ProjectProperties.GRADLE_ANDROID}"
        const val Kotlin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${ProjectProperties.GRADLE_KOTLIN}"
        const val Hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    }

    object Module {
        const val AndroidApplication = "com.android.application"
        const val AndroidLibrary = "com.android.library"
        const val KotlinAndroid = "org.jetbrains.kotlin.android"
        const val KotlinKapt = "kotlin-kapt"
        const val Hilt = "dagger.hilt.android.plugin"
    }
}
