plugins {
    id("titi.android.application")
}

android {
    namespace = "com.titi.app"

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
    implementation(project(":feature:main"))

    implementation(project(":data:time:impl"))
    implementation(project(":data:task:impl"))
    implementation(project(":data:color:impl"))
    implementation(project(":data:daily:impl"))
    implementation(project(":data:sleep:impl"))
    implementation(project(":data:alarm:impl"))

    implementation(libs.bundles.mavericks)
    implementation(libs.threetenabp)
}