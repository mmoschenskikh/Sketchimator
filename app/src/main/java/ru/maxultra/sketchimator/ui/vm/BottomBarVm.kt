package ru.maxultra.sketchimator.ui.vm

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class BottomButtonVm(
    @DrawableRes val icon: Int,
    val isSelected: Boolean,
)

@Immutable
data class BottomBarVm(
    val currentColor: Color,
)

@Immutable
data class BottomBarListener(
    val onPencilClicked: () -> Unit,
    val onBrushClicked: () -> Unit,
    val onEraserClicked: () -> Unit,
    val onShapesPaletteClicked: () -> Unit,
    val onColorPaletteClicked: () -> Unit,
)
