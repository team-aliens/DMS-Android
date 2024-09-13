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
    namespace = "team.aliens.dms.android.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "team.aliens.dms.android.network.auth.NetworkTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // TODO: move to :app
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = localProperty("PROD_BASE_URL"),
            )

            buildConfigField(
                type = "String",
                name = "TERMS_URL",
                value = localProperty("TERMS_URL"),
            )
        }

        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = localProperty("DEV_BASE_URL"),
            )
            buildConfigField(
                type = "String",
                name = "TERMS_URL",
                value = localProperty("TERMS_URL"),
            )
        }
    }

    buildFeatures {
        buildConfig = true
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

    implementation(project(ProjectPaths.Shared.MODEL))
    implementation(project(ProjectPaths.Shared.DATE))

    implementation(project(ProjectPaths.Core.JWT))
    implementation(project(ProjectPaths.Core.NETWORK))
    implementation(project(ProjectPaths.Core.SCHOOL))

    implementation(libs.androidx.core)

    implementation(libs.threetenbp)

    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // TODO: remove dependency
    implementation(libs.coroutines.android)

    implementation(libs.javax.inject)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso)
}

// TODO: 선언 위치 옮기기
fun localProperty(key: String) =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty(key)
