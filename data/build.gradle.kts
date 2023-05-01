plugins {
    id(Plugins.Module.AndroidLibrary)
    id(Plugins.Module.KotlinAndroid)
    id(Plugins.Module.Hilt)
    id(Plugins.Module.KotlinKapt)
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

    compileOptions {
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = ProjectProperties.JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":local_database"))
    implementation(Dependencies.Serialization.MOSHI)
    kapt(Dependencies.Serialization.MOSHI_COMPILER)

    implementation(Dependencies.Util.LOCALDATETIME)

    implementation(Dependencies.Di.HILT_ANDROID)
    implementation(Dependencies.Di.INJECT)
    kapt(Dependencies.Di.HILT_ANDROID_COMPILER)

    implementation(Dependencies.Remote.RETROFIT)
    implementation(Dependencies.Remote.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.Remote.OKHTTP_LOGGING)

    implementation(Dependencies.Kotlin.Coroutines)

    testImplementation(Dependencies.UnitTest.JUNIT)
    testImplementation(Dependencies.UnitTest.MOCKITO)
    testImplementation(Dependencies.UnitTest.MOCKITO_KOTLIN)
    testImplementation(Dependencies.UnitTest.MOCKITO_INLINE)

    testImplementation(Dependencies.Kotlin.COROUTINES_TEST)
}