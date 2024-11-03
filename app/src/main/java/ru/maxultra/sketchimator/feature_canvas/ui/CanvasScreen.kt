package ru.maxultra.sketchimator.feature_canvas.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.MainViewModel
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.SketchimatorScreen
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.feature_canvas.ui.components.BottomBar
import ru.maxultra.sketchimator.feature_canvas.ui.components.TopBar
import ru.maxultra.sketchimator.feature_canvas.ui.factory.toBottomBarVm
import ru.maxultra.sketchimator.feature_canvas.ui.factory.toTopBarVm
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarListener
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingStatus
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarListener


@Composable
fun CanvasScreen(viewModel: MainViewModel = viewModel()) {
    val state by viewModel.appState.collectAsState()
    val screen = state.currentScreen as SketchimatorScreen.Canvas
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                vm = state.toTopBarVm(),
                listener = TopBarListener(
                    onUndoActionClick = viewModel::onUndoActionClick,
                    onRedoActionClick = viewModel::onRedoActionClick,
                    onRemoveFrameClick = viewModel::onRemoveFrameClick,
                    onAddNewFrameClick = viewModel::onAddNewFrameClick,
                    onOpenFrameListClick = viewModel::onOpenFrameListClick,
                    onPauseAnimationClick = viewModel::onPauseAnimationClick,
                    onStartAnimationClick = viewModel::onStartAnimationClick,
                ),
                modifier = Modifier
                    .padding(
                        top = DimenTokens.x4,
                        start = DimenTokens.x4,
                        end = DimenTokens.x4,
                    ),
            )
        },
        bottomBar = {
            BottomBar(
                vm = screen.toBottomBarVm(),
                listener = BottomBarListener(
                    onPencilClicked = { viewModel.onToolClicked(DrawingTool.PENCIL) },
                    onEraserClicked = { viewModel.onToolClicked(DrawingTool.ERASER) },
                    onColorPaletteClicked = { viewModel.onPaletteClicked() },
                    onColorSelected = { viewModel.onColorSelected(it) },
                ),
                modifier = Modifier
                    .padding(
                        start = DimenTokens.x4,
                        end = DimenTokens.x4,
                        bottom = DimenTokens.x4,
                    ),
            )
        }
    ) { innerPadding ->
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(DimenTokens.x4)
                        .clip(SketchimatorTheme.shapes.extraLarge),
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                WorkingArea(
                    drawParameters = screen.parameters,
                    currentFramePaths = viewModel.currentFrameDrawnPaths,
                    previousFramePaths = state.previousFrame?.drawnPaths,
                    onPathAdded = viewModel::onPathDrawn,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(DimenTokens.x4)
                        .clip(SketchimatorTheme.shapes.extraLarge),
                )
            }
        }
    }
}

/**
 * Working area that contains a canvas to draw on.
 */
@Composable
private fun WorkingArea(
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

private fun DrawScope.drawPathWithParameters(
    path: PathWithParameters,
    drawingPreviousFrame: Boolean = false,
) {
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
                style = Stroke(width = path.parameters.strokeWidth),
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
                style = Stroke(width = path.parameters.strokeWidth),
                blendMode = BlendMode.Clear,
            )
        }

        DrawingTool.NONE -> Unit
    }
}

private val INVISIBLE_PATH_SIZE = Size(width = 0f, height = 0f)
private const val PREVIOUS_FRAME_ALPHA = 0.25f
