plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.data.time.impl"

}

dependencies {
    implementation(project(":data:time:api"))
}