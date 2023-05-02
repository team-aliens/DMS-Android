plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinKapt)
}

android {
    namespace = "team.aliens.di"
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
        sourceCompatibility = Versions.Java.Java
        targetCompatibility = Versions.Java.Java
    }
    kotlinOptions {
        jvmTarget = Versions.Java.Java.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":remote"))
    implementation(project(":local"))
    implementation(project(":local_database"))
    implementation(project(":local_domain"))

    implementation(Dependencies.Util.LocalDateTime)

    implementation(Dependencies.Remote.Retrofit)
    implementation(Dependencies.Remote.OkHttp)
    implementation(Dependencies.Remote.OkHttp)
    implementation(Dependencies.Remote.OkHttpLoggingInterceptor)

    implementation(Dependencies.Di.Hilt)
    kapt(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Serialization.Moshi)
    kapt(Dependencies.Serialization.MoshiCompiler)
    implementation(Dependencies.Serialization.GsonConverter)

    implementation(Dependencies.Local.Room)
    implementation(Dependencies.Local.RoomRuntime)
    kapt(Dependencies.Local.RoomCompiler)

    implementation(Dependencies.Local.DataStorePreferences)
}