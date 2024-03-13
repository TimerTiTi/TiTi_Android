plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.log"
}

dependencies {
    implementation(project(":domain:color"))
    implementation(project(":domain:daily"))
    implementation(project(":data:graph:api"))

    implementation(libs.threetenabp)
    implementation(libs.calendar)
}
