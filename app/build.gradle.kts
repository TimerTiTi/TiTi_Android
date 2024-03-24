import com.titi.common.AppConfig
import com.titi.common.BuildType

plugins {
    id("titi.android.application")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
}

android {
    namespace = "com.titi.app"

    defaultConfig {
        applicationId = AppConfig.APP_ID

        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = AppConfig.APP_VERSION_CODE
        versionName = AppConfig.APP_VERSION_NAME
    }

    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
            isDebuggable = true
            manifestPlaceholders["enableCrashReporting"] = false
        }
        getByName(BuildType.INHOUSE) {
            isMinifyEnabled = true
            isDebuggable = true
            isShrinkResources = true
            manifestPlaceholders["appName"] = "${AppConfig.APP_NAME} - ${BuildType.INHOUSE}"
            manifestPlaceholders["enableCrashReporting"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = false
            manifestPlaceholders["appName"] = AppConfig.APP_NAME
            manifestPlaceholders["enableCrashReporting"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    implementation(project(":data:graph:impl"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.bundles.mavericks)
    implementation(libs.threetenabp)
}
