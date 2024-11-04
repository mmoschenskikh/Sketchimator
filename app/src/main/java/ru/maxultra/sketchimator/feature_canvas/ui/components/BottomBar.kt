package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                iconColor = when {
                    vm.isBottomBarEnabled.not() -> SketchimatorTheme.colorScheme.onBackgroundSecondary
                    vm.selectedTool == DrawingTool.PENCIL -> SketchimatorTheme.colorScheme.highlight
                    else -> SketchimatorTheme.colorScheme.onBackground
                },
                enabled = vm.isBottomBarEnabled,
            )
            IconButton(
                icon = R.drawable.ic_erase_32,
                onClick = listener.onEraserClicked,
                iconColor = when {
                    vm.isBottomBarEnabled.not() -> SketchimatorTheme.colorScheme.onBackgroundSecondary
                    vm.selectedTool == DrawingTool.ERASER -> SketchimatorTheme.colorScheme.highlight
                    else -> SketchimatorTheme.colorScheme.onBackground
                },
                enabled = vm.isBottomBarEnabled,
            )
            IconButton(
                painter = OutlinedCircleColorPainter(
                    color = vm.currentColor,
                    outlineColor = if (vm.showColorPalette && vm.isBottomBarEnabled) {
                        SketchimatorTheme.colorScheme.highlight
                    } else {
                        Color.Unspecified
                    },
                ),
                onClick = listener.onColorPaletteClicked,
                enabled = vm.isBottomBarEnabled,
            )
        }
    }
}

@DayNightPreview
@Composable
private fun BottomBarPreview() {
    SketchimatorTheme {
        var selectedTool by remember { mutableStateOf(DrawingTool.PENCIL) }
        var showColorPalette by remember { mutableStateOf(false) }
        BottomBar(
            vm = BottomBarVm(
                previousColors = emptyList(),
                selectedTool = selectedTool,
                currentColor = Color.Blue,
                showColorPalette = showColorPalette,
            ),
            listener = BottomBarListener(
                onPencilClicked = { selectedTool = DrawingTool.PENCIL },
                onEraserClicked = { selectedTool = DrawingTool.ERASER },
                onColorPaletteClicked = { showColorPalette = true },
                onColorSelected = { showColorPalette = false },
            ),
        )
    }
}
