// TODO: Remove once KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ktlint.gradle)
}

android {
    namespace = "team.aliens.dms.android.core.ui"
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

    implementation(project(ProjectPaths.Core.DESIGN_SYSTEM))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)

    implementation(libs.material)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose)
    implementation(libs.androidx.compose.util)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.compose.test.junit)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.coil.compose)

    implementation(libs.threetenbp)

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)
}
