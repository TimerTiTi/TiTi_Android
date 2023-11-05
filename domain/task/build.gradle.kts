plugins {
   id("titi.android.library")
}

android {
    namespace = "com.titi.domain.task"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }

}

dependencies {
    implementation(project(":data:task:api"))

    implementation(libs.javax)
}