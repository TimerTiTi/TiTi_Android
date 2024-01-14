plugins {
    id("titi.android.library-no-hilt")
}

android {
    namespace = "com.titi.app.domain.task"
}

dependencies {
    implementation(project(":data:task:api"))
}
