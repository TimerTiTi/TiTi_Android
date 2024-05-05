plugins {
    id("titi.android.library-no-hilt")
}

android {
    namespace = "com.titi.app.domain.alarm"
}

dependencies {
    implementation(project(":data:alarm:api"))
    implementation(project(":data:notification:api"))

    implementation(libs.threetenabp)
}
