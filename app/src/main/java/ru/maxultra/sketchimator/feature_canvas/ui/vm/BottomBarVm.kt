package ru.maxultra.sketchimator.feature_canvas.ui.vm

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class BottomBarVm(
    val selectedTool: DrawingTool?,
    val currentColor: Color,
    val showColorPalette: Boolean,
)

@Immutable
data class BottomBarListener(
    val onPencilClicked: () -> Unit,
    val onEraserClicked: () -> Unit,
    val onColorPaletteClicked: () -> Unit,
)
