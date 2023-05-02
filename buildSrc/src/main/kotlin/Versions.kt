import org.gradle.api.JavaVersion

object Versions {

    object Android {
        const val Core = "1.10.0"
        const val Activity = "1.7.1"
        const val AppCompat = "1.6.1"
        const val WorkRuntime = "2.8.1"
        const val LifeCycleRunTime = "2.6.1"
    }

    object Kotlin {
        const val Kotlin = "1.8.10"
    }

    object Java {
        val Java = JavaVersion.VERSION_11
    }

    object Gradle {
        const val Android = "7.4.2"
        const val Kotlin = "1.8.10"
    }

    object Ui {
        const val Compose = "1.4.2"
        const val ComposeGlide = "2.1.11"
        const val ComposeHiltNavigation = "1.0.0"
        const val Navigation = "2.5.3"
        const val NavigationAnimation = "0.30.1"
        const val ViewPager = "0.30.1"
        const val Material = "1.8.0"
        const val Coil = "2.3.0"
        const val TedImagePicker = "1.4.2"
    }

    object Di {
        const val Hilt = "2.44"
        const val JavaInject = "1"
    }

    object Serialization {
        const val Gson = "2.9.0"
        const val Moshi = "1.13.0"
    }

    object Util {
        const val LocalDateTime = "1.3.1"
    }

    object Local {
        const val Room = "2.4.3"
        const val DataStore = "1.0.0"
    }

    object Remote {
        const val Retrofit = "2.7.1"
        const val OkHttp = "4.9.3"
    }

    object Test {
        const val JUnit = "4.13.2"
        const val Mockito = "4.2.0"
        const val CoroutinesTest = "1.6.4"
    }
}
