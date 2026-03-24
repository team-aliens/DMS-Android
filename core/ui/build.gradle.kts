// TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.compose.compiler)
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

    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    kotlinOptions {
        jvmTarget = Versions.java.toString()
    }
}

if (providers.gradleProperty("enableComposeCompilerMetrics").isPresent) {
    composeCompiler {
        metricsDestination = layout.buildDirectory.dir("compose_metrics")
        reportsDestination = layout.buildDirectory.dir("compose_metrics")
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

    implementation(libs.coil.compose)

    implementation(libs.threetenbp)

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)
}
