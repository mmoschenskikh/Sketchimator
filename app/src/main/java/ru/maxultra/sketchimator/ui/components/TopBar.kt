package ru.maxultra.sketchimator.ui.components

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
import ru.maxultra.sketchimator.ui.core_components.ButtonSize
import ru.maxultra.sketchimator.ui.core_components.IconButton
import ru.maxultra.sketchimator.ui.core_components.Surface
import ru.maxultra.sketchimator.ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.ui.util.DayNightPreview
import ru.maxultra.sketchimator.ui.vm.TopBarListener
import ru.maxultra.sketchimator.ui.vm.TopBarVm

@Composable
fun TopBar(
    topBarVm: TopBarVm,
    topBarListener: TopBarListener,
) {
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2),
            ) {
                IconButton(
                    icon = R.drawable.ic_curved_arrow_left_24,
                    onClick = topBarListener.onUndoActionClick,
                    size = ButtonSize.Small,
                    enabled = topBarVm.undoButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_curved_arrow_right_24,
                    onClick = topBarListener.onRedoActionClick,
                    size = ButtonSize.Small,
                    enabled = topBarVm.redoButtonEnabled,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(DimenTokens.x4),
            ) {
                IconButton(
                    icon = R.drawable.ic_trash_32,
                    onClick = topBarListener.onRemoveFrameClick,
                    enabled = topBarVm.removeFrameButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_file_plus_32,
                    onClick = topBarListener.onAddNewFrameClick,
                    enabled = topBarVm.addNewFrameButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_layers_32,
                    onClick = topBarListener.onOpenFrameListClick,
                    enabled = topBarVm.openFrameListButtonEnabled,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(DimenTokens.x4),
            ) {
                IconButton(
                    icon = R.drawable.ic_pause_32,
                    onClick = topBarListener.onPauseAnimationClick,
                    enabled = topBarVm.pauseAnimationButtonEnabled,
                )
                IconButton(
                    icon = R.drawable.ic_play_32,
                    onClick = topBarListener.onStartAnimationClick,
                    enabled = topBarVm.startAnimationButtonEnabled,
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
            topBarVm = TopBarVm(
                undoButtonEnabled = true,
                redoButtonEnabled = false,
                removeFrameButtonEnabled = false,
                addNewFrameButtonEnabled = true,
                openFrameListButtonEnabled = true,
                pauseAnimationButtonEnabled = false,
                startAnimationButtonEnabled = true,
            ),
            topBarListener = TopBarListener(
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
