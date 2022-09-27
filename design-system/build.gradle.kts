plugins {
    id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

android {
    compileSdk = ProjectProperties.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK_VERSION
        targetSdk = ProjectProperties.TARGET_SDK_VERSION

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
        kotlinCompilerExtensionVersion = Versions.COMPOSE
        kotlinCompilerVersion = ProjectProperties.KOTLIN_VERSION
    }

    compileOptions {
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = ProjectProperties.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(Dependency.Compose.COMPOSE_ACTIVITY)
    implementation(Dependency.Compose.COMPOSE_MATERIAL)
    implementation(Dependency.Compose.COMPOSE_PREVIEW)
    implementation(Dependency.Compose.COMPOSE_UI)
    implementation(Dependency.Compose.COMPOSE_NAV)
    implementation(Dependency.Compose.COMPOSE_ANI_NAV)
    implementation(Dependency.Compose.COMPOSE_UI_TOOL)

    androidTestImplementation(Dependency.Compose.COMPOSE_TEST)
    debugImplementation(Dependency.Compose.COMPOSE_UI_TOOL)

    implementation(Dependency.Ui.APP_COMPAT)
    implementation(Dependency.Ui.CORE_KTX)
    implementation(Dependency.Ui.FRAGMENT_KTX)
    implementation(Dependency.Ui.CONSTRAINT_LAYOUT)
    implementation(Dependency.Ui.MATERIAL)
    implementation(Dependency.Ui.CORE_KTX)
}