// TODO: Remove once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "team.aliens.dms.android.feature"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Ui.Compose
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":design-system"))
    implementation(project(":domain"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.compose)
    implementation(libs.androidx.compose.util)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.tooling.preview)
    androidTestImplementation(libs.androidx.compose.test.junit)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.coil.compose)

    implementation(libs.material)

    implementation(libs.javax.inject)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.threetenbp)

    implementation(libs.composeDestinations)
    ksp(libs.composeDestinations.ksp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
