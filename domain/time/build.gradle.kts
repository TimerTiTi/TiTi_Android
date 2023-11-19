plugins {
    id("titi.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.titi.domain.time"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:time:api"))

    implementation(libs.javax)
}