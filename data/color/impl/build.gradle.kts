plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.data.color.impl"
}

dependencies {
    implementation(project(":data:color:api"))
}