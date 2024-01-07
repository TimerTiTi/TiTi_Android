plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.data.alarm.impl"
}

dependencies {
    implementation(project(":data:alarm:api"))

    implementation(libs.threetenabp)
}