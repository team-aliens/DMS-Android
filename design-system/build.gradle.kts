plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
}

android {

    namespace = "team.aliens.design_system"
    compileSdk = ProjectProperties.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK_VERSION
        targetSdk = ProjectProperties.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Ui.COMPOSE
        kotlinCompilerVersion = Versions.Kotlin.Kotlin
    }

    compileOptions {
        sourceCompatibility = Versions.Java.Java
        targetCompatibility = Versions.Java.Java
    }

    kotlinOptions {
        jvmTarget = Versions.Java.Java.toString()
    }
}

dependencies {
    implementation(Dependencies.Ui.Compose)
    implementation(Dependencies.Ui.ComposeMaterial)
    implementation(Dependencies.Ui.ComposePreview)
    implementation(Dependencies.Ui.ComposeActiviy)
    implementation(Dependencies.Ui.ComposeNavigation)
    implementation(Dependencies.Ui.ComposeNavigationAnimation)
    implementation(Dependencies.Ui.ComposeUiTooling)
    implementation(Dependencies.Ui.ComposeGlide)
    androidTestImplementation(Dependencies.Ui.ComposeTest)
    debugImplementation(Dependencies.Ui.ComposeUiTooling)

    implementation(Dependencies.Test.JUnit)
}