plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.popup"
}

dependencies {
    implementation(project(":feature:color"))
    implementation(project(":feature:measure"))
}
