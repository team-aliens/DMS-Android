// TODO: Remove once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    // TODO: replace to android library later
    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)

    // TODO: remove later
    alias(libs.plugins.googleServices)
}

android {
    namespace = "team.aliens.dms_android.feature"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "team.aliens.dms_android"

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = 1
        versionName = "1.1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Ui.Compose
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":design-system"))
    implementation(project(":domain"))

    // TODO: remove project implementation
    implementation(project(":di"))

    implementation(Dependencies.Android.CoreX)
    implementation(Dependencies.Android.ActivityX)
    implementation(Dependencies.Android.FragmentX)
    implementation(Dependencies.Android.AppCompat)
    implementation(Dependencies.Android.WorkX)
    implementation(Dependencies.Android.LifeCycleViewModelX)
    implementation(Dependencies.Android.LifeCycleCompose)

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
    implementation(Dependencies.Ui.Pager)
    implementation(Dependencies.Ui.NavigationAnimation)
    implementation(Dependencies.Ui.PagerIndicator)

    implementation(Dependencies.Di.Hilt)
    ksp(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Test.JUnit)

    implementation(Dependencies.Remote.FirebaseMessaging)

    implementation(libs.composeDestinations)
    ksp(libs.composeDestinations.ksp)
}
