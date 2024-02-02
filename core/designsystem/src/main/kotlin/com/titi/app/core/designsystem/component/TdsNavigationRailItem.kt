package com.titi.app.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import kotlin.math.roundToInt

@Composable
fun TdsNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationRailItemColors = NavigationRailItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val styledIcon = @Composable {
        val iconColor by colors.iconColor(selected = selected, enabled = enabled)
        val clearSemantics = label != null && (alwaysShowLabel || selected)
        Box(modifier = if (clearSemantics) Modifier.clearAndSetSemantics {} else Modifier) {
            CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
        }
    }

    val styledLabel: @Composable (() -> Unit)? = label?.let {
        @Composable {
            val style = TdsTextStyle.NORMAL_TEXT_STYLE.getTextStyle(fontSize = 14.sp)
            val textColor by colors.textColor(selected = selected, enabled = enabled)
            CompositionLocalProvider(LocalContentColor provides textColor) {
                ProvideTextStyle(style, content = label)
            }
        }
    }

    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .height(height = NavigationRailItemHeight)
            .widthIn(min = NavigationRailItemWidth),
        contentAlignment = Alignment.Center,
    ) {
        val animationProgress: Float by animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = tween(ITEM_ANIMATION_DURATION_MILLIS),
        )

        NavigationRailItemBaselineLayout(
            icon = styledIcon,
            label = styledLabel,
            alwaysShowLabel = alwaysShowLabel,
            animationProgress = animationProgress,
        )
    }
}

object NavigationRailItemDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = TdsColor.TEXT.getColor(),
        selectedTextColor: Color = TdsColor.TEXT.getColor(),
        unselectedIconColor: Color = TdsColor.LIGHT_GRAY.getColor(),
        unselectedTextColor: Color = TdsColor.LIGHT_GRAY.getColor(),
    ): NavigationRailItemColors = NavigationRailItemColors(
        selectedIconColor = selectedIconColor,
        selectedTextColor = selectedTextColor,
        unselectedIconColor = unselectedIconColor,
        unselectedTextColor = unselectedTextColor,
        disabledIconColor = unselectedIconColor,
        disabledTextColor = unselectedTextColor,
    )
}

@Stable
class NavigationRailItemColors internal constructor(
    private val selectedIconColor: Color,
    private val selectedTextColor: Color,
    private val unselectedIconColor: Color,
    private val unselectedTextColor: Color,
    private val disabledIconColor: Color,
    private val disabledTextColor: Color,
) {
    @Composable
    internal fun iconColor(selected: Boolean, enabled: Boolean): State<Color> {
        val targetValue = when {
            !enabled -> disabledIconColor
            selected -> selectedIconColor
            else -> unselectedIconColor
        }
        return animateColorAsState(
            targetValue = targetValue,
            animationSpec = tween(ITEM_ANIMATION_DURATION_MILLIS),
        )
    }

    @Composable
    internal fun textColor(selected: Boolean, enabled: Boolean): State<Color> {
        val targetValue = when {
            !enabled -> disabledTextColor
            selected -> selectedTextColor
            else -> unselectedTextColor
        }
        return animateColorAsState(
            targetValue = targetValue,
            animationSpec = tween(ITEM_ANIMATION_DURATION_MILLIS),
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is NavigationRailItemColors) return false

        if (selectedIconColor != other.selectedIconColor) return false
        if (unselectedIconColor != other.unselectedIconColor) return false
        if (selectedTextColor != other.selectedTextColor) return false
        if (unselectedTextColor != other.unselectedTextColor) return false
        if (disabledIconColor != other.disabledIconColor) return false
        if (disabledTextColor != other.disabledTextColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedIconColor.hashCode()
        result = 31 * result + unselectedIconColor.hashCode()
        result = 31 * result + selectedTextColor.hashCode()
        result = 31 * result + unselectedTextColor.hashCode()
        result = 31 * result + disabledIconColor.hashCode()
        result = 31 * result + disabledTextColor.hashCode()

        return result
    }
}

@Composable
private fun NavigationRailItemBaselineLayout(
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
) {
    Layout(
        {
            Box(Modifier.layoutId(ICON_LAYOUT_ID_TAG)) { icon() }

            if (label != null) {
                Box(
                    Modifier
                        .layoutId(LABEL_LAYOUT_ID_TAG)
                        .alpha(if (alwaysShowLabel) 1f else animationProgress),
                ) { label() }
            }
        },
    ) { measurables, constraints ->
        val iconPlaceable =
            measurables.first { it.layoutId == ICON_LAYOUT_ID_TAG }.measure(constraints)

        val labelPlaceable =
            label?.let {
                measurables
                    .first { it.layoutId == LABEL_LAYOUT_ID_TAG }
                    .measure(
                        // Measure with loose constraints for height as we don't want the label to
                        // take up more space than it needs
                        constraints.copy(minHeight = 0),
                    )
            }

        if (label == null) {
            placeIcon(iconPlaceable, constraints)
        } else {
            placeLabelAndIcon(
                labelPlaceable!!,
                iconPlaceable,
                constraints,
                alwaysShowLabel,
                animationProgress,
            )
        }
    }
}

private fun MeasureScope.placeIcon(
    iconPlaceable: Placeable,
    constraints: Constraints,
): MeasureResult {
    val width = constraints.constrainWidth(iconPlaceable.width)
    val height = constraints.maxHeight

    val iconX = (width - iconPlaceable.width) / 2
    val iconY = (height - iconPlaceable.height) / 2

    return layout(width, height) {
        iconPlaceable.placeRelative(iconX, iconY)
    }
}

private fun MeasureScope.placeLabelAndIcon(
    labelPlaceable: Placeable,
    iconPlaceable: Placeable,
    constraints: Constraints,
    alwaysShowLabel: Boolean,
    animationProgress: Float,
): MeasureResult {
    val height = constraints.maxHeight

    val labelY = height - labelPlaceable.height - NavigationRailItemVerticalPadding.roundToPx()

    val selectedIconY = NavigationRailItemVerticalPadding.roundToPx()
    val unselectedIconY =
        if (alwaysShowLabel) selectedIconY else (height - iconPlaceable.height) / 2
    val iconDistance = unselectedIconY - selectedIconY

    val offset = (iconDistance * (1 - animationProgress)).roundToInt()
    val width = constraints.constrainWidth(
        maxOf(
            iconPlaceable.width,
            labelPlaceable.width,
        ),
    )
    val labelX = (width - labelPlaceable.width) / 2
    val iconX = (width - iconPlaceable.width) / 2

    return layout(width, height) {
        if (alwaysShowLabel || animationProgress != 0f) {
            labelPlaceable.placeRelative(labelX, labelY + offset)
        }
        iconPlaceable.placeRelative(iconX, selectedIconY + offset)
    }
}

private const val ICON_LAYOUT_ID_TAG: String = "icon"

private const val LABEL_LAYOUT_ID_TAG: String = "label"

private const val ITEM_ANIMATION_DURATION_MILLIS: Int = 150

internal val NavigationRailItemWidth: Dp = 80.0.dp

internal val NavigationRailItemHeight: Dp = 56.0.dp

internal val NavigationRailItemVerticalPadding: Dp = 4.dp
