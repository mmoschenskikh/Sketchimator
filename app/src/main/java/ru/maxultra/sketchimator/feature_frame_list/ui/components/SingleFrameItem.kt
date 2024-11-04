package ru.maxultra.sketchimator.feature_frame_list.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.Frame
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.IconButton
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.core_ui.util.DayNightPreview
import ru.maxultra.sketchimator.feature_canvas.ui.components.drawPathWithParameters
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.feature_frame_list.ui.vm.SingleFrameItemListener

@Composable
fun SingleFrameItem(
    frame: Frame,
    index: Int,
    height: Dp,
    frameAspectRatio: Float,
    scaleFactor: Float,
    listener: SingleFrameItemListener,
) {
    val scaleMatrix = Matrix().apply { scale(x = scaleFactor, y = scaleFactor) }
    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .padding(DimenTokens.x2)
            .clickable(enabled = ENABLE_ADDITIONAL_CONTROLS_FOR_FRAME_LIST) { listener.onFrameClick.invoke(index) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DimenTokens.x2),
    ) {
        Box {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(frameAspectRatio)
                    .padding(DimenTokens.x1)
                    .clip(SketchimatorTheme.shapes.small),
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(frameAspectRatio)
                    .padding(DimenTokens.x1)
                    .clip(SketchimatorTheme.shapes.small),
            ) {
                with(drawContext.canvas.nativeCanvas) {
                    val currentFrameLayer = saveLayer(null, null)
                    frame.drawnPaths.forEach { path ->
                        val scaledPath = path.copy(
                            path = path.path.copy().apply { transform(scaleMatrix) },
                            parameters = path.parameters.copy(
                                strokeWidth = path.parameters.strokeWidth * scaleFactor,
                            ),
                        )
                        drawPathWithParameters(scaledPath)
                    }
                    restoreToCount(currentFrameLayer)
                }
            }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.frame_number, index + 1),
            style = SketchimatorTheme.typography.headline4,
        )
        if (ENABLE_ADDITIONAL_CONTROLS_FOR_FRAME_LIST) {
            IconButton(
                icon = R.drawable.ic_pencil_32,
                onClick = { listener.onFrameClick(index) },
            )
            IconButton(
                icon = R.drawable.ic_trash_32,
                onClick = { listener.onFrameRemoveClick(index) },
            )
        }
    }
}


@DayNightPreview
@Composable
private fun SingleFrameItemPreview() {
    SketchimatorTheme {
        SingleFrameItem(
            frame = Frame(
                drawnPaths = listOf(
                    PathWithParameters(
                        Path().apply {
                            moveTo(1f, 1f)
                            lineTo(500f, 900f)
                        },
                        DrawParameters(
                            drawingTool = DrawingTool.ERASER,
                            color = Color.Black,
                            strokeWidth = 14.15f
                        )
                    )
                ),
                undonePaths = listOf(),
            ),
            index = 5,
            height = FRAME_PREVIEW_WIDTH,
            frameAspectRatio = 996f / 1416f,
            scaleFactor = with(LocalDensity.current) { FRAME_PREVIEW_WIDTH.toPx() / 996f.dp.toPx() },
            listener = SingleFrameItemListener(
                onFrameClick = {},
                onFrameRemoveClick = {},
            )
        )
    }
}

val FRAME_PREVIEW_WIDTH = DimenTokens.x44
private const val ENABLE_ADDITIONAL_CONTROLS_FOR_FRAME_LIST = false
