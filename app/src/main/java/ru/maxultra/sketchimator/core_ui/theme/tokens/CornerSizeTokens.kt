package ru.maxultra.sketchimator.core_ui.theme.tokens

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize

object CornerSizeTokens {
    val ExtraLarge = CornerSize(DimenTokens.x5)
    val Medium = CornerSize(DimenTokens.x3)
    val Small = CornerSize(DimenTokens.x2)
    val None = ZeroCornerSize
    val Full = CornerSize(percent = 50)
}
