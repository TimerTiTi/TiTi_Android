plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.main"
}

dependencies {
    implementation(project(":domain:color"))
    implementation(project(":domain:time"))
    implementation(project(":domain:daily"))

    implementation(project(":feature:time"))
    implementation(project(":feature:color"))
    implementation(project(":feature:measure"))

    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.material3.window.size)
}