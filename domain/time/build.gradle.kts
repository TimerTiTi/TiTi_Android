plugins {
    id("titi.android.library-no-hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.domain.time"
}

dependencies {
    implementation(project(":data:time:api"))

    implementation(libs.threetenabp)
}