plugins {
    id("titi.android.feature")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.app.feature.measure"
}

dependencies {
    implementation(project(":domain:time"))
    implementation(project(":domain:task"))
    implementation(project(":domain:color"))
    implementation(project(":domain:daily"))
    implementation(project(":domain:sleep"))
    implementation(project(":domain:alarm"))

    implementation(libs.threetenabp)
}