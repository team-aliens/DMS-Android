plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.DAGGER_HILT_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KT_LINT) version Versions.KT_LINT
}

android {
    compileSdk = ProjectProperties.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = ProjectProperties.APPLICATION_ID
        minSdk = ProjectProperties.MIN_SDK_VERSION
        targetSdk = ProjectProperties.TARGET_SDK_VERSION
        versionCode = ProjectProperties.VERSION_CODE
        versionName = ProjectProperties.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
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

    compileOptions {
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
        kotlinCompilerVersion = ProjectProperties.KOTLIN_VERSION
    }

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }
    kotlinOptions {
        jvmTarget = ProjectProperties.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(project(":auth_domain"))
    implementation(project(":feature_domain"))
    implementation(project(":local_domain"))
    implementation(project(":di"))
    implementation(project(":design-system"))

    implementation(Dependency.Ui.CORE_KTX)
    implementation(Dependency.Ui.APP_COMPAT)
    implementation(Dependency.Ui.MATERIAL)
    implementation(Dependency.Ui.CONSTRAINT_LAYOUT)
    implementation(Dependency.Ui.CIRCLEIMAGEVIEW)
    implementation(Dependency.Ui.COIL)

    implementation(Dependency.Compose.COMPOSE_ACTIVITY)
    implementation(Dependency.Compose.COMPOSE_MATERIAL)
    implementation(Dependency.Compose.COMPOSE_PREVIEW)
    implementation(Dependency.Compose.COMPOSE_UI)
    implementation(Dependency.Compose.COMPOSE_NAV)
    implementation(Dependency.Compose.COMPOSE_ANI_NAV)
    implementation(Dependency.Compose.COMPOSE_LANDSCAPIST)
    implementation(Dependency.Compose.COMPOSE_HILT_NAV)

    implementation(Dependency.Hilt.HILT_ANDROID)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    implementation(Dependency.Lifecycle.LIVEDATA)
    implementation(Dependency.Lifecycle.VIEWMODEL)
    implementation(Dependency.Lifecycle.RUNTIME)

    implementation(Dependency.TedImagePicker.TEDIMAGEPICKER)

    implementation(Dependency.ViewModel.VIEWMODEL)
    implementation(Dependency.ViewModel.LIVEDATA)

    implementation(Dependency.UnitTest.JUNIT)

    implementation(Dependency.Compose.VIEWPAGER)
    implementation(Dependency.Compose.VIEWPAGERINDICATE)


}
