plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.data.task.impl"
}

dependencies {
    implementation(project(":data:task:api"))
}