plugins {
    alias(libs.plugins.moduleAndroidLibrary)
    alias(libs.plugins.moduleHilt)
}

dependencies {
    implementation(projects.libraries.infrastructure.api.concurrency)
    implementation(libs.androidx.navigation.compose)
}
