plugins {
    id("titi.android.compose")
    id("titi.android.library")
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.titi.app.core.ui"
}

dependencies {
    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.json)
}
