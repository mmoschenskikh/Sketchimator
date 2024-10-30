package ru.maxultra.sketchimator

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import ru.maxultra.sketchimator.ui.theme.SketchimatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SketchimatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkingArea()
                }
            }
        }
    }
}

/**
 * Working area that contains a canvas to draw on.
 */
@Composable
fun WorkingArea() {
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var status by remember { mutableStateOf(MotionEvent.ACTION_UP) }
    val path by remember { mutableStateOf(Path()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    val pic = awaitFirstDown()
                    currentPosition = pic.position
                    status = MotionEvent.ACTION_DOWN
                    do {
                        val event: PointerEvent = awaitPointerEvent()

                        event.changes.forEach { pointerInputChange: PointerInputChange ->
                            previousPosition = currentPosition
                            currentPosition = pointerInputChange.position
                            status = MotionEvent.ACTION_MOVE
                            pointerInputChange.consume()
                        }
                    } while (event.changes.any { it.pressed })
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
            color = Color.Red,
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
