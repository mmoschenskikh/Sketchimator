package ru.maxultra.sketchimator.feature_frame_generation

import ru.maxultra.sketchimator.Frame

interface FrameSequenceGenerator {
    fun generate(frameCount: Int): List<Frame>
}
