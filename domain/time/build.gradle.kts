@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
   id("titi.android.library")
}

android {
    namespace = "com.titi.domain.time"

    defaultConfig {
       testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
     }

}

dependencies {
    implementation(project(":data:time:api"))

    implementation(libs.javax)
}