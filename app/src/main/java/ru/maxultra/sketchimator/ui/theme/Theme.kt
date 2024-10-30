package ru.maxultra.sketchimator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

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
    content: @Composable () -> Unit
) {
    SketchimatorTheme(
        colorScheme = if (isSystemInDarkTheme) SketchimatorTheme.colorSchemeDark else SketchimatorTheme.colorSchemeLight,
        typography = SketchimatorTheme.typography,
        shapes = SketchimatorTheme.shapes,
        content = content,
    )
}


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
        ) {
            ProvideTextStyle(value = typography.body1, content = content)
        }
    }
}
