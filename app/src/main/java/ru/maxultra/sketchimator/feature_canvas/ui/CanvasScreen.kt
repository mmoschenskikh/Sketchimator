package ru.maxultra.sketchimator.feature_canvas.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.maxultra.sketchimator.MainViewModel
import ru.maxultra.sketchimator.SketchimatorScreen
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.feature_canvas.ui.components.BottomBar
import ru.maxultra.sketchimator.feature_canvas.ui.components.CanvasWithBackground
import ru.maxultra.sketchimator.feature_canvas.ui.components.TopBar
import ru.maxultra.sketchimator.feature_canvas.ui.factory.toBottomBarVm
import ru.maxultra.sketchimator.feature_canvas.ui.factory.toTopBarVm
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarListener
import ru.maxultra.sketchimator.feature_canvas.ui.vm.DrawingTool
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarListener
import ru.maxultra.sketchimator.isPlaying


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
                    onCopyCurrentFrameClick = viewModel::onCopyCurrentFrameClick,
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
                    onFrameRateChanged = { viewModel.onFrameRateChanged(it) },
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
        CanvasWithBackground(
            innerPadding = innerPadding,
            drawParameters = screen.parameters,
            currentFrameNumber = state.frames.size.takeIf { state.isPlaying.not() },
            currentFramePaths = viewModel.currentFrameDrawnPaths,
            previousFramePaths = state.previousFrame?.drawnPaths?.takeIf { state.isPlaying.not() },
            onPathAdded = viewModel::onPathDrawn,
        )
    }
}
