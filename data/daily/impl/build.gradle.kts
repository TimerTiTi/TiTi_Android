plugins {
    id("titi.android.library")
    id("titi.android.hilt")
}

android {
    namespace = "com.titi.data.daily.impl"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:daily:api"))

    implementation(libs.bundles.moshi)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}