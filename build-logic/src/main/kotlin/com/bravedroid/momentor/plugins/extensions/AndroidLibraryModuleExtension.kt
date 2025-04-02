package com.bravedroid.momentor.plugins.extensions
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.android.build.gradle.LibraryExtension
import com.bravedroid.momentor.plugins.extensions.dsl.alias
import com.bravedroid.momentor.plugins.extensions.dsl.coreLibraryDesugaring
import com.bravedroid.momentor.plugins.extensions.dsl.libs
import com.bravedroid.momentor.plugins.extensions.dsl.plugins
import org.gradle.kotlin.dsl.configure

import com.bravedroid.momentor.plugins.config.BuildConfig
import com.bravedroid.momentor.plugins.config.configureKotlinCompilerOptions

fun Project.setupAndroidLibraryModule() {
    plugins {
        alias(libs.plugins.android.library)
        alias(libs.plugins.kotlin.android)
    }

    extensions.configure<LibraryExtension> {
        compileSdk = BuildConfig.COMPILE_SDK
        namespace = "com.bravedroid.${path.replace(":", ".").removePrefix(".")}".replace("-", ".")

        defaultConfig {
            minSdk = BuildConfig.MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }

        lint.targetSdk = BuildConfig.TARGET_SDK

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
    }

    configureKotlinCompilerOptions()

    dependencies {
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
}

