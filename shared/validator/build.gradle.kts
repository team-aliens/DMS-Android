plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java

    dependencies {
        implementation(libs.junit)
    }
}