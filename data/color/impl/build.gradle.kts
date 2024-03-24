plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.app.data.color.impl"
}

dependencies {
    implementation(project(":data:color:api"))
}
