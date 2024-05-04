plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.app.data.notification.impl"
}

dependencies {
    implementation(project(":data:notification:api"))
}
