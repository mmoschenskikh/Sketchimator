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
        val currentWidth: Float,
        val showBrushSettings: Boolean,
    ) : BottomBarVm()

    @Immutable
    data class FrameRateControls(
        val showAnimationSettings: Boolean,
        val frameRate: Float,
    ) : BottomBarVm()
}


@Immutable
data class BottomBarListener(
    val onPencilClicked: () -> Unit,
    val onEraserClicked: () -> Unit,
    val onColorPaletteClicked: () -> Unit,
    val onColorSelected: (Color?) -> Unit,
    val onAnimationSettingsClicked: () -> Unit,
    val onDismissAnimationSettings: () -> Unit,
    val onFrameRateChanged: (fps: Float) -> Unit,
    val onBrushSettingsClicked: () -> Unit,
    val onDismissBrushSettings: () -> Unit,
    val onBrushWidthChanged: (width: Float) -> Unit,
)
