package ru.maxultra.sketchimator.util

import kotlin.math.roundToLong

object FrameRateUtils {

    fun fpsToFrameTimeMs(fps: Float): Long = (ONE_SECOND_MS / fps).roundToLong()

    fun frameTimeMsToFps(frameTimeMs: Long): Float = ONE_SECOND_MS.toFloat() / frameTimeMs

    const val DEFAULT_FRAME_TIME_MS = 100L
    const val ONE_SECOND_MS = 1000L
}
