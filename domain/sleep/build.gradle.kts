plugins {
    id("titi.android.library-no-hilt")
}

android {
    namespace = "com.titi.domain.sleep"
}

dependencies {
    implementation(project(":data:sleep:api"))
}
