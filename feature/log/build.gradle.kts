plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.log"
}

dependencies {
    implementation(project(":domain:color"))

    implementation(libs.threetenabp)
    implementation(libs.calendar)
}
