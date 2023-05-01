object Dependencies {

    object Android {
        const val Core = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val Activity = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val Work = "androidx.work:work-runtime-ktx:${Versions.WORK_MANAGER}"
    }

    object Kotlin {
        const val Coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${ProjectProperties.KOTLINX_COROUTINES}"
        const val CoroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES_TEST}"
    }

    object Ui {
        const val ComposeMaterial = "androidx.compose.material:material:${Versions.COMPOSE}"
        const val Compose = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val ComposePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val ComposeActiviy = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
        const val ComposeTest = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val ComposeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val ComposeUiUtil = "androidx.compose.ui:ui-util:${Versions.COMPOSE}"
        const val ComposeNavigation = "androidx.navigation:navigation-compose:${Versions.NAV}"
        const val ComposeNavigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.ANI_NAV}"
        const val ComposeGlide = "com.github.skydoves:landscapist-glide:${Versions.LANDSCAPIST}"
        const val ComposeHiltNavigation =
            "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAV}"
        const val ViewPager = "com.google.accompanist:accompanist-pager:${Versions.VIEWPAGER}"
        const val ViewPagerIndicator =
            "com.google.accompanist:accompanist-pager-indicators:${Versions.VIEWPAGER}"

        const val Material = "com.google.android.material:material:${Versions.MATERIAL}"
        const val Coil = "io.coil-kt:coil-compose:${Versions.COIL}"
        const val TedImagePicker =
            "io.github.ParkSangGwon:tedimagepicker:${Versions.TEDIMAGEPICKER}"
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val Inject = "javax.inject:javax.inject:1"
    }

    object Serialization {
        const val Gson = "com.google.code.gson:gson:${Versions.GSON}" // todo should remove
        const val GsonConverter = // todo should remove
            "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
        const val Moshi = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val MoshiCompiler = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
    }

    object Util {
        const val LocalDateTime =// todo 분리
            "com.jakewharton.threetenabp:threetenabp:${Versions.LOCALDATETIME}"
    }

    object Lifecycle {
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
        const val Runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    }

    object Local {
        const val Room = "androidx.room:room-ktx:${Versions.ROOM}"
        const val RoomCompiler = "androidx.room:room-compiler:${Versions.ROOM}"
        const val RoomRuntime = "androidx.room:room-runtime:${Versions.ROOM}"
        const val DataStorePreferences =
            "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
    }

    object Remote {
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OkHttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    }

    object UnitTest {
        const val JUnit = "junit:junit:${Versions.JUNIT}"
        const val Mockito = "org.mockito:mockito-core:${Versions.MOCKITO}"
        const val MockitoKotlin =
            "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
        const val MockitoInline = "org.mockito:mockito-inline:${Versions.MOCKITO_INLINE}"
    }
}
