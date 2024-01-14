import com.titi.common.AppConfig
import com.titi.common.BuildType

plugins {
    id("titi.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.titi.app"

    defaultConfig {
        applicationId = AppConfig.APP_ID
        versionCode = AppConfig.APP_VERSION_CODE
        versionName = AppConfig.APP_VERSION_NAME
    }

    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            manifestPlaceholders["appName"] = AppConfig.APP_NAME
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName(BuildType.DEV) {
            initWith(getByName("debug"))
            manifestPlaceholders["appName"] = "${AppConfig.APP_NAME} - ${BuildType.DEV}"
            applicationIdSuffix = ".${BuildType.DEV}"
            versionNameSuffix = "-${BuildType.DEV}"
            applicationIdSuffix = ".${BuildType.DEV}"
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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.bundles.mavericks)
    implementation(libs.threetenabp)
}
