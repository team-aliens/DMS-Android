object Dependency {

    object GradlePlugin {
        const val GRADLE_ANDROID = "com.android.tools.build:gradle:${ProjectProperties.GRADLE_ANDROID}"
        const val GRADLE_KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${ProjectProperties.GRADLE_KOTLIN}"
        const val GRADLE_HILT = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    }

    object Kotlin {
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${ProjectProperties.KOTLIN_VERSION}"
        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${ProjectProperties.KOTLINX_COROUTINES}"
        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${ProjectProperties.KOTLINX_COROUTINES}"
    }

    object Ui {
        const val SPLASH_SCREEN_API = "androidx.core:core-splashscreen:${Versions.SPLASH_SCREEN_VERSION}"

        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

        const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

        const val DYNAMIC_TOAST = "com.pranavpandey.android:dynamic-toasts:${Versions.DYNAMIC_TOAST}"

    }

    object Coroutine {
        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"
    }

    object Room {
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    }

    object DataStore {
        const val DATASTORE_PREF = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
        const val DATASTORE_PREF_CORE = "androidx.datastore:datastore-preferences-core:${Versions.DATASTORE}"
    }

    object Compose {
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
        const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
        const val COMPOSE_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val COMPOSE_UI_TOOL = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val COMPOSE_NAV = "androidx.navigation:navigation-compose:${Versions.NAV}"
        const val COMPOSE_ANI_NAV = "com.google.accompanist:accompanist-navigation-animation:${Versions.ANI_NAV}"
        const val COMPOSE_LANDSCAPIST = "com.github.skydoves:landscapist-glide:${Versions.LANDSCAPIST}"
        const val COMPOSE_HILT_NAV = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAV}"
    }

    object Hilt {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val INJECT = "javax.inject:javax.inject:1"
    }

    object Moshi {
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val MOSHI_COMPILER = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
    }

    object Retrofit {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_CONVERTER_GSON =
            "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    }

    object CoroutineTest {
        const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES_TEST}"
    }

    object UnitTest {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val MOCKITO = "org.mockito:mockito-core:${Versions.MOCKITO}"
        const val MOCKITO_KOTLIN =
            "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
        const val MOCKITO_INLINE = "org.mockito:mockito-inline:${Versions.MOCKITO_INLINE}"
    }
}