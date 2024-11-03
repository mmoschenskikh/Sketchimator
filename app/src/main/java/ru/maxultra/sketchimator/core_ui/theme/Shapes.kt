package ru.maxultra.sketchimator.core_ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import ru.maxultra.sketchimator.core_ui.theme.tokens.CornerSizeTokens
import ru.maxultra.sketchimator.core_ui.theme.tokens.ShapeTokens

@Immutable
data class SketchimatorShapes(
    val extraLarge: CornerBasedShape = ShapeTokens.CornerExtraLarge,
    val medium: CornerBasedShape = ShapeTokens.CornerMedium,
    val small: CornerBasedShape = ShapeTokens.CornerSmall,
    val extraSmall: CornerBasedShape = ShapeTokens.CornerExtraSmall,
) {
    companion object {
        val None = ShapeTokens.CornerNone
        val Full = ShapeTokens.CornerFull
    }
}

val CornerBasedShape.top: CornerBasedShape
    get() = copy(bottomStart = CornerSizeTokens.None, bottomEnd = CornerSizeTokens.None)

val CornerBasedShape.bottom: CornerBasedShape
    get() = copy(topStart = CornerSizeTokens.None, topEnd = CornerSizeTokens.None)

internal val LocalSketchimatorShapes = staticCompositionLocalOf { SketchimatorShapes() }
