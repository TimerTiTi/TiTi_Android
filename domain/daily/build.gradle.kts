plugins {
    id("titi.android.library-no-hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.doamin.daily"
}

dependencies {
    implementation(project(":data:daily:api"))
    implementation(project(":core:util"))
    
    implementation(libs.threetenabp)
}