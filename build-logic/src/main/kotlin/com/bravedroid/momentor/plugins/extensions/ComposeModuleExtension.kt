package com.bravedroid.momentor.plugins.extensions

import com.bravedroid.momentor.plugins.extensions.dsl.alias
import com.bravedroid.momentor.plugins.extensions.dsl.debugImplementation
import com.bravedroid.momentor.plugins.extensions.dsl.implementation
import com.bravedroid.momentor.plugins.extensions.dsl.libs
import com.bravedroid.momentor.plugins.extensions.dsl.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.setupComposeModule() {
    plugins  {
        alias(libs.plugins.kotlin.compose)
     }

    dependencies {
        // Compose - Using BOM for version management
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.bundles.compose.ui)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.material.icons.extended)

        // Compose - debugging
        debugImplementation(libs.bundles.debug)


        // (Lifecycle + ViewModel) compose
        implementation(libs.bundles.compose.lifecycle)

        // Navigation compose
        implementation(libs.androidx.navigation.compose)

        // Coil
        implementation(libs.coil.compose)
    }
}
