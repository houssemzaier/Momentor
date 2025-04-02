package com.bravedroid.momentor.plugins

import com.bravedroid.momentor.plugins.extensions.dsl.alias
import com.bravedroid.momentor.plugins.extensions.dsl.implementation
import com.bravedroid.momentor.plugins.extensions.dsl.ksp
import com.bravedroid.momentor.plugins.extensions.dsl.libs
import com.bravedroid.momentor.plugins.extensions.dsl.plugins
import com.bravedroid.momentor.plugins.extensions.setupHiltDiModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * This plugin is used to set up Hilt DI module in the project.
 *
 * It applies the necessary configurations and dependencies for Hilt.
 *
 * @see <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt documentation</a>
 */
class ModuleHiltModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            setupHiltDiModule()
        }
    }
}
