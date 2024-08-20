// TODO: Remove once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint.gradle)
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
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

    implementation(project(ProjectPaths.Shared.DATE))
    implementation(project(ProjectPaths.Shared.EXCEPTION))
    implementation(project(ProjectPaths.Shared.MODEL))
    implementation(project(ProjectPaths.Shared.VALIDATOR))

    implementation(project(ProjectPaths.Core.UI))
    implementation(project(ProjectPaths.Core.DESIGN_SYSTEM))
    implementation(project(ProjectPaths.Core.FILE))
    implementation(project(ProjectPaths.Core.NOTIFICATION))

    implementation(project(ProjectPaths.DATA))

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

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)
}
