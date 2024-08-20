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
    namespace = "team.aliens.dms.android.database"
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

    implementation(project(ProjectPaths.Shared.DATE))

    implementation(project(ProjectPaths.Core.DATABASE))

    implementation(libs.threetenbp)

    implementation(libs.javax.inject)

    implementation(libs.hilt)
    testImplementation(libs.hilt.testing)
    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    implementation(libs.moshi)
    implementation(libs.moshi.codegen)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso)
}
