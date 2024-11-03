package ru.maxultra.sketchimator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.feature_canvas.ui.CanvasScreen
import ru.maxultra.sketchimator.feature_frame_list.ui.FrameListScreen

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.appState.collectAsState()
            SketchimatorTheme {
                BackHandler(
                    enabled = state.canHandleBackClick,
                    onBack = viewModel::onBackClick,
                )

                when (state.currentScreen) {
                    SketchimatorScreen.Canvas -> CanvasScreen()
                    SketchimatorScreen.AnimationPlayer -> {

                    }
                    SketchimatorScreen.FrameList -> FrameListScreen()
                }
            }
        }
    }
}
