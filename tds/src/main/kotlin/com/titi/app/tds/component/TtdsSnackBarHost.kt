package com.titi.app.tds.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.coroutines.resume
import kotlin.math.roundToInt
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Stable
class TtdsSnackbarHostState {

    private val mutex = Mutex()

    var currentSnackbarData by mutableStateOf<TtdsSnackbarData?>(null)
        private set

    suspend fun showSnackbar(
        startIcon: (@Composable () -> Unit)? = null,
        emphasizedMessage: String? = null,
        message: String,
        actionLabel: Boolean = false,
        withDismissAction: Boolean = false,
        duration: TtdsSnackbarDuration = if (actionLabel) {
            TtdsSnackbarDuration.Indefinite
        } else {
            TtdsSnackbarDuration.Short
        },
    ): TtdsSnackbarResult = showSnackbar(
        TtdsSnackbarVisualsImpl(
            startIcon = startIcon,
            emphasizedMessage = emphasizedMessage,
            message = message,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = duration,
        ),
    )

    private suspend fun showSnackbar(visuals: TtdsSnackbarVisuals): TtdsSnackbarResult =
        mutex.withLock {
            try {
                return suspendCancellableCoroutine { continuation ->
                    currentSnackbarData = TtdsSnackbarDataImpl(visuals, continuation)
                }
            } finally {
                currentSnackbarData = null
            }
        }

    private class TtdsSnackbarVisualsImpl(
        override val startIcon: (@Composable () -> Unit)?,
        override val emphasizedMessage: String?,
        override val message: String,
        override val actionLabel: Boolean,
        override val withDismissAction: Boolean,
        override val duration: TtdsSnackbarDuration,
    ) : TtdsSnackbarVisuals {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as TtdsSnackbarVisualsImpl

            if (startIcon != other.startIcon) return false
            if (emphasizedMessage != other.emphasizedMessage) return false
            if (message != other.message) return false
            if (actionLabel != other.actionLabel) return false
            if (withDismissAction != other.withDismissAction) return false
            if (duration != other.duration) return false

            return true
        }

        override fun hashCode(): Int {
            var result = startIcon.hashCode()
            result = 31 * result + emphasizedMessage.hashCode()
            result = 31 * result + message.hashCode()
            result = 31 * result + actionLabel.hashCode()
            result = 31 * result + withDismissAction.hashCode()
            result = 31 * result + duration.hashCode()
            return result
        }
    }

    private class TtdsSnackbarDataImpl(
        override val visuals: TtdsSnackbarVisuals,
        private val continuation: CancellableContinuation<TtdsSnackbarResult>,
    ) : TtdsSnackbarData {

        override fun performAction() {
            if (continuation.isActive) continuation.resume(TtdsSnackbarResult.ActionPerformed)
        }

        override fun dismiss() {
            if (continuation.isActive) continuation.resume(TtdsSnackbarResult.Dismissed)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as TtdsSnackbarDataImpl

            if (visuals != other.visuals) return false
            if (continuation != other.continuation) return false

            return true
        }

        override fun hashCode(): Int {
            var result = visuals.hashCode()
            result = 31 * result + continuation.hashCode()
            return result
        }
    }
}

@Composable
fun TtdsSnackbarHost(
    hostState: TtdsSnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (TtdsSnackbarData) -> Unit = { TtdsSnackbar(it) },
) {
    val currentSnackbarData = hostState.currentSnackbarData
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration = currentSnackbarData.visuals.duration.toMillis(
                currentSnackbarData.visuals.actionLabel,
                accessibilityManager,
            )
            delay(duration)
            currentSnackbarData.dismiss()
        }
    }
    TtdsSlideInSlideOutVertically(
        current = hostState.currentSnackbarData,
        modifier = modifier,
        content = snackbar,
    )
}

@Stable
interface TtdsSnackbarVisuals {
    val startIcon: (@Composable () -> Unit)?
    val emphasizedMessage: String?
    val message: String
    val actionLabel: Boolean
    val withDismissAction: Boolean
    val duration: TtdsSnackbarDuration
}

@Stable
interface TtdsSnackbarData {
    val visuals: TtdsSnackbarVisuals

    fun performAction()

    fun dismiss()
}

enum class TtdsSnackbarResult {
    Dismissed,
    ActionPerformed,
}

