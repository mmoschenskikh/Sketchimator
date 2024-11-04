package ru.maxultra.sketchimator.feature_frame_list.ui.vm

import androidx.compose.runtime.Immutable

@Immutable
data class TopBarVm(
    val frameCount: Int,
)

@Immutable
data class TopBarListener(
    val onBackClick: () -> Unit,
    val onRemoveAllClick: () -> Unit,
)
