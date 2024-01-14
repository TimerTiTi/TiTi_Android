plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.app.data.time.impl"
}

dependencies {
    implementation(project(":data:time:api"))
}
