plugins {
    id("titi.android.compose")
    id("titi.android.library")
}

android {
    namespace = "com.titi.app.core.designsystem"
}

dependencies {
    implementation(project(":core:util"))

    implementation(libs.threetenabp)
}
