@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Momentor"
val modulesFeatures = arrayOf(
    ":features:camera",
)
val modulesPresentationLibraries = arrayOf(
    ":libraries:presentation:design-system",
    ":libraries:presentation:translations",
    ":libraries:presentation:navigation",
)
val modulesInfrastructureLibraries = arrayOf(
    ":libraries:infrastructure:api:concurrency",
    ":libraries:infrastructure:api-impl:concurrency",
)
include(
    ":app",
    *modulesFeatures,
    *modulesPresentationLibraries,
    *modulesInfrastructureLibraries,
)
