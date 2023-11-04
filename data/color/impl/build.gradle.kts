plugins {
    id("titi.android.library")
    id("titi.android.hilt")
}

android {
    namespace = "com.titi.data.color.impl"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":core:util"))
    implementation(project(":data:color:api"))

    implementation(libs.bundles.moshi)
    implementation(libs.bundles.datastore)
}