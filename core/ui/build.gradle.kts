plugins {
    id("titi.android.library")
}

android {
    namespace = "com.titi.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.compose.navigation)
}