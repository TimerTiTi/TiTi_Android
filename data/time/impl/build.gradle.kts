plugins {
    id("titi.android.library")
    id("titi.android.hilt")
}

android {
    namespace = "com.titi.data.time.impl"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":core:util"))
    implementation(project(":data:time:api"))

    implementation(libs.bundles.moshi)
    implementation(libs.bundles.datastore)
}