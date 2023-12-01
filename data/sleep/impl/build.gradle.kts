plugins {
    id("titi.android.library")
    id("titi.android.hilt")
}

android {
    namespace = "com.titi.data.sleep.impl"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:sleep:api"))
    implementation(project(":core:util"))

    implementation(libs.bundles.moshi)
    implementation(libs.bundles.datastore)
}