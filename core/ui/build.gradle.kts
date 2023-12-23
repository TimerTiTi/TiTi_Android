plugins {
    id("titi.android.library")
}

android {
    namespace = "com.titi.core.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.compose.navigation)
}