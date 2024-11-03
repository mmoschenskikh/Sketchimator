package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.bottom
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.theme.tokens.PaletteTokens
import ru.maxultra.sketchimator.core_ui.theme.top
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview

@Composable
fun ColorPickerDialog(
    sourceColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var red by remember { mutableFloatStateOf(sourceColor.red * 255) }
    var green by remember { mutableFloatStateOf(sourceColor.green * 255) }
    var blue by remember { mutableFloatStateOf(sourceColor.blue * 255) }
    var alpha by remember { mutableFloatStateOf(sourceColor.alpha * 255) }

    val color = Color(
        red = red / 255,
        green = green / 255,
        blue = blue / 255,
        alpha = alpha / 255,
    )

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = modifier,
            shape = SketchimatorTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier.padding(
                    top = DimenTokens.x4,
                    start = DimenTokens.x4,
                    end = DimenTokens.x4,
                ),
            ) {
                ColorPreview(
                    text = stringResource(R.string.original_color),
                    colorClip = SketchimatorTheme.shapes.small.top,
                    color = sourceColor,
                )
                ColorPreview(
                    text = stringResource(R.string.resulting_color),
                    colorClip = SketchimatorTheme.shapes.small.bottom,
                    color = color,
                )
                RgbaChannelSlider(red, onValueChange = { red = it }) { ColorChannelIcon(Color.Red) }
                RgbaChannelSlider(green, onValueChange = { green = it }) { ColorChannelIcon(Color.Green) }
                RgbaChannelSlider(blue, onValueChange = { blue = it }) { ColorChannelIcon(Color.Blue) }
                RgbaChannelSlider(alpha, onValueChange = { alpha = it }) { AlphaChannelIcon() }
                DialogControlButtonRow(
                    onDismiss = { onDismiss.invoke() },
                    onConfirm = { onColorSelected.invoke(color) },
                )
                Spacer(Modifier.height(DimenTokens.x1))
            }
        }
    }
}

@Composable
private fun ColorPreview(
    text: String,
    colorClip: Shape,
    color: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
        )
        Box(
            modifier = Modifier
                .clip(colorClip)
                .weight(1f)
                .background(color)
                .height(DimenTokens.x6)
        )
    }
}

@Composable
private fun RgbaChannelSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    sliderIcon: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        sliderIcon.invoke()
        Spacer(Modifier.width(DimenTokens.x4))
        Slider(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(0.9f),
            valueRange = 0f..255f,
            steps = 256,
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
            text = "${value.toInt()}",
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun DialogControlButtonRow(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.Center) {
        TextButton(
            modifier = Modifier.weight(1f),
            onClick = onDismiss,
        ) {
            Text(
                text = stringResource(R.string.dismiss),
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.width(DimenTokens.x4))
        TextButton(
            modifier = Modifier.weight(1f),
            onClick = onConfirm,
        ) {
            Text(
                text = stringResource(R.string.confirm),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ColorChannelIcon(color: Color, ) {
    Box(
        modifier = Modifier
            .clip(SketchimatorTheme.shapes.extraSmall)
            .background(color)
            .size(DimenTokens.x4)
    )
}

@Composable
private fun AlphaChannelIcon() {
    Column(
        modifier = Modifier
            .clip(SketchimatorTheme.shapes.extraSmall)
            .size(DimenTokens.x4)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .background(PaletteTokens.Gray45)
                    .size(DimenTokens.x2)
            )
            Box(
                modifier = Modifier
                    .background(PaletteTokens.LightBluishWhite)
                    .size(DimenTokens.x2)
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .background(PaletteTokens.LightBluishWhite)
                    .size(DimenTokens.x2)
            )
            Box(
                modifier = Modifier
                    .background(PaletteTokens.Gray45)
                    .size(DimenTokens.x2)
            )
        }
    }
}

@DayNightPreview
@Composable
private fun ColorPickerDialogPreview() {
    SketchimatorTheme {
        ColorPickerDialog(
            sourceColor = Color.Magenta,
            onColorSelected = {},
            onDismiss = {},
        )
    }
}
