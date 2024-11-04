package ru.maxultra.sketchimator.feature_frame_generation.generator

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.addSvg
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.unit.IntSize
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.Frame
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.util.PathUtils.isOutOfBoundsByX
import ru.maxultra.sketchimator.util.PathUtils.isOutOfBoundsByY
import kotlin.math.sign
import kotlin.random.Random

class DvdScreenSaverGenerator(
    private val frameSize: IntSize,
) : FrameSequenceGenerator {

    override val type = FrameSequenceGenerator.Type.DVD_SCREENSAVER

    private val parameters = DrawParameters(
        color = Color.Black,
        strokeWidth = 10f,
        drawingTool = DrawingTool.PENCIL,
    )

    override fun generate(frameCount: Int): List<Frame> {
        val scaleMatrix = Matrix().apply { scale(x = DVD_LOGO_SCALE_FACTOR, y = DVD_LOGO_SCALE_FACTOR) }
        var dvdLogo = Path().apply {
            addSvg(DVD_LOGO_SVG)
            transform(scaleMatrix)
        }

        var offsetX = getRandomOffset()
        var offsetY = getRandomOffset()

        val frames = Array(frameCount) {
            offsetX = if (dvdLogo.isOutOfBoundsByX(frameSize, offsetX)) {
                getRandomOffset() * -sign(offsetX)
            } else {
                offsetX
            }

            offsetY = if (dvdLogo.isOutOfBoundsByY(frameSize, offsetY)) {
                getRandomOffset() * -sign(offsetY)
            } else {
                offsetY
            }
            dvdLogo = dvdLogo.copy().apply { translate(Offset(offsetX, offsetY)) }
            Frame(
                drawnPaths = listOf(PathWithParameters(dvdLogo, parameters)),
                undonePaths = emptyList(),
            )
        }

        return frames.toList()
    }


    private fun getRandomOffset(): Float = Random.nextInt(MIN_OFFSET_PER_FRAME, MAX_OFFSET_PER_FRAME).toFloat()
}

private const val MIN_OFFSET_PER_FRAME = 10
private const val MAX_OFFSET_PER_FRAME = 50
private const val DVD_LOGO_SCALE_FACTOR = 1 / 5f
private const val DVD_LOGO_SVG = "m91.1,0 l-13.7,57.7 102.3,0h24c65.7,0 105.9,26.4 94.7,73.4 -12.1,51.1 -69.6,73.4 -130.7,73.4h-22.9l29.8,-125.4h-102.3l-43.5,183.2h145.1c109.1,0 212.8,-57.6 231,-131.1 3.3,-13.5 2.9,-47.3 -5.4,-67.4 -0.2,-0.8 -0.4,-1.4 -1.2,-3 -0.3,-0.7 -0.6,-3.6 1.1,-4.3 0.9,-0.4 2.7,1.5 2.9,2 0.9,2.2 1.5,3.9 1.5,3.9l92.3,260.6 235,-265.2 99.5,-0.1h24c65.8,0 106.3,26.4 95.1,73.4 -12.1,51.1 -69.9,73.4 -131,73.4h-23l29.8,-125.5h-102.3l-43.5,183.2h145.1c109.1,0 213.5,-57.4 231,-131.1 17.5,-73.8 -59.1,-131.1 -168.7,-131.1h-216.4s-57.3,67.9 -68,80.7c-57.1,68.8 -67.2,87.2 -69,92 0.2,-4.8 -1.8,-23.4 -26.2,-93 -6.5,-18.5 -27.4,-79.7 -27.4,-79.7h-389.3zM499.8,324.2c-276,0 -499.8,31.7 -499.8,70.8s223.8,70.8 499.8,70.8c276,0 499.8,-31.7 499.8,-70.8s-223.8,-70.8 -499.8,-70.8zM481.7,372.8c63,0 114.1,10.6 114.1,23.6s-51.1,23.6 -114.1,23.6c-63,0 -114.1,-10.6 -114.1,-23.6s51.1,-23.6 114.1,-23.6z"
