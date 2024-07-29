plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.app.data.language.impl"
}

dependencies {
    implementation(project(":data:language:api"))
}
