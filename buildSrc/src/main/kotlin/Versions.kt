import org.gradle.api.JavaVersion

object Versions {

    object Android {
        const val Core = "1.5.0"
        const val Activity = "1.2.3"
        const val AppCompat = "1.3.0"
        const val Work = "2.6.0-alpha01"
    }

    object Local {
        const val Room = "2.4.3"
        const val DataStore = "1.0.0"
    }

    object Serialization {
        const val Gson = "2.9.0"
        const val Moshi = "1.13.0"
    }

    object Di {
        const val Hilt = "2.44"
        const val JavaInject = "1"
    }

    object Java {
        val Java = JavaVersion.VERSION_11
    }

    object Gradle {
        const val Android = "7.2.1"
        const val Kotlin = "1.6.10"
    }

    object Kotlin {
        const val Kotlin = "1.6.0"
        const val Coroutines = "1.6.0"
    }

    object LifeCycle {
        const val LifeCycle = "2.4.1"
    }

    object Ui {
        const val ComposeGlide = "1.4.7"
        const val ComposeHiltNavigation = "1.0.0"
        const val Compose = "1.2.0-alpha07"
        const val ComposeActivity = "1.4.0"
        const val Coil = "2.0.0-rc01"
        const val Material = "1.3.0"
        const val Navigation = "2.5.1"
        const val NavigationAnimation = "0.24.5-alpha"
        const val TedImagePicker = "1.2.7"
        const val ViewPager = "0.23.1"
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

    object Util {
        const val LocalDateTime = "1.3.1"
    }
}
