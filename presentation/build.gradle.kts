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
        sourceCompatibility = Versions.Java.Java
        targetCompatibility = Versions.Java.Java
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Ui.COMPOSE
        kotlinCompilerVersion = Versions.Kotlin.Kotlin
    }

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
    kotlinOptions {
        jvmTarget = Versions.Java.Java.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":local_domain"))
    implementation(project(":di"))
    implementation(project(":design-system"))

    implementation(Dependencies.Android.Core)
    implementation(Dependencies.Android.Activity)
    implementation(Dependencies.Android.AppCompat)
    implementation(Dependencies.Android.Work)

    implementation(Dependencies.Ui.Material)
    implementation(Dependencies.Ui.Compose)
    implementation(Dependencies.Ui.ComposeMaterial)
    implementation(Dependencies.Ui.ComposePreview)
    implementation(Dependencies.Ui.ComposeActiviy)
    implementation(Dependencies.Ui.ComposeNavigation)
    implementation(Dependencies.Ui.ComposeNavigationAnimation)
    implementation(Dependencies.Ui.ComposeGlide)
    implementation(Dependencies.Ui.ComposeHiltNavigation)
    implementation(Dependencies.Ui.ComposeUiUtil)
    implementation(Dependencies.Ui.ViewPager)
    implementation(Dependencies.Ui.ViewPagerIndicator)
    implementation(Dependencies.Ui.TedImagePicker)
    implementation(Dependencies.Ui.Coil)

    implementation(Dependencies.Di.Hilt)
    kapt(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Lifecycle.ViewModel)

    implementation(Dependencies.Test.JUnit)
}
