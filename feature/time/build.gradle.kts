plugins {
    id("titi.android.feature")
    id("titi.android.library.compose")
}

android {
    namespace = "com.titi.feature.time"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:util"))

    implementation(project(":domain:time"))
    implementation(project(":domain:task"))

    implementation(libs.balloon)
}