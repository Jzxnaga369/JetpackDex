// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}

buildscript {
    repositories {
//        maven { url=uri("../DexGuard-9.8.8/lib") }
    maven {
        url = uri("https://maven.guardsquare.com")
    }
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.1")
//        classpath("com.guardsquare:dexguard-gradle-plugin:9.8.8")
        // Add the AppGallery Connect plugin configuration. Please refer to AppGallery Connect Plugin Dependency to select a proper plugin version.
        classpath("com.guardsquare:plugin:+")
        classpath("com.huawei.agconnect:agcp:1.6.0.300")
    }
}