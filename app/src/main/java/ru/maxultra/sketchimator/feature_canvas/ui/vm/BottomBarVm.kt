package ru.maxultra.sketchimator.feature_canvas.ui.vm

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

sealed class BottomBarVm {

    @Immutable
    data class DrawingTools(
        val previousColors: List<Color>,
        val selectedTool: DrawingTool?,
        val currentColor: Color,
        val showColorPalette: Boolean,
    ) : BottomBarVm()

    @Immutable
    data class FrameRateControls(
        val frameRate: Float,
    ) : BottomBarVm()
}


@Immutable
data class BottomBarListener(
    val onPencilClicked: () -> Unit,
    val onEraserClicked: () -> Unit,
    val onColorPaletteClicked: () -> Unit,
    val onColorSelected: (Color?) -> Unit,
    val onFrameRateChanged: (fps: Float) -> Unit,
)
