buildscript {
    repositories {
        google() // Thêm repository google để lấy plugin
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        //noinspection UseTomlInstead
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.4")
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
