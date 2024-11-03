package ru.maxultra.sketchimator.feature_canvas.ui.factory

import ru.maxultra.sketchimator.AppState
import ru.maxultra.sketchimator.SketchimatorScreen
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarVm

fun AppState.toTopBarVm(): TopBarVm = TopBarVm(
    undoButtonEnabled = currentScreen != SketchimatorScreen.AnimationPlayer && currentFrame.drawnPaths.isNotEmpty(),
    redoButtonEnabled = currentScreen != SketchimatorScreen.AnimationPlayer && currentFrame.undonePaths.isNotEmpty(),
    removeFrameButtonEnabled = currentScreen != SketchimatorScreen.AnimationPlayer && frames.size > 1,
    addNewFrameButtonEnabled = currentScreen != SketchimatorScreen.AnimationPlayer,
    openFrameListButtonEnabled = currentScreen != SketchimatorScreen.AnimationPlayer,
    pauseAnimationButtonEnabled = currentScreen == SketchimatorScreen.AnimationPlayer,
    startAnimationButtonEnabled = currentScreen != SketchimatorScreen.AnimationPlayer && frames.size > 1,
)
