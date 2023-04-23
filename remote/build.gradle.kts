import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.DAGGER_HILT_PLUGIN)
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

    implementation(Dependency.Hilt.HILT_ANDROID)
    implementation(Dependency.Hilt.INJECT)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    implementation(Dependency.Retrofit.RETROFIT)
    implementation(Dependency.Retrofit.RETROFIT_CONVERTER_GSON)

    testImplementation(Dependency.UnitTest.JUNIT)

    implementation(Dependency.Kotlin.COROUTINES_CORE)
    implementation(Dependency.Kotlin.COROUTINES_ANDROID)
}

fun fetchProperty(
    key: String,
): String {
    return gradleLocalProperties(rootDir).getProperty(key)
}
