import com.titi.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("titi.android.library")
                apply("titi.android.hilt")
            }


            dependencies {
                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))

                add("implementation", libs.findLibrary("androidx.compose.navigation").get())
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())

                add("implementation", libs.findLibrary("mavericks").get())
                add("implementation", libs.findLibrary("mavericks.compose").get())
                add("implementation", libs.findLibrary("mavericks.hilt").get())
            }
        }
    }
}