package ru.maxultra.sketchimator.core_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import ru.maxultra.sketchimator.core_ui.theme.tokens.ColorDarkTokens
import ru.maxultra.sketchimator.core_ui.theme.tokens.ColorLightTokens

@Immutable
data class SketchimatorColorScheme(
    val background: Color,
    val onBackground: Color,
    val onBackgroundSecondary: Color,
    val highlight: Color,
    val fill: Color,
)

fun sketchimatorLightColors() = SketchimatorColorScheme(
    background = ColorLightTokens.Background,
    onBackground = ColorLightTokens.OnBackground,
    onBackgroundSecondary = ColorLightTokens.OnBackgroundSecondary,
    highlight = ColorLightTokens.Highlight,
    fill = ColorLightTokens.Fill,
)

fun sketchimatorDarkColors() = SketchimatorColorScheme(
    background = ColorDarkTokens.Background,
    onBackground = ColorDarkTokens.OnBackground,
    onBackgroundSecondary = ColorDarkTokens.OnBackgroundSecondary,
    highlight = ColorDarkTokens.Highlight,
    fill = ColorDarkTokens.Fill,
)

internal val LocalSketchimatorColorScheme = staticCompositionLocalOf { sketchimatorLightColors() }
