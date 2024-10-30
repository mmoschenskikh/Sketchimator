package ru.maxultra.sketchimator.ui.theme.tokens

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize

object CornerSizeTokens {
    val ExtraLarge = CornerSize(DimenTokens.x5)
    val None = ZeroCornerSize
    val Full = CornerSize(percent = 50)
}
