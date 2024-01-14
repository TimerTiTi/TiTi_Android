plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.app.data.daily.impl"
}

dependencies {
    implementation(project(":data:daily:api"))
}
