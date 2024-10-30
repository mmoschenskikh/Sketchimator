package ru.maxultra.sketchimator

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens
import ru.maxultra.sketchimator.feature_canvas.ui.components.BottomBar
import ru.maxultra.sketchimator.feature_canvas.ui.components.TopBar
import ru.maxultra.sketchimator.feature_canvas.ui.vm.BottomBarListener
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarListener
import ru.maxultra.sketchimator.feature_canvas.ui.vm.TopBarVm

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SketchimatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            vm = TopBarVm(
                                undoButtonEnabled = true,
                                redoButtonEnabled = false,
                                removeFrameButtonEnabled = false,
                                addNewFrameButtonEnabled = true,
                                openFrameListButtonEnabled = true,
                                pauseAnimationButtonEnabled = false,
                                startAnimationButtonEnabled = true,
                            ),
                            listener = TopBarListener(
                                onUndoActionClick = {},
                                onRedoActionClick = {},
                                onRemoveFrameClick = {},
                                onAddNewFrameClick = {},
                                onOpenFrameListClick = {},
                                onPauseAnimationClick = {},
                                onStartAnimationClick = {},
                            ),
                            modifier = Modifier.padding(top = DimenTokens.x4, start = DimenTokens.x4, end = DimenTokens.x4),
                        )
                    },
                    bottomBar = {
                        BottomBar(
                            listener = BottomBarListener(
                                onPencilClicked = {},
                                onBrushClicked = {},
                                onEraserClicked = {},
                                onShapesPaletteClicked = {},
                                onColorPaletteClicked = {},
                            ),
                            modifier = Modifier.padding(start = DimenTokens.x4, end = DimenTokens.x4, bottom = DimenTokens.x4),
                        )
                    }

                ) { innerPadding ->
                    Surface {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(DimenTokens.x4)
                                    .clip(SketchimatorTheme.shapes.extraLarge),
                                painter = painterResource(id = R.drawable.background),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                            WorkingArea(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(DimenTokens.x4)
                                    .clip(SketchimatorTheme.shapes.extraLarge),
                            )
                        }
                    }
                }

            }
        }
    }
}


/**
 * Working area that contains a canvas to draw on.
 */
@Composable
fun WorkingArea(modifier: Modifier = Modifier) {
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var status by remember { mutableStateOf(MotionEvent.ACTION_UP) }
    val path by remember { mutableStateOf(Path()) }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    var pointerChange = awaitFirstDown()
                    currentPosition = pointerChange.position
                    status = MotionEvent.ACTION_DOWN
                    val change = awaitTouchSlopOrCancellation(pointerChange.id) { cc: PointerInputChange, _: Offset ->
                        if (cc.positionChange() != Offset.Zero) cc.consume()
                    }
                    change?.let {
                        drag(it.id) { draggingChange ->
                            pointerChange = draggingChange
                            previousPosition = currentPosition
                            currentPosition = pointerChange.position
                            status = MotionEvent.ACTION_MOVE
                        }
                    }
                    status = MotionEvent.ACTION_OUTSIDE
                }
            }
    ) {
        when (status) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(currentPosition.x, currentPosition.y)
            }

            MotionEvent.ACTION_MOVE -> {
                path.quadraticTo(
                    previousPosition.x,
                    previousPosition.y,
                    (previousPosition.x + currentPosition.x) / 2,
                    (previousPosition.y + currentPosition.y) / 2
                )
            }

            MotionEvent.ACTION_UP -> {
                path.reset()
                currentPosition = Offset.Unspecified
                previousPosition = Offset.Unspecified
            }
        }
        drawPath(
            path,
            color = Color.Blue,
            style = Stroke(width = 5f)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SketchimatorTheme {
        Greeting("Android")
    }
}
