plugins {
    id("titi.android.library-no-hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.app.doamin.daily"
}

dependencies {
    implementation(project(":data:daily:api"))
    implementation(project(":data:time:api"))
    implementation(project(":core:util"))

    implementation(libs.threetenabp)
}
