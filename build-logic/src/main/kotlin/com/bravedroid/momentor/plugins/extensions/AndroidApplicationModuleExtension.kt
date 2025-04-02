package com.bravedroid.momentor.plugins.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.bravedroid.momentor.plugins.config.BuildConfig
import com.bravedroid.momentor.plugins.config.configureKotlinCompilerOptions
import com.bravedroid.momentor.plugins.extensions.dsl.alias
import com.bravedroid.momentor.plugins.extensions.dsl.coreLibraryDesugaring
import com.bravedroid.momentor.plugins.extensions.dsl.libs
import com.bravedroid.momentor.plugins.extensions.dsl.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

fun Project.setupAndroidApplicationModule() {
    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.kotlin.android)
    }

    extensions.configure<ApplicationExtension> {
        compileSdk = BuildConfig.COMPILE_SDK

        defaultConfig {
            minSdk = BuildConfig.MIN_SDK
            targetSdk = BuildConfig.TARGET_SDK
            versionCode = 1
            versionName = "1.0"
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        lint.targetSdk = BuildConfig.TARGET_SDK

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = BuildConfig.javaVersion
            targetCompatibility = BuildConfig.javaVersion
        }



        buildFeatures {
            compose = true
        }
    }

    configureKotlinCompilerOptions()

    dependencies {
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
}
