package ru.maxultra.sketchimator.ui.vm

import androidx.compose.runtime.Immutable

@Immutable
data class TopBarVm(
    val undoButtonEnabled: Boolean,
    val redoButtonEnabled: Boolean,
    val removeFrameButtonEnabled: Boolean,
    val addNewFrameButtonEnabled: Boolean,
    val openFrameListButtonEnabled: Boolean,
    val pauseAnimationButtonEnabled: Boolean,
    val startAnimationButtonEnabled: Boolean,
)

@Immutable
data class TopBarListener(
    val onUndoActionClick: () -> Unit,
    val onRedoActionClick: () -> Unit,
    val onRemoveFrameClick: () -> Unit,
    val onAddNewFrameClick: () -> Unit,
    val onOpenFrameListClick: () -> Unit,
    val onPauseAnimationClick: () -> Unit,
    val onStartAnimationClick: () -> Unit,
)
