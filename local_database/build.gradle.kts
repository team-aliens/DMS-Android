plugins {
    id(BuildPlugins.AndroidLibrary)
    id(BuildPlugins.KotlinAndroid)
    id(BuildPlugins.Hilt)
    id(BuildPlugins.KotlinKapt)
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

    implementation(Dependency.GSON.GSON)

    implementation(Dependency.Ui.LOCALDATETIME)
    implementation(Dependency.Moshi.MOSHI)
    kapt(Dependency.Moshi.MOSHI_COMPILER)

    implementation(Dependency.DataStore.DATASTORE_PREF)
    implementation(Dependency.DataStore.DATASTORE_PREF_CORE)

    implementation(Dependency.Room.ROOM_KTX)
    kapt(Dependency.Room.ROOM_COMPILER)

    implementation(Dependency.Hilt.HILT_ANDROID)
    implementation(Dependency.Hilt.INJECT)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)
}