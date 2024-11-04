package ru.maxultra.sketchimator.feature_frame_generation.impl

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toOffset
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.Frame
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.feature_frame_generation.FrameSequenceGenerator
import kotlin.math.sign
import kotlin.random.Random

class MovingTriangleGenerator(
    private val frameSize: IntSize,
    parameters: DrawParameters,
) : FrameSequenceGenerator {

    private val parameters: DrawParameters = parameters.copy(drawingTool = DrawingTool.PENCIL)

    override fun generate(frameCount: Int): List<Frame> {
        val firstPointX = Random.nextInt(frameSize.width)
        val firstPointY = Random.nextInt(frameSize.height)
        val secondPointX = Random.nextInt(firstPointX + POINT_OFFSET_LOWER_BOUND, firstPointX + POINT_OFFSET_UPPER_BOUND)
        val secondPointY = firstPointY + Random.nextInt(-POINT_OFFSET_LOWER_BOUND, POINT_OFFSET_UPPER_BOUND)
        val thirdPointX = firstPointX + Random.nextInt(-POINT_OFFSET_LOWER_BOUND, POINT_OFFSET_UPPER_BOUND)
        val thirdPointY = Random.nextInt(firstPointY + POINT_OFFSET_LOWER_BOUND, firstPointY + POINT_OFFSET_UPPER_BOUND)

        var triangle = buildTrianglePath(
            IntOffset(firstPointX, firstPointY).toOffset(),
            IntOffset(secondPointX, secondPointY).toOffset(),
            IntOffset(thirdPointX, thirdPointY).toOffset(),
        )

        var offsetX = getRandomOffset()
        var offsetY = getRandomOffset()

        val frames = Array(frameCount) {
            offsetX = if (triangle.isOutOfBoundsByX(frameSize, offsetX)) {
                getRandomOffset() * -sign(offsetX)
            } else {
                offsetX
            }

            offsetY = if (triangle.isOutOfBoundsByY(frameSize, offsetY)) {
                getRandomOffset() * -sign(offsetY)
            } else {
                offsetY
            }

            triangle = triangle.copy().apply { translate(Offset(offsetX, offsetY)) }
            Frame(
                drawnPaths = listOf(PathWithParameters(triangle, parameters)),
                undonePaths = emptyList(),
            )
        }

        return frames.toList()
    }

    private fun Path.isOutOfBoundsByX(
        frameSize: IntSize,
        offsetX: Float,
    ): Boolean = when {
        offsetX < 0 -> this.getBounds().left + offsetX <= 0 // moving left
        offsetX > 0 -> this.getBounds().right + offsetX >= frameSize.width // moving right
        else -> false
    }

    private fun Path.isOutOfBoundsByY(
        frameSize: IntSize,
        offsetY: Float,
    ): Boolean = when {
        offsetY < 0 -> this.getBounds().top + offsetY <= 0 // moving up
        offsetY > 0 -> this.getBounds().bottom + offsetY >= frameSize.height // moving down
        else -> false
    }

    private fun getRandomOffset(): Float = Random.nextInt(MIN_OFFSET_PER_FRAME, MAX_OFFSET_PER_FRAME).toFloat()

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

private const val MIN_OFFSET_PER_FRAME = 10
private const val MAX_OFFSET_PER_FRAME = 50

private const val POINT_OFFSET_LOWER_BOUND = 100
private const val POINT_OFFSET_UPPER_BOUND = 200
