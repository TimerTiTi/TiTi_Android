plugins {
    id("titi.android.compose")
    id("titi.android.library")
}

android {
    namespace = "com.titi.app.tds"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
}
