import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
    id(Plugins.Module.Hilt)
}

android {
    namespace = "team.aliens.remote"
    compileSdk = ProjectProperties.CompileSdkVersion

    defaultConfig {
        minSdk = ProjectProperties.MinSdkVersion
        targetSdk = ProjectProperties.TargetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            type = "String",
            name = "PROD_BASE_URL",
            value = fetchProperty("PROD_BASE_URL"),
        )

        buildConfigField(
            type = "String",
            name = "DEV_BASE_URL",
            value = fetchProperty("DEV_BASE_URL"),
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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

    implementation(Dependencies.Remote.Retrofit)

    implementation(Dependencies.Serialization.GsonConverter)

    testImplementation(Dependencies.Test.JUnit)
}

fun fetchProperty(
    key: String,
): String {
    return gradleLocalProperties(rootDir).getProperty(key)
}
