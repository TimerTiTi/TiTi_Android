plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.log"
}

dependencies {
    implementation(libs.threetenabp)
    implementation(libs.calendar)
}
