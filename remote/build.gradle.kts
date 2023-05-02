import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
    id(Plugins.Module.Hilt)
}

android {
    namespace = "team.aliens.remote"
    compileSdk = ProjectProperties.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK_VERSION
        targetSdk = ProjectProperties.TARGET_SDK_VERSION

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
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
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

    implementation(Dependencies.Kotlin.Coroutines)
}

fun fetchProperty(
    key: String,
): String {
    return gradleLocalProperties(rootDir).getProperty(key)
}
