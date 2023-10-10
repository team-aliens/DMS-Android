// TODO: Remove once KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "team.aliens.dms_android.core.school"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
