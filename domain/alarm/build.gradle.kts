plugins {
    id("titi.android.library")
}

android {
    namespace = "com.titi.domain.alarm"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:alarm:api"))
}