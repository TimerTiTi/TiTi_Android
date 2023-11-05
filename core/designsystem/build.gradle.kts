plugins {
    id("titi.android.library")
    id("titi.android.library.compose")
}

android {
    namespace = "com.titi.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}
