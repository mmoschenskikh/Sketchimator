package ru.maxultra.sketchimator.feature_canvas.ui.factory

import ru.maxultra.sketchimator.SketchimatorScreen
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarVm
import ru.maxultra.sketchimator.util.FrameRateUtils

fun SketchimatorScreen.Canvas.toBottomBarVm(): BottomBarVm =
    if (isPlaying) {
        BottomBarVm.FrameRateControls(
            showAnimationSettings = showAnimationSettings,
            frameRate = FrameRateUtils.frameTimeMsToFps(frameTimeMs),
        )
    } else {
        BottomBarVm.DrawingTools(
            previousColors = previousColors,
            selectedTool = parameters.drawingTool.takeIf { showColorPalette.not() && showBrushSettings.not() },
            currentColor = parameters.color,
            showColorPalette = showColorPalette && showBrushSettings.not(),
            showBrushSettings = showBrushSettings && showColorPalette.not(),
            currentWidth = parameters.strokeWidth,
        )
    }
