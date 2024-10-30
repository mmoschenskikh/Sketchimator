package ru.maxultra.sketchimator.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import ru.maxultra.sketchimator.core_ui.theme.tokens.ColorDarkTokens
import ru.maxultra.sketchimator.core_ui.theme.tokens.ColorLightTokens

object SketchimatorTheme {

    internal val colorSchemeLight = sketchimatorLightColors()

    internal val colorSchemeDark = sketchimatorDarkColors()

    val colorScheme: SketchimatorColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalSketchimatorColorScheme.current

    val typography: SketchimatorTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalSketchimatorTypography.current

    val shapes: SketchimatorShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalSketchimatorShapes.current
}


@Composable
fun SketchimatorTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    SketchimatorTheme(
        colorScheme = if (isSystemInDarkTheme) SketchimatorTheme.colorSchemeDark else SketchimatorTheme.colorSchemeLight,
        typography = SketchimatorTheme.typography,
        shapes = SketchimatorTheme.shapes,
        content = content,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchimatorTheme(
    colorScheme: SketchimatorColorScheme = SketchimatorTheme.colorScheme,
    typography: SketchimatorTypography = SketchimatorTheme.typography,
    shapes: SketchimatorShapes = SketchimatorTheme.shapes,
    content: @Composable () -> Unit,
) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalSketchimatorColorScheme provides colorScheme,
            LocalSketchimatorTypography provides typography,
            LocalSketchimatorShapes provides shapes,
            LocalMinimumInteractiveComponentSize provides Dp.Unspecified,
            LocalContentColor provides colorScheme.onBackground,
            LocalRippleConfiguration provides sketchimatorRippleConfiguration(),
        ) {
            ProvideTextStyle(value = typography.body1, content = content)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun sketchimatorRippleConfiguration() = RippleConfiguration(
    color = if (isSystemInDarkTheme()) {
        ColorDarkTokens.Fill
    } else {
        ColorLightTokens.Fill
    },
    rippleAlpha = if (isSystemInDarkTheme()) {
        RippleAlpha(alpha = ColorDarkTokens.Fill.alpha)
    } else {
        RippleAlpha(alpha = ColorLightTokens.Fill.alpha)
    }
)

private fun RippleAlpha(alpha: Float) = androidx.compose.material.ripple.RippleAlpha(
    pressedAlpha = alpha,
    focusedAlpha = alpha,
    draggedAlpha = alpha,
    hoveredAlpha = alpha,
)
