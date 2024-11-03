package ru.maxultra.sketchimator

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(
        AppState(
            screenStack = listOf(SketchimatorScreen.Canvas),
            frames = listOf(
                Frame(
                    drawnPaths = emptyList(),
                    undonePaths = emptyList(),
                )
            ),
        )
    )
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    val paths = mutableStateListOf<Path>()

    fun onPathDrawn(path: Path) {
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                currentFrame.copy(
                    drawnPaths = currentFrame.drawnPaths + path,
                )
            }
        }
        clearUndonePaths()
    }

    fun onUndoActionClick() {
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                val undonePath = currentFrame.drawnPaths.last()
                currentFrame.copy(
                    drawnPaths = currentFrame.drawnPaths.dropLast(1),
                    undonePaths = currentFrame.undonePaths + undonePath,
                )
            }
        }
    }

    fun onRedoActionClick() {
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                val redonePath = currentFrame.undonePaths.last()
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
    }

    fun onAddNewFrameClick() {
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

    private fun clearUndonePaths() {
        _appState.update { currentState ->
            currentState.copyWithCurrentFrameChanged { currentFrame ->
                currentFrame.copy(undonePaths = emptyList())
            }
        }
    }
}

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

data class Frame(
    val drawnPaths: List<Path>,
    val undonePaths: List<Path>,
)

sealed class SketchimatorScreen {

    data object Canvas : SketchimatorScreen()

    data object AnimationPlayer : SketchimatorScreen()

    data object FrameList : SketchimatorScreen()
}
