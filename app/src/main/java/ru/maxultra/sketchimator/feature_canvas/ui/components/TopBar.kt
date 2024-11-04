package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    var showDropdownMenu by remember { mutableStateOf(false) }
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
                Box {
                    IconButton(
                        icon = R.drawable.ic_file_plus_32,
                        onClick = { showDropdownMenu = true },
                        enabled = vm.addNewFrameButtonEnabled,
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .background(SketchimatorTheme.colorScheme.background),
                        expanded = showDropdownMenu,
                        onDismissRequest = { showDropdownMenu = false },
                    ) {
                        AddFrameDropdownItem(
                            text = stringResource(R.string.add_new_frame),
                            onClick = {
                                listener.onAddNewFrameClick.invoke();
                                showDropdownMenu = false
                            },
                        )
                        AddFrameDropdownItem(
                            text = stringResource(R.string.copy_frame),
                            onClick = {
                                listener.onCopyCurrentFrameClick.invoke();
                                showDropdownMenu = false
                            },
                        )
                    }
                }
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

@Composable
private fun AddFrameDropdownItem(
    text: String,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        onClick = onClick,
    ) {
        Text(
            color = SketchimatorTheme.colorScheme.onBackground,
            text = text,
        )
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
                onCopyCurrentFrameClick = {},
                onOpenFrameListClick = {},
                onPauseAnimationClick = {},
                onStartAnimationClick = {},
            ),
        )
    }
}
