package ru.maxultra.sketchimator.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import ru.maxultra.sketchimator.ui.theme.tokens.ColorDarkTokens
import ru.maxultra.sketchimator.ui.theme.tokens.ColorLightTokens

@Immutable
data class SketchimatorColorScheme(
    val background: Color,
    val onBackground: Color,
    val onBackgroundSecondary: Color,
    val highlight: Color,
)

fun sketchimatorLightColors() = SketchimatorColorScheme(
    background = ColorLightTokens.Background,
    onBackground = ColorLightTokens.OnBackground,
    onBackgroundSecondary = ColorLightTokens.OnBackgroundSecondary,
    highlight = ColorLightTokens.Highlight,
)

fun sketchimatorDarkColors() = SketchimatorColorScheme(
    background = ColorDarkTokens.Background,
    onBackground = ColorDarkTokens.OnBackground,
    onBackgroundSecondary = ColorDarkTokens.OnBackgroundSecondary,
    highlight = ColorDarkTokens.Highlight,
)

internal val LocalSketchimatorColorScheme = staticCompositionLocalOf { sketchimatorLightColors() }