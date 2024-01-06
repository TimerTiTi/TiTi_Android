plugins {
    id("titi.android.library")
    id("titi.android.hilt")
}

android {
    namespace = "com.titi.data.alarm.impl"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":core:util"))
    implementation(project(":data:alarm:api"))

    implementation(libs.bundles.moshi)
    implementation(libs.bundles.datastore)
    implementation(libs.threetenabp)
}