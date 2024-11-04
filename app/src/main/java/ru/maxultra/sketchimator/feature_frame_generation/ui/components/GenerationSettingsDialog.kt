package ru.maxultra.sketchimator.feature_frame_generation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Dialog
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview
import ru.maxultra.sketchimator.feature_frame_generation.generator.FrameSequenceGenerator
import ru.maxultra.sketchimator.feature_frame_generation.ui.vm.GenerationOptionVm
import ru.maxultra.sketchimator.feature_frame_generation.ui.vm.GenerationSettingsListener
import ru.maxultra.sketchimator.feature_frame_generation.ui.vm.GenerationSettingsVm

@Composable
fun GenerationSettingsDialog(
    vm: GenerationSettingsVm,
    listener: GenerationSettingsListener,
    modifier: Modifier = Modifier,
) {
    var frameNumber by remember { mutableIntStateOf(30) }
    Dialog(onDismissRequest = { listener.onGenerationOptionChosen.invoke(null, 0) }) {
        Surface(
            modifier = modifier,
            shape = SketchimatorTheme.shapes.medium,
        ) {
            Column(modifier = Modifier.padding(top = DimenTokens.x4)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.generation_settings_title),
                    style = SketchimatorTheme.typography.headline4.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(DimenTokens.x4))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DimenTokens.x4),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.frame_count),
                    )
                    TextField(
                        modifier = Modifier
                            .weight(1f),
                        value = frameNumber.toString(),
                        onValueChange = { value ->
                            val longValue = value.toLongOrNull()
                            frameNumber = longValue?.coerceIn(vm.minFrameCount.toLong(), vm.maxFrameCount.toLong())?.toInt() ?: 0
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = SketchimatorTheme.colorScheme.onBackground,
                            backgroundColor = SketchimatorTheme.colorScheme.fill,
                        )
                    )
                }
                Spacer(Modifier.height(DimenTokens.x4))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.generation_patterns),
                    style = SketchimatorTheme.typography.headline4.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(DimenTokens.x4))
                vm.generationOptions.fastForEachIndexed { index, option ->
                        HorizontalDivider()
                    GeneratorOption(
                        name = option.name,
                        onClick = { listener.onGenerationOptionChosen.invoke(option.type, frameNumber) },
                    )
                }
            }
        }
    }
}

@Composable
private fun GeneratorOption(
    name: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(DimenTokens.x4)
    ) {
        Text(text = name)
    }
}

@DayNightPreview
@Composable
private fun GenerationSettingsDialogPreview() {
    SketchimatorTheme {
        GenerationSettingsDialog(
            vm = GenerationSettingsVm(
                minFrameCount = 0,
                maxFrameCount = 0,
                generationOptions = FrameSequenceGenerator.Type.entries.map { type ->
                    when (type) {
                        FrameSequenceGenerator.Type.MOVING_TRIANGLE -> GenerationOptionVm(
                            name = stringResource(R.string.generator_moving_triangle),
                            type = type,
                        )
                        FrameSequenceGenerator.Type.DVD_SCREENSAVER -> GenerationOptionVm(
                            name = stringResource(R.string.generator_dvd_screensaver),
                            type = type,
                        )
                    }
                }
            ),
            listener = GenerationSettingsListener(onGenerationOptionChosen = { _, _ -> }),
        )
    }
}
