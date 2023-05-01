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

    implementation(Dependencies.Serialization.Moshi)
    kapt(Dependencies.Serialization.MoshiCompiler)
    implementation(Dependencies.Serialization.GsonConverter)

    implementation(Dependencies.Util.LocalDateTime)

    implementation(Dependencies.Di.Hilt)
    implementation(Dependencies.Di.Inject)
    kapt(Dependencies.Di.HiltCompiler)

    implementation(Dependencies.Remote.Retrofit)
    implementation(Dependencies.Remote.OkHttp)

    implementation(Dependencies.Kotlin.Coroutines)

    testImplementation(Dependencies.UnitTest.JUnit)
    testImplementation(Dependencies.UnitTest.Mockito)
    testImplementation(Dependencies.UnitTest.MockitoKotlin)
    testImplementation(Dependencies.UnitTest.MockitoInline)

    testImplementation(Dependencies.Kotlin.CoroutinesTest)
}