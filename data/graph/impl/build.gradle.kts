plugins {
    id("titi.android.data.local")
}

android {
    namespace = "com.titi.app.data.graph.impl"
}

dependencies {
    implementation(project(":data:graph:api"))
}
