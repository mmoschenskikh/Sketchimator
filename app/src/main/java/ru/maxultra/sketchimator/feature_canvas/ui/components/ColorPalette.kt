package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
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
    onColorSelected: (Color?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val yOffset = with(LocalDensity.current) {
        DimenTokens.x16.toPx() + WindowInsets.systemBars.getBottom(this)
    }.toInt().unaryMinus()
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
                    onClick = { },
                )
                IconButton(
                    painter = OutlinedCircleColorPainter(Color.Blue),
                    onClick = { onColorSelected.invoke(Color.Blue) },
                )
                IconButton(
                    painter = OutlinedCircleColorPainter(Color.Red),
                    onClick = { onColorSelected.invoke(Color.Red) },
                )
                IconButton(
                    painter = OutlinedCircleColorPainter(Color.Green),
                    onClick = { onColorSelected.invoke(Color.Green) },
                )
                IconButton(
                    painter = OutlinedCircleColorPainter(Color.Yellow),
                    onClick = { onColorSelected.invoke(Color.Yellow) },
                )
            }
        }

    }
}

@DayNightPreview
@Composable
private fun BottomBarPreview() {
    SketchimatorTheme {
        ColorPalette(
            onColorSelected = {}
        )
    }
}
