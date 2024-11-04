package ru.maxultra.sketchimator.feature_canvas.ui.factory

import ru.maxultra.sketchimator.SketchimatorScreen
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarVm

fun SketchimatorScreen.Canvas.toBottomBarVm(): BottomBarVm = BottomBarVm(
    previousColors = previousColors,
    selectedTool = parameters.drawingTool.takeIf { showColorPalette.not() },
    currentColor = parameters.color,
    showColorPalette = showColorPalette,
    isBottomBarEnabled = isPlaying.not(),
)
