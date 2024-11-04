package ru.maxultra.sketchimator.feature_frame_list.ui.vm

import androidx.compose.runtime.Immutable

@Immutable
data class SingleFrameItemListener(
    val onFrameClick: (index: Int) -> Unit,
    val onFrameRemoveClick: (index: Int) -> Unit,
)
