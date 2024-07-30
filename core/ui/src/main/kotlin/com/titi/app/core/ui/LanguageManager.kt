package com.titi.app.core.ui

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.core.app.LocaleManagerCompat
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageManager(private val context: Context) {
    private val _currentLanguage = MutableStateFlow(getCurrentLanguage())
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(languageCode: String) {
        val localeList = when (languageCode) {
            SYSTEM_DEFAULT -> LocaleList.getEmptyLocaleList()
            else -> LocaleList.forLanguageTags(languageCode)
        }

        _currentLanguage.value = languageCode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales = localeList
        } else {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }

    private fun getCurrentLanguage(): String {
        return if (isUsingSystemDefault()) {
            SYSTEM_DEFAULT
        } else {
            val currentLocale = context.resources.configuration.locales[0]
            when (currentLocale.language) {
                "en" -> ENGLISH
                "ko" -> KOREAN
                "zh" -> CHINA
                else -> SYSTEM_DEFAULT
            }
        }
    }

    private fun isUsingSystemDefault(): Boolean {
        return LocaleManagerCompat.getApplicationLocales(context).isEmpty
    }

    companion object {
        const val SYSTEM_DEFAULT = "system"
        const val ENGLISH = "en"
        const val KOREAN = "ko"
        const val CHINA = "zh"
    }
}
