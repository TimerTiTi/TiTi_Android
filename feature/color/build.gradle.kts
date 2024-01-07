plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.feature.color"
}

dependencies {
    implementation(project(":domain:color"))

    implementation(libs.color.picker)
}