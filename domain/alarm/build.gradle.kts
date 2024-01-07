plugins {
    id("titi.android.library-no-hilt")
}

android {
    namespace = "com.titi.domain.alarm"
}

dependencies {
    implementation(project(":data:alarm:api"))

    implementation(libs.threetenabp)
}