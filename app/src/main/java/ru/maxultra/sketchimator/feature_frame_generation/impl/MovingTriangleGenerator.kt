package ru.maxultra.sketchimator.feature_frame_generation.impl

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.unit.IntSize
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.Frame
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.feature_frame_generation.FrameSequenceGenerator
import kotlin.random.Random

class MovingTriangleGenerator(
    private val frameSize: IntSize,
    parameters: DrawParameters,
) : FrameSequenceGenerator {

    private val parameters: DrawParameters = parameters.copy(drawingTool = DrawingTool.PENCIL)

    override fun generate(frameCount: Int): List<Frame> {
        val firstPointX = Random.nextInt(frameSize.width).toFloat()
        val firstPointY = Random.nextInt(frameSize.height).toFloat()
        val secondPointX = Random.nextInt(frameSize.width).toFloat()
        val secondPointY = Random.nextInt(frameSize.height).toFloat()
        val thirdPointX = Random.nextInt(frameSize.width).toFloat()
        val thirdPointY = Random.nextInt(frameSize.height).toFloat()

        var triangle = buildTrianglePath(
            Offset(firstPointX, firstPointY),
            Offset(secondPointX, secondPointY),
            Offset(thirdPointX, thirdPointY),
        )

        val frames = mutableListOf(
            Frame(
                drawnPaths = listOf(PathWithParameters(triangle, parameters)),
                undonePaths = emptyList(),
            )
        )

        for (i in 1 until frameCount) {
            triangle = triangle.copy().apply { translate(Offset(15f, -15f)) }

            frames.add(
                Frame(
                    drawnPaths = listOf(PathWithParameters(triangle, parameters)),
                    undonePaths = emptyList(),
                )
            )
        }

        return frames
    }

    private fun buildTrianglePath(
        firstPoint: Offset,
        secondPoint: Offset,
        thirdPoint: Offset,
    ): Path = Path().apply {
        moveTo(firstPoint.x, firstPoint.y)
        lineTo(secondPoint.x, secondPoint.y)
        lineTo(thirdPoint.x, thirdPoint.y)
        lineTo(firstPoint.x, firstPoint.y)
        close()
    }
}
