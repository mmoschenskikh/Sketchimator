package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.IconButtonDefaultSize
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens

@Composable
fun BrushSettings(
    width: Float,
    onWidthChanged: (Float) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val yOffset = with(LocalDensity.current) {
        DimenTokens.x20.toPx() + WindowInsets.systemBars.getBottom(this)
    }.toInt().unaryMinus()
    Popup(
        alignment = Alignment.Center,
        offset = IntOffset(0, yOffset),
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = modifier.padding(horizontal = DimenTokens.x6),
            shape = SketchimatorTheme.shapes.medium,
            color = SketchimatorTheme.colorScheme.onBackgroundSecondary.copy(alpha = .5f),
        ) {
            Row(
                modifier = Modifier.padding(DimenTokens.x2),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.width_px),
                )
                Spacer(Modifier.width(DimenTokens.x2))
                Slider(
                    modifier = Modifier
                        .height(IconButtonDefaultSize)
                        .weight(0.9f),
                    value = width,
                    onValueChange = onWidthChanged,
                    valueRange = 1f..120f,
                    steps = 118,
                    colors = SliderDefaults.colors(
                        thumbColor = SketchimatorTheme.colorScheme.onBackground,
                        inactiveTrackColor = Color.Transparent,
                        activeTrackColor = SketchimatorTheme.colorScheme.onBackground,
                        inactiveTickColor = SketchimatorTheme.colorScheme.onBackgroundSecondary,
                        activeTickColor = Color.Transparent,
                    ),
                )
                Text(
                    modifier = Modifier
                        .weight(0.15f, fill = true),
                    text = "${width.toInt()}",
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}
