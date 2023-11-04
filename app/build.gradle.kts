plugins {
    id("titi.android.application.compose")
    id("titi.android.hilt")
}

android {
    namespace = "com.titi"

    defaultConfig {
        applicationId = "com.titi"
         versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(project(":feature:time"))

    implementation(project(":data:time:impl"))
    implementation(project(":data:task:impl"))
    implementation(project(":data:color:impl"))

    implementation(libs.threetenabp)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.bundles.mavericks)

}