plugins {
    id(Plugins.Module.AndroidApplication)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
}

android {

    namespace = "team.aliens.presentation"
    compileSdk = ProjectProperties.CompileSdkVersion

    defaultConfig {
        applicationId = ProjectProperties.ApplicationId
        minSdk = ProjectProperties.MinSdkVersion
        targetSdk = ProjectProperties.TargetSdkVersion
        versionCode = ProjectProperties.VersionCode
        versionName = ProjectProperties.VersionName

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
        kotlinCompilerExtensionVersion = Versions.Ui.Compose
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

    implementation(Dependencies.Android.CoreX)
    implementation(Dependencies.Android.ActivityX)
    implementation(Dependencies.Android.AppCompat)
    implementation(Dependencies.Android.WorkX)
    implementation(Dependencies.Android.LifeCycleViewModelX)

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
    implementation(Dependencies.Ui.PagerIndicator)
    implementation(Dependencies.Ui.TedImagePicker)
    implementation(Dependencies.Ui.Coil)

    implementation(Dependencies.Di.Hilt)
    kapt(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Test.JUnit)
}
