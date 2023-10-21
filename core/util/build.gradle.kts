@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
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