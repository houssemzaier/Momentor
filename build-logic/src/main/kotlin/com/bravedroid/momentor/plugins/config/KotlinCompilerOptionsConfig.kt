package com.bravedroid.momentor.plugins.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

fun Project.configureKotlinCompilerOptions() {
    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(BuildConfig.jvmTarget)
            freeCompilerArgs.addAll(BuildConfig.optIns.map { "-opt-in=$it" })
        }
    }
}
