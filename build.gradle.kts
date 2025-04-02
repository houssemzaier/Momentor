// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias (libs.plugins.android.library) apply false

    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // alias libs.plugins.org.jetbrains.kotlin.jvm apply false
    // alias libs.plugins.org.jetbrains.kotlin.android apply false
    // alias libs.plugins.org.jetbrains.kotlin.plugin.parcelize apply false

     alias( libs.plugins.ksp)apply false
    alias (libs.plugins.hilt)apply false

    //quality
    //     id 'jacoco'
    // alias libs.plugins.org.jlleitschuh.gradle.ktlint
    //     alias libs.plugins.io.gitlab.arturbosch.detekt




    // //quality
    // id 'jacoco'
    // alias libs.plugins.org.jlleitschuh.gradle.ktlint
    //     alias libs.plugins.io.gitlab.arturbosch.detekt
    //     alias libs.plugins.org.sonarqube
    //     alias libs.plugins.android.junit5.plugin apply false
  }
