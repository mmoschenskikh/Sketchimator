package ru.maxultra.sketchimator.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import ru.maxultra.sketchimator.ui.theme.tokens.ShapeTokens

object SketchimatorShapes {
    val None = ShapeTokens.CornerNone
    val Full = ShapeTokens.CornerFull
}

internal val LocalSketchimatorShapes = staticCompositionLocalOf { SketchimatorShapes }
