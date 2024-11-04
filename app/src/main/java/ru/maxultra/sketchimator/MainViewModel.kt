package ru.maxultra.sketchimator

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.util.FrameRateUtils

class MainViewModel : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(
        AppState(
            screenStack = listOf(
                SketchimatorScreen.Canvas(
                    parameters = DrawParameters(
                        drawingTool = DrawingTool.PENCIL,
                        color = Color.Blue,
                        strokeWidth = 20f,
                    ),
                    previousColors = listOf(
                        Color.Blue,
                        Color.Green,
                        Color.Red,
                        Color.Yellow,
                    )
                ),
            ),
            frames = listOf(
                Frame(
                    drawnPaths = emptyList(),
                    undonePaths = emptyList(),
                )
            ),
        )
    )
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    private val currentState: AppState
        get() = appState.value

    /**
     * List that contains all paths drawn in current frame.
     *
     * We need this property separately from [_appState] because
     * [_appState] takes significant time to update and causes blinking of drawn lines.
     */
    private val _currentFrameDrawnPaths: SnapshotStateList<PathWithParameters> = mutableStateListOf()
    val currentFrameDrawnPaths: List<PathWithParameters> = _currentFrameDrawnPaths

    private var animationPlayerJob: Job? = null

    fun onPathDrawn(path: Path) {
        _appState.update { currentState ->
            val canvasScreen = currentState.currentScreen as SketchimatorScreen.Canvas
            if (canvasScreen.parameters.drawingTool == DrawingTool.ERASER && currentFrameDrawnPaths.isEmpty()) {
                return@update currentState
            }
            val pathWithParameters = PathWithParameters(
                path = path,
                parameters = canvasScreen.parameters,
            )
            _currentFrameDrawnPaths.add(pathWithParameters)
            clearUndonePaths()
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                currentFrame.copy(
                    drawnPaths = currentFrameDrawnPaths.toList(),
                )
            }
        }
    }

    fun onUndoActionClick() {
        val undonePath = _currentFrameDrawnPaths.removeAt(_currentFrameDrawnPaths.lastIndex)
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                currentFrame.copy(
                    drawnPaths = currentFrameDrawnPaths.toList(),
                    undonePaths = currentFrame.undonePaths + undonePath,
                )
            }
        }
    }

    fun onRedoActionClick() {
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                val redonePath = currentFrame.undonePaths.last()
                _currentFrameDrawnPaths.add(redonePath)
                currentFrame.copy(
                    drawnPaths = currentFrame.drawnPaths + redonePath,
                    undonePaths = currentFrame.undonePaths.dropLast(1),
                )
            }
        }
    }

    fun onRemoveFrameClick() {
        _appState.update { currentState ->
            currentState.copy(
                frames = currentState.frames.dropLast(1),
            )
        }
        _currentFrameDrawnPaths.clear()
        _currentFrameDrawnPaths.addAll(appState.value.currentFrame.drawnPaths)
    }

    fun onAddNewFrameClick() {
        _currentFrameDrawnPaths.clear()
        _appState.update { currentState ->
            currentState.copy(
                frames = currentState.frames + Frame(
                    drawnPaths = emptyList(),
                    undonePaths = emptyList(),
                ),
            )
        }
    }

    fun onCopyCurrentFrameClick() {
        _appState.update { currentState ->
            currentState.copy(
                frames = currentState.frames + currentState.currentFrame,
            )
        }
    }

    fun onOpenFrameListClick() {
        _appState.update { currentState ->
            currentState.copy(
                screenStack = currentState.screenStack + SketchimatorScreen.FrameList,
            )
        }
    }

    fun onPauseAnimationClick() {
        val state = currentState
        if (state.isPlaying.not()) return
        animationPlayerJob?.cancel()
        animationPlayerJob = null
        _currentFrameDrawnPaths.clear()
        _currentFrameDrawnPaths.addAll(state.currentFrame.drawnPaths)
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    isPlaying = false,
                    parameters = canvas.parameters.copy(
                        drawingTool = canvas.previousDrawingTool,
                    ),
                    previousDrawingTool = DrawingTool.NONE,
                )
            )
        }
    }

    fun onStartAnimationClick() {
        val state = currentState
        if (state.isPlaying) return
        animationPlayerJob = viewModelScope.launch {
            while (isActive) {
                state.frames.forEach { frame ->
                    _currentFrameDrawnPaths.clear()
                    _currentFrameDrawnPaths.addAll(frame.drawnPaths)
                    delay(currentState.frameTimeMs)
                }
            }
        }
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    isPlaying = true,
                    previousDrawingTool = canvas.parameters.drawingTool,
                    parameters = canvas.parameters.copy(
                        drawingTool = DrawingTool.NONE,
                    ),
                )
            )
        }
    }

    fun onBackClick() {
        val state = currentState
        if (state.isPlaying) {
            onPauseAnimationClick()
            return
        }
        _appState.update { currentState ->
            currentState.copy(
                screenStack = currentState.screenStack.dropLast(1),
            )
        }
    }

    fun onToolClicked(tool: DrawingTool) {
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(parameters = canvas.parameters.copy(drawingTool = tool))
            )
        }
    }

    fun onPaletteClicked() {
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    previousDrawingTool = canvas.parameters.drawingTool,
                    showColorPalette = true,
                    parameters = canvas.parameters.copy(
                        drawingTool = DrawingTool.NONE,
                    )
                )
            )
        }
    }

    fun onColorSelected(color: Color?) {
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    showColorPalette = false,
                    parameters = canvas.parameters.copy(
                        drawingTool = canvas.previousDrawingTool,
                        color = color ?: canvas.parameters.color,
                    ),
                    previousColors = if (color != null) {
                        (canvas.previousColors + color).takeLast(PREVIOUS_COLORS_LIMIT)
                    } else {
                        canvas.previousColors
                    },
                    previousDrawingTool = DrawingTool.NONE,
                )
            )
        }
    }

    fun onAnimationSettingsClicked() {
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    showAnimationSettings = true,
                )
            )
        }
    }

    fun onDismissAnimationSettings() {
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    showAnimationSettings = false,
                )
            )
        }
    }

    fun onFrameRateChanged(fps: Float) {
        _appState.update { currentState ->
            val screenStack = currentState.screenStack
            val canvas = screenStack.last() as SketchimatorScreen.Canvas
            currentState.copy(
                screenStack = screenStack.dropLast(1) + canvas.copy(
                    frameTimeMs = FrameRateUtils.fpsToFrameTimeMs(fps = fps),
                )
            )
        }
    }

    private fun clearUndonePaths() {
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                currentFrame.copy(undonePaths = emptyList())
            }
        }
    }
}

