plugins {
    id("titi.android.library")
}

android {
    namespace = "com.titi.domain.sleep"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:sleep:api"))

    implementation(libs.javax)
}