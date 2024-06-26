package com.titi.app.core.ui

import android.content.res.Configuration

fun Configuration.isTablet(): Boolean {
    val screenLayout = this.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE ||
        screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE
}
