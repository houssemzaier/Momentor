package com.bravedroid.momentor.plugins.config

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object BuildConfig {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 24
    const val TARGET_SDK = 35

    val javaVersion = JavaVersion.VERSION_17
    val jvmTarget = JvmTarget.JVM_17

    const val MOMENTOR_APPLICATION_ID = "com.bravedroid.momentor"
    const val MOMENTOR_NAMESPACE = "com.bravedroid.momentor.app"

    val optIns = listOf(
        "androidx.compose.material3.ExperimentalMaterial3Api",
        "androidx.compose.foundation.ExperimentalFoundationApi",
        "androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
        "androidx.compose.animation.ExperimentalAnimationApi",
        "kotlinx.coroutines.ExperimentalCoroutinesApi"
    )
}
