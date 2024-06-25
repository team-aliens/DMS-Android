// TODO: Remove once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "team.aliens.dms.android.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = 15
        versionName = "1.3.6"

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
        buildConfig = true
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
    implementation(project(ProjectPaths.Shared.MODEL))

    implementation(project(ProjectPaths.Core.DATABASE))
    implementation(project(ProjectPaths.Core.DATASTORE))
    implementation(project(ProjectPaths.Core.DESIGN_SYSTEM))
    implementation(project(ProjectPaths.Core.JWT))
    implementation(project(ProjectPaths.Core.NETWORK))
    implementation(project(ProjectPaths.Core.PROJECT))
    implementation(project(ProjectPaths.Core.SCHOOL))
    implementation(project(ProjectPaths.Core.UI))
    implementation(project(ProjectPaths.Core.WIDGET))

    implementation(project(ProjectPaths.DATA))
    implementation(project(ProjectPaths.DATABASE))
    implementation(project(ProjectPaths.FEATURE))
    implementation(project(ProjectPaths.NETWORK))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.window)
    implementation(libs.androidx.activity.compose)

    implementation(libs.accompanist.adaptive)

    implementation(libs.androidx.compose)
    implementation(libs.androidx.compose.util)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.compose.material.window)
    androidTestImplementation(libs.androidx.compose.test.junit)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.composeDestinations)
    ksp(libs.composeDestinations.ksp)

    implementation(libs.coil.compose)

    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    implementation(libs.material)

    implementation(libs.javax.inject)

    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.threetenbp)

    implementation(libs.hilt)
    testImplementation(libs.hilt.testing)
    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)

    implementation(libs.app.update)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}
