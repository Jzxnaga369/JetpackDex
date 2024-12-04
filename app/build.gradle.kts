plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
//    id("dexguard")
    id ("com.guardsquare.plugin")
}

android {
    namespace = "com.rmldemo.guardsquare.uat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rmldemo.guardsquare.uat"
        minSdk = 24
        targetSdk = 34
        versionCode = 988
        versionName = "9.8.8-JetDex-A11YCallback"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("ecommerdex.jks")
            storePassword = "qwerty123"
            keyAlias = "keycommerce"
            keyPassword = "qwerty123"
        }
    }

    buildTypes {
//        debug {
//            isMinifyEnabled = false
//            isShrinkResources = false
//        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
        }

        applicationVariants.all {
            val variant = this
            outputs.all {
                val variantOutputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
                val appName = "JetpackDex"
                val newName = variantOutputImpl.outputFileName
                    .replace("app", appName)
                    .replace("-release", "-DexGuard-${variant.versionName}")
                variantOutputImpl.outputFileName = newName
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("com.google.firebase:firebase-crashlytics:19.0.1")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.firebase:firebase-messaging:24.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Coil Compose
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    //Dagger - Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Generate QR Code
    implementation("com.google.zxing:core:3.4.0")

    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.3")
    implementation("androidx.camera:camera-lifecycle:1.3.3")
    implementation("androidx.camera:camera-view:1.3.3")
    implementation("com.google.guava:guava:29.0-jre")

    // Maps
    implementation("com.google.maps.android:maps-compose:4.3.3")

    // Exo Player
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    // Permission
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    // Crypto Tink Wondr
    implementation("com.google.crypto.tink:tink-android:1.7.0")

    // Zoloz
    implementation("com.zoloz.android.build:zolozkit:1.4.1.240604171116")
    implementation("com.zoloz.android.build:nearx:1.3.6.240223182256")
    implementation("com.squareup.okio:okio:1.7.0@jar")
    implementation("com.alibaba:fastjson:1.1.68.android")

    // Huawei Location Kit
    implementation("com.huawei.hms:location:6.12.0.300")

    // Google Play Integrity
    implementation("com.google.android.play:integrity:1.4.0")

//    implementation(files("../../DexGuard-9.8.8/lib/dexguard-attestation-client.aar"))
    implementation(files("D:/CD/DexGuard-9.8.8/DexGuard-9.8.8/lib/dexguard-attestation-client.aar"))


}

//dexguard {
//    path = "../../DexGuard-9.8.8/"
//    // We can build the sample with the sample license file
//    // license = '/Users/awand/Development/Projects/DexGuard-9.6.0'
//    configurations {
//        register("debug") {
//            //defaultConfiguration 'dexguard-debug.pro'
//            defaultConfiguration("dexguard-release-aggressive.pro")
//            defaultConfiguration("dexguard-rasp.pro")
//            configuration("dexguard-project.txt")
//            //uploadCrashlyticsMappingFileRelease = false
//        }
//        register("release") {
//            //defaultConfiguration 'dexguard-debug.pro'
//            defaultConfiguration("dexguard-release-aggressive.pro")
//            defaultConfiguration("dexguard-rasp.pro")
//            configuration("dexguard-project.txt")
//            //uploadCrashlyticsMappingFileRelease = false
//        }
//    }
//}

guardsquare {
    defaultAppID.set("ed93ab42-c2f4-4a4b-abcb-5a381f16c06b")
//    noBrowser.set(true)
//    instrumentBuildType.set("release")
//    protectBuildType.set("release")
}