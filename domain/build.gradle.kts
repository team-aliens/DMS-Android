plugins {
    id(BuildPlugins.AndroidLibrary)
    id(BuildPlugins.KotlinAndroid)
    id(BuildPlugins.Hilt)
    id(BuildPlugins.KotlinKapt)
}

android {

    namespace = "team.aliens.domain"
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

    kotlinOptions {
        jvmTarget = ProjectProperties.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(Dependency.UnitTest.JUNIT)

    implementation(Dependency.Ui.LOCALDATETIME)

    implementation(Dependency.Hilt.HILT_ANDROID)
    implementation(Dependency.Hilt.INJECT)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    implementation(Dependency.Kotlin.COROUTINES_CORE)
    implementation(Dependency.Kotlin.COROUTINES_ANDROID)
}