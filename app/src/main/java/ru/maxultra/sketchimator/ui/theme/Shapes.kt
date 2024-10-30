package ru.maxultra.sketchimator.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import ru.maxultra.sketchimator.ui.theme.tokens.ShapeTokens

@Immutable
data class SketchimatorShapes(
    val extraLarge: CornerBasedShape = ShapeTokens.CornerExtraLarge,
) {
    val None = ShapeTokens.CornerNone
    val Full = ShapeTokens.CornerFull
}

internal val LocalSketchimatorShapes = staticCompositionLocalOf { SketchimatorShapes() }
