package com.bravedroid.momentor.plugins.extensions
import com.bravedroid.momentor.plugins.extensions.dsl.alias
import com.bravedroid.momentor.plugins.extensions.dsl.implementation
import com.bravedroid.momentor.plugins.extensions.dsl.ksp
import com.bravedroid.momentor.plugins.extensions.dsl.libs
import com.bravedroid.momentor.plugins.extensions.dsl.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.setupHiltDiModule() {
    plugins  {
        alias(libs.plugins.ksp)
        alias(libs.plugins.hilt)
    }

    dependencies {
        implementation(libs.hilt.android.asProvider())
        implementation(libs.bundles.hilt.androidx)
        ksp(libs.hilt.android.compiler)
    }
}
