object Dependencies {

    object Android {
        const val CoreX = "androidx.core:core-ktx:${Versions.Android.Core}"
        const val ActivityX = "androidx.activity:activity-ktx:${Versions.Android.Activity}"
        const val FragmentX = "androidx.fragment:fragment-ktx:${Versions.Android.Fragment}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Android.AppCompat}"
        const val WorkX = "androidx.work:work-runtime-ktx:${Versions.Android.WorkRuntime}"
        const val LifeCycleViewModelX =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.LifeCycleRunTime}"
        const val LifeCycleCompose =
            "androidx.lifecycle:lifecycle-runtime-compose:${Versions.Android.LifeCycleRunTime}"
    }

    object Ui {
        const val ComposeMaterial = "androidx.compose.material:material:${Versions.Ui.Compose}"
        const val Compose = "androidx.compose.ui:ui:${Versions.Ui.Compose}"
        const val ComposePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.Ui.Compose}"
        const val ComposeActiviy =
            "androidx.activity:activity-compose:${Versions.Android.Activity}"
        const val ComposeTest = "androidx.compose.ui:ui-test-junit4:${Versions.Ui.Compose}"
        const val ComposeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.Ui.Compose}"
        const val ComposeUiUtil = "androidx.compose.ui:ui-util:${Versions.Ui.Compose}"
        const val ComposeNavigation =
            "androidx.navigation:navigation-compose:${Versions.Ui.Navigation}"
        const val ComposeNavigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:${Versions.Ui.NavigationAnimation}"
        const val ComposeGlide = "com.github.skydoves:landscapist-glide:${Versions.Ui.ComposeGlide}"
        const val ComposeHiltNavigation =
            "androidx.hilt:hilt-navigation-compose:${Versions.Ui.ComposeHiltNavigation}"
        const val Pager = "com.google.accompanist:accompanist-pager:${Versions.Ui.Accompanist}"
        const val PagerIndicator =
            "com.google.accompanist:accompanist-pager-indicators:${Versions.Ui.Accompanist}"
        const val NavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.Ui.Accompanist}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val Coil = "io.coil-kt:coil-compose:${Versions.Ui.Coil}"
        const val TedImagePicker =
            "io.github.ParkSangGwon:tedimagepicker:${Versions.Ui.TedImagePicker}"
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}"
        const val JavaInject = "javax.inject:javax.inject:${Versions.Di.JavaInject}"
    }

    object Serialization {
        const val Gson =
            "com.google.code.gson:gson:${Versions.Serialization.Gson}" // todo should remove
        const val GsonConverter = // todo should remove
            "com.squareup.retrofit2:converter-gson:${Versions.Remote.Retrofit}"
        const val Moshi = "com.squareup.moshi:moshi-kotlin:${Versions.Serialization.Moshi}"
        const val MoshiCompiler =
            "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Serialization.Moshi}"
    }

    object Util {
        const val LocalDateTime =// todo 분리
            "org.threeten:threetenbp:${Versions.Util.LocalDateTime}"
    }

    object Local {
        const val Room = "androidx.room:room-ktx:${Versions.Local.Room}"
        const val RoomCompiler = "androidx.room:room-compiler:${Versions.Local.Room}"
        const val RoomRuntime = "androidx.room:room-runtime:${Versions.Local.Room}"
        const val DataStorePreferences =
            "androidx.datastore:datastore-preferences:${Versions.Local.DataStore}"
    }

    object Remote {
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Remote.Retrofit}"
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.Remote.OkHttp}"
        const val OkHttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Remote.OkHttp}"
        const val Firebase = "com.google.firebase:firebase-messaging-ktx:${Versions.Remote.Firebase}"
    }

    object Test {
        const val JUnit = "junit:junit:${Versions.Test.JUnit}"
        const val Mockito = "org.mockito:mockito-core:${Versions.Test.Mockito}"
        const val CoroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.CoroutinesTest}"
    }
}
