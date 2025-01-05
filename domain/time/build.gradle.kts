plugins {
    id("titi.android.library-no-hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.app.domain.time"
}

dependencies {
    implementation(project(":data:time:api"))
    implementation(project(":core:util"))
    implementation(libs.threetenabp)
}
