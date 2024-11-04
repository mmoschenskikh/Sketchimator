package ru.maxultra.sketchimator.feature_canvas.ui.factory

import ru.maxultra.sketchimator.AppState
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarVm
import ru.maxultra.sketchimator.isPlaying

fun AppState.toTopBarVm(): TopBarVm = TopBarVm(
    undoButtonEnabled = isPlaying.not() && currentFrame.drawnPaths.isNotEmpty(),
    redoButtonEnabled = isPlaying.not() && currentFrame.undonePaths.isNotEmpty(),
    removeFrameButtonEnabled = isPlaying.not() && frames.size > 1,
    addNewFrameButtonEnabled = isPlaying.not(),
    openFrameListButtonEnabled = isPlaying.not(),
    pauseAnimationButtonEnabled = isPlaying,
    startAnimationButtonEnabled = isPlaying.not() && frames.size > 1,
)
