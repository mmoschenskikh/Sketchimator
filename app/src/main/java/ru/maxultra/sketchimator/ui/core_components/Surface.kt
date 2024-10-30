package ru.maxultra.sketchimator.ui.core_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.maxultra.sketchimator.ui.theme.SketchimatorTheme

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        color = SketchimatorTheme.colorScheme.background,
        contentColor = SketchimatorTheme.colorScheme.onBackground,
        content = content,
    )
}
