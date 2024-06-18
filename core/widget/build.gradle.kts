plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "team.aliens.dms.android.core.widget"
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
                "proguard-rules.pro"
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

    implementation(project(ProjectPaths.DATA))

    implementation(project(ProjectPaths.Shared.DATE))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

    implementation(libs.androidx.compose)
    implementation(libs.androidx.compose.material)

    implementation("androidx.glance:glance-appwidget:1.1.0")
    implementation("androidx.glance:glance-material:1.1.0")
    implementation("androidx.glance:glance-material3:1.1.0")

    implementation("androidx.work:work-runtime-ktx:2.9.0")
}
