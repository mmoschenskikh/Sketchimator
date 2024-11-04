package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingStatus
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool

@Composable
fun FingerDrawingCanvas(
    drawParameters: DrawParameters,
    currentFramePaths: List<PathWithParameters>,
    previousFramePaths: List<PathWithParameters>?,
    onPathAdded: (Path) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var status by remember { mutableStateOf(DrawingStatus.IDLE) }
    var currentlyDrawingPath by remember { mutableStateOf(Path()) }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    var pointer = awaitFirstDown()
                    currentPosition = pointer.position
                    status = DrawingStatus.DOWN
                    val change = awaitTouchSlopOrCancellation(pointer.id) { pointerChange: PointerInputChange, _: Offset ->
                        if (pointerChange.positionChange() != Offset.Zero) {
                            pointerChange.consume()
                        }
                    }
                    change?.let {
                        drag(it.id) { draggingChange ->
                            pointer = draggingChange
                            currentPosition = pointer.position
                            status = DrawingStatus.MOVE
                        }
                    }
                    status = DrawingStatus.UP
                }
            }
    ) {
        if (drawParameters.drawingTool != DrawingTool.NONE) {
            when (status) {
                DrawingStatus.DOWN -> {
                    currentlyDrawingPath.moveTo(currentPosition.x, currentPosition.y)
                    previousPosition = currentPosition
                }

                DrawingStatus.MOVE -> {
                    currentlyDrawingPath.quadraticTo(
                        previousPosition.x,
                        previousPosition.y,
                        (previousPosition.x + currentPosition.x) / 2,
                        (previousPosition.y + currentPosition.y) / 2
                    )
                    previousPosition = currentPosition
                }

                DrawingStatus.UP -> {
                    if (currentlyDrawingPath.isEmpty.not() && currentlyDrawingPath.getBounds().size != INVISIBLE_PATH_SIZE) {
                        onPathAdded(currentlyDrawingPath)
                    }
                    currentlyDrawingPath = Path()
                    currentPosition = Offset.Unspecified
                    previousPosition = Offset.Unspecified
                    status = DrawingStatus.IDLE
                }

                DrawingStatus.IDLE -> Unit
            }
        }
        with(drawContext.canvas.nativeCanvas) {
            val previousFrameLayer = saveLayer(null, null)
            previousFramePaths?.forEach { path -> drawPathWithParameters(path, drawingPreviousFrame = true) }
            restoreToCount(previousFrameLayer)

            val currentFrameLayer = saveLayer(null, null)
            currentFramePaths.forEach { path -> drawPathWithParameters(path) }
            drawPathWithParameters(PathWithParameters(currentlyDrawingPath, drawParameters))
            restoreToCount(currentFrameLayer)
        }
    }
}

fun DrawScope.drawPathWithParameters(
    path: PathWithParameters,
    drawingPreviousFrame: Boolean = false,
) {
    val stroke = Stroke(
        width = path.parameters.strokeWidth,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round,
    )
    when (path.parameters.drawingTool) {
        DrawingTool.PENCIL -> {
            val alpha = if (drawingPreviousFrame) {
                PREVIOUS_FRAME_ALPHA * path.parameters.alpha
            } else {
                path.parameters.alpha
            }
            drawPath(
                path = path.path,
                color = path.parameters.color,
                alpha = alpha,
                style = stroke,
                blendMode = if (drawingPreviousFrame) {
                    BlendMode.DstAtop
                } else {
                    DrawScope.DefaultBlendMode
                }
            )
        }

        DrawingTool.ERASER -> {
            drawPath(
                path = path.path,
                color = Color.Transparent,
                style = stroke,
                blendMode = BlendMode.Clear,
            )
        }

        DrawingTool.NONE -> Unit
    }
}

private val INVISIBLE_PATH_SIZE = Size(width = 0f, height = 0f)
private const val PREVIOUS_FRAME_ALPHA = 0.25f
