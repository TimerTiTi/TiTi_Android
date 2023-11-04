plugins {
   id("titi.android.library")
}

android {
    namespace = "com.titi.core.util"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(libs.bundles.moshi)

    implementation(libs.bundles.datastore)

    implementation(libs.threetenabp)
}