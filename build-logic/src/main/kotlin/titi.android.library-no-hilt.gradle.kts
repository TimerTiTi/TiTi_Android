import com.titi.common.configureCoroutineAndroid
import com.titi.common.configureKotlinAndroid
import com.titi.common.libs

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()

dependencies {
    val libs = project.extensions.libs
    implementation(libs.findLibrary("javax").get())
}
