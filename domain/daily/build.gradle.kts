plugins {
    id("titi.android.library")
}

android {
    namespace = "com.titi.doamin.daily"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(project(":data:daily:api"))
    implementation(project(":core:util"))
    implementation(libs.threetenabp)
    implementation(libs.javax)
}