package com.titi.core.ui

import android.net.Uri
import androidx.core.net.toUri
import com.titi.core.ui.TiTiDeepLinkArgs.COLOR_ARGS
import com.titi.core.ui.TiTiDeepLinkConfigs.HOST_COLOR
import com.titi.core.ui.TiTiDeepLinkConfigs.SCHEME

object TiTiDeepLinkConfigs {

    const val SCHEME = "titi"

    const val HOST_COLOR = "color"

}

object TiTiDeepLinkArgs {
    const val COLOR_ARGS = "recordingMode"
}

enum class TiTiDeepLink(val uri: Uri) {
    COLOR("$SCHEME://$HOST_COLOR/".toUri())
}

fun createColorUri(recordingMode: Int) =
    TiTiDeepLink.COLOR.uri.toString().plus("?$COLOR_ARGS=$recordingMode").toUri()