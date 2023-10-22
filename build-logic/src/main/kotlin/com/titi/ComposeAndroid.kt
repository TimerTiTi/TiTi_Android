package com.titi

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("androidx-window").get())
            add("implementation", libs.findLibrary("androidx-compose-ui").get())
            add("implementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            add("implementation", libs.findLibrary("androidx.compose.material3").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.ext.junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
            add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
        }
    }


}