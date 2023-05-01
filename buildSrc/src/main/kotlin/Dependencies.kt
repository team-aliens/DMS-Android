object Dependencies {

    object Android {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val Work = "androidx.work:work-runtime-ktx:${Versions.WORK_MANAGER}"
    }

    object Kotlin {
        const val Coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${ProjectProperties.KOTLINX_COROUTINES}"
    }

    object Ui {
        const val CIRCLECOMPOSEIMAGE = "io.coil-kt:coil-compose:${Versions.COIL}"
        const val TEDIMAGEPICKER =
            "io.github.ParkSangGwon:tedimagepicker:${Versions.TEDIMAGEPICKER}"
    }

    object Serialization {
        const val GSON = "com.google.code.gson:gson:${Versions.GSON}" // todo should remove
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val MOSHI_COMPILER = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
    }

    object Util {
        const val LOCALDATETIME =//todo 분리
            "com.jakewharton.threetenabp:threetenabp:${Versions.LOCALDATETIME}"
    }

    object Lifecycle {
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
        const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
        const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    }

    object Local {
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    }

    object Remote {

    }

    object DataStore {
        const val DATASTORE_PREF = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
        const val DATASTORE_PREF_CORE =
            "androidx.datastore:datastore-preferences-core:${Versions.DATASTORE}"
    }

    object Compose {
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
        const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val COMPOSE_ACTIVITY =
            "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
        const val COMPOSE_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val COMPOSE_UI_TOOL = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val COMPOSE_NAV = "androidx.navigation:navigation-compose:${Versions.NAV}"
        const val COMPOSE_ANI_NAV =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.ANI_NAV}"
        const val COMPOSE_LANDSCAPIST =
            "com.github.skydoves:landscapist-glide:${Versions.LANDSCAPIST}"
        const val COMPOSE_HILT_NAV = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAV}"
        const val VIEWPAGER = "com.google.accompanist:accompanist-pager:${Versions.VIEWPAGER}"
        const val VIEWPAGERINDICATE =
            "com.google.accompanist:accompanist-pager-indicators:${Versions.VIEWPAGER}"
        const val COMPOSE_VIEWBINDING = "androidx.compose.ui:ui-viewbinding:${Versions.COMPOSE}"
        const val COMPOSE_UI_UTIL = "androidx.compose.ui:ui-util:${Versions.COMPOSE}"
    }

    object Hilt {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val HILT_WORK = "androidx.hilt:hilt-work:${Versions.HILT_WORK}"
        const val INJECT = "javax.inject:javax.inject:1"
    }

    object Retrofit {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_CONVERTER_GSON =
            "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
        const val OKHTTP_LOGGING = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val LOGINTERCEPTER = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    }

    object Navigation {
        const val NAVIGATION_FRAGMENT =
            "androidx.navigation:navigation-fragment-ktx:${Versions.NAV}"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAV}"
    }

    object CoroutineTest {
        const val COROUTINES_TEST =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES_TEST}"
    }

    //TODO: 추후에 커스텀으로 만들 예정입니다.
    object PinEntryEditText {
        const val PINENTRYEDITTEXT =
            "com.alimuzaffar.lib:pinentryedittext:${Versions.PINENTRYEDITTEXT}"
    }

    object UnitTest {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val MOCKITO = "org.mockito:mockito-core:${Versions.MOCKITO}"
        const val MOCKITO_KOTLIN =
            "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
        const val MOCKITO_INLINE = "org.mockito:mockito-inline:${Versions.MOCKITO_INLINE}"
    }
}
