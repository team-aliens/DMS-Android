// TODO: Remove once KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "team.aliens.dms_android.core.ui"
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
    implementation(project(":design-system"))

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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
