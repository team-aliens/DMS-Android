plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.DAGGER_HILT_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id("org.jetbrains.kotlin.android")
}

android {
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
    implementation(project(":auth_domain"))
    implementation(project(":feature_domain"))
    implementation(project(":local_domain"))
    implementation(project(":di"))
    implementation(project(":design-system"))

    implementation(Dependency.Ui.CORE_KTX)
    implementation(Dependency.Ui.APP_COMPAT)
    implementation(Dependency.Ui.MATERIAL)
    implementation(Dependency.Ui.CONSTRAINT_LAYOUT)
    implementation(Dependency.Ui.CIRCLE_IMAGE_VIEW)
    implementation(Dependency.Ui.COIL)
    implementation(Dependency.Ui.GLIDE_CORE)
    annotationProcessor(Dependency.Ui.GLIDE_COMPILER)

    implementation(Dependency.Compose.COMPOSE_ACTIVITY)
    implementation(Dependency.Compose.COMPOSE_MATERIAL)
    implementation(Dependency.Compose.COMPOSE_PREVIEW)
    implementation(Dependency.Compose.COMPOSE_UI)
    implementation(Dependency.Compose.COMPOSE_NAV)
    implementation(Dependency.Compose.COMPOSE_ANI_NAV)
    implementation(Dependency.Compose.COMPOSE_LANDSCAPIST)
    implementation(Dependency.Compose.COMPOSE_HILT_NAV)
    implementation(Dependency.Compose.COMPOSE_VIEWBINDING)
    implementation("androidx.compose.ui:ui-util:${Versions.COMPOSE}")

    implementation(Dependency.Navigation.NAVIGATION_FRAGMENT)
    implementation(Dependency.Navigation.NAVIGATION_UI)

    implementation(Dependency.Hilt.HILT_ANDROID)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    implementation(Dependency.Ui.FRAGMENT_KTX_NEW)

    implementation(Dependency.Lifecycle.LIVEDATA)
    implementation(Dependency.Lifecycle.VIEWMODEL)
    implementation(Dependency.Lifecycle.RUNTIME)

    implementation(Dependency.TedImagePicker.TEDIMAGEPICKER)

    //TODO: 추후에 커스텀으로 만들 예정입니다.
    implementation(Dependency.PinEntryEditText.PINENTRYEDITTEXT)

    implementation(Dependency.ViewModel.VIEWMODEL)
    implementation(Dependency.ViewModel.LIVEDATA)

    implementation(Dependency.UnitTest.JUNIT)

    implementation(Dependency.Compose.VIEWPAGER)
    implementation(Dependency.Compose.VIEWPAGERINDICATE)
}
