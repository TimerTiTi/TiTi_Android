plugins {
    id("titi.android.library")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.titi.app.core.util"
}

dependencies {
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    implementation(libs.bundles.datastore)

    implementation(libs.threetenabp)
}