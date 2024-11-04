package ru.maxultra.sketchimator.feature_canvas.ui.factory

import ru.maxultra.sketchimator.SketchimatorScreen
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarVm
import ru.maxultra.sketchimator.util.FrameRateUtils

fun SketchimatorScreen.Canvas.toBottomBarVm(): BottomBarVm =
    if (isPlaying) {
        BottomBarVm.FrameRateControls(
            frameRate = FrameRateUtils.frameTimeMsToFps(frameTimeMs),
        )
    } else {
        BottomBarVm.DrawingTools(
            previousColors = previousColors,
            selectedTool = parameters.drawingTool.takeIf { showColorPalette.not() },
            currentColor = parameters.color,
            showColorPalette = showColorPalette,
        )
    }
