plugins {
    id(Plugins.Module.AndroidApplication)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
}

android {

    namespace = "team.aliens.presentation"
    compileSdk = ProjectProperties.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = ProjectProperties.APPLICATION_ID
        minSdk = ProjectProperties.MIN_SDK_VERSION
        targetSdk = ProjectProperties.TARGET_SDK_VERSION
        versionCode = ProjectProperties.VERSION_CODE
        versionName = ProjectProperties.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        compose = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
        kotlinCompilerVersion = ProjectProperties.KOTLIN_VERSION
    }

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
    kotlinOptions {
        jvmTarget = ProjectProperties.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":local_domain"))
    implementation(project(":di"))
    implementation(project(":design-system"))

    implementation(Dependencies.Android.CORE_KTX)
    implementation(Dependencies.Android.ACTIVITY_KTX)
    implementation(Dependencies.Android.APP_COMPAT)
    implementation(Dependencies.Android.MATERIAL)

    implementation(Dependencies.Android.Work)

    implementation(Dependencies.Compose.COMPOSE_ACTIVITY)
    implementation(Dependencies.Compose.COMPOSE_MATERIAL)
    implementation(Dependencies.Compose.COMPOSE_PREVIEW)
    implementation(Dependencies.Compose.COMPOSE_UI)
    implementation(Dependencies.Compose.COMPOSE_NAV)
    implementation(Dependencies.Compose.COMPOSE_ANI_NAV)
    implementation(Dependencies.Compose.COMPOSE_LANDSCAPIST)
    implementation(Dependencies.Compose.COMPOSE_HILT_NAV)
    implementation(Dependencies.Compose.COMPOSE_VIEWBINDING)
    implementation(Dependencies.Compose.COMPOSE_UI_UTIL)

    implementation(Dependencies.Navigation.NAVIGATION_FRAGMENT)
    implementation(Dependencies.Navigation.NAVIGATION_UI)

    implementation(Dependencies.Di.HILT_ANDROID)
    kapt(Dependencies.Di.HILT_ANDROID_COMPILER)

    implementation(Dependencies.Lifecycle.LIVEDATA)
    implementation(Dependencies.Lifecycle.VIEWMODEL)
    implementation(Dependencies.Lifecycle.RUNTIME)

    implementation(Dependencies.Ui.TEDIMAGEPICKER)

    //TODO: 추후에 커스텀으로 만들 예정입니다.
    implementation(Dependencies.PinEntryEditText.PINENTRYEDITTEXT)

    implementation(Dependencies.UnitTest.JUNIT)

    implementation(Dependencies.Compose.VIEWPAGER)
    implementation(Dependencies.Compose.VIEWPAGERINDICATE)
    implementation(Dependencies.Ui.CIRCLECOMPOSEIMAGE)
}
