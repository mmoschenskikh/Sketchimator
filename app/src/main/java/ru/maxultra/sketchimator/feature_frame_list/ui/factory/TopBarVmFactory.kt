package ru.maxultra.sketchimator.feature_frame_list.ui.factory

import ru.maxultra.sketchimator.AppState
import ru.maxultra.sketchimator.feature_frame_list.ui.vm.TopBarVm

fun AppState.toTopBarVm(): TopBarVm = TopBarVm(
    frameCount = frames.size,
)