@Immutable
data class AppState(
    val screenStack: List<SketchimatorScreen>,
    val frames: List<Frame>,
) {
    val currentScreen: SketchimatorScreen
        get() = screenStack.last()

    val canHandleBackClick: Boolean
        get() = screenStack.size > 1 || isPlaying

    val currentFrame: Frame
        get() = frames.last()

    val previousFrame: Frame?
        get() = if (frames.size > 1) frames[frames.lastIndex - 1] else null

    fun copyWithCurrentFrameChanged(block: (currentFrame: Frame) -> Frame): AppState {
        return copy(
            frames = frames.mapIndexed { index, frame ->
                if (index == frames.lastIndex) {
                    block(frame)
                } else {
                    frame
                }
            }
        )
    }
}

val AppState.isPlaying: Boolean
    get() = (currentScreen as? SketchimatorScreen.Canvas)?.isPlaying ?: false

val AppState.frameTimeMs: Long
    get() = (currentScreen as? SketchimatorScreen.Canvas)?.frameTimeMs ?: FrameRateUtils.DEFAULT_FRAME_TIME_MS

@Immutable
data class Frame(
    val drawnPaths: List<PathWithParameters>,
    val undonePaths: List<PathWithParameters>,
)

@Immutable
data class DrawParameters(
    val drawingTool: DrawingTool,
    val color: Color,
    val strokeWidth: Float,
) {
    val alpha: Float = color.alpha
}

@Immutable
data class PathWithParameters(
    val path: Path,
    val parameters: DrawParameters,
)

sealed class SketchimatorScreen {

    @Immutable
    data class Canvas(
        val parameters: DrawParameters,
        val previousColors: List<Color>,
        val previousDrawingTool: DrawingTool = DrawingTool.NONE,
        val showAnimationSettings: Boolean = false,
        val showColorPalette: Boolean = false,
        val isPlaying: Boolean = false,
        val frameTimeMs: Long = FrameRateUtils.DEFAULT_FRAME_TIME_MS,
    ) : SketchimatorScreen()

    @Immutable
    data object FrameList : SketchimatorScreen()
}

private const val PREVIOUS_COLORS_LIMIT = 4
