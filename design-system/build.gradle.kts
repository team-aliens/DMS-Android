plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.KotlinKapt)
}

android {

    namespace = "team.aliens.design_system"
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
    implementation(Dependencies.Compose.COMPOSE_ACTIVITY)
    implementation(Dependencies.Compose.COMPOSE_MATERIAL)
    implementation(Dependencies.Compose.COMPOSE_PREVIEW)
    implementation(Dependencies.Compose.COMPOSE_UI)
    implementation(Dependencies.Compose.COMPOSE_NAV)
    implementation(Dependencies.Compose.COMPOSE_ANI_NAV)
    implementation(Dependencies.Compose.COMPOSE_UI_TOOL)
    implementation(Dependencies.Compose.COMPOSE_LANDSCAPIST)

    androidTestImplementation(Dependencies.Compose.COMPOSE_TEST)
    debugImplementation(Dependencies.Compose.COMPOSE_UI_TOOL)

    implementation(Dependencies.Ui.APP_COMPAT)
    implementation(Dependencies.Ui.CORE_KTX)
    implementation(Dependencies.Ui.FRAGMENT_KTX)
    implementation(Dependencies.Ui.CONSTRAINT_LAYOUT)
    implementation(Dependencies.Ui.MATERIAL)
    implementation(Dependencies.Ui.CORE_KTX)

    implementation(Dependencies.UnitTest.JUNIT)
}