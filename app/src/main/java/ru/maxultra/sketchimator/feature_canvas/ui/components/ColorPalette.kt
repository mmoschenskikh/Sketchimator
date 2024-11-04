package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.IconButton
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.graphics.painter.OutlinedCircleColorPainter
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview

@Composable
fun ColorPalette(
    previousColors: List<Color>,
    sourceColor: Color,
    onColorSelected: (Color?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val yOffset = with(LocalDensity.current) {
        DimenTokens.x20.toPx() + WindowInsets.systemBars.getBottom(this)
    }.toInt().unaryMinus()
    var showColorPickerDialog by remember { mutableStateOf(false) }
    if (showColorPickerDialog) {
        ColorPickerDialog(
            sourceColor = sourceColor,
            onColorSelected = onColorSelected,
            onDismiss = { showColorPickerDialog = false },
        )
    }
    Popup(
        alignment = Alignment.Center,
        offset = IntOffset(0, yOffset),
        onDismissRequest = { onColorSelected.invoke(null) },
    ) {
        Surface(
            modifier = modifier,
            shape = SketchimatorTheme.shapes.medium,
            color = SketchimatorTheme.colorScheme.onBackgroundSecondary.copy(alpha = .5f),
        ) {
            Row(
                modifier = Modifier.padding(DimenTokens.x2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2, Alignment.CenterHorizontally),
            ) {
                IconButton(
                    icon = R.drawable.ic_palette_32,
                    onClick = { showColorPickerDialog = true },
                )
                for (color in previousColors) {
                    QuickColor(color, onColorSelected)
                }
            }
        }
    }
}

@Composable
private fun QuickColor(
    color: Color,
    onColorSelected: (Color) -> Unit,
) {
    IconButton(
        painter = OutlinedCircleColorPainter(color),
        onClick = { onColorSelected.invoke(color) },
    )
}

@DayNightPreview
@Composable
private fun ColorPalettePreview() {
    SketchimatorTheme {
        ColorPalette(
            previousColors = emptyList(),
            sourceColor = Color.Blue,
            onColorSelected = {}
        )
    }
}
