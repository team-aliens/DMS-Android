plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinKapt)
}

android {

    namespace = "team.aliens.dms_android.domain"
    compileSdk = ProjectProperties.CompileSdkVersion

    defaultConfig {
        minSdk = ProjectProperties.MinSdkVersion
        targetSdk = ProjectProperties.TargetSdkVersion

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
        jvmTarget = Versions.Java.Java.toString()
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(Dependencies.Test.JUnit)

    implementation(Dependencies.Util.LocalDateTime)

    implementation(Dependencies.Di.Hilt)
    implementation(Dependencies.Di.JavaInject)
    kapt(Dependencies.Di.HiltCompiler)
}
