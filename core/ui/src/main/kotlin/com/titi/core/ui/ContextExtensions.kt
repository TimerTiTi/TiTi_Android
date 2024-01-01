package com.titi.core.ui

import android.app.Activity
import android.content.Context
import android.view.WindowManager

fun Context.setBrightness(isDark: Boolean) {
    val activity = this as? Activity ?: return
    val layoutParams: WindowManager.LayoutParams = activity.window.attributes
    layoutParams.screenBrightness =
        if (isDark) 5f / 255 else WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
    activity.window.attributes = layoutParams
}