package com.titi.core.util

import android.content.BroadcastReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun BroadcastReceiver.goAsync(
    coroutineScope: CoroutineScope,
    block: suspend () -> Unit
) {
    val pendingResult = goAsync()
    coroutineScope.launch {
        block()
        pendingResult.finish()
    }
}