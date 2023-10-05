import org.gradle.api.JavaVersion

object Versions {

    object Android {
        const val Core = "1.12.0"
        const val Activity = "1.7.2"
        const val Fragment = "1.6.1"
        const val AppCompat = "1.6.1"
        const val WorkRuntime = "2.8.1"
        const val LifeCycleRunTime = "2.6.2"
    }

    object Kotlin {
        const val Kotlin = "1.8.10"
    }

    object Java {
        val Java = JavaVersion.VERSION_17
    }

    object Gradle {
        const val Android = "8.1.2"
        const val Kotlin = "1.9.10"
        const val Firebase = "4.3.15"
    }

    object Ui {
        const val Compose = "1.5.3"
        const val ComposeGlide = "2.1.11"
        const val ComposeHiltNavigation = "1.0.0"
        const val Navigation = "2.7.3"
        const val NavigationAnimation = "0.32.0"
        const val Accompanist = "0.32.0"
        const val Material = "1.9.0"
    }

    object Di {
        const val Hilt = "2.48"
        const val JavaInject = "1"
    }

    object Serialization {
        const val Gson = "2.10.1"
        const val Moshi = "1.14.0"
    }

    object Util {
        const val LocalDateTime = "1.6.8"
    }

    object Local {
        const val Room = "2.5.1"
        const val DataStore = "1.0.0"
    }

    object Remote {
        const val Retrofit = "2.9.0"
        const val OkHttp = "4.11.0"
        const val Firebase = "23.1.2"
    }

    object Test {
        const val JUnit = "4.13.2"
        const val Mockito = "5.3.1"
        const val CoroutinesTest = "1.6.4"
    }
}
