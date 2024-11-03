package ru.maxultra.sketchimator

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool

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
                    showColorPalette = false,
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

    /**
     * List that contains all paths drawn in current frame.
     *
     * We need this property separately from [_appState] because
     * [_appState] takes significant time to update and causes blinking of drawn lines.
     */
    private val _currentFrameDrawnPaths: SnapshotStateList<PathWithParameters> = mutableStateListOf()
    val currentFrameDrawnPaths: List<PathWithParameters> = _currentFrameDrawnPaths

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
        val undonePath = _currentFrameDrawnPaths.removeLast()
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

    fun onOpenFrameListClick() {
        _appState.update { currentState ->
            currentState.copy(
                screenStack = currentState.screenStack + SketchimatorScreen.FrameList,
            )
        }
    }

    fun onPauseAnimationClick() {
    //                                        if (isPlaying) {
//                                            isPlaying = false
//                                            playJob?.cancel()
//                                            playJob = null
//                                            val frame = frames.removeAt(frames.lastIndex)
//                                            paths.clear()
//                                            paths.addAll(frame.first)
//                                            undonePaths.clear()
//                                            undonePaths.addAll(frame.second)
//                                        }
//

}

    fun onStartAnimationClick() {
        //                                        if (isPlaying.not()) {
//                                            isPlaying = true
//                                            playJob = lifecycleScope.launch {
//                                                frames.add(paths.toList() to undonePaths.toList())
//                                                val allFrames = frames.map { it.first }
//                                                while (isActive) {
//                                                    allFrames.forEach { currentFrame ->
//                                                        paths.clear()
//                                                        paths.addAll(currentFrame)
//                                                        delay(100L)
//                                                    }
//                                                }
//
//                                            }
//                                        }

    }

    fun onBackClick() {
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
                screenStack = screenStack.dropLast(1) + canvas.copy(showColorPalette = true,
                    parameters = canvas.parameters.copy(color = listOf(
                        Color.Blue,
                        Color.Green,
                        Color.Yellow,
                        Color.Magenta,
                        Color.Cyan,
                        Color.Red,
                    ).random()))
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
        get() = screenStack.size > 1

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
        val showColorPalette: Boolean = false,
    ) : SketchimatorScreen()

    @Immutable
    data object AnimationPlayer : SketchimatorScreen()
    @Immutable
    data object FrameList : SketchimatorScreen()
}
