plugins {
    id("titi.android.compose")
    id("titi.android.library")
}

android {
    namespace = "com.titi.app.core.designsystem"
}

dependencies {
    implementation(libs.threetenabp)
}
