package ru.maxultra.sketchimator.core_ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import ru.maxultra.sketchimator.core_ui.theme.tokens.TypographyTokens

@Immutable
data class SketchimatorTypography(
    val headline4: TextStyle = TypographyTokens.Headline4,
    val body1: TextStyle = TypographyTokens.Body1,
)

internal val LocalSketchimatorTypography = staticCompositionLocalOf { SketchimatorTypography() }
