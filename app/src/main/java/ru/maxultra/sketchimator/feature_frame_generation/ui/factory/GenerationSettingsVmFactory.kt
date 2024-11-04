package ru.maxultra.sketchimator.feature_frame_generation.ui.factory

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.maxultra.sketchimator.AppState
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.feature_frame_generation.generator.FrameSequenceGenerator
import ru.maxultra.sketchimator.feature_frame_generation.ui.vm.GenerationOptionVm
import ru.maxultra.sketchimator.feature_frame_generation.ui.vm.GenerationSettingsVm


@Composable
fun AppState.toGenerationSettingsVm(): GenerationSettingsVm = GenerationSettingsVm(
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
)
