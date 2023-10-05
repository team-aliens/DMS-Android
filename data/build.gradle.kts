plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinKapt)
}

android {
    namespace = "team.aliens.dms_android.data"
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
        sourceCompatibility = Versions.Java.Java
        targetCompatibility = Versions.Java.Java
    }

    kotlinOptions {
        jvmTarget = Versions.Java.Java.toString()
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.Serialization.Moshi)
    kapt(Dependencies.Serialization.MoshiCompiler)
    implementation(Dependencies.Serialization.GsonConverter)

    implementation(Dependencies.Util.LocalDateTime)

    implementation(Dependencies.Di.Hilt)
    implementation(Dependencies.Di.JavaInject)
    kapt(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Remote.Retrofit)
    implementation(Dependencies.Remote.OkHttp)

    testImplementation(Dependencies.Test.JUnit)
    testImplementation(Dependencies.Test.Mockito)
    testImplementation(Dependencies.Test.CoroutinesTest)
}