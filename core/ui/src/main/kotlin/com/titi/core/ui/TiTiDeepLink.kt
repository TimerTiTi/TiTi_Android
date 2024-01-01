package com.titi.core.ui

import android.net.Uri
import androidx.core.net.toUri
import com.titi.core.ui.TiTiDeepLinkArgs.COLOR_ARG
import com.titi.core.ui.TiTiDeepLinkArgs.MEASURE_ARG
import com.titi.core.ui.TiTiDeepLinkConfigs.HOST_COLOR
import com.titi.core.ui.TiTiDeepLinkConfigs.HOST_MEASURE
import com.titi.core.ui.TiTiDeepLinkConfigs.SCHEME

object TiTiDeepLinkConfigs {

    const val SCHEME = "titi"

    const val HOST_COLOR = "color"
    const val HOST_MEASURE = "measure"

}

object TiTiDeepLinkArgs {
    const val COLOR_ARG = "recordingMode"
    const val MEASURE_ARG = "splashResultState"
}

enum class TiTiDeepLink(val uri: Uri) {
    COLOR("$SCHEME://$HOST_COLOR/".toUri()),
    MEASURE("$SCHEME://$HOST_MEASURE/".toUri())
}

fun createColorUri(recordingMode: Int) =
    TiTiDeepLink.COLOR.uri.toString().plus("?$COLOR_ARG=$recordingMode").toUri()

fun createMeasureUri(splashResultState: String) =
    TiTiDeepLink.MEASURE.uri.toString().plus("?$MEASURE_ARG=$splashResultState").toUri()