enum class TtdsSnackbarDuration {
    Short,
    Long,
    Indefinite,
}

internal fun TtdsSnackbarDuration.toMillis(
    hasAction: Boolean,
    accessibilityManager: AccessibilityManager?,
): Long {
    val original = when (this) {
        TtdsSnackbarDuration.Indefinite -> Long.MAX_VALUE
        TtdsSnackbarDuration.Long -> 10000L
        TtdsSnackbarDuration.Short -> 4000L
    }
    if (accessibilityManager == null) {
        return original
    }
    return accessibilityManager.calculateRecommendedTimeoutMillis(
        original,
        containsIcons = true,
        containsText = true,
        containsControls = hasAction,
    )
}

@Composable
private fun TtdsSlideInSlideOutVertically(
    modifier: Modifier = Modifier,
    current: TtdsSnackbarData?,
    content: @Composable (TtdsSnackbarData) -> Unit,
) {
    val state = remember { TtdsSlideInSlideOutVerticallyState<TtdsSnackbarData?>() }
    if (current != state.current) {
        state.current = current
        val keys = state.items.map { it.key }.toMutableList()
        if (!keys.contains(current)) {
            keys.add(current)
        }
        state.items.clear()
        keys.filterNotNull().mapTo(state.items) { key ->
            TtdsSlideInSlideOutVerticallyAnimationItem(key) { children ->
                val isVisible = key == current
                val duration = if (isVisible) {
                    TTDS_SNACKBAR_SLIDE_IN_VERTICALLY_MILLIS
                } else {
                    TTDS_SNACKBAR_SLIDE_OUT_VERTICALLY_MILLIS
                }
                val delay = TTDS_SNACKBAR_SLIDE_OUT_VERTICALLY_MILLIS +
                    TTDS_SNACKBAR_IN_BETWEEN_DELAY_MILLIS
                val animationDelay = if (isVisible && keys.filterNotNull().size != 1) delay else 0

                val offsetY = animatedOffsetY(
                    animation = tween(
                        easing = LinearEasing,
                        delayMillis = animationDelay,
                        durationMillis = duration,
                    ),
                    visible = isVisible,
                    endDp = 40.dp,
                    onAnimationFinish = {
                        if (key != state.current) {
                            state.items.removeAll { it.key == key }
                            state.scope?.invalidate()
                        }
                    },
                )

                Box(
                    Modifier
                        .offset {
                            IntOffset(100, offsetY.value.roundToInt())
                        }
                        .semantics {
                            liveRegion = LiveRegionMode.Polite
                            dismiss {
                                key.dismiss()
                                true
                            }
                        },
                ) {
                    children()
                }
            }
        }
    }
    Box(modifier) {
        state.scope = currentRecomposeScope
        state.items.forEach { (item, opacity) ->
            key(item) {
                opacity {
                    content(item!!)
                }
            }
        }
    }
}

private class TtdsSlideInSlideOutVerticallyState<T> {
    var current: Any? = Any()
    var items = mutableListOf<TtdsSlideInSlideOutVerticallyAnimationItem<T>>()
    var scope: RecomposeScope? = null
}

private data class TtdsSlideInSlideOutVerticallyAnimationItem<T>(
    val key: T,
    val transition: TtdsSlideInSlideOutVerticallyTransition,
)

private typealias TtdsSlideInSlideOutVerticallyTransition =
    @Composable (content: @Composable () -> Unit) -> Unit

@Composable
private fun animatedOffsetY(
    animation: AnimationSpec<Float>,
    visible: Boolean,
    endDp: Dp,
    onAnimationFinish: () -> Unit = {},
): State<Float> {
    val density = LocalDensity.current
    val targetY = with(density) { endDp.toPx() }
    val offsetY = remember { Animatable(if (!visible) targetY else -targetY) }
    LaunchedEffect(visible) {
        offsetY.animateTo(
            if (visible) targetY else -targetY,
            animationSpec = animation,
        )
        onAnimationFinish()
    }

    return offsetY.asState()
}

private const val TTDS_SNACKBAR_SLIDE_IN_VERTICALLY_MILLIS = 150
private const val TTDS_SNACKBAR_SLIDE_OUT_VERTICALLY_MILLIS = 75
private const val TTDS_SNACKBAR_IN_BETWEEN_DELAY_MILLIS = 0
