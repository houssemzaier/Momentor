import org.gradle.initialization.DependenciesAccessors

import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)

    gradle.serviceOf<DependenciesAccessors>().classes.asFiles.forEach {
        compileOnly(files(it.absolutePath))
    }
}

group = "com.bravedroid.momentor.build-logic"

private val BASE_PACKAGE = "com.bravedroid.momentor"

private val modulePlugins = mapOf(
    "moduleAndroidApplication" to "$BASE_PACKAGE.plugins.ModuleAndroidApplicationPlugin",
    "moduleAndroidLibrary" to "$BASE_PACKAGE.plugins.ModuleAndroidLibraryPlugin",
    "moduleAndroidPresentationLibrary" to "$BASE_PACKAGE.plugins.ModuleAndroidPresentationLibraryPlugin",
    "moduleHilt" to "$BASE_PACKAGE.plugins.ModuleHiltModulePlugin",
    "moduleJetpackCompose" to "$BASE_PACKAGE.plugins.ModuleJetpackComposePlugin",
)

gradlePlugin {
    plugins {
        modulePlugins.forEach { (idSuffix, implClass) ->
            register(idSuffix) {
                id = "$BASE_PACKAGE.$idSuffix"
                implementationClass = implClass
            }
        }
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

