plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinKapt)
}

android {
    namespace = "team.aliens.local_database"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":local_domain"))

    implementation(Dependencies.Serialization.GSON)

    implementation(Dependencies.Util.LOCALDATETIME)
    implementation(Dependencies.Serialization.MOSHI)
    kapt(Dependencies.Serialization.MOSHI_COMPILER)

    implementation(Dependencies.DataStore.DATASTORE_PREF)
    implementation(Dependencies.DataStore.DATASTORE_PREF_CORE)

    implementation(Dependencies.Room.ROOM_KTX)
    kapt(Dependencies.Room.ROOM_COMPILER)

    implementation(Dependencies.Hilt.HILT_ANDROID)
    implementation(Dependencies.Hilt.INJECT)
    kapt(Dependencies.Hilt.HILT_ANDROID_COMPILER)
}