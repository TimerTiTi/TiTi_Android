plugins {
    id("titi.android.compose")
    id("titi.android.library")
}

android {
    namespace = "com.titi.app.core.ui"
}

dependencies {
    implementation(libs.androidx.compose.navigation)
}
