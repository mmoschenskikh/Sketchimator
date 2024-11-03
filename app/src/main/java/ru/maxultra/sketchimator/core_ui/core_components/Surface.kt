package ru.maxultra.sketchimator.core_ui.core_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorShapes
import ru.maxultra.sketchimator.core_ui.theme.SketchimatorTheme

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = SketchimatorShapes.None,
    color: Color = SketchimatorTheme.colorScheme.background,
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = SketchimatorTheme.colorScheme.onBackground,
        content = content,
    )
}
