object Dependencies {

    object Android {
        const val Core = "androidx.core:core-ktx:${Versions.Android.Core}"
        const val Activity = "androidx.activity:activity-ktx:${Versions.Android.Activity}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Android.AppCompat}"
        const val Work = "androidx.work:work-runtime-ktx:${Versions.Android.Work}"
    }

    object Kotlin {
        const val Coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.Coroutines}"
    }

    object Ui {
        const val ComposeMaterial = "androidx.compose.material:material:${Versions.Ui.COMPOSE}"
        const val Compose = "androidx.compose.ui:ui:${Versions.Ui.COMPOSE}"
        const val ComposePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.Ui.COMPOSE}"
        const val ComposeActiviy =
            "androidx.activity:activity-compose:${Versions.Ui.ACTIVITY_COMPOSE}"
        const val ComposeTest = "androidx.compose.ui:ui-test-junit4:${Versions.Ui.COMPOSE}"
        const val ComposeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.Ui.COMPOSE}"
        const val ComposeUiUtil = "androidx.compose.ui:ui-util:${Versions.Ui.COMPOSE}"
        const val ComposeNavigation = "androidx.navigation:navigation-compose:${Versions.Ui.NAV}"
        const val ComposeNavigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.Ui.ANI_NAV}"
        const val ComposeGlide = "com.github.skydoves:landscapist-glide:${Versions.Ui.LANDSCAPIST}"
        const val ComposeHiltNavigation =
            "androidx.hilt:hilt-navigation-compose:${Versions.Ui.HILT_NAV}"
        const val ViewPager = "com.google.accompanist:accompanist-pager:${Versions.Ui.VIEWPAGER}"
        const val ViewPagerIndicator =
            "com.google.accompanist:accompanist-pager-indicators:${Versions.Ui.VIEWPAGER}"

        const val Material = "com.google.android.material:material:${Versions.Ui.MATERIAL}"
        const val Coil = "io.coil-kt:coil-compose:${Versions.Ui.COIL}"
        const val TedImagePicker =
            "io.github.ParkSangGwon:tedimagepicker:${Versions.Ui.TEDIMAGEPICKER}"
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Di.HILT}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Di.HILT}"
        const val JavaInject = "javax.inject:javax.inject:${Versions.Di.JavaInject}"
    }

    object Serialization {
        const val Gson =
            "com.google.code.gson:gson:${Versions.Serialization.GSON}" // todo should remove
        const val GsonConverter = // todo should remove
            "com.squareup.retrofit2:converter-gson:${Versions.Remote.RETROFIT}"
        const val Moshi = "com.squareup.moshi:moshi-kotlin:${Versions.Serialization.MOSHI}"
        const val MoshiCompiler =
            "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Serialization.MOSHI}"
    }

    object Util {
        const val LocalDateTime =// todo 분리
            "com.jakewharton.threetenabp:threetenabp:${Versions.Util.LOCALDATETIME}"
    }

    object Lifecycle {
        const val ViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LifeCycle.LIFECYCLE}"
        const val Runtime =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LifeCycle.LIFECYCLE}"
    }

    object Local {
        const val Room = "androidx.room:room-ktx:${Versions.Local.ROOM}"
        const val RoomCompiler = "androidx.room:room-compiler:${Versions.Local.ROOM}"
        const val RoomRuntime = "androidx.room:room-runtime:${Versions.Local.ROOM}"
        const val DataStorePreferences =
            "androidx.datastore:datastore-preferences:${Versions.Local.DATASTORE}"
    }

    object Remote {
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Remote.RETROFIT}"
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.Remote.OKHTTP}"
        const val OkHttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Remote.OKHTTP}"
    }

    object Test {
        const val JUnit = "junit:junit:${Versions.Test.JUNIT}"
        const val Mockito = "org.mockito:mockito-core:${Versions.Test.MOCKITO}"
        const val CoroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.COROUTINES_TEST}"
    }
}
