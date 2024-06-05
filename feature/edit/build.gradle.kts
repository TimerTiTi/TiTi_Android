plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.edit"
}

dependencies {
    implementation(project(":domain:daily"))
}
