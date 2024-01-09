plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.color"
}

dependencies {
    implementation(project(":domain:color"))

    implementation(libs.color.picker)
}