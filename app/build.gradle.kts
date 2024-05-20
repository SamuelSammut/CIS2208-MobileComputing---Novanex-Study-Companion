import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.novanex_studycompanion"
    compileSdk = 34

    packaging {
        resources {
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "kotlin/reflect/reflect.kotlin_builtins",
                "kotlin/collections/collections.kotlin_builtins",
                "META-INF/kotlin-stdlib-common.kotlin_module",
                "META-INF/kotlin-stdlib-jdk7.kotlin_module",
                "META-INF/kotlin-stdlib-jdk8.kotlin_module",
                "META-INF/kotlin-stdlib.kotlin_module",
                "kotlin/annotation/annotation.kotlin_builtins",
                "kotlin/annotation/*",
                "kotlin/collections/collections.kotlin_builtins",
                "kotlin/coroutines/coroutines.kotlin_builtins",
                "kotlin/coroutines/*",
                "kotlin/internal/internal.kotlin_builtins",
                "kotlin/kotlin.kotlin_builtins",
                "kotlin/ranges/ranges.kotlin_builtins",
                "kotlin/reflect/reflect.kotlin_builtins",
                "META-INF/*.kotlin_module",
                "META-INF/*",
                "DebugProbesKt.bin"
            )
        }
    }


    defaultConfig {
        applicationId = "com.example.novanex_studycompanion"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        dataBinding = true
    }
}
//configurations.all {
//    resolutionStrategy {
//        force("androidx.core:core-ktx:1.10.1")
//        force("androidx.appcompat:appcompat:1.6.1")
//        force("com.google.android.material:material:1.12.0")
//        force("androidx.constraintlayout:constraintlayout:2.1.4")
////        force("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
////        force("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
//        force("androidx.navigation:navigation-fragment-ktx:2.5.1")
//        force("androidx.navigation:navigation-ui-ktx:2.5.1")
//        force("androidx.room:room-common:2.6.1")
//        force("androidx.room:room-runtime:2.6.1")
//        force("androidx.room:room-ktx:2.6.1")
//        force("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
//        force("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
//        force("org.jetbrains:annotations:23.0.0")
//        force("androidx.annotation:annotation:1.8.0")
//    }
//}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.android)
//
    // Exclude problematic modules globally
//    implementation(libs.compose.preview.renderer) {
//        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
//        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
//        exclude(group = "org.jetbrains", module = "annotations")
//    }
////
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
