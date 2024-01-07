plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.data.daily.impl"
}

dependencies {
    implementation(project(":data:daily:api"))
}