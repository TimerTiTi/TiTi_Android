plugins {
    id("titi.android.feature")
}

android {
    namespace = "com.titi.app.feature.setting"
}

dependencies {
    implementation(project(":data:notification:api"))
    implementation(project(":data:language:api"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)

    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)
}
