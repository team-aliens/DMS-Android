plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = Versions.java
    targetCompatibility = Versions.java

    sourceSets {
        dependencies {
            implementation(libs.coroutines)
        }
    }
}
