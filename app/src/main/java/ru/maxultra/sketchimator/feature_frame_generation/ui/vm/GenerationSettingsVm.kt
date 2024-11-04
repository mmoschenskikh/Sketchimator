package ru.maxultra.sketchimator.feature_frame_generation.ui.vm

import androidx.compose.runtime.Immutable
import ru.maxultra.sketchimator.feature_frame_generation.generator.FrameSequenceGenerator

@Immutable
data class GenerationSettingsVm(
    val minFrameCount: Int = MIN_FRAME_COUNT,
    val maxFrameCount: Int = MAX_FRAME_COUNT,
    val generationOptions: List<GenerationOptionVm>,
)

@Immutable
data class GenerationOptionVm(
    val name: String,
    val type: FrameSequenceGenerator.Type,
)

@Immutable
data class GenerationSettingsListener(
    val onGenerationOptionChosen: (type: FrameSequenceGenerator.Type?, frameCount: Int) -> Unit,
)

private const val MIN_FRAME_COUNT = 0
private const val MAX_FRAME_COUNT = Int.MAX_VALUE
