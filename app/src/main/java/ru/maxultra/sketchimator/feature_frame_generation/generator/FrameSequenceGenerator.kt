package ru.maxultra.sketchimator.feature_frame_generation.generator

import ru.maxultra.sketchimator.Frame

sealed interface FrameSequenceGenerator {

    val type: Type

    fun generate(frameCount: Int): List<Frame>

    enum class Type {
        DVD_SCREENSAVER,
        MOVING_TRIANGLE,
    }
}
