// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    kotlin("kapt") version "1.9.24"

}

buildscript {

    val nav_version by extra("2.5.1")

    dependencies {

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
