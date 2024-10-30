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
import androidx.compose.ui.graphics.Color
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.ui.core_components.IconButton
import ru.maxultra.sketchimator.ui.core_components.Surface
import ru.maxultra.sketchimator.ui.graphics.painter.OutlinedCircleColorPainter
import ru.maxultra.sketchimator.ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.ui.util.DayNightPreview
import ru.maxultra.sketchimator.ui.vm.BottomBarListener

@Composable
fun BottomBar(
    listener: BottomBarListener,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2, Alignment.CenterHorizontally),
        ) {
            IconButton(
                icon = R.drawable.ic_pencil_32,
                onClick = listener.onPencilClicked,
            )
            IconButton(
                icon = R.drawable.ic_brush_32,
                onClick = listener.onBrushClicked,
            )
            IconButton(
                icon = R.drawable.ic_erase_32,
                iconColor = SketchimatorTheme.colorScheme.highlight,
                onClick = listener.onEraserClicked,
            )
            IconButton(
                icon = R.drawable.ic_instruments_32,
                onClick = listener.onShapesPaletteClicked,
            )
            IconButton(
                painter = OutlinedCircleColorPainter(Color.Blue, SketchimatorTheme.colorScheme.highlight),
                onClick = listener.onColorPaletteClicked,
            )
        }
    }
}

@DayNightPreview
@Composable
private fun BottomBarPreview() {
    SketchimatorTheme {
        BottomBar(
            listener = BottomBarListener(
                onPencilClicked = {},
                onBrushClicked = {},
                onEraserClicked = {},
                onShapesPaletteClicked = {},
                onColorPaletteClicked = {},
            ),
        )
    }
}
