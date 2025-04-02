package com.bravedroid.momentor.plugins


import com.bravedroid.momentor.plugins.extensions.setupAndroidApplicationModule
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin to set up an Android application module with Hilt and base dependencies.
 *
 * This plugin configures the Android application module with necessary dependencies,
 * Hilt support, and other common configurations.
 */

class ModuleAndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            setupAndroidApplicationModule()
        }
    }
}
