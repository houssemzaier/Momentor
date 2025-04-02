package com.bravedroid.momentor.plugins

import com.bravedroid.momentor.plugins.extensions.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class ModuleAndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            setupAndroidLibraryModule()
        }
    }
}
