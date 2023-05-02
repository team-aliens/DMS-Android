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
        sourceCompatibility = Versions.Java.Java
        targetCompatibility = Versions.Java.Java
    }

    kotlinOptions {
        jvmTarget = Versions.Java.Java.toString()
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependencies.Di.Hilt)
    implementation(Dependencies.Di.JavaInject)
    kapt(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Serialization.Moshi)
    kapt(Dependencies.Serialization.MoshiCompiler)

    implementation(Dependencies.Local.DataStorePreferences)

    implementation(Dependencies.Local.Room)
    implementation(Dependencies.Local.RoomRuntime)
    kapt(Dependencies.Local.RoomCompiler)

    testImplementation(Dependencies.Test.JUnit)
}