package com.bravedroid.momentor.plugins

import com.bravedroid.momentor.plugins.extensions.setupComposeModule
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * This plugin is used to configure the Jetpack Compose module.
 */
class ModuleJetpackComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {

        with(target) {
            setupComposeModule()
        }
    }
}
