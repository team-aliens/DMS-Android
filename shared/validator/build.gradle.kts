plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ktlint.gradle)
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.junit)
}
