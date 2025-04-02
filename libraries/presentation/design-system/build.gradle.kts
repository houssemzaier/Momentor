plugins {
    alias(libs.plugins.moduleAndroidLibrary)
    alias(libs.plugins.moduleJetpackCompose)
 }

dependencies {
    implementation (projects.libraries.presentation.translations)
}
