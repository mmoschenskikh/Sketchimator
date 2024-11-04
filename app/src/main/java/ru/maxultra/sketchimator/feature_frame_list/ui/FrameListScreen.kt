package ru.maxultra.sketchimator.feature_frame_list.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.maxultra.sketchimator.MainViewModel
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.feature_frame_list.ui.components.FRAME_PREVIEW_WIDTH
import ru.maxultra.sketchimator.feature_frame_list.ui.components.SingleFrameItem
import ru.maxultra.sketchimator.feature_frame_list.ui.components.TopBar
import ru.maxultra.sketchimator.feature_frame_list.ui.factory.toTopBarVm
import ru.maxultra.sketchimator.feature_frame_list.ui.vm.SingleFrameItemListener
import ru.maxultra.sketchimator.feature_frame_list.ui.vm.TopBarListener

@Composable
fun FrameListScreen(viewModel: MainViewModel = viewModel()) {
    val appState by viewModel.appState.collectAsState()
    val frames = appState.frames
    val originalSize = appState.workingAreaSize
    val aspectRatio = originalSize.width.toFloat() / originalSize.height

    val framePreviewWidthPx = with(LocalDensity.current) { (FRAME_PREVIEW_WIDTH - DimenTokens.x4).toPx() } // DimenTokens.x4 is about getting canvas paddings into account
    val scaleFactor = framePreviewWidthPx / originalSize.width
    Scaffold(
        topBar = {
            TopBar(
                vm = appState.toTopBarVm(),
                listener = TopBarListener(
                    onBackClick = viewModel::onBackClick,
                    onRemoveAllClick = viewModel::onRemoveAllFramesClick,
                ),
                modifier = Modifier
                    .padding(
                        top = DimenTokens.x4,
                        start = DimenTokens.x4,
                        end = DimenTokens.x4,
                    ),
            )
        }
    ) { innerPadding ->
        Surface {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
                items(frames.size) { index ->
                    val frame = frames[index]
                    SingleFrameItem(
                        frame = frame,
                        index = index,
                        height = FRAME_PREVIEW_WIDTH / aspectRatio,
                        frameAspectRatio = aspectRatio,
                        scaleFactor = scaleFactor,
                        listener = SingleFrameItemListener(
                            onFrameClick = viewModel::onFrameChosen,
                            onFrameRemoveClick = viewModel::onRemoveFrameClick,
                        )
                    )
                }
            }
        }
    }
}
