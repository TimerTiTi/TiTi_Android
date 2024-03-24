import com.titi.common.configureCoroutineAndroid
import com.titi.common.configureHiltAndroid
import com.titi.common.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()