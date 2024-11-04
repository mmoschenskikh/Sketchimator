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
import androidx.compose.ui.graphics.Color
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.IconButton
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.graphics.painter.OutlinedCircleColorPainter
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarListener
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarVm
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool

@Composable
fun BottomBar(
    vm: BottomBarVm,
    listener: BottomBarListener,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        when (vm) {
            is BottomBarVm.DrawingTools -> DrawingTools(vm, listener)
            is BottomBarVm.FrameRateControls -> FrameRateControls(vm, listener)
        }
    }
}

@Composable
private fun DrawingTools(vm: BottomBarVm.DrawingTools, listener: BottomBarListener) {
    if (vm.showColorPalette) {
        ColorPalette(
            previousColors = vm.previousColors,
            sourceColor = vm.currentColor,
            onColorSelected = listener.onColorSelected,
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2, Alignment.CenterHorizontally),
    ) {
        IconButton(
            icon = R.drawable.ic_pencil_32,
            onClick = listener.onPencilClicked,
            iconColor = if (vm.selectedTool == DrawingTool.PENCIL) {
                SketchimatorTheme.colorScheme.highlight
            } else {
                SketchimatorTheme.colorScheme.onBackground
            },
        )
        IconButton(
            icon = R.drawable.ic_erase_32,
            onClick = listener.onEraserClicked,
            iconColor = if (vm.selectedTool == DrawingTool.ERASER) {
                SketchimatorTheme.colorScheme.highlight
            } else {
                SketchimatorTheme.colorScheme.onBackground
            },
        )
        IconButton(
            painter = OutlinedCircleColorPainter(
                color = vm.currentColor,
                outlineColor = if (vm.showColorPalette) {
                    SketchimatorTheme.colorScheme.highlight
                } else {
                    Color.Unspecified
                },
            ),
            onClick = listener.onColorPaletteClicked,
        )
    }
}

@Composable
private fun FrameRateControls(vm: BottomBarVm.FrameRateControls, listener: BottomBarListener) {
    if (vm.showAnimationSettings) {
        AnimationSettings(
            frameRate = vm.frameRate,
            onFrameRateChanged = listener.onFrameRateChanged,
            onDismiss = listener.onDismissAnimationSettings,
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2, Alignment.CenterHorizontally),
    ) {
        IconButton(
            icon = R.drawable.ic_display_settings_32,
            onClick = listener.onAnimationSettingsClicked,
            iconColor = if (vm.showAnimationSettings) {
                SketchimatorTheme.colorScheme.highlight
            } else {
                SketchimatorTheme.colorScheme.onBackground
            },
        )
    }
}

@DayNightPreview
@Composable
private fun BottomBarDrawingToolsPreview() {
    SketchimatorTheme {
        BottomBar(
            vm = BottomBarVm.DrawingTools(
                previousColors = emptyList(),
                selectedTool = DrawingTool.PENCIL,
                currentColor = Color.Blue,
                showColorPalette = false,
            ),
            listener = BottomBarListener(
                onPencilClicked = {},
                onEraserClicked = {},
                onColorPaletteClicked = {},
                onColorSelected = {},
                onAnimationSettingsClicked = {},
                onDismissAnimationSettings = {},
                onFrameRateChanged = {},
            ),
        )
    }
}

@DayNightPreview
@Composable
private fun BottomBarFrameRateControlsPreview() {
    SketchimatorTheme {
        BottomBar(
            vm = BottomBarVm.FrameRateControls(
                showAnimationSettings = false,
                frameRate = 10f,
            ),
            listener = BottomBarListener(
                onPencilClicked = {},
                onEraserClicked = {},
                onColorPaletteClicked = {},
                onColorSelected = {},
                onAnimationSettingsClicked = {},
                onDismissAnimationSettings = {},
                onFrameRateChanged = {},
            ),
        )
    }
}
