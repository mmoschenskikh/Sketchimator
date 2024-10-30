package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.ButtonSize
import ru.maxultra.sketchimator.core_ui.core_components.IconButton
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarListener
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarVm

@Composable
fun TopBar(
    vm: TopBarVm,
    listener: TopBarListener,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars),
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(
                    icon = R.drawable.ic_curved_arrow_left_24,
                    onClick = listener.onUndoActionClick,
                    size = ButtonSize.Small,
                    enabled = vm.undoButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_curved_arrow_right_24,
                    onClick = listener.onRedoActionClick,
                    size = ButtonSize.Small,
                    enabled = vm.redoButtonEnabled,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2),
            ) {
                IconButton(
                    icon = R.drawable.ic_trash_32,
                    onClick = listener.onRemoveFrameClick,
                    enabled = vm.removeFrameButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_file_plus_32,
                    onClick = listener.onAddNewFrameClick,
                    enabled = vm.addNewFrameButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_layers_32,
                    onClick = listener.onOpenFrameListClick,
                    enabled = vm.openFrameListButtonEnabled,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2),
            ) {
                IconButton(
                    icon = R.drawable.ic_pause_32,
                    onClick = listener.onPauseAnimationClick,
                    enabled = vm.pauseAnimationButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_play_32,
                    onClick = listener.onStartAnimationClick,
                    enabled = vm.startAnimationButtonEnabled,
                )
            }
        }
    }
}

@DayNightPreview
@Composable
private fun TopBarPreview() {
    SketchimatorTheme {
        TopBar(
            vm = TopBarVm(
                undoButtonEnabled = true,
                redoButtonEnabled = false,
                removeFrameButtonEnabled = false,
                addNewFrameButtonEnabled = true,
                openFrameListButtonEnabled = true,
                pauseAnimationButtonEnabled = false,
                startAnimationButtonEnabled = true,
            ),
            listener = TopBarListener(
                onUndoActionClick = {},
                onRedoActionClick = {},
                onRemoveFrameClick = {},
                onAddNewFrameClick = {},
                onOpenFrameListClick = {},
                onPauseAnimationClick = {},
                onStartAnimationClick = {},
            ),
        )
    }
}
