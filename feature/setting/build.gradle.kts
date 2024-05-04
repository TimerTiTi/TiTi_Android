plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.setting"
}

dependencies {
    implementation(project(":data:notification:api"))
}
