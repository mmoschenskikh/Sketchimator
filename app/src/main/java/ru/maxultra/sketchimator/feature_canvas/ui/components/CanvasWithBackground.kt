package ru.maxultra.sketchimator.feature_canvas.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ru.maxultra.sketchimator.DrawParameters
import ru.maxultra.sketchimator.PathWithParameters
import ru.maxultra.sketchimator.R
import ru.maxultra.sketchimator.core_ui.core_components.Surface
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme
import ru.maxultra.sketchimator.core_ui.theme.tokens.DimenTokens

@Composable
fun CanvasWithBackground(
    innerPadding: PaddingValues,
    @DrawableRes background: Int = R.drawable.background,
    drawParameters: DrawParameters,
    currentFramePaths: List<PathWithParameters>,
    previousFramePaths: List<PathWithParameters>? = null,
    onPathAdded: (Path) -> Unit,
) {
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
                painter = painterResource(id = background),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            FingerDrawingCanvas(
                drawParameters = drawParameters,
                currentFramePaths = currentFramePaths,
                previousFramePaths = previousFramePaths,
                onPathAdded = onPathAdded,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(DimenTokens.x4)
                    .clip(SketchimatorTheme.shapes.extraLarge),
            )
        }
    }
}
