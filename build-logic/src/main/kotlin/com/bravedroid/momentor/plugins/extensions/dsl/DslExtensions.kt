package com.bravedroid.momentor.plugins.extensions.dsl

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

/**
 * Adds a library or a bundle of libraries to the `implementation` configuration.
 *
 * Designed to be used with Gradle version catalogs (`libs.versions.toml`)
 *
 * @param provider A [Provider] of either a single [MinimalExternalModuleDependency] or a [ExternalModuleDependencyBundle].
 */
inline fun <reified T : Any> DependencyHandler.implementation(provider: Provider<T>) {
    addDependency("implementation", provider)
}

/**
 * Adds a library or a bundle of libraries to the `debugImplementation` configuration.
 *
 * Designed to be used with Gradle version catalogs (`libs.versions.toml`)
 *
 * @param provider A [Provider] of either a single [MinimalExternalModuleDependency] or a [ExternalModuleDependencyBundle].
 */
inline fun <reified T : Any> DependencyHandler.debugImplementation(provider: Provider<T>) {
    addDependency("debugImplementation", provider)
}

/**
 * Adds a library or a bundle of libraries to the `testImplementation` configuration.
 *
 * Designed to be used with Gradle version catalogs (`libs.versions.toml`)
 *
 * @param provider A [Provider] of either a single [MinimalExternalModuleDependency] or a [ExternalModuleDependencyBundle].
 */
inline fun <reified T : Any> DependencyHandler.testImplementation(provider: Provider<T>) {
    addDependency("testImplementation", provider)
}

/**
 * Adds a library or a bundle of libraries to the `androidTestImplementation` configuration.
 *
 * Designed to be used with Gradle version catalogs (`libs.versions.toml`)
 *
 * @param provider A [Provider] of either a single [MinimalExternalModuleDependency] or a [ExternalModuleDependencyBundle].
 */
inline fun <reified T : Any> DependencyHandler.androidTestImplementation(provider: Provider<T>) {
    addDependency("androidTestImplementation", provider)
}

/**
 * Adds a dependency to the `ksp` (Kotlin Symbol Processing) configuration.
 *
 * Designed to be used with Gradle version catalogs (`libs.versions.toml`)
 */
fun DependencyHandler.ksp(provider: Provider<MinimalExternalModuleDependency>) {
    addDependency("ksp", provider)
}

/**
 * Adds a dependency to the `coreLibraryDesugaring` configuration.
 *
 * Designed to be used with Gradle version catalogs (`libs.versions.toml`)
 */
fun DependencyHandler.coreLibraryDesugaring(provider: Provider<MinimalExternalModuleDependency>) {
    addDependency("coreLibraryDesugaring", provider)
}

/**
 * Internal helper to add a dependency or a bundle of dependencies from a version catalog to the specified configuration.
 *
 * Supports both [MinimalExternalModuleDependency] and [ExternalModuleDependencyBundle].
 */
inline fun <reified T : Any> DependencyHandler.addDependency(
    configurationName: String,
    provider: Provider<T>
) {
    provider.map { dep ->
        when (dep) {
            is MinimalExternalModuleDependency -> listOf("${dep.module.group}:${dep.module.name}:${dep.version}")
            is ExternalModuleDependencyBundle -> dep.map {
                "${it.module.group}:${it.module.name}:${it.version}"
            }
             else -> error("Unsupported dependency type: ${T::class.simpleName}")
        }
    }.get().forEach { notation ->
        add(configurationName, notation)
    }
}

/**
 * Applies plugins using a DSL-style action block on the [PluginManager].
 */
fun Project.plugins(action: PluginManager.() -> Unit) {
    pluginManager.action()
}

/**
 * Returns the version catalog accessor (`libs`) for this [Project].
 */
val Project.libs: LibrariesForLibs
    get() = extensionOf<LibrariesForLibs>("libs")

/**
 * Applies a plugin using its alias from the version catalog.
 *
 * @param notation A [Provider] of a [PluginDependency] (e.g., `libs.plugins.androidApplication`)
 */
fun PluginManager.alias(notation: Provider<PluginDependency>) {
    apply(notation.get().pluginId)
}

/**
 * Generic accessor for project extensions with proper type safety.
 */
inline fun <reified T> Project.extensionOf(name: String): T {
    return extensions.getByName(name).let {
        require(it is T) { "Extension '$name' is not of expected type ${T::class.qualifiedName}" }
        it
    }
}
