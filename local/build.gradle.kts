plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
    id(Plugins.Module.Hilt)
}

android {
    namespace = "team.aliens.local"
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
    compileOptions {
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependencies.Di.HILT_ANDROID)
    implementation(Dependencies.Di.INJECT)
    kapt(Dependencies.Di.HILT_ANDROID_COMPILER)

    implementation(Dependencies.Serialization.MOSHI)
    kapt(Dependencies.Serialization.MOSHI_COMPILER)

    implementation(Dependencies.Local.DATASTORE_PREF)

    implementation(Dependencies.Local.ROOM_KTX)
    implementation(Dependencies.Local.ROOM_RUNTIME)
    kapt(Dependencies.Local.ROOM_COMPILER)

    testImplementation(Dependencies.UnitTest.JUNIT)

    implementation(Dependencies.Kotlin.Coroutines)
}