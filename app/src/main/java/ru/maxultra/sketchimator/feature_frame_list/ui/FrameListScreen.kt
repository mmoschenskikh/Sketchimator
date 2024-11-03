package ru.maxultra.sketchimator.feature_frame_list.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.maxultra.sketchimator.MainViewModel
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme

@Composable
fun FrameListScreen(viewModel: MainViewModel = viewModel()) {
    val appState by viewModel.appState.collectAsState()
    val frames = appState.frames
    Surface {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(frames) { frame ->
                Box {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(SketchimatorTheme.shapes.extraLarge),
                        painter = painterResource(id = R.drawable.background),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        frame.drawnPaths.forEach { path ->
                            drawPath(
                                path.path,
                                color = Color.Blue,
                                style = Stroke(width = 10f)
                            )
                        }
                    }
                }
            }
        }
    }
}
