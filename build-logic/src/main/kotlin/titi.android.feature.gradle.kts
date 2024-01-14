import com.titi.common.libs

plugins {
    id("titi.android.library")
    id("titi.android.compose")
}

dependencies {
    val libs = project.extensions.libs

    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":core:designsystem"))

    implementation(libs.findLibrary("androidx.compose.navigation").get())
    implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
    implementation(libs.findBundle("mavericks").get())
}