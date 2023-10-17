// TODO: Remove once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "team.aliens.dms.android.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    kotlinOptions {
        jvmTarget = Versions.java.toString()
    }
}

dependencies {

    implementation(project(ProjectPaths.Core.database))
    implementation(project(ProjectPaths.Core.network))

    implementation(project(ProjectPaths.database))
    implementation(project(ProjectPaths.network))

    implementation(libs.androidx.core)

    implementation(libs.threetenbp)

    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.coroutines)

    implementation(libs.javax.inject)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
