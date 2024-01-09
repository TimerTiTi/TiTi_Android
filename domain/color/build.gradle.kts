plugins {
    id("titi.android.library-no-hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.app.domain.color"
}

dependencies {
    implementation(project(":data:color:api"))
}