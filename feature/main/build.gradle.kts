plugins {
    id("titi.android.feature")
    id("titi.android.library.compose")
}

android {
    namespace = "com.titi.feature.main"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:util"))
    implementation(project(":core:ui"))

    implementation(project(":domain:color"))
    implementation(project(":domain:time"))

    implementation(project(":feature:time"))
    implementation(project(":feature:color"))
    implementation(project(":feature:measure"))

    implementation(libs.androidx.splashscreen)
}