plugins {
    id("titi.android.feature")
    id("titi.android.library.compose")
}

android {
    namespace = "com.titi.feature.measure"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:util"))
    implementation(project(":core:ui"))

    implementation(project(":domain:time"))
    implementation(project(":domain:task"))
    implementation(project(":domain:color"))
    implementation(project(":domain:daily"))
    implementation(project(":domain:sleep"))

    implementation(libs.threetenabp)
}