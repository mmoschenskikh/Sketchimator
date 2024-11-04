package ru.maxultra.sketchimator.feature_frame_generation.generator

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.setFrom
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toOffset
import androidx.core.graphics.rotationMatrix
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.Frame
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.util.PathUtils.buildTrianglePath
import ru.maxultra.sketchimator.util.PathUtils.isOutOfBoundsByX
import ru.maxultra.sketchimator.util.PathUtils.isOutOfBoundsByY
import kotlin.math.sign
import kotlin.random.Random


class MovingTriangleGenerator(
    private val frameSize: IntSize,
    parameters: DrawParameters,
) : FrameSequenceGenerator {

    override val type = FrameSequenceGenerator.Type.MOVING_TRIANGLE

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
        val rotationMatrix = Matrix()

        var rotation = getRandomRotation(Random.nextBoolean())
        val frames = Array(frameCount) {
            triangle = triangle.copy()
            val bounds = triangle.getBounds()
            rotationMatrix.setFrom(
                rotationMatrix(
                    degrees = rotation,
                    px = bounds.center.x,
                    py = bounds.center.y,
                )
            )

            triangle.transform(rotationMatrix)

            offsetX = if (triangle.isOutOfBoundsByX(frameSize, offsetX)) {
                rotation = getRandomRotation(offsetY < 0)
                getRandomOffset() * -sign(offsetX)
            } else {
                offsetX
            }

            offsetY = if (triangle.isOutOfBoundsByY(frameSize, offsetY)) {
                rotation = getRandomRotation(offsetX < 0)
                getRandomOffset() * -sign(offsetY)
            } else {
                offsetY
            }

            triangle.translate(Offset(offsetX, offsetY))
            Frame(
                drawnPaths = listOf(PathWithParameters(triangle, parameters)),
                undonePaths = emptyList(),
            )
        }

        return frames.toList()
    }

    private fun getRandomOffset(): Float = Random.nextInt(MIN_OFFSET_PER_FRAME, MAX_OFFSET_PER_FRAME).toFloat()

    private fun getRandomRotation(
        clockwise: Boolean,
    ): Float {
        val lowerBound = if (clockwise) MIN_ROTATION_PER_FRAME else -MAX_ROTATION_PER_FRAME
        val upperBound = if (clockwise) MAX_ROTATION_PER_FRAME else MIN_ROTATION_PER_FRAME
        return Random.nextInt(lowerBound, upperBound).toFloat()
    }
}

private const val MIN_OFFSET_PER_FRAME = 10
private const val MAX_OFFSET_PER_FRAME = 50

private const val MIN_ROTATION_PER_FRAME = 0
private const val MAX_ROTATION_PER_FRAME = 30

private const val POINT_OFFSET_LOWER_BOUND = 100
private const val POINT_OFFSET_UPPER_BOUND = 200
