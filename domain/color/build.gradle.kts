plugins {
    id("titi.android.library")
}

android {
    namespace = "com.titi.domain.color"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:color:api"))

    implementation(libs.javax)
}