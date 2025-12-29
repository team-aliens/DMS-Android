// TODO: Remove once KTIJ-19369 is fixed
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "team.aliens.dms"
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.TARGET_SDK

        versionCode = ProjectProperties.VERSION_CODE
        versionName = ProjectProperties.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "ENVIRONMENT", "\"dev\"")
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "ENVIRONMENT", "\"prod\"")
        }
    }

    sourceSets {
        getByName("dev") {
            java.srcDirs("src/dev/java", "src/dev/kotlin")
            res.srcDir("src/dev/res")
        }
        getByName("prod") {
            java.srcDirs("src/prod/java", "src/prod/kotlin")
            res.srcDir("src/prod/res")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            splits.abi.isEnable = false
            aaptOptions.cruncherEnabled = false
            splits.density.isEnable = false
            aaptOptions.cruncherEnabled = false
        }
    }

    buildFeatures {
        buildConfig = true
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
    implementation(project(ProjectPaths.Core.NOTIFICATION))
    implementation(project(ProjectPaths.Core.DEVICE))
    implementation(project(ProjectPaths.Core.WIDGET))
    implementation(project(ProjectPaths.Core.ONBOARDING))

    implementation(project(ProjectPaths.DATA))
    implementation(project(ProjectPaths.DATABASE))
    implementation(project(ProjectPaths.FEATURE))
    implementation(project(ProjectPaths.NETWORK))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose)
    implementation(libs.androidx.compose.util)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.compose.material.window)
    androidTestImplementation(libs.androidx.compose.test.junit)
    implementation(libs.androidx.compose.material)

    add("prodImplementation", libs.androidx.navigation.compose)
    add("prodImplementation", libs.androidx.hilt.navigation.compose)
    add("devImplementation", libs.androidx.navigation3.runtime)
    add("devImplementation", libs.androidx.navigation3.ui)
    add("devImplementation", libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.lifecycle.runtime.compose)

    add("prodImplementation", libs.composeDestinations)
    add("kspProd", libs.composeDestinations.ksp)

    implementation(libs.coil.compose)

    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    implementation(libs.material)

    implementation(libs.javax.inject)
//    coreLibraryDesugaring(libs.desugar.jdk.libs)

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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}
