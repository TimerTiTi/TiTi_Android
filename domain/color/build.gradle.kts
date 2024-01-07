plugins {
    id("titi.android.library-no-hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.domain.color"
}

dependencies {
    implementation(project(":data:color:api"))
}