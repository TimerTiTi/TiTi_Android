plugins {
    id("titi.android.feature")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.feature.time"
}

dependencies {
    implementation(project(":domain:time"))
    implementation(project(":domain:task"))
    implementation(project(":domain:color"))
    implementation(project(":domain:daily"))

    implementation(libs.threetenabp)
    implementation(libs.balloon)
}