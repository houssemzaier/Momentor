import com.bravedroid.momentor.plugins.config.BuildConfig
import com.bravedroid.momentor.plugins.extensions.dsl.libs

plugins {
    alias(libs.plugins.moduleAndroidApplication)
    alias(libs.plugins.moduleJetpackCompose)
    alias(libs.plugins.moduleHilt)
}
android {
    defaultConfig {
        applicationId = BuildConfig.MOMENTOR_APPLICATION_ID
    }
    namespace = BuildConfig.MOMENTOR_NAMESPACE
}

dependencies {
    implementation(projects.features.camera)

    implementation(projects.libraries.presentation.navigation)
    implementation(projects.libraries.presentation.translations)
    implementation(projects.libraries.presentation.designSystem)

    implementation(projects.libraries.infrastructure.api.concurrency)
    implementation(projects.libraries.infrastructure.apiImpl.concurrency)

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
