plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinKapt)
}

android {
    namespace = "team.aliens.local_database"
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

    implementation(Dependencies.Serialization.Gson)

    implementation(Dependencies.Util.LocalDateTime)
    implementation(Dependencies.Serialization.Moshi)
    kapt(Dependencies.Serialization.MoshiCompiler)

    implementation(Dependencies.Local.DataStorePreferences)

    implementation(Dependencies.Local.Room)
    implementation(Dependencies.Local.RoomRuntime)
    kapt(Dependencies.Local.RoomCompiler)

    implementation(Dependencies.Di.Hilt)
    implementation(Dependencies.Di.JavaInject)
    kapt(Dependencies.Di.HiltCompiler)
}