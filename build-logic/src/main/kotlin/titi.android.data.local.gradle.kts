import com.titi.common.libs

plugins {
    id("titi.android.library")
}

dependencies {
    val libs = project.extensions.libs

    implementation(project(":core:util"))

    implementation(libs.findBundle("datastore").get())

    implementation(libs.findLibrary("moshi").get())
    implementation(libs.findLibrary("moshi.kotlin").get())
    "ksp"(libs.findLibrary("moshi.codegen").get())

    implementation(libs.findLibrary("androidx.room.runtime").get())
    implementation(libs.findLibrary("androidx.room.ktx").get())
    "ksp"(libs.findLibrary("androidx.room.compiler").get())
}
